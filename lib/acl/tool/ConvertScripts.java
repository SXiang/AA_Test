package lib.acl.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rational.test.ft.object.interfaces.TopLevelSubitemTestObject;

public class ConvertScripts {
	private static final String DATA_DRIVEN_STUB_SECTION = "dpInitialization(args[0], args[1]);";
	private static final String SECTION_INSERT_POINT = ".*dpInitialization.*";
	private static final String DP_VAR_PREFIX = "dp";
	private static final String DP_VAR_DEC_SECTION_BEGIN = "// BEGIN of datapool variables declaration";
	private static final String DP_VAR_DEC_SECTION_END = "// END of datapool variables declaration";
	private static final String DP_VAR_SECTION_BEGIN = "// BEGIN of datapool variables";
	private static final String DP_VAR_SECTION_END = "// END of datapool variables";

	private static final String NEW_DP_VAR_PREFIX = "reqArg";

	private static final String RFT_OBJECT_VAR_PREFIX = "rftObj";
	private static final String RFT_OBJECT_VAR_DEC_SECTION_BEGIN = "// BEGIN of rft object variables declaration";
	private static final String RFT_OBJECT_VAR_DEC_SECTION_END = "// END of rft object variables declaration";
	private static final String RFT_OBJECT_VAR_SECTION_BEGIN = "// BEGIN of rft object variables";
	private static final String RFT_OBJECT_VAR_SECTION_END = "// END of rft object variables";
	//private static final String RFT_HELPER_FILE_DIR = "Y:/workspace/QA_Automation_2009V1.0/AXEM_Automation/resources/AX_GatewayPro/Tasks/";
	private static final String RFT_HELPER_FILE_DIR = "Y:/workspace/RFT_Localization/Scripts/SilverBolt_Localized/AXEM_Integration_merged/resources/AXAutomatedScripts/";

	private static boolean infoLog = true;
	private static boolean debugLog = false;

	private static Map<String,String> logKeepingHm = new HashMap<String, String>();

	private static final String paddingStr = " ==== ";
	
	public static void main(String args[])
	{
		String targetDir = "C:/temp";

		if (args.length < 1) {
			System.out.println("usage: ConvertScripts <SRC_DIR> [TARGET_DIR] [debugFlag]\n");
			return;
		} else if (args.length >= 2) {
			targetDir=args[1].toString();
			targetDir=targetDir.endsWith("/")? targetDir:targetDir+"/";
		} else if (args.length == 3) {
			debugLog = ("true".equalsIgnoreCase(args[2].toString()))? true : false;
		}

		String srcDir=args[0].toString();
		srcDir=srcDir.endsWith("/")? srcDir:srcDir+"/";

		log(debugLog, "ConvertScripts from srcDir: " + srcDir + " to targetDir: " + targetDir);

		// main functions start here
		logKeepingHm.put("No insertDataDrivenStub Change", "");
		logKeepingHm.put("No organizeRftObjVars Change", "");
		logKeepingHm.put("No organizeDpVars Change", "");
		logKeepingHm.put("ERROR: No RftObjType Found", "");

		StringBuffer newContentsBuffer;
		// loop through all java files under src directory
		for (String curJavaFile : new File(srcDir).list(new OnlyExt("java"))) {				
			log(infoLog, "\n" + paddingStr + "Loading Java file: " + curJavaFile + " ... " + paddingStr);

			// load in current java file contents into buffer
			newContentsBuffer = getFileContents(srcDir + curJavaFile);

//			// 1. insert DataDriven Stub section
//			newContentsBuffer = insertDataDrivenStub(curJavaFile, newContentsBuffer);
//
//			// 2. organize RFT Object variables			
//			newContentsBuffer = organizeRftObjVars(curJavaFile, newContentsBuffer);
//
//			// 3. organize datapool variables
//			newContentsBuffer = organizeDpVars(curJavaFile, newContentsBuffer);				

			// A. left out part, move dp declaration to class level
			//newContentsBuffer = moveDpVarsSection(curJavaFile, newContentsBuffer);
			
			// B. left out part, move rftobj definition to class level
			//newContentsBuffer = moveRftObjSection(curJavaFile, newContentsBuffer);
			
			// C. separate rftobj declaration and definition
			newContentsBuffer = sepRftObjDecAssignSection(curJavaFile, newContentsBuffer);
			
			// 4. write to new file
			String outFileName = targetDir + curJavaFile;
			log(infoLog, paddingStr + "Creating new java file: " + outFileName + " ... " + paddingStr);
			writeToNewFile(outFileName, newContentsBuffer);
			
			//searchByPattern(newContentsBuffer);
		}

		// log no change files for attention
		log(infoLog, "\n\n" + paddingStr + " !!! No Change Summary !!! " + paddingStr);
		for (Entry<String, String> entry : logKeepingHm.entrySet()) {
			log(infoLog, "\n\t" + paddingStr + entry.getKey() + " : " + entry.getValue());
		}
	}

