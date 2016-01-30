var viewHeight = 0;

function showview(forwardvalue, postvalue) {
	var timergap = 180;
	refalshTime = Ext.create('Ext.toolbar.TextItem', {
		text : getCurrentTime()
	});
	Ext.define('TreeModel', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			type : 'string'
		}, {
			name : 'text',
			type : 'string'
		}, {
			name : 'level',
			type : 'int'
		}, {
			name : 'leaf',
			type : 'boolean'
		}, {
			name : 'url',
			type : 'string'
		}, {
			name : 'cls',
			type : 'string'
		}, {
			name : 'parent',
			type : 'string'
		}, {
			name : 'lkbcolor',
			type : 'string'
		}, {
			name : 'imgurl',
			type : 'string'
		} ]
	});

	var combodata = Ext.create('Ext.data.Store', {
		fields : [ 'display', 'value' ],
		data : [ {
			"display" : "5",
			"value" : "5"
		}, {
			"display" : "10",
			"value" : "10"
		}, {
			"display" : "30",
			"value" : "30"
		}, {
			"display" : "60",
			"value" : "60"
		}, {
			"display" : "120",
			"value" : "120"
		}, {
			"display" : "180",
			"value" : "180"
		}, {
			"display" : "300",
			"value" : "300"
		}, {
			"display" : "600",
			"value" : "600"
		} ]
	});

	var btnTimerStart = Ext.create('Ext.Button', {
		// text : 'start',
		enableToggle : true,
		iconCls : 'startbutton',
		tooltip : 'Auto Refresh',
		handler : function() {
			if (mainPanel.items != null && mainPanel.items.length != 0) {
				timerControl(task, this, postvalue, timergap);
			} else {
				this.toggle(false);
			}
		}
	});

	var btnRefresh = Ext.create('Ext.Button', {
		// text : 'stop',
		iconCls : 'refreshbutton',
		tooltip : 'Refresh',
		handler : function() {
			refreshTab(postvalue);
		}
	});

	timergapcombo = Ext.create('Ext.form.field.ComboBox', {
		store : combodata,
		queryMode : 'local',
		displayField : 'display',
		valueField : 'value',
		width : 60,
		typeAhead : true,
		editable : false,
		listeners : {
			change : function(combo, newValue, oldValue, eOpts) {
				if (timergap != newValue) {
					if (timerisrunning) {
						alert("please stop timmer");
						combo.setValue(oldValue);
					} else {
						timergap = newValue;
					}
				}
			}
		}
	});

	timergapcombo.setValue("180");

	var TreeStore = Ext.create('Ext.data.TreeStore', {
		model : 'TreeModel',
		proxy : {
			type : 'ajax',
			url : 'index.do',
			actionMethods : {
				read : 'POST'
			},
			reader : {
				type : 'json',
				root : 'datas'
			},
			extraParams : {
				DCITag : postvalue
			}
		},

		root : {
			text : 'LKB Functions',
			id : 'FuncTreeRoot',
			expanded : true
		}
	});

	funcTree = Ext.create('Ext.tree.Panel', {
		rootVisible : false,
		width : 150,
		height : 150,
		viewConfig : {
			loadMask : true
		},
		useArrows : true,
		store : TreeStore,
		listeners : {
			itemclick : function(view, rec, item, index, eventObj) {
				if (rec.data.leaf == true) {
					if (timerisrunning) {
						timerControl(task, btnTimerStart, postvalue, timergap);
					}
					indexstatusbar.showBusy();
					// alert(rec.data.lkbcolor);
					var tabId = "tab" + rec.data.id;
					var tabExists = false;
					// alert(mainPanel.items.length);
					for ( var i = 0; i < mainPanel.items.length && !tabExists; i++) {
						if (mainPanel.items.getAt(i).id == tabId) {
							tabExists = true;
						}
					}

					if (tabExists) {
						mainPanel.setActiveTab(tabId);
						indexstatusbar.clearStatus({
							useDefaults : true
						});
					} else {
						var funcurl = "";
						if (tabId.indexOf("EKBQ") != -1) {
							funcurl = "/LKB/FuncViews/Query/" + tabId.substring(3) + ".jsp";
						} else if (tabId.indexOf("SCH") != -1) {
							funcurl = "/LKB/FuncViews/WS/" + tabId.substring(3) + ".jsp";
						} else {
							funcurl = "/LKB/FuncViews/LKB01.jsp";
						}

						functab = Ext.create('Ext.Panel', {
							id : tabId,
							title : rec.data.text,
							closable : true,
							loader : {
								url : funcurl,
								scripts : true,
								params : {
									SFTTag : forwardvalue,
									funcid : rec.data.id,
									lkbcolor : rec.data.lkbcolor,
									imgurl : rec.data.imgurl
								},
								listeners : {
									load : function(cmp, response, options, eOpts) {
										indexstatusbar.clearStatus({
											useDefaults : true
										});
									}
								}
							},
							listeners : {
								added : function(tab) {
									tab.loader.load();
								}
							}
						});

						/*
						 * if (tabId.indexOf("EKBQ") == -1) { functab = Ext
						 * .create( 'Ext.Panel', { id : tabId, title :
						 * rec.data.text, closable : true, loader : { url :
						 * "/LKB/FuncViews/LKB01.jsp", // url : //
						 * "/LKB/test.jsp", scripts : true, params : { SFTTag :
						 * forwardvalue, funcid : rec.data.id, lkbcolor :
						 * rec.data.lkbcolor, imgurl : rec.data.imgurl },
						 * listeners : { load : function( cmp, response,
						 * options, eOpts) { indexstatusbar .clearStatus({
						 * useDefaults : true }); } } }, listeners : { added :
						 * function( tab) { tab.loader .load(); } } }); }else{
						 * functab = Ext.create('Ext.Panel', { id : tabId, title :
						 * rec.data.text, closable : true, loader : { url :
						 * "/LKB/FuncViews/Query/"+tabId.substring(3)+".jsp", //
						 * url : // "/LKB/test.jsp", scripts : true, params : {
						 * SFTTag : forwardvalue, funcid : rec.data.id, lkbcolor :
						 * rec.data.lkbcolor, imgurl : rec.data.imgurl },
						 * listeners : { load : function( cmp, response,
						 * options, eOpts) { indexstatusbar.clearStatus({
						 * useDefaults : true }); } } }, listeners : { added :
						 * function(tab) { tab.loader.load(); } } }); }
						 */
						mainPanel.add(functab);
						functab.show();
						mainPanel.setActiveTab(tabId);
					}
				}
			}
		}
	});

	indexstatusbar = Ext.create('Ext.ux.StatusBar', {
		id : 'index-statusbar',
		defaultText : 'Ready',
		defaultIconCls : 'x-status-valid',
		items : [ '-', btnRefresh, '-', btnTimerStart, timergapcombo, '-', "Last Update Time :", refalshTime ]
	});

	mainPanel = Ext.create('Ext.tab.Panel', {
		region : 'center',
		xtype : 'tabpanel',
		deferredRender : false,
		bbar : indexstatusbar,
		listeners : {
			tabchange : function(tabPanel, newCard, oldCard, eOpts) {
				if (timerisrunning) {
					timerControl(task, btnTimerStart, postvalue, timergap);
				}
				// alert(tabPanel.getActiveTab().getId());
				if (tabPanel.getActiveTab().getId().indexOf("EKBQ") == -1) {
					indexstatusbar.show();
				} else {
					indexstatusbar.hide();
				}
				indexstatusbar.doLayout();
			},
			resize : function(tabPanel, width, height, oldWidth, oldHeight, eOpts) {
				if (tabPanel.getActiveTab() != null) {
					var tmp = Ext.getCmp(tabPanel.getActiveTab().getId().substring(3) + "main");
					if (typeof tmp != 'undefined' && tmp != null) {
						tmp.setWidth(width);
						tmp.setHeight(height - tabPanel.getTabBar().getHeight());
					} else {
						tmp = Ext.getCmp("KBP" + tabPanel.getActiveTab().getId().substring(3));
						if (typeof tmp != 'undefined' && tmp != null) {
							tmp.setWidth(width);
							tmp.setHeight(height - tabPanel.getTabBar().getHeight() - indexstatusbar.getHeight());
						}
					}
				}
			}
		}

	});

	Ext.create('Ext.Viewport', {
		id : 'DCIIndex',
		layout : 'border',
		items : [ {
			region : 'west',
			stateId : 'funcPanel',
			id : 'west-panel',
			title : '功能列表',
			split : true,
			width : 200,
			minWidth : 175,
			maxWidth : 300,
			collapsible : true,
			animCollapse : true,
			layout : 'fit',
			items : [ funcTree ]
		}, mainPanel ]
	});

	viewHeight = mainPanel.getHeight() - mainPanel.getTabBar().getHeight();

	var runner = new Ext.util.TaskRunner();

	var task = runner.newTask({
		run : function() {
			refreshTab(postvalue);
		}
	});
};

