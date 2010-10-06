/*
 * Copyright © 2010, Contributor
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License, version 3, as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License, version 3 for more details.
 * 
 * You should have received a copy of the GNU General Public License, version 3,
 * along with this program; if not, see http://www.gnu.org/licenses/ or write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * THE FOLLOWING DISCLAIMER APPLIES TO ALL SOFTWARE CODE AND OTHER MATERIALS CONTRIBUTED IN CONNECTION WITH THIS PROGRAM:
 * 
 * THIS SOFTWARE IS LICENSED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE AND ANY WARRANTY OF NON-INFRINGEMENT,
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * THIS SOFTWARE MAY BE REDISTRIBUTED TO OTHERS ONLY BY EFFECTIVELY USING THIS OR ANOTHER EQUIVALENT DISCLAIMER AS WELL AS ANY OTHER LICENSE TERMS 
 * THAT MAY APPLY. 
 * 
 * Author : Zoltan Bekesi<bekesizoltan@gmail.com>
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
