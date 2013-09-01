package com.acl.qa.taf.helper.superhelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

//import org.apache.poi.hssf.usermodel.HSSFCell;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import com.acl.qa.taf.util.FileUtil;
import com.acl.qa.taf.util.UnicodeUtil;


public class ObjectHelper extends GuiFinderHelper {

	// objectHelper is shared by all test project in the framework,
	// don't create methods for individual project here

	public String actualValue = "";
	public boolean useFindMethod = false;
	public int indexOfObject = 0;
	public int currentPostNum = 0, prePostNum = 0;
	public boolean found = false;
	public static ArrayList<Object> foundObjs;
	public static int wt = 0;
	protected String lineDelimiter = "";

	public int checkBoxWidth = 10, checkBoxHeight = 10;
	// public int checkBoxWidth=20, checkBoxHeight=20;
	public int iconWidth = 14, iconHeight = 14;
	public String imgPath = "";// AX_GatewayPro.com.acl.qa.taf.conf.bean.ProjectConf.expectedDataDir+"baselineImages\\";
	public String imgActualPath = imgPath + "temp\\actual_";
	public String tmpImgPathDefault = imgActualPath + "tableIcon.jpg";

	public final String TABLE_TYPE_GeneralData = "GeneralData";
	public final String TABLE_TYPE_CompleteTable = "CompleteTable";
	public final String TABLE_TYPE_LinkToCompleteTable = "LinkToCompleteTable";
	public final String TABLE_TYPE_LayoutOnly = "LayoutOnly";
	public final String TABLE_TYPE_LinkToLayoutOnly = "LinkToLayoutOnly";
	public final String TABLE_TYPE_LinkToDataBroken = "LinkToDataBroken";
	public final String TABLE_TYPE_MasterCompleteTable = "MasterCompleteTable";
	public final String TABLE_TYPE_MasterLayoutOnly = "MasterLayoutOnly";
	public final String TABLE_TYPE_CompleteAnalytic = "CompleteAnalytic"; // Added
																			// to
																			// baseline
																			// -
																			// steven
	public final String TABLE_TYPE_MasterAnalytic = "MasterAnalytic";
	public final String TABLE_TYPE_AnalyticLink = "AnalyticLink";
	public final String TABLE_TYPE_AnalyticLinkBroken = "AnalyticLinkBroken";

	public final String TABLE_TYPE_GatewayLink = "GatewayLink";
	public final String TABLE_TYPE_GatewayLinkMouseOver = "GatewayLinkMouseOver";

	static String whitespace_chars = "" /* dummy empty string for homogeneity */
			+ "\\u0009" // CHARACTER TABULATION
			+ "\\u000A" // LINE FEED (LF)
			+ "\\u000B" // LINE TABULATION
			+ "\\u000C" // FORM FEED (FF)
			+ "\\u000D" // CARRIAGE RETURN (CR)
			+ "\\u0020" // SPACE
			+ "\\u0085" // NEXT LINE (NEL)
			+ "\\u00A0" // NO-BREAK SPACE
			+ "\\u1680" // OGHAM SPACE MARK
			+ "\\u180E" // MONGOLIAN VOWEL SEPARATOR
			+ "\\u2000" // EN QUAD
			+ "\\u2001" // EM QUAD
			+ "\\u2002" // EN SPACE
			+ "\\u2003" // EM SPACE
			+ "\\u2004" // THREE-PER-EM SPACE
			+ "\\u2005" // FOUR-PER-EM SPACE
			+ "\\u2006" // SIX-PER-EM SPACE
			+ "\\u2007" // FIGURE SPACE
			+ "\\u2008" // PUNCTUATION SPACE
			+ "\\u2009" // THIN SPACE
			+ "\\u200A" // HAIR SPACE
			+ "\\u2028" // LINE SEPARATOR
			+ "\\u2029" // PARAGRAPH SEPARATOR
			+ "\\u202F" // NARROW NO-BREAK SPACE
			+ "\\u205F" // MEDIUM MATHEMATICAL SPACE
			+ "\\u3000" // IDEOGRAPHIC SPACE
	;




	// ********** End of table processing

	public static int getNumberBetween(String input, char begin, char end) {
		int num = 0;
		int beginIndex = 0, endIndex = 0;

		beginIndex = input.indexOf(begin);
		endIndex = input.indexOf(end);
		// logTAFDebug(input+", numPost= input.substring(beginIndex+1,endIndex)");
		try {
			// logTAFDebug(input+",beginIndex = "+
			// (beginIndex+1)+",endIndex = "+(endIndex));
			// logTAFDebug(input+", numPost= "+
			// input.substring(beginIndex+1,endIndex));
			num = Integer.parseInt(input.substring(beginIndex + 1, endIndex));
		} catch (Exception e) {
			num = 0;
		}

		return num;
	}

	public static void trimAllInArray(String[] inputArray) {
		for (int i = 0; i < inputArray.length; i++) {
			inputArray[i] = inputArray[i].trim();
			// logTAFDebug("Input Array "+(i+1)+": '"+inputArray[i]+"'");
		}
	}


	public static String getFileName(String path) {
		String fname;
		fname = new File(FileUtil.getAbsDir(path)).getName();
		return fname;
	}


	public static String sanitizeText(String text) {
		boolean removeLineFeed = false;
		return sanitizeText(text, removeLineFeed);
	}

	public static String sanitizeText(String text, boolean removeLineFeed) {
		// Date: 10/04/11 10:48:41 (UTC-07:00) - MM/DD/YY HH:MM:SS (UTC-07:00)
		// Dir: [a-zA-Z]:\
		// User:

		byte[] bytes;
		try {
			bytes = text.getBytes("ASCII");
		} catch (UnsupportedEncodingException e) {
			bytes = null;
		}

		if (bytes != null) {
			text = new String(bytes).replaceAll("\\n", "[LineFeed]")
					.replaceAll("\\?", "");
			// text = text.replaceAll("\\p{Cc}|\\p{Cntrl}","");
		} else {
			// logTAFWarning("Unfortunlly, we failed to converty the text to ASCII");
			return "Can't sanitize this text with ascii encoding!";
		}

		String lineFeed = "\\n";
		String controls = "[\\uEFEE-\\uFFFF]";
		// if(removeLineFeed){
		// lineFeed = "";
		// }else{
		controls += "|\\p{Cc}|\\p{Cntrl}";
		// }
		String[] pattern = {
				controls, // Null

				"Produced with ACL by:.*",
				".*OUTPUTFOLDER.*",
				"[\\d]{1,2}[/-][0-9A-Za-z]{1,3}[/-][\\d]{2,4}", // Date
				"[\\d]{1,2}[-:][\\d]{1,2}[-:][\\d]{2}", // Time
				"\\([A-Z]{3}-[\\d]{2}[/:][\\d]{2}\\)", // Zone
				".*[a-zA-Z]:\\\\.*", // Dir Local
				".*\\\\.*", // Net Dir or Link
				".*[@]+.*", // Possible dSymbol
				// "\\.[0]+[\\D]", // For different number format, remove .0's

				"[Uu]ser[: ].*", "[sS]erial.*",
				"[ \\t\\x0B\\f\\r" + whitespace_chars + "]*", // Spaces for ease
																// reading
				// "[\\n][\\s]?[\\n]"
				"\\[LineFeed\\]" };
		String[] format = { "",

		"Produced with ACL by: DefName in acl.ini file", "", "[MM/DD/YY]",
				"[HH:MM:SS]", "[(UTC-07:00)]", "[LocalPath]", "[NetLink]",
				"[DSymbol]",
				// "",

				"User: [Username]", "Serial CAS-----------", "", lineFeed };
		for (int i = 0; i < pattern.length; i++) {
			// System.out.println("Nomornizing '"+pattern[i]+"' - '"+format[i]+"'");
			// System.out.println("Text Before: '"+text+"'");
			text = text.replaceAll(pattern[i], format[i]);
			// System.out.println("Text After: '"+text+"'");
		}
		if (removeLineFeed) {
			text = text.replaceAll(lineFeed, "").replaceAll("\\r", "");
		}
		return text;
	}

