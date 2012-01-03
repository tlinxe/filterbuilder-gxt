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
package hu.bekesi.zoltan.filterBuilder.client.resources;

import com.google.gwt.i18n.client.Messages;

public interface FBResource extends Messages {

	String invalid_field();

	String and();

	String or();

	String nand();

	String nor();

	String is_a();

	String not_is_a();

	String symbol_eq();
	String symbol_ltgt();
	String symbol_lt();
	String symbol_le();
	String symbol_gt();
	String symbol_ge();
	String between();

	String text_eq();
	String text_ne();
	String text_lt();
	String text_le();
	String text_gt();
	String text_ge();

	String in();
	String not_in();

	String equals();
	String not_equals();
	String contains();
	String starts_with();
	String ends_with();
	String does_not_contain();
	String does_not_start_with();
	String does_not_end_with();


}
