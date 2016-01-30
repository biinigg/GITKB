<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>System Functions</title>
<link rel="stylesheet" type="text/css" href="./../../extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./../../dcicss/dcicss.css" />
<script type="text/javascript" src="./../../extjs/js/ext-all.js"></script>
<script type="text/javascript" src="./../../dcijs/dci-all.js"></script>

<script type="text/javascript" charset="UTF-8">
	Ext.onReady(function() {
		var keys = [ "save_success" ];
		setMultiLangKeys(keys);

		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				var langs;
				langs = buildMultiLangObjct(keys, records[0].get('langValues'));
				showPage(records[0].get('dcitagValue'), langs);
			}
		});

		function showPage(postvalue, langs) {
			var main = Ext.create('Ext.panel.Panel', {
				renderTo : 'SystemFuncsPage',
				title : 'System Functions',
				border : 1,
				bodyPadding : '0 5 5 5',
				layout : 'absolute',
				height : 300,
				width : 300,
				items : [ {
					xtype : 'button',
					text : '重匯多語系',
					x : 0,
					y : 0,
					handler : function() {
						var me = this.up();
						
						me.setLoading(true);
						Ext.Ajax.request({
							method : 'POST',
							url : '../../SystemFuncs.dsc',
							params : {
								DCITag : postvalue,
								action : 'langReload'
							},
							success : function(a) {
								var result = Ext.JSON.decode(a.responseText);
								if (result.result) {
									Ext.Msg.alert("執行結果", "執行完成");
								} else {
									Ext.Msg.alert("執行結果", "執行失敗");
								}
								me.setLoading(false);
							},
							failure : function(f, action) {
								Ext.Msg.alert("執行結果", "執行失敗");
								me.setLoading(false);
							}
						});
					}
				} ]
			});
		}
	});
</script>
</head>
<body>
	<div id="SystemFuncsPage" align="center"></div>
</body>
</html>