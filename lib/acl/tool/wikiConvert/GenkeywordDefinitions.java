package lib.acl.tool.wikiConvert;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import resources.lib.acl.tool.wikiConvert.GenkeywordDefinitionsHelper;
import com.rational.test.ft.*;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.WPF.*;
import com.rational.test.ft.object.interfaces.dojo.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.object.interfaces.generichtmlsubdomain.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.*;
import com.rational.test.ft.vp.*;
import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;

public class GenkeywordDefinitions extends GenkeywordDefinitionsHelper
{
	/**
	 * Script Name   : <b>GenkeywordDefinitions</b>
	 * Generated     : <b>2010-12-17 12:07:24 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2010/12/17
	 * @author Steven_Xiang
	 */
	private static int logLvl = 2;	// 1: info; 2: debug

	private static final boolean showAteNotes = true;

	private static final String SCRIPT_NAME = "Script Name";		// one line expected	
	private static final String DESCRIPTION = "Description";
	private static final String NOTES = "Notes";					// notes for external/normal use 
	private static final String ATE_NOTES = "ATE Notes";		// notes for Automation Test Engineer

	private static final String STEP = "//@Step";		// main step 
	private static final String SUB_STEP = "//@@Step";	// sub step
	
	
	// @Step = main step with number
	// @@Step sub step with letter a-z; 
	//@Step-@OperationName @@Step-@OperationName' are for steps has different operation in the same keyword
	//@More";	// for steps which need multiple line comments
	
	private static final String MORE = "//@More";	// for steps which need multiple line comments
	private static final String STEP_IN_DOC = "Steps:";
	
	private static final String DP_INT_IN_DOC = "Datapool Arguments";

	private static final String DP_VAR_DEC_SECTION_BEGIN = "// BEGIN of datapool variables declaration";
	private static final String DP_VAR_DEC_SECTION_END = "// END of datapool variables declaration";
	
	private static final String START_OF_CLASS = "public class ";
	private static final String START_OF_MAIN = "public void testMain(";

	private static  String KEYWORD_DIR = "/AX_GatewayPro/Tasks";
	private static  String KEYWORD_DOC_FILE = "C:/temp/keyword_doc/AX_GatewayPro.txt";
	
    private static String project="GatewayPro";
    private static String backToMain = "[[QA Test Automation|Back To Main - QA Test Automation]]";
    private static String toContents = "";
    private static String thisHeader = "Keywords and Value options";
    
