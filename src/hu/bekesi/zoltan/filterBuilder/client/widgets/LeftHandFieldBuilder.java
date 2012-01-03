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
import hu.bekesi.zoltan.filterBuilder.client.widgets.fields.FilterField;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.Field;

/**
 * Interface that builds left hand side component of a comparison
 */
public interface LeftHandFieldBuilder {

	Field<FilterField> create(ListStore<FilterField> store, SimplePanel hp, SimpleModel simpleModel);

	void setFilterExpression(SimpleModel _model, Field<FilterField> field, SimplePanel hp);

	void prepareUpdateField(String id, Field<FilterField> field);

}
