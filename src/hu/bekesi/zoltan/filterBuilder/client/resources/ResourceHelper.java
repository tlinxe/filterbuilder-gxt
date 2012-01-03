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

import com.google.gwt.core.client.GWT;

/**
 * Access I18N resources
 *
 */
public class ResourceHelper {

	private static FBResource RESOURCES;

	public static FBResource getResources() {
		if (RESOURCES == null) {
			RESOURCES = (FBResource) GWT.create(FBResource.class);
		}
		return RESOURCES;
	}

	public static String getInvalidFieldMessage() {
		return getResources().invalid_field();
	}

}
