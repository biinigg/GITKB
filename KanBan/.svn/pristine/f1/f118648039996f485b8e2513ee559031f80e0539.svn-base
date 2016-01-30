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

	
	function showconfig(postvalue) {
		var dbtypeCombo = Ext.create('Ext.form.field.ComboBox', {
			fieldLabel : '資料庫類型',
			id : 'dbtype',
			name : 'DBType',
			store : combodata,
			displayField : 'display',
			valueField : 'value',
			typeAhead : true,
			editable : false,
			queryMode : 'local',
			forceSelection : true
		});

		var myFormPanel = new Ext.form.Panel({
			renderTo : 'TestForm',
			frame : true,
			title : 'LKB Config Setting',
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
				title : 'Database Information',
				defaultType : 'textfield',
				defaults : {
					width : 280
				},
				items : [ {
					fieldLabel : '資料庫位址',
					emptyText : '',
					id : 'dbaddr',
					name : 'DBAddr',
					allowBlank:false
				}, {
					xtype: 'numberfield',
					fieldLabel : '資料庫連接埠',
					emptyText : '',
					id : 'dbport',
					name : 'DBPort',
					allowBlank:false,
					allowDecimals:false,
					maxValue: 25536,
			        minValue: 0,
			        hideTrigger: true,
			        keyNavEnabled: false,
			        mouseWheelEnabled: false
				}, {
					fieldLabel : '資料庫名稱',
					id : 'dbname',
					name : 'DBName',
					allowBlank:false
				}, {
					fieldLabel : 'EKB資料庫名稱',
					id : 'ekbdbname',
					name : 'EKBDBName',
					allowBlank:false
				}, {
					fieldLabel : '資料庫登入名稱',
					id : 'userName',
					name : 'UserName',
					allowBlank:false
				}, {
					fieldLabel : '資料庫登入密碼',
					inputType : 'password',
					id : 'password',
					name : 'Password',
					allowBlank:false
				}, dbtypeCombo ]
			},

			{
				xtype : 'fieldset',
				title : 'Database Connection Pool Information',
				defaultType : 'numberfield',
				defaults : {
					width : 280,
					allowBlank:false,
					allowDecimals:false,
					maxValue: 999999,
			        minValue: 0,
			        hideTrigger: true,
			        keyNavEnabled: false,
			        mouseWheelEnabled: false,
					allowBlank:false
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
			} ],
			buttons : [ {
				text : '儲存',
				checkSession : false,
				disabled : true,
				formBind : true,
				handler : function() {
					this.up('form').getForm().submit(
							{
								url : '/LKB/conf01.do',
								waitMsg : '資料儲存中...',
								params : {
									confdata : Ext.encode(this.up('form')
											.getForm().getValues()),
											DCITag : postvalue
								},
								failure : function(form, action) {
									Ext.Msg.alert("儲存失敗", action.result.errorMessage);
								},
								success : function(form, action) {
									Ext.Msg.alert("儲存成功", "儲存成功");
								}
							});
				}
			} ]
		});
		myFormPanel.getForm().load({
			url : '/LKB/conf01.do',
			params : {
				DCITag : postvalue
			},
			failure : function(form, action) {
				Ext.Msg.alert("Load failed", action.result.errorMessage);
			},
			success : function(form, action) {
				if (dbtypeCombo.getValue() == null) {
					dbtypeCombo.setValue(combodata.getAt(0).get("value"));
					var form = myFormPanel.getForm();
					var field = form.findField('ekbdbname');
					field.setValue("EKB");
					
				}
			}
		});
	}