<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Enumeration"%>
<%@ taglib uri="./../../../dcitag/dcitag.tld" prefix="dcitag"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./../../dcicss/lkb01.css" />
<script type="text/javascript">
	Ext.onReady(function() {
		var localKeys = [ "refresh", "auto_refresh", "update_time", "plz_stop_auto_refresh", "idle" ];
		var keys = localKeys.concat(globeKeys);
		var uid = '<dcitag:reqParam paramName="uid"></dcitag:reqParam>';
		this.dcistore.setMultiLangKeys(keys);
		this.dcistore.setUid(uid);
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				var langs = buildMultiLangObjct(keys, records[0].get('langValues'));
				var infos = new Object();
				infos["func_id"] = '<dcitag:reqParam paramName="func_id"></dcitag:reqParam>';
				infos["func_name"] = '<dcitag:reqParam paramName="func_name"></dcitag:reqParam>';
				infos["conn_id"] = '<dcitag:reqParam paramName="conn_id"></dcitag:reqParam>';
				infos["relation_filter"] = '<dcitag:reqParam paramName="filter"></dcitag:reqParam>';
				infos["can_edit"] = '<dcitag:reqParam paramName="canEdit"></dcitag:reqParam>';

				//var funclangs = buildMultiLangObjct(keys, records[0].get('langValues'));
				Ext.Ajax.request({
					method : 'POST',
					url : '../../Funcs/LKB/LayoutKanBan.dsc',
					params : {
						DCITag : posvalue,
						uid : uid,
						action : 'getlkbinfo',
						funcid : infos.func_id,
						connid : infos.conn_id
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
							if (result.length > 0 && result[0].result) {
								loadLKB(records[0].get('dcitagValue'), infos, result[0].imgurl, result[0].lkbcolor, langs, uid);
								//showIndexPage(records[0].get('dcitagValue'), records[0].get('frowardTagValue'), langs, result.uid);
							} else {
								iserror = true;
								pagelogout();
								window.location = "/KanBan/FuncViews/Main/ErrorPage.jsp?errcode=dcie01&ltp=" + records[0].get('langType');
							}
						}
					},
					failure : function(f, a) {
						Ext.MessageBox.alert(langs.errmsg, langs.system_error);
					}
				});
			}
		});
	});

	function loadLKB(postvalue, infos, imgurl, lkbcolor, langs, uid) {
		var timerisrunning = false;
		var timergap = 180;

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

		function refreshTab() {
			if (panel != null) {
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
						url : '../../Funcs/LKB/LayoutKanBan.dsc',
						actionMethods : {
							read : 'POST'
						},
						reader : {
							type : 'json'
						},
						extraParams : {
							action : 'refresh',
							DCITag : postvalue,
							uid : uid,
							funcid : infos.func_id,
							connid : infos.conn_id
						}
					}
				});

				dciblkstore.load(function(records) {
					if (records != null && records.length > 0) {
						var p = null;

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

				Ext.fly(refalshTime.getEl()).update(getCurrentTime());

			}
		}

		function timerControl(task, timerbtn, postvalue, timergap) {
			if (timerisrunning) {
				task.stop();
				timerisrunning = false;
				// this.setText('start');
				timerbtn.setIconCls('startbutton');
				indexstatusbar.setStatus({
					text : langs.idle,
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
				refreshTab();
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
		var refalshTime = Ext.create('Ext.toolbar.TextItem', {
			text : getCurrentTime()
		});

		var btnTimerStart = Ext.create('Ext.Button', {
			enableToggle : true,
			iconCls : 'startbutton',
			tooltip : langs.auto_refresh,
			handler : function() {
				if (mainPanel.items != null && mainPanel.items.length != 0) {
					timerControl(task, this, postvalue, timergap);
				} else {
					this.toggle(false);
				}
			}
		});

		var btnRefresh = Ext.create('Ext.Button', {
			iconCls : 'refreshbutton',
			tooltip : langs.refresh,
			handler : function() {
				refreshTab();
			}
		});

		var timergapcombo = Ext.create('Ext.form.field.ComboBox', {
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
							Ext.MessageBox.alert(langs.errmsg,langs.plz_stop_auto_refresh);
							combo.setValue(oldValue);
						} else {
							timergap = newValue;
						}
					}
				}
			}
		});

		timergapcombo.setValue("180");

		var indexstatusbar = Ext.create('Ext.ux.StatusBar', {
			defaultText : langs.idle,
			defaultIconCls : 'x-status-valid',
			items : [ '-', btnRefresh, '-', btnTimerStart, timergapcombo, '-', langs.update_time + " :", refalshTime ]
		});

		var runner = new Ext.util.TaskRunner();

		var task = runner.newTask({
			run : function() {
				refreshTab();
			}
		});

		var panel = Ext.create("Ext.panel.Panel", {
			layout : 'absolute',
			autoScroll : true,
			border : false,
			bodyBorder : false,
			bodyStyle : 'background : ' + lkbcolor + ';',
			loader : {
				url : "../../Funcs/LKB/LayoutKanBan.dsc",
				params : {
					DCITag : postvalue,
					uid : uid,
					funcid : infos.func_id,
					connid : infos.conn_id
				}
			}
		});

		if (imgurl != null && imgurl != '') {
			var img = Ext.create('Ext.Img', {
				src : imgurl
			});
			panel.add(img);
		}

		var main = Ext.create('DCI.Customer.SubPanel', {
			id : infos.func_id + 'Main',
			renderTo : infos.func_id + 'Page',
			border : 0,
			minWidth : 1120,
			bodyPadding : '0 5 5 5',
			minHeight : 520,
			layout : 'fit',
			pagetype : 'kanban',
			widthChangeControls : [ panel ],
			items : [ panel ],
			bbar : indexstatusbar,
			bodyStyle : {
				background : 'lightblue',
				padding : '0px',
				margin : '0px'
			},
			focusPage : function() {
				var me = this;
				if (timerisrunning) {
					task.start(timergap * 1000);
				}
				if (Ext.get(me.renderTo) != null) {
					me.resize(Ext.get(me.renderTo).getWidth(), Ext.get(me.renderTo).getHeight());
				}
			},
			leavePage : function() {
				if (timerisrunning) {
					task.stop();
				}
			}
		});
		main.resize(Ext.get(infos.func_id + 'Main').getWidth(), Ext.get(infos.func_id + 'Page').getHeight());
		panel.loader.load({
			renderer : "component"
		});
	}
</script>

</head>
<body>
	<!--  <input type="button" value="OK" data-qtitle="OK Button" data-qwidth="300"
     data-qtip="<table><tr><td>This is a quick tip</td></tr><tr><td> from markup!</td></tr></table>"/>
<table><tr><td>-->
	<div id="<dcitag:reqParam paramName="func_id"></dcitag:reqParam>Page" class="page_div"
		style="background : <dcitag:reqParam paramName="lkbcolor"></dcitag:reqParam>;
	border-left: 1px solid #000;
	border-right: 1px solid #000;
	padding: 0 0 0 0;
	margin: auto;
	font: 1.5em arial, verdana, sans-serif;
	width: 100%;
	min-height: 100%;"></div>
	<!--</td></tr></table>-->
</body>
</html>