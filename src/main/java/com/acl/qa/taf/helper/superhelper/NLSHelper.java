package com.acl.qa.taf.helper.superhelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.acl.qa.taf.util.NLSUtil;

/**
 * Description : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since February 03, 2012
 */

public abstract class NLSHelper extends NameAndFormatHelper {

	// 4 methods for translation eng <-> loc - Steven

	static String idPattern = "lv_.*";

	public static String getLocValues(String name) {
		return getLocValues(name, "");
	}

	public static String getLocValues(String name, String className) {
		String value = NLSUtil.convert2Locale(name, className);
		if (!value.equals("") && !value.contains(name)) {
			value += "|" + name;
		}
		logTAFDebug("LocaleValuesConvert: " + name + " = '" + value + "'");
		return GuiFinderHelper.trimExp(value);
	}

	public static String getLocValue(String name) {
		return getLocValue(name, true);
	}

	public static String getLocValue(String name, boolean truncate) {
		return getLocValue(name, "", truncate);
	}

	public static String getLocValue(String name, String className) {
		return GuiFinderHelper.trimExp(getLocValue(name, className, true));
	}

	public static String getLocValue(String name, String className,
			boolean truncate) {
		if (GuiFinderHelper.isPattern(name)) {
			return getLocExp(name, className, truncate);
		} else {
			// return getLocExp(name,className,truncate);
			return getLocExp(name, className, truncate).split("\\|")[0];
		}
	}

	public static String getLocExp(String name, String className,
			boolean truncate) {

		String pre = "SingleLocValue_";

		String value = NLSUtil._convert2Locale(pre + name, className, truncate);
		logTAFDebug("LocaleValueConvert: " + name + " = '" + value + "'");
		return GuiFinderHelper.trimExp(value);
	}

	public static String getEngValues(String name) {
		return getEngValues(name, "");
	}

	public static String getEngValues(String name, String className) {
		String value = NLSUtil.convert2English(name, className);
		if (!value.equals("") && !name.contains(value)) {
			value += "|" + name;
		}
		logTAFDebug("EnglishValuesConvert: " + name + " = '" + value + "'");
		return GuiFinderHelper.trimExp(value);
	}

	public String getEngValue(String name) {
		return getEngValue(name, "");
	}

	public String getEngValue(String name, String className) {
		String pre = "SingleLocValue_";

		String value = NLSUtil._convert2English(pre + name, className);
		logTAFDebug("EnglishValueConvert: " + name + " = '" + value + "'");
		value = GuiFinderHelper.trimExp(value);
		if (GuiFinderHelper.isPattern(name)) {
			return value;
			// return GuiFinderHelper.correctPattern(value);
		} else {
			// return value;
			return value.split("\\|")[0];
		}
	}

	public String replaceLocAll(String pattern, String input) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		String suffix = "[\\s]*[:]?[\\s]*['\"%Iclds\\d]*[\\^]?[\\s]*$"
				+ "|[\\s]*[:]?[\\s]*['\"]?[%][\\d]*[l]?[Idsc]['\"]?.*$";
		String prefix = "";// "([\\s]*[\\^]?[\\s]*['\"%ds\\d]*[\\s]*";

		String[] idTrans = { "(?i)Count", "lv_ACLTXT_RH_SX_630_ID" };
		String temp = "";
		while (m.find()) {
			boolean isID = false;
			String captured = m.group(2);

			for (int i = 0; i < idTrans.length - 1; i = i + 2) {
				// System.out.print(" ^^^^^ "+captured+" matches("+idTrans[i]+") = "+captured.matches(idTrans[i]));
				if (captured.matches(idTrans[i])) {
					captured = idTrans[i + 1];
					isID = true;
					// System.out.print(captured+" =? "+idTrans[i+1]);

				}
			}
			if (!isID && captured.length() > 1
					&& captured.equals(captured.toUpperCase())) {
				captured = captured.charAt(0) + captured.substring(1);
			}

			String replacement = "";
			temp = captured;
			if (!isID) {
				temp = captured.replaceAll("[^\\.][\\*]+", "[\\*]+");
				replacement = getLocValue(prefix + temp + "(" + suffix + ")",
						false);
				if (replacement.equals(prefix + temp + "(" + suffix + ")")
						|| (prefix + temp + "(" + suffix + ")")
								.contains(replacement)
						|| replacement.matches(prefix + temp + "(" + suffix
								+ ")")) {
					replacement = captured;
				} else if (replacement != null) {
					replacement = replacement.replaceAll(suffix, "");
				}
			} else {
				replacement = getLocValue(captured, false);
			}

			if (replacement != null && !replacement.equals("")) {
				// replacement = replacement.replaceFirst("^"+prefix, "");
				// replacement = replacement.replaceAll(suffix, "");

				// try{
				// if(m.group(2).equals(m.group(2).toUpperCase())){
				// replacement = replacement.toUpperCase();
				// }else if(m.group(2).equals(m.group(2).toLowerCase())){
				// replacement = replacement.toLowerCase();
				// }
				// }catch(Exception e){
				//
				// }
				replacement = m.group(1) + replacement + m.group(3);
				// m.appendReplacement(sb, replacement);
				m.appendReplacement(sb, "");
				sb.append(replacement);
			}
		}
		m.appendTail(sb);

		return sb.toString();
	}

	public String replaceEngAll(String pattern, String input) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(input);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String replacement = getEngValue(m.group(1));
			if (replacement != null && !replacement.equals("")) {
				// m.appendReplacement(sb, replacement);
				m.appendReplacement(sb, "");
				sb.append(replacement);
			}
		}
		m.appendTail(sb);

		return sb.toString();
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
}
