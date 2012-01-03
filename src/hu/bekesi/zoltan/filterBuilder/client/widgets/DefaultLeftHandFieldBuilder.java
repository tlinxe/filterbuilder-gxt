/*
 *
 *   Copyright 2011 Bryn Ryans
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *   NOTICE THE GXT ( Ext-GWT ) LIBRARY IS A GPL v3 LICENCED PRODUCT.
 *   FIND OUT MORE ON:  http://www.sencha.com/license
 *
 *   Author : Bryn Ryans<snayrb99@gmail.com>
 *
 * */
package hu.bekesi.zoltan.filterBuilder.client.widgets;

import hu.bekesi.zoltan.filterBuilder.client.criteria.SimpleModel;
import hu.bekesi.zoltan.filterBuilder.client.resources.ResourceHelper;
import hu.bekesi.zoltan.filterBuilder.client.widgets.fields.FilterField;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.store.StoreListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.tips.ToolTipConfig;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.ui.Widget;

/**
 * Default implementation of the left hand field using a combo box.
 */
public class DefaultLeftHandFieldBuilder implements LeftHandFieldBuilder {

	private String _comboBoxTemplate;

	public DefaultLeftHandFieldBuilder(String comboBoxTemplate) {
		this._comboBoxTemplate = comboBoxTemplate;
	}

