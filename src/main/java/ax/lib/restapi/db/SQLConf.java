package ax.lib.restapi.db;

public class SQLConf {
		
	/* 
	 **********************************************************************************
	 * SQL queries to AX Server Databases axServerDBType = "PostgreSQL", or "Oracle" *
     ********************************************************************************** 
	 */
	/* 
	 * Audit Item Type
	 * BC Engagement
	 * LC Activity
	 * GAP Generic Acl Project
	 * AC === Analytic Container
	 * AN Analytic
	 * RF, RFC, RS; TB, TD, DMC  ----- check the Audit item type table
	 * TB Audit Table
	 * TD Table Data
	 * LK_TB Link to Table
	 */

	public static String getProjectID(String type, String bc){
		
		String sql="";

		sql = "SELECT id, name, programtype "+
			  "FROM audititems a "+
			  "WHERE a.programtype = '"+type.toUpperCase()+"' "+"AND a.name = '"+bc+"'";
			     		
		return sql;
	}

	public static String geTestSetID(String type, String bc, String lc){
		
		String sql="";

        sql = "SELECT a.programtype, a.name, b.name, b.id "+
			  "FROM audititems a, audititems b "+
			  "WHERE a.programtype = '"+type.toUpperCase()+"' "+
			     "AND a.name = '"+bc+"' AND a.itemtype = 'BC' AND a.id = b.parentid "+
			     "AND b.name = '"+lc+"' AND b.itemtype = 'LC'";
			     		
		return sql;
	}

	public static String getTestID(String type, String bc, String lc, String gap){
		
		String sql="";

		sql = "SELECT d.id "+
			  "FROM audititems a, audititems b, audititems c, audititems d, audititems e "+
			  "WHERE a.programtype = '"+type.toUpperCase()+"' "+
			     "AND a.name = '"+bc+"' AND a.itemtype = 'BC' AND a.id = b.parentid "+
			     "AND b.name = '"+lc+"' AND b.itemtype = 'LC' AND b.id = c.parentid "+
			     "AND c.itemtype = 'AC' AND c.id = d.parentid "+
			     "AND d.name like '%"+gap+"%' AND d.itemtype = 'GAP' Group By d.id";
			     		
		return sql;
	}


	public static String getAnalyticID(String type, String bc, String lc, String gap, String an){
		
		String sql="";

		sql = "SELECT a.programtype, a.name, b.name, c.name,d.name, e.name, e.id "+
			  "FROM audititems a, audititems b, audititems c, audititems d, audititems e "+
			  "WHERE a.programtype = '"+type.toUpperCase()+"' "+
			     "AND a.name = '"+bc+"' AND a.itemtype = 'BC' AND a.id = b.parentid "+
			     "AND b.name = '"+lc+"' AND b.itemtype = 'LC' AND b.id = c.parentid "+
			     "AND c.itemtype = 'AC' AND c.id = d.parentid "+
			     "AND d.name like '%"+gap+"%' AND d.itemtype = 'GAP' AND d.id = e.parentid "+
			     "AND e.name = '"+an+"' AND e.itemtype = 'AN'";
		
		return sql;
	}

	public static String getParameterSetID(String type, String bc, String lc, String gap, String an, String setName){
		
		String sql="";

		sql = "SELECT a.programtype, a.name, b.name, c.name,d.name, e.name, x.name, x.parametersetid "+
			  "FROM audititems a, audititems b, audititems c, audititems d, audititems e, "+
			        "analyticparameterset x "+
			  "WHERE a.programtype = '"+type.toUpperCase()+"' "+
			     "AND a.name = '"+bc+"' AND a.itemtype = 'BC' AND a.id = b.parentid "+
			     "AND b.name = '"+lc+"' AND b.itemtype = 'LC' AND b.id = c.parentid "+
			     "AND c.name = 'Analytic Container' AND c.itemtype = 'AC' AND c.id = d.parentid "+
			     "AND d.name like '%"+gap+"%' AND d.itemtype = 'GAP' AND d.id = e.parentid "+
			     "AND e.name = '"+an+"' AND e.itemtype = 'AN' " +
			     "AND e.id = x.analyticitemid AND x.name = '"+setName+"'";
			     		
		return sql;
	}

