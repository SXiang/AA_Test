package lib.acl.tool.LocalizationSetup.MyFiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lib.acl.util.FileUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class generates localized map from default English RFT Map
 * 
 * @author Paramita Ghosh
 * Modified by Henry Wu on 2010-02-09 to make it works as expected
 * 		Add .name, regex and make the tough unicode part work!
 * 
 */
public class Steven_GenerateLocalizedMap {

	private static int logLvl = 1;	// 1: info; 2: debug
     
	/*private static String[] oriMaps = {"AX_SharedTestObjectMap.rftmap",
									   "AX_GatewayPro_SharedMap.rftmap",
		                               "GWP_GoldbugSharedMap.rftmap",
		                               
		                                "EM_SharedTestObjectMap.rftmap",
		                                 "EM_GoldbugSharedMap.rftmap"};
		                            */
	
	private static String[] oriMaps2 = {"AX_SharedTestObjectMap.rftmap",
										"AX_GatewayPro_SharedMap.rftmap",
										"GWP_GoldbugSharedMap.rftmap", 
										"AX_Gateway_SharedMap.rftmap",
		                               "AX_Gateway_Goldbug_SharedMap.rftmap",
		                               "EM_SharedTestObjectMap.rftmap",
		                                 "EM_GoldbugSharedMap.rftmap"
										};
	
	private static String[] oriMaps1 = {"AX_SharedTestObjectMap.rftmap"};
	
	private static String ENCODING = "UTF-8";
	private static String workingDir = "lib\\acl\\resource\\LocalizationSetup\\MyFiles\\";//"C:/temp/RFT_Localization/";
	
	private static String enAllPropsFile = workingDir + "output\\properties\\AXEM_Automation_en.properties";
	private static Map<String,String> englishAllPropsHm;
	
	private static String mapInputDir = workingDir + "input\\RFTMaps_EN\\";
	private static String origMapFileName = "AX_SharedTestObjectMap.rftmap";
	private static String mapOutputDir = workingDir + "output\\RFTMaps_I18n\\";

	private static Document doc;
	private static String curLanguage;
	
	private static String curLangAllPropsFile;
	

