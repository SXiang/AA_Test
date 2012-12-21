package lib.acl.helper.sup;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.imageio.ImageIO;

import lib.acl.util.FileUtil;
import lib.acl.util.NLSUtil;

//import org.apache.commons.lang.StringUtils;


import com.rational.test.ft.object.interfaces.GuiSubitemTestObject;
import com.rational.test.ft.object.interfaces.ITopWindow;
import com.rational.test.ft.object.interfaces.TestObject;
import com.rational.test.ft.object.interfaces.ToggleGUITestObject;
import com.rational.test.ft.script.Action;
import com.rational.test.ft.script.RationalTestScript;
import com.rational.test.ft.script.State;
import com.rational.test.ft.value.MethodInfo;

import conf.beans.FrameworkConf;

public abstract class MySuperHelper extends ObjectHelper {

	protected static String RFT_DP_CLASS_NAME = "class com.rational.test.ft.datapool.impl.Datapool";

	protected static final String STR_DP_WORKING = "working";
	protected static final String STR_DP_LIBRARY = "library";	
	protected static final String REP_WORKING = NLSUtil.convert2Locale(STR_DP_WORKING);
	protected static final String REP_LIBRARY = NLSUtil.convert2Locale(STR_DP_LIBRARY);

	protected static final String SEP_PATH = "/";				// separator of OS path
	protected static final String SEP_REP_PATH = "->";			// separator of repository path	
	protected static final String SEP_ITEMS = "|";				// separator of items
	protected static final String SEP_ITEM_VALUES = ";";		// separator of values of an item
	protected static final String SEP_ITEM_VALUE_FIELDS = ":";	// separator of fields of a value of an item
	protected static final String SEP_KEY_VALUE = "=";			// separator of key/value pair

	protected static final String SEP_SPACE = " ";				// ' ' separator
	protected static final String SEP_DOT = ".";				// '.' separator
	protected static final String SEP_LINES = "\n";				// separator for lines
	protected static final String SEP_POUND = "#";				// '#' separator 
	                                                           
	protected static final String SEP_POUND_ITEMS = "#;;#";	    //  There is name issue FOR SEP_POUND ,use this in some keywords - Steven
	
	protected static final String SEP_CSV = ",";
	protected static final String SEP_CSV_REPLACE = "<";

	protected static final String SPLITTER_DOT = "\\.";
	protected static final String SPLITTER_ITEMS = "\\|";

	protected static final String PATTERN_EXCLUDE = "exclude";

	protected static final boolean isWinXp = "Windows XP".equals(System.getProperty("os.name"));

	protected Point leftTopPoint = atPoint(20, 8);

	// expand the curNode (i.e. GuiSubitemTestObject rftObjSystreeview32tree2 = sysTreeView32tree2();)
	// to nodePath (e.g. dpRoot + SEP_REP_PATH + dpEngagementName + SEP_REP_PATH + dpActivityName)
	// also support AxAddins, for which the root 'Working' is omit 
	
	protected boolean _expandNode(GuiSubitemTestObject curNode, String curPath, boolean refreshAfterExpand)
	{
		boolean success = true;

		try{	
			logTAFDebug(" < **** > Expanding [" + curPath + "] of [" + curNode + "].....");
			curNode.setState(Action.expand(), atPath(curPath));
			//curNode.doubleClick( atPath(curPath));
			//curNode.setState(Action.expandAndSelect(), atPath(curPath));
		}catch(Exception e){
			try{
				logTAFDebug(" < **** > Expanding exception [" + e.toString()+ "].....");
				if(refreshAfterExpand){
				 refreshWindow((ITopWindow) (curNode.getTopParent()));
				 curNode.setState(Action.expand(), atPath(curPath));
				}else{
					throw e;
				}

			}catch(Exception er){
			    success = false;
			  //logTAFWarning(er.toString());
			}
		}
		if(!success){
			logTAFDebug(" < **** > Exception while trying to expand [" + curPath + "] of [" + curNode + "]");
		}else{
			logTAFDebug(" < **** > Expand [" + curPath + "] of [" + curNode + "] Success !");
		}
		return success;
	}