function refreshTab(postvalue) {
	if (mainPanel.items != null && mainPanel.items.length != 0) {
		var acttab = mainPanel.getActiveTab();

		if (acttab == null) {
			alert("no active tab");
		} else {
			var actfuncid = acttab.id.substring(3);

			Ext.define('DCI.DCIBLKModel1', {
				extend : 'Ext.data.Model',
				fields : [ {
					name : 'id',
					type : 'string',
					mapping : 'id'
				}, {
					name : 'pattern',
					type : 'int'
				}, {
					name : 'bodyStyle',
					type : 'string'
				}, {
					name : 'html',
					type : 'string'
				}, {
					name : 'height',
					type : 'int'
				} ]
			});

			var dciblkstore = new Ext.data.Store({
				id : 'DCIBLKStore',
				requires : 'DCI.DCIBLKModel1',
				model : 'DCI.DCIBLKModel1',
				proxy : {
					type : 'ajax',
					url : 'showlkb.do',
					actionMethods : {
						read : 'POST'
					},
					reader : {
						type : 'json'
					},
					extraParams : {
						action : 'refresh',
						DCITag : postvalue,
						funcid : actfuncid
					}
				}
			});

			actfuncid = "KBP" + actfuncid;

			dciblkstore.load(function(records) {
				if (records != null && records.length > 0) {
					var p = null;
					// var p2 = null;
					for ( var i = 0; i < records.length; i++) {
						p = Ext.getCmp(records[i].get('id'));
						if (records[i].get('pattern') == 1) {
							p.body.update(records[i].get('html'));
							p.setBodyStyle(records[i].get('bodyStyle'));
						} else if (records[i].get('pattern') == 3 || records[i].get('pattern') == 4) {
							p.body.update(records[i].get('html'));
						}
					}
				}
			});

			/*
			 * Ext.getCmp("KBPKB000001").loader.load({ renderer : "component"
			 * });
			 */
			Ext.fly(refalshTime.getEl()).update(getCurrentTime());
		}
	}
}

