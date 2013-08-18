package com.acl.qa.taf.tool;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.acl.qa.taf.util.FileUtil;
import com.acl.qa.taf.util.NLSUtil;

// Run after ConvertScripts to generate new datapool based on new code template for datapool variables
public class Convert2NewDpI {
	private static final String LINE_SEP = "\n";
	private static final String CSV_SEP = ",";

	private static boolean infoLog = true;
	private static boolean debugLog = true;
	private static String paddingStr = "===";

	private static final String SEP_ITEM_VALUE_FIELDS = ":";
	private static final String SEP_ITEMS = "|";
	private static final String SPLITTER_ITEMS = "\\|";

	private static boolean newContents = false;

	public static void main(String args[])
	{
		String targetDir = "C:/temp/";

		if (args.length < 1) {
			System.out.println("usage: Convert2NewDpI <SRC_DIR> [TARGET_DIR] [debugFlag]\n");
			return;
		} else if (args.length >= 2) {
			targetDir=args[1].toString();
			targetDir=targetDir.endsWith("/")? targetDir:targetDir+"/";
		} else if (args.length == 3) {
			debugLog = ("true".equalsIgnoreCase(args[2].toString()))? true : false;
		}

		String srcDir=args[0].toString();
		srcDir=srcDir.endsWith("/")? srcDir:srcDir+"/";

		log(debugLog, "ConvertDatapool from srcDir: " + srcDir + " to targetDir: " + targetDir);

		// main functions start here
		for (String curCsvFile : new File(srcDir).list(new OnlyExt("csv"))) {		
			log(infoLog, "\n" + paddingStr + "Loading csv file: " + curCsvFile + " ... " + paddingStr);

			// load in current java file contents into buffer
			StringBuffer contentsBuffer = getFileContents(srcDir + curCsvFile);

			newContents = false;

			//			String keyword = "imports";
			//			String newReqArgs = "(dpImportToPath; dpImportSrcFolder; dpImportFiles; dpImportOverwrite; dpImportSourceDataFiles; dpOverwriteLinkedTables; dpExpectedErr; dpOperationType)";
			//			String oldReqArgs = "(dpImportToPath; dpImportSrcFolder; dpImportFiles; dpImportOverwrite; dpImportSourceDataFiles; dpExpectedErr; dpOperationType)";
			//
			//			StringBuffer newContentsBuffer = getNewFileContents_imports(keyword, newReqArgs, oldReqArgs, contentsBuffer);

			//contentsBuffer = getNewFileContents_propertiesPermission(contentsBuffer);
			//contentsBuffer = getNewFileContents_verifySummary(contentsBuffer);
			//contentsBuffer = getNewFileContents_verifySummaryFixBug(contentsBuffer);

			//contentsBuffer = getNewFileContents_permissionsFixBug(contentsBuffer);
			contentsBuffer = getNewFileContents_permissionsReformatUsername(contentsBuffer);
			
			if (newContents) {
				// write to new csv file
				String outFileName = targetDir + curCsvFile;
				log(infoLog, paddingStr + "Creating new csv file: " + outFileName + " ... " + paddingStr);
				writeToNewFile(outFileName, contentsBuffer);
			} else {
				log(infoLog, paddingStr + "No change for curCsvFile: [" + curCsvFile + "]");
			}
		}
	}

	// remove wrong append string introduced by not re-initialised StringBuilder of old getNewFileContents_propertiesPermission
	private static StringBuffer getNewFileContents_permissionsReformatUsername(StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity() + 100);

		String[] srcStringArr = contentsBuffer.toString().split(LINE_SEP);

		String curLine;
		String curNewLine;
		String[] curLineArr;
		String curKeyword;

		String keyword = "permissions";
		//String newReqArgs = "(dpOperation; dpItemPath; dpUsersPerms; dpSaveOrNot)";

