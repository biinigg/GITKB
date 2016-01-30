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
		var localKeys = [ "PE000Config","pid", "report_id", "report_name", "dept_name", 
		          "delete_msg", "delete_fail", "delete_success", "delete_result_title", "delete_confirm_title", "delete_confirm_msg", "delete_fail",
		         "load_def_format", "load_def_fail", "load_def_success", "load_def_result_title", "owner", "sequ_num","process_id","process_name","is_work","schedule_time",
				"load_def_confirm_title", "load_def_confirm_msg", "save_fail", "save_success", "save_result_title", "save_fail", "move", "still", "cus_format" ];
		var keys = localKeys.concat(globeKeys);
		var uid = '<dcitag:reqParam paramName="uid"></dcitag:reqParam>';
		this.dcistore.setMultiLangKeys(keys);
		this.dcistore.setUid(uid);
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				langs = buildMultiLangObjct(keys, records[0].get('langValues'));
				var langs = buildMultiLangObjct(keys, records[0].get('langValues'));
				showPage(records[0].get('dcitagValue'), langs, '<dcitag:reqParam paramName="canEdit"></dcitag:reqParam>', uid);
			}
		});
		
		function showPage(postvalue, langs, recvAuth, uid) {
			var canEdit = recvAuth == "1";
			/* var headform = Ext.create('DCI.Customer.HeadFormPanel', {
				height : 100,
				url : '../../Configs/PE000Config.dsc',
				languageObj : langs,
				items : [],
				showQueryWindow : function() {
					this.resetDataLoaded();
					var pageSize = 50;
					var initStore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'cols' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/PE000Config.dsc',
							actionMethods : {
								read : 'POST'
							},
							reader : {
								type : 'json'
							},
							extraParams : {
								DCITag : postvalue
							}
						}
					});

					var gstore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [],
						proxy : {
							type : 'ajax',
							url : '../../Configs/PE000Config.dsc',
							actionMethods : {
								read : 'POST'
							},
							reader : {
								root : 'items',
								totalProperty : 'total'
							},
							extraParams : {
								DCITag : postvalue,
								action : 'search',
								page : 1,
								pagesize : pageSize,
								filter : ''
							}
						},
						pageSize : pageSize
					});

					var gridColumns = [];

					var openwin = Ext.create('DCI.Customer.QueryWindow', {
						height : 200,
						width : 450,
						headform : this
					});

					initStore.load(function(records) {
						openwin.setInitObjects(records, gstore, gridColumns);
						openwin.show();
					});
				}
			});

			var headpanel = Ext.create('DCI.Customer.HeadPanel', {
				height : 200,
				width : '100%',
				headform : headform,
				postvalue : postvalue
			}); */
			//看板ID下拉選單
			function getComboValue(combobox, value) {
				var found = false;
				var displayValue = "";
				if (combobox != null) {
					store = combobox.getStore();

					for ( var i = 0; i < store.getCount(); i++) {
						if (value == store.getAt(i).get("value")) {
							displayValue = store.getAt(i).get("value");
							found = true;
							break;
						}
					}
				}

				if (!found) {
					displayValue = value;
				}

				return displayValue;
			}
			var reportId_Combo = Ext.create('DCI.Customer.ComboBox', {
				labelWidth : 0,
				displayField :'value',
				store : {
					fields : [ 'value','label' , 'package' ],
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
						var gridPanel = me.up().up();
						for ( var i = 0; i < store.getCount(); i++) {
							if (store.getAt(i).get("value") == newValue) {
								if (gridStore != null) {
									var ridx = gridStore.getCount() - 1;
									if (ridx < 0) {
										ridx = 0;
									}
									if (gridPanel.getSelectionModel().hasSelection()) {
										   var selectedRecord = gridPanel.getSelectionModel().getSelection()[0];
										   var row =gridPanel.store.indexOf(selectedRecord);
									}
									gridStore.getAt(row).set("report_name", store.getAt(i).get("label"));
									gridStore.getAt(row).commit();
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
			
			Ext.override(Ext.form.TimeField, { 
				getValue: function () { 
				return Ext.Date.format(this.value, 'H:i'); 
				} 
			}); 
			var scheduleTime = {
					xtype      : 'timefield',
					fieldLabel : "",
					labelWidth : 0,
					minValue:'00:00',
					maxValue:'23:59',
					increment:1,
					format: 'H:i',
					msgTarget:'qtip'
			}
			
			var bodypanel = Ext.create('DCI.Customer.BodyGridPanel', {
				title : langs.PE000Config,
				languageObj : langs,
				gridid : 'PE000Config_G01',
				actionurl : '../../Configs/PE000Config.dsc',
				postvalue : postvalue,
				uid : uid,
				//headform : headform,
				//keyfields : [ 'report_id' ],
				canEdit : canEdit,
				//stopUsingBtn : [ 0, 2 ],
				addNewRow : function() {
					var grid = this.items.get(1);
					var gridStore = grid.getStore();
					if (gridStore != null) {
						gridStore.add({
							sequ_num:'',
							process_id:'',
							process_name:'',
							is_work:true,
							report_id:'',
							report_name:'',
							dept_name:'',
							owner:'',
							schedule_time:'17:30',
							moditag : 1,
							moditp : 'add'
						});
					}
	
					grid.getView().select(gridStore.getCount() - 1);
				}
			});

			var bodyColumns = [ {
					text : langs.report_id,
					dataIndex : 'report_id',
					colid : 'colreport_id',
					width : 200,
					editor : reportId_Combo,
					renderer : function(value) {
						return getComboValue(reportId_Combo, value);
					}
			},
			 {
				text : langs.report_name,
				dataIndex : 'report_name',
				colid : 'colreport_name',
				width : 150
			}, {
				text : langs.dept_name,
				dataIndex : 'dept_name',
				colid : 'colprocess_name',
				width : 150
				,editor : Ext.create('DCI.Customer.TextField', {
					fieldLabel : "",
					labelWidth : 0,
					width : '100%',
					defaultvalue : '',
					allowBlank : false
				})
			} , {
				text : langs.owner,
				dataIndex : 'owner',
				colid : 'colowner',
				width : 150
				,editor : Ext.create('DCI.Customer.TextField', {
					fieldLabel : "",
					labelWidth : 0,
					width : '100%',
					defaultvalue : '',
					allowBlank : false
				})
			} , {
				text : langs.sequ_num,
				dataIndex : 'sequ_num',
				colid : 'sequ_num',
				width : 150
				,editor : Ext.create('DCI.Customer.TextField', {
					fieldLabel : "",
					labelWidth : 0,
					width : '100%',
					defaultvalue : '',
					allowBlank : false
				})
			} , {
				text : langs.process_id,
				dataIndex : 'process_id',
				colid : 'process_id',
				width : 150
				,editor : Ext.create('DCI.Customer.TextField', {
					fieldLabel : "",
					labelWidth : 0,
					width : '100%',
					defaultvalue : '',
					allowBlank : false
				})
			} , {
				text : langs.process_name,
				dataIndex : 'process_name',
				colid : 'colprocess_name',
				width : 150
				,editor : Ext.create('DCI.Customer.TextField', {
					fieldLabel : "",
					labelWidth : 0,
					width : '100%',
					defaultvalue : '',
					allowBlank : false
				})
			} , {
				text : langs.is_work,
				dataIndex : 'is_work',
				colid : 'colis_work',
				width : 150,
				xtype : 'dcicheckcolumn',
				canEditControl : function(col, rowIndex) {
					var me = this;
					var gridStore = me.up().up().getStore();
					if (gridStore == null) {
						return false;
					} else {
						return true;//gridStore.getAt(rowIndex).get("func_package") != "EKB";
					}
				}
			}, {
				text : langs.schedule_time,
				dataIndex :  'schedule_time',
				colid : 'colschedule_time',
				width : 150
				,editor :scheduleTime
			} ];
			
			var bodyStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : ['pid', 'report_id', 'report_name', 'dept_name', 'owner', 'sequ_num','process_id','process_name','is_work','schedule_time', 'moditag', 'moditp' ],
				proxy : {
					type : 'ajax',
					url : '../../Configs/PE000Config.dsc',
					actionMethods : {
						read : 'POST'
					},
					reader : {
						root : 'items'
					},
					extraParams : {
						DCITag : postvalue,
						uid : uid,
						action : 'bodyData'
					}
				},
				listeners : {
					update : function(store, record, operation, eOpts) {
						if (record.data.moditp == 'add') {
							for ( var i = 0; i < store.getCount(); i++) {
								if (store.getAt(i).id != record.id) {
									if (record.data.report_id == store.getAt(i).get("report_id")) {
										Ext.Msg.alert(langs.errmsg, langs.kanban_exist, function() {
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

			bodypanel.initBody(bodyStore, bodyColumns);

			var main = Ext.create('DCI.Customer.SubPanel', {
				id : 'PE000ConfigMain',
				renderTo : 'PE000ConfigPage',
				border : 0,
				bodyPadding : '0 5 5 5',
				layout : 'fit',
				widthChangeControls : [ bodypanel ],
				items : [ bodypanel ]
			});
			bodyStore.load();
			bodypanel.dataloaded(true);

			var initQueryStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : ['funcs'],//'conns',
				proxy : {
					type : 'ajax',
					url : '../../Configs/PE000Config.dsc',
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
					reportId_Combo.getStore().loadData(records[0].get("funcs"));
				}
			}); 

			main.resize(Ext.get("PE000ConfigPage").getWidth(), Ext.get("PE000ConfigPage").getHeight());
		}
	});
</script>
</head>
<body>
	<div id="PE000ConfigPage" class="page_div"></div>
</body>
</html>