	public static String removeLineFeed(String input, String to) {
		// Debugging ... Steven, we should use system linefeed instead?
		String oriInput = input;
		String output;

		// not so sure this line will work properly on diff os
		output = input.replace(sysLineSep.toString(), to);
		// these 3 lines are supposed to be removed -- Steven.
		output = input.replaceAll("\\r ", to);
		output = output.replaceAll("\\n", to);
		output = output.replaceAll("\\r", "");

		return output;
	}

	public static String removeLineFeed(String input) {
		// return removeLineFeed(input,"/");
		return removeLineFeed(input, "");
	}

	public static String getPrintableText(String text) {
		String[] pattern = { "[^\\p{Print}]+", // Not printable
				"[\\s]+|\\p{Cc}" // Cntrl "[\\x00-\\x1F\\x7F]"
		};
		String[] format = { "",// "[NotPrintable]"
				""// "[Cntrl]"
		};
		for (int i = 0; i < pattern.length; i++) {
			// System.out.println("Nomornizing '"+pattern[i]+"' - '"+format[i]+"'");
			// System.out.println("Text Before: '"+text+"'");
			try {
				text = text.replaceAll(pattern[i], format[i]);
			} catch (Exception e) {
				text = "";
			}
			// System.out.println("Text After: '"+text+"'");
		}
		return text;
	}

	public boolean compareTextFile(String fileMaster, String fileActual,
			String actualContents, boolean updateMasterFile, String fromKey,
			String endKey) {
		return compareTextFile(fileMaster, fileActual, actualContents,
				updateMasterFile, "File", fromKey, endKey, -1, -1);
	}

	public boolean compareTextFile(String fileMaster, String fileActual,
			boolean updateMasterFile, String fromKey, String endKey) {
		return compareTextFile(fileMaster, fileActual, null, updateMasterFile,
				"File", fromKey, endKey, -1, -1);
	}

	public boolean compareTextFile(String fileMaster, String fileActual,
			String actualContents, boolean updateMasterFile, int fromLine,
			int endLine) {
		return compareTextFile(fileMaster, fileActual, actualContents,
				updateMasterFile, "File", null, null, fromLine, endLine);
	}

	public boolean compareTextFile(String fileMaster, String fileActual,
			boolean updateMasterFile, int fromLine, int endLine) {
		return compareTextFile(fileMaster, fileActual, null, updateMasterFile,
				"File", null, null, fromLine, endLine);
	}

	public boolean compareTextFile(String fileMaster, String fileActual,
			boolean updateMasterFile, String fromKey, String endKey,
			int fromLine, int endLine) {
		return compareTextFile(fileMaster, fileActual, null, updateMasterFile,
				"File", fromKey, endKey, fromLine, endLine);
	}

	public boolean compareTextFile(String fileMaster, String fileActual,
			String actualContents, boolean updateMasterFile) {
		return compareTextFile(fileMaster, fileActual, actualContents,
				updateMasterFile, "File", null, null, -1, -1);
	}

	public boolean compareTextFile(String fileMaster, String fileActual,
			String actualContents, boolean updateMasterFile, String label) {
		return compareTextFile(fileMaster, fileActual, actualContents,
				updateMasterFile, label, null, null, -1, -1);
	}
	public boolean compareTextFile(String fileMaster, String fileActual,
			String actualContents, boolean updateMasterFile, String label,boolean exactMatch) {
		return compareTextFile(false,fileMaster, fileActual, actualContents,
				updateMasterFile, label, null, null, -1, -1,exactMatch);
	}
	
	public boolean compareTextFile(String fileMaster, String fileActual,
			String actualContents, boolean updateMasterFile, String label,boolean exactMatch,
			String[] ignorePattern, String[] ignoreName,String delimiter) {
		return compareTextFile(false,fileMaster, fileActual, actualContents,
				updateMasterFile, label, null, null, -1, -1,exactMatch,
				ignorePattern, ignoreName, delimiter);
	}
	public boolean compareTextFile(String fileMaster, String fileActual,
			boolean updateMasterFile) {
		return compareTextFile(fileMaster, fileActual, null, updateMasterFile,
				"File", null, null, -1, -1);
	}

	public boolean compareTextFile(String fileMaster, String fileActual,
			boolean updateMasterFile, String label) {
		return compareTextFile(fileMaster, fileActual, null, updateMasterFile,
				label, null, null, -1, -1);
	}

	public boolean compareTextFile(String fileMaster, String fileActual,
			String actualContents, boolean updateMasterFile, String label,
			String fromKey, String endKey, int fromLine, int endLine) {

		return compareTextFile(false, fileMaster, fileActual, actualContents,
				updateMasterFile, label, fromKey, endKey, fromLine, endLine);
	}

	// **** temp solution ***********
	public String superMasterFile = "";
	// **** 
	
