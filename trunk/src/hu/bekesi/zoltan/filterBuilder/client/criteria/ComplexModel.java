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

package hu.bekesi.zoltan.filterBuilder.client.criteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.Store;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ComplexModel extends BaseModelData implements FilterModel, IsSerializable, Serializable {

	private static final long serialVersionUID = 669506536405584340L;

	public List<FilterModel> getSubFilters() {
		return get("subFilters");
	}

	public void setSubFilters(List<FilterModel> subFilters) {
		set("subFilters", subFilters);
	}

	public String getOperator() {
		return get("operator");
	}

	public void setOperator(String operator) {
		set("operator", operator);
	}

	public ComplexModel() {
		setOperator("AND");
		setSubFilters(new ArrayList<FilterModel>());
	}

	public ComplexModel(String op) {
		setOperator(op);
		setSubFilters(new ArrayList<FilterModel>());
	}

	@Override
	public boolean filter(ModelData model) {
		return false;
	}

	@Override
	public boolean select(Store<ModelData> store, ModelData parent,
			ModelData item, String property) {
		if (getOperator().compareTo("AND") == 0) {
			for (FilterModel filterModel : getSubFilters()) {
				if (!filterModel.select(store, parent, item, property)) {
					return false;
				}
			}
			return true;

		} else if (getOperator().compareTo("NAND") == 0) {
			for (FilterModel filterModel : getSubFilters()) {
				if (!filterModel.select(store, parent, item, property)) {
					return true;
				}
			}
			return false;

		} else if (getOperator().compareTo("OR") == 0) {
			for (FilterModel filterModel : getSubFilters()) {
				if (filterModel.select(store, parent, item, property)) {
					return true;
				}
			}
			return false;
		} else if (getOperator().compareTo("NOR") == 0) {
			for (FilterModel filterModel : getSubFilters()) {
				if (filterModel.select(store, parent, item, property)) {
					return false;
				}
			}
			return true;
		} else {
			return !(getSubFilters().get(0).select(store, parent, item,
					property));
		}
	}

}