	public static void main(String args[]) {
	//	String[] supportLangArr = {"en", "bg", "de", "es", "fr", "pt"};
	//	String[] supportLangArr = {"en","fr","es","de","pt"};
		String[] supportLangArr = {"en"};
		
		Locale defaultLocale = FileUtil.locale;//get default location

		englishAllPropsHm = prepareEnAllPropsHm();	// value/key pair of AXEM_Automation_All_en.properties for quick search
		//System.out.println(englishAllPropsHm); //prints the hashmap
		String newMapFileName; 
		for(int numMaps=0;numMaps<oriMaps2.length;numMaps++){
		
			origMapFileName = oriMaps2[numMaps];//for each of the maps in the original maps array
	
			for (int i=0; i<supportLangArr.length; i++) {	
				curLanguage = supportLangArr[i];//for each of the supported languages
			//	curLanguage = "es";
				logInfo("############################");
				logInfo("## Current Language: [" + curLanguage + "] ##");
				logInfo("############################");

				try {
					//try to build a new xml document to act as the new map for the language
					DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					doc = db.parse(new File(mapInputDir + origMapFileName));
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			
				curLangAllPropsFile = workingDir + "output\\properties\\AXEM_Automation_" + curLanguage + ".properties";//create a properties file for the current language
				newMapFileName = mapOutputDir +curLanguage +"\\"+origMapFileName;

				logInfo("\tGenerate new Map [" + newMapFileName + "] based on \n\t properties file [" + curLangAllPropsFile + "] and [" + enAllPropsFile + "] ... \n");
			
				localizeNameAndText();

				generateLocalObjectMap(newMapFileName);
			}
		}
			Locale.setDefault(defaultLocale);
	}
	
	// find .name, .text, and regex node and replace the found value with localized value;
	// if it's the english hard code, do reverse search and get the corresponding localized value;
	@SuppressWarnings("null")
	private static void localizeNameAndText() {
		try {			
			NodeList nodeLst = doc.getElementsByTagName("Attribute");//returns a list of nodes with attributes, basically returns attributes.
//logInfo(""+nodeLst.getLength()); //length of the nodeLst is 5
			
			Node fstNode;
			for (int s=0; s<nodeLst.getLength(); s++) {
				//for each of the attributes
				fstNode = nodeLst.item(s);
				logInfo(fstNode.getLocalName());
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					Element fstElmnt = (Element) fstNode;//assign all the attributes of the first node to the first element
					NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("Value");//returns a nodelist of all elements with the name 'value'

					Element fstNmElmnt = (Element) fstNmElmntLst.item(0);//gets the first element from the element from the 'value' list

					if (fstNmElmnt.getAttribute("L").equalsIgnoreCase(".ObjectMapSet")) { // start of test objects
						//?????
						NodeList textNodeList = fstNmElmnt.getElementsByTagName("MTO");

						Element mtoElem = null;
						NodeList childNodeList = null;

						for (int i=0; i<textNodeList.getLength(); i++) { // loop all MTOs
							mtoElem = (Element) textNodeList.item(i);
							childNodeList = mtoElem.getElementsByTagName("Prop");

							Element propElem;
							NodeList propChildList;
							NodeList propValueList = null;
							Element valElem;
							String valueNodeName;

							for (int j=0; j<childNodeList.getLength(); j++) { // loop all properties of current mtoElem
								propElem = (Element) childNodeList.item(j);
								if (propElem != null) {
									propChildList = propElem.getElementsByTagName("Key");
									String curPropKeyText = ((Element) propChildList.item(0)).getTextContent();//type cast the object in the list so that you get the 'text' belonging to the element

									String origPropValueText = null;
									NodeList textValueNodeList = null;

									String textOrName = null;
									boolean isRegex = false;

									if ( curPropKeyText != null && curPropKeyText.equalsIgnoreCase(".text")) {
										// <Key>.text</Key><Val L=".caption"><Caption>mainWindow_title</Caption></Val>
										propValueList = propElem.getElementsByTagName("Val");
										valElem = ((Element) propValueList.item(0));
										valueNodeName = valElem.getAttribute("L");

										if (valueNodeName.equalsIgnoreCase(".caption")) {
											textValueNodeList = valElem.getElementsByTagName("Caption");

											if (textValueNodeList != null && textValueNodeList.item(0) != null) {
												origPropValueText = ((Element) textValueNodeList.item(0)).getTextContent();
												textOrName = ".text";
												
												if (origPropValueText.contains("Activity")){
													System.out.println("HI A");
												}
												
											}
										} else if (valueNodeName.equalsIgnoreCase(".RegExp")) {
											textValueNodeList = valElem.getElementsByTagName("Pattern");

											if (textValueNodeList != null && textValueNodeList.item(0) != null) {
												origPropValueText = ((Element) textValueNodeList.item(0)).getTextContent();
												textOrName = ".text";
												isRegex = true;
												
												if (origPropValueText.contains("Activity")){
													System.out.println("HI B");
												}
											}
										}
									}else if (curPropKeyText != null && curPropKeyText.equalsIgnoreCase(".name")) {
										// <Key>.name</Key><Val>mainWindow_title</Val>
										propValueList = propElem.getElementsByTagName("Val");
										valElem = ((Element) propValueList.item(0));
										valueNodeName = valElem.getAttribute("L");

										if (valueNodeName.equalsIgnoreCase(".RegExp")) {
											textValueNodeList = valElem.getElementsByTagName("Pattern");

											if (textValueNodeList != null && textValueNodeList.item(0) != null) {
												origPropValueText = ((Element) textValueNodeList.item(0)).getTextContent();
												textOrName = ".name";
												isRegex = true;
												
												if (origPropValueText.contains("Add")){
													System.out.println("HI C");
												}
												
											}
										} else {
											textValueNodeList = (NodeList) propValueList;
											if (textValueNodeList != null && textValueNodeList.item(0) != null) {
												origPropValueText = ((Element) textValueNodeList.item(0)).getTextContent();
												textOrName = ".name";
												
												if (origPropValueText.contains("Add")){
													System.out.println("HI D");
												}
												
											}
										}
										
										
									} else if (curPropKeyText != null && curPropKeyText.equalsIgnoreCase("text")){
										
										// <Key>.text</Key><Val L=".caption"><Caption>mainWindow_title</Caption></Val>
										propValueList = propElem.getElementsByTagName("Val");
										valElem = ((Element) propValueList.item(0));
										valueNodeName = valElem.getAttribute("L");
										//System.out.println("HI");
										
										if (valueNodeName.equalsIgnoreCase(".caption")) {
											textValueNodeList = valElem.getElementsByTagName("Caption");

											if (textValueNodeList != null && textValueNodeList.item(0) != null) {
												origPropValueText = ((Element) textValueNodeList.item(0)).getTextContent();
												textOrName = "text";
												
												if (origPropValueText.equals("Activity") ||origPropValueText.equals("Activity") ){
													System.out.println("HI 1");
												}
												
											}
										} else if (valueNodeName.equalsIgnoreCase(".RegExp")) {
											textValueNodeList = valElem.getElementsByTagName("Pattern");

											if (textValueNodeList != null && textValueNodeList.item(0) != null) {
												origPropValueText = ((Element) textValueNodeList.item(0)).getTextContent();
												textOrName = "text";
												isRegex = true;
												
												if (origPropValueText.equals("Activity") ||origPropValueText.equals("Activity") ){
													System.out.println("HI 2");
												}
												
											}
										}else {
											textValueNodeList = (NodeList) propValueList;
											if (textValueNodeList != null && textValueNodeList.item(0) != null) {
												origPropValueText = ((Element) textValueNodeList.item(0)).getTextContent();
												textOrName = "text";
												if (origPropValueText.equals("Activity") ||origPropValueText.equals("Activity")){
													System.out.println("HI 3");
												}
											}
										}//end else
									} else if (curPropKeyText != null && curPropKeyText.equalsIgnoreCase("name")) {
										// <Key>.name</Key><Val>mainWindow_title</Val>
										propValueList = propElem.getElementsByTagName("Val");
										valElem = ((Element) propValueList.item(0));
										valueNodeName = valElem.getAttribute("L");

										if (valueNodeName.equalsIgnoreCase(".RegExp")) {
											textValueNodeList = valElem.getElementsByTagName("Pattern");

											if (textValueNodeList != null && textValueNodeList.item(0) != null) {
												origPropValueText = ((Element) textValueNodeList.item(0)).getTextContent();
												textOrName = "name";
												isRegex = true;
												
												if (origPropValueText.equals("Add")){
													System.out.println("HI 4");
												}
												
											}
										} else {
											textValueNodeList = (NodeList) propValueList;
											if (textValueNodeList != null && textValueNodeList.item(0) != null) {
												origPropValueText = ((Element) textValueNodeList.item(0)).getTextContent();
												textOrName = "name";
												if (origPropValueText.equals("Activity")){
													System.out.println("HI 5");
												}
													
												
											}
										}
									}else if (curPropKeyText != null && curPropKeyText.equalsIgnoreCase(".captionText")) {
										// <Key>.name</Key><Val>mainWindow_title</Val>
										
										propValueList = propElem.getElementsByTagName("Val");
										valElem = ((Element) propValueList.item(0));
										valueNodeName = valElem.getAttribute("L");
										//System.out.println("!!!!!!!!!!!!!!!!!!!!!!Inside captionText");
										if (valueNodeName.equalsIgnoreCase(".caption")) {
											textValueNodeList = valElem.getElementsByTagName("Caption");

											if (textValueNodeList != null && textValueNodeList.item(0) != null) {
												origPropValueText = ((Element) textValueNodeList.item(0)).getTextContent();
												textOrName = "text";
												
												if (origPropValueText.equals("Add") ||origPropValueText.equals("Add...") ){
													System.out.println("HI 22");
												}
												
											}
										} else if (valueNodeName.equalsIgnoreCase(".RegExp")) {
											textValueNodeList = valElem.getElementsByTagName("Pattern");

											if (textValueNodeList != null && textValueNodeList.item(0) != null) {
												origPropValueText = ((Element) textValueNodeList.item(0)).getTextContent();
												textOrName = "text";
												isRegex = true;
												
												if (origPropValueText.equals("Add") ||origPropValueText.equals("Add...") ){
													System.out.println("HI 33");
												}
												
											}
										}else {
											textValueNodeList = (NodeList) propValueList;
											if (textValueNodeList != null && textValueNodeList.item(0) != null) {
												origPropValueText = ((Element) textValueNodeList.item(0)).getTextContent();
												textOrName = "text";
												if (origPropValueText.equals("Add") ||origPropValueText.equals("Add...")){
													System.out.println("HI 44");
												}
											}
										}//end else
									}
									
									
									if (origPropValueText != null && ! "".equalsIgnoreCase(origPropValueText)) {
										
										if (origPropValueText.contains("edit scripts"))
										{
											System.out.println("special value: "+origPropValueText);
											System.out.println("");
										}
										System.out.println("value: "+origPropValueText);
								//		System.out.println("pause");
										String convertedString = null;
										if (isRegex) {
											
											convertedString = getLocaleStrings(origPropValueText);
										} else {
											convertedString = getLocaleString(origPropValueText);
										}

										// Replace the keys with locale specific values
										if (convertedString != null	
												&& ! convertedString.equalsIgnoreCase(origPropValueText)) {
											((Element) textValueNodeList.item(0)).setTextContent(getUnicodedStr(convertedString));

											logDebug(textOrName + ":origPropValueText: [" + origPropValueText + "] --> convertedString: [" + convertedString + "]");
										}
										if (origPropValueText.equals("Add")){
											break;
										}
										if (convertedString == null) {
											if (! (origPropValueText.equals("OK")				// same for all language
													|| origPropValueText.equals("More")			// same for all language, analytic schedule's time spinbutton
													|| origPropValueText.equals("Application")	// same for all language, application menu bar
													|| origPropValueText.equals("Context")		// same for all language, popup menu
													|| origPropValueText.equals("Look in:")		// same for all language, select import files dialog: "Look in:" location dropdown box
													|| origPropValueText.equals("File name:")	// same for all language, select import files dialog: file name input field
													|| origPropValueText.equals("Yes To All")	// find by index, ignore
													|| origPropValueText.equals("Next >")		// find by index, ignore
												   )) {
											logError("No convertedString for " + textOrName + ":origPropValueText: [" + origPropValueText + "]");
											}
										}
									}//end if
								}
							}//end for loop properties for current mtoelem
						} // End of loop all MTOs

						logInfo("\tEnd loop of all MTOs for language [" + curLanguage + "]");
						break;
					}
				}
			}//end for loop
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// for regex
	private static String getLocaleStrings(String origPropValueTexts) {
		String convertedString = "";

		String tmpConvertedStr;

		logDebug("getLocaleStrings::origPropValueTexts: " + origPropValueTexts);
		for (String curStr : origPropValueTexts.split("\\|")) {
			tmpConvertedStr = getLocaleString(curStr);

			if (tmpConvertedStr != null) {
				convertedString += tmpConvertedStr + "|";
			} else {
				logWarning("Failed to convert [" + curStr + "] of [" + origPropValueTexts + "], use [" + curStr + "] instead!");
				convertedString += curStr + "|";
			}
		}

		convertedString = convertedString.substring(0, convertedString.length()-1);

		return convertedString;
	}

	// for normal origPropValueText
	private static String getLocaleString(String origPropValueText) {
		
		//System.out.println("get locale: Current language: "+ curLanguage);
		
		String convertedString = "";
		
		//String temp1="";
		String temp2="";
		String temp1="";
		String temp3="";
		String temp4="";
		String translation = "";
		String backup="";
		String [] arr;
		char[] cARR;
		boolean ampersand = false;
		boolean tab = false;
		boolean arrow = false;
		
		
		
		if(origPropValueText.contains("&")){
			origPropValueText = origPropValueText.replaceAll("&", "");
			ampersand = true;
		}
			
		if (origPropValueText.contains("\t")){
			arr = origPropValueText.split("\t");
			for(int count=0;count<arr.length;count++){
				temp1=englishAllPropsHm.get(arr[count]);//returns key for the found value 
				if (temp1!=null){
					temp2=getLocalizedString(temp1);
					temp2=temp2.replace("\\u","\\\\u");
					
					origPropValueText= origPropValueText.replaceFirst(arr[count],temp2);
				}
				
				if(curLanguage=="de"&& arr[count].contains("Ctrl")){
					//keyboard key 'ctrl' has different translation for some languages, this handles translation for german
					temp3=arr[count].replace("Ctrl", "Strg");
					origPropValueText = origPropValueText.replace(arr[count],temp3);
					System.out.println("ctrl: "+arr[count]);
				}
				if(curLanguage=="de"&& arr[count].contains("Cut")){
					//keyboard key 'ctrl' has different translation for some languages, this handles translation for german
					temp3=arr[count].replace("Ctrl", "Strg");
					origPropValueText = origPropValueText.replace(arr[count],temp3);
					System.out.println("ctrl: "+arr[count]);
				}
				if(curLanguage=="de"&& arr[count].contains("Paste")){
					//keyboard key 'ctrl' has different translation for some languages, this handles translation for german
					temp3=arr[count].replace("Ctrl", "Strg");
					origPropValueText = origPropValueText.replace(arr[count],temp3);
					System.out.println("ctrl: "+arr[count]);
				}
				if(curLanguage=="de"&& arr[count].contains("Search")){
					//keyboard key 'ctrl' has different translation for some languages, this handles translation for german
					temp3=arr[count].replace("Ctrl", "Strg");
					origPropValueText = origPropValueText.replace(arr[count],temp3);
					System.out.println("ctrl: "+arr[count]);
				}
				if(curLanguage=="de"&& arr[count].contains("Ctrl")&& arr[count].contains("Shift")){
					//keyboard key 'ctrl' has different translation for some languages, this handles translation for german
					
					arr[count]=arr[count].replace("Ctrl", "Strg");
					temp3=arr[count].replace("Shift", "Umschalttaste");
					origPropValueText = temp3;
					
					
					System.out.println("inside Array");
				}
				//System.out.println("inside Array");
			}
			
		
			
			
			convertedString = origPropValueText;
			tab=true;
		}
		
		if (origPropValueText.contains(">")){
			
			arr = origPropValueText.split(">");
			for (int count=0;count<arr.length;count++){
				arr[count]=arr[count].trim();
			}
			if (arr.length>0){
				temp1=englishAllPropsHm.get(origPropValueText);
				if (temp1!=null){
					convertedString=getLocalizedString(temp1);
					arrow=true;	
				}
			}
		}
		
		/*if (origPropValueText.contains(">") && ampersand){
			origPropValueText = origPropValueText.replaceAll("&", "");
			temp1=englishAllPropsHm.get(origPropValueText);
			convertedString=getLocalizedString(temp1);
			arrow = true;	
			ampersand = true;
		}*/
		
		
		if (!tab && !arrow)
			convertedString = getLocalizedString(origPropValueText);
		
		if (convertedString == null) {
			// could not find by origPropValueText key, could be hard code english
			// try to found key by origPropValueText as value string in english all properties file
			System.out.println("inside other condition statement");
			String key = englishAllPropsHm.get(origPropValueText);
	/////	//System.out.println("convereted PRINT 1: "+key); ----------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			if (key != null) {
				logDebug("Found key [" + key + "] by given [" + origPropValueText + "] in [" + enAllPropsFile + "]");
				convertedString = getLocalizedString(key);
				
			} else {
				if ("Run\tCtrl+R".equals(origPropValueText) 
						|| "Schedule\tCtrl+Shift+R".equals(origPropValueText)) {
					key = englishAllPropsHm.get(origPropValueText.split("\t")[0]);
					convertedString = getLocalizedString(key) + origPropValueText.substring(origPropValueText.split("\t")[0].length());
					
				}
				
				if (convertedString == null) {
					logWarning("No property set for [" + origPropValueText + "], need confirm with developer.");
				}
			}
		}
		//System.out.println("PRINT 2: "+convertedString);
		if (ampersand){ 
			convertedString="&"+convertedString;
		//	System.out.println("my string:"+(String)convertedString);
		}

		return convertedString;
	}

	// get corresponding localized value from language all properties file
	private static String getLocalizedString(String searchKey) {
		String convertedStr = null;

		try {
			FileReader freader;
			LineNumberReader lnreader;

			freader = new FileReader(new File(curLangAllPropsFile));
			lnreader = new LineNumberReader(freader);

			String line = "";
			String key = "";
			String[] tmpArr;

			while ((line = lnreader.readLine()) != null){				
				if(line.startsWith("#") || "".equalsIgnoreCase(line.trim()) || line == null || line.indexOf("=") == -1) {
					continue; //skip comments/empty line
				}

				tmpArr = line.split("=");
				key = tmpArr[0];	

				if (searchKey.equals(key)) {
					if (tmpArr.length > 1) {
						convertedStr = tmpArr[1];
						logDebug("Found key [" + searchKey + "] w/ value [" + convertedStr + "]");
					} else {
						logWarning("Found key [" + searchKey + "] w/o value");
					}

					break;	// found
				}
			}

			freader.close();
			lnreader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return convertedStr;
	}

	// write updated doc to target RFT Map file
	private static void generateLocalObjectMap(String newMapFileName) {
		try {
			// Transform the doc to String
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(doc);

			transformer.transform(source, result);

			String xmlString = result.getWriter().toString();

			// Write to new RFT Map File	
			File generatedMapFile = new File(newMapFileName);
			FileOutputStream fos = new FileOutputStream(generatedMapFile);
			OutputStreamWriter out = new OutputStreamWriter(fos, ENCODING);

			out.write(xmlString);

			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	// converts unicode String to unicoded string
	private static String getUnicodedStr(String origUnicodeStr) {
		String encodedStr = null;

		try {
			String localizedUnicodeStr = "";
			if (origUnicodeStr.contains("\\u")) {
				String[] unicodeValArr = origUnicodeStr.split("\\\\u");
				for (String curStr : unicodeValArr) {
					if (! "".equalsIgnoreCase(curStr)) {

						if (! curStr.matches("[0-9a-fA-F]{4}.*")) { // non-unicode part
							localizedUnicodeStr += curStr;
							continue;
						}

						String leftPart = curStr.substring(4);
						curStr = curStr.substring(0, 4);

						int intValue = Integer.parseInt(curStr, 16);
						localizedUnicodeStr += (char)intValue;

						if (leftPart != null && ! "".equalsIgnoreCase(leftPart)) {
							localizedUnicodeStr += leftPart;
						}
					}
				}
			} else {
				localizedUnicodeStr = origUnicodeStr;
			}

			byte[] encodedBytes = localizedUnicodeStr.getBytes(ENCODING);

			encodedStr = new String(encodedBytes, ENCODING);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return encodedStr;
	}

	private static Map<String,String> prepareEnAllPropsHm() {
		Map<String,String> enAllPropsHm = new HashMap<String, String>();
		
		try {
			FileReader freader;
			LineNumberReader lnreader;

			freader = new FileReader(new File(enAllPropsFile));
			lnreader = new LineNumberReader(freader);

			String line = "";
			String key = "";
			String value = "";
			String[] tmpArr;

			while ((line = lnreader.readLine()) != null){
				//skip comments/empty line
				if(line == null || "".equalsIgnoreCase(line.trim()) || line.startsWith("#") || line.indexOf("=") == -1) {
					continue;
				}

				tmpArr = line.split("=");
				key = tmpArr[0];
				value = (tmpArr.length > 1)? tmpArr[1] : "NULL"; // keep default value
				value = value.replaceAll("&", "");

				//logDebug(key + "=" + value);
				enAllPropsHm.put(value, key);	// a key/value reverse map!!!
			}

			freader.close();
			lnreader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return enAllPropsHm;
	}

	private static void logError(String message) {
		System.out.println("\t!!!! " + message + " !!!!");
	}

	private static void logWarning(String message) {
		System.out.println("\t\t#### " + message + " ####");
	}

	private static void logInfo(String message) {
		if (logLvl >= 1) {
			System.out.println(message);
		}
	}

	private static void logDebug(String message) {
		if (logLvl >= 2) {
			System.out.println(message);
		}
	}
}