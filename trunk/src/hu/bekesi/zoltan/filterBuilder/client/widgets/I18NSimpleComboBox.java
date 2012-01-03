/*
 *
 *   Copyright 2011 Bryn Ryans
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
 *   Author : Bryn Ryans<snayrb99@gmail.com>
 *
 * */
package hu.bekesi.zoltan.filterBuilder.client.widgets;

import java.util.List;

import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;

/**
 * Combo box that supports internationalized values. Alternative is to use combo box directly.
 */
public class I18NSimpleComboBox extends SimpleComboBox<String> {

	public I18NSimpleComboBox() {
		super();
		setDisplayField("displayValue");
	}

	public void add(List<String> values) {
		for (String t : values) {
			add(t);
		}
	}

	public void addValues(List<I18NSimpleComboValue> values) {
		for (I18NSimpleComboValue t : values) {
			add(t);
		}
	}

	public void add(String value) {
		add(value, value);
	}

	public void add(String displayValue, String value) {
		store.add(new I18NSimpleComboValue(displayValue, value));
	}

	public void add(I18NSimpleComboValue value) {
		store.add(value);
	}

	@SuppressWarnings("serial")
	public static class I18NSimpleComboValue extends SimpleComboValue<String> {

		public I18NSimpleComboValue() {
		}

		public I18NSimpleComboValue(String displayValue, String value) {
			set("displayValue", displayValue);
			setValue(value);
		}

	}

}
