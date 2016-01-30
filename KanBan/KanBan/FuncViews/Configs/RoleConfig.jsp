<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title><dcitag:reqParam paramName="func_name"></dcitag:reqParam></title>

<script type="text/javascript" charset="UTF-8">
var t1,t2,t3;
	Ext.onReady(function() {
		var localKeys = [ "save_msg", "delete_msg", "delete_fail", "delete_success", "delete_result_title", "delete_confirm_title", "delete_confirm_msg", "delete_fail",
				"save_fail", "save_success", "save_result_title", "save_fail", "role_id", "role_name", "role_desc", "system_build", "visible", "enable", "disable", "func_id",
				"conn_id", "can_edit", "group_role_edit", "toolbar_query_title", "system_def_can_not_edit", "confirm_title", "data_lose_warning", "role_exist", "load_def_format",
				"load_def_fail", "load_def_success", "load_def_result_title", "load_def_confirm_title", "load_def_confirm_msg", "cus_format", "copy_fail", "input_role_name",
				"todo_query", "just_use_in_view", "copy_role_confirm", "copy_success", "info_msg_title", "copy" ];
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
			var visible_combo = Ext.create('DCI.Customer.ComboBox', {
				fieldLabel : langs.visible,
				name : 'visible',
				store : {
					fields : [ 'label', 'value' ],
					data : [ {
						"label" : langs.enable,
						"value" : '1'
					}, {
						"label" : langs.disable,
						"value" : '0'
					} ]
				},
				defaultvalue : '1',
				x : 500,
				y : 10
			});

			var headform = Ext.create('DCI.Customer.HeadFormPanel', {
				height : 100,
				url : '../../Configs/RoleConfig.dsc',
				languageObj : langs,
				items : [ Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.role_id,
					emptyText : '',
					name : 'role_id',
					allowBlank : false,
					ispk : true,
					canEdit : false,
					defaultvalue : langs.system_build,
					x : 0,
					y : 10
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.role_name,
					emptyText : '',
					name : 'role_name',
					allowBlank : true,
					x : 250,
					y : 10
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.role_desc,
					emptyText : '',
					name : 'role_desc',
					allowBlank : true,
					width : 465,
					x : 0,
					y : 50
				}), visible_combo ],
				showQueryWindow : function() {
					var me = this;
					me.initQueryWindow();
					var pageSize = 10;
					var initStore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'cols' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/RoleConfig.dsc',
							actionMethods : {
								read : 'POST'
							},
							reader : {
								type : 'json'
							},
							extraParams : {
								DCITag : postvalue,
								uid : uid
							}
						}
					});

					var gstore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'role_id', 'role_name', 'role_desc', 'visible' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/RoleConfig.dsc',
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
						text : langs.role_id,
						dataIndex : 'role_id',
						width : 200
					}, {
						text : langs.role_name,
						dataIndex : 'role_name',
						width : 200
					} ];

					var openwin = Ext.create('DCI.Customer.QueryWindow', {
						height : 335,
						width : 450,
						headform : me,
						title : langs.toolbar_query_title,
						loadHeadData : function(record) {
							this.headform.loadRecord(record);
							this.headform.setDataLoaded(true);
							if (bodyStore != null) {
								if (bodyStore.getProxy().extraParams.hasOwnProperty('keys')) {
									bodyStore.getProxy().extraParams['keys'] = record.get("role_id");
									bodyStore.load();
								}
							}
							if (bodypanel != null) {
								var obj = new Object();
								obj["role_id"] = record.get("role_id");
								bodypanel.headKeys = obj;
								bodypanel.dataloaded(this.headform.dataloaded);
							}
							this.close();
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
				height : 160,
				headform : headform,
				postvalue : postvalue,
				uid : uid,
				canEdit : canEdit,
				bodyPadding : '0 0 10 0',
				keepAdd : false,
				exceptionEditAuth : function() {
					var result = [ "1", "" ];
					var form = this.bindform;
					if (headform != null) {
						if (form.items.get(0).getValue() == "RSYS") {
							result = [ "0", langs.system_def_can_not_edit ];
						}
					}
					return result;
				}
			});

			headpanel.setCusButtons([ {
				xtype : 'button',
				cls : 'copybutton',
				tooltip : langs.copy,
				x : 215,
				y : 3,
				width : 30,
				height : 30,
				handler : function() {
					var bform = this.up('panel').bindform;
					var pvalue = this.up('panel').postvalue;
					var langobj = bform.languageObj;
					if (bform != null) {
						if (bform.getCurrMode() == PageMode.View) {
							if (bform.dataloaded) {
								var pageSize = 10;

								var gstore = Ext.create('Ext.data.Store', {
									autoLoad : false,
									fields : [ 'role_id', 'role_name', 'role_desc', 'visible' ],
									proxy : {
										type : 'ajax',
										url : '../../Configs/RoleConfig.dsc',
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

								var roleid = bform.items.get(0).getValue();
								Ext.MessageBox.confirm(langobj.confirm_title, langobj.copy_role_confirm, function(btn) {
									if (btn == 'yes') {
										Ext.MessageBox.show({
											title : langobj.input_role_name,
											msg : langobj.input_role_name,
											width : 300,
											buttons : Ext.MessageBox.OKCANCEL,
											multiline : true,
											fn : function(btn, text) {
												if (btn == "ok") {
													Ext.Ajax.request({
														method : 'POST',
														url : '../../Configs/RoleConfig.dsc',
														params : {
															DCITag : pvalue,
															uid : uid,
															action : 'copyrole',
															role_id : roleid,
															role_name : text
														},
														success : function(a) {
															var result = Ext.JSON.decode(a.responseText);
															if (result.success) {
																if (gstore != null) {
																	if (gstore.getProxy().extraParams.hasOwnProperty('filter')) {
																		gstore.getProxy().extraParams['filter'] = " and role_id = '" + result.msg + "'";
																	}

																	gstore.load(function(records) {
																		if (records != null && records.length > 0) {
																			bform.loadRecord(records[0]);
																			bform.setDataLoaded(true);
																			if (bodyStore != null) {
																				if (bodyStore.getProxy().extraParams.hasOwnProperty('keys')) {
																					bodyStore.getProxy().extraParams['keys'] = result.msg;
																					bodyStore.load();
																				}
																			}
																			if (bodypanel != null) {
																				var obj = new Object();
																				obj["role_id"] = result.msg;
																				bodypanel.headKeys = obj;
																				bodypanel.dataloaded(bform.dataloaded);
																			}
																			Ext.Msg.alert(langobj.info_msg_title, langobj.copy_success);
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
					}
				}
			} ]);

			var conn_Combo = Ext.create('DCI.Customer.ComboBox', {
				labelWidth : 0
			});

			var func_Combo = Ext.create('DCI.Customer.ComboBox', {
				labelWidth : 0,
				store : {
					fields : [ 'label', 'value', 'package' ],
					autoLoad : false,
					proxy : {
						type : 'memory',
						reader : {
							type : 'json'
						}
					}
				},
				listeners : {
					change : function(comp, newValue, oldValue, eOpts) {
						var me = this;
						var store = me.getStore();
						var gridStore = me.up().up().getStore();
						for ( var i = 0; i < store.getCount(); i++) {
							if (store.getAt(i).get("value") == newValue) {
								if (gridStore != null) {
									var ridx = gridStore.getCount() - 1;
									if (ridx < 0) {
										ridx = 0;
									}
									if (store.getAt(i).get("package") == "EKB") {
										gridStore.getAt(ridx).set("can_edit", false);
									}
									gridStore.getAt(ridx).set("func_package", store.getAt(i).get("package"));
									gridStore.getAt(ridx).commit();
								}
								break;
							}
						}
					},
					resize : function(combo, width, height, oldWidth, oldHeight, eOpts) {
						combo.listConfig = {
							minWidth : width - combo.labelWidth
						};
					}
				}
			});

			Ext.define('DCI.Customer.BodyGridPanel2', {
				extend : 'Ext.panel.Panel',
				alias : 'DCI.Customer.BodyGridPanel2',
				region : 'center',
				layout : 'border',
				bodyStyle : {
					background : "#eaf1fb"
				},
				bodyPadding : 5,
				headform : null,
				actionurl : '',
				languageObj : null,
				currmode : PageMode.View,
				dataloaded : false,
				gridid : '',
				postvalue : '',
				keyfields : [],
				headKeys : {},
				canEdit : true,
				oriGStore : null,
				oriGColumns : [],
				stopUsingBtn : [],
				uid : '',
				cusFormatIconPath : '',
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
								// bodypanel.headform.setCurrMode(bodypanel.currmode);
							} else {
								Ext.MessageBox.alert(this.up('panel').up('panel').languageObj.errmsg, this.up('panel').up('panel').languageObj.no_edit_auth);
							}
						}
					}, {
						xtype : 'button',
						// text : this.up('panel').up('panel').languageObj == null ?
						// 'save_format' :
						// this.up('panel').up('panel').languageObj.save_format,
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
							// var ogs = panel.getOGS();
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
				}, {
					xtype : 'grid',
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
					store : null,
					listeners : {
						celldblclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
							var panel = this.up('panel');
							panel.griddbclick(grid, td, cellIndex, record, tr, rowIndex, e, eOpts);
						},
						selectionchange : function(row, selected, eOpts) {
							var panel = this.up('panel');
							panel.gridselectionchange(row, selected, eOpts);
						},
						deselect : function(row, record, index, eOpts) {
							var panel = this.up('panel');
							panel.griddeselect(row, record, index, eOpts);
						}
					},
					plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
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
							}
						}
					}) ]
				} ],
				setCusButton : function(buttons) {
					if (buttons != null) {
						var me = this;
						for ( var i = 0; i < buttons.length; i++) {
							me.insert(me.items.length - 1, buttons[i]);
						}
					}
				},
				getOGS : function() {
					return this.oriGStore;
				},
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
					t1=panel.items;
					t2=panel;
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
				initBody : function(store, columns) {
					var me = this;
					var cols = columns;
					var bodyGrid = this.items.get(1);
					var panel = me.items.get(0);
					var tmpcols = [];
					var obj = null;
					me.oriGStore = store;
					me.oriGColumns = [];

					if (me.cusFormatIconPath == null || me.cusFormatIconPath == '') {
						me.cusFormatIconPath = "../../images/icons/CusGridFormat.png";
					}

					for ( var i = 0; i < columns.length; i++) {
						obj = new Object();
						for ( var key in columns[i]) {
							obj[key] = columns[i][key];
						}

						me.oriGColumns.push(obj);
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
								DCITag : this.postvalue,
								uid : this.uid,
								action : 'getGFormat',
								gridid : this.gridid
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
									for ( var j = 0; j < cols.length; j++) {
										if (cols[j].colid == tmpstore.getAt(i).get('col_id')) {
											if (tmpstore.getAt(i).get('col_visible') == '1') {
												if (cols[j].hasOwnProperty('hidden')) {
													cols[j].hidden = false;
												} else {
													cols[j]['hidden'] = false;
												}

												if (tmpstore.getAt(i).get('col_width') != 0) {
													if (cols[j].hasOwnProperty('width')) {
														cols[j].width = parseInt(tmpstore.getAt(i).get('col_width'));
													} else {
														cols[j]['width'] = parseInt(tmpstore.getAt(i).get('col_width'));
													}
												}
											} else {
												if (tmpstore.getAt(i).get('col_width') != 0) {
													if (cols[j].hasOwnProperty('width')) {
														cols[j].width = parseInt(tmpstore.getAt(i).get('col_width'));
													} else {
														cols[j]['width'] = parseInt(tmpstore.getAt(i).get('col_width'));
													}
												}

												if (cols[j].hasOwnProperty('hidden')) {
													cols[j].hidden = true;
												} else {
													cols[j]['hidden'] = true;
												}
											}

											tmpcols.push(cols[j]);
											cols.splice(j, 1);
											break;
										}
									}
								}

								if (cols != null && cols.leght != 0) {
									for ( var i = 0; i < cols.length; i++) {
										cols[i]['hidden'] = true;
										tmpcols.push(cols[i]);
									}
								}
							} else {
								tmpcols = cols;
							}

							bodyGrid.reconfigure(store, tmpcols);
						} else {
							bodyGrid.reconfigure(store, cols);
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

				},
				gridReload : function() {
					var bodypanel = this;
					var grid = bodypanel.items.get(1);
					if (grid != null) {
						grid.getStore().load();
					}
				},
				griddbclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {

				},
				gridselectionchange : function(row, selected, eOpts) {

				},
				griddeselect : function(row, record, index, eOpts) {

				},
				getGrid : function() {
					var me = this;
					return me.items.get(1);
				},
				valided : function() {
					return true;
				}
			});

			var bodypanel = Ext.create('DCI.Customer.BodyGridPanel2', {
				title : langs.group_role_edit,
				languageObj : langs,
				gridid : 'RoleConfig_G01',
				actionurl : '../../Configs/RoleConfig.dsc',
				postvalue : postvalue,
				uid : uid,
				headform : headform,
				keyfields : [ 'conn_id', 'role_id', 'func_id' ],
				canEdit : canEdit,
				transSource : [],
				transSource2 : [],
				transType : function(value) {
					for ( var i = 0; i < this.transSource.length; i++) {
						if (this.transSource[i].value == value) {
							value = this.transSource[i].label;
							break;
						}
					}
					return value;
				},
				transType2 : function(value) {
					for ( var i = 0; i < this.transSource2.length; i++) {
						if (this.transSource2[i].value == value) {
							value = this.transSource2[i].label;
							break;
						}
					}
					return value;
				},
				addNewRow : function() {
					var grid = this.items.get(1);
					var gridStore = grid.getStore();
					if (gridStore != null) {
						gridStore.add({
							conn_id : '',
							role_id : this.headKeys.role_id,
							func_id : '',
							func_package : '',
							can_edit : false,
							moditag : 1,
							moditp : 'add'
						});
					}

					grid.getView().select(gridStore.getCount() - 1);
				},
				setNewCondi : function() {
					var me = this;
					var grid = me.getGrid();
					var store = grid.getStore();
					var head = me.headform;
					if (store != null) {
						if (store.getProxy().extraParams.hasOwnProperty('keys')) {
							store.getProxy().extraParams['keys'] = head.items.get(0).getValue();
							var obj = new Object();
							obj["role_id"] = head.items.get(0).getValue();
							me.headKeys = obj;
						}
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
				text : langs.conn_id,
				dataIndex : 'conn_id',
				colid : 'colconn_id',
				width : 200,
				renderer : function(value) {
					try {
						value = this.up().transType(value);
					} catch (e) {
						if (window.console) {
							console.log(e.message);
						}
					}
					return value;
				},
				listeners : {
					render : function(comp, eOpts) {
						var e = Ext.create('DCI.Customer.ComboBox', {
							labelWidth : 0
						});
						e.getStore().loadData(this.up('panel').up('panel').transSource);
						this.setEditor(e);
					}
				}
			}, {
				text : langs.func_id,
				dataIndex : 'func_id',
				colid : 'colfunc_id',
				width : 200,
				//editor : func_Combo,
				renderer : function(value) {
					try {
						value = this.up().transType2(value);
					} catch (e) {
						if (window.console) {
							console.log(e.message);
						}
					}
					return value;
				},
				listeners : {
					render : function(comp, eOpts) {
						var e = Ext.create('DCI.Customer.ComboBox', {
							labelWidth : 0,
							store : {
								fields : [ 'label', 'value', 'package' ],
								autoLoad : false,
								proxy : {
									type : 'memory',
									reader : {
										type : 'json'
									}
								}
							},
							listeners : {
								change : function(comp, newValue, oldValue, eOpts) {
									var me = this;
									var store = me.getStore();
									var gridStore = me.up().up().getStore();
									for ( var i = 0; i < store.getCount(); i++) {
										if (store.getAt(i).get("value") == newValue) {
											if (gridStore != null) {
												var ridx = gridStore.getCount() - 1;
												if (ridx < 0) {
													ridx = 0;
												}
												if (store.getAt(i).get("package") == "EKB") {
													gridStore.getAt(ridx).set("can_edit", false);
												}
												gridStore.getAt(ridx).set("func_package", store.getAt(i).get("package"));
												gridStore.getAt(ridx).commit();
											}
											break;
										}
									}
								},
								resize : function(combo, width, height, oldWidth, oldHeight, eOpts) {
									combo.listConfig = {
										minWidth : width - combo.labelWidth
									};
								}
							}
						});
						e.getStore().loadData(this.up('panel').up('panel').transSource2);
						this.setEditor(e);
					}
				}
			}, {
				xtype : 'dcicheckcolumn',
				text : langs.can_edit,
				dataIndex : 'can_edit',
				colid : 'colcan_edit',
				canEditControl : function(col, rowIndex) {
					var me = this;
					var gridStore = me.up().up().getStore();
					if (gridStore == null) {
						return false;
					} else {
						return gridStore.getAt(rowIndex).get("func_package") != "EKB";
					}
				}
			} ];

			var bodyStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'role_id', 'func_id', 'conn_id', 'func_package', 'can_edit', 'moditag', 'moditp' ],
				proxy : {
					type : 'ajax',
					url : '../../Configs/RoleConfig.dsc',
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
				},
				listeners : {
					update : function(store, record, operation, eOpts) {
						if (record.data.moditp == 'add') {
							for ( var i = 0; i < store.getCount(); i++) {
								if (store.getAt(i).id != record.id) {
									if (record.data.conn_id == store.getAt(i).get("conn_id") && record.data.func_id == store.getAt(i).get("func_id")) {
										Ext.Msg.alert(langs.errmsg, langs.role_exist, function() {
											store.remove(record);
										});
										break;
									}
								}
							}
						}
					}
				}
			});

			var main = Ext.create('DCI.Customer.SubPanel', {
				id : 'RoleConfigMain',
				renderTo : 'RoleConfigPage',
				border : 0,
				//pagetype : 'kanban',
				bodyPadding : '0 5 5 5',
				layout : 'border',
				widthChangeControls : [ headpanel, bodypanel ],
				items : [ headpanel, bodypanel ]
			});

			var initQueryStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'conns', /* 'roles', */'funcs' ],
				proxy : {
					type : 'ajax',
					url : '../../Configs/RoleConfig.dsc',
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

			initQueryStore.load(function(records) {
				if (records.length > 0) {
					conn_Combo.getStore().loadData(records[0].get("conns"));
					func_Combo.getStore().loadData(records[0].get("funcs"));
					bodypanel.transSource = records[0].get("conns");
					bodypanel.transSource2 = records[0].get("funcs");
					bodypanel.initBody(bodyStore, bodyColumns);
					/* visible_combo.loadDefault();

					visible_combo.listConfig = {
						minWidth : visible_combo.getWidth() - visible_combo.getLabelWidth()
					}; */
				}
			});

			main.resize(Ext.get("RoleConfigPage").getWidth(), Ext.get("RoleConfigPage").getHeight());
		}
	});
</script>
</head>
<body>
	<div id="RoleConfigPage" class="page_div"></div>
</body>
</html>