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

package hu.bekesi.zoltan.filterBuilder.client.widgets;

import com.extjs.gxt.ui.client.widget.form.ComboBox;

public class ComboBoxHelper {

	public static Object getComboBoxRealValue(ComboBox<?> comboBox) {
		if (comboBox.getValue() != null && comboBox.getRawValue() != null && comboBox.getRawValue().equals(comboBox.getValue().get(comboBox.getDisplayField()))) {
			return comboBox.getValue();
		} else {
			return comboBox.getRawValue();
		}

	}
}