	public void testMain(Object[] args) 
	{
		// TODO Insert code here
	        String projectPrefix = "AX_";
//			project = "Exception";
//			project = "Gateway";
//			project = "Addins";
//			project = "Soundwave";
		    project = "Desktop";
			projectPrefix = "ACL_";
			
			toContents = "[[#"+project+" "+thisHeader+"|Top]]";
			KEYWORD_DIR =  //"/"+projectPrefix+project+"/Tasks/Tools";
			               //"/"+projectPrefix+project+"/Tasks";
			               //"/"+projectPrefix+project+"/Tasks/Analyze";
			               //"/"+projectPrefix+project+"/Tasks/Data";
			               //"/"+projectPrefix+project+"/Tasks/Edit";
			               //"/"+projectPrefix+project+"/Tasks/Sampling";
			               "/"+projectPrefix+project+"/AppObject";

			               
			KEYWORD_DOC_FILE = System.getProperty("user.dir")+"/doc/"+project+"/"+projectPrefix+project+".txt";
			           //System.getProperty("user.dir")+"/doc"+KEYWORD_DIR+"/"+projectPrefix+project+".txt";
			
			StringBuffer contentsBuffer = new StringBuffer(2048);
			contentsBuffer.append(backToMain+"\n\n");	// for wiki
			

			contentsBuffer.append("-----------------------------------------------------\n");
			contentsBuffer.append("==== Common Arguments ====\n");
			
			contentsBuffer.append("<pre>\n");
			contentsBuffer.append(String.format("\t%1$18s : %2$s ","Run_Test","is an Option you can choose'T' for true or'F' for false to include/exclude Keywords")+"\n");
			contentsBuffer.append(String.format("\t%1$18s : %2$s ","Test_Category","Daily, Smoke or Regression")+"\n");
			contentsBuffer.append(String.format("\t%1$18s : %2$s ","Test_Scenario","Optional value which  describes  a new test scenario")+"\n");
			contentsBuffer.append(String.format("\t%1$18s : %2$s ","Keyword","is the name of the keyword")+"\n");
			contentsBuffer.append(String.format("\t%1$18s : %2$s ","KnownBugs","list of known bugs with brief description for each bug")+"\n");
			contentsBuffer.append(String.format("\t%1$18s : %2$s ","ExpectedErr","is a message which indicates It's a negative test with  expected  errors")+" \n");
			contentsBuffer.append(String.format("\t%1$18s : %2$s ","ProjectName","'Name|Name.ACL|Absolute path to project|Relative path to project'"+"\n"+
                                                "\t\t\t    If empty, current running project will be used for testing")+" \n");
			//contentsBuffer.append(String.format("\t%1$18s : %2$s ","MenuItem","[menu->menuitem]|[menuitem], a default path may be provided by each keyword")+"\n");
			contentsBuffer.append("</pre>\n");
			contentsBuffer.append("-----------------------------------------------------\n");
			
			contentsBuffer.append("=== "+project+" "+thisHeader+" ===\n");	// for wiki
			//contentsBuffer.append("-----------------------------------------------------\n");
			
			String keywordFolderFullPath = System.getProperty("user.dir") + KEYWORD_DIR;

			String curKeyword;
			
			for (String curKeywordFile : new File(keywordFolderFullPath).list(new FileFilter("java"))) {
				curKeyword = curKeywordFile.split("\\.")[0];

				if (!curKeyword.startsWith("_")&& ! curKeyword.startsWith("KeywordUtil")) { // ignore internal scripts
					logInfo("Working on keyword [" + curKeyword + "] ...");

					contentsBuffer.append("==== " + curKeyword + " ====" + "\n\n");
					contentsBuffer.append("<pre>\n");	// for wiki format
					contentsBuffer.append(getKeywordDocInfo(keywordFolderFullPath + "/" + curKeywordFile));
					contentsBuffer.append("</pre>\n");	// for wiki format
					contentsBuffer.append(toContents+"\n");	// back to contents
					contentsBuffer.append("-----------------------------------------------------\n");
				}
			}
			
			contentsBuffer.append(backToMain+"\n\n");	// for wiki

			logInfo("\n=== Write keywords documents to file [" + KEYWORD_DOC_FILE + "] ...");
			writeToNewFile(KEYWORD_DOC_FILE, contentsBuffer);
		}

