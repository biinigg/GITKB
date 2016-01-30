package com.dsc.dci.customer.c02420302.jweb.funcs.ekb.subkanban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.customer.c02420302.sqlcode.funcs.ekb.sqlSubKanBan02420302;
public class SubKanBan02420302 {
	private sqlSubKanBan02420302 cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;
	private String currLang;
	public SubKanBan02420302(HttpSession session, String uid) {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlSubKanBan02420302();
		this.dbctrl = new DBControl();
		APControl apc = new APControl();
		if (session == null) {
			this.currLang = null;
		} else {
			this.currLang = apc.getUserInfoFromSession(session, uid, "lang");
		}
	}
	public HashMap<String, Object> getInitCusData(String ckb_id) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean boolresult=false;
		String resultMsg="";
		try{
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.initData());
			ps.setString(1, this.currLang);
			ps.setString(2, this.currLang);
			ps.setString(3, ckb_id);
			rs = ps.executeQuery();
			if(rs.next()){
				res.put("ckb_id", rs.getString("ckb_id"));
				res.put("ckb_name",rs.getString("ckb_name") );
				res.put("head_kbid",rs.getString("head_kbid") );
				res.put("body_kbid",rs.getString("body_kbid") );
				res.put("combinecolumn",rs.getString("combinecolumn") );
				res.put("conn_id",rs.getString("conn_id") );
				res.put("head_name",rs.getString("head_name") );
				res.put("body_name",rs.getString("body_name") );
			}
			boolresult=true;
		}catch(Exception ex){
			ex.printStackTrace();
			resultMsg=ex.getMessage();
		}finally{
			res.put("success",boolresult);
			res.put("msg",resultMsg);
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return res;
	}
	public HashMap<String,Object> getInitData(String uid,String sql_id){
		HashMap<String, Object> res = new HashMap<String, Object>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		boolean boolresult=false;
		String resultMsg="";
		ArrayList<HashMap<String, Object>> advs = null;
		HashMap<String, Object> tmp2 = null;
		HashMap<String, Object> tmp3 = null;
		HashMap<String, Object> tmp4 = null;
		try{
			
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.initHPheadData());
			ps.setString(1, uid);
			ps.setString(2, uid);
			ps.setString(3, uid);
			ps.setString(4, uid);
			ps.setString(5, sql_id);
			rs = ps.executeQuery();
			String head_id="",body_id="";
			if(rs.next()){
				head_id= rs.getString("head_kbid");
				body_id= rs.getString("body_kbid");
				tmp3 = new HashMap<String, Object>();
				tmp3.put("op_is_open", rs.getString("params_value"));
				tmp3.put("filter_col", rs.getString("filter_col"));
				tmp3.put("filter_condi", rs.getString("filter_condi"));
				tmp3.put("filter_value", rs.getString("filter_value"));
				tmp3.put("sort_col", rs.getString("sort_col"));
				tmp3.put("sort_type", rs.getString("sort_type"));
				tmp3.put("page_size", rs.getString("page_size"));
				tmp3.put("grid_font_size", rs.getString("grid_font_size"));
				tmp3.put("grid_font_color", rs.getString("grid_font_color"));
				tmp3.put("grid_row_height", rs.getString("grid_row_height"));
				tmp3.put("grid_row_color", rs.getString("grid_row_color"));
				tmp3.put("grid_row_even_color", rs.getString("grid_row_even_color"));
				if (rs.getString("popup_width") == null) {
					tmp3.put("popup_width", 200);
				} else {
					if (DCIString.isInteger(rs.getString("popup_width"))) {
						tmp3.put("popup_width", rs.getInt("popup_width"));
					} else {
						tmp3.put("popup_width", 200);
					}
				}
//				advs=new ArrayList<HashMap<String, Object>>();
//				tmp4 = new HashMap<String, Object>();
//				tmp4.put("condi_relation", rs.getString("condi_relation"));
//				tmp4.put("condi_col", rs.getString("filter_col"));
//				tmp4.put("condi_type", rs.getString("filter_condi"));
//				tmp4.put("condi_value", rs.getString("filter_value"));
//				tmp4.put("condi_col_display", rs.getString("filter_col_display"));
//				tmp4.put("condi_type_display", rs.getString("filter_condi_display"));
//				tmp4.put("condi_value_display", rs.getString("filter_value_display"));
//				advs.add(tmp4);
			}
			ps1 = conn.prepareStatement(this.cmd.getAdvCondition());
			ps1.setString(1, uid);
			ps1.setString(2, sql_id);
			rs1 = ps1.executeQuery();

			while (rs1.next()) {
				advs=new ArrayList<HashMap<String, Object>>();
				tmp4 = new HashMap<String, Object>();
				tmp4.put("condi_relation", rs1.getString("condi_relation"));
				tmp4.put("condi_col", rs1.getString("filter_col"));
				tmp4.put("condi_type", rs1.getString("filter_condi"));
				tmp4.put("condi_value", rs1.getString("filter_value"));
				tmp4.put("condi_col_display", rs1.getString("filter_col_display"));
				tmp4.put("condi_type_display", rs1.getString("filter_condi_display"));
				tmp4.put("condi_value_display", rs1.getString("filter_value_display"));
				advs.add(tmp4);
			}
			ps2 = conn.prepareStatement(this.cmd.initHPbodyData());
			ps2.setString(1, uid);
			ps2.setString(2, uid);
			ps2.setString(3, uid);
			ps2.setString(4, body_id);
			rs2 = ps2.executeQuery();
			if(rs2.next()){
				tmp2 = new HashMap<String, Object>();
				//tmp2.put("op_is_open", rs2.getString("params_value"));
				tmp2.put("filter_col", rs2.getString("filter_col"));
				tmp2.put("filter_condi", rs2.getString("filter_condi"));
				tmp2.put("filter_value", rs2.getString("filter_value"));
				tmp2.put("sort_col", rs2.getString("sort_col"));
				tmp2.put("sort_type", rs2.getString("sort_type"));
				tmp2.put("page_size", rs.getString("page_size"));
				tmp2.put("grid_font_size", rs.getString("grid_font_size"));
				tmp2.put("grid_font_color", rs.getString("grid_font_color"));
				tmp2.put("grid_row_height", rs.getString("grid_row_height"));
				tmp2.put("grid_row_color", rs.getString("grid_row_color"));
				tmp2.put("grid_row_even_color", rs.getString("grid_row_even_color"));
				if (rs2.getString("popup_width") == null) {
					tmp2.put("popup_width", 200);
				} else {
					if (DCIString.isInteger(rs2.getString("popup_width"))) {
						tmp2.put("popup_width", rs2.getInt("popup_width"));
					} else {
						tmp2.put("popup_width", 200);
					}
				}
			}
			res.put("headDisplay", tmp3);
			res.put("headAdvances",advs);
			res.put("bodyDisplay",tmp2);
			boolresult=true;
		}catch(Exception ex){
			ex.printStackTrace();
			resultMsg=ex.getMessage();
		}finally{
			res.put("success",boolresult);
			res.put("msg",resultMsg);
			this.dbctrl.closeConnection(rs, ps, conn);
			this.dbctrl.closeConnection(rs1, ps1, null);
			this.dbctrl.closeConnection(rs2, ps2, null);
		}
		return res;
	}
}