function timerControl(task, timerbtn, postvalue, timergap) {
	if (timerisrunning) {
		task.stop();
		timerisrunning = false;
		// this.setText('start');
		timerbtn.setIconCls('startbutton');
		indexstatusbar.setStatus({
			text : 'Ready',
			iconCls : 'x-status-valid'
		});
		timerbtn.toggle(false);
	} else {
		// this.setText('stop');
		timerbtn.setIconCls('pausebutton');
		indexstatusbar.setStatus({
			text : '',
			iconCls : 'timerrunning'
		});
		timerbtn.toggle(true);
		timerisrunning = true;
		refreshTab(postvalue);
		task.start(timergap * 1000);
	}
}

function getCurrentTime() {
	var d = new Date();
	var h = d.getHours();
	var m = d.getMinutes();
	var s = d.getSeconds();
	if (h < 10) {
		h = "0" + h.toString();
	}
	if (m < 10) {
		m = "0" + m.toString();
	}
	if (s < 10) {
		s = "0" + s.toString();
	}
	return h + ":" + m + ":" + s;
}

function getCurrentDateTime() {
	var d = new Date();
	var y = d.getFullYear();
	var mm = d.getMonth() + 1;
	var dd = d.getDate();
	var h = d.getHours();
	var m = d.getMinutes();
	var s = d.getSeconds();

	if (mm < 10) {
		mm = "0" + mm.toString();
	}

	if (dd < 10) {
		dd = "0" + dd.toString();
	}

	if (h < 10) {
		h = "0" + h.toString();
	}
	if (m < 10) {
		m = "0" + m.toString();
	}
	if (s < 10) {
		s = "0" + s.toString();
	}
	return y + "/" + mm + "/" + dd + " " + h + ":" + m + ":" + s;
}

function loadLKB(postvalue, funcid, imgurl, lkbcolor) {

	var panel = Ext.create("Ext.panel.Panel", {
		renderTo : funcid,
		id : "KBP" + funcid,
		layout : 'absolute',
		autoScroll : true,
		height : mainPanel.getHeight() - mainPanel.getTabBar().getHeight() - indexstatusbar.getHeight(),
		border : false,
		bodyBorder : false,
		bodyStyle : 'background : ' + lkbcolor + ';',
		loader : {
			url : "showlkb.do",
			// url : "test.json",
			params : {
				DCITag : postvalue,
				funcid : funcid
			}
		/*
		 * , listeners : { load : function(cmp, response, options, eOpts) { //
		 * alert(panel.data); // setTip(response); } }
		 */
		},
		renderer : function(value, metaData, record, colIndex, store, view) {
			// alert(value);
		}
	});

	if (imgurl != null && imgurl != '') {
		var img = Ext.create('Ext.Img', {
			src : imgurl
		});
		panel.add(img);
	}
	panel.loader.load({
		renderer : "component"
	});
}
