<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="./../../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title><dcitag:reqParam paramName="func_name"></dcitag:reqParam></title>
<link rel="stylesheet" type="text/css"
	href="./../../extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./../../dcicss/dcicss.css" />
<script type="text/javascript" src="./../../extjs/js/ext-all.js"></script>
<script type="text/javascript" src="./../../dcijs/dci-all.js"></script>
<script type="text/javascript"
	src="<dcitag:extLangFile langDirPath="./../../extjs/js" useType="2"/>"></script>
<script type="text/javascript" charset="UTF-8">
var test;
	pagekey = '<dcitag:reqAttr attrName="pagekey"></dcitag:reqAttr>';
	globeuid = '<dcitag:reqAttr attrName="uid"></dcitag:reqAttr>';
	function loadJS(file) {
	    var jsFile = document.createElement('script');
	    jsFile.type = 'text/javascript';
	    jsFile.src = file;
	    test=document.body;
	    document.body.appendChild(jsFile);
	}
	this.dcistore.setUid(globeuid);
	this.dcistore.load(function(records) {
		if (records != null && records.length == 1) {
			var postvalue = records[0].get('dcitagValue');
			Ext.Ajax.request({
				method : 'POST',
				url : './../../EKB/OpenLink/IndKanBan.dsc',
				params : {
					DCITag : postvalue,
					uid : globeuid,
					action : 'getparams',
					pagekey : pagekey
				},
				success : function(response) {
					// console.log(response.responseText);
					if (response.responseText.indexOf("@dcifiltererrtag@$") != -1) {
						var result = response.responseText.split('$');
						if (result.length >= 2) {
							var resultdata = Ext.JSON.decode(result[1]);
							Ext.Msg.alert(langs.errmsg, resultdata.msg, function() {
								iserror = true;
								logout(postvalue, globeuid);
								window.location = resultdata.result;
							});
						}
					} else {
						var result = Ext.JSON.decode(response.responseText);
						if (result.success == true) {
							if(result.params.func_id.charAt(0)=='J'&&result.params.func_id.length==4){
									loadJS('./../../Customer/js/C02420302/IndKanBan02420302.js');
								}else{
									loadJS('./../../FuncViews/js/IndKanBan.js');
							}
							
						} else {
							Ext.Msg.alert(langs.errmsg, langs.kanban_not_exist);
						}
					}
				}
			});
		}
	});
	
	
	</script>
</head>
<body>
	<div id="indkbPage" class="indpage_div"></div>
</body>
</html>