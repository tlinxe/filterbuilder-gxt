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

package hu.bekesi.zoltan.filterBuilder.client.widgets.fields;

import hu.bekesi.zoltan.filterBuilder.client.criteria.SimpleModel;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.google.gwt.user.client.ui.Widget;

public abstract class FilterField extends BaseModelData {

	private static final long serialVersionUID = -8547150803166450116L;

	FilterField field;
	
	public FilterField() {
	
	}
	
	public FilterField(String valueField, String name)
	{
		setValueField(valueField);
		setName(name);
		field = this;
	}
	
	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name", name);
	}

	public String getValueField() {
		return get("valueField");
	}

	public void setValueField(String id) {
		set("valueField", id);
	}

//public abstract List<Widget> getWidgets();
	
	public abstract List<Widget> getWidgets(SimpleModel model);
	
}