		String curPerm;
		String curUser;
		String curNewUser;
		String dpSaveOrNot;
		for (int i=0; i<srcStringArr.length; i++) {
			curNewLine = null;
			curLine = srcStringArr[i];

			curLineArr = curLine.split(CSV_SEP);
			
			if (curLineArr.length==0) {
				log(debugLog, "Wierd line [" + curLine + "]");
				newContentsBuffer.append(LINE_SEP);
				continue;
			}
			
			curKeyword = curLineArr[0];

			if (curKeyword.equalsIgnoreCase(keyword)) {
				newContents = true;
				
				curPerm = curLineArr[4];
				
				Set<String> permSet = new HashSet<String>();
				
				for (String perm : curPerm.split(SPLITTER_ITEMS)) {
					curUser = perm.split(SEP_ITEM_VALUE_FIELDS)[0];
					curNewUser = curUser;
					
					if (curUser.indexOf("(") != -1) {
						curNewUser = curUser.substring(curUser.indexOf("(") + 1, curUser.indexOf(")"));
					}
					
					perm = perm.replace(curUser, curNewUser);
					
					permSet.add(perm);
				}
				
				dpSaveOrNot = (curLineArr.length >= 6)? curLineArr[5] : "Yes";
				
				curNewLine = curLineArr[0] + CSV_SEP + curLineArr[1] + CSV_SEP 
				+ curLineArr[2] + CSV_SEP + curLineArr[3] + CSV_SEP
				+ joinSetValues(permSet, SEP_ITEMS)	+ CSV_SEP + dpSaveOrNot + CSV_SEP + LINE_SEP;

				newContentsBuffer.append(curNewLine);

				log(debugLog, "old: [" + curLine + "]");
				log(debugLog, "## new: [" + curNewLine + "]");
			} else {
				newContentsBuffer.append(curLine + LINE_SEP);
			}
		}

