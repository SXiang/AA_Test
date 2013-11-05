package conf.bean;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;

import net.sf.cache4j.CacheFactory;

import com.acl.qa.taf.helper.superhelper.*;
import com.acl.qa.taf.util.*;

public class ProjectConf {

	// **** User defined parameters - declared on demand *****
	public String unicodeServerIP = "192.168.10.95",
			nonUnicodeServerIP = "192.168.10.98",
			serverPrefix = "C:/ACL/Automation/RFT_DATA/",
			serverNetDir = ":/ACL/Automation/",
			serverNetUser = "Administrator", serverNetPassword = "Password00",
			localizationDir = "", l10nPropertiesPrefix = "",tempLocalDir = "", tempServerNetDir = "",
			tempServerDir = "", serverInputDataDir = "",
			localInputDataDir = "",
			testDataDir = "",
			toolDir = "";

	// ********************************************************
	public String superMDir = "/master/",expectedDir = "/expecteddata/",actualDir="/actualdata/";
	// ********** Setters - Auto generated and dir addressed *****************



	
	public void setTempTestSummary(boolean tempTestSummary) {
		this.tempTestSummary = tempTestSummary;
	}

	public void setL10nPropertiesPrefix(String l10nPropertiesPrefix) {
		this.l10nPropertiesPrefix = l10nPropertiesPrefix;
	}

	public void setToolDir(String toolDir) {
		this.toolDir = FileUtil.getAbsDir(toolDir);
	}

	public void setTestDataDir(String testDataDir) {
		this.testDataDir = FileUtil.getAbsDir(testDataDir);
	}

	public void setTraceMemusage(boolean traceMemusage) {
		this.traceMemusage = traceMemusage;
	}

	public void setInBriefModel(boolean inBriefModel) {
		this.inBriefModel = inBriefModel;
	}

	public void setCopyToPublicDir(boolean copyToPublicDir) {
		this.copyToPublicDir = copyToPublicDir;
	}

	public void setProjconfigured(boolean projconfigured) {
		this.projconfigured = projconfigured;
	}

	public void setUpdateMasterFile(boolean updateMasterFile) {
		this.updateMasterFile = updateMasterFile;
	}

	public void setUpdateProjects(boolean updateProjects) {
		this.updateProjects = updateProjects;
	}

	public void setUnicodeTest(boolean unicodeTest) {
		this.unicodeTest = unicodeTest;
	}

	public boolean isUnicodeTest() {
		return unicodeTest;
	}

	public void setDailyTest(boolean dailyTest) {
		this.dailyTest = dailyTest;
	}

	public void setTimeIntervalForMemusage(int timeIntervalForMemusage) {
		this.timeIntervalForMemusage = timeIntervalForMemusage;
	}

	public void setWikiLink(String wikiLink) {
		this.wikiLink = wikiLink;
	}

	public void setTestDescApp(String testDescApp) {
		this.testDescApp = testDescApp;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setAclProjectDir(String aclProjectDir) {
		this.aclProjectDir = FileUtil.getAbsDir(aclProjectDir);
	}


	public void setBuildInfo(String buildInfo) {
		this.buildInfo = buildInfo;
	}



	public void setTestProject(String testProject) {
		this.testProject = testProject;
	}

	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}

