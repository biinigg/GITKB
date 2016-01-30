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
				"load_def_confirm_title", "load_def_confirm_msg", "cus_format", 'site_id', 'production_line', 'abnormal_description', 'equip_status', 'equip_id', 'equip_name',
				'production_line_id', 'production_line_name', 'query_results', 'time', 'remark', 'toolbar_query_title', 'date_range', 'exception_only',
				'equip_abnormal_statistical_chart', 'equip_status_statistical_chart' ];
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
				LKB002(infos, funclangs, records[0].get('dcitagValue'), uid);
			}
		});
	});

	function LKB002(kanbanInfos, funclangs, postvalue, uid) {
		var resultGrid = null;
		var conditionArea = null;

		var LKB002Panel = Ext.create('DCI.Customer.SubTabPanel', {
			id : 'LKB002Main',
			minWidth : 980,
			deferredRender : false,
			renderTo : 'LKB002Page',
			widthChangeControls : [ conditionArea, resultGrid ]
		});

		Ext.define('gridModel', {
			extend : 'Ext.data.Model',
			fields : [ {
				type : 'string',
				name : 'seq'
			}, {
				type : 'string',
				name : 'ws'
			}, {
				type : 'string',
				name : 'eq_ID'
			}, {
				type : 'string',
				name : 'eq_Name'
			}, {
				type : 'string',
				name : 'start_Time'
			}, {
				type : 'string',
				name : 'eq_STATUS_NAME'
			}, {
				type : 'string',
				name : 'eq_REASON_NAME'
			}, {
				type : 'string',
				name : 'remark'
			}, {
				type : 'string',
				name : 'code_Color'
			} ]
		});

		var f001 = Ext.create('Ext.form.ComboBox', {
			id : 'F001_02',
			renderer : "component",
			fieldLabel : funclangs.site_id,
			store : BuildComboboxStore('../../Funcs/LKB/LKB002.dsc', {
				DCITag : postvalue,
				uid : uid,
				conn_id : kanbanInfos.conn_id,
				action : 'F001'
			}),
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			x : 10,
			y : 10,
			listeners : {
				change : function(combobox, newValue, oldValue, eOpts) {
					if (typeof f002 !== 'undefined' && f002 != null) {
						f002.getStore().getProxy().extraParams['F001'] = newValue;
						f002.getStore().load();
						f002.clearValue();
					}
					if (typeof f005 !== 'undefined' && f005 != null) {
						f005.getStore().getProxy().extraParams['F001'] = newValue;
						f005.getStore().load();
						f005.clearValue();
					}
					if (typeof f009 !== 'undefined' && f009 != null) {
						f009.getStore().getProxy().extraParams['F001'] = newValue;
						f009.getStore().load();
						f009.clearValue();
					}
				}
			}
		});

		var f002 = Ext.create('Ext.form.ComboBox', {
			id : 'F002_02',
			renderer : "component",
			fieldLabel : funclangs.production_line,
			store : BuildComboboxStore('../../Funcs/LKB/LKB002.dsc', {
				DCITag : postvalue,
				uid : uid,
				conn_id : kanbanInfos.conn_id,
				action : 'F002',
				F001 : ''
			}),
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			x : 250,
			y : 10,
			listeners : {
				change : function(combobox, newValue, oldValue, eOpts) {
					if (typeof f009 !== 'undefined' && f009 != null) {
						f009.getStore().getProxy().extraParams['F002'] = newValue;
						f009.getStore().load();
						f009.clearValue();
					}
				}
			}
		});

		var f005 = Ext.create('Ext.form.ComboBox', {
			id : 'F005_02',
			renderer : "component",
			fieldLabel : funclangs.abnormal_description,
			store : BuildComboboxStore('../../Funcs/LKB/LKB002.dsc', {
				DCITag : postvalue,
				uid : uid,
				conn_id : kanbanInfos.conn_id,
				action : 'F005',
				F001 : ''
			}),
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			x : 480,
			y : 60
		});

		var f009 = Ext.create('Ext.form.ComboBox', {
			id : 'F009_02',
			renderer : "component",
			fieldLabel : funclangs.equip_status,
			store : BuildComboboxStore('../../Funcs/LKB/LKB002.dsc', {
				DCITag : postvalue,
				uid : uid,
				conn_id : kanbanInfos.conn_id,
				action : 'F009',
				F001 : '',
				F002 : ''
			}),
			displayField : 'label',
			valueField : 'value',
			labelWidth : 60,
			x : 250,
			y : 60
		});

		var f008 = Ext.create("DCI.LKB.Customer.OpenWinTrigger", {
			id : 'F008_02',
			fieldLabel : funclangs.equip_id,
			labelWidth : 60,
			x : 10,
			y : 60,
			triggerCls : 'x-form-search-trigger',
			multiSelect : true,
			displayColumns : [ [ 'EQ_ID', funclangs.equip_id ], [ 'EQ_Name', funclangs.equip_name ], [ 'WS_ID', funclangs.production_line_id ],
					[ 'WS_Name', funclangs.production_line_name ] ],
			returnColumn : 'EQ_ID;WS_ID',
			openWinDataUrl : '../../Funcs/LKB/LKB002.dsc',
			depComps : [ 'F001_02', 'F002_02' ],
			ouid : uid,
			openWinParams : {
				DCITag : postvalue,
				uid : uid,
				conn_id : kanbanInfos.conn_id,
				action : 'F008',
				F001 : '',
				F002 : ''
			}
		});

		var gridstore = new Ext.data.Store({
			id : 'gridStore',
			model : 'gridModel',
			autoLoad : false,
			proxy : {
				type : 'ajax',
				url : '../../Funcs/LKB/LKB002.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json',
					root : 'rows'
				}
			}
		});

		resultGrid = Ext.create('DCI.Customer.ViewGridPanel', {
			languageObj : funclangs,
			title : funclangs.query_results,
			gridid : 'LKB002-G01',
			border : 0,
			postvalue : postvalue,
			uid : uid,
			x : 10,
			y : 145,
		});

		var bodyColumns = [ {
			text : '',
			dataIndex : 'seq',
			colid : 'colseq',
			width : 50
		}, {
			text : funclangs.production_line,
			dataIndex : 'ws',
			colid : 'colws',
			width : 150
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
			dataIndex : 'start_Time',
			colid : 'colstart_Time',
			width : 200
		}, {
			text : funclangs.equip_status,
			dataIndex : 'eq_STATUS_NAME',
			colid : 'coleq_STATUS_NAME',
			renderer : function(value, metaData, record, rowIndex, colIndex, store) {
				metaData.style = "background-color:" + record.get('code_Color') + ";";
				return value;
			}

		}, {
			text : funclangs.abnormal_description,
			dataIndex : 'eq_REASON_NAME',
			colid : 'coleq_REASON_NAME',
		}, {
			text : funclangs.remark,
			dataIndex : 'remark',
			colid : 'colremark',
		} ];

		resultGrid.initBody(gridstore, bodyColumns);

		var sumbitbtn = Ext.create('Ext.Button', {
			text : funclangs.toolbar_query_title,
			renderer : "component",
			handler : function() {
				if (conditionArea != null) {
					gridstore.getProxy().extraParams = {
						DCITag : postvalue,
						uid : uid,
						conn_id : kanbanInfos.conn_id,
						action : 'query',
						F001 : f001.getValue(),
						F002 : f002.getValue(),
						F003s : conditionArea.getComponent("F003s_02").getRawValue(),
						F003e : conditionArea.getComponent("F003e_02").getRawValue(),
						F005 : f005.getValue(),
						F006 : conditionArea.getComponent("F006_02").getValue(),
						F008 : f008.getValue(),
						F009 : f009.getValue()
					};

					gridstore.load();

					var chartPanel = Ext.getCmp("LKB002C");
					if (chartPanel != null) {
						chartPanel.removeAll();
						chartPanel.insert(0, createPieChart_LKB2(postvalue, uid, f001.getValue(), f002.getValue(), conditionArea.getComponent("F003s_02").getValue(), conditionArea
								.getComponent("F003e_02").getValue(), f005.getValue(), f008.getValue(), f009.getValue(), "1", kanbanInfos));
					}
					var chartPanel2 = Ext.getCmp("LKB002C2");
					if (chartPanel2 != null) {
						chartPanel2.removeAll();
						chartPanel2.insert(0, createPieChart_LKB2(postvalue, uid, f001.getValue(), f002.getValue(), conditionArea.getComponent("F003s_02").getValue(),
								conditionArea.getComponent("F003e_02").getValue(), f005.getValue(), f008.getValue(), f009.getValue(), "2", kanbanInfos));
					}
				}
			},
			x : 820,
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
						url : '../../Funcs/LKB/LKB002.dsc',
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
					url : '../../Funcs/LKB/LKB002.dsc',
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
						F003s : conditionArea.getComponent("F003s_02").getRawValue(),
						F003e : conditionArea.getComponent("F003e_02").getRawValue(),
						F005 : f005.getValue(),
						F006 : conditionArea.getComponent("F006_02").getValue(),
						F008 : f008.getValue(),
						F009 : f009.getValue()
					}
				});
			},
			x : 865,
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
						url : '../../Funcs/LKB/LKB002.dsc',
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
					url : '../../Funcs/LKB/LKB002.dsc',
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
						F003s : conditionArea.getComponent("F003s_02").getRawValue(),
						F003e : conditionArea.getComponent("F003e_02").getRawValue(),
						F005 : f005.getValue(),
						F006 : conditionArea.getComponent("F006_02").getValue(),
						F008 : f008.getValue(),
						F009 : f009.getValue()
					}
				});

			},
			x : 910,
			y : 60
		});

		conditionArea = Ext.create('Ext.Panel', {
			renderer : "component",
			region : 'north',
			height : 110,
			border : 0,
			x : 10,
			y : 10,
			border : 5,
			layout : 'absolute',
			items : [ {
				xtype : 'datefield',
				id : 'F003s_02',
				format : 'Y/m/d H:i:s',
				altFormats : 'Y/m/d H:i:s',
				fieldLabel : funclangs.date_range + ':',
				width : 215,
				labelWidth : 60,
				x : 480,
				y : 10
			}, {
				xtype : 'datefield',
				id : 'F003e_02',
				format : 'Y/m/d H:i:s',
				altFormats : 'Y/m/d H:i:s',
				fieldLabel : ' ~ ',
				width : 175,
				labelWidth : 20,
				labelSeparator : ' ',
				x : 710,
				y : 10
			}, {
				xtype : 'checkboxfield',
				boxLabel : funclangs.exception_only,
				name : 'topping',
				inputValue : '1',
				id : 'F006_02',
				x : 735,
				y : 60
			}, f001, f002, f005, f008, f009, sumbitbtn, toexcel, tohtml ]
		});

		f001.getStore().load(function(records) {
			if (records.length > 0) {
				f001.setValue(records[0].get("value"));
				if (f002.getProxy().extraParams != null) {
					if (f002.getProxy().extraParams['F001'] == null) {
						f002.getProxy().extraParams.push({
							F001 : records[0].get("value")
						});
					} else {
						f002.getProxy().extraParams['F001'] = records[0].get("value");
					}
				}
				if (f005.getProxy().extraParams != null) {
					if (f005.getProxy().extraParams['F001'] == null) {
						f005.getProxy().extraParams.push({
							F001 : records[0].get("value")
						});
					} else {
						f005.getProxy().extraParams['F001'] = records[0].get("value");
					}
				}
				if (f009.getProxy().extraParams != null) {
					if (f009.getProxy().extraParams['F001'] == null) {
						f009.getProxy().extraParams.push({
							F001 : records[0].get("value")
						});
					} else {
						f009.getProxy().extraParams['F001'] = records[0].get("value");
					}
				}
			}
		});

		var tab1 = Ext.create('Ext.Panel', {
			id : 'LKB002Q',
			title : funclangs.toolbar_query_title,
			border : 0,
			closable : false,
			layout : 'border',
			items : [ conditionArea, resultGrid ]
		});

		var tab2 = Ext.create('Ext.Panel', {
			id : 'LKB002C',
			title : funclangs.equip_abnormal_statistical_chart,
			closable : false,
			layout : 'fit'
		});

		var tab3 = Ext.create('Ext.Panel', {
			id : 'LKB002C2',
			title : funclangs.equip_status_statistical_chart,
			closable : false,
			layout : 'fit'
		});

		LKB002Panel.add(tab1);
		LKB002Panel.add(tab2);
		LKB002Panel.add(tab3);
		LKB002Panel.setActiveTab('LKB002Q');
		//LKB002Panel.widthChangeControls = [ conditionArea, resultGrid ];

		LKB002Panel.resize(Ext.get("LKB002Page").getWidth(), Ext.get("LKB002Page").getHeight());
	}

	function createPieChart_LKB2(postvalue, uid, f001, f002, f003s, f003e, f005, f008, f009, charttype, kanbanInfos) {
		//alert(f001);

		var chart = null;

		Ext.define('chartModel', {
			extend : 'Ext.data.Model',
			fields : [ 'name', 'value', 'percent' ]
		});

		var chartStore = new Ext.data.Store({
			model : 'chartModel',
			autoLoad : false,
			proxy : {
				type : 'ajax',
				url : '../../Funcs/LKB/LKB002.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json',
					root : 'datas'
				},
				extraParams : {
					DCITag : postvalue,
					uid : uid,
					conn_id : kanbanInfos.conn_id,
					action : 'chart',
					F001 : f001,
					F002 : f002,
					F003s : f003s,
					F003e : f003e,
					F005 : f005,
					F008 : f008,
					F009 : f009
				}
			}
		});

		if (charttype == "2") {
			chartStore.getProxy().extraParams["action"] = "chart2";
		} else {
			chartStore.getProxy().extraParams["action"] = "chart";
		}

		chart = Ext.create('Ext.chart.Chart', {
			xtype : 'chart',
			animate : true,
			store : chartStore,
			shadow : true,
			legend : {
				position : 'right',
				labelFont : '35px Helvetica, sans-serif'
			},
			insetPadding : 60,
			theme : 'Base:gradients',
			series : [ {
				type : 'pie',
				field : 'value',
				showInLegend : true,
				donut : false,
				/* highlight : {
					segment : {
						margin : 20
					}
				}, */
				label : {
					field : 'name',
					display : 'rotate',
					contrast : true,
					font : '18px Arial',
					renderer : function(value, label, storeItem, item, i, display, animate, index) {
						return storeItem.get("percent") + "%(" + storeItem.get("value") + ")";
					}
				}
			} ]
		});

		if (f001 != null) {
			chart.store.load();
		}
		//alert("6");
		return chart;
	}
</script>
</head>
<body>
	<div id="LKB002Page" class="page_div"></div>
	<!-- <form action="../../Funcs/LKB/LKB002.dsc" method="POST" id="download02">
		<input type="hidden" id="action02" name="action" /> <input
			type="hidden" id="tag02" name="DCITag" /> <input type="hidden"
			id="data02" name="exceldatas" />
	</form> -->
</body>
</html>