	public boolean compareTextFile(boolean asciiOnly, String fileMaster,
			String fileActual, String actualContents, boolean updateMasterFile,
			String label, String fromKey, String endKey, int fromLine,
			int endLine) {
		return compareTextFile(asciiOnly, fileMaster,
			fileActual, actualContents, updateMasterFile,
			label, fromKey, endKey, fromLine,
			endLine,false);
	}
	public boolean compareTextFile(boolean asciiOnly, String fileMaster,
			String fileActual, String actualContents, boolean updateMasterFile,
			String label, String fromKey, String endKey, int fromLine,
			int endLine,boolean exactMatch) {
		return compareTextFile(asciiOnly, fileMaster,
				fileActual, actualContents,updateMasterFile,
				label, fromKey, endKey, fromLine,
				endLine,exactMatch,null,null,"");
	}
	public boolean compareTextFile(boolean asciiOnly, String fileMaster,
			String fileActual, String actualContents, boolean updateMasterFile,
			String label, String fromKey, String endKey, int fromLine,
			int endLine,boolean exactMatch,String[] ignorePattern,String[] ignoreName,String delimiter) {
		// String lineTerminator = "[\r\n\u0085\u2028\u2029]";
		boolean success = true;
		int numLineLimits = 20000;
		int sizeLimits = 10000;
		String[] textMaster, textActual;
		String fileDescription = "[" + label + "]";
        String _delimiter = "";
        String[] _ignorePattern ={"(\"id\":\")[0-9\\-a-z]+(\")"};
        String[] _ignoreName = {"$1u-u-i-d$2"};
        
       // {"id":"72f7481b-965a-46fc-8b31-e818e280eff5","name":
        
//        if(label.equalsIgnoreCase("JSON")){
//            delimiter = "\\},\\{";
//            
//        }
//		if (!label.equalsIgnoreCase("File")) {
//			if (!fileMaster.endsWith(fileDescription))
//				fileMaster += "[" + label + "]";
//			if (!fileActual.endsWith(fileDescription))
//				fileActual += "[" + label + "]";
//			if (!superMasterFile.endsWith(fileDescription))
//				superMasterFile += "[" + label + "]";
//		}
		
		
		if (actualContents != null) {// &&!actualContents.equals("")){
			FileUtil.delFile(fileActual);
			FileUtil.writeFileContents(fileActual, actualContents);
		}
		File temp = new File(FileUtil.getAbsDir(fileActual));
		if (!temp.exists()) {
			logTAFWarning("File not found - '" + fileActual + "'");
			return true;
		} else if (!temp.isFile()) {
			logTAFWarning("Not a file - '" + fileActual + "'");
			return true;
		} else if (temp.length() > sizeLimits * 1000) {
			logTAFWarning("File too big to be compared (" + temp.length()
					/ (1000 * 1000) + "MBytes)- '" + fileActual + "'");
			return true;
		}
       // if(!updateMasterFile&&new File(superMasterFile).exists()){
        if(new File(superMasterFile).exists()){
        	logTAFInfo("Get user provided master file from - "+superMasterFile);
        	FileUtil.delFile(fileMaster);
        	FileUtil.writeFileContents(fileMaster, FileUtil.getFileContents(superMasterFile));
        	//FileUtil.copyFile(superMasterFile, fileMaster);
        }else if (updateMasterFile || !new File(fileMaster).exists()) {
			logTAFInfo("Save/Update master file with current contents '" + fileMaster
					+ "' ");
			FileUtil.delFile(fileMaster);
			if (actualContents != null) {// &&!actualContents.equals("")){
				FileUtil.writeFileContents(fileMaster, actualContents);
			}else{
				FileUtil.writeFileContents(fileMaster, FileUtil.getFileContents(fileMaster));
			}
			
			return true;
		}

		//
		if (fileMaster.toUpperCase().matches(".+\\.XLS[X]?[\\s]*$")
				&& fileActual.toUpperCase().matches(".+\\.XLS[X]?[\\s]*$")) {

			if (isValidExcel(fileMaster) && isValidExcel(fileActual)) {
				return compareDataInExcel(fileMaster, fileActual, "All");
			} else {
				logTAFWarning("The xls file corropted? or it's an excel 2.1 which can't be auto compared currently");
				return true;
			}
		}

		// logTAFStep("Comparing "+label+"(First "+numLineLimits+" lines): "+fileMaster
		// +" and "+fileActual);

		logTAFStep("Comparing " + label + ": \n\t\t" + fileMaster + "\n\t and \n\t\t"
				+ fileActual);
        String tempMaster = "",tempActual="";
        if(delimiter.equals("")){
		       tempMaster = FileUtil.readFile(fileMaster, numLineLimits, true);
		       tempActual = FileUtil.readFile(fileActual, numLineLimits, true);
        }else{
        	   tempMaster = FileUtil.readFile(fileMaster, numLineLimits, false);
		       tempActual = FileUtil.readFile(fileActual, numLineLimits, false);
//		       tempMaster = FileUtil.readFile(fileMaster, delimiter,numLineLimits);
//		       tempActual = FileUtil.readFile(fileActual, delimiter,numLineLimits);
        }
		if (fromKey != null) {
			int fromMaster = tempMaster.lastIndexOf(fromKey);
			int fromActual = tempActual.lastIndexOf(fromKey);
			if (fromMaster > -1) {//Comparing sub contents
				tempMaster = "[StartComparison]"
						+ tempMaster.substring(fromMaster + fromKey.length());
			}
			if (fromActual > -1) {
				tempActual = "[StartComparison]"
						+ tempActual.substring(fromActual + fromKey.length());
			}
		}
		if (endKey != null) {
			int endMaster = tempMaster.lastIndexOf(endKey);
			int endActual = tempActual.lastIndexOf(endKey);
			if (endMaster > -1) {
				tempMaster = tempMaster.substring(0, endMaster)
						+ "[EndComparison]";
			}
			if (endActual > -1) {
				tempActual = tempActual.substring(0, endActual)
						+ "[EndComparison]";
			}
		}
		// textMaster = sanitizeText(tempMaster).split("\n");
		// textActual = sanitizeText(tempActual).split("\n");

		// String lineDel = getPossibleLineDelimiter(tempMaster);
		// textMaster = tempMaster.split(lineDel);
		// textActual = tempActual.split(lineDel);
        if(delimiter.equals("")){
        	delimiter = getPossibleLineDelimiter(tempMaster);
        }
        
        if(ignorePattern!=null&&ignoreName!=null){
        	tempMaster = stringReplaceAll(tempMaster,ignorePattern,ignoreName);
        	tempActual = stringReplaceAll(tempActual,ignorePattern,ignoreName);

        }
        textMaster = tempMaster.split(delimiter);
		textActual = tempActual.split(delimiter);
		
//		textMaster = tempMaster.split(getPossibleLineDelimiter(tempMaster));
//		textActual = tempActual.split(getPossibleLineDelimiter(tempActual));
		
		if (textActual.length > textMaster.length && textMaster.length < 3) {
			// Master file corrupted possibly or source file changed?
			// updateMaster file;
			logTAFInfo("Save/Update contents of master file '" + fileMaster
					+ "'");
			FileUtil.delFile(fileMaster);
			FileUtil.writeFileContents(fileActual, FileUtil.getFileContents(fileMaster));
			return true;
		}
		boolean compareNumLines = exactMatch||(isCompareable(fileMaster)
				&& isCompareable(fileActual));
		

		success = compareStringLines(compareNumLines,
				textMaster, textActual,
				fromLine, endLine, label,exactMatch);

		return success;
	}

	public boolean isCompareable(String fname) {
		String exts = ".+[Ll][oO][gG]\\]?\\s$|.+VIEW\\]\\s$?|.+GRAPH\\]?\\s$|.+[Tt][Xx][Tt]|[Ff][iI][Ll]\\s$";
		if (fname.matches(exts))
			return true;
		return false;
	}

	public static String[] removeEmptyLines(String[] text) {
		String result = "", del = "autoDelimiter";
		for (int i = 0; i < text.length; i++) {
			String temp = sanitizeText(text[i], true);
			if (!temp.matches("[\\s" + whitespace_chars + "]*")) {
				if (i == 0)
					result = temp;
				else
					result += del + temp;
			}

		}
		return result.split(del);
	}

	public String getPossibleLineDelimiter(String text) {
		String lineDelSfeed = "\\n", lineDelDfeed = "\\n[\\s]*[\\r]?\\n";
		String[] lineUser, lineDfeed;

		if (!lineDelimiter.equals("")) {
			lineUser = text.split("["+lineDelimiter+"]");
			if (lineUser.length > 1) {
				return lineDelimiter;
			}
		} else {
			lineDfeed = text.split(lineDelDfeed);
			if (lineDfeed.length > 1) {
				return lineDelDfeed;
			}
		}

		return lineDelSfeed;
	}

