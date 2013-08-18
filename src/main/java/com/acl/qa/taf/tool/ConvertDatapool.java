package com.acl.qa.taf.tool;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Run after ConvertScripts to generate new datapool based on new code template for datapool variables
public class ConvertDatapool {
	private static final String LINE_SEP = "\n";
	private static final String CSV_SEP = ",";
	private static final String PARAMETER_SEP = "; ";
	private static final String NEW_DP_VAR_PREFIX = "reqArg";
	private static final String DP_VAR_SECTION_BEGIN = "// BEGIN of datapool variables";
	private static final String DP_VAR_SECTION_END = "// END of datapool variables";

	private static boolean infoLog = true;
	private static boolean debugLog = true;

	private static String[] dpColumnDefArr;
	private static String keywordSrcDir;
	
	private static String paddingStr = " ==== ";

	public static void main(String args[])
	{
		String targetDir = "C:/temp";

		if (args.length < 2) {
			System.out.println("usage: ConvertDatapool <SRC_DIR> <KEYWORD_SRC_DIR> [TARGET_DIR] [debugFlag]\n");
			return;
		} else if (args.length >= 3) {
			targetDir=args[2].toString();
			targetDir=targetDir.endsWith("/")? targetDir:targetDir+"/";
		} else if (args.length == 4) {
			debugLog = ("true".equalsIgnoreCase(args[3].toString()))? true : false;
		}

		String srcDir=args[0].toString();
		srcDir=srcDir.endsWith("/")? srcDir:srcDir+"/";

		keywordSrcDir=args[1].toString();
		keywordSrcDir=keywordSrcDir.endsWith("/")? keywordSrcDir:keywordSrcDir+"/";

		log(debugLog, "ConvertDatapool from srcDir: " + srcDir + " to targetDir: " + targetDir);

		// main functions start here
		// loop through all java files under src directory
		for (String curCsvFile : new File(srcDir).list(new OnlyExt("csv"))) {				
			log(infoLog, "\n" + paddingStr + "Loading csv file: " + curCsvFile + " ... " + paddingStr);

			// load in current java file contents into buffer
			StringBuffer contentsBuffer = getFileContents(srcDir + curCsvFile);

			dpColumnDefArr = getDpColumnDef(curCsvFile, contentsBuffer);

			// loop through the current datapool's keywords
			StringBuffer newContentsBuffer = createNewDpContents(curCsvFile, contentsBuffer);

			// write to new csv file
			String outFileName = targetDir + curCsvFile;
			log(infoLog, paddingStr + "Creating new csv file: " + outFileName + " ... " + paddingStr);
			writeToNewFile(outFileName, newContentsBuffer);
		}
	}
	private static StringBuffer createNewDpContents(String curCsvFile, StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity());

		String[] srcStringArr = contentsBuffer.toString().split(LINE_SEP);

		int maxVarsCount = 0;
		String[] curLineArr;
		String curKeyword;
		String curKeywordReqDpVars;
		String[] curKeywordReqDpVarsArr;
		int keywordIndex = 0;
		
		// skip 1st Column Definition line
		for (int i=1; i<srcStringArr.length; i++) {
			log(debugLog, "\n\tCurrent Keyword Line: " + i + ": " + srcStringArr[i]);

			curLineArr = srcStringArr[i].split(CSV_SEP);
			curKeyword = curLineArr[0];
			
			// save curKeyword
			newContentsBuffer.append(curKeyword);

			// get Current Keyword's Required Datapool Variables from keyword java file
			curKeywordReqDpVars = getCurKeywordReqDpVars(curKeyword);
			
			if ("".equalsIgnoreCase(curKeywordReqDpVars)) {
				// no datapool variable needed
				log(debugLog, "FYI: No datapool variable needed for " + curKeyword);
				newContentsBuffer.append(CSV_SEP + "()" + LINE_SEP);
				continue;
			}
			
			// remove last PARAMETER_SEP
			curKeywordReqDpVars = curKeywordReqDpVars.substring(0, curKeywordReqDpVars.length() - PARAMETER_SEP.length());

			// save curKeyword's Required Dp Vars format
			newContentsBuffer.append(CSV_SEP + "(" + curKeywordReqDpVars + ")");

			curKeywordReqDpVarsArr = curKeywordReqDpVars.split(PARAMETER_SEP);
			
			// keep the max of dp variables 
			maxVarsCount = (curKeywordReqDpVarsArr.length > maxVarsCount)? curKeywordReqDpVarsArr.length:maxVarsCount;

			// loop to set current Keyword's Required Dp Vars's value from old datapool		
			for (int j=0; j<curKeywordReqDpVarsArr.length; j++) {
				String curReqDpVar = curKeywordReqDpVarsArr[j];

				// get the curReqDpVar's position in old datapool
				for (int k=0; k<dpColumnDefArr.length; k++) {
					if (curReqDpVar.equalsIgnoreCase(dpColumnDefArr[k])) {
						keywordIndex = k;
						break;
					}
				}

				String curArgValue = "";
				if (keywordIndex == 0) {
					log(infoLog, "ERROR: MISSING required argument column: " + curReqDpVar + " in old datapool; Set to empty string.");
				} else if (keywordIndex >= curLineArr.length) {
					log(infoLog, "WARNING: No value setting for required argument: " + curReqDpVar + " in old datapool; Set to empty string.");
				} else {
					curArgValue = curLineArr[keywordIndex];
					log(debugLog, "\t\tColumn [" + keywordIndex + "]; Name: [" + dpColumnDefArr[keywordIndex] + "]; Value: [" + curArgValue + "]");
				}

				// save the args value setting
				newContentsBuffer.append(CSV_SEP + curArgValue);
			}

			newContentsBuffer.append(LINE_SEP);
		}
		
