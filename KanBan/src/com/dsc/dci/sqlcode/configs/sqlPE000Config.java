package com.dsc.dci.sqlcode.configs;

public class sqlPE000Config {
	public String getAllDatas() {
		return " select * from Process_Exception order by sequ_num ";
	}
	public String bodyAdd(){
		return "insert into Process_Exception (sequ_num,process_id,process_name,is_work,report_id,report_name,dept_name,owner,schedule_time) values (?,?,?,?,?,?,?,?,?)";
	}
	
	public String bodyUpd(){
		return "update Process_Exception set sequ_num= ? , process_id= ? , process_name= ? , is_work= ? , report_id= ? , report_name= ? , dept_name= ? , owner= ? , schedule_time= ? where report_id = ? ";
	}
	
	public String bodyDel(){
		return "delete from Process_Exception where report_id = ? ";
	}
	public String getInitData(){
		return 
				"select '1' tp, conn_id value, conn_name label ,'' package" +
				"  from Conn_Info " + 
				"union all " + 
				//"select '2' tp, role_id value, role_name label " + 
				//"  from Role_Info " + 
				//"union all " + 
				"select '2' tp, func_id value, " + 
				"       case " + 
				"          when b.lang_value is null or b.lang_value = '' then " + 
				"           a.lang_key " + 
				"          else " + 
				"           b.lang_value " + 
				"        end label , package" + 
				"  from (select func_id, lang_key, package from Menu where is_program = '1') a " + 
				"  left join Multi_Language b on a.lang_key = b.lang_key and b.lang = ? " + 
				" where func_id like 'PE%' and package='EKB' and func_id <> 'PE001'"+
				" order by 1, 2";
	}
}
