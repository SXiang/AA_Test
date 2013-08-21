package com.acl.qa.taf.helper.superhelper;

import ibm.util.NLSHlpr;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.acl.qa.taf.util.NLSUtil;


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
	// ****** These functions are from RFT GUI testing, the implementation should be useful	for later work 
	// ******************************************************************************************
		
//		public static void inputChars(ITopWindow tw, String text){
//			text = text.trim();
//			cleanTextBox(tw);
////			if(isAscii(text)){
////				tw.inputChars(text);
////				logTAFDebug("Use inputChars directly for '"+text+"'");
////				sleep(1);
////				return;
////			}else{
//				//inputUnicodeChars(tw, text);
//				inputUnicodeChars(text);
////			}
//		}
	//	
	//
	//
//		public static void inputCharsFromKeyboard(ITopWindow tw, String text){
//			String inputChar ="";
////			logTAFInfo("Input Char: "+text);
//			if(!tw.isEnabled()){
//				sleep(2);
//				return;  // need further debugging - steven.
//			}
//	        cleanTextBox(tw);
//			for(int i=0;i<text.length();i++){
//				inputChar = setIME(tw,text.charAt(i));
//				if(inputChar.length()==1){
//					tw.inputChars(inputChar);
//				}else{
//					tw.inputKeys(inputChar);
//				}
//			}
//	       sleep(0);
//		}
	//	
//		public static void cleanTextBox(ITopWindow tw){
//			String keys = "{END}";
//			for(int i=0;i<1000;i++){
//				keys += "{BS}";
//			}
//			
//			tw.inputKeys(keys);
//			tw.inputKeys("^a{BS}");
//			sleep(1);
	//
//		}
	//	
//		public static void setText(TextGuiTestObject tgto,String text){
//			Point pt= new Point(5,5);
//			text = text.trim();
//			ObjectHelper.click(tgto,pt);
//			if(isAscii(text)){
//				tgto.setText(text);
//				logTAFDebug("Use setText directly for '"+text+"'");
//				sleep(1);
//			}else{
//			    inputUnicodeChars(getScreen(),text);
//			}
//		}
	//	
	//
//		public static void inputChars(TextGuiTestObject tgto,String text){
//			text = text.trim();
//			ObjectHelper.click(tgto);
//			if(isAscii(text)){
//				getScreen().getActiveWindow().inputChars(text);
//				logTAFDebug("Use inputChars directly for '"+text+"'");
//				sleep(1);
//			}else{
//			    inputUnicodeChars(getScreen(),text);
//			}
//			sleep(0);
//		}
//			
//		public static void inputChars(ITopWindow tw, TextGuiTestObject tgto,String text){
//			text = text.trim();
//			ObjectHelper.click(tgto);
//			inputChars(tw,text);
//		}
	//	
//		public static String getUnicodeChars(ITopWindow tw,char ch){
//			
//			String imeText;
//	        String imeIndex = "0";
//	        
//	        imeText = convertExtendedAscii(ch);
//			if(ch<256||imeText.length()>1){
//				imeIndex = "0";
//			}else{
//				imeIndex = poolLanguageIndex;
//			}
//			
//			if(!imeIndex.equals(currentImeIndex)){
//				tw.inputKeys("%+"+imeIndex);
//				currentImeIndex = imeIndex;
////				logTAFDebug("IME index set to '"+imeIndex+"'");
//				sleep(1);
//			}		
//			return imeText;
//		}	
//		public static String setIME(ITopWindow tw,char ch){
//			
//			String imeText;
//	        String imeIndex = "0";
//	        
//	        imeText = convertExtendedAscii(ch);
//			if(ch<256||imeText.length()>1){
//				imeIndex = "0";
//			}else{
//				imeIndex = poolLanguageIndex;
//			}
//			
//			if(!imeIndex.equals(currentImeIndex)){
//				tw.inputKeys("%+"+imeIndex);
//				currentImeIndex = imeIndex;
////				logTAFDebug("IME index set to '"+imeIndex+"'");
//				sleep(1);
//			}		
//			return imeText;
//		}
	//	