	public void setInputDataDir(String inputDataDir) {
		this.inputDataDir = FileUtil.getAbsDir(inputDataDir);
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setAUT(String aUT) {
		AUT = FileUtil.getAbsDir(aUT);
	}

	public void setAppLocale(String appLocale) {
		this.appLocale = appLocale;
	}

	public void setStartComm(String startComm) {
		this.startComm = startComm;
	}

	public void setOsname(String osname) {
		this.osname = osname;
	}

	public void setWinUser(String winUser) {
		this.winUser = winUser;
	}

	public void setWinName(String winName) {
		this.winName = winName;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	public void setUnicodeServerIP(String unicodeServerIP) {
		this.unicodeServerIP = unicodeServerIP;
	}

	public void setNonUnicodeServerIP(String nonUnicodeServerIP) {
		this.nonUnicodeServerIP = nonUnicodeServerIP;
	}

	public void setServerPrefix(String serverPrefix) {
		this.serverPrefix = serverPrefix;
	}

	public void setServerNetDir(String serverNetDir) {
		this.serverNetDir = serverNetDir;
	}

	public void setServerNetUser(String serverNetUser) {
		this.serverNetUser = serverNetUser;
	}

	public void setServerNetPassword(String serverNetPassword) {
		this.serverNetPassword = serverNetPassword;
	}

	public void setLocalizationDir(String localizationDir) {
		this.localizationDir = FileUtil.getAbsDir(localizationDir);
	}

	public void setTempLocalDir(String tempLocalDir) {
		if(isUnicodeTest()){
			tempLocalDir = tempLocalDir+"Unicode/";
		}else{
			tempLocalDir = tempLocalDir+"NonUnicode/";
		}
		this.tempLocalDir = FileUtil.getAbsDir(tempLocalDir);
	}

	public void setTempServerNetDir(String tempServerNetDir) {
		this.tempServerNetDir = tempServerNetDir;
	}

	public void setTempServerDir(String tempServerDir) {
		this.tempServerDir = tempServerDir;
	}

	public void setServerInputDataDir(String serverInputDataDir) {
		this.serverInputDataDir = serverInputDataDir;
	}

	public void setLocalInputDataDir(String localInputDataDir) {
		this.localInputDataDir = FileUtil.getAbsDir(localInputDataDir);
	}

	// **********************************************************************
	// ********** Default configurations - modify with caution *************
	// **********************************************************************

	public boolean tempTestSummary = true;
	public boolean traceMemusage = true;
	public boolean inBriefModel = false;
	public boolean copyToPublicDir = true;
	public boolean projconfigured = false;
	public boolean updateMasterFile = false;
	public boolean updateProjects = false;
	public boolean jenkinsReport = true;
	public boolean unicodeTest = false;
	public boolean dailyTest = false;

	public int timeIntervalForMemusage = 1;
	public int maxMemUsage = Integer.MAX_VALUE;
	public int stopIfNumConsecutiveFailures = Integer.MAX_VALUE;

	public String wikiLink = "";
	public String testDescApp = "\n\tDescription of the tested area goes here....";

	public String projectName = "";
    public String traceImageName = "";
	public String locale = FileUtil.locale.toString();
	public String aclProjectDir = "";
    public String
			//tempCsvFile = tempLocalDir+"automationTempData.csv",
            //tempCsvMainFile = tempLocalDir+"automationTempMainData.csv",
            tempCsvResult = tempLocalDir+"automationTempResult.csv",
			buildInfo = "",
			testProject = "",
			testerName = "Automation Demo Tester",
			inputDataDir = "", jenkinsReportDir = "", appName = "ACLWin";

	public String AUT="", appLocale = "", startComm, imageName;

	// Getter & Setter

	public String osname = System.getProperty("os.name").replaceAll(
			"[/\\\\:*?,|\\-\\s]", "_"), winUser = System.getProperty(
			"user.name").replaceAll("[/\\\\:*?,|\\-\\s]", "_"), winName = "",
			hostIP = "";

	
	public void setTraceImageName(String traceImageName) {
		this.traceImageName = traceImageName;
	}

	public void setJenkinsReport(boolean jenkinsReport) {
		this.jenkinsReport = jenkinsReport;
	}
    
	

	public void setStopIfNumConsecutiveFailures(int stopIfNumConsecutiveFailures) {
		if (stopIfNumConsecutiveFailures <= 0)
			stopIfNumConsecutiveFailures = Integer.MAX_VALUE;
		this.stopIfNumConsecutiveFailures = stopIfNumConsecutiveFailures;
	}

	public void setMaxMemUsage(int maxMemUsage) {
		if (maxMemUsage <= 0)
			maxMemUsage = Integer.MAX_VALUE;
		this.maxMemUsage = maxMemUsage;
	}

	public void setJenkinsReportDir(String jenkinsReportDir) {
		this.jenkinsReportDir = jenkinsReportDir;
		if (!jenkinsReportDir.equals("")) {
			this.jenkinsReport = true;
		} else {
			this.jenkinsReport = false;
		}
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
		// System.out.println("Set imageName: "+imageName+"?");
		setFinalization(true);
	}

	// ***************************************************************************************
	// ************** Some further settings/confs for each project
	// **************************
	// ***************************************************************************************

	public void setFinalization(boolean finalization) {
        if(projectName.trim()==""){
        	projectName = testProject;
        }
		if (!finalization || projconfigured)
			return;

		hostIP = LoggerHelper.hostIP;
		winName = osname;

		winName += "_IP"
				+ hostIP.replaceAll("192\\.168\\.", "").replaceAll("\\.", "_")
				+ "";
		ACLQATestScript.loggerConf.userUniqueID = winUser + "/" + winName + "/";
		ACLQATestScript.loggerConf.logDirForPublic += ACLQATestScript.loggerConf.userUniqueID;
		
		tempServerNetDir = tempServerNetDir
				+ ACLQATestScript.loggerConf.userUniqueID;
		tempServerDir = serverPrefix + tempServerDir
				+ ACLQATestScript.loggerConf.userUniqueID;
		
		//tempCsvFile = tempLocalDir+"automationTempData.csv";
	    //tempCsvMainFile = tempLocalDir+"automationTempMainData.csv";
	    tempCsvResult = tempLocalDir+"automationTempResult.csv";
	    
		serverInputDataDir = serverPrefix + serverInputDataDir;

		if (AUT!=null&&AUT != "") {
			this.startComm = "Start \"" + imageName + "\" /D\""
					+ FileUtil.getAbsDir(AUT)
					+ "\\\" /MAX /B /WAIT /SEPARATE \"" + AUT + imageName
					+ "\"";
		} else {
			this.startComm = "";
		}

		// FileUtil.mapDrive(curLabel,serverNetDir,serverNetUser,serverNetPassword);
		setL10NEnv();
		setVPPath();
		initCache();
		return;
	}
	
	public void setVPPath(){
		String uniFolder = "Unicode/";
		if(isUnicodeTest()){
			superMDir += uniFolder;
			expectedDir += uniFolder;
			actualDir += uniFolder;
		}
		
		if(!(appLocale.equalsIgnoreCase("")&&appLocale.equalsIgnoreCase("En"))){
			superMDir += appLocale.toLowerCase()+"/";
			expectedDir += appLocale.toLowerCase()+"/";
			actualDir += appLocale.toLowerCase()+"/";
		}
	}
	public void setL10NEnv(){
		
		String prefix = FileUtil.locale.getLanguage();
		if("".equals(this.appLocale)){
			this.appLocale = prefix;	
			//changeMap = false;
			//NLSUtil.appLocale = new Locale(ProjectConf.appLocale);
		}else if("en".equalsIgnoreCase(this.appLocale)){
			//changeMap = false;
		}else if(!this.appLocale.equalsIgnoreCase(prefix)){           

				 FileUtil.locale = new Locale(this.appLocale);
				 TAFLogger.updateLogger(); 
                 LoggerHelper.logTAFInfo("Are you testing application ("+
							this.appLocale+")"+ " with system locale - '"+prefix+"'?");

		}
		
// for locale change - temp (Steven)
		
		prefix = this.appLocale;			
		NLSUtil.appLocale = FileUtil.locale;
	
		this.locale = appLocale;
		LoggerHelper.localizationDir = localizationDir;
		//LoggerHelper.logTAFInfo("Map updated for - Locale "+prefix);
		return;
	}
	public void initCache() {
		String cacheConfig = localizationDir + "cache4j_config.xml";

		try {
			CacheFactory сacheFactory = CacheFactory.getInstance();
			InputStream in = new FileInputStream(cacheConfig);
			сacheFactory.loadConfig(in);
			LoggerHelper.cache_l10n = сacheFactory.getCache("cache_l10n");
			LoggerHelper.cache_l10n.clear();
			LoggerHelper.cache_en = сacheFactory.getCache("cache_en");
			LoggerHelper.cache_en.clear();
			// LoggerHelper.logTAFWarning(LoggerHelper.cache_l10n.getCacheInfo().toString());
			// LoggerHelper.logTAFWarning(LoggerHelper.cache_en.getCacheInfo().toString());
		} catch (Exception e) {
			LoggerHelper.logTAFWarning("Cache4jException? " + e.toString());
			LoggerHelper.cache_l10n = null;
			LoggerHelper.cache_en = null;
		}

	}

	// Added for Desktop - Steven
	public String testType = "";
    public String toAddress = "";
    public String ccAddress = "";
    public String bccAddress = "";
    
    
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}

	public void setTestType(String testType) {
		if (testType.equalsIgnoreCase("LOCALONLY")) {
			LoggerHelper.localOnlyTest = true;
		} else {
			LoggerHelper.localOnlyTest = false;
		}
		if (!testType.equalsIgnoreCase("SERVER")) {
			testType = "LOCAL";
		}
		this.testType = testType;
		if (this.testType.equalsIgnoreCase("LOCALONLY")) {
			LoggerHelper
					.logTAFWarning("ProjectType - '" + this.testType + "'?");
			this.testType = "LOCAL";
		}
	}
	
	// ************ Not used, will be moved later - steven
	public String serverName = "";
}