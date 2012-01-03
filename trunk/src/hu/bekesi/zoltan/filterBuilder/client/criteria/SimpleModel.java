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
import java.util.Collections;
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

public class SimpleModel extends BaseModelData implements FilterModel,IsSerializable, Serializable {

	private static final long serialVersionUID = 6628883511737728991L;
	private transient ChangeEventSupport changeEventSupport;

	public String getOp() {
		return get("op");
	}

	public void setOp(String op) {
		set("op", op);
	}

//	public FilterField getField() {
//		return get("field");
//	}
//
//	public void setField(FilterField field) {
//		set("field", field);
//	}

	public String getValueField() {
		return get("field");
	}

	public void setValueField(String field) {
		set("field", field);
	}

	public void addData(int idx, Object data) {
		List<Object> datas = get("datas");
		if (datas == null) {
			datas = new ArrayList<Object>();
			datas.add(data);
			set("datas", datas);
			notify("datas", null, datas);
		}
		else {
			datas.add(idx, data);
			notify("datas", null, datas);
		}
	}

	public void addData(Object data) {
		List<Object> datas = get("datas");
		if (datas == null) {
			datas = new ArrayList<Object>();
			datas.add(data);
			set("datas", datas);
			notify("datas", null, datas);
		}
		else {
			datas.add(data);
			notify("datas", null, datas);
		}
	}

	public void removeData(int idx) {
		List<Object> datas = get("datas");
		if (datas != null && idx < datas.size()) {
			Object data = datas.remove(idx);
			notify("datas", data, null);
		}
	}

	public List<Object> getDataList() {
		List<Object> datas = get("datas");
		return Collections.unmodifiableList(datas == null ?  Collections.emptyList() : datas);
	}

	public SimpleModel() {
	}

	public SimpleModel(String valueField) {
		setValueField(valueField);
	}

	public SimpleModel(String valueField, String op, Object data) {
		addData(data);
		setValueField(valueField);
		setOp(op);
	}

	public SimpleModel(String valueField, String op, List<Object> datas) {
		for (Object data : datas) {
			addData(data);
		}
		setValueField(valueField);
		setOp(op);
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
	public boolean filter(ModelData model) {
		return false;
	}

	@Override
	public boolean select(Store<ModelData> store, ModelData parent, ModelData item, String property) {
		if(getOp() ==  null) return true;
//		if (getOp().equals(FilterOperator.Contains)) {
//			return item.get(getField()).toString().contains(getDatas().get(0).toString());
//		} else if (getOp().equals(FilterOperator.DoesNotContain)) {
//			return !item.get(getField()).toString().contains(getDatas().get(0).toString());
//		} else if (getOp().equals(FilterOperator.DoesNotEndWith)) {
//			return !item.get(getField()).toString().endsWith(getDatas().get(0).toString());
//		} else if (getOp().equals(FilterOperator.DoesNotStartWith)) {
//			return !item.get(getField()).toString().startsWith(getDatas().get(0).toString());
//		} else if (getOp().equals(FilterOperator.StartsWith)) {
//			return item.get(getField()).toString().startsWith(getDatas().get(0).toString());
//		} else if (getOp().equals(FilterOperator.EndsWith)) {
//			return item.get(getField()).toString().endsWith(getDatas().get(0).toString());
//		} else if (getOp().equals(FilterOperator.Equals)) {
//			return item.get(getField()).toString().equals(getDatas().get(0).toString());
//		} else if (getOp().equals(FilterOperator.NotEquals)) {
//			return !item.get(getField()).toString().equals(getDatas().get(0).toString());
//		} else if (getOp().equals(FilterOperator.NumEquals)) {
//			return ((Number) item.get(getField())).doubleValue() == (((Number) getDatas().get(0))).doubleValue();
//		} else if (getOp().equals(FilterOperator.NumGreater)) {
//			return ((Number) item.get(getField())).doubleValue() > ((Number) getDatas().get(0)).doubleValue();
//		} else if (getOp().equals(FilterOperator.NumGreaterEqual)) {
//			return ((Number) item.get(getField())).doubleValue() >= ((Number) getDatas().get(0)).doubleValue();
//		} else if (getOp().equals(FilterOperator.NumNotEquals)) {
//			return ((Number) item.get(getField())).doubleValue() == (((Number) getDatas().get(0))).doubleValue();
//		} else if (getOp().equals(FilterOperator.NumSmaller)) {
//			return ((Number) item.get(getField())).doubleValue() < ((Number) getDatas().get(0)).doubleValue();
//		} else if (getOp().equals(FilterOperator.NumSmallerEqual)) {
//			return ((Number) item.get(getField())).doubleValue() <= ((Number) getDatas().get(0)).doubleValue();
//		} else if (getOp().equals(FilterOperator.Between)) {
//			return ((Number) item.get(getField())).doubleValue() > ((Number) getDatas().get(0)).doubleValue()
//					&& ((Number) item.get(getField())).doubleValue() < ((Number) getDatas().get(1)).doubleValue();
//		}
		List<Object> dataList = getDataList();
		Object o = dataList.size() == 0 ? null : dataList.get(0);
		String rhv = "";
		double nrhv1 = 0;
		if (o instanceof Number) {
			nrhv1 = ((Number) o).doubleValue();
		}
		else if (o != null) {
			rhv = o.toString();
		}
		o = dataList.size() < 2 ? null : dataList.get(1);
		double nrhv2 = o instanceof Number ? ((Number) o).doubleValue() : 0;
		if (getOp().compareTo("contains") == 0) {
			return item.get(getValueField()).toString().contains(rhv);
		} else if (getOp().compareTo("does not contain") == 0) {
			return !item.get(getValueField()).toString().contains(rhv);
		} else if (getOp().compareTo("does not end with") == 0) {
			return !item.get(getValueField()).toString().endsWith(rhv);
		} else if (getOp().compareTo("does not start with") == 0) {
			return !item.get(getValueField()).toString().startsWith(rhv);
		} else if (getOp().compareTo("starts with") == 0) {
			return item.get(getValueField()).toString().startsWith(rhv);
		} else if (getOp().compareTo("ends with") == 0) {
			return item.get(getValueField()).toString().endsWith(rhv);
		} else if (getOp().compareTo("equals") == 0) {
			return item.get(getValueField()).toString().equals(rhv);
		} else if (getOp().compareTo("not equals") == 0) {
			return !item.get(getValueField()).toString().equals(rhv);
		} else if (getOp().compareTo("==") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() == nrhv1;
		} else if (getOp().compareTo(">") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() > nrhv1;
		} else if (getOp().compareTo(">=") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() >= nrhv1;
		} else if (getOp().compareTo("!=") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() == nrhv1;
		} else if (getOp().compareTo("<") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() < nrhv1;
		} else if (getOp().compareTo("<=") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() <= nrhv1;
		} else if (getOp().compareTo("between") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() > nrhv1
					&& ((Number) item.get(getValueField())).doubleValue() < nrhv2;
		}

		return false;
	}

	@Override
	public void setChangeEventSupport(ChangeEventSupport changeEventSupport) {
		this.changeEventSupport = changeEventSupport;
	}

	public void addChangeListener(ChangeListener... listener) {
		if (changeEventSupport == null) {
			changeEventSupport = new ChangeEventSupport();
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

}
