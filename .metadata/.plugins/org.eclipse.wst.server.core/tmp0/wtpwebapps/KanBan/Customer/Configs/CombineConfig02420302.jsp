<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title><dcitag:reqParam paramName="func_name"></dcitag:reqParam></title>

<script type="text/javascript" charset="UTF-8">
	Ext.onReady(function() {
		var localKeys = [ "no_conn_data","conn_list","ckb_id","ckb_name","head_kbid","body_kbid","combinecolumn","no_kanban_data","no_choose_column","col_type_desc","no_duo_kbcombo","no_duo_col","create_link","create_link_fail","data_never_load"
		          		,"save_msg", "delete_msg", "delete_fail", "delete_success", "delete_result_title", "delete_confirm_title", "delete_confirm_msg", "delete_fail",
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
			var connid_combo = Ext.create('DCI.Customer.ComboBox', {
				fieldLabel : langs.conn_list,
				triggerAction: 'all',
				name : 'conn_list',
				emptyText:langs.no_conn_data,
				x : 0,
				y : 65
			});

			var headid_combo = Ext.create('DCI.Customer.ComboBox', {
				fieldLabel : langs.head_kbid,
				name : 'head_id',
				triggerAction: 'all',
				emptyText:langs.no_kanban_data,
				x : 250,
				y : 65
			});
			var bodyid_combo = Ext.create('DCI.Customer.ComboBox', {
				fieldLabel : langs.body_kbid,
				name : 'body_id',
				triggerAction: 'all',
				emptyText:langs.no_kanban_data,
				x : 250,
				y : 105
			});
			var combineColumn_combo = Ext.create('DCI.Customer.ComboBox', {
				fieldLabel : langs.combinecolumn,
				name : 'combinecolumn',
				triggerAction: 'all',
				emptyText:langs.no_choose_column,
				x : 500,
				y : 65
			});
			connid_combo.on("select",function(combobox){
				var value=combobox.getValue();
				var kbStore = Ext.create('Ext.data.Store', {
					autoLoad : false,
					fields : [ 'kbdatas' ],
					proxy : {
						type : 'ajax',
						url : '../../Configs/CombineConfig.dsc',
						actionMethods : {
							read : 'POST'
						},
						reader : {
							type : 'json'
						},
						extraParams : {
							DCITag : postvalue,
							uid : uid,
							action : 'KBData',
							conn_id:value
						}
					}
				});
				kbStore.load(function(record) {
					if (record.length > 0) {
						headid_combo.reset();
						bodyid_combo.reset();
						
						headid_combo.getStore().loadData(record[0].get("kbdatas"));
						headid_combo.listConfig = {
								minWidth : headid_combo.getWidth() - headid_combo.getLabelWidth()
						};
						bodyid_combo.getStore().loadData(record[0].get("kbdatas"));
						bodyid_combo.listConfig = {
								minWidth : bodyid_combo.getWidth() - bodyid_combo.getLabelWidth()
						};
						combineColumn_combo.reset();
						combineColumn_combo.getStore().loadData("");
					}
				});
				this.reset();
				combobox.setValue(value);
			});

			headid_combo.on("select",function(combobox){
				var hvalue=combobox.getValue();
				var bvalue=bodyid_combo.getValue();
				if(hvalue!=bvalue){
				if((hvalue!=undefined&&hvalue!="")&&(bvalue!=undefined&&bvalue!="")){
					var colStore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'columndatas' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/CombineConfig.dsc',
							actionMethods : {
								read : 'POST'
							},
							reader : {
								type : 'json'
							},
							extraParams : {
								DCITag : postvalue,
								uid : uid,
								action : 'colData',
								head_id:hvalue,
								body_id:bvalue,
							}
						}
					});
					colStore.load(function(record) {
						if (record.length > 0) {
							if(record[0].get("columndatas")==""){
								headid_combo.reset();
								Ext.Msg.alert(langs.info_msg_title, langs.no_duo_col);
								combineColumn_combo.reset();
								combineColumn_combo.getStore().loadData(record[0].get("columndatas"));
							}
							else{
								combineColumn_combo.reset();
								combineColumn_combo.getStore().loadData(record[0].get("columndatas"));
								combineColumn_combo.listConfig = {
										minWidth : combineColumn_combo.getWidth() - combineColumn_combo.getLabelWidth()
								};
							}
						}
					});
				}
				this.reset();
				combobox.setValue(hvalue);
			}else{
				Ext.Msg.alert(langs.info_msg_title, langs.no_duo_kbcombo);
				this.reset();
				combineColumn_combo.reset();
			}
			});
			bodyid_combo.on("select",function(combobox){
				var hvalue=headid_combo.getValue();
				var bvalue=combobox.getValue();
				if(hvalue!=bvalue){
					if((hvalue!=undefined&&hvalue!="")&&(bvalue!=undefined&&bvalue!="")){
						var colStore = Ext.create('Ext.data.Store', {
							autoLoad : false,
							fields : [ 'columndatas' ],
							proxy : {
								type : 'ajax',
								url : '../../Configs/CombineConfig.dsc',
								actionMethods : {
									read : 'POST'
								},
								reader : {
									type : 'json'
								},
								extraParams : {
									DCITag : postvalue,
									uid : uid,
									action : 'colData',
									head_id:hvalue,
									body_id:bvalue,
								}
							}
						});
						colStore.load(function(record) {
							if (record.length > 0) {
								if(record[0].get("columndatas")!=""){
									combineColumn_combo.reset();
									combineColumn_combo.getStore().loadData(record[0].get("columndatas"));
									combineColumn_combo.listConfig = {
											minWidth : combineColumn_combo.getWidth() - combineColumn_combo.getLabelWidth()
									};
								}else{
									bodyid_combo.reset();
									combineColumn_combo.reset();
									combineColumn_combo.getStore().loadData(record[0].get("columndatas"));
									Ext.Msg.alert(langs.info_msg_title, langs.no_duo_col);
								}
								
								
							}
						});
					}
					this.reset();
					combobox.setValue(bvalue);
				}else{
					Ext.Msg.alert(langs.info_msg_title, langs.no_duo_kbcombo);
					combineColumn_combo.reset();
					this.reset();
				}
			});
			
			var headform = Ext.create('DCI.Customer.HeadFormPanel', {
				height : 100,
				url : '../../Configs/CombineConfig.dsc',
				languageObj : langs,
				items : [ Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.ckb_id,
					emptyText : '',
					name : 'ckb_id',
					allowBlank : false,
					ispk : true,
					canEdit : false,
					defaultvalue : langs.system_build,
					x : 0,
					y : 20
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.ckb_name,
					emptyText : '',
					name : 'ckb_name',
					allowBlank : true,
					x : 250,
					y : 20
				}), connid_combo,headid_combo,bodyid_combo,combineColumn_combo,
				{
                    xtype: 'label',
                    text: langs.col_type_desc,
                    x : 500,
					y : 105,
					style: {
					             Color: 'red',
					             Style: 'solid'
					          }
                }],
				showQueryWindow : function() {
					var me = this;
					me.initQueryWindow();
					var pageSize = 10;
					var initStore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'cols' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/CombineConfig.dsc',
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
						fields : [ 'ckb_id', 'ckb_name', 'head_id', 'body_id','combinecolumn','conn_list' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/CombineConfig.dsc',
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
						text : langs.ckb_id,
						dataIndex : 'ckb_id',
						width : 200
					}, {
						text : langs.ckb_name,
						dataIndex : 'ckb_name',
						width : 200
					} ];

					var openwin = Ext.create('DCI.Customer.QueryWindow', {
						height : 335,
						width : 450,
						headform : me,
						title : langs.toolbar_query_title,
						loadHeadData : function(record) {
							var headPanelReloadStore = Ext.create('Ext.data.Store', {
								autoLoad : false,
								fields : [ 'conndatas','kbdatas','columndatas' ],
								proxy : {
									type : 'ajax',
									url : '../../Configs/CombineConfig.dsc',
									actionMethods : {
										read : 'POST'
									},
									reader : {
										type : 'json'
									},
									extraParams : {
										DCITag : postvalue,
										uid : uid,
										action : 'loadHeadPanel',
										datas: Ext.encode(record.data)
									}
								}
							});
							headPanelReloadStore.load(function(record2) {
								if (record2.length > 0) {
									connid_combo.defaultvalue=record.get("conn_list");
									headid_combo.defaultvalue=record.get("head_id");
									bodyid_combo.defaultvalue=record.get("body_id");
									combineColumn_combo.defaultvalue=record.get("combinecolumn");
									
									connid_combo.getStore().loadData(record2[0].get("conndatas"));
									connid_combo.loadDefault();
									connid_combo.listConfig = {
										minWidth : connid_combo.getWidth() - connid_combo.getLabelWidth()
									};
									headid_combo.getStore().loadData(record2[0].get("kbdatas"));
									headid_combo.loadDefault();
									headid_combo.listConfig = {
											minWidth : headid_combo.getWidth() - headid_combo.getLabelWidth()
									};
									bodyid_combo.getStore().loadData(record2[0].get("kbdatas"));
									bodyid_combo.loadDefault();
									bodyid_combo.listConfig = {
											minWidth : bodyid_combo.getWidth() - bodyid_combo.getLabelWidth()
									};
									combineColumn_combo.getStore().loadData(record2[0].get("columndatas"));
									combineColumn_combo.loadDefault();
									combineColumn_combo.listConfig = {
											minWidth : combineColumn_combo.getWidth() - combineColumn_combo.getLabelWidth()
									};
									/*connid_combo.setValue(record.get("conn_list"));
									headid_combo.setValue(record.get("head_id"));
									bodyid_combo.setValue(record.get("body_id"));
									combineColumn_combo.setValue(record.get("combinecolumn"));*/
								}
							});
							
							this.headform.loadRecord(record);
							this.headform.setDataLoaded(true);
							/*if (bodyStore != null) {
								if (bodyStore.getProxy().extraParams.hasOwnProperty('keys')) {
									bodyStore.getProxy().extraParams['keys'] = record.get("ckb_id");
									bodyStore.load();
								}
							}
							if (bodypanel != null) {
								var obj = new Object();
								obj["ckb_id"] = record.get("ckb_id");
								bodypanel.headKeys = obj;
								bodypanel.dataloaded(this.headform.dataloaded);
							}*/
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
				height : 300,
				headform : headform,
				postvalue : postvalue,
				uid : uid,
				canEdit : canEdit,
				bodyPadding : '0 0 10 0',
				keepAdd : false
			});
			headpanel.setCusButtons([
				{
					xtype : 'button',
					cls : 'kblink-toolbar',
					tooltip : langs.create_link,
					x : 215,
					y : 3,
					width : 30,
					height : 30,
					handler : function() {
						var bform = this.up('panel').bindform;

						if (bform.dataloaded) {
							test=bform.items;
							var funcid = bform.items.get(0).getValue();
							var connid = bform.items.get(2).getRawValue().split('-')[0];
							var connname = bform.items.get(2).getRawValue().split('-')[1];

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
									url : '../../Configs/CombineConfig.dsc',
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

							if (connid != null && connname !=null) {
								subconnstore.removeAll();
								//for ( var i = 0; i < conngstore.getCount(); i++) {
									subconnstore.add({
										label : connname,
										value : connid
									});
								//}
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
												url : '../../Configs/CombineConfig.dsc',
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
				}
			]);
/*
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
									fields : [ 'ckb_id', 'ckb_name', 'head_id', 'body_id', 'combinecolumn','conn_list' ],
									proxy : {
										type : 'ajax',
										url : '../../Configs/CombineConfig.dsc',
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
														url : '../../Configs/CombineConfig.dsc',
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
																		alert('gstore.load');
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

			var bodypanel = Ext.create('DCI.Customer.BodyGridPanel', {
				title : langs.group_role_edit,
				languageObj : langs,
				gridid : 'CombineConfig_G01',
				actionurl : '../../Configs/CombineConfig.dsc',
				postvalue : postvalue,
				uid : uid,
				headform : headform,
				keyfields : [ 'conn_id', 'role_id', 'func_id' ],
				canEdit : canEdit,
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
				editor : conn_Combo,
				renderer : function(value) {
					return getComboLabel(conn_Combo, value);
				}
			}, {
				text : langs.func_id,
				dataIndex : 'func_id',
				colid : 'colfunc_id',
				width : 200,
				editor : func_Combo,
				renderer : function(value) {
					return getComboLabel(func_Combo, value);
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
					url : '../../Configs/CombineConfig.dsc',
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
			});*/

			//bodypanel.initBody(bodyStore, bodyColumns);

			var main = Ext.create('DCI.Customer.SubPanel', {
				id : 'CombineConfigMain',
				renderTo : 'CombineConfigPage',
				border : 0,
				//pagetype : 'kanban',
				bodyPadding : '0 5 5 5',
				layout : 'border',
				widthChangeControls : [ headpanel ],
				items : [ headpanel ]
			});

			var initQueryStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'conndatas','kbdatas','columndatas' ],
				proxy : {
					type : 'ajax',
					url : '../../Configs/CombineConfig.dsc',
					actionMethods : {
						read : 'POST'
					},
					reader : {
						type : 'json'
					},
					extraParams : {
						DCITag : postvalue,
						uid : uid,
						action : 'conn'
					}
				}
			});
			initQueryStore.load(function(record) {
				if (record.length > 0) {
					connid_combo.getStore().loadData(record[0].get("conndatas"));//
					connid_combo.defaultvalue="OOXX";
					headid_combo.defaultvalue="OOXX";
					bodyid_combo.defaultvalue="OOXX";
					combineColumn_combo.defaultvalue="OOXX";
					connid_combo.loadDefault();
					connid_combo.listConfig = {
						minWidth : connid_combo.getWidth() - connid_combo.getLabelWidth()
					};
					headid_combo.getStore().loadData(record[0].get("kbdatas"));
					headid_combo.loadDefault();
					headid_combo.listConfig = {
							minWidth : headid_combo.getWidth() - headid_combo.getLabelWidth()
					};
					bodyid_combo.getStore().loadData(record[0].get("kbdatas"));
					bodyid_combo.loadDefault();
					bodyid_combo.listConfig = {
							minWidth : bodyid_combo.getWidth() - bodyid_combo.getLabelWidth()
					};
					combineColumn_combo.getStore().loadData(record[0].get("columndatas"));
					combineColumn_combo.loadDefault();
				}
			});

			main.resize(Ext.get("CombineConfigPage").getWidth(), Ext.get("CombineConfigPage").getHeight());
		}
	});
</script>
</head>
<body>
	<div id="CombineConfigPage" class="page_div"></div>
</body>
</html>