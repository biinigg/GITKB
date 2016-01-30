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
				"load_def_confirm_title", "load_def_confirm_msg", "cus_format", "to_excel", 'custom_supplied_materials', 'client_supplied_materials', 'indirect_materials',
				'direct_materials', 'info_update_complete_time', 'system_date', 'skl_end_week', 'skl_start_week', 'skl_end_date', 'skl_start_date', 'toolbar_query_title',
				'material_short_only', 'material_ready_date', 'material_short_type', 'optimistic_material_short', 'conservative_material_short', 'all', 'issued_warehouse',
				'assigned_warehouse', 'no', 'yes', 'production_line_outsource_id', 'schedule_type', 'sub_contract', 'in_house', 'planning_publish', 'publish', 'planning',
				'site_id', 'purchaser', 'custom_no', 'order_no', 'unreceived_total_qty', 'est_material_short_qty', 'demand_qty', 'warehouse_id', 'issue_date', 'material_type',
				'unit', 'spec', 'part_name', 'material_part_no', 'material_short_risk', 'production_unit', 'un_complete_qty', 'skl_begin_date', 'mfg_part_name', 'mfg_part_id',
				'mfg_order_id' ];

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
				wpp003(infos, funclangs, records[0].get('dcitagValue'), uid);
			}
		});
	});

	function wpp003(kanbanInfos, funclangs, postvalue, uid) {
		var resultGrid = null;
		var headArea = null;
		var outputArea = null;

		Ext.define('wpp003gridModel', {
			extend : 'Ext.data.Model',
			fields : [ {
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
				name : 'C12'
			}, {
				type : 'string',
				name : 'C13'
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
			}, {
				type : 'string',
				name : 'PART01'
			} ]
		});

		var initQueryStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "f001value", "f004_1value", "f004_2value", "f006value" ],
			proxy : {
				type : 'ajax',
				url : '../../Funcs/WPP/WPP003.dsc',
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
				url : '../../Funcs/WPP/WPP003.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json'
				}
			}
		});

		var gridstore = new Ext.data.Store({
			model : 'wpp003gridModel',
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
			gridid : 'WPP003_G01',
			postvalue : postvalue,
			uid : uid
		});

		var bodyColumns = [ {
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
			text : funclangs.mfg_part_name,
			dataIndex : 'PART01',
			colid : 'colPART01',
			width : 200
		}, {
			text : funclangs.skl_begin_date,
			dataIndex : 'TB010',
			colid : 'colTB010',
			width : 100
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
			text : funclangs.material_part_no,
			dataIndex : 'C02',
			colid : 'colC02',
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
			text : funclangs.unit,
			dataIndex : 'MB004',
			colid : 'colMB004',
			width : 100
		}, {
			text : funclangs.material_type,
			dataIndex : 'C22',
			colid : 'colC22',
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
			text : funclangs.issue_date,
			dataIndex : 'C07',
			colid : 'colC07',
			width : 100
		}, {
			text : funclangs.warehouse_id,
			dataIndex : 'C03',
			colid : 'colC03',
			width : 200
		}, {
			text : funclangs.demand_qty,
			dataIndex : 'C08',
			colid : 'colC08',
			width : 100
		}, {
			text : funclangs.est_material_short_qty,
			dataIndex : 'C12',
			colid : 'colC12',
			width : 100
		}, {
			text : funclangs.unreceived_total_qty,
			dataIndex : 'C21',
			colid : 'colC21',
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
				"label" : "1." + funclangs.planning,
				"value" : "1"
			}, {
				"label" : "2." + funclangs.publish,
				"value" : "2"
			} ]
		});

		var f002 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.planning_publish,
			store : f002store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			editable : false,
			multiSelect : false,
			x : 10,
			y : 45
		});

		var f003store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "1." + funclangs.in_house,
				"value" : "1"
			}, {
				"label" : "2." + funclangs.sub_contract,
				"value" : "2"
			} ]
		});

		var f003 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.schedule_type,
			store : f003store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 70,
			editable : false,
			multiSelect : false,
			x : 230,
			y : 10,
			listeners : {
				change : function(combobox, newValue, oldValue, eOpts) {
					if (initQueryStore.getCount() > 0) {
						var mwidth = f004.getWidth() - f004.getLabelWidth();
						f004store.removeAll();
						if (newValue == "1") {
							f004store.loadData(initQueryStore.getAt(0).get("f004_1value"));
						} else {
							f004store.loadData(initQueryStore.getAt(0).get("f004_2value"));
						}

						f004.listConfig = {
							minWidth : mwidth
						};

						if (f004store.getCount() > 0 && typeof f004 != 'undefined' && f004 != null) {
							f004.setValue(f004store.getAt(0).get("value"));
						}
					}
				}
			}
		});

		var f004store = Ext.create('Ext.data.Store', {
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

		var f004 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			queryMode : 'local',
			fieldLabel : funclangs.production_line_outsource_id,
			store : f004store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 70,
			matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 230,
			y : 45
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
			//matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 460,
			y : 10
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
			x : 460,
			y : 45
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
			y : 80
		});

		var f008store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "1." + funclangs.conservative_material_short,
				"value" : "1"
			}, {
				"label" : "2." + funclangs.optimistic_material_short,
				"value" : "2"
			} ]
		});

		var f008 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.material_short_type,
			store : f008store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 70,
			editable : false,
			multiSelect : false,
			x : 460,
			y : 80,
			listeners : {
				change : function(box, newValue, oldValue, eOpts) {
					var grid = resultGrid.getGrid();
					for ( var i = 0; i < grid.columns.length; i++) {
						if (grid.columns[i].dataIndex == "C07") {
							if (newValue == "1") {
								grid.columns[i].setText(funclangs.material_ready_date);
							} else {
								grid.columns[i].setText(funclangs.issue_date);
							}
							grid.doLayout();
							break;
						}
					}
				}
			}
		});

		var f009store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "Y." + funclangs.yes,
				"value" : "Y"
			}, {
				"label" : "N." + funclangs.no,
				"value" : "N"
			} ]
		});

		var f009 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.material_short_only,
			store : f009store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 70,
			editable : false,
			multiSelect : false,
			x : 230,
			y : 80
		});

		///預設值
		f002.setValue("2");
		f003.setValue("1");
		f005.setValue("N");
		f007.setValue("0");
		f008.setValue("2");
		f009.setValue("N");

		var sumbit = Ext.create('Ext.Button', {
			text : funclangs.toolbar_query_title,
			renderer : "component",
			handler : function() {
				if (headArea != null) {
					resultGrid.setLoading(true);
					queryStore.removeAll();
					gridstore.removeAll();
					Ext.getCmp("wpp03out01").setText("");
					Ext.getCmp("wpp03out02").setText("");
					Ext.getCmp("wpp03out03").setText("");
					Ext.getCmp("wpp03out04").setText("");
					Ext.getCmp("wpp03out05").setText("");
					Ext.getCmp("wpp03out06").setText("");
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
						F009 : f009.getValue()
					};

					queryStore.load(function(records) {
						//alert(records.length + "---" + records[0].get("headdatas").length);
						if (records.length == 1) {
							if (records[0].get("headdatas").length > 0) {
								Ext.getCmp("wpp03out01").setText(records[0].get("headdatas")[0]["TA006"]);
								Ext.getCmp("wpp03out02").setText(records[0].get("headdatas")[0]["TA007"]);
								Ext.getCmp("wpp03out03").setText(records[0].get("headdatas")[0]["TA008"]);
								Ext.getCmp("wpp03out04").setText(records[0].get("headdatas")[0]["TA009"]);
								Ext.getCmp("wpp03out05").setText(records[0].get("headdatas")[0]["currdate"]);
								Ext.getCmp("wpp03out06").setText(records[0].get("headdatas")[0]["TA012"]);
							}
							gridstore.loadData(records[0].get("griddatas"));
						}
						resultGrid.setLoading(false);
					});
				}
			},
			x : 645,
			y : 110
		});

		var exportBtn = Ext.create('Ext.Button', {
			text : funclangs.to_excel,
			width : 80,
			x : 565,
			y : 110,
			handler : function() {
				var grid = resultGrid.getGrid();
				if (grid && grid != null) {
					var downloadform = Ext.create('Ext.form.Panel', {
						standardSubmit : true,
						url : '../../Funcs/WPP/WPP003.dsc',
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
							F004 : f004.getValue(),
							F005 : f005.getValue(),
							F006 : f006.getValue(),
							F007 : f007.getValue(),
							F008 : f008.getValue(),
							F009 : f009.getValue(),
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
				id : 'wpp03out01',
				text : '',
				margin : '0 0 0 10',
				x : 75,
				y : 5
			}, {
				xtype : 'label',
				id : 'wpp03out02',
				text : '',
				margin : '0 0 0 10',
				x : 325,
				y : 5
			}, {
				xtype : 'label',
				id : 'wpp03out03',
				text : '',
				margin : '0 0 0 10',
				x : 85,
				y : 33
			}, {
				xtype : 'label',
				id : 'wpp03out04',
				text : '',
				margin : '0 0 0 10',
				x : 335,
				y : 33
			}, {
				xtype : 'label',
				id : 'wpp03out05',
				text : '',
				margin : '0 0 0 10',
				x : 60,
				y : 90
			}, {
				xtype : 'label',
				id : 'wpp03out06',
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
			height : 140,
			x : 10,
			y : 10,
			//width : mainPanel.getWidth() - 20,
			minWidth : 1140,
			border : 0,
			items : [ {
				region : 'west',
				split : true,
				splitterResize : false,
				minWidth : 700,
				layout : 'absolute',
				border : 0,
				items : [ f001, f002, f003, f004, f005, f006, f007, f008, f009, sumbit, exportBtn ]
			}, outputArea ]
		});

		var main = Ext.create('DCI.Customer.SubPanel', {
			id : 'WPP003Main',
			renderTo : 'WPP003Page',
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
				f004store.loadData(records[0].get("f004_1value"));
				f006store.loadData(records[0].get("f006value"));

				var minwidth1 = f001.getWidth() - f001.getLabelWidth();
				var minwidth4 = f004.getWidth() - f004.getLabelWidth();
				var minwidth6 = f006.getWidth() - f006.getLabelWidth();

				f001.listConfig = {
					minWidth : minwidth1
				};
				f004.listConfig = {
					minWidth : minwidth4
				};
				f006.listConfig = {
					minWidth : minwidth6
				};

				//alert(f001store.getCount());				
				if (f001store.getCount() > 0 && typeof f001 != 'undefined' && f001 != null) {
					f001.setValue(f001store.getAt(0).get("value"));
				}
				//alert(f003store.getCount());				
				if (f004store.getCount() > 0 && typeof f004 != 'undefined' && f004 != null) {
					f004.setValue(f004store.getAt(0).get("value"));
				}
				//alert(f003store.getCount());				
				if (f006store.getCount() > 0 && typeof f006 != 'undefined' && f006 != null) {
					f006.setValue(f006store.getAt(0).get("value"));
				}
			}
		});
		main.resize(Ext.get("WPP003Page").getWidth(), Ext.get("WPP003Page").getHeight());
	}
</script>


</head>
<body>
	<div id="WPP003Page" class="page_div"></div>
</body>
</html>