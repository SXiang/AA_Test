package com.acl.qa.taf.helper.superhelper;

import ibm.loggers.control.PackageLoggingController;
import ibm.loggers.targets.ConsoleTarget;
import ibm.loggers.targets.FileTarget;

import java.util.Calendar;

import com.acl.qa.taf.util.FileUtil;
import com.acl.qa.taf.util.FormatHtmlReport;


public class TAFLogger extends ibm.loggers.TargettedLogger {

	private static boolean configured = false;
	
	private static TAFLogger tLog;
	private String projectName = ACLQATestScript.projectConf.projectName;
	
	public static String testName = "";
	private String tafFolder = "TAF_Logs/";
	
	public static String 
	                  path, 
	                  name, time, 
	                  file,
	                  filePre, locale,
	                  screenShots;
	public static int year, month, day;
	public static String timeOfTest;
	public static String testResultXLS,
	                     testResultTXT,
	                     testResultTempTXT,
	                     testResultRFT,
	                     testResultHTML,
	                     memusageCSV;

	 // name must be set / changed before getlogger();
	private TAFLogger() {	 
		super(ACLQATestScript.loggerConf.iLogLevel);
		
		locale = FileUtil.locale.toString();
		
		file = getFileFullName(name);
		screenShots = file+"/ScreenShots";
		testResultXLS = path + tafFolder + name + time + ".xls";
		testResultTXT = path + tafFolder + name + time + "[Log]"+".html";
		testResultHTML = path + tafFolder + name + time + "[Report]"+".html";
		testResultTempTXT = path + tafFolder + name + time + "_tempSummary.txt";
		memusageCSV = path + tafFolder + name + time + "_memusage.csv";
		//memusageCSV = path + tafFolder + time + "_memusage.csv";
		testResultRFT = file + "/rational_ft_log.html";
		clearAllTargets();
		appendTargetToAll(new FileTarget(testResultTXT));
		
		appendTargetToAll(new ConsoleTarget());
		
		PackageLoggingController.setLogger(this);
		
		//System.out.println("Logger Created for '"+testResultTXT+"'");
	    this.logScriptInfo(FormatHtmlReport.addReportHeader("Test Log"));
		//configured = true;
	}
	
	public TAFLogger(String sName){
		this();
	}
	
	public static TAFLogger getLogger(){
		if(tLog == null){
			tLog = new TAFLogger();
		}
		return tLog;
	}
	public static TAFLogger updateLogger(){
		if(!configured||tLog==null){
			tLog = new TAFLogger();		
			configured = true;
		}
		return tLog;
	}
	private String getFileFullName(String fname){
		String monthArray[] = {"JAN","FEB","MAR","APR","MAY",
				"JUN","JUL","AUG","SEP","OCT","NOV","DEC"};

		Calendar cal = Calendar.getInstance();
		
		timeOfTest = cal.getTime().toString();
		name = fname;
		
		year = cal.get(Calendar.YEAR);
		month = (cal.get(Calendar.MONTH) + 1);
		day = cal.get(Calendar.DAY_OF_MONTH);
		time = "_" + cal.get(Calendar.HOUR_OF_DAY) 
				+ "_" + cal.get(Calendar.MINUTE) 
				+ "_" + cal.get(Calendar.SECOND);
		
		path = ACLQATestScript.loggerConf.logRoot + testName 
				+ "/" + year + "/" + locale
				+ "/" + monthArray[month-1] + "/" + day + "/";
		
		filePre = path + name;
		
		return filePre + time;
	}
}
