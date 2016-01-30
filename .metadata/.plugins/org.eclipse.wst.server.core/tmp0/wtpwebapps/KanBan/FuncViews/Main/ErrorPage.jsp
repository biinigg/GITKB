<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title><dcitag:multiLang langKey="errpage"></dcitag:multiLang></title>
<link rel="stylesheet" type="text/css" href="./../../extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./../../dcicss/dcicss.css" />
<script type="text/javascript" src="./../../extjs/js/ext-all.js"></script>
<script type="text/javascript" src="./../../extjs/js/ext-lang-zh_CN.js"></script>
<script type="text/javascript" charset="UTF-8">
	Ext.onReady(function() {
		var langs;
		var keys = [ "errmsg", "back_to_login" ];

		setMultiLangKeys(keys);
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				langs = buildMultiLangObjct(keys, records[0].get('langValues'));
				showErrPage();
			}
		});

		function showErrPage() {
			if (langs.errmsg == "errmsg") {
				langs.errmsg = "錯誤訊息";
			}
			if (langs.back_to_login == "back_to_login") {
				langs.back_to_login = "回登入頁面";
			}

			var code = document.getElementById("ErrorContent").innerHTML.replace(/^\s+/, "").replace(/\s+$/, "");

			if (code == 'dcie10') {
				document.getElementById("ErrorContent").innerHTML = "資料庫連線異常";
			}

			Ext.create('Ext.Panel', {
				title : langs.errmsg,
				frame : true,
				layout : 'absolute',
				deferredRender : false,
				renderTo : "ErrorPanel",
				contentEl : 'ErrorContent',
				width : 500,
				height : 150,
				bodyPadding : 5,
				items : [ {
					xtype : 'button',
					text : langs.back_to_login,
					renderer : "component",
					checkSession : false,
					width : 75,
					x : 210,
					y : 90,
					handler : function() {
						window.location = "./../../";
					}
				} ]
			});
		}
	});
</script>

</head>
<body>
	<div id="ErrorPanel" align="center"></div>
	<div id="ErrorContent" class="x-hide-display">
		<dcitag:multiLang requestTag="errcode"></dcitag:multiLang>
	</div>

</body>
</html>