	protected boolean expandNode(GuiSubitemTestObject curNode, String nodePath){
		return expandNode(curNode,nodePath,true);
	}
	protected boolean expandNode(GuiSubitemTestObject curNode, String nodePath,boolean refreshWindow)
	{		
		boolean success = true;

		String curFullPath = "";
        String tmpFullPath;
        boolean isAxAddins = (nodePath.startsWith(REP_WORKING) || nodePath.startsWith(REP_LIBRARY))? false : true;        
				
		int i = 0;
		
//		if(refreshWindow){
//			callScript(pathToKeywordScripts + "viewMenuOperations", new Object[]{"refresh", "No"});	
//		}
		
		for (String curPath : nodePath.split(SEP_REP_PATH)) {
			curFullPath += curPath;			
			success = _expandNode(curNode,curFullPath,refreshWindow);
			if(!success)
				break;
			if ((i == 2) && (! isAxAddins)) { // expand Related Files and Data folder as well for nodePath pointing to activity
				tmpFullPath = curFullPath + SEP_REP_PATH + NLSUtil.convert2Locale("relatedFilesContainer");
				success = _expandNode(curNode,tmpFullPath,true);
				tmpFullPath = curFullPath + SEP_REP_PATH + NLSUtil.convert2Locale("dataModel");
				success = _expandNode(curNode,tmpFullPath,true);
				tmpFullPath = curFullPath + SEP_REP_PATH + NLSUtil.convert2Locale("resultSet");
				success = _expandNode(curNode,tmpFullPath,true);
				tmpFullPath = curFullPath + SEP_REP_PATH + NLSUtil.convert2Locale("analyticsContainer");
				success = _expandNode(curNode,tmpFullPath,true);
			}
			
			curFullPath += SEP_REP_PATH;
			i++;
		}
		try{
		    refreshWindow((ITopWindow) (curNode.getTopParent()));
		}catch(Exception e){
			logTAFWarning("Failed to refresh window "+e.toString());
		}
        //logTAFDebug("Trying to expand '"+nodePath+" actual expanded: '"+nodePath+"' success ? = "+success);
		
		return success;
	}


	protected static boolean isEmpty(String myString) {
		return myString == null || myString.length() == 0;
	}

	protected static String chopLastStr(String myString, String charToChop) {
		return myString.endsWith(charToChop)? 
				myString.substring(0, myString.length() - charToChop.length()) : myString;
	}

	public static boolean isEnabled(TestObject curTO) {
		
		return (curTO.exists() 
				&& curTO.getProperty(".enabled").equals(true))? true : false;
	}

	protected static boolean isVisible(TestObject curTO) {
		boolean existed = false,
		        visible = false;
		try{
			existed = curTO.exists();
			visible = curTO.getProperty(".visible").equals(true);
		}catch(Exception e){
			logTAFDebug("Object '"+curTO+" not found "+e.toString());
		}
		return existed && visible? true : false;
	}

	protected boolean expectedErr(String dpExpectedErr){
		return expectedErr(dpExpectedErr,getDpString("ExpectedErr"));
		//return expectedErr(dpExpectedErr,"");
	}
	protected  boolean expectedErr(String dpExpectedErr,String dpExpectedErrMsg){
		boolean exErr;
		exErr = (isEmpty(dpExpectedErr)
				|| "NO".equalsIgnoreCase(dpExpectedErr))? false : true;
		if(exErr){
			expectedErr = dpExpectedErrMsg;
			if(expectedErr.equals("")){
				if(!dpExpectedErr.equalsIgnoreCase("Yes"))
					expectedErr = dpExpectedErr;
				else
					expectedErr = "Any error message";
				   
			}
		}
		return exErr;
	}

	public  void logTestResult(String dpExpectedErr, String dpExpectedErrMsg, String curErrorMessage) {		
		boolean expectedErr = expectedErr(dpExpectedErr);

		if (! isEmpty(curErrorMessage)) {
			if (isEmpty(dpExpectedErrMsg) || dpExpectedErrMsg.replaceAll(" ", "").toUpperCase().contains("ANYERROR")) {
				logTestResult(expectedErr, "Got error message [" + curErrorMessage + "]");
			} else if (curErrorMessage.equalsIgnoreCase(dpExpectedErrMsg)) {
				logTestResult(expectedErr, "Got expected error message [" + curErrorMessage + "]");
			} else {
				logTestResult("Error message mismatch, Expect [" + dpExpectedErrMsg + "] <-> Actual [" + curErrorMessage + "]", false);
			}
		} else if (expectedErr && ! isEmpty(dpExpectedErrMsg)) {
			logTestResult("Not found expected error message [" + dpExpectedErrMsg + "]", false);
		}
	}

	// make up expectedErr string to make it in pair with items string
	protected String validateExpectedErrList(String itemsList, String expectedErrs) {
		if (isEmpty(expectedErrs)) {
			expectedErrs = "NO";
		}

		String[] expectedErrsArr = expectedErrs.split(SPLITTER_ITEMS);
		String[] itemsArr = itemsList.split("(?<!\\\\)\\|");

		if (expectedErrsArr.length < itemsArr.length) {
			for (int i=expectedErrsArr.length; i<itemsArr.length; i++) {
				expectedErrs += "|NO";
			}

			logTAFWarning("expectedErrs should be in pair with ItemsList. Change it to [" + expectedErrs + "] now to continue, need to update datapool!");
		}

		return expectedErrs;
	}

