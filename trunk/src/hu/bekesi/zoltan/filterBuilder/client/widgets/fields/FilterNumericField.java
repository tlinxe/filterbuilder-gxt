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

package hu.bekesi.zoltan.filterBuilder.client.widgets.fields;

import hu.bekesi.zoltan.filterBuilder.client.criteria.SimpleModel;
import hu.bekesi.zoltan.filterBuilder.client.widgets.ComboBoxHelper;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.DelayedTask;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FilterNumericField<M extends ModelData> extends FilterField {

	private static final long serialVersionUID = 3824718298117696798L;
	ListStore<M> _store = null;
	String _comboDisplayField = null;
	String _comboValueField = null;

	public FilterNumericField() {

	}

	public FilterNumericField(String valueField_, String name_) {
		super(valueField_, name_);

	}

	public FilterNumericField(String valueField_, String name_, ListStore<M> store_, String comboDisplayField_, String comboValueField_) {
		super(valueField_, name_);
		_store = store_;
		_comboDisplayField = comboDisplayField_;
		_comboValueField = comboValueField_;
	}

	@Override
	public List<Widget> getWidgets(final SimpleModel model_) {

		ArrayList<Widget> widgets = new ArrayList<Widget>();

		final HorizontalPanel hp = new HorizontalPanel();

		SimpleComboBox<String> combo = new SimpleComboBox<String>();
		combo.setWidth(135);
		combo.setForceSelection(true);
		combo.setEditable(false);
		combo.setTriggerAction(TriggerAction.ALL);
		combo.add("=");
		combo.add("<>");
		combo.add("<");
		combo.add("<=");
		combo.add(">");
		combo.add(">=");
		combo.add("between");

		combo.setSimpleValue("=");

		if (model_.getOp() == null)
			model_.setOp("=");

		combo.addSelectionChangedListener(new SelectionChangedListener<SimpleComboValue<String>>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<SimpleComboValue<String>> se) {
				model_.setOp(se.getSelectedItem().getValue());
			}
		});

		combo.addSelectionChangedListener(new SelectionChangedListener<SimpleComboValue<String>>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<SimpleComboValue<String>> se) {
				if (se.getSelectedItem().getValue().compareTo("between") == 0) {
					hp.add(new Label("and"));

					if (_store != null) {
						final ComboBox<M> comboBox = new ComboBox<M>();
						comboBox.setStore(_store);
						comboBox.setDisplayField(_comboDisplayField);
						comboBox.setValueField(_comboValueField);
						comboBox.select(0);

						comboBox.addSelectionChangedListener(new SelectionChangedListener<M>() {

							@Override
							public void selectionChanged(SelectionChangedEvent<M> se) {
								if (model_.getDatas().size() > 1)
									model_.getDatas().remove(1);
								model_.getDatas().add(comboBox.getSelection().get(0));

							}
						});

						final DelayedTask _setValueTask = new DelayedTask(new Listener<BaseEvent>() {

							@Override
							public void handleEvent(BaseEvent be) {

								if (model_.getDatas().size() > 0)
									model_.getDatas().remove(0);
								model_.getDatas().add(ComboBoxHelper.getComboBoxRealValue(comboBox));
							}
						});

						comboBox.addKeyListener(new KeyListener() {

							@Override
							public void componentKeyPress(ComponentEvent event) {
								super.componentKeyPress(event);
								_setValueTask.delay(250);
							}
						});

						if (_store.getLoader() != null) {
							comboBox.setPageSize(10);
							comboBox.setMinListWidth(350);
						}
						hp.add(comboBox);
					} else {
						NumberField nf = new NumberField();
						nf.addListener(Events.Change, new Listener<FieldEvent>() {

							@Override
							public void handleEvent(FieldEvent be) {
								if (model_.getDatas().size() > 1)
									model_.getDatas().remove(1);
								model_.getDatas().add(be.getValue());
							}
						});
						hp.add(nf);
					}
					hp.layout(true);

				} else {
					while (hp.getItemCount() > 1) {
						hp.remove(hp.getWidget(1));
					}
					hp.layout(true);
				}

			}
		});

		widgets.add(combo);

		if (_store != null) {
			final ComboBox<M> comboBox = new ComboBox<M>();
			comboBox.setStore(_store);
			comboBox.setDisplayField(_comboDisplayField);
			comboBox.setValueField(_comboValueField);
			comboBox.select(0);

			comboBox.addSelectionChangedListener(new SelectionChangedListener<M>() {

				@Override
				public void selectionChanged(SelectionChangedEvent<M> se) {
					if (model_.getDatas().size() > 0)
						model_.getDatas().remove(0);

					model_.getDatas().add(0, comboBox.getSelection().get(0));

				}
			});

			final DelayedTask _setValueTask = new DelayedTask(new Listener<BaseEvent>() {

				@Override
				public void handleEvent(BaseEvent be) {

					if (model_.getDatas().size() > 0)
						model_.getDatas().remove(0);
					model_.getDatas().add(ComboBoxHelper.getComboBoxRealValue(comboBox));
				}
			});

			comboBox.addKeyListener(new KeyListener() {

				@Override
				public void componentKeyPress(ComponentEvent event) {
					super.componentKeyPress(event);
					_setValueTask.delay(250);
				}
			});

			if (_store.getLoader() != null) {
				comboBox.setPageSize(10);
				comboBox.setMinListWidth(350);
			}
			widgets.add(comboBox);
		} else {
			NumberField nf = new NumberField();
			nf.addListener(Events.Change, new Listener<FieldEvent>() {

				@Override
				public void handleEvent(FieldEvent be) {
					if (model_.getDatas().size() > 0)
						model_.getDatas().remove(0);
					model_.getDatas().add(0, be.getValue());
				}
			});
			hp.add(nf);
		}
		widgets.add(hp);

		return widgets;
	}

}
