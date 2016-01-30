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
		var localKeys = [ "errmsg", "system_error", "save_msg", "delete_msg", "delete_fail", "delete_success", "delete_confirm_title", "delete_confirm_msg", "delete_result_title",
				"save_fail", "save_success", "save_result_title", "save_fail", "system_build", "conn_id", "conn_name", "conn_desc", "db_addr", "db_name", "db_user", "db_pwd",
				"db_type", "db_port", "visible", "enable", "disable", "toolbar_query_title", "system_def_can_not_edit", "info_msg_title", "conn_check_fail", "conn_check_success",
				"confirm_title", "data_lose_warning", "check", "cus_format" ];
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
			var db_type_combo = Ext.create('DCI.Customer.ComboBox', {
				fieldLabel : langs.db_type,
				name : 'db_type',
				x : 500,
				y : 90,
				listeners : {
					change : function(comp, newValue, oldValue, eOpts) {
						var form = this.up('form');
						if (form != null) {
							var textbox = form.items.get(7);
							if (newValue == 'Oracle') {
								textbox.setValue(1521);
							} else {
								textbox.setValue(1433);
							}
						}
					}
				}
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
				x : 500,
				y : 10
			});
			var headform = Ext.create('DCI.Customer.HeadFormPanel', {
				height : 100,
				url : '../../Configs/ConnConfig.dsc',
				languageObj : langs,
				items : [ Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.conn_id,
					emptyText : '',
					name : 'conn_id',
					allowBlank : false,
					ispk : true,
					canEdit : false,
					defaultvalue : langs.system_build,
					x : 0,
					y : 10
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.conn_name,
					emptyText : '',
					name : 'conn_name',
					allowBlank : false,
					defaultvalue : '',
					x : 250,
					y : 10
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.conn_desc,
					emptyText : '',
					name : 'conn_desc',
					allowBlank : true,
					defaultvalue : '',
					width : 465,
					x : 0,
					y : 50
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.db_addr,
					emptyText : '',
					name : 'db_addr',
					allowBlank : false,
					defaultvalue : '',
					x : 0,
					y : 90
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.db_name,
					emptyText : '',
					name : 'db_name',
					allowBlank : false,
					defaultvalue : '',
					x : 250,
					y : 90
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.db_user,
					emptyText : '',
					name : 'db_user',
					allowBlank : false,
					defaultvalue : '',
					x : 0,
					y : 130
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.db_pwd,
					emptyText : '',
					name : 'db_pwd',
					allowBlank : false,
					inputType : 'password',
					defaultvalue : '',
					x : 250,
					y : 130
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.db_port,
					emptyText : '',
					name : 'db_port',
					allowBlank : false,
					defaultvalue : '1433',
					x : 500,
					y : 130
				}), db_type_combo, visible_combo ],
				cusValid : function(fn) {
					var me = this;
					var field_name = me.items.get(1);
					var conn_id = me.items.get(0).getValue();
					var conn_name = field_name.getValue();

					Ext.Ajax.request({
						method : 'POST',
						url : '../../Configs/ConnConfig.dsc',
						params : {
							DCITag : postvalue,
							uid : uid,
							action : 'checkName',
							conn_name : conn_name,
							conn_id : conn_id
						},
						success : function(a) {
							me.setLoading(false);
							var result = Ext.JSON.decode(a.responseText);
							if (result.success) {
								fn();
							} else {
								field_name.markInvalid(result.msg);
							}
						},
						failure : function(f, action) {
							me.setLoading(false);
							field_name.markInvalid(langs.system_error);
						}
					});

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
							url : '../../Configs/ConnConfig.dsc',
							actionMethods : {
								read : 'POST'
							},
							reader : {
								type : 'json'
							},
							extraParams : {
								DCITag : postvalue,
								uid : uid,
							}
						}
					});

					var gstore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'conn_id', 'conn_name', 'conn_desc', 'db_addr', 'db_name', 'db_user', 'db_pwd', 'db_type', 'db_port', 'visible' ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/ConnConfig.dsc',
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
						text : langs.conn_id,
						dataIndex : 'conn_id',
						width : 200
					}, {
						text : langs.conn_name,
						dataIndex : 'conn_name',
						width : 200
					} ];

					var openwin = Ext.create('DCI.Customer.QueryWindow', {
						height : 335,
						width : 450,
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
				height : 200,
				width : '100%',
				headform : headform,
				postvalue : postvalue,
				uid : uid,
				canEdit : canEdit,
				keepAdd : false,
				exceptionEditAuth : function() {
					var result = [ "1", "" ];
					var form = this.bindform;
					if (headform != null) {
						if (form.items.get(0).getValue() == "CSYS") {
							result = [ "0", langs.system_def_can_not_edit ];
						}
					}
					return result;
				}
			});

			headpanel.setCusButtons([ {
				xtype : 'button',
				cls : 'sqlcheckbutton',
				tooltip : langs.check,
				x : 215,
				y : 3,
				width : 30,
				height : 30,
				handler : function() {
					var bform = this.up('panel').bindform;
					var pvalue = this.up('panel').postvalue;
					var langobj = bform.languageObj;
					if (bform.getCurrMode() == PageMode.Add || bform.dataloaded) {
						bform.setLoading(true);
						Ext.Ajax.request({
							method : 'POST',
							url : '../../Configs/ConnConfig.dsc',
							params : {
								DCITag : pvalue,
								uid : uid,
								action : 'connCheck',
								datas : Ext.encode(bform.getForm().getValues())
							},
							success : function(a) {
								bform.setLoading(false);
								var result = Ext.JSON.decode(a.responseText);
								if (result.success) {
									Ext.Msg.alert(langobj.info_msg_title, langobj.conn_check_success);
								} else {
									Ext.Msg.alert(langobj.errmsg, langobj.conn_check_fail + "</br>" + result.msg);
								}
							},
							failure : function(f, action) {
								bform.setLoading(false);
								Ext.Msg.alert(langobj.errmsg, langobj.system_error);
							}
						});
					}
				}
			} ]);

			var main = Ext.create('DCI.Customer.SubPanel', {
				id : 'ConnConfigMain',
				renderTo : 'ConnConfigPage',
				border : 0,
				bodyPadding : '0 5 5 5',
				//layout : 'border',
				widthChangeControls : [ headpanel ],
				items : [ headpanel ]
			});

			var initQueryStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'dbtypes' ],
				proxy : {
					type : 'ajax',
					url : '../../Configs/ConnConfig.dsc',
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
					db_type_combo.getStore().loadData(record[0].get("dbtypes"));
					db_type_combo.loadDefault();
					visible_combo.loadDefault();

					db_type_combo.listConfig = {
						minWidth : db_type_combo.getWidth() - db_type_combo.getLabelWidth()
					};
					visible_combo.listConfig = {
						minWidth : visible_combo.getWidth() - visible_combo.getLabelWidth()
					};
				}
			});

			//main.resize(Ext.get("ConnConfigPage").getWidth(), Ext.get("ConnConfigPage").getHeight());
		}
	});
</script>
</head>
<body>
	<div id="ConnConfigPage" class="page_div"></div>
</body>
</html>