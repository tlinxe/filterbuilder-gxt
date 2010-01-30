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

import com.extjs.gxt.ui.client.Style.IconAlign;
import com.extjs.gxt.ui.client.data.ModelKeyProvider;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class SimplePanel extends HorizontalPanel implements Filter {

	public static final FilterBuilderIcons ICONS = GWT
			.create(FilterBuilderIcons.class);

	ComboBox<FilterField> combo;
	SimpleModel model = null;
	FilterPanel panel;
	ListStore<FilterField> store;

	public SimplePanel(final VerticalPanel verticalPanel,
			final FilterPanel panel, List<FilterField> fields) {
		final SimplePanel hp = this;
		this.panel = panel;
		// model = new SimpleModel(fields.get(0).getName());
		model = new SimpleModel(fields.get(0).getValueField());

		Button minus = new Button();// "-");

		minus.setIcon(ICONS.delete());
		minus.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				verticalPanel.remove(hp);
				panel.forceLayout();
			}
		});
		hp.add(minus);

		store = new ListStore<FilterField>();
		store.setKeyProvider(new ModelKeyProvider<FilterField>() {

			@Override
			public String getKey(FilterField model) {
				return model.getValueField();
			}
		});

		store.add(fields);

		combo = new ComboBox<FilterField>();
		combo
				.addSelectionChangedListener(new SelectionChangedListener<FilterField>() {
					@Override
					public void selectionChanged(
							SelectionChangedEvent<FilterField> se) {
						while (hp.getItemCount() > 2) {
							hp.remove(hp.getWidget(2));
						}
						List<Widget> w = se.getSelectedItem().getWidgets(model);
						for (Widget widget : w) {
							hp.add(widget);
						}
						hp.layout(true);
					}
				});

		combo.setStore(store);
		combo.setTriggerAction(TriggerAction.ALL);
		combo.setForceSelection(true);
		combo.setDisplayField("name");
		combo.setValueField("valueField");

		combo
				.addSelectionChangedListener(new SelectionChangedListener<FilterField>() {

					@Override
					public void selectionChanged(
							SelectionChangedEvent<FilterField> se) {
						// model.setField(se.getSelectedItem().getName());
						model.setValueField(se.getSelectedItem()
								.getValueField());
					}
				});

		hp.add(combo);

		this.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				combo.select(0);

			}

		});
	}

	@Override
	public FilterModel getFilterModel() {
		return model;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setFilterExpression(FilterModel filterModel) {
		model = (SimpleModel) filterModel;
		FilterField ff = combo.getStore().findModel("name",
				model.getValueField());
		// combo.select(ff);
		combo.disableEvents(true);
		ArrayList<FilterField> selection = new ArrayList<FilterField>();
		selection.add(ff);
		combo.setSelection(selection);
		combo.enableEvents(true);
		List<Widget> w = ff.getWidgets(model);
		for (Widget widget : w) {
			add(widget);
		}

		for (int i = 0; i < w.size(); i++) {
			Widget widget = w.get(i);
			add(widget);
			if (i == 0) {
				SimpleComboBox<String> comboS = (SimpleComboBox<String>) widget;
				SimpleComboValue<String> val = comboS.getStore().findModel(
						"value", model.getOp());
				comboS.select(val);
				ArrayList<SimpleComboValue<String>> selection2 = new ArrayList<SimpleComboValue<String>>();
				selection2.add(val);
				comboS.setSelection(selection2);

			}

			if (i % 2 == 1) {
				if (widget instanceof TextField) {
					((TextField) widget).setValue(model.getDatas().get(
							(i - 1) % 2));
				}
			}
		}

		layout(true);
		panel.forceLayout();
	}

	@Override
	public void addField(FilterField field) {
		store.add(field);

	}

	@Override
	public void removeField(FilterField field) {
		int index = store.indexOf(field);
		store.remove(field);
		store.insert(field, index);
	}

	@Override
	public void updateField(FilterField field) {
		store.remove(field);
	}
}