	public static boolean compareStringLines(String[] tm, String[] ta,
			String label) {
		return compareStringLines(false, tm, ta, -1, -1, label);
	}
	public static boolean compareStringLines(boolean compareNumLines,
			String[] tm, String[] ta, int fromLine, int endLine, String label){
		return compareStringLines(compareNumLines,tm,ta,fromLine,endLine,label,false);
	}
	public static boolean compareStringLines(boolean compareNumLines,
			String[] tm, String[] ta, int fromLine, int endLine, String label,boolean exactMatch) {
		boolean success = true, isInfo = false, linediff = false;

		if(!exactMatch){
			tm = removeEmptyLines(tm);
			ta = removeEmptyLines(ta);
		}
		String sm, sa, msg;

		// logTAFStep("Comparing '"+label+"'");
		if (fromLine < 0) {
			fromLine = 1;
		}
		if (endLine < 0) {
			endLine = tm.length;
		}
		if (tm.length != ta.length) {
			int diffLines = Math.abs(tm.length - ta.length);
			linediff = true;
			if (!compareNumLines) {// diffLines<=3){
				logTAFWarning("Not exactly match, there might be some dynamic data in the file which made it incompatiable"
						+ logLF+"Expected "
						+ tm.length
						+ " lines, but actual "
						+ ta.length + " lines found");
				isInfo = true;
			} else if (diffLines <= 3) {
				logTAFWarning(autoIssue + "Expected " + tm.length
						+ " lines, but actual " + ta.length + " lines found");
				isInfo = false;
			} else {
				logTAFWarning("Not exactly match, impacted by System locale? different encoding? or different OS platform?"
						+ logLF+"Expected "
						+ tm.length
						+ " lines, but actual "
						+ ta.length + " lines found");
				isInfo = true;
			}
		}
		boolean done = false;
		int maxError = 10, numError = 0;

		for (int i = 0; i < tm.length && i < ta.length && i > (fromLine - 2)
				&& i < endLine && !done && numError < maxError; i++) {
			// tm[i] = sanitizeText(tm[i],true);
			// ta[i] = sanitizeText(ta[i],true);
			isInfo = false;
			if (tm[i].trim().matches("[\\s]*")
					&& ta[i].trim().matches("[\\s]*")) {
				continue;
			}
			// logTAFInfo("^^^^^^ MasterLine: '"+tm[i]+"' ActualLine: '"+ta[i]+"'");
			if (!tm[i].trim().equalsIgnoreCase(ta[i].trim())) {
				msg = "Not match - Line " + (i + 1) + ": ";
               if(!exactMatch){
				sm = getPrintableText(tm[i]);
				sa = getPrintableText(ta[i]);              
				
               }else{
            	   sm = tm[i];
            	   sa = ta[i];
               }
				// logTAFInfo("MasterLine: '"+tm[i]+"' ActualLine: '"+ta[i]+"'");
				if (sm.trim().equals(sa.trim()) ||(!exactMatch&&ignoreable(sm + sa))) {
					isInfo = true;
					msg += "\n\t\t'"
							+ sm
							+ "[WithLinkOrPathOrDynamicSymbolOrOtherNonPrintableChars!]' - May need to open the file with proper software for details";
				} else {
					msg += logLF+"Expected, -" + sm + logLF+"Actual,   -" + sa;
//					if (linediff)
//						msg = autoIssue + msg;
				}
				if (isInfo) {
					// done = true;

					logTAFWarning(msg);
				} else {
					numError++;
					logTAFError(msg);
					success = false;
					//break;
				}
				isInfo = linediff;
			}
		}
		if (success) {
			logTAFInfo("No difference found between both files - " + tm.length
					+ " lines compared");
		}
		return success;
	}

	public static boolean ignoreable(String line) {
		String[] ignoreLine = { "RootEntry", "[MM/DD/YY]", "[HH:MM:SS]",
				"[(UTC-07:00)]", "[LocalPath]",
				"[NetLink]",
				"[DSymbol]",
				"encounteredwas", // For Age test
				"CurrVer",
				"ctimetaken",

				"vMain", // This 4 are special for i18n batch
				"scriptWasASuccess", "LOCProf",
				"random",
				// "_PAY_CONST",

				"Totalstringspace", "istarttime", "User: [Username]",
				"Produced with ACL by: DefName in acl.ini file",
				"Serial CAS-----------" };

		for (String key : ignoreLine) {
			if (line.contains(key)) {
				return true;
			}
		}

		return false;
	}



	public boolean equalString(String s1, String s2) {
		boolean equals = true;
		String LF = "" + (char) 10;
		String CR = "" + (char) 13;

		// s2 = s2.replaceAll(CR,"");
		int length1 = s1.length();
		int length2 = s2.length();
		int c1, c2;
		logTAFDebug("Comparing String s1: \n'" + s1 + "'\n with String s2: \n'"
				+ s2);

		for (int i = 0; i < length1 & i < length2; i++) {
			c1 = s1.charAt(i);
			c2 = s2.charAt(i);
			if (c1 != c2) {
				equals = false;
				logTAFDebug("Char[" + i + "] of s1 - " + (char) c1 + "(" + c1
						+ ") != Char[" + i + "] of s2 - " + (char) c2 + "("
						+ c2 + ")");
			}
		}

		if (length1 < length2) {
			for (int i = length1; i < length2; i++) {
				c2 = s2.charAt(i);
				logTAFDebug("Char[" + i + "] of s2 - " + (char) c2 + "(" + c2
						+ ")");
			}
		} else if (length1 > length2) {
			for (int i = length2; i < length1; i++) {
				c1 = s1.charAt(i);
				logTAFDebug("Char[" + i + "] of s1 - " + (char) c1 + "(" + c1
						+ ")");
			}
		}
		return equals;
	}


	public static boolean isValidExcel(String file) {

		InputStream fls = null;
		boolean isValidExcel = true;
		File fl = new File(FileUtil.getAbsDir(file));
		try {
			fls = new FileInputStream(fl);
			WorkbookFactory.create(fls);
			fls.close();
		} catch (Exception e) {
			isValidExcel = false;
		}
		return isValidExcel;
	}

