package lib.acl.helper.sup;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;

import lib.acl.util.DatapoolUtil;
import lib.acl.util.FileUtil;
import lib.acl.util.MemusageTracer;
import lib.acl.util.PropertyUtil;
import lib.acl.util.UnicodeUtil;

//import AX_GatewayPro.conf.beans.ProjectConf;

import ACL_Desktop.conf.beans.ProjectConf;

import com.rational.test.ft.object.interfaces.ITopWindow;
import com.rational.test.ft.script.RationalTestScript;

import conf.beans.AXCoreDBConf;
import conf.beans.ExceptionDBConf;
import conf.beans.FrameworkConf;
import conf.beans.LoggerConf;
import conf.beans.TimerConf;


public class InitializeTerminateHelper extends LoggerHelper {
	protected final String TEST_TYPE_REGRESSION_TEST = "RegressionTest";
	protected final String TEST_TYPE_SMOKE_TEST = "SmokeTest";
		
	protected final String TEST_RESULT_PASS = "Pass";
	protected final String TEST_RESULT_FAIL = "Fail";
	
	protected final String KEYWORD_RUN_TESTBY_DP_FILE = "runTestByDpFile";
	
	protected final String DP_TABLE_FOLDER = "/KeywordTable/";	
	protected final String DP_FILE_EXT = ".csv";
	protected final String DP_COMMENTS_LINE_PREFIX = "#";
	
	protected final String DP_VAR_TEST_CASE = "Test_Case";
	protected final String DP_VAR_TEST_TYPE = "Test_Type";	
	protected final String DP_VAR_KEYWORD = "Keyword";
	protected final String DP_VAR_RUN_TEST = "Run_Test";	
	protected final String DP_RUN_TEST_RUN = "T";
	protected final String DP_RUN_TEST_NOT_RUN = "F";
	
	protected final String TC_START_LINE_PREFIX = "\n======\t";
	protected final String TC_START_LINE_POSTFIX = "\t======";	
	protected final String TC_RESULT_LINE_PREFIX_PASS = " ~~~";
	protected final String TC_RESULT_LINE_PREFIX_FAIL = " ~~!";
	
	protected final String KEYWORD_START_LINE_PREFIX = "\t\n------     ";
	protected final String KEYWORD_START_LINE_POSTFIX = "     ------\n";	
	protected final String KEYWORD_PREFIX_PASS = "\t>>>";
	protected final String KEYWORD_PREFIX_FAIL = "\t>>!";
	
	protected final String TEST_RESULT_HEADER_BATCH = "Test_No.,Test_Name,Keyword_No.,Keyword_Name,Test_Result,Test_Message";
	protected final String TEST_RESULT_HEADER_NON_BATCH = "Keyword_No.,Keyword_Name,Test_Result,Test_Message";
	
	protected Object[] poolArgs;
	protected File poolCsvFile;
	protected String csvFile;
	protected String projPath;
	protected String ProjectConfClassName = "";
	protected String TimerConfClassName = "";
	

	
	public int startFromLine = 2;
	public int endAtLine = Integer.MAX_VALUE;
	private boolean lineRangeDefined = false;
	public int currentLine = 1;
	public static String testCategory = "";
	public static String sysPropPrefix = "AutoQA.";
	
	// This two are  used for unicode support
	protected String xlsName ="";
	protected String poolFile="";
	
	@SuppressWarnings("unchecked")
	protected Class classP,classT;
	int rftLevel= 0;
	static String screenResolution ="1024 x 768 pixels";
	protected static boolean isUnicode = true;
	protected boolean isMainScript = isMainScript();
	

