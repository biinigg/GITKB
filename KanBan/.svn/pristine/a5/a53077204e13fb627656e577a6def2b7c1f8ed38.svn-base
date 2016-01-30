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
				"load_def_confirm_title", "load_def_confirm_msg", "cus_format", "to_excel", 'publish_descript', 'purchasing', 'manufacture', 'production_control', 'publish_dept',
				'urgency_level', 'purchaser', 'planner', 'plan_receive_qty', 'material_short_qty', 'warehouse_id', 'required_qty', 'custom_supplied_materials',
				'client_supplied_materials', 'indirect_materials', 'direct_materials', 'material_type', 'material_part_no', 'option_part', 'feature_part', 'phantom_part',
				'sub_contract_part', 'self_manufactured', 'purchasing_part', 'part_type', 'issue_date', 'schedule_date', 'info_update_complete_time', 'publish_date',
				'system_date', 'skl_end_week', 'skl_start_week', 'skl_end_date', 'skl_start_date', 'toolbar_query_title', 'schedule_type', 'abnormal_type', 'all',
				'material_short_risk', 'completed_risk', 'start_risk_only', 'outsource_id', 'production_line', 'sub_contract', 'in_house', 'site_id', 'plan_shipping_date',
				'cus_name', 'cus_id', 'order_no', 'parent_mo_no', 'planned_batch_no', 'real_complete_date', 'real_release_date', 'publish_info', 'material_shortage', 'completed',
				'issuing', 'preparing', 'waiting', 'material_ready_progress', 'plan_mat_ready_date', 'labor_equip_total_hour', 'schedule_qty', 'assigned_completed', 'finished',
				'producing', 'material_released', 'non_produced', 'mfg_status', 'produced_qty', 'start_warning', 'completed_warning', 'optimistic_material_short', 'priority',
				'sub_datial', 'skl_begin_date', 'mfg_skl_complete_date', 'mfg_order_id', 'part_no', 'part_name', 'spec', 'unit', 'mfg_qty' ];
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
				wpp001(infos, funclangs, records[0].get('dcitagValue'), uid);
			}
		});
	});

	function wpp001(kanbanInfos, funclangs, postvalue, uid) {
		var resultGrid = null;
		var headArea = null;
		var outputArea = null;

		Ext.define('gridModel', {
			extend : 'Ext.data.Model',
			fields : [ {
				type : 'string',
				name : 'TB015'
			}, {
				type : 'string',
				name : 'TB016'
			}, {
				type : 'string',
				name : 'TB017'
			}, {
				type : 'string',
				name : 'TB008'
			}, {
				type : 'string',
				name : 'TB009'
			}, {
				type : 'string',
				name : 'TB010'
			}, {
				type : 'string',
				name : 'TB011'
			}, {
				type : 'string',
				name : 'MOID'
			}, {
				type : 'string',
				name : 'TA006'
			}, {
				type : 'string',
				name : 'MB002'
			}, {
				type : 'string',
				name : 'MB003'
			}, {
				type : 'string',
				name : 'TA007'
			}, {
				type : 'string',
				name : 'TA015'
			}, {
				type : 'string',
				name : 'TA017'
			}, {
				type : 'string',
				name : 'TA011'
			}, {
				type : 'string',
				name : 'TB012'
			}, {
				type : 'string',
				name : 'TB013'
			}, {
				type : 'string',
				name : 'TB014'
			}, {
				type : 'string',
				name : 'TE003'
			}, {
				type : 'string',
				name : 'NOTE'
			}, {
				type : 'string',
				name : 'TA012'
			}, {
				type : 'string',
				name : 'TA014'
			}, {
				type : 'string',
				name : 'TA033'
			}, {
				type : 'string',
				name : 'PORDERID'
			}, {
				type : 'string',
				name : 'ORDERID'
			}, {
				type : 'string',
				name : 'TB021'
			}, {
				type : 'string',
				name : 'TB022'
			}, {
				type : 'string',
				name : 'TB023'
			}, {
				type : 'string',
				name : 'TB001'
			}, {
				type : 'string',
				name : 'TB002'
			}, {
				type : 'string',
				name : 'TB003'
			}, {
				type : 'string',
				name : 'TB004'
			}, {
				type : 'string',
				name : 'TB005'
			}, {
				type : 'string',
				name : 'TB006'
			}, {
				type : 'string',
				name : 'TB007'
			} ]
		});

		var initQueryStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "f001value", "f003value", "f004value" ],
			proxy : {
				type : 'ajax',
				url : '../../Funcs/WPP/WPP001.dsc',
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
				url : '../../Funcs/WPP/WPP001.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json'
				}
			}
		});

		var gridstore = new Ext.data.Store({
			model : 'gridModel',
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
			gridid : 'WPP001_G01',
			postvalue : postvalue,
			uid : uid,
			griddbclick : function(comp, td, cellIndex, record, tr, rowIndex, e, eOpts) {
				var me = this;
				var grid = me.getGrid();
				var col = grid.columns[cellIndex].dataIndex;
				if (record.get(col) != "" && record.get(col) != null) {
					if (col == "TB009" || col == "TB017" || col == "NOTE") {
						var keys = null;
						if (col == "TB009" || col == "TB017") {
							keys = [ record.get("TB001"), record.get("TB002"), record.get("TB003"), record.get("TB004"), record.get("TB005") ];
						} else if (col == "NOTE") {
							keys = [ record.get("TB006"), record.get("TB007"), record.get("ORDERID") ];
						}

						showSubPanel01(postvalue, keys, col, kanbanInfos, uid, funclangs);
					}
				}
			}
		});

		var bodyColumns = [ {
			text : funclangs.start_warning,
			dataIndex : 'TB015',
			colid : 'colTB015',
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
			text : funclangs.completed_warning,
			dataIndex : 'TB016',
			colid : 'colTB016',
			width : 80,
			align : 'center',
			renderer : function(value, metaData, record, row, col, store, gridView) {
				if (value == 'R') {
					return '<img src="../../images/icons/RedLight.png" width="15px" height="15px"/>';
				} else if (value == 'Y') {
					return '<img src="../../images/icons/YellowLight.png" width="15px" height="15px"/>';
				} else {
					return "";
				}
			}
		}, {
			text : funclangs.optimistic_material_short,
			dataIndex : 'TB017',
			colid : 'colTB017',
			width : 80,
			align : 'center',
			renderer : function(value, metaData, record, row, col, store, gridView) {
				if (value == 'R') {
					return '<img src="../../images/icons/RedLight.png" width="15px" height="15px" />';
				} else {
					return "";
				}
			}
		}, {
			text : funclangs.priority,
			dataIndex : 'TB008',
			colid : 'colTB008',
			width : 80
		}, {
			text : funclangs.sub_datial,
			dataIndex : 'TB009',
			colid : 'colTB009',
			align : 'center',
			width : 150
		}, {
			text : funclangs.skl_begin_date,
			dataIndex : 'TB010',
			colid : 'colTB010',
			width : 150
		}, {
			text : funclangs.mfg_skl_complete_date,
			dataIndex : 'TB011',
			colid : 'colTB011',
			width : 150
		}, {
			text : funclangs.mfg_order_id,
			dataIndex : 'MOID',
			colid : 'colMOID',
			width : 200
		}, {
			text : funclangs.part_no,
			dataIndex : 'TA006',
			colid : 'colTA006',
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
			width : 150
		}, {
			text : funclangs.unit,
			dataIndex : 'TA007',
			colid : 'colTA007',
			width : 80
		}, {
			text : funclangs.mfg_qty,
			dataIndex : 'TA015',
			colid : 'colTA015',
			width : 100
		}, {
			text : funclangs.produced_qty,
			dataIndex : 'TA017',
			colid : 'colTA017',
			width : 100
		}, {
			text : funclangs.mfg_status,
			dataIndex : 'TA011',
			colid : 'colTA011',
			width : 150,
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
			text : funclangs.schedule_qty,
			dataIndex : 'TB012',
			colid : 'colTB012',
			width : 100
		}, {
			text : funclangs.labor_equip_total_hour,
			dataIndex : 'TB013',
			colid : 'colTB013',
			width : 100
		}, {
			text : funclangs.plan_mat_ready_date,
			dataIndex : 'TB014',
			colid : 'colTB014',
			width : 150
		}, {
			text : funclangs.material_ready_progress,
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
		}, {
			text : funclangs.publish_info,
			dataIndex : 'NOTE',
			colid : 'colNOTE',
			align : 'center',
			width : 80
		}, {
			text : funclangs.real_release_date,
			dataIndex : 'TA012',
			colid : 'colTA012',
			width : 150
		}, {
			text : funclangs.real_complete_date,
			dataIndex : 'TA014',
			colid : 'colTA014',
			width : 150
		}, {
			text : funclangs.planned_batch_no,
			dataIndex : 'TA033',
			colid : 'colTA033',
			width : 200
		}, {
			text : funclangs.parent_mo_no,
			dataIndex : 'PORDERID',
			colid : 'colPORDERID',
			width : 200
		}, {
			text : funclangs.order_no,
			dataIndex : 'ORDERID',
			colid : 'colORDERID',
			width : 200
		}, {
			text : funclangs.cus_id,
			dataIndex : 'TB021',
			colid : 'colTB021',
			width : 150
		}, {
			text : funclangs.cus_name,
			dataIndex : 'TB022',
			colid : 'colTB022',
			width : 250
		}, {
			text : funclangs.plan_shipping_date,
			dataIndex : 'TB023',
			colid : 'colTB023',
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
				"label" : "1." + funclangs.in_house,
				"value" : "1"
			}, {
				"label" : "2." + funclangs.sub_contract,
				"value" : "2"
			} ]
		});

		var f003store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
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
			shrinkWrap : 3,
			fieldLabel : funclangs.production_line,
			store : f003store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 10,
			y : 90
		});

		var f004store = Ext.create('Ext.data.Store', {
			fields : [ "label", "value" ],
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
			shrinkWrap : 3,
			fieldLabel : funclangs.outsource_id,
			store : f004store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			matchFieldWidth : false,
			editable : false,
			multiSelect : false,
			x : 230,
			y : 10
		});

		var f005store = Ext.create('Ext.data.Store', {
			fields : [ 'label', 'value' ],
			data : [ {
				"label" : "1." + funclangs.start_risk_only,
				"value" : "1"
			}, {
				"label" : "2." + funclangs.completed_risk,
				"value" : "2"
			}, {
				"label" : "3." + funclangs.material_short_risk,
				"value" : "3"
			}, {
				"label" : "4." + funclangs.all,
				"value" : "4"
			} ]
		});

		var f005 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : funclangs.abnormal_type,
			store : f005store,
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			editable : false,
			multiSelect : false,
			x : 230,
			y : 50
		});

		f005.setValue("4");

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
			y : 50,
			listeners : {
				change : function(combobox, newValue, oldValue, eOpts) {
					if (newValue == "1") {
						if (typeof f003 !== 'undefined' && f003 != null && typeof f004 !== 'undefined' && f004 != null) {
							//f004.clearValue();
							f003.setDisabled(false);
							f004.setDisabled(true);
						}
					} else {
						if (typeof f003 !== 'undefined' && f003 != null && typeof f004 !== 'undefined' && f004 != null) {
							//f003.clearValue();
							f003.setDisabled(true);
							f004.setDisabled(false);
						}
					}
				}
			}
		});

		f002.setValue("1");
		f003.setDisabled(false);
		f004.setDisabled(true);

		var sumbit = Ext.create('Ext.Button', {
			text : funclangs.toolbar_query_title,
			renderer : "component",
			handler : function() {
				if (headArea != null) {
					resultGrid.setLoading(true);
					queryStore.removeAll();
					gridstore.removeAll();
					Ext.getCmp("wpp01out01").setText("");
					Ext.getCmp("wpp01out02").setText("");
					//Ext.getCmp("wpp01out03").setText("");
					//Ext.getCmp("wpp01out04").setText("");
					Ext.getCmp("wpp01out05").setText("");
					Ext.getCmp("wpp01out06").setText("");
					//Ext.getCmp("wpp01out07").setText("");
					//Ext.getCmp("wpp01out08").setText("");
					Ext.getCmp("wpp01out09").setText("");
					Ext.getCmp("wpp01out10").setText("");
					//Ext.getCmp("wpp01out11").setText("");
					Ext.getCmp("wpp01out12").setText("");
					//Ext.getCmp("wpp01out13").setText("");
					queryStore.getProxy().extraParams = {
						DCITag : postvalue,
						uid : uid,
						action : 'query',
						conn_id : kanbanInfos.conn_id,
						F001 : f001.getValue(),
						F002 : f002.getValue(),
						F003 : f003.getValue(),
						F004 : f004.getValue(),
						F005 : f005.getValue()
					};

					queryStore.load(function(records) {
						//alert(records.length + "---" + records[0].get("headdatas").length);
						if (records.length == 1) {
							if (records[0].get("headdatas").length > 0) {
								Ext.getCmp("wpp01out01").setText(records[0].get("headdatas")[0]["TA006"]);
								Ext.getCmp("wpp01out02").setText(records[0].get("headdatas")[0]["TA007"]);
								//Ext.getCmp("wpp01out03").setText(records[0].get("headdatas")[0][""]);
								//Ext.getCmp("wpp01out04").setText(records[0].get("headdatas")[0][""]);
								Ext.getCmp("wpp01out05").setText(records[0].get("headdatas")[0]["TA008"]);
								Ext.getCmp("wpp01out06").setText(records[0].get("headdatas")[0]["TA009"]);
								//Ext.getCmp("wpp01out07").setText(records[0].get("headdatas")[0][""]);
								//Ext.getCmp("wpp01out08").setText(records[0].get("headdatas")[0][""]);
								Ext.getCmp("wpp01out09").setText(records[0].get("headdatas")[0]["currdate"]);
								Ext.getCmp("wpp01out10").setText(records[0].get("headdatas")[0]["TA010"]);
								//Ext.getCmp("wpp01out11").setText(records[0].get("headdatas")[0]["TA011"]);
								Ext.getCmp("wpp01out12").setText(records[0].get("headdatas")[0]["TA012"]);
								//Ext.getCmp("wpp01out13").setText(records[0].get("headdatas")[0][""]);
							}
							gridstore.loadData(records[0].get("griddatas"));
						}
						resultGrid.setLoading(false);
					});
				}
			},
			x : 405,
			y : 90
		});

		var exportBtn = Ext.create('Ext.Button', {
			//xtype : 'button',
			//cls : 'toexcelbutton',
			text : funclangs.to_excel,
			//tooltip : funclangs.to_excel,
			width : 80,
			//height : 30,
			x : 325,
			y : 90,
			handler : function() {
				var me = this;
				var grid = resultGrid.getGrid();
				if (grid && grid != null) {
					//buildHtml(grid.columns, grid.getStore().getRange(0), "1", function(datas) {
					var downloadform = Ext.create('Ext.form.Panel', {
						standardSubmit : true,
						url : '../../Funcs/WPP/WPP001.dsc',
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
							cols : Ext.encode(buildColObj(grid.columns))
						}
					});
					//});
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
				x : 185,
				y : 5
			}/* , {
																																																																																																																																					xtype : 'label',
																																																																																																																																					text : '建立日期：',
																																																																																																																																					margin : '0 0 0 10',
																																																																																																																																					x : 370,
																																																																																																																																					y : 5
																																																																																																																																				}, {
																																																																																																																																					xtype : 'label',
																																																																																																																																					text : '建立者：',
																																																																																																																																					margin : '0 0 0 10',
																																																																																																																																					x : 545,
																																																																																																																																					y : 5
																																																																																																																																				} */, {
				xtype : 'label',
				text : funclangs.skl_start_week + '：',
				margin : '0 0 0 10',
				x : 0,
				y : 33
			}, {
				xtype : 'label',
				text : funclangs.skl_end_week + '：',
				margin : '0 0 0 10',
				x : 185,
				y : 33
			}/* , {
																																																																																																																																					xtype : 'label',
																																																																																																																																					text : '修改日期：',
																																																																																																																																					margin : '0 0 0 10',
																																																																																																																																					x : 370,
																																																																																																																																					y : 33
																																																																																																																																				}, {
																																																																																																																																					xtype : 'label',
																																																																																																																																					text : '修改者：',
																																																																																																																																					margin : '0 0 0 10',
																																																																																																																																					x : 545,
																																																																																																																																					y : 33
																																																																																																																																				} */, {
				xtype : 'label',
				text : funclangs.system_date + '：',
				margin : '0 0 0 10',
				x : 370,
				y : 5
			}, {
				xtype : 'label',
				text : funclangs.publish_date + '：',
				margin : '0 0 0 10',
				x : 370,
				y : 33
			}/* , {
																																																																																																																																					xtype : 'label',
																																																																																																																																					text : '時間：',
																																																																																																																																					margin : '0 0 0 10',
																																																																																																																																					x : 545,
																																																																																																																																					y : 60
																																																																																																																																				} */, {
				xtype : 'label',
				text : funclangs.info_update_complete_time + '：',
				margin : '0 0 0 10',
				x : 0,
				y : 60
			}/* , {
																																																																																																																																					xtype : 'label',
																																																																																																																																					text : '版本：',
																																																																																																																																					margin : '0 0 0 10',
																																																																																																																																					x : 370,
																																																																																																																																					y : 90
																																																																																																																																				} */, {
				xtype : 'label',
				id : 'wpp01out01',
				text : '',
				margin : '0 0 0 10',
				x : 70,
				y : 5
			}, {
				xtype : 'label',
				id : 'wpp01out02',
				text : '',
				margin : '0 0 0 10',
				x : 258,
				y : 5
			}/* , {
																																																																																																																																					xtype : 'label',
																																																																																																																																					id : 'wpp01out03',
																																																																																																																																					text : '',
																																																																																																																																					margin : '0 0 0 10',
																																																																																																																																					x : 430,
																																																																																																																																					y : 5
																																																																																																																																				}, {
																																																																																																																																					xtype : 'label',
																																																																																																																																					id : 'wpp01out04',
																																																																																																																																					text : '',
																																																																																																																																					margin : '0 0 0 10',
																																																																																																																																					x : 595,
																																																																																																																																					y : 5
																																																																																																																																				} */, {
				xtype : 'label',
				id : 'wpp01out05',
				text : '',
				margin : '0 0 0 10',
				x : 85,
				y : 33
			}, {
				xtype : 'label',
				id : 'wpp01out06',
				text : '',
				margin : '0 0 0 10',
				x : 270,
				y : 33
			}/* , {
																																																																																																																																					xtype : 'label',
																																																																																																																																					id : 'wpp01out07',
																																																																																																																																					text : '',
																																																																																																																																					margin : '0 0 0 10',
																																																																																																																																					x : 430,
																																																																																																																																					y : 33
																																																																																																																																				}, {
																																																																																																																																					xtype : 'label',
																																																																																																																																					id : 'wpp01out08',
																																																																																																																																					text : '',
																																																																																																																																					margin : '0 0 0 10',
																																																																																																																																					x : 595,
																																																																																																																																					y : 33
																																																																																																																																				} */, {
				xtype : 'label',
				id : 'wpp01out09',
				text : '',
				margin : '0 0 0 10',
				x : 430,
				y : 5
			}, {
				xtype : 'label',
				id : 'wpp01out10',
				text : '',
				margin : '0 0 0 10',
				x : 430,
				y : 33
			}/* , {
																																																																																																																																					xtype : 'label',
																																																																																																																																					id : 'wpp01out11',
																																																																																																																																					text : '',
																																																																																																																																					margin : '0 0 0 10',
																																																																																																																																					x : 595,
																																																																																																																																					y : 60
																																																																																																																																				} */, {
				xtype : 'label',
				id : 'wpp01out12',
				text : '',
				margin : '0 0 0 10',
				x : 110,
				y : 60
			} /* , {
																																																																																											xtype : 'label',
																																																																																											id : 'wpp01out13',
																																																																																											text : '',
																																																																																											margin : '0 0 0 10',
																																																																																											x : 410,
																																																																																											y : 90
																																																																																										}  */]
		});

		headArea = Ext.create('Ext.Panel', {
			region : 'north',
			renderer : "component",
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
				minWidth : 445,
				layout : 'absolute',
				border : 0,
				items : [ f001, f002, f003, f004, f005, sumbit, exportBtn ]
			}, outputArea ]
		});

		var main = Ext.create('DCI.Customer.SubPanel', {
			id : 'WPP001Main',
			renderTo : 'WPP001Page',
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
		/* ,
					listeners : {
						resize : function(panel, width, height, oldWidth, oldHeight, eOpts) {
							if (resultGrid != null) {
								if (height > oldHeight) {
									resultGrid.setHeight(mainPanel.getHeight() - mainPanel.getTabBar().getHeight() - 162);
								}
							}
						}
					} */
		});

		initQueryStore.load(function(records) {
			if (records.length == 1) {
				f001store.loadData(records[0].get("f001value"));
				f003store.loadData(records[0].get("f003value"));
				f004store.loadData(records[0].get("f004value"));

				var minwidth1 = f001.getWidth() - f001.getLabelWidth();
				var minwidth3 = f003.getWidth() - f003.getLabelWidth();
				var minwidth4 = f004.getWidth() - f004.getLabelWidth();
				f001.listConfig = {
					minWidth : minwidth1
				};
				f003.listConfig = {
					minWidth : minwidth3
				};
				f004.listConfig = {
					minWidth : minwidth4
				};

				//alert(f001store.getCount());				
				if (f001store.getCount() > 0 && typeof f001 != 'undefined' && f001 != null) {
					f001.setValue(f001store.getAt(0).get("value"));
				}
				//alert(f003store.getCount());				
				if (f003store.getCount() > 0 && typeof f003 != 'undefined' && f003 != null) {
					f003.setValue(f003store.getAt(0).get("value"));
				}
				//alert(f004store.getCount());				
				if (f004store.getCount() > 0 && typeof f004 != 'undefined' && f004 != null) {
					f004.setValue(f004store.getAt(0).get("value"));
				}
			}
		});

		main.resize(Ext.get("WPP001Page").getWidth(), Ext.get("WPP001Page").getHeight());
	}

	function showSubPanel01(postvalue, keys, subtype, kanbanInfos, uid, funclangs) {
		//alert(subtype);
		var subGrid = null;
		var win = null;
		var subQueryStore = Ext.create('Ext.data.Store', {
			fields : [ "head", "griddatas" ],
			autoLoad : false,
			proxy : {
				type : 'ajax',
				url : '../../Funcs/WPP/WPP001.dsc',
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
					TB001 : '',
					TB002 : '',
					TB003 : '',
					TB004 : '',
					TB005 : '',
					TB006 : '',
					TB007 : '',
					ORDERID : ''
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

		if (subtype == "NOTE") {
			if (subQueryStore.getProxy().extraParams.hasOwnProperty('TB006')) {
				subQueryStore.getProxy().extraParams['TB006'] = keys[0];
			}

			if (subQueryStore.getProxy().extraParams.hasOwnProperty('TB007')) {
				subQueryStore.getProxy().extraParams['TB007'] = keys[1];
			}

			if (subQueryStore.getProxy().extraParams.hasOwnProperty('ORDERID')) {
				subQueryStore.getProxy().extraParams['ORDERID'] = keys[2];
			}
		} else {
			if (subQueryStore.getProxy().extraParams.hasOwnProperty('TB001')) {
				subQueryStore.getProxy().extraParams['TB001'] = keys[0];
			}
			if (subQueryStore.getProxy().extraParams.hasOwnProperty('TB002')) {
				subQueryStore.getProxy().extraParams['TB002'] = keys[1];
			}
			if (subQueryStore.getProxy().extraParams.hasOwnProperty('TB003')) {
				subQueryStore.getProxy().extraParams['TB003'] = keys[2];
			}
			if (subQueryStore.getProxy().extraParams.hasOwnProperty('TB004')) {
				subQueryStore.getProxy().extraParams['TB004'] = keys[3];
			}
			if (subQueryStore.getProxy().extraParams.hasOwnProperty('TB005')) {
				subQueryStore.getProxy().extraParams['TB005'] = keys[4];
			}
		}

		var title = "";
		var h = 300;
		var w = 500;

		if (subtype == "TB009") {
			title = funclangs.sub_datial;
			w = 500;
		} else if (subtype == "TB017") {
			title = funclangs.optimistic_material_short;
			w = 800;
		} else if (subtype == "NOTE") {
			title = funclangs.publish_info;
			w = 400;
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
				if (subtype == "TB009") {
					subGridStore.model.setFields([ "TC007", "TC008", "TC009", "TC010", "TC011", "TC012" ]);
					if (records[0].get("head").length == 1) {
						cols.push({
							text : funclangs.schedule_date,
							dataIndex : 'TC007',
							width : 80
						});
						cols.push({
							text : funclangs.schedule_qty,
							dataIndex : 'TC008',
							width : 80
						});
						cols.push({
							text : records[0].get("head")[0]["MA008"],
							dataIndex : 'TC009',
							width : 150
						});
						cols.push({
							text : records[0].get("head")[0]["MA009"],
							dataIndex : 'TC010',
							width : 150
						});
						cols.push({
							text : records[0].get("head")[0]["MA010"],
							dataIndex : 'TC011',
							width : 150
						});
						cols.push({
							text : records[0].get("head")[0]["MA011"],
							dataIndex : 'TC012',
							width : 150
						});
					}
				} else if (subtype == "TB017") {
					subGridStore.model.setFields([ "TG002", "TG003", "TG007", "TG008", "TG012", "TG013", "TG021", "MB002", "MB003", "MB004", "MB025", "TB011", "MAN01", "MAN02" ]);
					cols.push({
						text : funclangs.optimistic_material_short,
						dataIndex : 'TG013',
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
						text : funclangs.issue_date,
						dataIndex : 'TG007',
						width : 150
					});

					cols.push({
						text : funclangs.part_type,
						dataIndex : 'MB025',
						width : 100,
						renderer : function(value, metaData, record, row, col, store, gridView) {
							if (value == 'P') {
								return funclangs.purchasing_part;
							} else if (value == 'M') {
								return funclangs.self_manufactured;
							} else if (value == 'S') {
								return funclangs.sub_contract_part;
							} else if (value == 'Y') {
								return funclangs.phantom_part;
							} else if (value == 'F') {
								return funclangs.feature_part;
							} else if (value == 'O') {
								return funclangs.option_part;
							} else {
								return value;
							}
						}
					});
					cols.push({
						text : funclangs.material_part_no,
						dataIndex : 'TG002',
						width : 150
					});
					cols.push({
						text : funclangs.part_name,
						dataIndex : 'MB002',
						width : 200
					});
					cols.push({
						text : funclangs.spec,
						dataIndex : 'MB003',
						width : 200
					});
					cols.push({
						text : funclangs.unit,
						dataIndex : 'MB004',
						width : 150
					});
					cols.push({
						text : funclangs.material_type,
						dataIndex : 'TB011',
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
					});
					cols.push({
						text : funclangs.required_qty,
						dataIndex : 'TG008',
						width : 100
					});
					cols.push({
						text : funclangs.warehouse_id,
						dataIndex : 'TG003',
						width : 200
					});
					cols.push({
						text : funclangs.material_short_qty,
						dataIndex : 'TG012',
						width : 100
					});
					cols.push({
						text : funclangs.plan_receive_qty,
						dataIndex : 'TG021',
						width : 100
					});
					cols.push({
						text : funclangs.planner,
						dataIndex : 'MAN01',
						width : 150
					});
					cols.push({
						text : funclangs.purchaser,
						dataIndex : 'MAN02',
						width : 150
					});
				} else if (subtype == "NOTE") {
					subGridStore.model.setFields([ "TJ006", "TJ003", "TJ005" ]);
					cols.push({
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
					});
					cols.push({
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
					});
					cols.push({
						text : funclangs.publish_descript,
						dataIndex : 'TJ005',
						width : 200
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
	<div id="WPP001Page" class="page_div"></div>
</body>
</html>