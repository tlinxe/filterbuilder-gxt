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
import com.extjs.gxt.ui.client.data.ChangeEventSource;
import com.extjs.gxt.ui.client.data.ChangeEventSupport;
import com.extjs.gxt.ui.client.data.ChangeListener;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PropertyChangeEvent;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.util.Util;
import com.google.gwt.user.client.rpc.IsSerializable;

public class ComplexModel extends BaseModelData implements FilterModel, IsSerializable, Serializable {

	private static final long serialVersionUID = 669506536405584340L;
	private transient ChangeEventSupport changeEventSupport;

	public List<FilterModel> getSubFilters() {
		return get("subFilters");
	}

	public void addSubFilter(FilterModel subFilter) {
		List<FilterModel> subFilters = getSubFilters();
		if (subFilters == null) {
			subFilters = new ArrayList<FilterModel>();
			subFilters.add(subFilter);
			set("subFilters", subFilters);
		}
		else {
			subFilters.add(subFilter);
			notify("subFilters", null, subFilter);
		}
		subFilter.setChangeEventSupport(changeEventSupport);
	}

	public void removeSubFilter(FilterModel subFilter) {
		List<FilterModel> subFilters = get("subFilters");
		if (subFilters != null) {
			if (subFilters.remove(subFilter)) {
				notify("subFilters", subFilter, null);
			}
		}
	}

	public String getOperator() {
		return get("operator");
	}

	public void setOperator(String operator) {
		set("operator", operator);
	}

	public ComplexModel() {
		setOperator("AND");
	}

	public ComplexModel(String op) {
		setOperator(op);
	}

	@Override
	public boolean filter(ModelData model) {
		return false;
	}

	/**
	 * @see com.extjs.gxt.ui.client.data.BaseModelData#set(java.lang.String, java.lang.Object)
	 */
	@Override
	public <X> X set(String property, X value) {
		X oldValue = super.set(property, value);
		notify(property, oldValue, value);
		return oldValue;
	}

	@Override
	public boolean select(Store<ModelData> store, ModelData parent,
			ModelData item, String property) {
		List<FilterModel> subFilters = get("subFilters");
		if (getOperator().compareTo("AND") == 0) {
			if (subFilters != null) {
				for (FilterModel filterModel : subFilters) {
					if (!filterModel.select(store, parent, item, property)) {
						return false;
					}
				}
			}
			return true;

		} else if (getOperator().compareTo("NAND") == 0) {
			if (subFilters != null) {
				for (FilterModel filterModel : subFilters) {
					if (!filterModel.select(store, parent, item, property)) {
						return true;
					}
				}
			}
			return false;

		} else if (getOperator().compareTo("OR") == 0) {
			if (subFilters != null) {
				for (FilterModel filterModel : subFilters) {
					if (filterModel.select(store, parent, item, property)) {
						return true;
					}
				}
			}
			return false;
		} else if (getOperator().compareTo("NOR") == 0) {
			if (subFilters != null) {
				for (FilterModel filterModel : subFilters) {
					if (filterModel.select(store, parent, item, property)) {
						return false;
					}
				}
			}
			return true;
		} else {
			if (subFilters != null && subFilters.size() > 0) {
				return !(subFilters.get(0).select(store, parent, item,
					property));
			}
			return false;
		}
	}

	public void addChangeListener(ChangeListener... listener) {
		if (changeEventSupport == null) {
			setChangeEventSupport(new ChangeEventSupport());
		}
		changeEventSupport.addChangeListener(listener);
	}

	public void removeChangeListener(ChangeListener... listener) {
		if (changeEventSupport != null) {
			changeEventSupport.removeChangeListener(listener);
		}
	}

	private void notify(String property, Object oldValue, Object newValue) {
		if (changeEventSupport != null) {
			if (!Util.equalWithNull(oldValue, newValue)) {
				PropertyChangeEvent event = new PropertyChangeEvent(ChangeEventSource.Update, null, property, oldValue, newValue);
				changeEventSupport.notify(event);
			}
		}
	}

	@Override
	public void setChangeEventSupport(ChangeEventSupport changeEventSupport) {
		this.changeEventSupport = changeEventSupport;
		List<FilterModel> subFilters = getSubFilters();
		if (subFilters != null) {
			for (FilterModel filterModel : subFilters) {
				filterModel.setChangeEventSupport(changeEventSupport);
			}
		}
	}

}