	private static StringBuffer sepRftObjDecAssignSection(String curJavaFile, StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity());
		String[] srcStringArr = contentsBuffer.toString().split("\n");

		StringBuffer tmpBuffer = new StringBuffer();
		String searchPattern = "TestObject ";
		boolean inSection = false;
		for (String curString : srcStringArr) {
			if (curString.trim().equalsIgnoreCase(RFT_OBJECT_VAR_SECTION_BEGIN)) {
				newContentsBuffer.append("\t" + RFT_OBJECT_VAR_DEC_SECTION_BEGIN + "\n");
				tmpBuffer.append("\t\t" + RFT_OBJECT_VAR_SECTION_BEGIN + "\n");
				inSection = true;
			} else if (curString.trim().equalsIgnoreCase(RFT_OBJECT_VAR_SECTION_END)) {
				newContentsBuffer.append("\t" + RFT_OBJECT_VAR_DEC_SECTION_END + "\n");
				// copy the DP_VAR_SECTION_END line
				tmpBuffer.append("\t\t" +  RFT_OBJECT_VAR_SECTION_END + "\n\n");
				inSection = false;
			}

			// extract Current Keyword's rft Vars
			if (inSection && ! curString.trim().equalsIgnoreCase(RFT_OBJECT_VAR_SECTION_BEGIN)) {
				newContentsBuffer.append("\t" + curString.split("=")[0].trim() + ";\n");
				tmpBuffer.append("\t\t" +  curString.substring(curString.indexOf(searchPattern) + searchPattern.length()) + "\n");
			} else if (curString.trim().equalsIgnoreCase(DATA_DRIVEN_STUB_SECTION)) {
				newContentsBuffer.append(curString + "\n\n");
				newContentsBuffer.append(tmpBuffer);
			} else if (! inSection && ! curString.trim().equalsIgnoreCase(RFT_OBJECT_VAR_SECTION_END)){
				newContentsBuffer.append(curString + "\n");
			}
		}
		
