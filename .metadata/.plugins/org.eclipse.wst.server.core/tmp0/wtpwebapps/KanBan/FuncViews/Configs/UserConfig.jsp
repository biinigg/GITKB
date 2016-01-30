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
		var localKeys = [ "user_group", "user_id", "user_name", "user_pwd", "visible", "enable", "disable", "save_msg", "delete_msg", "delete_fail", "delete_success",
				"delete_result_title", "delete_confirm_title", "delete_confirm_msg", "delete_fail", "save_fail", "save_success", "save_result_title", "save_fail", "func_id",
				"conn_id", "can_edit", "user_role_edit", "toolbar_query_title", "system_def_can_not_delete", "confirm_title", "data_lose_warning", "load_def_format",
				"load_def_fail", "load_def_success", "load_def_result_title", "load_def_confirm_title", "load_def_confirm_msg", "cus_format" ];
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

	});

	function showPage(postvalue, langs, recvAuth, uid) {
		var canEdit = recvAuth == "1";
		var group_combo = Ext.create('DCI.Customer.ComboBox', {
			fieldLabel : langs.user_group,
			name : 'group_id',
			x : 500,
			y : 10
		});

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
			x : 250,
			y : 50
		});

		var headform = Ext.create('DCI.Customer.HeadFormPanel', {
			height : 100,
			url : '../../Configs/UserConfig.dsc',
			languageObj : langs,
			items : [ Ext.create('DCI.Customer.TextField', {
				fieldLabel : langs.user_id,
				emptyText : '',
				name : 'user_id',
				allowBlank : false,
				ispk : true,
				x : 0,
				y : 10
			}), Ext.create('DCI.Customer.TextField', {
				fieldLabel : langs.user_name,
				emptyText : '',
				name : 'user_name',
				allowBlank : true,
				x : 250,
				y : 10
			}), Ext.create('DCI.Customer.TextField', {
				fieldLabel : langs.user_pwd,
				emptyText : '',
				name : 'user_pwd',
				allowBlank : false,
				inputType : 'password',
				x : 0,
				y : 50
			}), group_combo, visible_combo ],
			showQueryWindow : function() {
				var me = this;
				me.initQueryWindow();
				var pageSize = 10;
				var initStore = Ext.create('Ext.data.Store', {
					autoLoad : false,
					fields : [ 'cols' ],
					proxy : {
						type : 'ajax',
						url : '../../Configs/UserConfig.dsc',
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
					fields : [ 'user_id', 'user_name', 'user_pwd', 'group_id', 'visible' ],
					proxy : {
						type : 'ajax',
						url : '../../Configs/UserConfig.dsc',
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
					text : langs.user_id,
					dataIndex : 'user_id',
					width : 200
				}, {
					text : langs.user_name,
					dataIndex : 'user_name',
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
								bodyStore.getProxy().extraParams['keys'] = record.get("user_id");
								bodyStore.load();
							}
						}
						if (bodypanel != null) {
							var obj = new Object();
							obj["user_id"] = record.get("user_id");
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
			height : 200,
			width : '100%',
			headform : headform,
			postvalue : postvalue,
			uid : uid,
			canEdit : canEdit,
			keepAdd : false,
			exceptionDeleteAuth : function() {
				var result = [ "1", "" ];
				var form = this.bindform;
				if (headform != null) {
					if (form.items.get(0).getValue() == "DS") {
						result = [ "0", langs.system_def_can_not_delete ];
					}
				}
				return result;
			}
		});

		var bodypanel = Ext.create('DCI.Customer.BodyGridPanel', {
			title : langs.user_role_edit,
			languageObj : langs,
			gridid : 'UserConfig_G01',
			actionurl : '../../Configs/UserConfig.dsc',
			postvalue : postvalue,
			headform : headform,
			uid : uid,
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
						user_id : this.headKeys.user_id,
						func_id : '',
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
						obj["user_id"] = head.items.get(0).getValue();
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
		} /* , Ext.create('DCI.Customer.CheckColumn', {
									text : langs.can_edit,
									dataIndex : 'can_edit',
									colid : 'colcan_edit'
								}) */];

		var bodyStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ 'user_id', 'func_id', 'conn_id', 'func_package', 'can_edit', 'moditag', 'moditp' ],
			proxy : {
				type : 'ajax',
				url : '../../Configs/UserConfig.dsc',
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
		});

		//bodypanel.initBody(bodyStore, bodyColumns);

		var main = Ext.create('DCI.Customer.SubPanel', {
			id : 'UserConfigMain',
			renderTo : 'UserConfigPage',
			border : 0,
			bodyPadding : '0 5 5 5',
			layout : 'border',
			widthChangeControls : [ headpanel, bodypanel ],
			items : [ headpanel, bodypanel ]
		});

		var initQueryStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ 'groups', 'conns', 'funcs' ],
			proxy : {
				type : 'ajax',
				url : '../../Configs/UserConfig.dsc',
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
				group_combo.getStore().loadData(record[0].get("groups"));
				group_combo.loadDefault();
				visible_combo.loadDefault();

				group_combo.listConfig = {
					minWidth : group_combo.getWidth() - group_combo.getLabelWidth()
				};
				visible_combo.listConfig = {
					minWidth : visible_combo.getWidth() - visible_combo.getLabelWidth()
				};

				conn_Combo.getStore().loadData(record[0].get("conns"));
				func_Combo.getStore().loadData(record[0].get("funcs"));
				bodypanel.transSource = record[0].get("conns");
				bodypanel.transSource2 = record[0].get("funcs");
				bodypanel.initBody(bodyStore, bodyColumns);
			}
		});

		main.resize(Ext.get("UserConfigPage").getWidth(), Ext.get("UserConfigPage").getHeight());
	}
	//class="page_div" #eaf1fb
</script>
</head>
<body>
	<div id="UserConfigPage" class="page_div"></div>
</body>
</html>