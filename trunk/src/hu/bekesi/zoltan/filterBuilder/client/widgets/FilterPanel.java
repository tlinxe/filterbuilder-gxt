/*
 * 
 *   Copyright 2011 Zoltan Bekesi
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
 *   Author : Zoltan Bekesi<bekesizoltan@gmail.com>
 * 
 * */

package hu.bekesi.zoltan.filterBuilder.client.widgets;

import hu.bekesi.zoltan.filterBuilder.client.criteria.ComplexModel;
import hu.bekesi.zoltan.filterBuilder.client.criteria.FilterModel;
import hu.bekesi.zoltan.filterBuilder.client.criteria.SimpleModel;
import hu.bekesi.zoltan.filterBuilder.client.icons.FilterBuilderIcons;
import hu.bekesi.zoltan.filterBuilder.client.widgets.fields.FilterField;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.core.XTemplate;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class FilterPanel extends HorizontalPanel implements Filter {

	public static final FilterBuilderIcons ICONS = GWT.create(FilterBuilderIcons.class);

	// List<FilterField> fields;
	ListStore<FilterField> _store;

	FilterPanel parent;

	HorizontalPanel horizontalPanel;
	VerticalPanel verticalPanel;

	SimpleComboBox<String> combo;

	// ComplexModel model = new ComplexModel(BinaryOperator.AND);
	ComplexModel model = new ComplexModel("AND");

	XTemplate _comboBoxTemplate;

	public void forceLayout() {
		this.layout(true);
		this.horizontalPanel.layout(true);
		// this.repaint();

		if (this.parent != null) {
			parent.forceLayout();
		}
	}

	public FilterPanel(ListStore<FilterField> store, XTemplate comboBoxTemplate) {
		this(null, store, comboBoxTemplate);
	}

	public void removeFilter(Widget widget) {
		verticalPanel.remove(widget);
		model.getSubFilters().remove(((Filter) widget).getFilterModel());
	}

	FilterPanel(FilterPanel parent_, ListStore<FilterField> store, final XTemplate comboBoxTemplate) {
		_store = store;
		this.parent = parent_;
		_comboBoxTemplate = comboBoxTemplate;

		setVerticalAlign(VerticalAlignment.MIDDLE);

		horizontalPanel = new HorizontalPanel();

		if (parent != null) {
			Button minus = new Button();// "-");
			minus.setIcon(ICONS.delete());

			horizontalPanel.add(minus);
			minus.addSelectionListener(new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					parent.removeFilter(FilterPanel.this);
					FilterPanel.this.forceLayout();
					parent.getFilterModel().getSubFilters().remove(model);
				}
			});
		}

		combo = new SimpleComboBox<String>();
		combo.setWidth(55);
		combo.setForceSelection(true);
		combo.setTriggerAction(TriggerAction.ALL);
		combo.add("AND");
		combo.add("OR");
		// combo.add("NOT");
		combo.add("NAND");
		combo.add("NOR");

		combo.setSimpleValue("AND");
		// combo.add(BinaryOperator.stringValues());

		combo.addSelectionChangedListener(new SelectionChangedListener<SimpleComboValue<String>>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<SimpleComboValue<String>> se) {
				// model.setOperator(BinaryOperator.getOp(se.getSelectedItem().getValue()));
				model.setOperator(se.getSelectedItem().getValue());
			}
		});

		horizontalPanel.add(combo);

		verticalPanel = new VerticalPanel();
		SimplePanel sp = new SimplePanel(verticalPanel, FilterPanel.this, _store, _comboBoxTemplate);
		verticalPanel.add(sp);
		model.getSubFilters().add(sp.getFilterModel());

		verticalPanel.addStyleName("leftborder");

		HorizontalPanel hp = new HorizontalPanel();

		Button plus = new Button();// "+");
		plus.setIcon(ICONS.add());
		plus.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				SimplePanel sp = new SimplePanel(verticalPanel, FilterPanel.this, _store, _comboBoxTemplate);
				verticalPanel.insert(sp, verticalPanel.getItemCount() - 1);
				model.getSubFilters().add(sp.getFilterModel());
				FilterPanel.this.forceLayout();
			}
		});
		hp.add(plus);

		Button plusAdv = new Button("+()");
		hp.add(plusAdv);

		plusAdv.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				FilterPanel fp = new FilterPanel(FilterPanel.this, _store, _comboBoxTemplate);
				verticalPanel.insert(fp, verticalPanel.getItemCount() - 1);
				model.getSubFilters().add(fp.getFilterModel());
				FilterPanel.this.forceLayout();

			}
		});

		this.add(horizontalPanel);
		// TableData td = new TableData();
		// td.setPadding(2);
		this.add(verticalPanel);// ,td);
		verticalPanel.add(hp);

	}

	@Override
	public ComplexModel getFilterModel() {
		return model;
	}

	@Override
	public void setFilterExpression(FilterModel filterModel) {
		model = (ComplexModel) filterModel;

		while (verticalPanel.getItemCount() > 1)
			verticalPanel.remove(verticalPanel.getWidget(0));

		SimpleComboValue<String> val = combo.getStore().findModel("value", model.getOperator());
		combo.select(val);
		ArrayList<SimpleComboValue<String>> selection2 = new ArrayList<SimpleComboValue<String>>();
		selection2.add(val);
		combo.setSelection(selection2);

		for (FilterModel filterM : model.getSubFilters()) {
			if (filterM instanceof SimpleModel) {
				SimplePanel simplePanel = new SimplePanel(verticalPanel, FilterPanel.this, _store, _comboBoxTemplate);
				verticalPanel.insert(simplePanel, verticalPanel.getItemCount() - 1);
				simplePanel.setFilterExpression(filterM);
			} else if (filterM instanceof ComplexModel) {
				FilterPanel fp = new FilterPanel(FilterPanel.this, _store, _comboBoxTemplate);
				verticalPanel.insert(fp, verticalPanel.getItemCount() - 1);
				fp.setFilterExpression(filterM);
			}
		}
		FilterPanel.this.forceLayout();
		combo.focus();

	}

	/*
	 * @Override public void addField(FilterField field) { fields.add(field);
	 * for (int i = 0; i < verticalPanel.getItemCount() - 1; i++) { ((Filter)
	 * verticalPanel.getItem(i)).addField(field); }
	 * 
	 * }
	 */

	// @Override
	// public void updateField( String id, String newName) {
	// for (int i = 0; i < fields.size(); i++) {
	// if (fields.get(i).getValueField().compareTo(field.getValueField()) == 0)
	// {
	// fields.remove(i);
	// fields.add(i, field);
	// break;
	// }
	// }
	// for (int i = 0; i < verticalPanel.getItemCount() - 1; i++) {
	// ((Filter) verticalPanel.getItem(i)).updateField(field);
	// }
	// }

	// @Override
	// public void removeField(FilterField field) {
	// //fields.remove(field);
	// for (int i = 0; i < verticalPanel.getItemCount() - 1; i++) {
	// ((Filter) verticalPanel.getItem(i)).removeField(field);
	// }
	// }

	@Override
	public void prepareUpdateField(String id) {
		for (int i = 0; i < verticalPanel.getItemCount() - 1; i++) {
			((Filter) verticalPanel.getItem(i)).prepareUpdateField(id);
		}

	}
	//
	// @Override
	// public void updateField(String id) {
	// for (int i = 0; i < verticalPanel.getItemCount() - 1; i++) {
	// ((Filter) verticalPanel.getItem(i)).updateField(id);
	// }
	//		
	// }

}
