function trimStr(str) {
	var start = -1, end = str.length;
	while (str.charCodeAt(--end) < 33)
		;
	while (str.charCodeAt(++start) < 33)
		;
	return str.slice(start, end + 1);
};

function BuildComboboxStore(url, parames) {
	Ext.define('comboboxModel', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'label',
			type : 'string'
		}, {
			name : 'value',
			type : 'string'
		} ]
	});

	var ComboboxStore = new Ext.data.Store({
		id : 'ComboboxStore',
		model : 'comboboxModel',
		autoLoad : false,
		proxy : {
			type : 'ajax',
			url : url,
			actionMethods : {
				read : 'POST'
			},
			reader : {
				type : 'json',
				root : 'values'
			},
			extraParams : parames
		}
	});

	return ComboboxStore;
}

Ext.define('DCI.LKB.Customer.OpenWinTrigger', {
	extend : 'Ext.form.field.Trigger',
	alias : 'DCI.OpenWinTrigger',
	multiSelect : false,
	displayColumns : [],
	returnColumn : '',
	openWinDataUrl : '',
	openWinParams : {},
	depComps : [],
	ouid : '',
	langobj : null,
	onTriggerClick : function() {
		if (this.id == '') {
			alert("元件設定錯誤");
		} else {
			var comp = null;
			var k = null;
			// alert(this.openWinParams.hasOwnProperty("action"));
			// alert(this.openWinParams.hasOwnProperty("F123"));

			for ( var i = 0; i < this.depComps.length; i++) {
				if (this.depComps[i].indexOf('_') == -1) {
					k = this.depComps[i];
				} else {
					k = this.depComps[i].substring(0, this.depComps[i].indexOf('_'));
				}
				// alert(k);
				if (this.openWinParams.hasOwnProperty(k)) {
					comp = Ext.getCmp(this.depComps[i]);
					if (comp != null) {
						// alert(comp.getValue());
						this.openWinParams[k] = comp.getValue();
					}
				}
			}

			/*
			 * for(var i = 0 ; i < this.openWinParams.items.length ; i++){
			 * alert(this.openWinParams.items[i].name); }
			 */

			/*
			 * for ( var i = 0; i < this.depComps.length; i++) { comp =
			 * Ext.getCmp(this.depComps[i]); if (comp != null) { } }
			 */
			this.openWinParams['uid'] = this.ouid;
			var win = createOpenWin(this, this.multiSelect, this.displayColumns, this.returnColumn, this.openWinDataUrl, this.openWinParams, this.langobj);
			win.show();
		}
	}
});

function createOpenWin(parent, multiSelect, dcol, rcol, url, params, langs) {
	var win = null;

	var fields = [];
	var columns = [];

	fields.push({
		name : "selected",
		type : 'bool'
	});
	columns.push({
		xtype : 'checkcolumn',
		text : '',
		dataIndex : 'selected'
	});

	for ( var i = 0; i < dcol.length; i++) {
		fields.push({
			name : dcol[i][0],
			type : 'string'
		});
		columns.push({
			text : dcol[i][1],
			dataIndex : dcol[i][0]
		});
	}

	var winModel = Ext.define('WinModel', {
		extend : 'Ext.data.Model',
		fields : fields
	});

	var winstore = new Ext.data.Store({
		id : 'WinStore',
		model : winModel,
		autoLoad : false,
		proxy : {
			type : 'ajax',
			url : url,
			actionMethods : {
				read : 'POST'
			},
			reader : {
				type : 'json',
				root : 'items'
			},
			extraParams : params
		}
	});

	var grid = Ext.create('Ext.grid.Panel', {
		renderer : "component",
		stripeRows : true,
		store : winstore,
		autoScroll : true,
		columns : columns
	});

	winstore.load();

	win = Ext.create('Ext.window.Window', {
		title : langs.open_win,
		width : 500,
		height : 300,
		minWidth : 300,
		minHeight : 200,
		layout : 'fit',
		plain : true,
		modal : true,
		items : grid,
		buttons : [ {
			text : langs.ok,
			handler : function() {
				var values = '';
				if (winstore.getCount() != 0) {
					var records = null;
					var rcols = null;

					rcols = rcol.split(';');
					for ( var i = 0; i < winstore.getCount(); i++) {
						records = winstore.getAt(i);
						if (records.get('selected')) {
							if (values == '') {
								for ( var j = 0; j < rcols.length; j++) {
									if (j == 0) {
										values = trimStr(records.get(rcols[j]));
									} else if (j == 1) {
										values += "(" + trimStr(records.get(rcols[j]));
									} else {
										values += "," + trimStr(records.get(rcols[j]));
									}
								}
							} else {
								values += ";";
								for ( var j = 0; j < rcols.length; j++) {
									if (j == 0) {
										values += trimStr(records.get(rcols[j]));
									} else if (j == 1) {
										values += "(" + trimStr(records.get(rcols[j]));
									} else {
										values += "," + trimStr(records.get(rcols[j]));
									}
								}
							}

							if (rcols.length > 1) {
								values += ")";
							}

							/*
							 * if (values == '') { values =
							 * trimStr(records.get(rcol)); } else { values +=
							 * ";" + trimStr(records.get(rcol)); }
							 */
						}
					}
				}
				parent.setValue(values);
				// alert(parent.getId() + "---" + values);
				this.up('.window').close();
			}
		}, {
			text : langs.cancel,
			handler : function() {
				this.up('.window').close();
			}
		} ]
	});

	return win;
}

var PageMode = buildMultiLangObjct([ "View", "Edit", "Add" ], [ "View", "Edit", "Add" ]);

Ext.define('DCI.Customer.Img', {
	extend : 'Ext.Img',
	alias : 'DCI.Customer.Img',
	width : 50,
	height : 50,
	loadDefault : function() {
		this.setSrc("");
	},
	setReadOnly : function(readonly) {

	}

});

Ext.define('DCI.Customer.FileField', {
	extend : 'Ext.form.field.File',
	alias : 'DCI.Customer.FileField',
	buttonText : '',
	buttonConfig : {
		iconCls : 'upload-icon'
	},
	emptyText : '',
	defaultvalue : '',
	ispk : false,
	canEdit : true,
	loadDefault : function() {
	},
	setReadOnly : function(readonly) {
		this.readOnly = readonly;
		this.setDisabled(readonly);
	}

});

Ext.define('DCI.Customer.TextField', {
	extend : 'Ext.form.field.Text',
	alias : 'DCI.Customer.TextField',
	msgTarget : 'side',
	oriAllowBlank : false,
	defaultvalue : '',
	ispk : false,
	canEdit : true,
	cusStyle : null,
	loadDefault : function() {
		this.setValue(this.defaultvalue);
	},
	setReadOnly : function(readonly) {
		if (readonly) {
			this.allowBlank = true;
			this.setFieldStyle({
				backgroundColor : '#ddd',
				backgroundImage : 'none'
			});
		} else {
			this.allowBlank = this.oriAllowBlank;
			if (this.cusStyle != null && this.cusStyle != '') {
				this.setFieldStyle(this.cusStyle);
			} else {
				this.setFieldStyle({
					backgroundColor : '#FFFFFF'
				});
			}
		}
		this.callParent(arguments);
	},
	initComponent : function() {
		this.oriAllowBlank = this.allowBlank;
		if (this.style != null) {
			this.cusStyle = this.style;
		}
		this.callParent(arguments);
	}
});

Ext.define('DCI.Customer.ComboBox', {
	extend : 'Ext.form.field.ComboBox',
	alias : 'DCI.Customer.ComboBox',
	displayField : 'label',
	valueField : 'value',
	typeAhead : true,
	editable : false,
	queryMode : 'local',
	matchFieldWidth : false,
	multiSelect : false,
	forceSelection : true,
	store : {
		fields : [ 'label', 'value' ],
		autoLoad : false,
		proxy : {
			type : 'memory',
			reader : {
				type : 'json'
			}
		}
	},
	defaultvalue : '',
	ispk : false,
	canEdit : true,
	cusStyle : null,
	loadDefault : function() {
		if (this.store != null && this.store.getCount() > 0) {
			if (this.defaultvalue == null || this.defaultvalue == '') {
				this.setValue(this.store.getAt(0).get("value"));
			} else {
				this.setValue(this.defaultvalue);
			}
		}
	},
	setReadOnly : function(readonly) {
		if (readonly) {
			this.setFieldStyle({
				backgroundColor : '#ddd',
				backgroundImage : 'none'
			});
		} else {
			if (this.cusStyle != null && this.cusStyle != '') {
				this.setFieldStyle(this.cusStyle);
			} else {
				this.setFieldStyle({
					backgroundColor : '#FFFFFF'
				});
			}
		}
		this.callParent(arguments);
	},
	initComponent : function() {
		if (this.style != null) {
			this.cusStyle = this.style;
		}
		this.callParent(arguments);
	}
});

Ext.define('DCI.Customer.HeadFormPanel', {
	extend : 'Ext.form.Panel',
	alias : 'DCI.Customer.HeadFormPanel',
	region : 'center',
	layout : 'absolute',
	frame : true,
	width : 1000,
	bodyPadding : 5,
	waitMsgTarget : true,
	method : 'POST',
	languageObj : null,
	searchWin : null,
	searchStore : null,
	currmode : PageMode.View,
	currrow : 0,
	dataloaded : false,
	bodypanel : null,
	toolpanel : null,
	defaults : {
		xtype : 'textfield'
	},
	fieldDefaults : {
		labelAlign : 'right',
		labelWidth : 80,
		msgTarget : 'side'
	},
	loadDefault : function() {
		if (this.items != null && this.items.length > 0) {
			for ( var i = 0; i < this.items.length; i++) {
				if (Ext.getClassName(this.items.get(i)).indexOf('DCI') != -1) {
					this.items.get(i).loadDefault();
				}
			}
		}

		if (this.bodypanel != null) {
			this.bodypanel.clearGrid();
		}
		this.resetDataLoaded();
	},
	showQueryWindow : function() {

	},
	initQueryWindow : function() {
		if (this.getCurrMode() == PageMode.Add) {
			this.loadDefault();

			for ( var i = 0; i < this.items.length; i++) {
				try {
					if (typeof this.items.get(i).clearInvalid == 'function') {
						this.items.get(i).clearInvalid();
					}
				} catch (err) {
					if (window.console) {
						console.log(err.message);
					}
				}
			}
		}
	},
	getCurrMode : function() {
		return this.currmode;
	},
	setCurrMode : function(currmode) {
		var me = this;
		me.currmode = currmode;
		if (me.currmode == PageMode.View) {
			setCompsReadOnly(this.items, true);
			if (me.bodypanel != null) {
				me.bodypanel.changeMode(PageMode.Edit);
			}
		} else if (me.currmode == PageMode.Add) {
			setCompsReadOnly(me.items, false);
		} else if (me.currmode == PageMode.Edit) {
			setPageEdit(me.items);
		}
	},
	resetDataLoaded : function() {
		this.setDataLoaded(false);
	},
	setDataLoaded : function(loaded) {
		this.dataloaded = loaded;
		if (this.bodypanel != null) {
			this.bodypanel.dataloaded(loaded);
		}

		if (this.toolpanel != null) {
			this.toolpanel.setDataLoadMode(loaded);
		}
	},
	cusValid : function(func) {
		func();
	},
	initComponent : function() {
		this.callParent(arguments);
		setCompsReadOnly(this.items, true);
	}
});