	public static String getTableID(String type, String bc, String lc, String tableName){
		
		String sql="";

		sql = "SELECT a.programtype, a.name, b.name, c.name,d.name, d.id "+
				  "FROM audititems a, audititems b, audititems c, audititems d "+
				  "WHERE a.programtype = '"+type.toUpperCase()+"' "+
				     "AND a.name = '"+bc+"' AND a.itemtype = 'BC' AND a.id = b.parentid "+
				     "AND b.name = '"+lc+"' AND b.itemtype = 'LC' AND b.id = c.parentid "+
				     "AND c.itemtype = 'DMC' AND c.id = d.parentid "+
				     "AND d.name = '"+ tableName +"' AND d.itemtype = 'TB'";

		return sql;
	}

	public static String getRelatedFileID(String type, String bc, String lc, String fileName){
		
		String sql="";

		sql = "SELECT a.programtype, a.name, b.name, c.name,d.name, d.id "+
				  "FROM audititems a, audititems b, audititems c, audititems d "+
				  "WHERE a.programtype = '"+type.toUpperCase()+"' "+
				     "AND a.name = '"+bc+"' AND a.itemtype = 'BC' AND a.id = b.parentid "+
				     "AND b.name = '"+lc+"' AND b.itemtype = 'LC' AND b.id = c.parentid "+
				     "AND c.itemtype = 'RFC' AND c.id = d.parentid "+
				     "AND d.name = '"+ fileName +"' AND d.itemtype = 'RF'";
				     		
		return sql;
	}
	
	
	public static String getResultFileID(String type, String bc, String lc, String an, String filename){
		String sql = "";
		
		sql ="select * from (SELECT a.programtype, a.name, b.name, c.name,d.name m, d.id, e.name, e.id n " +
			 "FROM audititems a, audititems b, audititems c, audititems d,audititems e " +
			 "WHERE a.programtype = 'WORKING' AND a.name = '" + bc + "' AND a.itemtype = 'BC' AND a.id = b.parentid " + 
			 "AND b.name = '" + lc +  "' AND b.itemtype = 'LC' AND b.id = c.parentid " + 
			 "AND c.name = 'Results Container' AND c.itemtype = 'RC' AND c.id = d.parentid " + 
			 "AND d.name like '" + an + "1%' AND d.itemtype = 'RS' AND d.id = e.parentid " + 
			 "AND e.name = '" + filename + "' AND e.itemtype = 'RF') f " +
			 "where f.m =(select max(m) from (SELECT a.programtype, a.name, b.name, c.name,d.name m, d.id, e.name, e.id n " +
			 "FROM audititems a, audititems b, audititems c, audititems d,audititems e " +
			 "WHERE a.programtype = 'WORKING' AND a.name = '" + bc + "' AND a.itemtype = 'BC' AND a.id = b.parentid " + 
			 "AND b.name = '" + lc + "' AND b.itemtype = 'LC' AND b.id = c.parentid " + 
			 "AND c.name = 'Results Container' AND c.itemtype = 'RC' AND c.id = d.parentid " + 
			 "AND d.name like '" + an + "%' AND d.itemtype = 'RS' AND d.id = e.parentid " + 
			 "AND e.name = '" + filename + "' AND e.itemtype = 'RF') g)";
     		
		return sql;
	}

	public static String getJobID(String type, String bc, String lc, String gap, String an, String scheduleid){
		
		String sql="";

		sql = "SELECT max(jobnumber) jobnumber "+
				"FROM scriptjobs f "+ 
				"WHERE f.scheduleid = '"+scheduleid+"' AND f.analyticid = (SELECT e.id "+
				  "FROM audititems a, audititems b, audititems c, audititems d, audititems e "+
				  "WHERE a.programtype = '"+type.toUpperCase()+"' "+
				     "AND a.name = '"+bc+"' AND a.itemtype = 'BC' AND a.id = b.parentid "+
				     "AND b.name = '"+lc+"' AND b.itemtype = 'LC' AND b.id = c.parentid "+
				     "AND c.itemtype = 'AC' AND c.id = d.parentid "+
				     "AND d.name like '%"+gap+"%' AND d.itemtype = 'GAP' AND d.id = e.parentid "+
				     "AND e.name = '"+an+"' AND e.itemtype = 'AN')";

		return sql;
	}

	public static String getResultID(){
		
		String sql="";

		return sql;
	}

	public static String getScheduleID(){
		
		String sql="";

		return sql;
	}

}