		private static String getKeywordDocInfo(String fileName) {
			StringBuffer contentsBuffer = new StringBuffer();

			/////////////////////////////////////////////////////////////////////////////////////////////
			// loop through keyword file to generate main manual and draft steps (for further processing)
			try {
				FileReader freader = new FileReader(new File(fileName));
				LineNumberReader lnreader = new LineNumberReader(freader);

				String keywordFileName = fileName.substring(fileName.lastIndexOf("/") + 1).split("\\.")[0];

				boolean inClass = false;
				boolean inMain = false;
				boolean inDpSection = false;
				String dpVar;
				String dpVarDes;

				int lineCount = 0;
				int prevKeyLineCount = 0;
				int stepCount = 0;
				int subStepCount = 0;

				String line;
				String prevKey = null;
				while ((line = lnreader.readLine()) != null) {
					line = line.trim();
					lineCount++;
	                
					if (line.contains(START_OF_CLASS + keywordFileName)) {
						inClass = true;
						continue;
					}

					if (! inClass) {
						continue;	// ignore any comments before start of class
					}
	                
					// Datapool section - begin
					if (line.equalsIgnoreCase(DP_VAR_DEC_SECTION_BEGIN)) {
						inDpSection = true;
						contentsBuffer.append("\n" + DP_INT_IN_DOC + "\n");
						continue;
					} else if (line.equalsIgnoreCase(DP_VAR_DEC_SECTION_END)){
						inDpSection = false;
						contentsBuffer.append("\n");
						continue;
					}

					if (inDpSection) {
						if(line.contains("//@arg ")){
							dpVar = line.split(" ")[2].split(";")[0].substring(2); //private String dpName;			// unlock target or 'all' to unlock all items
							dpVarDes = line.split("//@arg ")[1];
							contentsBuffer.append(String.format("\t%1$18s : %2$s ",dpVar,dpVarDes) + "\n");
						}else if(line.startsWith("//@value ")){
							dpVar=" ";
							dpVarDes = line.trim().split("//@value ")[1];
							//dpVarDes = dpVarDes.split(",")[0].trim()+
							contentsBuffer.append(String.format("\t%1$18s   %2$s ",dpVar,dpVarDes) + "\n");
							//contentsBuffer.append("\t" + dpVar + ((dpVar.length()<10)? "\t":"") + "\t  " + dpVarDes + "\n");
						}
						
						
						
						continue;
					}
					// Datapool section - end

					if (line.contains(START_OF_MAIN)) {
						inMain = true;	// identify header and main
						continue;
					}

					String key = null;	
					String value = null;
					if (! inMain) { // in header
						Pattern keyPattern = Pattern.compile(".*\\*\\s*(.*):(.*)");	// 
						Matcher keyMatcher = keyPattern.matcher(line);

						if (keyMatcher.find()) {
							key = keyMatcher.group(1).trim();
							value = keyMatcher.group(2).trim();

							if (key.equalsIgnoreCase(SCRIPT_NAME)
									|| key.equalsIgnoreCase(DESCRIPTION)
									|| key.equalsIgnoreCase(NOTES)
									|| key.equalsIgnoreCase(ATE_NOTES) && showAteNotes) {
								
								contentsBuffer.append(key);
								if (key.length() < 8) {
									contentsBuffer.append("\t\t");
								} else {
									contentsBuffer.append("\t");
								}

								//contentsBuffer.append(":\t" + value.replaceAll("<b>", "").replaceAll("</b>", "") + "\n");

								if (key.equalsIgnoreCase(SCRIPT_NAME)
										&& ! keywordFileName.equals(value.replaceAll("<b>", "").replaceAll("</b>", ""))) {
									logWarning("Not match Script Name for keyword [" + keywordFileName + "]");		
									contentsBuffer.append(":\t" + keywordFileName + "\n");
								}else{
									contentsBuffer.append(":\t" + value.replaceAll("<b>", "").replaceAll("</b>", "") + "\n");
								}
								
								prevKey = key;
								prevKeyLineCount = lineCount;
							} else {
								prevKey = null;	// not care key
							}
						} else if (prevKey != null 
								&& line.trim().length() > 1) {
							Pattern keyContPattern = Pattern.compile(".*\\*\\s*(.*)");	// 
							Matcher keyContMatcher = keyContPattern.matcher(line);

							if (keyContMatcher.find()) {
								if (lineCount == (prevKeyLineCount + 1)) { // continue contents of latest key
									contentsBuffer.append("\t\t\t\t" + keyContMatcher.group(1) + "\n");

									prevKeyLineCount = lineCount;	// for more lines
								}
							}
						}
					} else { // in main, search for steps only
						if (! isKeywordDocLine(line)) {
							continue;
						}
						
						boolean isMoreLine = false;

						if (line.contains(MORE)) {
							if (lineCount == (prevKeyLineCount + 1)) {
								key = prevKey;
								isMoreLine = true;
							} else {
								logWarning("Not valid line [" + line + "], must follow a valid step line!");
								continue;
							}						
						} else {
							key = line.contains(SUB_STEP)? SUB_STEP : STEP;
						}

						prevKeyLineCount = lineCount;
						prevKey = key;

						if (key.equals(SUB_STEP)) { // sub step
							if (! isMoreLine) {
								subStepCount++;
								contentsBuffer.append("\t\t" + (char)('a' + subStepCount - 1) + ". ");
							} else {
								contentsBuffer.append("\t\t\t" + MORE);
							}

							contentsBuffer.append(line.substring(line.indexOf(key) + key.length() + 1) + "\n");
						} else { // main step
							if (! isMoreLine) {
								stepCount++;
								subStepCount = 0;

								if (stepCount == 1) { // steps title
									contentsBuffer.append(STEP_IN_DOC + "\n");
								}

								contentsBuffer.append("\t" + stepCount + ". ");
							} else {
								contentsBuffer.append("\t  " + MORE);
							}

							contentsBuffer.append(line.substring(line.indexOf(key) + key.length() + 1) + "\n");
						}
					}

				} // end loop of read file
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}

			return getNormalizedSteps(contentsBuffer);
		}

