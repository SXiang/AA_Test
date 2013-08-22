package com.acl.qa.taf.helper.superhelper;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.Properties;

import com.acl.qa.taf.util.DatapoolUtil;
import com.acl.qa.taf.util.FileUtil;
import com.acl.qa.taf.util.FormatHtmlReport;
import com.acl.qa.taf.util.MemusageTracer;
import com.acl.qa.taf.util.PropertyUtil;
import com.acl.qa.taf.util.UnicodeUtil;

public class InitializeTerminateHelper extends ObjectHelper {
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
	
	//protected Object[] poolArgs;
	
	protected String projPath;	

	
	public int startFromLine = 2;
	public int endAtLine = Integer.MAX_VALUE;
	private boolean lineRangeDefined = false;
	public int currentLine = 1;
	public static String testCategory = "";
	public boolean testCaseExclusive = false;
	public static String sysPropPrefix = "AutoQA.";
	
	
	// This two are  used for unicode support
	protected String xlsName ="";
	protected String poolFile="";
	
	@SuppressWarnings("unchecked")
	
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
		String tcExclusive = "";
		setScriptName(testName);
		
		if(isMainScript()){
		   LoggerHelper.TAF_jenkinsReportDir = LoggerHelper.getSystemProperty(sysPropPrefix+"reportDir");		
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
	       tcExclusive = getSystemProperty(sysPropPrefix+"testCaseExclusive");
	       if(tcExclusive!=null&&tcExclusive!=""){
	    	   if(tcExclusive.equalsIgnoreCase("True"))
	    	      testCaseExclusive = true;
	    	   else
	    		  testCaseExclusive = false; 
			}     	
     	}
		
		poolFileOri = FileUtil.getAbsDir(poolFileOri);	

		if(!new File(poolFileOri).exists()){

			logTAFError("!!!File not found:'"+poolFileOri);
			return null;
		}
		
