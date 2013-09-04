package conf.bean;

import com.acl.qa.taf.helper.superhelper.DatabaseHelper;



public class DBConf {
	
	public String url="",driver="",newUrl="",newDriver="";    					
    public String serverip="192.168.10.183",
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
		setJDBCParameters(dbtype,serverip,port,dbname);
	}
	public void setJDBCParameters(String dbtype,String serverip,String port,String dbname){
//		Oracle Thin
//		jdbc:oracle:thin:@<HOST>:<PORT>:<SID>
//		oracle.jdbc.driver.OracleDriver
		if(dbtype.contains("Oracle")){
		  //url=jdbc:oracle:thin:@//192.168.10.156:1521/oracle
			newUrl = "jdbc:oracle:thin:@//"+
				  serverip+":"+port+"/"+
		          dbname;
			newDriver="oracle.jdbc.driver.OracleDriver";
            new oracle.jdbc.driver.OracleDriver();
		}
		
//		PostgreSQL (v6.5 and earlier)
//		jdbc:postgresql://<HOST>:<PORT>/<DB>
//		postgresql.Driver
//
//		PostgreSQL (v7.0 and later)
//		jdbc:postgresql://<HOST>:<PORT>/<DB>
//		org.postgresql.Driver		
		
 		else if(dbtype.contains("PostgreSQL")){
 			//"jdbc:postgresql://192.168.10.140:5432/AclAuditExchangeDB"
 			newUrl = "jdbc:postgresql://"+
 			       serverip+":"+port+"/"+
 			       dbname;
 			newDriver="org.postgresql.Driver";   
 			new org.postgresql.Driver();
 		}
		
//		Microsoft SQL Server
//		jdbc:weblogic:mssqlserver4:<DB>@<HOST>:<PORT>
//		weblogic.jdbc.mssqlserver4.Driver
//		Microsoft SQL Server 2000 (Microsoft Driver)
//		jdbc:microsoft:sqlserver://<HOST>:<PORT>[;DatabaseName=<DB>]
//		com.microsoft.jdbc.sqlserver.SQLServerDriver	
		
 		else if(dbtype.contains("SQLServer")){
 			//url=jdbc:sqlserver://192.168.10.174:1433;DatabaseName=AXExceptionAutomation
 			newUrl = "jdbc:sqlserver://"+
		       serverip+":"+port+";DatabaseName="+
		       dbname;
 			newDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
 			new com.microsoft.sqlserver.jdbc.SQLServerDriver();
 				
 		} 
//		IBM DB2
//		jdbc:db2://<HOST>:<PORT>/<DB>
//		COM.ibm.db2.jdbc.app.DB2Driver
 		else if(dbtype.contains("DB2")){
 			newUrl = "jdbc:db2://"+
		       serverip+":"+port+"/"+
		       dbname;
 			newDriver="com.ibm.db2.jcc.DB2Driver";
 			new com.ibm.db2.jcc.DB2Driver();	
 		}
		
		if(url.equals("")||driver.equals("")){
			url = newUrl;
			driver = newDriver;
		}
	}
    
}
