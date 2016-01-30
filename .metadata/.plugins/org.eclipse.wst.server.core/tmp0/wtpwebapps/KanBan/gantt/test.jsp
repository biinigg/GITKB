<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN">
<html>
<head>
<link href="./../../gantt/css/Styles/Examples.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="UTF-8">
	Ext.onReady(function() {
		var localKeys = [ "errmsg", "system_error", "save_msg", "delete_msg", "delete_fail", "delete_success", "delete_confirm_title", "delete_confirm_msg", "delete_result_title",
				"save_fail", "save_success", "save_result_title", "save_fail", "system_build", "conn_id", "conn_name", "conn_desc", "db_addr", "db_name", "db_user", "db_pwd",
				"db_type", "db_port", "visible", "enable", "disable", "toolbar_query_title", "system_def_can_not_edit", "info_msg_title", "conn_check_fail", "conn_check_success",
				"confirm_title", "data_lose_warning", "check" ];
		var keys = localKeys.concat(globeKeys);
		var uid = '<dcitag:reqParam paramName="uid"></dcitag:reqParam>';
		this.dcistore.setMultiLangKeys(keys);
		this.dcistore.setUid(uid);
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				var langs = buildMultiLangObjct(keys, records[0].get('langValues'));
				showPage(records[0].get('dcitagValue'), langs, '<dcitag:reqParam paramName="canEdit"></dcitag:reqParam>', uid);
			}
		});

		function showPage(postvalue, langs, recvAuth, uid) {
			var canEdit = recvAuth == "1";
			var headpanel = Ext.create('Ext.panel.Panel', {
				height : 400,
				width : '100%',
				html : '<iframe width="100%" height="400" src="../../gantt/test2.html"></iframe>'
			});

			var main = Ext.create('DCI.Customer.SubPanel', {
				id : 'GanttMain',
				renderTo : 'GanttPage',
				border : 0,
				bodyPadding : '0 5 5 5',
				layout : 'fit',
				widthChangeControls : [ headpanel ],
				items : [ headpanel]
			});


			main.resize(Ext.get("GanttPage").getWidth(), Ext.get("GanttPage").getHeight());
		}
	});
</script>
</head>
<body>
	<div id="GanttPage" class="page_div"></div>
</body>
</html>