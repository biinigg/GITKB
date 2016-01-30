<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title>KanBan Config Setting</title>
<link rel="stylesheet" type="text/css" href="./../../extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./../../dcicss/dcicss.css" />
<script type="text/javascript" src="./../../extjs/js/ext-all.js"></script>
<script type="text/javascript" src="./../../extjs/js/ext-lang-zh_TW.js"></script>
<script type="text/javascript" src="./../../dcijs/dci-all.js"></script>
<script type="text/javascript" src="./../../dcijs/dci-datas.js"></script>
<script type="text/javascript" src="./../js/Configs/SysDBConfig.js"></script>

<script type="text/javascript">
	Ext.onReady(function() {
		Ext.QuickTips.init();
		//alert('start');
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				showconfig(records[0].get('dcitagValue'));
			}
		});
	});
</script>


</head>
<body>
	<div id="ConfigForm" align="center"></div>
</body>
</html>