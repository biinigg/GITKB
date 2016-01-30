var tlang = [];
var slang = [];

var lang = null;

tlang["dbtype"] = "資料庫類型";
slang["dbtype"] = "数据库类型";
tlang["langType"] = "預設語言";
slang["langType"] = "默认语言";
tlang["localIP"] = "主機IP";
slang["localIP"] = "主机IP";
tlang["dbaddr"] = "資料庫位址";
slang["dbaddr"] = "数据库地址";
tlang["dbport"] = "資料庫連接埠";
slang["dbport"] = "数据库端口";
tlang["dbname"] = "資料庫名稱";
slang["dbname"] = "数据库名称";
tlang["userName"] = "資料庫登入名稱";
slang["userName"] = "数据库登入名称";
tlang["password"] = "資料庫登入密碼";
slang["password"] = "数据库登入密码";
tlang["guardIP"] = "安控服務IP";
slang["guardIP"] = "安控服务IP";
tlang["guardPort"] = "安控服務連接埠";
slang["guardPort"] = "安控服务端口";
tlang["officialUrl"] = "正式區URL";
slang["officialUrl"] = "正式区URL";
tlang["goreg"] = "進行授權";
slang["goreg"] = "进行授权";
tlang["save"] = "儲存";
slang["save"] = "储存";
tlang["savesuccess"] = "儲存成功";
slang["savesuccess"] = "储存成功";
tlang["savefail"] = "儲存失敗";
slang["savefail"] = "储存失败";
tlang["saving"] = "資料儲存中...";
slang["saving"] = "数据储存中...";
tlang["loading"] = "資料載入中...";
slang["loading"] = "数据加载中...";

lang = tlang;

var combodata = Ext.create('Ext.data.Store', {
	fields : [ 'display', 'value' ],
	data : [ {
		"display" : "SQL Server",
		"value" : "SqlServer"
	}, {
		"display" : "Oracle",
		"value" : "Oracle"
	} ]
});

var langcombodata = Ext.create('Ext.data.Store', {
	fields : [ 'display', 'value' ],
	data : [ {
		"display" : "繁體中文",
		"value" : "CHT"
	}, {
		"display" : "简体中文",
		"value" : "CHS"
	} ]
});