		return newContentsBuffer;
	}

	private static void searchByPattern(StringBuffer contentsBuffer) {
		//Pattern curPattern = Pattern.compile("\\w+\\(\\)\\.");
		Pattern curPattern = Pattern.compile("dp\\w+\\(\"");
		
		String[] srcStringArr = contentsBuffer.toString().split("\n");

		String curFoundStr;
		// loop through all lines to find all rftObj and keep them in masterHm
		for (String curString : srcStringArr) {			
			// in current line
			Matcher fit = curPattern.matcher(curString);
			while (fit.find()) {
				curFoundStr = fit.group();
				if (curFoundStr.indexOf("dpString") == -1) {
					log(infoLog, "Found: " + curFoundStr);
				}
			}
		}
	}
	
	private static StringBuffer insertDataDrivenStub(String curJavaFile, StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity());

		String[] srcStringArr = contentsBuffer.toString().split("\n");
		String searchPattern = "public void testMain(Object[] args)";

		boolean modified = false;
		boolean found = false;
		for (String curString : srcStringArr) {
			
			if (modified && curString.trim().equalsIgnoreCase(DATA_DRIVEN_STUB_SECTION)) {
				log(infoLog, "WARNING: Duplicate [" + DATA_DRIVEN_STUB_SECTION + "]. Removed");
			} else {
				// copy old contents
				newContentsBuffer.append(curString + "\n");
			}
			

			if (curString.trim().equalsIgnoreCase(searchPattern)) {			
				found = true;
			}

			// insert DATA_DRIVEN_STUB_SECTION at function's beginning
			if (found && ! modified && curString.trim().equalsIgnoreCase("{")) {
				modified = true;
				log(infoLog, "\n\t" + paddingStr + "Insert [" + DATA_DRIVEN_STUB_SECTION + "]\n");
				newContentsBuffer.append("\t\t// Data-Driven Stub" + "\n");
				newContentsBuffer.append("\t\t" + DATA_DRIVEN_STUB_SECTION + "\n\n");
			}
		}

		if (! modified) {
			logKeepingHm.put("No insertDataDrivenStub Change", logKeepingHm.get("No insertDataDrivenStub Change") + "\n" + curJavaFile);
		}

		return newContentsBuffer;
	}
	
	private static StringBuffer moveDpVarsSection(String curJavaFile, StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity());
		String[] srcStringArr = contentsBuffer.toString().split("\n");

		StringBuffer tmpBuffer = new StringBuffer();
		boolean inSection = false;
		for (String curString : srcStringArr) {	
			if (curString.trim().equalsIgnoreCase(DP_VAR_SECTION_BEGIN)) {
				tmpBuffer.append("\n\t" + DP_VAR_DEC_SECTION_BEGIN + "\n");
				inSection = true;
			} else if (curString.trim().equalsIgnoreCase(DP_VAR_SECTION_END)) {
				// copy the DP_VAR_SECTION_END line
				tmpBuffer.append("\t" +  DP_VAR_DEC_SECTION_END + "\n\n");
				inSection = false;
			}

			// extract Current Keyword's Dp Vars
			if (inSection && ! curString.trim().equalsIgnoreCase(DP_VAR_SECTION_BEGIN)) {
				// copy to tmpBuffer
				tmpBuffer.append("\tprivate " + curString.split("=")[0].trim() + ";\n");
				curString = curString.replaceFirst("String ", "");
			}
			
			newContentsBuffer.append(curString + "\n");
		}

		// found the beginning of class and insert the dp declare part.
		StringBuffer newContentsBuffer2 = new StringBuffer(newContentsBuffer.capacity());
		String searchPattern = "public void testMain(Object[] args)";
		srcStringArr = newContentsBuffer.toString().split("\n");
		boolean done = false;
		for (String curString : srcStringArr) {
			if (!done && curString.trim().equalsIgnoreCase(searchPattern) ) {
				newContentsBuffer2.append(tmpBuffer);
				done = true;
			}
			
			newContentsBuffer2.append(curString + "\n");			
		}
		
		return newContentsBuffer2;
	}
	
	private static StringBuffer moveRftObjSection(String curJavaFile, StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity());
		String[] srcStringArr = contentsBuffer.toString().split("\n");

		StringBuffer tmpBuffer = new StringBuffer();
		boolean inSection = false;
		for (String curString : srcStringArr) {
			if (curString.trim().equalsIgnoreCase(RFT_OBJECT_VAR_SECTION_BEGIN)) {
				tmpBuffer.append("\t" + RFT_OBJECT_VAR_SECTION_BEGIN + "\n");
				inSection = true;
			} else if (curString.trim().equalsIgnoreCase(RFT_OBJECT_VAR_SECTION_END)) {
				// copy the DP_VAR_SECTION_END line
				tmpBuffer.append("\t" +  RFT_OBJECT_VAR_SECTION_END + "\n\n");
				inSection = false;
			}

			// extract Current Keyword's Dp Vars
			if (inSection && ! curString.trim().equalsIgnoreCase(RFT_OBJECT_VAR_SECTION_BEGIN)) {
				// copy to tmpBuffer
				tmpBuffer.append("\tprivate " + curString.replaceFirst("\t\t", "") + "\n");
			}
			
			if (! inSection && ! curString.trim().equalsIgnoreCase(RFT_OBJECT_VAR_SECTION_END)) {
				// only copy non RFT_OBJECT_VAR_SECTION
				newContentsBuffer.append(curString + "\n");
			}
		}

		// found the beginning of class and insert the rftobj section.
		StringBuffer newContentsBuffer2 = new StringBuffer(newContentsBuffer.capacity());
		String searchPattern = "public void testMain(Object[] args)";
		srcStringArr = newContentsBuffer.toString().split("\n");
		boolean done = false;
		for (String curString : srcStringArr) {
			if (!done && curString.trim().equalsIgnoreCase(searchPattern) ) {
				newContentsBuffer2.append(tmpBuffer);
				done = true;
			}
			
			newContentsBuffer2.append(curString + "\n");			
		}
		
		return newContentsBuffer2;
	}
	// find all RFT object references to define them at the beginning of each function
	// And replace each reference with the new RFT Object variable
	private static StringBuffer organizeRftObjVars(String curJavaFile, StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity());
		Pattern rftObj = Pattern.compile("\\w+\\(\\)\\.");

		String[] srcStringArr = contentsBuffer.toString().split("\n");

		Map<String,String> masterHm = new TreeMap<String, String>();
		boolean modified = false;

		String curRftObjStr;
		String curRftObjType;
		// loop through all lines to find all rftObj and keep them in masterHm
		for (String curString : srcStringArr) {			
			// in current line
			Matcher fit = rftObj.matcher(curString);
			while (fit.find()) {
				modified = true;

				curRftObjStr = fit.group();
				if ( !curRftObjStr.equals("toLowerCase().") && !curRftObjStr.equals("getScreenTestObject().")
						&& !curRftObjStr.equals("toString().") && !curRftObjStr.equals("getNode().")
						&& !curRftObjStr.equals("getDefault().") && !curRftObjStr.equals("getProperties().")) {
					curRftObjStr = curRftObjStr.substring(0, curRftObjStr.length()-1);
					
					curRftObjType = getRftObjType(curRftObjStr, curJavaFile);
					
					if (curRftObjType != null) {
						masterHm.put(curRftObjStr, curRftObjType);
						log(debugLog, "\tFound: curVar: [" + curRftObjStr + "];\ttype: " + curRftObjType);
					} else {
						log(infoLog, "ERROR! Could not find RftObjType for " + curJavaFile + "'s [" + curRftObjStr + "]! NO definition && replacement done. Check manually...");
						logKeepingHm.put("ERROR: No RftObjType Found", logKeepingHm.get("ERROR: No RftObjType Found") + "\n" + curJavaFile + "'s " + curRftObjStr);
					}
				}
			}
		}

		if (modified) {
			String rftObjType;
			String rftObjVarName;
			String rftObjVarValue;
			for (String curString : srcStringArr) {
				if (curString.matches(SECTION_INSERT_POINT)) {
					// insert RFT Object variables section
					newContentsBuffer.append(curString + "\n");
					newContentsBuffer.append("\n");

					newContentsBuffer.append("\t\t" + RFT_OBJECT_VAR_SECTION_BEGIN + "\n");

					log(infoLog, "\n\t" + paddingStr + "Insert [RFT_OBJECT_VAR_SECTION]:");
					log(debugLog, "\t" + RFT_OBJECT_VAR_SECTION_BEGIN );
					for (Entry<String, String> entry : masterHm.entrySet()) {
						rftObjType = entry.getValue();
						rftObjVarValue = entry.getKey();
						rftObjVarName = rftObjVarValue.substring(0, rftObjVarValue.length() - 2);
						rftObjVarName = RFT_OBJECT_VAR_PREFIX + upperCase1stLowerLeftLetters(rftObjVarName);
						newContentsBuffer.append("\t\t" + rftObjType + " " + rftObjVarName + " = " + rftObjVarValue + ";\n");
						log(debugLog, "\t" + rftObjType + " " + rftObjVarName + " = " + rftObjVarValue + ";" );
					}
					log(debugLog, "\t" + RFT_OBJECT_VAR_SECTION_END + "\n");

					newContentsBuffer.append("\t\t" + RFT_OBJECT_VAR_SECTION_END + "\n");
				} else {
					// replace searchPatternStart references in current line
					Matcher fit = rftObj.matcher(curString);
					while (fit.find()) {
						for (Entry<String, String> entry : masterHm.entrySet()) {
							rftObjVarValue = entry.getKey();
							rftObjVarName = rftObjVarValue.substring(0, rftObjVarValue.length() - 2);
							rftObjVarName = RFT_OBJECT_VAR_PREFIX + upperCase1stLowerLeftLetters(rftObjVarName);
							curString = curString.replace(rftObjVarValue, rftObjVarName);
							log(debugLog, "\tReplacing " + rftObjVarValue + " with " + rftObjVarName);
						}
					}

					newContentsBuffer.append(curString + "\n");
				}
			}	
		} else {
			// no change needed
			logKeepingHm.put("No organizeRftObjVars Change", logKeepingHm.get("No organizeRftObjVars Change") + "\n" + curJavaFile);
			newContentsBuffer = contentsBuffer;
		}

		return newContentsBuffer;
	}

	// getRftObjType from helper file, need to set RFT_HELPER_FILE_DIR correctly
	private static String getRftObjType(String curRftObjStr, String curJavaFile) {
		String helperFileName = curJavaFile.split("\\.")[0] + "Helper" + ".java";
		StringBuffer contentsBuffer = getFileContents(RFT_HELPER_FILE_DIR + helperFileName);

		String curRftObjType = null;
		String[] srcStringArr = contentsBuffer.toString().split("\n");		
		for (String curString : srcStringArr) {			
			if (curString.indexOf(curRftObjStr) != -1) {
				curRftObjType = curString.split(" ")[1];
				break;
			}
		}

		return curRftObjType;
	}

	// find all dpString("datapool variables") to define them at the beginning of each function
	// And replace each reference with the new datapool variable
	private static StringBuffer organizeDpVars(String curJavaFile, StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity());

		String searchPatternStart = "dpString(\"";
		String searchPatternEnd = "\")";

		String tagetString;
		String targetDpVariable;

		String[] srcStringArr = contentsBuffer.toString().split("\n");

		Map<String,String> masterHm = new TreeMap<String, String>();

		int start;
		int end;
		boolean modified = false;

		// loop through all lines to find all searchPatternStart and keep them in masterHm
		for (String curString : srcStringArr) {			
			// in current line
			while (curString.indexOf(searchPatternStart) != -1) {
				modified = true;

				start = curString.indexOf(searchPatternStart);
				end = curString.indexOf(searchPatternEnd, start);

				tagetString = curString.substring(start, end + searchPatternEnd.length());

				targetDpVariable = curString.substring(start + searchPatternStart.length(), end);
				targetDpVariable = formatDpVariable(targetDpVariable);

				masterHm.put(targetDpVariable, tagetString);

				// search the left part of curString
				curString = curString.substring(end);
			}
		}

		if (modified) {
			for (String curString : srcStringArr) {
				if (curString.matches(SECTION_INSERT_POINT)) {
					// insert datapool variables section
					newContentsBuffer.append(curString + "\n");
					newContentsBuffer.append("\n");
					newContentsBuffer.append("\t\t" + DP_VAR_SECTION_BEGIN + "\n");

					log(infoLog, "\n\t\t" + paddingStr + "Insert [DP_VAR_SECTION]:");
					log(debugLog, "\t\t" + DP_VAR_SECTION_BEGIN );
					String tmpStr;
					int index=0;
					for (Entry<String, String> entry : masterHm.entrySet()) {
						tmpStr = "\t\tString " + entry.getKey() + " = " + searchPatternStart + NEW_DP_VAR_PREFIX + index + searchPatternEnd + ";";
						tmpStr += "\t//\t<==" + entry.getValue().trim() + "\n"; 
						newContentsBuffer.append(tmpStr);
						log(debugLog, tmpStr);
						index++;
					}

					log(debugLog, "\t\t" + DP_VAR_SECTION_END + "\n");

					newContentsBuffer.append("\t\t" + DP_VAR_SECTION_END + "\n");
				} else {
					// replace searchPatternStart references in current line
					if (curString.indexOf(searchPatternStart) != -1) {
						for (Entry<String, String> entry : masterHm.entrySet()) {
							curString = curString.replace(entry.getValue(), entry.getKey());
							log(debugLog, "\tReplacing " + entry.getValue() + " with " + entry.getKey());
						}
					}

					newContentsBuffer.append(curString + "\n");
				}
			}	
		} else {
			// no change needed
			logKeepingHm.put("No organizeDpVars Change", logKeepingHm.get("No organizeDpVars Change") + "\n" + curJavaFile);
			newContentsBuffer = contentsBuffer;
		}

		return newContentsBuffer;
	}


	// change datapool variable to obey java variable convention as firstLetterThenSecond
	// and remove " " and "_"
	private static String formatDpVariable(String orgDpVariable) {
		String[] tmpStringArr;
		StringBuffer buffer=null;

		String[] patternArr = {"_", " "};

		for (String curPattern : patternArr) {
			if (orgDpVariable.indexOf(curPattern) != -1) {
				buffer = new StringBuffer();
				tmpStringArr = orgDpVariable.split(curPattern);
				for (String tmpStr : tmpStringArr) {
					buffer.append(upperCase1stLowerLeftLetters(tmpStr));
				}
			} else {
				buffer = new StringBuffer(orgDpVariable);
			}

			orgDpVariable = buffer.toString();
		}

		// lowerCase the First Letter
		String targetDpVariable = DP_VAR_PREFIX + orgDpVariable;

		return targetDpVariable;
	}

	private static String upperCase1stLowerLeftLetters(String orgString) {
		String targetString = orgString.substring(0,1).toUpperCase() + orgString.substring(1).toLowerCase();
		return targetString;
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
				contentsBuffer.append(line + "\n");
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

class OnlyExt implements FilenameFilter {
	String ext;
	public OnlyExt(String ext) {
		this.ext = "." + ext;
	}
	public boolean accept(File dir, String name) {
		return name.endsWith(ext);
	}
}
