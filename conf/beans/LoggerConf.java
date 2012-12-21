package conf.beans;

public class LoggerConf {
	
	public static int iLogLevel = 40;
	public static int filterLevel = 5;
	public static int batchRunfilterLevel = 3;	
    public static boolean doneBatch = false,
                          doneTest = false;
	public static String logRoot = "";
	public static String userUniqueID = "";
	
	public static int getBatchRunfilterLevel() {
		return batchRunfilterLevel;
	}

	public static void setBatchRunfilterLevel(int batchRunfilterLevel) {
		if(!doneBatch){
		  LoggerConf.batchRunfilterLevel = batchRunfilterLevel;
		}
		doneBatch = true;
	}
	
	public static String getLogRoot() {
		return logRoot;
	}

	public static void setLogRoot(String logRoot) {
		LoggerConf.logRoot = logRoot;
	}

	public static int getILogLevel() {
		return iLogLevel;
	}

	public static void setILogLevel(int logLevel) {
		iLogLevel = logLevel;
	}

	public static int getFilterLevel() {
		return filterLevel;
	}

	public static void setFilterLevel(int filterLevel) {
		if(!doneTest)
		   LoggerConf.filterLevel = filterLevel;
		doneTest = true;
	}
}
