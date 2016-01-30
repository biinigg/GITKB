<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title>Insert title here</title>
<script type="text/javascript" charset="UTF-8">
	Ext.onReady(function() {
		var localKeys = [ "save_result_title", "save_success", "save_fail", "save_format", "load_def_format", "load_def_fail", "load_def_success", "load_def_result_title",
				"load_def_confirm_title", "load_def_confirm_msg", "cus_format", "to_excel", "yes", "no", 'custom_supplied_materials', 'client_supplied_materials',
				'indirect_materials', 'direct_materials', 'info_update_complete_time', 'system_date', 'skl_end_week', 'skl_start_week', 'skl_end_date', 'skl_start_date',
				'toolbar_query_title', 'purchaser', 'custom_no', 'order_no', 'skl_begin_date', 'production_unit', 'un_complete_qty', 'mfg_part_id', 'mfg_order_id',
				'unreceived_total_qty', 'material_short_qty', 'plan_receive_qty_today', 'divisible_qty', 'demand_qty', 'material_type', 'spec', 'unit', 'part_name',
				'material_part_no', 'material_short_risk', 'production_line', 'warehouse_id', 'material_short_only', 'purchaser', 'assigned_purchaser', 'material_type', 'all',
				'issued_warehouse', 'assigned_warehouse', 'site_id', 'in_house', 'sub_contract', 'schedule_type', 'production_line_outsource_id', 'conservative_material_short',
				'optimistic_material_short', 'material_short_type' ];

		var keys = localKeys.concat(globeKeys);
		var uid = '<dcitag:reqParam paramName="uid"></dcitag:reqParam>';
		this.dcistore.setMultiLangKeys(keys);
		this.dcistore.setUid(uid);
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				var infos = new Object();
				infos["func_id"] = '<dcitag:reqParam paramName="func_id"></dcitag:reqParam>';
				infos["func_name"] = '<dcitag:reqParam paramName="func_name"></dcitag:reqParam>';
				infos["conn_id"] = '<dcitag:reqParam paramName="conn_id"></dcitag:reqParam>';
				infos["relation_filter"] = '<dcitag:reqParam paramName="filter"></dcitag:reqParam>';
				infos["can_edit"] = '<dcitag:reqParam paramName="canEdit"></dcitag:reqParam>';
				var funclangs = buildMultiLangObjct(keys, records[0].get('langValues'));
				wpp004(infos, funclangs, records[0].get('dcitagValue'), uid);
			}
		});
	});

	function wpp004(kanbanInfos, funclangs, postvalue, uid) {
		var headArea = null;
		var outputArea = null;
		var bodyPanel = null;

		var initQueryStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "f001value", "f003_1value", "f003_2value", "f006value", "f009value" ],
			proxy : {
				type : 'ajax',
				url : '../../Funcs/WPP/WPP004.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json'
				},
				extraParams : {
					DCITag : postvalue,
					uid : uid,
					action : 'init',
					conn_id : kanbanInfos.conn_id
				}
			}
		});

		var tabstore = Ext.create('Ext.data.Store', {
			fields : [ {
				name : 'dates',
				type : 'string'
			} ],
			autoLoad : false,
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var queryStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "headdatas", "griddatas" ],
			proxy : {
				type : 'ajax',
				url : '../../Funcs/WPP/WPP004.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json'
				}
			}
		});

		bodyPanel = Ext.create('Ext.tab.Panel', {
			renderer : "component",
			region : 'center',
			minWidth : 1140,
			border : 0,
			deferredRender : false
		});

		var f001store = Ext.create('Ext.data.Store', {
			fields : [ {
				name : 'label',
				type : 'string'
			}, {
				name : 'value',
				type : 'string'
			} ],
			autoLoad : false,
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var f001 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			queryMode : 'local',
			fieldLabel : funclangs.site_id,
			store : f001store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 70,
			matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 5,
			y : 10
		});

		var f002store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "1." + funclangs.in_house,
				"value" : "1"
			}, {
				"label" : "2." + funclangs.sub_contract,
				"value" : "2"
			} ]
		});

		var f002 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.schedule_type,
			store : f002store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 70,
			editable : false,
			multiSelect : false,
			x : 5,
			y : 50,
			listeners : {
				change : function(combobox, newValue, oldValue, eOpts) {
					if (initQueryStore.getCount() > 0) {
						var mwidth = f003.getWidth() - f003.getLabelWidth();
						f003store.removeAll();
						if (newValue == "1") {
							f003store.loadData(initQueryStore.getAt(0).get("f003_1value"));
						} else {
							f003store.loadData(initQueryStore.getAt(0).get("f003_2value"));
						}

						f003.listConfig = {
							minWidth : mwidth
						};

						if (f003store.getCount() > 0 && typeof f003 != 'undefined' && f003 != null) {
							f003.setValue(f003store.getAt(0).get("value"));
						}
					}
				}
			}
		});

		var f003store = Ext.create('Ext.data.Store', {
			fields : [ {
				name : 'label',
				type : 'string'
			}, {
				name : 'value',
				type : 'string'
			} ],
			autoLoad : false,
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var f003 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			queryMode : 'local',
			fieldLabel : funclangs.production_line_outsource_id,
			store : f003store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 70,
			matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 5,
			y : 90
		});

		var f004store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "1." + funclangs.conservative_material_short,
				"value" : "1"
			}, {
				"label" : "2." + funclangs.optimistic_material_short,
				"value" : "2"
			} ]
		});

		var f004 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.material_short_type,
			store : f004store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 80,
			editable : false,
			multiSelect : false,
			x : 455,
			y : 10
		});

		var f005store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "Y." + funclangs.yes,
				"value" : "Y"
			}, {
				"label" : "N." + funclangs.no,
				"value" : "N"
			} ]
		});

		var f005 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.assigned_warehouse,
			store : f005store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 70,
			editable : false,
			multiSelect : false,
			x : 230,
			y : 50
		});

		var f006store = Ext.create('Ext.data.Store', {
			fields : [ {
				name : 'label',
				type : 'string'
			}, {
				name : 'value',
				type : 'string'
			} ],
			autoLoad : false,
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var f006 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.issued_warehouse,
			queryMode : 'local',
			store : f006store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 70,
			matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 230,
			y : 90
		});

		var f007store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "0." + funclangs.all,
				"value" : "0"
			}, {
				"label" : "1." + funclangs.direct_materials,
				"value" : "1"
			}, {
				"label" : "2." + funclangs.indirect_materials,
				"value" : "2"
			}, {
				"label" : "3." + funclangs.client_supplied_materials,
				"value" : "3"
			}, {
				"label" : "5." + funclangs.custom_supplied_materials,
				"value" : "5"
			} ]
		});

		var f007 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.material_type,
			store : f007store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 70,
			editable : false,
			multiSelect : false,
			x : 5,
			y : 130
		});

		var f008store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "Y." + funclangs.yes,
				"value" : "Y"
			}, {
				"label" : "N." + funclangs.no,
				"value" : "N"
			} ]
		});

		var f008 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.assigned_purchaser,
			store : f008store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 80,
			editable : false,
			multiSelect : false,
			x : 455,
			y : 50
		});

		var f009store = Ext.create('Ext.data.Store', {
			fields : [ {
				name : 'label',
				type : 'string'
			}, {
				name : 'value',
				type : 'string'
			} ],
			autoLoad : false,
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var f009 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.purchaser,
			queryMode : 'local',
			store : f009store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 80,
			matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 455,
			y : 90
		});

		var f010store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "Y." + funclangs.yes,
				"value" : "Y"
			}, {
				"label" : "N." + funclangs.no,
				"value" : "N"
			} ]
		});

		var f010 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.material_short_only,
			store : f010store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 70,
			editable : false,
			multiSelect : false,
			x : 230,
			y : 10
		});

		///預設值
		f002.setValue("1");
		f004.setValue("2");
		f005.setValue("N");
		f007.setValue("0");
		f008.setValue("N");
		f010.setValue("N");

		var gridColumns = [ {
			text : funclangs.warehouse_id,
			dataIndex : 'TG003',
			colid : 'colTG003',
			width : 200,
		}, {
			text : funclangs.production_line,
			dataIndex : 'TG017',
			colid : 'colTG017',
			width : 200
		}, {
			text : funclangs.material_short_risk,
			dataIndex : 'TG013',
			colid : 'colTG013',
			width : 80,
			align : 'center',
			renderer : function(value, metaData, record, row, col, store, gridView) {
				if (value == 'R') {
					return '<img src="../../images/icons/RedLight.png" width="15px" height="15px"/>';
				} else {
					return "";
				}
			}
		}, {
			text : funclangs.material_part_no,
			dataIndex : 'TG002',
			colid : 'colTG002',
			width : 150
		}, {
			text : funclangs.part_name,
			dataIndex : 'MB002',
			colid : 'colMB002',
			width : 200
		}, {
			text : funclangs.spec,
			dataIndex : 'MB003',
			colid : 'colMB003',
			width : 200
		}, {
			text : funclangs.spec,
			dataIndex : 'MB004',
			colid : 'colMB004',
			width : 100
		}, {
			text : funclangs.material_type,
			dataIndex : 'TG022',
			colid : 'colTG022',
			width : 150,
			renderer : function(value, metaData, record, row, col, store, gridView) {
				if (value == '1') {
					return funclangs.direct_materials;
				} else if (value == '2') {
					return funclangs.indirect_materials;
				} else if (value == '3') {
					return funclangs.client_supplied_materials;
				} else if (value == '5') {
					return funclangs.custom_supplied_materials;
				} else {
					return value;
				}
			}
		}, {
			text : funclangs.demand_qty,
			dataIndex : 'TG008',
			colid : 'colTG008',
			width : 100
		}, {
			text : funclangs.divisible_qty,
			dataIndex : 'TG009',
			colid : 'colTG009',
			width : 100
		}, {
			text : funclangs.plan_receive_qty_today,
			dataIndex : 'TG011',
			colid : 'colTG011',
			width : 100
		}, {
			text : funclangs.material_short_qty,
			dataIndex : 'TG012',
			colid : 'colTG012',
			width : 100
		}, {
			text : funclangs.unreceived_total_qty,
			dataIndex : 'TG021',
			colid : 'colTG021',
			width : 100
		}, {
			text : funclangs.mfg_order_id,
			dataIndex : 'MOID',
			colid : 'colMOID',
			width : 150
		}, {
			text : funclangs.mfg_part_id,
			dataIndex : 'TA006',
			colid : 'colTA006',
			width : 200
		}, {
			text : funclangs.un_complete_qty,
			dataIndex : 'TA015',
			colid : 'colTA015',
			width : 100
		}, {
			text : funclangs.production_unit,
			dataIndex : 'TA007',
			colid : 'colTA007',
			width : 100
		}, {
			text : funclangs.skl_begin_date,
			dataIndex : 'TB010',
			colid : 'colTB010',
			width : 100
		}, {
			text : funclangs.order_no,
			dataIndex : 'ORDERID',
			colid : 'colORDERID',
			width : 200
		}, {
			text : funclangs.custom_no,
			dataIndex : 'TB021',
			colid : 'colTB021',
			width : 150
		}, {
			text : funclangs.purchaser,
			dataIndex : 'MV002',
			colid : 'colMV002',
			width : 150
		} ];

		var sumbit = Ext.create('Ext.Button', {
			text : funclangs.toolbar_query_title,
			renderer : "component",
			handler : function() {
				if (headArea != null) {
					bodyPanel.setLoading(true);
					queryStore.removeAll();
					bodyPanel.removeAll(true);
					Ext.getCmp("wpp04out01").setText("");
					Ext.getCmp("wpp04out02").setText("");
					Ext.getCmp("wpp04out03").setText("");
					Ext.getCmp("wpp04out04").setText("");
					Ext.getCmp("wpp04out05").setText("");
					Ext.getCmp("wpp04out06").setText("");
					queryStore.getProxy().extraParams = {
						DCITag : postvalue,
						uid : uid,
						conn_id : kanbanInfos.conn_id,
						action : 'query',
						F001 : f001.getValue(),
						F002 : f002.getValue(),
						F003 : f003.getValue(),
						F004 : f004.getValue(),
						F005 : f005.getValue(),
						F006 : f006.getValue(),
						F007 : f007.getValue(),
						F008 : f008.getValue(),
						F009 : f009.getValue(),
						F010 : f010.getValue()
					};

					queryStore.load(function(records) {
						//alert(records.length + "---" + records[0].get("headdatas").length);
						if (records.length == 1) {
							if (records[0].get("headdatas").length > 0) {
								Ext.getCmp("wpp04out01").setText(records[0].get("headdatas")[0]["TA006"]);
								Ext.getCmp("wpp04out02").setText(records[0].get("headdatas")[0]["TA007"]);
								Ext.getCmp("wpp04out03").setText(records[0].get("headdatas")[0]["TA008"]);
								Ext.getCmp("wpp04out04").setText(records[0].get("headdatas")[0]["TA009"]);
								Ext.getCmp("wpp04out05").setText(records[0].get("headdatas")[0]["currdate"]);
								Ext.getCmp("wpp04out06").setText(records[0].get("headdatas")[0]["TA012"]);
							}

							tabstore.loadData(records[0].get("griddatas"));
							//gridstore.loadData(records[0].get("griddatas"));
							var gridtab = null;
							var tabtitle = '';
							for ( var i = 0; i < tabstore.getCount(); i++) {
								tabtitle = tabstore.getAt(i).get("dates");
								gridtab = Ext.create('Ext.Panel', {
									id : tabtitle,
									title : parseShowDate(tabtitle),
									closable : false,
									layout : 'fit',
									items : buildTab(records[0].get("griddatas")[i][tabtitle], gridColumns)
								});

								if (gridtab != null) {
									bodyPanel.add(gridtab);
									gridtab.show();
								}

								if (i == tabstore.getCount() - 1) {
									bodyPanel.setActiveTab(tabstore.getAt(0).get("dates"));
								}
							}
						}
						bodyPanel.setLoading(false);
					});
				}
			},
			x : 620,
			y : 130
		});

		var exportBtn = Ext.create('Ext.Button', {
			text : funclangs.to_excel,
			width : 80,
			x : 540,
			y : 130,
			handler : function() {
				//var grid = resultGrid.getGrid();
				if (gridColumns && gridColumns != null) {
					var downloadform = Ext.create('Ext.form.Panel', {
						standardSubmit : true,
						url : '../../Funcs/WPP/WPP004.dsc',
						method : 'POST'
					});

					//function buildColObj(cols) {
					var arr = [];

					for ( var i = 0; i < gridColumns.length; i++) {
						arr.push({
							hidden : false,
							width : gridColumns[i].width,
							text : gridColumns[i].text,
							dataIndex : gridColumns[i].dataIndex
						});
					}
					//}
					console.log(arr);
					downloadform.submit({
						target : '_blank',
						params : {
							DCITag : postvalue,
							uid : uid,
							conn_id : kanbanInfos.conn_id,
							action : 'excel',
							F001 : f001.getValue(),
							F002 : f002.getValue(),
							F003 : f003.getValue(),
							F004 : f004.getValue(),
							F005 : f005.getValue(),
							F006 : f006.getValue(),
							F007 : f007.getValue(),
							F008 : f008.getValue(),
							F009 : f009.getValue(),
							F010 : f010.getValue(),
							cols : Ext.encode(arr)
						}
					});
				}
			}
		});

		outputArea = Ext.create('Ext.Panel', {
			region : 'center',
			border : 0,
			layout : 'absolute',
			items : [ {
				xtype : 'label',
				text : funclangs.skl_start_date + '：',
				margin : '0 0 0 10',
				x : 0,
				y : 5
			}, {
				xtype : 'label',
				text : funclangs.skl_end_date + '：',
				margin : '0 0 0 10',
				x : 250,
				y : 5
			}, , {
				xtype : 'label',
				text : funclangs.skl_start_week + '：',
				margin : '0 0 0 10',
				x : 0,
				y : 50
			}, {
				xtype : 'label',
				text : funclangs.skl_end_week + '：',
				margin : '0 0 0 10',
				x : 250,
				y : 50
			}, {
				xtype : 'label',
				text : funclangs.system_date + '：',
				margin : '0 0 0 10',
				x : 0,
				y : 130
			}, {
				xtype : 'label',
				text : funclangs.info_update_complete_time + '：',
				margin : '0 0 0 10',
				x : 0,
				y : 90
			}, {
				xtype : 'label',
				id : 'wpp04out01',
				text : '',
				margin : '0 0 0 10',
				x : 75,
				y : 5
			}, {
				xtype : 'label',
				id : 'wpp04out02',
				text : '',
				margin : '0 0 0 10',
				x : 325,
				y : 5
			}, {
				xtype : 'label',
				id : 'wpp04out03',
				text : '',
				margin : '0 0 0 10',
				x : 85,
				y : 50
			}, {
				xtype : 'label',
				id : 'wpp04out04',
				text : '',
				margin : '0 0 0 10',
				x : 335,
				y : 50
			}, {
				xtype : 'label',
				id : 'wpp04out05',
				text : '',
				margin : '0 0 0 10',
				x : 60,
				y : 130
			}, {
				xtype : 'label',
				id : 'wpp04out06',
				text : '',
				margin : '0 0 0 10',
				x : 110,
				y : 90
			} ]
		});

		headArea = Ext.create('Ext.Panel', {
			renderer : "component",
			layout : 'border',
			region : 'north',
			height : 160,
			loadMask : true,
			x : 10,
			y : 10,
			//width : mainPanel.getWidth() - 20,
			minWidth : 1140,
			border : 0,
			items : [ {
				region : 'west',
				split : true,
				splitterResize : false,
				minWidth : 680,
				layout : 'absolute',
				border : 0,
				items : [ f001, f002, f003, f004, f005, f006, f007, f008, f009, f010, sumbit, exportBtn ]
			}, outputArea ]
		});
		headArea.setLoading(true);

		var main = Ext.create('DCI.Customer.SubPanel', {
			id : 'WPP004Main',
			renderTo : 'WPP004Page',
			border : 0,
			bodyPadding : '0 5 5 5',
			layout : 'border',
			widthChangeControls : [ headArea, bodyPanel ],
			items : [ headArea, bodyPanel ],
			bodyStyle : {
				background : 'lightblue',
				padding : '0px',
				margin : '0px'
			}
		});

		initQueryStore.load(function(records) {
			if (records.length == 1) {
				f001store.loadData(records[0].get("f001value"));
				f003store.loadData(records[0].get("f003_1value"));
				f006store.loadData(records[0].get("f006value"));
				f009store.loadData(records[0].get("f009value"));

				var minwidth1 = f001.getWidth() - f001.getLabelWidth();
				var minwidth3 = f003.getWidth() - f003.getLabelWidth();
				var minwidth6 = f006.getWidth() - f006.getLabelWidth();
				var minwidth9 = f009.getWidth() - f009.getLabelWidth();

				f001.listConfig = {
					minWidth : minwidth1
				};
				f003.listConfig = {
					minWidth : minwidth3
				};
				f006.listConfig = {
					minWidth : minwidth6
				};
				f009.listConfig = {
					minWidth : minwidth9
				};

				if (f001store.getCount() > 0 && typeof f001 != 'undefined' && f001 != null) {
					f001.setValue(f001store.getAt(0).get("value"));
				}

				if (f003store.getCount() > 0 && typeof f003 != 'undefined' && f003 != null) {
					f003.setValue(f003store.getAt(0).get("value"));
				}

				if (f006store.getCount() > 0 && typeof f006 != 'undefined' && f006 != null) {
					f006.setValue(f006store.getAt(0).get("value"));
				}

				if (f009store.getCount() > 0 && typeof f009 != 'undefined' && f009 != null) {
					f009.setValue(f009store.getAt(0).get("value"));
				}
			}
			headArea.setLoading(false);
		});

		main.resize(Ext.get("WPP004Page").getWidth(), Ext.get("WPP004Page").getHeight());
	}

	function buildTab(gridDatas, columns) {
		Ext.define('wpp004gridModel', {
			extend : 'Ext.data.Model',
			fields : [ {
				type : 'string',
				name : 'TG002'
			}, {
				type : 'string',
				name : 'TG003'
			}, {
				type : 'string',
				name : 'MOID'
			}, {
				type : 'string',
				name : 'TG007'
			}, {
				type : 'string',
				name : 'TG008'
			}, {
				type : 'string',
				name : 'TG009'
			}, {
				type : 'string',
				name : 'TG011'
			}, {
				type : 'string',
				name : 'TG012'
			}, {
				type : 'string',
				name : 'TG013'
			}, {
				type : 'string',
				name : 'TG017'
			}, {
				type : 'string',
				name : 'TG021'
			}, {
				type : 'string',
				name : 'TG022'
			}, {
				type : 'string',
				name : 'TA006'
			}, {
				type : 'string',
				name : 'TA007'
			}, {
				type : 'string',
				name : 'TA015'
			}, {
				type : 'string',
				name : 'MB002'
			}, {
				type : 'string',
				name : 'MB003'
			}, {
				type : 'string',
				name : 'MB004'
			}, {
				type : 'string',
				name : 'MV002'
			}, {
				type : 'string',
				name : 'TB010'
			}, {
				type : 'string',
				name : 'ORDERID'
			}, {
				type : 'string',
				name : 'TB021'
			} ]
		});

		var gridstore = new Ext.data.Store({
			model : 'wpp004gridModel',
			autoLoad : false,
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var resultGrid = Ext.create('Ext.grid.Panel', {
			renderer : "component",
			stripeRows : true,
			store : gridstore,
			autoScroll : true,
			loadMask : true,
			viewConfig : {
				forceFit : false,
				autoLoad : false
			},
			enableTextSelection : true,
			columns : columns
		});

		gridstore.loadData(gridDatas);

		return resultGrid;
	}
</script>
</head>
<body>
	<div id="WPP004Page" class="page_div"></div>
</body>
</html>