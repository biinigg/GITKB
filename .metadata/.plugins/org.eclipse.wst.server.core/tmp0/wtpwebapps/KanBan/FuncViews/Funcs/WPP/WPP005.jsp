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
				"load_def_confirm_title", "load_def_confirm_msg", "cus_format", "to_excel", 'publish_info', 'publish_descript', 'manufacture', 'production_control',
				'publish_dept', 'urgency_level', 'info_update_complete_time', 'system_date', 'toolbar_query_title', 'receipt_wh', 'is_urgent_materials', 'all',
				'assigned_warehouse', 'yes', 'no', 'receipt_plant', 'vendor_name', 'assigned_completed', 'finished', 'producing', 'material_released', 'non_produced',
				'mfg_status', 'production_line', 'mapped_mo_no', 'po_no', 'unit', 'receipt_qty', 'receipt_warehouse', 'spec', 'part_name', 'part_no', 'publish_info',
				'material_shortage', 'completed', 'issuing', 'preparing', 'waiting', 'urgent_materials', 'receipt_type', 'purchasing', 'sub_contract', 'schedule_property',
				'scheduling', 'skl_issue_date', 'receipt_no', 'plan_receipt_date', 'iqc_days', 'material_ready_status' ];

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
				wpp005(infos, funclangs, records[0].get('dcitagValue'), uid);
			}
		});
	});

	function wpp005(kanbanInfos, funclangs, postvalue, uid) {
		var resultGrid = null;
		var headArea = null;
		var outputArea = null;

		Ext.define('wpp005gridModel', {
			extend : 'Ext.data.Model',
			fields : [ {
				type : 'string',
				name : 'TK001'
			}, {
				type : 'string',
				name : 'TK003'
			}, {
				type : 'string',
				name : 'TK006'
			}, {
				type : 'string',
				name : 'TK007'
			}, {
				type : 'string',
				name : 'TK008'
			}, {
				type : 'string',
				name : 'TK009'
			}, {
				type : 'string',
				name : 'TK010'
			}, {
				type : 'string',
				name : 'TK012'
			}, {
				type : 'string',
				name : 'TK014'
			}, {
				type : 'string',
				name : 'TK015'
			}, {
				type : 'string',
				name : 'TK016'
			}, {
				type : 'string',
				name : 'TK019'
			}, {
				type : 'string',
				name : 'TK020'
			}, {
				type : 'string',
				name : 'TK023'
			}, {
				type : 'string',
				name : 'TK025'
			}, {
				type : 'string',
				name : 'TK027'
			}, {
				type : 'string',
				name : 'TK029'
			}, {
				type : 'string',
				name : 'TE003'
			}, {
				type : 'string',
				name : 'MB002'
			}, {
				type : 'string',
				name : 'MB003'
			}, {
				type : 'string',
				name : 'NOTE'
			}, {
				type : 'string',
				name : 'TJ001'
			}, {
				type : 'string',
				name : 'TJ002'
			} ]
		});

		var initQueryStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "f001value", "f006value" ],
			proxy : {
				type : 'ajax',
				url : '../../Funcs/WPP/WPP005.dsc',
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
				url : '../../Funcs/WPP/WPP005.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json'
				}
			}
		});

		var gridstore = new Ext.data.Store({
			model : 'wpp005gridModel',
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
			gridid : 'WPP005_G01',
			postvalue : postvalue,
			uid : uid,
			griddbclick : function(comp, td, cellIndex, record, tr, rowIndex, e, eOpts) {
				var grid = this.getGrid();
				var col = grid.columns[cellIndex].dataIndex;
				if (record.get(col) != "" && record.get(col) != null) {
					if (col == "NOTE") {
						var keys = [ record.get("TJ001"), record.get("TJ002") ];
						showSubPanel05(postvalue, keys, kanbanInfos, uid, funclangs);
					}
				}
			}
		});

		var bodyColumns = [ {
			text : funclangs.receipt_type,
			dataIndex : 'TK014',
			colid : 'colTK014',
			width : 100,
			renderer : function(value, metaData, record, row, col, store, gridView) {
				if (value == '1') {
					return funclangs.purchasing;
				} else if (value == '2') {
					return funclangs.sub_contract;
				} else {
					return value;
				}
			}
		}, {
			text : funclangs.skl_issue_date,
			dataIndex : 'TK029',
			colid : 'colTK029',
			width : 100
		}, {
			text : funclangs.receipt_no,
			dataIndex : 'TK001',
			colid : 'colTK001',
			width : 150
		}, {
			text : funclangs.plan_receipt_date,
			dataIndex : 'TK003',
			colid : 'colTK003',
			width : 100
		}, {
			text : funclangs.iqc_days,
			dataIndex : 'TK016',
			colid : 'colTK016',
			width : 100
		}, {
			text : funclangs.publish_info,
			dataIndex : 'NOTE',
			colid : 'colNOTE',
			align : 'center',
			width : 80
		}, {
			text : funclangs.part_no,
			dataIndex : 'TK006',
			colid : 'colTK006',
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
			text : funclangs.receipt_warehouse,
			dataIndex : 'TK007',
			colid : 'colTK007',
			width : 150
		}, {
			text : funclangs.receipt_qty,
			dataIndex : 'TK008',
			colid : 'colTK008',
			width : 100
		}, {
			text : funclangs.unit,
			dataIndex : 'TK009',
			colid : 'colTK009',
			width : 100
		}, {
			text : funclangs.po_no,
			dataIndex : 'TK010',
			colid : 'colTK010',
			width : 200
		}, {
			text : funclangs.mapped_mo_no,
			dataIndex : 'TK027',
			colid : 'colTK027',
			width : 200
		}, {
			text : funclangs.production_line,
			dataIndex : 'TK025',
			colid : 'colTK025',
			width : 200
		}, {
			text : funclangs.mfg_status,
			dataIndex : 'TK020',
			colid : 'colTK020',
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
		}, {
			text : funclangs.vendor_name,
			dataIndex : 'TK023',
			colid : 'colTK023',
			width : 200
		}, {
			text : funclangs.urgent_materials,
			dataIndex : 'TK019',
			colid : 'colTK019',
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
			text : funclangs.schedule_property,
			dataIndex : 'TK015',
			colid : 'colTK015',
			width : 100,
			renderer : function(value, metaData, record, row, col, store, gridView) {
				if (value == '1') {
					return funclangs.scheduling;
				} else {
					return '';
				}
			}
		}, {
			text : funclangs.material_ready_status,
			dataIndex : 'TE003',
			colid : 'colTE003',
			width : 100,
			renderer : function(value, metaData, record, row, col, store, gridView) {
				if (value == '1') {
					return funclangs.waiting;
				} else if (value == '2') {
					return funclangs.preparing;
				} else if (value == '3') {
					return funclangs.issuing;
				} else if (value == '4') {
					return funclangs.completed;
				} else if (value == '5') {
					return funclangs.material_shortage;
				} else {
					return value;
				}
			}
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
			fieldLabel : funclangs.receipt_plant,
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
				"label" : "1." + funclangs.purchasing,
				"value" : "1"
			}, {
				"label" : "2." + funclangs.sub_contract,
				"value" : "2"
			} ]
		});

		var f002 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.receipt_type,
			store : f002store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			editable : false,
			multiSelect : false,
			x : 240,
			y : 10
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
			editable : false,
			multiSelect : false,
			x : 460,
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
			}, {
				"label" : "A." + funclangs.all,
				"value" : "A"
			} ]
		});

		var f005 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.is_urgent_materials,
			store : f005store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			editable : false,
			multiSelect : false,
			x : 10,
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
			fieldLabel : funclangs.receipt_wh,
			queryMode : 'local',
			store : f006store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 240,
			y : 50
		});

		///預設值
		f002.setValue("1");
		f003.setValue("N");
		//f004.setValue("Y");
		f005.setValue("A");

		var sumbit = Ext.create('Ext.Button', {
			text : funclangs.toolbar_query_title,
			renderer : "component",
			handler : function() {
				if (headArea != null) {
					resultGrid.setLoading(true);
					queryStore.removeAll();
					gridstore.removeAll();
					Ext.getCmp("wpp05out01").setText("");
					Ext.getCmp("wpp05out02").setText("");
					queryStore.getProxy().extraParams = {
						DCITag : postvalue,
						uid : uid,
						conn_id : kanbanInfos.conn_id,
						action : 'query',
						F001 : f001.getValue(),
						F002 : f002.getValue(),
						F003 : f003.getValue(),
						//F004 : f004.getValue(),
						F005 : f005.getValue(),
						F006 : f006.getValue()
					};

					queryStore.load(function(records) {
						if (records.length == 1) {
							if (records[0].get("headdatas") != null && records[0].get("headdatas") != '') {
								if (records[0].get("headdatas").length > 0) {
									Ext.getCmp("wpp05out01").setText(records[0].get("headdatas")[0]["currdate"]);
									Ext.getCmp("wpp05out02").setText(records[0].get("headdatas")[0]["TK017"]);
								}
								gridstore.loadData(records[0].get("griddatas"));
							}
						}
						resultGrid.setLoading(false);
					});
				}
			},
			x : 635,
			y : 50
		});

		var exportBtn = Ext.create('Ext.Button', {
			text : funclangs.to_excel,
			width : 80,
			x : 555,
			y : 50,
			handler : function() {
				var grid = resultGrid.getGrid();
				if (grid && grid != null) {
					var downloadform = Ext.create('Ext.form.Panel', {
						standardSubmit : true,
						url : '../../Funcs/WPP/WPP005.dsc',
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
							//F004 : f004.getValue(),
							F005 : f005.getValue(),
							F006 : f006.getValue(),
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
				text : funclangs.system_date + '：',
				margin : '0 0 0 10',
				x : 0,
				y : 5
			}, {
				xtype : 'label',
				text : funclangs.info_update_complete_time + '：',
				margin : '0 0 0 10',
				x : 0,
				y : 40
			}, {
				xtype : 'label',
				id : 'wpp05out01',
				text : '',
				margin : '0 0 0 10',
				x : 60,
				y : 5
			}, {
				xtype : 'label',
				id : 'wpp05out02',
				text : '',
				margin : '0 0 0 10',
				x : 110,
				y : 40
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
				minWidth : 720,
				layout : 'absolute',
				border : 0,
				items : [ f001, f002, f003, f005, f006, sumbit, exportBtn ]
			}, outputArea ]
		});

		var main = Ext.create('DCI.Customer.SubPanel', {
			id : 'WPP005Main',
			renderTo : 'WPP005Page',
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
				f006store.loadData(records[0].get("f006value"));

				var minwidth1 = f001.getWidth() - f001.getLabelWidth();
				var minwidth6 = f006.getWidth() - f006.getLabelWidth();

				f001.listConfig = {
					minWidth : minwidth1
				};
				f006.listConfig = {
					minWidth : minwidth6
				};

				if (f001store.getCount() > 0 && typeof f001 != 'undefined' && f001 != null) {
					f001.setValue(f001store.getAt(0).get("value"));
				}

				if (f006store.getCount() > 0 && typeof f006 != 'undefined' && f006 != null) {
					f006.setValue(f006store.getAt(0).get("value"));
				}
			}
		});
		main.resize(Ext.get("WPP005Page").getWidth(), Ext.get("WPP005Page").getHeight());
	}

	function showSubPanel05(postvalue, keys, kanbanInfos, uid, funclangs) {
		var win = null;
		console.log(uid);
		var subGridStore = Ext.create('Ext.data.Store', {
			
			fields : [ "TJ003", "TJ005", "TJ006" ],
			autoLoad : false,
			proxy : {
				type : 'ajax',
				url : '../../Funcs/WPP/WPP005.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json',
					root : 'griddatas'
				},
				extraParams : {
					DCITag : postvalue,
					uid : uid,
					conn_id : kanbanInfos.conn_id,
					action : 'sub',
					TJ001 : keys[0],
					TJ002 : keys[1]
				}
			}
		});

		var subGrid = Ext.create('Ext.grid.Panel', {
			renderer : "component",
			stripeRows : true,
			columns : [ {
				text : funclangs.urgency_level,
				dataIndex : 'TJ006',
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
			}, {
				text : funclangs.publish_dept,
				dataIndex : 'TJ003',
				width : 80,
				renderer : function(value, metaData, record, row, col, store, gridView) {
					if (value == '1') {
						return funclangs.production_control;
					} else if (value == '2') {
						return funclangs.manufacture;
					} else if (value == '3') {
						return funclangs.purchasing;
					} else {
						return value;
					}
				}
			}, {
				text : funclangs.publish_descript,
				dataIndex : 'TJ005',
				width : 200
			} ],
			store : subGridStore,
			autoScroll : true,
			loadMask : true,
			viewConfig : {
				forceFit : false,
				autoLoad : false
			},
			enableTextSelection : true
		});

		//subGridStore.loadData(records[0].get("griddatas"));

		win = Ext.create('Ext.window.Window', {
			title : funclangs.publish_info,
			width : 400,
			height : 300,
			minWidth : 300,
			minHeight : 200,
			layout : 'fit',
			plain : true,
			modal : true
		});

		subGridStore.load(function(records) {
			win.add(subGrid);
		});

		win.show();

	}
</script>
</head>
<body>
	<div id="WPP005Page" class="page_div"></div>
</body>
</html>