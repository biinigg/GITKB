function openIE() {
	var wScriptShell = new ActiveXObject("WScript.Shell");
	var wshSysEnv = wScriptShell.Exec("iexplore.exe -nomerge " + document.URL);
}

function isIE(ver) {
	var b = document.createElement('b');
	b.innerHTML = '<!--[if IE ' + ver + ']><i></i><![endif]-->';
	return b.getElementsByTagName('i').length === 1;
}

function isRightVersionIE() {
	if (isIE(5)) {
		return false;
	} else if (isIE(6)) {
		return false;
	} else if (isIE(7)) {
		return false;
	} else if (isIE(8)) {
		return false;
	} else {
		return true;
	}
}







var langs;
var deflang;
var urllang = '';
var keys = [ "loginforme01", "no_user_selected", "errmsg", "online_num", "lic_limit", "used_lic_num", "login", "reset", "user_id", "user_pwd", "lang_type", "login_ip", "use_lic",
		"close", "kill", "user_list", "please_enter_pwd", "enter_pwd_title", "system_error","wrong_ie_ver" ];

Ext.onReady(function() {
	var url = location.href;
	if (url.indexOf('?') != -1) {
		var qs = url.substring(url.indexOf('?') + 1).split('&');
		for ( var i = 0; i < qs.length; i++) {
			qs[i] = qs[i].split('=');
			if (qs[i][0] == "ltp") {
				urllang = qs[i][1];
			}
		}
	}

	this.loginstore.setLoginMultiLangKeys(keys, urllang);
	this.loginstore.load(function(records) {
		if (records != null && records.length == 1) {
			langs = buildMultiLangObjct(keys, records[0].get('langValues'));
			
			if (navigator.appName == "Microsoft Internet Explorer") {
				//alert("ie5:" + isIE(5) + " ie6:" + isIE(6) + " ie7:" + isIE(7) + " ie8:" + isIE(8) + " ie9:" + isIE(9));
				if (!isRightVersionIE()) {
					//alert("瀏覽器支援不支援");
					alert(langs.wrong_ie_ver);
					window.opener = null;
					window.open('', '_self');
					window.close();
				}
			}
			
			
			this.ccode = records[0].get('frowardTagValue');
			loginform(records[0].get('frowardTagValue'), records[0].get('dcitagValue'));
			deflang = records[0].get('defLangType');
			document.getElementById("labelver").innerHTML = records[0].get('currVersion');
			if (urllang == null || urllang == "") {
				document.getElementById('langtp').value = deflang;
				changeBGImage(deflang);
			} else {
				document.getElementById('langtp').value = urllang;
				changeBGImage(urllang);
			}
		}
	});

	function loginform(forwardvalue, postvalue) {
		var h = document.getElementById("checkcode");
		var p = document.getElementById("DCITag");
		var a = document.getElementById("action");
		h.value = forwardvalue;
		p.value = postvalue;
		a.value = 'login';
	}

});

function changeBGImage(lang) {
	if (lang == "CHT") {
		document.getElementById("loginmain").style.backgroundImage = "url(images/background/KanBan_login_TC.jpg)";
	} else {
		document.getElementById("loginmain").style.backgroundImage = "url(images/background/KanBan_login_SC.jpg)";
	}
}

function formCheck() {
	var uid = document.getElementById("uid");
	if (uid.value == null || uid.value.length == 0) {
		alert(langs.loginforme01);
		return false;
	} else {
		return true;
	}
}

function resetForm() {
	var uid = document.getElementById("uid");
	var pwd = document.getElementById("pwd");
	var lang = document.getElementById("langtp");
	uid.value = "";
	pwd.value = "";
	lang.value = deflang;
	changeBGImage(lang.value);
}

function changeLang() {
	var lang = document.getElementById("langtp");
	this.loginstore.setLoginMultiLangKeys(keys, lang.value);
	this.loginstore.load(function(records) {
		if (records != null && records.length == 1) {
			langs = buildMultiLangObjct(keys, records[0].get('langValues'));

			document.getElementById("labelid").innerHTML = langs.user_id;
			document.getElementById("labelpwd").innerHTML = langs.user_pwd;
			document.getElementById("labeltp").innerHTML = langs.lang_type;
			document.getElementById("userlistbtn").value = langs.user_list;
			document.getElementById("resetbtn").value = langs.reset;
			document.getElementById("submit").value = langs.login;
			changeBGImage(lang.value);
		}
	});
}