	public static boolean compareDataInExcel(String masterFile,
			String actualFile, String types) {
		int maxRows = 65536;
		int maxColumns = 256;
		boolean success = true;
		logTAFInfo("Master data from file: " + masterFile);
		logTAFInfo("Actual data form file: " + actualFile);
		try {
			File master = new File(FileUtil.getAbsDir(masterFile));
			File actual = new File(FileUtil.getAbsDir(actualFile));

			InputStream isMaster = new FileInputStream(master);
			InputStream isActual = new FileInputStream(actual);

			try {// WorkbookFactory.create(in)
				Workbook hwbMaster = WorkbookFactory.create(isMaster);

				int numSheetsMaster = hwbMaster.getNumberOfSheets();

				Workbook hwbActual = WorkbookFactory.create(isActual);
				int numSheetsActual = hwbActual.getNumberOfSheets();

				if (numSheetsMaster == numSheetsActual) {
					logTAFInfo(numSheetsMaster
							+ " sheet(s) found in Both master and actual files");
				} else {
					logTAFError(numSheetsActual + " sheet(s) in actual file, "
							+ numSheetsMaster + " sheet(s) in master file");
					success = false;
				}

				int numComparableSheets = numSheetsMaster <= numSheetsActual ? numSheetsMaster
						: numSheetsActual;

				logTAFInfo("Comparing " + numComparableSheets
						+ " sheet(s) from both files");

				for (int sheet = 0; sheet < numComparableSheets; sheet++) {
					int numMismatchRow = 0, numWarning = 0;
					Sheet sMaster = hwbMaster.getSheetAt(sheet);
					int numRowsMaster = sMaster.getPhysicalNumberOfRows();
					Sheet sActual = hwbActual.getSheetAt(sheet);
					int numRowsActual = sActual.getPhysicalNumberOfRows();
					logTAFInfo("Sheet: " + (sheet + 1));
					if (numRowsMaster == numRowsActual) {
						logTAFInfo(numRowsMaster
								+ " Row(s) found in Both master and actual sheet "
								+ (sheet + 1));
					} else {
						logTAFError(numRowsActual + " Rows(s) in actual file, "
								+ numRowsMaster + " Rows(s) in master file ");
						success = false;
					}

					int numComparableRows = numRowsMaster <= numRowsActual ? numRowsMaster
							: numRowsActual;
					numComparableRows = numComparableRows <= maxRows ? numComparableRows
							: maxRows;
					logTAFInfo("Comparing " + numComparableRows
							+ " Row(s) from both sheets one by one");

					int[] actualIndex = null;
					String[] header = null;
					for (int i = 0; i < numComparableRows; i++) {
						Row rowMaster = sMaster.getRow(i);
						int numCellsMaster = rowMaster.getLastCellNum();
						org.apache.poi.ss.usermodel.Row rowActual = sActual
								.getRow(i);
						int numCellsActual = rowActual.getLastCellNum();

						boolean mismatchRow = true;
						// logTAFInfo("Row: "+(i+1));
						if (numCellsMaster == numCellsActual) {
							if (i == 0)
								logTAFInfo(numCellsMaster
										+ " Cell(s) found in Both master and actual - Row "
										+ (i + 1));
						} else {
							if (i == 0) // Only check the first for num cells -
										// Steven
								logTAFError(numCellsActual
										+ " Cell(s) in actual file - Row "
										+ (i + 1) + ", " + numCellsMaster
										+ " Cell(s) in master file - Row "
										+ (i + 1));
							success = false;
						}

						int numComparableCells = numCellsMaster <= numCellsActual ? numCellsMaster
								: numCellsActual;
						numComparableCells = numComparableCells <= maxColumns ? numComparableCells
								: maxColumns;

						if (numComparableCells > 0) {
							String cellValueMaster = null, cellValueActual = null;
							Cell cell = null;
							String ignoreColumns = ".*EBCDIC.*";

							if (i == 0) {
								actualIndex = new int[numCellsMaster];
								header = new String[numCellsMaster];

								boolean diffOrder = false;

								logTAFInfo("Comparing " + numComparableCells
										+ " Cell(s) from both rows one by one");
								for (int aIndex = 0; aIndex < numCellsMaster; aIndex++) {
									boolean found = false, foundSimilar = false;
									int itemIndex = -1;
									cell = rowMaster.getCell(aIndex);
									cellValueMaster = UnicodeUtil
											.getStringCellValueWithType(cell,
													types);
									header[aIndex] = cellValueMaster;
									for (int bIndex = 0; bIndex < numCellsActual; bIndex++) {
										cell = rowActual.getCell(bIndex);
										cellValueActual = UnicodeUtil
												.getStringCellValueWithType(
														cell, types);

										if (!cellValueMaster
												.equals(cellValueActual)) {
											if (cellValueMaster
													.toUpperCase()
													.replaceAll("__", "_")
													.contains(
															cellValueActual
																	.toUpperCase()
																	.replaceAll(
																			".| ",
																			"_")
																	.replaceAll(
																			"__|___",
																			"_"))) {
												foundSimilar = true;
												itemIndex = bIndex;
											}
										} else {
											found = true;
											itemIndex = bIndex;
											break;
										}
									}

									actualIndex[aIndex] = itemIndex;

									if (itemIndex != aIndex && itemIndex != -1) {
										diffOrder = true;

									}
									if (!found) {
										if (foundSimilar) {
											logTAFWarning("Warning - ACL DesktopHeader format issue?, Sheet "
													+ (sheet + 1)
													+ " Row "
													+ (i + 1)
													+ " Cell "
													+ (aIndex + 1)
													+ " mismatch - expected value = '"
													+ cellValueMaster
													+ "' actual value = '"
													+ cellValueActual + "'");
										} else {
											logTAFError("Column not found in actural file,  '"
													+ cellValueMaster);
										}
									} else {
										logTAFDebug("Column '"
												+ cellValueMaster
												+ "' found in Actual file with index '"
												+ itemIndex + "'");
									}

								}
								if (diffOrder) {
									logTAFWarning("Excel columns in different order !!!");
								}

								continue;
							} // End of header

							for (int j = 0; j < numComparableCells
									&& actualIndex[j] != -1; j++) {
								cell = rowMaster.getCell(j);
								cellValueMaster = UnicodeUtil
										.getStringCellValueWithType(cell, types);
								cell = rowActual.getCell(actualIndex[j]);
								cellValueActual = UnicodeUtil
										.getStringCellValueWithType(cell, types);

								if (!cellValueMaster.equals(cellValueActual)) {

									if (mismatchRow)
										numMismatchRow++;
									if (numMismatchRow < 25) {
										if (cellValueMaster
												.startsWith("[Date]")
												|| header[j]
														.matches(ignoreColumns)) {
											if (mismatchRow)
												numMismatchRow--;

											// Currently, there is an issue on
											// office 2003 with
											// format'English(Canada) or (United
											// Kingdom) or (South Africa)'
											if (numWarning++ < 25)
												logTAFWarning("Sheet "
														+ (sheet + 1)
														+ " Row "
														+ (i + 1)
														+ " Cell "
														+ (j + 1)
														+ " mismatch - expected value = '"
														+ cellValueMaster
														+ "' actual value = '"
														+ cellValueActual + "'");
										} else {
											mismatchRow = false;
											logTAFError("Sheet "
													+ (sheet + 1)
													+ " Row "
													+ (i + 1)
													+ " Cell "
													+ (j + 1)
													+ " mismatch - expected value = '"
													+ cellValueMaster
													+ "' actual value = '"
													+ cellValueActual + "'");
										}
										success = false;
									} else if (numMismatchRow == 25) {
										if (cellValueMaster
												.startsWith("[Date]")
												|| header[j]
														.matches(ignoreColumns)) {
											if (mismatchRow)
												numMismatchRow--;
											if (numWarning++ < 25)
												logTAFWarning("More mismatch record found - [Date] or ["
														+ ignoreColumns
														+ "].... ");
										} else {
											mismatchRow = false;
											logTAFError("More mismatch record found .... ");
											success = false;
										}
									}

								}
								// End of value condition
							}
							// End of for loop - cells
						}

					}
					logTAFInfo("Total " + numMismatchRow
							+ " mismached records found in sheet "
							+ (sheet + 1));
				}

			} catch (UnsupportedEncodingException e) {
				logTAFWarning(e.toString());
			} catch (IOException e) {
				logTAFWarning(e.toString());
			} catch (Exception e) {
				logTAFWarning(e.toString());
			}

			isMaster.close();
			isActual.close();

		} catch (Exception e) {
			logTAFWarning(e.toString());
		}
		return success;
	}



	@SuppressWarnings("finally")
	public boolean aclVersionUpdate = false;
	public static String nonError = "", nonError_cons = "";// ,msg_en;

