package com.acl.qa.taf.helper;

import ibm.util.FileOps;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.acl.qa.taf.helper.superhelper.InitializeTerminateHelper;
import com.acl.qa.taf.helper.superhelper.LoggerHelper;
import com.acl.qa.taf.helper.superhelper.TAFLogger;
import com.acl.qa.taf.util.DatapoolUtil;
import com.acl.qa.taf.util.FileUtil;


//import com.acl.qa.taf.conf.bean.FrameworkConf;

public class TestDriverSuperHelper   extends InitializeTerminateHelper {

	private String keywordLine1 = "\t\n------     ";
	private String keywordLine2 = "     ------\n";
	private String kPrefix = ">>>";
	private String testResultLine;
	public  String expectedErrMessage ="";
	public String testScenario="";
	public String buildName = "";
	private boolean prtFailScenarioTitle = false;
	public String linkToKeywordDescription = "";
	public String runTest ="";
	public String dpTestCategory ="";
	public Object[] args = new Object[3];
	public boolean unicodeOnly = false,
	               nonunicodeOnly = false,
	               skipTest=false; 
	public String subpathToKeyword="",
	              temp[];
	private Object ksh;
    private String testSubset = "";
	
	public TestDriverSuperHelper(){
		//batchRun = false;
	}
	
	
	// exeTestCase for a single pool
	public void exeTestCase(Object[] uargs){
		if(uargs==null||uargs.length<3){
			logTAFWarning("Failed to load your test data?");
			return;
		}else{
			args[0] = datapool = (HSSFSheet) uargs[0];
			args[1] = dph = (ArrayList<String>) uargs[1];
			args[2] = dpi = (Iterator) uargs[2];
			caseObj = this;
		}
		
		
		int numConsecutiveFails = 0;
		
		// Uncomment this section and line 336 to enable test matrix 
//		if(testResultHeader==""){
//			if(batchRun){
//				testResultHeader="Test_No.,Test_Name,Keyword_No.,Keyword_Name,Test_Result,Test_Message";
//			}else{
//				testResultHeader = "Keyword_No.,Keyword_Name,Test_Result,Test_Message";
//			}
//
//			FileUtil.writeFileContents(projectConf.tempCsvResult, testResultHeader);
//		}

		if(scriptStartTime == null){
			if(isMainScript)
				scriptStartTime = TAFLogger.timeOfTest;
			else 
				scriptStartTime = Calendar.getInstance().getTime().toString();
		}
        // ** Set for daily test range
		
		if(!isMainScript()){
     		if(!testCategory.equals("")){
     			startFromLine = 2;
				endAtLine = Integer.MAX_VALUE;
     		}
		}
		
		
		
		while (dpi.hasNext() && currentLine<=endAtLine &&
				!stopScript)
		{
			dpw = (HSSFRow)dpi.next();
			args[2] = dpw;
			
			
			skipTest = false;			
			runTest = getDpString("Run_Test");

			logItem = "";
			// To select form 'Defect Daily, Smoke, or Regression
			
			if(batchRun){
			   dpTestCategory = getDpString("Test_Category");
			   skipTest = isNotInCategory(dpTestCategory);
			}
            
			
			//##D## new isUnicode variable added and assigned value
			//isUnicode = getDpString("Is_Unicode");
			String keywordFullName = getDpString("Keyword").replaceAll("[\\\\/]",".");
			    pathToKeywordScripts = "";//originalPathToKeywordScripts;
				temp = keywordFullName.split("[\\\\/\\.]");
				for(int i=0;i<temp.length-1;i++){
					pathToKeywordScripts +=temp[i]+".";
				}
				keywordName = temp[temp.length-1];

			buildName = getDpString("Test_Project");	
            knownBugs = getDpString("KnownBug");
            expectedErr = getDpString("ExpectedErr");
              String ut = getDpString("UnicodeTest");
  			testSubset = getDpString("Test_Subset");
            if(!isValidBuild(buildName)){
            	skipTest = true;
            }
            
            if(!projectConf.testSubset.equals("")&&
            	//	!projectConf.testSubset.equals(testSubset)){
            		// simplify this  if you want - steven
            	!testSubset.matches(projectConf.testSubset+
            			"|.*,"+projectConf.testSubset+
            			"|"+projectConf.testSubset+",.*"+
            			"|.*,"+projectConf.testSubset+",.*")){
            		skipTest = true;
            }
            if(ut.equalsIgnoreCase("True")){
            	unicodeOnly = true;
            	if(unicodeOnly&&!isUnicode){
            		skipTest = true;
            	}
            }else if(ut.equalsIgnoreCase("False")){
            	nonunicodeOnly = true;
            	if(nonunicodeOnly&&isUnicode){
            		skipTest = true;
            	}
            }
//            if(ProjectConf.appLocale.matches("(?i)Ko|Pl")){  // Ko ACLSE is not available at this time - Steven
//            	ProjectConf.testType = "LOCALONLY";
//            	//sleep(1);
//            	//logTAFWarning("\t!!! No korean aclse available, some tests may fail due to the failure of server connection!!!");
//            }   

            String temp = getDpString("ProjectName");

            if(projectConf.testType.matches("(?i)SERVER")){   

            	if(temp.matches("(?i).+LOCAL(\\.ACL)?")){
            		skipTest = true;
            	}
            }            
            if(localOnlyTest){            	
            	if(getDpString("ProjectName").matches("(?i)(.+SERVER(\\.ACL)?)")){
            		skipTest = true;
            	}
            	if(keywordName.matches("(?i)servermenu"))
        			skipTest= true;
            }
            
            errorHandledInLine = false;
            currentLine++;
            logTAFDebug("Line : "+currentLine+" runTest = '"+runTest+"' keywordName = '"+keywordName+"'");
            

		    // ** For GWP, handle sleep time between keywords
            if ((runTest.matches("T|t")||runTest.equals(""))&&
					lineInRange(currentLine)&&
					"sleep".equalsIgnoreCase(keywordName)){ 
 				String sleepCounter = getDpString("reqArg0");
 				logTAFInfo("\n\tSleep [" + sleepCounter + "] seconds to wait for previous task complete ...\n");

 				sleep(Double.parseDouble(sleepCounter));

 			}
 		      if(currentLine==endAtLine)
 		    	  sleep(0);
   // ** Call keywords one by one
            if ((runTest.matches("T|t")||runTest.equals(""))&&
					!keywordName.startsWith("#") && 
					!"".equalsIgnoreCase(keywordName)&&
					lineInRange(currentLine)&&
					!"sleep".equalsIgnoreCase(keywordName)&&
					!skipTest){ 
				
				try{
					testScenario = getDpString("Test_Scenario").trim();					
				}catch(Exception e){
					testScenario = "";
				}
				if(!testScenario.equals("")){
					logTAFInfo("\n+++++ Test "+testScenario+"\n");
				}
				
				try
				{  
					testResult = "None";
					message = "";

					//Call each keyword in the associated data pool	
					numKWs++;
					if(batchRun)
						testResultLine = testCaseLine+","+numKWs+","+keywordName;
					else
						testResultLine = numKWs+","+keywordName;

					
					logTAFTestResult(keywordLine1 +" "+numKWs+").Keyword: " + keywordName +"(line "+ currentLine +" in data pool) started - "+
							Calendar.getInstance().getTime().toString()+ keywordLine2,true);
                    currentkeyword = caseName+"(line "+ currentLine +")."+keywordName;
				    currentTestLine = currentLine;
				    
						logTAFDebug("Call: "+pathToKeywordScripts+ keywordName);
						//stopScript = true;
						ksh = callScript(keywordFullName, args);
						unregisterAllInAUT();
					
					
					
	                logTAFDebug("ExpectedErr ='"+expectedErr+"'");
					if(!expectedErr.equals("")){						
						expectedErrMessage = "(Negative Test with expected error - "+expectedErr+")";						
					}else{
						expectedErrMessage = "";
					}
					if(errorHandledInLine)
						expectedErr = "";
					LoggerHelper.updateTestInfo("",true);
					
				}
				catch(Exception e) {
					logTAFException(e);
				}
				
//				logTAFDebug("test result before possible reverse, testResult ='"+testResult+"'");
				if(!errorHandledInLine&&!expectedErr.equals("")){ 
					
					if(testResult.equals("Pass")){
						testResult = "Fail";
						message = "Expected error does not apear - "+expectedErr;
						failMessage = message;						
					}else{						
						if(numKWsFail==0){							  
							// ***** For this test case
							testCaseResult = "Pass";
							failMessage = "";
							// ***** For batch run
							if(numTCsFail==0){
								mainMessage = "";
								testMainResult = "Pass";	
							}							
						}
						// ***** For this keyword
						testResult = "Pass"; 
						message = "";		
					}
					
				errorHandledInLine = false;
				expectedErr = "";
				logItem = "";
				
				}
				checkMemory();

				linkToKeywordDescription = keywordName;// + "|" +keywordName;
				
				if(testResult=="Pass"){
					numConsecutiveFails = 0;
					kPrefix = ">>>";
				}else{
					numConsecutiveFails++;					
						kPrefix = "!!!";                    
					String msgTag="";
					if(knownBugs.equals("")){
						if(isAutomationIssue(message)){
						    msgTag = "\n***\t  [AUTOMATION ISSUE?]";
						}else{
							msgTag = "\n***\t  [NEW ISSUE?]";
						}
					}else{
						msgTag = "\n***\t  [KNOWN ISSUE:    "+knownBugs+"]";
					}
					keywordErrors = keywordErrors + 
					//"\n**\t\t"+linkToKeywordDescription+" "+numKWs + ") " + 
					"\n**\t\t"+linkToKeywordDescription+" - "+"line "+ currentLine +") " + 
					
					((prtFailScenarioTitle)? ("Scenario - '" + curTestScenario+"' ") : "") + message +msgTag;
					//(knownBugs.equals("")?"\n***\t  [NEW ISSUE?]":"\n***\t  [KNOWN ISSUE:    "+knownBugs+"]");
					message = message.replaceAll("\\*+", "");
					unKnownBugs = message;//.replaceAll("\\n.*Snapshot:.*","");

//					keywordErrors = keywordErrors + ((prtFailScenarioTitle)? ("\n\tScenario Failed: " + curTestScenario) : "") + 
//					"\n\t   -- " + keywordName + " " + numKWs + ") " + message;
					prtFailScenarioTitle = false;	// only print once
					
// *********  You can debug here whenever there is a keyword failure
					numKWsFail++; 
// *********  				
					
					if (numConsecutiveFails>=projectConf.stopIfNumConsecutiveFailures){
						stopScript = true; // stop current test case if critical keywords fail
					}

				}
				
				testResultLine = testResultLine+","+testResult+","+message.replaceAll(",", "|").replaceAll("\\n","--");
//                logTAFDebug("Final test result for this keyword, testResult ='"+testResult+"'");

				if(!knownBugs.equals("")){
					if(testResult.equals("Pass")){
						kPrefix = ">>-"; // Pass: Fixed issue
						fixedBugs += "\n***\t"+linkToKeywordDescription+ " "+knownBugs;
						numFBugs++;
					}else {
						kPrefix = ">>!"; // Warning : Known(old) issue
						remainingBugs += "\n***\t"+linkToKeywordDescription+ " "+knownBugs;
						numRBugs++;
					}
				}else if(!testResult.equals("Pass")){
					if(isAutomationIssue(unKnownBugs)){
						kPrefix = ">!!";  // Fail: Automation issue
						automationBugs += "\n***\t"+linkToKeywordDescription+ " "+unKnownBugs;
						numABugs++;
					}else{
						kPrefix = "!!!";  // Fail: New issue
					    newBugs += "\n***\t"+linkToKeywordDescription+ " "+unKnownBugs;
					    numNBugs++;
					}
				}
				
				logTAFTestResult(kPrefix+" Keyword: " + 
						keywordName+ " " +
						(testResult==("Pass")? 
								testResult+"ed" +expectedErrMessage:testResult+"ed: " +
								message)+
								(knownBugs.equals("")?"":"\n\t  [KNOWN BUGS:    "+knownBugs+"]"),
								testResult=="Pass"?true:false);
				
				
				// **** Enable this to Create test matrices  ******
				//  FileOps.appendStringToFile(projectConf.tempCsvResult, testResultLine);
				sleep(timerConf.waitBetweenKeywords);

			}
			
			// get comment for current test scenario
			if (keywordName.startsWith(scenarioDesLinePrefix)) {
				curTestScenario = keywordName.substring(scenarioDesLinePrefix.length()).trim();
				prtFailScenarioTitle = ! "".equals(curTestScenario)? true : false;
			}
			

		} // End of while (all keywords)
		
		if(stopScript){
			logTAFInfo(stopMessage+" - stop remaining execution from this test suite");
		}

		if(testCaseResult == "Fail"){
			numTCsFail++;
		}

		scriptEndTime = Calendar.getInstance().getTime();
		
		String[] cases = caseName.split("\\.");
		String poolName = cases[cases.length-1];
		if(!batchRun){
			bugmessage = 
			(remainingBugs.equals("")?"":"\n* [["+poolName+"]]\n**"+colorRemainBug+"   Remaining Issues:   "+closeTag+remainingBugs)+
			(fixedBugs.equals("")?"":"\n* [["+poolName+"]]\n**"+colorFixedBug+"   Fixed Bugs:   "+closeTag+fixedBugs)+
			//(newBugs.equals("")?"":"\n* [["+poolName+"]]\n**"+colorNewIssue+"   New Issues?:   "+closeTag+unKnownBugs)+
			(newBugs.equals("")?"":"\n* [["+poolName+"]]\n**"+colorNewIssue+"   New Issues?:   "+closeTag+newBugs)+
			(automationBugs.equals("")?"":"\n* [["+poolName+"]]\n**"+colorAutomationIssue+"   Automation Issues?:   "+closeTag+automationBugs);
			cleanBugHistory();
//			fixedBugs = "";
//			newBugs = "";
//			automationBugs = "";
//			remainingBugs = "";
		}else{
			
		}
		
		
		numTestedKeywordInCase = numKWs;
		String Output_Report = testSummary(testKeyword);
    	logTAFTestResult(Output_Report,true);

		numKWs=0;
		numKWsFail=0;
		scriptStartTime = null;
        
		//logTAFDebug("<><><><><>\t"+appMemoryUsage());

		Output_Report = onTerminate(Output_Report);
		
		//logTAFDebug("<><><><><>\t"+appMemoryUsage());
		// Use these two lines if you want to see test results in data pools		
		//DatapoolUtil.storeCSV((IDatapool) poolArgs[0], poolCsvFile, ",", true);
		//DatapoolUtil.replaceData(poolCsvFile.getAbsolutePath(), "<NULL>", "",true);

	}
	private boolean isNotInCategory(String cate){
		boolean notInCate = false;
//		String autoRunTestCategory = null;
		String smoke = "Smoke",
		       regression = "Regression",
		       daily = "Daily",
		       defect = "Defect",
		       datatype = "DataType",
		       all = "All";

		
		if(testCategory.equals("")){
			return notInCate;
		}
		
		if(skipEuroLocale(cate))
			notInCate = true;
		
		if(currentLine==1){
			startFromLine = 2;
			endAtLine = Integer.MAX_VALUE;
		}
		
        if(testCategory.equalsIgnoreCase(smoke)){		
        	
        	if(cate.equals("")&&currentLine==1){ // For old data pools which haven't added field 'Test_Category' to
				 endAtLine = 4;
            }
			if(!cate.equalsIgnoreCase(daily)
					&&!cate.equalsIgnoreCase(smoke)
					&&!cate.equalsIgnoreCase(defect)
					&&!cate.equalsIgnoreCase(datatype)
					){
				notInCate = true;
			}
			
			if(testCaseExclusive&&cate.equalsIgnoreCase(daily)
					){
				notInCate = true;
			}
			
		}else if(testCategory.equalsIgnoreCase(daily)){		
        	if(cate.equals("")&&currentLine==1){ // For old data pools which haven't added field 'Test_Category' to
				 endAtLine = 2;
           }
			if(!cate.equalsIgnoreCase(daily)
					//&&!cate.equalsIgnoreCase(defect)
					){
				notInCate = true;
			}
		}else if(testCategory.equalsIgnoreCase(datatype)){		
			if(!cate.equalsIgnoreCase(datatype)
					){
				notInCate = true;
			}
		}else if(testCategory.equalsIgnoreCase(defect)){		
			if(!cate.equalsIgnoreCase(defect)
					){
				notInCate = true;
			}
		}else if(testCategory.equalsIgnoreCase(regression)){		
        	
			if(!cate.equalsIgnoreCase(daily)
					&&!cate.equalsIgnoreCase(smoke)
					&&!cate.equalsIgnoreCase(regression)
					&&!cate.equalsIgnoreCase(all)
					&&!cate.equalsIgnoreCase(defect)
					&&!cate.equalsIgnoreCase(datatype)
					){
				notInCate = true;
			}
			if(testCaseExclusive&&cate.equalsIgnoreCase(daily)
			){
		         notInCate = true;
	         }
		}else if(testCategory.equalsIgnoreCase(all)){  // All  --> in case we have other special cases added to data pool
			     //        --> or for debugging

		}else if(!testCategory.equalsIgnoreCase("")){
			notInCate = true;
		}
        
		return notInCate;
	}
	
	public static boolean skipEuroLocale(String cate){
		String cch = "";
		boolean skip = false;
		

		if(cate.equals("")){
			return skip;
		}
        String enCateOnly = "Defect"+
                            "|DataType"+
                            "";
		String validEnLocale = cch+"En"+		                     
		                     "|"+cch+"Zh|"+cch+"Ch|"+cch+"Cn"+		                     
		                     "|"+cch+"Ko"+
		                     "|"+cch+"Ja|"+cch+"Jp"+
		                     "";
		String validEuroLocale = cch+"De"+
                              "|"+cch+"Es"+
                              "|"+cch+"Pt"+
                                "|"+cch+"Fr"+
                                "|"+cch+"Pl"+
                               "";
		if(projectConf.appLocale.matches("(?i)"+validEnLocale)){
			skip = false;
		}else if(cate.matches("(?i)"+enCateOnly)){
			skip = true;
		}
		return skip;
		
	}
	
}
	
