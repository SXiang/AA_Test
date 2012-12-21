package conf.beans;

import lib.acl.helper.sup.LoggerHelper;

public class FrameworkConf {


	public static String projectName = "ACLQA_Automation";
	public static String logRoot = "../" + projectName + "_logs/";
	public static String copyFilesVbs = "lib/acl/tool/copyFiles.vbs";
	public static boolean tempTestSummary = false;
	public static boolean useExistedApp = false;
	public static boolean traceMemusage = true;
	public static boolean inBriefModel = false;
	public static int timeIntervalForMemusage = 1;
	public static int maxMemUsage = Integer.MAX_VALUE;
	public static int stopIfNumConsecutiveFailures =Integer.MAX_VALUE;
	
	// These App info will be loaded from property file as 
	// they would not be changed frequently.
	public static String application1 = "AX_GatewayPro", docGWPTitle ="AX GatewayPro";
	public static String application2 = "AX_Gateway", docGWTitle ="AX Gateway";
	public static String application3 = "AX_Admin", docAXAdminTitle ="AX Core Administration";
	public static String application4 = "AX_Exception", docEMTitle ="Ax Exception";
	public static String application5 = "AX_Addins";
	public static String application6 = "ACL_Soundwave";
	public static String application7 = "ACL_Desktop",docACLTitle = "ACL Analytics 10"; //"ACL 9"
	
    public static String debugEngagement = "-----";
	   // Error dialogs
	public static String winRuntimeErrTitle = "Microsoft Visual C++ Runtime Library";
	public static String axGatewayProErrTitle ="AuditExchange.exe";
	public static String excelErrTitle ="Microsoft Office Excel";
	public static String ironportErrTitle ="Windows Security|Define Data";
	public static String aclErrTitle ;//= "ACL Error";
	
	
	public static String wikiLink = "http://godzilla.dev.acl.com/wiki/index.php";
	public static String testDescApp1 = "\n\tDescription of the tested area goes here....";
	
	public static String testDescApp2 
	   = 
//		   "\n\tAdd items to toolbox and verify items in  toolbox"+
         "\n\tCreate result folder"+
//         "\n\tData table filter (multi criteria)  - save to results and verify"+ 
//         "\n\tData table group (multi columns)  - save to results and verify"+
	     "\n\tLogin - positive and negative"+ 
	     "\n\tRename files and results"+
	     "\n\tUpload to files"+
	     "\n\tUpload to result"+
	     "\n\tValidate items in repository"+
	     "\n\tVerify search results ( Working and Recent Work)"+
	     "\n\tVerify item's URL by loading the URL captured."+
	     "\n\tRun Analytics ( existing parameter set and new set)"+
	     "\n\tRun Analytics with Passwords( Goldbug R3 only)"+
	     "\n\tRun Analytics with list parameters( Goldbug R3 only)"+
//	     "\n\tData table profile ( from both data table and profile panel)"+
//	     "\n\tData table profile and precision checking ( Goldbug R3 only)"+
	     "\n\tUI  Quick list (Goldbug R3 feature)" +
	     "\n\tOpen Data table in Excel Addins and verify contents( Goldbug R3 only)"+
	     "";

	
	public static String testDescApp3 = "\n\tDescription of the tested area goes here....";
	
	public static String testDescApp4 
	  =  
		  "\n\tLogin/Logout EM  "+	   
		  "\n\tNavigate exception tabs and Verify all the pages by checking their page titles and presence of GUIs  "+	   
		  "\n\tApply filters in Dashboard and My Exceptions page, verify filter results and underlining links "+	   
		  "\n\tApply filters in exception details page, resetting page size,"+"\n\tverifying exception details, checking Parameters, history and backing to details page;Verify exceptions details after any modifications."+	   
		  "\n\tCreate/Edit a quick filter,Apply 'Save', 'Apply', Cancel' options"+	   
		  "\n\tDelete one or more filters and verify the existences."+	   
		  "\n\tPurge Exceptions/Purge All/View Logs, verify the results, Optionally export purge log."+	   
		  "\n\tExport/Export All Exceptions from Exceptions Details page, verify the existence of the exported file, "+"\n\toptionally view/verify Parameter Details page"+	   
		  "\n\tEdit exception by applying Actions, modify priority, add comments"+
		  "\n\tUploading Attachments to exception(s), opening/closing uploaded file by clicking the web link"+
		  "";
		  
