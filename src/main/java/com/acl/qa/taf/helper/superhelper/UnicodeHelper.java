package com.acl.qa.taf.helper.superhelper;

import ibm.util.NLSHlpr;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.acl.qa.taf.tool.TAFRobot;
import com.acl.qa.taf.util.NLSUtil;
import com.acl.qa.taf.util.UnicodeUtil;


/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  June 24, 2010
 */
public abstract class UnicodeHelper extends DatabaseHelper
{
	//Input chars using clipboard
	static NLSHlpr nlshelper;

	public static boolean isAscii(String text){
		for(int i=0;i<text.length();i++){
			if(text.charAt(i)>255)
				return false;
		}
		return true;
	}

    
	public UnicodeHelper(){
		nlshelper = new NLSHlpr();
	}
	

	
	public static String USCADateReverse(String curDate){
		
		return USCADateReverse(curDate,"/");
	}
	public static String USCADateReverse(String curDate,String sep){
		String rDate = curDate, dateArray[];
		dateArray = curDate.split(sep);
		if(dateArray.length==3){
			rDate = dateArray[1]+sep+dateArray[0]+sep+dateArray[2];
		}else{
			logTAFError("Date - '"+curDate+"' is not recornized as a valid date");  			   
		}
		//logTAFDebug("Convert Date '"+curDate+"' to '"+rDate+"'"	);
		return rDate;
	}

	public static String convertDatePattern(String curDate){
		// M/d/yy for English(United States)
		// dd/MM/yy for English(Canada)
		return convertDatePattern(curDate,"[mM]+/[dD]+/[yY]{2,}.*");
	}
	public static String convertDatePattern(String curDate,String pattern){
		String rDate = curDate;
		SimpleDateFormat sdf = new SimpleDateFormat();
		String curPattern = sdf.toPattern();
		
		if(!curPattern.matches(pattern)){
			rDate = USCADateReverse(rDate);
		}
		logTAFDebug("Convert Date '"+curDate+"' to '"+rDate+"'"	);
		return rDate;
	}
	


	public static void main(String[] arg){
		//USCADateReverse("11/14/2000");
	}
	
	
	
	// *****************************************************************************************
	// ****** Implemented for selenium2 webdriver  -- Steven 
	// ******************************************************************************************
	public static void inputChars(WebElement we, String text){
		inputChars(null,we,text);
	}	

		public static void inputChars(WebDriver driver, WebElement we, String text){
			text = text.trim();
			try{
				we.click();
			}catch(Exception e){
				
			}
			we.clear();
			if(isAscii(text)){
				we.sendKeys(text);
				//we.sendKeys(Keys.ENTER);
				logTAFDebug("Use inputChars directly for '"+text+"'");
				sleep(1);
				return;
			}else{

				inputUnicodeChars(driver,we, text);
				//inputUnicodeChars(text);
			}
		}
	//	
	//

			
        public static void inputCharsFromKeyboard(WebDriver driver,WebElement we, String text){
			if(driver==null){
				logTAFWarning("Webdriver has not been specified?. input failed");
				return;
			}
			String inputChar;
			Actions action;
			boolean ready = false;
			if(!ready){
			   logTAFInfo("Input Char: "+text+"' from keyboard method is not ready yet!");
			   return;
			}
			if(!we.isEnabled()){
				sleep(2);
				return;  // need further debugging - steven.
			}
	        
	        action = new Actions(driver);
	       
	        we.clear();
	        we.click();
	        TAFRobot robot = null;
			try {
				robot = new TAFRobot();
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return;
			}
			for(int i=0;i<text.length();i++){   // TBD... in progress ... Steven
				//inputChar[] = setIME(driver,we,text.charAt(i)).split(",");
				inputChar = getHexString(text.charAt(i));
//				robot.keyPress(KeyEvent.VK_ALT);
//				robot.keyPress(KeyEvent.VK_ADD);		
//				robot.keyRelease(KeyEvent.VK_ADD);
//				action.sendKeys(inputChar);
//				action.perform();
//				robot.keyRelease(KeyEvent.VK_ALT);
				robot.inputUnicodeFromKeyboard(we,inputChar);
//			    action.keyDown(Keys.ALT);
//			    action.sendKeys(Keys.ADD,inputChar);
//			    action.keyUp(Keys.ALT);
//			    action.perform();
			}


	       sleep(0);
		}


