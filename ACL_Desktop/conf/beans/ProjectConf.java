package ACL_Desktop.conf.beans;

import java.io.File;
import java.net.InetAddress;
import java.util.Locale;

import lib.acl.helper.sup.LoggerHelper;
import lib.acl.helper.sup.TAFLogger;
import lib.acl.util.FileUtil;
import lib.acl.util.NLSUtil;
import conf.beans.FrameworkConf;
import conf.beans.LoggerConf;

public class ProjectConf {
	static String locale = FileUtil.locale.toString();
	static boolean projconfigured = false;
	public static String projectName = "ACL_Desktop", // must be entered for each project
	                     pathToKeywordScripts = "ACL_Desktop.Tasks.",
	                     pathToTestCaseScripts = "ACL_Desktop.TestCase.",
	                     expectedDataDir = "ACL_Desktop/DATA/ExpectedData/",
	                     expectedDataBackupDir = "",
	                     inputDataDir = "",
	                     jenkinsReportDir = "",
	                     appName = "ACLWin";
    public static boolean updateMasterFile = false,
                          updateProjects = false,
                          finalization=true,
                          singleInstance=false,
                          jenkinsReport=false,
                          pinTable=false;
    public static String imageName = "ACLWin.exe";
    public static String autTitle = "ACL Analytics 10"; // "ACL 9"
    public static String AUT,appLocale="",startComm;
    //Getter & Setter
	public static boolean unicodeTest = false;
	public static boolean dailyTest = false;
	public static boolean emailReport = false;
	public static String ebasecamp = "STEVEN_XIANG@ACL.COM";
	public static String toAddress = "",
	                     ccAddress = "",
	                     bccAddress = "";
	public static String uNetLabel = "N";
	public static String nUNetLabel = "P";
	public static String osname = System.getProperty("os.name").replaceAll("[/\\\\:*?,|\\-\\s]", "_"),
	                     winUser = System.getProperty("user.name").replaceAll("[/\\\\:*?,|\\-\\s]", "_"),
	                     winName = "",
	                     hostIP = "";

	public static String testType = "LOCAL",
					unicodeServerIP = "192.168.10.84",
					nonUnicodeServerIP = "192.168.10.92",
					curLabel = "",
					curPrf = "acl10.prf",
					serverPrefix = "C:/ACL/Automation/RFT_DATA/",
					serverNetDir = ":/ACL/Automation/",
					serverNetUser = "Administrator",
					serverNetPassword = "Password00",
    
					localizationDir = "ACL_Desktop/DATA/LocalizationProperty/",
					objectMapDir = "ACL_Desktop/AppObject",objectMapDir2 = "",					
					i18nMapBackupDir = "",i18nMapBackupDir2 = "",
					tempLocalDir = "ACL_Desktop/TempData/",
					tempServerNetDir = "RFT_DATA/TempData/",
	                tempServerDir = "TempData/",
	                serverInputDataDir = "DataSource/",
	                localInputDataDir = "ACL_Desktop/DATA/ACLProject/DataSource/",

	                workbookDir = "ACL_Desktop/DATA/ACLProject/",
	                workbookBackupDir="",
	                testSuiteDir = "ACL_Desktop/DATA/KeywordTable/",
	                testSuiteBackupDir="";
	
	
	public static void setJenkinsReport(boolean jenkinsReport) {
//		ProjectConf.jenkinsReport = jenkinsReport;
//		LoggerHelper.RFT_jenkinsReport = ProjectConf.jenkinsReport;
	}


	public static void setJenkinsReportDir(String jenkinsReportDir) {
		ProjectConf.jenkinsReportDir = jenkinsReportDir;
		if(!jenkinsReportDir.equals("")){
		    ProjectConf.jenkinsReport = true;
		}else{
			ProjectConf.jenkinsReport = false;
		}
		LoggerHelper.RFT_jenkinsReport = ProjectConf.jenkinsReport;
		LoggerHelper.RFT_jenkinsReportDir = ProjectConf.jenkinsReportDir;
	}

