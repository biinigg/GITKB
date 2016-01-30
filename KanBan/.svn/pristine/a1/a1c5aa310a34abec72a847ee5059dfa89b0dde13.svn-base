<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title>LKB0001</title>
<style>
#eqList-view .x-panel-body {
	background: white;
	font: 11px Arial, Helvetica, sans-serif;
}

#eqList-view .thumb-wrap {
	/* float: left; */
	margin: 4px;
	margin-right: 0;
	padding: 0px;
}

#eqList-view .x-item-over {
	border: 1px solid #dddddd;
	background: #efefef
		url(./extjs/resources/ext-theme-classic/images/view/over.gif) repeat-x
		left top;
	padding: 4px;
}

#eqList-view .x-item-selected {
	background: #eff5fb
		url(./extjs/resources/ext-theme-classic/images/view/selected.gif)
		no-repeat right bottom;
	border: 1px solid #99bbe8;
	padding: 4px;
}

#eqList-view .x-item-selected .thumb {
	background: transparent;
}

#eqList-view .loading-indicator {
	font-size: 11px;
	background-image:
		url('../../resources/themes/images/default/grid/loading.gif');
	background-repeat: no-repeat;
	background-position: left;
	padding-left: 20px;
	margin: 10px;
}

#eqList-view .eq-head {
	background: #eff5fb;
	border: 1px solid #99bbe8;
	padding: 4px;
	text-align: center;
	height: 50px;
}

.x-view-selector {
	position: absolute;
	left: 0;
	top: 0;
	width: 0;
	border: 1px dotted;
	opacity: .5;
	-moz-opacity: .5;
	filter: alpha(opacity =                      
		                                                         
		                        50);
	zoom: 1;
	background-color: #c3daf9;
	border-color: #3399bb;
}

.ext-strict .ext-ie .x-tree .x-panel-bwrap {
	position: relative;
	overflow: hidden;
}
</style>

