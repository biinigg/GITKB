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
		var localKeys = [ "ParamsConfig", "config_id", "config_value", "config_desc", "load_def_format", "load_def_fail", "load_def_success", "load_def_result_title",
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
				url : '../../Configs/ParamsConfig.dsc',
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
							url : '../../Configs/ParamsConfig.dsc',
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
							url : '../../Configs/ParamsConfig.dsc',
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

			var conn_Combo = Ext.create('DCI.Customer.ComboBox', {
				labelWidth : 0
			});
			var bodypanel = Ext.create('DCI.Customer.BodyGridPanel', {
				title : langs.ParamsConfig,
				languageObj : langs,
				gridid : 'ParamsConfig_G01',
				actionurl : '../../Configs/ParamsConfig.dsc',
				postvalue : postvalue,
				uid : uid,
				//headform : headform,
				keyfields : [ 'config_id' ],
				canEdit : canEdit,
				stopUsingBtn : [ 0, 2 ]
			});

			var bodyColumns = [ {
				xtype : 'rownumberer',
				colid : 'colrownum',
				width : 40,
				sortable : false,
				locked : true
			}, {
				text : langs.config_id,
				dataIndex : 'config_id',
				colid : 'colconfig_id',
				width : 150
			}, {
				text : langs.config_value,
				dataIndex : 'config_value',
				colid : 'colconfig_value',
				width : 150,
				getEditor : function(record) {
					var editor = null;

					if (record.data.config_id == 'RemoveUserPwd') {
						editor = Ext.create('DCI.Customer.TextField', {
							fieldLabel : "",
							labelWidth : 0,
							inputType : 'password',
							defaultvalue : '',
							allowBlank : false
						});
					} else if (record.data.config_id == 'MarqueeType') {
						editor = Ext.create('DCI.Customer.ComboBox', {
							fieldLabel : "",
							labelWidth : 0,
							defaultvalue : '1',
							store : {
								fields : [ 'label', 'value' ],
								data : [ {
									"label" : langs.move,
									"value" : "1"
								}, {
									"label" : langs.still,
									"value" : "2"
								} ]
							}
						});
					} else {
						editor = Ext.create('DCI.Customer.TextField', {
							fieldLabel : "",
							labelWidth : 0,
							defaultvalue : '',
							allowBlank : false
						});
					}

					return Ext.create('Ext.grid.CellEditor', {
						field : editor,
						autoSize : {
							width : 'boundEl',
							height : 'boundEl'
						}
					});
				},
				renderer : function(value, metaData, record, row, col, store, gridView) {
					var display = "";
					if (record.data.config_id == 'RemoveUserPwd') {
						for ( var i = 0; i < value.length; i++) {
							display += "．";
						}
					} else if (record.data.config_id == 'MarqueeType') {
						if (value == '1') {
							display = langs.move;
						} else {
							display = langs.still;
						}
					} else {
						display = value;
					}

					return display;
				}
			}, {
				text : langs.config_desc,
				dataIndex : 'config_desc',
				colid : 'colconfig_desc',
				width : 400
			} ];

			var bodyStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'config_id', 'config_value', 'config_desc', 'moditag', 'moditp' ],
				proxy : {
					type : 'ajax',
					url : '../../Configs/ParamsConfig.dsc',
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
				}
			});

			bodypanel.initBody(bodyStore, bodyColumns);

			var main = Ext.create('DCI.Customer.SubPanel', {
				id : 'ParamsConfigMain',
				renderTo : 'ParamsConfigPage',
				border : 0,
				bodyPadding : '0 5 5 5',
				layout : 'fit',
				widthChangeControls : [ bodypanel ],
				items : [ bodypanel ]
			});
			bodyStore.load();
			//bodypanel.getGrid().getStore().load();
			bodypanel.dataloaded(true);

			/* var initQueryStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [],
				proxy : {
					type : 'ajax',
					url : '../../Configs/ParamsConfig.dsc',
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

			main.resize(Ext.get("ParamsConfigPage").getWidth(), Ext.get("ParamsConfigPage").getHeight());
		}
	});
</script>
</head>
<body>
	<div id="ParamsConfigPage" class="page_div"></div>
</body>
</html>