	public static void setAutTitle(String autTitle) {
		ProjectConf.autTitle = autTitle;
		LoggerHelper.autTitle = autTitle;
	}

	public static void setToAddress(String toAddress) {
		ProjectConf.toAddress = toAddress;
	}

	public static void setCcAddress(String ccAddress) {
		ProjectConf.ccAddress = ccAddress;
	}

	public static void setBccAddress(String bccAddress) {
		ProjectConf.bccAddress = bccAddress;
	}

	public static void setAppLocale(String appLocale) {
		ProjectConf.appLocale = appLocale;
	}
	
	public static void setTestSuiteDir(String testSuiteDir) {
		ProjectConf.testSuiteDir = testSuiteDir;
	}

	public static void setTestSuiteBackupDir(String testSuiteBackupDir) {
		ProjectConf.testSuiteBackupDir = testSuiteBackupDir;
	}

	public static void setExpectedDataBackupDir(String expectedDataBackupDir) {
		ProjectConf.expectedDataBackupDir = expectedDataBackupDir;
	}

	public static void setWorkbookBackupDir(String workbookBackupDir) {
		ProjectConf.workbookBackupDir = workbookBackupDir;
	}

	public static void setEbasecamp(String ebasecamp) {
		ProjectConf.ebasecamp = ebasecamp;
	}

	public static void setUpdateProjects(boolean updateProjects) {
		ProjectConf.updateProjects = updateProjects;
	}

	public static void setDailyTest(boolean dailyTest) {
		ProjectConf.dailyTest = dailyTest;
		//only test first test case in data pool if not specified by other parameters
	}
	
	public static void setSingleInstance(boolean singleInstance) {
		ProjectConf.singleInstance = singleInstance;
	}

	public static void setEmailReport(boolean emailReport) {
		ProjectConf.emailReport = emailReport;
		LoggerHelper.RFT_emailReport = ProjectConf.emailReport;
	}

	public static void setObjectMapDir(String objectMapDir) {
		ProjectConf.objectMapDir = objectMapDir;
	}

	public static void setAUT(String AUT) {
		ProjectConf.AUT = AUT.replaceAll("[aA][Cc][Ll][Ww][Ii][Nn][\\.][Ee][xX][eE]", "");
	}
	public static boolean isUnicodeTest() {
		return unicodeTest;
	}

		public static void setUnicodeTest(boolean unicodeTest) {
			ProjectConf.unicodeTest = unicodeTest;
			//System.out.println("Set UnicodeTest = "+unicodeTest);
		}
	
	public static void setInputDataDir(String inputDataDir) {
			ProjectConf.inputDataDir = inputDataDir;
		}

	
	public static void setImageName(String imageName) {
		ProjectConf.imageName = imageName;
		//System.out.println("Set imageName: "+imageName+"?");
		setFinalization(true);
	}
	
//	public static String getLocale() {
//		return locale;
//	}
//
//	public static void setLocale(String locale) {
//		if(locale.matches("en_.*")){
//			locale = "en_CA";
//		}
//		ProjectConf.locale = locale;
//	}

	
	public static String getAppName() {
		return appName;
	}

	public static void setAppName(String appName) {
		ProjectConf.appName = appName;
	}              

	
	public static boolean isUpdateMasterFile() {
		return updateMasterFile;
	}

	public static void setUpdateMasterFile(boolean updateMasterFile) {
		ProjectConf.updateMasterFile = updateMasterFile;
	}



	public static String getExpectedDataDir() {
		return expectedDataDir;
	}

	public static void setExpectedDataDir(String expectedDataDir) {
		ProjectConf.expectedDataDir = expectedDataDir;
	}

	public static String getPathToTestCaseScripts() {
		return pathToTestCaseScripts;
	}

	public static void setPathToTestCaseScripts(String pathToTestCaseScripts) {
		ProjectConf.pathToTestCaseScripts = pathToTestCaseScripts;
	}

	public static String getProjectName() {
		return projectName;
	}

	public static void setProjectName(String projectName) {
		ProjectConf.projectName = projectName;
	}

	public static String getPathToKeywordScripts() {
		return pathToKeywordScripts;
	}

