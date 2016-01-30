<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title><dcitag:reqParam paramName="func_name"></dcitag:reqParam></title>
<link rel="stylesheet" type="text/css" href="./../../codemirror/css/codemirror.css" />
<script type="text/javascript" src="./../../codemirror/mode/sql.js"></script>

<script type="text/javascript" charset="UTF-8">
	Ext.onReady(function() {
		var localKeys = [ "save_msg", "delete_msg", "delete_fail", "delete_success", "delete_result_title", "delete_fail", "save_fail", "save_success", "save_result_title",
				"save_fail", "delete_confirm_title", "delete_confirm_msg", "system_build", "marquee_id", "marquee_type", "marquee_msg", "start_date", "end_date", "conns", "sqls",
				"sql_name", "conn_name", "marquee_type", "sql_msg", "text_msg", "start_date", "end_date", "toolbar_query_title", "confirm_title", "data_lose_warning",
				"not_allow_blank", "sql_id", "conn_id", "select_all", "sys_sql_msg", "sys_text_msg", "cus_format" ];
		var keys = localKeys.concat(globeKeys);
		var uid = '<dcitag:reqParam paramName="uid"></dcitag:reqParam>';
		this.dcistore.setMultiLangKeys(keys);
		this.dcistore.setUid(uid);
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				var langs = buildMultiLangObjct(keys, records[0].get('langValues'));
				showPage(records[0].get('dcitagValue'), langs, '<dcitag:reqParam paramName="canEdit"></dcitag:reqParam>', uid);
			}
		});

		function showPage(postvalue, langs, recvAuth, uid) {
			var canEdit = recvAuth == "1";
			var connsTextBox = Ext.create('DCI.Customer.TextField', {
				fieldLabel : langs.conn_name,
				width : 460,
				emptyText : '',
				name : 'displayconns',
				defaultvalue : '',
				canEdit : false,
				x : 480,
				y : 40
			});

			var connData = Ext.create('DCI.Customer.Hidden', {
				name : 'conns',
				value : '',
				triggerModi : false,
				bindBtn : null,
				setBindButton : function(btn) {
					this.bindBtn = btn;
				},
				setSelectedValue : function(value) {
					this.triggerModi = true;
					this.setValue(value);
					this.triggerModi = false;
				},
				loadDefault : function() {
					//this.triggerModi = false;
					this.setValue(this.defaultvalue);
					//this.triggerModi = true;
				},
				listeners : {
					change : function(hidden, newValue, oldValue, eOpts) {
						var me = this;
						if (!me.triggerModi && me.bindBtn != null && me.bindBtn.gridStore != null) {
							var stroe = me.bindBtn.gridStore;
							if (newValue == null && newValue == '') {
								for ( var i = 0; i < stroe.getCount(); i++) {
									stroe.getAt(i).set('selected', false);
								}
							} else {
								var tmp = [];
								tmp = newValue.split(';');
								for ( var i = 0; i < stroe.getCount(); i++) {
									stroe.getAt(i).set('selected', false);
									for ( var j = 0; j < tmp.length; j++) {
										if (stroe.getAt(i).get("conn_id") == tmp[j]) {
											stroe.getAt(i).set('selected', true);
											tmp.splice(j, 1);
											break;
										}
									}
								}
							}
						}
					}
				}
			});

			var openWinBtnConn = Ext.create('DCI.Customer.OpenWinTrigger', {
				x : 940,
				y : 40,
				cls : 'search-toolbar',
				width : 30,
				height : 30,
				winHeight : 365,
				winWidth : 450,
				langsObj : langs,
				displayComp : connsTextBox,
				targetComp : connData,
				actionurl : '../../Configs/MarqueeConfig.dsc',
				buttonID : 'connBtn',
				pvalue : postvalue,
				puid : uid,
				showSelectAll : true,
				displayCols : [ {
					xtype : 'checkcolumn',
					//text : langs.sql_id,
					dataIndex : 'selected',
					width : 100,
					listeners : {
						checkchange : function(comp, rowIndex, checked, eOpts) {
							var me = this;
							var sa = me.up('panel').up('panel').items.get(0).items.get(3);
							if (checked) {
								var all = true;
								var store = me.up('panel').getStore();
								if (store != null) {
									for ( var i = 0; i < store.getCount(); i++) {
										if (!store.getAt(i).get("selected") || store.getAt(i).get("selected") == '') {
											all = false;
											break;
										}
									}
									if (all) {
										sa.setChecked(1);
									}
								}
							} else {
								sa.setChecked(0);
							}
						}
					}
				}, {
					text : langs.conn_id,
					dataIndex : 'conn_id',
					width : 200
				}, {
					text : langs.conn_name,
					dataIndex : 'conn_name',
					width : 200
				} ],
				gridStore : Ext.create('Ext.data.Store', {
					autoLoad : false,
					fields : [ 'selected', 'conn_id', 'conn_name' ],
					proxy : {
						type : 'ajax',
						url : '../../Configs/MarqueeConfig.dsc',
						actionMethods : {
							read : 'POST'
						},
						reader : {
							root : 'items'
						},
						extraParams : {
							DCITag : postvalue,
							uid : uid,
							action : 'openwin',
							filter : '',
							btnid : 'connBtn'
						}
					}
				}),
				selectAllEvent : function(comp, newValue, oldValue, eOpts) {
					var me = this;

					for ( var i = 0; i < me.gridStore.getCount(); i++) {
						me.gridStore.getAt(i).set('selected', newValue);
					}
				},
				isAll : function() {
					var result = 0;
					var me = this;
					var all = true;
					var store = me.gridStore;
					if (store != null) {
						for ( var i = 0; i < store.getCount(); i++) {
							if (!store.getAt(i).get("selected") || store.getAt(i).get("selected") == '') {
								all = false;
								break;
							}
						}
						if (all) {
							result = 1;
						}
					}

					return result;
				},
				setSelectedData : function(values) {
					var me = this;
					var vbox = me.targetComp;
					var dbox = me.displayComp;
					var dataStr = "";
					var displaystr = "";
					var cnt = 0;
					for ( var i = 0; i < values.getCount(); i++) {
						if (values.getAt(i).get("selected") && values.getAt(i).get("selected") != '') {
							if (cnt == 0) {
								dataStr = values.getAt(i).get("conn_id");
								displaystr = values.getAt(i).get("conn_name");
								cnt++;
							} else {
								dataStr += ';' + values.getAt(i).get("conn_id");
								displaystr += ';' + values.getAt(i).get("conn_name");
							}
						}
					}

					vbox.setSelectedValue("");
					vbox.setSelectedValue(dataStr);
					dbox.setValue(displaystr);
				},
				setInitObjs : function(record) {
					this.filterComboRecord = record;
					if (this.gridStore != null) {
						this.gridStore.load();
					}

					if (connData != null) {
						connData.setBindButton(this);
					}
				},
				listeners : {
					click : function(btn, e, eOpts) {
						var me = this;
						var form = me.up();
						var newValue = form.items.get(1).getValue();
						var show = true;
						if (newValue == '3') {
							me.setDisabled(true);
							show = false;
						} else if (newValue == '1') {
							me.setDisabled(true);
							show = false;
						}
						return show;
					}
				}
			});

			var sqlsTextBox = Ext.create('DCI.Customer.TextField', {
				fieldLabel : langs.sql_name,
				width : 450,
				emptyText : '',
				name : 'displaysqls',
				defaultvalue : '',
				canEdit : false,
				x : 0,
				y : 40
			});

			var sqlData = Ext.create('DCI.Customer.Hidden', {
				name : 'sqls',
				value : '',
				triggerModi : false,
				bindBtn : null,
				setBindButton : function(btn) {
					this.bindBtn = btn;
				},
				setSelectedValue : function(value) {
					this.triggerModi = true;
					this.setValue(value);
					this.triggerModi = false;
				},
				loadDefault : function() {
					this.triggerModi = false;
					this.setValue(this.defaultvalue);
					this.triggerModi = true;
				},
				listeners : {
					change : function(hidden, newValue, oldValue, eOpts) {
						var me = this;
						if (!me.triggerModi && me.bindBtn != null && me.bindBtn.gridStore != null) {
							var stroe = me.bindBtn.gridStore;
							if (newValue == null && newValue == '') {
								for ( var i = 0; i < stroe.getCount(); i++) {
									stroe.getAt(i).set('selected', false);
								}
							} else {
								var tmp = [];
								tmp = newValue.split(';');
								for ( var i = 0; i < stroe.getCount(); i++) {
									stroe.getAt(i).set('selected', false);
									for ( var j = 0; j < tmp.length; j++) {
										if (stroe.getAt(i).get("sql_id") == tmp[j]) {
											stroe.getAt(i).set('selected', true);
											tmp.splice(j, 1);
											break;
										}
									}
								}
							}
						}
					}
				}
			});

			var openWinBtnSql = Ext.create('DCI.Customer.OpenWinTrigger', {
				x : 450,
				y : 40,
				cls : 'search-toolbar',
				width : 30,
				height : 30,
				winHeight : 365,
				winWidth : 450,
				langsObj : langs,
				displayComp : sqlsTextBox,
				targetComp : sqlData,
				actionurl : '../../Configs/MarqueeConfig.dsc',
				buttonID : 'sqlBtn',
				pvalue : postvalue,
				puid : uid,
				showSelectAll : true,
				displayCols : [ {
					xtype : 'checkcolumn',
					//text : langs.sql_id,
					dataIndex : 'selected',
					width : 100,
					listeners : {
						checkchange : function(comp, rowIndex, checked, eOpts) {
							var me = this;
							var sa = me.up('panel').up('panel').items.get(0).items.get(3);
							if (checked) {
								var all = true;
								var store = me.up('panel').getStore();
								if (store != null) {
									for ( var i = 0; i < store.getCount(); i++) {
										if (!store.getAt(i).get("selected") || store.getAt(i).get("selected") == '') {
											all = false;
											break;
										}
									}
									if (all) {
										sa.setChecked(1);
									}
								}
							} else {
								sa.setChecked(0);
							}
						}
					}
				}, {
					text : langs.sql_id,
					dataIndex : 'sql_id',
					width : 200
				}, {
					text : langs.sql_name,
					dataIndex : 'sql_name',
					width : 200
				} ],
				gridStore : Ext.create('Ext.data.Store', {
					autoLoad : false,
					fields : [ 'selected', 'sql_id', 'sql_name' ],
					proxy : {
						type : 'ajax',
						url : '../../Configs/MarqueeConfig.dsc',
						actionMethods : {
							read : 'POST'
						},
						reader : {
							root : 'items'
						},
						extraParams : {
							DCITag : postvalue,
							uid : uid,
							action : 'openwin',
							filter : '',
							btnid : 'sqlBtn'
						}
					}
				}),
				selectAllEvent : function(comp, newValue, oldValue, eOpts) {
					var me = this;

					for ( var i = 0; i < me.gridStore.getCount(); i++) {
						me.gridStore.getAt(i).set('selected', newValue);
					}
				},
				isAll : function() {
					var result = 0;
					var me = this;
					var all = true;
					var store = me.gridStore;
					if (store != null) {
						for ( var i = 0; i < store.getCount(); i++) {
							if (!store.getAt(i).get("selected") || store.getAt(i).get("selected") == '') {
								all = false;
								break;
							}
						}
						if (all) {
							result = 1;
						}
					}

					return result;
				},
				setSelectedData : function(values) {
					var me = this;
					var vbox = me.targetComp;
					var dbox = me.displayComp;
					var dataStr = "";
					var displaystr = "";
					var cnt = 0;
					for ( var i = 0; i < values.getCount(); i++) {
						if (values.getAt(i).get("selected") && values.getAt(i).get("selected") != '') {
							if (cnt == 0) {
								dataStr = values.getAt(i).get("sql_id");
								displaystr = values.getAt(i).get("sql_name");
								cnt++;
							} else {
								dataStr += ';' + values.getAt(i).get("sql_id");
								displaystr += ';' + values.getAt(i).get("sql_name");
							}
						}
					}

					vbox.setSelectedValue("");
					vbox.setSelectedValue(dataStr);
					dbox.setValue(displaystr);
				},
				setInitObjs : function(record) {
					this.filterComboRecord = record;
					if (this.gridStore != null) {
						this.gridStore.load(function(record) {
							//console.log(record.length);
						});
					}

					if (sqlData != null) {
						sqlData.setBindButton(this);
					}
				},
				listeners : {
					click : function(btn, e, eOpts) {
						var me = this;
						var form = me.up();
						var newValue = form.items.get(1).getValue();
						var show = true;
						if (newValue == '3') {
							me.setDisabled(true);
							show = false;
						} else if (newValue == '4') {
							me.setDisabled(true);
							show = false;
						}
						return show;
					}
				}
			});

			var sqleditor = Ext.create('DCI.Customer.SqlEditor', {
				fieldLabel : langs.marquee_msg,
				labelWidth : 75,
				height : 200,
				width : 970,
				name : 'message',
				x : 0,
				y : 70
			});

			var headform = Ext.create('DCI.Customer.HeadFormPanel', {
				height : 100,
				url : '../../Configs/MarqueeConfig.dsc',
				languageObj : langs,
				items : [ Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.marquee_id,
					emptyText : '',
					name : 'marquee_id',
					allowBlank : false,
					ispk : true,
					canEdit : false,
					defaultvalue : langs.system_build,
					width : 200,
					x : 0,
					y : 10
				}), Ext.create('DCI.Customer.ComboBox', {
					fieldLabel : langs.marquee_type,
					labelWidth : 100,
					name : 'marquee_type',
					store : {
						fields : [ 'label', 'value' ],
						data : [ {
							"label" : langs.text_msg,
							"value" : "1"
						}, {
							"label" : langs.sql_msg,
							"value" : "2"
						}, {
							"label" : langs.sys_text_msg,
							"value" : "3"
						}, {
							"label" : langs.sys_sql_msg,
							"value" : "4"
						} ]
					},
					defaultvalue : "1",
					width : 240,
					x : 210,
					y : 10,
					listeners : {
						change : function(comp, newValue, oldValue, eOpts) {
							var panel = this.up();
							if (panel.getCurrMode() == PageMode.Edit || panel.getCurrMode() == PageMode.Add) {
								var hconn = panel.items.get(4);
								var conn = panel.items.get(5);
								var bconn = panel.items.get(6);
								var hsql = panel.items.get(7);
								var sql = panel.items.get(8);
								var bsql = panel.items.get(9);
								if (newValue == '1') {
									conn.setValue("");
									hconn.setValue("");
									bconn.setDisabled(true);
									bsql.setDisabled(false);
								} else if (newValue == '2') {
									bsql.setDisabled(false);
									bconn.setDisabled(false);
								} else if (newValue == '3') {
									sql.setValue("");
									hsql.setValue("");
									bsql.setDisabled(true);
									conn.setValue("");
									hconn.setValue("");
									bconn.setDisabled(true);
								} else if (newValue == '4') {
									sql.setValue("");
									hsql.setValue("");
									bsql.setDisabled(true);
									bconn.setDisabled(false);
								}
							}
						}
					}
				}), Ext.create('DCI.Customer.DateField', {
					fieldLabel : langs.start_date,
					width : 250,
					name : 'start_date',
					defaultvalue : Ext.Date.format(new Date(Ext.Date.now()), 'Y/m/d H:i:s'),
					format : 'Y/m/d H:i:s',
					x : 480,
					y : 10
				}), Ext.create('DCI.Customer.DateField', {
					fieldLabel : langs.end_date,
					width : 250,
					name : 'end_date',
					defaultvalue : '',
					format : 'Y/m/d H:i:s',
					x : 720,
					y : 10
				}), connData, connsTextBox, openWinBtnConn, sqlData, sqlsTextBox, openWinBtnSql, sqleditor ],
				cusValid : function(fn) {
					var me = this;
					var msg = me.items.get(10);
					//var sql = me.items.get(8);
					//var conn = me.items.get(5);
					var ok = true;

					if (msg.getValue() == null || msg.getValue().length == 0) {
						msg.activeErrorsTpl = sql.activeErrorsTpl;
						ok = ok && false;
						msg.markInvalid(langs.not_allow_blank);
					}

					/* if (sql.getValue() == null || sql.getValue().length == 0) {
						ok = ok && false;
						sql.markInvalid(langs.not_allow_blank);
					}

					if (conn.getValue() == null || conn.getValue().length == 0) {
						ok = ok && false;
						conn.markInvalid(langs.not_allow_blank);
					} */

					if (ok) {
						fn();
					}
				},
				showQueryWindow : function() {
					var me = this;
					me.resetDataLoaded();
					var pageSize = 10;
					var initStore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'cols' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/MarqueeConfig.dsc',
							actionMethods : {
								read : 'POST'
							},
							reader : {
								type : 'json'
							},
							extraParams : {
								DCITag : postvalue,
								uid : uid,
								action : "initQuery",
								btnid : 'headform'
							}
						}
					});

					var gstore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ "marquee_id", "marquee_type", "message", "start_date", "end_date", "conns", "sqls", "displaysqls", "displayconns" ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/MarqueeConfig.dsc',
							actionMethods : {
								read : 'POST'
							},
							reader : {
								root : 'items',
								totalProperty : 'total'
							},
							extraParams : {
								DCITag : postvalue,
								uid : uid,
								action : 'search',
								page : 1,
								pagesize : pageSize,
								filter : ''
							}
						},
						pageSize : pageSize
					});

					var gridColumns = [ {
						text : langs.sql_name,
						dataIndex : 'displaysqls',
						width : 200
					}, {
						text : langs.conns,
						dataIndex : 'displayconns',
						width : 200
					}, {
						text : langs.marquee_msg,
						dataIndex : 'message',
						width : 200
					} ];

					var openwin = Ext.create('DCI.Customer.QueryWindow', {
						height : 335,
						width : 650,
						headform : me,
						title : langs.toolbar_query_title
					});

					initStore.load(function(records) {
						openwin.setInitObjects(records, gstore, gridColumns);
						me.searchWin = openwin;
						me.searchStore = gstore;
						openwin.show();
					});
				}
			});

			var headpanel = Ext.create('DCI.Customer.HeadPanel', {
				height : 350,
				width : '100%',
				headform : headform,
				postvalue : postvalue,
				uid : uid,
				canEdit : canEdit,
				keepAdd : false
			});

			var main = Ext.create('DCI.Customer.SubPanel', {
				id : 'MarqueeConfigMain',
				renderTo : 'MarqueeConfigPage',
				border : 0,
				bodyPadding : '0 5 5 5',
				minWidth : 1020,
				minHeight : 500,
				//layout : 'border',
				widthChangeControls : [ headpanel ],
				items : [ headpanel ]
			});

			/* var initQueryStore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'connList', 'sqlList' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/MarqueeConfig.dsc',
							actionMethods : {
								read : 'POST'
							},
							reader : {
								type : 'json'
							},
							extraParams : {
								DCITag : postvalue,
								action : 'init'
							}
						}
					});

					initQueryStore.load(function(record) {

						//console.log(canEdit);
						if (record.length > 0) {
							//console.log(canEdit);
						}
					}); */

			//main.resize(Ext.get("MarqueeConfigPage").getWidth(), Ext.get("MarqueeConfigPage").getHeight());
		}
	});
</script>
</head>
<body>
	<div id="MarqueeConfigPage" class="page_div"></div>
</body>
</html>