	public static String testDescApp5 = "\n\tGui availablities - AX_Addins - Excel  "+	   
	  "\n\t  Open from AX Core"+	  
	  "\n\t  Save to AX Core"+	   
	  "\n\t  Insert Url"+	   
	  "\n\t  Run Analytic"+	  
	  //"\n\t"+
	  //"\n\t"+
	  //"\n\t"+
	  //"\n\t"+
	  //"\n\t  ..."+	   
	  //"\n\t  ..."+	   
	  "";
	public static String testDescApp6 = "\n\tGui availablities - Soundwave  "+	   
	  "\n\tDefine/Undefine data range to protect  "+	   
	  "\n\tAdd/Delete computed columns"+
	  "\n\tSummarize, Age and Stratify"+
	  "\n\tAdd/Delete/Show Notes"+
	  "\n\tAdd/Delete/Show Row Status, Edit status list"+
	  "\n\tSample"+
	  "\n\tSide Panel"+
	  "\n\tWork Paper Insert"+
	  "\n\tHelp"+
	  "\n\t"+
	  "";
	public static String testDescApp7 = "\n\tACL File Menu  "+	   
	  "\n\tACL Edit Menu  "+	   
	  "\n\tACL Data Menu"+
	  "\n\tACL Analyze Menu"+
	  "\n\tACL Sampling Menu"+
	  "\n\tACL Server Menu"+
	  "\n\tACL Data Definition Wizard"+
	  "\n\t"+
	  "";
	public static String aclProjectDir = "",//"V:/",
	                     exportDir = "lib/acl/resource/Data/Export/",	  
	                     tempCsvFile = "resources/.DatapoolScriptCache/automationTempData.csv",
	                     tempCsvMainFile = "resources/.DatapoolScriptCache/automationTempMainData.csv",
	                     tempCsvResult = "resources/.DatapoolScriptCache/automationTempResult.csv",
	                     logDirForPublic = "//nas2-dev/DevRoot/QA/Projects/Galvatron/Automation/",	                 
	                     buildInfo = "",
	                     buildName = "",

	                     serverName = "",
	                     serverIP = "",
	                     serverOS = "",
	                     serverType = "",
	                     axCoreLanguage = "english",
	                     
	                     browserName = "MS Internet Explorer",
	                     browserVersion = "",
						 testerName = "Steven_Xiang";
	
	
	

	public static void setInBriefModel(boolean inBriefModel) {
		FrameworkConf.inBriefModel = inBriefModel;
	}

	public static void setStopIfNumConsecutiveFailures(
			int stopIfNumConsecutiveFailures) {
		if(stopIfNumConsecutiveFailures<=0)
			stopIfNumConsecutiveFailures = Integer.MAX_VALUE;
		FrameworkConf.stopIfNumConsecutiveFailures = stopIfNumConsecutiveFailures;
	}

	public static void setUseExistedApp(boolean useExistedApp) {
		FrameworkConf.useExistedApp = useExistedApp;
	}

	public static void setWikiLink(String wikiLink) {
		FrameworkConf.wikiLink = wikiLink;
	}

	public static void setDebugEngagement(String debugEngagement) {
		FrameworkConf.debugEngagement = debugEngagement;
	}

	public static void setTraceMemusage(boolean traceMemusage) {
		FrameworkConf.traceMemusage = traceMemusage;
	}

