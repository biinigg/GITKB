<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title><dcitag:reqParam paramName="func_name"></dcitag:reqParam></title>
<style>
</style>
<script type="text/javascript" charset="UTF-8">
	function updatePanelOpen(kanbanInfo, postvalue, uid, langs, value) {
		Ext.Ajax.request({
			method : 'POST',
			url : '../../Funcs/EKB/KanBan.dsc',
			params : {
				DCITag : postvalue,
				uid : uid,
				action : 'updatePanelOpen',
				params_value : value,
				sql_id : kanbanInfo.func_id
			},
			failure : function(f, action) {
				Ext.Msg.alert(langs.save_result_title, langs.save_fail + " :</br>" + action.result.errorMessage);
			}
		});
	}
	function removeAllStyle(id) {
		var rmcnt = 0;
		while (document.getElementById(id) != null) {
			Ext.util.CSS.removeStyleSheet(id);
			if (rmcnt >= 10) {
				break;
			}
			rmcnt++;
		}
	}

	function changeKanbanGrid(newValue, classname, changeType) {
		try {
			var pName = null;
			var pValue = null;

			if (changeType == "FS") {
				pName = "font-size";
				pValue = newValue + "pt";
				Ext.util.CSS.updateRule("." + classname + " .x-progress-text", pName, (newValue - 2) + "pt");
				//Ext.util.CSS.createStyleSheet("." + classname + " .x-progress-text {font-size:"+newValue+ "pt;font-family:Calibri;line-height:"+(newValue+10)+"px;}");
			} else if (changeType == "FC") {
				pName = "color";
				pValue = "#" + newValue;
			} else if (changeType == "BC") {
				pName = "background-color";
				pValue = "#" + newValue;
			} else if (changeType == "RH") {
				pName = "height";
				pValue = newValue + "px";
				Ext.util.CSS.updateRule("." + classname + " .x-progress-default", pName, (newValue - 8) + "px");
				Ext.util.CSS.updateRule("." + classname + " .x-progress-text", "line-height", (newValue - 8) + "px");
			} else if (changeType == "BcEven") {
				pName = "background-color";
				pValue = "#" + newValue;
			}
			if (pName != null) {
				if (changeType != "BcEven") {
					Ext.util.CSS.updateRule("." + classname + " .x-grid-row .x-grid-cell", pName, pValue);
				}
				if (changeType != "BC") {
					Ext.util.CSS.updateRule("." + classname + " .x-grid-row-alt .x-grid-cell", pName, pValue);
				}
				if (pName != "background-color" && pName != "height") {
					Ext.util.CSS.updateRule("." + classname + " .x-column-header", pName, pValue);
				}
			}
		} catch (e) {
			if (window.console) {
				console.log(e.message);
			}
		}
	}

	function filterComboData(type, langsObj) {
		var datas = null;
		if (type == "NUM" || type == "DATE") {
			datas = [ {
				"label" : langsObj.bigger,
				"value" : ">"
			}, {
				"label" : langsObj.bigger_equal,
				"value" : ">="
			}, {
				"label" : langsObj.smaller,
				"value" : "<"
			}, {
				"label" : langsObj.smaller_equal,
				"value" : "<="
			}, {
				"label" : langsObj.equal,
				"value" : "="
			}, {
				"label" : langsObj.not_equal,
				"value" : "<>"
			} ];
		} else if (type == "LIGHT" || type == "MAPPING") {
			datas = [ {
				"label" : langsObj.equal,
				"value" : "="
			}, {
				"label" : langsObj.not_equal,
				"value" : "<>"
			} ];
		} else {
			datas = [ {
				"label" : langsObj.start_with,
				"value" : "startwith"
			}, {
				"label" : langsObj.end_with,
				"value" : "endwith"
			}, {
				"label" : langsObj.bigger,
				"value" : ">"
			}, {
				"label" : langsObj.bigger_equal,
				"value" : ">="
			}, {
				"label" : langsObj.smaller,
				"value" : "<"
			}, {
				"label" : langsObj.smaller_equal,
				"value" : "<="
			}, {
				"label" : langsObj.equal,
				"value" : "="
			}, {
				"label" : langsObj.not_equal,
				"value" : "<>"
			}, {
				"label" : langsObj.like,
				"value" : "like"
			}, {
				"label" : langsObj.not_like,
				"value" : "notlike"
			} ];
		}

		return datas;
	}
	var test,y1,y2,y3;
	var ckbInfos= new Object();
	Ext.onReady(function() {
		var localKeys = [ "load_fail","errmsg","system_error",
		         		"toolbar_query_title", "condi_col", "save_msg", "load_def_fail", "load_def_success", "load_def_result_title", "load_def_confirm_title",
				"load_def_confirm_msg", "save_fail", "save_success", "save_result_title", "save_fail", "sort_column", "page_size", "no_query", "no_sort", "asc", "desc",
				"refresh_gap", "minutes", "favorties_setup", "start_with", "end_with", "bigger", "bigger_equal", "smaller", "smaller_equal", "equal", "not_equal", "like",
				"not_like", "font_size", "row_height", "bg_color", "font_color", "condi_relation", "adv_search", "save_condi", "load_def_format", "load_def_condi", "to_excel",
				"to_html", "start_timer", "stop_timer", "seconds", "cus_format", "cross_db" ];
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
				infos["relation_filter"] = "<dcitag:reqParam paramName='filter'></dcitag:reqParam>";
				Ext.Ajax.request({
					method : 'POST',
					async: false,
					setTimeout:1000,
					url : '../../CUS/Funcs/EKB/SubKanBan02420302.dsc',
					params : {
						DCITag:  records[0].get('dcitagValue'),
						uid : uid,
						action : 'init',
						ckb_id:infos.func_id
					},
					success : function(a) {
						var result = Ext.JSON.decode(a.responseText);
						if (result.success) {
							infos['body_kbid']=result.body_kbid;
							infos['ckb_id']=result.ckb_id;
							infos['ckb_name']=result.ckb_name;
							infos['combinecolumn']=result.combinecolumn;
							infos['conn_id']=result.conn_id;
							infos['head_kbid']=result.head_kbid;
							infos['head_name']=result.head_name;
							infos['body_name']=result.body_name;
							infos['condition']=' AND '+result.combinecolumn+' = ';
							/*
							ckbInfos['body_kbid']=result.body_kbid;
							ckbInfos['ckb_id']=result.ckb_id;
							ckbInfos['ckb_name']=result.ckb_name;
							ckbInfos['combinecolumn']=result.combinecolumn;
							ckbInfos['conn_id']=result.conn_id;
							ckbInfos['head_kbid']=result.head_kbid;*/
						} else {
							Ext.Msg.alert(langobj.errmsg, langobj.load_fail + "</br>" + result.msg);
						}
					},
					failure : function(f, action) {
						Ext.Msg.alert(langobj.errmsg, langobj.system_error);
					}
				});
				showPage(infos, records[0].get('dcitagValue'), langs, uid);
			}
		});

		function showPage(kanbanInfo, postvalue, langs, uid) {
			var bodytaskIsRunning = false;
			var headtaskIsRunning =false;
			var bodypopuptaskIsRunning = false;
			
			//var runner = new Ext.util.TaskRunner();
			//var pageSize = 20;
			//var gridClass = kanbanInfo.func_id + "grid";
			/*var initCusKanBanStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'msg','success','ckb_id','ckb_name','combinecolumn','conn_id','body_kbid','head_kbid' ],
				proxy : {
					type : 'ajax',
					url : '../../CUS/Funcs/EKB/SubKanBan02420302.dsc',
					actionMethods : {
						read : 'POST'
					},
					reader : {
						type : 'json'
					},
					extraParams : {
						DCITag: postvalue,
						uid : uid,
						action : 'init',
						ckb_id:kanbanInfo.func_id
					}
				}
			});
			initCusKanBanStore.load(function(record) {
				if (record.length > 0 && record[0].get("success")) {
					ckbInfos['ckb_id']=record[0].get("ckb_id");
					ckbInfos['ckb_name']=record[0].get("ckb_name");
					ckbInfos['combinecolumn']=record[0].get("combinecolumn");
					ckbInfos['head_kbid']=record[0].get("head_kbid");
					ckbInfos['body_kbid']=record[0].get("body_kbid");
					ckbInfos['conn_id']=record[0].get("conn_id");
				}else{
					Ext.Msg.alert(langobj.errmsg, langobj.load_fail + "</br>" + record[0].get("msg"));
				}
				
			});*/
			
			var advWin = Ext.create('Ext.window.Window', {
				layout : 'border',
				title : langs.toolbar_query_title,
				closeAction : 'hide',
				height : 300,
				width : 520,
				minHeight : 300,
				minWidth : 520,
				modal : true,
				plain : true,
				condiStr : '',
				kanbanStore : null,
				headPanel : null,
				lightsData : null,
				langObj : langs,
				items : [ {
					xtype : 'panel',
					region : 'north',
					layout : 'anchor',
					border : 0,
					height : 70,
					items : [ {
						xtype : 'panel',
						layout : 'absolute',
						anchor : '100% 50%',
						height : 35,
						condiParams : {
							condi_col : '',
							condi_type : '',
							condi_value : '',
							dcondi_col : '',
							dcondi_type : '',
							dcondi_value : ''
						},
						items : [ Ext.create('DCI.Customer.ComboBox', {
							fieldLabel : langs.condi_col,
							store : {
								fields : [ 'label', 'value', 'col_type', 'config_value' ],
								autoLoad : false,
								proxy : {
									type : 'memory',
									reader : {
										type : 'json'
									}
								}
							},
							labelWidth : 90,
							width : 250,
							x : 0,
							y : 5,
							listeners : {
								change : function(combo, newValue, oldValue, eOpts) {
									var panel = this.up('panel');
									var typeCombo = panel.items.get(1);
									var comboStore = combo.getStore();
									var currcomp = panel.items.get(2);
									var coltype = null;
									var valueComp = null;
									var configValue = null;
									for ( var i = 0; i < comboStore.getCount(); i++) {
										if (newValue == comboStore.getAt(i).get("value")) {
											coltype = comboStore.getAt(i).get("col_type");
											configValue = comboStore.getAt(i).get("config_value");
											break;
										}
									}

									if (coltype != null) {
										typeCombo.getStore().loadData(filterComboData(coltype, langs));
										typeCombo.loadDefault();

										if (coltype == "DATE") {
											valueComp = Ext.create('DCI.Customer.DateField', {
												fieldLabel : "",
												labelWidth : 0,
												width : 150,
												name : 'condi_value',
												defaultvalue : '',
												parentPanel : panel,
												x : 340,
												y : 5,
												listeners : {
													change : function(comp, newValue, oldValue, eOpts) {
														comp.parentPanel.condiParams["condi_value"] = Ext.Date.format(new Date(newValue), 'Ymd');
														comp.parentPanel.condiParams["dcondi_value"] = Ext.Date.format(new Date(newValue), 'Y/m/d');
													}
												}
											});
										} else if (coltype == "MAPPING" || coltype == "LIGHT") {
											valueComp = Ext.create('DCI.Customer.ComboBox', {
												fieldLabel : "",
												labelWidth : 0,
												width : 150,
												name : 'condi_value',
												defaultvalue : '',
												parentPanel : panel,
												x : 340,
												y : 5,
												listeners : {
													change : function(comp, newValue, oldValue, eOpts) {
														comp.parentPanel.condiParams["condi_value"] = newValue;
														comp.parentPanel.condiParams["dcondi_value"] = comp.getRawValue();
													}
												}
											});

											if (coltype == "MAPPING") {
												var combodata = [];
												var vcData = configValue.split(";");
												var items = null;
												for ( var i = 0; i < vcData.length; i++) {
													items = vcData[i].split("=");
													if (items != null && items.length == 2) {
														combodata.push({
															value : items[0],
															label : items[1]
														});
													}
												}
												valueComp.getStore().loadData(combodata);
											} else {
												if (panel.up('panel').up('window').lightsData != null) {
													valueComp.getStore().loadData(panel.up('panel').up('window').lightsData);
												}
											}

											valueComp.loadDefault();
										} else {
											valueComp = Ext.create('DCI.Customer.TextField', {
												fieldLabel : "",
												labelWidth : 0,
												width : 150,
												name : 'condi_value',
												defaultvalue : '',
												parentPanel : panel,
												x : 340,
												y : 5,
												listeners : {
													change : function(comp, newValue, oldValue, eOpts) {
														comp.parentPanel.condiParams["condi_value"] = newValue;
														comp.parentPanel.condiParams["dcondi_value"] = newValue;
													}
												}
											});
										}

										panel.remove(currcomp.getId());
										panel.insert(2, valueComp);
									}

									panel.condiParams["condi_col"] = newValue;
									panel.condiParams["dcondi_col"] = combo.getRawValue();
								}
							}
						}), Ext.create('DCI.Customer.ComboBox', {
							fieldLabel : "",
							labelWidth : 0,
							width : 90,
							x : 250,
							y : 5,
							listeners : {
								change : function(combo, newValue, oldValue, eOpts) {
									var panel = this.up('panel');
									panel.condiParams["condi_type"] = newValue;
									panel.condiParams["dcondi_type"] = combo.getRawValue();
								}
							}
						}), Ext.create('DCI.Customer.TextField', {
							fieldLabel : "",
							labelWidth : 0,
							width : 150,
							name : 'condi_value',
							defaultvalue : '',
							x : 340,
							y : 5
						}) ]
					}, {
						xtype : 'panel',
						anchor : '100% 50%',
						layout : 'absolute',
						items : [ {
							xtype : 'radiogroup',
							fieldLabel : langs.condi_relation,
							columns : 2,
							vertical : true,
							width : 200,
							x : 0,
							y : 5,
							items : [ {
								boxLabel : 'AND',
								name : 'rb',
								inputValue : 'AND',
								checked : true
							}, {
								boxLabel : 'OR',
								name : 'rb',
								inputValue : 'OR'
							} ]
						}, {
							xtype : 'button',
							text : langs.add,
							x : 200,
							y : 5,
							handler : function() {
								var panel = this.up('panel');
								var panel1 = panel.up('panel').items.get(0);
								var store = panel.up('panel').up('window').items.get(1).getStore();
								var rb = panel.items.get(0).getValue();

								if (store != null) {
									store.add({
										condi_relation : rb.rb,
										condi_col_display : panel1.condiParams.dcondi_col,
										condi_type_display : panel1.condiParams.dcondi_type,
										condi_value_display : panel1.condiParams.dcondi_value,
										condi_col : panel1.condiParams.condi_col,
										condi_type : panel1.condiParams.condi_type,
										condi_value : panel1.condiParams.condi_value
									});
								}
							}
						}, {
							xtype : 'button',
							text : langs._delete,
							x : 250,
							y : 5,
							handler : function() {
								var grid = this.up('panel').up('panel').up('window').items.get(1);
								var selectionModel = grid.getSelectionModel();
								if (selectionModel.hasSelection()) {
									grid.getStore().remove(grid.getSelectionModel().getSelection()[0]);
								}
							}
						}, {
							xtype : 'button',
							text : langs.clear,
							x : 300,
							y : 5,
							handler : function() {
								var store = this.up('panel').up('panel').up('window').items.get(1).getStore();

								if (store != null) {
									store.removeAll();
								}
							}
						} ]
					} ]
				}, {
					xtype : 'grid',
					region : 'center',
					renderer : "component",
					border : 0,
					stripeRows : true,
					autoScroll : true,
					loadMask : true,
					hideHeaders : true,
					enableTextSelection : true,
					viewConfig : {
						forceFit : false,
						autoLoad : false
					},
					columns : [ {
						dataIndex : 'condi_relation',
						width : 50
					}, {
						dataIndex : 'condi_col_display',
						width : 150
					}, {
						text : langs.conn_id,
						dataIndex : 'condi_type_display',
						width : 100
					}, {
						text : langs.conn_id,
						dataIndex : 'condi_value_display',
						width : 200
					} ],
					store : Ext.create('Ext.data.Store', {
						fields : [ 'condi_relation', 'condi_col_display', 'condi_type_display', 'condi_value_display', 'condi_col', 'condi_type', 'condi_value' ],
						autoLoad : false,
						proxy : {
							type : 'memory',
							reader : {
								type : 'json'
							}
						},
						syncCondiString : function(window) {
							var fstr = "";
							var store = this;

							for ( var i = 0; i < store.getCount(); i++) {
								fstr += " " + store.getAt(i).get("condi_relation") + " " + store.getAt(i).get("condi_col") + " ";
								if (store.getAt(i).get("condi_type") == "startwith") {
									fstr += "like '" + store.getAt(i).get("condi_value") + "%' ";
								} else if (store.getAt(i).get("condi_type") == "endwith") {
									fstr += "like '%" + store.getAt(i).get("condi_value") + "' ";
								} else if (store.getAt(i).get("condi_type") == "like") {
									fstr += "like '%" + store.getAt(i).get("condi_value") + "%' ";
								} else if (store.getAt(i).get("condi_type") == "notlike") {
									fstr += "not like '%" + store.getAt(i).get("condi_value") + "%' ";
								} else {
									fstr += store.getAt(i).get("condi_type") + " '" + store.getAt(i).get("condi_value") + "' ";
								}
							}

							window.condiStr = fstr;
						},
						listeners : {
							datachanged : function(store, eOpts) {
								var btn = null;
								if (hp1 != null) {
									btn = hp1.items.get(4);
									if (btn != null) {
										if (store.getCount() > 0) {
											btn.getEl().setStyle({
												borderWidth : "2px 2px 2px 2px",
												borderColor : 'red',
												boderStyle : 'solid'
											});
										} else {
											btn.getEl().setStyle({
												borderWidth : "1px 1px 1px 1px",
												borderColor : '',
												boderStyle : ''
											});
										}
									}
								}
							}
						}
					})
				} ],
				buttons : [ {
					xtype : 'button',
					text : langs.ok,
					handler : function() {
						var win = this.up('window');
						var store = win.items.get(1).getStore();
						if (store != null) {
							store.syncCondiString(win);
							if (win.kanbanStore != null) {
								win.kanbanStore.gridreload(win.headPanel, win.condiStr,bodygrid.getStore(),"");
							}
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
				setComboData : function(record, selectedValue) {
					var me = this;
					var combo1 = me.items.get(0).items.get(0).items.get(0);
					var cStore = combo1.getStore();

					if (cStore != null) {
						cStore.loadData(record);
						if (selectedValue == null || selectedValue == "") {
							combo1.loadDefault();
						} else {
							combo1.setValue(selectedValue);
						}
					}
				},
				setInitData : function(hpanel, gstore, record, initData) {
					var me = this;
					var comp = me.items.get(0).items.get(0).items.get(0);
					if (record["filter_col"] == null || record["filter_col"] == "") {
						comp.loadDefault();
					} else {
						comp.setValue(record["filter_col"]);
					}

					comp = me.items.get(0).items.get(0).items.get(1);
					if (record["filter_condi"] == null || record["filter_condi"] == "") {
						comp.loadDefault();
					} else {
						comp.setValue(record["filter_condi"]);
					}

					comp = me.items.get(0).items.get(0).items.get(2);
					if (record["filter_value"] == null || record["filter_value"] == "") {
						comp.loadDefault();
					} else {
						comp.setValue(record["filter_value"]);
					}

					me.headPanel = hpanel;
					me.kanbanStore = gstore;

					if (initData != null) {
						me.items.get(1).getStore().loadData(initData);
						me.items.get(1).getStore().syncCondiString(advWin);
					}

					var panel = me.items.get(0).items.get(0);
					var comboStore = panel.items.get(0).getStore();
					var typeCombo = panel.items.get(1);
					var currcomp = panel.items.get(2);
					var coltype = null;
					var valueComp = null;
					var configValue = null;
					if (comboStore != null && comboStore.getCount() > 0) {
						coltype = comboStore.getAt(0).get("col_type");
						configValue = comboStore.getAt(0).get("config_value");

						if (coltype != null) {
							typeCombo.getStore().loadData(filterComboData(coltype, langs));
							typeCombo.loadDefault();

							if (coltype == "DATE") {
								valueComp = Ext.create('DCI.Customer.DateField', {
									fieldLabel : "",
									labelWidth : 0,
									width : 150,
									name : 'condi_value',
									defaultvalue : '',
									parentPanel : panel,
									x : 340,
									y : 5,
									listeners : {
										change : function(comp, newValue, oldValue, eOpts) {
											comp.parentPanel.condiParams["condi_value"] = Ext.Date.format(new Date(newValue), 'Ymd');
											comp.parentPanel.condiParams["dcondi_value"] = Ext.Date.format(new Date(newValue), 'Y/m/d');
										}
									}
								});
							} else if (coltype == "MAPPING" || coltype == "LIGHT") {
								valueComp = Ext.create('DCI.Customer.ComboBox', {
									fieldLabel : "",
									labelWidth : 0,
									width : 150,
									name : 'condi_value',
									defaultvalue : '',
									parentPanel : panel,
									x : 340,
									y : 5,
									listeners : {
										change : function(comp, newValue, oldValue, eOpts) {
											comp.parentPanel.condiParams["condi_value"] = newValue;
											comp.parentPanel.condiParams["dcondi_value"] = panel.items.get(2).getRawValue();
										}
									}
								});

								if (coltype == "MAPPING") {
									var combodata = [];
									var vcData = configValue.split(";");
									var items = null;
									for ( var i = 0; i < vcData.length; i++) {
										items = vcData[i].split("=");
										if (items != null && items.length == 2) {
											combodata.push({
												value : items[0],
												label : items[1]
											});
										}
									}
									valueComp.getStore().loadData(combodata);
								} else {
									if (me.lightsData != null) {
										valueComp.getStore().loadData(me.lightsData);
									}
								}

								valueComp.loadDefault();
								panel.condiParams["condi_value"] = valueComp.getValue();
								panel.condiParams["dcondi_value"] = valueComp.getRawValue();
							} else {
								valueComp = Ext.create('DCI.Customer.TextField', {
									fieldLabel : "",
									labelWidth : 0,
									width : 150,
									name : 'condi_value',
									defaultvalue : '',
									parentPanel : panel,
									x : 340,
									y : 5,
									listeners : {
										change : function(comp, newValue, oldValue, eOpts) {
											comp.parentPanel.condiParams["condi_value"] = newValue;
											comp.parentPanel.condiParams["dcondi_value"] = newValue;
										}
									}
								});
							}

							panel.remove(currcomp.getId());
							panel.insert(2, valueComp);
						}
					}

				},
				getCondiValue : function() {
					var me = this;
					var values = [];
					var store = me.items.get(1).getStore();

					if (store != null) {
						for ( var i = 0; i < store.getCount(); i++) {
							values.push({
								condi_relation : store.getAt(i).get("condi_relation"),
								condi_col : store.getAt(i).get("condi_col"),
								condi_type : store.getAt(i).get("condi_type"),
								condi_value : store.getAt(i).get("condi_value"),
								condi_col_display : store.getAt(i).get("condi_col_display"),
								condi_type_display : store.getAt(i).get("condi_type_display"),
								condi_value_display : store.getAt(i).get("condi_value_display"),
								seq : i
							});
						}
					}
					return values;
				}
			});

			var hp1 = Ext.create('Ext.panel.Panel', {
				title : "",
				id:kanbanInfo.func_id+'-'+'hp1',
				anchor : '100% 50%',
				layout : 'absolute',
				border : 0,
				bodyStyle : {
					background : '#d3e1f1'
				},
				bodyPadding : 0,
				targetGrid : null,
				lightsData : null,
				condiParams : {
					condi_col : '',
					condi_type : '',
					condi_value : ''
				},
				items : [ Ext.create('DCI.Customer.ComboBox', {
					fieldLabel : langs.condi_col,
					store : {
						fields : [ 'label', 'value', 'col_type', 'config_value' ],
						autoLoad : false,
						proxy : {
							type : 'memory',
							reader : {
								type : 'json'
							}
						}
					},
					labelWidth : 90,
					width : 250,
					x : 0,
					y : 8,
					listeners : {
						change : function(combo, newValue, oldValue, eOpts) {
							var panel = this.up('panel');
							var typeCombo = panel.items.get(1);
							var comboStore = combo.getStore();
							var currcomp = panel.items.get(2);
							var coltype = null;
							var valueComp = null;
							var configValue = null;
							for ( var i = 0; i < comboStore.getCount(); i++) {
								if (newValue == comboStore.getAt(i).get("value")) {
									coltype = comboStore.getAt(i).get("col_type");
									configValue = comboStore.getAt(i).get("config_value");
									break;
								}
							}

							if (coltype != null) {
								typeCombo.getStore().loadData(filterComboData(coltype, langs));
								typeCombo.loadDefault();

								if (coltype == "DATE") {
									valueComp = Ext.create('DCI.Customer.DateField', {
										fieldLabel : "",
										labelWidth : 0,
										width : 150,
										name : 'condi_value',
										defaultvalue : '',
										parentPanel : panel,
										x : 340,
										y : 8,
										listeners : {
											change : function(comp, newValue, oldValue, eOpts) {
												comp.parentPanel.condiParams["condi_value"] = Ext.Date.format(new Date(newValue), 'Ymd');
											}
										}
									});
								} else if (coltype == "MAPPING" || coltype == "LIGHT") {
									valueComp = Ext.create('DCI.Customer.ComboBox', {
										fieldLabel : "",
										labelWidth : 0,
										width : 150,
										name : 'condi_value',
										defaultvalue : '',
										parentPanel : panel,
										x : 340,
										y : 8,
										listeners : {
											change : function(comp, newValue, oldValue, eOpts) {
												comp.parentPanel.condiParams["condi_value"] = newValue;
											}
										}
									});

									if (coltype == "MAPPING") {
										var combodata = [];
										var vcData = configValue.split(";");
										var items = null;
										for ( var i = 0; i < vcData.length; i++) {
											items = vcData[i].split("=");
											if (items != null && items.length == 2) {
												combodata.push({
													value : items[0],
													label : items[1]
												});
											}
										}
										valueComp.getStore().loadData(combodata);
									} else {
										if (panel.lightsData != null) {
											valueComp.getStore().loadData(panel.lightsData);
										}
									}

									valueComp.loadDefault();
								} else {
									valueComp = Ext.create('DCI.Customer.TextField', {
										fieldLabel : "",
										labelWidth : 0,
										width : 150,
										name : 'condi_value',
										defaultvalue : '',
										parentPanel : panel,
										x : 340,
										y : 8,
										listeners : {
											change : function(comp, newValue, oldValue, eOpts) {
												comp.parentPanel.condiParams["condi_value"] = newValue;
											}
										}
									});
								}

								panel.remove(currcomp.getId());
								panel.insert(2, valueComp);
							}

							panel.condiParams["condi_col"] = newValue;
						}
					}
				}), Ext.create('DCI.Customer.ComboBox', {
					fieldLabel : "",
					labelWidth : 0,
					width : 90,
					x : 250,
					y : 8,
					listeners : {
						change : function(combo, newValue, oldValue, eOpts) {
							var panel = this.up('panel');
							panel.condiParams["condi_type"] = newValue;
						}
					}
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : "",
					labelWidth : 0,
					width : 150,
					name : 'condi_value',
					defaultvalue : '',
					x : 340,
					y : 8
				}), {
					xtype : 'button',
					cls : 'search-toolbar',
					tooltip : langs.search,
					width : 30,
					height : 30,
					x : 500,
					y : 3,
					handler : function() {
						var panel = this.up('panel');
						//var store = panel.targetGrid.getStore();
						var headstore =Ext.getCmp(kanbanInfo.func_id+'-'+'headgrid').getStore();
						var bodystore =Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').getStore();
						kanbanInfo.relation_filter = "";
						/*if (advWin != null && advWin.condiStr != null && advWin.condiStr.length > 0) {
							headstore.advGridReload(panel.up('panel'), advWin.CondiStr);
						} else {
							headstore.gridreload(panel.up('panel'));
						}*/
						var bodycondi="";
						if(headstore.getAt(0)!=null){
						  	bodycondi=kanbanInfo.condition+"'"+headstore.getAt(0).get(kanbanInfo.combinecolumn)+"'";
						}
						headstore.gridreload(panel.up('panel'), advWin.condiStr,bodystore,bodycondi);
							//alert('2:'+headstore.getAt(0).get(kanbanInfo.combinecolumn));
							//bodystore.gridreload(panel.up('panel'), kanbanInfo.condition+"'"+headstore.getAt(0).get(kanbanInfo.combinecolumn)+"'");
					}
				}, {
					xtype : 'button',
					cls : 'advsearchbutton',
					tooltip : langs.adv_search,
					width : 30,
					height : 30,
					x : 530,
					y : 3,
					handler : function() {
						if (advWin != null) {
							advWin.show();
						}
					}
				}, {
					xtype : 'button',
					cls : 'savebutton',
					tooltip : langs.save_format,
					width : 30,
					height : 30,
					x : 560,
					y : 3,
					handler : function() {
						var panel = this.up('panel');
						var panel2 = this.up('panel').up('panel').items.get(1);
						var cols = this.up('panel').targetGrid.columns;
						var colinfo = [];
						var condiInfo = new Object();
						var grid = panel.targetGrid;
						
						if (grid == null) {
							condiInfo["popup_width"] = 200;
						} else {
							condiInfo["popup_width"] = grid.getPopupWidth();
						}

						for ( var attrname in panel2.displayParams) {
							condiInfo[attrname] = panel2.displayParams[attrname];
						}

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
							url : '../../Funcs/EKB/KanBan.dsc',
							params : {
								DCITag : postvalue,
								uid : uid,
								action : 'saveFormat',
								gridid : kanbanInfo.func_id,
								datas : Ext.encode(condiInfo),
								//觸發自訂格式按鈕
								coldatas : '[{"col_id":"forCusIcon","col_index":0,"col_width":100,"col_visible":1}]'//Ext.encode(colinfo)
									
							},
							success : function(a) {
								/*if (grid != null) {
									Ext.getCmp(kanbanInfo.func_id+'-'+'headgrid').addCusFormatIcon();
									Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').addCusFormatIcon();
								}*/
								var displayInfo = {
									grid_font_size : panel2.displayParams['fs'],
									grid_font_color : panel2.displayParams['fc'],
									grid_row_height : panel2.displayParams['rh'],
									grid_row_color : panel2.displayParams['bc'],
									grid_row_even_color : panel2.displayParams['bcEven']
								};

								panel2.setCss(displayInfo);
								Ext.Msg.alert(langs.save_result_title, langs.save_success);
							},
							failure : function(f, action) {
								Ext.Msg.alert(langs.save_result_title, langs.save_fail + " :</br>" + action.result.errorMessage);
							}
						});
					}
				}, {
					xtype : 'button',
					cls : 'backdefault_greenbutton',
					tooltip : langs.load_def_format,
					width : 30,
					height : 30,
					x : 590,
					y : 3,
					handler : function() {
						var panel = this.up('panel').up('panel').items.get(1);
						Ext.MessageBox.confirm(langs.load_def_confirm_title, langs.load_def_confirm_msg, function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									method : 'POST',
									url : '../../Funcs/EKB/KanBan.dsc',
									params : {
										DCITag : postvalue,
										uid : uid,
										action : 'loadDefFormat',
										datas : "dodelete",
										gridid : kanbanInfo.func_id
									},
									success : function(a) {
										var displayInfo = {
											grid_font_size : 13,
											grid_font_color : '000000',
											grid_row_height : 28,
											grid_row_color : null,
											grid_row_even_color : null
										};
										panel.setCss(displayInfo);
										if (headgrid != null) {
											headgrid.rmCusFormatIcon();
											headgrid.setPopupWidth(200);
											if (initQueryHeadStore != null && initQueryHeadStore.getCount() > 0) {
												initQueryHeadStore.load(function(records) {
													headgrid.reloadHeadGridFormat(records[0].get('cols'), headGridStore);
												});
											}
										}
										if (bodygrid != null) {
											bodygrid.rmCusFormatIcon();
											bodygrid.setPopupWidth(200);
											if (initQueryGridStore != null && initQueryGridStore.getCount() > 0) {
												initQueryGridStore.load(function(records) {
													bodygrid.reloadBodyGridFormat(records[0].get('cols'), gridStore);
												});
											}
										}
										Ext.Msg.alert(langs.load_def_result_title, langs.load_def_success);
									},
									failure : function(f, action) {
										Ext.Msg.alert(langs.load_def_result_title, langs.load_def_fail + " :</br>" + action.result.errorMessage);
									}
								});
							}
						});
					}
				}, {
					xtype : 'button',
					cls : 'save-toolbar',
					tooltip : langs.save_condi,
					width : 30,
					height : 30,
					x : 620,
					y : 3,
					handler : function() {
						var panel = this.up('panel');
						var panel2 = this.up('panel').up('panel').items.get(1);
						var condiInfo = new Object();
						var advdatas = null;

						for ( var attrname in panel.condiParams) {
							condiInfo[attrname] = panel.condiParams[attrname];
						}

						for ( var attrname in panel2.sortParams) {
							condiInfo[attrname] = panel2.sortParams[attrname];
						}

						if (condiInfo.condi_col == null || condiInfo.condi_col == "") {
							condiInfo["condi_type"] = "";
							condiInfo["condi_value"] = "";
						}

						if (condiInfo.sort_col == null || condiInfo.sort_col == "") {
							condiInfo["sort_type"] = "";
						}

						if (advWin != null) {
							if (advWin.getCondiValue() != null && advWin.getCondiValue().length > 0) {
								advdatas = Ext.encode(advWin.getCondiValue());
							}
						}

						Ext.Ajax.request({
							method : 'POST',
							url : '../../Funcs/EKB/KanBan.dsc',
							params : {
								DCITag : postvalue,
								uid : uid,
								action : 'saveCondi',
								sql_id : kanbanInfo.func_id,
								datas : Ext.encode(condiInfo),
								advDatas : advdatas
							},
							success : function(a) {
								kanbanInfo.relation_filter = "";
								var store = panel.targetGrid.getStore();
								store.gridreload(panel.up('panel'), advWin.condiStr);
								Ext.Msg.alert(langs.save_result_title, langs.save_success);
							},
							failure : function(f, action) {
								Ext.Msg.alert(langs.save_result_title, langs.save_fail + " :</br>" + action.result.errorMessage);
							}
						});
					}
				}, {
					xtype : 'button',
					cls : 'backdefault_bluebutton',
					tooltip : langs.load_def_condi,
					width : 30,
					height : 30,
					x : 650,
					y : 3,
					handler : function() {
						var head = this.up('panel').up('panel');
						var panel1 = head.items.get(0);
						var panel2 = head.items.get(1);
						Ext.MessageBox.confirm(langs.load_def_confirm_title, langs.load_def_confirm_msg, function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									method : 'POST',
									url : '../../Funcs/EKB/KanBan.dsc',
									params : {
										action : 'loadDefCondi',
										uid : uid,
										datas : "dodelete",
										DCITag : postvalue,
										sql_id : kanbanInfo.func_id
									},
									success : function(a) {
										var displayInfo = {
											filter_col : '',
											filter_condi : '',
											filter_value : '',
											sort_col : '',
											sort_type : '',
											page_size : pageSize
										};

										panel1.setInitData(displayInfo);
										if (advWin != null) {
											var store = advWin.items.get(1).getStore();
											if (store != null) {
												store.removeAll();
											}
										}
										panel2.setInitData(displayInfo);
										panel1.targetGrid.getStore().gridreload(head, "",bodygrid.getStore(),"");
										Ext.Msg.alert(langs.load_def_result_title, langs.load_def_success);
									},
									failure : function(f, action) {
										Ext.Msg.alert(langs.load_def_result_title, langs.load_def_fail + " :</br>" + action.result.errorMessage);
									}
								});
							}
						});
					}
				} ],
				setComboData : function(record, selectedValue) {
					var me = this;
					var combo1 = me.items.get(0);
					var cStore = combo1.getStore();
					record.unshift({
						label : langs.no_query,
						value : '',
						col_type : 'CHAR',
						config_value : ''
					});
					if (cStore != null) {
						cStore.loadData(record);
						if (selectedValue == null || selectedValue == "") {
							combo1.loadDefault();
						} else {
							combo1.setValue(selectedValue);
						}
					}
					record.shift();
				},
				setInitData : function(record) {
					var me = this;
					var comp = me.items.get(0);
					if (record["filter_col"] == null || record["filter_col"] == "") {
						comp.loadDefault();
					} else {
						comp.setValue(record["filter_col"]);
					}

					comp = me.items.get(1);
					if (record["filter_condi"] == null || record["filter_condi"] == "") {
						comp.loadDefault();
					} else {
						comp.setValue(record["filter_condi"]);
					}

					comp = me.items.get(2);
					if (record["filter_value"] == null || record["filter_value"] == "") {
						comp.loadDefault();
					} else {
						comp.setValue(record["filter_value"]);
					}

					comp = me.up('panel');

					if (record["op_is_open"] == null || record["op_is_open"].length == 0) {
						comp.expand();
					} else {
						if (record["op_is_open"] == "0") {
							comp.collapse();
						} else {
							comp.expand();
						}

					}
				}
			});
			
			/*generateHeadGrid(hp1,kanbanInfo, postvalue, langs, uid);
			generateBodyGrid(hp1,kanbanInfo, postvalue, langs, uid);*/
			
			/******************************************表頭**************************************************/
	var hpageSize = 1;
	var headkid=kanbanInfo.head_kbid;//"K0005";
	var headGridClass = kanbanInfo.func_id+'-'+headkid + "grid";
	//runner = new Ext.util.TaskRunner();
	var headGridStore = Ext.create('Ext.data.Store', {
		autoLoad : false,
		fields : [],
		proxy : {
			async: true,
			type : 'ajax',
			timeout : 300000,
			url : '../../Funcs/EKB/KanBan.dsc',
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
				action : 'query',
				page : 1,
				pagesize : hpageSize,
				filter : '',
				sort : '',
				sql_id : headkid,
				conn_id : kanbanInfo.conn_id
			}
		},
		pageSize : hpageSize,
		gridreload : function(headPanel, filter,bstore,bodycondi) {
			var store = this;
			var panel1 = headPanel.items.get(0);
			var panel2 = headPanel.items.get(1);
			var fstr = "";
			var sstr = "";
			if (filter == null || filter.length == 0) {
				var col = panel1.condiParams.condi_col;
				var condi = panel1.condiParams.condi_type;
				var value = panel1.condiParams.condi_value;
				if (col != null && col != "") {
					fstr = " and " + col + " ";

					if (condi == "startwith") {
						fstr += "like '" + value + "%' ";
					} else if (condi == "endwith") {
						fstr += "like '%" + value + "' ";
					} else if (condi == "like") {
						fstr += "like '%" + value + "%' ";
					} else if (condi == "notlike") {
						fstr += "not like '%" + value + "%' ";
					} else {
						fstr += condi + " '" + value + "' ";
					}
				}
			} else {
				fstr = filter;
			}

			if (panel2.sortParams.sort_col != null && panel2.sortParams.sort_col != "") {
				sstr = panel2.sortParams.sort_col + " " + panel2.sortParams.sort_type;
			}

			if (store.getProxy().extraParams.hasOwnProperty('page')) {
				store.getProxy().extraParams['page'] = 1;
				store.currentPage = 1;
			}

			if (store.getProxy().extraParams.hasOwnProperty('pagesize')) {
				store.getProxy().extraParams['pagesize'] = hpageSize;
				store.pageSize = hpageSize;
			}

			if (store.getProxy().extraParams.hasOwnProperty('filter')) {
				if (kanbanInfo.relation_filter != null && kanbanInfo.relation_filter.length > 0) {
					store.getProxy().extraParams['filter'] = kanbanInfo.relation_filter;
				} else {
					store.getProxy().extraParams['filter'] = fstr;
				}
			}

			if (store.getProxy().extraParams.hasOwnProperty('sort')) {
				store.getProxy().extraParams['sort'] = sstr;
			}
			if (panel1.targetGrid != null) {
				panel1.targetGrid.getSelectionModel().deselectAll();
			}
			store.on("load", function() {
				if(store.getAt(0)!=null){
					if (bodygrid.usePopup) {
						popuptask.stop();
					}
					bodycondi=kanbanInfo.condition+"'"+store.getAt(0).get(kanbanInfo.combinecolumn)+"'";
					if(bstore!=undefined)
					{
						if (bstore.getProxy().extraParams.hasOwnProperty('filter')) {
							if (kanbanInfo.relation_filter != null && kanbanInfo.relation_filter.length > 0) {
								bstore.getProxy().extraParams['filter'] = kanbanInfo.relation_filter;
							} else {
								bstore.getProxy().extraParams['filter'] = bodycondi;
							}
						}
						bstore.load(function(){
							if(Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').getStore().totalCount==0){
								Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').dockedItems.get(3).showPopup("","");
							}else{
								Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').getSelectionModel().select(0);
							}
							if(bodygrid.usePopup){
								popuptask.executeTask();
							}
						});
					}
				}else{
					bstore.getProxy().extraParams['filter'] =  " and "+kanbanInfo.combinecolumn+"='' " ;
					bstore.load(function(){Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').dockedItems.get(3).showPopup("","");});
				}
			},
			this,
			{
			     single:true //設定執行一次性的事件
			});
			store.load();
		}
	});
	/*
	marqueepanel = Ext.create('Ext.panel.Panel', {
		region : 'north',
		height : 30,
		width : '100%',
		border : 2,
		title : "",
		dock : 'top',
		dataTask : null,
		scrollTask : null,
		marqueeUrl : '../../Funcs/EKB/KanBan.dsc',
		html : '',
		startMarquee : function(panel, refreshGap, showType) {
			var mRunner = new Ext.util.TaskRunner();
			var taskScroll = null;
			var mStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'message' ],
				proxy : {
					type : 'ajax',
					url : this.marqueeUrl,
					actionMethods : {
						read : 'POST'
					},
					reader : {
						root : 'marquee'
					},
					extraParams : {
						DCITag : postvalue,
						uid : uid,
						action : 'marqueeData',
						sql_id : headkid,
						conn_id : kanbanInfo.conn_id
					}
				}
			});

			function loadData(callback) {
				if (mStore != null) {
					mStore.load(function(record) {
						if (record.length > 0) {
							var value = "";
							if (showType == '1') {
								for ( var i = 0; i < record.length; i++) {
									if (i == 0) {
										value = '<div style="font-size:22px;"><Marquee height="' + panel.height
												+ '" scrollamount="3" onmouseover="this.stop()" onmouseout="this.start()">' + record[i].get('message');
									} else {
										value += "                              " + record[i].get('message');
									}

									if (i == record.length - 1) {
										value += "</div></Marquee>";
									}
								}
								panel.update(value);
							} else {
								if (panel.scrollTask != null) {
									panel.scrollTask.stop();
									panel.body.dom.scrollTop = 0;
								}
								var t = "";
								for ( var i = 0; i < record.length; i++) {
									if (i == 0) {
										t = '<div style="font-size:22px;text-align:center;overflow:hidden;height:'+panel.height+'px;">' + record[i].get('message')
												+ "</div>";
										value = '<div>' + t;
									} else {
										value += '<div  style="font-size:22px;text-align:center;overflow:hidden;height:'+panel.height+'px;">' + record[i].get('message')
												+ '</div>';
									}

									if (i == record.length - 1) {
										value += t + "</div>";
									}
								}

								panel.update(value);
								if (panel.scrollTask != null) {
									panel.scrollTask.start(5000);
								}
							}
							callback(panel);
						}
					});
				}
			}

			var taskGetData = mRunner.newTask({
				run : function() {
					loadData(function(panel) {
					});
				}
			});

			this.dataTask = taskGetData;
			loadData(function(panel) {
				taskGetData.start(refreshGap * 1000);
				var currRow = 0;
				if (showType == "2") {
					taskScroll = mRunner.newTask({
						run : function() {
							if (mStore.getCount() > 0) {
								if (currRow == 0) {
									panel.body.dom.scrollTop = 0;
								}
								if (currRow == mStore.getCount() - 1) {
									currRow = 0;
								} else {
									currRow++;
								}
								panel.scrollBy(0, panel.height, true);
							}
						}
					});
					panel.scrollTask = taskScroll;
					taskScroll.start(5000);
				} else {
					panel.scrollTask = null;
				}
			});

		},
		stopScrollTask : function() {
			if (this.dataTask != null) {
				this.dataTask.stop();
			}
			if (this.scrollTask != null) {
				this.scrollTask.stop();
			}
		},
		listeners : {
			el : {
				mouseover : function(event, html, eOpts) {
					if (marqueepanel != null) {
						if (marqueepanel.scrollTask != null) {
							marqueepanel.scrollTask.stop();
						}
					}
				}
			},
			body : {
				mouseout : function(event, html, eOpts) {
					if (marqueepanel != null) {
						if (marqueepanel.scrollTask != null) {
							marqueepanel.scrollTask.start(5000);
						}
					}
				}
			}
		}
	});
	
	popuptask = runner.newTask({
		timegap : 600,
		run : function() {
			if (bodygrid != null) {
				bodygrid.nextSelectedRow();
			}
		},
		setTimeGap : function(timegap) {
			if (timegap == null || timegap == "" || timegap < 1) {
				this.timegap = 600;
			} else {
				this.timegap = timegap;
			}
		},
		executeTask : function() {
			this.start(this.timegap * 1000);
			popuptaskIsRunning = true;
		},
		stopTask : function() {
			this.stop();
			popuptaskIsRunning = false;
		}
	});
	*/
	var headgrid = Ext.create('Ext.grid.Panel', {
		//height : 120,   
		id:kanbanInfo.func_id+'-'+'headgrid',
		renderer : "component",
		stripeRows : true,
		autoScroll : true,
		loadMask : true,
		allowDeselect : true,
		usePopup : false,
		popupImgPath : '',
		popupImgCols : [],
		popupTitleCols : [],
		popup_refresh_gap : 600,
		selModel : {
			allowDeselect : true
		},
		enableTextSelection : true,
		viewConfig : {
			forceFit : false,
			autoLoad : false
		},
		//enableLocking : true,
		store : null,
		bodyCls : headGridClass,
		componentCls : headGridClass,
		columns : [],
		sqlRelationStore : Ext.create('Ext.data.Store', {
			fields : [ 'col_id', 'target_sql_id', 'target_col_id', 'target_ori_col_id', "target_sql_name" ],
			autoLoad : false,
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		}),
		currpage : 0,
		totalpage : 0,
		timer : null,
		dockedItems : [
				{
					xtype : 'pagingtoolbar',
					store : null,
					dock : 'top',
					displayInfo : false,
					items : [ '-', {
						//text : 'stop timer',
						enableToggle : true,
						width : 50,
						tooltip : '',
						cls : 'starttimerbutton',
						toggleHandler : function(btn, pressed) {
							//if (main != null && !main.globalRunning) {
								this.btnClick(btn, pressed);
							//}
						},
						btnClick : function(btn, pressed) {
							var timer = this.up('panel').timer;
							if (timer != null) {
								if (pressed) {
									timer.executeTask();
									/* btn.removeCls('starttimerbutton');
									btn.addCls('stoptimerbutton');
									btn.setTooltip(langs.stop_timer); */
								} else {
									timer.stopTask();
									/* btn.removeCls('stoptimerbutton');
									btn.addCls('starttimerbutton');
									btn.setTooltip(langs.start_timer); */
								}
							}
						}
					}, '-', {
						xtype : 'label',
						text : langs.refresh_gap
					}, {
						xtype : 'label',
						text : '',
						margin : '0 0 0 10'
					}, '-', Ext.create('DCI.Customer.Img', {
						src : "../../images/icons/CusGridFormat.png",
						width : 20,
						height : 20,
						hidden : true,
						listeners : {
							afterrender : function(c) {
								Ext.create('Ext.tip.ToolTip', {
									target : c.getEl(),
									html : langs.cus_format
								});
							}
						}
					}), Ext.create('DCI.Customer.Img', {
						src : "../../images/button_icon/BtnCrossDatabase.png",
						width : 20,
						height : 20,
						hidden : true,
						listeners : {
							afterrender : function(c) {
								Ext.create('Ext.tip.ToolTip', {
									target : c.getEl(),
									html : langs.cross_db
								});
							}
						}
					}) ],
					listeners : {
						beforechange : function(pagingbar, page, eOpts) {
							var bstore=Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').getStore();
							if (this.store.getProxy().extraParams.hasOwnProperty('page')) {
								if (page == null || page == 0) {
									this.store.getProxy().extraParams['page'] = 1;
								} else {
									this.store.getProxy().extraParams['page'] = page;
								}
							}
							this.store.on("load", function() {
								if(this.store.getAt(0)!=null){
									if (bodygrid.usePopup) {
										popuptask.stop();
									}
									var bodycondi=kanbanInfo.condition+"'"+this.store.getAt(0).get(kanbanInfo.combinecolumn)+"'";
									if(bstore!=undefined)
									{
										if (bstore.getProxy().extraParams.hasOwnProperty('filter')) {
											if (kanbanInfo.relation_filter != null && kanbanInfo.relation_filter.length > 0) {
												bstore.getProxy().extraParams['filter'] = kanbanInfo.relation_filter;
											} else {
												bstore.getProxy().extraParams['filter'] = bodycondi;
											}
										}
										bstore.load(function(){
											if(Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').getStore().totalCount==0){
												Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').dockedItems.get(3).showPopup("","");
											}else{
												Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').getSelectionModel().select(0);
											}
											if(bodygrid.usePopup){
												popuptask.executeTask();
											}
										});
										
									}
								}else{
									Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').dockedItems.get(3).showPopup("","");
								}
								
							},
							this,
							{
							     single:true //設定執行一次性的事件
							});
							this.store.load();
						},
						change : function(pagingbar, pageData, eOpts) {
							if (pageData == null || pageData.currentPage == null || pageData.pageCount == null) {
								this.up('panel').setCurrPage(0);
								this.up('panel').setTotlaPage(0);
							} else {
								this.up('panel').setCurrPage(pageData.currentPage);
								this.up('panel').setTotlaPage(pageData.pageCount);
							}
						}
					}
				}/*,
				//marqueepanel,
				{
					xtype : 'panel',
					height : '100%',
					width : 200,
					border : 5,
					title : "",
					dock : 'right',
					layout : 'fit',
					collapsible : true,
					collapseDirection : "right",
					animCollapse : false,
					hidden : false,
					buttonAlign : 'left',
					resizable : true,
					items : [ {
						xtype : 'panel',
						height : '100%',
						title : "",
						width : '100%',
						border : 0
					} ],
					header : {
						items : [ {
							xtype : 'button',
							cls : 'playbutton',
							pressedCls : 'pausebutton',
							width : 15,
							height : 15,
							enableToggle : true,
							handler : function() {
								var me = this;
								if (popuptask != null) {
									if (me.pressed) {
										popuptask.executeTask();
										if (bodygrid != null) {
											bodygrid.nextSelectedRow();
										}
									} else {
										popuptask.stopTask();
									}
								}
							}
						} ]
					}
					/*,showPopup : function(title, path) {
						var me = this;
						var displaypanel = me.items.get(0);

						var popup = me.items.get(0);
						if (popup != null) {
							if (title == null || title == "") {
								popup.setTitle(" ");
							} else {
								popup.setTitle(title);
							}

							Ext.Ajax.request({
								method : 'POST',
								url : './../../PublicCtrl.dsc',
								params : {
									DCITag : postvalue,
									uid : uid,
									action : 'B64Encode',
									str : path
								},
								success : function(a) {
									var result = Ext.JSON.decode(a.responseText);
									var h = '<img width=' + displaypanel.getWidth() + ' height=' + displaypanel.getHeight() + ' src="./../../ImageLoader.dsc?imgpath='
											+ result.result + '"/>';
									displaypanel.update(h);
								}
							});
						}
					}
				}*/ ],
		listeners : {
			selectionchange : function(smodel, selected, eOpts) {
				var me = this;
				if (me.usePopup && selected.length > 0) {
					var file = "";
					var title = "";
					var popup = me.dockedItems.get(3);

					for ( var i = 0; i < me.popupImgCols.length; i++) {
						if (i == 0) {
							file = selected[0].get(me.popupImgCols[i]);
						} else {
							file += "--" + selected[0].get(me.popupImgCols[i]);
						}
					}

					for ( var i = 0; i < me.popupTitleCols.length; i++) {
						if (i == 0) {
							title = selected[0].get(me.popupTitleCols[i]);
						} else {
							title += "--" + selected[0].get(me.popupTitleCols[i]);
						}
					}

					if (popup != null) {
						var tmp = me.popupImgPath.replace("\\", "\"");
						tmp = tmp.replace("\"", "\\") + "\\";
						file = tmp + file + ".jpg";
						popup.showPopup(title, file);
					}

				}
			},
			celldblclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {

			},
			cellcontextmenu : function(view, td, cellIndex, record, tr, rowIndex, e, eOpts) {
				var me = this;

				if (me.sqlRelationStore != null && me.sqlRelationStore.getCount() > 0) {
					var gridMenu = Ext.create('Ext.menu.Menu', {
						mpanel : main.parentComp,
						items : [],
						clickEvent : function(value, targetsql, targetcol) {
							var me = this;
							if (me.mpanel != null) {
								Ext.Ajax.request({
									method : 'POST',
									url : '../../Funcs/EKB/KanBan.dsc',
									params : {
										action : 'funcInfo',
										uid : uid,
										DCITag : postvalue,
										func_id : targetsql,
										conn_id : kanbanInfo.conn_id
									},
									success : function(a) {
										if (a.responseText == null || a.responseText == '' || a.responseText.length == 0) {
											alert("open kanban " + targetsql + " fail");
										} else {
											var result = Ext.JSON.decode(a.responseText);
											var funcInfo = {
												url : result.url,
												text : result.text,
												func_package : result.func_package,
												can_edit : result.can_edit,
												filter : " and " + targetcol + " = '" + value + "'"
											};
											me.mpanel.beforeAddCheck(targetsql, kanbanInfo.conn_id, funcInfo, true);
										}
									}
								});
							}
						}
					});

					for ( var i = 0; i < me.sqlRelationStore.getCount(); i++) {
						if (me.columns[cellIndex].dataIndex == me.sqlRelationStore.getAt(i).get("col_id")) {
							gridMenu.add({
								text : me.sqlRelationStore.getAt(i).get("target_sql_name"),
								currcol : me.sqlRelationStore.getAt(i).get("col_id"),
								cellValue : record.get(me.columns[cellIndex].dataIndex),
								targetsql : me.sqlRelationStore.getAt(i).get("target_sql_id"),
								targetcol : me.sqlRelationStore.getAt(i).get("target_col_id"),
								targetoricol : me.sqlRelationStore.getAt(i).get("target_ori_col_id"),
								handler : function() {
									var menu = this.up('menu');
									menu.clickEvent(this.cellValue, this.targetsql, this.targetoricol);
								}
							});
						}
					}

					if (gridMenu.items.length > 0) {
						e.stopEvent();
						gridMenu.showAt(e.getXY());
					}
				}
			}
		},
		addCusFormatIcon : function() {
			var tbar = this.dockedItems.items[1];
			tbar.items.get(17).show();
		},
		rmCusFormatIcon : function() {
			var tbar = this.dockedItems.items[1];
			tbar.items.get(17).hide();
		},
		setCrossDBIcon : function(is_cross) {
			var tbar = this.dockedItems.items[1];
			if (is_cross) {
				tbar.items.get(18).show();
			} else {
				tbar.items.get(18).hide();
			}
		},
		initHeadGrid : function(timer, columns, store, relationRecord) {
			this.timer = timer;
			var cols = columns;
			var me = this;
			var tmpcols = [];
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
						DCITag : postvalue,
						uid : uid,
						action : 'getGFormat',
						gridid : headkid
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
						Ext.getCmp(kanbanInfo.func_id+'-'+'headgrid').addCusFormatIcon();
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

						if (cols != null && cols.length != 0) {
							for ( var i = 0; i < cols.length; i++) {
								cols[i]['hidden'] = true;
								tmpcols.push(cols[i]);
							}
						}
					} else {
						tmpcols = cols;
					}

					if (relationRecord != null) {
						for ( var j = 0; j < tmpcols.length; j++) {
							for ( var i = 0; i < relationRecord.length; i++) {
								if ("col" + relationRecord[i].col_id == tmpcols[j].colid) {
									if (tmpcols[j].hasOwnProperty('componentCls')) {
										tmpcols[j].componentCls = 'dci-realtion-column-header';
									} else {
										tmpcols[j]['componentCls'] = 'dci-realtion-column-header';
									}

									//Ext.util.CSS.updateRule("." + headGridClass + " .x-column-header .x-column-header-inner", "background-color", "red");
									break;
								}
							}
						}
					}

					me.reconfigure(store, tmpcols);
				} else {
					if (relationRecord != null) {
						for ( var j = 0; j < cols.length; j++) {
							for ( var i = 0; i < relationRecord.length; i++) {
								if ("col" + relationRecord[i].col_id == cols[j].colid) {
									if (cols[j].hasOwnProperty('componentCls')) {
										cols[j].componentCls = 'dci-realtion-column-header';
									} else {
										cols[j]['componentCls'] = 'dci-realtion-column-header';
									}
									break;
								}
							}
						}
					}
					me.reconfigure(store, cols);
				}
				//headinit
				me.child('pagingtoolbar').bindStore(store);
				//me.enableLocking = true;

				if (hp1 != null) {
					hp1.targetGrid = me;
				}
			});

			me.sqlRelationStore.loadData(relationRecord);
		},
		reloadHeadGridFormat : function(columns, store) {
			var cols = columns;
			var me = this;
			var tmpcols = [];
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
						DCITag : postvalue,
						uid : uid,
						action : 'getGFormat',
						gridid : headkid
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
						Ext.getCmp(kanbanInfo.func_id+'-'+'headgrid').addCusFormatIcon();
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

						if (cols != null && cols.length != 0) {
							for ( var i = 0; i < cols.length; i++) {
								cols[i]['hidden'] = true;
								tmpcols.push(cols[i]);
							}
						}
					} else {
						tmpcols = cols;
					}

					if (me.sqlRelationStore != null) {
						for ( var j = 0; j < tmpcols.length; j++) {
							for ( var i = 0; i < me.sqlRelationStore.getCount(); i++) {
								if ("col" + me.sqlRelationStore.getAt(i).data.col_id == tmpcols[j].colid) {
									if (tmpcols[j].hasOwnProperty('componentCls')) {
										tmpcols[j].componentCls = 'dci-realtion-column-header';
									} else {
										tmpcols[j]['componentCls'] = 'dci-realtion-column-header';
									}

									//Ext.util.CSS.updateRule("." + headGridClass + " .x-column-header .x-column-header-inner", "background-color", "red");
									break;
								}
							}
						}
					}

					me.reconfigure(store, tmpcols);
				} else {
					if (me.sqlRelationStore != null) {
						for ( var j = 0; j < cols.length; j++) {
							for ( var i = 0; i < me.sqlRelationStore.getCount(); i++) {
								if ("col" + me.sqlRelationStore.getAt(i).data.col_id == cols[j].colid) {
									if (cols[j].hasOwnProperty('componentCls')) {
										cols[j].componentCls = 'dci-realtion-column-header';
									} else {
										cols[j]['componentCls'] = 'dci-realtion-column-header';
									}
									break;
								}
							}
						}
					}
					me.reconfigure(store, cols);
				}
				//reloadhead
				y2=me.child('pagingtoolbar');
				me.child('pagingtoolbar').bindStore(headGridStore);
				//me.enableLocking = true;

				if (hp1 != null) {
					hp1.targetGrid = me;
				}
			});
		},
		setCurrPage : function(value) {
			this.currpage = value;
		},
		setTotlaPage : function(value) {
			this.totalpage = value;
		},
		nextPage : function() {
			var cpage = 0;
			var panel = this;
			if (panel.currpage == null || panel.currpage == 0) {
				cpage = 1;
			} else {
				cpage = panel.currpage + 1;
				if (cpage > panel.totalpage) {
					cpage = 1;
				}
			}

			if (panel.store.getProxy().extraParams.hasOwnProperty('page')) {
				panel.store.getProxy().extraParams['page'] = cpage;
				panel.store.currentPage = cpage;
			}
			
			var bstore=Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').getStore();
			panel.store.load(function(record) {
				panel.getSelectionModel().deselectAll();
				panel.setCurrPage(cpage);
				panel.setRefreshTime();
				if(panel.store.getAt(0)!=null){
					if (bodygrid.usePopup) {
						popuptask.stop();
					}
					var bodycondi=kanbanInfo.condition+"'"+panel.store.getAt(0).get(kanbanInfo.combinecolumn)+"'";
					if(bstore!=undefined)//,bodycondi
					{
						if (bstore.getProxy().extraParams.hasOwnProperty('filter')) {
							if (kanbanInfo.relation_filter != null && kanbanInfo.relation_filter.length > 0) {
								bstore.getProxy().extraParams['filter'] = kanbanInfo.relation_filter;
							} else {
								bstore.getProxy().extraParams['filter'] = bodycondi;
							}
						}
						bstore.load(function(){
							if(Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').getStore().totalCount==0){
								Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').dockedItems.get(3).showPopup("","");
							}else{
								Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').getSelectionModel().select(0);
							}
							if(bodygrid.usePopup){
								popuptask.executeTask();
							}
						});
					}
				}else{
					Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').dockedItems.get(3).showPopup("","");
				}
			});
		},
		globalNextPage : function(initPage) {
			var cpage = 0;
			var panel = this;
			if (initPage == -1) {
				if (panel.store.getProxy().extraParams.hasOwnProperty('page')) {
					panel.store.getProxy().extraParams['page'] = 1;
					panel.store.currentPage = 1;
				}
				panel.store.load(function(record) {
					panel.getSelectionModel().deselectAll();
					panel.setCurrPage(1);
					panel.setRefreshTime();

					if (panel.usePopup) {
						var sModel = panel.getSelectionModel();

						if (sModel != null) {
							sModel.select(0);
						}
					}
				});
			} else {
				cpage = panel.currpage + 1;
				if (cpage > panel.totalpage) {
					cpage = 1;
				}
				if (panel.usePopup) {
					var sModel = panel.getSelectionModel();
					if (sModel != null) {
						var sindex = 0;
						if (sModel.lastSelected == null) {
							sModel.select(0);
						} else {
							if (sModel.lastSelected.index % panel.getStore().pageSize == panel.getStore().getCount() - 1 || sModel.getSelection() == null
									|| sModel.getSelection().length == 0) {
								if (panel.store.getProxy().extraParams.hasOwnProperty('page')) {
									panel.store.getProxy().extraParams['page'] = cpage;
									panel.store.currentPage = cpage;
								}
								panel.store.load(function(record) {
									sModel.deselectAll();
									panel.setCurrPage(cpage);
									panel.setRefreshTime();
									panel.getSelectionModel().select(0);
								});
							} else {
								sindex = (sModel.lastSelected.index + 1) % panel.getStore().pageSize;
								panel.getSelectionModel().select(sindex);
							}
						}
					}
				} else {
					if (panel.store.getProxy().extraParams.hasOwnProperty('page')) {
						panel.store.getProxy().extraParams['page'] = cpage;
						panel.store.currentPage = cpage;
					}
					panel.store.load(function(record) {
						panel.getSelectionModel().deselectAll();
						panel.setCurrPage(cpage);
						panel.setRefreshTime();
					});
				}
			}

		},
		nextSelectedRow : function() {
			var me = this;
			var sModel = me.getSelectionModel();

			if (sModel != null) {
				var sindex = 0;
				if (sModel.lastSelected != null) {
					if (me.getSelectionModel().lastSelected.index % me.getStore().pageSize >= me.getStore().getCount() - 1 || sModel.getSelection() == null
							|| sModel.getSelection().length == 0) {
						sindex = 0;
					} else {
						sindex = (me.getSelectionModel().lastSelected.index + 1) % me.getStore().pageSize;
					}
				}
				sModel.select(sindex);
			}
		},
		setRefreshTimeTitle : function(gap, unit) {
			var label = this.dockedItems.get(1).items.get(14);
			if (label != null) {
				label.setText(langs.refresh_gap + "  " + gap + "  " + unit);
			}
		},
		setRefreshTime : function() {
			var label = this.dockedItems.get(1).items.get(15);
			if (label != null) {
				label.setText(Ext.Date.format(new Date(Ext.Date.now()), 'Y/m/d H:i:s'));
			}
		},
		setButtonStatus : function(isrunning) {
			var btn = this.dockedItems.get(1).items.get(12);
			btn.toggle(isrunning);
			btn.btnClick(btn, isrunning);
		}
		,setPopupInfo : function(infos) {
			/*var me = this;
			var popup = me.dockedItems.get(3);
			if (infos != null) {
				if (infos.use_popup) {
					popup.setVisible(true);
					me.usePopup = infos.use_popup;
					me.popupImgPath = infos.popup_dir;
					me.popup_refresh_gap = infos.popup_refresh_gap;
					if (infos.imgcols != null && infos.imgcols.trim().length > 0) {
						me.popupImgCols = infos.imgcols.split(';');
					}

					if (infos.titlecols != null && infos.titlecols.trim().length > 0) {
						me.popupTitleCols = infos.titlecols.split(';');
					}
				} else {
					popup.setVisible(false);
					me.usePopup = false;
					me.popupImgPath = "";
					me.popupImgCols = [];
					me.popupTitleCols = [];
					me.popup_refresh_gap = 600;
				}
			}
			if (popuptask != null) {
				popuptask.setTimeGap(me.popup_refresh_gap);
			}

			if (me.usePopup) {
				popup.header.items.get(0).toggle(true);
				popup.header.items.get(0).handler();
			}*/
		},
		getPopupWidth : function() {
			var me = this;
			var popup = me.dockedItems.get(3);
			var pwidth = 200;
			if (popup != null) {
				pwidth = popup.getWidth();
			}

			return pwidth;
		},
		setPopupWidth : function(pwidth) {
			var me = this;
			var popup = me.dockedItems.get(3);

			if (popup != null) {
				popup.setWidth(pwidth);
			}
		},
		changeIcon : function(pressed) {
			var btn = this.dockedItems.get(1).items.get(12);
			btn.pressed = pressed;
			if (pressed) {
				btn.removeCls('starttimerbutton');
				btn.addCls('stoptimerbutton');
				btn.setTooltip(langs.stop_timer);
			} else {
				btn.removeCls('stoptimerbutton');
				btn.addCls('starttimerbutton');
				btn.setTooltip(langs.start_timer);
			}
		}
	});
	
	var initQueryHeadStore = Ext.create('Ext.data.Store', {
		autoLoad : false,
		fields : [ 'cols', 'fields', 'legend', 'marquee', 'refresh', 'display', 'combo', 'lights', 'popup', 'advances', 'relation', 'is_cross' ],
		proxy : {
			type : 'ajax',
			url : '../../Funcs/EKB/KanBan.dsc',
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
				sql_id : headkid
			}
		}
	});
			/******************************************表身**************************************************/
	var pageSize = 9999;
	var body_id =kanbanInfo.body_kbid;
	var gridClass = kanbanInfo.func_id+'-'+body_id + "grid";
	runner = new Ext.util.TaskRunner();
	var gridStore = Ext.create('Ext.data.Store', {
		autoLoad : false,
		fields : [],
		proxy : {
			type : 'ajax',
			timeout : 300000,
			url : '../../Funcs/EKB/KanBan.dsc',
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
				action : 'query',
				page : 1,
				pagesize : pageSize,
				filter : '',
				sort : '',
				sql_id : body_id,
				conn_id : kanbanInfo.conn_id
			}
		},
		pageSize : pageSize,
		gridreload : function(headPanel, filter) {
			var store = this;
			var panel1 = Ext.getCmp(kanbanInfo.func_id+'-'+'hp1');
			var panel2 = Ext.getCmp(kanbanInfo.func_id+'-'+'hp2');
			var fstr = "";
			var sstr = "";
			/*
			if (filter == null || filter.length == 0) {
				var col = panel1.condiParams.condi_col;
				var condi = panel1.condiParams.condi_type;
				var value = panel1.condiParams.condi_value;
				if (col != null && col != "") {
					fstr = " and " + col + " ";

					if (condi == "startwith") {
						fstr += "like '" + value + "%' ";
					} else if (condi == "endwith") {
						fstr += "like '%" + value + "' ";
					} else if (condi == "like") {
						fstr += "like '%" + value + "%' ";
					} else if (condi == "notlike") {
						fstr += "not like '%" + value + "%' ";
					} else {
						fstr += condi + " '" + value + "' ";
					}
				}
			} else {
				fstr = filter;
			}
			
			if (panel2.sortParams.sort_col != null && panel2.sortParams.sort_col != "") {
				sstr = panel2.sortParams.sort_col + " " + panel2.sortParams.sort_type;
			}*/
			fstr = filter;
			if (store.getProxy().extraParams.hasOwnProperty('page')) {
				store.getProxy().extraParams['page'] = 1;
				store.currentPage = 1;
			}
			if (store.getProxy().extraParams.hasOwnProperty('pagesize')) {
				store.getProxy().extraParams['pagesize'] = panel2.sortParams.page_size;
				store.pageSize = panel2.sortParams.page_size;
			}

			if (store.getProxy().extraParams.hasOwnProperty('filter')) {
				if (kanbanInfo.relation_filter != null && kanbanInfo.relation_filter.length > 0) {
					store.getProxy().extraParams['filter'] = kanbanInfo.relation_filter;
				} else {
					store.getProxy().extraParams['filter'] = fstr;
				}
			}
			if (bodygrid != null) {
				bodygrid.getSelectionModel().select(0);
			}
//			if (bodygrid != null) {
//				bodygrid.getSelectionModel().deselectAll();
//			}
		}
	});
	
	marqueepanel = Ext.create('Ext.panel.Panel', {
		region : 'north',
		height : 30,
		width : '100%',
		border : 2,
		dock : 'top',
		dataTask : null,
		scrollTask : null,
		marqueeUrl : '../../Funcs/EKB/KanBan.dsc',
		html : '',
		startMarquee : function(panel, refreshGap, showType) {
			var mRunner = new Ext.util.TaskRunner();
			var taskScroll = null;
			var mStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'message' ],
				proxy : {
					type : 'ajax',
					url : this.marqueeUrl,
					actionMethods : {
						read : 'POST'
					},
					reader : {
						root : 'marquee'
					},
					extraParams : {
						DCITag : postvalue,
						uid : uid,
						action : 'marqueeData',
						sql_id : body_id,
						conn_id : kanbanInfo.conn_id
					}
				}
			});

			function loadData(callback) {
				if (mStore != null) {
					mStore.load(function(record) {
						if (record.length > 0) {
							var value = "";
							if (showType == '1') {
								for ( var i = 0; i < record.length; i++) {
									if (i == 0) {
										value = '<div style="font-size:22px;"><Marquee height="' + panel.height
												+ '" scrollamount="3" onmouseover="this.stop()" onmouseout="this.start()">' + record[i].get('message');
									} else {
										value += "                              " + record[i].get('message');
									}

									if (i == record.length - 1) {
										value += "</div></Marquee>";
									}
								}
								panel.update(value);
							} else {
								if (panel.scrollTask != null) {
									panel.scrollTask.stop();
									panel.body.dom.scrollTop = 0;
								}
								var t = "";
								for ( var i = 0; i < record.length; i++) {
									if (i == 0) {
										t = '<div style="font-size:22px;text-align:center;overflow:hidden;height:'+panel.height+'px;">' + record[i].get('message')
												+ "</div>";
										value = '<div>' + t;
									} else {
										value += '<div  style="font-size:22px;text-align:center;overflow:hidden;height:'+panel.height+'px;">' + record[i].get('message')
												+ '</div>';
									}

									if (i == record.length - 1) {
										value += t + "</div>";
									}
								}

								panel.update(value);
								if (panel.scrollTask != null) {
									panel.scrollTask.start(5000);
								}
							}
							callback(panel);
						}
					});
				}
			}

			var taskGetData = mRunner.newTask({
				run : function() {
					loadData(function(panel) {
					});
				}
			});

			this.dataTask = taskGetData;
			loadData(function(panel) {
				taskGetData.start(refreshGap * 1000);
				var currRow = 0;
				if (showType == "2") {
					taskScroll = mRunner.newTask({
						run : function() {
							if (mStore.getCount() > 0) {
								if (currRow == 0) {
									panel.body.dom.scrollTop = 0;
								}
								if (currRow == mStore.getCount() - 1) {
									currRow = 0;
								} else {
									currRow++;
								}
								panel.scrollBy(0, panel.height, true);
							}
						}
					});
					panel.scrollTask = taskScroll;
					taskScroll.start(5000);
				} else {
					panel.scrollTask = null;
				}
			});

		},
		stopScrollTask : function() {
			if (this.dataTask != null) {
				this.dataTask.stop();
			}
			if (this.scrollTask != null) {
				this.scrollTask.stop();
			}
		},
		listeners : {
			el : {
				mouseover : function(event, html, eOpts) {
					if (marqueepanel != null) {
						if (marqueepanel.scrollTask != null) {
							marqueepanel.scrollTask.stop();
						}
					}
				}
			},
			body : {
				mouseout : function(event, html, eOpts) {
					if (marqueepanel != null) {
						if (marqueepanel.scrollTask != null) {
							marqueepanel.scrollTask.start(5000);
						}
					}
				}
			}
		}
	});
	
	var popuptask = runner.newTask({
		timegap : 600,
		run : function() {
			if (bodygrid != null) {
				bodygrid.nextSelectedRow();
			}
		},
		setTimeGap : function(timegap) {
			if (timegap == null || timegap == "" || timegap < 1) {
				this.timegap = 600;
			} else {
				this.timegap = timegap;
			}
		},
		executeTask : function() {
			this.start(this.timegap * 1000);
			bodypopuptaskIsRunning = true;
		},
		stopTask : function() {
			this.stop();
			bodypopuptaskIsRunning = false;
		}
	});
	
	var bodygrid = Ext.create('Ext.grid.Panel', {
		//height : 400,  
		region : 'center',
		renderer : "component",
		stripeRows : true,
		autoScroll : true,
		loadMask : true,
		allowDeselect : true,
		usePopup : true,
		id:kanbanInfo.func_id+'-'+'bodygrid',
		popupImgPath : '',
		popupImgCols : [],
		popupTitleCols : [],
		popup_refresh_gap : 600,
		selModel : {
			allowDeselect : true
		},
		enableTextSelection : true,
		viewConfig : {
			forceFit : false,
			autoLoad : false
		},
		//enableLocking : true,
		store : null,
		bodyCls : gridClass,
		componentCls : gridClass,
		columns : [],
		sqlRelationStore : Ext.create('Ext.data.Store', {
			fields : [ 'col_id', 'target_sql_id', 'target_col_id', 'target_ori_col_id', "target_sql_name" ],
			autoLoad : false,
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		}),
		currpage : 0,
		totalpage : 0,
		timer : null,
		dockedItems : [
				{
					xtype : 'pagingtoolbar',
					store : null,
					dock : 'bottom',
					displayInfo : false,
					items : [ '-', {
						//text : 'stop timer',
						enableToggle : true,
						width : 50,
						tooltip : '',
						cls : 'starttimerbutton',
						toggleHandler : function(btn, pressed) {
							//if (main != null && !main.globalRunning) {
								this.btnClick(btn, pressed);
							//}
						},
						btnClick : function(btn, pressed) {
							var timer = this.up('panel').timer;
							if (timer != null) {
								if (pressed) {
									timer.executeTask();
									/* btn.removeCls('starttimerbutton');
									btn.addCls('stoptimerbutton');
									btn.setTooltip(langs.stop_timer); */
								} else {
									timer.stopTask();
									/* btn.removeCls('stoptimerbutton');
									btn.addCls('starttimerbutton');
									btn.setTooltip(langs.start_timer); */
								}
							}
						}
					}, '-', {
						xtype : 'label',
						text : langs.refresh_gap
					}, {
						xtype : 'label',
						text : '',
						margin : '0 0 0 10'
					}, '-', Ext.create('DCI.Customer.Img', {
						src : "../../images/icons/CusGridFormat.png",
						width : 20,
						height : 20,
						hidden : true,
						listeners : {
							afterrender : function(c) {
								Ext.create('Ext.tip.ToolTip', {
									target : c.getEl(),
									html : langs.cus_format
								});
							}
						}
					}), Ext.create('DCI.Customer.Img', {
						src : "../../images/button_icon/BtnCrossDatabase.png",
						width : 20,
						height : 20,
						hidden : true,
						listeners : {
							afterrender : function(c) {
								Ext.create('Ext.tip.ToolTip', {
									target : c.getEl(),
									html : langs.cross_db
								});
							}
						}
					}) ],
					listeners : {
						beforechange : function(pagingbar, page, eOpts) {
							if (this.store.getProxy().extraParams.hasOwnProperty('page')) {
								if (page == null || page == 0) {
									this.store.getProxy().extraParams['page'] = 1;
								} else {
									this.store.getProxy().extraParams['page'] = page;
								}
							}
							this.store.load();
						},
						change : function(pagingbar, pageData, eOpts) {
							if (pageData == null || pageData.currentPage == null || pageData.pageCount == null) {
								this.up('panel').setCurrPage(0);
								this.up('panel').setTotlaPage(0);
							} else {
								this.up('panel').setCurrPage(pageData.currentPage);
								this.up('panel').setTotlaPage(pageData.pageCount);
							}
						}
					}
				},
				marqueepanel,
				{
					xtype : 'panel',
					height : '100%',
					width : 200,
					border : 5,
					title : "",
					dock : 'right',
					layout : 'fit',
					collapsible : true,
					collapseDirection : "right",
					animCollapse : false,
					hidden : false,
					buttonAlign : 'left',
					resizable : true,
					items : [ {
						xtype : 'panel',
						height : '100%',
						title : "",
						width : '100%',
						border : 0
					} ],
					header : {
						items : [ {
							xtype : 'button',
							cls : 'playbutton',
							pressedCls : 'pausebutton',
							width : 15,
							height : 15,
							enableToggle : true,
							handler : function() {
								var me = this;
								if (popuptask != null) {
									if (me.pressed) {
										popuptask.executeTask();
									} else {
										popuptask.stopTask();
									}
								}
							}
						} ]
					},
					showPopup : function(title, path) {
						var me = this;
						var displaypanel = me.items.get(0);

						var popup = me.items.get(0);
						if (popup != null) {
							if (title == null || title == "") {
								popup.setTitle(" ");
							} else {
								popup.setTitle(title);
							}

							Ext.Ajax.request({
								method : 'POST',
								url : './../../PublicCtrl.dsc',
								params : {
									DCITag : postvalue,
									uid : uid,
									action : 'B64Encode',
									str : path
								},
								success : function(a) {
									var result = Ext.JSON.decode(a.responseText);
									var h = '<img width=' + displaypanel.getWidth() + ' height=' + displaypanel.getHeight() + ' src="./../../ImageLoader.dsc?imgpath='
											+ result.result + '"/>';
									displaypanel.update(h);
								}
							});
						}
					}
				} ],
		listeners : {
			selectionchange : function(smodel, selected, eOpts) {
				var me = this;
				if (me.usePopup && selected.length > 0) {
					var file = "";
					var title = "";
					var popup = me.dockedItems.get(3);
					for ( var i = 0; i < me.popupImgCols.length; i++) {
						if (i == 0) {
							file = selected[0].get(me.popupImgCols[i]);
						} else {
							file += "--" + selected[0].get(me.popupImgCols[i]);
						}
					}

					for ( var i = 0; i < me.popupTitleCols.length; i++) {
						if (i == 0) {
							title = selected[0].get(me.popupTitleCols[i]);
						} else {
							title += "--" + selected[0].get(me.popupTitleCols[i]);
						}
					}
					
					if (popup != null) {
						var tmp = me.popupImgPath.replace("\\", "\"");
						tmp = tmp.replace("\"", "\\") + "\\";
						file = tmp + file + ".jpg";
						popup.showPopup(title, file);
					}

				}
			},
			celldblclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {

			},
			cellcontextmenu : function(view, td, cellIndex, record, tr, rowIndex, e, eOpts) {
				var me = this;

				if (me.sqlRelationStore != null && me.sqlRelationStore.getCount() > 0) {
					var gridMenu = Ext.create('Ext.menu.Menu', {
						mpanel : main.parentComp,
						items : [],
						clickEvent : function(value, targetsql, targetcol) {
							var me = this;
							if (me.mpanel != null) {
								Ext.Ajax.request({
									method : 'POST',
									url : '../../Funcs/EKB/KanBan.dsc',
									params : {
										action : 'funcInfo',
										uid : uid,
										DCITag : postvalue,
										func_id : body_id,
										conn_id : kanbanInfo.conn_id
									},
									success : function(a) {
										if (a.responseText == null || a.responseText == '' || a.responseText.length == 0) {
											alert("open kanban " + targetsql + " fail");
										} else {
											var result = Ext.JSON.decode(a.responseText);
											var funcInfo = {
												url : result.url,
												text : result.text,
												func_package : result.func_package,
												can_edit : result.can_edit,
												filter : " and " + targetcol + " = '" + value + "'"
											};
											me.mpanel.beforeAddCheck(targetsql, kanbanInfo.conn_id, funcInfo, true);
										}
									}
								});
							}
						}
					});

					for ( var i = 0; i < me.sqlRelationStore.getCount(); i++) {
						if (me.columns[cellIndex].dataIndex == me.sqlRelationStore.getAt(i).get("col_id")) {
							gridMenu.add({
								text : me.sqlRelationStore.getAt(i).get("target_sql_name"),
								currcol : me.sqlRelationStore.getAt(i).get("col_id"),
								cellValue : record.get(me.columns[cellIndex].dataIndex),
								targetsql : me.sqlRelationStore.getAt(i).get("target_sql_id"),
								targetcol : me.sqlRelationStore.getAt(i).get("target_col_id"),
								targetoricol : me.sqlRelationStore.getAt(i).get("target_ori_col_id"),
								handler : function() {
									var menu = this.up('menu');
									menu.clickEvent(this.cellValue, this.targetsql, this.targetoricol);
								}
							});
						}
					}

					if (gridMenu.items.length > 0) {
						e.stopEvent();
						gridMenu.showAt(e.getXY());
					}
				}
			}
		},
		addCusFormatIcon : function() {
			var tbar = this.dockedItems.items[1];
			tbar.items.get(17).show();
		},
		rmCusFormatIcon : function() {
			var tbar = this.dockedItems.items[1];
			tbar.items.get(17).hide();
		},
		setCrossDBIcon : function(is_cross) {
			var tbar = this.dockedItems.items[1];
			if (is_cross) {
				tbar.items.get(18).show();
			} else {
				tbar.items.get(18).hide();
			}
		},
		initBodyGrid : function(timer, columns, store, relationRecord) {
			this.timer = timer;
			var cols = columns;
			var me = this;
			var tmpcols = [];
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
						DCITag : postvalue,
						uid : uid,
						action : 'getGFormat',
						gridid : body_id
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
						Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').addCusFormatIcon();
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

						if (cols != null && cols.length != 0) {
							for ( var i = 0; i < cols.length; i++) {
								cols[i]['hidden'] = true;
								tmpcols.push(cols[i]);
							}
						}
					} else {
						tmpcols = cols;
					}

					if (relationRecord != null) {
						for ( var j = 0; j < tmpcols.length; j++) {
							for ( var i = 0; i < relationRecord.length; i++) {
								if ("col" + relationRecord[i].col_id == tmpcols[j].colid) {
									if (tmpcols[j].hasOwnProperty('componentCls')) {
										tmpcols[j].componentCls = 'dci-realtion-column-header';
									} else {
										tmpcols[j]['componentCls'] = 'dci-realtion-column-header';
									}

									//Ext.util.CSS.updateRule("." + gridClass + " .x-column-header .x-column-header-inner", "background-color", "red");
									break;
								}
							}
						}
					}

					me.reconfigure(store, tmpcols);
				} else {
					if (relationRecord != null) {
						for ( var j = 0; j < cols.length; j++) {
							for ( var i = 0; i < relationRecord.length; i++) {
								if ("col" + relationRecord[i].col_id == cols[j].colid) {
									if (cols[j].hasOwnProperty('componentCls')) {
										cols[j].componentCls = 'dci-realtion-column-header';
									} else {
										cols[j]['componentCls'] = 'dci-realtion-column-header';
									}
									break;
								}
							}
						}
					}
					me.reconfigure(store, cols);
				}
				//initbody
				y3=me.child('pagingtoolbar');
				me.child('pagingtoolbar').bindStore(gridStore);
				//me.enableLocking = true;

				/*if (hp1 != null) {
					hp1.targetGrid = me;
				}*/
			});

			me.sqlRelationStore.loadData(relationRecord);
		},
		reloadBodyGridFormat : function(columns, store) {
			var cols = columns;
			var me = this;
			var tmpcols = [];
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
						DCITag : postvalue,
						uid : uid,
						action : 'getGFormat',
						gridid : body_id
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
						Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').addCusFormatIcon();
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

						if (cols != null && cols.length != 0) {
							for ( var i = 0; i < cols.length; i++) {
								cols[i]['hidden'] = true;
								tmpcols.push(cols[i]);
							}
						}
					} else {
						tmpcols = cols;
					}

					if (me.sqlRelationStore != null) {
						for ( var j = 0; j < tmpcols.length; j++) {
							for ( var i = 0; i < me.sqlRelationStore.getCount(); i++) {
								if ("col" + me.sqlRelationStore.getAt(i).data.col_id == tmpcols[j].colid) {
									if (tmpcols[j].hasOwnProperty('componentCls')) {
										tmpcols[j].componentCls = 'dci-realtion-column-header';
									} else {
										tmpcols[j]['componentCls'] = 'dci-realtion-column-header';
									}

									//Ext.util.CSS.updateRule("." + gridClass + " .x-column-header .x-column-header-inner", "background-color", "red");
									break;
								}
							}
						}
					}

					me.reconfigure(store, tmpcols);
				} else {
					if (me.sqlRelationStore != null) {
						for ( var j = 0; j < cols.length; j++) {
							for ( var i = 0; i < me.sqlRelationStore.getCount(); i++) {
								if ("col" + me.sqlRelationStore.getAt(i).data.col_id == cols[j].colid) {
									if (cols[j].hasOwnProperty('componentCls')) {
										cols[j].componentCls = 'dci-realtion-column-header';
									} else {
										cols[j]['componentCls'] = 'dci-realtion-column-header';
									}
									break;
								}
							}
						}
					}
					me.reconfigure(store, cols);
				}
				//reloadbody
				y4=me.child('pagingtoolbar');
				me.child('pagingtoolbar').bindStore(gridStore);
				//me.enableLocking = true;

				if (hp1 != null) {
					hp1.targetGrid = me;
				}
			});
		},
		setCurrPage : function(value) {
			this.currpage = value;
		},
		setTotlaPage : function(value) {
			this.totalpage = value;
		},
		nextPage : function() {
			var cpage = 0;
			var panel = this;
			if (panel.currpage == null || panel.currpage == 0) {
				cpage = 1;
			} else {
				cpage = panel.currpage + 1;
				if (cpage > panel.totalpage) {
					cpage = 1;
				}
			}

			if (panel.store.getProxy().extraParams.hasOwnProperty('page')) {
				panel.store.getProxy().extraParams['page'] = cpage;
				panel.store.currentPage = cpage;
			}
			panel.store.load(function(record) {
				panel.getSelectionModel().deselectAll();
				panel.setCurrPage(cpage);
				panel.setRefreshTime();
			});
		},
		globalNextPage : function(initPage) {
			var cpage = 0;
			var panel = this;
			if (initPage == -1) {
				if (panel.store.getProxy().extraParams.hasOwnProperty('page')) {
					panel.store.getProxy().extraParams['page'] = 1;
					panel.store.currentPage = 1;
				}
				panel.store.load(function(record) {
					panel.getSelectionModel().deselectAll();
					panel.setCurrPage(1);
					panel.setRefreshTime();

					if (panel.usePopup) {
						var sModel = panel.getSelectionModel();

						if (sModel != null) {
							sModel.select(0);
						}
					}
				});
			} else {
				cpage = panel.currpage + 1;
				if (cpage > panel.totalpage) {
					cpage = 1;
				}
				if (panel.usePopup) {
					var sModel = panel.getSelectionModel();
					if (sModel != null) {
						var sindex = 0;
						if (sModel.lastSelected == null) {
							sModel.select(0);
						} else {
							if (sModel.lastSelected.index % panel.getStore().pageSize == panel.getStore().getCount() - 1 || sModel.getSelection() == null
									|| sModel.getSelection().length == 0) {
								if (panel.store.getProxy().extraParams.hasOwnProperty('page')) {
									panel.store.getProxy().extraParams['page'] = cpage;
									panel.store.currentPage = cpage;
								}
								panel.store.load(function(record) {
									sModel.deselectAll();
									panel.setCurrPage(cpage);
									panel.setRefreshTime();
									panel.getSelectionModel().select(0);
								});
							} else {
								sindex = (sModel.lastSelected.index + 1) % panel.getStore().pageSize;
								panel.getSelectionModel().select(sindex);
							}
						}
					}
				} else {
					if (panel.store.getProxy().extraParams.hasOwnProperty('page')) {
						panel.store.getProxy().extraParams['page'] = cpage;
						panel.store.currentPage = cpage;
					}
					panel.store.load(function(record) {
						panel.getSelectionModel().deselectAll();
						panel.setCurrPage(cpage);
						panel.setRefreshTime();
					});
				}
			}

		},
		nextSelectedRow : function() {
			var me = this;
			var sModel = me.getSelectionModel();
			if (sModel != null) {
				var data = me.store.getAt(0);
				if(data!=null){
						me.store.removeAt(0);
						me.store.insert(me.store.getCount(),data);
						me.getSelectionModel().select(0);
						//me.setPopUpRefreshTime();
					
				}
			}
		},
		setRefreshTimeTitle : function(gap, unit) {
			var label = this.dockedItems.get(1).items.get(14);
			if (label != null) {
				label.setText(langs.refresh_gap + "  " + gap + "  " + unit);
			}
		},
		setRefreshTime : function() {
			var label = this.dockedItems.get(1).items.get(15);
			if (label != null) {
				label.setText(Ext.Date.format(new Date(Ext.Date.now()), 'Y/m/d H:i:s'));
			}
		},
		setPopUpRefreshTime : function() {
			var label = this.dockedItems.get(1).items.get(15);
			if (label != null) {
				label.setText(Ext.Date.format(new Date(Ext.Date.now()), 'Y/m/d H:i:s'));
			}
		},
		setButtonStatus : function(isrunning) {
			var btn = this.dockedItems.get(1).items.get(12);
			btn.toggle(isrunning);
			btn.btnClick(btn, isrunning);
		},
		setPopupInfo : function(infos) {
			var me = this;
			var popup = me.dockedItems.get(3);
			if (infos != null) {
				if (infos.use_popup) {
					popup.setVisible(true);
					me.usePopup = infos.use_popup;
					me.popupImgPath = infos.popup_dir;
					me.popup_refresh_gap = infos.popup_refresh_gap;
					if (infos.imgcols != null && infos.imgcols.trim().length > 0) {
						me.popupImgCols = infos.imgcols.split(';');
					}

					if (infos.titlecols != null && infos.titlecols.trim().length > 0) {
						me.popupTitleCols = infos.titlecols.split(';');
					}
				} else {
					popup.setVisible(false);
					me.usePopup = false;
					me.popupImgPath = "";
					me.popupImgCols = [];
					me.popupTitleCols = [];
					me.popup_refresh_gap = 600;
				}
			}
			if (popuptask != null) {
				popuptask.setTimeGap(me.popup_refresh_gap);
			}

			if (me.usePopup) {
				popup.header.items.get(0).toggle(true);
				popup.header.items.get(0).handler();
			}
		},
		getPopupWidth : function() {
			var me = this;
			var popup = me.dockedItems.get(3);
			var pwidth = 200;
			if (popup != null) {
				pwidth = popup.getWidth();
			}

			return pwidth;
		},
		setPopupWidth : function(pwidth) {
			var me = this;
			var popup = me.dockedItems.get(3);

			if (popup != null) {
				popup.setWidth(pwidth);
			}
		},
		changeIcon : function(pressed) {
			var btn = this.dockedItems.get(1).items.get(12);
			btn.pressed = pressed;
			if (pressed) {
				btn.removeCls('starttimerbutton');
				btn.addCls('stoptimerbutton');
				btn.setTooltip(langs.stop_timer);
			} else {
				btn.removeCls('stoptimerbutton');
				btn.addCls('starttimerbutton');
				btn.setTooltip(langs.start_timer);
			}
		}
	});
	
	var initQueryGridStore = Ext.create('Ext.data.Store', {
		autoLoad : false,
		fields : [ 'cols', 'fields', 'legend', 'marquee', 'refresh', 'display', 'combo', 'lights', 'popup', 'advances', 'relation', 'is_cross' ],
		proxy : {
			type : 'ajax',
			url : '../../Funcs/EKB/KanBan.dsc',
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
				sql_id : body_id
			}
		}
	});
			/************************************************************************************************/
			var headgridpanel = Ext.create('Ext.panel.Panel', {
				lightsData:null,
				layout : 'fit',
				anchor: '0 40%', 
				title : kanbanInfo.head_name,
				header : {
					height : 35
				},
				items : [ headgrid],
				listeners : {
				}
			});
			var bodygridpanel = Ext.create('Ext.panel.Panel', {
				lightsData:null,
				layout : 'fit',
				anchor: '0 80%', 
				title : kanbanInfo.body_name,
				header : {
					height : 35
				},
				items : [ bodygrid],
				listeners : {
				}
			});

			var gridpanel = Ext.create('Ext.panel.Panel', {
				layout : 'anchor',
				region : 'center',
				autoScroll : true,
				items : [ headgridpanel,bodygridpanel],
				listeners : {
				}
			});
			
			var hp2 = Ext.create('Ext.panel.Panel', {
				title : "",
				id:kanbanInfo.func_id+'-'+'hp2',
				anchor : '100% 50%',
				layout : 'absolute',
				border : 0,
				bodyStyle : {
					background : '#d3e1f1'
				},
				bodyPadding : 0,
				sortParams : {
					sort_col : '',
					sort_type : '',
					page_size : pageSize
				},
				displayParams : {
					fs : 13,
					fc : '000000',
					rh : 28,
					bc : 'FFFFFF',
					bcEven : 'CCE4F6'
				},
				bodygrid : null,
				headgrid:null,
				items : [ Ext.create('DCI.Customer.ComboBox', {
					fieldLabel : langs.sort_column,
					name : 'sort_column',
					labelWidth : 90,
					width : 250,
					x : 0,
					y : 8,
					listeners : {
						change : function(comp, newValue, oldValue, eOpts) {
							var panel = this.up('panel');
							panel.sortParams["sort_col"] = newValue;
						}
					}
				}), Ext.create('DCI.Customer.ComboBox', {
					fieldLabel : "",
					labelWidth : 0,
					width : 90,
					store : {
						fields : [ 'label', 'value' ],
						data : [ {
							"label" : langs.asc,
							"value" : "asc"
						}, {
							"label" : langs.desc,
							"value" : "desc"
						} ]
					},
					x : 250,
					y : 8,
					listeners : {
						change : function(comp, newValue, oldValue, eOpts) {
							var panel = this.up('panel');
							panel.sortParams["sort_type"] = newValue;
						}
					}
				}), Ext.create('DCI.Customer.NumberBox', {
					fieldLabel : langs.page_size,
					labelWidth : 60,
					defaultvalue : pageSize,
					maxValue : 9999,
					minValue : 1,
					disabled:true,
					width : 150,
					x : 340,
					y : 8,
					listeners : {
						change : function(comp, newValue, oldValue, eOpts) {
							var panel = this.up('panel');
							panel.sortParams["page_size"] = newValue;
						}
					}
				}), {
					xtype : 'button',
					cls : 'configbutton',
					tooltip : langs.favorties_setup,
					x : 500,
					y : 5,
					width : 30,
					height : 30,
					handler : function() {
						var params = this.up('panel').displayParams;
						var win = Ext.create('Ext.window.Window', {
							title : langs.favorties_setup,
							width : 500,
							height : 300,
							minWidth : 300,
							minHeight : 200,
							layout : 'fit',
							plain : true,
							modal : true,
							items : [ {
								xtype : 'panel',
								layout : 'anchor',
								items : [ {
									xtype : 'slider',
									anchor : '80% 25%',
									fieldLabel : langs.font_size,
									border : 1,
									value : params.fs,
									minValue : 8,
									maxValue : 48,
									increment : 1,
									plugins : new Ext.slider.Tip(),
									listeners : {
										change : function(slider, newValue, thumb, eOpts) {
											changeKanbanGrid(newValue, gridClass, "FS");
											if (bodygrid != null) {
												if (bodygrid.columns.length > 0) {
													try {
														var w = bodygrid.columns[0].width;
														bodygrid.columns[0].setWidth(w + 10);
														bodygrid.columns[0].setWidth(w);
													} catch (e) {
														if (window.console) {
															console.log(e.message);
														}
													}
												}
											}
											changeKanbanGrid(newValue, headGridClass, "FS");
											if (headgrid != null) {
												if (headgrid.columns.length > 0) {
													try {
														var w = headgrid.columns[0].width;
														headgrid.columns[0].setWidth(w + 10);
														headgrid.columns[0].setWidth(w);
													} catch (e) {
														if (window.console) {
															console.log(e.message);
														}
													}
												}
											}
											params.fs = newValue;
										},
										afterrender : function(slider, eOpts) {
											var me = this;
											me.labelCell.dom.attributes.getNamedItem("valign").value = "middle";
										}
									}
								}, {
									xtype : 'dcicolorpicker',
									fieldLabel : langs.font_color,
									defaultColor : '000000',
									curColor : params.fc,
									width : 180,
									listeners : {
										change : function(picker, newValue, oldValue, eOpts) {
											//changeKanbanGrid(newValue, gridClass, "FC");
											changeKanbanGrid(newValue, gridClass, "FC");
											changeKanbanGrid(newValue, headGridClass, "FC");
											params.fc = newValue;
										}
									}
								}, {
									xtype : 'slider',
									anchor : '80% 25%',
									fieldLabel : langs.row_height,
									value : params.rh,
									minValue : 16,
									maxValue : 100,
									increment : 4,
									plugins : new Ext.slider.Tip(),
									listeners : {
										change : function(slider, newValue, thumb, eOpts) {
											//changeKanbanGrid(newValue, gridClass, "RH");
											changeKanbanGrid(newValue, gridClass, "RH");
											changeKanbanGrid(newValue, headGridClass, "RH");
											params.rh = newValue;
										},
										afterrender : function(slider, eOpts) {
											var me = this;
											me.labelCell.dom.attributes.getNamedItem("valign").value = "middle";
										}
									}
								}, /* {
											xtype : 'dcicolorpicker',
											fieldLabel : langs.bg_color,
											defaultColor : 'FFFFFF',
											curColor : params.bc,
											width : 180,
											listeners : {
												change : function(picker, newValue, oldValue, eOpts) {
													changeKanbanGrid(newValue, gridClass, "BC");
													params.bc = newValue;
												}
											}
								} */
								{
									bodyStyle : 'border:0 ;',
									items : [ {
										layout : 'column',
										bodyStyle : 'border:0 ;',
										items : [ {
											columnWidth : .4,
											bodyStyle : 'border:0;',
											items : [ {
												xtype : "dcicolorpicker",
												fieldLabel : langs.bg_color,
												defaultColor : 'FFFFFF',
												curColor : params.bc,
												width : 180,
												listeners : {
													change : function(picker, newValue, oldValue, eOpts) {
														changeKanbanGrid(newValue, gridClass, "BC");
														changeKanbanGrid(newValue, headGridClass, "BC");
														params.bc = newValue;
													}
												}
											} ]
										}, {
											columnWidth : .5,
											bodyStyle : 'border:0;',
											items : [ {
												xtype : "dcicolorpicker",
												defaultColor : 'CCE4F6',
												curColor : params.bcEven,
												width : 70,
												listeners : {
													change : function(picker, newValue, oldValue, eOpts) {
														//alert(newValue);alert(gridClass);
														//changeKanbanGrid(newValue, gridClass, "BcEven");
														changeKanbanGrid(newValue, gridClass , "BcEven");
														changeKanbanGrid(newValue, headGridClass, "BcEven");
														params.bcEven = newValue;
													}
												}

											} ]
										} ]
									} ]
								} ]
							} ],
							buttons : [ {
								text : langs.close,
								handler : function() {
									this.up('.window').close();
								}
							} ]
						});

						win.show();
					}
				}/*, {
					xtype : 'button',
					cls : 'toexcelbutton',
					tooltip : langs.to_excel,
					width : 30,
					height : 30,
					x : 530,
					y : 5,
					handler : function() {
						var me = this;
						var grid = me.up('panel').bodygrid;
						if (grid != null) {
							//buildHtml(grid.columns, grid.getStore().getRange(0), "1", function(datas) {
							var downloadform = Ext.create('Ext.form.Panel', {
								standardSubmit : true,
								url : '../../Funcs/EKB/KanBan.dsc',
								method : 'POST'
							});

							downloadform.submit({
								target : '_blank',
								params : {
									DCITag : postvalue,
									uid : uid,
									conn_id : kanbanInfo.conn_id,
									func_id : kanbanInfo.func_id,
									action : 'excel',
									filter : grid.getStore().getProxy().extraParams['filter'],
									sort : grid.getStore().getProxy().extraParams['sort'],
									cols : Ext.encode(buildColObj(grid.columns)),
									ctype : "1"
								}
							});
							//});
						}
					}
				}, {
					xtype : 'button',
					cls : 'tohtmlbutton',
					tooltip : langs.to_html,
					width : 30,
					height : 30,
					x : 560,
					y : 5,
					handler : function() {
						var me = this;
						var grid = me.up('panel').bodygrid;
						if (grid != null) {
							//buildHtml(grid.columns, grid.getStore().getRange(0), "2", function(datas) {
							var downloadform = Ext.create('Ext.form.Panel', {
								standardSubmit : true,
								url : '../../Funcs/EKB/KanBan.dsc',
								method : 'POST'
							});

							downloadform.submit({
								target : '_blank',
								params : {
									DCITag : postvalue,
									uid : uid,
									conn_id : kanbanInfo.conn_id,
									func_id : kanbanInfo.func_id,
									action : 'html',
									filter : grid.getStore().getProxy().extraParams['filter'],
									sort : grid.getStore().getProxy().extraParams['sort'],
									cols : Ext.encode(buildColObj(grid.columns)),
									ctype : "2"
								}
							});
							//});
						}
					}
				}*/ ],
				setComboData : function(record) {
					var me = this;
					var combo = me.items.get(0);
					var cStore = combo.getStore();
					record.unshift({
						label : langs.no_sort,
						value : ''
					});
					if (cStore != null) {
						cStore.loadData(record);
						combo.loadDefault();
					}
					record.shift();
				},
				setCss : function(displayInfo) {
					var hp2 = this;
					var cssText = "";
					var hdCssText = "";
					var evenText = "";
					//Ext.util.CSS.removeStyleSheet(gridClass);
					//headGridClass
					/*****headgridClass*****/
					removeAllStyle(headGridClass);

					if (displayInfo.grid_font_size != null && displayInfo.grid_font_size != "") {
						cssText += "font-size:" + displayInfo.grid_font_size + "pt;";
						evenText += "font-size:" + displayInfo.grid_font_size + "pt;";
						hdCssText += "font-size:" + displayInfo.grid_font_size + "pt;";
						hp2.displayParams.fs = displayInfo.grid_font_size;
						Ext.util.CSS.createStyleSheet("." + headGridClass + " .x-progress-text {font-size:" + (displayInfo.grid_font_size - 2) + "pt;}", headGridClass);
					} else {
						cssText += "font-size:13pt;";
						evenText += "font-size:13pt;";
						hdCssText += "font-size:13pt;";
						hp2.displayParams.fs = '13';
						Ext.util.CSS.createStyleSheet("." + headGridClass + " .x-progress-text {font-size:11pt}", headGridClass);
					}
					if (displayInfo.grid_font_color != null && displayInfo.grid_font_color != "") {
						cssText += "color:#" + displayInfo.grid_font_color + ";";
						evenText += "color:#" + displayInfo.grid_font_color + ";";
						hdCssText += "color:#" + displayInfo.grid_font_color + ";";
						hp2.displayParams.fc = displayInfo.grid_font_color;
					} else {
						cssText += "color:black;";
						evenText += "color:black;";
						hdCssText += "color:black;";
						hp2.displayParams.fc = '000000';
					}
					if (displayInfo.grid_row_height != null && displayInfo.grid_row_height != "") {
						cssText += "height:" + displayInfo.grid_row_height + "px;";
						evenText += "height:" + displayInfo.grid_row_height + "px;";
						hp2.displayParams.rh = displayInfo.grid_row_height;
						Ext.util.CSS.createStyleSheet("." + headGridClass + " .x-progress-default {height:" + (displayInfo.grid_row_height - 8) + "px;}", headGridClass);
						Ext.util.CSS.createStyleSheet("." + headGridClass + " .x-progress-text {line-height:" + (displayInfo.grid_row_height - 8) + "px;}", headGridClass);
					} else {
						cssText += "height:28px;";
						evenText += "height:28px;";
						hp2.displayParams.rh = '28';
						Ext.util.CSS.createStyleSheet("." + headGridClass + " .x-progress-default {height:20px;}", headGridClass);
						Ext.util.CSS.createStyleSheet("." + headGridClass + " .x-progress-text {line-height:20px;}", headGridClass);
					}

					if (displayInfo.grid_row_color != null && displayInfo.grid_row_color != "") {
						cssText += "background-color:#" + displayInfo.grid_row_color + ";";
						hp2.displayParams.bc = displayInfo.grid_row_color;
					} else {
						cssText += "background-color:withe;";
						hp2.displayParams.bc = 'FFFFFF';
					}

					if (displayInfo.grid_row_even_color != null && displayInfo.grid_row_even_color != "") {
						evenText += "background-color:#" + displayInfo.grid_row_even_color + ";";
						hp2.displayParams.bcEven = displayInfo.grid_row_even_color;
					} else {
						evenText += "background-color:#cce4f6;";
						hp2.displayParams.bcEven = 'cce4f6';
					}
					Ext.util.CSS.createStyleSheet('.' + headGridClass + ' .x-grid-row .x-grid-cell {' + cssText + '}', headGridClass);
					Ext.util.CSS.createStyleSheet('.' + headGridClass + ' .x-grid-row-alt .x-grid-cell {' + evenText + '}', headGridClass);
					Ext.util.CSS.createStyleSheet('.' + headGridClass + ' .x-column-header {' + hdCssText + '}', headGridClass);
					/*****bodygridClass*****/
					removeAllStyle(gridClass);

					if (displayInfo.grid_font_size != null && displayInfo.grid_font_size != "") {
						cssText += "font-size:" + displayInfo.grid_font_size + "pt;";
						evenText += "font-size:" + displayInfo.grid_font_size + "pt;";
						hdCssText += "font-size:" + displayInfo.grid_font_size + "pt;";
						hp2.displayParams.fs = displayInfo.grid_font_size;
						Ext.util.CSS.createStyleSheet("." + gridClass + " .x-progress-text {font-size:" + (displayInfo.grid_font_size - 2) + "pt;}", gridClass);
					} else {
						cssText += "font-size:13pt;";
						evenText += "font-size:13pt;";
						hdCssText += "font-size:13pt;";
						hp2.displayParams.fs = '13';
						Ext.util.CSS.createStyleSheet("." + gridClass + " .x-progress-text {font-size:11pt}", gridClass);
					}
					if (displayInfo.grid_font_color != null && displayInfo.grid_font_color != "") {
						cssText += "color:#" + displayInfo.grid_font_color + ";";
						evenText += "color:#" + displayInfo.grid_font_color + ";";
						hdCssText += "color:#" + displayInfo.grid_font_color + ";";
						hp2.displayParams.fc = displayInfo.grid_font_color;
					} else {
						cssText += "color:black;";
						evenText += "color:black;";
						hdCssText += "color:black;";
						hp2.displayParams.fc = '000000';
					}
					if (displayInfo.grid_row_height != null && displayInfo.grid_row_height != "") {
						cssText += "height:" + displayInfo.grid_row_height + "px;";
						evenText += "height:" + displayInfo.grid_row_height + "px;";
						hp2.displayParams.rh = displayInfo.grid_row_height;
						Ext.util.CSS.createStyleSheet("." + gridClass + " .x-progress-default {height:" + (displayInfo.grid_row_height - 8) + "px;}", gridClass);
						Ext.util.CSS.createStyleSheet("." + gridClass + " .x-progress-text {line-height:" + (displayInfo.grid_row_height - 8) + "px;}", gridClass);
					} else {
						cssText += "height:28px;";
						evenText += "height:28px;";
						hp2.displayParams.rh = '28';
						Ext.util.CSS.createStyleSheet("." + gridClass + " .x-progress-default {height:20px;}", gridClass);
						Ext.util.CSS.createStyleSheet("." + gridClass + " .x-progress-text {line-height:20px;}", gridClass);
					}

					if (displayInfo.grid_row_color != null && displayInfo.grid_row_color != "") {
						cssText += "background-color:#" + displayInfo.grid_row_color + ";";
						hp2.displayParams.bc = displayInfo.grid_row_color;
					} else {
						cssText += "background-color:withe;";
						hp2.displayParams.bc = 'FFFFFF';
					}

					if (displayInfo.grid_row_even_color != null && displayInfo.grid_row_even_color != "") {
						evenText += "background-color:#" + displayInfo.grid_row_even_color + ";";
						hp2.displayParams.bcEven = displayInfo.grid_row_even_color;
					} else {
						evenText += "background-color:#cce4f6;";
						hp2.displayParams.bcEven = 'cce4f6';
					}
					Ext.util.CSS.createStyleSheet('.' + gridClass + ' .x-grid-row .x-grid-cell {' + cssText + '}', gridClass);
					Ext.util.CSS.createStyleSheet('.' + gridClass + ' .x-grid-row-alt .x-grid-cell {' + evenText + '}', gridClass);
					Ext.util.CSS.createStyleSheet('.' + gridClass + ' .x-column-header {' + hdCssText + '}', gridClass);
				},
				setInitData : function(record, grid) {
					var me = this;
					var comp = me.items.get(0);
					if (record["sort_col"] == null || record["sort_col"] == "") {
						comp.loadDefault();
					} else {
						comp.setValue(record["sort_col"]);
					}

					comp = me.items.get(1);
					if (record["sort_type"] == null || record["sort_type"] == "") {
						comp.loadDefault();
					} else {
						comp.setValue(record["sort_type"]);
					}

					comp = me.items.get(2);
					if (record["page_size"] != null && record["page_size"] != "") {
						comp.setValue(record["page_size"]);
					} else {
						comp.loadDefault();
					}

					me.bodygrid = grid;
				}
			});

			var headpanel = Ext.create('Ext.panel.Panel', {
				region : 'north',
				height : 120,
				collapsible : true,
				title : kanbanInfo.func_name,
				header : {
					height : 35
				},
				layout : 'anchor',
				items : [ hp1, hp2 ],
				listeners : {
					collapse : function() {
						updatePanelOpen(kanbanInfo, postvalue, uid, langs, '0');
					},
					expand : function() {
						updatePanelOpen(kanbanInfo, postvalue, uid, langs, '1');
					}
				}
			});

			var headpagetask = runner.newTask({
				timegap : 600,
				displaygap : 30,
				run : function() {
					if (headgrid != null) {
						headgrid.nextPage();
					}
				},
				setTimeGap : function(timegap) {
					if (timegap == null || timegap == "" || timegap < 1) {
						this.timegap = 600;
					} else {
						this.timegap = timegap;
					}
				},
				getTimeGap : function() {
					return this.timegap;
				},
				setDisplayGap : function(timegap) {
					if (timegap == null || timegap == "" || timegap < 1) {
						this.displaygap = 600;
					} else {
						this.displaygap = timegap;
					}
				},
				getDisplayGap : function() {
					return this.displaygap;
				},
				executeTask : function() {
					this.start(this.timegap * 1000);
					headtaskIsRunning = true;
					if (headgrid != null) {
						headgrid.changeIcon(true);
					}
				},
				stopTask : function() {
					this.stop();
					headtaskIsRunning = false;
					if (headgrid != null) {
						headgrid.changeIcon(false);
					}
				}
			});

			

			var pagetask = runner.newTask({
				timegap : 600,
				displaygap : 30,
				run : function() {
					if (bodygrid != null) {
						bodygrid.nextPage();
					}
				},
				setTimeGap : function(timegap) {
					if (timegap == null || timegap == "" || timegap < 1) {
						this.timegap = 600;
					} else {
						this.timegap = timegap;
					}
				},
				getTimeGap : function() {
					return this.timegap;
				},
				setDisplayGap : function(timegap) {
					if (timegap == null || timegap == "" || timegap < 1) {
						this.displaygap = 600;
					} else {
						this.displaygap = timegap;
					}
				},
				getDisplayGap : function() {
					return this.displaygap;
				},
				executeTask : function() {
					this.start(this.timegap * 1000);
					bodytaskIsRunning = true;
					if (bodygrid != null) {
						bodygrid.changeIcon(true);
					}
				},
				stopTask : function() {
					this.stop();
					bodytaskIsRunning = false;
					if (bodygrid != null) {
						bodygrid.changeIcon(false);
					}
				}
			});

			/*var popuptask = runner.newTask({
				timegap : 600,
				run : function() {
					if (bodygrid != null) {
						bodygrid.nextSelectedRow(0);
					}
				},
				setTimeGap : function(timegap) {
					if (timegap == null || timegap == "" || timegap < 1) {
						this.timegap = 600;
					} else {
						this.timegap = timegap;
					}
				},
				executeTask : function() {
					this.start(this.timegap * 1000);
					bodypopuptaskIsRunning = true;
				},
				stopTask : function() {
					this.stop();
					bodypopuptaskIsRunning = false;
				}
			});*/
			var main = Ext.create('DCI.Customer.SubPanel', {
				id : kanbanInfo.func_id + 'Main',
				renderTo : kanbanInfo.func_id + 'Page',
				autoDestroy : true,
				pagetype : 'kanban',
				border : 0,
				bodyPadding : '0 5 5 5',
				layout : 'border',
				widthChangeControls : [ headpanel,gridpanel],
				items : [ headpanel,gridpanel ],
				globalRunning : false,
				beforeClose : function() {
					if (marqueepanel != null) {
						marqueepanel.stopScrollTask();
					}

					if (pagetask != null) {
						pagetask.stopTask();
					}
					if (headpagetask != null) {
						headpagetask.stopTask();
					}

					if (popuptask != null) {
						popuptask.stopTask();
					}
					removeAllStyle(gridClass);
				},
				focusPage : function() {
					if (!this.globalRunning) {
						if (bodytaskIsRunning) {
							pagetask.executeTask();
						}
						if (headtaskIsRunning) {
							headpagetask.executeTask();
						}

						if (bodypopuptaskIsRunning) {
							popuptask.executeTask();
						}
						var me = this;
						if (Ext.get(me.renderTo) != null) {
							me.resize(Ext.get(me.renderTo).getWidth(), Ext.get(me.renderTo).getHeight());
						}

						if (bodygrid != null) {
							bodygrid.setRefreshTimeTitle(pagetask.getDisplayGap(), langs.minutes);
						}
						if (headgrid != null) {
							headgrid.setRefreshTimeTitle(headpagetask.getDisplayGap(), langs.minutes);
						}
					}

				},
				leavePage : function() {
					if (bodytaskIsRunning) {
						pagetask.stop();
					}
					if(headtaskIsRunning){
						headpagetask.stop();
						}
					if (bodypopuptaskIsRunning) {
						popuptask.stop();
					}
				},
				globalTimerEvent : function(initPage, gap) {
					if (pagetask != null) {
						pagetask.stop();
						if (bodygrid != null) {
							bodygrid.changeIcon(false);
						}
					}

					if (popuptask != null) {
						popuptask.stop();
					}

					if (bodygrid != null) {
						bodygrid.setRefreshTimeTitle(gap, langs.seconds);
						bodygrid.globalNextPage(initPage);
					}
					this.globalRunning = true;
					return bodygrid.currpage;
				},
				globalTimerStop : function() {
					this.globalRunning = false;
					if (bodytaskIsRunning) {
						pagetask.executeTask();
					}
					if(headtaskIsRunning){
						headpagetask.executeTask();
					}
					

					if (bodypopuptaskIsRunning) {
						Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').getSelectionModel().select(0);
						popuptask.executeTask();
					}

					if (bodygrid != null) {
						bodygrid.setRefreshTimeTitle(pagetask.getDisplayGap(), langs.minutes);
					}
				},
				switchToNextTab : function() {
					var tonextpage = false;

					if (bodygrid.currpage == bodygrid.totalpage) {
						if (bodygrid.usePopup) {
							var sModel = bodygrid.getSelectionModel();

							if (sModel != null) {
								if (sModel.lastSelected != null) {
									if (sModel.lastSelected.index % bodygrid.getStore().pageSize >= bodygrid.getStore().getCount() - 1) {
										tonextpage = true;
									}
								}
							}
						} else {
							tonextpage = true;
						}
					} else {
						tonextpage = false;
					}

					return tonextpage;
				},
				relationReload : function(filter) {
					var me = this;
					kanbanInfo.relation_filter = filter;
					me.items.get(1).getStore().gridreload(me.items.get(0), "reload");
				}
			});
			
			var initStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'headDisplay','headAdvances','bodyDisplay' ],
				proxy : {
					type : 'ajax',
					url : '../../CUS/Funcs/EKB/SubKanBan02420302.dsc',
					actionMethods : {
						read : 'POST'
					},
					reader : {
						type : 'json'
					},
					extraParams : {
						DCITag : postvalue,
						uid : uid,
						action : 'initFirst',
						sql_id : kanbanInfo.func_id
					}
				}
			});
			initStore.load(function(hprecord){
				if (hprecord.length > 0) {
					
				initQueryHeadStore.load(function(record) {
					if (record.length > 0) {
						if (headGridStore != null) {
							var fields = record[0].get('fields');
							for ( var f = 0; f < fields.length; f++) {
								if (fields[f].sortType == "") {
									fields[f].sortType = function(value) {
										var newvalue = parseFloat(String(value).replace(/,/g, '').replace('$', ''));
										return isNaN(newvalue) ? 0 : newvalue;
									};
								}
							}
							headGridStore.model.setFields(fields);

							if (headgrid != null) {
								headgrid.setCrossDBIcon(record[0].get('is_cross'));
								headgrid.initHeadGrid(headpagetask, record[0].get('cols'), headGridStore, record[0].get('relation'));
								headgrid.setPopupInfo(record[0].get('popup'));
							}
							//*******************************ff
							/*if (marqueepanel != null) {
								var minfo = record[0].get('marquee');
								if (minfo.use_marquee == 1) {
									if (minfo.marquee_location == "1") {
										marqueepanel.setDocked('bottom');
									} else {
										marqueepanel.setDocked('top');
									}
									headgrid.doLayout();
									marqueepanel.startMarquee(marqueepanel, minfo.marquee_refresh_gap, minfo.marquee_type);
								} else {
									marqueepanel.setVisible(false);
								}
							}*/

							if (headpagetask != null) {
								var rinfo = record[0].get('refresh');
								headpagetask.setTimeGap(rinfo.refresh_gap);
								headpagetask.setDisplayGap(rinfo.display_gap);
								headgrid.setRefreshTimeTitle(rinfo.display_gap, langs.minutes);
								if (rinfo.auto_refresh == "1") {
									headpagetask.executeTask();
								}
								headgrid.setButtonStatus(rinfo.auto_refresh == "1");
							}

							if (headgridpanel != null) {
								headgridpanel.lightsData = record[0].get('lights');
							}
							if (hp1 != null && hp2 != null) {
								var displayInfo = hprecord[0].get('headDisplay');
								hp2.setCss(displayInfo);
								hp1.setComboData(record[0].get('combo'));
								hp2.setComboData(record[0].get('combo'));
								hp1.setInitData(displayInfo);
								hp2.setInitData(displayInfo, headgrid);
								headgrid.setPopupWidth(displayInfo.popup_width);
							}

							if (advWin != null) {
								advWin.setComboData(record[0].get('combo'));
								advWin.lightsData = record[0].get('lights');
								advWin.setInitData(headpanel, headGridStore, advWin, hprecord[0].get('headAdvances'));
							}
							var bodycondi="";
							//gridStore=Ext.getCmp(kanbanInfo.func_id+'-'+'bodygrid').getStore();
							headGridStore.gridreload(headpanel, advWin.condiStr,gridStore,bodycondi);
							//gridStore.gridreload(headpanel, kanbanInfo.condition+"'"+headGridStore.getAt(0).get(kanbanInfo.combinecolumn)+"'");
							//gridStore.gridreload(headpanel, kanbanInfo.condition+"'"+headGridStore.getAt(0).get(kanbanInfo.combinecolumn)+"'");
							var tab = Ext.getCmp('tab' + kanbanInfo.func_id);

							if (main != null) {
								main.setParent(tab.up());
							}
							
						}
						if (headgridpanel != null) {
							var legend = record[0].get('legend');
							if (legend == null || legend.length == 0) {
								headgridpanel.setTitle("<table><tr><td><font size='2'>" + kanbanInfo.head_name + "</font></td></tr></table>");
							} else {
								headgridpanel.setTitle(record[0].get('legend').replace("$$$kn$$$", kanbanInfo.head_name));
							}
						}
						
					}
					initQueryGridStore.load(function(brecord) {
						if (brecord.length > 0) {
							if (gridStore != null) {
								var fields = brecord[0].get('fields');
								for ( var f = 0; f < fields.length; f++) {
									if (fields[f].sortType == "") {
										fields[f].sortType = function(value) {
											var newvalue = parseFloat(String(value).replace(/,/g, '').replace('$', ''));
											return isNaN(newvalue) ? 0 : newvalue;
										};
									}
								}
								gridStore.model.setFields(fields);

								if (bodygrid != null) {
									bodygrid.setCrossDBIcon(brecord[0].get('is_cross'));
									bodygrid.initBodyGrid(pagetask, brecord[0].get('cols'), gridStore, brecord[0].get('relation'));
									bodygrid.setPopupInfo(brecord[0].get('popup'));
								}

								if (marqueepanel != null) {
									var minfo = brecord[0].get('marquee');
									if (minfo.use_marquee == 1) {
										if (minfo.marquee_location == "1") {
											marqueepanel.setDocked('bottom');
										} else {
											marqueepanel.setDocked('top');
										}
										bodygrid.doLayout();
										marqueepanel.startMarquee(marqueepanel, minfo.marquee_refresh_gap, minfo.marquee_type);
									} else {
										marqueepanel.setVisible(false);
									}
								}

								if (pagetask != null) {
									var rinfo = brecord[0].get('refresh');
									pagetask.setTimeGap(rinfo.refresh_gap);
									pagetask.setDisplayGap(rinfo.display_gap);
									bodygrid.setRefreshTimeTitle(rinfo.display_gap, langs.minutes);
									if (rinfo.auto_refresh == "1") {
										pagetask.executeTask();
									}
									bodygrid.setButtonStatus(rinfo.auto_refresh == "1");
								}

								if (bodygridpanel != null) {
									bodygridpanel.lightsData = brecord[0].get('lights');
								}

								if (hp1 != null && hp2 != null) {
									var displayInfo = hprecord[0].get('bodyDisplay');
									hp2.setCss(displayInfo);
									//hp1.setComboData(brecord[0].get('combo'));
									//hp2.setComboData(brecord[0].get('combo'));
									//hp1.setInitData(displayInfo);
									//hp2.setInitData(displayInfo, bodygrid);
									bodygrid.setPopupWidth(displayInfo.popup_width);
								}

							/*	if (advWin != null) {
									advWin.setComboData(brecord[0].get('combo'));
									advWin.lightsData = brecord[0].get('lights');
									advWin.setInitData(headpanel, gridStore, advWin, brecord[0].get('advances'));
								}*/
								var tab = Ext.getCmp('tab' + kanbanInfo.func_id);

								if (main != null) {
									main.setParent(tab.up());
								}
							}

							if (bodygridpanel != null) {
								var legend = brecord[0].get('legend');
								if (legend == null || legend.length == 0) {
									bodygridpanel.setTitle("<table><tr><td><font size='2'>" + kanbanInfo.body_name + "</font></td></tr></table>");
								} else {
									bodygridpanel.setTitle(brecord[0].get('legend').replace("$$$kn$$$", kanbanInfo.body_name));
								}
							}
						}
					});
				});	
				
				}
			});
			main.resize(Ext.get(kanbanInfo.func_id + "Page").getWidth(), Ext.get(kanbanInfo.func_id + "Page").getHeight());
		}
	});
</script>
</head>
<body>
	<div id="<dcitag:reqParam paramName="func_id"></dcitag:reqParam>Page" class="page_div"></div>

	<!-- <audio id="audio1" src="success.wav" preload="auto" autobuffer></audio> -->
</body>
</html>