package com.dsc.dci.customer.c02420302.jweb.funcs.configs.combineconfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.customer.c02420302.sqlcode.funcs.configs.sqlCombineConfig02420302;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.Singleton;

public class CombineConfig02420302 {
	private sqlCombineConfig02420302 cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;
	private String currLang;
	public CombineConfig02420302(HttpSession session, String uid) {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlCombineConfig02420302();
		this.dbctrl = new DBControl();
		APControl apc = new APControl();
		if (session == null) {
			this.currLang = null;
		} else {
			this.currLang = apc.getUserInfoFromSession(session, uid, "lang");
		}
	}

	public HashMap<String, Object> getIntiData(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> conns = null;
		// ArrayList<HashMap<String, Object>> roles = null;
		ArrayList<HashMap<String, Object>> funcs = null;
		HashMap<String, Object> tmp = null;
		Singleton s = Singleton.getInstance();
		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getInitData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, lang);
			rs = ps.executeQuery();

			conns = new ArrayList<HashMap<String, Object>>();
			// roles = new ArrayList<HashMap<String, Object>>();
			funcs = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("value"));

				if (rs.getString("tp").equals("1")) {
					tmp.put("label", s.getLanguage(lang, rs.getString("label")));
					conns.add(tmp);
				} else if (rs.getString("tp").equals("2")) {
					tmp.put("label", rs.getString("label"));
					tmp.put("package", rs.getString("package"));
					funcs.add(tmp);
				}
			}

			datas = new HashMap<String, Object>();
			datas.put("conns", conns);
			// datas.put("roles", roles);
			datas.put("funcs", funcs);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	public HashMap<String, Object> allData(String strpage, String strpagesize, String filter) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> items = null;
		HashMap<String, Object> tmp = null;
		int cnt = 0;
		int page = 0;
		int pagesize = 0;
		int totalRow = 0;
		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.allData(filter), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			items = new ArrayList<HashMap<String, Object>>();
			if (DCIString.isNullOrEmpty(strpage)) {
				page = 1;
			} else {
				page = Integer.parseInt(strpage);
			}
			if (DCIString.isNullOrEmpty(strpagesize)) {
				pagesize = APConstants.getDefaultpagesize();
			} else {
				pagesize = Integer.parseInt(strpagesize);
			}
			if (rs.next()) {
				rs.last();
				totalRow = rs.getRow();
				rs.beforeFirst();
				if ((page - 1) * pagesize <= totalRow) {
					if ((page - 1) * pagesize != 0) {
						rs.absolute((page - 1) * pagesize);
					}
					cnt = 1;
					while (rs.next()) {
						tmp = new HashMap<String, Object>();
						tmp.put("id", cnt);
						tmp.put("ckb_id", rs.getString("ckb_id"));
						tmp.put("ckb_name", rs.getString("ckb_name"));
						tmp.put("head_id", rs.getString("head_kbid"));
						tmp.put("body_id", rs.getString("body_kbid"));
						tmp.put("combinecolumn", rs.getString("combinecolumn"));
						tmp.put("conn_list", rs.getString("conn_id"));
						items.add(tmp);
						cnt++;
						if (cnt > pagesize) {
							break;
						}
					}
				}
			} else {
				totalRow = 0;
			}

			datas = new HashMap<String, Object>();
			datas.put("items", items);
			datas.put("total", totalRow);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		// System.out.println("execute getAllUserInfo");

		return datas;
	}

	public HashMap<String, Object> getQueryColumns(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> cols = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.allData(" and 1=2 "), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			if (rs != null) {
				ResultSetMetaData rsmd = rs.getMetaData();
				cols = new ArrayList<HashMap<String, Object>>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					tmp = new HashMap<String, Object>();
					tmp.put("value", rsmd.getColumnName(i).toLowerCase());
					tmp.put("label", Singleton.getInstance().getLanguage(lang, rsmd.getColumnName(i).toLowerCase()));
					cols.add(tmp);
				}
			}

			datas = new HashMap<String, Object>();
			datas.put("cols", cols);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}
	public HashMap<String,Object> validateCombineKanBan(HashMap<String, Object> datas,String lang){
		HashMap<String,Object> result =new HashMap<String,Object>();
		boolean boolResult=false;
		String resultMsg="";
		Singleton s = Singleton.getInstance();
		//String ckb_id=datas.get("ckb_id").toString();
		String ckb_name=datas.get("ckb_name").toString();
		String conn_list=datas.get("conn_list").toString();
		String combinecolumn=datas.get("combinecolumn").toString();
		String head_id=datas.get("head_id").toString();
		String body_id=datas.get("body_id").toString();
		if(ckb_name.equals("")||conn_list.equals("")||head_id.equals("")||body_id.equals("")||combinecolumn.equals("")){
			boolResult=false;
			resultMsg=s.getLanguage(lang, "dcij01");
		}else if(head_id.equals(body_id)){
			boolResult=false;
			resultMsg=s.getLanguage(lang, "dcij02");
		}else{
			boolResult=true;
		}
		result.put("boolResult",boolResult);
		result.put("resultMsg",resultMsg);
		return result;
	}
	public HashMap<String, Object> createCusKanBan(HashMap<String, Object> datas, String mode,String newid){
		HashMap<String, Object> res=new HashMap<String, Object>();
		Connection conn = null;
		PreparedStatement ps1 = null,ps2 = null,ps3 = null,ps4 = null,ps5 = null,ps6 = null;
		PreparedStatement ps7 = null,ps8 = null,ps9 = null,ps10 = null,ps11 = null;
		boolean boolResult =false;
		String resultMsg="";
		try {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				String conn_id=datas.get("conn_list").toString();
				String ckb_id=newid;
				String ckb_name=datas.get("ckb_name").toString();
				ps4 = conn.prepareStatement(this.cmd.delGrid_Format(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps4.setString(1, ckb_id);
				ps4.executeUpdate();
				ps5 = conn.prepareStatement(this.cmd.delSql_Advance_Condition(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps5.setString(1, ckb_id);
				ps5.executeUpdate();
				ps6 = conn.prepareStatement(this.cmd.delSql_Condition(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps6.setString(1, ckb_id);
				ps6.executeUpdate();
				ps7 = conn.prepareStatement(this.cmd.delSql_Init_Params(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps7.setString(1, ckb_id);
				ps7.executeUpdate();
				ps8 = conn.prepareStatement(this.cmd.delSql_Format(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps8.setString(1, ckb_id);
				ps8.executeUpdate();
				if (mode.equals("Add")) {
					ps1 = conn.prepareStatement(this.cmd.delCKBRole_Rule_Info(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps1.setString(1, conn_id);
					ps1.setString(2, ckb_id);
					ps1.executeUpdate();
					ps2 = conn.prepareStatement(this.cmd.delCKBSql_Conn_Mapping(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps2.setString(1, ckb_id);
					ps2.setString(2, conn_id);
					ps2.executeUpdate();
					ps3 = conn.prepareStatement(this.cmd.delCKBMenu_CUS(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps3.setString(1, ckb_id);
					ps3.executeUpdate();
					ps9 = conn.prepareStatement(this.cmd.addCKBRole_Rule_Info(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps9.setString(1, conn_id);
					ps9.setString(2, ckb_id);
					ps9.executeUpdate();
					ps10 = conn.prepareStatement(this.cmd.addCKBSql_Conn_Mapping(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps10.setString(1, ckb_id);
					ps10.setString(2, conn_id);
					ps10.executeUpdate();
					ps11 = conn.prepareStatement(this.cmd.addCKBMenu_CUS(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps11.setString(1, ckb_id);
					ps11.setString(2, ckb_name);
					ps11.executeUpdate();
				} else {
					ps1 = conn.prepareStatement(this.cmd.updCKBRole_Rule_Info(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps1.setString(1, conn_id);
					ps1.setString(2, ckb_id);
					ps1.executeUpdate();
					ps2 = conn.prepareStatement(this.cmd.updCKBSql_Conn_Mapping(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps2.setString(1, conn_id);
					ps2.setString(2, ckb_id);
					ps2.executeUpdate();
					ps3 = conn.prepareStatement(this.cmd.updCKBMenu_CUS(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					ps3.setString(1, ckb_name);
					ps3.setString(2, ckb_id);
					ps3.executeUpdate();
				}
				
				boolResult = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			boolResult = false;
			resultMsg = ex.getMessage();
		} finally {
			this.dbctrl.closeConnection(null, ps1, conn);
			this.dbctrl.closeConnection(null, ps2, null);
			this.dbctrl.closeConnection(null, ps3, null);
			this.dbctrl.closeConnection(null, ps4, null);
			this.dbctrl.closeConnection(null, ps5, null);
			this.dbctrl.closeConnection(null, ps6, null);
			this.dbctrl.closeConnection(null, ps7, null);
			this.dbctrl.closeConnection(null, ps8, null);
			this.dbctrl.closeConnection(null, ps9, null);
			this.dbctrl.closeConnection(null, ps10, null);
			this.dbctrl.closeConnection(null, ps11, null);
			res = new HashMap<String, Object>();
			res.put("success", boolResult);
			res.put("errorMessage", resultMsg);
		}
		return res;
	}
	
	public HashMap<String, Object> saveData(String formDatas, String mode,String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> result = null;
		HashMap<String, Object> resultdata = null;
		HashMap<String, Object> validatedata = null;
		String sql = null;
		boolean boolResult = false;
		String resultMsg = "";
		String newid = null;
		
		try {
			datas = DCIString.jsonTranToObjMap(formDatas);// new
															// ObjectMapper().readValue(formDatas,
															// HashMap.class);
			validatedata=validateCombineKanBan(datas,lang);
			if((Boolean)validatedata.get("boolResult")){
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				if (mode.equals("Add")) {
					sql = this.cmd.addData();
				} else {
					sql = this.cmd.updateData();
				}
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				String conn_id=datas.get("conn_list").toString();
//				if(conn_id.contains("-"))
//					conn_id=conn_id.split("-")[0].trim();
				String head_kbid=datas.get("head_id").toString();
				String body_kbid=datas.get("body_id").toString();
				ps.setString(1, datas.get("ckb_name").toString());
				ps.setString(2, head_kbid);
				ps.setString(3, body_kbid);
				ps.setString(4, datas.get("combinecolumn").toString());
				ps.setString(5, conn_id);
				if (mode.equals("Add")) {
					newid = getMaxID(conn);
				} else {
					newid = datas.get("ckb_id").toString();
				}
				ps.setString(6, newid);
				ps.executeUpdate();

				resultdata = allData("1", "1", " and ckb_id = '" + newid + "'");
				boolResult = true;
			}else{
				boolResult = false;
				resultMsg =(String) validatedata.get("resultMsg");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			boolResult = false;
			resultMsg = ex.getMessage();
		} finally {
			this.dbctrl.closeConnection(null, ps, conn);
			result = new HashMap<String, Object>();
			result.put("success", boolResult);
			result.put("errorMessage", resultMsg);
			result.put("resultData", resultdata);
		}
		if(boolResult){
			HashMap<String,Object> res=createCusKanBan(datas,mode,newid);
			if(!(Boolean)res.get("success")){
				result.put("success", res.get("success"));
				result.put("errorMessage", res.get("errorMessage"));
			}
		}
		return result;
	}
	
	public HashMap<String,Object> getColData(String head_id,String body_id){
		HashMap<String, Object> result = new HashMap<String, Object>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<HashMap<String, Object>> columndatas =null;
		HashMap<String, Object> tmp = null;
//		if(head_id.contains("-"))
//			head_id=head_id.split("-")[0].trim();
//		if(body_id.contains("-"))
//			body_id=body_id.split("-")[0].trim();
		try{
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.columnData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, head_id);
			ps.setString(2, body_id);
			rs = ps.executeQuery();
			columndatas= new ArrayList<HashMap<String, Object>>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("label", rs.getString("col_id").trim());
				tmp.put("value", rs.getString("col_id").trim());
				columndatas.add(tmp);
			}
			result.put("columndatas", columndatas);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return result;
	}
	public HashMap<String,Object> getKBData(String conn_id){
		HashMap<String, Object> result = new HashMap<String, Object>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<HashMap<String, Object>> kbdatas =null;
		HashMap<String, Object> tmp = null;
		try{
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.kbData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, conn_id);
			ps.setString(2, this.currLang);
			rs = ps.executeQuery();
			kbdatas= new ArrayList<HashMap<String, Object>>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("label", rs.getString("sql_id").trim()+"-"+rs.getString("lang_value").trim());
				tmp.put("value", rs.getString("sql_id").trim());
				kbdatas.add(tmp);
			}
			result.put("kbdatas", kbdatas);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return result;
	}
	public HashMap<String,Object> initConnData(){
		HashMap<String, Object> result = new HashMap<String, Object>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		ArrayList<HashMap<String, Object>> conndatas =null;
		ArrayList<HashMap<String, Object>> kbdatas =null;
		ArrayList<HashMap<String, Object>> columndatas =null;
		HashMap<String, Object> tmp = null;
		try{
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.connData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			conndatas = new ArrayList<HashMap<String, Object>>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("label", rs.getString("conn_id")+"-"+rs.getString("conn_name").trim());
				tmp.put("value", rs.getString("conn_id"));
				conndatas.add(tmp);
			}
			String firstConn="";
			rs.beforeFirst();
			if(rs.next()){
				firstConn=rs.getString("conn_id");
			}
			ps2 = conn.prepareStatement(this.cmd.kbData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps2.setString(1, firstConn);
			ps2.setString(2, this.currLang);
			rs2 = ps2.executeQuery();
			kbdatas= new ArrayList<HashMap<String, Object>>();
			while (rs2.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("label", rs2.getString("sql_id")+"-"+rs2.getString("lang_value").trim());
				tmp.put("value", rs2.getString("sql_id"));
				kbdatas.add(tmp);
			}
			
			String firstSql="",secSql="";
			rs2.beforeFirst();
			if(rs2.next()){
				firstSql=rs2.getString("sql_id");
			}
			if(rs2.next()){
				secSql=rs2.getString("sql_id");
			}
			
			ps3 = conn.prepareStatement(this.cmd.columnData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps3.setString(1, firstSql);
			ps3.setString(2, secSql);
			rs3 = ps3.executeQuery();
			columndatas= new ArrayList<HashMap<String, Object>>();
			while (rs3.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("label", rs3.getString("col_id"));
				tmp.put("value", rs3.getString("col_id"));
				columndatas.add(tmp);
			}
			result.put("kbdatas", kbdatas);
			result.put("conndatas", conndatas);
			result.put("columndatas", columndatas);

		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			this.dbctrl.closeConnection(rs, ps, conn);
			this.dbctrl.closeConnection(rs2, ps2, null);
			this.dbctrl.closeConnection(rs3, ps3, null);
		}
		return result;
	}
	
	public HashMap<String,Object> reloadHeadPanel(String recordDatas){
		HashMap<String, Object> result = new HashMap<String, Object>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		ArrayList<HashMap<String, Object>> conndatas =null;
		ArrayList<HashMap<String, Object>> kbdatas =null;
		ArrayList<HashMap<String, Object>> columndatas =null;
		HashMap<String, Object> tmp = null;
		HashMap<String, Object> datas =new HashMap<String, Object>();
		datas=DCIString.jsonTranToObjMap(recordDatas);
//		System.out.println(datas);
		try{
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.connData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			conndatas = new ArrayList<HashMap<String, Object>>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("label", rs.getString("conn_id")+"-"+rs.getString("conn_name").trim());
				tmp.put("value", rs.getString("conn_id"));
				conndatas.add(tmp);
			}
			String filterConn=datas.get("conn_list").toString();
			ps2 = conn.prepareStatement(this.cmd.kbData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps2.setString(1, filterConn);
			ps2.setString(2, this.currLang);
			rs2 = ps2.executeQuery();
			kbdatas= new ArrayList<HashMap<String, Object>>();
			while (rs2.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("label", rs2.getString("sql_id")+"-"+rs2.getString("lang_value").trim());
				tmp.put("value", rs2.getString("sql_id"));
				kbdatas.add(tmp);
			}
			
			String headfilter=datas.get("head_id").toString();
			String bodyfilter=datas.get("body_id").toString();
			ps3 = conn.prepareStatement(this.cmd.columnData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps3.setString(1, headfilter);
			ps3.setString(2, bodyfilter);
			rs3 = ps3.executeQuery();
			columndatas= new ArrayList<HashMap<String, Object>>();
			while (rs3.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("label", rs3.getString("col_id"));
				tmp.put("value", rs3.getString("col_id"));
				columndatas.add(tmp);
			}
			result.put("kbdatas", kbdatas);
			result.put("conndatas", conndatas);
			result.put("columndatas", columndatas);

		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			this.dbctrl.closeConnection(rs, ps, conn);
			this.dbctrl.closeConnection(rs2, ps2, null);
			this.dbctrl.closeConnection(rs3, ps3, null);
		}
		return result;
	}
	public HashMap<String, Object> getAllUsers() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> alldatas = null;
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getAllUsers());
			rs = ps.executeQuery();

			datas = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("label", rs.getString("user_name"));
				tmp.put("value", rs.getString("user_id"));
				datas.add(tmp);
			}

			alldatas = new HashMap<String, Object>();
			alldatas.put("items", datas);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return alldatas;
	}
	public HashMap<String, Object> createLink(String sql_id, String conn_id, String uid, HttpServletRequest request) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		String linkvalue = null;
		String pwd = "";

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);

			if (DCIString.isNullOrEmpty(sql_id) || DCIString.isNullOrEmpty(conn_id)) {
				datas = new HashMap<String, Object>();
				datas.put("success", false);
				datas.put(
						"msg",
						Singleton.getInstance().getLanguage(
								new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"),
								"create_link_fail"));
			} else {
				ps = conn.prepareStatement(this.cmd.getPwd());
				ps.setString(1, uid);
				rs = ps.executeQuery();

				if (rs.next()) {
					if (rs.getObject("user_pwd") == null) {
						pwd = "";
					} else {
						pwd = rs.getString("user_pwd");
					}
				}

				linkvalue = DCIString.Base64Encode(DCIString.strEncode(conn_id + ";" + sql_id + ";" + uid + ";" + pwd
						+ ";" + DCIWebConstants.getPostTagValue()));

				datas = new HashMap<String, Object>();
				datas.put("success", true);
				datas.put(
						"linkvalue",
						request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
								+ request.getContextPath() + "/EKB/OpenLink/IndKanBan.dsc?indkey=" + linkvalue);
				datas.put("msg", "");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			datas = new HashMap<String, Object>();
			datas.put("success", false);
			datas.put("msg", ex.getMessage());
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}
	public HashMap<String, Object> deleteData(String formdatas) {
		Connection conn = null;
		PreparedStatement ps = null,ps2 = null,ps3 = null,ps4 = null;
		PreparedStatement ps5 = null,ps6 = null,ps7 = null,ps8 = null,ps9 = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";
		try {
			datas = DCIString.jsonTranToObjMap(formdatas);// new
															// ObjectMapper().readValue(formdatas,
															// HashMap.class);
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.deleteData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, datas.get("ckb_id").toString());
			ps.executeUpdate();
			ps2 = conn.prepareStatement(this.cmd.deleteCKBRole_Rule_Info(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps2.setString(1, datas.get("ckb_id").toString());
			ps2.executeUpdate();
			ps3 = conn.prepareStatement(this.cmd.deleteCKBSql_Conn_Mapping(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps3.setString(1, datas.get("ckb_id").toString());
			ps3.executeUpdate();
			ps4 = conn.prepareStatement(this.cmd.deleteCKBMenu_CUS(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps4.setString(1, datas.get("ckb_id").toString());
			ps4.executeUpdate();
			
			ps5 = conn.prepareStatement(this.cmd.delGrid_Format(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps5.setString(1, datas.get("ckb_id").toString());
			ps5.executeUpdate();
			ps6 = conn.prepareStatement(this.cmd.delSql_Advance_Condition(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps6.setString(1, datas.get("ckb_id").toString());
			ps6.executeUpdate();
			ps7 = conn.prepareStatement(this.cmd.delSql_Condition(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps7.setString(1, datas.get("ckb_id").toString());
			ps7.executeUpdate();
			ps8 = conn.prepareStatement(this.cmd.delSql_Init_Params(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps8.setString(1, datas.get("ckb_id").toString());
			ps8.executeUpdate();
			ps9 = conn.prepareStatement(this.cmd.delSql_Format(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps9.setString(1, datas.get("ckb_id").toString());
			ps9.executeUpdate();
			boolResult = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			boolResult = false;
			resultMsg = ex.getMessage();
		} finally {
			this.dbctrl.closeConnection(null, ps, conn);
			this.dbctrl.closeConnection(null, ps2, null);
			this.dbctrl.closeConnection(null, ps3, null);
			this.dbctrl.closeConnection(null, ps4, null);
			this.dbctrl.closeConnection(null, ps5, null);
			this.dbctrl.closeConnection(null, ps6, null);
			this.dbctrl.closeConnection(null, ps7, null);
			this.dbctrl.closeConnection(null, ps8, null);
			this.dbctrl.closeConnection(null, ps9, null);
			result = new HashMap<String, Object>();
			result.put("success", boolResult);
			result.put("errorMessage", resultMsg);
		}
		return result;
	}

	private String getMaxID(Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String id = null;
		boolean reconn = false;
		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps = conn.prepareStatement(this.cmd.maxID(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getString(1);
				if (DCIString.isNullOrEmpty(id)) {
					id = "J001";
				} else {
					id = id.substring(0, 1)
							+ DCIString.strFix(String.valueOf(Integer.parseInt(id.substring(1)) + 1), 3, true, "0");
				}
			} else {
				id = "J001";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			id = "";
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}
		return id;
	}

}
