/**
 * 
 */
package ax.lib.restapi.db;

import java.sql.Timestamp;

import com.acl.qa.taf.helper.superhelper.ACLQATestScript;

/**
 * Script Name   : <b>SQLQuery.java</b>
 * Generated     : <b>11:15:30 AM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Aug 27, 2013
 * @author steven_xiang
 * 
 */
public class SQLQuery {

	/**
	 * Demo Database DAO interface
	 */

	public static String getTableContentsQueryDemo(String table){
		return getTableContentsQueryDemo(ACLQATestScript.dbConf.dbtype,table);
	}
		
	public static String getTableContentsQueryDemo(String dbtype,String table){
        return getTableContentsQueryDemo(dbtype,table,10);
	}	

	public static String getTableContentsQueryDemo(String dbtype,String table,int limit){
		String sql="";
		
		if(dbtype.contains("PostgreSQL")){
		    sql = "SELECT * "+
		           "FROM  "+table+" "+
		           "LIMIT "+limit;
		}else if(dbtype.contains("Oracle")){
		    sql = "SELECT * "+
		       "FROM  "+table+" "+
	           "WHERE rownum<= "+limit;
		}else if(dbtype.contains("SQLServer")){
			sql = "SELECT top "+limit+" * "+
		          "FROM "+table+" ";
				
     	}else if(dbtype.contains("DB2")){
		    sql = "SELECT * "+
		       "FROM  "+table+" "+
	           "FETCH first "+limit+" rows only";
		}
		
		
		return sql;
	}
	
	
	
	
			/* 
			 ****************************************************************************
			 * SQL queries to  Databases  -- From Old implementation as references
		     **************************************************************************** 
			 */
			/*  These info were from old AXCore, need to be verified - Steven
			 * Audit Item Type
			 * BC Engagement
			 * LC Activity
			 * GAP Generic Acl Project
			 * AC === Analytic Container
			 * AN Analytic
			 * RF, RFC, RS; TB, TD, DMC  ----- checke the type table
			 */
		
