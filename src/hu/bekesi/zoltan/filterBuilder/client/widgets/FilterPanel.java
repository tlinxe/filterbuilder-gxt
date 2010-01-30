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
import hu.bekesi.zoltan.filterBuilder.client.criteria.FilterModel;
import hu.bekesi.zoltan.filterBuilder.client.criteria.SimpleModel;
import hu.bekesi.zoltan.filterBuilder.client.icons.FilterBuilderIcons;
import hu.bekesi.zoltan.filterBuilder.client.widgets.fields.FilterField;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;

public class FilterPanel extends HorizontalPanel implements Filter {

	public static final FilterBuilderIcons ICONS = GWT.create(FilterBuilderIcons.class);
	
	List<FilterField> fields;
	FilterPanel panel;
	FilterPanel parent;

	HorizontalPanel horizontalPanel;
	VerticalPanel verticalPanel;

	SimpleComboBox<String> combo;

	//ComplexModel model = new ComplexModel(BinaryOperator.AND);
	ComplexModel model = new ComplexModel("AND");
	
	
	public void forceLayout() {
		this.layout(true);
		this.repaint();

		if (this.parent != null) {
			parent.forceLayout();
		}
	}

	public FilterPanel(List<FilterField> fields_) {
		this(null, fields_);
	}

	public void removeFilter(Widget widget) {
		verticalPanel.remove(widget);
		model.getSubFilters().remove(((Filter) widget).getFilterModel());
	}

	FilterPanel(FilterPanel parent_, List<FilterField> fields_) {
		fields = fields_;
		this.parent = parent_;
		this.panel = this;

		setVerticalAlign(VerticalAlignment.MIDDLE);

		horizontalPanel = new HorizontalPanel();

		if (parent != null) {
			Button minus = new Button();//"-");
			minus.setIcon(ICONS.delete());

			horizontalPanel.add(minus);
			minus.addSelectionListener(new SelectionListener<ButtonEvent>() {
				@Override
				public void componentSelected(ButtonEvent ce) {
					parent.removeFilter(panel);
					panel.forceLayout();
				}
			});
		}

		combo = new SimpleComboBox<String>();
		combo.setWidth(55);
		combo.setForceSelection(true);
		combo.setTriggerAction(TriggerAction.ALL);
		combo.add("AND");
		combo.add("OR");
		combo.add("NOT");
		combo.setSimpleValue("AND");
		//combo.add(BinaryOperator.stringValues());

		combo.addSelectionChangedListener(new SelectionChangedListener<SimpleComboValue<String>>() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent<SimpleComboValue<String>> se) {
				//model.setOperator(BinaryOperator.getOp(se.getSelectedItem().getValue()));
				model.setOperator(se.getSelectedItem().getValue());
			}
		});
		

		horizontalPanel.add(combo);

		verticalPanel = new VerticalPanel();
		SimplePanel sp = new SimplePanel(verticalPanel, panel, fields);
		verticalPanel.add(sp);
		model.getSubFilters().add(sp.getFilterModel());
		
		
		verticalPanel.addStyleName("leftborder");

		HorizontalPanel hp = new HorizontalPanel();

		Button plus = new Button();//"+");
		plus.setIcon(ICONS.add());
		plus.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				SimplePanel sp = new SimplePanel(verticalPanel, panel, fields);
				verticalPanel.insert(sp, verticalPanel.getItemCount() - 1);
				model.getSubFilters().add(sp.getFilterModel());
				panel.forceLayout();
			}
		});
		hp.add(plus);

		Button plusAdv = new Button("+()");
		hp.add(plusAdv);

		plusAdv.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				FilterPanel fp = new FilterPanel(panel, fields);
				verticalPanel.insert(fp, verticalPanel.getItemCount() - 1);
				model.getSubFilters().add(fp.getFilterModel());
				panel.forceLayout();

			}
		});

//		if (parent != null) {
			this.add(horizontalPanel);
//		} else {
//			VerticalPanel temp = new VerticalPanel();
//			temp.add(horizontalPanel);
//			
//			HorizontalPanel temp2 = new HorizontalPanel();
//			Button filterButton = new Button("Filter");
//			Button clearButton = new Button("X");
//			temp2.add(filterButton);
//			temp2.add(clearButton);
//			
//			temp.add(temp2);
//			this.add(temp);
//		}

		

		this.add(verticalPanel);
		verticalPanel.add(hp);

	}

	@Override
	public ComplexModel getFilterModel() {
//		ComplexModel model = new ComplexModel(BinaryOperator.valueOf(combo.getValue().getValue()));
//
//		for (int i = 0; i < verticalPanel.getItemCount() - 1; i++) {
//			model.getSubFilters().add(((Filter) verticalPanel.getItem(i)).getFilterModel());
//		}
		return model;
	}

	@Override
	public void setFilterExpression(FilterModel filterModel)
	{
		model = (ComplexModel)filterModel;
		
		while(verticalPanel.getItemCount() > 1)verticalPanel.remove(verticalPanel.getWidget(0));
		
	
		SimpleComboValue<String> val = combo.getStore().findModel("value", model.getOperator());
		combo.select(val);
		ArrayList<SimpleComboValue<String>> selection2 = new ArrayList<SimpleComboValue<String>>();
	    selection2.add(val);
	    combo.setSelection(selection2);
		
		
		for (FilterModel filterM : model.getSubFilters()) {
			if(filterM instanceof SimpleModel)
			{
				SimplePanel simplePanel = new SimplePanel(verticalPanel, panel, fields);
				verticalPanel.insert(simplePanel, verticalPanel.getItemCount() - 1);
				simplePanel.setFilterExpression(filterM);
			}else if(filterM instanceof ComplexModel)
			{
				FilterPanel fp = new FilterPanel(panel, fields);
				verticalPanel.insert(fp, verticalPanel.getItemCount() - 1);
				fp.setFilterExpression(filterM);
			}
		}
		panel.forceLayout();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
}