function showconfig(postvalue) {
	var initStore = Ext.create('Ext.data.Store', {
		autoLoad : false,
		fields : [ 'ips' ],
		proxy : {
			type : 'ajax',
			url : '../../Configs/SysDBConfig.dsc',
			actionMethods : {
				read : 'POST'
			},
			reader : {
				type : 'json'
			},
			extraParams : {
				DCITag : postvalue,
				action : 'getAllIP'
			}
		}
	});

	var dbtypeCombo = Ext.create('Ext.form.field.ComboBox', {
		fieldLabel : '資料庫類型',
		store : combodata,
		name : 'DBType',
		id : 'dbtype',
		displayField : 'display',
		valueField : 'value',
		typeAhead : true,
		editable : false,
		queryMode : 'local',
		forceSelection : true,
		listeners : {
			change : function(comp, newValue, oldValue, eOpts) {
				var fieldset = this.up();
				if (fieldset != null) {
					var textbox = fieldset.items.get(1);
					if (newValue == 'Oracle') {
						textbox.setValue(1521);
					} else {
						textbox.setValue(1433);
					}
				}
			}
		}
	});
	var langTypeCombo = Ext.create('Ext.form.field.ComboBox', {
		fieldLabel : '預設語言',
		store : langcombodata,
		name : 'LangType',
		id : 'langType',
		displayField : 'display',
		valueField : 'value',
		typeAhead : true,
		editable : false,
		queryMode : 'local',
		forceSelection : true,
		listeners : {
			change : function(comp, newValue, oldValue, eOpts) {
				if (newValue == "CHS") {
					lang = slang;
				} else {
					lang = tlang;
				}

				var comp = null;
				for ( var i = 0; i < ConfigFormPanel.items.get(0).items.length; i++) {
					comp = ConfigFormPanel.items.get(0).items.get(i);
					comp.setFieldLabel(lang[comp.id]);
				}

				for ( var i = 0; i < ConfigFormPanel.items.get(1).items.length; i++) {
					comp = ConfigFormPanel.items.get(1).items.get(i);
					comp.setFieldLabel(lang[comp.id]);
				}
				ConfigFormPanel.down('#regbtn').setText(lang.goreg);
				ConfigFormPanel.down('#savebtn').setText(lang.save);
			}
		}
	});

	var ipCombo = Ext.create('Ext.form.ComboBox', {
		fieldLabel : 'LocalIP',
		name : 'LocalIP',
		id : 'localIP',
		allowBlank : false,
		displayField : 'display',
		valueField : 'value',
		typeAhead : true,
		editable : false,
		queryMode : 'local',
		matchFieldWidth : false,
		multiSelect : false,
		forceSelection : true,
		store : {
			fields : [ 'display', 'value' ],
			autoLoad : false,
			proxy : {
				type : 'memory',
				reader : {
					type : 'json'
				}
			}
		},
		width : 280
	});

	var ConfigFormPanel = new Ext.form.Panel({
		renderTo : 'ConfigForm',
		frame : true,
		title : 'KanBan Config Setting',
		width : 340,
		bodyPadding : 5,
		waitMsgTarget : true,
		method : 'POST',
		defaults : {
			xtype : 'textfield'
		},
		fieldDefaults : {
			labelAlign : 'right',
			labelWidth : 100,
			msgTarget : 'side'
		},
		items : [ {
			xtype : 'fieldset',
			title : 'System Information',
			defaultType : 'textfield',
			defaults : {
				width : 280
			},
			items : [ {
				fieldLabel : '資料庫位址',
				emptyText : '',
				id : 'dbaddr',
				name : 'DBAddr',
				allowBlank : false
			}, {
				xtype : 'numberfield',
				fieldLabel : '資料庫連接埠',
				emptyText : '',
				id : 'dbport',
				name : 'DBPort',
				allowBlank : false,
				allowDecimals : false,
				maxValue : 25536,
				minValue : 0,
				hideTrigger : true,
				keyNavEnabled : false,
				mouseWheelEnabled : false
			}, {
				fieldLabel : '資料庫名稱',
				id : 'dbname',
				name : 'DBName',
				allowBlank : false
			}, {
				fieldLabel : '資料庫登入名稱',
				id : 'userName',
				name : 'UserName',
				allowBlank : false
			}, {
				fieldLabel : '資料庫登入密碼',
				inputType : 'password',
				id : 'password',
				name : 'Password',
				allowBlank : false
			}, dbtypeCombo, langTypeCombo ]
		},

		{
			xtype : 'fieldset',
			title : 'Guard Service Information',
			hidden : true,
			items : [ ipCombo, {
				xtype : 'textfield',
				fieldLabel : 'Guard Server IP',
				name : 'GuardIP',
				id : 'guardIP',
				allowBlank : false,
				width : 280
			}, {
				fieldLabel : 'Port',
				name : 'GuardPort',
				id : 'guardPort',
				xtype : 'numberfield',
				width : 280,
				allowBlank : false,
				allowDecimals : false,
				maxValue : 25535,
				minValue : 0,
				hideTrigger : true,
				keyNavEnabled : false,
				mouseWheelEnabled : false
			} ]
		},

		{
			xtype : 'fieldset',
			title : 'Database Connection Pool Information',
			hidden : true,
			defaultType : 'numberfield',
			defaults : {
				width : 280,
				allowBlank : false,
				allowDecimals : false,
				maxValue : 999999,
				minValue : 0,
				hideTrigger : true,
				keyNavEnabled : false,
				mouseWheelEnabled : false,
				allowBlank : false
			},
			items : [ {
				fieldLabel : '最大連接數量',
				name : 'maxActive'
			}, {
				fieldLabel : '最大等待數量',
				name : 'maxWait'
			}, {
				fieldLabel : '最大閒置數量',
				name : 'maxIdle'
			}, {
				fieldLabel : '最小閒置數量',
				name : 'minIdle'
			} ]
		}, {
			xtype : 'hiddenfield',
			value : 'save',
			id : 'action',
			name : 'action'
		},

		{
			xtype : 'fieldset',
			title : 'Test Area Information',
			hidden : true,
			items : [ {
				xtype : 'textfield',
				fieldLabel : '正式區URL',
				name : 'OfficialUrl',
				id : 'officialUrl',
				allowBlank : false,
				width : 280
			} ]
		} ],
		buttons : [ {
			text : '進行授權',
			itemId : 'regbtn',
			handler : function() {
				window.location = "./../../FuncViews/Configs/RegSerial.jsp";
			}
		}, {
			text : '儲存',
			itemId : 'savebtn',
			checkSession : false,
			disabled : true,
			formBind : true,
			handler : function() {
				this.up('form').getForm().submit({
					url : '../../Configs/SysDBConfig.dsc',
					waitMsg : lang.saving,
					timeout : 180000,
					params : {
						confdata : Ext.encode(this.up('form').getForm().getValues()),
						DCITag : postvalue
					},
					failure : function(form, action) {
						Ext.Msg.alert(lang.savefail, action.result.errorMessage);
					},
					success : function(form, action) {
						if (action.result.success) {
							Ext.Msg.alert(lang.savesuccess, lang.savesuccess);
						} else {
							Ext.Msg.alert(lang.savefail, action.result.errorMessage);
						}

					}
				});
			}
		} ]
	});

	ConfigFormPanel.setLoading(true);
	initStore.load(function(records) {
		if (records.length > 0) {
			ipCombo.getStore().loadData(records[0].get("ips"));

			ConfigFormPanel.setLoading(false);
			ConfigFormPanel.getForm().load({
				url : '../../Configs/SysDBConfig.dsc',
				waitMsg : lang.loading,
				params : {
					DCITag : postvalue
				},
				failure : function(form, action) {
					Ext.Msg.alert("Load fail", action.result.errorMessage);
				},
				success : function(form, action) {
					if (dbtypeCombo.getValue() == null) {
						dbtypeCombo.setValue(combodata.getAt(0).get("value"));
					}
					if (langTypeCombo.getValue() == null) {
						langTypeCombo.setValue(langcombodata.getAt(0).get("value"));
					}

					var result = Ext.JSON.decode(action.response.responseText);

					ConfigFormPanel.items.get(1).setVisible(result.isvm);
					ConfigFormPanel.items.get(1).items.get(0).allowBlank = !result.isvm;
					ConfigFormPanel.items.get(1).items.get(1).allowBlank = !result.isvm;
					ConfigFormPanel.items.get(4).setVisible(result.isTestArea);
					ConfigFormPanel.items.get(4).items.get(0).allowBlank = !result.isTestArea;
				}
			});
		} else {
			ConfigFormPanel.setLoading(false);
		}
	});

}