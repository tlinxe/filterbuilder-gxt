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
