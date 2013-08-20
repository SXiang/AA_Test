package conf.bean;

import com.acl.qa.taf.util.FileUtil;

public class LoggerConf {

	public int iLogLevel = 40;
	public int filterLevel = 5;
	public int batchRunfilterLevel = 3;
	public boolean doneBatch = false, doneTest = false, openHtmlReport = false,
			openLogFile = false;
	public String logRoot = "";
	public String userUniqueID = "";
	public String logDirForPublic = "//nas2-dev/DevRoot/QA/Projects/Galvatron/Automation/";

	
	public boolean isOpenHtmlReport() {
		return openHtmlReport;
	}

	public void setOpenHtmlReport(boolean openHtmlReport) {
		this.openHtmlReport = openHtmlReport;
	}

	public boolean isOpenLogFile() {
		return openLogFile;
	}

	public void setOpenLogFile(boolean openLogFile) {
		this.openLogFile = openLogFile;
	}

	public int getBatchRunfilterLevel() {
		return batchRunfilterLevel;
	}

	public void setLogDirForPublic(String logDirForPublic) {
		this.logDirForPublic = FileUtil.getAbsDir(logDirForPublic);

	}

	public void setBatchRunfilterLevel(int batchRunfilterLevel) {
		if (!doneBatch) {
			this.batchRunfilterLevel = batchRunfilterLevel;
		}
		doneBatch = true;
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
		if (!doneTest)
			this.filterLevel = filterLevel;
		doneTest = true;
	}
}