		// construct the new datapool 1st line based on maxVarsCount
		String newColumnDefLine = "Keyword[][STRING]" + CSV_SEP + "RequiredArgs[][STRING]";
		for (int i=0; i<maxVarsCount; i++) {
			newColumnDefLine += CSV_SEP + NEW_DP_VAR_PREFIX + i + "[][STRING]";
		}
		
		// insert the 1st Column Definition line
		log(debugLog, "\t" + paddingStr + "newColumnDefLine: [" + newColumnDefLine + "]");
		newContentsBuffer.insert(0, newColumnDefLine + "\n");

		return newContentsBuffer;
	}
	
	// get Current Keyword's Required Datapool Variables from keyword java file's DP_VAR_SECTION_BEGIN-DP_VAR_SECTION_END section's comments
	// 		e.g.
	// 		// BEGIN of datapool variables
	//		String dpRole = getDpString("reqArg0");	//	<==dpString("Role")
	//		String dpUsers = getDpString("reqArg1");	//	<==dpString("Users")
	// 		END of datapool variables
	
	private static String getCurKeywordReqDpVars(String curKeyword) {
		StringBuffer contentsBuffer = getFileContents(keywordSrcDir + curKeyword + ".java");
		String[] srcStringArr = contentsBuffer.toString().split(LINE_SEP);

		boolean found = false;
		StringBuffer buffer = new StringBuffer();

		for (String curString : srcStringArr) {	
			if (curString.trim().equalsIgnoreCase(DP_VAR_SECTION_BEGIN)) {
				found = true;
				continue;
			} else if (curString.trim().equalsIgnoreCase(DP_VAR_SECTION_END)) {
				// done
				break;
			}

			// extract Current Keyword's Dp Vars
			if (found) {
				buffer.append(curString.split("<==")[1].split("\"")[1] + PARAMETER_SEP);
			}
		}

		return buffer.toString();
	}

	// get 1st line of datapool
	private static String[] getDpColumnDef(String curCsvFile, StringBuffer contentsBuffer) {
		String[] srcStringArr = contentsBuffer.toString().split(LINE_SEP)[0].split(CSV_SEP);

		log(debugLog, "\tDatapool Column Definition of " + curCsvFile + ":");

		for (int i=0; i<srcStringArr.length; i++) {
			srcStringArr[i] = srcStringArr[i].split("\\[\\]")[0];
			log(debugLog, "\t\tDpColumnDef " + i + ": [" + srcStringArr[i] + "]");
		}

		return srcStringArr;
	}
	private static StringBuffer getFileContents(String fileName) {
		StringBuffer contentsBuffer = new StringBuffer(1024);
		try {
			FileReader freader;
			LineNumberReader lnreader;
			freader = new FileReader(new File(fileName));
			lnreader = new LineNumberReader(freader);

			String line;
			while ((line = lnreader.readLine()) != null){
				contentsBuffer.append(line + LINE_SEP);
			}

			freader.close();
			lnreader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return contentsBuffer;
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

	private static void log (boolean logFlag, String message) {
		if (logFlag) {
			System.out.println(message);
		}
	}
}