Ext.define('DCI.Customer.QueryWindow', {
	extend : 'Ext.window.Window',
	alias : 'DCI.Customer.QueryWindow',
	layout : 'border',
	title : 'Query',
	height : 200,
	width : 400,
	minWidth : 300,
	minHeight : 200,
	modal : true,
	plain : true,
	headform : null,
	items : [ {
		xtype : 'panel',
		region : 'north',
		height : 35,
		layout : 'absolute',
		items : [ {
			xtype : 'combobox',
			fieldLabel : '',
			displayField : 'label',
			valueField : 'value',
			typeAhead : true,
			editable : false,
			queryMode : 'local',
			labelWidth : 1,
			matchFieldWidth : false,
			multiSelect : false,
			forceSelection : true,
			store : Ext.create('Ext.data.Store', {
				fields : [ {
					name : 'label',
					type : 'string'
				}, {
					name : 'value',
					type : 'string'
				} ],
				autoLoad : false,
				proxy : {
					type : 'memory',
					reader : {
						type : 'json'
					}
				}
			}),
			x : 5,
			y : 5
		}, {
			xtype : 'textfield',
			name : 'qvalue',
			fieldLabel : '',
			labelWidth : 1,
			width : 160,
			x : 155,
			y : 5
		}, {
			xtype : 'button',
			cls : 'search-toolbar',
			width : 30,
			height : 30,
			x : 315,
			y : 1,
			handler : function() {
				this.up('panel').up('window').clickFunc();
			}
		} ]
	}, {
		xtype : 'grid',
		region : 'center',
		renderer : "component",
		stripeRows : true,
		autoScroll : true,
		loadMask : true,
		enableTextSelection : true,
		viewConfig : {
			forceFit : false,
			autoLoad : false
		},
		columns : [],
		store : null,
		dockedItems : [ {
			xtype : 'pagingtoolbar',
			store : null,
			dock : 'bottom',
			displayInfo : true,
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
				}
			}
		} ],
		listeners : {
			celldblclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
				var me = this;
				var win = this.up('window');
				var store = grid.getStore();
				if (win.headform != null) {
					if (store.currentPage == 1) {
						win.headform.currrow = rowIndex;
					} else {
						win.headform.currrow = ((store.currentPage - 1) * store.pageSize) + rowIndex;
					}
				}
				this.up('window').loadHeadData(record);

			}
		}
	} ],
	setInitObjects : function(records, gridStore, gridColumns) {
		if (records.length > 0) {
			var combo = this.items.get(0).items.get(0);
			combo.getStore().loadData(records[0].get("cols"));
			if (combo.getStore().getCount() > 0 && typeof combo != 'undefined' && combo != null) {
				combo.setValue(combo.getStore().getAt(0).get("value"));
			}
		}
		this.items.get(1).reconfigure(gridStore, gridColumns);
		this.items.get(1).child('pagingtoolbar').bindStore(gridStore);
		gridStore.load();
	},
	loadHeadData : function(record) {
		this.headform.loadRecord(record);
		this.headform.setDataLoaded(true);
		this.close();
	},
	clickFunc : function() {
		var me = this;
		var gstore = me.items.get(1).getStore();
		var fcol = me.items.get(0).items.get(0).getValue();
		var fvalue = me.items.get(0).items.get(1).getValue();

		if (gstore.getProxy().extraParams.hasOwnProperty('filter')) {
			gstore.getProxy().extraParams['filter'] = " and " + fcol + " like '%" + fvalue + "%' ";
		}
		if (gstore.getProxy().extraParams.hasOwnProperty('page')) {
			gstore.getProxy().extraParams['page'] = 1;
		}
		gstore.currentPage = 1;
		gstore.load();
	}
});
Ext.define('DCI.Customer.SubTabPanel', {
	extend : 'Ext.tab.Panel',
	alias : 'DCI.Customer.SubTabPanel',
	minWidth : 900,
	minHeight : 400,
	widthChangeControls : [],
	bodyStyle : {
		background : "#eaf1fb"
	},
	resize : function(width, height) {
		if (width > this.width) {
			this.width = width;
			for ( var i = 0; i < this.widthChangeControls.length; i++) {
				if (this.widthChangeControls[i] != null) {
					this.widthChangeControls[i].setWidth(width - 20);
				}
			}
		} else {
			var itemWidth = this.minWidth;
			if (width > this.minWidth) {
				itemWidth = width;
			}
			for ( var i = 0; i < this.widthChangeControls.length; i++) {
				if (this.widthChangeControls[i] != null) {
					this.widthChangeControls[i].setWidth(itemWidth - 20);
				}
			}
			this.width = width;
		}

		if (height < this.minHeight) {
			height = this.minHeight;
		}
		this.setHeight(height);
		// this.updateLayout();
	},
	beforeClose : function() {

	},
	focusPage : function() {
		var me = this;
		if (Ext.get(me.renderTo) != null) {
			me.resize(Ext.get(me.renderTo).getWidth(), Ext.get(me.renderTo).getHeight());
		}
	},
	leavePage : function() {

	},
	globalTimerEvent : function(initPage, gap) {

	},
	globalTimerStop : function() {

	}
});

Ext.define('DCI.Customer.SubPanel', {
	extend : 'Ext.Panel',
	alias : 'DCI.Customer.SubPanel',
	minWidth : 900,
	minHeight : 400,
	parentComp : null,
	bodyStyle : {
		background : "#eaf1fb"
	},
	widthChangeControls : [],
	setParent : function(parent) {
		this.parentComp = parent;
		if (parent != null) {
			// this.resize(parent.getWidth(), parent.getHeight());
		}
	},
	resize : function(width, height) {
		if (width > this.width) {
			this.width = width;
			for ( var i = 0; i < this.widthChangeControls.length; i++) {
				if (this.widthChangeControls[i] != null) {
					this.widthChangeControls[i].setWidth(width - 20);
				}
			}
		} else {
			var itemWidth = this.minWidth;
			if (width > this.minWidth) {
				itemWidth = width;
			}

			for ( var i = 0; i < this.widthChangeControls.length; i++) {
				if (this.widthChangeControls[i] != null) {
					this.widthChangeControls[i].setWidth(itemWidth - 20);
				}
			}
			this.width = width;
		}

		if (height < this.minHeight) {
			height = this.minHeight;
		}
		this.setHeight(height);
		// this.updateLayout();
	},
	beforeClose : function() {

	},
	focusPage : function() {
		var me = this;
		if (Ext.get(me.renderTo) != null) {
			me.resize(Ext.get(me.renderTo).getWidth(), Ext.get(me.renderTo).getHeight());
		}
	},
	leavePage : function() {

	},
	globalTimerEvent : function(initPage, gap) {

	},
	globalTimerStop : function() {

	}
});