	public static void setPathToKeywordScripts(String pathToKeywordScripts) {
		ProjectConf.pathToKeywordScripts = pathToKeywordScripts;
	}

	public static void setOsname(String osname) {
	//	ProjectConf.osname = osname;
	}

	// Added for Desktop - Steven
	public static void setTestType(String testType) {
        if(testType.equalsIgnoreCase("LOCALONLY")){
        	LoggerHelper.localOnlyTest = true;
        	}else{
        		LoggerHelper.localOnlyTest = false;
        	}
        if(!testType.equalsIgnoreCase("SERVER")){
        	testType = "LOCAL";
        }
		ProjectConf.testType = testType;
		if(ProjectConf.testType.equalsIgnoreCase("LOCALONLY")){
			LoggerHelper.logWarning("ProjectType - '"+ProjectConf.testType+"'?");
			ProjectConf.testType = "LOCAL";
		}
	}

	public static void setUnicodeServerIP(String unicodeServerIP) {
		ProjectConf.unicodeServerIP = unicodeServerIP;
	}

	public static void setNonUnicodeServerIP(String nonUnicodeServerIP) {
		ProjectConf.nonUnicodeServerIP = nonUnicodeServerIP;
	}

	public static void setServerPrefix(String serverPrefix) {
		ProjectConf.serverPrefix = serverPrefix;
	}


	public static void setServerNetUser(String serverNetUser) {
		ProjectConf.serverNetUser = serverNetUser;
	}

	public static void setServerNetPassword(String serverNetPassword) {
		ProjectConf.serverNetPassword = serverNetPassword;
	}

	public static void setLocalizationDir(String localizationDir) {
		ProjectConf.localizationDir = localizationDir;
	}

	public static void setTempLocalDir(String tempLocalDir) {
		if(isUnicodeTest()){
			tempLocalDir = tempLocalDir+"Unicode/";
		}else{
			tempLocalDir = tempLocalDir+"NonUnicode/";
		}
		ProjectConf.tempLocalDir = tempLocalDir;
	}


	public static void setTempServerDir(String tempServerDir) {
		ProjectConf.tempServerDir = tempServerDir;
	}

	public static void setServerInputDataDir(String serverInputDataDir) {
		ProjectConf.serverInputDataDir = serverInputDataDir;
	}
	
	public static void setLocalInputDataDir(String localInputDataDir) {
		ProjectConf.localInputDataDir = localInputDataDir;
	}

	public static void setServerNetDir(String serverNetDir) {
		ProjectConf.serverNetDir = serverNetDir;
	}
	
	public static void setWorkbookDir(String workbookDir) {
		ProjectConf.workbookDir = workbookDir;
	}
	
	public static void setTempServerNetDir(String tempServerNetDir) {
		ProjectConf.tempServerNetDir = tempServerNetDir;
	}
	
