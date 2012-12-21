package lib.acl.helper.sup;

import java.net.URL;
import java.sql.*;
import java.util.*;
import java.io.*;

import conf.beans.*;

public class DatabaseHelper extends LoggerHelper {

		private static Connection axCoreDBConn = null,
		                          exceptionDBConn = null;
		private static Statement axCoreDBStmt = null,
		                         exceptionDBStmt = null;
		
		private static Properties sidprop = null;
		private static String userSIDProperties = "conf/userSID.properties";
		private static String thisUserName ="";
		private static String sid = "";
    
		
	    public static String preSavedEXID=""; // for EX work flow testing across keywords
	    public static String preSavedResultFolder="";//for gateway dynamic result folder verification.
	    public static String preSavedPSet = ""; //for the reuse of current Analytic parameter set as it's randomly named and unique.
	    public static String currentItemDBPath =""; // 
	    
		// Main APIs
		public ResultSet queryAXCoreDB(String sql){
			return executeSQL(getAXCoreDBStatement(), sql);
		}
		public ResultSet queryExceptionDB(String sql){
			return executeSQL(getExceptionDBStatement(), sql);
		}
		
		public static String getUserSID(String username){
			String searchName=username.replace("\\", "/");
			if(username.equals(thisUserName)){
				return sid;
			}
			
			if(sidprop==null){
				sidprop = new Properties(); 
				try { 
						sidprop.load(new FileInputStream(userSIDProperties)); 
					} 
				    catch (Exception e) {
				    	logTAFWarning("Failed to load "+userSIDProperties+", please check your file and try again");
				    } 
			}
			try{
				sid = sidprop.getProperty(searchName);
			}catch(Exception e){
				logTAFWarning("Failed to get the sid for '"+username+"'");
			}
			
			if(sid == null){
				logTAFDebug("Not found sid for '"+username+"'");
				sid = "";
			}
			return sid;			
			}
		
		//ResultSet and Statements
		protected  ResultSet executeSQL(Statement stmt, String sql){
			ResultSet rs = null;
			try {
				rs = stmt.executeQuery(sql);
			} catch (SQLException e) {
				logTAFWarning(e.toString());
			}
			return rs;
		}

	    protected  Statement getAXCoreDBStatement() 
	    {
	    	if(!validDBStmt(axCoreDBStmt)){
	    		axCoreDBStmt = getStatement(getAXCoreDBConnection());
	    	}
	        return axCoreDBStmt;
	    }
	    
	    protected  Statement getExceptionDBStatement() 
	    {
	    	 if(!validDBStmt(exceptionDBStmt)){
	    		 exceptionDBStmt = getStatement(getExceptionDBConnection());
	    	 }
	         return exceptionDBStmt;
	    }
	    
	    // Singleton DB Connections
	    protected  Connection getAXCoreDBConnection() 
	    {
	    	if(!validDBConn(axCoreDBConn)){
	    		axCoreDBConn = newAXCoreDBConnection();

	    	}
	        return axCoreDBConn;
	    }
	    protected  Connection getExceptionDBConnection() 
	    {
	    	 if(!validDBConn(exceptionDBConn)){
	    		 exceptionDBConn = newExceptionDBConnection();
			 }
	         return exceptionDBConn;
	    }
	    
	    // New DB Connections
	    protected  Connection newAXCoreDBConnection() 
	    {
	    		axCoreDBConn = newConnection(AXCoreDBConf.driver,AXCoreDBConf.url, 
	    				AXCoreDBConf.userid, AXCoreDBConf.passwd);
	        return axCoreDBConn;
	    }
	    protected  Connection newExceptionDBConnection() 
	    {
	    		 exceptionDBConn = newConnection(ExceptionDBConf.driver,ExceptionDBConf.url, 
	    				 ExceptionDBConf.userid, ExceptionDBConf.passwd);
	         return exceptionDBConn;
	    }	  
	    