	// ****b. filtering message and compare with non error pattern
	public static boolean isMatched(String msg, String exps, String cons) {
		boolean matched = false;
		// msg =
		// "告诫！处理中的副本的大小比项目小或版本比项目旧。这是不正常的。是否仍要使用处理中的副本?Çüé_âäàåçêëèïîì_9_PÄ.ACL 58910 字节Tue Jun 04 19:44:48 2013Çüé_âäàåçêëèïîì_9_PÄ.AC 53186 字节Tue Jun 04 19:44:48 2013";
		// String temp = getLocValues("All previously saved.*");
		// if(msg.contains("TableTableTable"))
		// sleep(0);

		String allString = exps;
		if (!cons.equals("")) {
			if (exps.equals(""))
				allString = cons;
			else
				allString += "|" + cons;
		}

		if (allString.trim().equals(""))
			return false;

		String san_nonError = allString;
		String san_msg = msg;

//		if (!ProjectConf.appLocale.matches("(?i)Zh|Ko|Ja")) {
//			san_msg = sanitizeText(msg);
//			san_nonError = sanitizeText(allString);
//		}

		if (msg.matches(exps) || msg.contains(exps)
				|| san_msg.matches(san_nonError) || exps.contains(msg)) {
			return matched = true;
		}
		// if(!ProjectConf.appLocale.equalsIgnoreCase("En")){
		String[] expArray = exps.split("\\|");
		for (int i = 0; i < expArray.length; i++) {
			if (msg.matches("(?i).*" + expArray[i] + ".*")) {
				// msg_en = expArray[i];
				return matched = true;
			}
		}

		return matched;
	}