Ext.define('DCI.Customer.ToolBarPanel', {
	extend : 'Ext.Panel',
	alias : 'DCI.Customer.ToolBarPanel',
	region : 'north',
	width : 300,
	height : 35,
	bodyStyle : {
		background : "#99bce8"
	},
	x : 0,
	y : 0,
	border : 0,
	layout : 'absolute',
	bindform : null,
	postvalue : '',
	uid : '',
	canEdit : true,
	cusMsg : [],
	keepAdd : true,
	items : [ {
		xtype : 'button',
		cls : 'search-toolbar',
		x : 5,
		y : 3,
		width : 30,
		height : 30,
		handler : function() {
			var me = this;
			var bform = me.up('panel').bindform;
			var bodyInEdit = false;

			if (bform != null) {
				bform.setLoading(false);
				if (bform.bodypanel && bform.bodypanel != null && bform.bodypanel.getCurrMode() == PageMode.Edit) {
					bodyInEdit = true;
				}
				if (bform.getCurrMode() == PageMode.View && !bodyInEdit) {
					bform.showQueryWindow();
				} else {
					Ext.MessageBox.confirm(bform.languageObj.confirm_title, bform.languageObj.data_lose_warning, function(btn) {
						if (btn == 'yes') {
							bform.showQueryWindow();
							bform.setCurrMode(PageMode.View);
							me.up('panel').ModeChange(PageMode.View);
						}
					});
				}
			}
		}
	}, {
		xtype : 'button',
		cls : 'add-toolbar',
		x : 40,
		y : 3,
		width : 30,
		height : 30,
		handler : function() {
			var bform = this.up('panel').bindform;
			if (bform != null) {
				if (bform.getCurrMode() == PageMode.View) {
					bform.setLoading(false);
					if (this.up('panel').canEdit) {
						bform.loadDefault();
						bform.setCurrMode(PageMode.Add);
						this.up('panel').ModeChange(PageMode.Add);
					} else {
						Ext.MessageBox.alert(bform.languageObj.errmsg, bform.languageObj.no_edit_auth);
					}
				}
			}
		}
	}, {
		xtype : 'button',
		cls : 'edit-toolbar',
		x : 75,
		y : 3,
		width : 30,
		height : 30,
		handler : function() {
			var bform = this.up('panel').bindform;
			if (bform != null) {
				if (bform.getCurrMode() == PageMode.View && bform.dataloaded) {
					bform.setLoading(false);
					if (this.up('panel').canEdit) {
						var eauth = this.up('panel').exceptionEditAuth();
						if (eauth != null && eauth.length == 2) {
							if (eauth[0] == '1') {
								bform.setCurrMode(PageMode.Edit);
								this.up('panel').ModeChange(PageMode.Edit);
							} else {
								Ext.MessageBox.alert(bform.languageObj.errmsg, eauth[1]);
							}
						}
					} else {
						Ext.MessageBox.alert(bform.languageObj.errmsg, bform.languageObj.no_edit_auth);
					}
				}
			}
		}
	}, {
		xtype : 'button',
		cls : 'delete-toolbar',
		x : 110,
		y : 3,
		width : 30,
		height : 30,
		handler : function() {
			var bform = this.up('panel').bindform;
			var pvalue = this.up('panel').postvalue;
			var puid = this.up('panel').uid;
			var cusmsg = this.up('panel').cusMsg;
			if (bform != null) {
				if (bform.getCurrMode() == PageMode.Edit) {
					bform.setLoading(false);
					if (this.up('panel').canEdit) {
						var toolbar = this.up('panel');
						var eauth = toolbar.exceptionDeleteAuth();
						if (eauth != null && eauth.length == 2) {
							if (eauth[0] == '1') {
								Ext.MessageBox.confirm(bform.languageObj.delete_confirm_title, bform.languageObj.delete_confirm_msg, function(btn) {
									if (btn == 'yes') {
										Ext.Ajax.request({
											method : 'POST',
											url : bform.url,
											params : {
												action : 'del',
												datas : Ext.encode(bform.getForm().getValues()),
												DCITag : pvalue,
												uid : puid
											},
											success : function(a) {
												var result = Ext.JSON.decode(a.responseText);
												if (result.success) {
													bform.loadDefault();
													bform.setCurrMode(PageMode.View);
													toolbar.ModeChange(PageMode.View);

													for ( var i = 0; i < bform.items.length; i++) {
														try {
															if (typeof bform.items.get(i).clearInvalid == 'function') {
																bform.items.get(i).clearInvalid();
															}
														} catch (err) {
															if (window.console) {
																console.log(err.message);
															}
														}
													}
													if (cusmsg != null && cusmsg.length >= 3) {
														Ext.Msg.alert(bform.languageObj.delete_result_title, cusmsg[2]);
													} else {
														Ext.Msg.alert(bform.languageObj.delete_result_title, bform.languageObj.delete_success);
													}
												} else {
													Ext.Msg.alert(bform.languageObj.save_result_title, bform.languageObj.delete_fail + " :</br>" + result.errorMessage);
												}

											},
											failure : function(f, action) {
												Ext.MessageBox.alert(bform.languageObj.errmsg, bform.languageObj.system_error);
											}
										});
									}
								});

							} else {
								Ext.MessageBox.alert(bform.languageObj.errmsg, eauth[1]);
							}
						}
					} else {
						alerExt.MessageBox.alert(bform.languageObj.errmsg, bform.languageObj.no_edit_auth);
					}
				}
			}
		}
	}, {
		xtype : 'button',
		cls : 'save-toolbar',
		x : 145,
		y : 3,
		width : 30,
		height : 30,
		handler : function() {
			var bform = this.up('panel').bindform;
			var toolbar = this.up('panel');
			var cusmsg = this.up('panel').cusMsg;
			var keepadd = this.up('panel').keepAdd;
			if (bform != null) {
				if (bform.getCurrMode() == PageMode.Add || bform.getCurrMode() == PageMode.Edit) {
					bform.setLoading(false);
					if (this.up('panel').canEdit) {
						if (bform.isValid()) {
							bform.cusValid(function() {
								bform.getForm().submit({
									waitMsg : bform.languageObj.save_msg,
									params : {
										action : 'save',
										datas : Ext.encode(bform.getForm().getValues()),
										DCITag : toolbar.postvalue,
										uid : toolbar.uid,
										mode : bform.getCurrMode()
									},
									failure : function(form, action) {
										Ext.Msg.alert(bform.languageObj.save_result_title, bform.languageObj.save_fail + " :</br>" + action.result.errorMessage);
									},
									success : function(form, action) {
										var cmode = bform.getCurrMode();

										if (cmode == PageMode.Add) {
											if (keepadd) {
												bform.loadDefault();

												bform.setCurrMode(PageMode.View);
												toolbar.ModeChange(PageMode.View);
											} else {
												bform.setCurrMode(PageMode.View);
												toolbar.ModeChange(PageMode.View);

												var resultdata = action.result.resultData.items[0];
												if (resultdata == null) {
													bform.loadDefault();
												} else {
													bform.getForm().setValues(resultdata);
													bform.setDataLoaded(true);
												}
											}

											if (bform.bodypanel != null) {
												bform.bodypanel.setNewCondi();
											}
										} else {
											bform.setCurrMode(PageMode.View);
											toolbar.ModeChange(PageMode.View);
										}
										for ( var i = 0; i < bform.items.length; i++) {
											try {
												if (typeof bform.items.get(i).clearInvalid == 'function') {
													bform.items.get(i).clearInvalid();
												}
											} catch (err) {
												if (window.console) {
													console.log(err.message);
												}
											}
										}
										if (resultdata != null) {
											toolbar.afterSaveFunc(resultdata);
										}
										if (cmode == PageMode.Add && cusmsg != null && cusmsg.length >= 1) {
											Ext.Msg.alert(bform.languageObj.save_result_title, cusmsg[0]);
										} else if (cmode == PageMode.Edit && cusmsg != null && cusmsg.length >= 2) {
											Ext.Msg.alert(bform.languageObj.save_result_title, cusmsg[1]);
										} else {
											Ext.Msg.alert(bform.languageObj.save_result_title, bform.languageObj.save_success);
										}
									}
								});
							});
						}
					} else {
						Ext.MessageBox.alert(bform.languageObj.errmsg, bform.languageObj.no_edit_auth);
					}
				}
			}
		}
	}, {
		xtype : 'button',
		cls : 'exit-toolbar',
		x : 180,
		y : 3,
		width : 30,
		height : 30,
		handler : function() {
			var me = this;
			var bform = me.up('panel').bindform;
			if (bform != null) {
				bform.setLoading(false);
				if (bform.getCurrMode() == PageMode.Add || bform.getCurrMode() == PageMode.Edit) {
					Ext.MessageBox.confirm(bform.languageObj.confirm_title, bform.languageObj.data_lose_warning, function(btn) {
						if (btn == 'yes') {
							if (bform.getCurrMode() == PageMode.Add) {
								bform.loadDefault();
							}
							for ( var i = 0; i < bform.items.length; i++) {
								try {
									if (typeof bform.items.get(i).clearInvalid == 'function') {
										bform.items.get(i).clearInvalid();
									}
								} catch (err) {
									if (window.console) {
										console.log(err.message);
									}
								}
							}
							bform.setCurrMode(PageMode.View);
							me.up('panel').ModeChange(PageMode.View);
						}
					});
				}
			}
		}
	}, {
		xtype : 'label',
		text : '',
		height : 30,
		style : 'font: bold 20px courier',
		x : 215,
		y : 5
	} ],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'right',
		width : 122,
		style : {
			background : "#99bce8"
		},
		layout : 'absolute',
		items : [ {
			xtype : 'button',
			cls : 'first-disable-toolbar',
			x : 0,
			y : 3,
			width : 30,
			height : 30,
			handler : function() {
				var me = this;
				var bform = me.up('panel').bindform;
				if (bform != null) {
					var store = bform.searchStore;
					if (store != null && store.getTotalCount() > 1 && bform.currrow != 0) {
						if (bform.getCurrMode() == PageMode.View) {
							if (store.currentPage == 1) {
								bform.currrow = 0;
								bform.searchWin.loadHeadData(store.getAt(0));
							} else {
								if (store.getProxy().extraParams.hasOwnProperty('page')) {
									store.getProxy().extraParams['page'] = 1;
								}
								store.currentPage = 1;
								store.load(function() {
									bform.currrow = 0;
									bform.searchWin.loadHeadData(store.getAt(0));
								});
							}
						} else if (bform.getCurrMode() == PageMode.Edit) {
							Ext.MessageBox.confirm(bform.languageObj.confirm_title, bform.languageObj.data_lose_warning, function(btn) {
								if (btn == 'yes') {
									if (store.currentPage == 1) {
										bform.currrow = 0;
										bform.searchWin.loadHeadData(store.getAt(0));
									} else {
										if (store.getProxy().extraParams.hasOwnProperty('page')) {
											store.getProxy().extraParams['page'] = 1;
										}
										store.currentPage = 1;
										store.load(function() {
											bform.currrow = 0;
											bform.searchWin.loadHeadData(store.getAt(0));
										});
									}
									for ( var i = 0; i < bform.items.length; i++) {
										try {
											if (typeof bform.items.get(i).clearInvalid == 'function') {
												bform.items.get(i).clearInvalid();
											}
										} catch (err) {
											if (window.console) {
												console.log(err.message);
											}
										}
									}
									bform.setCurrMode(PageMode.View);
									me.up('panel').ModeChange(PageMode.View);
								}
							});
						}
					}
				}
			}
		}, {
			xtype : 'button',
			cls : 'back-disable-toolbar',
			x : 30,
			y : 3,
			width : 30,
			height : 30,
			handler : function() {
				var me = this;
				var bform = me.up('panel').bindform;
				if (bform != null) {
					var store = bform.searchStore;
					var idx = bform.currrow;
					if (store != null && store.getTotalCount() > 1) {
						if (idx >= 1) {
							idx--;
							var page = 1;
							if (idx % store.pageSize == 0) {
								page = (idx / store.pageSize) + 1;
							} else {
								page = Math.ceil(idx / store.pageSize);
							}

							if (bform.getCurrMode() == PageMode.View) {
								if (store.currentPage == page) {
									bform.currrow = idx;
									bform.searchWin.loadHeadData(store.getAt(idx % store.pageSize));
								} else {
									if (store.getProxy().extraParams.hasOwnProperty('page')) {
										store.getProxy().extraParams['page'] = page;
									}
									store.currentPage = page;
									store.load(function() {
										bform.currrow = idx;
										bform.searchWin.loadHeadData(store.getAt(idx % store.pageSize));
									});
								}
							} else if (bform.getCurrMode() == PageMode.Edit) {
								Ext.MessageBox.confirm(bform.languageObj.confirm_title, bform.languageObj.data_lose_warning, function(btn) {
									if (btn == 'yes') {
										if (store.currentPage == page) {
											bform.currrow = idx;
											bform.searchWin.loadHeadData(store.getAt(idx % store.pageSize));
										} else {
											if (store.getProxy().extraParams.hasOwnProperty('page')) {
												store.getProxy().extraParams['page'] = page;
											}
											store.currentPage = page;
											store.load(function() {
												bform.currrow = idx;
												bform.searchWin.loadHeadData(store.getAt(idx % store.pageSize));
											});
										}
										for ( var i = 0; i < bform.items.length; i++) {
											try {
												if (typeof bform.items.get(i).clearInvalid == 'function') {
													bform.items.get(i).clearInvalid();
												}
											} catch (err) {
												if (window.console) {
													console.log(err.message);
												}
											}
										}
										bform.setCurrMode(PageMode.View);
										me.up('panel').ModeChange(PageMode.View);
									}
								});
							}
						}
					}
				}
			}
		}, {
			xtype : 'button',
			cls : 'forward-disable-toolbar',
			x : 60,
			y : 3,
			width : 30,
			height : 30,
			handler : function() {
				var me = this;
				var bform = me.up('panel').bindform;
				if (bform != null) {
					var store = bform.searchStore;
					var idx = bform.currrow;
					if (store != null && store.getTotalCount() > 1) {
						if (idx < store.getTotalCount() - 1) {
							idx++;
							var page = 1;
							if (idx % store.pageSize == 0) {
								page = (idx / store.pageSize) + 1;
							} else {
								page = Math.ceil(idx / store.pageSize);
							}
							if (bform.getCurrMode() == PageMode.View) {
								if (store.currentPage == page) {
									bform.currrow = idx;
									bform.searchWin.loadHeadData(store.getAt(idx % store.pageSize));
								} else {
									if (store.getProxy().extraParams.hasOwnProperty('page')) {
										store.getProxy().extraParams['page'] = page;
									}
									store.currentPage = page;
									store.load(function() {
										bform.currrow = idx;
										bform.searchWin.loadHeadData(store.getAt(idx % store.pageSize));
									});
								}
							} else if (bform.getCurrMode() == PageMode.Edit) {
								Ext.MessageBox.confirm(bform.languageObj.confirm_title, bform.languageObj.data_lose_warning, function(btn) {
									if (btn == 'yes') {
										if (store.currentPage == page) {
											bform.currrow = idx;
											bform.searchWin.loadHeadData(store.getAt(idx % store.pageSize));
										} else {
											if (store.getProxy().extraParams.hasOwnProperty('page')) {
												store.getProxy().extraParams['page'] = page;
											}
											store.currentPage = page;
											store.load(function() {
												bform.currrow = idx;
												bform.searchWin.loadHeadData(store.getAt(idx % store.pageSize));
											});
										}
										for ( var i = 0; i < bform.items.length; i++) {
											try {
												if (typeof bform.items.get(i).clearInvalid == 'function') {
													bform.items.get(i).clearInvalid();
												}
											} catch (err) {
												if (window.console) {
													console.log(err.message);
												}
											}
										}
										bform.setCurrMode(PageMode.View);
										me.up('panel').ModeChange(PageMode.View);
									}
								});
							}
						}
					}
				}
			}
		}, {
			xtype : 'button',
			cls : 'end-disable-toolbar',
			x : 90,
			y : 3,
			width : 30,
			height : 30,
			handler : function() {
				var me = this;
				var bform = me.up('panel').bindform;
				if (bform != null) {
					var store = bform.searchStore;
					var targetRow = -1;
					if (store != null && store.getTotalCount() > 1) {
						if (bform.getCurrMode() == PageMode.View) {
							if (store.currentPage == Math.ceil(store.getTotalCount() / store.pageSize)) {
								bform.currrow = store.getTotalCount() - 1;
								targetRow = store.getTotalCount() % store.pageSize;
								if (targetRow == 0) {
									targetRow = store.pageSize - 1;
								} else {
									targetRow = targetRow - 1;
								}
								// console.log(store.getTotalCount() %
								// store.pageSize);
								// bform.searchWin.loadHeadData(store.getAt((store.getTotalCount()
								// % store.pageSize) - 1));
								bform.searchWin.loadHeadData(store.getAt(targetRow));
							} else {
								var page = Math.ceil(store.getTotalCount() / store.pageSize);
								if (store.getProxy().extraParams.hasOwnProperty('page')) {
									store.getProxy().extraParams['page'] = page;
								}
								store.currentPage = page;
								store.load(function() {
									bform.currrow = store.getTotalCount() - 1;
									targetRow = store.getTotalCount() % store.pageSize;
									if (targetRow == 0) {
										targetRow = store.pageSize - 1;
									} else {
										targetRow = targetRow - 1;
									}
									bform.searchWin.loadHeadData(store.getAt(targetRow));
								});
							}
						} else if (bform.getCurrMode() == PageMode.Edit) {
							Ext.MessageBox.confirm(bform.languageObj.confirm_title, bform.languageObj.data_lose_warning, function(btn) {
								if (btn == 'yes') {
									if (store.currentPage == Math.ceil(store.getTotalCount() / store.pageSize)) {
										bform.currrow = store.getTotalCount() - 1;
										targetRow = store.getTotalCount() % store.pageSize;
										if (targetRow == 0) {
											targetRow = store.pageSize - 1;
										} else {
											targetRow = targetRow - 1;
										}
										bform.searchWin.loadHeadData(store.getAt(targetRow));
									} else {
										var page = Math.ceil(store.getTotalCount() / store.pageSize);
										if (store.getProxy().extraParams.hasOwnProperty('page')) {
											store.getProxy().extraParams['page'] = page;
										}
										store.currentPage = page;
										store.load(function() {
											bform.currrow = store.getTotalCount() - 1;
											targetRow = store.getTotalCount() % store.pageSize;
											if (targetRow == 0) {
												targetRow = store.pageSize - 1;
											} else {
												targetRow = targetRow - 1;
											}
											bform.searchWin.loadHeadData(store.getAt(targetRow));
										});
									}
									for ( var i = 0; i < bform.items.length; i++) {
										try {
											if (typeof bform.items.get(i).clearInvalid == 'function') {
												bform.items.get(i).clearInvalid();
											}
										} catch (err) {
											if (window.console) {
												console.log(err.message);
											}
										}
									}
									bform.setCurrMode(PageMode.View);
									me.up('panel').ModeChange(PageMode.View);
								}
							});
						}
					}
				}
			}
		} ]
	} ],
	setBindForm : function(bindform) {
		this.bindform = bindform;
	},
	ModeChange : function(mode) {
		var label = this.items.get(this.items.length - 1);
		if (label != null) {
			if (mode == PageMode.Edit || mode == PageMode.Add) {
				label.setText(this.bindform.languageObj.mode_edit);
			} else {
				label.setText(this.bindform.languageObj.mode_view);
			}
		}
		var btnadd = this.items.get(1);
		var btnedit = this.items.get(2);
		var btndelete = this.items.get(3);
		var btnsave = this.items.get(4);
		var btnexit = this.items.get(5);

		if (mode == PageMode.Add) {
			btnsave.removeCls('save-disable-toolbar');
			btnsave.addCls('save-toolbar');
			btnexit.removeCls('exit-disable-toolbar');
			btnexit.addCls('exit-toolbar');
			btnedit.removeCls('edit-toolbar');
			btnedit.addCls('edit-disable-toolbar');
			btndelete.removeCls('delete-toolbar');
			btndelete.addCls('delete-disable-toolbar');
			btnadd.removeCls('add-disable-toolbar');
			btnadd.addCls('add-toolbar');
		} else if (mode == PageMode.Edit) {
			btnexit.removeCls('exit-disable-toolbar');
			btnexit.addCls('exit-toolbar');
			btnedit.removeCls('edit-disable-toolbar');
			btnedit.addCls('edit-toolbar');
			btndelete.removeCls('delete-disable-toolbar');
			btndelete.addCls('delete-toolbar');
			btnsave.removeCls('save-disable-toolbar');
			btnsave.addCls('save-toolbar');
			btnadd.removeCls('add-toolbar');
			btnadd.addCls('add-disable-toolbar');
		} else if (mode == PageMode.View) {
			btnexit.removeCls('exit-toolbar');
			btnexit.addCls('exit-disable-toolbar');
			if (this.bindform.dataloaded) {
				btnedit.removeCls('edit-disable-toolbar');
				btnedit.addCls('edit-toolbar');
			} else {
				btnedit.removeCls('edit-toolbar');
				btnedit.addCls('edit-disable-toolbar');
			}
			btndelete.removeCls('delete-toolbar');
			btndelete.addCls('delete-disable-toolbar');
			btnsave.removeCls(btnsave.cls);
			btnsave.addCls('save-disable-toolbar');
			btnadd.removeCls('add-disable-toolbar');
			btnadd.addCls('add-toolbar');
		}
	},
	setCusButtons : function(buttons) {
		if (buttons != null) {
			var me = this;
			for ( var i = 0; i < buttons.length; i++) {
				me.insert(me.items.length - 1, buttons[i]);
			}
			var label = me.items.get(me.items.length - 1);
			var newx = label.x;
			newx = newx + buttons.length * 35;
			label.x = newx;
		}
	},
	exceptionEditAuth : function() {
		return [ "1", "" ];
	},
	exceptionDeleteAuth : function() {
		return [ "1", "" ];
	},
	setDataLoadMode : function(loaded) {
		var me = this;
		var btnedit = me.items.get(2);
		var ditems = me.getDockedItems('[dock="right"]');
		var dpanel = ditems[0];
		var form = me.bindform;
		var store = form.searchStore;
		var idx = form.currrow;

		if (btnedit != null) {
			if (loaded) {
				btnedit.removeCls('edit-disable-toolbar');
				btnedit.addCls('edit-toolbar');
			} else {
				btnedit.removeCls('edit-toolbar');
				btnedit.addCls('edit-disable-toolbar');
			}
		}

		if (loaded && store != null && store.getTotalCount() > 1) {
			if (idx == 0) {
				dpanel.items.get(0).removeCls('first-toolbar');
				dpanel.items.get(0).addCls('first-disable-toolbar');
				dpanel.items.get(1).removeCls('back-toolbar');
				dpanel.items.get(1).addCls('back-disable-toolbar');
				dpanel.items.get(2).removeCls('forward-disable-toolbar');
				dpanel.items.get(2).addCls('forward-toolbar');
				dpanel.items.get(3).removeCls('end-disable-toolbar');
				dpanel.items.get(3).addCls('end-toolbar');
			} else if (idx == store.getTotalCount() - 1) {
				dpanel.items.get(0).removeCls('first-disable-toolbar');
				dpanel.items.get(0).addCls('first-toolbar');
				dpanel.items.get(1).removeCls('back-disable-toolbar');
				dpanel.items.get(1).addCls('back-toolbar');
				dpanel.items.get(2).removeCls('forward-toolbar');
				dpanel.items.get(2).addCls('forward-disable-toolbar');
				dpanel.items.get(3).removeCls('end-toolbar');
				dpanel.items.get(3).addCls('end-disable-toolbar');
			} else {
				dpanel.items.get(0).removeCls('first-disable-toolbar');
				dpanel.items.get(0).addCls('first-toolbar');
				dpanel.items.get(1).removeCls('back-disable-toolbar');
				dpanel.items.get(1).addCls('back-toolbar');
				dpanel.items.get(2).removeCls('forward-disable-toolbar');
				dpanel.items.get(2).addCls('forward-toolbar');
				dpanel.items.get(3).removeCls('end-disable-toolbar');
				dpanel.items.get(3).addCls('end-toolbar');
			}
		} else {
			dpanel.items.get(0).removeCls('first-toolbar');
			dpanel.items.get(0).addCls('first-disable-toolbar');
			dpanel.items.get(1).removeCls('back-toolbar');
			dpanel.items.get(1).addCls('back-disable-toolbar');
			dpanel.items.get(2).removeCls('forward-toolbar');
			dpanel.items.get(2).addCls('forward-disable-toolbar');
			dpanel.items.get(3).removeCls('end-toolbar');
			dpanel.items.get(3).addCls('end-disable-toolbar');
			if (!loaded) {
				form.searchStore = null;
			}
		}
	},
	setTips : function(languageObj) {
		var me = this;
		var ditems = me.getDockedItems('[dock="right"]');
		var dpanel = ditems[0];

		me.items.get(0).setTooltip(languageObj.search);
		me.items.get(1).setTooltip(languageObj.add);
		me.items.get(2).setTooltip(languageObj.edit);
		me.items.get(3).setTooltip(languageObj._delete);
		me.items.get(4).setTooltip(languageObj.save);
		me.items.get(5).setTooltip(languageObj.exit);

		dpanel.items.get(0).setTooltip(languageObj.first_row);
		dpanel.items.get(1).setTooltip(languageObj.back_row);
		dpanel.items.get(2).setTooltip(languageObj.next_row);
		dpanel.items.get(3).setTooltip(languageObj.last_row);
	}
});

