<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./../../dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title><dcitag:multiLang langKey="title_index" /></title>

<link rel="stylesheet" type="text/css" href="./../../extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./../../dcicss/dcicss.css" />
<script type="text/javascript" src="./../../codemirror/js/codemirror.js"></script>
<script type="text/javascript" src="./../../extjs/js/ext-all.js"></script>
<script type="text/javascript" src="./../../dcijs/dci-all.js"></script>
<script>
	
</script>
<script type="text/javascript" src="<dcitag:extLangFile langDirPath="./../../extjs/js" useType="1"/>"></script>
<script type="text/javascript" src="./../js/Main/Index.js"></script>
<script type="text/javascript">
	var globeKeys = [ "close", "search", "save", "add", "_delete", "edit", "exit", "errmsg", "save_format", "to_edit", "to_view", "mode_view", "mode_edit", "no_edit_auth",
			"expand", "collapse", "refersh", "clear", "system_error", "ok", "cancel", "first_row", "back_row", "next_row", "last_row" ];
	//var cnt = 0;
	var posvalue;
	var islogout = false;
	var isrefresh = false;
	var iserror = false;
	var idxuid;
	window.onbeforeunload = check;
	document.onkeydown = getrefresh;

	function check(e) {
		//console.log(iserror);		

		if (!isrefresh && !islogout && !iserror) {
			e = e || window.event;

			if (e) {
				e.returnValue = '<dcitag:multiLang langKey="logout_confirm" />';
			}

			return '<dcitag:multiLang langKey="logout_confirm" />';
		}

	}

	function pagelogout() {
		if (!isrefresh && !islogout) {
			try {
				logoutF(posvalue);
			} catch (e) {

			}
		}
	}

	function getrefresh() {
		//console.log(event.keyCode);
		if (event.keyCode == 116 || (event.ctrlKey && event.keyCode == 82)) {
			isrefresh = true;
		}
	}
</script>

<script type="text/javascript" charset="UTF-8">
	Ext.onReady(function() {
		//修正日期元件中文顯示
		var proto = Ext.picker.Date.prototype, date = Ext.Date;

		proto.monthNames = date.monthNames;
		proto.dayNames = date.dayNames;
		proto.format = date.defaultFormat;
		//

		Ext.QuickTips.init();
		var localKeys = [ "tree_panel_title", "refresh", "auto_refresh", "conn_target", "func_tree_root", "add_folder_title", "node_lang_panel", "user_id", "user_name",
				"group_name", "login_time", "functions", "favorties", "no_f_node_selected", "system_error", "save_success", "save_result_title", "save_fail", "add_success",
				"add_result_title", "add_fail", "clear_user_folder", "clear_user_folder_title", "delete_fail", "delete_success", "delete_confirm_title", "delete_confirm_msg",
				"delete_result_title", "clear_success", "clear_result_title", "clear_fail", "clear_success", "clear_fail", "kanban_not_exist", "favor_exist", "not_program",
				"load_fail", "add_favorite", "logout", "kanban_loop", "logout_confirm", "logout_fail", "confirm_title", "no_kanban_can_show", "confirm_title", "data_lose_warning",
				"clear_favor_folder_title", "clear_favor_folder", "get_task_gap_fail", "cus_format", "have_node_warning", "remove_node_before_delete" ];
		var keys = localKeys.concat(globeKeys);
		this.dcistore.setMultiLangKeys(keys);
		var loginKey = '<dcitag:reqParam paramName="key"></dcitag:reqParam>';
		this.dcistore.setLoginKey(loginKey);
		this.dcistore.load(function(records) {
			if (records != null && records.length == 1) {
				var langs = buildMultiLangObjct(keys, records[0].get('langValues'));
				posvalue = records[0].get('dcitagValue');

				Ext.Ajax.request({
					method : 'POST',
					url : './../../PublicCtrl.dsc',
					params : {
						DCITag : posvalue,
						uid : records[0].get('userID'),
						action : 'getuid',
						key : loginKey
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
							if (result.result) {
								idxuid = result.uid;
								showIndexPage(records[0].get('dcitagValue'), records[0].get('frowardTagValue'), langs, result.uid);
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

	/* function test() {
		alert("1234");
		if (mainPanel == null) {
			alert("null");
		} else {
			var o = new Object();
			o["func_package"] = "EKB";
			o["url"] = "./../../FuncViews/Funcs/EKB/KanBan.jsp";
			o["text"] = "test";
			o["can_edit"] = "0";
			o["filter"] = "";
			mainPanel.beforeAddCheck("K0010", "C002", o, false);
		}
	} */
</script>



</head>
<body onunload="pagelogout()">
	<form action="./../../Login.dsc" method="POST" id="logoutform">
		<input type="hidden" id="logoutaction" name="action" /> <input type="hidden" id="logouttag" name="DCITag" />
	</form>
</body>
</html>