	// logTAFInfo("***********'"+msg +"' matches '"+nonError +" = " +
	// san_msg.matches(nonError));

//	public boolean dismissPopup(String winClass, String winTitle,
//			String userAction, boolean isInfoUser, boolean loop, int maxCheck,
//			String expInfo) {
//		// dismissPopup("#32770","ACL Wizard Error|"+LoggerHelper.autTitle,"OK",
//		// false,false,1, " continue");
//
//		// ***************** Message related **************************
//
//		String platformVariants = "Confirm Save As";
//		String fatalError = "Network Error";
//		String vsError = "Visual Studio .* Debugger.?" + "";
//		String progressBar = "Status of Task" + "|.*Progress\\.\\.\\."
//				+ "|Import - .*" + "";
//		String incompletedMsg = ".*Valid choices are";
//		String progressMsg = ".*may take some time.*";
//		String aclseError = "Time display formats must.*";
//		String connectionError = "The connection to the server has been lost.*";
//		// *************************************************************
//		String workingTitle = "(?i).*.ACL - " + LoggerHelper.autTitle;
//		String minimizeTitle = "(?i)Jenkins.*" + "|(?i)Playback.*";
//		// ******** Default Pop up captions *******
//		String winTitleDefault = "ACL|Aclwin|"
//				+ LoggerHelper.autTitle
//				+ ".?|Security.?"
//				+ "|Project.?|Save Project As.?|Save New Project As.?"
//				+ "|Confirm Save As.?|Network Error.?|Warning.?|Security Warning.?"
//				+ "|ACL Error.?|Error.?" + "|Automatic Updates.?"
//				+ "|Save the file as.?" + "|Save file as.?:?"
//				+ "|Visual Studio .* Debugger.?" + "|Option files is missing.?"
//				+ "|Status of Task.?" + "|.*Progress\\.\\.\\." + "|Import - .*"
//				+ "|.*Error.*" + "";
//		String winClassDefault = "#32770";
//		// ***********************************************
//
//		// *********** actions
//		// *********************************************************
//		String[] actions = { ".*[Oo][kK].*", "Yes", "Restart Later", "Close",
//				"Cancel", "Don't Send", "No", "X" };
//		String[] actions2 = { "Restart Later", "Cancel", "Don't Send", "Close",
//				"X", "No", ".*[Oo][kK].*", "Yes" };
//		// *****************************************************************************
//
//		// ******** Report *************************
//		String msgPrefix = "[Popup Message] - \n\t\t";
//		String userPre = "User Decision: ";
//		String autoPre = "Automation Decision: ";
//		String actualTitle = "";
//		String reportTitle = "";
//
//		// ******************************************
//
//		// Step: 1 Preparing...
//		// *********** Controls **************
//		int numCheck = 0;
//		int QACheckTime = 5;
//		boolean dismissed = false;
//		boolean tryAgain = true;
//		boolean notFound = true;
//		boolean isInfo = isInfoUser;
//		boolean isProgressMsg = false;
//
//		TopLevelTestObject popup = null;
//		TestObject msgBox;
//		TestObject popupObject;
//		GuiTestObject userButton;
//		GuiTestObject autoButton;
//
//		// *******************************************
//
//		// *** Pattern represented informations which are not considered as
//		// Error
//		String nonErrors = "All previously saved.*"
//				+ "|.*Do you want to save the changes.*"
//				+ "|.*Namespace Tree Control.*"
//				+ "|.*will attempt to harmonize these fields.*"
//				+ "|.*Do you wish to continue.*"
//				+ "|.*Do you want to proceed.*"
//				+ "|.*Do you want to continue.*"
//				+ "|.*Do you still want to use.*"
//				+ "|.*is from a previous version.*"
//				+ "|.*Options file missing.*" + "|.*Are you sure you want to.*"
//				+ "|.*ACL preferences file.*" +
//				// "|.*This may take some time.*"+
//				// "|.*Delete.*"+
//				"";
//		String nonErrors_cons = "Automatic Updates" + "|Edit";
//		String passInfo = ".*Test Passed.*";
//		String expError = "";
//
//		// *** Max number of loops, negative number means infinite
//		if (maxCheck <= 0) {
//			maxCheck = QACheckTime;// Integer.MAX_VALUE;
//		}
//		if (userAction.equals("Yes"))
//			sleep(0);
//		// *** Title,Class and actions *************************
//		if (winTitle.trim().equals("")) { // Title 0 - use specified caption
//			winTitle = getLocalizedWinTitle(winTitleDefault); // Title 1 - check
//																// default
//																// caption
//																// pattern
//		}
//
//		if (winClass.equals("")) { // Class 0 - user input
//			winClass = winClassDefault; // Class 1 - default to #32770
//		}
//		if (userAction.equalsIgnoreCase("Any")) { // Action 1 - user input
//													// Action 2 - default
//			userAction = "";
//		}
//		// Step 2. Begin with finding popup windows.....
//		// if(winTitle.contains("ACL Wizard Error")||expInfo.contains("continue")){
//		// sleep(0);
//		// }
//
//		while (tryAgain
//				&& numCheck < maxCheck
//				&& (popupObject = findTopLevelWindow(winTitle, winClass)) != null) {
//			try {
//				popup = new TopLevelTestObject(popupObject);
//			} catch (Exception e) {
//				logTAFDebug("Window not found " + e.toString());
//				return false; // It's not a toplevelwindow;
//			}
//
//			notFound = false;
//
//			// 1. *** popup found, get the actual Title then convert to english
//			IWindow iw = null;
//
//			try {
//				iw = getScreen().getActiveWindow();
//				actualTitle = iw.getText();
//			} catch (Exception e) {
//			}
//			try {
//				actualTitle = popup.getProperty(".name").toString();
//			} catch (Exception e) {
//				actualTitle = "";
//			}
//			if (actualTitle.equals("")) {
//				try {
//					actualTitle = getActiveWinTitle();
//				} catch (Exception e) {
//					actualTitle = "";
//				}
//			}
//
//			if (actualTitle.matches(minimizeTitle)) {
//				iw.minimize();
//				return false;
//			} else if (actualTitle.matches(workingTitle)) {
//				return false;
//			} else if (isProgressMsg) {
//				return false;
//			} else if (actualTitle.equals(LoggerHelper.autTitle)
//					&& numCheck > 3 && maxCheck == QACheckTime) {
//				try {
//					while (iw != null
//							&& actualTitle.equals(LoggerHelper.autTitle)
//							&& iw.isShowing()) {
//						numCheck++;
//						LoggerHelper.logTAFInfo("Too many " + iw.getText()
//								+ " - Errors, close the pop up");
//						iw.close();
//						sleep(1);
//						iw = ObjectHelper.getDialog(LoggerHelper.autTitle,
//								winClass, true);
//						actualTitle = iw.getText();
//						if (numCheck >= maxCheck) {
//							logTAFWarning("It seems something wrong, pleas check the log for details");
//							stopApp();
//						}
//					}
//
//				} catch (Exception e) {
//					return true;
//				}
//				if (!actualTitle.equals(LoggerHelper.autTitle)) {
//					continue;
//				} else {
//					numCheck++;
//				}
//			} else {
//				numCheck++;
//			}
//
//			String locTitle = actualTitle.replaceAll("%\\s", "");
//
//			String[] titleArray = getEngValue(locTitle).split("\\|");
//			actualTitle = titleArray[titleArray.length - 1];
//
//			if (locTitle.matches(locWinTitleDefault)
//					|| actualTitle.matches(winTitleDefault)) {
//				// actions = actions;
//			} else // if(!winTitle.matches(winTitleDefault)||
//					// !winTitleDefault.contains(winTitle))
//			{
//				actions = actions2; // if it's a popup other than those in
//									// default,
//									// the default actions changes: cancel first
//			}
//			// ********************************************************
//			// 2. *** Wait if in progress....
//
//			if (actualTitle.contains("Error"))
//				sleep(0);
//			if (actualTitle.matches(platformVariants)) {
//				logTAFWarning("Label '" + actualTitle
//						+ "' is not an ACL message?");
//			}
//
//			else if (actualTitle.matches("Delete")) {
//				logTAFDebug("Delete warning ?");
//				isInfo = false;
//			} else if (actualTitle.matches(vsError)
//					|| actualTitle.contains("Studio")) {
//				logTAFWarning(actualTitle);
//				isInfo = false;
//				userAction = "No";
//			} else if (actualTitle.matches("Error")) {
//				// logTAFWarning("Error ?");
//				// userAction = "OK";
//				isInfo = false;
//			}
//
//			if (actualTitle.matches(getLocValues(progressBar))) {
//				int maxProcessTime = 3, atime = 0;
//				while (getActiveWinTitle().equals(locTitle)
//						&& atime++ < maxProcessTime) {
//					logTAFInfo(actualTitle
//							+ " [In progress..., wait for 10 seconds]");
//					sleep(10);
//				}
//				if (getActiveWinTitle().equals(locTitle)) {
//					continue;
//				} else {
//					return false;
//				}
//			}
//
//			if (!actualTitle.equalsIgnoreCase(locTitle)) {
//				reportTitle = locTitle + "[" + actualTitle + "]";
//			} else {
//				reportTitle = locTitle;
//			}
//			// *********************************************************
//
//			// 3. *** Get message from the popup
//			String msg = "";
//			if ((msgBox = findStatictext(popup)) != null) {
//				Object msgobj = msgBox.getProperty(".text");
//
//				if (msgobj == null)
//					msgobj = msgBox.getProperty(".name");
//				if (msgobj == null)
//					msgobj = msgBox.getDescriptiveName();
//				if (msgobj != null) {
//					msg = msgobj.toString();
//					// logTAFInfo("Message of "+ actualTitle +" is: '"+msg+"'");
//					logTAFDebug("Message of " + actualTitle + " is: '" + msg
//							+ "'");
//				} else
//					logTAFDebug("Message of " + actualTitle
//							+ " Popup not found?");
//			} else {
//				logTAFDebug("Message of " + actualTitle + " Popup not found");
//			}
//			// 4. *** Analyse message, determine if it's information or error
//			// message
//			// ****a. special cases
//			if (msg.toUpperCase().startsWith(getLocValue("CLICK HERE"))) {
//				msg = "ACL Crashed?"; // For possible acl scrashing...
//				isInfo = false;
//			} else if (msg.matches(getLocValues(incompletedMsg) + ".{5,}")) { // message
//																				// completed
//				msg = "Incompleted message ?" + msg;
//				isInfo = true;
//			} else if (msg.matches(getLocValues(progressMsg))) {
//				isProgressMsg = true;
//				int maxProcessTime = 2, atime = 0;
//				while (getActiveWinTitle().equals(locTitle)
//						&& atime++ < maxProcessTime) {
//					logTAFInfo(actualTitle + " - " + msg
//							+ ", wait for 5 seconds");
//					sleep(5);
//				}
//				if (getActiveWinTitle().equals(locTitle)) {
//					continue;
//				} else {
//					return false;
//				}
//			} else if (msg.matches(getLocValues(incompletedMsg))) { // message
//																	// incompleted.
//				msg = "Incompleted message ?" + msg;
//				isInfo = false;
//			} else if (removeLineFeed(msg)
//					.matches(
//							removeLineFeed(getLocValues(".*is from a previous version.*")))) {
//
//				aclVersionUpdate = true;
//				isInfo = true;
//			} else if (msg.matches(getLocValues(aclseError))) {
//				isInfo = false;
//			} else if (msg.matches(getLocValues(connectionError))) {
//				msg = autoIssue + msg;
//				isInfo = false;
//			}
//
//			if (nonError.equals("")) {
//				// nonError = getLocValues(".*Do you still want to use.*");
//				nonError = removeLineFeed(getLocValues(nonErrors));
//			}
//			if (nonError_cons.equals(""))
//				nonError_cons = removeLineFeed(getLocValues(nonErrors_cons));
//
//			if (!expInfo.equals("")) {
//				expError = removeLineFeed(getLocValues(expInfo));
//			}
//			// String debugString = "具有此名称的 n 条目 已存在";
//			// //if(msg.contains("already exists")){
//			// if(msg.contains(debugString)){
//			// LoggerHelper.logTAFDebug("'"+msg+"'");
//			// }
//
//			msg = removeLineFeed(msg);
//			// if(msg.contains("ACL concluyó de manera anormal mientras")||msg.equals("")){
//			// sleep(0);
//			//
//			// }
//
//			if (isMatched(msg, expError, "")) {
//				isInfo = true;
//			} else if (isMatched(msg, nonError, nonError_cons)) {
//
//				isInfo = true;
//			} else if (!expInfo.equals("")) { // if user specific info, user
//												// user specific true|false
//				isInfo = isInfoUser;
//			} else if (numCheck > 1) { // All following popups are treated as
//										// infomation,
//										// we only report possible error from
//										// the first
//				isInfo = true;
//			}
//
//			if (msg.matches(passInfo)) {
//				sleep(10); // special for executing command
//			}
//			// logTAFInfo(nonError+" = '"+msg+"' "+(msg.matches(nonError)||numCheck>1));
//
//			// 5. *** Apply action(s) to the popup message
//			// ****a. apply user specified actions
//			if (!userAction.equals("")) {
//				// if(userAction.matches(".*Working|.*Last-saved|.*Cancel"));
//
//				if (userAction.equalsIgnoreCase("X")) {
//					reportDismissedWin(reportTitle, msg, isInfo);
//					popup.close();
//				} else if (userAction.equalsIgnoreCase("ENTER")) {
//					reportDismissedWin(reportTitle, msg, isInfo);
//					getScreen().inputKeys("{" + userAction + "}");
//				} else if ((userButton = findPushbutton(popup, userAction)) != null) {
//					if (isEnabled(userButton)) {
//						reportDismissedWin(reportTitle, msg, isInfo);
//						click(userButton, userPre + userAction);
//						dismissed = true;
//					} else {
//						dismissed = false;
//					}
//				}
//				// if(userAction.matches(".*Working|.*Last-saved|.*Cancel")){
//				// printObjectTree(popup);
//				// sleep(0);
//				// }
//			} else {
//				// ****b. Apply default actions - trying one by one until button
//				// found
//				for (int act = 0; act < actions.length; act++) {
//					if (actions[act].equalsIgnoreCase("X")) { // Close the popup
//						reportDismissedWin(reportTitle, msg, isInfo);
//						getScreen().getActiveWindow().close();
//						try {
//							getScreen().getActiveWindow().close();
//							// popup.close();
//							dismissed = true;
//						} finally {
//							break;
//						}
//					} else {
//						if ((autoButton = findPushbutton(popup, actions[act])) != null) {
//							// if(msg.matches(getLocValues(".*harmonize.*")))
//							// logTAFDebug("Taking Action["+act+"]: "+autoPre+actions[act]);
//							reportDismissedWin(reportTitle, msg, isInfo);
//							click(autoButton, autoPre + actions[act]);
//							dismissed = true;
//							break;
//						}
//					}
//				}
//			}
//			// 6. *** Check if dismissed sucessfully
//			if (!dismissed) {
//				// ****a. close active window and report
//				notFound = true;
//				if (userAction.equals("")) {
//					logTAFWarning("Failed to dismiss this "
//							+ actualTitle
//							+ " by submit any button actions, we will try to use 'X' to close it now");
//					try {
//						reportDismissedWin(reportTitle, msg, isInfo);
//						getScreen().getActiveWindow().close();
//						// popup.close();
//						dismissed = true;
//					} finally {
//
//					}
//				} else {
//					logTAFDebug("Failed to dismiss Popup Message by action on '"
//							+ userAction + "' - '" + actualTitle + "': " + msg);
//				}
//			} else {
//				// **** b. just report
//				// if(isInfo||numCheck>1){
//				// logTAFInfo("Popup Message - '"+actualTitle+"': "+msg);
//				// }else{
//				// logError(msg);
//				// }
//			}
//
//			// 7. cleanup this loop
//
//			if (!loop) { // use decition
//				tryAgain = false;
//			} else { // for following loop, use defaults only!
//				sleep(1);
//				userAction = "";
//				winTitle = getLocalizedWinTitle(winTitleDefault);
//				winClass = winClassDefault;
//			}
//		}// End while
//
//		return dismissed;
//	}
//
//	private static String locWinTitleDefault = "";
//
//	private String getLocalizedWinTitle(String winTitleDefault) {
//		if (locWinTitleDefault == "")
//			locWinTitleDefault = getLocValues(winTitleDefault);
//		return "Localized:" + locWinTitleDefault;
//	}
//
//	private void reportDismissedWin(String winTitle, String msg, boolean isInfo) {
//		String fatalError = "(?i).*cannot [\\s]*access.*"
//				+ "|(?i).*You cannot use the Command Line when a script is running.*"
//				+ "";
//		String indexError = "|Cannot overwrite open Index.*"
//				+ "|'EMPNOINX' is in use and cannot be modified.*"
//				+ "|Disk file.*EMPNOINX\\.INX already exists, overwrite\\?.*"
//				+ "";
//		String l10nError = ".*Missing closing parenthesis.*";
//		String emptyProject = ".*not an ACL project.*";
//		String progressBar = "Status of Task" + "|.*Progress\\.\\.\\."
//				+ "|Import - .*" + "";
//
//		String progressMsg = ".*may take some time.*";
//		String projectWarning = " - ACL terminated abnormally? - if it's not a negative test, check the data pool to ensure opennew used for each test suite";
//		String msg_en = "";
//		if (msg.trim().equals("") && !isInfo) {
//			msg_en = projectWarning;
//			msg = autoIssue
//					+ getLocValue(".*" + projectWarning + ".*").split("\\|")[0];
//			if (!msg_en.equals("") && !msg.matches(msg_en)) {
//				msg += "[Possible english:" + msg_en + "]";
//			}
//		} else if (!ProjectConf.appLocale.equalsIgnoreCase("En")) {
//			msg_en = getEngValue(msg);
//			if (!msg_en.equals(msg)
//					&& !(msg_en.matches("^\\s*['\"(\\[]?%[scd]['\")\\]]?\\s*"))
//					&& !(msg_en
//							.matches("^[\\\\]?\\s*['\"(\\[]?.[*+?]['\")\\]]?[\\\\]?\\s*"))
//					&& !(msg).contains(msg_en) && !msg_en.contains(msg)) {
//				String[] msgArray = msg_en.split("\\|");
//				// msg_en = msgArray[msgArray.length-1]; // Debug...
//				msg_en = msgArray[0];
//				msg_en = removeLineFeed(msg_en);
//				msg_en = GuiFinderHelper.removePattern(msg_en);
//				if (!msg_en.equals("") && !msg.matches(msg_en))
//					msg += "[Possible english:" + msg_en + "]";
//			}
//		} else {
//			// logTAFInfo("English: "+msg_en);
//		}
//
//		if (isInfo && (!msg.matches(fatalError) || !msg_en.matches(fatalError))
//				&& !msg.matches(indexError)) {
//			logTAFInfo("Popup Message - '" + winTitle + "': " + msg);
//		} else if (winTitle.matches(progressBar) || msg.matches(progressMsg)) {
//			logTAFInfo("Still In progress...?, wait for one more minute");
//			sleep(60);
//		} else {
//			if (msg.matches("(?i)" + l10nError + "|" + emptyProject)) {
//				logTAFError(autoIssue + winTitle + "-" + msg);
//			} else {
//				logError(winTitle + "-" + msg);
//			}
//		}
//	}
//
//	public String getStaticValueByLabel(TestObject anchor, String label) {
//		return getStaticValueByLabel(anchor, label, false);
//	}
//
//	public String getStaticValueByLabel(TestObject anchor, String label,
//			boolean isChild) {
//		String svalue = "";
//		TestObject lab, item;
//		int labint = -1, svalint = -1;
//
//		lab = findTestObject(anchor, true, ".class", "Static", ".name", label);
//		if (lab != null) {
//			try {
//				labint = Integer.parseInt(lab.getProperty(".classIndex")
//						.toString());
//				svalint = labint + 1;
//			} catch (Exception e) {
//
//			}
//		}
//
//		item = findTestObject(anchor, true, ".class", "Static", ".classIndex",
//				svalint + "");
//		if (item != null) {
//			svalue = item.getProperty(".name").toString();
//		}
//		return svalue;
//
//	}
//

	public ObjectHelper() {
		foundObjs = new ArrayList<Object>();

	}

	public static void main(String[] args) {
		String dpMasterFile = "D:\\ACL\\TFSView\\RFT_Automation\\QA_Automation_2012_V2.0\\ACLQA_Automation\\ACL_Desktop\\DATA\\ExpectedData\\NonUnicode\\en_US\\Analyze\\Benford\\BenfordTest_10.FIL";
		String dpActualFile = "P:\\RFT_DATA\\TempData\\Steven_Xiang\\Windows_XP_IP4_124\\Analyze\\Benford\\BenfordTest_10.FIL";
		new ObjectHelper().compareTextFile(dpMasterFile, dpActualFile, false,
				"File");
	}

}