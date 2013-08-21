package conf.bean;

public class DBConf {
	
	public String url="",driver="",    					
    					serverip="192.168.10.183",
    					port="1521",
    					dbtype="ORACLE",
    					dbname="xe",
    					userid="DC_User_02",
    					passwd="password";
	
	public String workingRoot = "WORKING";
	public String  libraryRoot = "LIBRARY";
	
	public String ax_userRole = "user",
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
	
	
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setServerip(String serverip) {
		this.serverip = serverip;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
    
	//Invoked after loading....
	
	public void setJDBCParameters(){
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
