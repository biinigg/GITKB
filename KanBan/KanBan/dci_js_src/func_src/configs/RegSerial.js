function showPage(postvalue) {
	var serial_combo = Ext.create('DCI.Customer.ComboBox', {
		fieldLabel : "選擇序號",
		name : 'serial_number',
		x : 0,
		y : 10
	});

	var mainPanel = Ext.create('Ext.panel.Panel', {
		renderTo : 'RegSerialForm',
		frame : true,
		width : '80%',
		height : 500,
		layout : {
			type : 'vbox',
			align : 'center'
		},
		bodyPadding : 3,
		items : [ Ext.create('Ext.form.Panel', {
			title : 'step 1',
			flex : 2,
			frame : true,
			layout : 'absolute',
			width : '100%',
			// height : 80,
			bodyPadding : 5,
			waitMsgTarget : true,
			method : 'POST',
			defaults : {
				xtype : 'textfield'
			},
			fieldDefaults : {
				labelAlign : 'right',
				labelWidth : 60,
				msgTarget : 'side'
			},
			items : [ {
				fieldLabel : '序號',
				emptyText : '',
				name : 'serial_number',
				width : 350,
				allowBlank : false,
				x : 0,
				y : 10
			}, {
				xtype : 'button',
				cls : 'savebutton',
				width : 30,
				height : 30,
				x : 350,
				y : 10,
				handler : function() {
					var wform = this.up('panel');
					var panel = wform.up('panel');
					if (wform.isValid()) {
						wform.getForm().submit({
							url : '../../Configs/RegSerial.dsc',
							waitMsg : '資料儲存中...',
							params : {
								DCITag : postvalue,
								action : 'save_serial'
							},
							failure : function(form, action) {
								Ext.Msg.alert("儲存失敗", action.result.errorMessage);
							},
							success : function(form, action) {
								panel.datareload();
								if (action.result.success) {
									var resultmsg = "儲存成功";
									if (!action.result.regstatus) {
										resultmsg += ";序號已過期";
									}
									Ext.Msg.alert("儲存成功", resultmsg, function() {
										wform.items.get(0).setValue("");
										wform.items.get(0).clearInvalid();
									});
								} else {
									Ext.Msg.alert("儲存失敗", action.result.errorMessage, function() {
										wform.items.get(0).markInvalid(action.result.errorMessage);
									});
								}
							}
						});
					}
				}
			} ]
		}), Ext.create('Ext.form.Panel', {
			title : 'step 2',
			flex : 3,
			frame : true,
			layout : 'absolute',
			width : '100%',
			// height : 180,
			bodyPadding : 5,
			waitMsgTarget : true,
			method : 'POST',
			defaults : {
				xtype : 'textfield'
			},
			fieldDefaults : {
				labelAlign : 'right',
				labelWidth : 60,
				msgTarget : 'side'
			},
			items : [ Ext.create('DCI.Customer.ComboBox', {
				fieldLabel : "選擇序號",
				name : 'serial_number',
				width : 350,
				allowBlank : false,
				x : 0,
				y : 10
			}), {
				fieldLabel : '機碼',
				emptyText : '',
				width : 350,
				name : 'mkey',
				readOnly : true,
				allowBlank : false,
				x : 0,
				y : 40
			}, {
				fieldLabel : '執行碼',
				emptyText : '',
				name : 'execute_code',
				width : 350,
				allowBlank : false,
				x : 0,
				y : 70
			}, {
				xtype : 'button',
				cls : 'savebutton',
				width : 30,
				height : 30,
				x : 350,
				y : 10,
				handler : function() {
					var wform = this.up('panel');
					var panel = wform.up('panel');
					if (wform.isValid()) {
						wform.getForm().submit({
							url : '../../Configs/RegSerial.dsc',
							waitMsg : '資料儲存中...',
							params : {
								DCITag : postvalue,
								action : 'save_execute'
							},
							failure : function(form, action) {
								Ext.Msg.alert("授權結果", "授權失敗<br/>" + action.result.errorMessage);
							},
							success : function(form, action) {
								panel.datareload();
								if (action.result.success) {
									Ext.Msg.alert("授權結果", action.result.errorMessage, function() {
										if (action.result.regstatus) {
											wform.items.get(0).setValue("");
											wform.items.get(2).setValue("");
											wform.items.get(0).clearInvalid();
											wform.items.get(2).clearInvalid();
										} else {
											wform.items.get(0).markInvalid(action.result.errorMessage);
											wform.items.get(2).markInvalid(action.result.errorMessage);
										}
									});
								} else {
									Ext.Msg.alert("授權結果", "授權失敗<br/>" + action.result.errorMessage);
								}
							}
						});
					}
				}
			} ]
		}), {
			xtype : 'grid',
			title : '已安裝序號',
			flex : 5,
			border : 0,
			// hight : 150,
			width : '100%',
			stripeRows : true,
			autoScroll : true,
			loadMask : true,
			enableTextSelection : true,
			viewConfig : {
				forceFit : false,
				autoLoad : false
			},
			columns : [ {
				text : "已安裝序號",
				dataIndex : 'display_serial',
				width : 250
			}, {
				text : "模組",
				dataIndex : 'module_name',
				width : 80,
				renderer : function(value) {
					/*
					 * var d = ""; if (value == '100') { d = "EKB"; } else if
					 * (value == '010') { d = "WPP"; } else if (value == '001') {
					 * d = "LKB"; } else if (value == '111') { d =
					 * "EKB;WPP;LKB"; } else if (value == '101') { d =
					 * "EKB;LKB"; } else if (value == '110') { d = "EKB;WPP"; }
					 * else if (value == '011') { d = "WPP;LKB"; } else { d =
					 * ""; } return d;
					 */
					return value;
				}
			}, {
				text : "人數",
				dataIndex : 'user_qty',
				width : 50
			}, {
				text : "使用期限",
				dataIndex : 'display_expired_date',
				width : 90,
				renderer : function(value) {
					var d = "";
					if (value == '99999999') {
						d = "永久";
					} else {
						d = value;
					}
					return d;
				}
			}, {
				text : "序號用途</br>(正式/借貨)",
				dataIndex : 'area',
				width : 80,
				renderer : function(value) {
					var d = "";
					if (value == '0123456789') {
						d = "借貨";
					} else {
						d = "正式";
					}
					return d;
				}
			}, {
				text : "是否註冊執行碼",
				dataIndex : 'isReg',
				width : 100,
				renderer : function(value) {
					var d = "";
					if (value == '1') {
						d = "N";
					} else {
						d = "Y";
					}
					return d;
				}
			}, {
				text : "授權狀態",
				dataIndex : 'status',
				width : 80,
				renderer : function(value) {
					var d = "";
					if (value == 'A') {
						d = "授權成功";
					} else if (value == 'B') {
						d = "過期";
					} else if (value == 'C') {
						d = "尚未授權";
					} else if (value == 'D') {
						d = "授權失敗";
					} else {
						d = "";
					}
					return d;
				}
			} ],
			store : {
				fields : [ 'display_serial', 'module_id', 'user_qty', 'area', 'display_expired_date', 'isReg', 'status', 'module_name' ],
				autoLoad : false,
				proxy : {
					type : 'memory',
					reader : {
						type : 'json'
					}
				}
			}
		} ],
		setInitData : function(record) {
			var me = this;
			var combo = me.items.get(1).items.get(0);
			var textbox = me.items.get(1).items.get(1);
			var grid = me.items.get(2);

			if (combo != null) {
				combo.getStore().loadData(record.get("serials"));
			}

			if (textbox != null) {
				textbox.setValue(record.get("mkey"));
			}

			if (grid != null) {
				grid.getStore().loadData(record.get("regs"));
			}
		},
		datareload : function() {
			var me = this;
			var combo = me.items.get(1).items.get(0);
			var grid = me.items.get(2);
			initQueryStore.load(function(records) {
				if (records.length > 0) {
					if (combo != null) {
						combo.getStore().loadData(records[0].get("serials"));
					}
					if (grid != null) {
						grid.getStore().loadData(records[0].get("regs"));
					}
				}
			});
		}
	});

	var initQueryStore = Ext.create('Ext.data.Store', {
		autoLoad : false,
		fields : [ 'regs', 'serials', 'mkey' ],
		proxy : {
			type : 'ajax',
			url : '../../Configs/RegSerial.dsc',
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

	initQueryStore.load(function(records) {
		if (records.length > 0) {
			// serial_combo.getStore().loadData(record[0].get("serials"));
			mainPanel.setInitData(records[0]);
		}
	});
}