package com.dci.jweb.DataBaseLib.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import com.dci.jweb.DCIBeans.Database.DBInfoBean;
import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DCIEnums.Database.DBType;
import com.dci.jweb.DCIInterface.Database.IDBInfoBean;

public class test {
	public static void main(String[] args) {
		// new test().test4();
		// new test().test7();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			/*
			 * conn = new test().getConnedtion("Ben", "1234"); ps =
			 * conn.prepareStatement("insert into test01 values (?,?)",
			 * ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			 * Random rn = new Random(); for (int i = 4; i < 1000; i++) {
			 * ps.setString(1, String.valueOf(i)); ps.setString(2,
			 * String.valueOf(rn.nextInt(999999))); ps.executeUpdate(); }
			 */

			// Database db = new Database();
			// System.out.println(db.getUrl(DBType.Informix, "10.40.71.84",
			// "9091", "TESTDB/ekbtest"));
			// new test().test10();
			throw new Exception("test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getClass().getName());
		}

		// createData();
	}

	public Connection getConnedtion(String userid, String pwd) throws Exception {
		boolean ok = false;
		Connection conn = null;

		try {
			Class.forName("com.informix.jdbc.IfxDriver");
			conn = DriverManager.getConnection(
					"jdbc:informix-sqli://10.40.71.84:9091/EKBTEST:INFORMIXSERVER=TESTDB", userid, pwd);

			if (conn != null && !conn.isClosed()) {
				ok = true;
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			ok = false;
			throw ex;
		} /*
		 * finally { try { if (conn != null && !conn.isClosed()) { conn.close();
		 * } } catch (Exception ex) { } conn = null; }
		 */

		return conn;
	}

	public void test2() {
		DatabaseObjects dbobj = DatabaseObjects.getInstance();
		// dbobj.setSFTDBInfo("10.40.15.111", "1087", "SFTSYS", "sa",
		// "DSC12800", DBType.SqlServer);
		// dbobj.createDataSource("10.40.15.111", "1087", "SFTSYS", "sa",
		// "DSC12800", DBType.SqlServer, ConnectionType.SYS);
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			conn = dbobj.getConnection(ConnectionType.SFT);

			ps = conn.prepareStatement("select * from Plan_Case");

			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("plan_case_alias"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void test1() {
		/*
		 * DatabaseParameters parames = DatabaseParameters.getInstance();
		 * parames.setSFTDBInfo("addr", "name", "user", "pwd", DBType.Oracle);
		 * DBInfoBean infos = parames.getSFTDBInfo();
		 * System.out.println(infos.getDBAddr() + "-" + infos.getDBName() + "-"
		 * + infos.getUserName() + "-" + infos.getPassword()); parames =
		 * DatabaseParameters.getInstance(); parames.setSFTDBInfo("addr2",
		 * "name2", "user2", "pwd2", DBType.SqlServer); infos =
		 * parames.getSFTDBInfo(); System.out.println(infos.getDBAddr() + "-" +
		 * infos.getDBName() + "-" + infos.getUserName() + "-" +
		 * infos.getPassword());
		 */
	}

	public void test3() {
		IDBInfoBean infos = new DBInfoBean();
		infos.setDBAddr("localhost");
		// infos.setDBName("PROD_XH01");
		infos.setDBName("HYCAPS");
		infos.setDBPort("1433");
		infos.setDBType(DBType.SqlServer);
		infos.setUserName("sa");
		infos.setPassword("1234");
		infos.setMaxActive(100);
		infos.setMaxIdle(100);
		infos.setMaxWait(10);
		infos.setMinIdle(10);
		infos.setRemoveAbandoned(true);

		DatabaseObjects dbobj = DatabaseObjects.getInstance();
		dbobj.createDataSource(infos, ConnectionType.SYS);

		HashMap<String, ArrayList<Object>> alldatas = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		ArrayList<Object> datas = null;
		ArrayList<String[]> cols = null;

		try {
			conn = dbobj.getConnection(ConnectionType.SYS);
			cols = new ArrayList<String[]>(0);
			// cols.add(new String[] { "TA001", "TB001" });
			// cols.add(new String[] { "TA002", "TB002" });
			// cols.add(new String[] { "TA003", "TB003" });
			// cols.add(new String[] { "TA004", "TB004" });
			cols.add(new String[] { "SA001", "SB001" });
			cols.add(new String[] { "SA002", "SB002" });
			String sql1 = "select SA001 ,SA002 ,SA001 C1,SA002 C1 from SKLSA";
			String sql2 = "select * from SKLSB";

			ps = conn.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			ps1 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs1 = ps1.executeQuery();

			long l1 = Calendar.getInstance().getTimeInMillis();
			datas = new DatabaseFuncs().JoinResult(rs, rs1, cols);
			System.out.println(Calendar.getInstance().getTimeInMillis() - l1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println(datas.size());
	}

	public void test4() {
		IDBInfoBean infos = new DBInfoBean();
		infos.setDBAddr("localhost");
		// infos.setDBName("PROD_XH01");
		infos.setDBName("HYCAPS");
		infos.setDBPort("1433");
		infos.setDBType(DBType.SqlServer);
		infos.setUserName("sa");
		infos.setPassword("1234");
		infos.setMaxActive(100);
		infos.setMaxIdle(100);
		infos.setMaxWait(10);
		infos.setMinIdle(10);
		infos.setRemoveAbandoned(true);

		DatabaseObjects dbobj = DatabaseObjects.getInstance();
		dbobj.createDataSource(infos, ConnectionType.SYS);

		HashMap<String, ArrayList<Object>> alldatas = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		ArrayList<Object> datas = null;
		ArrayList<String[]> cols = null;

		try {
			conn = dbobj.getConnection(ConnectionType.SYS);
			cols = new ArrayList<String[]>(0);
			cols.add(new String[] { "DB002", "DD002" });
			// cols.add(new String[] { "SA002", "SA002" });

			String sql1 = "select * from DATDB";
			String sql2 = "select * from DATDD";

			sql1 = "select * from (" + sql1 + ") as data1 order by ";
			sql2 = "select * from (" + sql2 + ") as data2 order by ";

			for (int i = 0; i < cols.size(); i++) {
				if (i == 0) {
					sql1 += cols.get(i)[0];
					sql2 += cols.get(i)[1];
				} else {
					sql1 += "," + cols.get(i)[0];
					sql2 += "," + cols.get(i)[1];
				}
			}

			ps = conn.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps1 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			rs1 = ps1.executeQuery();

			long l1 = Calendar.getInstance().getTimeInMillis();
			datas = new DatabaseFuncs().JoinResult(rs, rs1, cols);
			System.out.println(Calendar.getInstance().getTimeInMillis() - l1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println(datas.size());
	}

	public void test5() {
		IDBInfoBean infos = new DBInfoBean();
		infos.setDBAddr("localhost");
		// infos.setDBName("PROD_XH01");
		infos.setDBName("HYCAPS");
		infos.setDBPort("1433");
		infos.setDBType(DBType.SqlServer);
		infos.setUserName("sa");
		infos.setPassword("1234");
		infos.setMaxActive(100);
		infos.setMaxIdle(100);
		infos.setMaxWait(10);
		infos.setMinIdle(10);
		infos.setRemoveAbandoned(true);

		DatabaseObjects dbobj = DatabaseObjects.getInstance();
		dbobj.createDataSource(infos, ConnectionType.SYS);

		Connection conn = null;
		ArrayList<Object> datas = null;
		ArrayList<String[]> cols = null;

		try {
			conn = dbobj.getConnection(ConnectionType.SYS);
			cols = new ArrayList<String[]>(0);
			cols.add(new String[] { "DB002", "DD002" });

			long l1 = Calendar.getInstance().getTimeInMillis();
			datas = new DatabaseFuncs().JoinResult(conn, "select * from DATDB", "select * from DATDD", cols,
					true);
			System.out.println(Calendar.getInstance().getTimeInMillis() - l1);
			System.out.println(datas.size());
			System.out.println(datas.get(0));
			l1 = Calendar.getInstance().getTimeInMillis();
			datas = new DatabaseFuncs().LeftJoinResult(conn, "select * from DATDB", "select * from DATDD",
					cols, true);
			System.out.println(Calendar.getInstance().getTimeInMillis() - l1);
			System.out.println(datas.size());
			l1 = Calendar.getInstance().getTimeInMillis();
			datas = new DatabaseFuncs().UnionAllResult(conn, "select * from DATDB", "select * from DATDD");
			System.out.println(Calendar.getInstance().getTimeInMillis() - l1);
			System.out.println(datas.size());
			l1 = Calendar.getInstance().getTimeInMillis();
			datas = new DatabaseFuncs().UnionResult(conn, "select 'b' from DATDB", "select 'b' from DATDD");
			System.out.println(Calendar.getInstance().getTimeInMillis() - l1);
			System.out.println(datas.size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void test6() {
		IDBInfoBean infos = new DBInfoBean();
		infos.setDBAddr("localhost");
		// infos.setDBName("PROD_XH01");
		infos.setDBName("BEN_TEST");
		infos.setDBPort("1433");
		infos.setDBType(DBType.SqlServer);
		infos.setUserName("sa");
		infos.setPassword("1234");
		infos.setMaxActive(100);
		infos.setMaxIdle(100);
		infos.setMaxWait(10);
		infos.setMinIdle(10);
		infos.setRemoveAbandoned(true);

		DatabaseObjects dbobj = DatabaseObjects.getInstance();
		dbobj.createDataSource(infos, ConnectionType.SYS);

		Connection conn = null;
		ArrayList<Object> datas = null;
		ArrayList<String[]> cols = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;

		try {

			conn = dbobj.getConnection(ConnectionType.SYS);
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("select * from Test01");
			// ps1 =
			// conn.prepareStatement("update Test01 set c3 = ? where c1 = '1'");
			ps1 = conn.prepareStatement("insert into Test01 (C1) values (?)");
			rs = ps.executeQuery();
			long l1 = Calendar.getInstance().getTimeInMillis();
			int cnt = 0;
			for (int i = 0; i < 20000; i++) {
				// while (rs.next()) {
				// cnt = i;
				ps1.setString(1, "0");// String.valueOf(cnt));
				// ps1.setString(2, "1");// String.valueOf(cnt));//
				// rs.getString("c1"));

				ps1.addBatch();

				if (cnt != 0 && cnt % 100 == 0) {
					ps1.executeBatch();
					System.out.println(Calendar.getInstance().getTimeInMillis() - l1);
				}
				cnt++;
			}
			if (cnt != 0 && cnt % 1000 != 0) {
				ps1.executeBatch();
			}
			conn.commit();
			System.out.println(Calendar.getInstance().getTimeInMillis() - l1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void test7() {
		IDBInfoBean infos = new DBInfoBean();
		infos.setDBAddr("localhost");
		// infos.setDBName("PROD_XH01");
		infos.setDBName("HYCAPS");
		infos.setDBPort("1433");
		infos.setDBType(DBType.SqlServer);
		infos.setUserName("sa");
		infos.setPassword("1234");
		infos.setMaxActive(100);
		infos.setMaxIdle(100);
		infos.setMaxWait(10);
		infos.setMinIdle(10);
		infos.setRemoveAbandoned(true);

		DatabaseObjects dbobj = DatabaseObjects.getInstance();
		dbobj.createDataSource(infos, ConnectionType.SYS);

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement("select * from SKLDA", ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			HashMap<String, String> joincols = new DatabaseFuncs().colnameRepeat(rs.getMetaData(),
					rs.getMetaData());

			for (String key : joincols.keySet()) {
				System.out.println(key);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void test8() {
		IDBInfoBean infos = new DBInfoBean();
		infos.setDBAddr("10.40.71.84");
		// infos.setDBName("PROD_XH01");
		infos.setDBName("EKB2_Demo");
		infos.setDBPort("1433");
		infos.setDBType(DBType.SqlServer);
		infos.setUserName("sa");
		infos.setPassword("1234");
		infos.setMaxActive(100);
		infos.setMaxIdle(100);
		infos.setMaxWait(10);
		infos.setMinIdle(10);
		infos.setRemoveAbandoned(true);

		DatabaseObjects dbobj = DatabaseObjects.getInstance();
		dbobj.createDataSource(infos, ConnectionType.SYS);

		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ArrayList<Object> datas = null;
		ArrayList<String[]> cols = null;

		try {
			conn = dbobj.getConnection(ConnectionType.SYS);
			cols = new ArrayList<String[]>(0);
			cols.add(new String[] { "colB", "colB" });

			ps1 = conn.prepareStatement("select * from TB01_84 order by colB",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps2 = conn.prepareStatement("select * from TB02 order by colB",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			rs1 = ps1.executeQuery();
			rs2 = ps2.executeQuery();

			long l1 = Calendar.getInstance().getTimeInMillis();
			/*
			 * datas = new DatabaseFuncs().JoinResult(conn,
			 * "select * from TB01_84", "select * from TB02", cols, true);
			 */
			HashMap<String, String> mcols = new HashMap<String, String>();
			mcols.put("colCC", "colA$/#colB$*#100");
			mcols.put("colCB", "colA11$/#100");
			datas = new DatabaseFuncs().MathJoinResult(rs1, rs2, cols, mcols);
			System.out.println(Calendar.getInstance().getTimeInMillis() - l1);
			System.out.println(datas.size());
			System.out.println(datas.get(0));
			/*
			 * l1 = Calendar.getInstance().getTimeInMillis(); datas = new
			 * DatabaseFuncs().LeftJoinResult(conn, "select * from DATDB",
			 * "select * from DATDD", cols, true);
			 * System.out.println(Calendar.getInstance().getTimeInMillis() -
			 * l1); System.out.println(datas.size()); l1 =
			 * Calendar.getInstance().getTimeInMillis(); datas = new
			 * DatabaseFuncs().UnionAllResult(conn, "select * from DATDB",
			 * "select * from DATDD");
			 * System.out.println(Calendar.getInstance().getTimeInMillis() -
			 * l1); System.out.println(datas.size()); l1 =
			 * Calendar.getInstance().getTimeInMillis(); datas = new
			 * DatabaseFuncs().UnionResult(conn, "select 'b' from DATDB",
			 * "select 'b' from DATDD");
			 * System.out.println(Calendar.getInstance().getTimeInMillis() -
			 * l1); System.out.println(datas.size());
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void test9() {
		ArrayList<HashMap<String, Object>> datas = null;
		ArrayList<HashMap<String, Object>> data1 = null;
		ArrayList<HashMap<String, Object>> data2 = null;
		ArrayList<String[]> cols = null;

		HashMap<String, Object> tmp = null;

		try {

			data1 = new ArrayList<HashMap<String, Object>>();
			data2 = new ArrayList<HashMap<String, Object>>();

			/*
			 * tmp = new HashMap<String, Object>(); tmp.put("col1", 1);
			 * tmp.put("col2", 2); data1.add(tmp); tmp = new HashMap<String,
			 * Object>(); tmp.put("col1", 2); tmp.put("col2", 2);
			 * data1.add(tmp); tmp = new HashMap<String, Object>();
			 * tmp.put("col1", 3); tmp.put("col2", 2); data1.add(tmp); tmp = new
			 * HashMap<String, Object>(); tmp.put("col1", 4); tmp.put("col2",
			 * 2); data1.add(tmp); tmp = new HashMap<String, Object>();
			 * tmp.put("colA", 1); tmp.put("colB", 3); data2.add(tmp); tmp = new
			 * HashMap<String, Object>(); tmp.put("colA", 2); tmp.put("colB",
			 * 3); data2.add(tmp); tmp = new HashMap<String, Object>();
			 * tmp.put("colA", 5); tmp.put("colB", 3); data2.add(tmp); tmp = new
			 * HashMap<String, Object>(); tmp.put("colA", 6); tmp.put("colB",
			 * 3); data2.add(tmp);
			 */

			Random rn = new Random();
			for (int i = 0; i < 100000; i++) {
				tmp = new HashMap<String, Object>();
				tmp.put("col1", i);
				tmp.put("col2", rn.nextInt(99999));
				data1.add(tmp);
			}
			for (int i = 0; i < 100000; i++) {
				tmp = new HashMap<String, Object>();
				tmp.put("colA", i);
				tmp.put("colB", rn.nextInt(99999));
				data2.add(tmp);
			}

			cols = new ArrayList<String[]>();
			cols.add(new String[] { "col1", "colA" });
			cols.add(new String[] { "col2", "colB" });

			long l1 = Calendar.getInstance().getTimeInMillis();
			datas = new DatabaseFuncs().JoinResult(data1, data2, cols);
			System.out.println(Calendar.getInstance().getTimeInMillis() - l1);
			System.out.println(datas.size());
			System.out.println(datas.get(0));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void test10() {
		IDBInfoBean infos = new DBInfoBean();
		infos.setDBAddr("10.40.71.84");
		// infos.setDBName("PROD_XH01");
		infos.setDBName("EKB2_Demo");
		infos.setDBPort("1433");
		infos.setDBType(DBType.SqlServer);
		infos.setUserName("sa");
		infos.setPassword("1234");
		infos.setMaxActive(100);
		infos.setMaxIdle(100);
		infos.setMaxWait(10);
		infos.setMinIdle(10);
		infos.setRemoveAbandoned(true);

		DatabaseObjects dbobj = DatabaseObjects.getInstance();
		dbobj.createDataSource(infos, ConnectionType.SYS);

		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;
		ArrayList<String[]> cols = null;

		try {
			conn = dbobj.getConnection(ConnectionType.SYS);
			cols = new ArrayList<String[]>(0);
			cols.add(new String[] { "A.colB", "B.colB" });
			// cols.add(new String[] { "B.colB", "C.colB" });
			cols.add(new String[] { "A.colA", "C.colA" });
			String[] sqls = new String[] { "select * from TB02 order by colB",
					"select * from TB01_84 order by colB", "select * from TB01_84 order by colB" };
			// ps1 = conn.prepareStatement("select * from TB02 order by colB",
			// ResultSet.TYPE_FORWARD_ONLY,
			// ResultSet.CONCUR_READ_ONLY);
			// ps2 =
			// conn.prepareStatement("select * from TB01_84 order by colB",
			// ResultSet.TYPE_FORWARD_ONLY,
			// ResultSet.CONCUR_READ_ONLY);
			// ps3 = conn.prepareStatement("select * from TB02 order by colB",
			// ResultSet.TYPE_FORWARD_ONLY,
			// ResultSet.CONCUR_READ_ONLY);
			// ps4 =
			// conn.prepareStatement("select * from TB01_84 order by colB",
			// ResultSet.TYPE_FORWARD_ONLY,
			// ResultSet.CONCUR_READ_ONLY);
			//
			// rs1 = ps1.executeQuery();
			// rs2 = ps2.executeQuery();
			// rs3 = ps3.executeQuery();
			// rs4 = ps4.executeQuery();
			ResultSet[] rss = new ResultSet[3];
			for (int i = 0; i < sqls.length; i++) {
				ps1 = conn.prepareStatement(sqls[i], ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				rss[i] = ps1.executeQuery();
				ps1.close();
			}

			HashMap<String, Object> test = new HashMap<String, Object>();
			HashMap<String, Object> test2 = new HashMap<String, Object>();
			HashMap<String, Object> test3 = new HashMap<String, Object>();
			HashMap<String, Object> test4 = new HashMap<String, Object>();
			long l1 = Calendar.getInstance().getTimeInMillis();
			// while (rs1.next()) {
			// test.put(String.valueOf(rs1.getRow()), rs1.getString(2));
			// }
			// System.out.println(Calendar.getInstance().getTimeInMillis() -
			// l1);
			// System.out.println(test.size());
			// l1 = Calendar.getInstance().getTimeInMillis();
			// rs1.last();
			// int loop = rs1.getRow();
			// rs1.beforeFirst();
			// for (int i = 0; i < loop; i++) {
			// rs1.absolute(i + 1);
			// test2.put(rs1.getString(1), rs1.getString(2));
			// }
			// System.out.println(Calendar.getInstance().getTimeInMillis() -
			// l1);
			// System.out.println(test2.size());

			// l1 = Calendar.getInstance().getTimeInMillis();
			// for (String key : test.keySet()) {
			// test3.put(key, test.get(key));
			// }
			// System.out.println(Calendar.getInstance().getTimeInMillis() -
			// l1);
			// System.out.println(test3.size());
			// l1 = Calendar.getInstance().getTimeInMillis();
			// while (rs2.next()) {
			// test4.put(String.valueOf(rs2.getRow()), rs2.getString(2));
			// }
			// System.out.println(Calendar.getInstance().getTimeInMillis() -
			// l1);
			// System.out.println(test4.size());

			l1 = Calendar.getInstance().getTimeInMillis();
			System.out.println(l1);
			datas = new DatabaseFuncs().LeftJoinResult(rss, cols);
			// datas = new DatabaseFuncs().LeftJoinResult(new ResultSet[] { rs1,
			// rs2, rs3 }, cols);
			// datas = new DatabaseFuncs().UnionResult(new ResultSet[] { rs1,
			// rs2, rs3, rs4 });
			System.out.println(Calendar.getInstance().getTimeInMillis() - l1);
			System.out.println(datas.size());
			// if (datas != null) {
			// if (datas.size() == 0) {
			// System.out.println("data 0");
			// } else {
			if (ps1 != null) {
				ps1.close();
			}
			ps1 = conn.prepareStatement(
					"insert into datacheck (colA,colB,colA1,colB1,colA2,colB2) values (?,?,?,?,?,?)",
					// "insert into datacheck (colA,colB,colA1,colB1) values (?,?,?,?)",
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			System.out.println(datas.size());
			for (int i = 0; i < datas.size(); i++) {
				tmp = datas.get(i);

				// for (String k : tmp.keySet()) {
				// System.out.print(datas.get(i).get(k) + ";");
				// }
				// System.out.println("");
				// if (datas.get(i).get("colB").toString().equals("555055")) {
				// System.out.println(datas.get(i));
				// }

				ps1.setString(1, datas.get(i).get("colA").toString());
				ps1.setString(2, datas.get(i).get("colB").toString());
				ps1.setString(3, datas.get(i).get("colA1").toString());
				ps1.setString(4, datas.get(i).get("colB1").toString());
				ps1.setString(5, datas.get(i).get("colA2").toString());
				ps1.setString(6, datas.get(i).get("colB2").toString());
				ps1.executeUpdate();
			}
			// }
			// } else {
			// System.out.println("data null");
			// }
			/*
			 * l1 = Calendar.getInstance().getTimeInMillis(); datas = new
			 * DatabaseFuncs().LeftJoinResult(conn, "select * from DATDB",
			 * "select * from DATDD", cols, true);
			 * System.out.println(Calendar.getInstance().getTimeInMillis() -
			 * l1); System.out.println(datas.size()); l1 =
			 * Calendar.getInstance().getTimeInMillis(); datas = new
			 * DatabaseFuncs().UnionAllResult(conn, "select * from DATDB",
			 * "select * from DATDD");
			 * System.out.println(Calendar.getInstance().getTimeInMillis() -
			 * l1); System.out.println(datas.size()); l1 =
			 * Calendar.getInstance().getTimeInMillis(); datas = new
			 * DatabaseFuncs().UnionResult(conn, "select 'b' from DATDB",
			 * "select 'b' from DATDD");
			 * System.out.println(Calendar.getInstance().getTimeInMillis() -
			 * l1); System.out.println(datas.size());
			 */
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void createData() {

		IDBInfoBean infos = new DBInfoBean();
		infos.setDBAddr("localhost");
		infos.setDBName("BEN_TEST");
		infos.setDBPort("1433");
		infos.setDBType(DBType.SqlServer);
		infos.setUserName("sa");
		infos.setPassword("1234");
		infos.setMaxActive(100);
		infos.setMaxIdle(100);
		infos.setMaxWait(10);
		infos.setMinIdle(10);
		infos.setRemoveAbandoned(true);

		DatabaseObjects dbobj = DatabaseObjects.getInstance();
		dbobj.createDataSource(infos, ConnectionType.SYS);

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dbobj.getConnection(ConnectionType.SYS);
			// ps =
			// conn.prepareStatement("update Test01 set C2 = ? where C1 = ?");
			ps = conn.prepareStatement("insert into Test02 (CQ2,CQ1) values (?,?)");

			for (int i = 0; i < 100; i++) {
				ps.setString(1, String.valueOf((int) (Math.random() * 10000)));
				ps.setString(2, String.valueOf(i));
				ps.executeUpdate();
			}

		} catch (Exception ex) {

		} finally {

		}
	}
}
