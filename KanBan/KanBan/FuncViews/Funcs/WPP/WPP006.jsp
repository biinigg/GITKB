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
				"load_def_confirm_title", "load_def_confirm_msg", "cus_format", "to_excel", 'schedule_qty', 'real_release_date', 'plan_release_date', 'produced_qty', 'unit',
				'project_produce_qty', 'part_name', 'product_no', 'real_complete_date', 'plan_complete_date', 'assigned_completed', 'finished', 'producing', 'material_released',
				'non_produced', 'mfg_status', 'mfg_order_id', 'sub_contract', 'in_house', 'mfg_type', 'material_short_risk', 'completed_risk', 'start_risk', 'publish_descript',
				'urgency_level', 'shipping_skl_order_mo', 'delayed_order_resord', 'skl_shipping_rates', 'info_update_time', 'shipping_skl_publish_date', 'shipping_skl_end_date',
				'shipping_skl_start_date', 'toolbar_query_title', 'planning_publish', 'publish', 'planning', 'shipping_warehouse', 'site_id', 'remark1', 'order_publish',
				'stock_unit', 'stock_qty', 'plan_shipping_date', 'order_shipped_qty', 'order_unit', 'skl_shipping_qty', 'order_qty', 'part_spec', 'shipping_part_name',
				'shipping_part_id', 'status', 'closed', 'assigned_closed', 'non_shipping', 'shipping', 'progress_risk', 'behind_days', 'production_status', 'unknown', 'started',
				'non_produced', 'priority', 'skl_shipping_date', 'order_no', 'cus_id', 'cus_name', 'order_delay' ];

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
				wpp006(infos, funclangs, records[0].get('dcitagValue'), uid);
			}
		});
	});

	function wpp006(kanbanInfos, funclangs, postvalue, uid) {
		var resultGrid = null;
		var headArea = null;
		var outputArea = null;

		Ext.define('wpp006gridModel', {
			extend : 'Ext.data.Model',
			fields : [ {
				type : 'string',
				name : 'TN022'
			}, {
				type : 'string',
				name : 'TN023'
			}, {
				type : 'string',
				name : 'TN024'
			}, {
				type : 'string',
				name : 'TN027'
			}, {
				type : 'string',
				name : 'TN006'
			}, {
				type : 'string',
				name : 'TN007'
			}, {
				type : 'string',
				name : 'ORDER_ID'
			}, {
				type : 'string',
				name : 'TN008'
			}, {
				type : 'string',
				name : 'TN009'
			}, {
				type : 'string',
				name : 'TN010'
			}, {
				type : 'string',
				name : 'TN011'
			}, {
				type : 'string',
				name : 'TN012'
			}, {
				type : 'string',
				name : 'TN013'
			}, {
				type : 'string',
				name : 'TN014'
			}, {
				type : 'string',
				name : 'TN015'
			}, {
				type : 'string',
				name : 'TN017'
			}, {
				type : 'string',
				name : 'TN021'
			}, {
				type : 'string',
				name : 'TN019'
			}, {
				type : 'string',
				name : 'TN018'
			}, {
				type : 'string',
				name : 'TN020'
			}, {
				type : 'string',
				name : 'MC007'
			}, {
				type : 'string',
				name : 'TN016'
			}, {
				type : 'string',
				name : 'NOTE'
			}, {
				type : 'string',
				name : 'TN025'
			} ]
		});

		var initQueryStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "f001value", "f002value" ],
			proxy : {
				type : 'ajax',
				url : '../../Funcs/WPP/WPP006.dsc',
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
				url : '../../Funcs/WPP/WPP006.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json'
				}
			}
		});

		var gridstore = new Ext.data.Store({
			model : 'wpp006gridModel',
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
			gridid : 'WPP006_G01',
			postvalue : postvalue,
			uid : uid,
			griddbclick : function(comp, td, cellIndex, record, tr, rowIndex, e, eOpts) {
				var grid = this.getGrid();
				var col = grid.columns[cellIndex].dataIndex;
				if (record.get(col) != "" && record.get(col) != null) {
					if (col == "NOTE") {
						var keys = [ record.get("TN008"), record.get("TN009"), record.get("TN010") ];
						showSubPanel06(postvalue, keys, col, kanbanInfos, uid, funclangs);
					} else {
						var keys = [ record.get("TN008"), record.get("TN009"), record.get("TN010"), record.get("TN013") ];
						showSubPanel06(postvalue, keys, col, kanbanInfos, uid, funclangs);
					}
				}
			}
		});

		var bodyColumns = [ {
			text : funclangs.status,
			dataIndex : 'TN022',
			colid : 'colTN022',
			width : 80,
			align : 'center',
			renderer : function(value, metaData, record, row, col, store, gridView) {
				if (value == 'Y') {
					return funclangs.closed;
				} else if (value == 'y') {
					return funclangs.assigned_closed;
				} else if (value == 'N') {
					return funclangs.non_shipping;
				} else if (value == 'D') {
					return funclangs.shipping;
				} else {
					return value;
				}
			}
		}, {
			text : funclangs.order_delay,
			dataIndex : 'TN023',
			colid : 'colTN023',
			width : 100,
			align : 'center',
			renderer : function(value, metaData, record, row, col, store, gridView) {
				if (value == 'Y') {
					return '<img src="../../images/icons/RedLight.png" width="15px" height="15px"/>';
				} else {
					return "";
				}
			}
		}, {
			text : funclangs.behind_days,
			dataIndex : 'TN024',
			colid : 'colTN024',
			align : 'right',
			style : 'text-align:center',
			width : 100
		}, {
			text : funclangs.production_status,
			dataIndex : 'TN027',
			colid : 'colTN027',
			style : 'text-align:center',
			width : 100,
			renderer : function(value, metaData, record, row, col, store, gridView) {
				if (value == '0') {
					return funclangs.unknown;
				} else if (value == '1') {
					return funclangs.started;
				} else if (value == '2') {
					return funclangs.non_produced;
				} else {
					return value;
				}
			}
		}, {
			text : funclangs.priority,
			dataIndex : 'TN006',
			colid : 'colTN006',
			style : 'text-align:center',
			width : 100
		}, {
			text : funclangs.skl_shipping_date,
			dataIndex : 'TN007',
			colid : 'colTN007',
			style : 'text-align:center',
			width : 100
		}, {
			text : funclangs.order_no,
			dataIndex : 'ORDER_ID',
			colid : 'colORDER_ID',
			style : 'text-align:center',
			width : 180
		}, {
			text : funclangs.cus_id,
			dataIndex : 'TN011',
			colid : 'colTN011',
			style : 'text-align:center',
			width : 100
		}, {
			text : funclangs.cus_name,
			dataIndex : 'TN012',
			colid : 'colTN012',
			style : 'text-align:center',
			width : 180
		}, {
			text : funclangs.shipping_part_id,
			dataIndex : 'TN013',
			colid : 'colTN013',
			style : 'text-align:center',
			width : 150
		}, {
			text : funclangs.shipping_part_name,
			dataIndex : 'TN014',
			colid : 'colTN014',
			style : 'text-align:center',
			width : 200
		}, {
			text : funclangs.part_spec,
			dataIndex : 'TN015',
			colid : 'colTN015',
			style : 'text-align:center',
			width : 200
		}, {
			text : funclangs.order_qty,
			dataIndex : 'TN017',
			colid : 'colTN017',
			align : 'right',
			style : 'text-align:center',
			width : 100
		}, {
			text : funclangs.skl_shipping_qty,
			dataIndex : 'TN021',
			colid : 'colTN021',
			align : 'right',
			style : 'text-align:center',
			width : 100
		}, {
			text : funclangs.order_unit,
			dataIndex : 'TN019',
			colid : 'colTN019',
			style : 'text-align:center',
			width : 100
		}, {
			text : funclangs.order_shipped_qty,
			dataIndex : 'TN018',
			colid : 'colTN018',
			align : 'right',
			style : 'text-align:center',
			width : 100
		}, {
			text : funclangs.plan_shipping_date,
			dataIndex : 'TN020',
			colid : 'colTN020',
			style : 'text-align:center',
			width : 150
		}, {
			text : funclangs.stock_qty,
			dataIndex : 'MC007',
			colid : 'colMC007',
			align : 'right',
			style : 'text-align:center',
			width : 100
		}, {
			text : funclangs.stock_unit,
			dataIndex : 'TN016',
			colid : 'colTN016',
			style : 'text-align:center',
			width : 100
		}, {
			text : funclangs.order_publish,
			dataIndex : 'NOTE',
			colid : 'colNOTE',
			align : 'center',
			width : 100
		}, {
			text : funclangs.remark1,
			dataIndex : 'TN025',
			colid : 'colTN025',
			style : 'text-align:center',
			width : 200
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

		var f002 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.shipping_warehouse,
			queryMode : 'local',
			store : f002store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 240,
			y : 10
		});

		var f003store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "1." + funclangs.planning,
				"value" : "1"
			}, {
				"label" : "2." + funclangs.publish,
				"value" : "2"
			} ]
		});

		var f003 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.planning_publish,
			store : f003store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			editable : false,
			multiSelect : false,
			x : 10,
			y : 50
		});

		f003.setValue("2");

		var sumbit = Ext.create('Ext.Button', {
			text : funclangs.toolbar_query_title,
			renderer : "component",
			handler : function() {
				if (headArea != null) {
					resultGrid.setLoading(true);
					queryStore.removeAll();
					gridstore.removeAll();
					Ext.getCmp("wpp06out01").setText("");
					Ext.getCmp("wpp06out02").setText("");
					Ext.getCmp("wpp06out03").setText("");
					Ext.getCmp("wpp06out04").setText("");
					Ext.getCmp("wpp06out05").setText("");
					Ext.getCmp("wpp06out06").setText("");
					queryStore.getProxy().extraParams = {
						DCITag : postvalue,
						uid : uid,
						conn_id : kanbanInfos.conn_id,
						action : 'query',
						F001 : f001.getValue(),
						F002 : f002.getValue(),
						F003 : f003.getValue()
					};

					queryStore.load(function(records) {
						if (records.length == 1) {
							if (records[0].get("headdatas") != null && records[0].get("headdatas") != '') {
								if (records[0].get("headdatas").length > 0) {
									Ext.getCmp("wpp06out01").setText(records[0].get("headdatas")[0]["TL005"]);
									Ext.getCmp("wpp06out02").setText(records[0].get("headdatas")[0]["TL006"]);
									Ext.getCmp("wpp06out03").setText(records[0].get("headdatas")[0]["TL009"]);
									Ext.getCmp("wpp06out04").setText(records[0].get("headdatas")[0]["TL011"]);
									Ext.getCmp("wpp06out05").setText(records[0].get("headdatas")[0]["value"]);
									Ext.getCmp("wpp06out06").setText(records[0].get("headdatas")[0]["TL016"]);
								}
								gridstore.loadData(records[0].get("griddatas"));
							}
						}
						resultGrid.setLoading(false);
					});
				}
			},
			x : 415,
			y : 50
		});

		var exportBtn = Ext.create('Ext.Button', {
			text : funclangs.to_excel,
			width : 80,
			x : 335,
			y : 50,
			handler : function() {
				var grid = resultGrid.getGrid();
				if (grid && grid != null) {
					var downloadform = Ext.create('Ext.form.Panel', {
						standardSubmit : true,
						url : '../../Funcs/WPP/WPP006.dsc',
						method : 'POST'
					});

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
				text : funclangs.shipping_skl_start_date + '：',
				margin : '0 0 0 10',
				x : 0,
				y : 10
			}, {
				xtype : 'label',
				text : funclangs.shipping_skl_end_date + '：',
				margin : '0 0 0 10',
				x : 220,
				y : 10
			}, {
				xtype : 'label',
				id : 'wpp06out01',
				text : '',
				margin : '0 0 0 10',
				x : 110,
				y : 10
			}, {
				xtype : 'label',
				id : 'wpp06out02',
				text : '',
				margin : '0 0 0 10',
				x : 320,
				y : 10
			}, {
				xtype : 'label',
				text : funclangs.shipping_skl_publish_date + '：',
				margin : '0 0 0 10',
				x : 0,
				y : 50
			}, {
				xtype : 'label',
				text : funclangs.info_update_time + '：',
				margin : '0 0 0 10',
				x : 220,
				y : 50
			}, {
				xtype : 'label',
				id : 'wpp06out03',
				text : '',
				margin : '0 0 0 10',
				x : 110,
				y : 50
			}, {
				xtype : 'label',
				id : 'wpp06out04',
				text : '',
				margin : '0 0 0 10',
				x : 320,
				y : 50
			}, {
				xtype : 'label',
				text : funclangs.skl_shipping_rates + '：',
				margin : '0 0 0 10',
				x : 430,
				y : 10
			}, {
				xtype : 'label',
				text : funclangs.delayed_order_resord + '：',
				margin : '0 0 0 10',
				x : 430,
				y : 50
			}, {
				xtype : 'label',
				id : 'wpp06out05',
				text : '',
				margin : '0 0 0 10',
				x : 530,
				y : 10
			}, {
				xtype : 'label',
				id : 'wpp06out06',
				text : '',
				margin : '0 0 0 10',
				x : 530,
				y : 50
			} ]
		});

		headArea = Ext.create('Ext.Panel', {
			renderer : "component",
			region : 'north',
			layout : 'border',
			height : 90,
			x : 10,
			y : 10,
			minWidth : 1140,
			border : 0,
			items : [ {
				region : 'west',
				split : true,
				splitterResize : false,
				minWidth : 480,
				layout : 'absolute',
				border : 0,
				items : [ f001, f002, f003, sumbit, exportBtn ]
			}, outputArea ]
		});

		var main = Ext.create('DCI.Customer.SubPanel', {
			id : 'WPP006Main',
			renderTo : 'WPP006Page',
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
				f002store.loadData(records[0].get("f002value"));

				var minwidth1 = f001.getWidth() - f001.getLabelWidth();
				var minwidth2 = f002.getWidth() - f002.getLabelWidth();

				f001.listConfig = {
					minWidth : minwidth1
				};
				f002.listConfig = {
					minWidth : minwidth2
				};

				if (f001store.getCount() > 0 && typeof f001 != 'undefined' && f001 != null) {
					f001.setValue(f001store.getAt(0).get("value"));
				}

				if (f002store.getCount() > 0 && typeof f002 != 'undefined' && f002 != null) {
					f002.setValue(f002store.getAt(0).get("value"));
				}
			}
		});

		main.resize(Ext.get("WPP006Page").getWidth(), Ext.get("WPP006Page").getHeight());
	}

	function showSubPanel06(postvalue, keys, subtype, kanbanInfos, uid, funclangs) {
		var subGrid = null;
		var win = null;
		var subQueryStore = Ext.create('Ext.data.Store', {
			fields : [ "griddatas" ],
			autoLoad : false,
			proxy : {
				type : 'ajax',
				url : '../../Funcs/WPP/WPP006.dsc',
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
					SubType : subtype,
					TN008 : keys[0],
					TN009 : keys[1],
					TN010 : keys[2],
					TN013 : ''
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

		if (subtype != "NOTE") {
			if (subQueryStore.getProxy().extraParams.hasOwnProperty('TN013') && keys.length == 4) {
				subQueryStore.getProxy().extraParams['TN013'] = keys[3];
			}
		}

		var title = "";
		var h = 300;
		var w = 500;

		if (subtype == "NOTE") {
			title = funclangs.order_publish;
			w = 400;
		} else {
			title = funclangs.shipping_skl_order_mo;
			w = 800;
		}

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
				if (subtype == "NOTE") {
					subGridStore.model.setFields([ "TP006", "TP005" ]);
					cols.push({
						text : funclangs.urgency_level,
						dataIndex : 'TP006',
						align : 'center',
						width : 80,
						renderer : function(value, metaData, record, row, col, store, gridView) {
							if (value == '1') {
								return "";
							} else if (value == '2') {
								return '<img src="../../images/icons/YellowLight.png" width="15px" height="15px" />';
							} else if (value == '3') {
								return '<img src="../../images/icons/RedLight.png"  width="15px" height="15px"/>';
							} else {
								return "";
							}
						}
					});
					cols.push({
						text : funclangs.publish_descript,
						dataIndex : 'TP005',
						style : 'text-align:center',
						width : 300
					});
				} else {
					subGridStore.model.setFields([ "TO009", "TO025", "TO026", "TO027", "TO021", "MO_ID", "TO014", "TO023", "TO016", "TO010", "TO011", "TO017", "TO013", "TO018",
							"TO022", "TO015", "TO024", "TO012" ]);

					cols.push({
						text : funclangs.status,
						dataIndex : 'TO009',
						align : 'center',
						width : 100
					});

					cols.push({
						text : funclangs.start_risk,
						dataIndex : 'TO025',
						align : 'center',
						width : 80,
						renderer : function(value, metaData, record, row, col, store, gridView) {
							if (value == 'Y') {
								return '<img src="../../images/icons/RedLight.png"  width="15px" height="15px"/>';
							} else {
								return "";
							}
						}
					});

					cols.push({
						text : funclangs.completed_risk,
						dataIndex : 'TO026',
						align : 'center',
						width : 80,
						renderer : function(value, metaData, record, row, col, store, gridView) {
							if (value == 'R') {
								return '<img src="../../images/icons/RedLight.png"  width="15px" height="15px"/>';
							} else if (value == 'Y') {
								return '<img src="../../images/icons/YellowLight.png"  width="15px" height="15px"/>';
							} else {
								return "";
							}
						}
					});

					cols.push({
						text : funclangs.material_short_risk,
						dataIndex : 'TO027',
						align : 'center',
						width : 80,
						renderer : function(value, metaData, record, row, col, store, gridView) {
							if (value == 'Y') {
								return '<img src="../../images/icons/RedLight.png"  width="15px" height="15px"/>';
							} else {
								return "";
							}
						}
					});

					cols.push({
						text : funclangs.mfg_type,
						dataIndex : 'TO021',
						style : 'text-align:center',
						width : 100,
						renderer : function(value, metaData, record, row, col, store, gridView) {
							if (value == '1') {
								return funclangs.in_house;
							} else if (value == '2') {
								return funclangs.sub_contract;
							} else {
								return value;
							}
						}
					});
					cols.push({
						text : funclangs.mfg_order_id,
						dataIndex : 'MO_ID',
						style : 'text-align:center',
						width : 150
					});
					cols.push({
						text : funclangs.mfg_status,
						dataIndex : 'TO014',
						style : 'text-align:center',
						width : 100,
						renderer : function(value, metaData, record, row, col, store, gridView) {
							if (value == '1') {
								return funclangs.non_produced;
							} else if (value == '2') {
								return funclangs.material_released;
							} else if (value == '3') {
								return funclangs.producing;
							} else if (value == 'Y') {
								return funclangs.finished;
							} else if (value == 'y') {
								return funclangs.assigned_completed;
							} else {
								return value;
							}
						}
					});
					cols.push({
						text : funclangs.plan_complete_date,
						dataIndex : 'TO023',
						style : 'text-align:center',
						width : 150
					});
					cols.push({
						text : funclangs.real_complete_date,
						dataIndex : 'TO016',
						style : 'text-align:center',
						width : 150
					});
					cols.push({
						text : funclangs.product_no,
						dataIndex : 'TO010',
						style : 'text-align:center',
						width : 150
					});
					cols.push({
						text : funclangs.part_name,
						dataIndex : 'TO011',
						style : 'text-align:center',
						width : 200
					});
					cols.push({
						text : funclangs.project_produce_qty,
						align : 'right',
						style : 'text-align:center',
						dataIndex : 'TO017',
						width : 100
					});
					cols.push({
						text : funclangs.unit,
						dataIndex : 'TO013',
						style : 'text-align:center',
						width : 150
					});
					cols.push({
						text : funclangs.produced_qty,
						align : 'right',
						style : 'text-align:center',
						dataIndex : 'TO018',
						width : 100
					});
					cols.push({
						text : funclangs.plan_release_date,
						dataIndex : 'TO022',
						style : 'text-align:center',
						width : 150
					});
					cols.push({
						text : funclangs.real_release_date,
						dataIndex : 'TO015',
						style : 'text-align:center',
						width : 150
					});
					cols.push({
						text : funclangs.schedule_qty,
						align : 'right',
						style : 'text-align:center',
						dataIndex : 'TO024',
						width : 100
					});
					cols.push({
						text : funclangs.part_spec,
						dataIndex : 'TO012',
						style : 'text-align:center',
						width : 150
					});
				}

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
	<div id="WPP006Page" class="page_div"></div>
</body>
</html>