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
				"load_def_confirm_title", "load_def_confirm_msg", "cus_format", "to_excel", 'mfg_skl_complete_date', 'unreceived_uninspect_qty', 'uninspect_qty',
				'unreceived_uncomplete_qty', 'purchasing_produce_qty', 'due_complete_date', 'order_id', 'info_update_complete_time', 'system_date', 'skl_end_week',
				'skl_start_week', 'skl_end_date', 'skl_start_date', 'toolbar_query_title', 'all', 'material_short_type', 'optimistic_material_short',
				'conservative_material_short', 'issued_warehouse', 'planning_publish', 'publish', 'planning', 'assigned_warehouse', 'schedule_type', 'yes', 'no', 'sub_contract',
				'in_house', 'site_id', 'purchaser', 'custom_no', 'order_no', 'un_complete_qty', 'mfg_part_id', 'skl_begin_date', 'custom_supplied_materials',
				'client_supplied_materials', 'indirect_materials', 'direct_materials', 'material_type', 'unit', 'spec', 'part_name', 'warehouse_id', 'material_part_no',
				'issue_date', 'inhouse_subcontract', 'mfg_order_id', 'demand_qty', 'divisible_qty', 'project_short_qty', 'material_short_risk', 'unallocated_qty',
				'plan_receive_total_qty' ];
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
				wpp002(infos, funclangs, records[0].get('dcitagValue'), uid);
			}
		});
	});

	function wpp002(kanbanInfos, funclangs, postvalue, uid) {
		var resultGrid = null;
		var headArea = null;
		var outputArea = null;

		Ext.define('wpp002gridModel', {
			extend : 'Ext.data.Model',
			fields : [ {
				type : 'string',
				name : 'dtp'
			}, {
				type : 'string',
				name : 'C02'
			}, {
				type : 'string',
				name : 'C03'
			}, {
				type : 'string',
				name : 'MOID'
			}, {
				type : 'string',
				name : 'C07'
			}, {
				type : 'string',
				name : 'C08'
			}, {
				type : 'string',
				name : 'C09'
			}, {
				type : 'string',
				name : 'C10'
			},/*  {
																																																																																																	type : 'string',
																																																																																																	name : 'C11'
																																																																																																}, */{
				type : 'string',
				name : 'C12'
			}, {
				type : 'string',
				name : 'C13'
			}, {
				type : 'string',
				name : 'C17'
			}, {
				type : 'string',
				name : 'C21'
			}, {
				type : 'string',
				name : 'C22'
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

		var initQueryStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "f001value", "f005value" ],
			proxy : {
				type : 'ajax',
				url : '../../Funcs/WPP/WPP002.dsc',
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

		var queryStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "headdatas", "griddatas" ],
			proxy : {
				type : 'ajax',
				url : '../../Funcs/WPP/WPP002.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json'
				}
			}
		});

		var gridstore = new Ext.data.Store({
			model : 'wpp002gridModel',
			autoLoad : false,
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		resultGrid = Ext.create('DCI.Customer.ViewGridPanel', {
			languageObj : funclangs,
			gridid : 'WPP002_G01',
			postvalue : postvalue,
			uid : uid,
			griddbclick : function(comp, td, cellIndex, record, tr, rowIndex, e, eOpts) {
				if (record.get("dtp") == "0") {
					if (record.get("C21") != null && record.get("C21") != "" && parseFloat(record.get("C21")) > 0) {
						var keys = [ record.get("C02"), record.get("C03") ];
						showSubPanel02(postvalue, keys, kanbanInfos, uid, funclangs);
					}
				}
			}
		});

		var bodyColumns = [ {
			text : funclangs.material_part_no,
			dataIndex : 'C02',
			colid : 'colC02',
			width : 150
		}, {
			text : funclangs.issue_date,
			dataIndex : 'C07',
			colid : 'colC07',
			width : 100
		}, {
			text : funclangs.inhouse_subcontract,
			dataIndex : 'C17',
			colid : 'colC17',
			width : 100
		}, {
			text : funclangs.mfg_order_id,
			dataIndex : 'MOID',
			colid : 'colMOID',
			width : 150
		}, {
			text : funclangs.demand_qty,
			dataIndex : 'C08',
			colid : 'colC08',
			width : 100
		}, {
			text : funclangs.divisible_qty,
			dataIndex : 'C09',
			colid : 'colC09',
			width : 100
		}, {
			text : funclangs.project_short_qty,
			dataIndex : 'C12',
			colid : 'colC12',
			width : 100
		}, {
			text : funclangs.material_short_risk,
			dataIndex : 'C13',
			colid : 'colC13',
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
			text : funclangs.unallocated_qty,
			dataIndex : 'C10',
			colid : 'colC10',
			width : 100
		}, {
			text : funclangs.plan_receive_total_qty,
			dataIndex : 'C21',
			colid : 'colC21',
			width : 100
		}, {
			text : funclangs.warehouse_id,
			dataIndex : 'C03',
			colid : 'colC03',
			width : 80
		}, {
			text : funclangs.part_name,
			dataIndex : 'MB002',
			colid : 'colMB002',
			width : 180
		}, {
			text : funclangs.spec,
			dataIndex : 'MB003',
			colid : 'colMB003',
			width : 100
		}, {
			text : funclangs.unit,
			dataIndex : 'MB004',
			colid : 'colMB004',
			width : 100
		}, {
			text : funclangs.material_type,
			dataIndex : 'C22',
			colid : 'colC22',
			width : 100,
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
			text : funclangs.skl_begin_date,
			dataIndex : 'TB010',
			colid : 'colTB010',
			width : 100
		}, {
			text : funclangs.mfg_part_id,
			dataIndex : 'TA006',
			colid : 'colTA006',
			width : 150
		}, {
			text : funclangs.un_complete_qty,
			dataIndex : 'TA015',
			colid : 'colTA015',
			width : 100
		}, {
			text : funclangs.unit,
			dataIndex : 'TA007',
			colid : 'colTA007',
			width : 100
		}, {
			text : funclangs.order_no,
			dataIndex : 'ORDERID',
			colid : 'colORDERID',
			width : 180
		}, {
			text : funclangs.custom_no,
			dataIndex : 'TB021',
			colid : 'colTB021',
			width : 100
		}, {
			text : funclangs.purchaser,
			dataIndex : 'MV002',
			colid : 'colMV002',
			width : 100
		} ];

		resultGrid.initBody(gridstore, bodyColumns);

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
			labelWidth : 60,
			matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 10,
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
			labelWidth : 60,
			editable : false,
			multiSelect : false,
			x : 10,
			y : 50
		});

		var f003store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "Y." + funclangs.yes,
				"value" : "Y"
			}, {
				"label" : "N." + funclangs.no,
				"value" : "N"
			} ]
		});

		var f003 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.assigned_warehouse,
			store : f003store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			width : 160,
			//matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 220,
			y : 10,
			listeners : {
				change : function(combobox, newValue, oldValue, eOpts) {
					if (newValue == "Y") {
						if (typeof f005 !== 'undefined' && f005 != null) {
							f005.setDisabled(false);
						}
					} else {
						if (typeof f005 !== 'undefined' && f005 != null) {
							f005.setDisabled(true);
						}
					}
				}
			}
		});

		var f004store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "1." + funclangs.planning,
				"value" : "1"
			}, {
				"label" : "2." + funclangs.publish,
				"value" : "2"
			} ]
		});

		var f004 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.planning_publish,
			store : f004store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			width : 160,
			//matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 220,
			y : 50
		});

		var f005store = Ext.create('Ext.data.Store', {
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

		var f005 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.issued_warehouse,
			queryMode : 'local',
			store : f005store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 400,
			y : 10
		});

		var f006store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "1." + funclangs.conservative_material_short,
				"value" : "1"
			}, {
				"label" : "2." + funclangs.optimistic_material_short,
				"value" : "2"
			} ]
		});

		var f006 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.material_short_type,
			store : f006store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			editable : false,
			multiSelect : false,
			x : 400,
			y : 50,
			listeners : {
				change : function(box, newValue, oldValue, eOpts) {
					var grid = resultGrid.getGrid();
					for ( var i = 0; i < grid.columns.length; i++) {
						if (grid.columns[i].dataIndex == "C07") {
							if (newValue == "1") {
								grid.columns[i].setText("備料日");
							} else {
								grid.columns[i].setText("發料日");
							}
							grid.doLayout();
							break;
						}
					}
				}
			}
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
			labelWidth : 60,
			editable : false,
			multiSelect : false,
			x : 10,
			y : 90
		});

		///預設值
		f002.setValue("1");
		f003.setValue("N");
		f004.setValue("2");
		f006.setValue("2");
		f007.setValue("0");

		var sumbit = Ext.create('Ext.Button', {
			text : funclangs.toolbar_query_title,
			renderer : "component",
			handler : function() {
				if (headArea != null) {
					resultGrid.setLoading(true);
					queryStore.removeAll();
					gridstore.removeAll();
					Ext.getCmp("wpp02out01").setText("");
					Ext.getCmp("wpp02out02").setText("");
					Ext.getCmp("wpp02out03").setText("");
					Ext.getCmp("wpp02out04").setText("");
					Ext.getCmp("wpp02out05").setText("");
					Ext.getCmp("wpp02out06").setText("");
					queryStore.getProxy().extraParams = {
						DCITag : postvalue,
						uid : uid,
						action : 'query',
						conn_id : kanbanInfos.conn_id,
						F001 : f001.getValue(),
						F002 : f002.getValue(),
						F003 : f003.getValue(),
						F004 : f004.getValue(),
						F005 : f005.getValue(),
						F006 : f006.getValue(),
						F007 : f007.getValue()
					};

					queryStore.load(function(records) {
						//alert(records.length + "---" + records[0].get("headdatas").length);
						if (records.length == 1) {
							if (records[0].get("headdatas").length > 0) {
								Ext.getCmp("wpp02out01").setText(records[0].get("headdatas")[0]["TA006"]);
								Ext.getCmp("wpp02out02").setText(records[0].get("headdatas")[0]["TA007"]);
								Ext.getCmp("wpp02out03").setText(records[0].get("headdatas")[0]["TA008"]);
								Ext.getCmp("wpp02out04").setText(records[0].get("headdatas")[0]["TA009"]);
								Ext.getCmp("wpp02out05").setText(records[0].get("headdatas")[0]["currdate"]);
								Ext.getCmp("wpp02out06").setText(records[0].get("headdatas")[0]["TA012"]);
							}
							gridstore.loadData(records[0].get("griddatas"));
						}
						resultGrid.setLoading(false);
					});
				}
			},
			x : 573,
			y : 90
		});

		var exportBtn = Ext.create('Ext.Button', {
			text : funclangs.to_excel,
			width : 80,
			x : 493,
			y : 90,
			handler : function() {
				var me = this;
				var grid = resultGrid.getGrid();
				if (grid && grid != null) {
					var downloadform = Ext.create('Ext.form.Panel', {
						standardSubmit : true,
						url : '../../Funcs/WPP/WPP002.dsc',
						method : 'POST'
					});

					downloadform.submit({
						target : '_blank',
						params : {
							DCITag : postvalue,
							uid : uid,
							action : 'excel',
							conn_id : kanbanInfos.conn_id,
							F001 : f001.getValue(),
							F002 : f002.getValue(),
							F003 : f003.getValue(),
							F004 : f004.getValue(),
							F005 : f005.getValue(),
							F006 : f006.getValue(),
							F007 : f007.getValue(),
							cols : Ext.encode(buildColObj(grid.columns))
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
				y : 33
			}, {
				xtype : 'label',
				text : funclangs.skl_end_week + '：',
				margin : '0 0 0 10',
				x : 250,
				y : 33
			}, {
				xtype : 'label',
				text : funclangs.system_date + '：',
				margin : '0 0 0 10',
				x : 0,
				y : 90
			}, {
				xtype : 'label',
				text : funclangs.info_update_complete_time + '：',
				margin : '0 0 0 10',
				x : 0,
				y : 63
			}, {
				xtype : 'label',
				id : 'wpp02out01',
				text : '',
				margin : '0 0 0 10',
				x : 75,
				y : 5
			}, {
				xtype : 'label',
				id : 'wpp02out02',
				text : '',
				margin : '0 0 0 10',
				x : 325,
				y : 5
			}, {
				xtype : 'label',
				id : 'wpp02out03',
				text : '',
				margin : '0 0 0 10',
				x : 85,
				y : 33
			}, {
				xtype : 'label',
				id : 'wpp02out04',
				text : '',
				margin : '0 0 0 10',
				x : 335,
				y : 33
			}, {
				xtype : 'label',
				id : 'wpp02out05',
				text : '',
				margin : '0 0 0 10',
				x : 60,
				y : 90
			}, {
				xtype : 'label',
				id : 'wpp02out06',
				text : '',
				margin : '0 0 0 10',
				x : 110,
				y : 63
			} ]
		});

		headArea = Ext.create('Ext.Panel', {
			renderer : "component",
			region : 'north',
			layout : 'border',
			height : 120,
			x : 10,
			y : 10,
			//width : mainPanel.getWidth() - 20,
			minWidth : 1140,
			border : 0,
			items : [ {
				region : 'west',
				split : true,
				splitterResize : false,
				minWidth : 610,
				layout : 'absolute',
				border : 0,
				items : [ f001, f002, f003, f004, f005, f006, f007, sumbit, exportBtn ]
			}, outputArea ]
		});

		var main = Ext.create('DCI.Customer.SubPanel', {
			id : 'WPP002Main',
			renderTo : 'WPP002Page',
			border : 0,
			bodyPadding : '0 5 5 5',
			layout : 'border',
			widthChangeControls : [ headArea, resultGrid ],
			items : [ headArea, resultGrid ],
			bodyStyle : {
				background : 'lightblue',
				padding : '0px',
				margin : '0px'
			}
		});

		initQueryStore.load(function(records) {
			if (records.length == 1) {
				f001store.loadData(records[0].get("f001value"));
				f005store.loadData(records[0].get("f005value"));

				var minwidth1 = f001.getWidth() - f001.getLabelWidth();
				var minwidth5 = f005.getWidth() - f005.getLabelWidth();

				f001.listConfig = {
					minWidth : minwidth1
				};
				f005.listConfig = {
					minWidth : minwidth5
				};

				//alert(f001store.getCount());				
				if (f001store.getCount() > 0 && typeof f001 != 'undefined' && f001 != null) {
					f001.setValue(f001store.getAt(0).get("value"));
				}
				//alert(f003store.getCount());				
				if (f005store.getCount() > 0 && typeof f005 != 'undefined' && f005 != null) {
					f005.setValue(f005store.getAt(0).get("value"));
				}
			}
		});
		main.resize(Ext.get("WPP002Page").getWidth(), Ext.get("WPP002Page").getHeight());
	}

	function showSubPanel02(postvalue, keys, kanbanInfos, uid, funclangs) {
		var subGrid = null;
		var win = null;
		var subQueryStore = Ext.create('Ext.data.Store', {
			fields : [ "griddatas" ],
			autoLoad : false,
			proxy : {
				type : 'ajax',
				url : '../../Funcs/WPP/WPP002.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json'
				},
				extraParams : {
					DCITag : postvalue,
					uid : uid,
					conn_id : kanbanInfos.conn_id,
					action : 'sub',
					TF002 : keys[0],
					TF003 : keys[1]
				}
			}
		});

		var subGridStore = Ext.create('Ext.data.Store', {
			fields : [],
			autoLoad : false,
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var title = "";
		var h = 300;
		var w = 800;

		win = Ext.create('Ext.window.Window', {
			title : title,
			width : w,
			height : h,
			minWidth : 300,
			minHeight : 200,
			layout : 'fit',
			plain : true,
			modal : true
		});

		subQueryStore.load(function(records) {
			if (records.length == 1) {
				var cols = [];
				subGridStore.model.setFields([ "TD001", "TD012", "TD008", "TD009", "TD015", "TK008", "TD015-TK008", "NOTE" ]);
				cols.push({
					text : funclangs.order_id,
					dataIndex : 'TD001',
					style : 'text-align:center',
					width : 180
				});

				cols.push({
					text : funclangs.due_complete_date,
					dataIndex : 'TD012',
					style : 'text-align:center',
					width : 150
				});

				cols.push({
					text : funclangs.purchasing_produce_qty,
					dataIndex : 'TD008',
					align : 'right',
					style : 'text-align:center',
					width : 100
				});

				cols.push({
					text : funclangs.unit,
					dataIndex : 'TD009',
					style : 'text-align:center',
					width : 100
				});

				cols.push({
					text : funclangs.unreceived_uncomplete_qty,
					dataIndex : 'TD015',
					align : 'right',
					style : 'text-align:center',
					width : 100
				});
				cols.push({
					text : funclangs.uninspect_qty,
					dataIndex : 'TK008',
					align : 'right',
					style : 'text-align:center',
					width : 100
				});
				cols.push({
					text : funclangs.unreceived_uninspect_qty,
					dataIndex : 'TD015-TK008',
					align : 'right',
					style : 'text-align:center',
					width : 100
				});
				cols.push({
					text : funclangs.mfg_skl_complete_date,
					dataIndex : 'NOTE',
					style : 'text-align:center',
					width : 150
				});

				subGrid = Ext.create('Ext.grid.Panel', {
					renderer : "component",
					stripeRows : true,
					columns : cols,
					store : subGridStore,
					autoScroll : true,
					loadMask : true,
					viewConfig : {
						forceFit : false,
						autoLoad : false
					},
					enableTextSelection : true
				});

				win.add(subGrid);
				subGridStore.loadData(records[0].get("griddatas"));
			}

		});

		win.show();

	}
</script>


</head>
<body>
	<div id="WPP002Page" class="page_div"></div>
</body>
</html>