			public static String getUserPermissionOnItem(String type, String bc, String lc, String userSID){
				// Permissionid could be 'FULL', 'READ' or 'NONE'
				// inheritpermission = f/t
				String sql="";
		        
		        if(!lc.equals("")){
		  		  sql = "SELECT a.programtype AS Root, a.name AS Engagement, "+
		  		                "y.usersid, y.audititemid AS engID, y.permissionid AS engPermissionID, "+
		  		                "b.name AS Activity, b.inheritpermission, "+
		  		                "x.audititemid AS actID, x.permissionid AS actPermissionID "+
		  		        "FROM audititems a, audititemuserpermission y, audititems b "+
		  		        "LEFT JOIN audititemuserpermission x "+
		  		        "ON b.id = x.audititemid  AND x.usersid = '"+userSID+"' "+
				        "WHERE a.programtype = '"+type+"' "+
				           "AND a.name = '"+bc+"' AND a.itemtype = 'BC' AND a.id = b.parentid "+
				           "AND b.name = '"+lc+"' AND b.itemtype = 'LC' "+
				           "AND a.id = y.audititemid  AND y.usersid = '"+userSID+"' " +
				           "";	
		        }else{
				  sql = "SELECT a.programtype AS Root, a.name AS Engagement, "+
		                "x.usersid, x.audititemid AS engID, x.permissionid AS engPermissionID "+
					    "FROM audititems a, audititemuserpermission x "+
					    "WHERE a.programtype = '"+type+"' "+
					       "AND a.name = '"+bc+"' AND a.itemtype = 'BC' "+
					       "AND a.id = x.audititemid AND x.usersid = '"+userSID+"' "+
					       "";	
		        }
				return sql;
			}	
			
			
			public static String getParameterSetValue(String type, String bc, String lc, String gap, String an,
					                                          String setName, String userSID){
				
				String sql="";
		        String passwordColumn="",passwordTable="", passwordJoinCondition="", orderBy="";
		        
		        if(!userSID.equals("")){
		        	//get password values, otherwise, get set info only 
		        	passwordColumn = ", y.parametersetid , y.usersid, y.passwordindex, y.value ";
		        	passwordTable = ", passwordparametervalues y ";
		        	passwordJoinCondition = "AND x.parametersetid = y.parametersetid AND y.usersid = '"+userSID+"' ";
		        	orderBy = "ORDER BY y.passwordindex	";
		        } 
		        
				sql = "SELECT a.programtype AS Root, a.name AS Engagement, b.name AS Activity, "+
				       "d.name AS Project, e.name AS Analytic, x.name AS ParameterSet, x.revision "+
				             passwordColumn+
					  "FROM audititems a, audititems b, audititems c, audititems d, audititems e, "+
					        "analyticparameterset x " + passwordTable+
					  "WHERE a.programtype = '"+type+"' "+
					     "AND a.name = '"+bc+"' AND a.itemtype = 'BC' AND a.id = b.parentid "+
					     "AND b.name = '"+lc+"' AND b.itemtype = 'LC' AND b.id = c.parentid "+
					     "AND c.name = 'Analytic Container' AND c.itemtype = 'AC' AND c.id = d.parentid "+
					     "AND d.name like '%"+gap+"' AND d.itemtype = 'GAP' AND d.id = e.parentid "+
					     "AND e.name = '"+an+"' AND e.itemtype = 'AN' " +
					     "AND e.id = x.analyticitemid AND x.name = '"+setName+"' "+
					     passwordJoinCondition+
					   orderBy;
					     		
				return sql;
			}	
			
			
			public static String getParameterSetID(String type, String bc, String lc, String gap, String an, String setName){
				
				String sql="";

				sql = "SELECT a.programtype, a.name, b.name, c.name,d.name, e.name, x.name, x.parametersetid "+
					  "FROM audititems a, audititems b, audititems c, audititems d, audititems e, "+
					        "analyticparameterset x "+
					  "WHERE a.programtype = '"+type+"' "+
					     "AND a.name = '"+bc+"' AND a.itemtype = 'BC' AND a.id = b.parentid "+
					     "AND b.name = '"+lc+"' AND b.itemtype = 'LC' AND b.id = c.parentid "+
					     "AND c.name = 'Analytic Container' AND c.itemtype = 'AC' AND c.id = d.parentid "+
					     "AND d.name like '%"+gap+"' AND d.itemtype = 'GAP' AND d.id = e.parentid "+
					     "AND e.name = '"+an+"' AND e.itemtype = 'AN' " +
					     "AND e.id = x.analyticitemid AND x.name = '"+setName+"'";
					     		
				return sql;
			}
			public static String getParameterSetID(String testSetID, String an, String setName){
				
				String sql="";

				sql = "SELECT x.parametersetid "+
					  "FROM audititems a, audititems b, "+
					        "analyticparameterset x "+
					  "WHERE a.itemtype = 'GAP' AND a.id = '"+testSetID+"' "+
					     "AND b.name = '"+an+"' AND b.itemtype = 'AN' " +
					     "AND b.parentid = a.id "+
					     "AND b.id = x.analyticitemid AND x.name = '"+setName+"'";
					     		
				return sql;
			}		
			
			public static String deleteParameterSet(String id){
				String sql="";
				sql = "DELETE "+
				      "FROM analyticparameterset "+
					  "WHERE parametersetid = '"+id+"'"
					  ;
				return sql;
				
			}
			public static String getAnalyticID(String type, String bc, String lc, String gap, String an){
				
				String sql="";

				sql = "SELECT a.programtype, a.name, b.name, c.name,d.name, e.name, e.id "+
					  "FROM audititems a, audititems b, audititems c, audititems d, audititems e "+
					  "WHERE a.programtype = '"+type+"' "+
					     "AND a.name = '"+bc+"' AND a.itemtype = 'BC' AND a.id = b.parentid "+
					     "AND b.name = '"+lc+"' AND b.itemtype = 'LC' AND b.id = c.parentid "+
					     "AND c.name = 'Analytic Container' AND c.itemtype = 'AC' AND c.id = d.parentid "+
					     "AND d.name like '%"+gap+"' AND d.itemtype = 'GAP' AND d.id = e.parentid "+
					     "AND e.name = '"+an+"' AND e.itemtype = 'AN'";
					     		
				return sql;
			}
			
