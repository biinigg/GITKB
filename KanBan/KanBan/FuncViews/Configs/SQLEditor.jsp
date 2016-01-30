<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title><dcitag:reqParam paramName="func_name"></dcitag:reqParam></title>
<link rel="stylesheet" type="text/css" href="./../../codemirror/css/codemirror.css" />
<style>
</style>
<script type="text/javascript" src="./../../codemirror/mode/sql.js"></script>
<script type="text/javascript" charset="UTF-8">
	
</script>
<script type="text/javascript" charset="UTF-8">
var t1,t2,t3,t4;
	Ext.onReady(function() {
		var localKeys = [ "system_build", "save_msg", "delete_msg", "delete_fail", "delete_success", "delete_result_title", "delete_fail", "save_fail", "save_success",
				"save_result_title", "save_fail", "upload_icon", "delete_confirm_title", "delete_confirm_msg", "sql_id", "sql_name", "group_by", "order_by", "auto_refresh",
				"refresh_gap", "use_marquee", "marquee_refresh_gap", "col_id", "selected", "conn_id", "conn_name", "conn_list", "sql_id", "col_id", "col_lang_key", "col_type",
				"col_ori_name", "col_width", "col_seq", "visible", "is_popup", "popup_title", "config_value", "col_name_cht", "col_name_chs", "move", "still", "marquee_location",
				"marquee_type", "display_top", "display_bottom", "use_popup", "popup_dir", "popup_refresh_gap", "legend", "lights", "icon_id", "icon_name", "icon_map_key",
				"lenged_value", "target_sql_name", "target_col_name", "seq", "col_relation", "cross_type", "join_key_set1", "join_key_set2", "toolbar_query_title",
				"info_msg_title", "col_create_fail", "col_create_success", "sql_check_success", "sql_check_fail", "sql_col_edit", "confirm_title", "data_lose_warning", "ok",
				"cancel", "clear", "relation_config", "copy", "build_column", "check", "copy_success", "copy_fail", "copy_kanban_confirm", "cross_db_setup", "legend_value",
				"kanban_relation_exist", "not_allow_blank", "need_end_with_where", "system_error", "no_conn_data", "load_def_format", "load_def_fail", "load_def_success",
				"load_def_result_title", "load_def_confirm_title", "load_def_confirm_msg", "illegal_keyword", "select_all", "no_sql_text", "full_sql", "sql_added_msg",
				"edit_sql_win", "input_sql_name", "just_use_in_edit", "just_use_in_view", "todo_query", "create_link", "create_link_fail", "data_never_load", "calc_rule",
				"sort_column", "user_id", "cht", "chs", "join_keys", "join_data_lose_warning", "cross_seq", "no_data_selected", "lost_join_key", "cus_format" ];
		var keys = localKeys.concat(globeKeys);
		var uid = '<dcitag:reqParam paramName="uid"></dcitag:reqParam>';
		this.dcistore.setMultiLangKeys(keys);
		this.dcistore.setUid(uid);
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				var langs = buildMultiLangObjct(keys, records[0].get('langValues'));
				showPage(records[0].get('dcitagValue'), langs, '<dcitag:reqParam paramName="canEdit"></dcitag:reqParam>', uid, records[0].get('langType'));
			}
		});

		function showPage(postvalue, langs, recvAuth, uid, langtp) {
			var canEdit = recvAuth == "1";
			var column_type_combo = Ext.create('DCI.Customer.ComboBox', {
				width : '100%',
				labelWidth : 0,
				listeners : {
					change : function(comp, newValue, oldValue, eOpts) {
						if (newValue == "IMAGELINK" || newValue == "CHAR") {
							if (comp.up() != null) {
								if (comp.up().up() != null) {
									if (comp.up().up().getSelectionModel() != null) {
										if (comp.up().up().getSelectionModel().getSelection()[0] != null) {
											comp.up().up().getSelectionModel().getSelection()[0].set('config_value', "");
										}
									}
								}
							}
						}
					}
				}

			});

			var date_format_combo = Ext.create('DCI.Customer.ComboBox', {
				width : '100%',
				labelWidth : 0
			});

			var connData = Ext.create('DCI.Customer.Hidden', {
				name : 'conndatas',
				value : '',
				triggerModi : false,
				setSelectedValue : function(value) {
					this.triggerModi = true;
					this.setValue(value);
					this.triggerModi = false;
				},
				loadDefault : function() {
					this.setValue(this.defaultvalue);
				},
				listeners : {
					change : function(hidden, newValue, oldValue, eOpts) {
						if (!this.triggerModi && openWinBtn != null && openWinBtn.gridStore != null) {
							var stroe = openWinBtn.gridStore;
							if (newValue == null && newValue == '') {
								for ( var i = 0; i < stroe.getCount(); i++) {
									stroe.getAt(i).set('selected', false);
								}

								if (connGrid != null) {
									connGrid.getStore().removeAll();
								}
							} else {
								var tmp = [];
								var datas = [];
								tmp = newValue.split(';');
								for ( var i = 0; i < stroe.getCount(); i++) {
									stroe.getAt(i).set('selected', false);
									for ( var j = 0; j < tmp.length; j++) {
										if (stroe.getAt(i).get("conn_id") == tmp[j]) {
											stroe.getAt(i).set('selected', true);
											datas.push({
												conn_name : stroe.getAt(i).get("conn_name"),
												conn_id : stroe.getAt(i).get("conn_id")
											});
											tmp.splice(j, 1);
											break;
										}
									}
								}
								connGrid.getStore().loadData(datas);
							}
						}
					}
				}
			});

			var sql_cross = Ext.create('DCI.Customer.Hidden', {
				name : 'sql_cross',
				value : '',
				listeners : {
					change : function(hidden, newValue, oldValue, eOpts) {
						/* if (crossOpenWinBtn != null) {
							crossOpenWinBtn.crossInfo = newValue;
						} */

						if (newValue == null || newValue == '') {
							if (crossGridStore != null) {
								crossGridStore.removeAll();
							}
							if (oriCrossGridStore != null) {
								oriCrossGridStore.removeAll();
							}
						}
					}
				}
			});

			var legend_lang = Ext.create('DCI.Customer.Hidden', {
				name : 'legend_lang',
				value : '',
				listeners : {
					change : function(hidden, newValue, oldValue, eOpts) {

					}
				}
			});

			var sqleditor = Ext.create('DCI.Customer.SqlEditor', {
				fieldLabel : 'SQL',
				labelWidth : 40,
				height : 230,
				width : 815,
				name : 'sql_context',
				x : 0,
				y : 100
			});

			var connGrid = Ext.create('Ext.grid.Panel', {
				title : langs.conn_list,
				hideHeaders : true,
				store : Ext.create('Ext.data.Store', {
					fields : [ {
						name : 'conn_name',
						type : 'string'
					}, {
						name : 'conn_id',
						type : 'string'
					} ],
					autoLoad : false,
					proxy : {
						type : 'memory',
						reader : {
							type : 'json'
						}
					}
				}),
				columns : [ {
					text : langs.conn_name,
					dataIndex : 'conn_name'
				} ],
				disableSelection : true,
				forceFit : true,
				height : 230,
				width : 150,
				x : 820,
				y : 100
			});

			var openWinBtn = Ext.create('DCI.Customer.OpenWinTrigger', {
				x : 970,
				y : 100,
				cls : 'search-toolbar',
				width : 30,
				height : 30,
				targetComp : connGrid,
				actionurl : '../../Configs/SQLEditor.dsc',
				buttonID : 'connBtn',
				pvalue : postvalue,
				puid : uid,
				langsObj : langs,
				winHeight : 365,
				winWidth : 450,
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
				gridStore : Ext.create('Ext.data.Store', {
					autoLoad : false,
					fields : [ 'selected', 'conn_id', 'conn_name' ],
					proxy : {
						type : 'ajax',
						url : '../../Configs/SQLEditor.dsc',
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
							filter : ''
						}
					}
				}),
				setSelectedData : function(values) {
					if (values != null) {
						var gstore = this.targetComp.getStore();
						if (gstore != null) {
							var datas = [];
							var dataStr = "";
							var cnt = 0;

							for ( var i = 0; i < values.getCount(); i++) {
								if (values.getAt(i).get("selected") && values.getAt(i).get("selected") != '') {
									if (cnt == 0) {
										dataStr = values.getAt(i).get("conn_id");
										cnt++;
									} else {
										dataStr += ';' + values.getAt(i).get("conn_id");
									}
									datas.push({
										conn_id : values.getAt(i).get("conn_id"),
										conn_name : values.getAt(i).get("conn_name")
									});
								}
							}
							gstore.removeAll();
							if (connData != null) {
								connData.setSelectedValue("");
								connData.setSelectedValue(dataStr);
							}

							gstore.loadData(datas);
						}
					}
				},
				loadDefault : function() {
					if (this.targetComp != null) {
						var gstore = this.targetComp.getStore();

						if (gstore != null) {
							gstore.removeAll();
						}
					}
				},
				setInitObjs : function(record) {
					this.filterComboRecord = record;
					if (this.gridStore != null) {
						this.gridStore.load();
					}
				}
			});

			var legendTextBox = Ext.create('DCI.Customer.TextField', {
				fieldLabel : langs.legend,
				width : 420,
				emptyText : '',
				name : 'legend',
				defaultvalue : '',
				canEdit : false,
				x : 550,
				y : 70
			});

			var legendOpenWinBtn = Ext.create('DCI.Customer.EmptyButton', {
				x : 970,
				y : 70,
				cls : 'search-toolbar',
				width : 30,
				height : 30,
				targetComp : legendTextBox,
				valueComp : legend_lang,
				handler : function() {
					var me = this;
					var gStore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'light_id', 'icon_id', 'icon_name', 'icon_map_key', 'legend_value', 'sort_id', 'legend_value_chs' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/SQLEditor.dsc',
							actionMethods : {
								read : 'POST'
							},
							reader : {
								root : 'items'
							},
							extraParams : {
								DCITag : postvalue,
								uid : uid,
								action : 'legendopenwin'
							}
						}
					});

					var win = Ext.create('Ext.window.Window', {
						title : langs.toolbar_query_title,
						width : 950,
						height : 400,
						minWidth : 950,
						minHeight : 400,
						layout : 'fit',
						plain : true,
						modal : true,
						items : [ {
							xtype : 'grid',
							renderer : "component",
							stripeRows : true,
							autoScroll : true,
							loadMask : true,
							enableTextSelection : true,
							viewConfig : {
								forceFit : false,
								autoLoad : false
							},
							columns : [ {
								xtype : 'dcilightcolumn',
								text : langs.lights,
								dataIndex : 'light_id',
								width : 80
							}, {
								text : langs.icon_id,
								dataIndex : 'icon_id',
								width : 80
							}, {
								text : langs.icon_name,
								dataIndex : 'icon_name',
								width : 100
							}, {
								text : langs.icon_map_key,
								dataIndex : 'icon_map_key',
								width : 80
							}, {
								text : langs.legend_value + "(" + langs.cht + ")",
								dataIndex : 'legend_value',
								width : 250,
								editor : {
									allowBlank : true
								}
							}, {
								text : langs.legend_value + "(" + langs.chs + ")",
								dataIndex : 'legend_value_chs',
								width : 250,
								editor : {
									allowBlank : true
								}
							}, {
								text : langs.sort_column,
								dataIndex : 'sort_id',
								width : 70,
								editor : {
									allowBlank : false
								}
							} ],
							store : gStore,
							plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
								clicksToEdit : 1,
								listeners : {
									edit : function(editor, e) {
										gStore.sort('sort_id', 'ASC');
									}
								}
							}) ]
						} ],
						buttons : [
								{
									text : langs.ok,
									handler : function() {
										var legendstr = null;
										var legendvalue = null;
										var lValue = null;
										var tmpvalue = null;
										if (this.up('.window').items.get(0).plugins[0] != null) {
											this.up('.window').items.get(0).plugins[0].completeEdit();
										}

										for ( var i = 0; i < gStore.getCount(); i++) {
											tmpvalue = gStore.getAt(i).get("legend_value") + gStore.getAt(i).get("legend_value_chs");
											if (langtp == 'CHT') {
												lValue = gStore.getAt(i).get("legend_value");
											} else {
												lValue = gStore.getAt(i).get("legend_value_chs");
											}
											if (lValue != null && lValue != "" && lValue.trim().length > 0) {
												if (legendstr == null || legendstr.indexOf("=") == -1) {
													legendstr = gStore.getAt(i).get("icon_id") + "=" + lValue;
												} else {
													legendstr += ";" + gStore.getAt(i).get("icon_id") + "=" + lValue;
												}
											}
											if (tmpvalue != null && tmpvalue != "" && tmpvalue.trim().length > 0) {
												if (legendvalue == null || legendvalue.indexOf("=") == -1) {
													legendvalue = gStore.getAt(i).get("icon_id") + "=" + gStore.getAt(i).get("legend_value_chs") + "^"
															+ gStore.getAt(i).get("legend_value");
												} else {
													legendvalue += ";" + gStore.getAt(i).get("icon_id") + "=" + gStore.getAt(i).get("legend_value_chs") + "^"
															+ gStore.getAt(i).get("legend_value");
												}
											}
										}

										me.targetComp.setValue(legendstr);
										me.valueComp.setValue(legendvalue);
										this.up('.window').close();
									}
								}, {
									text : langs.cancel,
									handler : function() {
										this.up('.window').close();
									}
								} ]
					});

					gStore.load(function(record) {
						if (me.valueComp != null) {
							var tmp = me.valueComp.getValue();
							if (tmp != null && tmp != "" && tmp.trim().length > 0) {
								var arr = tmp.split(';');
								var item = null;
								var id = null;
								var iconLangs = null;
								//var base = 0;
								var cnt = arr.length;
								for ( var i = 0; i < record.length; i++) {
									id = record[i].get("icon_id");
									for ( var j = 0; j < arr.length; j++) {
										item = arr[j].split('=');
										if (item[0] == id) {
											iconLangs = item[1].split('^');
											if (iconLangs[0].indexOf('legend_' + id) == -1) {
												record[i].set("legend_value_chs", iconLangs[0]);
											} else {
												record[i].set("legend_value_chs", "");
											}

											if (iconLangs[1].indexOf('legend_' + id) == -1) {
												record[i].set("legend_value", iconLangs[1]);
											} else {
												record[i].set("legend_value", "");
											}
											//record[i].set("legend_value_chs", item[1].split('^')[0]);
											record[i].set("sort_id", j + 1);
											//base++;
											//arr.splice(j, 1);
											break;
										}
									}
									if (record[i].get("sort_id") > 10000) {
										cnt++;
										record[i].set("sort_id", cnt * 10);
									} else {
										record[i].set("sort_id", record[i].get("sort_id") * 10);
									}
								}

							} else {
								for ( var i = 0; i < record.length; i++) {
									record[i].set("sort_id", (i + 1) * 10);
								}
							}
							gStore.commitChanges();
							gStore.sort('sort_id', 'ASC');
							win.show();
						}
					});

				},
				setReadOnly : function(readonly) {
					this.setDisabled(readonly);
				},
				loadDefault : function() {

				}
			});

			var crossOpenWinBtn = Ext.create('DCI.Customer.EmptyButton', {
				x : 970,
				y : 10,
				//cls : 'crossdbeditbutton',
				cls : 'crossdbeditbutton-withdata2',
				tooltip : langs.cross_db_setup,
				width : 30,
				height : 30,
				//hidden : true,
				targetComp : sql_cross,
				crossInfo : '',
				handler : function() {
					var me = this;
					var cross_sqleditor = Ext.create('DCI.Customer.SqlEditor', {
						fieldLabel : 'SQL',
						height : 200,
						width : 800,
						name : 'sql_context',
						x : 5,
						y : 100
					});

					var conn_combo = Ext.create('DCI.Customer.ComboBox', {
						fieldLabel : langs.conn_id,
						name : 'conn_id',
						store : Ext.create('Ext.data.Store', {
							autoLoad : false,
							fields : [ 'label', 'value' ],
							proxy : {
								type : 'ajax',
								url : '../../Configs/SQLEditor.dsc',
								actionMethods : {
									read : 'POST'
								},
								reader : {
									root : 'items'
								},
								extraParams : {
									DCITag : postvalue,
									uid : uid,
									action : 'connlist',
									filter : ''
								}
							}
						}),
						defaultvalue : '',
						width : 300,
						x : 315,
						y : 10
					});

					var cross_combo = Ext.create('DCI.Customer.ComboBox', {
						fieldLabel : langs.cross_type,
						name : 'visible',
						store : {
							fields : [ 'label', 'value' ],
							data : [ {
								"label" : 'Union All',
								"value" : 'union_all'
							}, {
								"label" : 'Join',
								"value" : 'join'
							} ]
						},
						defaultvalue : 'union_all',
						width : 300,
						x : 5,
						y : 10,
						listeners : {
							change : function(combo, newValue, oldValue, eOpts) {
								var me = this;
								var panel = me.up();

								if (panel != null) {
									var set1 = panel.items.get(5);
									var set2 = panel.items.get(6);

									set1.setDisabled(newValue != 'join');
									set2.setDisabled(newValue != 'join');
								}
							}
						}
					});

					var win = Ext.create('Ext.window.Window',
							{
								title : langs.cross_db_setup,
								width : 850,
								height : 400,
								minWidth : 800,
								minHeight : 350,
								layout : 'fit',
								plain : true,
								modal : true,
								parentButton : me,
								items : [ {
									xtype : 'panel',
									layout : 'absolute',
									border : 0,
									bodyStyle : {
										background : "transparent"
									},
									items : [ Ext.create('DCI.Customer.TextField', {
										fieldLabel : langs.group_by,
										width : 300,
										emptyText : '',
										name : 'group_by',
										defaultvalue : '',
										x : 5,
										y : 40
									}), Ext.create('DCI.Customer.TextField', {
										fieldLabel : langs.order_by,
										width : 300,
										emptyText : '',
										name : 'order_by',
										defaultvalue : '',
										x : 315,
										y : 40
									}), cross_sqleditor, cross_combo, conn_combo, Ext.create('DCI.Customer.TextField', {
										fieldLabel : langs.join_key_set1,
										width : 300,
										emptyText : '',
										name : 'join_key_set1',
										defaultvalue : '',
										x : 5,
										y : 70
									}), Ext.create('DCI.Customer.TextField', {
										fieldLabel : langs.join_key_set2,
										width : 300,
										emptyText : '',
										name : 'join_key_set2',
										defaultvalue : '',
										x : 315,
										y : 70
									}) ]
								} ],
								buttons : [
										{
											text : langs.ok,
											handler : function() {
												var pwin = this.up('.window');
												var newValue = "";
												var crossobj = {
													conn_id : pwin.items.get(0).items.get(4).getValue(),
													sql_context : pwin.items.get(0).items.get(2).getValue(),
													order_by : pwin.items.get(0).items.get(1).getValue(),
													group_by : pwin.items.get(0).items.get(0).getValue(),
													cross_type : pwin.items.get(0).items.get(3).getValue(),
													join_key_set1 : pwin.items.get(0).items.get(5).getValue(),
													join_key_set2 : pwin.items.get(0).items.get(6).getValue()
												};
												newValue = Ext.JSON.encode(crossobj);

												////////////////////////////////////////////////////
												var conn = pwin.items.get(0).items.get(4);
												var sql = pwin.items.get(0).items.get(2);
												var jk1 = pwin.items.get(0).items.get(5);
												var jk2 = pwin.items.get(0).items.get(6);

												sql.activeErrorsTpl = conn.activeErrorsTpl;
												var ok = true;
												if (conn.getValue() == null || conn.getValue().length == 0) {
													ok = ok && false;
													conn.markInvalid(langs.no_conn_data);
													//Ext.MessageBox.alert(langs.errmsg, langs.no_conn_data);
												}

												if (sql.getValue() == null || sql.getValue().length == 0) {
													ok = ok && false;
													sql.markInvalid(langs.not_allow_blank);
												} else {
													var str = sql.getValue().toLowerCase();

													if (str.indexOf("truncate ") != -1 || str.indexOf("drop ") != -1 || str.indexOf("delete ") != -1
															|| str.indexOf("update ") != -1 || str.indexOf("insert ") != -1 || str.indexOf("alter ") != -1
															|| str.indexOf("create ") != -1 || str.indexOf("replace ") != -1) {
														ok = ok && false;
														sql.markInvalid(langs.illegal_keyword);
													}
												}

												if (crossobj.cross_type == "join") {
													if (jk1.getValue() == null || jk1.getValue().length == 0) {
														ok = ok && false;
														jk1.markInvalid(langs.not_allow_blank);
													}

													if (jk2.getValue() == null || jk2.getValue().length == 0) {
														ok = ok && false;
														jk2.markInvalid(langs.not_allow_blank);
													}

													/* if (jk1.getValue() != null && jk1.getValue().length != 0 && jk2.getValue() != null && jk2.getValue().length != 0) {
														if (jk1.getValue().split(",").length != jk1.getValue().split(",").length) {
															jk1.markInvalid(langs.not_allow_blank);
															jk2.markInvalid(langs.not_allow_blank);
														}
													} */
												}

												if (ok) {
													var sql_text = sql.getValue();
													var group_by = pwin.items.get(0).items.get(0).getValue();
													var order_by = pwin.items.get(0).items.get(1).getValue();
													var conn_id = conn.getValue();
													Ext.Ajax.request({
														method : 'POST',
														url : '../../Configs/SQLEditor.dsc',
														params : {
															DCITag : postvalue,
															uid : uid,
															action : 'checkSql',
															sql_text : sql_text,
															group_by : group_by,
															order_by : order_by,
															conn_id : conn_id
														},
														success : function(a) {
															me.setLoading(false);
															var result = Ext.JSON.decode(a.responseText);
															if (result.success) {
																pwin.parentButton.crossInfo = newValue;
																if (pwin.parentButton.targetComp != null) {
																	pwin.parentButton.targetComp.setValue(newValue);
																}
																pwin.close();
															} else {
																sql.activeErrorsTpl = conn.activeErrorsTpl;
																sql.markInvalid(langs.sql_check_fail + "</br>" + result.msg);
															}
														},
														failure : function(f, action) {
															me.setLoading(false);
															sql.activeErrorsTpl = conn.activeErrorsTpl;
															sql.markInvalid(langs.system_error);
														}
													});
												}

											}
										}, {
											text : langs.clear,
											handler : function() {
												var pwin = this.up('.window');
												pwin.parentButton.loadDefault();
												if (pwin.parentButton.targetComp != null) {
													pwin.parentButton.targetComp.setValue('');
												}
												this.up('.window').close();
											}
										}, {
											text : langs.cancel,
											handler : function() {
												this.up('.window').close();
											}
										} ]
							});

					var panel = win.items.get(0);
					win.show();

					cross_combo.loadDefault();

					cross_combo.listConfig = {
						minWidth : cross_combo.getWidth() - cross_combo.getLabelWidth()
					};

					if (me.crossInfo != null && me.crossInfo.length > 0) {
						var crossobj = Ext.JSON.decode(me.crossInfo);

						if (crossobj.sql_context != null) {
							panel.items.get(2).setValue(crossobj.sql_context);
						}
						if (crossobj.order_by != null) {
							panel.items.get(1).setValue(crossobj.order_by);
						}
						if (crossobj.group_by != null) {
							panel.items.get(0).setValue(crossobj.group_by);
						}
						if (crossobj.cross_type != null) {
							panel.items.get(3).setValue(crossobj.cross_type);
						}
						if (crossobj.conn_id == null) {
							conn_combo.getStore().load(function(record) {
								conn_combo.loadDefault();
								conn_combo.listConfig = {
									minWidth : conn_combo.getWidth() - conn_combo.getLabelWidth()
								};
							});
						} else {
							conn_combo.getStore().load(function(record) {
								conn_combo.listConfig = {
									minWidth : conn_combo.getWidth() - conn_combo.getLabelWidth()
								};
								conn_combo.setValue(crossobj.conn_id);
							});
						}
						if (crossobj.join_key_set1 != null) {
							panel.items.get(5).setValue(crossobj.join_key_set1);
						}
						if (crossobj.join_key_set2 != null) {
							panel.items.get(6).setValue(crossobj.join_key_set2);
						}
					} else {
						conn_combo.getStore().load(function(record) {
							conn_combo.loadDefault();
							conn_combo.listConfig = {
								minWidth : conn_combo.getWidth() - conn_combo.getLabelWidth()
							};
						});
					}
				},
				setReadOnly : function(readonly) {
					this.setDisabled(readonly);
				},
				loadDefault : function() {
					var me = this;
					me.crossInfo = '';
				}
			});

			var oriCrossGridStore = Ext.create('Ext.data.Store', {
				fields : [ 'cross_type', 'sql_context', 'group_by', 'order_by', 'join_keys', 'cross_seq', 'cross_id', 'conn_id', 'cross_seq_show' ],
				autoLoad : false,
				proxy : {
					type : 'memory',
					reader : {
						type : 'json'
					}
				}
			});

			var crossGridStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'cross_type', 'sql_context', 'group_by', 'order_by', 'join_keys', 'cross_seq', 'cross_id', 'conn_id', 'cross_seq_show' ],
				proxy : {
					type : 'ajax',
					url : '../../Configs/SQLEditor.dsc',
					actionMethods : {
						read : 'POST'
					},
					reader : {
						root : 'items'
					},
					extraParams : {
						DCITag : postvalue,
						action : 'crossMain',
						uid : uid,
						sql_id : ''
					}
				},
				listeners : {
					datachanged : function(store, eOpts) {
						if (crossMainOpenWinBtn != null) {
							if (store.getCount() > 0) {
								crossMainOpenWinBtn.getEl().setStyle({
									borderWidth : "2px 2px 2px 2px",
									borderColor : 'red',
									boderStyle : 'solid'
								});
							} else {
								crossMainOpenWinBtn.getEl().setStyle({
									borderWidth : "1px 1px 1px 1px",
									borderColor : '',
									boderStyle : ''
								});
							}
						}
					}
				}
			});

			var crossMainOpenWinBtn = Ext.create('DCI.Customer.EmptyButton', {
				x : 970,
				y : 10,
				cls : 'crossdbeditbutton',
				tooltip : langs.cross_db_setup,
				width : 30,
				height : 30,
				//hidden : true,
				targetComp : sql_cross,
				mainSqlObjs : null,
				handler : function() {
					var mainBtn = this;

					var bodygrid = Ext.create('Ext.grid.Panel', {
						region : 'center',
						renderer : "component",
						stripeRows : true,
						autoScroll : true,
						loadMask : true,
						enableTextSelection : true,
						viewConfig : {
							forceFit : false,
							autoLoad : false
						},
						store : crossGridStore,
						columns : [ {
							text : langs.cross_seq,
							dataIndex : 'cross_seq_show',
							width : 80
						}, {
							text : 'SQL',
							dataIndex : 'sql_context',
							width : 400
						}, {
							text : langs.group_by,
							dataIndex : 'group_by',
							width : 200
						}, {
							text : langs.order_by,
							dataIndex : 'order_by',
							width : 200
						}, {
							text : langs.join_keys,
							dataIndex : 'join_keys',
							width : 100
						} ],
						listeners : {
							selectionchange : function(smodel, selected, eOpts) {
								var me = this;
								var combo = me.up('panel').items.get(0).items.get(0);
								if (selected[0] != null && selected[0].data != null) {
									combo.setValue(selected[0].data.cross_type);
								}
							},
							celldblclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {

							},
							cellcontextmenu : function(view, td, cellIndex, record, tr, rowIndex, e, eOpts) {
								//var me = this;
							}
						},
						initBodyGrid : function(timer, columns, store, relationRecord) {

						}
					});

					var cross_combo = Ext.create('DCI.Customer.ComboBox', {
						fieldLabel : langs.cross_type,
						needConfirm : true,
						name : 'visible',
						store : {
							fields : [ 'label', 'value' ],
							data : [ {
								"label" : 'Union All',
								"value" : 'union_all'
							}, {
								"label" : 'Join',
								"value" : 'join'
							}, {
								"label" : 'LeftJoin',
								"value" : 'left_join'
							} ]
						},
						defaultvalue : 'union_all',
						width : 300,
						x : 5,
						y : 10,
						listeners : {
							change : function(combo, newValue, oldValue, eOpts) {
								var me = this;
								if (oldValue == 'join' || oldValue == 'left_join') {
									if (newValue == 'union_all' || newValue == 'union') {
										if (crossGridStore != null && crossGridStore.getCount() > 0) {
											Ext.MessageBox.confirm(langs.confirm_title, langs.join_data_lose_warning, function(btn) {
												if (btn == 'yes') {
													if (bodygrid != null) {
														var gStore = bodygrid.getStore();
														for ( var i = 0; i < gStore.getCount(); i++) {
															gStore.getAt(i).set('cross_type', newValue);
															gStore.getAt(i).set('join_keys', "");
															gStore.commitChanges();
														}
													}
												} else {
													me.setValue(oldValue);
												}
											});
										}
									} else {
										var gStore = bodygrid.getStore();
										for ( var i = 0; i < gStore.getCount(); i++) {
											gStore.getAt(i).set('cross_type', newValue);
											gStore.commitChanges();
										}
									}
								} else {
									var gStore = bodygrid.getStore();
									for ( var i = 0; i < gStore.getCount(); i++) {
										gStore.getAt(i).set('cross_type', newValue);
										gStore.commitChanges();
									}
								}
							}
						}
					});

					var cross_sqleditor = Ext.create('DCI.Customer.SqlEditor', {
						fieldLabel : 'SQL',
						labelWidth : 60,
						height : 230,
						width : 800,
						name : 'sql_context',
						x : 5,
						y : 100
					});

					var conn_combo = Ext.create('DCI.Customer.ComboBox', {
						fieldLabel : langs.conn_id,
						labelWidth : 60,
						name : 'conn_id',
						store : Ext.create('Ext.data.Store', {
							fields : [ 'label', 'value' ],
							autoLoad : false,
							proxy : {
								type : 'ajax',
								url : '../../Configs/SQLEditor.dsc',
								actionMethods : {
									read : 'POST'
								},
								reader : {
									root : 'items'
								},
								extraParams : {
									DCITag : postvalue,
									uid : uid,
									action : 'connlist',
									filter : ''
								}
							}
						}),
						defaultvalue : '',
						width : 300,
						x : 5,
						y : 10
					});

					var sqlEditorWin = Ext.create('Ext.window.Window', {
						title : langs.cross_db_setup,
						width : 850,
						height : 400,
						minWidth : 800,
						minHeight : 350,
						layout : 'fit',
						plain : true,
						modal : true,
						closeAction : 'hide',
						parentButton : mainBtn,
						cross_type : 'union_all',
						currSeq : -1,
						items : [ {
							xtype : 'panel',
							layout : 'absolute',
							border : 0,
							bodyStyle : {
								background : "transparent"
							},
							items : [ cross_sqleditor, conn_combo, Ext.create('DCI.Customer.TextField', {
								fieldLabel : langs.group_by,
								labelWidth : 60,
								width : 380,
								emptyText : '',
								name : 'group_by',
								defaultvalue : '',
								x : 5,
								y : 40
							}), Ext.create('DCI.Customer.TextField', {
								fieldLabel : langs.order_by,
								labelWidth : 60,
								width : 380,
								emptyText : '',
								name : 'order_by',
								defaultvalue : '',
								x : 420,
								y : 40
							}), Ext.create('DCI.Customer.TextField', {
								fieldLabel : langs.join_keys,
								labelWidth : 60,
								width : 760,
								emptyText : '',
								readOnly : true,
								name : 'join_keys',
								defaultvalue : '',
								x : 5,
								y : 70
							}), {
								xtype : 'button',
								x : 765,
								y : 70,
								text : langs.edit,
								handler : function() {
									var panel = this.up('panel');
									var win = panel.up("window");
									var joinInitStore = Ext.create('Ext.data.Store', {
										fields : [ 'datas' ],
										autoLoad : false,
										proxy : {
											type : 'ajax',
											url : '../../Configs/SQLEditor.dsc',
											actionMethods : {
												read : 'POST'
											},
											reader : {
												type : 'json'
											},
											extraParams : {
												DCITag : postvalue,
												uid : uid,
												action : 'getKeyColumns',
												sqls : ''
											}
										}
									});

									var joinbodygrid = Ext.create('Ext.grid.Panel', {
										region : 'center',
										renderer : "component",
										store : Ext.create('Ext.data.Store', {
											fields : [ {
												name : 'joinkey',
												type : 'string'
											} ],
											autoLoad : false,
											proxy : {
												type : 'memory',
												reader : {
													type : 'json'
												}
											}
										}),
										columns : [ {
											text : langs.join_keys,
											dataIndex : 'joinkey',
											width : '100%'
										} ],
										allowDeselect : true,
										selModel : {
											allowDeselect : true
										},
										forceFit : true
									});

									var keystr = panel.items.get(4).getValue();
									var keys = null;

									if (keystr != null && keystr != '') {
										keys = keystr.split(';');
										var tmp = [];
										for ( var i = 0; i < keys.length; i++) {
											tmp.push({
												joinkey : keys[i]
											});
										}
										joinbodygrid.getStore().loadData(tmp);
									}

									var joinwin = Ext.create('Ext.window.Window', {
										title : langs.joinKey_setup,
										width : 550,
										height : 400,
										minWidth : 500,
										minHeight : 350,
										layout : 'border',
										plain : true,
										modal : true,
										tragetComp : null,
										colsData : null,
										items : [ {
											xtype : 'panel',
											region : 'north',
											layout : 'absolute',
											height : 40,
											items : [ Ext.create('DCI.Customer.ComboBox', {
												fieldLabel : langs.join_keys + "1",
												labelWidth : 60,
												width : 110,
												x : 5,
												y : 10,
												listeners : {
													change : function(combo, newValue, oldValue, eOpts) {
														var panel = this.up('panel');
														var win = panel.up('window');
														var combo2 = panel.items.get(1);

														if (win.colsData != null) {
															for ( var i = 0; i < win.colsData.length; i++) {
																if (win.colsData[i].seq == newValue) {
																	combo2.getStore().loadData(win.colsData[i].cols);
																	combo2.loadDefault();
																}
															}
														}
													}
												}
											}), Ext.create('DCI.Customer.ComboBox', {
												labelWidth : 0,
												width : 120,
												valueNotFoundText : "",
												x : 120,
												y : 10
											}), Ext.create('DCI.Customer.ComboBox', {
												fieldLabel : langs.join_keys + "2",
												labelWidth : 60,
												width : 180,
												valueNotFoundText : "",
												seq : '',
												x : 260,
												y : 10
											}), {
												xtype : 'button',
												text : langs.add,
												x : 445,
												y : 10,
												width : 40,
												handler : function() {
													var gridstore = this.up('panel').up('window').items.get(1).getStore();
													var combo1 = this.up('panel').items.get(0);
													var combo2 = this.up('panel').items.get(1);
													var combo3 = this.up('panel').items.get(2);

													if (gridstore != null) {
														gridstore.add({
															joinkey : combo1.getValue() + "." + combo2.getValue() + "=" + combo3.seq + "." + combo3.getValue()
														});
													}
												}
											}, {
												xtype : 'button',
												text : langs._delete,
												x : 485,
												y : 10,
												width : 40,
												handler : function() {
													var grid = this.up('panel').up('window').items.get(1);
													var selectionModel = grid.getSelectionModel();
													if (selectionModel.hasSelection()) {
														grid.getStore().remove(grid.getSelectionModel().getSelection()[0]);
													}
												}
											} ]
										}, joinbodygrid ],
										buttons : [ {
											text : langs.ok,
											handler : function() {
												var win = this.up('.window');
												var gridstore = win.items.get(1).getStore();
												var value = null;
												if (gridstore == null || gridstore.getCount() == 0) {
													value = "";
												} else {
													for ( var i = 0; i < gridstore.getCount(); i++) {
														if (i == 0) {
															value = gridstore.getAt(i).get('joinkey');
														} else {
															value += ";" + gridstore.getAt(i).get('joinkey');
														}
													}
												}

												win.tragetComp.setValue(value);
												win.close();
											}
										}, {
											text : langs.cancel,
											handler : function() {
												this.up('.window').close();
											}
										} ],
										initJoinWin : function(seqs, cols, tragetComp) {
											var me = this;
											var panel = me.items.get(0);
											var combo1 = panel.items.get(0);
											var combo3 = panel.items.get(2);
											me.colsData = cols;
											combo1.getStore().loadData(seqs);
											combo1.loadDefault();
											combo3.seq = cols[cols.length - 1].seq;
											combo3.getStore().loadData(cols[cols.length - 1].cols);
											combo3.loadDefault();
											me.tragetComp = tragetComp;
											me.show();
										}
									});

									var formDatas = [];

									formDatas.push({
										connid : mainBtn.mainSqlObjs.get(18).getValue(),
										sql : mainBtn.mainSqlObjs.get(17).getValue(),
										groupBy : mainBtn.mainSqlObjs.get(4).getValue(),
										seq : "0"
									});

									if (crossGridStore != null) {
										for ( var i = 0; i < crossGridStore.getCount(); i++) {
											if (i < win.currSeq - 1) {
												formDatas.push({
													connid : crossGridStore.getAt(i).get("conn_id"),
													sql : crossGridStore.getAt(i).get("sql_context"),
													groupBy : crossGridStore.getAt(i).get("group_by"),
													seq : crossGridStore.getAt(i).get("cross_seq").toString()
												});
											} else {
												break;
											}
										}
									}

									formDatas.push({
										connid : panel.items.get(1).getValue(),
										sql : panel.items.get(0).getValue(),
										groupBy : panel.items.get(2).getValue(),
										seq : win.currSeq.toString()
									});

									if (joinInitStore.getProxy().extraParams.hasOwnProperty('sqls')) {
										joinInitStore.getProxy().extraParams['sqls'] = Ext.encode(formDatas);
									}

									joinInitStore.load(function(record) {
										var seqs = [];
										for ( var i = 0; i < (record[0].data.datas.length - 1); i++) {
											seqs.push({
												label : record[0].data.datas[i].seq,
												value : record[0].data.datas[i].seq
											});
										}
										joinwin.initJoinWin(seqs, record[0].data.datas, panel.items.get(4));
									});
								}
							} ]
						} ],
						buttons : [ {
							text : langs.ok,
							handler : function() {
								var win = this.up('.window');
								var comps = win.items.get(0).items;
								var conn = comps.get(1);
								var checkok = true;
								if (comps.get(0).getValue() == null || comps.get(0).getValue() == '') {
									comps.get(0).activeErrorsTpl = conn.activeErrorsTpl;
									comps.get(0).markInvalid(langs.not_allow_blank);
									checkok = false;
								}

								if (win.cross_type == 'join' || win.cross_type == 'left_join') {
									if (comps.get(4).getValue() == null || comps.get(4).getValue() == '') {
										comps.get(4).activeErrorsTpl = comps.get(4).activeErrorsTpl;
										comps.get(4).markInvalid(langs.sql_check_fail + "</br>" + langs.not_allow_blank);
										checkok = checkok & false;
									}
								}
								if (checkok) {
									Ext.Ajax.request({
										method : 'POST',
										url : '../../Configs/SQLEditor.dsc',
										params : {
											DCITag : postvalue,
											uid : uid,
											action : 'checkSql',
											sql_text : comps.get(0).getValue(),
											group_by : comps.get(2).getValue(),
											order_by : comps.get(3).getValue(),
											conn_id : conn.getValue()
										},
										success : function(a) {
											var result = Ext.JSON.decode(a.responseText);
											if (result.success) {
												if (win.currSeq > crossGridStore.getCount()) {
													crossGridStore.add({
														cross_type : win.cross_type,
														sql_context : comps.get(0).getValue(),
														group_by : comps.get(2).getValue(),
														order_by : comps.get(3).getValue(),
														join_keys : comps.get(4).getValue(),
														cross_seq : win.currSeq.toString(),
														conn_id : comps.get(1).getValue(),
														cross_seq_show : String.fromCharCode(win.currSeq + 65)

													});
												} else {
													for ( var i = 0; i < crossGridStore.getCount(); i++) {
														if (crossGridStore.getAt(i).get('cross_seq') == win.currSeq) {
															var record = crossGridStore.getAt(i);

															record.set('sql_context', comps.get(0).getValue());
															record.set('conn_id', comps.get(1).getValue());
															record.set('group_by', comps.get(2).getValue());
															record.set('order_by', comps.get(3).getValue());
															record.set('join_keys', comps.get(4).getValue());
															break;
														}
													}
												}

												crossGridStore.commitChanges();
												win.close();
											} else {
												comps.get(0).activeErrorsTpl = conn.activeErrorsTpl;
												comps.get(0).markInvalid(langs.sql_check_fail + "</br>" + result.msg);
											}
										},
										failure : function(f, action) {
											comps.get(0).activeErrorsTpl = conn.activeErrorsTpl;
											comps.get(0).markInvalid(langs.system_error);
										}
									});
								}
							}
						}, {
							text : langs.cancel,
							handler : function() {
								this.up('.window').close();
							}
						} ],
						initWin : function(currMode, currCross, cross_type, seq) {
							var me = this;
							var ccombo = me.items.get(0).items.get(1);
							ccombo.getStore().load(function(record) {
								var comps = me.items.get(0).items;
								if (currMode == 'add') {
									comps.get(0).loadDefault();
									comps.get(1).loadDefault();
									comps.get(2).loadDefault();
									comps.get(3).loadDefault();
									comps.get(4).loadDefault();
								} else {
									comps.get(0).setValue(currCross.sql_context);
									comps.get(1).setValue(currCross.conn_id);
									comps.get(2).setValue(currCross.group_by);
									comps.get(3).setValue(currCross.order_by);
									comps.get(4).setValue(currCross.join_keys);
								}

								comps.get(5).setDisabled(cross_type != 'join' && cross_type != 'left_join');
								me.cross_type = cross_type;
								me.currSeq = seq;
								me.show();
							});
						}
					});

					var win = Ext.create('Ext.window.Window', {
						title : langs.cross_db_setup,
						width : 850,
						height : 400,
						minWidth : 800,
						minHeight : 350,
						layout : 'border',
						plain : true,
						modal : true,
						parentButton : mainBtn,
						items : [ {
							region : 'north',
							xtype : 'panel',
							layout : 'absolute',
							height : 40,
							currCross : null,
							border : 0,
							bodyStyle : {
								background : "transparent"
							},
							items : [ cross_combo, {
								xtype : 'button',
								x : 315,
								y : 10,
								text : langs.add,
								handler : function() {
									sqlEditorWin.initWin('add', null, this.up('panel').items.get(0).getValue(), bodygrid.getStore().getCount() + 1);
								}
							}, {
								xtype : 'button',
								x : 355,
								y : 10,
								text : langs.edit,
								handler : function() {
									if (bodygrid != null) {
										var s = bodygrid.getSelectionModel().getSelection();
										if (s.length > 0) {
											sqlEditorWin.initWin('edit', s[0].data, this.up('panel').items.get(0).getValue(), s[0].data.cross_seq);
										} else {
											Ext.Msg.alert(langs.errmsg, langs.no_data_selected);
										}
									}
								}
							}, {
								xtype : 'button',
								x : 395,
								y : 10,
								text : langs._delete,
								handler : function() {
									if (bodygrid != null) {
										var s = bodygrid.getSelectionModel().getSelection();
										if (s.length > 0) {
											bodygrid.getStore().remove(s[0]);
										} else {
											Ext.Msg.alert(langs.errmsg, langs.no_data_selected);
										}
									}
								}
							} ]
						}, bodygrid ],
						buttons : [ {
							text : langs.ok,
							handler : function() {
								var checkok = true;
								var errcode = null;
								for ( var i = 0; i < crossGridStore.getCount(); i++) {
									if (crossGridStore.getAt(i).data.cross_type == 'join' || crossGridStore.getAt(i).data.cross_type == 'left_join') {
										if (crossGridStore.getAt(i).data.join_keys == null || crossGridStore.getAt(i).data.join_keys == '') {
											checkok = false;
											errcode = langs.lost_join_key;
											break;
										}
									}
								}

								if (checkok) {
									var value = [];
									var record = null;
									oriCrossGridStore.removeAll();
									for ( var i = 0; i < crossGridStore.getCount(); i++) {
										record = crossGridStore.getAt(i);
										oriCrossGridStore.add(record.copy());
										value.push(record.copy().data);
									}
									this.up('.window').parentButton.targetComp.setValue(Ext.JSON.encode(value));
									this.up('.window').close();
								} else {
									Ext.MessageBox.alert(langs.errmsg, errcode);
								}
							}
						}, {
							text : langs.cancel,
							handler : function() {
								crossGridStore.removeAll();
								for ( var i = 0; i < oriCrossGridStore.getCount(); i++) {
									crossGridStore.add(oriCrossGridStore.getAt(i).copy());
								}

								this.up('.window').close();
							}
						} ]
					});

					if (crossGridStore != null && crossGridStore.getCount() > 0) {
						cross_combo.setValue(crossGridStore.getAt(0).get('cross_type'));
					} else {
						cross_combo.loadDefault();
					}
					win.show();

					cross_combo.listConfig = {
						minWidth : cross_combo.getWidth() - cross_combo.getLabelWidth()
					};
				},
				setReadOnly : function(readonly) {
					this.setDisabled(readonly);
				},
				loadDefault : function() {
					var me = this;
					me.crossInfo = '';
				}
			});

			var headform = Ext.create('DCI.Customer.HeadFormPanel', {
				height : 200,
				url : '../../Configs/SQLEditor.dsc',
				languageObj : langs,
				items : [ Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.sql_id,
					emptyText : '',
					name : 'sql_id',
					allowBlank : false,
					ispk : true,
					canEdit : false,
					defaultvalue : langs.system_build,
					width : 250,
					x : 0,
					y : 10
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.sql_name,
					width : 300,
					emptyText : '',
					name : 'sql_name',
					allowBlank : false,
					defaultvalue : '',
					x : 250,
					y : 10
				}), Ext.create('DCI.Customer.CheckBox', {
					fieldLabel : langs.auto_refresh,
					name : 'auto_refresh',
					defaultvalue : 0,
					x : 550,
					y : 10
				}), Ext.create('DCI.Customer.NumberBox', {
					fieldLabel : langs.refresh_gap,
					name : 'refresh_gap',
					minValue : 1,
					defaultvalue : 30,
					width : 165,
					x : 655,
					y : 10
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.group_by,
					width : 250,
					emptyText : '',
					name : 'group_by',
					defaultvalue : '',
					x : 0,
					y : 40
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.order_by,
					width : 300,
					emptyText : '',
					name : 'order_by',
					defaultvalue : '',
					x : 250,
					y : 40
				}), Ext.create('DCI.Customer.CheckBox', {
					fieldLabel : langs.use_marquee,
					name : 'use_marquee',
					defaultvalue : 0,
					x : 550,
					y : 40
				}), Ext.create('DCI.Customer.NumberBox', {
					fieldLabel : langs.marquee_refresh_gap,
					name : 'marquee_refresh_gap',
					defaultvalue : 180,
					minValue : 1,
					step : 10,
					labelWidth : 110,
					width : 165,
					x : 655,
					y : 40
				}), Ext.create('DCI.Customer.ComboBox', {
					fieldLabel : langs.marquee_location,
					name : 'marquee_location',
					store : {
						fields : [ 'label', 'value' ],
						data : [ {
							"label" : langs.display_bottom,
							"value" : "1"
						}, {
							"label" : langs.display_top,
							"value" : "2"
						} ]
					},
					defaultvalue : "1",
					labelWidth : 105,
					width : 160,
					x : 810,
					y : 10
				}), Ext.create('DCI.Customer.ComboBox', {
					fieldLabel : langs.marquee_type,
					name : 'marquee_type',
					store : {
						fields : [ 'label', 'value' ],
						data : [ {
							"label" : langs.move,
							"value" : "1"
						}, {
							"label" : langs.still,
							"value" : "2"
						} ]
					},
					defaultvalue : "1",
					labelWidth : 105,
					width : 160,
					x : 810,
					y : 40
				}), Ext.create('DCI.Customer.CheckBox', {
					fieldLabel : langs.use_popup,
					name : 'use_popup',
					defaultvalue : 0,
					x : 0,
					y : 70
				}), Ext.create('DCI.Customer.NumberBox', {
					fieldLabel : langs.popup_refresh_gap,
					name : 'popup_refresh_gap',
					defaultvalue : 30,
					minValue : 10,
					width : 150,
					x : 100,
					y : 70
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.popup_dir,
					width : 300,
					emptyText : '',
					name : 'popup_dir',
					defaultvalue : '',
					x : 250,
					y : 70
				}), legendTextBox, legendOpenWinBtn, openWinBtn, connGrid, sqleditor, connData, sql_cross, crossMainOpenWinBtn, legend_lang ],
				cusValid : function(fn) {
					var me = this;
					var conn = me.items.get(18);
					var sql = me.items.get(17);

					sql.activeErrorsTpl = conn.activeErrorsTpl;
					var ok = true;
					if (conn.getValue() == null || conn.getValue().length == 0) {
						ok = ok && false;
						Ext.MessageBox.alert(langs.errmsg, langs.no_conn_data);
					}

					if (sql.getValue() == null || sql.getValue().length == 0) {
						ok = ok && false;
						sql.markInvalid(langs.not_allow_blank);
					} else {
						var str = sql.getValue().toLowerCase();

						if (str.indexOf("truncate ") != -1 || str.indexOf("drop ") != -1 || str.indexOf("delete ") != -1 || str.indexOf("update ") != -1
								|| str.indexOf("insert ") != -1 || str.indexOf("alter ") != -1 || str.indexOf("create ") != -1 || str.indexOf("replace ") != -1) {
							ok = ok && false;
							sql.markInvalid(langs.illegal_keyword);
						}
					}

					if (ok) {
						var sql_text = me.items.get(17).getValue();
						var group_by = me.items.get(4).getValue();
						var order_by = me.items.get(5).getValue();
						var conn_id = conn.getValue();
						Ext.Ajax.request({
							method : 'POST',
							url : '../../Configs/SQLEditor.dsc',
							params : {
								DCITag : postvalue,
								uid : uid,
								action : 'checkSql',
								sql_text : sql_text,
								group_by : group_by,
								order_by : order_by,
								conn_id : conn_id
							},
							success : function(a) {
								me.setLoading(false);
								var result = Ext.JSON.decode(a.responseText);
								if (result.success) {
									fn();
								} else {
									sql.activeErrorsTpl = conn.activeErrorsTpl;
									sql.markInvalid(langs.sql_check_fail + "</br>" + result.msg);
								}
							},
							failure : function(f, action) {
								me.setLoading(false);
								sql.activeErrorsTpl = conn.activeErrorsTpl;
								sql.markInvalid(langs.system_error);
							}
						});
					}
				},
				showQueryWindow : function() {
					var me = this;
					me.initQueryWindow();
					var pageSize = 10;

					var initStore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'cols' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/SQLEditor.dsc',
							actionMethods : {
								read : 'POST'
							},
							reader : {
								type : 'json'
							},
							extraParams : {
								DCITag : postvalue,
								uid : uid,
								btnid : 'headform'
							}
						}
					});

					var gstore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						/* fields : [ 'sql_id', 'sql_name', 'sql_context', 'group_by', 'order_by', 'auto_refresh', 'refresh_gap', 'use_marquee', 'marquee_location', 'marquee_type',
								'marquee_refresh_gap', 'use_popup', 'popup_dir', 'popup_refresh_gap', 'legend', 'legend_lang', 'cross_conn_id', 'cross_sql_context',
								'cross_order_by', 'cross_group_by', 'cross_type', 'join_key_set1', 'join_key_set2' ], */
						fields : [ 'sql_id', 'sql_name', 'sql_context', 'group_by', 'order_by', 'auto_refresh', 'refresh_gap', 'use_marquee', 'marquee_location', 'marquee_type',
								'marquee_refresh_gap', 'use_popup', 'popup_dir', 'popup_refresh_gap', 'legend', 'legend_lang' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/SQLEditor.dsc',
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
						text : langs.sql_id,
						dataIndex : 'sql_id',
						width : 200
					}, {
						text : langs.sql_name,
						dataIndex : 'sql_name',
						width : 200
					} ];

					var openwin = Ext.create('DCI.Customer.QueryWindow', {
						height : 335,
						width : 500,
						headform : me,
						title : langs.toolbar_query_title,
						loadHeadData : function(record) {
							var head = this.headform;
							var win = this;
							/* var crossobj = {
								conn_id : record.get("cross_conn_id"),
								sql_context : record.get("cross_sql_context"),
								order_by : record.get("cross_order_by"),
								group_by : record.get("cross_group_by"),
								cross_type : record.get("cross_type"),
								join_key_set1 : record.get("join_key_set1"),
								join_key_set2 : record.get("join_key_set2")
							}; */

							//this.headform.items.get(19).setValue(Ext.JSON.encode(crossobj));
							head.items.get(20).mainSqlObjs = this.headform.items; //new cross db
							if (crossGridStore.getProxy().extraParams.hasOwnProperty('sql_id')) {
								crossGridStore.getProxy().extraParams['sql_id'] = record.get("sql_id");
							}
							crossGridStore.load(function(record) {
								var value = [];
								oriCrossGridStore.removeAll();
								for ( var i = 0; i < crossGridStore.getCount(); i++) {
									oriCrossGridStore.add(record[i].copy());
									value.push(record[i].copy().data);
								}
								head.items.get(19).setValue(Ext.JSON.encode(value));
							});

							var connsStore = Ext.create('Ext.data.Store', {
								autoLoad : false,
								fields : [ 'conns' ],
								proxy : {
									type : 'ajax',
									url : '../../Configs/SQLEditor.dsc',
									actionMethods : {
										read : 'POST'
									},
									reader : {
										root : 'items'
									},
									extraParams : {
										DCITag : postvalue,
										uid : uid,
										action : 'sqlConns',
										sqlid : record.get('sql_id')
									}
								}
							});
							connsStore.load(function(records) {
								if (records.length > 0) {
									connData.setValue(records[0].get('conns'));
									head.loadRecord(record);
									head.setDataLoaded(true);

									/* if (bodyStore != null) {
										if (bodyStore.getProxy().extraParams.hasOwnProperty('keys')) {
											bodyStore.getProxy().extraParams['keys'] = record.get("sql_id");
											bodyStore.load(function(records) {
												if (bodypanel != null) {
													var obj = new Object();
													obj["sql_id"] = record.get("sql_id");
													bodypanel.headKeys = obj;
													bodypanel.dataloaded(head.dataloaded);
												}
											});
										}
									} */

									if (bodypanel != null) {
										var bodyStore = bodypanel.getGrid().getStore();
										if (bodyStore.getProxy().extraParams.hasOwnProperty('keys')) {
											bodyStore.getProxy().extraParams['keys'] = record.get("sql_id");
											//bodyStore.load(function(records) {
											var obj = new Object();
											obj["sql_id"] = record.get("sql_id");
											bodypanel.headKeys = obj;
											bodypanel.dataloaded(head.dataloaded);
											//});
										}
									}

									bodypanel.gridReload();
									win.close();
								}
							});
						},
						clickFunc : function() {
							var me = this;
							var gstore = me.items.get(1).getStore();
							var fcol = me.items.get(0).items.get(0).getValue();
							var fvalue = me.items.get(0).items.get(1).getValue();

							if (gstore.getProxy().extraParams.hasOwnProperty('filter')) {
								if (fcol == "sql_name") {
									gstore.getProxy().extraParams['filter'] = " and lang_value like '%" + fvalue + "%' ";
								} else {
									gstore.getProxy().extraParams['filter'] = " and " + fcol + " like '%" + fvalue + "%' ";
								}
							}
							if (gstore.getProxy().extraParams.hasOwnProperty('page')) {
								gstore.getProxy().extraParams['page'] = 1;
							}
							gstore.currentPage = 1;
							gstore.load();
						}
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
				height : 380,
				width : '100%',
				headform : headform,
				postvalue : postvalue,
				uid : uid,
				canEdit : canEdit,
				cusMsg : [ langs.sql_added_msg ],
				keepAdd : false
			});

			headpanel.setCusButtons([
					{
						xtype : 'button',
						cls : 'buildcolumn-toolbar',
						tooltip : langs.build_column,
						x : 215,
						y : 3,
						width : 30,
						height : 30,
						handler : function() {
							var bform = this.up('panel').bindform;
							var pvalue = this.up('panel').postvalue;
							var langobj = bform.languageObj;
							if (bform.dataloaded) {
								Ext.Ajax.request({
									method : 'POST',
									url : '../../Configs/SQLEditor.dsc',
									params : {
										DCITag : pvalue,
										uid : uid,
										action : 'bodyBuild',
										sql_id : bform.items.get(0).getValue()
									},
									success : function(a) {
										var bodyStore = bodypanel.getGrid().getStore();
										var result = Ext.JSON.decode(a.responseText);
										if (bodyStore != null && result != null) {
											bodyStore.removeAll();
											bodyStore.loadRawData(result);
										}
										Ext.Msg.alert(langobj.info_msg_title, langobj.col_create_success);
									},
									failure : function(f, action) {
										Ext.Msg.alert(langobj.errmsg, langobj.col_create_fail);
									}
								});
							}
						}
					},
					{
						xtype : 'button',
						cls : 'sqlcheckbutton',
						tooltip : langs.check,
						x : 250,
						y : 3,
						width : 30,
						height : 30,
						handler : function() {
							var bform = this.up('panel').bindform;
							var pvalue = this.up('panel').postvalue;
							var langobj = bform.languageObj;
							if (bform.getCurrMode() == PageMode.Add || bform.dataloaded) {
								bform.setLoading(true);
								var sql_text = bform.items.get(17).getValue();
								var group_by = bform.items.get(4).getValue();
								var order_by = bform.items.get(5).getValue();
								var conn_id = bform.items.get(18).getValue();

								Ext.Ajax.request({
									method : 'POST',
									url : '../../Configs/SQLEditor.dsc',
									params : {
										DCITag : pvalue,
										uid : uid,
										action : 'checkSql',
										sql_text : sql_text,
										group_by : group_by,
										order_by : order_by,
										conn_id : conn_id
									},
									success : function(a) {
										bform.setLoading(false);
										var result = Ext.JSON.decode(a.responseText);
										if (result.success) {
											Ext.Msg.alert(langobj.info_msg_title, langobj.sql_check_success);
										} else {
											Ext.Msg.alert(langobj.errmsg, langobj.sql_check_fail + "</br>" + result.msg);
										}
									},
									failure : function(f, action) {
										bform.setLoading(false);
										Ext.Msg.alert(langobj.errmsg, langobj.system_error);
									}
								});
							}
						}
					},
					{
						xtype : 'button',
						cls : 'copybutton',
						tooltip : langs.copy,
						x : 285,
						y : 3,
						width : 30,
						height : 30,
						handler : function() {
							var bform = this.up('panel').bindform;
							var pvalue = this.up('panel').postvalue;
							var langobj = bform.languageObj;
							if (bform != null) {
								if (canEdit) {
									if (bform.getCurrMode() == PageMode.View) {
										if (bform.dataloaded) {
											var pageSize = 10;

											var gstore = Ext.create('Ext.data.Store', {
												autoLoad : false,
												fields : [ 'sql_id', 'sql_name', 'sql_context', 'group_by', 'order_by', 'auto_refresh', 'refresh_gap', 'use_marquee',
														'marquee_location', 'marquee_type', 'marquee_refresh_gap', 'use_popup', 'popup_dir', 'popup_refresh_gap', 'legend',
														'legend_lang', 'cross_conn_id', 'cross_sql_context', 'cross_order_by', 'cross_group_by', 'cross_type', 'join_key_set1',
														'join_key_set2' ],
												proxy : {
													type : 'ajax',
													url : '../../Configs/SQLEditor.dsc',
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

											var sqlid = bform.items.get(0).getValue();
											Ext.MessageBox.confirm(langobj.confirm_title, langobj.copy_kanban_confirm, function(btn) {
												if (btn == 'yes') {
													Ext.MessageBox.show({
														title : langobj.input_sql_name,
														msg : langobj.input_sql_name,
														width : 300,
														buttons : Ext.MessageBox.OKCANCEL,
														multiline : true,
														fn : function(btn, text) {
															if (btn == "ok") {
																Ext.Ajax.request({
																	method : 'POST',
																	url : '../../Configs/SQLEditor.dsc',
																	params : {
																		DCITag : pvalue,
																		uid : uid,
																		action : 'copysql',
																		//sql_id : record.data.sql_id,
																		sql_id : sqlid,
																		sql_name : text
																	},
																	success : function(a) {
																		var bodyStore = bodypanel.getGrid().getStore();
																		var result = Ext.JSON.decode(a.responseText);
																		if (result.success) {
																			//win.close();
																			if (gstore != null) {
																				if (gstore.getProxy().extraParams.hasOwnProperty('filter')) {
																					gstore.getProxy().extraParams['filter'] = " and sql_id = '" + result.msg + "'";
																				}

																				gstore.load(function(records) {
																					if (records != null && records.length > 0) {
																						var record = records[0];
																						/* var crossobj = {
																							conn_id : record.get("cross_conn_id"),
																							sql_context : record.get("cross_sql_context"),
																							order_by : record.get("cross_order_by"),
																							group_by : record.get("cross_group_by"),
																							cross_type : record.get("cross_type"),
																							join_key_set1 : record.get("join_key_set1"),
																							join_key_set2 : record.get("join_key_set2")
																						};
																						bform.items.get(19).setValue(Ext.JSON.encode(crossobj)); */

																						bform.items.get(20).mainSqlObjs = bform.items; //new cross db
																						if (crossGridStore.getProxy().extraParams.hasOwnProperty('sql_id')) {
																							crossGridStore.getProxy().extraParams['sql_id'] = record.get("sql_id");
																						}
																						crossGridStore.load(function(record) {
																							var value = [];
																							oriCrossGridStore.removeAll();
																							for ( var i = 0; i < crossGridStore.getCount(); i++) {
																								oriCrossGridStore.add(record[i].copy());
																								value.push(record[i].copy().data);
																							}
																							bform.items.get(19).setValue(Ext.JSON.encode(value));
																						});

																						var connsStore = Ext.create('Ext.data.Store', {
																							autoLoad : false,
																							fields : [ 'conns' ],
																							proxy : {
																								type : 'ajax',
																								url : '../../Configs/SQLEditor.dsc',
																								actionMethods : {
																									read : 'POST'
																								},
																								reader : {
																									root : 'items'
																								},
																								extraParams : {
																									DCITag : postvalue,
																									uid : uid,
																									action : 'sqlConns',
																									sqlid : result.msg
																								}
																							}
																						});
																						connsStore.load(function(records) {
																							if (records.length > 0) {
																								connData.setValue(records[0].get('conns'));
																								bform.loadRecord(record);
																								bform.setDataLoaded(true);

																								if (bodyStore != null) {
																									if (bodyStore.getProxy().extraParams.hasOwnProperty('keys')) {
																										bodyStore.getProxy().extraParams['keys'] = record.get("sql_id");
																										bodyStore.load(function(records) {
																											if (bodypanel != null) {
																												var obj = new Object();
																												obj["sql_id"] = record.get("sql_id");
																												bodypanel.headKeys = obj;
																												bodypanel.dataloaded(bform.dataloaded);
																											}
																										});
																									}
																								}
																								Ext.Msg.alert(langobj.info_msg_title, langobj.copy_success);
																							}
																						});
																					} else {
																						Ext.Msg.alert(langobj.errmsg, langobj.copy_fail);
																					}
																				});
																			}

																		} else {
																			Ext.Msg.alert(langobj.errmsg, langobj.copy_fail + "</br>" + result.msg);
																		}
																	},
																	failure : function(f, action) {
																		Ext.Msg.alert(langobj.errmsg, langobj.system_error);
																	}
																});
															}
														}
													});
												}
											});
										} else {
											Ext.Msg.alert(langobj.errmsg, langobj.todo_query);
										}
									} else {
										Ext.Msg.alert(langobj.errmsg, langobj.just_use_in_view);
									}
								} else {
									Ext.MessageBox.alert(langobj.errmsg, langobj.no_edit_auth);
								}

							}
						}
					}, {
						xtype : 'button',
						cls : 'viewsqlbutton',
						tooltip : langs.full_sql,
						x : 320,
						y : 3,
						width : 30,
						height : 30,
						handler : function() {
							var bform = this.up('panel').bindform;

							var sql_text = bform.items.get(17).getValue();
							var group_by = bform.items.get(4).getValue();
							var order_by = bform.items.get(5).getValue();

							if (sql_text != null && sql_text.length > 0) {
								var fullsqlwin = Ext.create('Ext.window.Window', {
									layout : 'fit',
									title : 'SQL',
									closeAction : 'hide',
									height : 500,
									width : 800,
									minHeight : 400,
									minWidth : 600,
									modal : true,
									plain : true,
									items : [ Ext.create('DCI.Customer.SqlEditor', {
										labelWidth : 0,
										readOnly : true,
										margin : '0 0 5 0'
									}) ],
									buttons : [ {
										xtype : 'button',
										text : langs.close,
										handler : function() {
											this.up('window').close();
										}
									} ],
									setInit : function(value) {
										var me = this;
										me.items.get(0).setValue(value);
									}
								});

								if (group_by != null && group_by.length > 0) {
									sql_text += "\r\ngroup by " + group_by;
								}

								if (order_by != null && order_by.length > 0) {
									sql_text += "\r\norder by " + order_by;
								}

								fullsqlwin.setInit(sql_text);
								fullsqlwin.show();
							} else {
								Ext.Msg.alert(langs.errmsg, langs.no_sql_text);
							}
						}
					}, {
						xtype : 'button',
						cls : 'sqleditwinbutton',
						tooltip : langs.edit_sql_win,
						x : 355,
						y : 3,
						width : 30,
						height : 30,
						handler : function() {
							var bform = this.up('panel').bindform;

							if (bform.getCurrMode() == PageMode.Add || bform.getCurrMode() == PageMode.Edit) {
								var sql_text = bform.items.get(17).getValue();
								var group_by = bform.items.get(4).getValue();
								var order_by = bform.items.get(5).getValue();

								var sqlpanel = Ext.create('Ext.panel.Panel', {
									region : 'center',
									layout : 'fit',
									border : 0,
									title : "",
									items : [ Ext.create('DCI.Customer.SqlEditor', {
										fieldLabel : "SQL",
										labelWidth : 30,
										margin : '0 0 5 0'
									}) ],
									setInit : function(value) {
										var me = this;
										me.items.get(0).setValue(value);
									},
									getSql : function() {
										var me = this;
										return me.items.get(0).getValue();
									}
								});

								var spanel = Ext.create('Ext.panel.Panel', {
									region : 'south',
									layout : 'column',
									border : 1,
									title : "",
									height : 90,
									items : [ Ext.create('Ext.panel.Panel', {
										columnWidth : 0.5,
										layout : 'border',
										border : 1,
										title : "",
										height : 85,
										padding : '3 2 1 3',
										items : [ {
											xtype : 'label',
											region : 'north',
											text : 'Group By :',
											height : 15
										}, Ext.create('DCI.Customer.SqlEditor', {
											region : 'center',
											labelWidth : 0,
											margin : '0 0 5 0'
										}) ]
									}), Ext.create('Ext.panel.Panel', {
										columnWidth : 0.5,
										layout : 'border',
										border : 1,
										title : "",
										height : 85,
										padding : '3 3 1 2',
										items : [ {
											xtype : 'label',
											region : 'north',
											text : 'Order By :',
											height : 15

										}, Ext.create('DCI.Customer.SqlEditor', {
											region : 'center',
											labelWidth : 0,
											margin : '0 0 5 0'
										}) ]
									}) ],
									setInit : function(gb, ob) {
										var me = this;
										me.items.get(0).items.get(1).setValue(gb);
										me.items.get(1).items.get(1).setValue(ob);
									},
									getGroup : function() {
										var me = this;
										return me.items.get(0).items.get(1).getValue();
									},
									getOrder : function() {
										var me = this;
										return me.items.get(1).items.get(1).getValue();
									}
								});

								var fullsqlwin = Ext.create('Ext.window.Window', {
									layout : 'border',
									title : 'SQL',
									closeAction : 'hide',
									height : 600,
									width : 1000,
									minHeight : 400,
									minWidth : 600,
									modal : true,
									plain : true,
									items : [ sqlpanel, spanel ],
									buttons : [ {
										xtype : 'button',
										text : langs.ok,
										handler : function() {
											var win = this.up('window');
											bform.items.get(17).setValue(win.items.get(0).getSql());
											bform.items.get(4).setValue(win.items.get(1).getGroup());
											bform.items.get(5).setValue(win.items.get(1).getOrder());
											win.close();
										}
									}, {
										xtype : 'button',
										text : langs.cancel,
										handler : function() {
											this.up('window').close();
										}
									} ],
									setInit : function(sql, gb, ob) {
										var me = this;
										me.items.get(0).setInit(sql);
										me.items.get(1).setInit(gb, ob);
									}
								});
								fullsqlwin.setInit(sql_text, group_by, order_by);
								fullsqlwin.show();
							} else {
								Ext.Msg.alert(langs.errmsg, langs.just_use_in_edit);
							}
						}
					}, {
						xtype : 'button',
						cls : 'kblink-toolbar',
						tooltip : langs.create_link,
						x : 390,
						y : 3,
						width : 30,
						height : 30,
						handler : function() {
							var bform = this.up('panel').bindform;

							if (bform.dataloaded) {
								var funcid = bform.items.get(0).getValue();
								var conngstore = bform.items.get(16).getStore();

								var subconnstore = Ext.create('Ext.data.Store', {
									fields : [ 'label', 'value' ],
									autoLoad : false,
									proxy : {
										type : 'memory',
										reader : {
											type : 'json'
										}
									}
								});

								var subuserstore = Ext.create('Ext.data.Store', {
									fields : [ 'label', 'value' ],
									autoLoad : false,
									proxy : {
										type : 'ajax',
										url : '../../Configs/SQLEditor.dsc',
										actionMethods : {
											read : 'POST'
										},
										reader : {
											root : 'items'
										},
										extraParams : {
											DCITag : postvalue,
											uid : uid,
											action : 'linkusers'
										}
									}
								});

								if (conngstore != null && conngstore.getCount() > 0) {
									subconnstore.removeAll();
									for ( var i = 0; i < conngstore.getCount(); i++) {
										subconnstore.add({
											label : conngstore.getAt(i).get("conn_name"),
											value : conngstore.getAt(i).get("conn_id")
										});
									}
								}

								var subconncombo = Ext.create('DCI.Customer.ComboBox', {
									fieldLabel : langs.conn_list,
									labelWidth : 60,
									name : 'conns',
									store : subconnstore,
									width : 250,
									defaultvalue : '',
									x : 5,
									y : 10
								});

								var subusercombo = Ext.create('DCI.Customer.ComboBox', {
									fieldLabel : langs.user_id,
									labelWidth : 40,
									name : 'users',
									store : subuserstore,
									width : 250,
									defaultvalue : '',
									x : 260,
									y : 10
								});

								var linktoolpanel = Ext.create('Ext.panel.Panel', {
									region : 'north',
									layout : 'absolute',
									border : 0,
									title : "",
									height : 40,
									items : [ subconncombo, subusercombo, {
										xtype : 'button',
										text : langs.create_link,
										x : 515,
										y : 10,
										width : 60,
										handler : function() {
											if (subconncombo.getValue() == null || subconncombo.getValue().length == 0) {
												subconncombo.markInvalid(langs.not_allow_blank);
											} else {
												Ext.Ajax.request({
													method : 'POST',
													url : '../../Configs/SQLEditor.dsc',
													params : {
														DCITag : postvalue,
														uid : uid,
														action : 'createLink',
														func_id : funcid,
														conn_id : subconncombo.getValue(),
														linkuid : subusercombo.getValue()
													},
													success : function(response) {
														if (response.responseText.indexOf("@dcifiltererrtag@$") != -1) {
															var result = response.responseText.split('$');
															if (result.length >= 2) {
																var resultdata = Ext.JSON.decode(result[1]);
																Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
																	iserror = true;
																	window.location = resultdata.result;
																});
															}
														} else {
															var result = Ext.JSON.decode(response.responseText);

															if (result.success == true) {
																if (linkfield != null) {
																	linkfield.setValue(result.linkvalue);
																}
															} else {
																Ext.Msg.alert(langs.errmsg, langs.create_link_fail);
															}
														}

													},
													failure : function(f, action) {
														Ext.Msg.alert(langs.errmsg, langs.create_link_fail + " :</br>" + action.result.errorMessage);
													}
												});
											}
										}
									} ]
								});

								var linkfield = Ext.create('Ext.form.field.TextArea', {
									grow : true,
									name : '',
									fieldLabel : '',
									readOnly : true
								});

								var linkpanel = Ext.create('Ext.panel.Panel', {
									region : 'center',
									layout : 'fit',
									border : 0,
									title : "",
									items : [ linkfield ]
								});

								var linkwin = Ext.create('Ext.window.Window', {
									layout : 'border',
									title : langs.create_link,
									closeAction : 'hide',
									height : 300,
									width : 600,
									minHeight : 300,
									minWidth : 600,
									modal : true,
									plain : true,
									items : [ linktoolpanel, linkpanel ]
								});

								subuserstore.load(function(record) {
									subusercombo.loadDefault();
									subconncombo.loadDefault();
									linkwin.show();
								});

							} else {
								Ext.Msg.alert(langs.errmsg, langs.data_never_load);
							}
						}
					} ]);

			var relationConfigWin = Ext.create('Ext.window.Window', {
				layout : 'border',
				title : langs.relation_config,
				closeAction : 'hide',
				height : 300,
				width : 570,
				minHeight : 300,
				minWidth : 570,
				modal : true,
				plain : true,
				selectedRecord : null,
				items : [ {
					xtype : 'panel',
					region : 'north',
					layout : 'absolute',
					height : 30,
					items : [ Ext.create('DCI.Customer.ComboBox', {
						fieldLabel : langs.target_sql_name,
						labelWidth : 80,
						width : 200,
						store : Ext.create('Ext.data.Store', {
							autoLoad : false,
							fields : [ 'label', 'value', 'cols' ],
							proxy : {
								type : 'ajax',
								url : '../../Configs/SQLEditor.dsc',
								actionMethods : {
									read : 'POST'
								},
								reader : {
									root : 'items'
								},
								extraParams : {
									DCITag : postvalue,
									uid : uid,
									action : 'sqlcollist'
								}
							}
						}),
						x : 0,
						y : 4,
						listeners : {
							change : function(combo, newValue, oldValue, eOpts) {
								var me = this;
								var store = me.getStore();
								var colcombo = me.up('panel').items.get(1);

								if (store == null || store.getCount() == 0) {
									colcombo.clearItems();
								} else {
									var i = 0;
									var cols = null;
									for (i = 0; i < store.getCount(); i++) {
										if (newValue == store.getAt(i).get("value")) {
											cols = store.getAt(i).get("cols");
											if (cols == null || cols.length == 0) {
												colcombo.clearItems();
											} else {
												colcombo.getStore().loadData(cols);
												colcombo.loadDefault();
											}
											break;
										}
									}
									if (i == store.getCount()) {
										colcombo.clearItems();
									}
								}
							}
						}
					}), Ext.create('DCI.Customer.ComboBox', {
						fieldLabel : langs.target_col_name,
						labelWidth : 80,
						width : 200,
						valueNotFoundText : "",
						x : 205,
						y : 4,
						clearItems : function() {
							var me = this;
							me.setValue("no_columns");
							me.getStore().loadData([]);
						}
					}), {
						xtype : 'button',
						text : langs.add,
						x : 405,
						y : 4,
						width : 40,
						handler : function() {
							var gridstore = this.up('panel').up('window').items.get(1).getStore();
							var combo1 = this.up('panel').items.get(0);
							var combo2 = this.up('panel').items.get(1);

							if (gridstore != null) {
								if (combo1.getValue() != null && combo2.getValue() != null && combo2.getValue() != "no_columns") {
									var exist = false;

									for ( var i = 0; i < gridstore.getCount(); i++) {
										if (combo1.getValue() == gridstore.getAt(i).get("target_sql_id") && combo2.getValue() == gridstore.getAt(i).get("target_col_id")) {
											exist = true;
											break;
										}
									}

									if (exist) {
										Ext.Msg.alert(langs.errmsg, langs.kanban_relation_exist);
									} else {
										var seq = 0;
										seq = (gridstore.getCount() + 1) * 10;
										gridstore.add({
											target_sql_id : combo1.getValue(),
											target_sql_name : getComboLabel(combo1, combo1.getValue()),
											target_col_id : combo2.getValue(),
											target_col_name : getComboLabel(combo2, combo2.getValue()),
											seq : seq
										});
									}
								}
							}
						}
					}, {
						xtype : 'button',
						text : langs._delete,
						x : 445,
						y : 4,
						width : 40,
						handler : function() {
							var grid = this.up('panel').up('window').items.get(1);
							var selectionModel = grid.getSelectionModel();
							if (selectionModel.hasSelection()) {
								grid.getStore().remove(grid.getSelectionModel().getSelection()[0]);
							}
						}
					}, {
						xtype : 'button',
						text : langs.clear,
						x : 485,
						y : 4,
						width : 40,
						handler : function() {
							var store = this.up('panel').up('window').items.get(1).getStore();

							if (store != null) {
								store.removeAll();
							}
						}
					} ]
				}, {
					xtype : 'grid',
					region : 'center',
					renderer : "component",
					border : 0,
					stripeRows : true,
					autoScroll : true,
					loadMask : true,
					enableTextSelection : true,
					viewConfig : {
						forceFit : false,
						autoLoad : false
					},
					columns : [ {
						text : langs.target_sql_name,
						dataIndex : 'target_sql_name',
						width : 250
					}, {
						text : langs.target_col_name,
						dataIndex : 'target_col_name',
						width : 150
					}, {
						text : langs.seq,
						dataIndex : 'seq',
						width : 100,
						editor : {
							xtype : 'numberfield',
							allowBlank : false
						}
					} ],
					store : Ext.create('Ext.data.Store', {
						fields : [ 'target_sql_id', 'target_sql_name', 'target_col_id', 'target_col_name', 'seq' ],
						autoLoad : false,
						proxy : {
							type : 'memory',
							reader : {
								type : 'json'
							}
						}
					}),
					plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
						clicksToEdit : 1,
						listeners : {
							edit : function(editor, e, eOpts) {
								var store = editor.grid.getStore();
								store.sort('seq', 'ASC');
							}
						}
					}) ]
				} ],
				buttons : [ {
					xtype : 'button',
					text : langs.ok,
					handler : function() {
						var win = this.up('window');
						var store = win.items.get(1).getStore();
						var datas = [];
						if (store != null && win.selectedRecord != null) {
							if (store.getCount() == 0) {
								win.selectedRecord.set("write_data", "");
							} else {
								for ( var i = 0; i < store.getCount(); i++) {
									datas.push({
										col_id : win.selectedRecord.get("col_id"),
										target_sql_id : store.getAt(i).get("target_sql_id"),
										target_col_id : store.getAt(i).get("target_col_id"),
										target_sql_name : store.getAt(i).get("target_sql_name"),
										target_col_name : store.getAt(i).get("target_col_name"),
										seq : store.getAt(i).get("seq")
									});
								}

								win.selectedRecord.set("write_data", Ext.encode(datas));
							}
							win.selectedRecord.set("relation_data", datas);
							win.selectedRecord.set("moditag", 1);
							win.selectedRecord.set("moditp", 'upd');
						}
						win.close();
					}
				}, {
					xtype : 'button',
					text : langs.cancel,
					handler : function() {
						this.up('window').close();
					}
				} ],
				setInitData : function(record) {
					var me = this;
					var sqlcombo = me.items.get(0).items.get(0);
					var gridstore = me.items.get(1).getStore();
					if (sqlcombo != null) {
						sqlcombo.getStore().load(function(records) {
							sqlcombo.loadDefault();
						});
					}

					if (gridstore != null) {
						if (record != null && record.get("relation_data") != null && record.get("relation_data").length > 0) {
							gridstore.loadData(record.get("relation_data"));
						} else {
							gridstore.removeAll();
						}
					}
					me.selectedRecord = record;
				}
			});

			var langChooseWin = Ext.create('Ext.window.Window', {
				layout : 'fit',
				title : '',
				closeAction : 'hide',
				height : 200,
				width : 270,
				minHeight : 200,
				minWidth : 270,
				modal : true,
				plain : true,
				record : null,
				editor : null,
				currTp : '',
				items : [ {
					xtype : 'grid',
					//region : 'center',
					renderer : "component",
					border : 0,
					hideHeaders : true,
					stripeRows : true,
					autoScroll : true,
					loadMask : true,
					enableTextSelection : true,
					viewConfig : {
						forceFit : false,
						autoLoad : false
					},
					columns : [ {
						text : langs.target_sql_name,
						dataIndex : 'lang_value',
						width : '100%'
					} ],
					store : Ext.create('Ext.data.Store', {
						fields : [ 'lang_value' ],
						autoLoad : false,
						proxy : {
							type : 'ajax',
							url : '../../Configs/SQLEditor.dsc',
							actionMethods : {
								read : 'POST'
							},
							reader : {
								root : 'items'
							},
							extraParams : {
								DCITag : postvalue,
								uid : uid,
								action : 'langList',
								langtp : '',
								key : ''
							}
						}
					}),
					listeners : {
						celldblclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
							var me = this.up('window');
							if (me.record != null) {
								if (me.currTp == 'CHT') {
									me.record.set("cht", record.data.lang_value);
								} else if (me.currTp == 'CHS') {
									me.record.set("chs", record.data.lang_value);
								}
							}

							if (me.editor != null) {
								me.editor.setValue(record.data.lang_value);
							}

							me.close();
						}
					}
				} ],
				setInitData : function(langtp, editor, record) {
					var me = this;
					var gridstore = me.items.get(0).getStore();
					if (langtp == 'CHT') {
						me.title = langs.cht;
					} else if (langtp == 'CHS') {
						me.title = langs.chs;
					}
					me.currTp = langtp;
					me.record = record;
					me.editor = editor;
					if (gridstore.getProxy().extraParams.hasOwnProperty('langtp')) {
						gridstore.getProxy().extraParams['langtp'] = langtp;
					}
					if (gridstore.getProxy().extraParams.hasOwnProperty('key')) {
						gridstore.getProxy().extraParams['key'] = record.data.col_id;
					}

					gridstore.load();
				}
			});

			var bodyplugin = Ext.create('Ext.grid.plugin.CellEditing', {
				clicksToEdit : 1,
				listeners : {
					beforeedit : function(editor, e, eOpts) {
						if (e.grid.up('panel').currmode == PageMode.View) {
							return false;
						} else {
							if (e.record.get("moditp") != "add") {
								if (e.grid.up('panel').keyfields != null) {
									var keys = e.grid.up('panel').keyfields;
									for ( var i = 0; i < keys.length; i++) {
										if (keys[i] == e.column.dataIndex) {
											return false;
										}
									}
								}
							}
						}
					},
					edit : function(editor, e, eOpts) {
						if (e.record.get("moditag") != 1) {
							if (e.record != null) {
								e.record.set("moditag", 1);
								e.record.set("moditp", "edit");
								e.record.commit();
							}
						}

						e.grid.getStore().sort({
							property : 'col_seq',
							direction : 'ASC'
						});
						e.grid.getStore().sort({
							property : 'fix_row',
							direction : 'ASC'
						});
					}
				}
			});

			var bodyColumns = [ {
				xtype : 'rownumberer',
				colid : 'colrownum',
				width : 40,
				sortable : false,
				locked : true
			}, {
				text : langs.col_id,
				dataIndex : 'col_id',
				colid : 'colcol_id',
				width : 150
			}, {
				text : langs.col_type,
				dataIndex : 'col_type',
				colid : 'colcol_type',
				width : 150,
				editor : Ext.create('Ext.grid.CellEditor', {
					field : column_type_combo,
					autoSize : {
						width : 'boundEl',
						height : 'field'
					}
				}),
				renderer : function(value) {
					try {
						value = this.transType(value);
					} catch (e) {
						if (window.console) {
							console.log(e.message);
						}
					}
					return value;
				}
			}, {
				text : langs.config_value,
				dataIndex : 'config_value',
				colid : 'colconfig_value',
				width : 200,
				editor : {
					allowBlank : true
				},
				getEditor : function(record) {
					var newValue = record.get('col_type');
					var neditor = null;
					if (newValue == "DATE") {
						neditor = date_format_combo;
					} else if (newValue == "IMAGELINK" || newValue == "CHAR") {
						neditor = Ext.create('DCI.Customer.TextField', {
							fieldLabel : "",
							labelWidth : 0,
							defaultvalue : '',
							readOnly : true,
							allowBlank : true
						});
					} else {
						neditor = Ext.create('DCI.Customer.TextField', {
							fieldLabel : "",
							labelWidth : 0,
							defaultvalue : '',
							allowBlank : true
						});
					}

					neditor.loadDefault();

					return Ext.create('Ext.grid.CellEditor', {
						field : neditor
					});
				}
			}, {
				text : langs.col_lang_key,
				dataIndex : 'col_lang_key',
				colid : 'colcol_lang_key',
				width : 150
			}, {
				text : langs.cht,
				dataIndex : 'cht',
				colid : 'colcht',
				width : 200,
				editor : {
					xtype : 'dcitriggereditor',
					allowBlank : true,
					onTriggerClick : function() {
						var grid = bodypanel.getGrid();
						var record = grid.getSelectionModel().getSelection()[0];

						if (langChooseWin != null) {
							langChooseWin.setInitData("CHT", this, record);
							langChooseWin.show();
						}
					}
				}
			}, {
				text : langs.chs,
				dataIndex : 'chs',
				colid : 'colchs',
				width : 200,
				editor : {
					xtype : 'dcitriggereditor',
					allowBlank : true,
					onTriggerClick : function() {
						var grid = bodypanel.getGrid();
						var record = grid.getSelectionModel().getSelection()[0];

						if (langChooseWin != null) {
							langChooseWin.setInitData("CHS", this, record);
							langChooseWin.show();
						}
					}
				}
			}, {
				text : langs.col_ori_name,
				dataIndex : 'col_ori_name',
				colid : 'colcol_ori_name',
				width : 200,
				editor : {
					xtype : 'textareafield',
					allowBlank : true,
					listeners : {
						paste : {
							element : 'inputEl',
							delay : 1,
							fn : function(event, inputEl) {
								if (event.type == "paste") {
									var nv = inputEl.value;
									nv = nv.replace(/(\r|\n)/g, " ");
									nv = nv.replace(/(<)/g, " < ");
									nv = nv.replace(/(>)/g, " > ");
									inputEl.value = nv;
								}
							}
						},
						change : function(comp, newValue, oldValue, eOpts) {
							newValue = newValue.replace(/(\r|\n)/g, " ");
							if (newValue.indexOf(" < ") == -1) {
								newValue = newValue.replace(/(<)/g, " < ");
							}
							if (newValue.indexOf(" > ") == -1) {
								newValue = newValue.replace(/(>)/g, " > ");
							}
							comp.setValue(newValue);
						}
					}
				}
			}, {
				text : langs.col_width,
				dataIndex : 'col_width',
				colid : 'colcol_width',
				width : 100,
				editor : {
					xtype : 'numberfield',
					allowBlank : false,
					minValue : 0,
					maxValue : 5000
				}
			}, {
				text : langs.col_seq,
				dataIndex : 'col_seq',
				colid : 'colcol_seq',
				width : 100,
				editor : {
					xtype : 'numberfield',
					allowBlank : false,
					minValue : 0,
					maxValue : 99999
				}
			}, {
				text : langs.col_relation,
				dataIndex : 'relation_image',
				colid : 'colrelation',
				align : 'center',
				width : 100,
				renderer : function(value, metaData, record) {
					if (record.data.relation_data != null && record.data.relation_data != '') {
						metaData.style = 'background-color:#FFFFBB !important;';
					}
					return "<img src='" + value + "' />";
				}
			}, {
				xtype : 'dcicheckcolumn',
				text : langs.visible,
				dataIndex : 'visible',
				colid : 'colvisible',
				width : 100
			}, {
				xtype : 'dcicheckcolumn',
				text : langs.is_popup,
				dataIndex : 'is_popup',
				colid : 'colis_popup',
				width : 100
			}, {
				xtype : 'dcicheckcolumn',
				text : langs.popup_title,
				dataIndex : 'popup_title',
				colid : 'colpopup_title',
				width : 100
			}, {
				text : langs.calc_rule,
				dataIndex : 'calc_rule',
				colid : 'colcalc_rule',
				width : 200,
				editor : {
					allowBlank : true
				}
			} ];

			var bodygrid = Ext.create('Ext.grid.Panel', {
				region : 'center',
				stripeRows : true,
				autoScroll : true,
				loadMask : true,
				allowDeselect : true,
				selModel : {
					allowDeselect : true
				},
				enableTextSelection : true,
				viewConfig : {
					forceFit : false,
					autoLoad : false
				},
				columns : [],
				transSource : [],
				transType : function(value) {
					for ( var i = 0; i < this.transSource.length; i++) {
						if (this.transSource[i].value == value) {
							value = this.transSource[i].label;
							break;
						}
					}
					//value = getComboLabel(column_type_combo, value);
					return value;
				},
				store : Ext.create('Ext.data.Store', {
					autoLoad : false,
					fields : [ 'sql_id', 'col_id', 'col_lang_key', 'col_type', 'col_ori_name', 'col_width', 'col_seq', 'locked', 'config_value', 'cht', 'chs', 'moditag', 'moditp',
							"relation_image", "relation_data", {
								name : 'visible',
								type : 'bool'
							}, {
								name : 'is_popup',
								type : 'bool'
							}, {
								name : 'popup_title',
								type : 'bool'
							}, "calc_rule" ],
					proxy : {
						type : 'ajax',
						url : '../../Configs/SQLEditor.dsc',
						actionMethods : {
							read : 'POST'
						},
						reader : {
							root : 'items'
						},
						extraParams : {
							DCITag : postvalue,
							uid : uid,
							action : 'bodyData',
							keys : ''
						}
					}
				}),
				listeners : {
					celldblclick : function(view, td, cellIndex, record, tr, rowIndex, e, eOpts) {
						var me = bodypanel;
						if (me.currmode == PageMode.Edit) {
							if (view.headerCt.gridDataColumns[cellIndex].dataIndex == "relation_image") {
								if (me.configWin != null) {
									me.configWin.setInitData(record);
									me.configWin.show();
								}
							} else if (view.headerCt.gridDataColumns[cellIndex].colid == 'colchtpicker') {
								alert("cht");
							} else if (view.headerCt.gridDataColumns[cellIndex].colid == 'colchspicker') {
								alert("chs");
							}
						}
					}
				},
				plugins : [ bodyplugin ]
			});

			var bodypanel = Ext.create('Ext.panel.Panel', {
				region : 'center',
				layout : 'border',
				border : 0,
				bodyStyle : {
					background : "#eaf1fb"
				},
				bodyPadding : 5,
				headform : headform,
				actionurl : '../../Configs/SQLEditor.dsc',
				languageObj : langs,
				gridid : 'SQLEditor_G01',
				currmode : PageMode.View,
				dataloaded : false,
				postvalue : postvalue,
				keyfields : [ 'sql_id', 'col_id' ],
				headKeys : {},
				canEdit : canEdit,
				oriGColumns : bodyColumns,
				stopUsingBtn : [ 0, 2 ],
				configWin : relationConfigWin,
				uid : uid,
				cusFormatIconPath : '',
				tools : [ {
					type : 'up',
					headHeight : 0,
					handler : function() {
						var me = this;
						var panel = me.up('panel');
						var hpanel = panel.headform.up('panel');
						if (hpanel != null) {
							if (me.headHeight == 0) {
								me.setType('down');
								me.headHeight = hpanel.getHeight();
								hpanel.setHeight(0);
							} else {
								me.setType('up');
								hpanel.setHeight(me.headHeight);
								me.headHeight = 0;
							}
						}
					}
				} ],
				items : [ {
					xtype : 'panel',
					region : 'north',
					border : 0,
					layout : 'absolute',
					height : 40,
					x : 400,
					y : 0,
					items : [ {
						xtype : 'button',
						// text : this.up('panel').up('panel').languageObj == null ? 'add' :
						// this.up('panel').up('panel').languageObj.add,
						width : 50,
						disabled : true,
						x : 0,
						y : 5,
						handler : function() {
							var currmode = this.up('panel').up('panel').currmode;

							if (currmode == PageMode.Edit) {
								this.up('panel').up('panel').addNewRow();
							}
						}
					}, {
						xtype : 'button',
						// text : this.up('panel').up('panel').languageObj == null ? 'save'
						// : this.up('panel').up('panel').languageObj.save,
						disabled : true,
						width : 50,
						x : 55,
						y : 5,
						handler : function() {
							var bodypanel = this.up('panel').up('panel');
							var currmode = bodypanel.currmode;

							if (currmode == PageMode.Edit) {
								if (bodypanel.valided()) {
									var langobj = bodypanel.languageObj;
									var store = bodypanel.items.get(1).getStore();
									if (bodypanel.items.get(1).plugins[0] != null) {
										bodypanel.items.get(1).plugins[0].completeEdit();
									}
									var datas = [];
									var addRows = 0;
									for ( var i = 0; i < store.getCount(); i++) {
										if (store.getAt(i).get("moditag") == 1) {
											datas.push(store.getAt(i).data);
											if (store.getAt(i).get("moditp") == 'add') {
												addRows++;
											}
										}
									}

									Ext.Ajax.request({
										method : 'POST',
										url : bodypanel.actionurl,
										params : {
											DCITag : bodypanel.postvalue,
											uid : bodypanel.uid,
											action : 'bodySave',
											datas : Ext.encode(datas),
											allAdd : store.getCount() == addRows
										},
										success : function(a) {
											bodypanel.gridReload();
											Ext.Msg.alert(langobj.save_result_title, langobj.save_success);
										},
										failure : function(f, action) {
											Ext.Msg.alert(langobj.save_result_title, langobj.save_fail + " :</br>" + action.result.errorMessage);
										}
									});
								}
							}
						}
					}, {
						xtype : 'button',
						// text : this.up('panel').up('panel').languageObj == null ?
						// '_delete' : this.up('panel').up('panel').languageObj._delete,
						disabled : true,
						width : 50,
						x : 110,
						y : 5,
						handler : function() {
							var bodypanel = this.up('panel').up('panel');
							var currmode = bodypanel.currmode;
							var grid = bodypanel.items.get(1);
							var langobj = bodypanel.languageObj;
							var url = bodypanel.actionurl;
							Ext.MessageBox.confirm(langobj.delete_confirm_title, langobj.delete_confirm_msg, function(btn) {
								if (btn == 'yes') {
									if (currmode == PageMode.Edit) {
										if (grid != null) {
											var s = grid.getSelectionModel().getSelection();
											if (s.length > 0) {
												Ext.Ajax.request({
													method : 'POST',
													url : url,
													params : {
														DCITag : bodypanel.postvalue,
														uid : bodypanel.uid,
														action : 'bodyDelete',
														datas : Ext.encode(s[0].data)
													},
													success : function(a) {
														bodypanel.gridReload();
														Ext.Msg.alert(langobj.delete_result_title, langobj.delete_success);
													},
													failure : function(f, action) {
														Ext.Msg.alert(langobj.delete_result_title, langobj.delete_fail + " :</br>" + action.result.errorMessage);
													}
												});
											}
										}
									}
								}
							});
						}
					}, {
						xtype : 'button',
						// text : 'view',
						disabled : true,
						width : 100,
						x : 165,
						y : 5,
						handler : function() {
							var bodypanel = this.up('panel').up('panel');
							if (bodypanel.canEdit) {
								bodypanel.changeMode(bodypanel.currmode);
								if (bodypanel.currmode == PageMode.View) {
									bodypanel.gridReload();
								}

							} else {
								Ext.MessageBox.alert(this.up('panel').up('panel').languageObj.errmsg, this.up('panel').up('panel').languageObj.no_edit_auth);
							}
						}
					}, {
						xtype : 'button',
						width : 100,
						x : 270,
						y : 5,
						handler : function() {
							var panel = this.up('panel').up('panel');
							var grid = panel.items.get(1);
							var cols = grid.getView().headerCt.getGridColumns();
							var langobj = panel.languageObj;
							var colinfo = [];
							for ( var i = 0; i < cols.length; i++) {
								var obj = new Object();
								obj['col_id'] = cols[i].colid;
								obj['col_index'] = cols[i].getIndex();
								obj['col_width'] = cols[i].width;
								obj['col_visible'] = cols[i].hidden ? 0 : 1;
								colinfo.push(obj);
							}

							Ext.Ajax.request({
								method : 'POST',
								url : './../../PublicCtrl.dsc',
								params : {
									DCITag : this.up('panel').up('panel').postvalue,
									uid : this.up('panel').up('panel').uid,
									action : 'saveGFormat',
									gridid : this.up('panel').up('panel').gridid,
									datas : Ext.encode(colinfo)
								},
								success : function(a) {
									panel.addCusFormatIcon();
									Ext.Msg.alert(langobj.save_result_title, langobj.save_success);
								},
								failure : function(f, action) {
									Ext.Msg.alert(langobj.save_result_title, langobj.save_fail + " :</br>" + action.result.errorMessage);
								}
							});
						}
					}, {
						xtype : 'button',
						width : 120,
						x : 375,
						y : 5,
						handler : function() {
							var panel = this.up('panel').up('panel');
							var grid = panel.items.get(1);
							var langobj = panel.languageObj;
							var ogc = panel.getOGC();
							Ext.MessageBox.confirm(langobj.load_def_confirm_title, langobj.load_def_confirm_msg, function(btn) {
								if (btn == 'yes') {
									Ext.Ajax.request({
										method : 'POST',
										url : './../../PublicCtrl.dsc',
										params : {
											DCITag : panel.postvalue,
											uid : panel.uid,
											action : 'clearGFormat',
											gridid : panel.gridid
										},
										success : function(a) {
											grid.reconfigure(null, ogc);
											panel.rmCusFormatIcon();
											Ext.Msg.alert(langobj.load_def_result_title, langobj.load_def_success);
										},
										failure : function(f, action) {
											Ext.Msg.alert(langobj.load_def_result_title, langobj.load_def_fail + " :</br>" + action.result.errorMessage);
										}
									});
								}
							});
						}
					} ]
				}, bodygrid ],
				getOGC : function() {
					return this.oriGColumns;
				},
				isCusFormatExist : function() {
					var panel = this.items.get(0);
					var exist = false;

					for ( var i = 0; i < panel.items.length; i++) {
						if (panel.items.get(i).iconid == 'cusicon') {
							exist = true;
							break;
						}
					}

					return exist;
				},
				addCusFormatIcon : function() {
					var me = this;
					var panel = this.items.get(0);
					var lastItemIdx = panel.items.length - 1;

					if (!me.isCusFormatExist()) {
						panel.items.insert(panel.items.length, Ext.create('DCI.Customer.Img', {
							iconid : 'cusicon',
							src : me.cusFormatIconPath,
							x : panel.items.get(lastItemIdx).x + panel.items.get(lastItemIdx).width + 2,
							y : 8,
							width : 20,
							height : 20,
							listeners : {
								afterrender : function(c) {
									Ext.create('Ext.tip.ToolTip', {
										target : c.getEl(),
										html : me.languageObj.cus_format
									});
								}
							}
						}));
						panel.doLayout();
					}
				},
				rmCusFormatIcon : function() {
					var me = this;
					var panel = this.items.get(0);
					if (me.isCusFormatExist()) {
						panel.items.get(panel.items.length - 1).destroy();
						panel.items.remove(panel.items.get(panel.items.length - 1));
						panel.doLayout();
					}
				},
				initBody : function() {
					var me = this;
					var bodyGrid = this.items.get(1);
					var panel = me.items.get(0);
					var store = bodyGrid.getStore();
					var columns = bodyColumns;//bodyGrid.columns;
					var tmpcols = [];
					me.oriGColumns = [];

					if (me.cusFormatIconPath == null || me.cusFormatIconPath == '') {
						me.cusFormatIconPath = "../../images/icons/CusGridFormat.png";
					}

					for ( var i = 0; i < columns.length; i++) {
						me.oriGColumns.push(columns[i]);
					}

					var colsStore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'colFormats' ],
						proxy : {
							type : 'ajax',
							url : './../../PublicCtrl.dsc',
							actionMethods : {
								read : 'POST'
							},
							reader : {
								type : 'json'
							},
							extraParams : {
								DCITag : me.postvalue,
								uid : me.uid,
								action : 'getGFormat',
								gridid : me.gridid
							}
						}
					});

					colsStore.load(function(record) {
						if (record.length > 0) {
							var tmpstore = Ext.create('Ext.data.Store', {
								fields : [ 'col_id', 'col_index', 'col_width', 'col_visible' ],
								autoLoad : false,
								proxy : {
									type : 'memory',
									reader : {
										type : 'json'
									}
								}
							});

							tmpstore.loadData(record[0].get('colFormats'));

							if (tmpstore.getCount() > 0) {
								me.addCusFormatIcon();
								for ( var i = 0; i < tmpstore.getCount(); i++) {
									for ( var j = 0; j < columns.length; j++) {
										if (columns[j].colid == tmpstore.getAt(i).get('col_id')) {
											if (tmpstore.getAt(i).get('col_visible') == '1') {
												if (columns[j].hasOwnProperty('hidden')) {
													columns[j].hidden = false;
												} else {
													columns[j]['hidden'] = false;
												}

												if (tmpstore.getAt(i).get('col_width') != 0) {
													if (columns[j].hasOwnProperty('width')) {
														columns[j].width = parseInt(tmpstore.getAt(i).get('col_width'));
													} else {
														columns[j]['width'] = parseInt(tmpstore.getAt(i).get('col_width'));
													}
												}
											} else {
												if (tmpstore.getAt(i).get('col_width') != 0) {
													if (columns[j].hasOwnProperty('width')) {
														columns[j].width = parseInt(tmpstore.getAt(i).get('col_width'));
													} else {
														columns[j]['width'] = parseInt(tmpstore.getAt(i).get('col_width'));
													}
												}

												if (columns[j].hasOwnProperty('hidden')) {
													columns[j].hidden = true;
												} else {
													columns[j]['hidden'] = true;
												}
											}

											tmpcols.push(columns[j]);
											columns.splice(j, 1);
											break;
										}
									}
								}

								if (columns != null && columns.leght != 0) {
									for ( var i = 0; i < columns.length; i++) {
										columns[i]['hidden'] = true;
										tmpcols.push(columns[i]);
									}
								}

								bodyGrid.reconfigure(null, tmpcols);
							}
						}

						if (bodyGrid.columns == null || bodyGrid.columns.length == 0) {
							bodyGrid.reconfigure(null, me.oriGColumns);
						}
					});

					me.changeMode(PageMode.Edit);
					me.dataloaded(false);

					if (me.languageObj != null) {
						panel.items.get(0).setText(me.languageObj.add);
						panel.items.get(1).setText(me.languageObj.save);
						panel.items.get(2).setText(me.languageObj._delete);
						panel.items.get(3).setText(me.languageObj.to_edit);
						panel.items.get(4).setText(me.languageObj.save_format);
						panel.items.get(5).setText(me.languageObj.load_def_format);
					}

					if (this.headform != null) {
						this.headform.bodypanel = this;
					}
				},
				getCurrMode : function() {
					return this.currmode;
				},
				addNewRow : function() {

				},
				dataloaded : function(loaded) {
					var btn = this.items.get(0).items.get(3);
					if (btn != null) {
						btn.setDisabled(!loaded);
						if (!loaded) {
							this.changeMode(PageMode.Edit);
						}
					}
				},
				clearGrid : function() {
					var store = this.items.get(1).getStore();
					if (store != null) {
						store.removeAll();
					}
				},
				changeMode : function(mode) {
					var me = this;
					var toolbtns = this.items.get(0).items;

					if (mode == PageMode.View) {
						for ( var i = 0; i <= 3; i++) {
							if (i == 3) {
								toolbtns.get(i).setText(me.languageObj.to_view);
							} else {
								if (this.stopUsingBtn != null && this.stopUsingBtn.length > 0) {
									var stopuse = false;
									for ( var j = 0; j < this.stopUsingBtn.length; j++) {
										if (this.stopUsingBtn[j] == i) {
											stopuse = true;
											break;
										}
									}

									toolbtns.get(i).setDisabled(stopuse);
								} else {
									toolbtns.get(i).setDisabled(false);
								}
							}
						}
						this.currmode = PageMode.Edit;
					} else {
						for ( var i = 0; i <= 3; i++) {
							if (i == 3) {
								toolbtns.get(i).setText(me.languageObj.to_edit);
							} else {
								toolbtns.get(i).setDisabled(true);
							}
						}
						this.currmode = PageMode.View;
					}
				},
				setNewCondi : function() {
					var me = this;
					var grid = me.getGrid();
					var store = grid.getStore();
					var head = me.headform;
					if (store != null) {
						if (store.getProxy().extraParams.hasOwnProperty('keys')) {
							store.getProxy().extraParams['keys'] = head.items.get(0).getValue();
						}
					}
				},
				gridReload : function() {
					var bodypanel = this;
					var grid = bodypanel.items.get(1);
					if (grid != null) {
						grid.getStore().load();
					}
				},
				getGrid : function() {
					var me = this;
					return me.items.get(1);
				},
				valided : function() {
					return true;
				}
			});

			/*
			 var bodypanel = Ext.create('DCI.Customer.BodyGridPanel', {
			 title : langs.sql_col_edit,
			 languageObj : langs,
			 gridid : 'SQLEditor_G01',
			 actionurl : '../../Configs/SQLEditor.dsc',
			 postvalue : postvalue,
			 uid : uid,
			 headform : headform,
			 keyfields : [ 'sql_id', 'col_id' ],
			 canEdit : canEdit,
			 stopUsingBtn : [ 0, 2 ],
			 configWin : relationConfigWin,
			 tools : [ {
			 type : 'up',
			 headHeight : 0,
			 handler : function() {
			 var me = this;
			 var panel = me.up('panel');
			 var hpanel = panel.headform.up('panel');
			 if (hpanel != null) {
			 if (me.headHeight == 0) {
			 me.setType('down');
			 me.headHeight = hpanel.getHeight();
			 hpanel.setHeight(0);
			 } else {
			 me.setType('up');
			 hpanel.setHeight(me.headHeight);
			 me.headHeight = 0;
			 }
			 }
			 }
			 } ],
			 addNewRow : function() {
			 var grid = this.items.get(1);
			 var gridStore = grid.getStore();
			 if (gridStore != null) {
			 gridStore.add({
			 sql_id : this.headKeys.sql_id,
			 col_id : '',
			 col_lang_key : '',
			 col_type : '',
			 col_ori_name : '',
			 col_width : 100,
			 col_seq : '',
			 visible : 1,
			 is_popup : 0,
			 popup_title : 0,
			 config_value : '',
			 cht : '',
			 chs : '',
			 moditag : 1,
			 moditp : 'add',
			 relation_image : './../../images/button_icon/BtnConfig.png',
			 relation_data : '',
			 calc_rule : ''
			 });

			 grid.getView().select(gridStore.getCount() - 1);
			 }
			 },
			 griddbclick : function(view, td, cellIndex, record, tr, rowIndex, e, eOpts) {
			 var me = this;
			 if (me.currmode == PageMode.Edit) {
			 if (view.headerCt.gridDataColumns[cellIndex].dataIndex == "relation_image") {
			 if (me.configWin != null) {
			 me.configWin.setInitData(record);
			 me.configWin.show();
			 }
			 } else if (view.headerCt.gridDataColumns[cellIndex].colid == 'colchtpicker') {
			 alert("cht");
			 } else if (view.headerCt.gridDataColumns[cellIndex].colid == 'colchspicker') {
			 alert("chs");
			 }
			 }
			 },
			 setNewCondi : function() {
			 var me = this;
			 var grid = me.getGrid();
			 var store = grid.getStore();
			 var head = me.headform;
			 if (store != null) {
			 if (store.getProxy().extraParams.hasOwnProperty('keys')) {
			 store.getProxy().extraParams['keys'] = head.items.get(0).getValue();
			 }
			 }

			 }
			 });
			 */
			Ext.define('DCI.Customer.TriggerEditor', {
				extend : 'Ext.form.field.Trigger',
				alias : 'DCI.Customer.TriggerEditor',
				xtype : 'dcitriggereditor',
				onTriggerClick : function() {

				}
			});

			/* 	var bodyColumns = [ {
					xtype : 'rownumberer',
					colid : 'colrownum',
					width : 40,
					sortable : false,
					locked : true
				}, {
					text : langs.col_id,
					dataIndex : 'col_id',
					colid : 'colcol_id',
					width : 150
				}, {
					text : langs.col_type,
					dataIndex : 'col_type',
					colid : 'colcol_type',
					width : 150,
					editor : column_type_combo,
					renderer : function(value) {
						return getComboLabel(column_type_combo, value);
					}
				}, {
					text : langs.config_value,
					dataIndex : 'config_value',
					colid : 'colconfig_value',
					width : 200,
					editor : {
						allowBlank : true
					},
					getEditor : function(record) {
						var newValue = record.get('col_type');
						var neditor = null;
						if (newValue == "DATE") {
							neditor = date_format_combo;
						} else if (newValue == "IMAGELINK" || newValue == "CHAR") {
							neditor = Ext.create('DCI.Customer.TextField', {
								fieldLabel : "",
								labelWidth : 0,
								width : '100%',
								defaultvalue : '',
								readOnly : true,
								allowBlank : true
							});
						} else {
							neditor = Ext.create('DCI.Customer.TextField', {
								fieldLabel : "",
								labelWidth : 0,
								width : '100%',
								defaultvalue : '',
								allowBlank : true
							});
						}

						neditor.loadDefault();

						return Ext.create('Ext.grid.CellEditor', {
							field : neditor
						});
					}
				}, {
					text : langs.col_lang_key,
					dataIndex : 'col_lang_key',
					colid : 'colcol_lang_key',
					width : 150
				}, {
					text : langs.cht,
					dataIndex : 'cht',
					colid : 'colcht',
					width : 200,
					editor : {
						xtype : 'dcitriggereditor',
						allowBlank : true,
						onTriggerClick : function() {
							var grid = bodypanel.getGrid();
							var record = grid.getSelectionModel().getSelection()[0];

							if (langChooseWin != null) {
								langChooseWin.setInitData("CHT", this, record);
								langChooseWin.show();
							}
						}
					}
				}, {
					text : langs.chs,
					dataIndex : 'chs',
					colid : 'colchs',
					width : 200,
					editor : {
						xtype : 'dcitriggereditor',
						allowBlank : true,
						onTriggerClick : function() {
							var grid = bodypanel.getGrid();
							var record = grid.getSelectionModel().getSelection()[0];

							if (langChooseWin != null) {
								langChooseWin.setInitData("CHS", this, record);
								langChooseWin.show();
							}
						}
					}
				}, {
					text : langs.col_ori_name,
					dataIndex : 'col_ori_name',
					colid : 'colcol_ori_name',
					width : 200,
					editor : {
						xtype : 'textareafield',
						allowBlank : true,
						listeners : {
							paste : {
								element : 'inputEl',
								delay : 1,
								fn : function(event, inputEl) {
									if (event.type == "paste") {
										var nv = inputEl.value;
										nv = nv.replace(/(\r|\n)/g, " ");
										nv = nv.replace(/(<)/g, " < ");
										nv = nv.replace(/(>)/g, " > ");
										inputEl.value = nv;
									}
								}
							},
							change : function(comp, newValue, oldValue, eOpts) {
								newValue = newValue.replace(/(\r|\n)/g, " ");
								if (newValue.indexOf(" < ") == -1) {
									newValue = newValue.replace(/(<)/g, " < ");
								}
								if (newValue.indexOf(" > ") == -1) {
									newValue = newValue.replace(/(>)/g, " > ");
								}
								comp.setValue(newValue);
							}
						}
					}
				}, {
					text : langs.col_width,
					dataIndex : 'col_width',
					colid : 'colcol_width',
					width : 100,
					editor : {
						xtype : 'numberfield',
						allowBlank : false,
						minValue : 0,
						maxValue : 5000
					}
				}, {
					text : langs.col_seq,
					dataIndex : 'col_seq',
					colid : 'colcol_seq',
					width : 100,
					editor : {
						xtype : 'numberfield',
						allowBlank : false,
						minValue : 0,
						maxValue : 99999
					}
				}, {
					text : langs.col_relation,
					dataIndex : 'relation_image',
					colid : 'colrelation',
					align : 'center',
					width : 100,
					renderer : function(value, metaData, record) {
						if (record.data.relation_data != null && record.data.relation_data != '') {
							metaData.style = 'background-color:#FFFFBB !important;';
						}
						return "<img src='" + value + "' />";
					}
				}, {
					xtype : 'dcicheckcolumn',
					text : langs.visible,
					dataIndex : 'visible',
					colid : 'colvisible',
					width : 100
				}, {
					xtype : 'dcicheckcolumn',
					text : langs.is_popup,
					dataIndex : 'is_popup',
					colid : 'colis_popup',
					width : 100
				}, {
					xtype : 'dcicheckcolumn',
					text : langs.popup_title,
					dataIndex : 'popup_title',
					colid : 'colpopup_title',
					width : 100
				}, {
					text : langs.calc_rule,
					dataIndex : 'calc_rule',
					colid : 'colcalc_rule',
					width : 200,
					editor : {
						allowBlank : true
					}
				} ];

				var bodyStore = Ext.create('Ext.data.Store', {
					autoLoad : false,
					fields : [ 'sql_id', 'col_id', 'col_lang_key', 'col_type', 'col_ori_name', 'col_width', 'col_seq', 'locked', 'config_value', 'cht', 'chs', 'moditag', 'moditp',
							"relation_image", "relation_data", {
								name : 'visible',
								type : 'bool'
							}, {
								name : 'is_popup',
								type : 'bool'
							}, {
								name : 'popup_title',
								type : 'bool'
							}, "calc_rule" ],
					proxy : {
						type : 'ajax',
						url : '../../Configs/SQLEditor.dsc',
						actionMethods : {
							read : 'POST'
						},
						reader : {
							root : 'items'
						},
						extraParams : {
							DCITag : postvalue,
							uid : uid,
							action : 'bodyData',
							keys : ''
						}
					}
				}); */

			bodypanel.initBody();//(bodyStore, bodyColumns);

			var main = Ext.create('DCI.Customer.SubPanel', {
				id : 'SQLEditorMain',
				renderTo : 'SQLEditorPage',
				minWidth : 1020,
				minHeight : 500,
				border : 0,
				bodyPadding : '0 5 5 5',
				layout : 'border',
				widthChangeControls : [ headpanel, bodypanel ],
				items : [ headpanel, bodypanel ]
			});

			var initQueryStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'coltps', 'formats' ],
				proxy : {
					type : 'ajax',
					url : '../../Configs/SQLEditor.dsc',
					actionMethods : {
						read : 'POST'
					},
					reader : {
						type : 'json'
					},
					extraParams : {
						DCITag : postvalue,
						uid : uid,
						action : 'init'
					}
				}
			});

			initQueryStore.load(function(record) {
				if (record.length > 0) {
					column_type_combo.getStore().loadData(record[0].get("coltps"));
					column_type_combo.loadDefault();
					date_format_combo.getStore().loadData(record[0].get("formats"));
					date_format_combo.loadDefault();
					bodygrid.transSource = record[0].get("coltps");
				}
			});

			main.resize(Ext.get("SQLEditorPage").getWidth(), Ext.get("SQLEditorPage").getHeight());
		}
	});
</script>
</head>
<body>
	<div id="SQLEditorPage" class="page_div"></div>
</body>
</html>