//		public static String convertExtendedAscii(char text){
//			boolean done = false;
//			String temp="";
//			String  easciiText = "%({Num0}";
////			String easciiText = "%(+";
//			
//			int[] uniCode ={8364,129,8218,402,8222,8230,8224,8225,710,8240,
//					        352,8249,338,141,381,143,144,8216,8217,8220,
//					        8221,8226,8211,8212,732,8482,353,8250,339,157,
//					        382,376};
//			
//			for(int i= 0;i<uniCode.length&&!done;i++){
//				if(text==uniCode[i]&&text>255){
//					temp = ""+(i+128);
//					for(int j=0;j<temp.length();j++){
//						easciiText +="{Num"+temp.charAt(j)+"}"; 
//					}
//					easciiText +=")";
//					done = true;
//				}
//			}
//			if(done){
////			   logTAFDebug("converted '"+text+"' to Keys '"+easciiText+"'");
//	    		return easciiText;
//			}
//			return ""+text;
//		}
	//  
	// Temp use
	
//	public static void inputUnicodeChars(TopLevelSubitemTestObject tgto, String text){
//        inputUnicodeChars(getScreen(),text);
//	}	
//	// Temp use
//	public static void inputUnicodeChars(IWindow tgto, String text){
//          inputUnicodeChars(getScreen(),text);
//	}	
//	public static void inputUnicodeChars(TextGuiSubitemTestObject tgto, String text){
//		try{
//		    tgto.setText("");
//		    tgto.click();
//		}catch(Exception e){
//			logTAFWarning(e.toString());
//		}
//		sleep(1);
//		
//		inputUnicodeChars(getScreen(),text);
//
//	}	
//	public static void inputUnicodeChars(TextGuiTestObject tgto, String text){
//		
//		tgto.setText("");
//		tgto.click();
//		sleep(1);
//		
//		inputUnicodeChars(getScreen(),text);
//
//	}
//	public static void inputUnicodeChars(String text){
//		
//		inputUnicodeChars(getScreen(),text);
//
//	}
//	public static void inputUnicodeChars(IScreen tw, String text){
//		//copy/paste from clipboard
//		//cleanTextBox((ITopWindow) tw);
//		logTAFInfo("Input text: '"+text+"'");	
//		boolean reinput = true;
//		tw.inputKeys("^a{BS}");
//		nlshelper.copyToClipboard("");
//		
//		if(!pasteSuccess(tw,"")){
//			cleanTextBox(tw.getActiveWindow());		
//			reinput = false;
//		}
//		nlshelper.copyToClipboard(text);
//		tw.inputKeys("^v");
//		sleep(1);
//		
//		//input from keyboard char by char
//		if(!pasteSuccess(tw,text)){
////			logTAFWarning("Paste '"+text+"' by ^v failed? or interfared by some unexpected windows activity...");
////			inputCharsFromKeyboard(tw.getActiveWindow(),text);
//			if(reinput){
//			   logTAFWarning("Paste '"+text+"' by ^v failed, try to input through keyboard...");			
//			   inputCharsFromKeyboard(tw.getActiveWindow(),text);
//			}else{
//				//logTAFError(autoIssue+"Paste '"+text+"' by ^v failed? or interfered by some unexpected window's activity...");
//			}
//		}
//
//	}
//	
//    public static boolean pasteSuccess(IScreen tw,String pasted){
//    	String copied;
//    	//
//    	tw.inputKeys("^c");
//    	sleep(1);
//    	copied = getSystemClipboard().getText().trim();
//    	logTAFDebug("String was pasted '"+pasted+"', actual string is '"+copied+"'");
//    	//return(copied.equals(pasted)||(!copied.equals("")&&pasted.contains(copied)));
//    	
//    	return(copied.equals(pasted));
//    }
}