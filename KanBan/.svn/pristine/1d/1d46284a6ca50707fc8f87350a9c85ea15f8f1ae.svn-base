package com.dsc.dci.sqlcode.funcs.lkb;

public class sqlLayoutKanBan {
	public String getLKBInfo(){
		String sql = null;
		
		sql = "select * from LayoutKB where LKB_ID = ?";
		
		return sql;
	}
	
	public String getMasterData(String sql) {
		if (sql == null) {
			sql = "select '' eq,'' value_range,'' groupid";
		}
		return "select bk.*,case when bk.OBJ_ID is null then bk.Bg_Color else do.OBJ_Content end block_color  from "
				+ "(select bm.*, rc.Value_Range1, rc.Value_Range2, rc.OBJ_ID "
				+ "          from (select * "
				+ "                   from BlockMaster "
				+ "                  where LKB_ID = ?) as bm "
				+ "          left join (select * "
				+ "                       from RangeConfig "
				+ "                      where LKB_ID = ? and Layout_ID = 'bkMaster') as rc on "
				+ "                                                 bm.Block_ID = rc.Block_ID ) as bk "
				+ "join ("
				+ sql
				+ ") as vr "
				+ "    on bk.DB_Block_ID = vr.eq and bk.Group_ID = vr.groupid  "
				+ "   and 1 = case when bk.OBJ_ID IS NULL then 1 "
				+ "                when bk.OBJ_ID IS not NULL and bk.Value_Range1 IS NULL then "
				+ "                     case when vr.value_range > bk.Value_Range2 then 1 else 0 end "
				+ "                when bk.OBJ_ID IS not NULL and bk.Value_Range2 IS NULL then "
				+ "                     case when vr.value_range < bk.Value_Range1 then 1 else 0 end "
				+ "                else case when bk.OBJ_ID IS not NULL and vr.value_range between bk.Value_Range1 and bk.Value_Range2 then 1 else 0 end end "
				+ "  left join DisplayObject do on bk.OBJ_ID = do.OBJ_ID";

	}

	public String getSQLAndConn() {
		return "select aa.Group_ID,aa.Conn_ID, bb.* " + "  from (select a.Group_ID, b.Conn_ID, b.SQL_ID "
				+ "           from (select distinct Group_ID " + "                    from BlockMaster "
				+ "                   where LKB_ID = ?) as a "
				+ "           join DataGroupInfo b on a.Group_ID = b.Group_ID) as aa "
				+ "  join SQLInfo bb on aa.SQL_ID = bb.SQL_ID";
	}

	public String getBlockDetail(String sql) {
		if (sql == null) {
			sql = "select '' eq,'' value_range,'' groupid";
		}
		return "select bk.*, "
				+ "       case "
				+ "          when bk.OBJ_ID is null then "
				+ "           bk.dtl_Font_Color "
				+ "          else "
				+ "           do.OBJ_Content "
				+ "        end Font_Color "
				+ "  from (SELECT a.Group_ID, a.Block_Pattern, b.Block_ID, b.Layout_ID, b.DB_Column,"
				+ "                b.Font_Size, b.Font_Color as dtl_Font_Color, b.Display_Title, b.IsHint,a.DB_Block_ID, "
				+ "                c.OBJ_ID, c.Value_Range1, c.Value_Range2 "
				+ "           FROM (select Block_ID, Group_ID, Block_Pattern, DB_Block_ID "
				+ "                    from BlockMaster "
				+ "                   where LKB_ID = ?) as a "
				+ "           join (select * "
				+ "                  from BlockDetail "
				+ "                 where LKB_ID = ?) as b on a.Block_ID = b.Block_ID "
				+ "           left join (select * "
				+ "                       from RangeConfig "
				+ "                      where LKB_ID = ?) as c on b.Block_ID = c.Block_ID and "
				+ "                                                         b.Layout_ID = c.Layout_ID) as bk "
				+ "  join ("
				+ sql
				+ ") as vr "
				+ "on  bk.DB_Block_ID = vr.eq and bk.Group_ID = vr.groupid "
				+ "   and 1 = case when bk.OBJ_ID IS NULL then 1 "
				+ "                when bk.OBJ_ID IS not NULL and bk.Value_Range1 IS NULL then "
				+ "                     case when vr.value_range > bk.Value_Range2 then 1 else 0 end "
				+ "                when bk.OBJ_ID IS not NULL and bk.Value_Range2 IS NULL then "
				+ "                     case when vr.value_range < bk.Value_Range1 then 1 else 0 end "
				+ "                else case when bk.OBJ_ID IS not NULL and vr.value_range between bk.Value_Range1 and bk.Value_Range2 then 1 else 0 end end "
				+ "  left join DisplayObject do on bk.OBJ_ID = do.OBJ_ID order by Block_ID, Layout_ID";

	}
}
