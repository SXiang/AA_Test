package com.acl.qa.taf.helper.superhelper;

import ibm.util.NLSHlpr;

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

	
//	public static void inputChars(ITopWindow tw, String text){
//		text = text.trim();
//		cleanTextBox(tw);
////		if(isAscii(text)){
////			tw.inputChars(text);
////			logTAFDebug("Use inputChars directly for '"+text+"'");
////			sleep(1);
////			return;
////		}else{
//			//inputUnicodeChars(tw, text);
//			inputUnicodeChars(text);
////		}
//	}
//	
//
//
//	public static void inputCharsFromKeyboard(ITopWindow tw, String text){
//		String inputChar ="";
////		logTAFInfo("Input Char: "+text);
//		if(!tw.isEnabled()){
//			sleep(2);
//			return;  // need further debugging - steven.
//		}
//        cleanTextBox(tw);
//		for(int i=0;i<text.length();i++){
//			inputChar = setIME(tw,text.charAt(i));
//			if(inputChar.length()==1){
//				tw.inputChars(inputChar);
//			}else{
//				tw.inputKeys(inputChar);
//			}
//		}
//       sleep(0);
//	}
//	
//	public static void cleanTextBox(ITopWindow tw){
//		String keys = "{END}";
//		for(int i=0;i<1000;i++){
//			keys += "{BS}";
//		}
//		
//		tw.inputKeys(keys);
//		tw.inputKeys("^a{BS}");
//		sleep(1);
//
//	}
//	
//	public static void setText(TextGuiTestObject tgto,String text){
//		Point pt= new Point(5,5);
//		text = text.trim();
//		ObjectHelper.click(tgto,pt);
//		if(isAscii(text)){
//			tgto.setText(text);
//			logTAFDebug("Use setText directly for '"+text+"'");
//			sleep(1);
//		}else{
//		    inputUnicodeChars(getScreen(),text);
//		}
//	}
//	
//
//	public static void inputChars(TextGuiTestObject tgto,String text){
//		text = text.trim();
//		ObjectHelper.click(tgto);
//		if(isAscii(text)){
//			getScreen().getActiveWindow().inputChars(text);
//			logTAFDebug("Use inputChars directly for '"+text+"'");
//			sleep(1);
//		}else{
//		    inputUnicodeChars(getScreen(),text);
//		}
//		sleep(0);
//	}
//		
//	public static void inputChars(ITopWindow tw, TextGuiTestObject tgto,String text){
//		text = text.trim();
//		ObjectHelper.click(tgto);
//		inputChars(tw,text);
//	}
//	
//	public static String getUnicodeChars(ITopWindow tw,char ch){
//		
//		String imeText;
//        String imeIndex = "0";
//        
//        imeText = convertExtendedAscii(ch);
//		if(ch<256||imeText.length()>1){
//			imeIndex = "0";
//		}else{
//			imeIndex = poolLanguageIndex;
//		}
//		
//		if(!imeIndex.equals(currentImeIndex)){
//			tw.inputKeys("%+"+imeIndex);
//			currentImeIndex = imeIndex;
////			logTAFDebug("IME index set to '"+imeIndex+"'");
//			sleep(1);
//		}		
//		return imeText;
//	}	
//	public static String setIME(ITopWindow tw,char ch){
//		
//		String imeText;
//        String imeIndex = "0";
//        
//        imeText = convertExtendedAscii(ch);
//		if(ch<256||imeText.length()>1){
//			imeIndex = "0";
//		}else{
//			imeIndex = poolLanguageIndex;
//		}
//		
//		if(!imeIndex.equals(currentImeIndex)){
//			tw.inputKeys("%+"+imeIndex);
//			currentImeIndex = imeIndex;
////			logTAFDebug("IME index set to '"+imeIndex+"'");
//			sleep(1);
//		}		
//		return imeText;
//	}
//	
//	public static String convertExtendedAscii(char text){
//		boolean done = false;
//		String temp="";
//		String  easciiText = "%({Num0}";
////		String easciiText = "%(+";
//		
//		int[] uniCode ={8364,129,8218,402,8222,8230,8224,8225,710,8240,
//				        352,8249,338,141,381,143,144,8216,8217,8220,
//				        8221,8226,8211,8212,732,8482,353,8250,339,157,
//				        382,376};
//		
//		for(int i= 0;i<uniCode.length&&!done;i++){
//			if(text==uniCode[i]&&text>255){
//				temp = ""+(i+128);
//				for(int j=0;j<temp.length();j++){
//					easciiText +="{Num"+temp.charAt(j)+"}"; 
//				}
//				easciiText +=")";
//				done = true;
//			}
//		}
//		if(done){
////		   logTAFDebug("converted '"+text+"' to Keys '"+easciiText+"'");
//    		return easciiText;
//		}
//		return ""+text;
//	}
//    
	public static boolean isAscii(String text){
		for(int i=0;i<text.length();i++){
			if(text.charAt(i)>255)
				return false;
		}
		return true;
	}