	    // New connection with user defined parameters
	    protected static Connection newConnection(String driver, String url,String userid, String passwd) 
	    {
	    	DatabaseMetaData dma = null;
	    	
	        Connection conn=null;
	        try {
	         Class.forName(driver);
	         conn = DriverManager.getConnection(url, userid, passwd);	         
	        }catch(Exception e){
	        	  logTAFDebug(e.toString());
	        	  logTAFWarning("Failed to get DB connection through:"+
	        			  "\n\t\t driver='"+driver+"'"+
	        			  "\n\t\t url='"+url+"'"+
	        			  "\n\t\t userid='"+userid+"', passwd='"+passwd+"'");
	        	  return null;
	        }
	        try{
	        	checkForWarning(conn.getWarnings());
	        	dma = conn.getMetaData();
	        	logTAFInfo("\n\tConnected to .. " + dma.getURL());
	        	logTAFInfo("Driver ........ " + dma.getDriverName());
	        	logTAFInfo("Version ....... " + dma.getDriverVersion());			
	        	dma = null;
	        }catch(Exception e){
	        	logTAFDebug(e.toString());
	        }
			
	        return conn;
	    } 
	    
	    
	    protected Statement getStatement(Connection conn)
	    {
	      Statement statement = null;
	      try {
	             statement = conn.createStatement();
	           } catch (SQLException ex){
	              logTAFDebug(ex.toString());
	            }

	       return statement;
	    }
	    
	    
		private boolean validDBStmt(Statement stmt){
			boolean valid = true;
			try{
			     if(stmt == null)
			    	 return false;
			     if(stmt.isClosed())
			    	 return false;
			}catch(Exception e){
				logTAFDebug(e.toString());
				valid = false;
			}
			return valid;
		}
		
		private boolean validDBConn(Connection conn){
			boolean valid = true;
			try{
			     if(conn == null)
			    	 return false;
			     if(conn.isClosed())
			    	 return false;
			}catch(Exception e){
				logTAFDebug(e.toString());
				valid = false;
			}
			return valid;
		}
		
		// ******* Methods accessed at onTerminate *********
		public static void releaseDBResources(){
			//Close SQL Statements
			try{
				if(axCoreDBStmt!=null)
				     axCoreDBStmt.close();
			}catch(Exception e){
				logTAFDebug(e.toString());
			}
			try{
				if(exceptionDBStmt!=null)
				     exceptionDBStmt.close();
			}catch(Exception e){
				logTAFDebug(e.toString());
			}
			//Close SQL Connections
			try{
				if(axCoreDBConn!=null)
				     axCoreDBConn.close();
			}catch(Exception e){
				logTAFDebug(e.toString());
			}
			try{
				if(exceptionDBConn!=null)
				     exceptionDBConn.close();
			}catch(Exception e){
				logTAFDebug(e.toString());
			}

		}
		
		// -------------------------------------------------------------------
		// checkForWarning
		// Checks for and displays warnings. Returns true if a warning
		// existed
		// -------------------------------------------------------------------

