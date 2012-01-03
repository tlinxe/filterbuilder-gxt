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

import hu.bekesi.zoltan.filterBuilder.client.criteria.FilterModel;
import hu.bekesi.zoltan.filterBuilder.client.criteria.SimpleModel;
import hu.bekesi.zoltan.filterBuilder.client.icons.FilterBuilderIcons;
import hu.bekesi.zoltan.filterBuilder.client.widgets.fields.FilterField;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.google.gwt.core.client.GWT;

public class SimplePanel extends HorizontalPanel implements Filter {

	public static final FilterBuilderIcons ICONS = GWT.create(FilterBuilderIcons.class);

	private LeftHandFieldBuilder leftHandField;
	private Field<FilterField> field;
	private SimpleModel _model = null;
	private int layoutSpacing;

	public SimplePanel(final VerticalPanel verticalPanel, final FilterPanel panel, ListStore<FilterField> store, LeftHandFieldBuilder leftHandField) {
		this(verticalPanel, panel, store, leftHandField, null);
	}

	public SimplePanel(final VerticalPanel verticalPanel, final FilterPanel panel, ListStore<FilterField> store, LeftHandFieldBuilder leftHandField, SimpleModel model) {

		layoutSpacing = panel.layoutSpacing;
		setSpacing(layoutSpacing);
		this.leftHandField = leftHandField;

		final SimplePanel hp = this;
		_model = model == null ? new SimpleModel(store.getAt(0).getValueField()) : model;
		Button minus = new Button();// "-");
		minus.setIcon(ICONS.delete());
		minus.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				verticalPanel.remove(hp);
				panel.forceLayout();
				panel.getFilterModel().removeSubFilter(_model);
			}

		});
		hp.add(minus);
		field = leftHandField.create(store, hp, _model);
		hp.add(field);

	}

	@Override
	public FilterModel getFilterModel() {
		return _model;
	}

	@Override
	public void setFilterExpression(FilterModel filterModel) {
		_model = (SimpleModel) filterModel;
		leftHandField.setFilterExpression(_model, field, this);
	}

	@Override
	public void prepareUpdateField(String id) {
		leftHandField.prepareUpdateField(id, field);
	}

}
