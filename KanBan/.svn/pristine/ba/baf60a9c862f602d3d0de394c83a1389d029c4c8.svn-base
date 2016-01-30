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
		var localKeys = [ "save_msg", "delete_msg", "delete_fail", "delete_success", "delete_result_title", "delete_confirm_title", "delete_confirm_msg", "delete_fail",
				"save_fail", "save_success", "save_result_title", "save_fail", "group_id", "group_name", "group_desc", "system_build", "visible", "enable", "disable",
				"toolbar_query_title", "unselected_role", "selected_role", "role_name", "role_desc", "group_role_edit", "no_data_selected", "system_def_can_not_edit",
				"confirm_title", "data_lose_warning", "cus_format", "copy_fail", "input_group_name", "todo_query", "just_use_in_view", "copy_group_confirm", "copy_success",
				"info_msg_title", "copy" ];
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

			var bodyValue = Ext.create('Ext.form.field.Hidden', {
				name : 'bodyvalue',
				value : ''
			});

			var headform = Ext.create('DCI.Customer.HeadFormPanel', {
				height : 100,
				url : '../../Configs/GroupConfig.dsc',
				languageObj : langs,
				items : [ Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.group_id,
					emptyText : '',
					name : 'group_id',
					allowBlank : false,
					ispk : true,
					defaultvalue : langs.system_build,
					x : 0,
					y : 10
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.group_name,
					emptyText : '',
					name : 'group_name',
					allowBlank : true,
					x : 250,
					y : 10
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.group_desc,
					emptyText : '',
					name : 'group_desc',
					allowBlank : true,
					width : 465,
					x : 0,
					y : 50
				}), visible_combo, bodyValue ],
				showQueryWindow : function() {
					var me = this;
					me.initQueryWindow();
					var pageSize = 10;
					var initStore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'cols' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/GroupConfig.dsc',
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
						fields : [ 'group_id', 'group_name', 'visible', 'group_desc' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/GroupConfig.dsc',
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
						text : langs.group_id,
						dataIndex : 'group_id',
						width : 200
					}, {
						text : langs.group_name,
						dataIndex : 'group_name',
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
									//console.log(record.get("group_id"));
									bodyStore.getProxy().extraParams['keys'] = record.get("group_id");
									bodyStore.load();
								}
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
				},
				loadDefault : function() {
					if (this.items != null && this.items.length > 0) {
						for ( var i = 0; i < this.items.length; i++) {
							if (Ext.getClassName(this.items.get(i)).indexOf('DCI') != -1) {
								this.items.get(i).loadDefault();
							} else if (Ext.getClassName(this.items.get(i)).indexOf('Hidden') != -1) {
								this.items.get(i).setValue("");
							}
						}

						if (bodyStore != null) {
							if (bodyStore.getProxy().extraParams.hasOwnProperty('keys')) {
								bodyStore.getProxy().extraParams['keys'] = "$$$$$";
								bodyStore.load();
							}
						}
					}
					this.resetDataLoaded();
				},
				resetDataLoaded : function() {
					this.dataloaded = false;
					if (bodypanel != null) {
						bodypanel.dataloaded(this.dataloaded);
					}
				},
				setCurrMode : function(currmode) {
					this.currmode = currmode;
					if (this.currmode == PageMode.View) {
						setCompsReadOnly(this.items, true);
						if (funcPanel != null) {
							funcPanel.setMode(true);
						}
					} else if (this.currmode == PageMode.Add) {
						setCompsReadOnly(this.items, false);
						if (funcPanel != null) {
							funcPanel.setMode(false);
						}
					} else if (this.currmode == PageMode.Edit) {
						setPageEdit(this.items);
						if (funcPanel != null) {
							funcPanel.setMode(false);
						}
					}
				}
			});

			var headpanel = Ext.create('DCI.Customer.HeadPanel', {
				region : 'north',
				height : 160,
				headform : headform,
				postvalue : postvalue,
				uid : uid,
				canEdit : canEdit,
				keepAdd : false,
				bodyPadding : '0 0 10 0',
				exceptionEditAuth : function() {
					var result = [ "1", "" ];
					var form = this.bindform;
					if (headform != null) {
						if (form.items.get(0).getValue() == "GSYS") {
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
									fields : [ 'group_id', 'group_name', 'visible', 'group_desc' ],
									proxy : {
										type : 'ajax',
										url : '../../Configs/GroupConfig.dsc',
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

								var groupid = bform.items.get(0).getValue();
								Ext.MessageBox.confirm(langobj.confirm_title, langobj.copy_group_confirm, function(btn) {
									if (btn == 'yes') {
										Ext.MessageBox.show({
											title : langobj.input_group_name,
											msg : langobj.input_group_name,
											width : 300,
											buttons : Ext.MessageBox.OKCANCEL,
											multiline : true,
											fn : function(btn, text) {
												if (btn == "ok") {
													Ext.Ajax.request({
														method : 'POST',
														url : '../../Configs/GroupConfig.dsc',
														params : {
															DCITag : pvalue,
															uid : uid,
															action : 'copygroup',
															group_id : groupid,
															group_name : text
														},
														success : function(a) {
															var result = Ext.JSON.decode(a.responseText);
															if (result.success) {
																if (gstore != null) {
																	if (gstore.getProxy().extraParams.hasOwnProperty('filter')) {
																		gstore.getProxy().extraParams['filter'] = " and group_id = '" + result.msg + "'";
																	}

																	gstore.load(function(records) {
																		if (records != null && records.length > 0) {
																			bform.loadRecord(records[0]);
																			bform.setDataLoaded(true);
																			if (bodyStore != null) {
																				if (bodyStore.getProxy().extraParams.hasOwnProperty('keys')) {
																					//console.log(record.get("group_id"));
																					bodyStore.getProxy().extraParams['keys'] = result.msg;
																					bodyStore.load();
																				}
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

			//Body comps
			var bodyStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'selected', 'unselected' ],
				proxy : {
					type : 'ajax',
					url : '../../Configs/GroupConfig.dsc',
					actionMethods : {
						read : 'POST'
					},
					reader : {
						type : 'json'
					},
					extraParams : {
						DCITag : postvalue,
						uid : uid,
						action : 'roles',
						keys : ''
					}
				},
				listeners : {
					load : function(store, records, successful, eOpts) {
						if (records && records.length > 0) {
							unselectStore.loadData(records[0].get("unselected"));
							selectStore.loadData(records[0].get("selected"));
						}
					}
				}
			});

			var unselectStore = Ext.create('Ext.data.Store', {
				fields : [ 'role_name', 'role_id', 'role_desc' ],
				autoLoad : false,
				proxy : {
					type : 'memory',
					reader : {
						type : 'json'
					}
				}
			});
			var selectStore = Ext.create('Ext.data.Store', {
				fields : [ 'role_name', 'role_id', 'role_desc' ],
				autoLoad : false,
				proxy : {
					type : 'memory',
					reader : {
						type : 'json'
					}
				},
				listeners : {
					load : function(store, records, successful, eOpts) {
						if (bodyValue != null) {
							var tmp = "";
							for ( var i = 0; i < store.getCount(); i++) {
								if (i == 0) {
									tmp = store.getAt(i).get("role_id");
								} else {
									tmp += ";" + store.getAt(i).get("role_id");
								}
							}
							bodyValue.setValue(tmp);
						}
					}
				}
			});
			var unselectGrid = Ext.create('Ext.grid.Panel', {
				title : langs.unselected_role,
				store : unselectStore,
				columns : [ {
					text : langs.role_name,
					dataIndex : 'role_name',
					width : 148
				}, {
					text : langs.role_desc,
					dataIndex : 'role_desc',
					width : 250
				} ],
				height : 200,
				width : 400,
				x : 0,
				y : 0
			});
			var selectGrid = Ext.create('Ext.grid.Panel', {
				title : langs.selected_role,
				store : selectStore,
				columns : [ {
					text : langs.role_name,
					dataIndex : 'role_name',
					width : 148
				}, {
					text : langs.role_desc,
					dataIndex : 'role_desc',
					width : 250
				} ],
				height : 200,
				width : 400,
				x : 480,
				y : 0
			});

			var funcPanel = Ext.create('Ext.panel.Panel', {
				border : 0,
				layout : 'absolute',
				height : 200,
				width : 80,
				x : 400,
				y : 0,
				items : [ {
					xtype : 'button',
					text : '>>',
					disabled : true,
					width : 30,
					x : 25,
					y : 10,
					handler : function() {
						if (headform.getCurrMode() != PageMode.View) {
							if (unselectStore != null && selectStore != null && unselectStore.getCount() > 0) {
								for ( var i = 0; i < unselectStore.getCount(); i++) {
									selectStore.add({
										role_id : unselectStore.getAt(i).get("role_id"),
										role_name : unselectStore.getAt(i).get("role_name"),
										role_desc : unselectStore.getAt(i).get("role_desc")
									});
								}
								unselectStore.removeAll();
								if (bodyValue != null) {
									var tmp = "";
									for ( var i = 0; i < selectStore.getCount(); i++) {
										if (i == 0) {
											tmp = selectStore.getAt(i).get("role_id");
										} else {
											tmp += ";" + selectStore.getAt(i).get("role_id");
										}
									}
									bodyValue.setValue(tmp);
								}
							}
						}
					}
				}, {
					xtype : 'button',
					text : '>',
					disabled : true,
					width : 30,
					x : 25,
					y : 36,
					handler : function() {
						if (headform.getCurrMode() != PageMode.View) {
							if (unselectGrid != null && unselectGrid.getStore().getCount() > 0) {
								var s = unselectGrid.getSelectionModel().getSelection();
								if (s.length > 0) {
									selectStore.add({
										role_id : s[0].data.role_id,
										role_name : s[0].data.role_name,
										role_desc : s[0].data.role_desc
									});
									unselectStore.remove(s[0]);

									if (bodyValue != null) {
										var tmp = "";
										for ( var i = 0; i < selectStore.getCount(); i++) {
											if (i == 0) {
												tmp = selectStore.getAt(i).get("role_id");
											} else {
												tmp += ";" + selectStore.getAt(i).get("role_id");
											}
										}
										bodyValue.setValue(tmp);
									}
								} else {
									Ext.Msg.alert(langs.errmsg, langs.no_data_selected);
								}
							}
						}
					}
				}, {
					xtype : 'button',
					text : '<',
					disabled : true,
					width : 30,
					x : 25,
					y : 62,
					handler : function() {
						if (headform.getCurrMode() != PageMode.View) {
							if (selectGrid != null && selectGrid.getStore().getCount() > 0) {
								var s = selectGrid.getSelectionModel().getSelection();
								if (s.length > 0) {
									unselectStore.add({
										role_id : s[0].data.role_id,
										role_name : s[0].data.role_name,
										role_desc : s[0].data.role_desc
									});

									selectStore.remove(s[0]);
									if (bodyValue != null) {
										var tmp = "";
										for ( var i = 0; i < selectStore.getCount(); i++) {
											if (i == 0) {
												tmp = selectStore.getAt(i).get("role_id");
											} else {
												tmp += ";" + selectStore.getAt(i).get("role_id");
											}
										}
										bodyValue.setValue(tmp);
									}
								} else {
									Ext.Msg.alert(langs.errmsg, langs.no_data_selected);
								}
							}
						}
					}
				}, {
					xtype : 'button',
					text : '<<',
					disabled : true,
					width : 30,
					x : 25,
					y : 88,
					handler : function() {
						if (headform.getCurrMode() != PageMode.View) {
							if (unselectStore != null && selectStore != null && selectStore.getCount() > 0) {
								for ( var i = 0; i < selectStore.getCount(); i++) {
									unselectStore.add({
										role_id : selectStore.getAt(i).get("role_id"),
										role_name : selectStore.getAt(i).get("role_name"),
										role_desc : selectStore.getAt(i).get("role_desc")
									});
								}
								selectStore.removeAll();
								if (bodyValue != null) {
									bodyValue.setValue("");
								}
							}
						}
					}
				} ],
				setMode : function(view) {
					for ( var i = 0; i < this.items.length; i++) {
						this.items.get(i).setDisabled(view);
					}
				}
			});

			var bodypanel = Ext.create('Ext.panel.Panel', {
				title : langs.group_role_edit,
				region : 'center',
				border : 0,
				bodyStyle : {
					background : "#dfe9f6"
				},
				layout : 'absolute',
				items : [ unselectGrid, funcPanel, selectGrid ],
				dataloaded : function(loaded) {
					//this.items.get(1).setMode(loaded);
				},
				gridReload : function() {
					if (bodyStore != null) {
						if (bodyStore.getProxy().extraParams.hasOwnProperty('keys')) {
							bodyStore.getProxy().extraParams['keys'] = "$$$$$";
							bodyStore.load();
						}
					}
				}
			});
			//headform.bodypanel = bodypanel;

			var main = Ext.create('DCI.Customer.SubPanel', {
				id : 'GroupConfigMain',
				renderTo : 'GroupConfigPage',
				border : 0,
				minWidth : 900,
				minHeight : 420,
				bodyPadding : '0 5 5 5',
				layout : 'border',
				widthChangeControls : [ headpanel, bodypanel ],
				items : [ headpanel, bodypanel ]
			});

			headform.loadDefault();
			visible_combo.loadDefault();
			visible_combo.listConfig = {
				minWidth : visible_combo.getWidth() - visible_combo.getLabelWidth()
			};

			/* var initQueryStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [],
				proxy : {
					type : 'ajax',
					url : '../../Configs/GroupConfig.dsc',
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
				if (record.length > 0) {
					
				}
			}); */

			main.resize(Ext.get("GroupConfigPage").getWidth(), Ext.get("GroupConfigPage").getHeight());
		}
	});
</script>
</head>
<body>
	<div id="GroupConfigPage" class="page_div"></div>
</body>
</html>