Ext.define('DCI.Customer.BodyGridPanel', {
	extend : 'Ext.panel.Panel',
	alias : 'DCI.Customer.BodyGridPanel',
	region : 'center',
	layout : 'border',
	bodyStyle : {
		background : "#eaf1fb"
	},
	bodyPadding : 5,
	headform : null,
	actionurl : '',
	languageObj : null,
	currmode : PageMode.View,
	dataloaded : false,
	gridid : '',
	postvalue : '',
	keyfields : [],
	headKeys : {},
	canEdit : true,
	oriGStore : null,
	oriGColumns : [],
	stopUsingBtn : [],
	uid : '',
	cusFormatIconPath : '',
	items : [ {
		xtype : 'panel',
		region : 'north',
		border : 0,
		layout : 'absolute',
		height : 40,
		x : 400,
		y : 0,
		items : [ {
			xtype : 'button',
			// text : this.up('panel').up('panel').languageObj == null ? 'add' :
			// this.up('panel').up('panel').languageObj.add,
			width : 50,
			disabled : true,
			x : 0,
			y : 5,
			handler : function() {
				var currmode = this.up('panel').up('panel').currmode;

				if (currmode == PageMode.Edit) {
					this.up('panel').up('panel').addNewRow();
				}
			}
		}, {
			xtype : 'button',
			// text : this.up('panel').up('panel').languageObj == null ? 'save'
			// : this.up('panel').up('panel').languageObj.save,
			disabled : true,
			width : 50,
			x : 55,
			y : 5,
			handler : function() {
				var bodypanel = this.up('panel').up('panel');
				var currmode = bodypanel.currmode;

				if (currmode == PageMode.Edit) {
					if (bodypanel.valided()) {
						var langobj = bodypanel.languageObj;
						var store = bodypanel.items.get(1).getStore();
						if (bodypanel.items.get(1).plugins[0] != null) {
							bodypanel.items.get(1).plugins[0].completeEdit();
						}
						var datas = [];
						var addRows = 0;
						for ( var i = 0; i < store.getCount(); i++) {
							if (store.getAt(i).get("moditag") == 1) {
								datas.push(store.getAt(i).data);
								if (store.getAt(i).get("moditp") == 'add') {
									addRows++;
								}
							}
						}

						Ext.Ajax.request({
							method : 'POST',
							url : bodypanel.actionurl,
							params : {
								DCITag : bodypanel.postvalue,
								uid : bodypanel.uid,
								action : 'bodySave',
								datas : Ext.encode(datas),
								allAdd : store.getCount() == addRows
							},
							success : function(a) {
								bodypanel.gridReload();
								Ext.Msg.alert(langobj.save_result_title, langobj.save_success);
							},
							failure : function(f, action) {
								Ext.Msg.alert(langobj.save_result_title, langobj.save_fail + " :</br>" + action.result.errorMessage);
							}
						});
					}
				}
			}
		}, {
			xtype : 'button',
			// text : this.up('panel').up('panel').languageObj == null ?
			// '_delete' : this.up('panel').up('panel').languageObj._delete,
			disabled : true,
			width : 50,
			x : 110,
			y : 5,
			handler : function() {
				var bodypanel = this.up('panel').up('panel');
				var currmode = bodypanel.currmode;
				var grid = bodypanel.items.get(1);
				var langobj = bodypanel.languageObj;
				var url = bodypanel.actionurl;
				Ext.MessageBox.confirm(langobj.delete_confirm_title, langobj.delete_confirm_msg, function(btn) {
					if (btn == 'yes') {
						if (currmode == PageMode.Edit) {
							if (grid != null) {
								var s = grid.getSelectionModel().getSelection();
								if (s.length > 0) {
									Ext.Ajax.request({
										method : 'POST',
										url : url,
										params : {
											DCITag : bodypanel.postvalue,
											uid : bodypanel.uid,
											action : 'bodyDelete',
											datas : Ext.encode(s[0].data)
										},
										success : function(a) {
											bodypanel.gridReload();
											Ext.Msg.alert(langobj.delete_result_title, langobj.delete_success);
										},
										failure : function(f, action) {
											Ext.Msg.alert(langobj.delete_result_title, langobj.delete_fail + " :</br>" + action.result.errorMessage);
										}
									});
								}
							}
						}
					}
				});
			}
		}, {
			xtype : 'button',
			// text : 'view',
			disabled : true,
			width : 100,
			x : 165,
			y : 5,
			handler : function() {
				var bodypanel = this.up('panel').up('panel');
				if (bodypanel.canEdit) {
					bodypanel.changeMode(bodypanel.currmode);
					if (bodypanel.currmode == PageMode.View) {
						bodypanel.gridReload();
					}
					// bodypanel.headform.setCurrMode(bodypanel.currmode);
				} else {
					Ext.MessageBox.alert(this.up('panel').up('panel').languageObj.errmsg, this.up('panel').up('panel').languageObj.no_edit_auth);
				}
			}
		}, {
			xtype : 'button',
			// text : this.up('panel').up('panel').languageObj == null ?
			// 'save_format' :
			// this.up('panel').up('panel').languageObj.save_format,
			width : 100,
			x : 270,
			y : 5,
			handler : function() {
				var panel = this.up('panel').up('panel');
				var grid = panel.items.get(1);
				var cols = grid.getView().headerCt.getGridColumns();
				var langobj = panel.languageObj;
				var colinfo = [];
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
					url : './../../PublicCtrl.dsc',
					params : {
						DCITag : this.up('panel').up('panel').postvalue,
						uid : this.up('panel').up('panel').uid,
						action : 'saveGFormat',
						gridid : this.up('panel').up('panel').gridid,
						datas : Ext.encode(colinfo)
					},
					success : function(a) {
						panel.addCusFormatIcon();
						Ext.Msg.alert(langobj.save_result_title, langobj.save_success);
					},
					failure : function(f, action) {
						Ext.Msg.alert(langobj.save_result_title, langobj.save_fail + " :</br>" + action.result.errorMessage);
					}
				});
			}
		}, {
			xtype : 'button',
			width : 120,
			x : 375,
			y : 5,
			handler : function() {
				var panel = this.up('panel').up('panel');
				var grid = panel.items.get(1);
				var langobj = panel.languageObj;
				// var ogs = panel.getOGS();
				var ogc = panel.getOGC();
				Ext.MessageBox.confirm(langobj.load_def_confirm_title, langobj.load_def_confirm_msg, function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							method : 'POST',
							url : './../../PublicCtrl.dsc',
							params : {
								DCITag : panel.postvalue,
								uid : panel.uid,
								action : 'clearGFormat',
								gridid : panel.gridid
							},
							success : function(a) {
								grid.reconfigure(null, ogc);
								panel.rmCusFormatIcon();
								Ext.Msg.alert(langobj.load_def_result_title, langobj.load_def_success);
							},
							failure : function(f, action) {
								Ext.Msg.alert(langobj.load_def_result_title, langobj.load_def_fail + " :</br>" + action.result.errorMessage);
							}
						});
					}
				});
			}
		} ]
	}, {
		xtype : 'grid',
		region : 'center',
		stripeRows : true,
		autoScroll : true,
		loadMask : true,
		allowDeselect : true,
		selModel : {
			allowDeselect : true
		},
		enableTextSelection : true,
		viewConfig : {
			forceFit : false,
			autoLoad : false
		},
		columns : [],
		store : null,
		listeners : {
			celldblclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
				var panel = this.up('panel');
				panel.griddbclick(grid, td, cellIndex, record, tr, rowIndex, e, eOpts);
			},
			selectionchange : function(row, selected, eOpts) {
				var panel = this.up('panel');
				panel.gridselectionchange(row, selected, eOpts);
			},
			deselect : function(row, record, index, eOpts) {
				var panel = this.up('panel');
				panel.griddeselect(row, record, index, eOpts);
			}
		},
		plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
			clicksToEdit : 1,
			listeners : {
				beforeedit : function(editor, e, eOpts) {
					if (e.grid.up('panel').currmode == PageMode.View) {
						return false;
					} else {
						if (e.record.get("moditp") != "add") {
							if (e.grid.up('panel').keyfields != null) {
								var keys = e.grid.up('panel').keyfields;
								for ( var i = 0; i < keys.length; i++) {
									if (keys[i] == e.column.dataIndex) {
										return false;
									}
								}
							}
						}
					}
				},
				edit : function(editor, e, eOpts) {
					if (e.record.get("moditag") != 1) {
						if (e.record != null) {
							e.record.set("moditag", 1);
							e.record.set("moditp", "edit");
							e.record.commit();
						}
					}
				}
			}
		}) ]
	} ],
	setCusButton : function(buttons) {
		if (buttons != null) {
			var me = this;
			for ( var i = 0; i < buttons.length; i++) {
				me.insert(me.items.length - 1, buttons[i]);
			}
		}
	},
	getOGS : function() {
		return this.oriGStore;
	},
	getOGC : function() {
		return this.oriGColumns;
	},
	isCusFormatExist : function() {
		var panel = this.items.get(0);
		var exist = false;

		for ( var i = 0; i < panel.items.length; i++) {
			if (panel.items.get(i).iconid == 'cusicon') {
				exist = true;
				break;
			}
		}

		return exist;
	},
	addCusFormatIcon : function() {
		var me = this;
		var panel = this.items.get(0);
		var lastItemIdx = panel.items.length - 1;

		if (!me.isCusFormatExist()) {
			panel.items.insert(panel.items.length, Ext.create('DCI.Customer.Img', {
				iconid : 'cusicon',
				src : me.cusFormatIconPath,
				x : panel.items.get(lastItemIdx).x + panel.items.get(lastItemIdx).width + 2,
				y : 8,
				width : 20,
				height : 20,
				listeners : {
					afterrender : function(c) {
						Ext.create('Ext.tip.ToolTip', {
							target : c.getEl(),
							html : me.languageObj.cus_format
						});
					}
				}
			}));
			panel.update();
		}
	},
	rmCusFormatIcon : function() {
		var me = this;
		var panel = this.items.get(0);
		if (me.isCusFormatExist()) {
			panel.items.remove(panel.items.get(panel.items.length - 1));
			panel.update();
		}
	},
	initBody : function(store, columns) {
		var me = this;
		var cols = columns;
		var bodyGrid = this.items.get(1);
		var panel = me.items.get(0);
		var tmpcols = [];
		var obj = null;
		me.oriGStore = store;
		me.oriGColumns = [];

		if (me.cusFormatIconPath == null || me.cusFormatIconPath == '') {
			me.cusFormatIconPath = "../../images/icons/CusGridFormat.png";
		}

		for ( var i = 0; i < columns.length; i++) {
			obj = new Object();
			for ( var key in columns[i]) {
				obj[key] = columns[i][key];
			}

			me.oriGColumns.push(obj);
		}

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
					DCITag : this.postvalue,
					uid : this.uid,
					action : 'getGFormat',
					gridid : this.gridid
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
					me.addCusFormatIcon();
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

					if (cols != null && cols.leght != 0) {
						for ( var i = 0; i < cols.length; i++) {
							cols[i]['hidden'] = true;
							tmpcols.push(cols[i]);
						}
					}
				} else {
					tmpcols = cols;
				}

				bodyGrid.reconfigure(store, tmpcols);
			} else {
				bodyGrid.reconfigure(store, cols);
			}
		});
		me.changeMode(PageMode.Edit);
		me.dataloaded(false);

		if (me.languageObj != null) {
			panel.items.get(0).setText(me.languageObj.add);
			panel.items.get(1).setText(me.languageObj.save);
			panel.items.get(2).setText(me.languageObj._delete);
			panel.items.get(3).setText(me.languageObj.to_edit);
			panel.items.get(4).setText(me.languageObj.save_format);
			panel.items.get(5).setText(me.languageObj.load_def_format);
		}

		if (this.headform != null) {
			this.headform.bodypanel = this;
		}
	},
	getCurrMode : function() {
		return this.currmode;
	},
	addNewRow : function() {

	},
	dataloaded : function(loaded) {
		var btn = this.items.get(0).items.get(3);
		if (btn != null) {
			btn.setDisabled(!loaded);
			if (!loaded) {
				this.changeMode(PageMode.Edit);
			}
		}
	},
	clearGrid : function() {
		var store = this.items.get(1).getStore();
		if (store != null) {
			store.removeAll();
		}
	},
	changeMode : function(mode) {
		var me = this;
		var toolbtns = this.items.get(0).items;
		if (mode == PageMode.View) {
			for ( var i = 0; i <= 3; i++) {
				if (i == 3) {
					toolbtns.get(i).setText(me.languageObj.to_view);
				} else {
					if (this.stopUsingBtn != null && this.stopUsingBtn.length > 0) {
						var stopuse = false;
						for ( var j = 0; j < this.stopUsingBtn.length; j++) {
							if (this.stopUsingBtn[j] == i) {
								stopuse = true;
								break;
							}
						}

						toolbtns.get(i).setDisabled(stopuse);
					} else {
						toolbtns.get(i).setDisabled(false);
					}
				}
			}
			this.currmode = PageMode.Edit;
		} else {
			for ( var i = 0; i <= 3; i++) {
				if (i == 3) {
					toolbtns.get(i).setText(me.languageObj.to_edit);
				} else {
					toolbtns.get(i).setDisabled(true);
				}
			}
			this.currmode = PageMode.View;
		}
	},
	setNewCondi : function() {

	},
	gridReload : function() {
		var bodypanel = this;
		var grid = bodypanel.items.get(1);
		if (grid != null) {
			grid.getStore().load();
		}
	},
	griddbclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {

	},
	gridselectionchange : function(row, selected, eOpts) {

	},
	griddeselect : function(row, record, index, eOpts) {

	},
	getGrid : function() {
		var me = this;
		return me.items.get(1);
	},
	valided : function() {
		return true;
	}
});

