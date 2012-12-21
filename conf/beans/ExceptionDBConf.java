package conf.beans;

public class ExceptionDBConf {
	
	public static String url="",driver="",    					
						serverip="192.168.10.183",
						port="1521",
						dbtype="ORACLE",
						dbname="XE",
						userid="EM_3",
						passwd="EM_3";

    //Getters
	
	//Setters 

	public static void setUserid(String userid) {
		ExceptionDBConf.userid = userid;
	}

	public static void setServerip(String serverip) {
		ExceptionDBConf.serverip = serverip;
	}

	public static void setPort(String port) {
		ExceptionDBConf.port = port;
	}

	public static void setDbtype(String dbtype) {
		ExceptionDBConf.dbtype = dbtype;
	}

	public static void setDbname(String dbname) {
		ExceptionDBConf.dbname = dbname;
	}

	public static void setPasswd(String passwd) {
		ExceptionDBConf.passwd = passwd;
	}

	// Invoked after loading...
	public static void setJDBCParameters(){
		if(dbtype.contains("Oracle")){
		  //url=jdbc:oracle:thin:@//192.168.10.156:1521/oracle
			url = "jdbc:oracle:thin:@//"+
				  serverip+":"+port+"/"+
		          dbname;
			driver="oracle.jdbc.driver.OracleDriver";

		}
 		else if(dbtype.contains("SQLServer")){
 			//url=jdbc:sqlserver://192.168.10.174:1433;DatabaseName=AXExceptionAutomation
 			url = "jdbc:sqlserver://"+
		       serverip+":"+port+";DatabaseName="+
		       dbname;
 			driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
 				
 		}
	}

}
