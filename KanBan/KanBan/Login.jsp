<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="./dcitag/dcitag.tld" prefix="dcitag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title><dcitag:multiLang langKey="title_index" /></title>
<link rel="stylesheet" type="text/css" href="./extjs/resources/css/ext-all.css" />
<script type="text/javascript" src="./extjs/js/ext-all.js"></script>
<script type="text/javascript" src="./dcijs/dci-all.js"></script>

<script type="text/javascript" src="<dcitag:extLangFile langDirPath="./extjs/js" useType="0"/>"></script>
<script type="text/javascript" src="./FuncViews/js/Login.js"></script>

</head>
<body style="background-color: #E0E8F3;">
	<form action="./Login.dsc" method="post" onsubmit="return formCheck();">
		<div style="width: 100%; height: 100vh; display: table;">
			<div style="display: table-cell; vertical-align: middle;">
				<table border="0" align="center" id="loginmain" cellpadding="0" cellspacing="0" style="width: 730; height: 550; background-image: url(images/background/KanBan_login_TC.jpg); background-repeat: no-repeat;">
					<tr>
						<td width="420" height="185" style="vertical-align: text-top;"><div id="labelver"></div></td>
						<td width="294"></td>
					</tr>
					<tr>
						<td width="420" height="150"></td>
						<td width="294">
							<table width="100%" height="150" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr height="40">
									<td width="40%"><div align="right" style="padding-right: 10px" id="labelid">
											<dcitag:multiLang langKey="user_id" />
										</div></td>
									<td width="60%"><input type="text" id="uid" name="uid" maxlength="10" style="width: 90%"></input></td>
								</tr>
								<tr height="40">
									<td><div align="right" style="padding-right: 10px" id="labelpwd">
											<dcitag:multiLang langKey="user_pwd" />
										</div></td>
									<td><input type="password" id="pwd" name="pwd" maxlength="10" style="width: 90%"></input></td>
								</tr>
								<tr height="40">
									<td><div align="right" style="padding-right: 10px" id="labeltp">
											<dcitag:multiLang langKey="lang_type" />
										</div></td>
									<td><select id="langtp" name="langtp" style="width: 90%" ONCHANGE="changeLang();">
											<option value="CHT">
												<dcitag:multiLang langKey="lang_cht" />
											</option>
											<option value="CHS">
												<dcitag:multiLang langKey="lang_chs" />
											</option>
									</select></td>
								</tr>
								<tr height="30">
									<td colspan="2">
										<table width="95%">
											<tr>
												<td align="right"><input type="button" id="newie" value="<dcitag:multiLang langKey="newie" />" style="visibility: hidden" onclick="openIE();"><input type="button" id="userlistbtn" value="<dcitag:multiLang langKey="user_list" />" onclick="showUserList();"><input
													type="button" id="resetbtn" value="<dcitag:multiLang langKey="reset" />" onclick="resetForm();"><input type="submit" id="submit" value="<dcitag:multiLang langKey="login" />"></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>

									<td colspan="2"><input type="hidden" id="action" name="action" /><input type="hidden" id="checkcode" name="checkcode" /> <input type="hidden" id="DCITag" name="DCITag" /></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="420" height="43"></td>
						<td width="294">
							<table width="100%" height="43">
								<tr>
									<td align="center"><dcitag:multiLang requestTag="ecode" /></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td width="420"></td>
						<td width="294"></td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</body>
</html>