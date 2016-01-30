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
	/*
	function changeKanbanGrid(newValue, classname, changeType) {
		try {
			var pName = null;
			var pValue = null;

			if (changeType == "FS") {
				pName = "font-size";
				pValue = newValue + "pt";
			} else if (changeType == "FC") {
				pName = "color";
				pValue = "#" + newValue;
			} else if (changeType == "BC") {
				pName = "background-color";
				pValue = "#" + newValue;
			} else if (changeType == "RH") {
				pName = "height";
				pValue = newValue + "px";
			}
			if (pName != null) {
				Ext.util.CSS.updateRule("." + classname + " .x-grid-row .x-grid-cell", pName, pValue);
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
	}*/

	Ext.onReady(function() {
				
				var localKeys = [ "toolbar_query_title", "condi_col", "save_msg", "load_def_fail", "load_def_success", "load_def_result_title", "load_def_confirm_title",
						"load_def_confirm_msg", "save_fail", "save_success", "save_result_title", "save_fail", "sort_column", "page_size", "no_query", "no_sort", "asc", "desc",
						"refresh_gap", "minutes", "favorties_setup", "start_with", "end_with", "bigger", "bigger_equal", "smaller", "smaller_equal", "equal", "not_equal", "like",
						"not_like", "font_size", "row_height", "bg_color", "font_color", "condi_relation", "adv_search", "save_condi", "load_def_format", "load_def_condi",
						"to_excel", "to_html", "start_timer", "stop_timer", "seconds", "cus_format"
						,"process_id","process_name", "report_id", "report_name","dept_name","owner","PE001" ];
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
						showPage(infos, records[0].get('dcitagValue'), langs, uid);
					}
				});
				function showPage(kanbanInfo, postvalue, langs, uid) {	
					Array.prototype.containsArray = function(e){  
						for(i=0;i<this.length && this[i]!=e;i++);  
						return !(i==this.length);  
					}
					var langArr=["process_id","process_name", "report_id", "report_name","dept_name","owner"];  

					var gridClass = kanbanInfo.func_id + "grid";
					var hp1 = Ext.create('Ext.panel.Panel', {
						title : "",
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
						items : [{
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

								for (var i = 0; i < cols.length; i++) {
									var obj = new Object();
									obj['col_id'] = cols[i].colid;
									obj['col_index'] = cols[i].getIndex();
									obj['col_width'] = cols[i].width;
									//obj['col_visible'] = cols[i].hidden ? 0 : 1;
									colinfo.push(obj);
								}

								Ext.Ajax.request({
									method : 'POST',
									url : '../../Funcs/EKB/PE001.dsc',
									params : {
										DCITag : postvalue,
										uid : uid,
										action : 'saveFormat',
										gridid : kanbanInfo.func_id,
										datas : Ext.encode(condiInfo),
										coldatas : Ext.encode(colinfo)
									},
									success : function(a) {
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
											url : '../../Funcs/EKB/PE001.dsc',
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
													grid_row_height : 16,
													grid_row_color : 'FFFFFF'
												};

												panel.setCss(displayInfo);
												if (grid != null) {
													grid.setPopupWidth(200);
													if (initQueryStore != null && initQueryStore.getCount() > 0) {
														initQueryStore.load(function(records) {
															grid.reloadBodyGridFormat(records[0].get('cols'), gridStore);
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
						}],
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
						}
					});
					//hp2
					var hp2 = Ext.create('Ext.panel.Panel', {
						title : "",
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
							//page_size : pageSize
						},
						displayParams : {
							fs : 13,
							fc : '000000',
							rh : 16,
							bc : 'FFFFFF'
						},
						grid : null,
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
													changeKanbanGrid(newValue, gridClass, "FC");
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
													changeKanbanGrid(newValue, gridClass, "RH");
													params.rh = newValue;
												},
												afterrender : function(slider, eOpts) {
													var me = this;
													me.labelCell.dom.attributes.getNamedItem("valign").value = "middle";
												}
											}
										}, {
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
						}, {
							xtype : 'button',
							cls : 'toexcelbutton',
							tooltip : langs.to_excel,
							width : 30,
							height : 30,
							x : 530,
							y : 5,
							handler : function() {
								var grid = this.up().up().up().down("panel[id=bodygrid]");
								if (grid != null) {
									//buildHtml(grid.columns, grid.getStore().getRange(0), "1", function(datas) {
									var downloadform = Ext.create('Ext.form.Panel', {
										standardSubmit : true,
										url : '../../Funcs/EKB/PE001.dsc',
										method : 'POST'
									});
									test=grid.columns;
									downloadform.submit({
										target : '_blank',
										params : {
											DCITag : postvalue,
											uid : uid,
											conn_id : kanbanInfo.conn_id,
											func_id : kanbanInfo.func_id,
											action : 'excel',
											filter : '',//grid.getStore().getProxy().extraParams['filter'],
											sort : '',//grid.getStore().getProxy().extraParams['sort'],
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
								var grid = me.up().up().up().down("panel[id=bodygrid]");
								if (grid != null) {
									//buildHtml(grid.columns, grid.getStore().getRange(0), "2", function(datas) {
									var downloadform = Ext.create('Ext.form.Panel', {
										standardSubmit : true,
										url : '../../Funcs/EKB/PE001.dsc',
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
											filter : '',//grid.getStore().getProxy().extraParams['filter'],
											sort : '',//grid.getStore().getProxy().extraParams['sort'],
											cols : Ext.encode(buildColObj(grid.columns)),
											ctype : "2"
										}
									});
									//});
								}
							}
						} ],
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
							Ext.util.CSS.removeStyleSheet(gridClass);
							if (displayInfo.grid_font_size != null && displayInfo.grid_font_size != "") {
								cssText += "font-size:" + displayInfo.grid_font_size + "pt;";
								hdCssText += "font-size:" + displayInfo.grid_font_size + "pt;";
								hp2.displayParams.fs = displayInfo.grid_font_size;
							} else {
								cssText += "font-size:13pt;";
								hdCssText += "font-size:13pt;";
							}
							if (displayInfo.grid_font_color != null && displayInfo.grid_font_color != "") {
								cssText += "color:#" + displayInfo.grid_font_color + ";";
								hdCssText += "color:#" + displayInfo.grid_font_color + ";";
								hp2.displayParams.fc = displayInfo.grid_font_color;
							} else {
								cssText += "color:black;";
								hdCssText += "color:black;";
							}
							if (displayInfo.grid_row_height != null && displayInfo.grid_row_height != "") {
								cssText += "height:" + displayInfo.grid_row_height + "px;";
								hp2.displayParams.rh = displayInfo.grid_row_height;
							} else {
								cssText += "height:16px;";
							}
							if (displayInfo.grid_row_color != null && displayInfo.grid_row_color != "") {
								cssText += "background-color:#" + displayInfo.grid_row_color + ";";
								hp2.displayParams.bc = displayInfo.grid_row_color;
							} else {
								cssText += "background-color:withe;";
							}
							Ext.util.CSS.createStyleSheet('.' + gridClass + ' .x-grid-row .x-grid-cell {' + cssText + '}', gridClass);
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
					//_hp2

					var headpanel = Ext.create('Ext.panel.Panel', {
						region : 'north',
						height : 120,
						collapsible : true,
						title : eval('langs.'+kanbanInfo.func_id),
						header : {
							height : 35
						},
						layout : 'anchor',
						id:'hp',
						items : [ hp1, hp2 ]
					});

					var initQueryStore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'cols', 'fields', 'colSet' ],
						proxy : {
							type : 'ajax',
							url : '../../Funcs/EKB/PE001.dsc',
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
								conn_id : kanbanInfo.conn_id
							}
						}
					});
					initQueryStore.load(function(record) {
						if (record.length > 0) {
							var colSet = record[0].get('colSet').split(';');
							//if (gridStore != null) {
							var fields = record[0].get('cols');
							var datas = record[0].get('fields');
							var gstore = Ext.create("Ext.data.Store", {
								data : datas,
								fields : []
							});
							gstore.model.setFields(colSet);
							if (gstore != null) {
								gstore.reload();
							} else
								gstore.load();
							//建立 GridPanel
							var grid = Ext.create("Ext.grid.Panel", {
								region : 'center',
								id:'bodygrid',
								stripeRows : true,
								height : 500,
								width : 1100,
								autoScroll : true,
								//title : kanbanInfo.func_id,
								columns : fields,
								store : gstore,
								renderTo:"ggrid",
								//renderer : "component",
								selModel : {
									allowDeselect : true
								},
								//frame : true,
								viewConfig : {
									forceFit : true,
									autoLoad : false
									/*getRowClass: function(record, rowIndex, rowParams, store){
										alert(record.length);
									    var columnHead = record.get(getColumnHeader(record.length));
									    if(langArr.containsArray(columnHead)) {
									        return eval('langs.'+columnHead);
								    	}
									}*/
								},
								loadMask : true,
								//enableLocking : true,
								enableTextSelection : true,
								bodyCls : gridClass,
								componentCls : gridClass,
								//****hyperLink
								listeners : {
									cellclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
										var me = this;
										if (me.columns[cellIndex].dataIndex === "report_id") {
											Ext.Ajax.request({
												method : 'POST',
												url : '../../Funcs/EKB/PE001.dsc',
												params : {
													action : 'funcInfo',
													uid : uid,
													DCITag : postvalue,
													func_id : record.get(me.columns[cellIndex].dataIndex),
													conn_id : kanbanInfo.conn_id
												},
												success : function(a) {
													if (a.responseText == null || a.responseText == '' || a.responseText.length == 0) {
														alert("open kanban " + record.get(me.columns[cellIndex].dataIndex) + " fail");
													} else {
														var result = Ext.JSON.decode(a.responseText);
														var funcInfo = {
															url : result.url,
															text : result.text,
															func_package : result.func_package,
															can_edit : result.can_edit,
														//filter : " and " + targetcol + " = '" + value + "'"
														};
														mainPanel.addKanBanTab(record.get(me.columns[cellIndex].dataIndex), result.conn_id, funcInfo, false);
													}
												},
												failure : function(a) {
													alert("open kanban " + record.get(me.columns[cellIndex].dataIndex) + " fail");
												}
											});
										}
										//alert(me.columns[cellIndex].dataIndex);
										//alert(record.get(me.columns[cellIndex].dataIndex));
									}
								}

							//@@@@hyperLink
							});
							var main = Ext.create('DCI.Customer.SubPanel', {
								//id : kanbanInfo.func_id + 'Main',
								id:'x',
								renderTo : kanbanInfo.func_id + 'Page',
								autoDestroy : true,
								pagetype : 'kanban',
								border : 0,
								bodyPadding : '0 5 5 5',
								layout : 'border',
								widthChangeControls : [ headpanel,grid ],
								items : [ headpanel,grid ],
								//globalRunning : false,
								beforeClose : function() {
									/*if (marqueepanel != null) {
										marqueepanel.stopScrollTask();
									}

									if (pagetask != null) {
										pagetask.stopTask();
									}

									if (popuptask != null) {
										popuptask.stopTask();
									}*/

									Ext.util.CSS.removeStyleSheet(gridClass);
								},
								focusPage : function() {

								},
								leavePage : function() {

								},relationReload : function(filter) {
									var me = this;
									kanbanInfo.relation_filter = filter;
									me.items.get(1).getStore().gridreload(me.items.get(0), "reload");
								}
							});
							main.resize(Ext.get(kanbanInfo.func_id + "Page").getWidth(), Ext.get(kanbanInfo.func_id + "Page").getHeight());
							/*gridStore.model.setFields(fields);

							if (bodygrid != null) {
								bodygrid.initBodyGrid(pagetask, record[0].get('cols'), gridStore, record[0].get('relation'));
								bodygrid.setPopupInfo(record[0].get('popup'));
							}

							gridStore.gridreload(headpanel, advWin.condiStr);

							var tab = Ext.getCmp('tab' + kanbanInfo.func_id);

							if (main != null) {
								main.setParent(tab.up());
							}*/
							//}
						}
					});

					
				}
			});
</script>
</head>
<body>
	<div id="<dcitag:reqParam paramName="func_id"></dcitag:reqParam>Page" class="page_div"></div>
	<div id="ggrid"></div>
</body>
</html>