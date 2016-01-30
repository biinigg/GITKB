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
				"load_def_confirm_title", "load_def_confirm_msg", "cus_format", 'site_id', 'production_line', 'equipment', 'equip_id', 'equip_name', 'production_line_id',
				'production_line_name', 'work_order', 'process', 'process_id', 'prcess_name', 'description', 'query_results', 'work_order_id', 'process_id_name_sequ', 'time',
				'arrived_qty', 'completed_qty', 'yield_rate', 'loss_qty', 'qty_surplus', 'rework_qty', 'toolbar_query_title', 'date_range', 'yield_range', 'only_numeric_allowed',
				'range_non_nullable', 'exception_only', 'day_start_time', 'equip_yield_trend_chart','open_win' ];
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
				LKB003(infos, funclangs, records[0].get('dcitagValue'), uid);
			}
		});
	});

	function LKB003(kanbanInfos, funclangs, postvalue, uid) {
		var resultGrid = null;
		var conditionArea = null;
		var chartfields = [];

		var LKB003Panel = Ext.create('DCI.Customer.SubTabPanel', {
			id : 'LKB003Main',
			minWidth : 1120,
			deferredRender : false,
			renderTo : 'LKB003Page',
			widthChangeControls : [ conditionArea, resultGrid ],
			listeners : {
				tabchange : function(tabPanel, newCard, oldCard, eOpts) {
					if (tabPanel.getActiveTab().getId() == "LKB003C") {
						if (typeof queryStore !== 'undefined' && queryStore != null) {
							if (queryStore.getCount() == 1) {
								var fields = [];
								seriesStore.removeAll();
								chartStore.removeAll();
								chart.series.clear();
								seriesStore.loadData(queryStore.getAt(0).get("series"));

								var records = seriesStore.getRange(0);

								for ( var i = 0; i < records.length; i++) {
									if (records[i].get('series') == "date") {
										fields.push({
											name : records[i].get('series'),
											type : 'string'
										});
									} else {
										fields.push({
											name : records[i].get('series'),
											type : 'double'
										});
										chartfields.push(records[i].get('series'));
										//alert(records[i].get('series'));
										chart.series.add({
											type : 'line',
											axis : 'left',
											xField : 'date',
											yField : records[i].get('series'),
											highlight : {
												size : 7,
												radius : 7
											},
											label : {
												field : records[i].get('series'),
												display : 'under',
												contrast : true,
												font : '18px Arial',
												renderer : function(v) {
													return v + "%";
												}
											},
											markerConfig : {
												type : 'circle',
												size : 4,
												radius : 4,
												'stroke-width' : 0
											}
										});
									}
								}
								chartModel.setFields(fields);
								chartStore.loadData(queryStore.getAt(0).get("chartdatas"));
								queryStore.add({
									rows : "",
									series : "",
									chartdatas : ""
								});
							}
						}
					}
				}
			}
		});

		Ext.define('gridModel', {
			extend : 'Ext.data.Model',
			fields : [ {
				type : 'string',
				name : 'seq'
			}, {
				type : 'string',
				name : 'order_ID'
			}, {
				type : 'string',
				name : 'ws'
			}, {
				type : 'string',
				name : 'op'
			}, {
				type : 'string',
				name : 'eq_ID'
			}, {
				type : 'string',
				name : 'eq_Name'
			}, {
				type : 'string',
				name : 'out_Time'
			}, {
				type : 'double',
				name : 'arrive_Qty'
			}, {
				type : 'double',
				name : 'out_Qty'
			}, {
				type : 'double',
				name : 'utility'
			}, {
				type : 'double',
				name : 'shrinkage_Qty'
			}, {
				type : 'double',
				name : 'surplus_Qty'
			}, {
				type : 'double',
				name : 'rework_Qty'
			} ]
		});

		var queryStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "rows", "series", "chartdatas" ],
			proxy : {
				type : 'ajax',
				url : '../../Funcs/LKB/LKB003.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json'
				}
			}
		});

		var seriesStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "series" ],
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var f001 = Ext.create('Ext.form.ComboBox', {
			id : 'F001',
			renderer : "component",
			fieldLabel : funclangs.site_id,
			store : BuildComboboxStore('../../Funcs/LKB/LKB003.dsc', {
				DCITag : postvalue,
				uid : uid,
				conn_id : kanbanInfos.conn_id,
				action : 'F001'
			}),
			displayField : 'label',
			valueField : 'value',
			labelWidth : 50,
			x : 10,
			y : 10,
			listeners : {
				change : function(combobox, newValue, oldValue, eOpts) {
					if (typeof f002 !== 'undefined' && f002 != null) {
						f002.getStore().getProxy().extraParams['F001'] = newValue;
						f002.getStore().load();
						f002.clearValue();
					}
				}
			}
		});

		var f002 = Ext.create('Ext.form.ComboBox', {
			id : 'F002',
			renderer : "component",
			fieldLabel : funclangs.production_line,
			store : BuildComboboxStore('../../Funcs/LKB/LKB003.dsc', {
				DCITag : postvalue,
				uid : uid,
				conn_id : kanbanInfos.conn_id,
				action : 'F002',
				F001 : ''
			}),
			displayField : 'label',
			valueField : 'value',
			labelWidth : 50,
			x : 240,
			y : 10
		});

		/* var f005 = Ext.create('Ext.form.ComboBox', {
			id : 'F005',
			renderer : "component",
			fieldLabel : '機台',
			store : ConditionStore(this.id, postvalue),
			displayField : 'label',
			valueField : 'value',
			labelWidth : 50,
			x : 10,
			y : 60
		}); 

		var f004 = Ext.create("DCI.LKB.Customer.OpenWinTrigger",
				{
					id : 'F004',
					fieldLabel : '機台類型',
					labelWidth : 80,
					x : 250,
					y : 60,
					triggerCls : 'x-form-search-trigger',
					multiSelect : true,
					displayColumns : [ [ 'eq_Property', '類型代碼' ],
							[ 'code_Name', '名稱' ] ],
					returnColumn : 'eq_Property',
					openWinDataUrl : '../../Funcs/LKB/LKB003.dsc',
					openWinParams : {
						DCITag : postvalue,
						action : 'F004'
					}
				});
		 */
		var f005 = Ext.create("DCI.LKB.Customer.OpenWinTrigger", {
			id : 'F005',
			fieldLabel : funclangs.equipment,
			labelWidth : 50,
			x : 10,
			y : 60,
			triggerCls : 'x-form-search-trigger',
			multiSelect : true,
			langobj : funclangs,
			displayColumns : [ [ 'EQ_ID', funclangs.equip_id ], [ 'EQ_Name', funclangs.equip_name ], [ 'WS_ID', funclangs.production_line_id ],
					[ 'WS_Name', funclangs.production_line_name ] ],
			returnColumn : 'EQ_ID;WS_ID',
			openWinDataUrl : '../../Funcs/LKB/LKB003.dsc',
			depComps : [ 'F001', 'F002' ],
			ouid : uid,
			openWinParams : {
				DCITag : postvalue,
				uid : uid,
				conn_id : kanbanInfos.conn_id,
				action : 'F005',
				F001 : '',
				F002 : ''
			}
		});

		var f006 = Ext.create("DCI.LKB.Customer.OpenWinTrigger", {
			id : 'F006',
			fieldLabel : funclangs.work_order,
			labelWidth : 50,
			x : 240,
			y : 60,
			triggerCls : 'x-form-search-trigger',
			multiSelect : true,
			displayColumns : [ [ 'Order_ID', funclangs.work_order ] ],
			returnColumn : 'Order_ID',
			openWinDataUrl : '../../Funcs/LKB/LKB003.dsc',
			depComps : [],
			ouid : uid,
			openWinParams : {
				DCITag : postvalue,
				uid : uid,
				conn_id : kanbanInfos.conn_id,
				action : 'F006'
			}
		});

		var f011 = Ext.create("DCI.LKB.Customer.OpenWinTrigger", {
			id : 'F011',
			fieldLabel : funclangs.process,
			labelWidth : 60,
			x : 450,
			y : 60,
			triggerCls : 'x-form-search-trigger',
			multiSelect : true,
			displayColumns : [ [ 'OP_ID', funclangs.process_id ], [ 'OP_Name', funclangs.prcess_name ], [ 'OP_Desc', funclangs.description ],
					[ 'WS_ID', funclangs.production_line_id ], [ 'WS_Name', funclangs.production_line_name ] ],
			returnColumn : 'OP_ID',
			openWinDataUrl : '../../Funcs/LKB/LKB003.dsc',
			depComps : [ 'F001', 'F002' ],
			ouid : uid,
			openWinParams : {
				DCITag : postvalue,
				uid : uid,
				conn_id : kanbanInfos.conn_id,
				action : 'F011',
				F001 : '',
				F002 : ''
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
			title : funclangs.query_results,
			gridid : 'LKB003-G01',
			border : 0,
			postvalue : postvalue,
			uid : uid
		});

		var bodyColumns = [ {
			text : '',
			dataIndex : 'seq',
			colid : 'colseq',
			width : 50
		}, {
			text : funclangs.work_order_id,
			dataIndex : 'order_ID',
			colid : 'colorder_ID',
			width : 120
		}, {
			text : funclangs.production_line,
			dataIndex : 'ws',
			colid : 'colws',
			width : 150
		}, {
			text : funclangs.process_id_name_sequ,
			dataIndex : 'op',
			colid : 'colop',
			width : 200
		}, {
			text : funclangs.equip_id,
			dataIndex : 'eq_ID',
			colid : 'coleq_ID',
			width : 120
		}, {
			text : funclangs.equip_name,
			dataIndex : 'eq_Name',
			colid : 'coleq_Name',
			width : 200
		}, {
			text : funclangs.time,
			dataIndex : 'out_Time',
			colid : 'colout_Time',
			width : 200
		}, {
			text : funclangs.arrived_qty,
			dataIndex : 'arrive_Qty',
			colid : 'colarrive_Qty'
		}, {
			text : funclangs.completed_qty,
			dataIndex : 'out_Qty',
			colid : 'colout_Qty'
		}, {
			text : funclangs.yield_rate,
			dataIndex : 'utility',
			colid : 'colutility'
		}, {
			text : funclangs.loss_qty,
			dataIndex : 'shrinkage_Qty',
			colid : 'colshrinkage_Qty'
		}, {
			text : funclangs.qty_surplus,
			dataIndex : 'surplus_Qty',
			colid : 'colsurplus_Qty'
		}, {
			text : funclangs.rework_qty,
			dataIndex : 'rework_Qty',
			colid : 'colrework_Qty'
		} ];

		resultGrid.initBody(gridstore, bodyColumns);

		var sumbit = Ext.create('Ext.Button', {
			text : funclangs.toolbar_query_title,
			renderer : "component",
			handler : function() {
				if (conditionArea != null) {
					resultGrid.setLoading(true);

					//alert(conditionArea.getComponent("F007").getRawValue());
					//gridstore.getProxy().extraParams = {
					queryStore.removeAll();
					gridstore.removeAll();
					seriesStore.removeAll();
					chartStore.removeAll();
					queryStore.getProxy().extraParams = {
						DCITag : postvalue,
						uid : uid,
						conn_id : kanbanInfos.conn_id,
						action : 'query',
						F001 : f001.getValue(),
						F002 : f002.getValue(),
						F003s : conditionArea.getComponent("F003s").getRawValue(),
						F003e : conditionArea.getComponent("F003e").getRawValue(),
						F005 : f005.getValue(),
						F006 : f006.getValue(),
						F007 : conditionArea.getComponent("F007").getRawValue(),
						F008 : conditionArea.getComponent("F008").getValue(),
						F009s : conditionArea.getComponent("F009s").getValue(),
						F009e : conditionArea.getComponent("F009e").getValue(),
						F011 : f011.getValue()
					};

					queryStore.load(function(records) {
						if (records[0].get("rows") != null) {
							gridstore.loadData(records[0].get("rows"));
						}
						resultGrid.setLoading(false);
					});
				}
			},
			x : 960,
			y : 60
		});

		var toexcel = Ext.create('Ext.Button', {
			text : 'excel',
			renderer : "component",
			handler : function() {
				var grid = resultGrid.getGrid();
				/* buildHtml(grid.columns, grid.getStore().getRange(0), "1", function(datas) {
					var downloadform = Ext.create('Ext.form.Panel', {
						standardSubmit : true,
						url : '../../Funcs/LKB/LKB003.dsc',
						method : 'POST'
					});

					downloadform.submit({
						target : '_blank',
						params : {
							DCITag : postvalue,
							conn_id : kanbanInfos.conn_id,
							action : 'excel',
							exceldatas : datas
						}
					});
				}); */

				var downloadform = Ext.create('Ext.form.Panel', {
					standardSubmit : true,
					url : '../../Funcs/LKB/LKB003.dsc',
					method : 'POST'
				});

				downloadform.submit({
					target : '_blank',
					params : {
						DCITag : postvalue,
						uid : uid,
						conn_id : kanbanInfos.conn_id,
						action : 'excel',
						cols : Ext.encode(buildColObj(grid.columns)),
						ctype : "1",
						F001 : f001.getValue(),
						F002 : f002.getValue(),
						F003s : conditionArea.getComponent("F003s").getRawValue(),
						F003e : conditionArea.getComponent("F003e").getRawValue(),
						F005 : f005.getValue(),
						F006 : f006.getValue(),
						F007 : conditionArea.getComponent("F007").getRawValue(),
						F008 : conditionArea.getComponent("F008").getValue(),
						F009s : conditionArea.getComponent("F009s").getValue(),
						F009e : conditionArea.getComponent("F009e").getValue(),
						F011 : f011.getValue()
					}
				});

			},
			x : 1000,
			y : 60
		});

		var tohtml = Ext.create('Ext.Button', {
			text : 'html',
			renderer : "component",
			handler : function() {
				var grid = resultGrid.getGrid();
				/* buildHtml(grid.columns, grid.getStore().getRange(0), "2", function(datas) {
					var downloadform = Ext.create('Ext.form.Panel', {
						standardSubmit : true,
						url : '../../Funcs/LKB/LKB003.dsc',
						method : 'POST'
					});

					downloadform.submit({
						target : '_blank',
						params : {
							DCITag : postvalue,
							conn_id : kanbanInfos.conn_id,
							action : 'html',
							exceldatas : datas
						}
					});
				}); */

				var downloadform = Ext.create('Ext.form.Panel', {
					standardSubmit : true,
					url : '../../Funcs/LKB/LKB003.dsc',
					method : 'POST'
				});

				downloadform.submit({
					target : '_blank',
					params : {
						DCITag : postvalue,
						uid : uid,
						conn_id : kanbanInfos.conn_id,
						action : 'html',
						cols : Ext.encode(buildColObj(grid.columns)),
						ctype : "2",
						F001 : f001.getValue(),
						F002 : f002.getValue(),
						F003s : conditionArea.getComponent("F003s").getRawValue(),
						F003e : conditionArea.getComponent("F003e").getRawValue(),
						F005 : f005.getValue(),
						F006 : f006.getValue(),
						F007 : conditionArea.getComponent("F007").getRawValue(),
						F008 : conditionArea.getComponent("F008").getValue(),
						F009s : conditionArea.getComponent("F009s").getValue(),
						F009e : conditionArea.getComponent("F009e").getValue(),
						F011 : f011.getValue()
					}
				});
			},
			x : 1045,
			y : 60
		});

		conditionArea = Ext.create('Ext.Panel', {
			renderer : "component",
			region : 'north',
			height : 120,
			border : 0,
			layout : 'absolute',
			items : [ {
				xtype : 'datefield',
				id : 'F003s',
				format : 'Y/m/d H:i:s',
				altFormats : 'Y/m/d H:i:s',
				fieldLabel : funclangs.date_range + ':',
				width : 255,
				labelWidth : 70,
				x : 450,
				y : 10
			}, {
				xtype : 'datefield',
				id : 'F003e',
				format : 'Y/m/d H:i:s',
				altFormats : 'Y/m/d H:i:s',
				fieldLabel : ' ~ ',
				width : 175,
				labelWidth : 20,
				labelSeparator : ' ',
				x : 720,
				y : 10
			}, {
				xtype : 'textfield',
				id : 'F009s',
				fieldLabel : funclangs.yield_range + '(%)',
				width : 115,
				labelWidth : 80,
				value : 0,
				x : 680,
				y : 60,
				msgTarget : 'under',
				regex : /^([0-9]+){0,1}(?:\.[0-9]+){0,1}$/,
				regexText : funclangs.only_numeric_allowed,
				validateOnBlur : true,
				validator : function(value) {
					if (isNaN(value) || value == null || value == "") {
						return funclangs.range_non_nullable;
					} else {
						return true;
					}
				}
			}, {
				xtype : 'textfield',
				id : 'F009e',
				fieldLabel : ' ~ ',
				width : 55,
				labelWidth : 20,
				labelSeparator : ' ',
				value : 100,
				x : 810,
				y : 60,
				msgTarget : 'under',
				regex : /^([0-9]+){0,1}(?:\.[0-9]+){0,1}$/,
				regexText : funclangs.only_numeric_allowed,
				validateOnBlur : true,
				validator : function(value) {
					if (isNaN(value) || value == null || value == "") {
						return funclangs.range_non_nullable;
					} else {
						return true;
					}
				}
			}, {
				xtype : 'checkboxfield',
				boxLabel : funclangs.exception_only,
				name : 'topping',
				inputValue : '1',
				id : 'F008',
				x : 880,
				y : 60
			}, {
				xtype : 'timefield',
				id : 'F007',
				format : 'H:i',
				fieldLabel : funclangs.day_start_time,
				hideTrigger : false,
				labelWidth : 100,
				width : 180,
				value : '08:00',
				x : 920,
				y : 10
			}, f001, f002, f005, f006, f011, sumbit, toexcel, tohtml ]
		});

		var chartModel = Ext.define('chartModel', {
			extend : 'Ext.data.Model'
		});

		var chartStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			model : chartModel,
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var chart = Ext.create('Ext.chart.Chart', {
			x : 0,
			y : 0,
			//width : mainPanel.getWidth() * 0.9,
			//height : (viewHeight - LKB003Panel.getTabBar().getHeight()) * 0.9,
			style : 'background:#fff;',
			animate : true,
			store : chartStore,
			shadow : true,
			theme : 'Category1',
			legend : {
				position : 'right'
			},
			axes : [ {
				type : 'Numeric',
				minimum : 0,
				maximum : 100,
				position : 'left',
				fields : chartfields,
				majorTickSteps : 4,
				minorTickSteps : 1,
				label : {
					renderer : function(v) {
						return v + '%';
					}
				},
				grid : {
					odd : {
						opacity : 1,
						fill : '#ddd',
						stroke : '#bbb',
						'stroke-width' : 1
					}
				}
			}, {
				type : 'Category',
				position : 'bottom',
				fields : [ 'date' ]
			} ]
		});

		f001.getStore().load(function(records) {
			if (records.length > 0) {
				f001.setValue(records[0].get("value"));
				f002.getStore().getProxy().extraParams = {
					DCITag : postvalue,
					uid : uid,
					conn_id : kanbanInfos.conn_id,
					action : 'F002',
					F001 : records[0].get("value")
				};
			}
		});

		var tab1 = Ext.create('Ext.Panel', {
			id : 'LKB003Q',
			title : funclangs.toolbar_query_title,
			closable : false,
			layout : 'border',
			//height : viewHeight - LKB003Panel.getTabBar().getHeight(),
			//width : mainPanel.getWidth(),
			items : [ conditionArea, resultGrid ]
		});

		var tab2 = Ext.create('Ext.Panel', {
			id : 'LKB003C',
			title : funclangs.equip_yield_trend_chart,
			closable : false,
			//height : viewHeight - LKB003Panel.getTabBar().getHeight(),
			//width : mainPanel.getWidth(),
			layout : 'fit',
			//layout : 'absolute',
			items : chart
		});

		LKB003Panel.add(tab1);
		LKB003Panel.add(tab2);
		LKB003Panel.setActiveTab('LKB003Q');
		//LKB003Panel.widthChangeControls = [ conditionArea, resultGrid ];

		LKB003Panel.resize(Ext.get("LKB003Page").getWidth(), Ext.get("LKB003Page").getHeight());
	}
</script>

</head>
<body>
	<div id="LKB003Page" class="page_div"></div>
	<!-- <form action="../../Funcs/LKB/LKB003.do" method="POST" id="download03">
		<input type="hidden" id="action03" name="action" /> <input
			type="hidden" id="tag03" name="DCITag" /> <input type="hidden"
			id="data03" name="exceldatas" />
	</form> -->
</body>
</html>