		private static boolean checkForWarning(SQLWarning warn) throws SQLException {
			boolean rc = false;

			// If a SQLWarning object was given, display the
			// warning messages. Note that there could be
			// multiple warnings chained together
			if (warn != null) {
				logTAFDebug("\n *** SQL Warning ***\n");
				rc = true;
				while (warn != null) {
					logTAFDebug("SQLState: " + warn.getSQLState());
					logTAFDebug("Message:  " + warn.getMessage());
					logTAFDebug("Vendor:   " + warn.getErrorCode());
					
					warn = warn.getNextWarning();
				}
			}
			return rc;
		}

		
       //*************** Friendly methods *******************************
		protected static Vector<Vector<String>> getResultVector(ResultSet rs){
			int i;
			Vector<Vector<String>> table = new Vector<Vector<String>>();
			Vector<String> row;
			String value ="";
			//ResultSet column index started at 1
			if (rs == null) {
    			logTAFDebug ("--- NULL result set.");
    			return table;
    		}  
			
			try{
				ResultSetMetaData rsmd = rs.getMetaData();
				int numCols = rsmd.getColumnCount();

				// Column headings
				row = new Vector<String>();
				for (i = 1; i <= numCols; i++) {
					row.add(rsmd.getColumnLabel(i));
				}
				table.add(row);
				// Display data, fetching until end of the result set
				// initially positioned before the first row
				boolean more = rs.next();
				while (more) {
					row = new Vector<String>();
					for (i = 1; i <= numCols; i++) {
						 value = rs.getString(i)==null?"NULL":rs.getString(i);
					     row.add(value);
					}
					table.add(row);
					more = rs.next();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return table;
		}


		protected String getCellStringValue(Vector<Vector<String>>table,int row,int col){
			String value ="";
//            if(table==null)
//            	return value;
			try{
				value=table.elementAt(row).elementAt(col);
			}catch(Exception e){
				value = "NotFound";
			}
			if(value==null){
				value = "";
			}
			return value;
		}
        protected static int getNumColumns(ResultSet rs){
        	int numCols=0;
        	ResultSetMetaData rsmd=null;
        	rsmd = getMetaData(rs);
        	try {
				numCols = rsmd.getColumnCount();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rsmd=null;
			return numCols;
        }
        
        protected static ArrayList<String> getColumnLabels(ResultSet rs){
        	ArrayList<String> al = new ArrayList<String>();
        	ResultSetMetaData rsmd= getMetaData(rs);
        	try{
        		int numCols = rsmd.getColumnCount();
        	    for(int i=0;i<numCols;i++){
        		  al.add(rsmd.getColumnLabel(i));
        	      }
        	    }catch (SQLException e) {
    				e.printStackTrace();
    			}       	    
        	return al;
        }
        
        protected static ResultSetMetaData getMetaData(ResultSet rs){
        	ResultSetMetaData rsmd = null;
        	try {
        		rsmd = rs.getMetaData();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return rsmd;
        }
        

    	public static void displayResultSet(Vector<Vector<String>> table){
    	
    		displayResultSet(table, "Displaying Table Data");
    	}
    	
    	public static void displayResultSet(Vector<Vector<String>> table, String title){
    		if (table == null) {
    			logTAFWarning ("--- Empty result set.");
    			return;
    		}  
    		
    		Vector<String> row;
            int numCols = table.elementAt(0).size();
            int size = table.size(),lineLength=0;               
      	    int[] lengths = new int[numCols];
      	    String[] formats = new String[lengths.length];
    		String sep = " | ",lineSep = "-",line="";
	
      	  // Find the maximum length of a string in each column
      	    for (int i = 0; i < size; i++) {
      	       row = table.elementAt(i);      	       
      	       for (int j = 0; j < numCols; j++) {
      	    	   lengths[j] = Math.max(row.elementAt(j).length(), lengths[j]);
      	       }
      	    }
      	    
      	  // Generate a format string for each column
      	    for (int i = 0; i < lengths.length; i++) {
      	    	formats[i] = "%1$" + lengths[i] + "s" 
      	    	+ (i + 1 == lengths.length ? sep+"\n" : sep);
      	    	lineLength += lengths[i]+sep.length();
      	    	//logTAFDebug("Length of column "+i+": "+lengths[i]);
      	    }

      	  // Print out
      	   System.out.print("\n\t**********  "+title+" *************\n");	
      	   
      	   line = getLineSep(lineSep,lineLength-1);
      	    for (int i = 0; i < size; i++) {
      	    	if(i==0){
      	    		System.out.println("\t  "+line);
      	    	}
      	    	row = table.elementAt(i);
      	    	System.out.print("\t"+sep);
      	    	for (int j = 0; j < numCols; j++) {
      	    		System.out.printf(formats[j], 
      	    				row.elementAt(j).replaceAll(System.getProperty ( "line.separator" ),"[]"));
      	    	}
      	    	System.out.println("\t  "+line);
      	    }
    	}

        public static String getLineSep(String sep, int length){
        	String chars ="";
        	for(int i=0;i<length;i++){
        		chars += sep;
        	}
        	return chars;
        }

}