Ext.define('DCI.Customer.ViewGridPanel', {
	extend : 'Ext.panel.Panel',
	alias : 'DCI.Customer.ViewGridPanel',
	region : 'center',
	layout : 'border',
	bodyStyle : {
		background : "#eaf1fb"
	},
	bodyPadding : 5,
	actionurl : '',
	// languageObj : null,
	dataloaded : false,
	gridid : '',
	postvalue : '',
	uid : '',
	cusFormatIconPath : '',
	oriGStore : null,
	oriGColumns : [],
	items : [ {
		xtype : 'panel',
		region : 'north',
		border : 0,
		header : false,
		split : true,
		collapsible : true,
		collapseMode : 'mini',
		animCollapse : false,
		collapsed : true,
		layout : 'absolute',
		height : 35,
		items : [ {
			xtype : 'button',
			text : 'save format',
			width : 150,
			x : 0,
			y : 5,
			handler : function() {
				var panel = this.up('panel').up('panel');
				var grid = panel.items.get(2);
				var cols = grid.getView().headerCt.getGridColumns();
				var langobj = panel.languageObj;
				var colinfo = [];
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
					url : './../../PublicCtrl.dsc',
					params : {
						DCITag : panel.postvalue,
						uid : panel.uid,
						action : 'saveGFormat',
						gridid : panel.gridid,
						datas : Ext.encode(colinfo)
					},
					success : function(a) {
						panel.addCusFormatIcon();
						Ext.Msg.alert(langobj.save_result_title, langobj.save_success);
					},
					failure : function(f, action) {
						Ext.Msg.alert(langobj.save_result_title, langobj.save_fail + " :</br>" + action.result.errorMessage);
					}
				});
			}
		}, {
			xtype : 'button',
			width : 120,
			x : 155,
			y : 5,
			handler : function() {
				var panel = this.up('panel').up('panel');
				var grid = panel.items.get(2);
				var langobj = panel.languageObj;
				var ogs = panel.getOGS();
				var ogc = panel.getOGC();
				Ext.MessageBox.confirm(langobj.load_def_confirm_title, langobj.load_def_confirm_msg, function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							method : 'POST',
							url : './../../PublicCtrl.dsc',
							params : {
								DCITag : panel.postvalue,
								uid : panel.uid,
								action : 'clearGFormat',
								gridid : panel.gridid
							},
							success : function(a) {
								grid.reconfigure(ogs, ogc);
								panel.rmCusFormatIcon();
								Ext.Msg.alert(langobj.load_def_result_title, langobj.load_def_success);
							},
							failure : function(f, action) {
								Ext.Msg.alert(langobj.load_def_result_title, langobj.load_def_fail + " :</br>" + action.result.errorMessage);
							}
						});
					}
				});
			}
		} ]
	}, {
		xtype : 'grid',
		region : 'center',
		stripeRows : true,
		autoScroll : true,
		loadMask : true,
		allowDeselect : true,
		selModel : {
			allowDeselect : true
		},
		enableTextSelection : true,
		viewConfig : {
			forceFit : false,
			autoLoad : false
		},
		columns : [],
		store : null,
		listeners : {
			celldblclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
				var panel = this.up('panel');
				panel.griddbclick(grid, td, cellIndex, record, tr, rowIndex, e, eOpts);
			}
		}
	} ],
	getOGS : function() {
		return this.oriGStore;
	},
	getOGC : function() {
		return this.oriGColumns;
	},
	isCusFormatExist : function() {
		var panel = this.items.get(0);
		var exist = false;

		for ( var i = 0; i < panel.items.length; i++) {
			if (panel.items.get(i).iconid == 'cusicon') {
				exist = true;
				break;
			}
		}

		return exist;
	},
	addCusFormatIcon : function() {
		var me = this;
		var panel = this.items.get(0);
		var lastItemIdx = panel.items.length - 1;
		if (!me.isCusFormatExist()) {
			panel.items.insert(panel.items.length, Ext.create('DCI.Customer.Img', {
				iconid : 'cusicon',
				src : me.cusFormatIconPath,
				x : panel.items.get(lastItemIdx).x + panel.items.get(lastItemIdx).width + 2,
				y : 8,
				width : 20,
				height : 20,
				listeners : {
					afterrender : function(c) {
						Ext.create('Ext.tip.ToolTip', {
							target : c.getEl(),
							html : me.languageObj.cus_format
						});
					}
				}
			}));
			panel.update();
		}
	},
	rmCusFormatIcon : function() {
		var me = this;
		var panel = this.items.get(0);
		if (me.isCusFormatExist()) {
			panel.items.remove(panel.items.get(panel.items.length - 1));
			panel.update();
		}
	},
	initBody : function(store, columns) {
		var me = this;
		var cols = columns;
		var bodyGrid = me.items.get(2);
		var panel = me.items.get(0);
		var tmpcols = [];
		var obj = null;
		me.oriGStore = store;
		me.oriGColumns = [];

		if (me.cusFormatIconPath == null || me.cusFormatIconPath == '') {
			me.cusFormatIconPath = "../../images/icons/CusGridFormat.png";
		}

		for ( var i = 0; i < columns.length; i++) {
			obj = new Object();
			for ( var key in columns[i]) {
				obj[key] = columns[i][key];
			}

			me.oriGColumns.push(obj);
		}

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
					DCITag : me.postvalue,
					uid : me.uid,
					action : 'getGFormat',
					gridid : me.gridid
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
					me.addCusFormatIcon();
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

					if (cols != null && cols.leght != 0) {
						for ( var i = 0; i < cols.length; i++) {
							cols[i]['hidden'] = true;
							tmpcols.push(cols[i]);
						}
					}
				} else {
					tmpcols = cols;
				}

				bodyGrid.reconfigure(store, tmpcols);
			} else {
				bodyGrid.reconfigure(store, cols);
			}
		});

		if (me.languageObj != null) {
			panel.items.get(0).setText(me.languageObj.save_format);
			panel.items.get(1).setText(me.languageObj.load_def_format);
		}
	},
	clearGrid : function() {
		var store = this.items.get(2).getStore();
		if (store != null) {
			store.removeAll();
		}
	},
	gridReload : function() {
		var bodypanel = this;
		var grid = bodypanel.items.get(2);
		if (grid != null) {
			grid.getStore().load();
		}
	},
	griddbclick : function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {

	},
	getGrid : function() {
		var me = this;
		return me.items.get(2);
	}
});

