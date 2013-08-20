package com.acl.qa.taf.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.acl.qa.taf.helper.superhelper.InitializeTerminateHelper;
import com.acl.qa.taf.helper.superhelper.LoggerHelper;
import com.acl.qa.taf.helper.superhelper.TAFLogger;
import com.acl.qa.taf.util.FileUtil;
import com.acl.qa.taf.util.FormatHtmlReport;


public class TestSuiteSuperHelper extends InitializeTerminateHelper{


	private Object[] args;
	private String testCaseLine1="\n======\t";
	private String testCaseLine2 ="\t======";
	private String tPrefix = "===>";

	public TestSuiteSuperHelper(){
		batchRun = true;
	}

	public void exeBatchRun(Object[] uargs){
		
		
		if(uargs==null||uargs.length<3){
			logTAFWarning("Failed to load your batch run test data?");
			return;
		}else{
			datapool = (HSSFSheet) uargs[0];
			dph = (ArrayList<String>) uargs[1];
			dpi = (Iterator) uargs[2];
			suiteObj = this;
		}
		
		String username= "";
		String password= "";
		String testCase= "";
		//String subpathToCase="";
		//String testType = "";
		String buildName = "";
		String runTest = "";
		                 // **** This 3 have been moved to LoggerHelper ... Steven.
                         //		String bugremaining="";
                         //		String bugfixed="",bugnew="",bugauto="";
                         //		int bugNumF=0, bugNumN=0,bugNumA=0,bugNumR=0;
		String temp[];
		batchRun = true;
		loggerConf.filterLevel = loggerConf.batchRunfilterLevel; 	
		
		//dpReset();
		while (dpi.hasNext()&&!stopTest)
		{
			   dpw =  (HSSFRow) dpi.next();
			   
			    currentLine++;
			    numTestedKeywordInCase =0;
			    buildName = getDpString("Test_Project");
			    testCase = getDpString("TestSuite_Driver").replaceAll("[\\\\/]", ".");
			        
			    runTest = getDpString("Run_Test");
			    
				if((runTest.matches("T|t")||runTest.equals(""))&&
//					(buildName.equalsIgnoreCase("All")||
//					 buildName.equalsIgnoreCase("")||
//					 buildName.contains(FrameworkConf.buildName.trim()))&&
					(isValidBuild(buildName))&&
						!testCase.startsWith("#") && 
						!"".equalsIgnoreCase(testCase)&&
					 lineInRange(currentLine)
				    ){
					//username = getDpString("Username");
					//password = getDpString("Password");
					
					//testType = getDpString("Test_Type");

					testCaseResult = "None";
					failMessage = "";
					caseName = testCase;//.replaceAll(" SmokeTest\\.| RegressionTest\\.", " .");
					
					args = new Object[] {testCase==null ? "" : testCase.trim(), 
							             username==null ? "" : username.trim(),
							             password==null ? "" : password.trim()};
					numTCs++;
									
					
					//logTAFTestResult(testCaseLine1 + " "+numTCs+". "+testSuite+": " + testCase +"(line "+ currentLine +" in batch run data pool)"+ " [" + testType + "]" + testCaseLine2,true);
					logTAFTestResult(testCaseLine1 + " "+numTCs+". "+testSuite+": " + 
							testCase +"(line "+ currentLine +" in batch run data pool)"+ 
							testCaseLine2,true);
					if(numTCs == 1)
						scriptStartTime = TAFLogger.timeOfTest;
					else
						scriptStartTime =Calendar.getInstance().getTime().toString();
						
				    logTAFInfo(" "+testSuite+" start time: "+scriptStartTime);
					
					testCaseLine =numTCs+","+testCase;		
					try{
						
						if (testCase.toLowerCase().endsWith(DP_FILE_EXT)) {// Run for dp file directly, may have some problem - Steven
                            logTAFWarning("You'd better change your call to a keyword instead of a data pool file '"+testCase+"'");
							callScript(pathToTestCaseScripts + KEYWORD_RUN_TESTBY_DP_FILE, 
									//new Object[] {projectName + DP_TABLE_FOLDER + testType + "/" + testCase});
									new Object[] {projectName + DP_TABLE_FOLDER + testCase});
						} else {
							//callScript(pathToTestCaseScripts + testType +subpathToCase+ "." + testCase, args);
							callScript(testCase, args);
						}
											  
					  LoggerHelper.updateTestInfo("", true);
					}
					catch(Exception e){					
						logTAFException(e);
					}		

    				
					
					if(testCaseResult=="Pass"||keywordErrors.trim().equals("")){
						tPrefix = "===>";
						if(numTestedKeywordInCase!=0){
						testDetails = testDetails+
						"\n\n*\t[["+currentPoolName+"|"+testSuite+" "+numTCs+". "+ caseName+"]] PASSED";
						}else{
							testDetails = testDetails+
							"\n\n*\t[["+currentPoolName+"|"+testSuite+" "+numTCs+". "+ caseName+"]] DISABLED";	
						}
					}else{
						tPrefix = "==!>";
						errorDetails = errorDetails+
			               "\n\n*\t[["+currentPoolName+"|"+testSuite+" "+numTCs+". "+ caseName+"]]"+keywordErrors;
						testDetails = testDetails+
						"\n\n*\t[["+currentPoolName+"|"+testSuite+" "+numTCs+". "+ caseName+"]]"+keywordErrors;
						keywordErrors = "";
					}
					logTAFInfo(" "+testSuite+" end time: "+scriptEndTime);
					//logTAFTestResult(tPrefix+" "+testSuite+": " + testCase+ " [" + testType + "] "+
					logTAFTestResult(tPrefix+" "+testSuite+": " + testCase+
							(testCaseResult=="Pass"? 
									testCaseResult+"ed ":testCaseResult+"ed: "+
							failMessage),
							testCaseResult=="Pass"?true:false );
					
					// temp test summary after each "+testSuite+"
					if(projectConf.tempTestSummary){
					   FileUtil.writeFileContents(TAFLogger.testResultTempTXT, 
							   "\t*** Temp Summary From line "+startFromLine+" To line "+currentLine+" ***"+
							   testSummary(testSuite));
					}
					
//					if(numTestedKeywordInCase==0){
//						numTCs--;
//					}
				}
				
				String[] cases = testCase.split("\\.");
				String testC = cases[cases.length-1];
				String bugPrefix = "\n**   [["+testC+"]] ";
				if(!fixedBugs.equals("")||!remainingBugs.equals("")
						||!newBugs.equals("")||!automationBugs.equals("")){
					bugremaining += remainingBugs.equals("")?"":bugPrefix+remainingBugs;
					bugfixed += fixedBugs.equals("")?"":bugPrefix+ fixedBugs;
					bugnew +=newBugs.equals("")?"":bugPrefix+ newBugs;
					bugauto +=automationBugs.equals("")?"":bugPrefix+ automationBugs;
//					bugmessage     += "\n*  [["+testCase+"]]"+
//							(remainingBugs.equals("")?"":"\n**   Remaining Bugs:  "+remainingBugs)+
//							(fixedBugs.equals("")?"":"\n**   Fixed Bugs:  "+fixedBugs);
					bugNumF += numFBugs;
					bugNumR += numRBugs;
					bugNumA += numABugs;
					bugNumN += numNBugs;
					
					cleanBugHistory();
//					fixedBugs = "";
//					remainingBugs = "";
//					newBugs = "";
//					automationBugs = "";
					
				}
			numTested += numTestedKeywordInCase;
			
		} // end of all "+testSuite+"s
		
		if(bugnew.trim().equals("")){
			bugnew = "None";
		}
		if(bugremaining.trim().equals("")){
			bugremaining = "None";
		}
		if(bugfixed.trim().equals("")){
			bugfixed = "None";
		}
		if(bugauto.trim().equals("")){
			bugauto = "None";
		}
		
		String newIssueHints = getNewIssueHints(bugnew);
		bugmessage  = newIssueHints+
		//"\n*"+colorNewIssue+"   New Issues?:  "+bugnew.replaceAll("\\n.*Snapshot:.*","")+closeTag+
			          "\n*"+colorRemainBug+"   Known Issues:  "+getString(bugNumR)+bugremaining+closeTag+
//		              "\n*"+colorFixedBug+"   Fixed Known Issues:  "+getString(bugNumF)+bugfixed+closeTag+
		              "\n*"+colorAutomationIssue+"   Automation Issues?:  "+getString(bugNumA)+bugauto.replaceAll("\\n.*Snapshot:.*","")+closeTag;
		
		String Output_Report = testSummary(testSuite);
		
		logTAFTestResult(Output_Report,true);
		//sendEmail(FileUtil.getAbsDir(TAFLogger.testResultTXT));
		String emailSubject=System.getProperty(sysPropPrefix+"emailSubject");
		String emailTitle=System.getProperty(sysPropPrefix+"emailTitle");
		
		if(emailSubject==null||emailSubject.equals("")){
			emailSubject=reportSubject;
		}
		if(emailTitle==null||emailTitle.equals("")){
			emailTitle="Automation Test Report";
		}	
		
		Output_Report = FormatHtmlReport.getHttpReportFromWiki(Output_Report,FileUtil.getAbsDir(TAFLogger.testResultHTML),emailTitle,emailSubject);
		stopApp();

        sendEmail(Output_Report);
		numTCs=0;
		numTCsFail=0;
		
// Use these two lines if you want to see test results in data pools		
//		DatapoolUtilities.storeCSV((IDatapool) poolArgs[0],poolCsvFile,",",true);   	
//		DatapoolUtil.replaceData(poolCsvFile.getAbsolutePath(), "<NULL>", "",true);
		onTerminate();
	}
	
	public void sendEmail(String Output_Report){
		String exeDir = FileUtil.getAbsDir("tool");
		String exeFile = "CDOMessage.exe";
		String subject = "Automation Test Report - "+projectConf.projectName;//"Files";
		
		String smtpServer = "xchg-cas-array.acl.com";
		String fromAddress = "QAMail@ACL.COM";
		String fromName = projectConf.testerName;
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
					emailCmd = exeDir+s+exeFile+s+subject+d+smtpServer+d+fromName+d+fromAddress+d+toAddress+d+
					           body+d+attachFiles+d+ccAddress+d+bccAddress+d+importance+d+userName+d+password+d+
					           ipPort+d+ssl;
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
			//logTAFWarning("emailCmd = '"+emailCmd+"'");
			FileUtil.exeComm(false,emailCmd.replace("Output_Report",Output_Report ));
			
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
//*************************
	
}
