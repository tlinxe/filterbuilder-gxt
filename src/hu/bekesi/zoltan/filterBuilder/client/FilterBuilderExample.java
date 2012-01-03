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

package hu.bekesi.zoltan.filterBuilder.client;

import hu.bekesi.zoltan.filterBuilder.client.criteria.ComplexModel;
import hu.bekesi.zoltan.filterBuilder.client.criteria.SimpleModel;
import hu.bekesi.zoltan.filterBuilder.client.widgets.FilterBuilder;
import hu.bekesi.zoltan.filterBuilder.client.widgets.fields.FilterField;
import hu.bekesi.zoltan.filterBuilder.client.widgets.fields.FilterIsAField;
import hu.bekesi.zoltan.filterBuilder.client.widgets.fields.FilterNumericField;
import hu.bekesi.zoltan.filterBuilder.client.widgets.fields.FilterSetField;
import hu.bekesi.zoltan.filterBuilder.client.widgets.fields.FilterTextField;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.ListLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.AnchorData;
import com.extjs.gxt.ui.client.widget.layout.AnchorLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class FilterBuilderExample implements EntryPoint {
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	public void onModuleLoad() {

		createClientSideGrid();
		createServerSideGrid();
		createClientSideLoadGrid();
		createClientSideCombos();
	}

	public void createClientSideCombos() {
		Button button = new Button("Client Side Combos");
		RootPanel.get().add(button);
		button.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {

				Window w = new Window();
				w.setHeading("Client side Combos");
				w.setModal(true);
				w.setWidth(1500);
				w.setHeight(800);
				w.setLayout(new AnchorLayout());

				// ////////////////////////////////////////

				ListStore<ModelData> store = new ListStore<ModelData>();
				//
				BaseModelData mm = null;
				mm = new BaseModelData();
				mm.set("myid", "v11");
				mm.set("val", "V11");
				store.add(mm);
				mm = new BaseModelData();
				mm.set("myid", "v12");
				mm.set("val", "V12");
				store.add(mm);
				mm = new BaseModelData();
				mm.set("myid", "v13");
				mm.set("val", "V13");
				store.add(mm);
				mm = new BaseModelData();
				mm.set("myid", "v14");
				mm.set("val", "V14");
				store.add(mm);
				mm = new BaseModelData();
				mm.set("myid", "v15");
				mm.set("val", "V15");
				store.add(mm);
				mm = new BaseModelData();
				mm.set("myid", "v16");
				mm.set("val", "V16");
				store.add(mm);
				mm = new BaseModelData();
				mm.set("myid", "v17");
				mm.set("val", "V17");
				store.add(mm);
				mm = new BaseModelData();
				mm.set("myid", "v18");
				mm.set("val", "V18");
				store.add(mm);
				mm = new BaseModelData();
				mm.set("myid", "v19");
				mm.set("val", "V19");
				store.add(mm);
				mm = new BaseModelData();
				mm.set("myid", "v110");
				mm.set("val", "V110");
				store.add(mm);
				ArrayList<FilterField> fields = new ArrayList<FilterField>();

				fields.add(new FilterTextField<ModelData>("val1", "Value1", store, "val", "myid"));

				// ///////////////////////////////////////////
				final FilterBuilder fb = new FilterBuilder(fields);

				w.add(fb, new AnchorData("100% 30%"));

				ListStore<ModelData> bigStore = new ListStore<ModelData>();

				fb.bind(bigStore);

				for (int i = 0; i < 100; i++) {
					BaseModelData m = new BaseModelData();
					m.set("fid", "id" + i);
					m.set("val1", "v1" + i);
					m.set("val2", "v2" + i);
					m.set("val3", "v3" + i);
					m.set("val4", "v4" + i);
					m.set("val5", i);
					bigStore.add(m);
				}

				List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

				ColumnConfig column = new ColumnConfig();
				column.setId("fid");
				column.setHeader("ID");
				column.setWidth(200);
				configs.add(column);

				for (int i = 1; i < 6; i++) {
					column = new ColumnConfig();
					column.setId("val" + i);
					column.setHeader("Val" + i);
					column.setWidth(100);
					configs.add(column);
				}

				ColumnModel cm = new ColumnModel(configs);

				Grid<ModelData> grid = new Grid<ModelData>(bigStore, cm);
				// grid.setStyleAttribute("borderTop", "none");
				grid.setAutoExpandColumn("fid");
				grid.setBorders(true);
				grid.setStripeRows(true);
				w.add(grid, new AnchorData("100% 70%"));

				w.show();

			}
		});

	}

	public void createServerSideGrid() {
		Button button = new Button("Server Side");
		RootPanel.get().add(button);
		button.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {

				Window w = new Window();
				w.setHeading("Server side");
				w.setModal(true);
				w.setWidth(1500);
				w.setHeight(800);
				w.setLayout(new AnchorLayout());
				final FilterBuilder fb = new FilterBuilder(getFields());
				w.add(fb, new AnchorData("100% 30%"));

				RpcProxy<ListLoadResult<ModelData>> proxy = new RpcProxy<ListLoadResult<ModelData>>() {

					@Override
					protected void load(Object loadConfig, AsyncCallback<ListLoadResult<ModelData>> callback) {
						greetingService.getData((ListLoadConfig) loadConfig, callback);

					}
				};

				ListLoader<ListLoadResult<ModelData>> loader = new BaseListLoader<ListLoadResult<ModelData>>(proxy);
				ListStore<ModelData> bigStore = new ListStore<ModelData>(loader);

				loader.addLoadListener(new LoadListener() {

					@Override
					public void loaderLoadException(LoadEvent le) {
						System.out.println(le.toString());
						super.loaderLoadException(le);
					}

					@Override
					public void loaderLoad(LoadEvent le) {
						System.out.println(le.toString());
						super.loaderLoad(le);
					}

				});

				// fb.bind(bigStore);
				fb.bind(loader);
				// for (int i = 0; i < 100; i++) {
				// BaseModelData m = new BaseModelData();
				// m.set("fid", "id" + i);
				// m.set("val1", "v1" + i);
				// m.set("val2", "v2" + i);
				// m.set("val3", "v3" + i);
				// m.set("val4", "v4" + i);
				// m.set("val5", i);
				// bigStore.add(m);
				// }

				List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

				ColumnConfig column = new ColumnConfig();
				column.setId("fid");
				column.setHeader("ID");
				column.setWidth(200);
				configs.add(column);

				for (int i = 1; i < 6; i++) {
					column = new ColumnConfig();
					column.setId("val" + i);
					column.setHeader("Val" + i);
					column.setWidth(100);
					configs.add(column);
				}

				ColumnModel cm = new ColumnModel(configs);

				Grid<ModelData> grid = new Grid<ModelData>(bigStore, cm);
				grid.setAutoExpandColumn("fid");
				grid.setBorders(true);
				grid.setStripeRows(true);
				w.add(grid, new AnchorData("100% 70%"));

				w.show();
				loader.load();
			}
		});
	}

	public void createClientSideLoadGrid() {
		Button button = new Button("Client Side Loadable Grid");
		RootPanel.get().add(button);
		button.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {

				Window w = new Window();
				w.setHeading("Client Side Loadable Grid");
				w.setModal(true);
				w.setWidth(1500);
				w.setHeight(800);
				w.setLayout(new AnchorLayout());

				final ArrayList<FilterField> fields = getFields();
				final FilterBuilder fb = new FilterBuilder(fields);
				w.add(fb, new AnchorData("100% 30%"));

				ListStore<ModelData> bigStore = new ListStore<ModelData>();

				fb.bind(bigStore);

				for (int i = 0; i < 100; i++) {
					BaseModelData m = new BaseModelData();
					m.set("fid", "id" + i);
					m.set("val1", "v1" + i);
					m.set("val2", "v2" + i);
					m.set("val3", "v3" + i);
					m.set("val4", "v4" + i);
					m.set("val5", i);
					bigStore.add(m);
				}

				List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

				ColumnConfig column = new ColumnConfig();
				column.setId("fid");
				column.setHeader("ID");
				column.setWidth(200);
				configs.add(column);

				for (int i = 1; i < 6; i++) {
					column = new ColumnConfig();
					column.setId("val" + i);
					column.setHeader("Val" + i);
					column.setWidth(100);
					configs.add(column);
				}

				ColumnModel cm = new ColumnModel(configs);

				Grid<ModelData> grid = new Grid<ModelData>(bigStore, cm);
				grid.setAutoExpandColumn("fid");
				grid.setBorders(true);
				grid.setStripeRows(true);
				w.add(grid, new AnchorData("100% 70%"));

				w.getHeader().addTool(new ToolButton("x-tool-refresh", new SelectionListener<IconButtonEvent>() {

					@Override
					public void componentSelected(IconButtonEvent ce) {
						ComplexModel cm1 = new ComplexModel("OR");
						SimpleModel sm1 = new SimpleModel(fields.get(0).getValueField(), "contains", "0");
						SimpleModel sm2 = new SimpleModel(fields.get(1).getValueField(), "contains", "3");
						ComplexModel cm2 = new ComplexModel("AND");
						SimpleModel sm3 = new SimpleModel(fields.get(2).getValueField(), "contains", "5");
						SimpleModel sm4 = new SimpleModel(fields.get(3).getValueField(), "contains", "3");

						BaseModelData m = null;
						m = new BaseModelData();
						m.set("id", "id5");
						m.set("val", "val5");

						SimpleModel sm5 = new SimpleModel(fields.get(5).getValueField(), "equals", "String Value");
						//SimpleModel sm5 = new SimpleModel(fields.get(5).getValueField(), ">=", "String Value");

						m = new BaseModelData();
						m.set("id", "id8");
						m.set("val", "val8");

						SimpleModel sm6 = new SimpleModel(fields.get(6).getValueField(), "equals", m);

						m = new BaseModelData();
						m.set("id", "id2");
						m.set("val", "val2");

						SimpleModel sm7 = new SimpleModel(fields.get(7).getValueField(), "not in", m);
						SimpleModel sm8 = new SimpleModel(fields.get(8).getValueField(), "not is-a", m);

						cm1.getSubFilters().add(sm1);
						cm1.getSubFilters().add(sm2);
						cm2.getSubFilters().add(sm3);
						cm2.getSubFilters().add(sm4);
						cm2.getSubFilters().add(sm5);
						cm2.getSubFilters().add(sm6);
						cm1.getSubFilters().add(cm2);
						cm1.getSubFilters().add(sm7);
						cm1.getSubFilters().add(sm8);
						fb.setFilterExpression(cm1);
					}
				}));

				w.getHeader().addTool(new ToolButton("x-tool-gear", new SelectionListener<IconButtonEvent>() {

					@Override
					public void componentSelected(IconButtonEvent ce) {
						ComplexModel cm1 = new ComplexModel("OR");
						SimpleModel sm1 = new SimpleModel(
						// fields.get(0).getValueField(),
								"val100", "contains", "0");
						SimpleModel sm2 = new SimpleModel(fields.get(1).getValueField(), "contains", "3");
						ComplexModel cm2 = new ComplexModel("AND");
						ArrayList<Object> objList = new ArrayList<Object>();
						objList.add(new Integer(5));
						objList.add(new Integer(7));
						SimpleModel sm3 = new SimpleModel(fields.get(4).getValueField(), "between", objList);
						SimpleModel sm4 = new SimpleModel(fields.get(4).getValueField(), "contains", "3");

						BaseModelData m = null;
						m = new BaseModelData();
						m.set("id", "id5");
						m.set("val", "val5");

						SimpleModel sm5 = new SimpleModel(fields.get(5).getValueField(), "equals", m);

						m = new BaseModelData();
						m.set("id", "id8");
						m.set("val", "val8");

						SimpleModel sm6 = new SimpleModel(fields.get(6).getValueField(), "equals", m);

						cm1.getSubFilters().add(sm1);
						cm1.getSubFilters().add(sm2);
						cm2.getSubFilters().add(sm3);
						cm2.getSubFilters().add(sm4);
						cm2.getSubFilters().add(sm5);
						cm2.getSubFilters().add(sm6);
						cm1.getSubFilters().add(cm2);
						fb.setFilterExpression(cm1);
					}
				}));

				w.show();

			}
		});

	}

	public void createClientSideGrid() {
		Button button = new Button("Client Side");
		RootPanel.get().add(button);
		button.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {

				Window w = new Window();
				w.setHeading("Client side");
				w.setModal(true);
				w.setWidth(1500);
				w.setHeight(800);
				w.setLayout(new AnchorLayout());
				final FilterBuilder fb = new FilterBuilder(getFields());
				w.add(fb, new AnchorData("100% 30%"));

				ListStore<ModelData> bigStore = new ListStore<ModelData>();

				fb.bind(bigStore);

				for (int i = 0; i < 100; i++) {
					BaseModelData m = new BaseModelData();
					m.set("fid", "id" + i);
					m.set("val1", "v1" + i);
					m.set("val2", "v2" + i);
					m.set("val3", "v3" + i);
					m.set("val4", "v4" + i);
					m.set("val5", i);
					bigStore.add(m);
				}

				List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

				ColumnConfig column = new ColumnConfig();
				column.setId("fid");
				column.setHeader("ID");
				column.setWidth(200);
				configs.add(column);

				for (int i = 1; i < 6; i++) {
					column = new ColumnConfig();
					column.setId("val" + i);
					column.setHeader("Val" + i);
					column.setWidth(100);
					configs.add(column);
				}

				ColumnModel cm = new ColumnModel(configs);

				Grid<ModelData> grid = new Grid<ModelData>(bigStore, cm);
				// grid.setStyleAttribute("borderTop", "none");
				grid.setAutoExpandColumn("fid");
				grid.setBorders(true);
				grid.setStripeRows(true);
				w.add(grid, new AnchorData("100% 70%"));

				final FilterTextField<ModelData> addedField = new FilterTextField<ModelData>("val7", "val7");
				w.getHeader().addTool(new ToolButton("x-tool-plus", new SelectionListener<IconButtonEvent>() {

					@Override
					public void componentSelected(IconButtonEvent ce) {
						fb.addField(addedField);
					}
				}));

				w.getHeader().addTool(new ToolButton("x-tool-minus", new SelectionListener<IconButtonEvent>() {

					@Override
					public void componentSelected(IconButtonEvent ce) {
						fb.removeField(addedField);
					}
				}));

				w.getHeader().addTool(new ToolButton("x-tool-refresh", new SelectionListener<IconButtonEvent>() {

					@Override
					public void componentSelected(IconButtonEvent ce) {
						// addedField.setName(addedField.getName() + 1);
						// fb.updateField(addedField);
						fb.updateFieldName(addedField.getValueField(), addedField.getName() + 1);
					}
				}));

				w.show();

			}
		});

	}

	private ArrayList<FilterField> getFields() {
		ListStore<ModelData> store = new ListStore<ModelData>();
		//
		BaseModelData m = null;
		m = new BaseModelData();
		m.set("id", "id1");
		m.set("val", "val1");
		store.add(m);
		m = new BaseModelData();
		m.set("id", "id2");
		m.set("val", "val2");
		store.add(m);
		m = new BaseModelData();
		m.set("id", "id3");
		m.set("val", "val3");
		store.add(m);
		m = new BaseModelData();
		m.set("id", "id4");
		m.set("val", "val4");
		store.add(m);
		m = new BaseModelData();
		m.set("id", "id5");
		m.set("val", "val5");
		store.add(m);
		m = new BaseModelData();
		m.set("id", "id6");
		m.set("val", "val6");
		store.add(m);
		m = new BaseModelData();
		m.set("id", "id7");
		m.set("val", "val7");
		store.add(m);

		ArrayList<FilterField> fields = new ArrayList<FilterField>();

		fields.add(new FilterTextField<ModelData>("val1", "val1"));
		fields.add(new FilterTextField<ModelData>("val2", "val2"));
		fields.add(new FilterTextField<ModelData>("val3", "val3"));
		fields.add(new FilterTextField<ModelData>("val4", "val4"));
		fields.add(new FilterNumericField<ModelData>("val5", "val5"));

		// fields.add(new FilterTextField("id1", "name1"));
		fields.add(new FilterTextField<ModelData>("id2", "name2", store, "val", "id"));

		RpcProxy<PagingLoadResult<BaseModelData>> proxy = new RpcProxy<PagingLoadResult<BaseModelData>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<BaseModelData>> callback) {
				// service.getPosts((PagingLoadConfig) loadConfig, callback);
				greetingService.getSearchData((PagingLoadConfig) loadConfig, callback);
			}
		};

		// loader
		final PagingLoader<PagingLoadResult<BaseModelData>> loader = new BasePagingLoader<PagingLoadResult<BaseModelData>>(proxy);
		loader.setRemoteSort(true);

		ListStore<BaseModelData> store2 = new ListStore<BaseModelData>(loader);

		fields.add(new FilterTextField<BaseModelData>("id3", "name3", store2, "val", "id"));


		store = new ListStore<ModelData>();
		m = new BaseModelData();
		m.set("id", "id1");
		m.set("val", "val1");
		store.add(m);
		m = new BaseModelData();
		m.set("id", "id2");
		m.set("val", "val2");
		store.add(m);
		fields.add(new FilterSetField<ModelData>("setid", "set", store, "val", "id"));

		store = new ListStore<ModelData>();
		m = new BaseModelData();
		m.set("id", "id1");
		m.set("val", "val1");
		store.add(m);
		m = new BaseModelData();
		m.set("id", "id2");
		m.set("val", "val2");
		store.add(m);
		fields.add(new FilterIsAField<ModelData>("isaid", "isa", store, "val", "id"));


		return fields;
	}

}
