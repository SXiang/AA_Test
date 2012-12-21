package conf.beans;

import java.io.IOException;
import java.util.Properties;

public class AXCoreDBConf {
	
	public static String url="",driver="",    					
    					serverip="192.168.10.183",
    					port="1521",
    					dbtype="ORACLE",
    					dbname="xe",
    					userid="DC_User_02",
    					passwd="password";
	
	public static String workingRoot = "WORKING";
	public static String  libraryRoot = "LIBRARY";
	
	public static String ax_userRole = "user",
	                     ax_adminRole = "useradmin",
	                     ax_gatewayAccess = "gatewayAccess",
	                     em_admin = "EM Admin",
	                     em_readOnly = "Read Only EM",
	                     em_accessAll = "Access All Entities EM",
	                     em_showAll = "Show All Entities EM",
	                     em_primaryReviewer = "Primary Reviewer",
	                     em_secondaryReviewer = "Secondary Reviewer";
	              

//Getters

	
//Setters
	
	
	public static void setUserid(String userid) {
		AXCoreDBConf.userid = userid;
	}

	public static void setServerip(String serverip) {
		AXCoreDBConf.serverip = serverip;
	}

	public static void setPort(String port) {
		AXCoreDBConf.port = port;
	}

	public static void setDbtype(String dbtype) {
		AXCoreDBConf.dbtype = dbtype;
	}

	public static void setDbname(String dbname) {
		AXCoreDBConf.dbname = dbname;
	}

	public static void setPasswd(String passwd) {
		AXCoreDBConf.passwd = passwd;
	}
    
	//Invoked after loading....
	
	public static void setJDBCParameters(){
		if(dbtype.contains("Oracle")){
		  //url=jdbc:oracle:thin:@//192.168.10.156:1521/oracle
			url = "jdbc:oracle:thin:@//"+
				  serverip+":"+port+"/"+
		          dbname;
			driver="oracle.jdbc.driver.OracleDriver";

		}
 		else if(dbtype.contains("PostgreSQL")){
 			//"jdbc:postgresql://192.168.10.140:5432/AclAuditExchangeDB"
 			url = "jdbc:postgresql://"+
 			       serverip+":"+port+"/"+
 			       dbname;
 			driver="org.postgresql.Driver";   
 		}
	}
    
}