Ext.define('DCI.Customer.HeadPanel', {
	extend : 'Ext.panel.Panel',
	alias : 'DCI.Customer.HeadPanel',
	region : 'north',
	width : 300,
	border : 0,
	headform : null,
	postvalue : '',
	uid : '',
	layout : 'border',
	canEdit : true,
	cusMsg : [],
	keepAdd : true,
	exceptionEditAuth : function() {
		return [ "1", "" ];
	},
	exceptionDeleteAuth : function() {
		return [ "1", "" ];
	},
	afterSaveFunc : function(resultdata) {

	},
	initComponent : function() {
		if (this.headform != null) {
			var toolbar = Ext.create('DCI.Customer.ToolBarPanel', {
				bindform : this.headform,
				postvalue : this.postvalue,
				uid : this.uid,
				canEdit : this.canEdit,
				exceptionEditAuth : this.exceptionEditAuth,
				exceptionDeleteAuth : this.exceptionDeleteAuth,
				afterSaveFunc : this.afterSaveFunc,
				cusMsg : this.cusMsg,
				keepAdd : this.keepAdd
			});

			// toolbar.items.get(0).setTooltip(this.headform.languageObj.search);
			// toolbar.items.get(1).setTooltip(this.headform.languageObj.add);
			// toolbar.items.get(2).setTooltip(this.headform.languageObj.edit);
			// toolbar.items.get(3).setTooltip(this.headform.languageObj._delete);
			// toolbar.items.get(4).setTooltip(this.headform.languageObj.save);
			// toolbar.items.get(5).setTooltip(this.headform.languageObj.exit);
			toolbar.setTips(this.headform.languageObj);
			toolbar.ModeChange(PageMode.View);
			this.headform.toolpanel = toolbar;
			this.items = [ toolbar, this.headform ];
		}
		this.callParent(arguments);
	},
	setCusButtons : function(buttons) {
		if (this.items.get(0) != null) {
			this.items.get(0).setCusButtons(buttons);
		}
	},
	getCurrMode : function() {
		var me = this;

		if (me.headform != null) {
			return me.headform.getCurrMode();
		}
	}
});

Ext.define('DCI.Customer.ProgressBar', {
	extend : 'Ext.ProgressBar',
	xtype : 'dciprogressBar',
	alias : 'DCI.Customer.ProgressBar',
	tbgcolor : "#73a3e0",
	textStyle : "",
	renderTpl : [ '<tpl if="internalText">', '<div class="{baseCls}-text {baseCls}-text-back">{text}</div>', '</tpl>',
			'<div id="{id}-bar" class="{baseCls}-bar" style="background-color:{textbgcolor};width:{percentage}%">', '<tpl if="internalText">',
			'<div <tpl if="textStyle">style={textStyle}</tpl> class="{baseCls}-text">', '<div>{text}</div>', '</div>', '</tpl>', '</div>' ],

	initRenderData : function() {
		var me = this;
		return Ext.apply(me.callParent(), {
			internalText : !me.hasOwnProperty('textEl'),
			text : me.text || '&#160;',
			percentage : me.value ? me.value * 100 : 0,
			textbgcolor : me.tbgcolor,
			textStyle : me.textStyle == "" ? null : me.textStyle
		});
	}
});

Ext.define('DCI.Customer.CheckColumn', {
	extend : 'Ext.grid.column.CheckColumn',
	xtype : 'dcicheckcolumn',
	alias : 'DCI.Customer.CheckColumn',
	colid : '',
	showOnly : false,
	isSingleSelect : false,
	width : 200,
	canEditControl : function(col, rowIndex) {
		return true;
	},
	listeners : {
		beforecheckchange : function(col, rowIndex, checked, eOpts) {
			if (this.showOnly) {
				return false;
			} else {
				if (this.up('panel').up('panel').currmode == PageMode.View) {
					return false;
				} else {
					return this.canEditControl(col, rowIndex);
				}
			}
		},
		checkchange : function(col, rowIndex, checked, eOpts) {
			var store = this.up('panel').getStore();
			if (!this.showOnly) {
				if (store != null && store.getAt(rowIndex).get("moditag") != 1) {
					store.getAt(rowIndex).set("moditag", 1);
					store.getAt(rowIndex).set("moditp", "edit");
					store.getAt(rowIndex).commit();
				}
			}

			if (this.isSingleSelect) {
				if (store) {
					for ( var i = 0; i < store.getCount(); i++) {
						if (i != rowIndex) {
							store.getAt(i).set(col.dataIndex, false);

							if (store.getAt(i).get("moditag") != 1) {
								store.getAt(i).set("moditag", 1);
								store.getAt(i).set("moditp", "edit");
								store.getAt(i).commit();
							}
						}
					}
				}
			}
		}
	}
});