	public Object[] onInitialize(String poolFileOri,String command, String testName) {	
		menuItem = command;
		return onInitialize(poolFileOri,testName);
	}
	public Object[] onInitialize(String poolFileOri, String testName) {	
		
		
		String userTestFile="";	
		String autoRunTestCategory = "";
		if(isMainScript()){
		   LoggerHelper.RFT_jenkinsReportDir = LoggerHelper.getSystemProperty(sysPropPrefix+"reportDir");		
	       userTestFile = getSystemProperty(sysPropPrefix+"testDataFile");		
	      
		   if(userTestFile != null&&!userTestFile.equals("")){
			    startFromLine = 2;
				endAtLine = Integer.MAX_VALUE;
			    poolFileOri = userTestFile;
		   }
		   
		   autoRunTestCategory = getSystemProperty(sysPropPrefix+"testCategory");
	       if(autoRunTestCategory!=null&&autoRunTestCategory!=""){
				testCategory = autoRunTestCategory;
			}
     	
     	}
		
		
		poolFileOri = FileUtil.getAbsDir(poolFileOri,getCurrentProject().getLocation() + "/" );	
		if(!new File(poolFileOri).exists()){

			logTAFError("!!!File not found:'"+poolFileOri);
			return null;
		}
		//## new code for non-unicode/unicde test cases switch, added constructor in 
		//## batch and testcase run super helper
		//## simply delete these code to roll back
		//## BY David Shao
		
		if (isMainScript()){   
			getHostInfo();
			                   // we need only load properties for a test once, - Steven
		    TAFLogger.name = testName; // set the log file /folder name before running 
		    caseName = testName.split("\\.")[testName.split("\\.").length-1];
			loadProperties();
			try {
				//fetches the manually defined value from the respective project configuration file.
				isUnicode = Class.forName(ProjectConfClassName).getField("unicodeTest").get(null).toString().equalsIgnoreCase("True");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		poolLanguageIndex = UnicodeUtil.setPoolLanguageIndex(poolFileOri);

		
		stopScript = false;		
		screenResolution = getScreenResolution();
		
		if (isMainScript()){
			poolFile = UnicodeUtil.XlsToCsv(poolFileOri, FrameworkConf.tempCsvMainFile);
            logTAFDebug("poolFile:'"+poolFile+"'");

     		//loadProperties();
     		
     		rftLevel=LoggerConf.filterLevel;
			if(rftLevel<0)
				rftLevel = -1;
			if(rftLevel>5)
				rftLevel=5;			
     		
     		setCurrentLogFilter(rftLevel);
     		
     		if (isMainScript()){ // don't know why the outer condition doesn't work - Steven
     			logTAFTestResult(printTestLabel("Test Script : " + getScriptName() + " started "),true);

     			logTAFInfo("- AUT: "+projName+(FrameworkConf.buildInfo.equalsIgnoreCase("")? "":" - "+FrameworkConf.buildInfo));
                
     			if(!ProjectConf.appLocale.equals(""))
     			   logTAFInfo("- AUT Locale: "+ProjectConf.appLocale);
     			logTAFInfo("- System Locale: "+TAFLogger.locale);
     			logTAFInfo("- Tester: "+FrameworkConf.testerName);
     			if(isWeb){					
     				logTAFInfo("- Browser Name: "+FrameworkConf.browserName+ ", Version: "+FrameworkConf.browserVersion);
     				logTAFInfo("- Screen Resolution: "+ screenResolution);
     			}		
     			logTAFInfo("- Log Filter Level: "+LoggerConf.filterLevel);
     			logTAFInfo("- Batch Run Log Filter Level: "+LoggerConf.batchRunfilterLevel);
     			if(FrameworkConf.traceMemusage){
     				logTAFInfo("- Track the Memusage over time - enabled");
     				mt = new MemusageTracer();
     				mt.start();
     			}

     			workingDir_Server = processLink(TAFLogger.file+"\\ProjectArchive");
     			try{
     				String user="ACL\\qamail",pass="Password00";
     				FileUtil.mkDirs(workingDir_Server);
     				if(!new File(workingDir_Server).exists()){
     					//Try to connnect
     					FileUtil.mapDrive("", workingDir_Server, user, pass);
     				}
     			}catch(Exception e){
     				//
     			}
     		}

		}else{			
			poolFile = UnicodeUtil.XlsToCsv(poolFileOri, FrameworkConf.tempCsvFile);
//			logTAFDebug("Test Name: "+testName +" poolFile "+ poolFile);
		}
		if(!poolFile.equals(poolFileOri)){
			xlsName = poolFileOri; //Save the xls file name
		}

        currentCSVName = new File(poolFileOri).getName().split("\\.")[0];        
		csvFile = poolFile;	

			poolCsvFile = new File(poolFile);
			poolArgs = DatapoolUtil.setDefaultDataPool(this, poolCsvFile);
		
		//logTAFDebug("poolArgs: '"+Arrays.toString(poolArgs));

		return poolArgs;
	}
    
	public void onTerminate() {
		boolean appClosed = true;
        // if single test, don't logout, you can verify the contents then
		// if batch run, logout and kill the app as next run needs a fresh running app
		
	    
		//if(isMainScript){
		if (isMainScript()){
			//logTAFInfo(" Test Script end time: "+scriptEndTime);
			logTAFTestResult(printTestLabel( "Test Script : "+ getScriptName() + " "+testMainResult+"ed")
					         ,testMainResult=="Pass"?true:false);
			DatabaseHelper.releaseDBResources(); // Disconnect possible DB connections and statements
//			if(TAFLogger.configured) 
//				DatapoolUtil.defaultLogProcess(this);
			mt.stopTracing();

		}else{	
            if(!testCategory.matches("Daily")&&numTestedKeywordInCase!=0&&!ProjectConf.singleInstance){
		       stopApp(); //stop is not good for daily testing .. we need to implement other strategy for this purpose
			   logInfo("\n\tAutomation: " + (TimerConf.waitTimeBetweenTestCases/1000) + " seconds break please ...\n");
				try {
					Thread.sleep(TimerConf.waitTimeBetweenTestCases);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
            }

		}

		//Write to Excel 
		try {
			// Use this line to write test result to original .xls data pool
			//UnicodeUtil.CsvToXls(poolFile, xlsName);
			UnicodeUtil.CsvToXls(FrameworkConf.tempCsvResult, TAFLogger.testResultXLS);
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
		
	}

//	  public boolean onUnhandledException(java.lang.Throwable e) {
//		  errorHandledInLine = false;// in case of failing to handle expected errors in keyword.
//		  logTAFError ("UnhandledException occur: "+e.toString());
//		  sysExceptionCaught = true;
//		  stopApp();
//		  //dismissPopup("Any",false,true);
//		  return true;
//	  }
	  
	protected boolean lineInRange(int currentLine){
		if(!lineRangeDefined){
		     String  userTestFile = getSystemProperty(sysPropPrefix+"testDataFile");		
			   if(userTestFile != null&&!userTestFile.equals("")){
				    startFromLine = 2;
					endAtLine = Integer.MAX_VALUE;
			   }
			 lineRangeDefined = true;
	     	}
		
		if(currentLine>=startFromLine&&currentLine<=endAtLine){
			return true;
		}else{
			return false;
		}
	}
	private String loadProjConf(){
		
		
		String scriptName = getScriptName();
		
		
		if(scriptName.contains(FrameworkConf.application1)){
			projName = FrameworkConf.application1;
			testDescription = FrameworkConf.testDescApp1;
			//resourcePropPrefix = "GWP_";
		}else if(scriptName.contains(FrameworkConf.application2)){
			projName = FrameworkConf.application2;
			testDescription = FrameworkConf.testDescApp2;
			isWeb = true; 
			resourcePropPrefix = "GW_";
		}else if(scriptName.contains(FrameworkConf.application3)){
			projName = FrameworkConf.application3;
			testDescription = FrameworkConf.testDescApp3;
			isWeb = true; 
			//resourcePropPrefix = "GW_ADMIN";
		}else if(scriptName.contains(FrameworkConf.application4)){
			projName = FrameworkConf.application4;
			testDescription = FrameworkConf.testDescApp4;
			isWeb = true;
			resourcePropPrefix = "EM_";
		}else if(scriptName.contains(FrameworkConf.application5)){
			projName = FrameworkConf.application5;
			testDescription = FrameworkConf.testDescApp5;
			//resourcePropPrefix = "AX_";
		}else if(scriptName.contains(FrameworkConf.application6)){
			projName = FrameworkConf.application6;
			testDescription = FrameworkConf.testDescApp6;
			//resourcePropPrefix = "ACL_Soundwave";
		}else if(scriptName.contains(FrameworkConf.application7)){
			projName = FrameworkConf.application7;
			testDescription = FrameworkConf.testDescApp7;
			//resourcePropPrefix = "ACL_Desktop";
		}else{
			projName = FrameworkConf.application1;
			logTAFWarning("Your test script "+scriptName+" has not been configured properly!");
		}
		
		TAFLogger.testName = projName;
		projPath=projName + "/conf/";
		ProjectConfClassName = projName+".conf.beans.ProjectConf";
		TimerConfClassName = projName+".conf.beans.TimerConf";
		
		try {
			classP = getClass().getClassLoader().loadClass(ProjectConfClassName).getClass();
			try {
				projectName = Class.forName(ProjectConfClassName).getField("projectName").get(null).toString();
				imageName = Class.forName(ProjectConfClassName).getField("imageName").get(null).toString();
				pathToTestCaseScripts = Class.forName(ProjectConfClassName).getField("pathToTestCaseScripts").get(null).toString();
				originalPathToTestCaseScripts = pathToTestCaseScripts;
				pathToKeywordScripts = Class.forName(ProjectConfClassName).getField("pathToKeywordScripts").get(null).toString();
				originalPathToKeywordScripts = pathToKeywordScripts;
				localizationDir = Class.forName(ProjectConfClassName).getField("localizationDir").get(null).toString();
				//isDailyTest = Class.forName(ProjectConfClassName).getField("dailyTest").get(null).toString().equalsIgnoreCase("True")?true:false;
				logTAFDebug("CasePath ="+pathToTestCaseScripts);
				logTAFDebug("ProjectName ="+projectName);
				logTAFDebug("imageName ="+imageName);
				logTAFDebug("keywordPath ="+pathToKeywordScripts);
			} catch (Exception e) {
				//e.printStackTrace();
			}
			classT = getClass().getClassLoader().loadClass(TimerConfClassName).getClass();
			
		} catch (ClassNotFoundException e) {
			logTAFException(e);
		}
		return null;
	}
	
//	private String changeFolder(String file){
//		String modifiedPath = file;
//		if(FrameworkConf.unicodeTest){
//			try{
//				modifiedPath = file.replace("SmokeTest","SmokeTest/Unicode_Test").replace("RegressionTest","RegressionTest/Unicode_Test");
//			}catch(Exception e){
//				logTAFWarning(e.toString());
//				modifiedPath = file;
//			}
//		}
//		//logTAFInfo("***** "+modifiedPath+" ****");
//		return modifiedPath;
//	}
	
	private void loadProperties(){	
		Properties projProp;
		Properties frameProp;

		String framePath = "conf/";


		try {	
			
			// ************* loading framework props first as it is shared by all tests ***********
			frameProp = new Properties();
			frameProp.load(new FileInputStream(framePath + "framework.properties"));
			PropertyUtil.setProperties(new FrameworkConf(), frameProp);
		
			// loading framework timer props
			frameProp = new Properties();
			frameProp.load(new FileInputStream(framePath + "timer.properties"));
			PropertyUtil.setProperties(new TimerConf(), frameProp);
			
			// loading AXCore DB props
			frameProp = new Properties();
			frameProp.load(new FileInputStream(framePath + "axCoreDB.properties"));
			PropertyUtil.setProperties(new AXCoreDBConf(), frameProp);
			AXCoreDBConf.setJDBCParameters();
			
			// loading Exception DB props
			frameProp = new Properties();
			frameProp.load(new FileInputStream(framePath + "exceptionDB.properties"));
			PropertyUtil.setProperties(new ExceptionDBConf(), frameProp);
			ExceptionDBConf.setJDBCParameters();
			
			// ************************************  Config this project ***************************
			loadProjConf();
			// loading logging props
			frameProp = new Properties();
			frameProp.load(new FileInputStream(framePath + "logger.properties"));

			projProp = new Properties(frameProp);
			projProp.load(new FileInputStream(projPath + "logger.properties"));

			PropertyUtil.setProperties(new LoggerConf(), projProp);

			// loading project props
			projProp = new Properties();
			projProp.load(new FileInputStream(projPath + "project.properties"));
			//PropertyUtil.setProperties(classP, projProp);
			PropertyUtil.setProperties(Class.forName(ProjectConfClassName).newInstance(), projProp);

			// loading project timer props
			projProp = new Properties();
			projProp.load(new FileInputStream(projPath + "timer.properties"));
			PropertyUtil.setProperties(Class.forName(TimerConfClassName).newInstance(), projProp);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String printTestLabel(String message){		
		return "\n*****\t"+message+"\t*****\n";		
	}
	
	

	
	public String testSummary(String target){
		 String linkToWikiAuto = "[[QA Test Automation|Back To Main - QA Test Automation]]";
		 String wikiTitlePre = "\n===   ", wikiTitleSuf = "    ===\n----\n";
		 String wikiTitleSubPre = "\n====   ", wikiTitleSubSuf = "    ====\n----\n";
		 String caption = "Test Summary",
		               spacer ="",
		               line0="",line1="",line2="",summary="";
			
		 if(target.equalsIgnoreCase(testSuite)){
			
			 if(!testCategory.equals("")){
				 caption +=" ["+testCategory+" Test]";
			 }
			scriptStartTime = TAFLogger.timeOfTest;
			if(scriptEndTime == null)
				scriptEndTime = Calendar.getInstance().getTime();				
			 spacer = " "+spacer;
		 }else{
			 //caption = "Keyword "+caption;
			 spacer = "\t"+spacer;			 
		 }
		 //Start of Test Summary

		 line0="\n\n"+spacer+"***********Begin of Wiki Report***********\n\n";
		 
		 line1=wikiTitlePre+caption+wikiTitleSuf;
		 line2="\n\n"+spacer+"***********End of Wiki Report***********\n";
		 summary = "\n\n"+spacer+"**************   "+colorDiv+caption+_closeTag+"    **************\n";
		 
		 if(isMainScript()){
			 reportSubject = "AUT: "
				 +(projectName.equalsIgnoreCase("ACL_Desktop")?"ACL Analytics":projectName)
			     +"["+TAFLogger.locale+"]"+" - "+FrameworkConf.buildName+
	            "["+FrameworkConf.buildInfo+"]-"+		            
	            (ProjectConf.isUnicodeTest()?"Unicode":"NonUnicode")+
	            "(ACL "+ProjectConf.testType+" Project)";
			 summary = "\n"+line0+linkToWikiAuto+colorDiv+line1;
			 summary = summary + 
			   "\n*\t <b>TEST ENVIRONMENT:</b>"+
			   "\n**\t "+reportSubject+
//		       "\n**\t- AUT: "+projName+" - "+FrameworkConf.buildName+
//		            "["+FrameworkConf.buildInfo+"]-"+		            
//		            (ProjectConf.isUnicodeTest()?"Unicode":"NonUnicode")+
//		            "(ACL "+ProjectConf.testType+" Project)"+
//		       "\n**\t- JVM Locale (Client Machine): "+TAFLogger.locale+
		       "\n**\t- Tester: "+FrameworkConf.testerName+
		       "\n**\t- Name of Test Machine: "+hostName+ " IP Address: "+hostIP+
	           "\n**\t- Operating System (Client): "+System.getProperty("os.name")+ ", "+System.getProperty("os.version")+
			   "\n**\t- Java Version: "+System.getProperty("java.version"); 
			   if(isWeb){	
					summary = summary + 
				       "\n**\t- Browser Name: "+FrameworkConf.browserName+ ", Version: "+FrameworkConf.browserVersion+
				       "\n**\t- Screen Resolution: "+ screenResolution;
				}
			   if(FrameworkConf.serverIP!=""&&FrameworkConf.serverName!="")	{	 
			  		summary = summary + 
					 "\n**\t- Name of Server Machine: "+FrameworkConf.serverName+ " IP Address: "+FrameworkConf.serverIP +
					 "\n**\t- Operating System (Server): " +FrameworkConf.serverOS+ ", "+
						FrameworkConf.serverType;
		  	   }
			   summary = summary +"\n\n*\t <b>RESULTS:</b>";
	            if(ProjectConf.appLocale.matches("(?i)Ko|Pl")){
	            	summary += "\t!!! No Korean|Polish aclse available, some tests skipped and tests may fail due to the failure of server connection!!!";
	            }      
			   summary = summary +"\n**\t Test Script start time: "+scriptStartTime;
		 }
		      
//		       summary = summary+"\n***\t\t Total tested "+target+"(s): "+
//		                   (target.equalsIgnoreCase(testSuite)?numTCs:numKWs)+
//		                   "\n***\t\t Passed "+target+"(s): "+
//		                   (target.equalsIgnoreCase(testSuite)?
//		                		   ((numTCs-(bugNumN+bugNumA))+" ("+((numTCs-(bugNumN+bugNumA))*100/numTCs)+"%)"):(numKWs-numKWsFail+numRBugs))+ 
//		                       (target.equalsIgnoreCase(testSuite)?
//		                    		   (bugNumR==0?"":("\n****\t\t Passed with known issue(s) - "+bugNumR)):"")+
//		                   "\n***\t\t Failed "+target+"(s): "+
//		                   (target.equalsIgnoreCase(testSuite)?
//		                		   ((bugNumN+bugNumA)+" ("+(100-((numTCs-(bugNumN+bugNumA))*100/numTCs))+"%)"):(numKWsFail-numRBugs))+
//		                   //ADDing some info here .... Steven
//		                      (target.equalsIgnoreCase(testSuite)?
//		                           (bugNumN==0?"":("\n****\t\t Possible new issue(s) - "+bugNumN)+" ("+(bugNumN*100/numTCs)+"%)"):"") +
//		                      (target.equalsIgnoreCase(testSuite)?
//		                           (bugNumA==0?"":("\n****\t\t Possible automation issue(s) - "+bugNumA)):"");
	       summary = summary+"\n***\t\t Total tested "+target+"(s): "+
           (target.equalsIgnoreCase(testSuite)?numTCs:numKWs)+
           "\n***\t\t Passed "+target+"(s): "+
           (target.equalsIgnoreCase(testSuite)?
        		   (numTCs-(bugNumN+bugNumA)):(numKWs-numKWsFail+numRBugs))+ 
               (target.equalsIgnoreCase(testSuite)?
            		   (bugNumR==0?"":("\n****\t\t Passed with known issue(s) - "+bugNumR)):"")+
           "\n***\t\t Failed "+target+"(s): "+
           (target.equalsIgnoreCase(testSuite)?
        		   (bugNumN+bugNumA):(numKWsFail-numRBugs))+
           //ADDing some info here .... Steven
              (target.equalsIgnoreCase(testSuite)?
                   (bugNumN==0?"":("\n****\t\t Possible new issue(s) - "+bugNumN)):"") +
              (target.equalsIgnoreCase(testSuite)?
                   (bugNumA==0?"":("\n****\t\t Possible automation issue(s) - "+bugNumA)):"");
		      if(isMainScript()){

		    	  summary = summary + "\n**\t Test Script end time: "+ scriptEndTime   ;
		    	  summary = summary + "\n";
		    	  
		    	  //*********** Links *********************
			    	//Start of Logs for Public Access
		    	  
		    	  testResultTXT_Server = processLink(TAFLogger.testResultTXT);
		    	  testResultXLS_Server = processLink(TAFLogger.testResultXLS);
		    	  testResultHTML_Server = processLink(TAFLogger.testResultHTML);
		    	  memusageCSV_Server = processLink(TAFLogger.memusageCSV);
		    	  screenShots_Server = processLink(TAFLogger.screenShots);
		    	  
		    	  summary = summary +
		    	  "\n*\t <b>LINKS:</b>"+
		    	      
		    	      "\n**\tTest Log: "+ getFileLink(testResultTXT_Server)+
		    	      "\n**\tTest Matrix: "+ getFileLink(testResultXLS_Server)+
		    	      "";
		    	  if(FrameworkConf.traceMemusage){
		    		  summary = summary + "\n**\tMemory Trace: "+ getFileLink(memusageCSV_Server);
		    	  }
		    	  if(RFT_emailReport){
		    		  summary = summary + "\n**\tHtml Report: "+ getFileLink(testResultHTML_Server);
		    	  }
		    	  if(projectArchived){
		    		  summary = summary + "\n**\tProject Archived In: "+getFileLink(workingDir_Server);
		    	  }
		    	  summary = summary +"\n**\tAbout: [[Desktop_Continues_Testing]], [[Desktop_Automation_Guide]]";
		    	  summary = summary + "\n"+_closeTag;
		    	  //***************************************
		        
		    	  // Start of Analysis
		    	  summary = summary+wikiTitleSubPre+colorDiv+"Test Result Analysis"+_closeTag+wikiTitleSubSuf;
		    	  //resultAnalysis = "*\t\tAnalysis goes here...\n**\t\t\t...\n***\t\t\t...";
		    	  resultAnalysis = bugmessage+
		    	  		           "";
//		    	  		           "\n*\t\tRemaining Known Bugs: "+remainingBugs+"" +
//		    	  		           "\n**\t\t\t...\n***\t\t\t..."+
//		    	  		           "\n*\t\tFixed Known Bugs: "+fixedBugs+"" +
//		    	  		           "\n**\t\t\t...\n***\t\t\t...";
		    	  
		    	  summary = summary +"\n"+resultAnalysis+"\n";
		    	  //Start of Error Details
		    	  summary = summary+wikiTitleSubPre+colorDiv+"Test Details"+_closeTag+wikiTitleSubSuf;
		    	  //summary = summary +"<pre>\n";
		    	  
		    	  //summary = summary + (target.equalsIgnoreCase(testSuite)?errorDetails:getErrorDetails(keywordErrors));
			      summary = summary + addColorLabel((target.equalsIgnoreCase(testSuite)?
			    		  testDetails.replaceAll(" SmokeTest\\.| RegressionTest\\.", " .")
			    		  :getErrorDetails(keywordErrors)));
		    	  summary = summary + "\n----\n";
		    	  
		    	 return processFileLink(summary+linkToWikiAuto+line2);
		      }else{
		    	  
		      }
		return summary+"\n\n"+spacer+"***********************************************\n";
		       
		
	}
	public String addColorLabel(String report){
		String newIssue="\\[NEW\\sISSUE.\\](.*)",
		       autoIssue="\\[AUTOMATION\\sISSUE.\\](.*)",
		       knownBug="\\[KNOWN\\sISSUE:(.*)",
		       pass="\\sPASS[E]?[D]?|\\sPass";
		String newIssueC=colorNewIssue+"[NEW ISSUE?]";
		String autoIssueC=colorNewIssue+"[AUTOMATION ISSUE?]";
		String knownBugC=colorRemainBug+"[KNOWN ISSUE:";
		String passC=colorPass+" PASS "+closeTag;
		//String newIssueC=colorNewIssue+"[NEW ISSUE?]"+closeTag;
		report = report.replaceAll(newIssue, newIssueC+"$1"+closeTag);
		report = report.replaceAll(autoIssue, autoIssueC+"$1"+closeTag);
		report = report.replaceAll(knownBug, knownBugC+"$1"+closeTag);
		report = report.replaceAll(pass, passC);
		//report.replaceAll(newIssue, colorNewIssue+newIssue+closeTag);
		return report;
	}
	
	public static String processFileLink(String result){
		if(true)
			return result;
		//In testing.... 
		String uncPath = "[/\\]{2}([a-zA-Z])";
		String localPath = "([A-Za-z]:[\\/][a-zA-Z])";
		String uncPathC = "file://";
		String localPathC = "file:///";
		
		
		result = result.replaceAll(uncPath, uncPathC+"$1");
		result = result.replaceAll(localPath, localPathC+"$1");
		result = result.replaceAll("\\", "/");
		
		return  result;
	}
	public boolean getHostInfo(){
		boolean done = false;
		InetAddress inadd;
		try{
		   inadd = InetAddress.getLocalHost();
		   hostName = inadd.getHostName();
		   hostIP = inadd.getHostAddress();
		   done = true;
		}catch(Exception  e){
			logTAFWarning(e.toString());
		}
		return done;
	}
	public static String getScreenResolution(){
		
		Dimension dim =	Toolkit.getDefaultToolkit().getScreenSize();
		
		return (int)dim.getWidth() + " x "+(int)dim.getHeight()+" pixels";
	}
	public static String getFileLink(String oriLink){
		
	    return getFileLink("",oriLink);
	}	
	public static String getFileLink(String label,String oriLink){
		String outPutLink;		
	    outPutLink = oriLink;//processLink(oriLink);
	    if(label.equals(""))
	    	label = FileUtil.getFullName(outPutLink);
	    outPutLink = "<a href=\"file:///"+outPutLink+"\">"+label+"</a>";
	    return outPutLink;
	}
	public static String processLink(String oriLink){
		String outPutLink;		
		outPutLink = FrameworkConf.logDirForPublic+oriLink.replaceAll("Zipped_ACLQA_Automation_logs/", "");
	    outPutLink = outPutLink.replace('/', '\\');
	  
	    return outPutLink;
	}
	
	public static String getErrorDetails(String errorDetails){
		String errorPrefix = errorDetails.trim().equalsIgnoreCase("")?"":"\n\n*\t[["+currentCSVName+"|Test Case."+currentCSVName+"]]";
		errorDetails = errorPrefix+errorDetails;		
		return errorDetails;
	}
	
	
	// *** By Henry -- Need refine - Steven
	protected String appMemoryUsage() {
		if (app != null && app.exists()) {
			return appMemoryUsage(String.valueOf(app.getProcessId()));
        }
		return null;
	}
	
	protected String appMemoryUsage(String reportBy) {
		if (reportBy == null || "".equalsIgnoreCase(reportBy)) {
			return null;
		}
		
		String memUsage = null;
		String tmpMemUsageMsgFile = "__rftTmpMemoryUsage.txt";
		String tasklistStr = "tasklist /fo list /fi \"";		
		tasklistStr += (reportBy.matches("^\\d+$"))? "pid eq " + reportBy : "imagename eq " + reportBy + ".exe";
		tasklistStr += "\" >" + tmpMemUsageMsgFile;
		
		FileUtil.exeComm(tasklistStr);
		
		memUsage = FileUtil.readFile(tmpMemUsageMsgFile);
		int index = memUsage.indexOf("Mem Usage:");
		if (index != -1) {
			memUsage = memUsage.substring(index + "Mem Usage:".length()).trim();
		} else {
			memUsage = null;
		}
		
		return memUsage;
	}
	
//	String maxMemUsage = "150000"; // 150000 = 150M
//	String tmpMessageFile = "__rftTaskkillMsg.txt";
//	FileUtil.exeComm("taskkill /F /T /IM " + ProjectConf.applicationName + ".exe /FI \"memusage gt " + maxMemUsage + "\"" + " >" + tmpMessageFile);
//	
//	if (FileUtil.readFile(tmpMessageFile).startsWith("SUCCESS:")) {
//		logTAFWarning("Current '" + ProjectConf.applicationName + "' instance exceed maximum automation memory usage threshold [" + maxMemUsage + "], is forcely killed!");
//	}
}
