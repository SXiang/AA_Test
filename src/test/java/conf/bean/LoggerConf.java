package conf.bean;

import com.acl.qa.taf.util.FileUtil;

public class LoggerConf {
	
	public int iLogLevel = 40;
	public int filterLevel = 5;
	public int batchRunfilterLevel = 3;	
    public boolean doneBatch = false,
                          doneTest = false;
	public String logRoot = "";
	public String userUniqueID = "";
	
	public int getBatchRunfilterLevel() {
		return batchRunfilterLevel;
	}

	public void setBatchRunfilterLevel(int batchRunfilterLevel) {
		if(!doneBatch){
		  this.batchRunfilterLevel = batchRunfilterLevel;
		}
		doneBatch = true;
	}
	
	public String getLogRoot() {
		return logRoot;
	}

	public void setLogRoot(String logRoot) {
		this.logRoot = FileUtil.getAbsDir(logRoot);
	}

	public int getILogLevel() {
		return iLogLevel;
	}

	public void setILogLevel(int logLevel) {
		iLogLevel = logLevel;
	}

	public int getFilterLevel() {
		return filterLevel;
	}

	public void setFilterLevel(int filterLevel) {
		if(!doneTest)
		   this.filterLevel = filterLevel;
		doneTest = true;
	}
}
