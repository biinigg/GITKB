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
		var localKeys = [ "save_msg", "delete_msg", "delete_fail", "delete_success", "delete_result_title", "delete_fail", "save_fail", "save_success", "save_result_title",
				"save_fail", "icon_id", "icon_name", "icon_map_key", "icon_path", "icon_type", "system_build", "upload_icon", "delete_confirm_title", "delete_confirm_msg",
				"toolbar_query_title", "confirm_title", "data_lose_warning", "system_def_can_not_delete", "cht", "chs", "cus_format" ];
		var keys = localKeys.concat(globeKeys);
		var uid = '<dcitag:reqParam paramName="uid"></dcitag:reqParam>';
		this.dcistore.setMultiLangKeys(keys);
		this.dcistore.setUid(uid);
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				var funclangs = buildMultiLangObjct(keys, records[0].get('langValues'));
				showPage(records[0].get('dcitagValue'), funclangs, '<dcitag:reqParam paramName="canEdit"></dcitag:reqParam>', uid, records[0].get('langType'));
			}
		});

		function showPage(postvalue, langs, recvAuth, uid, langtp) {
			var canEdit = recvAuth == "1";
			var iconpath = "./../../ImageLoader.dsc?iconid=";

			var privateImage = Ext.create('DCI.Customer.Img', {
				x : 500,
				y : 50
			});

			var nameTextBox = Ext.create('DCI.Customer.TextField', {
				fieldLabel : langs.icon_name,
				emptyText : '',
				name : 'icon_show_name',
				allowBlank : false,
				maxLength : 70,
				defaultvalue : '',
				canEdit : false,
				x : 250,
				y : 10
			});

			var langOpenWinBtn = Ext.create('DCI.Customer.EmptyButton', {
				x : 485,
				y : 5,
				cls : 'search-toolbar',
				width : 30,
				height : 30,
				targetComp : nameTextBox,
				valueComp : null,
				names : [],
				handler : function() {
					var me = this;
					var gStore = Ext.create('Ext.data.Store', {
						autoLoad : false,
						fields : [ 'chs_name', 'cht_name' ]
					});

					var win = Ext.create('Ext.window.Window', {
						title : langs.toolbar_query_title,
						width : 515,
						height : 200,
						minWidth : 515,
						minHeight : 200,
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
								text : langs.cht,
								dataIndex : 'cht_name',
								width : 250,
								editor : {
									allowBlank : true
								}
							}, {
								text : langs.chs,
								dataIndex : 'chs_name',
								width : 250,
								editor : {
									allowBlank : true
								}
							} ],
							store : gStore,
							plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
								clicksToEdit : 1
							}) ]
						} ],
						buttons : [ {
							text : langs.ok,
							handler : function() {
								if (this.up('.window').items.get(0).plugins[0] != null) {
									this.up('.window').items.get(0).plugins[0].completeEdit();
								}

								if (gStore.getCount() >= 1) {
									me.names = [ gStore.getAt(0).get("chs_name"), gStore.getAt(0).get("cht_name") ];
								} else {
									me.names = [ '', '' ];
								}

								if (langtp == 'CHT') {
									me.targetComp.setValue(me.names[1]);
								} else {
									me.targetComp.setValue(me.names[0]);
								}
								if (me.valueComp != null) {
									me.valueComp.setSelectedValue(gStore.getAt(0).get("chs_name") + "$" + gStore.getAt(0).get("cht_name"));
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

					if (me.names.length == 2) {
						gStore.add({
							chs_name : me.names[0],
							cht_name : me.names[1]
						});
					} else {
						gStore.add({
							chs_name : '',
							cht_name : ''
						});
					}

					win.show();

				},
				setReadOnly : function(readonly) {
					this.setDisabled(readonly);
				},
				loadDefault : function() {

				},
				setInit : function(comp) {
					this.valueComp = comp;
				}
			});

			var iconNames = Ext.create('DCI.Customer.Hidden', {
				name : 'icon_names',
				value : '',
				defaultvalue : '',
				triggerModi : false,
				setSelectedValue : function(value) {
					this.triggerModi = true;
					this.setValue(value);
					this.triggerModi = false;
				},
				listeners : {
					change : function(comp, newValue, oldValue, eOpts) {
						if (!this.triggerModi) {
							if (langOpenWinBtn != null) {
								if (newValue != null && newValue.indexOf('$') != -1) {
									langOpenWinBtn.names = newValue.split('$');
								} else {
									langOpenWinBtn.names = [ '', '' ];
								}
							} else {
								langOpenWinBtn.names = [ '', '' ];
							}
						}
					}
				}
			});
			langOpenWinBtn.setInit(iconNames);
			var headform = Ext.create('DCI.Customer.HeadFormPanel', {
				height : 100,
				url : '../../Configs/IconConfig.dsc',
				languageObj : langs,
				items : [ Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.icon_id,
					emptyText : '',
					name : 'icon_id',
					allowBlank : false,
					ispk : true,
					canEdit : false,
					defaultvalue : langs.system_build,
					x : 0,
					y : 10
				}), nameTextBox, Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.icon_map_key,
					emptyText : '',
					name : 'icon_map_key',
					maxLength : 5,
					allowBlank : false,
					canEdit : false,
					defaultvalue : langs.system_build,
					x : 530,
					y : 10
				}), Ext.create('DCI.Customer.FileField', {
					fieldLabel : langs.upload_icon,
					name : 'file',
					width : 490,
					canEdit : true,
					ispk : true,
					readOnly : true,
					allowBlank : false,
					x : 0,
					y : 70
				}), privateImage, {
					xtype : 'hiddenfield',
					name : 'icon_path',
				}, Ext.create('DCI.Customer.Hidden', {
					name : 'icon_type',
					value : '',
					defaultvalue : 'CUS'
				}), Ext.create('DCI.Customer.Hidden', {
					name : 'icon_name',
					value : '',
					defaultvalue : ''
				}), langOpenWinBtn, iconNames ],
				cusValid : function(fn) {
					var me = this;
					var field_name = me.items.get(2);
					var icon_id = me.items.get(0).getValue();
					var value = field_name.getValue();

					Ext.Ajax.request({
						method : 'POST',
						url : '../../Configs/IconConfig.dsc',
						params : {
							DCITag : postvalue,
							action : 'mapkeyCheck',
							icon_id : icon_id,
							value : value
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
							url : '../../Configs/IconConfig.dsc',
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
						fields : [ "icon_id", "icon_name", "icon_show_name", "icon_map_key", "icon_path", "icon_type", "icon_id_show", "icon_names" ],
						proxy : {
							type : 'ajax',
							url : '../../Configs/IconConfig.dsc',
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
						text : langs.icon_id,
						dataIndex : 'icon_id',
						width : 100
					}, {
						text : langs.icon_name,
						dataIndex : 'icon_show_name',
						width : 200
					}, {
						text : langs.icon_map_key,
						dataIndex : 'icon_map_key',
						width : 100
					}, {
						//text : langs.icon_name,
						xtype : 'dcilightcolumn',
						align : 'center',
						dataIndex : 'icon_id_show',
						width : 100
					} ];

					var openwin = Ext.create('DCI.Customer.QueryWindow', {
						height : 365,
						width : 550,
						headform : me,
						title : langs.toolbar_query_title,
						loadHeadData : function(record) {
							this.headform.loadRecord(record);
							this.headform.setDataLoaded(true);

							if (privateImage != null) {
								privateImage.setSrc(iconpath + record.get("icon_id_show"));
							}

							this.close();
						},
						clickFunc : function() {
							var me = this;
							var gstore = me.items.get(1).getStore();
							var fcol = me.items.get(0).items.get(0).getValue();
							var fvalue = me.items.get(0).items.get(1).getValue();
							//console.log(fcol);
							if (gstore.getProxy().extraParams.hasOwnProperty('filter')) {
								if (fcol == "icon_name") {
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
						if (form.items.get(6).getValue() == "SYS") {
							result = [ "0", langs.system_def_can_not_delete ];
						}
					}
					return result;
				},
				afterSaveFunc : function(result) {
					if (privateImage != null) {
						privateImage.setSrc(iconpath + result.icon_id_show);
					}
				}
			});

			var main = Ext.create('DCI.Customer.SubPanel', {
				id : 'IconConfigMain',
				renderTo : 'IconConfigPage',
				border : 0,
				bodyPadding : '0 5 5 5',
				//layout : 'border',
				widthChangeControls : [ headpanel ],
				items : [ headpanel ]
			});

			/* var initQueryStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'currlang' ],
				proxy : {
					type : 'ajax',
					url : '../../Configs/IconConfig.dsc',
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

			//main.resize(Ext.get("IconConfigPage").getWidth(), Ext.get("IconConfigPage").getHeight());
		}
	});
</script>
</head>
<body>
	<div id="IconConfigPage" class="page_div"></div>
</body>
</html>