		if (isMainScript()){   
			getHostInfo();
			                   // we need only load properties for a test once, - Steven
		    TAFLogger.name = testName; // set the log file /folder name before running 
		    caseName = testName.split("\\.")[testName.split("\\.").length-1];
			loadProperties();
			try {
				//fetches the manually defined value from the respective project configuration file.
				isUnicode = projectConf.isUnicodeTest();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		poolLanguageIndex = UnicodeUtil.setPoolLanguageIndex(poolFileOri);

		
		stopScript = false;		
		screenResolution = getScreenResolution();
		poolFile = poolFileOri;
		if (isMainScript()){
			// *** Read to an csv - for the use of RFT
			//poolFile = UnicodeUtil.XlsToCsv(poolFileOri, projectConf.tempCsvMainFile);
			
            logTAFDebug("poolFile:'"+poolFile+"'");

     		
     		rftLevel=loggerConf.filterLevel;
			if(rftLevel<0)
				rftLevel = -1;
			if(rftLevel>5)
				rftLevel=5;			
     		
     		setCurrentLogFilter(rftLevel);
     		
     		if (isMainScript()){ // don't know why the outer condition doesn't work - Steven
     			logTAFTestResult(printTestLabel("Test Script : " + getScriptName() + " started "),true);

     			logTAFInfo("- AUT: "+projName+(projectConf.buildInfo.equalsIgnoreCase("")? "":" - "+projectConf.buildInfo));
                
     			if(!projectConf.appLocale.equals(""))
     			   logTAFInfo("- AUT Locale: "+projectConf.appLocale);
     			else
     			   logTAFInfo("- System Locale: "+TAFLogger.locale);
     			logTAFInfo("- Tester: "+projectConf.testerName);
//     			if(isWeb){					
//     				logTAFInfo("- Browser Name: "+projectConf.browserName+ ", Version: "+projectConf.browserVersion);
//     				logTAFInfo("- Screen Resolution: "+ screenResolution);
//     			}		
     			logTAFInfo("- Log Filter Level: "+loggerConf.filterLevel);
     			logTAFInfo("- Batch Run Log Filter Level: "+loggerConf.batchRunfilterLevel);
     			if(projectConf.traceMemusage){
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

		}
		if(!poolFile.equals(poolFileOri)){ 
			//**** if file converted, address changed - not using with selenium
			xlsName = poolFileOri; //Save the xls file name
		}
        currentPoolName = new File(poolFile).getName().split("\\.")[0];    
        
        return new DatapoolUtil().setDefaultDataPool(this, poolFile);
	}
    public void onTerminate(){
    	onTerminate("");
    }
	public String onTerminate(String Output_Report) {
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
			if(mt!=null)
			   mt.stopTracing();
			logInfo(FormatHtmlReport.addReportFooter("Test Log"));
			

	        if(batchRun){
	            if(Output_Report!=""){
	                Output_Report = createHtmlReport(Output_Report);	                
	              }
	    		sendEmail(Output_Report);
	    	   if(loggerConf.isOpenHtmlReport()){
	        	 FileUtil.exeComm("start,chrome,"+TAFLogger.testResultHTML,false);
	    	   }
	        }
			if(loggerConf.isOpenLogFile()){
	        	//FileUtil.exeComm("start,notepad++,"+TAFLogger.testResultTXT,false);
	        	FileUtil.exeComm("start,chrome,"+TAFLogger.testResultTXT,false);
	        }
            System.exit(0);
		}else{
            

            if(!testCategory.matches("Daily")&&numTestedKeywordInCase!=0){
		       stopApp(); 
			   logTAFInfo("\n\tAutomation: " + (timerConf.waitTimeBetweenTestCases) + " seconds break please ...\n");
				try {
					Thread.sleep(timerConf.waitTimeBetweenTestCases*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
            }

		}
		return Output_Report;
	}

	  
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
	
	private void loadProperties(){	
		Properties projProp;
		String scriptName = getScriptName();
		projName = scriptName.split("\\.")[0];
		testDescription = "Test description should be defined in your project conf class";		
		TAFLogger.testName = projName;
		projPath="/conf/";
		
		try {	
			// ************************************  Config this project ***************************
			// loading logging props
			projProp = new Properties();
			projProp.load(this.getClass().getResourceAsStream(projPath+"logger.properties"));
			PropertyUtil.setProperties(loggerConf, projProp);

			// loading project props
			projProp = new Properties();
			projProp.load(this.getClass().getResourceAsStream(projPath + "project.properties"));
			PropertyUtil.setProperties(projectConf, projProp);

			// loading project timer props
			projProp = new Properties();
			projProp.load(this.getClass().getResourceAsStream(projPath + "timer.properties"));			
			PropertyUtil.setProperties(timerConf, projProp);

			// loading AXCore DB props
			projProp = new Properties();
			projProp.load(this.getClass().getResourceAsStream(projPath + "dbConf.properties"));
			PropertyUtil.setProperties(dbConf, projProp);
			dbConf.setJDBCParameters();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		projectName = projectConf.projectName;
		imageName = projectConf.imageName;
		pathToTestCaseScripts = projName+".testdriver.";
		originalPathToTestCaseScripts = pathToTestCaseScripts;
		pathToKeywordScripts = projName+".keyword";
		originalPathToKeywordScripts = pathToKeywordScripts;
		localizationDir = projectConf.localizationDir;
		logTAFDebug("CasePath ="+pathToTestCaseScripts);
		logTAFDebug("ProjectName ="+projectName);
		logTAFDebug("imageName ="+imageName);
		logTAFDebug("keywordPath ="+pathToKeywordScripts);
	}

	private String printTestLabel(String message){		
		return "\n*****\t"+message+"\t*****\n";		
	}
	
	

	
	public String testSummary(String target){
		 String linkToWikiAuto = "[[Analytics+Continuous+Delivery|Analytics+Continuous+Delivery]]";
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
		 
	
		 boolean unicodeTest = false;
		 
		 if(isMainScript()){
			 if(!projectConf.appLocale.equalsIgnoreCase("En")){
				 try{
					 System.out.println("\n\t ************** L10N Cache Hits & Misses ***************");
					 logTAFInfo("Cache_l18n contains '"+LoggerHelper.cache_l10n.size()+"' items\n\t\t"+LoggerHelper.cache_l10n.getCacheInfo());
					 logTAFInfo("Cache_en contains '"+LoggerHelper.cache_en.size()+"' items\n\t\t"+LoggerHelper.cache_en.getCacheInfo());
					 System.out.println("\t *******************************************************\n");
				 }catch(Exception e){
					 //
				 }
			 }
			 if(numTCs > -1){// was > 2 to exclude AN batch test

			   reportSubject = "AUT: "
				 //+(projectName.equalsIgnoreCase("ACL_Desktop")?"ACL Analytics":projectName)
			     +"["+TAFLogger.locale+"]"+" - "+projectConf.testProject+
	            "["+projectConf.buildInfo+"]-"+		            
	            //(unicodeTest?"Unicode":"NonUnicode")+
	            //"(ACL "+projectConf.testType+" Project)"+
	            "";
			 }
			 summary = "\n"+line0+linkToWikiAuto+colorDiv+line1;
			 summary = summary + 
			   "\n*\t <b>TEST ENVIRONMENT:</b>"+
			   (reportSubject.equals("")?"":"\n**\t "+reportSubject)+
//		       "\n**\t- AUT: "+projName+" - "+projectConf.testProject+
//		            "["+projectConf.buildInfo+"]-"+		            
//		            (projectConf.isUnicodeTest()?"Unicode":"NonUnicode")+
//		            "(ACL "+projectConf.testType+" Project)"+
		       //"\n**\t- JVM Locale (Client Machine): "+TAFLogger.locale+
		       "\n**\t- Tester: "+projectConf.testerName+
		       "\n**\t- Name of Test Machine: "+hostName+ " IP Address: "+hostIP+
	           "\n**\t- Operating System (Client): "+System.getProperty("os.name")+ ", "+System.getProperty("os.version")+
			   "\n**\t- Java Version: "+System.getProperty("java.version"); 
//			   if(isWeb){	
//					summary = summary + 
//				       "\n**\t- Browser Name: "+projectConf.browserName+ ", Version: "+projectConf.browserVersion+
//				       "\n**\t- Screen Resolution: "+ screenResolution;
//				}
//			   if(projectConf.serverIP!=""&&projectConf.serverName!="")	{	 
//			  		summary = summary + 
//					 "\n**\t- Name of Server Machine: "+projectConf.serverName+ " IP Address: "+projectConf.serverIP +
//					 "\n**\t- Operating System (Server): " +projectConf.serverOS+ ", "+
//						projectConf.serverType;
//		  	   }
			   
			   
			   testResultTXT_Server = processLink(TAFLogger.testResultTXT);
			   testResultXLS_Server = processLink(TAFLogger.testResultXLS);
			   testResultHTML_Server = processLink(TAFLogger.testResultHTML);
			   memusageCSV_Server = processLink(TAFLogger.memusageCSV);
			   screenShots_Server = processLink(TAFLogger.screenShots);

			   if(numTCs > -1||!target.equalsIgnoreCase(testSuite)){ // 0){// was > 2 to exclude AN batch test,someone does not want to see this part in the report, so exclude it -- Steven
				   summary = summary +"\n\n*\t <b>RESULTS:</b>";
//				   if(projectConf.appLocale.matches("(?i)Ko|Pl")){
//					   summary += "\t!!! No Korean|Polish aclse available, some tests skipped and tests may fail due to the failure of server connection!!!";
//				   }      
				   summary = summary +"\n**\t Test Script start time: "+scriptStartTime;

				   //String targetT = "Keyword";
				   String targetT = "TestCase";
				   summary = summary+"\n***\t\t Total tested "+target+"(s): ";
				   if(numTested==0&&target.equalsIgnoreCase(testSuite)){
					   summary = summary+(target.equalsIgnoreCase(testSuite)?numTCs:numKWs);
					   logTAFDebug("Automaiton issue: number of tested "+targetT+"s = "+numTested+" is not correctly counted?");
				   }else{
					   summary = summary+(target.equalsIgnoreCase(testSuite)?(numTCs+"["+numTested+targetT+"s]"):numKWs)+

				      "\n***\t\t Passed "+targetT+"(s): "+
				      (target.equalsIgnoreCase(testSuite)?
						   (numTested-(bugNumN+bugNumA)):(numKWs-numKWsFail+numRBugs))+ 
						   (target.equalsIgnoreCase(testSuite)?
								   (bugNumR==0?"":("\n****\t\t Passed with known issue(s) - "+bugNumR)):"")+
								   "\n***\t\t Failed "+targetT+"(s): "+
								   (target.equalsIgnoreCase(testSuite)?
										   (bugNumN+bugNumA):(numKWsFail-numRBugs))+
										   //ADDing some info here .... Steven
										   (target.equalsIgnoreCase(testSuite)?
												   (bugNumN==0?"":("\n****\t\t Possible new issue(s) - "+bugNumN)):"") +
												   (target.equalsIgnoreCase(testSuite)?
														   (bugNumA==0?"":("\n****\t\t Possible automation issue(s) - "+bugNumA)):"")+
					  "";
				   }
				   
				   if(isMainScript()){

					   summary = summary + "\n**\t Test Script end time: "+ scriptEndTime   ;
					   summary = summary + "\n";

					   //*********** Links *********************
					   //^^^^^^^^^^^^ Start of Logs for Public Access ^^^^^^^^^^^^

					   summary = summary +
					   "\n*\t <b>LINKS:</b>"+

					   "\n**\tTest Log: "+ getFileLink(testResultTXT_Server)+
					  // "\n**\tTest Matrix: "+ getFileLink(testResultXLS_Server)+
					   "";
					   if(projectConf.traceMemusage){
						   summary = summary + "\n**\tMemory Trace: "+ getFileLink(memusageCSV_Server);
					   }
					   if(batchRun){//TAF_emailReport){
						   summary = summary + "\n**\tTest Report: "+ getFileLink(testResultHTML_Server);
					   }
					   if(projectArchived){
						   summary = summary + "\n**\tProject Archived In: "+getFileLink(workingDir_Server);
					   }
					   
					   //summary = summary +"\n**\tAbout: [[Desktop_Continues_Testing]], [[Desktop_Automation_Guide]]";
					   summary = summary + "\n"+_closeTag;
					   //***************************************
				   }
			   }
		    	  //^^^^^^^^^^^^ Start of Analysis ^^^^^^^^^^^^
			   if(numTCs > -1||!target.equalsIgnoreCase(testSuite)){ // Failed tests only, categorised list
				   summary = summary+wikiTitleSubPre+colorDiv+"Test Result Analysis"+_closeTag+wikiTitleSubSuf;
				   //resultAnalysis = "*\t\tAnalysis goes here...\n**\t\t\t...\n***\t\t\t...";
				   resultAnalysis = bugmessage+
				   "";
				   //		    	  		           "\n*\t\tRemaining Known Bugs: "+remainingBugs+"" +
				   //		    	  		           "\n**\t\t\t...\n***\t\t\t..."+
				   //		    	  		           "\n*\t\tFixed Known Bugs: "+fixedBugs+"" +
				   //		    	  		           "\n**\t\t\t...\n***\t\t\t...";
				  
				   summary = summary +"\n"+resultAnalysis+"\n";
				   
				   String recentbugList = FileUtil.getFileContents(loggerConf.logDirForPublic+"RecentBugs\\recentBugList.html");
				   String anotherBugList = FileUtil.getFileContents(loggerConf.logDirForPublic+"RecentBugs\\recentBugList_1.html");

				   if(numTCs > -1||!target.equalsIgnoreCase(testSuite)){						   
				         if(bugNumN+bugNumA>0&&recentbugList!=null&&recentbugList.length()>100){
				        	 summary = summary+wikiTitleSubPre+colorDiv+"Recent Bugs"+_closeTag+wikiTitleSubSuf;
				        	 summary = summary + "\n"+recentbugList.replaceAll("\r\n","")+"\n";
				         }
				   }else{
				         if(bugNumN+bugNumA>0&&anotherBugList!=null&&anotherBugList.length()>100){
				        	 summary = summary+wikiTitleSubPre+colorDiv+"Recent Bugs"+_closeTag+wikiTitleSubSuf;
				        	 summary = summary + "\n"+anotherBugList.replaceAll("\r\n","")+"\n";
				         }				         
				   }
			   }
		    	  //^^^^^^^^^^^^ Start of Error Details ^^^^^^^^^^^^
		    	  if(numTCs > -1||!target.equalsIgnoreCase(testSuite)){ // Contains passed tests, was > 2 to exclude AN batch test 
		    		  summary = summary+wikiTitleSubPre+colorDiv+"Test Details"+_closeTag+wikiTitleSubSuf;
		    		  //summary = summary +"<pre>\n";

		    		  //summary = summary + (target.equalsIgnoreCase(testSuite)?errorDetails:getErrorDetails(keywordErrors));
		    		  summary = summary + addColorLabel((target.equalsIgnoreCase(testSuite)?
		    				  testDetails.replaceAll(" SmokeTest\\.| RegressionTest\\.", " .")
		    				  :getErrorDetails(keywordErrors)));
		    	  }
		    	  //^^^^^^^^^^^^ Finished Error Details ^^^^^^^^^^^^
		    	  
		    	  
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
	    outPutLink = oriLink;
	    //processLink(oriLink);
	    if(label.equals(""))
	    	label = FileUtil.getFullName(outPutLink);
	    
	    outPutLink = FormatHtmlReport.linkOpenTag+" href=\"file:///"+outPutLink+"\">"+label+"</a>";
	    return outPutLink;
	}
	public static String processLink(String oriLink){
		String outPutLink;		
		outPutLink = oriLink.replaceFirst(FileUtil.userWorkingDir+"/",loggerConf.logDirForPublic);
	    outPutLink = outPutLink.replace('/', '\\');
	  
	    return outPutLink;
	}
	
	public static String getErrorDetails(String errorDetails){
		String errorPrefix = errorDetails.trim().equalsIgnoreCase("")?"":"\n\n*\t[["+currentPoolName+"|Test Case."+currentPoolName+"]]";
		errorDetails = errorPrefix+errorDetails;		
		return errorDetails;
	}
	
	
	// *** Need to get pid from system somehow -
	protected String appMemoryUsage() {
		String pid = "2131234"; 
		
		if (pid != null ) {		
			return appMemoryUsage(pid);
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
//	FileUtil.exeComm("taskkill /F /T /IM " + projectConf.applicationName + ".exe /FI \"memusage gt " + maxMemUsage + "\"" + " >" + tmpMessageFile);
//	
//	if (FileUtil.readFile(tmpMessageFile).startsWith("SUCCESS:")) {
//		logTAFWarning("Current '" + projectConf.applicationName + "' instance exceed maximum automation memory usage threshold [" + maxMemUsage + "], is forcely killed!");
//	}
	
	public String createHtmlReport(String Output_Report){
		String emailSubject=System.getProperty(sysPropPrefix+"emailSubject");
		String emailTitle=System.getProperty(sysPropPrefix+"emailTitle");
		
		if(emailSubject==null||emailSubject.equals("")){
			emailSubject=reportSubject;
		}
		if(emailTitle==null||emailTitle.equals("")){
			emailTitle="Automation Test Report";
		}	
		
		Output_Report = FormatHtmlReport.getHttpReportFromWiki(Output_Report,FileUtil.getAbsDir(TAFLogger.testResultHTML),emailTitle,emailSubject);
		
        return Output_Report;
	}
	public void sendEmail(String Output_Report){
		String exeDir = projectConf.toolDir;
		String exeFile = "CDOMessage.exe";
		String subject = "Automation Test Report - "+projectConf.projectName;//"Files";
		
		String smtpServer = "xchg-cas-array.acl.com";
		String fromAddress = "QAMail@ACL.COM";
		String fromName = "QAMail";
		String toAddress = projectConf.toAddress;//ProjectConf.ebasecamp;
		String body = "Output_Report";
		String attachFiles = "";//testResultHTML_Server;
		String ccAddress = projectConf.ccAddress;
		String bccAddress = projectConf.bccAddress;
		String importance = "Normal";
		String userName = "ACL\\QAMail";
		String password = "Password00";
		String ipPort = "25";
		String ssl = "1";
		
		String s = " ";
		String d = ",";
	
		String eCommands[] = null;
		String emailPattern = "(?i)^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
		
		copyTestResults("QAServer");
		
		if(TAF_jenkinsReport){
			copyTestResults("Jenkins");	
		}else if(TAF_emailReport){ // If runs from Jenkins, don't send email from RFT.				
			String emailCmd=System.getProperty(sysPropPrefix+"emailCmd");
			if(emailCmd==null){
				if(toAddress.matches(emailPattern)){
					try{
					exeDir = exeDir.replaceAll("\\/", "\\\\");
					emailCmd = exeDir+s+exeFile+s+subject+d+smtpServer+d+fromName+d+fromAddress+d+toAddress+d+
					           body+d+attachFiles+d+ccAddress+d+bccAddress+d+importance+d+userName+d+password+d+
					           ipPort+d+ssl;
					}catch(Exception e){
						logTAFException(e);
					}
				}else{
				     logTAFWarning("Email Report option is only available with continues testing!");
				     return;
				}
			}else{
				eCommands = emailCmd.split(",");
				fromAddress = eCommands[2];
				toAddress = eCommands[4];
				ccAddress = eCommands[7];
				bccAddress = eCommands[8];
			}
			
			if(bugNumA>3){ // Return it to tester due to automation problems... Steven.
				if(toAddress.matches(emailPattern))
					emailCmd.replace(toAddress, fromAddress);
				if(ccAddress.matches(emailPattern))
					emailCmd.replace(ccAddress, "");
				if(bccAddress.matches(emailPattern))
					emailCmd.replace(bccAddress, "");
			}
			// As in the 'doTest.bat', "OOOOO...OOOOO" is used to flag the email command Steven
			emailCmd = "START \"Send Email report...\" /D"+emailCmd.replaceAll("OOOOO", "");
			logTAFInfo("emailCmd = '"+emailCmd+"'");
			//FileUtil.exeComm(false,emailCmd.replace("Output_Report",Output_Report.replaceFirst("[\\/\\\\]$", "") ));
			FileUtil.exeComm(false,emailCmd.replace("Output_Report",Output_Report));
			sleep(0);
		}
	}
	
	public void copyTestResults(){
		copyTestResults("QAServer");
	}
	public void copyTestResults(String to){	
		if(to.equalsIgnoreCase("QAServer")){
			copyToQAServer();
		}else if(to.equalsIgnoreCase("Jenkins")){
			copyToJenkins(true);
		}
		
	}
	public void copyToJenkins(){
	   copyToJenkins(false);
	}
	public void copyToJenkins(boolean isfinal){
		String reportDir = TAF_jenkinsReportDir;

		if(reportDir==null||reportDir==""){
			logTAFWarning("Jenkins_home not found !!!");
			return;
			//reportDir = "D:\\ACL\\JENKINS_HOME\\jobs\\TestReport";
		}
		FileUtil.removeDir(reportDir+"\\FinishedTest\\");
		if(isfinal){
			FileUtil.mkDirs(reportDir+"\\FinishedTest\\file");
			if(testInterrupted){
				FileUtil.mkDirs(reportDir+"\\Interrupted\\file");
			}
		}else{
			//FileUtil.removeDir(reportDir+"\\FinishedTest\\");
		}
		
		logTAFDebug("Copy test report to jenkins "+TAFLogger.testResultTXT+"\\..\\");
		FileUtil.mkDirs(reportDir+"\\screenShots\\");		
		FileUtil.copyDir(TAFLogger.screenShots, reportDir+"\\screenShots\\");	
		
		FileUtil.copyFile(TAFLogger.testResultTXT, reportDir+"\\test_details.log");
		FileUtil.copyFile(TAFLogger.testResultXLS, reportDir+"\\test_matrix.xls");
		FileUtil.copyFile(TAFLogger.testResultHTML, reportDir+"\\test_summary.html");
		FileUtil.copyFile(TAFLogger.memusageCSV, reportDir+"\\test_memusage.csv");
	}
	public void copyToQAServer(){
		logTAFDebug("Copy test report to QAServer from "+TAFLogger.testResultTXT+"/../");
		FileUtil.mkDirs(screenShots_Server+"\\");		
		FileUtil.copyDir(TAFLogger.screenShots, screenShots_Server+"\\");	
		
		FileUtil.mkDirs(testResultTXT_Server);
//		FileUtil.copyDir(TAFLogger.testResultTXT, new File(testResultTXT_Server).getParent());
//		FileUtil.copyDir(TAFLogger.testResultXLS, new File(testResultXLS_Server).getParent());
//		FileUtil.copyDir(TAFLogger.testResultHTML, new File(testResultHTML_Server).getParent());
//		FileUtil.copyDir(TAFLogger.memusageCSV, new File(memusageCSV_Server).getParent());

		FileUtil.copyFile(TAFLogger.testResultTXT, testResultTXT_Server);
		FileUtil.copyFile(TAFLogger.testResultXLS, testResultXLS_Server);
		FileUtil.copyFile(TAFLogger.testResultHTML, testResultHTML_Server);
		FileUtil.copyFile(TAFLogger.memusageCSV, memusageCSV_Server);
	}
}
