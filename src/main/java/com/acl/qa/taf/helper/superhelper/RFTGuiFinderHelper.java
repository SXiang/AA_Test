package com.acl.qa.taf.helper.superhelper;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import com.acl.qa.taf.util.FileUtil;

/**
 * Description : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since March 19, 2012
 */

// while((iw=getDialog(winTitle,className))!=null
// &&numCheck++<maxCheck){
// actualTitle = iw.getText();
public abstract class RFTGuiFinderHelper extends WinTreeHelper {
	// public static int defaultMatchFlag //= Regex.MATCH_CASEINDEPENDENT;
	// //= Regex.MATCH_MULTILINE;
	// = Regex.MATCH_NORMAL;
	public static boolean caseSensitive = false;
	public static String defaultTestDomain = "Win";
	public static String defaultWinClass = "#32770";
	public static String classKey = ".class";
	public static String nameKey = ".name";
	public static String textKey = ".text";
	public static String indexKey = ".classIndex";

	boolean found = false;

	public static boolean pickDateTime(String winTitle, int calIndex,
			Calendar calendar) {
		int uYear, uMonth, uDay;
		String controlID = "";
		String className = "SysDateTimePick32";
		String util = "lib/acl/tool/DatePicker";

		uYear = calendar.get(Calendar.YEAR);
		uMonth = calendar.get(Calendar.MONTH) + 1;
		uDay = calendar.get(Calendar.DAY_OF_MONTH);
		// DatePicker.exe Age,,[CLASS:SysDateTimePick32;INSTANCE:1],1905,04,15
		String args = winTitle + ",,[CLASS:" + className + ";INSTANCE:"
				+ (calIndex + 1) + "]," + uYear + "," + uMonth + "," + uDay;

		FileUtil.runExecutable(util, args);
		return true;
	}

	public static String removePattern(String pattern) {
		String[] ori = { "\\s", "\\\\[.]" };
		String[] rep = { " ", "." };
		for (int i = 0; i < ori.length; i++) {
			pattern = pattern.replace(ori[i], rep[i]);
		}
		pattern = trimString(pattern, "\\.\\*");
		pattern = trimString(pattern, "\\.\\+");
		return pattern;
	}

	public static String correctPattern(String pattern) {
		if (pattern
				.contains("Arbeitskopie ist kleiner oder älter als das Projekt")) {
			// sleep(0);
		}
		String skipS = ".*[\\.][\\+\\*]$" + "|^[\\.][\\+\\*].+"
				+ "|.*[\\.][\\+?\\*][\\|].*" + "|.*[\\|][\\.][\\+?\\*].*";
		if (pattern.matches(skipS)) {
			return pattern;
		}
		// if(!isPattern(pattern))
		// return pattern;
		// String[] ori = {
		// // "\\\\n|\\\\r"
		// "\\n|\\r"
		// ,"\\s" //Spaces
		//
		// ,"\"\""
		// //,"\\-"
		// ,"([^\\\\]?)[\\(]" // ( and )
		// , "([^\\\\]?)[\\)]"
		// , "([^\\\\])([\\.\\+\\*])"
		// ,"(['\"]?)%[\\-+]?[I]?[\\d]*[l]?[Icdsi](['\"]?)"
		// ,"(['\"]?)%[\\-+]?[I]?[\\d]*xh(['\"]?)"
		// ,"(['\"]?)%\\(0x%X\\)(['\"]?)"
		// ,"\\?"
		// ,"[\\\\]+"// In case of duplicates
		// };
		// String[] rep = {
		// // "/"
		// "/"
		// ,"\\\\s"
		//
		// ,"\""
		// //,"\\\\-"
		// ,"$1\\\\("
		// ,"$1\\\\)"
		// ,"$1\\\\$2"
		// ,"$1.+$2"
		// ,"$1.+$2"
		// ,"$1.+$2"
		// ,"\\\\?"
		// ,"\\\\"
		// };
		String[] ori = {
				sysLineSep,
				"\\n|\\r[ ]?",
				"\\s" // Spaces
				,
				"\"\"",
				"([^\\\\]?)[\\(]" // ( and )
				,
				"([^\\\\]?)[\\)]",
				"([^\\\\])([\\.])([^\\+\\*\\?])" // escape .
				,
				"([^\\.%])([\\+\\*\\?])" // escape +*?
				, "(['\"]?)%[\\-+]?[I]?[\\d]*[l]?[Icdsi](['\"]?)",
				"(['\"]?)%[\\-+]?[I]?[\\d]*xh(['\"]?)",
				"(['\"]?)%\\(0x%X\\)(['\"]?)", "\\?", "[\\\\]+"// In case of
																// duplicates
		};
		String[] rep = {
				// "/"
				"", "", "\\\\s", "\"", "$1\\\\(", "$1\\\\)", "$1\\\\$2$3",
				"$1\\\\$2", "$1.+$2", "$1.+$2", "$1.+$2", "\\\\?", "\\\\" };
		for (int i = 0; i < ori.length; i++) {
			pattern = pattern.replaceAll("(?i)" + ori[i], rep[i]);
		}

		// pattern = pattern.replaceAll("\\s", "\\\\s");
		// logTAFDebug("Pattern: "+pattern);

		return trimExp(pattern);
	}

