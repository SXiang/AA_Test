package com.acl.qa.taf.tool;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.acl.qa.taf.util.FileUtil;

public class GenKeywordDoc {
	private static int logLvl = 2;	// 1: info; 2: debug
	private static final boolean showAteNotes = true;

	private static String PROJECT;
	private static final String STR_BACK_TO_AUTOMATION_MAIN = "[[QA Test Automation|Back To Main - QA Test Automation]]";
	private static String STR_BACK_TO_AUTOMATION_PROJ_AND_MAIN;
	private static String STR_PROJ_KEYWORD_DEF;
	private static String STR_PROJ_KEYWORD_DEF_TOP;
	
	private static final String DP_INT_IN_DOC = "Datapool Interface:";
	
	private static final String SCRIPT_NAME = "Script Name";		// one line expected	
	private static final String DESCRIPTION = "Description";
	private static final String NOTES = "Notes";					// notes for external/normal use 
	private static final String ATE_NOTES = "ATE Notes";		// notes for Automation Test Engineer

	private static final String STEP = "//@Step";		// main step
	private static final String SUB_STEP = "//@@Step";	// sub step
	// '//@Step-@OperationName' and '//@@Step-@OperationName' are for steps has different operation in the same keyword
	private static final String MORE = "//@More";	// for steps which need multiple line comments
	private static final String STEP_IN_DOC = "Steps:";

	private static final String DP_VAR_DEC_SECTION_BEGIN = "// BEGIN of datapool variables declaration";
	private static final String DP_VAR_DEC_SECTION_END = "// END of datapool variables declaration";
	
	private static final String START_OF_CLASS = "public class ";
	private static final String START_OF_MAIN = "public void testMain(";
	
	public static void main(String args[])
	{
		String[] projects = {"GatewayPro", "Admin", "Addins"};
		
		for (String curProj : projects) {
			PROJECT = curProj;
			STR_BACK_TO_AUTOMATION_PROJ_AND_MAIN = "[[" + PROJECT + " Automation Guide|Back To " + PROJECT + " Automation Guide]] | " + STR_BACK_TO_AUTOMATION_MAIN;
			STR_PROJ_KEYWORD_DEF = "[[" + PROJECT + "_Keyword_Definition]]";
			STR_PROJ_KEYWORD_DEF_TOP = "[[#" + PROJECT + "_Keyword_Definition|Top]]";
			
			String keywordFolderFullPath = FileUtil.userWorkingDir + "/AX_" + PROJECT + "/Tasks";
			String keywordsDefWikiFile = FileUtil.userWorkingDir + "/doc/WikiPages" + "/AX_" + PROJECT + "/AX_" + PROJECT + "_keywords_Definitions.txt";
			
			logInfo("\n=======================================");
			logInfo("=== Working on project [" + PROJECT + "] ===\n");
			
			StringBuffer contentsBuffer = new StringBuffer(2048);
			contentsBuffer.append(STR_BACK_TO_AUTOMATION_PROJ_AND_MAIN + "\n\n");
			
			contentsBuffer.append("===" + STR_PROJ_KEYWORD_DEF + "===\n\n");

			String curKeyword;
			for (String curKeywordFile : new File(keywordFolderFullPath).list(new OnlyExt("java"))) {
				curKeyword = curKeywordFile.split("\\.")[0];

				if (! curKeyword.startsWith("_")) { // ignore internal scripts
					logInfo("Working on keyword [" + curKeyword + "] ...");

					contentsBuffer.append("==== " + curKeyword + " ====" + "\n\n");
					contentsBuffer.append("<pre>\n");
					contentsBuffer.append(getKeywordDocInfo(keywordFolderFullPath + "/" + curKeywordFile));
					contentsBuffer.append("</pre>\n");
					contentsBuffer.append(STR_PROJ_KEYWORD_DEF_TOP);	// back to contents
					contentsBuffer.append("\n-----------------------------------------------------\n");
					contentsBuffer.append("-----------------------------------------------------\n");
				}
			}
			
			if (PROJECT.equalsIgnoreCase("GatewayPro") || PROJECT.equalsIgnoreCase("Admin") ) {
				// for sleep 'keyword' provided by framework
				contentsBuffer.append("==== sleep ====" + "\n\n");
				contentsBuffer.append("<pre>\n");
				contentsBuffer.append("Script Name	:	sleep\n");
				contentsBuffer.append("Description	:	sleep for target seconds to control whole test process\n");
				contentsBuffer.append("Notes		:   None\n\n");
				contentsBuffer.append(DP_INT_IN_DOC + "\n");
				contentsBuffer.append("\tdpSeconds:	seconds to sleep/wait\n\n");
				
				contentsBuffer.append("\nTemplate (Text):\n\t " + "sleep,(dpSeconds),v_dpSeconds,\n");
				contentsBuffer.append("\nTemplate (Excel):</pre>\n<TABLE><TD>sleep\n<TD>(dpSeconds)\n<TD>v_dpSeconds\n<TD>\n</TABLE>\n\n");
				contentsBuffer.append(STR_PROJ_KEYWORD_DEF_TOP);

				contentsBuffer.append("\n-----------------------------------------------------\n");
				contentsBuffer.append("-----------------------------------------------------\n");
			}
			
			contentsBuffer.append(STR_BACK_TO_AUTOMATION_PROJ_AND_MAIN);

			logInfo("=== Write wiki keywords definition of project [" + PROJECT + "] to file [" + keywordsDefWikiFile + "]");
			writeToNewFile(keywordsDefWikiFile, contentsBuffer);
			logInfo("===================================================================");
		}
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
			
			StringBuilder dpIntSb = new StringBuilder();
			StringBuilder dpIntVarsSb = new StringBuilder();
			final String SEP_CSV_TD = "\n<TD>";
			final String SEP_CSV_COMMA = ",";
			final String SEP_DP_VARS = "; ";
						
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
					
					dpIntSb.delete(0, dpIntSb.length());	// init dpIntSb
					dpIntVarsSb.delete(0, dpIntVarsSb.length()); // init dpIntVarsSb

					continue;
				} else if (line.equalsIgnoreCase(DP_VAR_DEC_SECTION_END)){
					inDpSection = false;
					
					String keywordNameStr = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.indexOf(".java"));
					
