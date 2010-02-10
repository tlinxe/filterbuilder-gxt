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

package hu.bekesi.zoltan.filterBuilder.client.widgets.fields;

import hu.bekesi.zoltan.filterBuilder.client.criteria.SimpleModel;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.user.client.ui.Widget;

public class FilterTextField<M extends ModelData> extends FilterField {

	private static final long serialVersionUID = 1350088696367002710L;

	ListStore<M> store = null;
	String comboDisplayField = null;
	String comboValueField = null;

	public FilterTextField() {

	}

	public FilterTextField(String valueField, String name) {
		super(valueField, name);
	}

	public FilterTextField(String valueField, String name, ListStore<M> store_,
			String displayField_, String valueField_) {
		super(valueField, name);
		store = store_;
		comboDisplayField = displayField_;
		comboValueField = valueField_;
	}

	@Override
	public List<Widget> getWidgets(final SimpleModel model) {

		ArrayList<Widget> widgets = new ArrayList<Widget>();

		SimpleComboBox<String> combo = new SimpleComboBox<String>();
		combo.setWidth(135);
		combo.setForceSelection(true);
		combo.setTriggerAction(TriggerAction.ALL);
		combo.add("equals");
		combo.add("not equals");
		combo.add("contains");
		combo.add("starts with");
		combo.add("ends with");
		combo.add("does not contain");
		combo.add("does not start with");
		combo.add("does not end with");
		combo.setSimpleValue("equals");

		if (model.getOp() == null)
			model.setOp("equals");
		combo
				.addSelectionChangedListener(new SelectionChangedListener<SimpleComboValue<String>>() {

					@Override
					public void selectionChanged(
							SelectionChangedEvent<SimpleComboValue<String>> se) {
						// model.setOp(FilterOperator.getOpByValue(se.getSelectedItem().getValue()));
						model.setOp(se.getSelectedItem().getValue());

					}
				});

		widgets.add(combo);

		if (store != null) {
			final ComboBox<M> comboBox = new ComboBox<M>();
			comboBox.setStore(store);
			comboBox.setDisplayField(comboDisplayField);
			comboBox.setValueField(comboValueField);
			comboBox.select(0);
			if (store.getLoader() != null) {
				comboBox.setPageSize(10);
			}
			widgets.add(comboBox);
			comboBox
					.addSelectionChangedListener(new SelectionChangedListener<M>() {

						@Override
						public void selectionChanged(SelectionChangedEvent<M> se) {
							if (model.getDatas().size() > 0)
								model.getDatas().remove(0);
							// model.getDatas().add(comboBox.getSelectedText());
							model.getDatas().add(
									comboBox.getSelection().get(0));//.get(comboValueField));

						}
					});

		} else {

			final TextField<String> tf = new TextField<String>();

			tf.addListener(Events.Change, new Listener<FieldEvent>() {

				@Override
				public void handleEvent(FieldEvent be) {
					if (model.getDatas().size() > 0)
						model.getDatas().remove(0);
					model.getDatas().add(be.getValue());

				}
			});
			widgets.add(tf);
		}
		return widgets;

	}

}
