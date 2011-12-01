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

package hu.bekesi.zoltan.filterBuilder.server;

import hu.bekesi.zoltan.filterBuilder.client.GreetingService;
import hu.bekesi.zoltan.filterBuilder.client.criteria.ComplexModel;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) {
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}
	
	
	@Override
	public ListLoadResult<ModelData> getData(ListLoadConfig loadConfig) {

		ArrayList<ModelData> list = new ArrayList<ModelData>();

//		try {
			if (loadConfig != null  && loadConfig.get("filter") != null ) {
				ComplexModel cm = loadConfig.get("filter");

				BaseModelData m = null;

				for (int i = 0; i < 100; i++) {
					m = new BaseModelData();
					m.set("fid", "id" + i);
					m.set("val1", "v1" + i);
					m.set("val2", "v2" + i);
					m.set("val3", "v3" + i);
					m.set("val4", "v4" + i);
					m.set("val5", i);
					if (cm == null || cm.select(null, null, m, "")) {
						list.add(m);
					}
				}
			} else {
				BaseModelData m = null;

				for (int i = 0; i < 100; i++) {
					m = new BaseModelData();
					m.set("fid", "id" + i);
					m.set("val1", "v1" + i);
					m.set("val2", "v2" + i);
					m.set("val3", "v3" + i);
					m.set("val4", "v4" + i);
					m.set("val5", i);
					list.add(m);

				}
			}

//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}

		return new BaseListLoadResult<ModelData>(list);
	}


	@Override
	public PagingLoadResult<BaseModelData> getSearchData(PagingLoadConfig loadConfig) {
		
		ArrayList<BaseModelData> list = new ArrayList<BaseModelData>();
		
		
		BaseModelData m = null;
		for (int i = 0; i < 10; i++) {
			m = new BaseModelData();
			m.set("id", "id" + i);
			m.set("val", "val" + i);
			list.add(m);
		}
		PagingLoadResult<BaseModelData> result = new BasePagingLoadResult<BaseModelData>(list);
		result.setOffset(0);
		result.setTotalLength(10);
		return result;
	}



	
}