					String dpIntStr = dpIntSb.substring(0, dpIntSb.length() - SEP_DP_VARS.length()) + ")";
					
					contentsBuffer.append("\nTemplate (Text):\n\t " + keywordNameStr + SEP_CSV_COMMA + "(");
					contentsBuffer.append(dpIntStr + dpIntVarsSb.toString().replaceAll(SEP_CSV_TD, SEP_CSV_COMMA) + SEP_CSV_COMMA + "\n");
					
					contentsBuffer.append("\nTemplate (Excel):</pre>\n <TABLE>" + SEP_CSV_TD + keywordNameStr + SEP_CSV_TD + "(");
					contentsBuffer.append(dpIntStr + dpIntVarsSb + SEP_CSV_TD + "\n" + "</TABLE>" + "\n\n");
					
					contentsBuffer.append("---------------------\n");
					contentsBuffer.append("<pre>\n");
					continue;
				}

				if (inDpSection) {
					if (line.trim().startsWith("//")) { // for continue comments line
						dpVar = "";
						dpVarDes = (line.split("//").length > 1)? line.split("//")[1] : " ";
						
						contentsBuffer.append(String.format("%20s  %s\n", dpVar, dpVarDes));
					} else {
						dpVar = line.split(" ")[2].split(";")[0]; //private String dpName;			// unlock target or 'all' to unlock all items
						dpVarDes = line.split("//")[1];
						
						contentsBuffer.append(String.format("%20s :%s\n", dpVar, dpVarDes));
						
						dpIntSb.append(dpVar + SEP_DP_VARS);
						dpIntVarsSb.append(SEP_CSV_TD + "v_" + dpVar);
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
							
							contentsBuffer.append(String.format("%-12s:  %s\n", key, value.replaceAll("<b>", "").replaceAll("</b>", "")));
							
							if (key.equalsIgnoreCase(SCRIPT_NAME)
									&& ! keywordFileName.equals(value.replaceAll("<b>", "").replaceAll("</b>", ""))) {
								logWarning("Not match Script Name for keyword [" + keywordFileName + "]");							
							}
							
							prevKey = key;
							prevKeyLineCount = lineCount;
						} else { // for continue line w/ ":"					
							if (prevKey != null 
									&& line.trim().length() > 1) // non-empty line 
							{
								Pattern keyContPattern = Pattern.compile(".*\\*\\s*(.*)");	// 
								Matcher keyContMatcher = keyContPattern.matcher(line);

								if (keyContMatcher.find()
										&& ! (line.trim().startsWith("* Generated")
												|| line.trim().startsWith("* Original Host"))) {
									if (lineCount == (prevKeyLineCount + 1)) { // continue contents of latest key
										contentsBuffer.append(String.format("%15s%s\n", " ", keyContMatcher.group(1)));
										prevKeyLineCount = lineCount;	// for more lines
									}
								} else {
									prevKey = null;	// not care line, clear saved prevKey
								}
							}
						}
					} else if (prevKey != null 
							&& line.trim().length() > 1) 
						{ // for continue line w/o ":"
						Pattern keyContPattern = Pattern.compile(".*\\*\\s*(.*)");	// 
						Matcher keyContMatcher = keyContPattern.matcher(line);

						if (keyContMatcher.find()) {
							if (lineCount == (prevKeyLineCount + 1)) { // continue contents of latest key
								contentsBuffer.append(String.format("%15s%s\n", " ", keyContMatcher.group(1)));
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

	private static void logInfo(String message) {
		if (logLvl >= 1) {
			System.out.println(message);
		}
	}

	private static void logWarning(String message) {
		System.out.println("\t\t#### " + message + " ####");
	}
}