	//maneError method is used to report invalid input
	public boolean nameError(String name){
		boolean invalid = false;
		char[] invalidChar = {'/','\\',':','*','?','<','>','|'};

		for (char ichar:invalidChar){
			if(name.indexOf(ichar)!=-1){
				invalid = true;
				break;
			}
		}
		return invalid;
	}

	// compare 2 text files' contents, return true if they're the same
	protected boolean isSameTextFile(String f1, String f2) {
		String str1 = getFileContents(f1).toString();
		String str2 = getFileContents(f2).toString();

		return str1.equals(str2)? true : false;
	}

	// get file's contents and return the contents in StringBuffer format
	protected StringBuffer getFileContents(String fileName) {
		StringBuffer contentsBuffer = new StringBuffer();
		try {
			//FileReader freader;
			LineNumberReader lnreader;
			//freader = new FileReader(new File(fileName));
			InputStreamReader in = new InputStreamReader(new FileInputStream(fileName),"Unicode");
			lnreader = new LineNumberReader(in);

			String line;
			while ((line = lnreader.readLine()) != null){
				contentsBuffer.append(line + SEP_LINES);
			}

			//freader.close();
			lnreader.close();
		} catch (IOException e) {
			logTAFException(e);
			logTAFDebug("Exception while getFileContents: " + e.getMessage());
		}

		return contentsBuffer;
	}

	// return formated time string
	protected static java.util.Date getDateByTimeStr(String myTimeStr, String dateFormat){
		java.util.Date myDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(dateFormat);

			myDate = format.parse(myTimeStr);
		} catch (ParseException e) {
			logTAFException(e);
		}