		// loop through contentsBuffer to handle multiple line steps and steps for different operations
		private static String getNormalizedSteps(StringBuffer contentsBuffer) {
			StringBuffer newContentsBuffer = new StringBuffer();

			Pattern operationStepPattern = Pattern.compile("(.*)\\.\\s@(.*?)\\s(.*)");	// e.g. '1. @AddUser Add user if not exist' 
			Matcher operationStepMatcher = operationStepPattern.matcher(contentsBuffer.toString());

			if (operationStepMatcher.find()
					|| contentsBuffer.toString().contains(MORE)) {	
				String curCounter;
				String curCounterType;
				String prevCounterType = "";
				String curOperation = "";
				String prevOperation = "";
				int curOpStepCount = 1;
				int curOpSubStepCount = 1;
				int stepCount = 1;
				int subStepCount = 1;

				String curStepContents;

				boolean inSteps = false;
				for (String curLine: contentsBuffer.toString().split("\n")) {
					if (! inSteps) {
						if(curLine.contains(STEP_IN_DOC)) {
							inSteps = true;
						}
						newContentsBuffer.append(curLine + "\n");
						continue;
					} else {	// in steps
						operationStepMatcher = operationStepPattern.matcher(curLine.trim());

						if (operationStepMatcher.find()) {	// for operation steps
							curCounter = operationStepMatcher.group(1);
							curOperation = operationStepMatcher.group(2);
							curStepContents = operationStepMatcher.group(3);

							if (! curOperation.equalsIgnoreCase(prevOperation)) { // new operation
								curOpStepCount = 1;	// reset counter
								curOpSubStepCount = 1;
								prevOperation = curOperation;
							}

							curCounterType = (curCounter.charAt(0) >= 'a' && curCounter.charAt(0) <= 'z')? "char" : "int";

							// append the operation title
							if (curCounterType.equals("int") && (curOpStepCount == 1)) {
								newContentsBuffer.append("\n\t" + getOperationName(curOperation) + ":\n");
							} else if (curCounterType.equals("char") && (curOpSubStepCount == 1)
									&& (prevCounterType.equals("char") || prevCounterType.equals(""))){	// in sub Step, but diff operation 
								newContentsBuffer.append("\n\t\t" + getOperationName(curOperation) + ":\n");
							}

							prevCounterType = curCounterType;

							if (curCounterType.equals("char")) {
								newContentsBuffer.append("\t\t\t" + (char)('a' + curOpSubStepCount - 1) + "). " + curStepContents + "\n");
								curOpSubStepCount++;
							} else { // main step
								newContentsBuffer.append("\t\t" + curOpStepCount + "). " + curStepContents + "\n");
								curOpStepCount++;
								curOpSubStepCount=1;	// reset subStep counter
							}
						} else if (curLine.trim().startsWith(MORE)) { // '//@More ***' line
							if (! "".equalsIgnoreCase(prevOperation)) {
								newContentsBuffer.append("\t");
							}

							curLine = curLine.replace(MORE, "");
							newContentsBuffer.append(curLine + "\n");
						} else { // for non operation steps, non more line
							curLine = curLine.trim();

							if (! "".equalsIgnoreCase(prevOperation)) {
								prevOperation = "";	// reset curOperation
								prevCounterType = "";

								newContentsBuffer.append("\n");
							}

							curStepContents = curLine.substring(curLine.indexOf(" "));
							curCounter = curLine.substring(0, curLine.indexOf("."));

							curCounterType = (curCounter.charAt(0) >= 'a' && curCounter.charAt(0) <= 'z')? "char" : "int";

							if( curCounterType.equals("int")) {							
								newContentsBuffer.append("\t" + stepCount + "." + curStepContents + "\n");
								stepCount++;
								subStepCount = 1;
							} else {
								newContentsBuffer.append("\t\t" + (char)('a' + subStepCount - 1) + "." + curStepContents + "\n");
								subStepCount++;
							}																			
						}
					}
				}
			} else {
				newContentsBuffer = contentsBuffer;
			}

			return newContentsBuffer.toString();
		}

		private static String getOperationName(String origName) {
			StringBuffer newNameBuffer = new StringBuffer();

			char c;
			for (int i=0; i<origName.length(); i++) {
				c = origName.charAt(i);

				if (i==0) {
					newNameBuffer.append(Character.toString(c).toUpperCase());
					continue;
				}

				if (c >= 'A' && c <= 'Z') {
					newNameBuffer.append(" ");
				}

				newNameBuffer.append(c);
			}

			return newNameBuffer.toString().trim();		
		}

		private static boolean isKeywordDocLine(String line) {
			if ((line == null || "".equalsIgnoreCase(line.trim()))) {
				return false;
			}

			if (line.contains(STEP)
					|| line.contains(SUB_STEP)
					|| line.contains(MORE))  {
				return true;
			}

			return false;
		}

		private static void writeToNewFile(String fileName, StringBuffer newContentsBuffer) {
			try {
				FileWriter txt;
				PrintWriter out;
				txt = new FileWriter(new File(fileName));
				out = new PrintWriter(txt);

				out.print(newContentsBuffer);

				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static void logInfo(String message) {
			if (logLvl >= 1) {
				System.out.println(message);
			}
		}

		public static void logWarning(String message) {
			System.out.println("\t\t#### " + message + " ####");
		}
	}



