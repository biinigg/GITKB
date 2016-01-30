function allNodeLoaded(tree, node) {
	var loaded = true;
	var cnodes = null;
	// console.log(tree);
	if (node == null) {
		node = tree.getRootNode();
	}

	if (node.hasChildNodes()) {
		cnodes = node.childNodes;
		for ( var i = 0; i < cnodes.length; i++) {
			loaded = allNodeLoaded(tree, cnodes[i]);
			if (!loaded) {
				break;
			}
		}
	} else {
		if (node.isLeaf()) {
			loaded = true;
		} else {
			loaded = node.isLoaded();
		}
	}
	return loaded;
}
function showIndexPage(postvalue, forwardvalue, langs, uid) {
	var sysFolder = [ "TreeModel-Configs", "TreeModel-LKB", "TreeModel-ET_FOLDER", "TreeModel-WF_FOLDER", "TreeModel-SM_FOLDER", "TreeModel-EF_FOLDER", "TreeModel-WPP",
			"TreeModel-TT_FOLDER" ];
	var runner = new Ext.util.TaskRunner();
	var currTabID = null;
	var pagetask = runner.newTask({
		timegap : 900,
		run : function() {
			try {
				var tmp = null;
				var nextTab = false;
				var done = false;
				if (mainPanel != null) {
					for ( var i = 0; i < mainPanel.items.length; i++) {
						if (mainPanel.items.get(i).tabPackage == 'EKB') {
							if (currTabID == null) {
								currTabID = mainPanel.items.get(i).id;
								tmp = Ext.getCmp(currTabID.substring(3) + "Main");
								tmp.globalTimerEvent(-1, this.timegap);
								mainPanel.setActiveTab(currTabID);
								done = true;
								break;
							} else {
								if (currTabID == mainPanel.items.get(i).id || nextTab) {
									if (nextTab) {
										currTabID = mainPanel.items.get(i).id;
										tmp = Ext.getCmp(currTabID.substring(3) + "Main");
										tmp.globalTimerEvent(-1, this.timegap);
										mainPanel.setActiveTab(currTabID);
										done = true;
										break;
									} else {
										tmp = Ext.getCmp(currTabID.substring(3) + "Main");
										if (tmp.switchToNextTab()) {
											nextTab = true;
											continue;
										} else {
											tmp.globalTimerEvent(0, this.timegap);
											mainPanel.setActiveTab(currTabID);
											done = true;
											break;
										}
									}
								}
							}
						}
					}

					if (!done) {
						for ( var i = 0; i < mainPanel.items.length; i++) {
							if (mainPanel.items.get(i).tabPackage == 'EKB') {
								currTabID = mainPanel.items.get(i).id;
								tmp = Ext.getCmp(currTabID.substring(3) + "Main");
								tmp.globalTimerEvent(-1, this.timegap);
								mainPanel.setActiveTab(currTabID);
								break;
							}
						}
					}
				}
			} catch (e) {
				if (window.console) {
					console.log(e.message);
				}
			}
		},
		setTimeGap : function(timegap) {
			if (timegap == null || timegap == "" || timegap < 1) {
				timegap = 600;
			}
			this.timegap = timegap;
		},
		getTimeGap : function() {
			return this.timegap;
		},
		executeTask : function() {
			// console.log(this.timegap);
			var task = this;
			/*
			 * Ext.Ajax.request({ method : 'POST', url :
			 * './../../Funcs/Main/Index.dsc', params : { DCITag : postvalue,
			 * action : 'pageTaskGap' }, success : function(a) { if
			 * (a.responseText.indexOf("@dcifiltererrtag@$") != -1) { var result =
			 * a.responseText.split('$'); if (result.length >= 2) { var
			 * resultdata = Ext.JSON.decode(result[1]);
			 * Ext.Msg.alert(langs.errmsg, resultdata.msg, function() { iserror =
			 * true; window.location = resultdata.result; }); } } else { var
			 * result = Ext.JSON.decode(a.responseText);
			 * task.setTimeGap(result.result[0].gap);
			 */
			task.start(task.timegap * 1000);
			if (northEastPanel != null) {
				northEastPanel.changeIcon(true);
			}
			/*
			 * } }, failure : function(f, a) {
			 * Ext.MessageBox.alert(langs.errmsg, langs.get_task_gap_fail); }
			 * });
			 */

		},
		stopTask : function() {
			if (mainPanel.getActiveTab() != null) {
				if (mainPanel.getActiveTab().tabPackage == 'EKB') {
					var tmp = Ext.getCmp(mainPanel.getActiveTab().id.substring(3) + "Main");
					if (tmp) {
						tmp.globalTimerStop();
					}
				}
			}

			this.stop();
			if (northEastPanel != null) {
				northEastPanel.changeIcon(false);
			}
			currTabID = null;
		}
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
			name : 'parent',
			type : 'string'
		}, {
			name : 'lang_key',
			type : 'string'
		}, {
			name : 'func_package',
			type : 'string'
		}, {
			name : 'can_edit',
			type : 'string'
		}, {
			name : 'init_load_expanded',
			type : 'string'
		} ]
	});

	var initQueryStore = Ext.create('Ext.data.Store', {
		autoLoad : false,
		checkSession : false,
		fields : [ "dblistvalue", "userInfo", "pageTimerGap", "hasfavor", "sysMarquee", "hascus" ],
		proxy : {
			type : 'ajax',
			url : './../../Funcs/Main/Index.dsc',
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

	var dbliststore = new Ext.data.Store({
		fields : [ 'label', 'value' ],
		autoLoad : false,
		proxy : {
			type : 'memory',
			reader : {
				type : 'json'
			}
		}
	});

	var userInfoStore = new Ext.data.Store({
		fields : [ 'uid', 'uname', 'gname', 'ltime' ],
		autoLoad : false,
		proxy : {
			type : 'memory',
			reader : {
				type : 'json'
			}
		}
	});

	var dblist = Ext.create('Ext.form.field.ComboBox', {
		store : dbliststore,
		queryMode : 'local',
		displayField : 'label',
		valueField : 'value',
		width : 125,
		x : 62,
		y : 5,
		typeAhead : true,
		editable : false,
		listeners : {
			change : function(combo, newValue, oldValue, eOpts) {
				if (typeof TreeStore !== 'undefined' && TreeStore != null) {
					TreeStore.getRootNode().removeAll();
					TreeStore.getProxy().extraParams['connid'] = newValue;
					TreeStore.load();
				}
				if (typeof FavorStore !== 'undefined' && FavorStore != null) {
					FavorStore.getRootNode().removeAll();
					FavorStore.getProxy().extraParams['connid'] = newValue;
					FavorStore.load();
				}

				Ext.Ajax.request({
					method : 'POST',
					url : './../../Funcs/Main/Index.dsc',
					params : {
						DCITag : postvalue,
						action : 'hasCusTree',
						uid : uid,
						connid : newValue
					},
					success : function(a) {
						if (a.responseText.indexOf("@dcifiltererrtag@$") != -1) {
							var result = a.responseText.split('$');
							if (result.length >= 2) {
								var resultdata = Ext.JSON.decode(result[1]);
								Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
									iserror = true;
									window.location = resultdata.result;
								});
							}
						} else {
							var result = Ext.JSON.decode(a.responseText);

							if (result.result[0].hasdefcus == 'true') {
								funcTree.setCusIcon();
							} else {
								funcTree.rmCusIcon();
							}
							if (result.result[0].hasfavcus == 'true') {
								favorTree.setCusIcon();
							} else {
								favorTree.rmCusIcon();
							}
						}
					},
					failure : function(f, a) {
						// Ext.MessageBox.alert(langs.errmsg,
						// langs.system_error);
					}
				});

			}
		}
	});

	var TreeStore = Ext.create('Ext.data.TreeStore', {
		model : 'TreeModel',
		autoLoad : false,
		proxy : {
			type : 'ajax',
			url : './../../Funcs/Main/Index.dsc',
			actionMethods : {
				read : 'POST'
			},
			reader : {
				type : 'json',
				root : 'datas'
			},
			extraParams : {
				DCITag : postvalue,
				action : 'tree',
				uid : uid,
				connid : ''
			}
		},
		root : {
			text : langs.func_tree_root,
			id : 'FuncTreeRoot'
		}
	});

	var funcTree = Ext.create('Ext.tree.Panel', {
		rootVisible : true,
		viewConfig : {
			loadMask : true,
			plugins : {
				ptype : 'treeviewdragdrop',
				ddGroup : 'ddgroup',
				// appendOnly : true,
				sortOnDrop : true,
				containerScroll : true
			}
		},
		useArrows : true,
		store : TreeStore,
		tbar : [ {
			xtype : 'button',
			iconCls : 'savebutton_tree',
			tooltip : langs.save,
			handler : function() {

				buildTreeData(funcTree.getRootNode(), true);
				if (arr != null && arr != '') {
					Ext.Ajax.request({
						method : 'POST',
						url : './../../Funcs/Main/Index.dsc',
						params : {
							DCITag : postvalue,
							action : 'save',
							uid : uid,
							connid : dblist.getValue(),
							treeArr : arr
						},
						success : function(a) {
							if (a.responseText.indexOf("@dcifiltererrtag@$") != -1) {
								var result = a.responseText.split('$');
								if (result.length >= 2) {
									var resultdata = Ext.JSON.decode(result[1]);
									Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
										iserror = true;
										window.location = resultdata.result;
									});
								}
							} else {
								var result = Ext.JSON.decode(a.responseText);
								if (result.success[0].result) {
									funcTree.setCusIcon();
									Ext.MessageBox.alert(langs.save_result_title, langs.save_success);
								} else {
									Ext.MessageBox.alert(langs.save_result_title, langs.save_fail);
								}
							}
						},
						failure : function(f, a) {
							Ext.MessageBox.alert(langs.errmsg, langs.system_error);
						}
					});
				}
			}
		}, {
			xtype : 'button',
			iconCls : 'refreshbutton_tree',
			tooltip : langs.refersh,
			handler : function() {
				var dbvalue = dblist.getValue();
				if (typeof TreeStore !== 'undefined' && TreeStore != null) {
					var loaded = allNodeLoaded(funcTree, null);
					if (!TreeStore.isLoading() && loaded) {
						TreeStore.getRootNode().removeAll();
						TreeStore.getProxy().extraParams['connid'] = dbvalue;
						TreeStore.load();
					}
				}
			}
		}, {
			xtype : 'button',
			iconCls : 'addfolderbutton',
			tooltip : langs.add,
			handler : function() {
				if (funcTree.getSelectionModel().hasSelection()) {
					var selectedNode = funcTree.getSelectionModel().getSelection();
					var newnode = {
						id : (new Date).getTime(),
						leaf : false
					};

					if (selectedNode[0].data.leaf) {
						TreeStore.getNodeById(selectedNode[0].data.parent).appendChild(newnode);
					} else {
						selectedNode[0].appendChild(newnode);
					}
					TreeStore.getNodeById(newnode.id).data.lang_key = "lang" + newnode.id;

					Ext.MessageBox.show({
						title : langs.add_folder_title,
						msg : langs.node_lang_panel,
						width : 300,
						buttons : Ext.MessageBox.OKCANCEL,
						multiline : true,
						fn : function(btn, text) {
							if (btn == "ok") {
								var node = TreeStore.getNodeById(newnode.id);
								node.set("text", text);

								buildTreeData(funcTree.getRootNode(), true);
								Ext.Ajax.request({
									method : 'POST',
									url : './../../Funcs/Main/Index.dsc',
									params : {
										DCITag : postvalue,
										action : 'savelang',
										uid : uid,
										connid : dblist.getValue(),
										langkey : node.data.lang_key,
										langvalue : text,
										treeArr : arr
									},
									success : function(a) {
										if (a.responseText.indexOf("@dcifiltererrtag@$") != -1) {
											var result = a.responseText.split('$');
											if (result.length >= 2) {
												var resultdata = Ext.JSON.decode(result[1]);
												Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
													iserror = true;
													window.location = resultdata.result;
												});
											}
										} else {
											var result = Ext.JSON.decode(a.responseText);
											if (result.success[0].result) {
												Ext.MessageBox.alert(langs.add_result_title, langs.add_success);
											} else {
												Ext.MessageBox.alert(langs.add_result_title, langs.add_fail);
											}
										}
									},
									failure : function(f, a) {
										Ext.MessageBox.alert(langs.errmsg, langs.system_error);
									}
								});
							} else {
								var node = TreeStore.getNodeById(newnode.id);
								node.remove();
							}
						}
					});

				} else {
					Ext.MessageBox.alert(langs.errmsg, langs.no_f_node_selected);
				}
			}
		}, {
			xtype : 'button',
			iconCls : 'deletebutton_tree',
			tooltip : langs._delete,
			handler : function() {
				if (funcTree.getSelectionModel().hasSelection()) {
					Ext.MessageBox.confirm(langs.delete_confirm_title, langs.delete_confirm_msg, function(btn) {
						if (btn == 'yes') {
							var node = funcTree.getSelectionModel().getSelection()[0];
							if (node.hasChildNodes()) {
								Ext.MessageBox.alert(langs.errmsg, langs.remove_node_before_delete);
							} else {
								node.remove();

								buildTreeData(funcTree.getRootNode(), true);

								Ext.Ajax.request({
									method : 'POST',
									url : './../../Funcs/Main/Index.dsc',
									params : {
										DCITag : postvalue,
										action : 'save',
										uid : uid,
										connid : dblist.getValue(),
										treeArr : arr
									},
									success : function(a) {
										if (a.responseText.indexOf("@dcifiltererrtag@$") != -1) {
											var result = a.responseText.split('$');
											if (result.length >= 2) {
												var resultdata = Ext.JSON.decode(result[1]);
												Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
													iserror = true;
													window.location = resultdata.result;
												});
											}
										} else {
											var result = Ext.JSON.decode(a.responseText);
											if (result.success[0].result) {
												funcTree.setCusIcon();
												Ext.MessageBox.alert(langs.delete_result_title, langs.delete_success);
											} else {
												Ext.MessageBox.alert(langs.delete_result_title, langs.delete_fail);
											}
										}
									},
									failure : function(f, a) {
										Ext.MessageBox.alert(langs.errmsg, langs.system_error);
									}
								});
							}
						}
					});
				}
			}
		}, {
			xtype : 'button',
			iconCls : 'clearbutton_tree',
			tooltip : langs.clear_user_folder_title,
			handler : function() {
				Ext.MessageBox.show({
					title : langs.clear_user_folder_title,
					msg : langs.clear_user_folder,// langs.nodelangpanel,
					width : 300,
					buttons : Ext.MessageBox.OKCANCEL,
					fn : function(btn, text) {
						if (btn == "ok") {
							Ext.Ajax.request({
								method : 'POST',
								url : './../../Funcs/Main/Index.dsc',
								params : {
									DCITag : postvalue,
									action : 'clean',
									uid : uid,
									connid : dblist.getValue()
								},
								success : function(a) {
									if (a.responseText.indexOf("@dcifiltererrtag@$") != -1) {
										var result = a.responseText.split('$');
										if (result.length >= 2) {
											var resultdata = Ext.JSON.decode(result[1]);
											Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
												iserror = true;
												window.location = resultdata.result;
											});
										}
									} else {
										if (typeof TreeStore !== 'undefined' && TreeStore != null) {
											TreeStore.getRootNode().removeAll();
											TreeStore.getProxy().extraParams['connid'] = dblist.getValue();
											TreeStore.load();
										}
										var result = Ext.JSON.decode(a.responseText);
										if (result.success[0].result) {
											funcTree.rmCusIcon();
											Ext.MessageBox.alert(langs.clear_result_title, langs.clear_success);
										} else {
											Ext.MessageBox.alert(langs.clear_result_title, langs.clear_fail);
										}
									}
								},
								failure : function(f, a) {
									Ext.MessageBox.alert(langs.errmsg, langs.system_error);
								}
							});
						}
					}
				});
			}
		}, {
			xtype : 'button',
			iconCls : 'collapseAllbutton_tree',
			tooltip : langs.collapse,
			handler : function() {
				funcTree.collapseAll();
			}
		}, {
			xtype : 'button',
			iconCls : 'expandAllbutton_tree',
			tooltip : langs.expand,
			handler : function() {
				funcTree.expandAll();
			}
		}, {
			xtype : 'button',
			iconCls : 'addfavoritebutton',
			tooltip : langs.add_favorite,
			handler : function() {
				if (funcTree.getSelectionModel().hasSelection()) {
					if (FavorStore != null) {

						var selectedNode = funcTree.getSelectionModel().getSelection();
						var tmpnode = FavorStore.getNodeById(selectedNode[0].data.id)
						if (tmpnode == null) {
							if (selectedNode[0].data.leaf) {
								var newnode = {
									id : selectedNode[0].data.id,
									leaf : selectedNode[0].data.leaf,
									parent : null,
									lang_key : selectedNode[0].data.lang_key,
									text : selectedNode[0].data.text,
									url : selectedNode[0].data.url,
									func_package : selectedNode[0].data.func_package
								};

								FavorStore.getRootNode().appendChild(newnode);
							} else {
								Ext.MessageBox.alert(langs.errmsg, langs.not_program);
							}

							var idx = 0;

							if (FavorStore.getRootNode() != null && FavorStore.getRootNode().childNodes != null) {
								idx = FavorStore.getRootNode().childNodes.length;
							}

							Ext.Ajax.request({
								method : 'POST',
								url : './../../Funcs/Main/Index.dsc',
								params : {
									DCITag : postvalue,
									action : 'addfavor',
									uid : uid,
									connid : dblist.getValue(),
									treeArr : selectedNode[0].data.id + ',,' + selectedNode[0].data.lang_key + ',' + idx + ',' + selectedNode[0].data.leaf
								}
							});

							favorTree.setCusIcon();
						} else {
							Ext.MessageBox.alert(langs.errmsg, langs.favor_exist);
						}
					}
				}
			}
		} ],
		listeners : {
			itemclick : function(view, rec, item, index, eventObj, eOpts) {
				if (rec.data.leaf) {
					// mainPanel.addKanBanTab(rec.data.id, dblist.getValue(),
					// rec.data, false);
					if (rec.data.id == 'TreeLogout') {
						Ext.MessageBox.confirm(langs.confirm_title, langs.logout_confirm, function(btn) {
							if (btn == 'yes') {
								islogout = true;
								logout(postvalue);
							}
						});
					} else {
						mainPanel.beforeAddCheck(rec.data.id, dblist.getValue(), rec.data, false);
					}
				}
			},
			itemdblclick : function(view, rec, item, index, eventObj, eOpts) {
				/*
				 * if (rec.data.leaf) { // mainPanel.addKanBanTab(rec.data.id,
				 * dblist.getValue(), // rec.data, false); if (rec.data.id ==
				 * 'TreeLogout') { Ext.MessageBox.confirm(langs.confirm_title,
				 * langs.logout_confirm, function(btn) { if (btn == 'yes') {
				 * islogout = true; logout(postvalue); } }); } else {
				 * mainPanel.beforeAddCheck(rec.data.id, dblist.getValue(),
				 * rec.data, false); } }
				 */
			},
			afteritemexpand : function(node, index, item, eOpts) {
				if (!node.isRoot()) {
					// console.log(sysFolder.indexOf(node.id));
					// if (sysFolder.indexOf(node.id) != -1) {
					if (existInArray(sysFolder, node.id)) {
						if (!node.hasChildNodes()) {
							var pnode = node.parentNode;
							if (pnode != null) {
								pnode.removeChild(node);
							}
						}
					}
				}
				/*
				 * if (node.isRoot()) { node.data.init_load_expanded = "1"; }
				 * else { if (node.data.init_load_expanded == null ||
				 * node.data.init_load_expanded == "0") { if
				 * (node.hasChildNodes()) { node.collapse();
				 * node.data.init_load_expanded = "1"; } else { var pnode =
				 * node.parentNode; if (pnode != null) {
				 * pnode.removeChild(node); } } } }
				 */
			},
			load : function(store, node, records, successful, eOpts) {
				var me = this;
				if (node.isRoot()) {
					try {
						me.expandAll();
					} catch (e) {
						if (window.console) {
							console.log(e.message);
						}
					}
				}
			}
		},
		setCusIcon : function() {
			var me = this;
			var ditems = me.getDockedItems('toolbar[dock="top"]');
			var items = ditems[0];
			var btn = items.items.get(4);
			if (btn != null) {
				btn.getEl().setStyle({
					borderWidth : "2px 2px 2px 2px",
					borderColor : 'red',
					boderStyle : 'solid'
				});
			}
		},
		rmCusIcon : function() {
			var me = this;
			var ditems = me.getDockedItems('toolbar[dock="top"]');
			var items = ditems[0];
			var btn = items.items.get(4);
			if (btn != null) {
				btn.getEl().setStyle({
					borderWidth : "1px 1px 1px 1px",
					borderColor : '',
					boderStyle : ''
				});
			}
		}
	});

	var FavorStore = Ext.create('Ext.data.TreeStore', {
		model : 'TreeModel',
		autoLoad : false,
		proxy : {
			type : 'ajax',
			url : './../../Funcs/Main/Index.dsc',
			actionMethods : {
				read : 'POST'
			},
			reader : {
				type : 'json',
				root : 'datas'
			},
			extraParams : {
				DCITag : postvalue,
				action : 'favor',
				uid : uid,
				connid : ''
			}
		},
		root : {
			text : langs.func_tree_root,
			id : 'FuncTreeRoot'
		}
	});

	var favorTree = Ext.create('Ext.tree.Panel', {
		rootVisible : true,
		viewConfig : {
			loadMask : true,
			plugins : {
				ptype : 'treeviewdragdrop',
				ddGroup : 'ddgroup',
				// appendOnly : true,
				sortOnDrop : true,
				containerScroll : true
			}
		},
		useArrows : true,
		store : FavorStore,
		tbar : [ {
			xtype : 'button',
			iconCls : 'savebutton_tree',
			tooltip : langs.save,
			handler : function() {

				buildTreeData(favorTree.getRootNode(), true);
				if (arr != null && arr != '') {
					Ext.Ajax.request({
						method : 'POST',
						url : './../../Funcs/Main/Index.dsc',
						params : {
							DCITag : postvalue,
							action : 'savefavor',
							uid : uid,
							connid : dblist.getValue(),
							treeArr : arr
						},
						success : function(a) {
							if (a.responseText.indexOf("@dcifiltererrtag@$") != -1) {
								var result = a.responseText.split('$');
								if (result.length >= 2) {
									var resultdata = Ext.JSON.decode(result[1]);
									Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
										iserror = true;
										window.location = resultdata.result;
									});
								}
							} else {
								var result = Ext.JSON.decode(a.responseText);
								if (result.success[0].result) {
									favorTree.setCusIcon();
									Ext.MessageBox.alert(langs.save_result_title, langs.save_success);
								} else {
									Ext.MessageBox.alert(langs.save_result_title, langs.save_fail);
								}
							}
						},
						failure : function(f, a) {
							Ext.MessageBox.alert(langs.errmsg, langs.system_error);
						}
					});
				}
			}
		}, {
			xtype : 'button',
			iconCls : 'refreshbutton_tree',
			tooltip : langs.refresh,
			handler : function() {
				// var dbvalue = dblist.getValue();
				if (typeof FavorStore !== 'undefined' && FavorStore != null) {
					var loaded = allNodeLoaded(favorTree, null);
					if (!FavorStore.isLoading() && loaded) {
						FavorStore.getRootNode().removeAll();
						FavorStore.getProxy().extraParams['connid'] = dblist.getValue();
						FavorStore.load();
					}
				}
			}
		}, {
			xtype : 'button',
			iconCls : 'addfolderbutton',
			tooltip : langs.add,
			handler : function() {
				if (favorTree.getSelectionModel().hasSelection()) {
					var selectedNode = favorTree.getSelectionModel().getSelection();
					var newnode = {
						id : (new Date).getTime(),
						leaf : false
					};

					if (selectedNode[0].data.leaf) {
						FavorStore.getNodeById(selectedNode[0].data.parent).appendChild(newnode);
					} else {
						selectedNode[0].appendChild(newnode);
					}
					FavorStore.getNodeById(newnode.id).data.lang_key = "lang" + newnode.id;

					Ext.MessageBox.show({
						title : langs.add_folder_title,
						msg : langs.node_lang_panel,
						width : 300,
						buttons : Ext.MessageBox.OKCANCEL,
						multiline : true,
						fn : function(btn, text) {
							if (btn == "ok") {
								var node = FavorStore.getNodeById(newnode.id);
								node.set("text", text);
								// alert(node.data.lang_key);
								buildTreeData(favorTree.getRootNode(), true);
								Ext.Ajax.request({
									method : 'POST',
									url : './../../Funcs/Main/Index.dsc',
									params : {
										DCITag : postvalue,
										action : 'savelangfavor',
										uid : uid,
										connid : dblist.getValue(),
										langkey : node.data.lang_key,
										langvalue : text,
										treeArr : arr
									},
									success : function(a) {
										if (a.responseText.indexOf("@dcifiltererrtag@$") != -1) {
											var result = a.responseText.split('$');
											if (result.length >= 2) {
												var resultdata = Ext.JSON.decode(result[1]);
												Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
													iserror = true;
													window.location = resultdata.result;
												});
											}
										} else {
											var result = Ext.JSON.decode(a.responseText);
											if (result.success[0].result) {
												Ext.MessageBox.alert(langs.add_result_title, langs.add_success);
											} else {
												Ext.MessageBox.alert(langs.add_result_title, langs.add_fail);
											}
										}
									},
									failure : function(f, a) {
										Ext.MessageBox.alert(langs.errmsg, langs.system_error);
									}
								});
							} else {
								var node = FavorStore.getNodeById(newnode.id);
								node.remove();
							}
						}
					});

				} else {
					Ext.MessageBox.alert(langs.errmsg, langs.no_f_node_selected);
				}
			}
		}, {
			xtype : 'button',
			iconCls : 'deletebutton_tree',
			tooltip : langs._delete,
			handler : function() {
				Ext.MessageBox.confirm(langs.delete_confirm_title, langs.delete_confirm_msg, function(btn) {
					if (btn == 'yes') {
						if (favorTree.getSelectionModel().hasSelection()) {
							var node = favorTree.getSelectionModel().getSelection()[0];
							node.remove();

							buildTreeData(favorTree.getRootNode(), true);

							Ext.Ajax.request({
								method : 'POST',
								url : './../../Funcs/Main/Index.dsc',
								params : {
									DCITag : postvalue,
									action : 'savefavor',
									uid : uid,
									connid : dblist.getValue(),
									treeArr : arr
								},
								success : function(a) {
									if (a.responseText.indexOf("@dcifiltererrtag@$") != -1) {
										var result = a.responseText.split('$');
										if (result.length >= 2) {
											var resultdata = Ext.JSON.decode(result[1]);
											Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
												iserror = true;
												window.location = resultdata.result;
											});
										}
									} else {
										var result = Ext.JSON.decode(a.responseText);
										if (result.success[0].result) {
											favorTree.setCusIcon();
											Ext.MessageBox.alert(langs.delete_result_title, langs.delete_success);
										} else {
											Ext.MessageBox.alert(langs.delete_result_title, langs.delete_fail);
										}
									}
								},
								failure : function(f, a) {
									Ext.MessageBox.alert(langs.errmsg, langs.system_error);
								}
							});
						}
					}
				});
			}
		}, {
			xtype : 'button',
			iconCls : 'clearbutton_tree',
			tooltip : langs.clear_favor_folder_title,
			handler : function() {
				Ext.MessageBox.show({
					title : langs.clear_favor_folder_title,
					msg : langs.clear_favor_folder,
					width : 300,
					buttons : Ext.MessageBox.OKCANCEL,
					fn : function(btn, text) {
						if (btn == "ok") {
							Ext.Ajax.request({
								method : 'POST',
								url : './../../Funcs/Main/Index.dsc',
								params : {
									DCITag : postvalue,
									action : 'cleanfavor',
									uid : uid,
									connid : dblist.getValue()
								},
								success : function(a) {
									if (a.responseText.indexOf("@dcifiltererrtag@$") != -1) {
										var result = a.responseText.split('$');
										if (result.length >= 2) {
											var resultdata = Ext.JSON.decode(result[1]);
											Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
												iserror = true;
												window.location = resultdata.result;
											});
										}
									} else {
										if (typeof FavorStore !== 'undefined' && FavorStore != null) {
											FavorStore.getRootNode().removeAll();
											FavorStore.getProxy().extraParams['connid'] = dblist.getValue();
											FavorStore.load();
										}
										var result = Ext.JSON.decode(a.responseText);
										if (result.success[0].result) {
											favorTree.rmCusIcon();
											Ext.MessageBox.alert(langs.clear_result_title, langs.clear_success);
										} else {
											Ext.MessageBox.alert(langs.clear_result_title, langs.clear_fail);
										}
									}
								},
								failure : function(f, a) {
									Ext.MessageBox.alert(langs.errmsg, langs.system_error);
								}
							});
						}
					}
				});
			}
		}, {
			xtype : 'button',
			iconCls : 'collapseAllbutton_tree',
			tooltip : langs.collapse,
			handler : function() {
				favorTree.collapseAll();
			}
		}, {
			xtype : 'button',
			iconCls : 'expandAllbutton_tree',
			tooltip : langs.expand,
			handler : function() {
				favorTree.expandAll();
			}
		} ],
		listeners : {
			itemclick : function(view, rec, item, index, eventObj, eOpts) {
				if (rec.data.leaf) {
					if (rec.data.id == 'TreeLogout') {
						Ext.MessageBox.confirm(langs.confirm_title, langs.logout_confirm, function(btn) {
							if (btn == 'yes') {
								islogout = true;
								logout(postvalue);
							}
						});
					} else {// beforeAddCheck(rec.data.id, dblist.getValue(),
						// rec.data, false);
						mainPanel.beforeAddCheck(rec.data.id, dblist.getValue(), rec.data, false);
					}
				}
			},
			itemdblclick : function(view, rec, item, index, eventObj, eOpts) {
				/*
				 * if (rec.data.leaf) { if (rec.data.id == 'TreeLogout') {
				 * Ext.MessageBox.confirm(langs.confirm_title,
				 * langs.logout_confirm, function(btn) { if (btn == 'yes') {
				 * islogout = true; logout(postvalue); } }); } else
				 * {//beforeAddCheck(rec.data.id, dblist.getValue(), rec.data,
				 * false); mainPanel.beforeAddCheck(rec.data.id,
				 * dblist.getValue(), rec.data, false); } }
				 */
			},
			afteritemexpand : function(node, index, item, eOpts) {
				if (!node.isRoot()) {
					// if (sysFolder.indexOf(node.id) != -1) {
					if (existInArray(sysFolder, node.id)) {
						if (!node.hasChildNodes()) {
							var pnode = node.parentNode;
							if (pnode != null) {
								pnode.removeChild(node);
							}
						}
					}
				}
				/*
				 * if (node.isRoot()) { node.data.init_load_expanded = "1"; }
				 * else { if (node.data.init_load_expanded == null ||
				 * node.data.init_load_expanded == "0") { if
				 * (node.hasChildNodes()) { node.collapse();
				 * node.data.init_load_expanded = "1"; } else { var pnode =
				 * node.parentNode; if (pnode != null) {
				 * pnode.removeChild(node); } } } }
				 */
			},
			load : function(store, node, records, successful, eOpts) {
				var me = this;
				if (node.isRoot()) {
					try {
						me.expandAll();
					} catch (e) {
						if (window.console) {
							console.log(e.message);
						}
					}
				}
			}
		},
		setCusIcon : function() {
			var me = this;
			var ditems = me.getDockedItems('toolbar[dock="top"]');
			var items = ditems[0];
			var btn = items.items.get(4);
			if (btn != null) {
				btn.getEl().setStyle({
					borderWidth : "2px 2px 2px 2px",
					borderColor : 'red',
					boderStyle : 'solid'
				});
			}
		},
		rmCusIcon : function() {
			var me = this;
			var ditems = me.getDockedItems('toolbar[dock="top"]');
			var items = ditems[0];
			var btn = items.items.get(4);
			if (btn != null) {
				btn.getEl().setStyle({
					borderWidth : "1px 1px 1px 1px",
					borderColor : '',
					boderStyle : ''
				});
			}
		}
	});

	/*
	 * var btnTimerStart = Ext.create('Ext.Button', { enableToggle : true,
	 * iconCls : 'startbutton', tooltip : langs.auto_refresh, handler :
	 * function() { if (mainPanel.items != null && mainPanel.items.length != 0) {
	 * //timerControl(task, this, postvalue, timergap); } else {
	 * this.toggle(false); } } });
	 * 
	 * var btnRefresh = Ext.create('Ext.Button', { // text : 'stop', iconCls :
	 * 'refreshbutton', tooltip : langs.refresh, handler : function() {
	 * //refreshTab(postvalue); } });
	 * 
	 * var indexstatusbar = Ext.create('Ext.ux.StatusBar', { id :
	 * 'index-statusbar', defaultText : 'Ready', defaultIconCls :
	 * 'x-status-valid', items : [ '-', btnRefresh, '-', btnTimerStart, '-',
	 * "Last Update Time :" ] });
	 */

	mainPanel = Ext.create('Ext.tab.Panel', {
		region : 'center',
		xtype : 'tabpanel',
		// autoScroll : true,
		deferredRender : false,
		bodyStyle : {
			background : '#EEF5FC'
		},
		listeners : {
			tabchange : function(tabPanel, newCard, oldCard, eOpts) {
				if (newCard != null && oldCard != null) {
					var tmpOld = Ext.getCmp(oldCard.getId().substring(3) + "Main");
					var tmpNew = Ext.getCmp(newCard.getId().substring(3) + "Main");

					if (tmpOld != null) {
						if (tmpOld.pagetype == "kanban") {
							tmpOld.leavePage();
						}
					}

					if (tmpNew != null) {
						// if (tmpNew.pagetype == "kanban") {
						tmpNew.focusPage();
						// }
					}
				}
			},
			resize : function(tabPanel, width, height, oldWidth, oldHeight, eOpts) {
				// console.log(tabPanel.getActiveTab());
				if (tabPanel.getActiveTab() != null) {
					var tmp = null;
					var tmptab = null;
					for ( var i = 0; i < tabPanel.items.length; i++) {
						tmptab = tabPanel.items.get(i);
						if (tabPanel.getActiveTab().getId() == tmptab.getId()) {
							tmp = Ext.getCmp(tmptab.getId().substring(3) + "Main");
							if (tmp != null) {
								tmp.resize(width - 20, height - tabPanel.getTabBar().getHeight() - 2);
							}
						} // else {
						tmp = Ext.get(tmptab.getId().substring(3) + "Page");
						if (tmp != null) {
							tmp.setWidth(width - 20);
							tmp.setHeight(height - tabPanel.getTabBar().getHeight() - 2);
						}
						// }
					}
					/*
					 * tmptab = tabPanel.getActiveTab(); tmp =
					 * Ext.getCmp(tmptab.getId().substring(3) + "Main"); if (tmp !=
					 * null) { tmp.resize(width - 20, height -
					 * tabPanel.getTabBar().getHeight() - 2); }
					 */
				}
			}
		},
		beforeAddCheck : function(func_id, conn_id, nodeData, isRelation) {
			var func_package = "";
			var me = this;
			var tabId = "tab" + func_id;
			var tabExists = false;
			if (nodeData != null) {
				func_package = nodeData.func_package;
			}

			for ( var i = 0; i < me.items.length && !tabExists; i++) {
				if (me.items.getAt(i).id == tabId) {
					tabExists = true;
					break;
				}
			}

			if (func_package == "EKB" && !tabExists) {
				Ext.Ajax.request({
					method : 'POST',
					url : './../../Funcs/Main/Index.dsc',
					params : {
						DCITag : postvalue,
						action : 'kanbanCheck',
						uid : uid,
						func_id : func_id,
						connid : conn_id
					},
					success : function(response) {
						if (response.responseText.indexOf("@dcifiltererrtag@$") != -1) {
							var result = response.responseText.split('$');
							if (result.length >= 2) {
								var resultdata = Ext.JSON.decode(result[1]);
								Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
									iserror = true;
									window.location = resultdata.result;
								});
							}
						} else {
							var result = Ext.JSON.decode(response.responseText);
							if (result.result[0].success == "true") {
								me.addKanBanTab(func_id, conn_id, nodeData, isRelation);
							} else {
								Ext.Msg.alert(langs.errmsg, langs.kanban_not_exist);
							}
						}
					}
				});
			} else {
				me.addKanBanTab(func_id, conn_id, nodeData, isRelation);
			}

		},
		addKanBanTab : function(func_id, conn_id, nodeData, isRelation) {
			pagetask.stopTask();
			var me = this;
			var tabId = "tab" + func_id;
			var tabExists = false;
			var func_name = "";
			var func_can_edit = "0";
			var func_url = "";
			var func_package = "";

			if (nodeData != null) {
				func_name = nodeData.text;
				func_url = nodeData.url;
				func_package = nodeData.func_package;
				func_can_edit = nodeData.can_edit;
			}

			for ( var i = 0; i < me.items.length && !tabExists; i++) {
				if (me.items.getAt(i).id == tabId) {
					tabExists = true;
					break;
				}
			}

			if (!tabExists) {
				functab = Ext.create('Ext.panel.Panel', {
					id : tabId,
					title : func_name,
					closable : true,
					autoScroll : true,
					tabPackage : func_package,
					bodyStyle : {
						background : "#eaf1fb"
					},
					loader : {
						url : func_url,
						scripts : true,
						params : {
							forwardTag : forwardvalue,
							func_id : func_id,
							func_name : func_name,
							conn_id : conn_id,
							canEdit : func_can_edit,
							filter : nodeData.filter,
							uid : uid
						},
						listeners : {
							exception : function(loader, response, options, eOpts) {
								Ext.Msg.alert(langs.errmsg, langs.load_fail);
								me.remove(functab);
							},
							load : function(loader, response, options, eOpts) {
								if (response.responseText.length < 100 && response.responseText.indexOf("@dcifiltererrtag@$") != -1) {
									var result = response.responseText.split('$');
									if (result.length >= 2) {
										// console.log(result[1]);
										var resultdata = Ext.JSON.decode(result[1]);
										Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
											iserror = true;
											if (resultdata.result != null && resultdata.result.length > 0) {
												window.location = resultdata.result;
											} else {
												me.remove(functab);
											}
										});
									}
								}
							}
						}
					},
					listeners : {
						added : function(tab) {
							tab.loader.load();
						},
						beforeclose : function(panel, eOpts) {
							if (panel != null) {
								var tmp = Ext.getCmp(panel.id.substring(3) + "Main");
								if (tmp != null) {
									var needConfirm = false;
									if (tmp.items != null) {
										for ( var i = 0; i < tmp.items.length; i++) {
											if (typeof tmp.items.get(i).getCurrMode == 'function') {
												needConfirm = tmp.items.get(i).getCurrMode() == PageMode.Edit || tmp.items.get(i).getCurrMode() == PageMode.Add;
												if (needConfirm) {
													break;
												}
											}
										}
									}

									if (needConfirm) {
										Ext.MessageBox.confirm(langs.confirm_title, langs.data_lose_warning, function(btn) {
											if (btn == 'yes') {
												tmp.beforeClose();
												me.remove(panel);
											}
										});
									} else {
										tmp.beforeClose();
										me.remove(panel);
									}
									return false;
								}
							}
						}
					}
				});

				me.add(functab);
			}

			if (tabExists && isRelation) {
				var tmp = Ext.getCmp(func_id + "Main");
				if (tmp != null && nodeData != null) {
					tmp.relationReload(nodeData.filter);
				}
			}
			me.setActiveTab(tabId);
		}

	});

	var uidlabel = Ext.create('Ext.form.Label', {
		xtype : 'label',
		text : '',
		margin : '0 0 0 5',
		x : 90,
		y : 5
	});

	var unamelabel = Ext.create('Ext.form.Label', {
		xtype : 'label',
		text : '',
		margin : '0 0 0 5',
		x : 250,
		y : 5
	});

	var ltimelabel = Ext.create('Ext.form.Label', {
		xtype : 'label',
		text : '',
		margin : '0 0 0 5',
		x : 250,
		y : 30
	});

	var gnamelabel = Ext.create('Ext.form.Label', {
		xtype : 'label',
		text : '',
		margin : '0 0 0 5',
		x : 90,
		y : 30
	});

	var wnPanel = Ext.create('Ext.panel.Panel', {
		region : 'north',
		xtype : 'panel',
		height : 30,
		margins : '0 5 5 5',
		layout : 'absolute',
		border : false,
		split : false,
		items : [ {
			xtype : 'label',
			text : langs.conn_target,
			margin : '0 0 0 2',
			width : 60,
			x : 0,
			y : 8
		}, dblist, {
			xtype : 'button',
			iconCls : 'refreshbutton_tree',
			tooltip : langs.refersh,
			x : 188,
			y : 5,
			handler : function() {
				var dbvalue = dblist.getValue();

				var dbStore = Ext.create('Ext.data.Store', {
					autoLoad : false,
					checkSession : false,
					fields : [ "dblistvalue" ],
					proxy : {
						type : 'ajax',
						url : './../../Funcs/Main/Index.dsc',
						actionMethods : {
							read : 'POST'
						},
						reader : {
							type : 'json'
						},
						extraParams : {
							DCITag : postvalue,
							uid : uid,
							action : 'dblist'
						}
					}
				});

				dbStore.load(function(records) {
					dbliststore.loadData(records[0].get("dblistvalue"));
					dblist.setValue(dbvalue);
				});
			}
		} ]
	});

	var defTreePanel = Ext.create('Ext.panel.Panel', {
		title : langs.functions,
		xtype : 'panel',
		margins : '0 0 0 0',
		border : false,
		split : false,
		layout : 'fit',
		items : [ funcTree ],
		setCusIcon : function() {
			var tree = this.items.get(0);
			tree.setCusIcon();
			/*
			 * var bar = tree.tbar; var btn = bar[3]; console.log(tree);
			 * console.log(bar); console.log(btn); if (btn != null) {
			 * btn.getEl().setStyle({ borderWidth : "2px 2px 2px 2px",
			 * borderColor : 'red', boderStyle : 'solid' }); }
			 */
		}
	});

	var favorTreePanel = Ext.create('Ext.panel.Panel', {
		xtype : 'panel',
		title : langs.favorties,
		margins : '0 0 0 0',
		border : false,
		split : false,
		layout : 'fit',
		items : [ favorTree ]
	});

	var wsPanel = Ext.create('Ext.panel.Panel', {
		region : 'center',
		margins : '0 0 0 0',
		border : false,
		split : false,
		layout : {
			type : 'accordion',
			titleCollapse : false,
			animate : true
		},
		items : [ defTreePanel, favorTreePanel ]
	});

	var westPanel = Ext.create('Ext.panel.Panel', {
		region : 'west',
		stateId : 'funcPanel',
		title : langs.tree_panel_title,
		split : true,
		splitterResize : false,
		width : 220,
		collapsible : true,
		animCollapse : false,
		layout : 'border',
		border : false,
		items : [ wnPanel, wsPanel ]
	});

	var northWestPanel = Ext.create('Ext.panel.Panel', {
		region : 'west',
		xtype : 'panel',
		layout : 'absolute',
		margins : '0 0 0 0',
		width : 400,
		bodyStyle : {
			background : '#EEF5FC'
		},
		border : false,
		split : false,
		items : [ {
			xtype : 'label',
			text : langs.user_id,
			margin : '0 0 0 5',
			x : 5,
			y : 5
		}, uidlabel, {
			xtype : 'label',
			text : langs.user_name,
			margin : '0 0 0 5',
			x : 170,
			y : 5
		}, unamelabel, {
			xtype : 'label',
			text : langs.group_name,
			margin : '0 0 0 5',
			x : 5,
			y : 30
		}, gnamelabel, {
			xtype : 'label',
			text : langs.login_time,
			margin : '0 0 0 5',
			x : 170,
			y : 30
		}, ltimelabel ]
	});

	var northCenterPanel = Ext.create('Ext.panel.Panel', {
		region : 'center',
		xtype : 'panel',
		layout : 'absolute',
		margins : '0 0 0 0',
		bodyStyle : {
			background : '#EEF5FC'
		},
		border : false,
		split : false
	});

	var northEastPanel = Ext.create('Ext.panel.Panel', {
		region : 'east',
		xtype : 'panel',
		margins : '0 0 0 0',
		split : false,
		layout : 'absolute',
		border : false,
		bodyStyle : {
			background : '#EEF5FC'
		},
		items : [ {
			xtype : 'button',
			cls : 'starttimerbutton24',
			tooltip : langs.kanban_loop,
			enableToggle : true,
			x : 10,
			y : 10,
			width : 35,
			height : 35,
			handler : function() {
				var me = this;
				if (me.pressed) {
					var tab = mainPanel.getActiveTab();

					if (tab == null) {
						Ext.MessageBox.alert(langs.errmsg, langs.no_kanban_can_show);
						me.pressed = false;
					} else {
						var found = false;

						Ext.Ajax.request({
							method : 'POST',
							url : './../../Funcs/Main/Index.dsc',
							params : {
								DCITag : postvalue,
								uid : uid,
								action : 'pageTaskGap'
							},
							success : function(a) {
								if (a.responseText.indexOf("@dcifiltererrtag@$") != -1) {
									var result = a.responseText.split('$');
									if (result.length >= 2) {
										var resultdata = Ext.JSON.decode(result[1]);
										Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
											iserror = true;
											window.location = resultdata.result;
										});
									}
								} else {
									var result = Ext.JSON.decode(a.responseText);
									pagetask.setTimeGap(result.result[0].gap);

									if (tab != null && tab.tabPackage == "EKB") {
										currTabID = mainPanel.getActiveTab().id;
										var tmp = Ext.getCmp(currTabID.substring(3) + "Main");
										tmp.globalTimerEvent(-1, pagetask.getTimeGap());
										found = true;
									} else {
										for ( var i = 0; i < mainPanel.items.length; i++) {
											if (mainPanel.items.get(i).tabPackage == 'EKB') {
												currTabID = mainPanel.items.get(i).id;
												var tmp = Ext.getCmp(currTabID.substring(3) + "Main");
												tmp.globalTimerEvent(-1, pagetask.getTimeGap());
												mainPanel.setActiveTab(currTabID);
												found = true;
												break;
											}
										}
									}

									if (found) {
										pagetask.executeTask();
									} else {
										Ext.MessageBox.alert(langs.errmsg, langs.no_kanban_can_show);
										me.pressed = false;
									}
								}
							},
							failure : function(f, a) {
								Ext.MessageBox.alert(langs.errmsg, langs.get_task_gap_fail);
								me.pressed = false;
							}
						});

					}
				} else {
					pagetask.stopTask();
				}
				// me.changeIcon();
			}
		}, {
			xtype : 'button',
			cls : 'logoutbutton',
			tooltip : langs.logout,
			x : 50,
			y : 10,
			width : 35,
			height : 35,
			handler : function() {
				Ext.MessageBox.confirm(langs.confirm_title, langs.logout_confirm, function(btn) {
					if (btn == 'yes') {
						islogout = true;
						logout(postvalue);
					}
				});
			}
		} ],
		changeIcon : function(pressed) {
			var btn = this.items.get(0);
			btn.pressed = pressed;
			if (pressed) {
				btn.removeCls('starttimerbutton24');
				btn.addCls('stoptimerbutton24');
			} else {
				btn.removeCls('stoptimerbutton24');
				btn.addCls('starttimerbutton24');
			}
		}
	});

	var northPanel = Ext.create('Ext.panel.Panel', {
		region : 'north',
		xtype : 'panel',
		header : false,
		height : 50,
		split : true,
		splitterResize : false,
		collapsible : true,
		collapseMode : 'mini',
		animCollapse : false,
		margins : '0 0 0 0',
		layout : 'border',
		bodyStyle : {
			background : '#EEF5FC'
		},
		items : [ northWestPanel, northCenterPanel, northEastPanel ]
	});

	var southPanel = Ext.create('Ext.panel.Panel', {
		region : 'south',
		xtype : 'panel',
		header : false,
		height : 40,
		iheight : 40,
		split : true,
		splitterResize : false,
		collapsible : true,
		collapseMode : 'mini',
		animCollapse : false,
		dataTask : null,
		scrollTask : null,
		collapseBySys : false,
		collapseByUser : false,
		showType : "1",
		bodyStyle : {
			background : '#EEF5FC'
		},
		syscollapse : function() {
			// this.initheight = this.height;
			this.collapseBySys = true;
			this.collapse(true);
			this.collapseBySys = false;
		},
		sysexpand : function() {
			if (!this.collapseByUser) {
				this.expand(true);
			}
		},
		listeners : {
			collapse : function(p, eOpts) {
				if (!p.collapseBySys) {
					p.collapseByUser = true;
				}
			},
			expand : function(p, eOpts) {
				// console.log(panel.initheight);
				// p.initheight = p.height;
				p.collapseByUser = false;
				// console.log(panel.initheight);
			}
		},
		startMarquee : function(refreshGap, showType) {
			var panel = this;
			var mRunner = new Ext.util.TaskRunner();
			var taskScroll = null;
			// panel.showType = showType;
			panel.syscollapse();
			var mStore = Ext.create('Ext.data.Store', {
				autoLoad : false,
				fields : [ 'message' ],
				proxy : {
					type : 'ajax',
					url : './../../Funcs/Main/Index.dsc',
					actionMethods : {
						read : 'POST'
					},
					reader : {
						root : 'marquee'
					},
					extraParams : {
						DCITag : postvalue,
						uid : uid,
						action : 'marqueeData'
					}
				}
			});
			mStore.getProxy().on("exception", function(proxy, response, operation, eOpts) {
				// console.log(response.responseText);
				if (response.responseText.indexOf('@dcifiltererrtag@') != -1) {
					var result = response.responseText.split('$');
					if (result.length >= 2) {
						// console.log(result[1]);
						var resultdata = Ext.JSON.decode(result[1]);
						// console.log(resultdata.msg);
						if (taskGetData != null) {
							taskGetData.stop();
						}
						Ext.Msg.alert("error", resultdata.msg, function() {
							iserror = true;
							window.location = resultdata.result;
						});
					}
				}
			});
			var currRow = 0;
			if (showType == "2") {
				if (taskScroll == null) {
					taskScroll = mRunner.newTask({
						run : function() {
							if (mStore.getCount() > 0) {
								if (currRow == 0) {
									panel.body.dom.scrollTop = 0;
								}
								if (currRow == mStore.getCount() - 1) {
									currRow = 0;
								} else {
									currRow++;
								}
								panel.scrollBy(0, panel.iheight, true);
							}
						}
					});
				}
				panel.scrollTask = taskScroll;
				taskScroll.start(5000);
			} else {
				panel.scrollTask = null;
			}

			function loadData() {
				if (mStore != null) {
					mStore.load(function(record) {
						if (record != null && record.length > 0) {
							var value = "";
							panel.sysexpand();
							if (showType == '1') {
								for ( var i = 0; i < record.length; i++) {
									if (i == 0) {
										value = '<div style="font-size:22px;"><Marquee height="' + panel.iheight
												+ '" scrollamount="3" onmouseover="this.stop()" onmouseout="this.start()">' + record[i].get('message');
									} else {
										value += "                              " + record[i].get('message');
									}

									if (i == record.length - 1) {
										value += "</div></Marquee>";
									}
								}
								panel.update(value);
							} else {
								if (panel.scrollTask != null) {
									panel.scrollTask.stop();
									panel.body.dom.scrollTop = 0;
								}
								var t = "";

								for ( var i = 0; i < record.length; i++) {
									if (i == 0) {
										t = '<div style="font-size:22px;text-align:center;overflow:hidden;height:' + panel.iheight + 'px;">' + record[i].get('message') + "</div>";
										value = '<div>' + t;
									} else {
										value += '<div  style="font-size:22px;text-align:center;overflow:hidden;height:' + panel.iheight + 'px;">' + record[i].get('message')
												+ '</div>';
									}

									if (i == record.length - 1) {
										value += t + "</div>";
									}
								}

								panel.update(value);
								if (panel.scrollTask != null) {
									panel.scrollTask.start(5000);
								}
							}

							// callback(panel);
						} else {
							if (panel.scrollTask != null) {
								panel.scrollTask.stop();
								panel.body.dom.scrollTop = 0;
							}
							panel.update("");
						}
					});
				}
			}

			var taskGetData = mRunner.newTask({
				run : function() {
					loadData();
				}
			});

			this.dataTask = taskGetData;
			loadData();

			taskGetData.start(refreshGap * 1000);
		}
	});

	Ext.create('Ext.Viewport', {
		layout : 'border',
		items : [ westPanel, northPanel, mainPanel, southPanel ],
		listenres : [ {
			disable : function(Viewport, eOpts) {
				logout(postvalue);
			}
		} ]
	});

	initQueryStore.load(function(records) {
		if (records.length == 1) {
			// console.log(records[0].get("pageTimerGap")[0].gap);
			pagetask.setTimeGap(records[0].get("pageTimerGap")[0].gap);
			dbliststore.loadData(records[0].get("dblistvalue"));
			userInfoStore.loadData(records[0].get("userInfo"));
			// alert(dbliststore.getCount());
			if (dbliststore.getCount() > 0 && typeof dblist != 'undefined' && dblist != null) {
				var conn = dbliststore.getAt(0).get("value");
				dblist.setValue(conn);
				// TreeStore.getRootNode().removeAll();
				TreeStore.getRootNode().expand();
				FavorStore.getRootNode().expand();
			}
			if (userInfoStore.getCount() > 0) {
				uidlabel.setText(" : " + userInfoStore.getAt(0).get("uid"));
				unamelabel.setText(" : " + userInfoStore.getAt(0).get("uname"));
				ltimelabel.setText(" : " + userInfoStore.getAt(0).get("ltime"));
				gnamelabel.setText(" : " + userInfoStore.getAt(0).get("gname"));
			}

			if (defTreePanel != null) {
				if (records[0].get("hasfavor")[0].hasfavor.toLowerCase() == "true") {
					defTreePanel.collapse(true);
				}
			}

			if (funcTree != null && favorTree != null) {
				var tag = records[0].get("hascus")[0];
				if (tag.hasdefcus.toLowerCase() == "true") {
					funcTree.setCusIcon();
				}
				if (tag.hasfavcus.toLowerCase() == "true") {
					favorTree.setCusIcon();
				}
			}

			if (southPanel != null) {
				southPanel.startMarquee(records[0].get("sysMarquee")[0].marquee_refresh_gap, records[0].get("sysMarquee")[0].marquee_type);
			}
		}
	});

	var arr = "";

	function buildTreeData(node, reset) {
		if (reset) {
			arr = "";
		}
		var tmp = null;
		var childs = node.childNodes;

		for ( var i = 0; i < childs.length; i++) {
			tmp = "";
			tmp += childs[i].data.id;
			if (reset) {
				tmp += ",";
			} else {
				tmp += "," + childs[i].parentNode.data.id;
			}
			tmp += "," + childs[i].data.lang_key;
			tmp += "," + childs[i].parentNode.indexOf(childs[i]);
			tmp += "," + childs[i].data.leaf;

			if (arr == "") {
				arr = tmp;
			} else {
				arr += ";" + tmp;
			}

			if (!childs[i].data.leaf && childs[i].hasChildNodes()) {
				buildTreeData(childs[i], false);
			}
		}

	}

}