		public static String getHexString(char ch){
			if(ch<256)
				return UnicodeUtil.getCharHexString(ch);
			int[] uniCode ={8364,129,8218,402,8222,8230,8224,8225,710,8240,
					        352,8249,338,141,381,143,144,8216,8217,8220,
					        8221,8226,8211,8212,732,8482,353,8250,339,157,
					        382,376};
			
			for(int i= 0;i<uniCode.length;i++){
				if(ch==uniCode[i]){
					ch = (char) (i+128);
					break;
				}
			}

			return UnicodeUtil.getCharHexString(ch);

		}
		public static String setIME(WebDriver driver,WebElement we,char ch){
			
			String imeText;
	        String imeIndex = "0";
            Actions action = new Actions(driver);
	        imeText = convertExtendedAscii(ch);
			if(ch<256||imeText.length()>1){
				imeIndex = "0";
			}else{
				imeIndex = poolLanguageIndex; // Need check the initialization ... in progress ...  Steven
			}
			
			if(!imeIndex.equals(currentImeIndex)){
				we.sendKeys(Keys.chord(Keys.ALT,Keys.SHIFT,imeIndex));
				//action.sendKeys(Keys.chord(Keys.ALT,Keys.SHIFT,"1"));
				currentImeIndex = imeIndex;
				logTAFWarning("IME index set to '"+imeIndex+"'");
				action.keyDown(Keys.ALT).keyDown(Keys.SHIFT).sendKeys("1").keyUp(Keys.SHIFT).keyUp(Keys.ALT).perform();
				sleep(1);
			}		
			return imeText;
		}
		
		public static String convertExtendedAscii(char text){
			boolean done = false;
			String temp="";
			String  easciiText = ""+getNumpadValue((char) 0);
//			String easciiText = "%(+";
			if(text<256)
				return ""+text;
			int[] uniCode ={8364,129,8218,402,8222,8230,8224,8225,710,8240,
					        352,8249,338,141,381,143,144,8216,8217,8220,
					        8221,8226,8211,8212,732,8482,353,8250,339,157,
					        382,376};
			
			for(int i= 0;i<uniCode.length&&!done;i++){
				if(text==uniCode[i]&&text>255){
					temp = ""+(i+128);
					for(int j=0;j<temp.length();j++){
						easciiText +=","+getNumpadValue(temp.charAt(j))+""; 
					}
					easciiText +="";
					done = true;
				}
			}
			if(done){
//			   logTAFDebug("converted '"+text+"' to Keys '"+easciiText+"'");
	    		return easciiText;
			}
			
			temp = ""+(0+text);

			//easciiText = ""+getNumpadValue((char) 0);
			for(int j=0;j<temp.length();j++){
				if(j>0)
				   easciiText +=","+getNumpadValue(temp.charAt(j))+""; 
				else
					easciiText =getNumpadValue(temp.charAt(j))+""; 
			}

			logTAFInfo("converted '"+text+"("+temp+")' to Keys '"+easciiText+"'");
			done = true;
			if(done){
//				   logTAFDebug("converted '"+text+"' to Keys '"+easciiText+"'");
		    		return easciiText;
				}
			return ""+text;
		}
	//  

		public static CharSequence getNumpadValue(char ch){
			CharSequence cs= ""+ch;
			switch (ch) {
			     case '0': cs=Keys.NUMPAD0;
			     case '1': cs=Keys.NUMPAD1;
			     case '2': cs=Keys.NUMPAD2;
			     case '3': cs=Keys.NUMPAD3;
			     case '4': cs=Keys.NUMPAD4;
			     case '5': cs=Keys.NUMPAD5;
			     case '6': cs=Keys.NUMPAD6;
			     case '7': cs=Keys.NUMPAD7;
			     case '8': cs=Keys.NUMPAD8;
			     case '9': cs=Keys.NUMPAD9;
			     
			}
			
			return cs;
		}
	public static void inputUnicodeChars(WebDriver driver,WebElement we, String text){
		//copy/paste from clipboard
		//cleanTextBox((ITopWindow) tw);
		logTAFInfo("Input text: '"+text+"'");	
		boolean reinput = true;
		
		nlshelper.copyToClipboard("");
		
		if(!pasteSuccess(we,"")){
			we.clear();	
			reinput = false;
		}
		nlshelper.copyToClipboard(text);
		we.sendKeys(Keys.chord(Keys.CONTROL,"v"));
		sleep(1);
		
		//input from keyboard char by char
		if(!pasteSuccess(we,text)){
//			logTAFWarning("Paste '"+text+"' by ^v failed? or interfared by some unexpected windows activity...");
//			inputCharsFromKeyboard(tw.getActiveWindow(),text);
			if(reinput){
			   logTAFWarning("Paste '"+text+"' by ^v failed, try to input through keyboard...");			
			   inputCharsFromKeyboard(driver,we,text);
			}else{
				//logTAFError(autoIssue+"Paste '"+text+"' by ^v failed? or interfered by some unexpected window's activity...");
			}
		}

	}
	
    public static boolean pasteSuccess(WebElement we,String pasted){
    	String copied=pasted;
    	//
    	we.sendKeys(Keys.chord(Keys.CONTROL,"c"));
    	sleep(1);
    	try {
			copied = (String)getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    	logTAFDebug("String was pasted '"+pasted+"', actual string is '"+copied+"'");
    	//return(copied.equals(pasted)||(!copied.equals("")&&pasted.contains(copied)));
    	
    	return(copied.equals(pasted));
    }
}