	public static void setTimeIntervalForMemusage(int timeIntervalForMemusage) {
		FrameworkConf.timeIntervalForMemusage = timeIntervalForMemusage;
	}

	public static void setMaxMemUsage(int maxMemUsage) {
		if(maxMemUsage<=0)
			maxMemUsage = Integer.MAX_VALUE;
		FrameworkConf.maxMemUsage = maxMemUsage;
	}
	
	public static void setTempTestSummary(boolean tempTestSummary) {
		FrameworkConf.tempTestSummary = tempTestSummary;
	}

	public static String getTesterName() {
		return testerName;
	}

	public static void setTesterName(String testerName) {
		FrameworkConf.testerName = testerName;
	}

	public static boolean unicodeTest = false;
	
	
	
	public static String getAxCoreLanguage() {
		return axCoreLanguage;
	}

	public static void setAxCoreLanguage(String axCoreLanguage) {
		FrameworkConf.axCoreLanguage = axCoreLanguage;
	}

	public static boolean isUnicodeTest() {
		return unicodeTest;
	}

	public static void setUnicodeTest(boolean unicodeTest) {
		FrameworkConf.unicodeTest = unicodeTest;
	}

	public static String getBuildName() {
		return buildName;
	}

	public static void setBuildName(String buildName) {
		FrameworkConf.buildName = buildName;
	}

	public static String getBrowserName() {
		return browserName;
	}

	public static void setBrowserName(String browserName) {
		FrameworkConf.browserName = browserName;
	}

	public static String getBrowserVersion() {
		return browserVersion;
	}

	public static void setBrowserVersion(String browserVersion) {
		FrameworkConf.browserVersion = browserVersion;
	}



	public static String getServerName() {
		return serverName;
	}

	public static void setServerName(String serverName) {
		FrameworkConf.serverName = serverName;
	}

	public static String getServerIP() {
		return serverIP;
	}

	public static void setServerIP(String serverIP) {
		FrameworkConf.serverIP = serverIP;
	}

	public static String getServerOS() {
		return serverOS;
	}

	public static void setServerOS(String serverOS) {
		FrameworkConf.serverOS = serverOS;
	}

	public static String getServerType() {
		return serverType;
	}

	public static void setServerType(String serverType) {
		FrameworkConf.serverType = serverType;
	}

	public static boolean copyToPublicDir = true;


	public static String getBuildInfo() {
		return buildInfo;
	}

	public static void setBuildInfo(String buildInfo) {
		FrameworkConf.buildInfo = buildInfo;
	}

	public static boolean isCopyToPublicDir() {
		return copyToPublicDir;
	}

	public static void setCopyToPublicDir(boolean copyToPublicDir) {
		FrameworkConf.copyToPublicDir = copyToPublicDir;
	}

	public static String getLogDirForPublic() {
		return logDirForPublic;
	}

	public static void setLogDirForPublic(String logDirForPublic) {
		FrameworkConf.logDirForPublic = logDirForPublic;
	}


	public static String getAclProjectDir() {
		return aclProjectDir;
	}

	public static void setAclProjectDir(String aclProjectDir) {
		FrameworkConf.aclProjectDir = aclProjectDir;
	}

	public static String getExportDir() {
		return exportDir;
	}

	public static void setExportDir(String exportDir) {
		FrameworkConf.exportDir = exportDir;
	}


	public static String getProjectName() {
		return projectName;
	}

	public static void setProjectName(String projectName) {
		FrameworkConf.projectName = projectName;
	}

	public static String getLogRoot() {
		return logRoot;
	}

	public static void setLogRoot(String logRoot) {
		FrameworkConf.logRoot = logRoot;
	}

	public static String getCopyFilesVbs() {
		return copyFilesVbs;
	}

	public static void setCopyFilesVbs(String copyFilesVbs) {
		FrameworkConf.copyFilesVbs = copyFilesVbs;
	}
}

