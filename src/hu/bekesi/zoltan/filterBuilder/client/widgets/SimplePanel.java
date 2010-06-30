/*
 * Copyright © 2010, Contributor
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License, version 3, as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License, version 3 for more details.
 * 
 * You should have received a copy of the GNU General Public License, version 3,
 * along with this program; if not, see http://www.gnu.org/licenses/ or write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * THE FOLLOWING DISCLAIMER APPLIES TO ALL SOFTWARE CODE AND OTHER MATERIALS CONTRIBUTED IN CONNECTION WITH THIS PROGRAM:
 * 
 * THIS SOFTWARE IS LICENSED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE AND ANY WARRANTY OF NON-INFRINGEMENT,
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * THIS SOFTWARE MAY BE REDISTRIBUTED TO OTHERS ONLY BY EFFECTIVELY USING THIS OR ANOTHER EQUIVALENT DISCLAIMER AS WELL AS ANY OTHER LICENSE TERMS 
 * THAT MAY APPLY. 
 * 
 * Author : Zoltan Bekesi<bekesizoltan@gmail.com>
 * 
 * */

package hu.bekesi.zoltan.filterBuilder.client.widgets;

import hu.bekesi.zoltan.filterBuilder.client.criteria.FilterModel;
import hu.bekesi.zoltan.filterBuilder.client.criteria.SimpleModel;
import hu.bekesi.zoltan.filterBuilder.client.icons.FilterBuilderIcons;
import hu.bekesi.zoltan.filterBuilder.client.widgets.fields.FilterField;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HideMode;
import com.extjs.gxt.ui.client.Style.IconAlign;
import com.extjs.gxt.ui.client.core.XTemplate;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelKeyProvider;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class SimplePanel extends HorizontalPanel implements Filter {

	public static final FilterBuilderIcons ICONS = GWT.create(FilterBuilderIcons.class);

	ComboBox<FilterField> _combo;
	SimpleModel _model = null;
	FilterPanel _panel;
	ListStore<FilterField> _store;

	FilterField selectedField = null;
	boolean _currentlySelectedFieldWillBeUpdated = false;
	XTemplate _comboBoxTemplate;
	
	public SimplePanel(final VerticalPanel verticalPanel, final FilterPanel panel, ListStore<FilterField> store,XTemplate comboBoxTemplate) {
		final SimplePanel hp = this;
		_panel = panel;
		_model = new SimpleModel(store.getAt(0).getValueField());
		_comboBoxTemplate = comboBoxTemplate;
		Button minus = new Button();// "-");

		minus.setIcon(ICONS.delete());
		minus.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				verticalPanel.remove(hp);
				panel.forceLayout();
				panel.getFilterModel().getSubFilters().remove(_model);
			}
		});
		hp.add(minus);

		_store = store;
		_store.addStoreListener(new StoreListener<FilterField>() {

			@Override
			public void storeRemove(StoreEvent<FilterField> se) {
				if (se.getModel().equals(selectedField)) {
					_combo.setToolTip("Invalid field!");
					_combo.addStyleName("x-form-invalid");
					while (hp.getItemCount() > 2) {
						hp.remove(hp.getWidget(2));
					}
				}
				super.storeRemove(se);
			}

			@Override
			public void storeUpdate(StoreEvent<FilterField> se) {
				if (_currentlySelectedFieldWillBeUpdated) {
					FilterField ff = _combo.getStore().findModel("valueField", se.getModel().getValueField());
					_combo.disableEvents(true);
					ArrayList<FilterField> selection = new ArrayList<FilterField>();
					selection.add(ff);
					_combo.setSelection(selection);
					_combo.enableEvents(true);
					_combo.setValue(ff);
				}
				super.storeDataChanged(se);
			}

		});

		_combo = new ComboBox<FilterField>();
		_combo.addSelectionChangedListener(new SelectionChangedListener<FilterField>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<FilterField> se) {
				selectedField = se.getSelectedItem();
				while (hp.getItemCount() > 2) {
					hp.remove(hp.getWidget(2));
				}
				if (se.getSelectedItem() != null) {
					_combo.removeStyleName("x-form-invalid");
					_combo.setToolTip((ToolTipConfig) null);
					List<Widget> w = se.getSelectedItem().getWidgets(_model);
					for (Widget widget : w) {
						hp.add(widget);
					}
				} else {
					_combo.setToolTip("Invalid field!");
					_combo.addStyleName("x-form-invalid");
				}
				hp.layout(true);

			}
		});

		_combo.setStore(store);
		_combo.setTriggerAction(TriggerAction.ALL);

		_combo.setDisplayField("name");
		_combo.setValueField("valueField");
		_combo.setAllowBlank(false);
		_combo.setEditable(false);
		
		if(_comboBoxTemplate != null)
		{
			_combo.setTemplate(_comboBoxTemplate);
		}
		
		_combo.addSelectionChangedListener(new SelectionChangedListener<FilterField>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<FilterField> se) {
				// model.setField(se.getSelectedItem().getName());
				if (se.getSelectedItem() != null) {
					_model.setValueField(se.getSelectedItem().getValueField());
				} else {
					_model.setValueField(null);
				}
			}
		});

		hp.add(_combo);

		this.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				_combo.select(0);

			}

		});
	}

	@Override
	public FilterModel getFilterModel() {
		return _model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setFilterExpression(FilterModel filterModel) {
		_model = (SimpleModel) filterModel;
		FilterField ff = _combo.getStore().findModel("valueField", _model.getValueField());

		if (ff == null) {
			selectedField = null;
			_combo.setToolTip("Invalid field!");
			_combo.addStyleName("x-form-invalid");

		} else {
			_combo.removeStyleName("x-form-invalid");
			_combo.setToolTip((ToolTipConfig) null);
			_combo.disableEvents(true);
			ArrayList<FilterField> selection = new ArrayList<FilterField>();
			selection.add(ff);
			selectedField = ff;
			_combo.setSelection(selection);
			_combo.enableEvents(true);
			List<Widget> w = ff.getWidgets(_model);
			for (Widget widget : w) {
				add(widget);
			}
			int dataIndexer = 0;
			for (int i = 0; i < w.size(); i++) {
				Widget widget = w.get(i);
				add(widget);
				if (i == 0) {
					SimpleComboBox<String> comboS = (SimpleComboBox<String>) widget;
					SimpleComboValue<String> val = comboS.getStore().findModel("value", _model.getOp());
					comboS.select(val);
					ArrayList<SimpleComboValue<String>> selection2 = new ArrayList<SimpleComboValue<String>>();
					selection2.add(val);
					comboS.setSelection(selection2);

				}

				if (i % 2 == 1) {
					if (widget instanceof HorizontalPanel) {
						((NumberField) ((HorizontalPanel) widget).getItem(0)).setValue((Number) _model.getDatas().get(dataIndexer++));

						if (_model.getDatas().size() > 1)
							((NumberField) ((HorizontalPanel) widget).getItem(2)).setValue((Number) _model.getDatas().get(dataIndexer++));
					} else if (widget instanceof TextField) {
						if (!(_model.getDatas().get(dataIndexer) instanceof ModelData)) {
							if (widget instanceof ComboBox) {
								// ((ComboBox) widget).clearSelections();
								// ((ComboBox) widget).clear();

								final ComboBox comboBox = (ComboBox) widget;
								if (comboBox.isRendered()) {
									comboBox.setRawValue((String) _model.getDatas().get(dataIndexer++));
								} else {
									final int dataIndex = dataIndexer++;
									
									comboBox.addListener(Events.Render, new Listener<BaseEvent>() {

										@Override
										public void handleEvent(BaseEvent be) {
											comboBox.setRawValue((String) _model.getDatas().get(dataIndex));
											comboBox.removeListener(Events.Render, this);
										}
									});

								}
							} else {
								((TextField) widget).setValue(_model.getDatas().get(dataIndexer++));
							}
						} else {
							((TextField) widget).setValue(_model.getDatas().get(dataIndexer++));
						}
					}
				}
			}
		}

		layout(true);
		_panel.forceLayout();
	}

	@Override
	public void prepareUpdateField(String id) {
		_currentlySelectedFieldWillBeUpdated = _combo.getSelection().size() > 0 && _combo.getSelection().get(0).getValueField().equals(id);
	}

}