<script type="text/javascript" charset="UTF-8">
	Ext.onReady(function() {
		var localKeys = [ "site_id", "production_line", "equipment", "equip_id", "equip_name", "production_line_id", "production_line_name", "start_time_expired",
				"one_day_equip_utilization", "three_days_equip_utilization", "toolbar_query_title", "start_date", "days", "equip_qty", "update_time", "select_equip" ];
		var keys = localKeys.concat(globeKeys);
		var uid = '<dcitag:reqParam paramName="uid"></dcitag:reqParam>';
		this.dcistore.setMultiLangKeys(keys);
		this.dcistore.setUid(uid);
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				var langs = buildMultiLangObjct(keys, records[0].get('langValues'));
				var infos = new Object();
				infos["func_id"] = '<dcitag:reqParam paramName="func_id"></dcitag:reqParam>';
				infos["func_name"] = '<dcitag:reqParam paramName="func_name"></dcitag:reqParam>';
				infos["conn_id"] = '<dcitag:reqParam paramName="conn_id"></dcitag:reqParam>';
				infos["relation_filter"] = '<dcitag:reqParam paramName="filter"></dcitag:reqParam>';
				infos["can_edit"] = '<dcitag:reqParam paramName="canEdit"></dcitag:reqParam>';

				//var funclangs = buildMultiLangObjct(keys, records[0].get('langValues'));
				LKB0001(infos, records[0].get('dcitagValue'), langs, uid);
			}
		});
	});

	function LKB0001(kanbanInfos, postvalue, langs, uid) {
		var conditionArea = null;
		var selectedeq = Ext.create('Ext.form.Label', {
			text : '',
			width : 160,
			margin : '0 0 0 10',
			x : 75,
			y : 5
		});

		var eqlistStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "eqid", "title", "pic1", "pic2", "timegap", "EQ_Time", "utility1", "utility3", "chartData" ],
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var chart1Store = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "date", "value" ],
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var chart2Store = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "date", "value" ],
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var statusStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ "status", "eqcnt", "percent", "statuscolor" ],
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		});

		var queryStore = Ext.create('Ext.data.Store', {
			id : 'queryStore',
			autoLoad : false,
			fields : [ "eqlist", "status", "chart1" ],
			proxy : {
				type : 'ajax',
				url : '../../Funcs/LKB/LKB001.dsc',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json'
				}
			},
			listeners : {
				load : function(store, records, successful, eOpts) {
					//alert(successful);
					if (successful) {
						//alert(records[0].get("eqlist"));
						eqlistStore.loadData(records[0].get("eqlist"));
						chart1Store.loadData(records[0].get("chart1"));
						statusStore.loadData(records[0].get("status"));
						totaleqs.setText(eqlistStore.getCount());
						//alert(eqlistStore.getCount());
					}
					chartArea1.setLoading(false);
					eqList.setLoading(false);
				}
			}

		});

		var f001 = Ext.create('Ext.form.ComboBox', {
			renderer : "component",
			fieldLabel : langs.site_id,
			editable : false,
			store : BuildComboboxStore('../../Funcs/LKB/LKB001.dsc', {
				DCITag : postvalue,
				uid : uid,
				action : 'F001',
				conn_id : kanbanInfos.conn_id
			}),
			displayField : 'label',
			valueField : 'value',
			labelWidth : 50,
			x : 10,
			y : 18,
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
			renderer : "component",
			editable : false,
			fieldLabel : langs.production_line,
			store : BuildComboboxStore('../../Funcs/LKB/LKB001.dsc', {
				DCITag : postvalue,
				uid : uid,
				conn_id : kanbanInfos.conn_id,
				action : 'F002',
				F001 : ''
			}),
			displayField : 'label',
			valueField : 'value',
			labelWidth : 50,
			x : 230,
			y : 18
		});

		var f008 = Ext.create("DCI.LKB.Customer.OpenWinTrigger", {
			id : 'F008_01',
			fieldLabel : langs.equipment,
			labelWidth : 50,
			width : 250,
			x : 570,
			y : 18,
			triggerCls : 'x-form-search-trigger',
			multiSelect : true,
			displayColumns : [ [ 'EQ_ID', langs.equip_id ], [ 'EQ_Name', langs.equip_name ], [ 'WS_ID', langs.production_line_id ], [ 'WS_Name', langs.production_line_name ] ],
			returnColumn : 'EQ_ID;WS_ID',
			openWinDataUrl : '../../Funcs/LKB/LKB001.dsc',
			depComps : [ 'F001_01', 'F002_01' ],
			ouid : uid,
			openWinParams : {
				DCITag : postvalue,
				uid : uid,
				action : 'F008',
				F001 : '',
				F002 : '',
				conn_id : kanbanInfos.conn_id
			}
		});

		var tplStr1 = '<tr><td colspan="2">{EQ_Time}' + langs.start_time_expired + '</td></tr>';
		var tplStr2 = '<tr><td colspan="2">' + langs.one_day_equip_utilization + '：{utility1}</td></tr>';
		var tplStr3 = '<tr><td colspan="2">' + langs.three_days_equip_utilization + '：{utility3}</td></tr>';
		var tpl01 = Ext.create('Ext.view.View', {
			store : eqlistStore,
			tpl : [ '<table border="1" height="100%" border="0" cellpadding="0" cellspacing="0">', '<tr><tpl for=".">', '<td class="thumb-wrap" id="{eqid}">',
					'<table width="200px" height="100%" cellpadding="0" cellspacing="0" ><tr>', '<td class="eq-head" colspan="2" >{title}</td></tr>',
					'<tr><td width="60%"><img src="{pic1}" height = "100px" width="120px"/></td>', '<td align="center"><img src="{pic2}" height = "50px" width="50px"/></td></tr>',
					tplStr1, tplStr2, tplStr3, '</table></td></tpl></tr></table>' ],
			multiSelect : false,
			height : '100%',
			trackOver : true,
			overItemCls : 'x-item-over',
			itemSelector : 'td.thumb-wrap',
			listeners : {
				selectionchange : function(dv, selected, eOpts) {
					if (selected.length == 0) {
						selectedeq.setText("");
						chart2Store.loadData([]);
					} else {
						selectedeq.setText(selected[0].get("title"));
						chart2Store.loadData(selected[0].get("chartData"));
					}
				}
			}
		});

		var tpl02 = Ext.create('Ext.view.View', {
			store : statusStore,
			tpl : [ '<table height="100%" border="0" cellpadding="0" cellspacing="0" margin="0" ><tr><tpl for=".">', '<td>',
					'<table width="200px" height="100%" border="0" cellpadding="0" cellspacing="0" ', 'style="padding-left : 10px ;background-color:{statuscolor}"><tr>',
					'<td valign="middle">{status}：{eqcnt}({percent})</td></tr>', '</table></td></tpl></tr></table>' ],
			multiSelect : false,
			height : '100%',
			trackOver : true,
			overItemCls : 'x-item-over',
			itemSelector : 'td.thumb-wrap'
		});

		var eqList = Ext.create('Ext.Panel', {
			id : 'eqList-view',
			//id : 'images-view',
			region : 'center',
			autoScroll : true,
			frame : true,
			collapsible : false,
			width : 800,
			items : tpl01
		});

		var updatetime = Ext.create('Ext.form.Label', {
			text : '',
			margin : '0 0 0 10',
			x : 965,
			y : 5
		});

		var totaleqs = Ext.create('Ext.form.Label', {
			text : '0',
			margin : '0 0 0 10',
			x : 75,
			y : 5
		});

		var chartArea1 = Ext.create('Ext.Panel', {
			region : 'center',
			border : 0,
			layout : 'fit',
			items : Ext.create('Ext.chart.Chart', {
				style : 'background:#fff;',
				animate : true,
				store : chart1Store,
				shadow : true,
				theme : 'Category1',
				axes : [ {
					type : 'Numeric',
					minimum : 0,
					maximum : 100,
					position : 'left',
					fields : [ 'value' ],
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
				} ],
				series : [ {
					type : 'line',
					axis : 'left',
					xField : 'date',
					yField : 'value',
					highlight : {
						size : 7,
						radius : 7
					},
					label : {
						field : 'value',
						display : 'under',
						contrast : true,
						font : '18px Arial',
						padding : 3,
						renderer : function(value, label, storeItem, item, i, display, animate, index) {
							return value + "%";
						}
					}
				} ]
			})
		});

		var chartArea2 = Ext.create('Ext.Panel', {
			region : 'center',
			border : 0,
			layout : 'fit',
			items : Ext.create('Ext.chart.Chart', {
				style : 'background:#fff',
				animate : true,
				store : chart2Store,
				shadow : true,
				theme : 'Category1',
				axes : [ {
					type : 'Numeric',
					minimum : 0,
					maximum : 100,
					position : 'left',
					fields : [ 'value' ],
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
				} ],
				series : [ {
					type : 'line',
					axis : 'left',
					xField : 'date',
					yField : 'value',
					highlight : {
						size : 7,
						radius : 7
					},
					label : {
						field : 'value',
						display : 'under',
						contrast : true,
						font : '18px Arial',
						renderer : function(value, label, storeItem, item, i, display, animate, index) {
							return value + "%";
						}
					}
				} ]
			})
		});

		var submitBtn = Ext.create('Ext.Button', {
			text : langs.toolbar_query_title,
			renderer : "component",
			handler : function() {
				chartArea1.setLoading(true);
				eqList.setLoading(true);
				//testGridstore.load();
				if (conditionArea != null) {
					totaleqs.setText("");
					updatetime.setText(getCurrentDateTime());
					//alert(updatetime.text);
					queryStore.getProxy().extraParams = {
						DCITag : postvalue,
						uid : uid,
						conn_id : kanbanInfos.conn_id,
						action : 'query',
						F001 : f001.getValue(),
						F002 : f002.getValue(),
						F003 : conditionArea.items.get(0).getValue(),
						F005 : conditionArea.items.get(1).getRawValue(),
						F008 : f008.getValue(),
						currtime : updatetime.text
					};
					queryStore.load();
				}
			},
			x : 1020,
			y : 18
		});

		conditionArea = Ext.create('Ext.Panel', {
			region : 'north',
			renderer : "component",
			bodyStyle : {
				background : '#fff'
			},
			height : 60,
			x : 10,
			y : 10,
			width : 1100,
			border : 5,
			layout : 'absolute',
			items : [ {
				xtype : 'numberfield',
				fieldLabel : langs.days,
				value : 7,
				minValue : 3,
				maxValue : 14,
				labelWidth : 50,
				width : 100,
				x : 450,
				y : 18
			}, {
				xtype : 'timefield',
				format : 'H:i',
				fieldLabel : langs.start_date,
				labelWidth : 70,
				width : 150,
				value : '08:00',
				x : 850,
				y : 18
			}, f001, f002, f008, submitBtn ]
		});

		var p1 = Ext.create('Ext.panel.Panel', {
			region : 'center',
			layout : 'border',
			height : 230,
			x : 10,
			y : 80,
			width : 1100,
			items : [ {
				region : 'north',
				border : 0,
				height : 60,
				split : false,
				collapsible : false,
				animCollapse : true,
				//layout : 'absolute',
				layout : 'border',
				items : [ {
					region : 'north',
					border : 0,
					height : 23,
					split : false,
					collapsible : false,
					animCollapse : true,
					layout : 'absolute',
					items : [ {
						xtype : 'label',
						text : langs.equip_qty + '：',
						width : 60,
						margin : '0 0 0 10',
						x : 5,
						y : 5
					}, totaleqs, {
						xtype : 'label',
						text : langs.update_time + '：',
						width : 60,
						margin : '0 0 0 10',
						x : 895,
						y : 5
					}, updatetime ]
				}, {
					region : 'center',
					autoScroll : true,
					frame : true,
					border : 0,
					collapsible : false,
					width : '100%',
					items : tpl02,
					bodyStyle : {
						background : '#ffffff'
					}
				} ]
			}, chartArea1 ]
		});

		var p2 = Ext.create('Ext.panel.Panel', {
			region : 'south',
			layout : 'border',
			border : 0,
			height : 260,
			x : 10,
			y : 320,
			width : 1100,
			items : [ {
				region : 'west',
				width : 300,
				minWidth : 300,
				maxWidth : 300,
				split : false,
				collapsible : false,
				animCollapse : true,
				layout : 'border',
				items : [ {
					region : 'north',
					border : 0,
					height : 30,
					collapsible : false,
					animCollapse : true,
					split : false,
					layout : 'absolute',
					items : [ {
						xtype : 'label',
						text : langs.select_equip + '：',
						width : 60,
						margin : '0 0 0 10',
						x : 5,
						y : 5
					}, selectedeq ]
				}, chartArea2 ]
			}, eqList ]
		});

		var main = Ext.create('DCI.Customer.SubPanel', {
			id : 'LKB001Main',
			renderTo : 'LKB001Page',
			border : 0,
			minWidth : 1120,
			bodyPadding : '0 5 5 5',
			minHeight : 520,
			layout : 'border',
			widthChangeControls : [ conditionArea, p1, p2 ],
			items : [ conditionArea, p1, p2 ],
			bodyStyle : {
				background : 'lightblue',
				padding : '0px',
				margin : '0px'
			}
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
			}
		});

		main.resize(Ext.get("LKB001Page").getWidth(), Ext.get("LKB001Page").getHeight());
	}
</script>
</head>
<body>
	<div id="LKB001Page" class="page_div"></div>
</body>
</html>