	public static void setFinalization(boolean finalization){
		
		if(!finalization||projconfigured)
			return;
		if(workbookBackupDir.equals("")){
			workbookBackupDir = workbookDir;
		}
		
//		try{
//			   hostIP = (InetAddress.getLocalHost()).getHostAddress();
//			   winName = InetAddress.getLocalHost().getHostName();
//			   //winName = System.getProperty(InetAddress.getLocalHost().getCanonicalHostName());
//			}catch(Exception e){
//				System.out.println("When getting IP host: '"+e.toString()+"");
//			
//		}
			
		hostIP = LoggerHelper.hostIP;
//		winName = LoggerHelper.hostName;
//		if(winName==null){
			winName = osname;
			
//		}
		winName += "_IP"+hostIP.replaceAll("192\\.168\\.", "").replaceAll("\\.", "_")+"";
		
//		System.out.println("When getting IP : '"+hostIP+"'");
		
//		System.out.println("When gettinghost: '"+winName+"'");
		LoggerConf.userUniqueID = winUser+"/"+winName+"/";
		tempServerNetDir = tempServerNetDir+LoggerConf.userUniqueID;
        tempServerDir = serverPrefix+tempServerDir+LoggerConf.userUniqueID;
        FrameworkConf.logDirForPublic += LoggerConf.userUniqueID;
        
		serverInputDataDir = serverPrefix+serverInputDataDir;
		
		String uniPrf = "acl9.prf";
		String nonuniPrf = "aclwin9.prf";

		if(ProjectConf.autTitle.endsWith("10")){
			uniPrf = "acl10.prf";
			nonuniPrf = "aclwin10.prf";
		}else if(AUT!=""){
			FileUtil.regsvr32("ACLServer.dll",AUT);
		}
		
		if(isUnicodeTest()){
			workbookBackupDir = workbookBackupDir+"BACKUP\\Unicode\\";
			workbookDir = workbookDir+"Unicode\\";
			expectedDataDir += "Unicode\\"+locale+"\\"+ProjectConf.testType+"\\";
			expectedDataBackupDir = workbookBackupDir+"BACKUP\\ExpectedData\\Unicode\\"+locale+"\\"+ProjectConf.testType+"\\";
			curLabel = uNetLabel;
			serverNetDir = uNetLabel+": \\\\"+unicodeServerIP+"\\"+serverNetDir;		
			tempServerNetDir = uNetLabel+":/"+tempServerNetDir;
            curPrf = uniPrf;
		}else{
			workbookBackupDir = workbookBackupDir+"BACKUP\\NonUnicode\\";
			workbookDir = workbookDir+"NonUnicode\\";
			
			//expectedDataDir += "NonUnicode\\"+locale+"\\";
			//expectedDataBackupDir = workbookDir+"BACKUP\\ExpectedData\\NonUnicode\\"+locale+"\\";
			expectedDataDir += "NonUnicode\\"+locale+"\\"+ProjectConf.testType+"\\";
			expectedDataBackupDir = workbookBackupDir+"BACKUP\\ExpectedData\\NonUnicode\\"+locale+"\\"+ProjectConf.testType+"\\";
			curLabel = nUNetLabel;
			serverNetDir = nUNetLabel+": \\\\"+nonUnicodeServerIP+"\\"+serverNetDir;
			tempServerNetDir = nUNetLabel+":/"+tempServerNetDir;
			curPrf = nonuniPrf;
		}
		
		FileUtil.delFile(workbookDir+"*.prf");
		FileUtil.copyFile(workbookBackupDir+curPrf, workbookDir+curPrf);

		if(AUT!=""){
			ProjectConf.startComm = "Start \""+imageName+"\" /D\""+FileUtil.getAbsDir(workbookDir)+"\\\" /MAX /B /WAIT /SEPARATE \""+AUT+imageName+"\"";
		}else{
		    ProjectConf.startComm = "";
		}
		

		if(ProjectConf.updateProjects&&LoggerHelper.batchRun){ //***** Copy and update are time consuming, especially for remote access - Steven
			if(!updateMasterFile){		
				LoggerHelper.logTAFInfo("!!!Update all test projects and master data with backups...");
				FileUtil.updateDir(workbookBackupDir,workbookDir); 
				FileUtil.updateDir(expectedDataBackupDir,expectedDataDir);
				FileUtil.updateDir(testSuiteBackupDir,testSuiteDir);
			}else{
			//}else{
				LoggerHelper.logTAFInfo("!!!Overwrite all test projects and master data with their backup copies...");
				FileUtil.copyDir(workbookBackupDir,workbookDir); 
				FileUtil.copyDir(expectedDataBackupDir,expectedDataDir); 
				if(!testSuiteBackupDir.equals("")){
				   FileUtil.copyDir(testSuiteBackupDir,testSuiteDir);
				   FileUtil.makeWriteable(testSuiteDir);
				}
			}
			FileUtil.makeWriteable(workbookDir);
			FileUtil.makeWriteable(expectedDataDir);			
		}

			FileUtil.mapDrive(curLabel,serverNetDir,serverNetUser,serverNetPassword);
			//FileUtil.updateDir(workbookBackupDir+"DataFile\\",curLabel+":\\RFT_DATA\\Data\\DataFile\\"); 
			// curLabel+="";
		
	   //***********************  For localization test only ******************************************
	    
	        setI18nObjectMaps();
        //projconfigured = true;
	    return;
	}
    
