<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><dcitag:multiLang langKey="filename" /></title>
<script type="text/javascript" src="./../../codemirror/mode/sql.js"></script>

<script type="text/javascript" charset="UTF-8">
	Ext.onReady(function() {
		var langs;
		var keys = [ "id" ];

		setMultiLangKeys(keys);
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				langs = buildMultiLangObjct(keys, records[0].get('langValues'));
				showpage();
			}
		});

		function showpage() {
			var sqleditor = Ext.create('Ext.form.TextArea', {
				width : '100%',
				x : 0,
				y : 50
			});

			var main = Ext.create('DCI.Customer.SubPanel', {
				id : 'SQLEditorMain2',
				renderTo : 'test',
				border : 0,
				bodyPadding : '0 5 5 5',
				items : [ sqleditor ]
			});

			/*  CodeMirror(document.getElementById(sqleditor.getId()), {
				//value : "select * from test where t1 = '1'\n",
				mode : "text/x-sql",
			    lineNumbers: true,
			});  */
			console.log(sqleditor.getId());
			CodeMirror.fromTextArea(document.getElementById(sqleditor.getId() + "-inputEl"), {
				mode : "text/x-sql",
				lineNumbers : true,
			});
		}

	});
</script>
</head>
<body>
	<div id="test" style="height: 100vh; background: yellow"></div>
</body>
</html>