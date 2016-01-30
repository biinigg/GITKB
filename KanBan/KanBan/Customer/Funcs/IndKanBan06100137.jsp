<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title><dcitag:reqParam paramName="func_name"></dcitag:reqParam></title>
<link rel="stylesheet" type="text/css" href="./../../extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./../../dcicss/dcicss.css" />
<script type="text/javascript" src="./../../extjs/js/ext-all.js"></script>
<script type="text/javascript" src="./../../dcijs/dci-all.js"></script>

<script type="text/javascript" src="<dcitag:extLangFile langDirPath="./../../extjs/js" useType="2"/>"></script>
<script type="text/javascript" src="./../../Customer/js/C06100137/IndKanBan06100137.js"></script>

<script type="text/javascript" charset="UTF-8">
	pagekey = '<dcitag:reqAttr attrName="pagekey"></dcitag:reqAttr>';
	globeuid = '<dcitag:reqAttr attrName="uid"></dcitag:reqAttr>';
</script>
</head>
<body>
	<div id="indkbPage" class="indpage_div"></div>
</body>
</html>