		return myDate;
	}

	protected static String getDateStrByDate(java.util.Date myDate, String dateFormat){
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);

		return format.format(myDate);
	}

	// return month number (start from 1, i.e. January==1, December==12) by full month name
	protected int getMonthNum(String fullMonthName) {	
		Calendar cal = Calendar.getInstance();

		int curMonth = cal.get(Calendar.MONTH);

		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
		int i=0;
		for (; i<12; i++) {
			cal.set(Calendar.MONTH, i);

			if (fullMonthName.equalsIgnoreCase(sdf.format(cal.getTime()))) { 
				break;
			}
		}

		cal.set(Calendar.MONTH, curMonth);

		return (i < 12)? (i+1) : null;
	}

	// return full month name by month number which is start from 1, i.e. January==1, December==12
	protected String getFullMonthName(int monthNum) {		
		Calendar cal = Calendar.getInstance();

		int curMonth = cal.get(Calendar.MONTH);

		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
		cal.set(Calendar.MONTH, monthNum - 1);
		String monthStr = sdf.format(cal.getTime());

		cal.set(Calendar.MONTH, curMonth);

		return monthStr;
	}

	// get string of csvString (sperated by "|"), append \" for each item
	protected String getFormatedFilesString(String csvString) 
	{
		String[] strArr = csvString.split(SPLITTER_ITEMS);

		if (strArr.length == 1) {
			return csvString;
		}

		StringBuilder formatedStrSb = new StringBuilder(); 

		for (int i=0; i<strArr.length; i++) {
			// enclosed file name with "
			formatedStrSb.append("\"" + strArr[i].trim() + "\"" + " ");
		}

		return formatedStrSb.toString().trim();
	}

	protected boolean isGoldbugBuild() {
		return (FrameworkConf.buildInfo.toUpperCase().contains("GOLDBUG") || FrameworkConf.buildName.toUpperCase().contains("GOLDBUG"))? true : false;
	}

	protected TestObject getTOByBuild(TestObject curBuildTO, TestObject preCurBuildTO) {
		return isGoldbugBuild()? curBuildTO : preCurBuildTO;		
	}

	// get localised number string for en format decimal string, 
	// no matter TS provided or not Or the format is correct or not
	
	// ** Need to simplify this later - Steven
	protected static String getLocNumStr(String numStr) {
		if (isEmpty(numStr)) {
			return numStr;
		}

		final String ds_en = ".";
		final String ds_non_en = ",";
		final String ts_en = ",";

		String curLang = FileUtil.locale.getLanguage();		

		String[] numStrArr = numStr.split(SPLITTER_DOT);
		String numStrBeforeDs = numStrArr[0];
		String numStrAfterDs = (numStrArr.length > 1)? numStrArr[1] : null;

		final String decTsPat = "(\\d{1,3}(\\,\\d{3})*)$";
		final String decNoTsPat = "(\\d+)$";

		String locStr = numStrBeforeDs;

		// validate en decimal string
		if (numStrBeforeDs.length() > 3) {
			if (numStrBeforeDs.indexOf(ts_en) != -1 && ! numStrBeforeDs.matches(decTsPat)) { // wrong ts format
				numStrBeforeDs = numStrBeforeDs.replaceAll(ts_en, "");
			}

			if (numStrBeforeDs.matches(decNoTsPat)) { // without ts
				StringBuilder numSb = new StringBuilder();
				numStrBeforeDs = new StringBuilder(numStrBeforeDs).reverse().toString();
				for (int i=1; i<=numStrBeforeDs.length(); i++) {
					if ((i%3) == 0) {
						numSb.append(numStrBeforeDs.charAt(i-1) + ts_en);
					} else {
						numSb.append(numStrBeforeDs.charAt(i-1));
					}
				}

				locStr = numSb.reverse().toString(); 

			}
		}

		// replace all Thousands Separator
		if (curLang.equals("bg") || curLang.equals("fr")) {
			locStr = numStrBeforeDs.replaceAll(ts_en, " ");
		} else if (curLang.equals("de") || curLang.equals("es") || curLang.equals("pt")) {
			locStr = numStrBeforeDs.replaceAll(ts_en, ".");
		}

		if (numStrAfterDs != null) { // append the numStrAfterDs with localised Decimal Separator
			String dotSep = (curLang.equals("bg") || curLang.equals("de") || curLang.equals("es") 
					|| curLang.equals("fr") || curLang.equals("pt"))? ds_non_en : ds_en;

			locStr += dotSep + numStrAfterDs;
		}

		return locStr;
	}

	
	protected  void setAutoRefresh(GuiSubitemTestObject table,ToggleGUITestObject ckbox, GuiSubitemTestObject gsto,String onOrOff) {
		boolean welldone = false;
		int count = 3;
		
		adjustAnaStatusWindow(table, gsto, -1, 260);
		while ((! welldone) && (count > 0)) {
			try {
				if(isVisible(ckbox)){
				  ckbox.clickToState("On".equalsIgnoreCase(onOrOff)? State.selected() : State.notSelected());
				  logTAFStep("Set Auto-refresh to this Analytics Status window as '"+onOrOff+"'");
				  welldone = true;
				}else{
					throw new Exception();
				}
			} catch (Exception e) {	
				logTAFDebug("Exception when set auto refresh '"+e.toString());
				if (--count > 0) {
					adjustAnaStatusWindow(table, gsto, -1, 260);
				}				
			}
		}
		
		if (welldone && (count < 3)) {
			logTAFWarning("Exception happened while setting auto refresh, success by " + (3 - count) + " more times try after adjust 'Analytics Status' window");
		}
	}
	
	protected void adjustAnaStatusWindow(GuiSubitemTestObject table,GuiSubitemTestObject gsto,
			int xoffset, int yoffset) {
		int offx=1,offy=1;
		Rectangle trec = table.getScreenRectangle();		
		Rectangle tree = gsto.getScreenRectangle();
		int x,y;
		
		x = tree.width+offx;
		if(xoffset>=0&&trec.width!=xoffset){ 
			  xoffset = xoffset - trec.width;
			  y = (int)(tree.height/2);
			  gsto.hover(atPoint(x,y));
		      gsto.dragToScreenPoint(atPoint(x,y),atPoint(tree.x+x+xoffset, tree.y+y));
		      logTAFInfo("Window size ajusted with x offset '"+xoffset+"'");
		}
//		if(yoffset!=0&&(Math.abs(trec.height-Math.abs(yoffset))>5)){
		if(yoffset>=0&&trec.height!=yoffset){
			yoffset = yoffset-trec.height;
			y = tree.height + offy;	
			logTAFDebug("Point to adjust win from : ("+(tree.x+x)+","+(tree.y+y)+") to : ("+(tree.x+x)+","+(tree.y +y-yoffset)+")");
			gsto.hover(atPoint(x,y));
			gsto.dragToScreenPoint(atPoint(x,y),atPoint(tree.x+x, tree.y + y - yoffset));
		    logTAFInfo("Window size ajusted with y offset '"+yoffset+"'");
		}
	}
	// for debug only
	// print out all properties in TestObjects in TestObject array
	protected void printTOProperties(TestObject[] TOs) {
		int i = 0; 
		for (TestObject to : TOs) {
			logInfo("P" + (i++) + ": [" + to.getProperties() + "]");
		}
	}

	// for debug only
	// print out all properties of TestObject
	protected void printTOProperties(TestObject TO) {
		logInfo(TO.getProperties().toString());
	}

	// for debug only
	// print out all methods of TestObject
	protected void printTOMethods(TestObject TO) {
		int i = 0;
		logInfo("Object Class [" + TO.getObjectClassName() + "]'s methods: \n");
		for (MethodInfo mi : TO.getMethods()) {
			logInfo("M" + (i++) + ": [" + mi.getName() + ":" + mi.getSignature() + "]");
		}
	}
}