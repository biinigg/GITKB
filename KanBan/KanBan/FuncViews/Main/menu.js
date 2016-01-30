/**
 * 
 */
var t1, t2, t3, t4;
function showPage() {

	Ext.Loader.setConfig({
		enabled : true
	});

	Ext.Loader.setPath('Ext.ux', '../ux/');

	Ext.require([ 'Ext.tab.*', 'Ext.ux.TabCloseMenu' ]);
	Ext
			.onReady(function() {

				var currentItem;

				var tabs = Ext.widget('tabpanel', {
					renderTo : 'tabs',
					resizeTabs : true,
					enableTabScroll : true,
					width : 600,
					height : 250,
					defaults : {
						autoScroll : true,
						bodyPadding : 10
					},
					items : [ {
						title : 'Tab 1',
						iconCls : 'tabs',
						// html: 'Tab Body<br/><br/>' + Ext.example.bogusMarkup,
						closable : true
					} ],
					plugins : Ext.create('Ext.ux.TabCloseMenu', {
						extraItemsTail : [ '-', {
							text : 'Closable',
							checked : true,
							hideOnClick : true,
							handler : function(item) {
								currentItem.tab.setClosable(item.checked);
							}
						}, '-', {
							text : 'Enabled',
							checked : true,
							hideOnClick : true,
							handler : function(item) {
								currentItem.tab.setDisabled(!item.checked);
							}
						} ],
						listeners : {
							beforemenu : function(menu, item) {
								var enabled = menu.child('[text="Enabled"]');
								menu.child('[text="Closable"]').setChecked(
										item.closable);
								if (item.tab.active) {
									enabled.disable();
								} else {
									enabled.enable();
									enabled.setChecked(!item.tab.isDisabled());
								}

								currentItem = item;
							}
						}
					})
				});

				// tab generation code
				var index = 0;

				while (index < 3) {
					addTab(index % 2);
				}

				function doScroll(item) {
					var id = item.id.replace('_menu', ''), tab = tabs
							.getComponent(id).tab;

					tabs.getTabBar().layout.overflowHandler.scrollToItem(tab);
				}

				function addTab(closable) {
					++index;
					tabs.add({
						closable : !!closable,
						html : 'Tab Body ' + index + '<br/><br/>',// +
						// Ext.example.bogusMarkup,
						iconCls : 'tabs',
						title : 'New Tab ' + index
					}).show();
				}

				function addToMenu(ct, tab) {
					menu.add({
						text : tab.title,
						id : tab.id + '_menu',
						handler : doScroll
					});
				}

				function removeFromMenu(ct, tab) {
					var id = tab.id + '_menu';
					menu.remove(id);
				}

				tabs.on({
					add : addToMenu,
					remove : removeFromMenu
				});

				Ext.widget('button', {
					iconCls : 'new-tab',
					renderTo : 'addButtonCt',
					text : 'Add Closable Tab',
					handler : function() {
						addTab(true);
					}
				});

				Ext.widget('button', {
					iconCls : 'new-tab',
					renderTo : 'addButtonCt',
					style : 'margin-left: 8px;',
					text : 'Add Unclosable Tab',
					handler : function() {
						addTab(false);
					}
				});

				var menu = new Ext.menu.Menu();
				tabs.items.each(function(tab) {
					addToMenu(tabs, tab);
				});
				Ext.widget('button', {
					iconCls : 'scroll',
					renderTo : 'addButtonCt',
					style : 'margin-left: 8px;',
					text : 'Scroll to:',
					menu : menu
				})
				// 下拉式按鈕多選單
				var ItemPanel = Ext.create('Ext.panel.Panel', {
					// title : 'Menu',
					bodyPadding : 5,
					width : 250,
					height : 250,
					id : 'ItemPanel',
					layout : {
						type : 'table',
						columns : 2,
						pack:'center'
					},
					defaults : {
						//style: 'margin:0 auto;margin-top:100px;',
						enableToggle : true,
						bodyStyle : 'padding:40px',
						width : 120
					}
				});
				function menuListener(txt) {
					var panel = Ext.getCmp('ItemPanel');
					panel.removeAll();
					for ( var i in txt) {
						var arr=txt[i].split(',');
						panel.add({
							xtype : 'button',
							text :arr[0] ,
							toggleGroup : 'btngroup',
							value:arr[1] ,
							handler:function(){
								Ext.getCmp('price').setValue(this.value);
							}
						});
					}
					panel.doLayout();
				}

				var menuPanel = Ext.create('Ext.panel.Panel', {
					title : 'Menu',
					autoScroll : true,
					layout : {
						type:'vbox',
						align: 'center',
						pack: 'center'
					},
					width : 350,
					height : 350,
					defaults : {
						bodyStyle : 'padding:20px'
					},
					items : [{
								xtype : 'button',
								width : 120,
								id:'menuBtn',
								text : '請選擇類別',
								menu : new Ext.menu.Menu({
									items : [{
												text : '類別：車',
												handler : function() {
													var menuBtn=Ext.getCmp('menuBtn');
													menuBtn.setText(this.text);
													var arr = new Array("audi,1000",
															"volvol,800", "benz,20");
													menuListener(arr);
												}
											},
											{
												text : '類別：人',
												handler : function() {
													var menuBtn=Ext.getCmp('menuBtn');
													menuBtn.setText(this.text);
													var arr = new Array("andy",
															"vicky", "bebe");
													menuListener(arr);
												}
											}]
								})
							}, ItemPanel, {
								xtype : 'textfield',
								labelWidth : 110,
								fieldLabel : '單價',
								id : 'price',
								name : 'price',
								value : 0,
								readOnly:true,
								minValue : 1,
								//maxValue : 999,
								//colspan : 3
							}, {
								xtype : 'button',
								width:120,
								text : '確定',
								handler : function() {
									var panel = Ext.getCmp('ItemPanel');
									var btnList = panel.items;
									for (var i = 0; i < btnList.length; i++) {
										if (btnList.get(i).pressed) {
											alert(btnList.get(i).text);
										}
									}
								}
							} ]
				});

				// 小算盤
				function cal() {
					var count = Ext.getCmp('count').getValue();
					if (this.value == -1) {
						Ext.getCmp('count').setValue(0);
					} else if (this.value == -2) {
						Ext.getCmp('count').setValue(Math.floor(count / 10));
					} else {
						if (count > 0) {
							Ext.getCmp('count').setValue(
									count * 10 + this.value);
						} else {
							Ext.getCmp('count').setValue(this.value);
						}

					}

				}
				var digitForm = Ext.create('Ext.form.Panel', {
					title : 'Base Example',
					bodyPadding : 5,
					height : 175,
					// rowspan: 2,
					layout : {
						type : 'table',
						columns : 3
					},
					defaults : {
						bodyStyle : 'padding:20px',
						handler : cal
					},
					items : [ {
						xtype : 'numberfield',
						labelWidth : 110,
						fieldLabel : '數量',
						id : 'count',
						name : 'count',
						value : 0,
						minValue : 1,
						maxValue : 999,
						colspan : 3
					}, {
						xtype : 'button',
						value : 7,
						text : '7',
						width : 100
					}, {
						xtype : 'button',
						value : 8,
						text : '8',
						width : 100
					}, {
						xtype : 'button',
						value : 9,
						text : '9',
						width : 100
					}, {
						xtype : 'button',
						value : 4,
						text : '4',
						width : 100
					}, {
						xtype : 'button',
						value : 5,
						text : '5',
						width : 100
					}, {
						xtype : 'button',
						value : 6,
						text : '6',
						width : 100
					}, {
						xtype : 'button',
						value : 1,
						text : '1',
						width : 100
					}, {
						xtype : 'button',
						value : 2,
						text : '2',
						width : 100
					}, {
						xtype : 'button',
						value : 3,
						text : '3',
						width : 100
					}, {
						xtype : 'button',
						value : -1,
						text : '清除',
						width : 100
					}, {
						xtype : 'button',
						value : 0,
						text : '0',
						width : 100
					}, {
						xtype : 'button',
						value : -2,
						text : '刪除',
						width : 100
					} ]
				});
				// result view
				function verifyAll(panel){
					var success=false
					return success;
				}
				
				var resultForm = Ext.create('Ext.form.Panel', {
					title : '總計',
					bodyPadding : 5,
					height : 175,
					// rowspan: 2,
					// layout:'vbox',
					// layout:{
					// type:'table',
					// columns: 3
					// },
					defaults : {
						bodyStyle : 'padding:20px',
						//handler : cal
					},
					items : [ {
						xtype : 'textfield',
						labelWidth : 110,
						fieldLabel : '總金額',
						id : 'amount',
						readOnly : true,
						style : {
							background : 'red'
						},
						fieldStyle : {
						// background : 'red',
						}
					// colspan: 3
					}, {
						xtype : 'textfield',
						labelWidth : 110,
						fieldLabel : '總數量',
						id : 'sum',
						readOnly : true,
						style : {
							background : 'red'
						},
						fieldStyle : {
						// background : 'red',
						// fontSize : '8px',
						}
					}],
					buttonAlign: 'center',
					buttons:[ {
						xtype : 'button',
						text : '結帳',
						width : 150,
						handler:function(){
							var panel=this.up('panel').up('panel');
							if(verifyAll(panel)){
								alert(1);
							}else{
								alert(0);
							}
						}
					
					}]
				});

				var main = Ext.create('Ext.panel.Panel', {
					title : 'Count',
					height : '100%',
					width : '100%',
					layout : {
						type : 'table',
						columns : 5
					},
					renderTo : 'Caculator',
					items : [ menuPanel, digitForm, resultForm ]

				});
			});
}
