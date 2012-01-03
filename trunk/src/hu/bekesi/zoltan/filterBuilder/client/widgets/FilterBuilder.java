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

import hu.bekesi.zoltan.filterBuilder.client.criteria.ComplexModel;
import hu.bekesi.zoltan.filterBuilder.client.widgets.I18NSimpleComboBox.I18NSimpleComboValue;
import hu.bekesi.zoltan.filterBuilder.client.widgets.fields.FilterField;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.Loader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.Element;

public class FilterBuilder extends VerticalPanel {

	private FilterPanel filterPanel;
	private Store<ModelData> store;
	private Loader<ListLoadResult<ModelData>> loader;
	private ListStore<FilterField> filterStore;

	public FilterBuilder(List<FilterField> fields_) {
		this(fields_, true);
	}

	public FilterBuilder(List<FilterField> fields_, boolean showButtons) {
		this(fields_, showButtons, null, 2);
	}

	public FilterBuilder(List<FilterField> fields_, int layoutSpacing) {
		this(fields_, true, layoutSpacing);
	}

	public FilterBuilder(List<FilterField> fields_, boolean showButtons, int layoutSpacing) {
		this(fields_, showButtons, null, layoutSpacing);
	}

	public FilterBuilder(List<FilterField> fields_, boolean showButtons, LeftHandFieldBuilder leftHandField) {
		this(fields_, showButtons, leftHandField, 2);
	}

	public FilterBuilder(List<FilterField> fields_, boolean showButtons, LeftHandFieldBuilder leftHandField, int layoutSpacing) {
		this(fields_, showButtons, leftHandField, layoutSpacing, null);
	}

	public FilterBuilder(List<FilterField> fields_, boolean showButtons, LeftHandFieldBuilder leftHandField, int layoutSpacing, List<I18NSimpleComboValue> logicalOperands) {

		filterStore = new ListStore<FilterField>();
		filterStore.add(fields_);
		filterStore.setMonitorChanges(true);
		filterPanel = new FilterPanel(filterStore, leftHandField, layoutSpacing, logicalOperands);
		this.add(filterPanel);
		if (showButtons) {
			HorizontalPanel temp = new HorizontalPanel();
			temp.setSpacing(layoutSpacing);
			Button filterButton = new Button("Filter");
			filterButton.setWidth(150);
			filterButton
					.addSelectionListener(new SelectionListener<ButtonEvent>() {

						@Override
						public void componentSelected(ButtonEvent ce) {
							filter();
						}
					});

			Button clearButton = new Button("X");
			clearButton
					.addSelectionListener(new SelectionListener<ButtonEvent>() {

						@Override
						public void componentSelected(ButtonEvent ce) {
							clearFilter();
						}
					});

			temp.add(filterButton);
			temp.add(clearButton);
			this.add(temp);
		}

	}

	public void clearFilter() {
		if (loader != null) {
			loader.load();
		} else if (store != null) {
			store.clearFilters();
		}
		filterPanel.setFilterExpression(new ComplexModel("AND"));
	}

	public void filter() {
		if (loader != null) {
			BaseListLoadConfig config = new BaseListLoadConfig();
			config.set("filter", getFilterExpression());
			loader.load(config);
		} else if (store != null) {
			store.applyFilters("");
		}

	}

	public void bind(Store<ModelData> store_) {
		store = store_;
		store.addFilter(filterPanel.getFilterModel());
	}

	public void bind(Loader<ListLoadResult<ModelData>> loader_) {
		loader = loader_;
	}

	public void unbind(Store<ModelData> store_) {

		store_.removeFilter(filterPanel.getFilterModel());
		store = null;
	}

	public ComplexModel getFilterExpression() {
		return filterPanel.getFilterModel();
	}

	public void setFilterExpression(ComplexModel complexModel) {
		if (store != null) {
			store.clearFilters();
		}
		filterPanel.setFilterExpression(complexModel);
		if (store != null) {
			store.addFilter(complexModel);
		}
		filterPanel.forceLayout();
	}

	public void addField(FilterField field) {
		// TODO filterPanel.addField(field);
		filterStore.add(field);
	}

	public void updateFieldName(String id, String newName) {
		filterPanel.prepareUpdateField(id);
		FilterField ff = filterStore.findModel("valueField", id);
		if (ff != null) {
			ff.setName(newName);
			filterStore.update(ff);
		}
		// filterPanel.updateField(id);
	}

	public void removeField(FilterField field) {
		filterStore.remove(field);
		// TODO filterPanel.removeField(field);
	}

	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		// HorizontalPanel sets this to visible which hides the scrollbar.
		setStyleAttribute("overflow", "auto");
	}

}