Ext.define('DCI.Customer.P2DisplayColumn', {
	extend : 'Ext.grid.column.Column',
	xtype : 'dcip2column',
	alias : 'DCI.Customer.P2DisplayColumn',
	colid : '',
	width : 100,
	renderer : function(value, metaData, record, rowIndex, colIndex, store) {
		this.callParent(arguments);

		var result = null;
		var dataIndex = this.headerCt.getGridColumns()[colIndex].dataIndex;
		var coltype = record.get(dataIndex + "_type");
		var ptcolor = record.get(dataIndex + "_ptcolor");
		var pbgcolor = record.get(dataIndex + "_pbgcolor");

		if (coltype == 'LIGHT') {
			result = "<img src='./../../ImageLoader.dsc?iconid=" + value + "' width=16 hight=16/>";
		} else if (coltype == 'CHECKBOX') {
			if (value) {
				result = '<img class="x-grid-checkcolumn x-grid-checkcolumn-checked" src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==">';
			} else {
				result = '<img class="x-grid-checkcolumn" src="data:image/gif;base64,R0lGODlhAQABAID/AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==">';
			}
		} else if (coltype == 'IMAGELINK') {
			result = "<img src='" + value + "' />";
		} else if (coltype == 'IMAGEMAPPING') {
			result = "<img src='./../../ImageLoader.dsc?imgpath=" + value + "'/>";
		} else if (coltype == 'PROGRESS') {
			var oriValue = value.replace(/,/g, "");
			var tmpValue = (value.replace(/,/g, "")) * 1 / 100;
			var tcolor = "#FFFFFF";
			var bcolor = "red";
			if (ptcolor && pbgcolor && ptcolor != "" && pbgcolor != "") {
				bcolor = "#" + pbgcolor;
			} else {
				if (oriValue >= 51 && oriValue < 81) {
					bcolor = "brown";
				} else if (oriValue >= 81 && oriValue < 100) {
					bcolor = "blue";
				} else if (oriValue >= 100) {
					bcolor = "green";
				} else {
					bcolor = "red";
				}
			}

			if (ptcolor && ptcolor && ptcolor != "" && ptcolor != "") {
				tcolor = "#" + ptcolor;
			}

			var b = Ext.create('DCI.Customer.ProgressBar', {
				tbgcolor : bcolor,
				textStyle : 'color:' + tcolor,
				style : {
					width : '100%'
				}
			});
			if (tmpValue > 1) {
				tmpValue = 1;
			}

			b.updateProgress(tmpValue, oriValue + "%", true);

			result = Ext.DomHelper.markup(b.getRenderTree());
		} else {
			result = value;
		}

		return result;
	}

});

Ext.define('DCI.Customer.NumberBox', {
	extend : 'Ext.form.field.Number',
	alias : 'DCI.Customer.NumberBox',
	defaultvalue : 30,
	labelAlign : 'right',
	ispk : false,
	canEdit : true,
	maxValue : 9999,
	minValue : 30,
	cusStyle : null,
	loadDefault : function() {
		this.setValue(this.defaultvalue);
	},
	setReadOnly : function(readonly) {
		if (readonly) {
			this.setFieldStyle({
				backgroundColor : '#ddd',
				backgroundImage : 'none'
			});
		} else {
			if (this.cusStyle != null && this.cusStyle != '') {
				this.setFieldStyle(this.cusStyle);
			} else {
				this.setFieldStyle({
					backgroundColor : '#FFFFFF'
				});
			}
		}
		this.callParent(arguments);
	},
	initComponent : function() {
		if (this.style != null) {
			this.cusStyle = this.style;
		}
		this.callParent(arguments);
	}
});

Ext.define('DCI.Customer.LightColumn',
		{
			extend : 'Ext.grid.column.Column',
			xtype : 'dcilightcolumn',
			alias : 'DCI.Customer.LightColumn',
			colid : '',
			width : 100,
			renderer : function(value, metaData, record, rowIndex, columnIndex, store, view) {
				var sqlid = view.ownerCt.columns[columnIndex].sqlid;
				if (sqlid == undefined)
					sqlid = "";
				var rowHeight = view.ownerCt.columns[columnIndex].rowHeight;
				if (rowHeight == undefined)
					rowHeight = "16";
				return " <img id='light" + sqlid + rowIndex + ";" + columnIndex + "' src='./../../ImageLoader.dsc?iconid=" + value + "' width=" + rowHeight + " hight=" + rowHeight
						+ "/> ";
			}
		});

Ext.define('DCI.Customer.ImgPickerColumn', {
	extend : 'Ext.grid.column.Column',
	xtype : 'dciimgpickercolumn',
	alias : 'DCI.Customer.ImgPickerColumn',
	colid : '',
	text : '',
	menuDisabled : true,
	sortable : false,
	maxWidth : 30,
	minWidth : 30,
	width : 30,
	// imgUrl : './../../images/button_icon/ToolBarSearch.png',
	renderer : function(value) {
		this.callParent(arguments);
		return "<img src='./../../images/button_icon/ToolBarSearch.png' />";
	}
});

Ext.define('DCI.Customer.ImgColumn', {
	extend : 'Ext.grid.column.Column',
	xtype : 'dciimgcolumn',
	alias : 'DCI.Customer.ImgColumn',
	align : 'center',
	colid : '',
	width : 100,
	renderer : function(value) {
		this.callParent(arguments);
		return "<img src='" + value + "' />";
	}
});

Ext.define('DCI.Customer.ImgMapColumn', {
	extend : 'Ext.grid.column.Column',
	xtype : 'dciimgmapcolumn',
	alias : 'DCI.Customer.ImgMapColumn',
	colid : '',
	width : 100,
	renderer : function(value) {
		this.callParent(arguments);
		return "<img src='./../../ImageLoader.dsc?imgpath=" + value + "'/>";
	}
});

Ext.define('DCI.Customer.DateField', {
	extend : 'Ext.form.field.Date',
	xtype : 'dcidatefield',
	alias : 'DCI.Customer.DateField',
	oriAllowBlank : false,
	defaultvalue : '',
	ispk : false,
	canEdit : true,
	cusStyle : null,
	loadDefault : function() {
		this.setValue(this.defaultvalue);
	},
	setReadOnly : function(readonly) {
		if (readonly) {
			this.allowBlank = true;
			this.setFieldStyle({
				backgroundColor : '#ddd',
				backgroundImage : 'none'
			});
		} else {
			this.allowBlank = this.oriAllowBlank;
			if (this.cusStyle != null && this.cusStyle != '') {
				this.setFieldStyle(this.cusStyle);
			} else {
				this.setFieldStyle({
					backgroundColor : '#FFFFFF'
				});
			}
		}
		this.callParent(arguments);
	},
	initComponent : function() {
		this.oriAllowBlank = this.allowBlank;
		if (this.style != null) {
			this.cusStyle = this.style;
		}
		this.callParent(arguments);
	}
});

Ext.define('DCI.Customer.ColorPicker', {
	extend : 'Ext.form.field.Trigger',
	xtype : 'dcicolorpicker',
	alias : 'DCI.Customer.ColorPicker',
	picker : null,
	defaultColor : 'FFFFFF',
	curColor : '',
	setValue : function(value) {
		var me = this;
		me.curColor = value;

		me.callParent(arguments);
		return me;
	},
	getValue : function() {
		return this.curColor;
	},
	onTriggerClick : function() {
		var me = this;
		if (!me.picker) {
			me.picker = Ext.create('Ext.picker.Color', {
				pickerField : this,
				ownerCt : this,
				renderTo : document.body,
				floating : true,
				hidden : false,
				focusOnShow : true,
				style : {
					backgroundColor : "#fff"
				},
				listeners : {
					scope : this,
					select : function(field, value, opts) {
						me.setValue(value);
						me.setFieldStyle('background:#' + value);
						me.picker.hide();
					},
					show : function(field, opts) {
						field.getEl().monitorMouseLeave(500, field.hide, field);
						me.picker.setXY([ me.getX() + me.getLabelWidth(), me.getY() + me.getHeight() ]);
					},
					move : function(comp, x, y, eOpts) {
						comp.setXY([ me.getX() + me.getLabelWidth(), me.getY() + me.getHeight() ]);
					}
				}
			});
			me.picker.alignTo(me.inputEl, 'tl-bl?');
			me.picker.show(me.inputEl);
		} else {
			if (me.picker.hidden) {
				me.picker.show();
			} else {
				me.picker.hide();
			}
		}
	},
	initComponent : function() {
		var me = this;
		if (me.curColor != null && me.curColor != '') {
			me.setFieldStyle('background:#' + me.curColor);
		} else {
			me.setFieldStyle('background:#' + me.defaultColor);
		}
		me.callParent(arguments);
	},
	setCurrColor : function(color) {
		var me = this;
		me.curColor = color;
		me.setFieldStyle('background:#' + me.curColor);
	},
	loadDefault : function() {
		var me = this;
		var color = "FFFFFF";
		if (me.defaultColor && me.defaultColor != null && me.defaultColor != '') {
			color = me.defaultColor;
		}
		if (me.picker && me.picker != null) {
			me.picker.select(color);
		}
		// me.curColor = color;
		me.setValue(color);
		me.setFieldStyle('background:#' + me.curColor);
	}
});

Ext.define('DCI.Customer.ProgressBarColumn', {
	extend : 'Ext.grid.column.Column',
	xtype : 'dciprogressbarcolumn',
	alias : 'DCI.Customer.ProgressBarColumn',
	colid : '',
	width : 100,
	renderer : function(value, metaData, record, rowIndex, colIndex, store) {
		this.callParent(arguments);
		var dataIndex = this.headerCt.getGridColumns()[colIndex].dataIndex;
		var ptcolor = record.get(dataIndex + "_ptcolor");
		var pbgcolor = record.get(dataIndex + "_pbgcolor");
		var oriValue = value.replace(/,/g, "");
		var tmpValue = (value.replace(/,/g, "")) * 1 / 100;
		var tcolor = "#FFFFFF";
		var bcolor = "red";

		if (ptcolor && pbgcolor && ptcolor != "" && pbgcolor != "") {
			bcolor = "#" + pbgcolor;
		} else {
			if (oriValue >= 51 && oriValue < 81) {
				bcolor = "brown";
			} else if (oriValue >= 81 && oriValue < 100) {
				bcolor = "blue";
			} else if (oriValue >= 100) {
				bcolor = "green";
			} else {
				bcolor = "red";
			}
		}

		if (ptcolor && ptcolor && ptcolor != "" && ptcolor != "") {
			tcolor = "#" + ptcolor;
		}

		var b = Ext.create('DCI.Customer.ProgressBar', {
			tbgcolor : bcolor,
			textStyle : 'color:' + tcolor,
			style : {
				width : '100%'
			}
		});
		if (tmpValue > 1) {
			tmpValue = 1;
		}
		b.updateProgress(tmpValue, oriValue + "%", true);

		// result = Ext.DomHelper.markup(b.getRenderTree());

		/*
		 * var oriValue = value.replace(/,/g, ""); var tmpValue =
		 * (value.replace(/,/g, "")) * 1 / 100;
		 * 
		 * var bcolor = "red";
		 * 
		 * if (oriValue >= 51 && oriValue < 81) { bcolor = "brown"; } else if
		 * (oriValue >= 81 && oriValue < 100) { bcolor = "blue"; } else if
		 * (oriValue >= 100) { bcolor = "green"; } else { bcolor = "red"; }
		 * 
		 * var b = Ext.create('Ext.ProgressBar', { cls : 'x-progress-' + bcolor,
		 * style : { width : '100%' } }); if (tmpValue > 1) { tmpValue = 1; }
		 * 
		 * b.updateProgress(tmpValue, oriValue + "%", true);
		 */

		return Ext.DomHelper.markup(b.getRenderTree());
	}

});