	public static void setI18nObjectMaps(){
		String subDir = "_Karen";
		objectMapDir2 = objectMapDir+subDir+"\\";
		objectMapDir += "\\";
		boolean changeMap = true;
		
		String prefix = FileUtil.locale.getLanguage();
		if("".equals(ProjectConf.appLocale)){
			ProjectConf.appLocale = prefix;	
			//changeMap = false;
			//NLSUtil.appLocale = new Locale(ProjectConf.appLocale);
		}else if("en".equalsIgnoreCase(ProjectConf.appLocale)){
			//changeMap = false;
		}else if(!ProjectConf.appLocale.equalsIgnoreCase(prefix)){           
				LoggerHelper.logTAFInfo("Are you testing application ("+
						ProjectConf.appLocale+")"+ " with system locale - '"+prefix+"'?");
				 FileUtil.locale = new Locale(ProjectConf.appLocale);

		}
		
		TAFLogger.updateLogger(); // for locale change - temp (Steven)
		
		prefix = ProjectConf.appLocale;			
		NLSUtil.appLocale = FileUtil.locale;
		String mapLabelFolder="Working_map\\";
		String mapLabel = mapLabelFolder+"Working_Map_",curMap = mapLabel+prefix+"\\whatever";
		
		i18nMapBackupDir = localizationDir + "i18nMapsBackup\\";
		i18nMapBackupDir2 = localizationDir + "i18nMapsBackup"+subDir+"\\";
		
        //boolean done = (new File(localizationDir+curMap).exists())?true:prefix.equalsIgnoreCase("en");
		ProjectConf.locale = appLocale;
		if(new File(localizationDir+curMap).exists()&&(prefix.equalsIgnoreCase("en")||!updateMasterFile)){
			LoggerHelper.logTAFDebug("Using current maps - Locale "+prefix);
			return;
		}
		else if(new File(localizationDir+mapLabel+"en").exists()){
			FileUtil.copyFile(objectMapDir+"*.rftmap",i18nMapBackupDir+"*.en"+"_LastWorking_rftmap" ,false);
			FileUtil.copyFile(objectMapDir2+"*.rftmap",i18nMapBackupDir2+"*.en"+"_LastWorking_rftmap",false);
		}
		
//		if(!changeMap){
//			return;
//		}
		
		LoggerHelper.logTAFDebug("Start to update RFT map - "+prefix+" ");
		LoggerHelper.logTAFDebug("Rename RFT map - "+localizationDir+mapLabel+"*"+" to "+curMap);
		FileUtil.renameFiles(localizationDir+mapLabel+"*", curMap,false);
		FileUtil.removeDir(localizationDir+mapLabelFolder);
    	FileUtil.mkDirs(localizationDir+curMap,true);
		LoggerHelper.logTAFDebug("Delete RFT map - "+objectMapDir+"*.rftmap");
		FileUtil.delFile(objectMapDir+"*.rftmap");
		LoggerHelper.logTAFDebug("Delete RFT map - "+objectMapDir2+"*.rftmap");
		FileUtil.delFile(objectMapDir2+"*.rftmap");
		
		//Copy and rename...
		LoggerHelper.logTAFDebug("Copy RFT map from "+i18nMapBackupDir+"*."+prefix+"_rftmap"+
				                    " to "+objectMapDir+"*.rftmap");
		FileUtil.copyFile(i18nMapBackupDir+"*."+prefix+"_rftmap", objectMapDir+"*.rftmap",false);
		
		LoggerHelper.logTAFDebug("Copy RFT map from "+i18nMapBackupDir2+"*."+prefix+"_rftmap"+
                " to "+objectMapDir2+"*.rftmap");
		FileUtil.copyFile(i18nMapBackupDir2+"*."+prefix+"_rftmap", objectMapDir2+"*.rftmap",false);
		try {
			
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoggerHelper.logTAFInfo("Map updated for - Locale "+prefix);
		return;
	}
}