function showUserList() {
	Ext.onReady(function() {
		var relationConfigWin = Ext.create('Ext.window.Window', {
			layout : 'border',
			title : langs.user_list,
			closeAction : 'hide',
			height : 300,
			width : 520,
			minHeight : 300,
			minWidth : 520,
			modal : true,
			plain : true,
			items : [ {
				xtype : 'panel',
				region : 'north',
				layout : 'absolute',
				height : 30,
				items : [ {
					xtype : 'button',
					text : langs.kill,
					x : 0,
					y : 3,
					width : 90,
					handler : function() {
						var panel = this.up('panel');
						var win = panel.up('window');
						var grid = win.items.get(1);
						// console.log(grid.getSelectionModel().getSelection()[0]);

						if (grid.getSelectionModel().getSelection() == null || grid.getSelectionModel().getSelection()[0] == null) {
							Ext.Msg.alert(langs.errmsg, langs.no_user_selected);
						} else {
							var msgbox = Ext.MessageBox.prompt(langs.enter_pwd_title, langs.please_enter_pwd, function(btn, text) {
								if (btn == "ok") {
									Ext.Ajax.request({
										method : 'POST',
										url : './LoginInfo.dsc',
										params : {
											DCITag : document.getElementById("DCITag").value,
											action : 'removeUser',
											user_id : grid.getSelectionModel().getSelection()[0].data.user_id,
											ip_addr : grid.getSelectionModel().getSelection()[0].data.ip_addr,
											pwd : text,
											langtp : document.getElementById("langtp").value
										},
										success : function(a) {
											var result = Ext.JSON.decode(a.responseText);
											if (result.success) {
												win.loadDatas();
											} else {
												Ext.MessageBox.alert(langs.errmsg, result.errorMessage);
											}
										},
										failure : function(f, action) {
											Ext.MessageBox.alert(langs.errmsg, langs.system_error);
										}
									});
								}
							});
							msgbox.textField.inputEl.dom.type = 'password';
						}
					}
				}, Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.online_num,
					labelWidth : 60,
					width : 100,
					emptyText : '0',
					readOnly : true,
					fieldStyle : 'text-align: right;',
					x : 100,
					y : 3
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.used_lic_num,
					labelWidth : 90,
					width : 130,
					emptyText : '0',
					readOnly : true,
					fieldStyle : 'text-align: right;',
					x : 215,
					y : 3
				}), Ext.create('DCI.Customer.TextField', {
					fieldLabel : langs.lic_limit,
					labelWidth : 90,
					width : 130,
					emptyText : '0',
					readOnly : true,
					fieldStyle : 'text-align: right;',
					x : 355,
					y : 3
				}) ]
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
				columns : [ {
					// text : langs.target_sql_name,
					dataIndex : 'seq',
					width : 100
				}, {
					text : langs.user_id,
					dataIndex : 'user_id',
					width : 150
				}, {
					text : langs.login_ip,
					dataIndex : 'ip_addr_d',
					width : 100
				}, Ext.create('DCI.Customer.CheckColumn', {
					text : langs.use_lic,
					dataIndex : 'use_lic',
					showOnly : true,
					width : 100
				}) ],
				store : Ext.create('Ext.data.Store', {
					autoLoad : false,
					fields : [ 'seq', 'user_id', 'ip_addr', 'ip_addr_d', 'use_lic' ],
					proxy : {
						type : 'memory',
						reader : {
							type : 'json'
						}
					}
				})
			} ],
			buttons : [ {
				xtype : 'button',
				text : langs.close,
				handler : function() {
					this.up('window').close();
				}
			} ],
			loadDatas : function() {
				var me = this;

				var initQueryStore = Ext.create('Ext.data.Store', {
					autoLoad : false,
					fields : [ 'griduser', 'head' ],
					proxy : {
						type : 'ajax',
						url : './LoginInfo.dsc',
						actionMethods : {
							read : 'POST'
						},
						reader : {
							type : 'json'
						},
						extraParams : {
							DCITag : document.getElementById("DCITag").value,
							action : 'userlist'
						}
					}
				});

				initQueryStore.load(function(record) {
					if (record.length > 0) {
						me.items.get(1).getStore().loadData(record[0].get("griduser"));
						if (record[0].get("head") != null) {
							me.items.get(0).items.get(1).setValue(record[0].get("head")["onlineUser"]);
							me.items.get(0).items.get(2).setValue(record[0].get("head")["usedLic"]);
							me.items.get(0).items.get(3).setValue(record[0].get("head")["licLimit"]);
						}
					}
				});

			}
		});
		relationConfigWin.loadDatas();
		relationConfigWin.show();
	});

}