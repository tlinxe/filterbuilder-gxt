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

public class SimpleModel extends BaseModelData implements FilterModel,IsSerializable, Serializable {

	private static final long serialVersionUID = 6628883511737728991L;


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
	
	public List<Object> getDatas() {
		return get("datas");
	}

	public void setDatas(List<Object> datas) {
		set("datas", datas);
	}

	public SimpleModel() {
		setDatas(new ArrayList<Object>());
	}

//	public SimpleModel(FilterField field) {
//		setDatas(new ArrayList<Object>());
//		setValueField(field);
//	}
//
//	public SimpleModel(FilterField field, String op, Object data) {
//		setDatas(new ArrayList<Object>());
//		getDatas().add(data);
//		setValueField(field);
//		setOp(op);
//	}
//
//	public SimpleModel(FilterField field, String op, List<Object> data) {
//		setDatas(data);
//		setValueField(field);
//		setOp(op);
//	}

	public SimpleModel(String valueField) {
		setDatas(new ArrayList<Object>());
		setValueField(valueField);
	}

//	public SimpleModel(String field, FilterOperator op, Object data) {
//		setDatas(new ArrayList<Object>());
//		getDatas().add(data);
//		setField(field);
//		setOp(op);
//	}
//
//	public SimpleModel(String field, FilterOperator op, List<Object> data) {
//		setDatas(data);
//		setField(field);
//		setOp(op);
//	}
	
	public SimpleModel(String valueField, String op, Object data) {
		setDatas(new ArrayList<Object>());
		getDatas().add(data);
		setValueField(valueField);
		setOp(op);
	}

	public SimpleModel(String valueField, String op, List<Object> data) {
		setDatas(data);
		setValueField(valueField);
		setOp(op);
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
		if (getOp().compareTo("contains") == 0) {
			return item.get(getValueField()).toString().contains(getDatas().get(0).toString());
		} else if (getOp().compareTo("does not contain") == 0) {
			return !item.get(getValueField()).toString().contains(getDatas().get(0).toString());
		} else if (getOp().compareTo("does not end with") == 0) {
			return !item.get(getValueField()).toString().endsWith(getDatas().get(0).toString());
		} else if (getOp().compareTo("does not start with") == 0) {
			return !item.get(getValueField()).toString().startsWith(getDatas().get(0).toString());
		} else if (getOp().compareTo("starts with") == 0) {
			return item.get(getValueField()).toString().startsWith(getDatas().get(0).toString());
		} else if (getOp().compareTo("ends with") == 0) {
			return item.get(getValueField()).toString().endsWith(getDatas().get(0).toString());
		} else if (getOp().compareTo("equals") == 0) {
			return item.get(getValueField()).toString().equals(getDatas().get(0).toString());
		} else if (getOp().compareTo("not equals") == 0) {
			return !item.get(getValueField()).toString().equals(getDatas().get(0).toString());
		} else if (getOp().compareTo("==") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() == (((Number) getDatas().get(0))).doubleValue();
		} else if (getOp().compareTo(">") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() > ((Number) getDatas().get(0)).doubleValue();
		} else if (getOp().compareTo(">=") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() >= ((Number) getDatas().get(0)).doubleValue();
		} else if (getOp().compareTo("!=") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() == (((Number) getDatas().get(0))).doubleValue();
		} else if (getOp().compareTo("<") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() < ((Number) getDatas().get(0)).doubleValue();
		} else if (getOp().compareTo("<=") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() <= ((Number) getDatas().get(0)).doubleValue();
		} else if (getOp().compareTo("between") == 0) {
			return ((Number) item.get(getValueField())).doubleValue() > ((Number) getDatas().get(0)).doubleValue()
					&& ((Number) item.get(getValueField())).doubleValue() < ((Number) getDatas().get(1)).doubleValue();
		}
		
		return false;
	}

}