function logout(postvalue) {
	Ext.Ajax.request({
		method : 'POST',
		url : './../../Login.dsc',
		params : {
			DCITag : postvalue,
			uid : idxuid,
			action : 'logout'
		},
		success : function(a) {
			var result = Ext.JSON.decode(a.responseText);
			if (result.success) {
				window.location.href = "./../../";
			} else {
				Ext.MessageBox.alert(langs.errmsg, langs.logout_fail);
			}
		},
		failure : function(f, a) {
			Ext.MessageBox.alert(langs.errmsg, langs.logout_fail);
		}
	});
}
/*
 * function logout2(postvalue) { Ext.Ajax.request({ method : 'POST', url :
 * './../../Login.dsc', params : { DCITag : postvalue, uid : idxuid, action :
 * 'logout' } }); }
 */

function logoutF(postvalue) {
	if (navigator.userAgent.match("Firefox") == "Firefox") {
		var ajax = new XMLHttpRequest();
		if (ajax) {
			ajax.open("GET", '/KanBan/Login.dsc?action=logout&uid=' + idxuid + '&DCITag=' + postvalue, false);
			ajax.send("");
		}
	} else if (navigator.appName == "Microsoft Internet Explorer") {
		window.navigate('./../../Login.dsc?action=logout&uid=' + idxuid + '&DCITag=' + postvalue);
	} else {
		Ext.Ajax.request({
			method : 'POST',
			url : './../../Login.dsc',
			params : {
				DCITag : postvalue,
				uid : idxuid,
				action : 'logout'
			}
		});
	}
}