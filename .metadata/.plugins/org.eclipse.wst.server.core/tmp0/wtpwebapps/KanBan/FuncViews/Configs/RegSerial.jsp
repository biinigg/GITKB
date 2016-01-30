<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title><dcitag:multiLang langKey="register_setup" /></title>
<link rel="stylesheet" type="text/css" href="./../../extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./../../dcicss/dcicss.css" />
<script type="text/javascript" src="./../../extjs/js/ext-all.js"></script>
<script type="text/javascript" src="./../../dcijs/dci-all.js"></script>
<script type="text/javascript" src="./../../extjs/js/ext-lang-zh_TW.js"></script>
<script type="text/javascript" src="./../js/Configs/RegSerial.js"></script>

<script type="text/javascript" charset="UTF-8">
	Ext.onReady(function() {
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				showPage(records[0].get('dcitagValue'));
			}
		});
	});
</script>
</head>
<body>
	<div id="RegSerialForm"></div>
</body>
</html>