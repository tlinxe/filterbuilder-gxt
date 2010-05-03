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

package hu.bekesi.zoltan.filterBuilder.client.widgets;

import hu.bekesi.zoltan.filterBuilder.client.criteria.ComplexModel;
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

public class FilterBuilder extends VerticalPanel {

	FilterPanel filterPanel;
	Store<ModelData> store;
	Loader<ListLoadResult<ModelData>> loader;
	ListStore<FilterField> filterStore;

	public FilterBuilder(List<FilterField> fields_) {
		this(fields_, true);
	}

	public FilterBuilder(List<FilterField> fields_, boolean showButtons) {

		filterStore = new ListStore<FilterField>();
		filterStore.add(fields_);
		filterStore.setMonitorChanges(true);
		filterPanel = new FilterPanel(filterStore);
		this.add(filterPanel);
		if (showButtons) {
			HorizontalPanel temp = new HorizontalPanel();
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
		} else {
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
		store.clearFilters();
		filterPanel.setFilterExpression(complexModel);
		store.addFilter(complexModel);
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

}