//	// Temp use
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
//	public UnicodeHelper(){
//		nlshelper = new NLSHlpr();
//	}
//	
//
//	
//	public static String USCADateReverse(String curDate){
//		
//		return USCADateReverse(curDate,"/");
//	}
//	public static String USCADateReverse(String curDate,String sep){
//		String rDate = curDate, dateArray[];
//		dateArray = curDate.split(sep);
//		if(dateArray.length==3){
//			rDate = dateArray[1]+sep+dateArray[0]+sep+dateArray[2];
//		}else{
//			logTAFError("Date - '"+curDate+"' is not recornized as a valid date");  			   
//		}
//		//logTAFDebug("Convert Date '"+curDate+"' to '"+rDate+"'"	);
//		return rDate;
//	}
//
//	public static String convertDatePattern(String curDate){
//		// M/d/yy for English(United States)
//		// dd/MM/yy for English(Canada)
//		return convertDatePattern(curDate,"[mM]+/[dD]+/[yY]{2,}.*");
//	}
//	public static String convertDatePattern(String curDate,String pattern){
//		String rDate = curDate;
//		SimpleDateFormat sdf = new SimpleDateFormat();
//		String curPattern = sdf.toPattern();
//		
//		if(!curPattern.matches(pattern)){
//			rDate = USCADateReverse(rDate);
//		}
//		logTAFDebug("Convert Date '"+curDate+"' to '"+rDate+"'"	);
//		return rDate;
//	}
//	
	// 4 methods for translation eng <-> loc - Steven
	
	static String idPattern = "lv_.*";
	public static String getLocValues(String name){
		return getLocValues(name,"");
	}
	public static String getLocValues(String name,String className){
		String value = NLSUtil.convert2Locale(name,className);
		if(!value.equals("")&&!value.contains(name)){
			value += "|"+name;
		}
		logTAFDebug("LocaleValuesConvert: "+name+
				" = '"+value+"'");
		return GuiFinderHelper.trimExp(value);
	}	
	public static String getLocValue(String name){
		return getLocValue(name,true);
	}
	public static String getLocValue(String name,boolean truncate){
		return getLocValue(name,"",truncate);
	}
	public static String getLocValue(String name,String className){
		return GuiFinderHelper.trimExp(getLocValue(name,className,true));
	}
	public static String getLocValue(String name,String className,boolean truncate){
		if(GuiFinderHelper.isPattern(name)){
			return getLocExp(name,className,truncate);
		}else{		
			//return getLocExp(name,className,truncate);
          return getLocExp(name,className,truncate).split("\\|")[0];
		}
	}
	public static String getLocExp(String name,String className,boolean truncate){
    	
    	String pre = "SingleLocValue_";
    	
		String value = NLSUtil._convert2Locale(pre+name,className,truncate);
		logTAFDebug("LocaleValueConvert: "+name+
				" = '"+value+"'");
		return GuiFinderHelper.trimExp(value);
	}
	public static String getEngValues(String name){
		return getEngValues(name,"");
	}
	public static String getEngValues(String name,String className){
		String value = NLSUtil.convert2English(name,className);
		if(!value.equals("")&&!name.contains(value)){
			value += "|"+name;
		}
		logTAFDebug("EnglishValuesConvert: "+name+
				" = '"+value+"'");
		return GuiFinderHelper.trimExp(value);
	}	
	public String getEngValue(String name){
		return getEngValue(name,"");
	}
	public String getEngValue(String name,String className){
		String pre = "SingleLocValue_";
    	
		String value = NLSUtil._convert2English(pre+name,className);
		logTAFDebug("EnglishValueConvert: "+name+
				" = '"+value+"'");
		value = GuiFinderHelper.trimExp(value);
		if(GuiFinderHelper.isPattern(name)){
			return value;
			//return GuiFinderHelper.correctPattern(value);
		}else{		
		  //return value;
          return value.split("\\|")[0];
		}
	}

	public String replaceLocAll(String pattern, String input){  
		Pattern p = Pattern.compile(pattern);  
		Matcher m = p.matcher(input);  
		StringBuffer sb = new StringBuffer();  
		String suffix = "[\\s]*[:]?[\\s]*['\"%Iclds\\d]*[\\^]?[\\s]*$"+
			            "|[\\s]*[:]?[\\s]*['\"]?[%][\\d]*[l]?[Idsc]['\"]?.*$";
		String prefix = "";//"([\\s]*[\\^]?[\\s]*['\"%ds\\d]*[\\s]*";
		
		String[] idTrans = {"(?i)Count","lv_ACLTXT_RH_SX_630_ID"};
		String temp = "";
		while (m.find())  
		{  
			boolean isID = false;
			String captured = m.group(2);
			
			for(int i=0;i<idTrans.length-1;i=i+2){
				//System.out.print(" ^^^^^ "+captured+" matches("+idTrans[i]+") = "+captured.matches(idTrans[i]));
				   if(captured.matches(idTrans[i])){
					   captured = idTrans[i+1];
					   isID = true;
					   //System.out.print(captured+" =? "+idTrans[i+1]);
					  
				  }
				}
			if(!isID&&captured.length()>1&&captured.equals(captured.toUpperCase())){
				   captured =captured.charAt(0)+captured.substring(1);
				}
			

		

			String replacement = "";
			temp = captured;
			if(!isID){
				temp = captured.replaceAll("[^\\.][\\*]+", "[\\*]+");
			    replacement = getLocValue(prefix+temp+"("+suffix+")",false);
				if(replacement.equals(prefix+temp+"("+suffix+")")
						||(prefix+temp+"("+suffix+")").contains(replacement)
						||replacement.matches(prefix+temp+"("+suffix+")")){
					replacement = captured;
				}else if (replacement != null){
					replacement = replacement.replaceAll(suffix, "");
				}
			}else{
				replacement = getLocValue(captured,false);
			}

			if (replacement != null&&
					!replacement.equals("")) {
				//replacement = replacement.replaceFirst("^"+prefix, "");
				//replacement = replacement.replaceAll(suffix, "");

//				try{
//					if(m.group(2).equals(m.group(2).toUpperCase())){
//						replacement = replacement.toUpperCase();
//					}else if(m.group(2).equals(m.group(2).toLowerCase())){
//						replacement = replacement.toLowerCase();
//					}
//				}catch(Exception e){
//
//				}
	        	replacement = m.group(1)+replacement+m.group(3);
	        	//m.appendReplacement(sb, replacement);  
	            m.appendReplacement(sb, "");  
	            sb.append(replacement); 
	        }
	    }  
	    m.appendTail(sb);  
	    
	    return sb.toString();  	   
	}
	public String replaceEngAll(String pattern, String input){  
	    Pattern p = Pattern.compile(pattern);  
	    Matcher m = p.matcher(input);  
	    StringBuffer sb = new StringBuffer();  
	    while (m.find())  
	    {  
	    	String replacement = getEngValue(m.group(1));
	        if (replacement != null&&
	        		!replacement.equals("")) {
	        	//m.appendReplacement(sb, replacement);  
	            m.appendReplacement(sb, "");  
	            sb.append(replacement); 
	        }
	    }  
	    m.appendTail(sb);  
	    
	    return sb.toString();  	   
	}

	public static void main(String[] arg){
		//USCADateReverse("11/14/2000");
	}
}