	public static String _correctPattern(String pattern) {
		String skipS = ".*\\.[+*]$" + "|^\\.[+*?].+" + "|.*\\.[+?*]\\|.*"
				+ "|.*\\|[.][+?*].*";
		if (pattern.matches(skipS)) {
			return pattern;
		}
		// if(!isPattern(pattern))
		// return pattern;
		String[] ori = { sysLineSep, "\\n|\\r[ ]?", "([^\\\\])([.+*()])",
				"(['\"]?)%[\\d]*[Icds](['\"]?)", "[?]" };
		String[] rep = { ""
				// "/"
				, "", "$1\\\\$2", "$1.+2", "\\\\?" };

		for (int i = 0; i < ori.length; i++) {
			pattern = pattern.replaceAll(ori[i], rep[i]);
		}

		// pattern = pattern.replaceAll("\\s", "\\\\s");
		// logTAFDebug("Pattern: "+pattern);

		return trimExp(pattern);
	}

	public static boolean isPattern(String str) {
		if (str.matches(".*[\\[\\]\\|\\*+?].*" + "|.*\\(\\?i\\).*" + "|i{0}")) {
			// LoggerHelper.logTAFWarning(str+ " is a reg pattern");
			return true;
		} else {
			// LoggerHelper.logTAFWarning(str+ "is not a reg pattern");
			return false;
		}
	}

	public static String localizeProps(String value) {
		String temp = getLocValues(value);
		String lvalue1 = "", lvalue2 = "";

		// if(value.contains("ACL Wizard Error"));

		if (value.equalsIgnoreCase(temp)) {
			return value;
		} else if (isValidPattern(lvalue1 = correctPattern(temp))) {
			// if(isValidPattern(lvalue2 = correctPattern(value)+"|"+lvalue1)){
			// value = lvalue2;
			// }else{
			value = lvalue1;
			// }
		} else {
			value = temp;
		}

		return value;
	}

	public static void localizeProps(String... pairs) {
		String localProp = ".name|name|.text|text"; // For localization project
		String className = getClassName(pairs);
		boolean isdbtn = true;// isDButton(className);
		String temp = "";

		for (int i = 0; i < pairs.length - 1; i = i + 2) {
			if (pairs[i].matches(localProp)) { // Localized value
				temp = getLocValues(pairs[i + 1], className);
				if (pairs[i + 1].equals("ACL_CmdLine_RunBtn")) {
					className = className;
				}

				logTAFDebug("\t\t\t Convert " + pairs[i] + "-" + pairs[i + 1]
						+ " -> '" + temp + "'");
				if (isValidPattern(pairs[i + 1] + "|" + temp)
						&& !pairs[i + 1]
								.matches("(.*\\|)?" + temp + "(\\|.*)?")) {
					if (!temp.equals(""))
						pairs[i + 1] += "|" + temp;
				} else {
					pairs[i + 1] = temp;
				}
				// .correctPattern("");
				pairs[i + 1] = trimExp(pairs[i + 1]);
			}
		}
	}

	public static String trimExp(String from) {
		// return from;
		from = from.replaceAll("\\|\\|", "|");
		from = from.replaceAll("\\|\\s[.][\\*\\+\\?]\\s\\|", "|");
		return trimString(from, "\\|");
	}

	public static String trimString(String from, String st) {

		from = from.replaceAll("^" + st, "");
		from = from.replaceAll(st + "$", "");
		return from;
	}

	public static boolean isDButton(String className) {
		String[] dButton = { "BUTTON", "MENUITEM" };
		String[] dLang = { "zh", "ja", "ko" };
		boolean isdbtn = false;

		for (int i = 0; i < dLang.length; i++) {
			if (FileUtil.locale.getLanguage().equalsIgnoreCase(dLang[i])) {
				isdbtn = true;
				break;
			}
		}
		if (!isdbtn)
			return isdbtn;
		for (int i = 0; i < dButton.length; i++) {
			if (className.toUpperCase().contains(dButton[i]))
				return true;
		}

		return false;
	}

	public static String getClassName(String... pairs) {
		String className = "";
		for (int i = 0; i < pairs.length - 1; i = i + 2) {
			if (pairs[i].matches(classKey)) {
				className = pairs[i + 1];
				break;
			}
		}
		return className;
	}

	public static boolean isValidPattern(String sValue) {
		boolean isPattern = true;
		try {
			Pattern.compile(sValue);
		} catch (Exception e) {
			isPattern = false;
		}
		return isPattern;
	}
	// ***********************************************************************************
}