	@Override
	public Field<FilterField> create(final ListStore<FilterField> store, final SimplePanel hp, final SimpleModel simpleModel) {

		final ComboBox<FilterField> combo = new ComboBox<FilterField>();

		store.addStoreListener(new StoreListener<FilterField>() {

			@Override
			public void storeRemove(StoreEvent<FilterField> se) {
				FilterField selectedField = combo.getData("selectedField");
				if (se.getModel().equals(selectedField)) {
					combo.setToolTip(ResourceHelper.getInvalidFieldMessage());
					combo.addStyleName("x-form-invalid");
					while (hp.getItemCount() > 2) {
						hp.remove(hp.getWidget(2));
					}
				}
				super.storeRemove(se);
			}

			@Override
			public void storeUpdate(StoreEvent<FilterField> se) {
				boolean currentlySelectedFieldWillBeUpdated = Boolean.TRUE.equals(combo.getData("currentlySelectedFieldWillBeUpdated"));
				if (currentlySelectedFieldWillBeUpdated) {
					FilterField ff = combo.getStore().findModel("valueField", se.getModel().getValueField());
					combo.disableEvents(true);
					ArrayList<FilterField> selection = new ArrayList<FilterField>();
					selection.add(ff);
					combo.setSelection(selection);
					combo.enableEvents(true);
					combo.setValue(ff);
				}
				super.storeDataChanged(se);
			}

		});


		combo.addSelectionChangedListener(new SelectionChangedListener<FilterField>() {
			@Override
			public void selectionChanged(SelectionChangedEvent<FilterField> se) {
				FilterField selectedField = se.getSelectedItem();
				combo.setData("selectedField", selectedField);
				while (hp.getItemCount() > 2) {
					hp.remove(hp.getWidget(2));
				}
				if (se.getSelectedItem() != null) {
					combo.removeStyleName("x-form-invalid");
					combo.setToolTip((ToolTipConfig) null);
					List<Widget> w = se.getSelectedItem().getWidgets(simpleModel);
					for (Widget widget : w) {
						hp.add(widget);
					}
				} else {
					combo.setToolTip(ResourceHelper.getInvalidFieldMessage());
					combo.addStyleName("x-form-invalid");
				}
				hp.layout(true);

			}
		});

		combo.setStore(store);
		combo.setTriggerAction(TriggerAction.ALL);

		combo.setDisplayField("name");
		combo.setValueField("valueField");
		combo.setAllowBlank(false);
		combo.setEditable(false);

		if(_comboBoxTemplate != null) {
			combo.setTemplate(_comboBoxTemplate);
		}

		combo.addSelectionChangedListener(new SelectionChangedListener<FilterField>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<FilterField> se) {
				// model.setField(se.getSelectedItem().getName());
				if (se.getSelectedItem() != null) {
					simpleModel.setValueField(se.getSelectedItem().getValueField());
				} else {
					simpleModel.setValueField(null);
				}
			}
		});

		combo.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				if (store.getCount() > 0) {
					// Let rendering complete before setting the value.
					// The setting of the value triggers the selection changed
					// listener which results in the operand and right hand value
					// getting rendered.
					Scheduler.get().scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {
							combo.setValue(store.getAt(0));
						}
					});
				}
			}

		});

		return combo;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setFilterExpression(final SimpleModel simpleModel, Field<FilterField> field, SimplePanel hp) {

		ComboBox<FilterField> combo = (ComboBox<FilterField>) field;
		FilterField ff = combo.getStore().findModel("valueField", simpleModel.getValueField());

		if (ff == null) {
			combo.setData("selectedField", null);
			combo.setToolTip(ResourceHelper.getInvalidFieldMessage());
			combo.addStyleName("x-form-invalid");

		} else {
			combo.removeStyleName("x-form-invalid");
			combo.setToolTip((ToolTipConfig) null);
			combo.disableEvents(true);
			ArrayList<FilterField> selection = new ArrayList<FilterField>();
			selection.add(ff);
			combo.setData("selectedField", ff);
			combo.setSelection(selection);
			combo.enableEvents(true);
			List<Widget> w = ff.getWidgets(simpleModel);
			for (Widget widget : w) {
				hp.add(widget);
			}
			int dataIndexer = 0;
			for (int i = 0; i < w.size(); i++) {
				Widget widget = w.get(i);
				hp.add(widget);
				if (i == 0) {
					SimpleComboBox<String> comboS = (SimpleComboBox<String>) widget;
					SimpleComboValue<String> val = comboS.getStore().findModel("value", simpleModel.getOp());
					comboS.select(val);
					ArrayList<SimpleComboValue<String>> selection2 = new ArrayList<SimpleComboValue<String>>();
					selection2.add(val);
					comboS.setSelection(selection2);

				}

				if (i % 2 == 1) {
					if (widget instanceof HorizontalPanel) {
						((NumberField) ((HorizontalPanel) widget).getItem(0)).setValue((Number) simpleModel.getDataList().get(dataIndexer++));

						if (simpleModel.getDataList().size() > 1)
							((NumberField) ((HorizontalPanel) widget).getItem(2)).setValue((Number) simpleModel.getDataList().get(dataIndexer++));
					} else if (widget instanceof TextField) {
						if (!(simpleModel.getDataList().get(dataIndexer) instanceof ModelData)) {
							if (widget instanceof ComboBox) {
								// ((ComboBox) widget).clearSelections();
								// ((ComboBox) widget).clear();

								final ComboBox<?> comboBox = (ComboBox<?>) widget;
								if (comboBox.isRendered()) {
									comboBox.setRawValue((String) simpleModel.getDataList().get(dataIndexer++));
								} else {
									final int dataIndex = dataIndexer++;

									comboBox.addListener(Events.Render, new Listener<BaseEvent>() {

										@Override
										public void handleEvent(BaseEvent be) {
											comboBox.setRawValue((String) simpleModel.getDataList().get(dataIndex));
											comboBox.removeListener(Events.Render, this);
										}
									});

								}
							} else {
								((TextField) widget).setValue(simpleModel.getDataList().get(dataIndexer++));
							}
						} else {
							((TextField) widget).setValue(simpleModel.getDataList().get(dataIndexer++));
						}
					}
				}
			}
		}

	}

	@Override
	public void prepareUpdateField(String id, Field<FilterField> field) {

		ComboBox<FilterField> combo = (ComboBox<FilterField>) field;
		Boolean currentlySelectedFieldWillBeUpdated = combo.getSelection().size() > 0 && combo.getSelection().get(0).getValueField().equals(id);
		field.setData("currentlySelectedFieldWillBeUpdated", currentlySelectedFieldWillBeUpdated);

	}

}