			public static String getUserRoles(String dbtype,String name, String sid){
				String sql="";
				
			    sql = "SELECT r.id,r.name,r.description,u.sid "+
			           "FROM roles r,userrole u "+ 
			           "WHERE r.name = '"+name+"' "+
			              "AND u.sid = '"+sid+"' "+
			              "AND u.roleid = r.id";
			return sql;
		}	
			public static String getUserRoles(String dbtype,String name){
				String sql="";
				
				    sql = "SELECT id,name,description,validfrom,validto,application,entity_required,createddate "+
				           "FROM roles "+
				           "WHERE name = '"+name+"'";
				return sql;
			}	
			
		    // Example of query get method
			public static String getUserRoles(String dbtype){
				String sql="";
				
				if(dbtype.contains("PostgreSQL")){
				    sql = "SELECT id,name,description,validfrom,validto,application,entity_required,createddate "+
				           "FROM roles "+
				           "LIMIT 20";
				}else if(ACLQATestScript.dbConf.dbtype.contains("Oracle")){
				    sql = "SELECT id,name,description,validfrom,validto,application,entity_required,createddate "+
			           "FROM roles "+
			           "WHERE rownum<=20 ";
				}
				return sql;
			}

			// ******** SET GWEM users ****************
			
			public static String getInsertUsers(String sid,
					String password,String lastname,String firstname, String email,Timestamp validto,String department){
		        String sql="";
				sql = "INSERT INTO users (sid, password, lastname, firstname, email, department) "+
				          "VALUES ('"+sid+"', "+password+", '"+lastname+"', '"+
				                      firstname+"', '"+email+"', '"+department+"')";
				return sql;
			}
			
			public static String getUpdateUsers(String sid,
					String password,String lastname,String firstname, String email,Timestamp validto,String department){
		        String sql="";
				sql = "UPDATE users "+
				      " SET "+
				          //"password="+password+
				          " lastname='"+lastname+"'"+
				          ", firstname='"+firstname+"'"+
				          ", email='"+email+"'"+
				          //", validto='"+validto+"' "+
				          ", department='"+department+"'"+
				      " WHERE sid = '"+sid+"' ";
				return sql;
			}
			public static String getInsertUserRole(String sid,
					String roleId,Timestamp validto,String entityid){
				String sql="";
				
				if(entityid.equals("")){
					sql =  "INSERT INTO userRole ( sid,roleId ) "+
			          "VALUES ('"+sid+"', "+roleId+")";
				}else{
					sql =  "INSERT INTO userRole ( sid,roleId ,entityid ) "+
			          "VALUES ('"+sid+"', "+roleId+", '"+entityid+"')";
				}
				return sql;
			}
			
			public static String getUpdateUserRole(String sid,
					String roleId,Timestamp validto,String entityid){
				String sql="";
				//update fileds if there are any needs in the future, currently do nothing
				if(entityid.equals("")){
					sql ="UPDATE userRole "+
				      "SET roleId="+roleId+
			          " WHERE sid = '"+sid+"' AND roleId = "+roleId+" "+
			          " AND entityid is NULL ";
				}else{
					sql = "UPDATE userRole "+
					  "SET roleId="+roleId+
				      " WHERE sid = '"+sid+"' AND roleId = "+roleId+" "+
				      " AND entityid='"+entityid+"' ";
				}
				return sql;
			}
			

			/* 
			 **********************************************************************************
			 * SQL queries to Exception Databases - exceptionDBType = "SQLServer" or "Oracle" *
		     ********************************************************************************** 
			 */	


			
			//Example of query get method
			public static String getEmEntity(){
				return getEmEntity(ACLQATestScript.dbConf.dbtype);
			}
			
			public static String getEmEntity(String dbtype){
				String sql="";
				
				if(dbtype.contains("SQLServer")){
				    sql = "SELECT top 20 entityid,ui_label,description,active "+
			               "FROM em_entity ";
				    
				}else if(dbtype.contains("Oracle")){
				    sql = "SELECT entityid,ui_label,description,active "+
			           "FROM em_entity "+
			           "WHERE rownum<=20 ";
				}
				return sql;
			}
		

}