Ext.define('DCI.Customer.Hidden', {
	extend : 'Ext.form.field.Hidden',
	alias : 'DCI.Customer.Hidden',
	defaultvalue : '',
	triggerModi : true,
	loadDefault : function() {
		this.setValue(this.defaultvalue);
	}
});

Ext.define('DCI.Customer.CheckBox', {
	extend : 'Ext.form.field.Checkbox',
	xtype : 'dcicheckbox',
	alias : 'DCI.Customer.CheckBox',
	defaultvalue : '',
	ispk : false,
	canEdit : true,
	checked : false,
	loadDefault : function() {
		this.setValue(this.defaultvalue);
	},
	getSubmitValue : function() {
		var value = 0;
		if (this.checked) {
			value = 1;
		} else {
			value = 0;
		}

		return value;
	}
});

Ext.define('DCI.Customer.EmptyButton', {
	extend : 'Ext.button.Button',
	alias : 'DCI.Customer.EmptyButton',
	targetComp : null,
	ispk : false,
	canEdit : true
});

Ext.define('DCI.Customer.OpenWinWindow', {
	extend : 'Ext.window.Window',
	alias : 'DCI.Customer.OpenWinWindow',
	layout : 'border',
	title : 'Query',
	height : 200,
	width : 400,
	minWidth : 300,
	minHeight : 200,
	modal : true,
	plain : true,
	parentButton : null,
	showSelectAll : false,
	items : [ {
		xtype : 'panel',
		region : 'north',
		layout : 'absolute',
		height : 35,
		items : [ {
			xtype : 'combobox',
			fieldLabel : '',
			displayField : 'label',
			valueField : 'value',
			typeAhead : true,
			editable : false,
			queryMode : 'local',
			labelWidth : 1,
			matchFieldWidth : false,
			multiSelect : false,
			forceSelection : true,
			store : Ext.create('Ext.data.Store', {
				fields : [ {
					name : 'label',
					type : 'string'
				}, {
					name : 'value',
					type : 'string'
				} ],
				autoLoad : false,
				proxy : {
					type : 'memory',
					reader : {
						type : 'json'
					}
				}
			}),
			x : 5,
			y : 5
		}, {
			xtype : 'textfield',
			name : 'qvalue',
			fieldLabel : '',
			labelWidth : 1,
			width : 160,
			x : 140,
			y : 5
		}, {
			xtype : 'button',
			cls : 'search-toolbar',
			width : 30,
			height : 30,
			x : 300,
			y : 1,
			handler : function() {
				var gstore = this.up('panel').up('window').items.get(1).getStore();
				var fcol = this.up('panel').items.get(0).getValue();
				var fvalue = this.up('panel').items.get(1).getValue();

				if (gstore.getProxy().extraParams.hasOwnProperty('filter')) {
					gstore.getProxy().extraParams['filter'] = " and " + fcol + " like '%" + fvalue + "%' ";
				}
				gstore.load();
			}
		}, {
			xtype : 'dcicheckbox',
			labelWidth : 30,
			defaultvalue : 0,
			x : 335,
			y : 5,
			triggerChange : true,
			listeners : {
				change : function(comp, newValue, oldValue, eOpts) {
					var me = this;
					if (me.triggerChange) {
						if (me.up('panel').up('panel').parentButton != null) {
							me.up('panel').up('panel').parentButton.selectAllEvent(comp, newValue, oldValue, eOpts);
						}
					}
				}
			},
			setChecked : function(value) {
				var me = this;
				me.triggerChange = false;
				me.setValue(value);
				me.triggerChange = true;
			}
		} ]
	}, {
		xtype : 'grid',
		region : 'center',
		renderer : "component",
		border : 0,
		stripeRows : true,
		autoScroll : true,
		loadMask : true,
		enableTextSelection : true,
		viewConfig : {
			forceFit : false,
			autoLoad : false
		},
		columns : [],
		store : null
	} ],
	buttons : [ {
		xtype : 'button',
		text : 'OK',
		btnkey : 'btn001',
		handler : function() {
			var win = this.up('window');
			win.parentButton.setSelectedData(win.items.get(1).getStore());
			win.close();
		}
	}, {
		xtype : 'button',
		text : 'Cancel',
		btnkey : 'btn002',
		handler : function() {
			this.up('window').close();
		}
	} ],
	setInitObjects : function(records, gridStore, gridColumns) {
		var me = this;
		if (records.length > 0) {
			var combo = this.items.get(0).items.get(0);
			combo.getStore().loadData(records[0].get("cols"));
			if (combo.getStore().getCount() > 0 && typeof combo != 'undefined' && combo != null) {
				combo.setValue(combo.getStore().getAt(0).get("value"));
			}
		}
		me.items.get(1).reconfigure(gridStore, gridColumns);

		if (me.parentButton.langsObj != null) {
			me.down('button[btnkey=btn001]').setText(me.parentButton.langsObj.ok);
			me.down('button[btnkey=btn002]').setText(me.parentButton.langsObj.cancel);
			me.setTitle(me.parentButton.langsObj.toolbar_query_title);
			me.items.get(0).items.get(3).setFieldLabel(me.parentButton.langsObj.select_all);
		}
		me.items.get(0).items.get(3).setVisible(me.showSelectAll);
		if (me.showSelectAll) {
			me.items.get(0).items.get(3).setValue(me.parentButton.isAll());
		}
	}
});

Ext.define('DCI.Customer.OpenWinTrigger', {
	extend : 'Ext.button.Button',
	alias : 'DCI.Customer.OpenWinTrigger',
	targetComp : null,
	ispk : false,
	canEdit : true,
	displayCols : [],
	gridStore : null,
	filterComboRecord : null,
	actionurl : '',
	buttonID : '',
	pvalue : '',
	puid : '',
	winHeight : 200,
	winWidth : 450,
	langsObj : null,
	showSelectAll : false,
	handler : function() {
		var openwin = Ext.create('DCI.Customer.OpenWinWindow', {
			height : this.winHeight,
			width : this.winWidth,
			parentButton : this,
			showSelectAll : this.showSelectAll
		});
		openwin.setInitObjects(this.filterComboRecord, this.gridStore, this.displayCols);
		openwin.show();
	},
	setReadOnly : function(readonly) {
		this.setDisabled(readonly);
	},
	loadDefault : function() {
		if (this.targetComp != null) {
			this.targetComp.loadDefault();
		}
	},
	setInitObjs : function(record) {
		this.filterComboRecord = record;
		if (this.gridStore != null) {
			this.gridStore.load();
		}
	},
	setSelectedData : function() {

	},
	initComponent : function() {
		var me = this;
		var opInitStore = Ext.create('Ext.data.Store', {
			autoLoad : false,
			fields : [ 'cols' ],
			proxy : {
				type : 'ajax',
				url : me.actionurl,
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : 'json'
				},
				extraParams : {
					DCITag : me.pvalue,
					uid : me.puid,
					action : 'openwinQcombo',
					btnid : me.buttonID
				}
			}
		});

		opInitStore.load(function(records) {
			if (records.length > 0) {
				me.setInitObjs(records);
			}
		});
		this.callParent(arguments);
	},
	selectAllEvent : function(comp, newValue, oldValue, eOpts) {

	},
	isAll : function() {
		return 0;
	}
});

Ext.define('DCI.Customer.SqlEditor', {
	extend : 'Ext.form.TextArea',
	alias : 'DCI.Customer.SqlEditor',
	width : '100%',
	labelWidth : 75,
	msgTarget : 'side',
	autoScroll : true,
	cusStyle : null,
	/*
	 * fieldStyle : { 'fontFamily' : 'courier new', 'fontSize' : '20px' },
	 */
	defaultvalue : '',
	ispk : false,
	canEdit : true,
	codemirror : null,
	/*
	 * listeners : { afterrender : function(textarea, eOpts) { var height =
	 * textarea.getHeight();
	 * 
	 * textarea.codemirror =
	 * CodeMirror.fromTextArea(document.getElementById(textarea.inputEl.id), {
	 * mode : "text/x-sql", lineNumbers : true, readOnly : textarea.readOnly });
	 * 
	 * textarea.codemirror.setSize('100%', height); }, resize :
	 * function(textarea, width, height, oldWidth, oldHeight, eOpts) { if
	 * (textarea.getHeight() < height) {
	 * textarea.setHeight(textarea.getHeight()); } } },
	 */
	initComponent : function() {
		var me = this;
		if (me != null) {
			me.maxHeight = me.height;
		}
		if (me.style != null) {
			me.cusStyle = me.style;
		}
		this.callParent(arguments);
	},
	loadDefault : function() {
		/*
		 * if (this.codemirror != null) {
		 * this.codemirror.setValue(this.defaultvalue); }
		 */
		this.setValue(this.defaultvalue);
	}/*
		 * , setReadOnly : function(readOnly) { if (this.codemirror == null) {
		 * this.readOnly = readOnly; } else { this.codemirror.options.readOnly =
		 * readOnly; } } , setValue : function(value) { if (this.codemirror !=
		 * null) { this.codemirror.setValue(value); } }, getValue : function() {
		 * var value = null; if (this.codemirror != null) { value =
		 * this.codemirror.getValue(); } return value; }, getSubmitValue :
		 * function() { return this.getValue(); }
		 */
	,
	setReadOnly : function(readonly) {
		if (readonly) {
			this.setFieldStyle({
				backgroundColor : '#ddd',
				backgroundImage : 'none'
			});
		} else {
			if (this.cusStyle != null && this.cusStyle != '') {
				this.setFieldStyle(this.cusStyle);
			} else {
				this.setFieldStyle({
					backgroundColor : '#FFFFFF'
				});
			}
		}
		this.callParent(arguments);
	}
});

Ext.define('Ext.ux.plugins.FitToParent', {
	alias : 'plugin.fittoparent',

	extend : 'Object',

	/**
	 * @cfg {HTMLElement/Ext.Element/String} parent The element to fit the
	 *      component size to (defaults to the element the component is rendered
	 *      to).
	 */
	/**
	 * @cfg {Boolean} fitWidth If the plugin should fit the width of the
	 *      component to the parent element (default <tt>true</tt>).
	 */
	fitWidth : true,

	/**
	 * @cfg {Boolean} fitHeight If the plugin should fit the height of the
	 *      component to the parent element (default <tt>true</tt>).
	 */
	fitHeight : true,
	/**
	 * @cfg {Boolean} offsets Decreases the final size with [width, height]
	 *      (default <tt>[0, 0]</tt>).
	 */
	offsets : [ 0, 0 ],
	/**
	 * @constructor
	 * @param {HTMLElement/Ext.Element/String/Object}
	 *            config The parent element or configuration options.
	 * @ptype fittoparent
	 */
	constructor : function(config) {
		config = config || {};
		if (config.tagName || config.dom || Ext.isString(config)) {
			config = {
				parent : config
			};
		}
		Ext.apply(this, config);
	},
	init : function(c) {
		this.component = c;
		c.on('render', function(c) {
			this.parent = Ext.get(this.parent || c.getPositionEl().dom.parentNode);
			if (c.doLayout) {
				c.monitorResize = true;
				c.doLayout = Ext.Function.createInterceptor(c.doLayout, this.fitSize, this);
			}
			this.fitSize();
			Ext.EventManager.onWindowResize(this.fitSize, this);
		}, this, {
			single : true
		});
	},
	fitSize : function() {
		var pos = this.component.getPosition(true), size = this.parent.getViewSize();
		this.component.setSize(this.fitWidth ? size.width - pos[0] - this.offsets[0] : undefined, this.fitHeight ? size.height - pos[1] - this.offsets[1] : undefined);
	}
});