		return newContentsBuffer;
	}
	
	// remove wrong append string introduced by not re-initialised StringBuilder of old getNewFileContents_propertiesPermission
	private static StringBuffer getNewFileContents_permissionsFixBug(StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity() + 100);

		String[] srcStringArr = contentsBuffer.toString().split(LINE_SEP);

		String curLine;
		String curNewLine;
		String[] curLineArr;
		String curKeyword;

		String keyword = "permissions";
		//String newReqArgs = "(dpOperation; dpItemPath; dpUsersPerms; dpSaveOrNot)";

		String curPerm;
		for (int i=0; i<srcStringArr.length; i++) {
			curNewLine = null;
			curLine = srcStringArr[i];

			curLineArr = curLine.split(CSV_SEP);
			
			if (curLineArr.length==0) {
				log(debugLog, "Wierd line [" + curLine + "]");
				newContentsBuffer.append(LINE_SEP);
				continue;
			}
			
			curKeyword = curLineArr[0];

			if (curKeyword.equalsIgnoreCase(keyword)) {
				newContents = true;
				
				curPerm = curLineArr[4];
				
				Set<String> permSet = new HashSet<String>();
				
				for (String perm : curPerm.split(SPLITTER_ITEMS)) {
					permSet.add(perm);
				}
				
				curNewLine = curLineArr[0] + CSV_SEP + curLineArr[1] + CSV_SEP 
				+ curLineArr[2] + CSV_SEP + curLineArr[3] + CSV_SEP
				+ joinSetValues(permSet, SEP_ITEMS)	+ CSV_SEP + curLineArr[5] + CSV_SEP + LINE_SEP;

				newContentsBuffer.append(curNewLine);

				log(debugLog, "old: [" + curLine + "]");
				log(debugLog, "## new: [" + curNewLine + "]");
			} else {
				newContentsBuffer.append(curLine + LINE_SEP);
			}
		}

		return newContentsBuffer;
	}
	
	private static StringBuffer getNewFileContents_propertiesPermission(StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity() + 100);

		String[] srcStringArr = contentsBuffer.toString().split(LINE_SEP);

		String curLine;
		String curNewLine;
		String[] curLineArr;
		String curKeyword;

		String keyword = "propertiesPermission";
		String newKeyword = "permissions";
		String newReqArgs = "(dpOperation; dpItemPath; dpUsersPerms; dpSaveOrNot)";
		//String oldReqArgs = "(dpOperation; dpItemPath; dpTargetUsers; dpRoles; dpPermissions; dpUsersInList; dpSaveOrNot)";

		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<srcStringArr.length; i++) {
			curNewLine = null;
			curLine = srcStringArr[i];
			//log(debugLog, "\n\tCurrent Keyword Line: " + i + ": " + curLine);

			curLineArr = curLine.split(CSV_SEP);
			
			if (curLineArr.length==0) {
				log(debugLog, "Wierd line [" + curLine + "]");
				newContentsBuffer.append(LINE_SEP);
				continue;
			}
			
			curKeyword = curLineArr[0];

			if (curKeyword.equalsIgnoreCase(keyword)) {
				newContents = true;

				curNewLine = newKeyword + CSV_SEP + newReqArgs + CSV_SEP 
						+ curLineArr[2] + CSV_SEP + curLineArr[3] + CSV_SEP;
				
				if (curLineArr[4].indexOf(SEP_ITEMS) != -1) { // multiple users
					String[] usersArr = curLineArr[4].split(SPLITTER_ITEMS);

					String[] rolesArr = curLineArr[5].split(SPLITTER_ITEMS);
					String[] permissionsArr = curLineArr[6].split(SPLITTER_ITEMS);
					String[] usersInListArr = curLineArr[7].split(SPLITTER_ITEMS);

					for (int j=0; j<usersArr.length; j++) {
						sb.append(usersArr[j] + SEP_ITEM_VALUE_FIELDS);
						
						sb.append((rolesArr.length>j && ! rolesArr[j].equals("")? rolesArr[j] : "user") + SEP_ITEM_VALUE_FIELDS);
						sb.append((permissionsArr.length>j && ! permissionsArr[j].equals("")? permissionsArr[j] : "read") + SEP_ITEM_VALUE_FIELDS);
						sb.append((usersInListArr.length>j && ! usersInListArr[j].equals("")? usersInListArr[j] : "yes"));
						
						sb.append(SEP_ITEMS);
					}
					
					curNewLine += chopLastStr(sb.toString(), SEP_ITEMS) + CSV_SEP;					
				} else {
					curLineArr[5] = curLineArr[5].equals("")? "user" : curLineArr[5];
					curLineArr[6] = curLineArr[6].equals("")? "read" : curLineArr[6];
					curLineArr[7] = curLineArr[7].equals("")? "yes" : curLineArr[7];

					curNewLine += curLineArr[4] + SEP_ITEM_VALUE_FIELDS + curLineArr[5] + SEP_ITEM_VALUE_FIELDS	
					+ curLineArr[6] + SEP_ITEM_VALUE_FIELDS	+ curLineArr[7] + CSV_SEP;
				}			

				curNewLine += curLineArr[8] + CSV_SEP + LINE_SEP;

				newContentsBuffer.append(curNewLine);

				log(debugLog, "old: [" + curLine + "]");
				log(debugLog, "## new: [" + curNewLine + "]");

			} else {
				newContentsBuffer.append(curLine + LINE_SEP);
			}
		}

		return newContentsBuffer;
	}

	// remove wrong append string introduced by not re-initialised StringBuilder of old getNewFileContents_verifySummary
	private static StringBuffer getNewFileContents_verifySummaryFixBug(StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity() + 100);

		String[] srcStringArr = contentsBuffer.toString().split(LINE_SEP);

		String curLine;
		String curNewLine;
		String[] curLineArr;
		String curKeyword;

		String keyword = "verifySummary";
		String newReqArgs = "(dpItemPath; dpItemName; dpPropsKeyValue)";
		//String oldReqArgs = "(dpItemPath; dpItemName; dpPropKeys; dpPropValues)";

		String prevKeysValues = null;
		int substrStart;
		
		for (int i=0; i<srcStringArr.length; i++) {
			curNewLine = null;
			curLine = srcStringArr[i];
			//log(debugLog, "\n\tCurrent Keyword Line: " + i + ": " + curLine);

			curLineArr = curLine.split(CSV_SEP);
			
			if (curLineArr.length==0) {
				log(debugLog, "Wierd line [" + curLine + "]");
				newContentsBuffer.append(LINE_SEP);
				continue;
			}
			
			curKeyword = curLineArr[0];

			if (curKeyword.equalsIgnoreCase(keyword)) {
				newContents = true;
				
				substrStart = prevKeysValues == null? 0 : (prevKeysValues.length() + 1);
				
				curNewLine = keyword + CSV_SEP + newReqArgs + CSV_SEP 
				+ curLineArr[2] + CSV_SEP + curLineArr[3] + CSV_SEP 
				+ curLineArr[4].substring(substrStart)	+ CSV_SEP + LINE_SEP;

				newContentsBuffer.append(curNewLine);

				log(debugLog, "old: [" + curLine + "]");
				log(debugLog, "## new: [" + curNewLine + "]");
				
				prevKeysValues = curLineArr[4];
			} else {
				newContentsBuffer.append(curLine + LINE_SEP);
			}
		}

		return newContentsBuffer;
	}
	
	private static StringBuffer getNewFileContents_verifySummary(StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity() + 100);

		String[] srcStringArr = contentsBuffer.toString().split(LINE_SEP);

		String curLine;
		String curNewLine;
		String[] curLineArr;
		String curKeyword;

		String keyword = "verifySummary";
		String newReqArgs = "(dpItemPath; dpItemName; dpPropsKeyValue)";
		//String oldReqArgs = "(dpItemPath; dpItemName; dpPropKeys; dpPropValues)";

		for (int i=0; i<srcStringArr.length; i++) {
			curNewLine = null;
			curLine = srcStringArr[i];
			//log(debugLog, "\n\tCurrent Keyword Line: " + i + ": " + curLine);

			curLineArr = curLine.split(CSV_SEP);
			
			if (curLineArr.length==0) {
				log(debugLog, "Wierd line [" + curLine + "]");
				newContentsBuffer.append(LINE_SEP);
				continue;
			}
			
			curKeyword = curLineArr[0];

			if (curKeyword.equalsIgnoreCase(keyword)) {
				newContents = true;

				String[] keysArr = curLineArr[4].split(SPLITTER_ITEMS);
				String[] valuesArr = curLineArr[5].split(SPLITTER_ITEMS);

				StringBuilder sb = new StringBuilder();
				
				for (int j=0; j<keysArr.length; j++) {
					sb.append(keysArr[j] + SEP_ITEM_VALUE_FIELDS + valuesArr[j] + SEP_ITEMS);
				}
				
				curNewLine = keyword + CSV_SEP + newReqArgs + CSV_SEP 
				+ curLineArr[2] + CSV_SEP + curLineArr[3] + CSV_SEP 
				+ chopLastStr(sb.toString(), SEP_ITEMS)	+ CSV_SEP + LINE_SEP;

				newContentsBuffer.append(curNewLine);

				log(debugLog, "old: [" + curLine + "]");
				log(debugLog, "## new: [" + curNewLine + "]");

			} else {
				newContentsBuffer.append(curLine + LINE_SEP);
			}
		}

		return newContentsBuffer;
	}

	@SuppressWarnings("unused")
	private static StringBuffer getNewFileContents_imports(String keyword,
			String newReqArgs, String oldReqArgs, StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(contentsBuffer.capacity() + 100);

		String[] srcStringArr = contentsBuffer.toString().split(LINE_SEP);

		String curLine;
		String curNewLine;
		String[] curLineArr;
		String curKeyword;

		for (int i=0; i<srcStringArr.length; i++) {
			curNewLine = null;
			curLine = srcStringArr[i];
			//log(debugLog, "\n\tCurrent Keyword Line: " + i + ": " + curLine);

			curLineArr = curLine.split(CSV_SEP);
			curKeyword = curLineArr[0];

			if (curKeyword.equalsIgnoreCase(keyword)) {
				newContents = true;

				//String newReqArgs = "(dpImportToPath; dpImportSrcFolder; dpImportFiles; dpImportOverwrite; dpImportSourceDataFiles; dpOverwriteLinkedTables; dpExpectedErr; dpOperationType)";
				//String oldReqArgs = "(dpImportToPath; dpImportSrcFolder; dpImportFiles; dpImportOverwrite; dpImportSourceDataFiles; dpExpectedErr; dpOperationType)";
				String dpOverwriteLinkedTables = "NO";

				curNewLine = curKeyword + CSV_SEP + newReqArgs + CSV_SEP 
				+ curLineArr[2] + CSV_SEP + curLineArr[3] + CSV_SEP + curLineArr[4] + CSV_SEP + curLineArr[5] + CSV_SEP 
				+ curLineArr[6] + CSV_SEP + dpOverwriteLinkedTables + CSV_SEP + curLineArr[7] + CSV_SEP + curLineArr[8] + CSV_SEP + LINE_SEP;

				newContentsBuffer.append(curNewLine);

				log(debugLog, "old: [" + curLine + "]");
				log(debugLog, "## new: [" + curNewLine + "]");

			} else {
				newContentsBuffer.append(curLine + LINE_SEP);
			}
		}

		return newContentsBuffer;
	}

	@SuppressWarnings("unused")
	private static StringBuffer getNewFileContents_verifySummary2(
			String keyword, String newReqArgs, String oldReqArgs,
			StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(1024);

		String[] srcStringArr = contentsBuffer.toString().split(LINE_SEP);

		String curLine;
		String curNewLine;
		String[] curLineArr;
		String curKeyword;

		for (int i=0; i<srcStringArr.length; i++) {
			curNewLine = null;
			curLine = srcStringArr[i];
			//log(debugLog, "\n\tCurrent Keyword Line: " + i + ": " + curLine);

			curLineArr = curLine.split(CSV_SEP);
			curKeyword = curLineArr[0];

			if (curKeyword.equalsIgnoreCase(keyword)) {
				newContents = true;

				//String newReqArgs = "(ItemPath; ItemName; PropKeys; PropValues)";
				//String oldReqArgs = "(ActivityName; AnalyticStatus-xx; ContainerName-xx; Created by; Data source-xx; EngagementName; File size; ItemName; Last modified by; Record count; Root)";
				String dpActivityName = curLineArr[2];
				String dpAnalyticStatus = curLineArr[3];	// no use anymore
				String dpContainerName = curLineArr[4];		// no use anymore
				String dpCreatedBy = curLineArr[5];
				String dpDataSource = curLineArr[6]; 		// no use anymore
				String dpEngagementName = curLineArr[7];
				String dpFileSize = curLineArr[8];	
				String dpItemName = curLineArr[9];	
				String dpLastModifiedBy = curLineArr[10];
				String dpRecordCount = curLineArr[11];	
				String dpRoot = curLineArr[12];

				String ItemPath = getItemPath2(dpRoot, dpEngagementName, dpActivityName);

				StringBuffer PropKeys = new StringBuffer();
				StringBuffer PropValues = new StringBuffer();

				if (dpCreatedBy != null && ! "".equalsIgnoreCase(dpCreatedBy)) {
					PropKeys.append("createdBy|");
					PropValues.append(dpCreatedBy + "|");
				}

				if (dpFileSize != null && ! "".equalsIgnoreCase(dpFileSize)) {
					PropKeys.append("fileSize|");
					PropValues.append(dpFileSize + "|");
				}

				if (dpLastModifiedBy != null && ! "".equalsIgnoreCase(dpLastModifiedBy)) {
					PropKeys.append("jobLastModifiedBy|");
					PropValues.append(dpLastModifiedBy + "|");
				}

				if (dpRecordCount != null && ! "".equalsIgnoreCase(dpRecordCount)) {
					PropKeys.append("recordCount|");
					PropValues.append(dpRecordCount + "|");
				}

				String PropKeysStr = PropKeys.substring(0, PropKeys.length()-1);
				String PropValuesStr = PropValues.substring(0, PropValues.length()-1);

				// String newReqArgs = "(dpItemToDeletePath; dpItemToDelete; dpConfirm; OperationType)";
				curNewLine = curKeyword + CSV_SEP + newReqArgs + CSV_SEP 
				+ ItemPath + CSV_SEP + dpItemName + CSV_SEP + PropKeysStr + CSV_SEP + PropValuesStr + CSV_SEP + LINE_SEP;

				newContentsBuffer.append(curNewLine);

				log(debugLog, "old: [" + curLine + "]");
				log(debugLog, "## new: [" + curNewLine + "]");

			} else {
				newContentsBuffer.append(curLine + LINE_SEP);
			}
		}

		return newContentsBuffer;
	}

	private static String getItemPath2(String root, String eng,	String act) {
		StringBuffer path = new StringBuffer();
		path.append(root);

		if (! "".equalsIgnoreCase(eng)) {
			path.append("->" + eng);
		} else {
			return path.toString();
		}

		if (! "".equalsIgnoreCase(act)) {
			path.append("->" + act);
		} else {
			return path.toString();
		}

		return path.toString();
	}

	@SuppressWarnings("unused")
	private static StringBuffer getNewFileContents_verifySummary(
			String keyword, String newReqArgs, String oldReqArgs,
			StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(1024);

		String[] srcStringArr = contentsBuffer.toString().split(LINE_SEP);

		String curLine;
		String curNewLine;
		String[] curLineArr;
		String curKeyword;

		for (int i=0; i<srcStringArr.length; i++) {
			curNewLine = null;
			curLine = srcStringArr[i];
			//log(debugLog, "\n\tCurrent Keyword Line: " + i + ": " + curLine);

			curLineArr = curLine.split(CSV_SEP);
			curKeyword = curLineArr[0];

			if (curKeyword.equalsIgnoreCase(keyword)) {
				newContents = true;
				//
				//				String newReqArgs = "(ItemPath; ItemName; PropKeys; PropValues)";
				//				String oldReqArgs = "(ItemPath; ItemName; AnalyticStatus; CreatedBy; FileSize; LastModifiedBy; RecordCount)";

				String ItemPath = curLineArr[2];
				String ItemName = curLineArr[3];
				String AnalyticStatus = curLineArr[4];
				String CreatedBy = curLineArr[5];
				String FileSize = curLineArr[6];
				String LastModifiedBy = curLineArr[7];
				String RecordCount = curLineArr[8];

				StringBuffer PropKeys = new StringBuffer();
				StringBuffer PropValues = new StringBuffer();

				if (AnalyticStatus != null && ! "".equalsIgnoreCase(AnalyticStatus)) {
					PropKeys.append("analyticsStatus|");
					PropValues.append(AnalyticStatus + "|");
				}

				if (CreatedBy != null && ! "".equalsIgnoreCase(CreatedBy)) {
					PropKeys.append("createdBy|");
					PropValues.append(CreatedBy + "|");
				}

				if (FileSize != null && ! "".equalsIgnoreCase(FileSize)) {
					PropKeys.append("fileSize|");
					PropValues.append(FileSize + "|");
				}

				if (LastModifiedBy != null && ! "".equalsIgnoreCase(LastModifiedBy)) {
					PropKeys.append("jobLastModifiedBy|");
					PropValues.append(LastModifiedBy + "|");
				}

				if (RecordCount != null && ! "".equalsIgnoreCase(RecordCount)) {
					PropKeys.append("recordCount|");
					PropValues.append(RecordCount + "|");
				}

				String PropKeysStr = PropKeys.substring(0, PropKeys.length()-1);
				String PropValuesStr = PropValues.substring(0, PropValues.length()-1);

				// String newReqArgs = "(dpItemToDeletePath; dpItemToDelete; dpConfirm; OperationType)";
				curNewLine = curKeyword + CSV_SEP + newReqArgs + CSV_SEP 
				+ ItemPath + CSV_SEP + ItemName + CSV_SEP + PropKeysStr + CSV_SEP + PropValuesStr + CSV_SEP + LINE_SEP;

				newContentsBuffer.append(curNewLine);

				log(debugLog, "old: [" + curLine + "]");
				log(debugLog, "## new: [" + curNewLine + "]");

			} else {
				newContentsBuffer.append(curLine + LINE_SEP);
			}
		}

		return newContentsBuffer;
	}

	@SuppressWarnings("unused")
	private static StringBuffer getNewFileContents_createContainer(String keyword, String newReqArgs, String oldReqArgs,
			StringBuffer contentsBuffer) {
		StringBuffer newContentsBuffer = new StringBuffer(1024);

		String[] srcStringArr = contentsBuffer.toString().split(LINE_SEP);

		String curLine;
		String curNewLine;
		String[] curLineArr;
		String curKeyword;

		for (int i=0; i<srcStringArr.length; i++) {
			curNewLine = null;
			curLine = srcStringArr[i];

			curLineArr = curLine.split(CSV_SEP);
			curKeyword = curLineArr[0];

			if (curKeyword.equalsIgnoreCase("fileMenuEngagement")
					|| curKeyword.equalsIgnoreCase("fileMenuActivity")
					|| curKeyword.equalsIgnoreCase("fileMenuResult")) {
				newContents = true;

				// String newReqArgs = "(ItemPath; ItemsList; ForceCreateItemPath; ExpectedErr; OperationType)";


				String oldExpectedErr = null;
				String Root = "working";
				String EngagementName = "";
				String operationType = "fileMenu";

				String ItemPath = Root;
				String ItemsList = "";
				String ForceCreateItemPath = "NO";

				if (curKeyword.equalsIgnoreCase("fileMenuEngagement")) {
					// String oldReqArgs = "(CreateEngErr; EngagementName; Root; operationType)";
					oldExpectedErr = curLineArr[2];
					EngagementName = curLineArr[3];
					Root = curLineArr[4];
					operationType = curLineArr[5];

					ItemPath = Root;
					ItemsList = EngagementName;
				} else if (curKeyword.equalsIgnoreCase("fileMenuActivity")) {
					// String oldReqArgs = (ActivityName; CreateActivityErr; EngagementName; Root; operationType)
					String ActivityName = curLineArr[2];
					oldExpectedErr = curLineArr[3];
					EngagementName = curLineArr[4];
					Root = curLineArr[5];
					operationType = curLineArr[6];

					ItemPath = Root + "->" + EngagementName;
					ItemsList = ActivityName;
				} else if (curKeyword.equalsIgnoreCase("fileMenuResult")) {
					// String oldReqArgs = (ActivityName; CreateResultErr; EngagementName; ResultsName; Root; operationType)
					String ActivityName = curLineArr[2];
					oldExpectedErr = curLineArr[3];
					EngagementName = curLineArr[4];
					String ResultsName = curLineArr[5];
					Root = curLineArr[6];
					operationType = curLineArr[7];

					ItemPath = Root + "->" + EngagementName + "->" + ActivityName;
					ItemsList = ResultsName;
				}

				String ExpectedErr = (oldExpectedErr == null || "".equalsIgnoreCase(oldExpectedErr))? "NO":"YES";

				// String newReqArgs = "(dpItemToDeletePath; dpItemToDelete; dpConfirm; OperationType)";
				curNewLine = "createContainer" + CSV_SEP + newReqArgs + CSV_SEP 
				+ ItemPath + CSV_SEP + ItemsList + CSV_SEP + ForceCreateItemPath + CSV_SEP + ExpectedErr + CSV_SEP + operationType + CSV_SEP + LINE_SEP;

				newContentsBuffer.append(curNewLine);

				log(debugLog, "old: [" + curLine + "]");
				log(debugLog, "## new: [" + curNewLine + "]");

			} else {
				newContentsBuffer.append(curLine + LINE_SEP);
			}
		}

		return newContentsBuffer;
	}

	@SuppressWarnings("unused")
	private static String getItemPath(String root, String eng, String act, String container, String item) {
		StringBuffer path = new StringBuffer();
		path.append(root);

		if (! "".equalsIgnoreCase(eng)) {
			path.append("->" + eng);
		} else {
			return path.toString();
		}

		if (! "".equalsIgnoreCase(act)) {
			path.append("->" + act);
		} else {
			return path.toString();
		}

		if (! "".equalsIgnoreCase(container)) {
			path.append("->" + container);
		} else {
			return path.toString();
		}

		if (! "".equalsIgnoreCase(item)) {
			path.append("->" + item);
		} else {
			return path.toString();
		}

		return path.toString();
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
	
	private static String chopLastStr(String myString, String charToChop) {
		return myString.endsWith(charToChop)? 
				myString.substring(0, myString.length() - charToChop.length()) : myString;
	}
	
	private static String joinSetValues(Set<String> mySet, String mySep) {
		StringBuilder mySb = new StringBuilder();
		Iterator<String> itr = mySet.iterator();
	    while(itr.hasNext()) {
	    	mySb.append(itr.next() + mySep);
	    }
	    
	    return chopLastStr(mySb.toString(), mySep);
	}
}