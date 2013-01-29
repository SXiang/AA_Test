package lib.acl.helper.sup;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

import lib.acl.tool._printObjects;
import lib.acl.tool.htmlRFTHandler;
import lib.acl.util.FileUtil;
import lib.acl.util.NLSUtil;
import lib.acl.util.UnicodeUtil;

import ACL_Desktop.conf.beans.ProjectConf;
import conf.beans.TimerConf;

import com.rational.test.ft.AmbiguousRecognitionException;
import com.rational.test.ft.ObjectNotFoundException;

import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.MethodInfo;
import com.rational.test.ft.value.RegularExpression;
import com.rational.test.ft.vp.*;

import conf.beans.FrameworkConf;

public class ObjectHelper extends RFTGuiFinderHelper{
	
	//objectHelper is shared by all test project in the framework,
	// don't create methods for individual project here
		
	    
		public String actualValue="";
		public boolean useFindMethod= false;
		public int indexOfObject = 0;
		public int currentPostNum=0, prePostNum=0;
		public 	boolean found = false;
		public static ArrayList<TestObject> foundObjs;
		public static int wt = 0;
		public htmlRFTHandler htmlhandler;
		protected String lineDelimiter = "";
		
		public int checkBoxWidth=10, checkBoxHeight=10;
//		public int checkBoxWidth=20, checkBoxHeight=20;
		public int iconWidth=14, iconHeight=14;
		public String imgPath = "";//AX_GatewayPro.conf.beans.ProjectConf.expectedDataDir+"baselineImages\\";
		public String imgActualPath = imgPath+"temp\\actual_";
		public String tmpImgPathDefault = imgActualPath + "tableIcon.jpg";
		
		public final String TABLE_TYPE_GeneralData = "GeneralData";
		public final String TABLE_TYPE_CompleteTable = "CompleteTable";
		public final String TABLE_TYPE_LinkToCompleteTable = "LinkToCompleteTable";
		public final String TABLE_TYPE_LayoutOnly = "LayoutOnly";
		public final String TABLE_TYPE_LinkToLayoutOnly = "LinkToLayoutOnly";
		public final String TABLE_TYPE_LinkToDataBroken = "LinkToDataBroken";
		public final String TABLE_TYPE_MasterCompleteTable = "MasterCompleteTable";
		public final String TABLE_TYPE_MasterLayoutOnly = "MasterLayoutOnly";
		public final String TABLE_TYPE_CompleteAnalytic = "CompleteAnalytic"; // Added to baseline - steven
		public final String TABLE_TYPE_MasterAnalytic = "MasterAnalytic";
		public final String TABLE_TYPE_AnalyticLink = "AnalyticLink";
		public final String TABLE_TYPE_AnalyticLinkBroken = "AnalyticLinkBroken";
		
		public final String TABLE_TYPE_GatewayLink = "GatewayLink";
		public final String TABLE_TYPE_GatewayLinkMouseOver = "GatewayLinkMouseOver";

	
		static String whitespace_chars =  ""       /* dummy empty string for homogeneity */
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
	    //*******   get TestObjects  *********************
		
		

		// get object by find method - should be faster than recursive function.
		
		public TestObject getTestObject(TestObject topObject, List propList, int index){	
			
			TestObject[] objs = null;
			TestObject obj = topObject;
			
			try {			
				   objs = obj.find(propList);
	                  if(objs != null){
	                      if(objs.length>index)
	                          obj = objs[index];
	                      else 
	                          obj = objs[0];
	                     
	                  }else{
	                	  obj = null;
	                  }       
			}catch(Exception e){
				obj = null;
				logTAFException(e);
			}
			finally{			
				unregister(objs);
			}
			return obj;
		}
		
		//* get object recursively *
		
		public TestObject getTestObject(TestObject topObject, 
				String class1,String value1){
			return getTestObject(topObject, 
					class1,value1, "", "",
					true,0);
		}
		
		public TestObject getTestObject(TestObject topObject, 
				String class1,String value1, String class2,String value2){
			return getTestObject(topObject, 
					class1,value1, class2, value2,
					true,0);
		}
		
		public TestObject getParent(TestObject parent, 
				String class1,String value1, String class2,String value2){
			
			int i=0;
			boolean found = false, found1= false, found2=false;

			while(!found&&parent!=null&&i++<10){
				found1=false;
				found2=false;
				if(parent.getProperties().keySet().contains(class1)){
					found1= parent.getProperty(class1).toString().equalsIgnoreCase(UnicodeUtil.removeUnicodeSpace(value1));
					
//					if(class1.equalsIgnoreCase(".text")&&
//							parent.getProperty(class1).toString().trim().contains("5")){
//		    			logTAFInfo("#### parentGui 1#### "+class1+"1 = '"+parent.getProperty(class1).toString()+
//		    					"'\n\t#### ValueInp 1#### "+"expected value1 = '"+UnicodeUtil.removeUnicodeSpace(value1)+"'");
//		    			logTAFInfo("#### parentGui2 #### "+class2+"2 = '"+parent.getProperty(class2).toString()+
//		    					"'\n\t#### ValueInp2 #### "+"expected value2 = '"+UnicodeUtil.removeUnicodeSpace(value2)+"'\n\n");
//					}
				}
				if(parent.getProperties().keySet().contains(class2)){
					found2= parent.getProperty(class2).toString().equalsIgnoreCase(UnicodeUtil.removeUnicodeSpace(value2));
				}
				if(found1&&found2){
					return parent;
				}
				try{
				  parent = parent.getParent();
				}catch(Exception e){
					parent = null;
				}
				
			}
			return null;
		}
		//*******   New find methods *******************
		
		// *** Without methodArray
		public TestObject getTestObject(TestObject topObject,String[] propertyArray, String[] valueArray){
			return getTestObject(topObject,propertyArray, valueArray,
					true, 0);
		}
		public TestObject getTestObject(TestObject topObject,String[] propertyArray, String[] valueArray,
				int index){
			return getTestObject(topObject,propertyArray, valueArray,
					true, index);
		}
		public TestObject getTestObject(TestObject topObject,String[] propertyArray, String[] valueArray,
				boolean children){
			return getTestObject(topObject,propertyArray, valueArray,
					children, 0);
		}
		
		public TestObject getTestObject(TestObject topObject,String[] propertyArray, String[] valueArray,
				boolean children,int index){
			
			String[] methodArray = new String[propertyArray.length];
			
			return getTestObject(topObject,propertyArray, valueArray, methodArray,
					children, index);
		}
		// *** With methodArray
		public TestObject getTestObject(TestObject topObject,String[] propertyArray, String[] valueArray, String[] methodArray){
			return getTestObject(topObject,propertyArray, valueArray, methodArray,
					true, 0);
		}
		public TestObject getTestObject(TestObject topObject,String[] propertyArray, String[] valueArray, String[] methodArray,
				int index){
			return getTestObject(topObject,propertyArray, valueArray, methodArray,
					true, index);
		}
		public TestObject getTestObject(TestObject topObject,String[] propertyArray, String[] valueArray, String[] methodArray,
				boolean children){
			return getTestObject(topObject,propertyArray, valueArray, methodArray,
					children, 0);
		}
		
		public TestObject getTestObject(TestObject topObject,String[] propertyArray, String[] valueArray, String[] methodArray,
				boolean children,int index){
			
			//waitForLoading(topObject);
			
			boolean findLast = false;
			if(topObject==null||
					propertyArray.length != valueArray.length||
					propertyArray.length != methodArray.length||
					valueArray.length != methodArray.length){
				logTAFWarning("Not valid parameters for getTestObject from "+topObject.toString());
				return null;
			}
			
			//logTAFDebug("Before search, foundObjs = "+foundObjs);	
			foundObjs.clear();
//			logTAFDebug("Before search, foundObjs = "+foundObjs);
			getTestObject(topObject,propertyArray, valueArray, methodArray,
					children, index, true);
//			logTAFDebug("After search, foundObjs = "+foundObjs);
			
			
			if(index == -1){
				findLast = true;
				index = foundObjs.size()-1;
			}
			if(index>=0&&index<foundObjs.size()){
				    logTAFDebug("Return object["+index+"] in total "+foundObjs.size()+" found(s) : '"+foundObjs.get(index).getDescriptiveName()+"'");
			        return foundObjs.get(index);
			}
			else{
				    logTAFDebug("Only "+foundObjs.size()+" found(s) ', are you sure there should be "+(findLast?" at least 1 ": (index+1))+" objects with these criterion?");
				    return null;
			}
		}
		
		public TestObject getTestObject(TestObject topObject,String[] propertyArray, String[] valueArray, String[] methodArray,
				boolean children, int index, boolean loop){
			
			// index = -1 --> find last 
			// index =0 --> find first
			// index = n>0 --> find nth 
			
			TestObject[] objs;
			TestObject current = null;			
            boolean done = false;
    		
			if(children)
			    objs = topObject.getChildren();
			else
				objs = topObject.getOwnedObjects();
			
			int length = objs.length;
			
			for (int i=0; i<length&&objs[i]!=null&&!done; i++){
				current = objs[i];			
//				logTAFDebug(" ************      Current searching object: "+current.getDescriptiveName()+" ************");
//				logTAFDebug("                .classIndex = "+current.getProperties());
				if(matchProperties(current,propertyArray, valueArray, methodArray)){
					foundObjs.add(current);
//					logTAFDebug(" ************************  found a match !!!");
				}
				if(foundObjs.size()>=index+1&&index!=-1){
					done = true;
		    	}else {
					getTestObject(current,propertyArray, valueArray, methodArray,
							children, index, loop);
				}
			}
			return null;
		}
		
		public boolean matchProperties(TestObject to, String[] propertyArray, 
				                       String[] valueArray, String[] methodArray){
			boolean match = false;
			int size = propertyArray.length;
			boolean propertyExist = false;
			for(int i=0;i<size;i++){
				propertyExist = to.getProperties().keySet().contains(propertyArray[i]);
//				logTAFDebug("Property "+i+" of size "+size+" - '"+propertyArray[i]+"' found?"+propertyExist);
//				logTAFDebug("method "+i+" - '"+methodArray[i]+"'");
				if(!propertyExist)
					return false;
				logTAFDebug("Property - '"+propertyArray[i]+
						"': Expected value - '"+valueArray[i]+"' Actual value - '"+to.getProperty(propertyArray[i]).toString());
				if(methodArray[i].equals("equals")){
					match = (to.getProperty(propertyArray[i]).toString().equals(valueArray[i]));
				}else if(methodArray[i]==null||methodArray[i].equals("matches")){
					match = (to.getProperty(propertyArray[i]).toString().matches(valueArray[i]));
				}else if(methodArray[i].equals("endsWith")){
					match = (to.getProperty(propertyArray[i]).toString().endsWith(valueArray[i]));
				}else if(methodArray[i].equals("contains")){
					match = (to.getProperty(propertyArray[i]).toString().contains(valueArray[i]));
				}else if(methodArray[i].equals("startsWith")){
					match = (to.getProperty(propertyArray[i]).toString().startsWith(valueArray[i]));
				}else if(methodArray[i].equals("equalsIgnoreCase")){
					match = (to.getProperty(propertyArray[i]).toString().equalsIgnoreCase(valueArray[i]));
				}else{
					match = (to.getProperty(propertyArray[i]).toString().matches(valueArray[i]));
				}
				if(!match)
					return false;
			}
			return match;
		}
			

        // ********  end of new find methods *******************
		
		public TestObject getTestObject(TestObject topObject, 
				String class1,String value1, String class2,String value2,
				boolean children, int index){

			waitForLoading(topObject);
			
			return getTestObject(topObject, 
					class1,value1, class2,value2,
					 children, index, true);
		}
		
		public TestObject getTestObject(TestObject topObject, 
				String class1,String value1, String class2,String value2,
				boolean children, int index, boolean ready){
			// index = -1 --> find last 
			// index =0 --> find first
			// index = n>0 --> find nth -- !!! not ready for use yet 

			if(topObject == null)
				return null;
			
			TestObject[] objs;
			boolean found1 = false,
			        found2 = false;
			
			String[] value = new String[]{value1,value2};//we can use an array to organize all the values later

			
	       // ***********  for possible pattern match with some prefix of the first value or the descriptive name

			String postFix = "*", inputText=value1;
			
			boolean prefix = false;
			
			if(inputText.substring(inputText.length()-1).equals(postFix)){
	    		value1 = inputText.substring(0,inputText.length()-1);   		
	    		prefix = true;
	    	}
            //****************
			
			
//			sleep(TimerConf.pageRefreshTime);
			if(children)
			    objs = topObject.getChildren();
			else
				objs = topObject.getOwnedObjects();
//			sleep(TimerConf.pageRefreshTime);
			TestObject current = null,objFound = null;
			
			ArrayList<TestObject> targets;
			
			int length = objs.length;
			for (int i=0; i<length&&objs[i]!=null; i++){
				current = objs[i];
				found1 = true;
				found2 = true;
				
//				logTAFInfo(current.getDescriptiveName()+" ||| "+current.getProperty(".text"));
//				try{
//				     logTAFInfo("'"+current.getProperty(".class")+"' ||| '"+(current.getProperty(".title").toString()).trim()+"'");
//				}catch(Exception e){
//					
//				}
				if(class1 !=""){
					if(current.getProperties().keySet().contains(class1)){
						if(prefix){
							found1= UnicodeUtil.convertUnicodeSpace(current.getProperty(class1).toString()).startsWith(value1);							
						}
						else{
					        found1= UnicodeUtil.convertUnicodeSpace(current.getProperty(class1).toString()).equalsIgnoreCase(value1);
						}
//						if(class1.equalsIgnoreCase(".text")&&
//								current.getProperty(class1).toString().trim().contains("5")){
//			    			logTAFInfo("**** ValueGui 1**** "+class1+"1 = '"+UnicodeUtil.convertUnicodeSpace(current.getProperty(class1).toString())+
//			    					"'\n\t**** ValueInp 1**** "+"expected value1 = '"+value1+"'");
//			    			logTAFInfo("**** ValueGui2 **** "+class2+"2 = '"+UnicodeUtil.convertUnicodeSpace(current.getProperty(class2).toString())+
//			    					"'\n\t**** ValueInp2 **** "+"expected value2 = '"+value2+"'\n\n");
//						}
					}
					else
						found1 = false;
				}
				else {
					if(prefix){
					   found1= UnicodeUtil.convertUnicodeSpace(current.getDescriptiveName()).startsWith(value1);
					   
					}
				    else
					   found1= UnicodeUtil.convertUnicodeSpace(current.getDescriptiveName()).equalsIgnoreCase(value1);
				}
				
	            if(class2 != ""){
	                 if(current.getProperties().keySet().contains(class2))
	            	    found2 = UnicodeUtil.convertUnicodeSpace(current.getProperty(class2).toString()).equalsIgnoreCase(value2);    
	                 else
	                	 found2 = false;
	            }
				if(found2&&found1){
					
					if(prefix){
						currentPostNum = getNumberBetween(current.getProperty(".text").toString(),'(',')');
//						logTAFDebug("curPostNum = "+currentPostNum+", prePostNum = "+prePostNum);
						if(currentPostNum<prePostNum){
							found = true;						
						}else{
							objFound = current;
							prePostNum = currentPostNum;
						}
					}else{
						objFound = current;
					}
					
					if(index == 0 || found){						
						//currentPostNum = 0;
						return objFound;
					}
				}else{
					//logTAFInfo(current.getDescriptiveName()+":"+objName+" ||| "+current.getProperty(".class")+":"+objClassName);
				}
			 
				if(prefix){
					if(!found){
						current = getTestObject(current, 
		            		class1,value1+postFix,class2,value2,
		            		children,index,ready);
					}else{
						return objFound;
					}
				}else{
					current = getTestObject(current, 
							class1,value1,class2,value2,
							children,index,ready);
				}
	            if(current != null)
	            	objFound = current;
			}
//			if(objFound != null)
				//logTAFInfo("******Return: "+objFound.getDescriptiveName()+":"+objName+" ||| "+objFound.getProperty(".class")+":"+objClassName);
			
			//currentPostNum = 0;
			return objFound;
		}

		
		// **** get objects by invoking user specified method ****
		
		public TestObject findTabItem(TestObject sTabFolderObject,String name,String method,String signature,Object[] objs){
			TestObject[] tabs;
			
			if(signature == null)
			    tabs = (TestObject[])sTabFolderObject.invoke(method);
			else
				tabs = (TestObject[])sTabFolderObject.invoke(method,signature,objs);
			
			TestObject to = null;
			if(null != tabs){
				int tabCount = tabs.length;
				for(int i=0; i< tabCount; i++){
					String itemname = (String)tabs[i].getProperty("text");
					if(itemname.indexOf(name) != -1){
						to = tabs[i];
						break;				
					}
				}
			}
			return to;
		}
		
		
		// **** IWindow operations ************
		public TestObject enableIWindow(String testDomain,String text){
			return enableIWindow(testDomain,"",text,true);
		}
		public TestObject enableIWindow(String testDomain,String processName, String text, boolean wait){
			RootTestObject root = null;
			IWindow current = null,currents[];
			Long hWnd   = null;
			Integer pId = null;
			int numFind=0;
			TestObject[] testObj=null;
			TestObject domainObject=null;

			while(pId==null&&hWnd==null&&numFind++<30){
				sleep(2);
				root = RootTestObject.getRootTestObject();
				current = RationalTestScript.getScreen().getActiveWindow(); 
				logTAFDebug("Active Window '"+current.getText());
			    current.activate();
			    current.getScreenPoint();
//                currents = RationalTestScript.getTopWindows();
//                for(IWindow c:currents){
				 hWnd = new Long(current.getHandle());
				 pId  = new Integer(current.getPid());
//                }

				if(!wait)
					break;
			}
			
			if(testDomain.equalsIgnoreCase("Win")&&hWnd!=null&&pId!=null){
				testObj = root.find(atChild(".hwnd", hWnd, ".processId", pId));
			}else {
				testObj = root.find(atDescendant(".processName",processName,"Text",text));
			}
			if(testObj!=null&&testObj.length!=0){
				domainObject = testObj[0];
			}else{
				domainObject = null;	
				logTAFDebug(testDomain+" Domain not enabled successfully!!!");
			}

			return domainObject;

		}
        
        public TestObject getDomainObject(String testDomain,String className, String text,boolean wait){
        	String actualDomainName = null;
        	TestObject to = null;
        	
        	boolean done = false;
        	TestObject top = null,myto=null;
            String prop[] = {"\\.class","Text"};
            String value[] = {className,text};
            String comp[] = new String[prop.length];
            int numFind = 0;
            for(int i =0;i<comp.length;i++){
            	comp[i]="matches";
            }
            //***************************************
            //enableIWindow(testDomain,wait);
            while(!done&&numFind++<30){
            	sleep(2);
            	DomainTestObject domains[] = getDomains();
            	for (int j = 0; j < domains.length&&!done; j++) 
            	{
            		actualDomainName = domains[j].getName().toString();
            		if ((actualDomainName != null) && (actualDomainName.equals(testDomain)))
            		{
            			for (int topObjects=1; topObjects<domains[j].getTopObjects().length&&!done; topObjects++)
            			{
            				top = domains[j].getTopObjects()[topObjects];           				
            				try{
            				 to = getTestObject(top,prop,value,comp,0);
            				 logTAFDebug("Topobject "+j+":"+top);
            				}catch(Exception e){
            					logTAFDebug("Topobject "+j+":"+e.toString());
            				}
            			     //to = top.find(atDescendant(".class",props,".name",text));
            				if(to!=null){
            					done = true;
            					logTAFDebug("Domain Object found: "+to);
            				}
            			}
            		}
            	}
            	if(!wait)
            		break;
            }
            
            if(done)
            	return to;
            else{
            	//top = RootTestObject.getRootTestObject().objectAtPoint(getDialog(text,null).getScreenPoint());
            	logTAFDebug("Domain Object not found: "+text+"  -  "+top);
            }
            return top;
        }
		public IWindow getIWinObject(IWindow iTopwin, String objText, String objClassName,boolean children){
			IWindow[] wins;
			if(children)
			    wins = iTopwin.getChildren(); //getOwned()
			else
				wins = iTopwin.getOwned();
			
			IWindow current = null;
			
			int length = wins.length;
			for (int i=0; i<length; i++){
				current = wins[i];
				if(current.getText().matches(objText)
						&& current.getWindowClassName().matches(objClassName)){
					return current;
				}
			}return null;
		}
		

	   //****** getSubitems and other objects ***************	
		public Point getCenterPoint(GuiTestObject to){
			
			Rectangle rec = to.getScreenRectangle();
//			Rectangle rec = (Rectangle) to.getProperty(".bounds");
			Point p = new Point();
			int width = rec.width;
			int height = rec.height;
			
//			if(width==0)
//				width =7;
//			if(height == 0)
//				height = 7;
			p.x = rec.x +width/2;
			p.y = rec.y +height/2;
			
			return p;
		}
			
		
		
		public void waitForGUIObjectExistence(TestObject to, int maxTime,int retryBetween, String name, boolean expected){
			
			try{
				to.waitForExistence(maxTime,retryBetween);	
			}catch(ObjectNotFoundException e){
				if(expected)
				   logError(name+" does not exist");
				else
					logInfo(name+" does not exist");
			}
		}
		
		public boolean isBoundsVisiable(TestObject to){
			boolean visiable = false;
			try{
				Rectangle rect;
				rect = (Rectangle) to.getProperty(".bounds");
				if(rect.width !=0 && rect.height != 0){
					visiable = true;
				}
			}catch(Exception e){
				// do nothing 
			}
			
			return visiable;
		}
		
		
		
		// ****************** maneError method is used to report invalid input ***************
		public boolean nameError(String name){
			boolean invalid = false;
			char[] invalidChar = {'/','\\',':','*','?','<','>','|'};
            
			if(name.equals("")){
				return true;
			}
			for (char ichar:invalidChar){
				if(name.indexOf(ichar)!=-1){
					invalid = true;
					break;
				}
			}
			return invalid;
		}
	    
		// RFT got objects recognition problem sometimes, we are trying this way to solve it - steven
		public boolean rftPreparation(TopLevelTestObject tlto){
			try{
				tlto.restore();
				tlto.maximize();
			}catch(Exception e){
				logTAFException(e);
			}
			return true;
		}
		public void moveScrollbar(BrowserTestObject browser, GuiTestObject logo,String direction){
		    int numMove = 50;
		    String shortcut = "{PGUP}";
		    
		    //To bottom
		    if(direction.equalsIgnoreCase("END")){
		    	shortcut = "{END}";
		    	numMove = 1;
		    	
		    }else if(direction.equalsIgnoreCase("LEFT")){
		    	shortcut = "{LEFT}";
		    }else if(direction.equalsIgnoreCase("RIGHT")){
		    	shortcut = "{RIGHT}";
		    }else if(direction.equalsIgnoreCase("UP")){
		    	shortcut = "{UP}";
		    }else if(direction.equalsIgnoreCase("DOWN")){
		    	shortcut = "{DOWN}";
		    	
		    //To top	
		    }else if(direction.equalsIgnoreCase("HOME")){
		    	shortcut = "{HOME}";
		    	numMove =1;
		    }
		    if(logo != null){
		    	click(logo);
			 	sleep(2);
		    }
			for(int i=0;i<numMove;i++){
		     browser.inputKeys(shortcut);
			}
			sleep(TimerConf.pageRefreshTime);
		}
		
		public  boolean isLoading(TestObject to){			
			boolean loading = false;
			TestObject[] tables = null;			
			String gwth = "LOADING",gwtd="Please wait..";
			
			
		//	if(!new htmlRFTHandler().isInProgress()){
				tables = getRootTestObject().find(atDescendant(".text",gwtd),false);
				if(tables.length>0){
					if(getRootTestObject().find(atDescendant(".text",gwth),false).length>0){
						loading = true;
						logTAFDebug("Loading table found:'"+tables[0].getProperty(".text")+"'");
					}
				}
//			}else{
//				loading = true;	
//				logTAFDebug("Loading table found");
//			}

			return loading;
		}
		
		public  boolean waitForLoading(TestObject to){
			return waitForLoading(to, 120 );
		}
		
		public  boolean waitForLoading(TestObject to, int maxTime){
			if(!isWeb){
				return true; // this wait is just for web application
			}
            boolean waiting =  false,done = true;
			int startTime=0,interval = 2;
			//logTAFDebug("Check loading status of '"+to.getProperty(".class")+"'");
			while(startTime<maxTime&&isLoading(to)){
				if(startTime<interval){
					logTAFInfo("LOADING, Please wait..");
					waiting = true;
				}
				sleep(interval);
				startTime += interval;
				
			}
			if(isLoading(to)){     
				//logTAFError("Application spent more than "+maxTime+" seconds in loading");
				refreshWindow((BrowserTestObject) to.getTopParent());
				if(isLoading(to)){
				   done = false;
				   logTAFError("Application spent more than "+maxTime+
						   " seconds in loading, refreshing the window could not solve the problem "+
						   "");
				}else{
					logTAFError("Application spent more than "+maxTime+
							   " seconds in loading, Loading table disapeared after refreshing the browser");
				}
			}
			return done&&waiting;
		}
		
		/*public static boolean refreshWindow(){
			return refreshWindow(getScreen().getActiveWindow());
		}*/
		public  boolean refreshWindow(){
			return refreshWindow(getScreen().getActiveWindow());
		}
		/*public static boolean refreshWindow(ITopWindow browser){
	        boolean done= false;
	    	
			try{
				browser.activate();
				sleep(1);
				browser.inputKeys("{F5}");
				sleep(5);
				browser.maximize();
				sleep(1);
				done = true;
			}catch(Exception e){
				logTAFWarning("Failed to refresh window by F5 "+e.toString());
			}
			return done;
		}
		
		public static boolean refreshWindow(ITopWindow browser,GuiTestObject variable){
	        boolean done= false;
	    	
			try{
				browser.activate();
				sleep(1);
				browser.inputKeys("{F5}");
				
				if(variable.exists()){
					click(variable);
					
				}
			
				
				sleep(5);
				browser.maximize();
				sleep(1);
				done = true;
			}catch(Exception e){
				logTAFWarning("Failed to refresh window by F5 "+e.toString());
			}
			return done;
		}
		*/
		public static boolean  refreshWindow(ITopWindow browser){
	        boolean done= false;
	    	
			try{
				browser.activate();
				sleep(1);
				browser.inputKeys("{F5}");
				
				
				
				sleep(5);
			//	browser.maximize();
				sleep(1);
				done = true;
				
			}catch(Exception e){
				logTAFWarning("Failed to refresh window by F5 "+e.toString());
			}
			return done;
		}
		
		
		
		
		public void  verifyUrl(BrowserTestObject browser,String url,TestObject to,String item){
			boolean loaded = false;
			boolean downloadWindow = false;
			int maxSeconds = 30,checkIn = 0;
			String[] urlArray;
			try{
			    browser.waitForExistence(TimerConf.maxWaitTime,TimerConf.waitBetweenRetry);
			    sleep(1);
			}catch(Exception e){
				logTAFWarning("IE crashed ? ");
			}
			String oriUrl = browser.getProperty(".documentName").toString();
			String status = "";
	
			if (!url.startsWith("https")){
				urlArray = url.split(" ");
				for(int i=0;i<urlArray.length;i++){
					if(urlArray[i].contains("https")){
						url=urlArray[i];
						break;
					}
				}
				//url=urlArray[2];
				System.out.println("MY URL: "+url);
				System.out.println();
			}
			
				if(browser.loadUrl(url)){
					System.out.println("TRUE");
				}
				else{
					System.out.println("False");
				}
				System.out.println();
				try{
				      to.waitForExistence(30,2);
				      downloadWindow = true;
				      sleep(1);
				      while(to.exists()){
				        ((GuiTestObject) to).click();
				         logTAFInfo("File downloading window appeared for this url");
				         sleep(3);
				      }
				      
				}catch(Exception e){
					while(!loaded&&checkIn<maxSeconds){
						checkIn += 3;
						sleep(3);
						try{
							browser.waitForExistence(TimerConf.maxWaitTime,TimerConf.waitBetweenRetry);
							sleep(1);
							status = browser.getProperty(".statusText").toString();
						}catch(Exception e1){
							//logTAFWarning("");
						}
						if(status.equalsIgnoreCase("Done")){		
							loaded = true;
						}else{
							logTAFWarning("Browser is loading '"+url+"' ...");
						}
					}
				}

				// It will never come to this line as there is a certificate prompt page before download
				//logTAFStep("It leads to a correct file downloading window for this file");


				if(loaded){
					String loadedUrl = browser.getProperty(".documentName").toString().trim();
					if(loadedUrl.equalsIgnoreCase(url))
					  logTAFStep("Url has been loaded  correctly for this item");
					else
					  logTAFError("Url has not been loaded  correctly for this item");
					//browser.loadUrl(oriUrl);
				}
				else if(!downloadWindow){				
					logTAFWarning("Url took longer time to be loaded than usual");
					browser.loadUrl(oriUrl);
				}else{
					//Close download dialogs if there is one
				}
				sleep(3);
				browser.loadUrl(oriUrl);

		}
		
//*********************** Override RFT Methods ***********
		/** 
		 * Overrides the base implementation of 
		 * onObjectNotFound. Whenever this event 
		 * occurs, look through all the active domains 
		 * (places where objects might be found). 
		 * For HTML domains (Java and other domains 
		 * are skipped) finds all the top objects. 
		 * If the top object is an Html Dialog, 
		 * close to dismiss the dialog. 
		 * Logs a warning when this happens. */ 

		public void onObjectNotFound( 
				ITestObjectMethodState testObjectMethodState) { 
			// Implementation of event handler 
			boolean dismissedAWindow = false,dismissedABrowser=false;
			boolean loading = false;
			if(!isWeb){
				dismissedAWindow = 
					//new ACL_Soundwave.AppObject.TAFGetExcelObjects().catchExcelAddinErr()||
					//new ACL_Soundwave.AppObject.TAFGetExcelObjects().cancelAcernoMsg()||
					dismissWinDialog(FrameworkConf.ironportErrTitle);//||
					dismissWinDialog("Visual Studio Just-In-Time Debugger");
					//dismissAppDialog(FrameworkConf.excelErrTitle)||
	                //dismissAppDialog(FrameworkConf.axGatewayProErrTitle);
				
				//return; // Following methods are mainly for web applications, we'll add some for GWP later - steven.
				
			}else{
				htmlhandler = new htmlRFTHandler();
				dismissedABrowser = 
					             htmlhandler.dismissBrowser(FrameworkConf.docGWTitle)||
									htmlhandler.dismissBrowser(FrameworkConf.docEMTitle)||
										htmlhandler.dismissBrowser(FrameworkConf.docAXAdminTitle);
			    loading = testObjectMethodState.getScriptCommandFlags().check(ScriptCommandFlags.LOADING);
			    if(loading&&
					wt<conf.beans.TimerConf.maxPageLoadingTime){
				    if(wt==0)
					   logTAFWarning("Loading, please wait");
					wt += TimerConf.pageRefreshTime;
					sleep(TimerConf.pageRefreshTime);
			    }else{
			    	sleep(1);
			    	if(wt>0){
			    		logTAFWarning("Browser taking more than '"+wt+"' seconds to load");
			    	}else{
			    		refreshWindow();
			    	}
			    	wt = 0;
			    	loading = false;
			    }
			}
			
			if (dismissedABrowser)
			{
				logTAFDebug("Dismissed A html popup,Trying to find Object again...");
				testObjectMethodState.findObjectAgain();
			}else if (dismissedAWindow){
				//  try again
				logTAFDebug("Dismissed A windows popup,Trying to find Object again...");
				testObjectMethodState.findObjectAgain();
			}else if(loading){
				logTAFDebug("Application is in Loading status,Trying to find Object again...");
				testObjectMethodState.findObjectAgain();
			}

		} 

		  public void onAmbiguousRecognition(ITestObjectMethodState testObjectMethodState,
		          TestObject[] choices,
		          int[] scores){
//                  if(!isWeb){
////                	  logTAFWarning(scores.length+" '"+testObjectMethodState.getTestObject().getDescriptiveName()+
////    			    		  " found, will return the one with index = "+indexOfObject);
////    				  
////    			      testObjectMethodState.setFoundTestObject(choices[indexOfObject]);
////    			      testObjectMethodState.setReturnValue(choices[indexOfObject]);
//                	  return; // don't use it on gwp now -- Steven
//                  }
			      useFindMethod = true; // incase there is a find method defined for this object.
			      logTAFWarning(scores.length+" '"+testObjectMethodState.getTestObject().getDescriptiveName()+
			    		  " found, will return the one with index = "+indexOfObject);
				  
			      testObjectMethodState.setFoundTestObject(choices[indexOfObject]);
			      testObjectMethodState.setReturnValue(choices[indexOfObject]);
		  }
          
		  
		  // Warpper for RFT methods

		  public boolean waitForExistence(TestObject to){
			  return waitForExistence(to,TimerConf.maxWaitTime,TimerConf.waitBetweenRetry);
		  }
		  public boolean waitForExistence(TestObject to, int maxWaitTime, int waitBetweenRetry){
              boolean pass = false;
			  try{
			      to.waitForExistence(maxWaitTime, waitBetweenRetry);
			      pass = true;
		      }catch(AmbiguousRecognitionException e){
		    	  pass = false;
		    	  //LogTAFInfo("");
		      }catch(ObjectNotFoundException e){
		    	  pass = false;
		    	//LogTAFInfo("");
		      }catch(Exception e){
		    	  pass = false;
			    	//LogTAFInfo("");
			      }
		      return pass;
		  }
		  
		  public void onTestObjectMethodException(ITestObjectMethodState testObjectMethodState, TestObject testObject) {
			  
		  }
		  
		  public void onSubitemNotFound(ITestObjectMethodState testObjectMethodState, TestObject foundObject, java.lang.String subitemDescription) {
			  
			  
		  }		  
		  public boolean onUnhandledException(java.lang.Throwable e) {
			  errorHandledInLine = false;
			  
			  String err = e.toString();
			  String warnings = ".*InvalidWindowHandleException.*"+
			                    "|OtherException...";
			  
			  if(err.matches(warnings)){
				  logTAFWarning(err);
				  return true;
			  }
			  logTAFError ("UnhandledException occur: "+err);
			  if(e instanceof com.rational.test.ft.sys.ApplicationNotRespondingException){
				  sysExceptionCaught = true;
				  stopApp();
				  return true;
			  }		        
			  boolean dismissedAWindow = false,dismissedABrowser=false;
				if(!isWeb){
					dismissedAWindow = 					
						dismissWinDialog(FrameworkConf.winRuntimeErrTitle)||
						dismissPopup("Any",true,true)||
		                dismissAppDialog(FrameworkConf.axGatewayProErrTitle);
					
					//return; // Following methods are mainly for web applications, we'll add some for GWP later - steven.
				}else{
					htmlhandler = new htmlRFTHandler();
					dismissedABrowser = 
						             htmlhandler.dismissBrowser(FrameworkConf.docGWTitle)||
										htmlhandler.dismissBrowser(FrameworkConf.docEMTitle)||
											htmlhandler.dismissBrowser(FrameworkConf.docAXAdminTitle);
				}
				if (dismissedAWindow||dismissedABrowser)
				{
					//logTAFError("");
				}//else 
//				if(e instanceof com.rational.test.ft.sys.ApplicationNotRespondingException||
//					e instanceof	com.rational.test.ft.WindowActivateFailedException){
					  sysExceptionCaught = true;
//				  }
			  return true;
		  }
//*********************** End of Override RFT Methods ***********
		  
		public boolean dismissAppDialog(String title){
			GuiTestObject gto, button,msg;
			
			
			if(title.contains("AuditExchange")){
				//AX_GatewayPro.AppObject.TAFGetGoldbugObjects rts; 
				//rts = new AX_GatewayPro.AppObject.TAFGetGoldbugObjects();
				//gto =  rts.getErrorX();
				//button = rts.getErrorOk();
				//msg =  rts.getErrorMsg();
//				if(gto.exists()){
//					logTAFWarning("Dismissing pop window : '"+msg.getProperty("text"));
//					click(button);
//					sleep(2);
//					return true;				
//				}
			}else if(title.contains("Excel")){ // not done yet - Steven
//				ACL_Soundwave.AppObject.TAFGetExcelObjects rts; 
//				rts = new ACL_Soundwave.AppObject.TAFGetExcelObjects();
//				gto =  rts.getErrorX();
//				button = rts.getErrorOk();
//				msg =  rts.getErrorMsg();
//				if(gto.exists()){
//					logTAFWarning("Dismissing pop window : '"+msg.getProperty("text"));
//					click(button);
//					sleep(2);
//					return true;				
//				}
			}

			return false;
		}
//		
		public boolean dismissWinDialog(String title){
			return dismissWinDialog(title,"",true);
		}
		public boolean dismissWinDialog(String title,String className){
			return dismissWinDialog(title,className,true);
		}
		public boolean dismissWinDialog(String title,String className,boolean isWarning){
			boolean dismissAWindow = false;
			IWindow app = null;
			String info = "";
			int limits = 30,actualNum = 0;
			if(!title.equals("")||!className.equals("")){
				sleep(2);
				while((app=getDialog(title,className))!=null&&++actualNum<limits){
                    info = app.getText();
					if(isWarning){
					   logTAFWarning("Dismissing win dialog - '"+info+"'");
					}else{
						logTAFError(info);
//						if(!expectedErr.equals("")){
//						   if(expectedErr.contains(info)){
//							   logTAFInfo("Expected err - '"+info+"'");
//						   }else{
//						       logTAFWarning("Actual err - '"+info+"', Expected err - '"+expectedErr+"'");
//						   }
//							errorHandledInLine = true;
//						}else{
//						    logTAFError("UnExpected Dialog: '"+info+"'");
//						}
					}
					app.close();
					sleep(2);
					dismissAWindow = true;
				}
			}
			return dismissAWindow;
		}
//			
		public static void reverseTestResultToPass(boolean pass,String headline){
		  if(expectedErr.equals("")){
			if(testResult.equals("Pass")&&!pass){
				testResult = "Fail";
				message = "Expected error does not apear - "+expectedErr;
				failMessage = message;
			}
			else if(!testResult.equals("Pass")&&pass){
				testResult = "Pass";
				message = headline;
			}
			updateTestInfo(message, pass);
		  }else{
			 // logTAFDebug("Negative test, don't reverse the result");
		  }
		}
		
// ************    Table functions, in development....
		public String getHtmlTableCellText(StatelessGuiSubitemTestObject tableObj,
				int rowIndex, int colIndex,String htmlClass,int itemIndex){
		    String text="";
		    try{
		    	text = getHtmlTableCellObject(tableObj,
						rowIndex, colIndex,htmlClass,itemIndex).getProperty(".text").toString();
//		    	new AX_Gateway.AppObject._printObjects().printTestObjectTree(getHtmlTableCellObject(tableObj,
//						rowIndex, colIndex,htmlClass,itemIndex));		
		    }catch(Exception e){
	            text=null;
		    }
		    return text;
		}
		
		public TestObject getHtmlTableCellObject(StatelessGuiSubitemTestObject tableObj,
				int rowIndex, int colIndex,String htmlClass,int itemIndex){
			TestObject cellObj = tableObj.find(atList(
					atChild(".class" , "Html.TBODY"),
					atChild(".class" , "Html.TR",".rowIndex", rowIndex),
					atChild(".class" , "Html.TD",".cellIndex",colIndex)
			), false)[0];
//			TestObject []wrappedObj = cellObj.find(atChild(".class",htmlClass),false);
			TestObject []wrappedObj = cellObj.find(atDescendant(".class",htmlClass),false);

			if(wrappedObj.length < itemIndex + 1){
				return null;
			}
			else{	
				return wrappedObj[itemIndex];
			}
		}

		public TestObject[] getHtmlTableCellObjects(StatelessGuiSubitemTestObject tableObj,String htmlClass){
			return 	getHtmlTableCellObjects(tableObj,
					0,htmlClass,0);
		}
		
		public TestObject[] getHtmlTableCellObjects(StatelessGuiSubitemTestObject tableObj,int colIndex, String htmlClass){
			return 	getHtmlTableCellObjects(tableObj,
					colIndex,htmlClass,0);
		}
		
		public TestObject[] getHtmlTableCellObjects(StatelessGuiSubitemTestObject tableObj,
				int colIndex,String htmlClass,int classIndex){
			if(tableObj == null )
				return null;
			
			TestObject cellObj[] = tableObj.find(atList(
					atChild(".class" , "Html.TBODY"),
					atChild(".class" , "Html.TR"),
					atChild(".class" , "Html.TD",".cellIndex",colIndex)
			), false);
			
			for(int i=0;i<cellObj.length;i++){
				try{
					//logTAFDebug("cell "+i+": "+cellObj[i]);
			        cellObj[i] = cellObj[i].find(atChild(".class",htmlClass))[classIndex];
			        //logTAFDebug("Obj "+i+": "+cellObj[i]);
				}catch(Exception e){
					cellObj[i] = null;
				}
			}	
			
			return cellObj;
		}
		
		
		
		public static String 	getTestDataFromGrid(GuiSubitemTestObject gsto){
			String text = "";
			ITestDataTable idt;
			idt = (ITestDataTable)gsto.getTestData("viewcontents");
			//TBD


			return text;

		}
		
		public static String searchCellTextByCell(StatelessGuiSubitemTestObject table,String columnName,int row){
			int rowIndex = -1;
			int columnIndex =0;
			int numRows=0;
			String actualText="";
			ITestDataTable myTable;
			if(table.exists()){
				myTable = (ITestDataTable)table.getTestData("contents");
				numRows = myTable.getRowCount();
				columnIndex = myTable.getColumnIndex(new Column(columnName));	
				actualText = myTable.getCell(row, columnIndex).toString();
				}
			return actualText;
		}
		
		public static int searchTableRowByText(StatelessGuiSubitemTestObject table,String column,String textValue){
			int rowIndex = -1;
			int columnIndex =0;
			int numRows=0;
			String[] cols = column.split("\\|");
			String actualText;
			ITestDataTable myTable = null;
			if(table.exists()){
				//logTAFWarning(table.getTestDataTypes().toString());
				try{
				 myTable = (ITestDataTable)table.getTestData("contents");
				}catch(Exception e){
					logTAFException(e);
					return rowIndex;
					//myTable = (ITestDataTable)table.getTestData("list6");
				}
				numRows = myTable.getRowCount();
				for(int i=0;i<cols.length;i++){
				   columnIndex = myTable.getColumnIndex(new Column(cols[i]));
				   if(columnIndex>=0){
					   break;
				   }
				}
				if(columnIndex < 0){
					return rowIndex;
				}
				for(int i=0;i<numRows;i++){
					actualText = myTable.getCell(i, columnIndex).toString();
					//logTAFDebug("Row "+(i+1)+": '"+actualText+"'");
					if(actualText.trim().equalsIgnoreCase(textValue.trim())){
						return rowIndex = i;
					}else{
						logTAFDebug("Row "+(i+1)+": '"+actualText+"' != '"+textValue+"' ?");
					}
				}
			}
			table.unregister();
			return rowIndex;
		}
		
		public static int getTableRows(StatelessGuiSubitemTestObject table){
			int numRows=-1;
			if(table.exists()){
				ITestDataTable myTable;
				myTable = (ITestDataTable)table.getTestData("contents");
				numRows = myTable.getRowCount();
			}
			return numRows;
		}
		
		//*******Begin: get multiple data from a table, such as exception ids in a table
		
	    public String getColumnData(StatelessGuiSubitemTestObject itdo,int column, String property){
	    	int numrows = 0;
	    	String seperater = "|", id="", value="";
	    	numrows = getTableRows(itdo);
	    	for(int row=0;row<numrows;row++){
	    		if(row == 0){
	    			seperater = "";
	    		}else{
	    			seperater = "|";
	    		}
	    		id += seperater+((TestObject) itdo.getSubitem(atCell(atRow(row),atColumn(column)))).getProperty(property).toString();
	    	}
	    	return id;
	    }
	    
	    public String getRowData(ITestDataTable itdt){
	    	   String rowtext="";
 	       for(int row=0;row<itdt.getRowCount();row++){
 	    	 for(int col=0;col<itdt.getColumnCount();col++){ 	    	 
 		       rowtext += " "+itdt.getCell(row, col).toString();
 	    	 }
 	    	 rowtext += "\n";
 	        }
 	       return rowtext;
     }
	    
	    public String getColumnData(ITestDataTable itdo,int column){
	    	   String id="";
    	       for(int row=1;row<itdo.getRowCount();row++){
    	    	 if(row==0){
    	    		id =  itdo.getCell(0, column).toString();
    	    	 }else{
    		       id += "|"+itdo.getCell(row, column).toString();
    	    	 }
    	        }
    	       return id;
        }
	    // ********End:   return data combined with '|' ************
	    
	    
	    
	    
// **********    End of table processing
	    
	    public static int getNumberBetween(String input, char begin,char end){
	    	int num = 0;
	    	int beginIndex =0, endIndex = 0;
	    	
	    	beginIndex = input.indexOf(begin);
	    	endIndex = input.indexOf(end);
	    	//logTAFDebug(input+", numPost= input.substring(beginIndex+1,endIndex)");
	    	try{
	    		//logTAFDebug(input+",beginIndex = "+ (beginIndex+1)+",endIndex = "+(endIndex));
	    		//logTAFDebug(input+", numPost= "+ input.substring(beginIndex+1,endIndex));
	    		num = Integer.parseInt(input.substring(beginIndex+1,endIndex));
	    	}catch(Exception e){
	    		num = 0;
	    	}
	    	
	    	return num;
	    }

       public static void trimAllInArray(String[] inputArray){
    	   for (int i=0;i<inputArray.length;i++){
    		   inputArray[i]=inputArray[i].trim();
    		  //logTAFDebug("Input Array "+(i+1)+": '"+inputArray[i]+"'");
    	   }
       }
       
       
       public static String getAbsolutePath(String oriFile, String defaultPath){
    	   String absFile = oriFile;
    	   if(!oriFile.contains("/")&&(!oriFile.contains("\\"))){
    		   oriFile = defaultPath+oriFile;    		   
    	   }
    	   absFile = new File(oriFile).getAbsolutePath();
    	   return absFile;
       }
       
       public static String getFileName(String path){
    	   String fname;
    	   fname = new File(path).getName();
    	   return fname;
       }
       
       public static void verifyActiveWin(String appLabel){
    	   IWindow app=null;
    	   if(!appLabel.equals("")){
    		   for(int i=0;i<30&&app==null;i++){
    			   app = getITopWinObject(appLabel,null);
    			   sleep(2);
    		   }
    		   if(app.equals(null)){
    			   logTAFError("Window '"+appLabel+"' not found");
    		   }else{
    			   app.close();
    			   logTAFStep("Closing window "+appLabel);
    			   sleep(TimerConf.pageRefreshTime);
    		   }
    	   }else{
    		   logTAFWarning("Win title can't be empty for verification - "+appLabel);
    	   }
       }      
       
       public static String sanitizeText(String text) {
    	   boolean removeLineFeed = false;
    	   return sanitizeText(text,removeLineFeed);
       }
       public static String sanitizeText(String text,boolean removeLineFeed) {
    	   //Date: 10/04/11 10:48:41 (UTC-07:00) - MM/DD/YY HH:MM:SS (UTC-07:00)
    	   //Dir: [a-zA-Z]:\
    	   //User:   
    	   
    	   byte[] bytes;
    	   try {
			bytes = text.getBytes("ASCII");
		   } catch (UnsupportedEncodingException e) {
			bytes = null;
		   }
		   
		   if(bytes!=null){
			   text = new String(bytes).replaceAll("\\n", "[LineFeed]").replaceAll("\\?", "");
			   //text = text.replaceAll("\\p{Cc}|\\p{Cntrl}","");
		   }else{
			   //logTAFWarning("Unfortunlly, we failed to converty the text to ASCII");
			   return "Can't sanitize this text with ascii encoding!";
		   }
		   
		   String lineFeed = "\\n";
		   String controls = "[\\uEFEE-\\uFFFF]";
//		   if(removeLineFeed){
//			   lineFeed = "";			   
//		   }else{
			   controls +="|\\p{Cc}|\\p{Cntrl}";
//		   }
    	   String[] pattern = {
    			               controls,           //Null
    			               
    			               "Produced with ACL by:.*",
    			               ".*OUTPUTFOLDER.*",
    			               "[\\d]{1,2}[/-][0-9A-Za-z]{1,3}[/-][\\d]{2,4}", //Date
    			               "[\\d]{1,2}[-:][\\d]{1,2}[-:][\\d]{2}", //Time
    			               "\\([A-Z]{3}-[\\d]{2}[/:][\\d]{2}\\)",     //Zone
    			               ".*[a-zA-Z]:\\\\.*",                      //Dir Local
    			               ".*\\\\.*",                      //Net Dir or Link
    			               ".*[@]+.*",                           //Possible dSymbol
    			               //"\\.[0]+[\\D]",    // For different number format, remove .0's
    			               
    			               "[Uu]ser[: ].*",
    			               "[sS]erial.*",    	
    			               "[ \\t\\x0B\\f\\r"+whitespace_chars+"]*",   //Spaces for ease reading  
    			               //"[\\n][\\s]?[\\n]"
    			               "\\[LineFeed\\]"
    			               };
    	   String[] format = {"",
    			              
		                      "Produced with ACL by: DefName in acl.ini file",
		                      "",		                      
		                      "[MM/DD/YY]",
    			              "[HH:MM:SS]",
    			              "[(UTC-07:00)]",
    			              "[LocalPath]",
    			              "[NetLink]",
    			              "[DSymbol]",
    			              //"",
    			              
    			              "User: [Username]",       			              
    			              "Serial CAS-----------",
    			              "",
    			              lineFeed
    	                      };
    	   for(int i=0;i<pattern.length;i++){
    		   //System.out.println("Nomornizing '"+pattern[i]+"' - '"+format[i]+"'");
    		  // System.out.println("Text Before: '"+text+"'");
    		   text = text.replaceAll(pattern[i],format[i]);
    		  // System.out.println("Text After: '"+text+"'");
    	   }
    	   if(removeLineFeed){
    		   text = text.replaceAll(lineFeed,"").replaceAll("\\r", "");
    	   }
    	    return text;
       }
       
       public static String removeLineFeed(String input){
    	   return input.replaceAll("\\n","/").replaceAll("\\r", "/");
       }
       public static String getPrintableText(String text){  
    	   String[] pattern = {"[^\\p{Print}]+", //Not printable
    			               "[\\s]+|\\p{Cc}" //Cntrl    	"[\\x00-\\x1F\\x7F]"		 
    			               };
    	   String[] format = {"",//"[NotPrintable]"
    			              ""//"[Cntrl]"
    	                      };
    	   for(int i=0;i<pattern.length;i++){
    		   //System.out.println("Nomornizing '"+pattern[i]+"' - '"+format[i]+"'");
    		  // System.out.println("Text Before: '"+text+"'");
    		   try{
    		       text = text.replaceAll(pattern[i],format[i]);
    		   }catch(Exception e){
    			   text = "";
    		   }
    		  // System.out.println("Text After: '"+text+"'");
    	   }
    	    return text;
       }
       public  boolean compareTextFile(String fileMaster,String fileActual,
    		   String actualContents, boolean updateMasterFile,
    		   String fromKey, String endKey){
    	        return compareTextFile(fileMaster,fileActual,actualContents,updateMasterFile,"File",
    	        		   fromKey,endKey,-1,-1);
       }
       public  boolean compareTextFile(String fileMaster,String fileActual,
    		   boolean updateMasterFile,
    		   String fromKey, String endKey){
    	        return compareTextFile(fileMaster,fileActual,null,updateMasterFile,"File",
    	        		   fromKey,endKey,-1,-1);
       }
       public  boolean compareTextFile(String fileMaster,String fileActual,
    		   String actualContents,boolean updateMasterFile,
    		   int fromLine, int endLine){
    	        return compareTextFile(fileMaster,fileActual,actualContents,updateMasterFile,"File",
    	        		   null,null,fromLine,endLine);
       }
       public  boolean compareTextFile(String fileMaster,String fileActual,
    		   boolean updateMasterFile,
    		   int fromLine, int endLine){
    	        return compareTextFile(fileMaster,fileActual,null,updateMasterFile,"File",
    	        		   null,null,fromLine,endLine);
       }
       public  boolean compareTextFile(String fileMaster,String fileActual,
    		   boolean updateMasterFile,
    		   String fromKey, String endKey, int fromLine, int endLine){
    	        return compareTextFile(fileMaster,fileActual,null,updateMasterFile,"File",
    	        		   fromKey,endKey,fromLine,endLine);
       }
       
       public  boolean compareTextFile(String fileMaster,String fileActual,
    		   String actualContents,boolean updateMasterFile){
    	        return compareTextFile(fileMaster,fileActual,actualContents,updateMasterFile,"File",
    	        		   null,null,-1,-1);
       }

       public  boolean compareTextFile(String fileMaster,String fileActual,
    		   String actualContents,boolean updateMasterFile,String label){
    	        return compareTextFile(fileMaster,fileActual,actualContents,updateMasterFile,label,
    	        		   null,null,-1,-1);
       }
       public  boolean compareTextFile(String fileMaster,String fileActual,
    		   boolean updateMasterFile){
    	        return compareTextFile(fileMaster,fileActual,null,updateMasterFile,"File",
    	        		   null,null,-1,-1);
       }
       public  boolean compareTextFile(String fileMaster,String fileActual,
    		   boolean updateMasterFile, String label){
    	        return compareTextFile(fileMaster,fileActual,null,updateMasterFile,label,
 	        		   null,null,-1,-1);
       }
       public  boolean compareTextFile(String fileMaster,String fileActual,
    		   String actualContents,boolean updateMasterFile, String label,
    		   String fromKey, String endKey, int fromLine, int endLine){
    	   
    	   return compareTextFile(false,fileMaster,fileActual,
        		   actualContents,updateMasterFile, label,
        		   fromKey, endKey, fromLine, endLine);
       }
       public  boolean compareTextFile(boolean asciiOnly,String fileMaster,String fileActual,
    		   String actualContents,boolean updateMasterFile, String label,
    		   String fromKey, String endKey, int fromLine, int endLine){
    	       //String lineTerminator = "[\r\n\u0085\u2028\u2029]";
    	   boolean success = true;
    	   int numLineLimits = 20000;
    	   int sizeLimits = 10000;
    	   String[] textMaster, textActual;
    	   String fileDescription = "["+label+"]";
    	   
    	   if(!label.equalsIgnoreCase("File")){
    		  if(!fileMaster.endsWith(fileDescription))
    		     fileMaster += "["+label+"]";
    		  if(!fileActual.endsWith(fileDescription))
    		     fileActual += "["+label+"]";
    	   }
  	   if(actualContents!=null){//&&!actualContents.equals("")){
     	      FileUtil.writeFileContents(fileActual, actualContents);
     	   }
    	   File temp = new File(fileActual);
    	   if(!temp.exists()){
    		   logTAFWarning("File not found - '"+fileActual+"'");
    		   return true;
    	   }else if(!temp.isFile()){
    		   logTAFWarning("Not a file - '"+fileActual+"'");
    		   return true;
    	   }else if(temp.length()>sizeLimits*1000){
    		   logTAFWarning("File too big to be compared ("+temp.length()/(1000*1000)+"MBytes)- '"+fileActual+"'");
    		   return true;
    	   }
    	   
    	   
    	   
    	   if(updateMasterFile||!new File(fileMaster).exists()){
    		   logTAFInfo("Save/Update contents of master file '"+fileMaster+"'");
    			   FileUtil.copyFile(fileActual, fileMaster);
               return true;
    	   }
    	   
    	   
    	   if(fileMaster.toUpperCase().matches(".+\\.XLS[X]?[\\s]*$")&&
    			   fileActual.toUpperCase().matches(".+\\.XLS[X]?[\\s]*$")){
    		   
    		  if(isValidExcel(fileMaster)&&isValidExcel(fileActual)	){ 
    	         return compareDataInExcel(fileMaster,fileActual,"All") ;
    		  }else{
    			  logTAFWarning("The xls file corropted? or it's an excel 2.1 which can't be auto compared currently");
    			  return true;
    		  }
    	   }
    	   
    	   //logTAFStep("Comparing "+label+"(First "+numLineLimits+" lines): "+fileMaster +" and "+fileActual);
    	   
    	   logTAFStep("Comparing "+label+": "+fileMaster +" and "+fileActual);
    	   
    	   String tempMaster = FileUtil.readFile(fileMaster,numLineLimits,true);
    	   String tempActual = FileUtil.readFile(fileActual,numLineLimits,true);
    	   
    	   
    	   if(fromKey!=null){
    		   int fromMaster = tempMaster.lastIndexOf(fromKey);
    		   int fromActual = tempActual.lastIndexOf(fromKey);
    		   if(fromMaster>-1){
    		       tempMaster = "[StartComparison]"+tempMaster.substring(fromMaster+fromKey.length());    		       
    		   }    		   
    		   if(fromActual>-1){
    			   tempActual = "[StartComparison]"+tempActual.substring(fromActual+fromKey.length());    		       
    		   }   		   
    	   }
    	   if(endKey!=null){
    		   int endMaster = tempMaster.lastIndexOf(endKey);
    		   int endActual = tempActual.lastIndexOf(endKey);
    		   if(endMaster>-1){
    		       tempMaster = tempMaster.substring(0,endMaster)+"[EndComparison]";    		       
    		   }    		   
    		   if(endActual>-1){
    			   tempActual = tempActual.substring(0,endActual)+"[EndComparison]";    		       
    		   }   		   
    	   }
//    	   textMaster = sanitizeText(tempMaster).split("\n");
//    	   textActual = sanitizeText(tempActual).split("\n");
    	   
    	   
//    	   String lineDel = getPossibleLineDelimiter(tempMaster);
//    	   textMaster = tempMaster.split(lineDel);    	   
//    	   textActual = tempActual.split(lineDel);
    	   
    	   textMaster = tempMaster.split(getPossibleLineDelimiter(tempMaster));    	   
    	   textActual = tempActual.split(getPossibleLineDelimiter(tempActual));
    	   if(textActual.length>textMaster.length&&textMaster.length<3){
    		   //Master file corrupted possibly or source file changed?
    		   //updateMaster file;
    		   logTAFInfo("Save/Update contents of master file '"+fileMaster+"'");
			   FileUtil.copyFile(fileActual, fileMaster);
    		   return true;
    	   }
           boolean compareNumLines = isCompareable(fileMaster)&&isCompareable(fileActual);
    	   success = compareStringLines(compareNumLines,removeEmptyLines(textMaster),removeEmptyLines(textActual),fromLine,endLine,label);
    	   
    	   return success;
       }
       
       public boolean isCompareable(String fname){
    	   String exts = ".+[Ll][oO][gG]\\]?\\s$|.+VIEW\\]\\s$?|.+GRAPH\\]?\\s$|.+[Tt][Xx][Tt]|[Ff][iI][Ll]\\s$";
    	   if(fname.matches(exts))
    		   return true;
    	   return false;
       }
       public String[] removeEmptyLines(String[] text){
    	   String result="",del="autoDelimiter";
    	   for(int i=0;i<text.length;i++){
    		   String temp = sanitizeText(text[i],true);
    		   if(!temp.matches("[\\s"+whitespace_chars+"]*")){
    			   if(i==0)
    			      result = temp;
    			   else
    				  result += del+temp;
    		   }
        	
    	   }    	   
    	   return result.split(del);
       }
       public String getPossibleLineDelimiter(String text){
    	   String lineDelSfeed = "\\n",lineDelDfeed="\\n[\\s]*[\\r]?\\n";
    	   String[] lineUser,lineDfeed;
    	   
    	   if(!lineDelimiter.equals("")){
    	      lineUser = text.split(lineDelimiter);    	   
    	      if(lineUser.length>10){
    	    	  return lineDelimiter;
    	      }
    	   }else{    	   
    	     lineDfeed = text.split(lineDelDfeed);
   	         if(lineDfeed.length>20){
	    	      return lineDelDfeed;
	          }
    	   }
    	   
    	   return lineDelSfeed;
       }
       public static boolean compareStringLines(String[] tm, String[] ta, String label){
           return compareStringLines(false,tm,ta,-1,-1,label);
       }
       public static boolean compareStringLines(boolean compareNumLines,String[] tm, String[] ta,int fromLine, int endLine, String label){
    	   boolean success = true,isInfo = false,linediff=false;
    	   
    	   String sm,sa,msg;
    	   
    	   //logTAFStep("Comparing '"+label+"'");
    	   if(fromLine<0){
    		   fromLine = 1;
    	   }
    	   if(endLine<0){
    		   endLine = tm.length;
    	   }
    	   if(tm.length!= ta.length){
    		   int diffLines = Math.abs(tm.length-ta.length); 
    		   linediff = true;
    		   if(!compareNumLines){//diffLines<=3){
    			   logTAFWarning("Not exactly match, there might be some dynamic data in the file which made it incompatiable"+
    					   "\t\tExpected "+tm.length+" lines, but actual "+ta.length+" lines found");
    			   isInfo = true;
    		   }else if(diffLines<=3){
    			   logTAFWarning(autoIssue+"Expected "+tm.length+" lines, but actual "+ta.length+" lines found");
    			   isInfo = false;
    		   }else{
    			   logTAFWarning("Not exactly match, impacted by System locale? different encoding? or different OS platform?"+
    					   "\t\tExpected "+tm.length+" lines, but actual "+ta.length+" lines found");    			  
    		       isInfo = true;
    		   }
    	   }
    	   boolean done = false;
    	   int maxError =10,numError=0;
    	   
    	   for(int i=0;i<tm.length&&i<ta.length&&
    	               i>(fromLine-2)&&i<endLine
    	               &&!done&&numError<maxError;
    	           i++){    
//    		   tm[i] = sanitizeText(tm[i],true);
//        	   ta[i] = sanitizeText(ta[i],true);
        	   if(tm[i].trim().matches("[\\s]*")&&ta[i].trim().matches("[\\s]*")){
        		   continue;
        	   }
        	   //logTAFInfo("^^^^^^ MasterLine: '"+tm[i]+"' ActualLine: '"+ta[i]+"'");
    			if(!tm[i].trim().equalsIgnoreCase(ta[i].trim())){
    				   
    				   
    				   sm = getPrintableText(tm[i]);
    				   sa = getPrintableText(ta[i]);
    				   msg = "Not match - Line "+(i+1)+": ";
    				   
    				   //logTAFInfo("MasterLine: '"+tm[i]+"' ActualLine: '"+ta[i]+"'");
    				   if(sm.trim().equals(sa.trim())||ignoreable(sm+sa)
    						  ){ 
    					   isInfo = true;
    					   msg += "'"+sm+"[WithLinkOrPathOrDynamicSymbolOrOtherNonPrintableChars!]' - May need to open the file with proper software for details";    				   
    				   }else {
    					   msg += "'Expected, '"+sm+""+"', Actual, '"+sa+"'";
    					   if(linediff)
    						   msg = autoIssue+msg;
    				   }
    				   if(isInfo){
    					   //done = true;
    					   
    					   logTAFDebug(msg);
    				   }else{
    					   numError++;
    					   logTAFError(msg);
    					   success = false;
    				   }
    				   isInfo = linediff;
    			 }   		   
    	   }
    	   if(success){
    		   logTAFInfo("No difference found between both files - "+tm.length+" lines compared");
    	   }
    	   return success;
       }
       public static boolean ignoreable(String line){
    	   String[] ignoreLine = {
    			      "RootEntry",
    			      "[MM/DD/YY]",
		              "[HH:MM:SS]",
		              "[(UTC-07:00)]",
		              "[LocalPath]",
		              "[NetLink]",
		              "[DSymbol]",
		              "encounteredwas",  //For Age test
		              "CurrVer",
		              "ctimetaken",
		              
		              "vMain",          //This 4 are special for i18n batch
		              "scriptWasASuccess",
		              "LOCProf",
		              "random",
		             // "_PAY_CONST",
		              
		              "Totalstringspace",
		              "istarttime",
		              "User: [Username]",   
		              "Produced with ACL by: DefName in acl.ini file",
		              "Serial CAS-----------"};
    	   
    	   for(String key:ignoreLine){
    		   if(line.contains(key)){
    			   return true;
    		   }
    	   }
    	   
    	   return false;
       }
    //****     don't use  .disabled property, it does work on gateway ********
     public static boolean isEnabled(TestObject to) {
    	 return isEnabled(to,true);
     }
   	public static boolean isEnabled(TestObject to,boolean ignoreNotFound) {
   		boolean enabled = false;  
   		if(!ignoreNotFound&&(to==null||!to.exists())){
   			enabled = false;
   		}else if(	propertyMatch(to,".enabled","true",ignoreNotFound)||
   			propertyMatch(to,"disabled","false",ignoreNotFound)){
   			enabled = true;
   		   }
   		return enabled;
//		return (to.exists() 
//				&& to.getProperty("disabled").equals(true))? false : true;
	}  
  	public static boolean setState(ToggleGUITestObject tgto,boolean check){
   		//unchecked = "1", checked = "17" in GWP
  		tgto.setProperty(".checked", check?"true":"false");
  		sleep(2);
   		return isChecked(tgto);
   	}
   	public static boolean isChecked(ToggleGUITestObject tgto, boolean ignoreNotfound){
   		//unchecked = "0|1", checked = "16|17" in GWP
   		return propertyMatch(tgto,".checked","true",ignoreNotfound);
   	}
   	public static boolean isChecked(ToggleGUITestObject tgto){
   		//unchecked = "0|1", checked = "16|17" in GWP
   		return isChecked(tgto,true);
   		//return propertyMatch(tgto,".checked","true");
   	}
//   	public static boolean propertyMatch(TestObject to, String name,String value){
//   		boolean match = false;
//   		
//   		String actualValue ="";
//   		if(to.exists()){
//   			try{
//   				actualValue = to.getProperty(name).toString();
//   				match = actualValue.matches(value)||actualValue.equalsIgnoreCase(value);
//   				logTAFDebug("Actual value: '"+actualValue+"', Expected value: '"+ value+"'");
//   			}catch(Exception e){
//   				logTAFDebug("Exception when check property - "+name+" of "+ to+": "+e.toString());
//   			
//   				match = true;
//   				//_printObjects.printObjectTree(to);
//   			}
//   		}else{
//   			//logTAFWarning("Object doesn't exists - "+to);
//   		}
//   	     return match;
//   	}
     //****************
	
       // ***************  Native App *****************************
   	public static Point getItemCenter(IGraphical win,int item, int numItems){
   		return getItemCenter(win.getScreenRectangle(),item,numItems	);
   	}   	

	public static IWindow getITopWinObject(String objText, String className){
		return getITopWinObject(getScreen().getActiveWindow(),objText,className,false);		
	}  	   
	
	public static IWindow getITopWinObject(IWindow win, String objText, String className,boolean exactMatch){
			
			boolean textmatch = false;
			boolean classmatch = false;
			
			
			
			if(objText!=null&&objText!=""){
				if(!objText.startsWith("Localized:"))
				    objText = localizeProps(objText);
				else
					objText = objText.replaceFirst("Localized", "");
				String[] objs = objText.split("\\|");
				String text = win.getText();
				logTAFDebug("Win text '"+text+"'"	);
//				if(objText.contains("ACL Wizard Error")){
//					sleep(0);
//				}
				for(int i=0;i<objs.length;i++){		
					if(text.equalsIgnoreCase(objs[i])||text.matches(objs[i])){
						textmatch = true;
						break;
					}
					if(!exactMatch&&text.contains(objs[i])){
					   textmatch = true;	
					   break;
					}
				}
			}else{
				textmatch = true;
			}
			if(className!=null&&className!=""){
				String[] clas = className.split("\\|");
				String classN = win.getWindowClassName();
				logTAFDebug("Win class '"+classN+"'"	);
				for(int i=0;i<clas.length;i++){
					if(classN.contains(clas[i])||classN.matches(clas[i])){
						classmatch = true;
						break;
					}
				}
			}else{
				classmatch=true;
			}
			if(textmatch&&classmatch){
				return win;
			}else{
				
			}
			return null;
		}
	
   	public static Point getItemCenter(Rectangle rec,int item, int numItems){
   		double x, y;
   		double height;
		double width;
   		
   		height = rec.getHeight()/numItems;
   		width = rec.getWidth();
   		
   		x = width/2;
   		y = height*item - height/2;
   		
   		return new Point((int)x,(int)y);
   	}
   	public static Point getGuiRelativePoint(GuiTestObject gto, String p){
   	 return getGuiRelativePoint(gto, p, new Point(0,0));
   	}
   	public static Point getGuiRelativePoint(GuiTestObject gto, String p, Point adjust){
   		//p : center, top, right, bottom, left
   		Rectangle rct = gto.getScreenRectangle();
   		int x,y;
   		if(p.equalsIgnoreCase("top")){
   			x = rct.width/2;
   			y = 0;
   		}else if(p.equalsIgnoreCase("bottom")){
   			x = rct.width/2;
   			y = rct.height;
   		}else if(p.equalsIgnoreCase("left")){
   			x = 0;
   			y = rct.height/2;
   		}else if(p.equalsIgnoreCase("right")){
   			x = rct.width;
   			y = rct.height/2;
   		}else if(p.equalsIgnoreCase("topright")){
   			x = rct.width;
   			y = 0;
   		}else if(p.equalsIgnoreCase("bottomright")){
   			x = rct.width;
   			y = rct.height;
   		}else if(p.equalsIgnoreCase("bottomleft")){
   			x = 0;
   			y = rct.height;
   		}else{//(p.equalsIgnoreCase("Center")){
   			x = rct.width/2;
   			y = rct.height/2;
   		}
   		
   		return new Point(x+adjust.x,y+adjust.y);
   	}
   	public static Point getRelativeCenter(GuiTestObject gto){
   		Rectangle rct = gto.getScreenRectangle();
   		int x,y;
   		x = rct.width/2;
   		y = rct.height/2;
   		return new Point(x,y);
   	}
   	public static Point getRelativeCenter(Rectangle rootR, double userX,double userY){
   		Point centerP;
   		
   		int x,y;
   		x = rootR.x+(int)(rootR.width*userX);
   		y = rootR.y+(int)(rootR.height*userY);	
   		centerP = new Point(x,y);
   		
   		return centerP;
   	}
   	   
		 // ***************  Dialog *****************************
   	   public static String getActiveWinTitle(){
   		   String text = "";
           IWindow current = getScreen().getActiveWindow(); 
           if(current!=null){
        	   //current.restore();
        	   try{
        	       text = current.getText();
        	   }catch(Exception e){
        		   text = "";
        		   logTAFDebug("Exception caught when trying to getText from active window");
        	   }
           }
           return text;
   	   }
   	public static IWindow getDialog(String caption,String className) {
   		return getDialog(caption,className,false);
     }
       public static IWindow getDialog(String caption,String className,boolean  exactMatch ) 
       { 
           IWindow[] wins = RationalTestScript.getTopWindows(); 
           IWindow current = getScreen().getActiveWindow(); 
           for (int i = -1; i<wins.length; i++) 
           { 
        	   if(i>-1){
        		   //return null;
                  current = wins[i]; 
        	   }
               //check whether control is showing 
               if (!current.isShowing()) 
                       continue;                
               if ((current = getITopWinObject(current,caption,className,exactMatch))!=null) {     
            	    
            	    //logTAFInfo("Dialog '"+caption+"' found as win name '"+current.getText()+"' : class '"+current.getWindowClassName()+"'" );
            	     return current; 
               }
           } 

           return null; 
       }
       public static IWindow findControl(IWindow parentWindow, String caption) 
       { 
           
           if (parentWindow == null) 
               return null; 
           
           //else find control 
           IWindow children[] = parentWindow.getChildren();                
           // if there are no children for this control, then control can't be found 
           if (children.length == 0) 
               return null; 
           
           for (int i = 0; i < children.length; i++) { 
        	  // logTAFInfo("control "+i+": "+children[i].getText());
               if (children[i].getText().trim().equalsIgnoreCase(caption)) {
            	  // logTAFInfo("Dialog control '"+caption+"' found " );
                   return children[i]; 
               }
           } 
           // no match to caption was found 
           return null; 
       }
       public static void chooseFile(String appLabel, String file){
    	   IWindow fileSelector;
    	   IWindow openButton;
    	   IWindow filechooser;
    	   
    	   filechooser = getDialog(appLabel,null);
    	   try{
    	        
    	        fileSelector = findControl(filechooser,"File &name:");
    	        openButton = findControl(filechooser,"&Open");
    	       
    	        fileSelector.click();
    	        sleep(2);
    	        filechooser.inputChars(file);
    	        logTAFStep("Select file: '"+file+"'");
    	        sleep(2);
    	        
    	       openButton.click(atPoint(5,3));
    	        logTAFStep("Click Open");
    	        sleep(3);
    	   }catch(Exception e){
    		   //logTAFError("File Chooser not found");
    		   logTAFWarning(e.toString());
    	   }
    	   
       }
       public static boolean emptyTable(StatelessGuiSubitemTestObject table){
    	   boolean empty = false;
    	   
   		try{
			table.waitForExistence(TimerConf.maxWaitTime,TimerConf.waitBetweenRetry);
		}catch(Exception e){
			logTAFWarning("Table not found	");
			empty = true;
			return empty;
			}
		ITestDataTable mytable = (ITestDataTable) table.getTestData("contents");
		int numRows = mytable.getRowCount();
        if(numRows == 0){
        	logTAFWarning(table.getDescriptiveName()+" Table is empty	");
        	empty = true;
        }
        return empty;
       }
       
       public  boolean verifyButtonStatus(GuiTestObject gto, String name){
    	   return verifyButtonStatus(gto,name, true);
       }
        public  boolean verifyButtonStatus(GuiTestObject gto, String name, boolean active){
            //****     don't use  .disabled property, it does work on gateway ********
        	boolean pass = false;
        	sleep(1);
        	if(gto.isShowing()){
        		try{
        			if(isWeb)
            		    gto.click(atPoint(2,-2));
            	}catch(Exception e){
            		logTAFDebug("Click a safe point away from the botton (2,-2)	");
            	}
               	if(!isEnabled(gto)){
               		if(active){
               		       logTAFError("Gui Object '"+name+"' is not enabled");}
               		else
               			pass = true;
               	}else{
               		if(!active)
            		       logTAFWarning("Button '"+name+"' is enabled");
               		else
               			pass = true;
               	}
        	}else{
        		logTAFWarning("Button '"+name+"' is not showing");
        	}
        	return pass;
        }
        
        public static void click(GuiTestObject gto,boolean dclick){   
        	click(gto,null,null,null,dclick);
        }
        public static void click(GuiTestObject gto,String label,boolean dclick){
        	click(gto,null,null,label,dclick);
        }
        
        public static void click(GuiTestObject gto){   
        	click(gto,null,null,null,false);
        }
        public static void click(GuiTestObject gto,String label){
        	click(gto,null,null,label,false);
        }
        
        public static void click(GuiTestObject gto,MouseModifiers mm){   
        	click(gto,mm,null,null,false);
        }
        public static void click(GuiTestObject gto,Point pt){   
        	click(gto,null,pt,null,false);
        }
        public static void click(GuiTestObject gto,Point pt,String label){   
        	click(gto,null,pt,label,false);
        }
        public static void click(GuiTestObject gto,Point pt,String label,boolean dclick){   
        	click(gto,null,pt,label,dclick);
        }
        public static void click(GuiTestObject gto,MouseModifiers mm,Point pt,String label){
            click(gto,mm,pt,label,false);
        }
            
        public static void click(GuiTestObject gto,MouseModifiers mm,Point pt,String label,boolean dclick){
            if(gto==null){
            	logTAFWarning("You are clicking on a null pointer for '"+label+"'");
            	return;
            }
        	gto.waitForExistence();
        	Object oj = gto.getProperty(".name");
        	if(label!=null&&label!="")
        		logTAFInfo("Click on '"+label+"' on object - "+(oj==null?"label":oj));
        	if(pt!=null){
        		pt.x += 1;
        		pt.y +=1;
        		
        	}
        	try{
        		if(mm!=null){
        			if(pt!=null){
        			   //gto.click(mm,pt);
        				if(dclick)
        					getScreen().doubleClick(mm,gto.getScreenPoint(pt));
        				else
        				    getScreen().click(mm,gto.getScreenPoint(pt));
        			}
        			else{
        				//gto.click(mm);
        				if(dclick)
        					getScreen().doubleClick(mm,gto.getScreenPoint());
        				else
        				    getScreen().click(mm,gto.getScreenPoint());
        			} 
        		}
        		else{
        			if(pt!=null){
        				if(dclick)
        					getScreen().doubleClick(gto.getScreenPoint(pt));
        				else
        				    getScreen().click(gto.getScreenPoint(pt));
         			   //gto.click(pt);
        			}
         			else{
         				//gto.click();
         				if(dclick)
        					getScreen().doubleClick(gto.getScreenPoint());
        				else{
        					//gto.click();
         				    getScreen().click(gto.getScreenPoint());
        				}
         			}
        		}
        	}catch(Exception e){
        		logTAFWarning("Exception thrown when click on '"+gto+"' "+e.toString());
        	}
        }
        
        public  void clickOnObjectSafely(GuiTestObject gto, String name){
        	clickOnObjectSafely(gto, null, name);
        }
        
        public void clickOnObjectSafely(GuiTestObject gto, Point pt, String name){

        	
        	//gto.click();
        	//waitForLoading(gto);
           
        	if(gto.exists()){//&&gto.isEnabled()){
  
        		try{
        	       	if(pt==null)
                		pt = getRelativeCenter(gto);
        			//gto.hover(1);
        			//getScreen().getActiveWindow().inputKeys("{CLEAR}");
//        			if(pt==null){
//        				//gto.hover(1);
//        				//click(gto);
//        				gto.click();
//        			}
//        			else{
//        				//gto.hover(1,pt);
//        				//click(gto,pt);
//                        gto.click(pt);
//        			}
//        			logTAFStep("Click on '"+name+"'");
//        			
        			click(gto,pt,name);
        		}catch(Exception e){
        			logTAFError("Excepiton when click '"+name+"': "+ e.toString());
        		}
        	}else{
        		logTAFError("Gui Object '"+name+"' is not showing for clicking");
        	}
        	//sleep(1);
        	//waitForLoading(gto);
        }
        
        
        // Start of Set, verify, modify Gui values
        public boolean actionOnSelect(SelectGuiSubitemTestObject to, String label,String[] input, String action){
        	return actionOnSelect(true,to,label,input,action);
        }
        public boolean actionOnSelect(boolean localizable,SelectGuiSubitemTestObject to, String label,String[] input, String action){
    		// need to be modified --- Steven
        	boolean modified = false;
    		String keyDown = "{Ctrl KeyDn}";
    		String keyUp = "{Ctrl KeyUp}";
    		
    		if(to==null){
    			logTAFError("Combo box not found: "+label);
    			return false;
    		}
    		if(input==null||input.length<1){
    			return modified;
    		}

    		//input = "t1|t99".split("\\|");   		
    		for(int i=0;i<input.length&&!input[i].equals("");i++){    			
    			if(i==1){
    				getScreen().getActiveWindow().inputKeys(keyDown);
    			}
    			actionOnSelect(localizable,to, label,input[i],action);
                modified = true;
                if(i>0&&i==input.length-1){
                    getScreen().getActiveWindow().inputKeys(keyUp);
    			}
    		}
    		return modified;
    	} 
        public boolean actionOnSelect(SelectGuiSubitemTestObject to, String label,String input, String action){
        	return actionOnSelect(true,to,label,input,action);
        }
        public boolean actionOnSelect(boolean localizable, SelectGuiSubitemTestObject to, String label,String input, String action){
        	return actionOnSelect(localizable,to,label,input,action,false);
        }
        public boolean actionOnSelect(SelectGuiSubitemTestObject to, String label,String input, String action,boolean returnSuccess){
        	return actionOnSelect(true,to,label,input,action,returnSuccess);
        }
    	public boolean actionOnSelect(boolean localizable, SelectGuiSubitemTestObject to, String label,String input, String action,boolean returnSuccess){
    		boolean modified = false;
    		boolean selected = false;
    		String oriInput = input;
    		
    		if(to==null){
    			logTAFError("Combo box not found: "+label);
    			return false;
    		}
    		if(localizable)
    		   input = getLocValue(oriInput);
    		String tempT = "";
    	    if(!action.equalsIgnoreCase("New")){
    	    	//logTAFDebug("Saved: '"+to.getSelectedText()+"', Actual: "+input+"'");
    	    	tempT = to.getSelectedText();
    	    	if(tempT.equals(input)||tempT.equals(oriInput)){
    	    			logTAFInfo(label+" "+input+" loaded correctly");
    	    	}else if(action.equalsIgnoreCase("Modify")){
    	    		try{
      		    		 to.select(input) ;
   	    		    }catch(Exception e){
   	    			  to.select(oriInput);
   	    		    }
       		    		modified = true;
       		    		logTAFInfo("Modify: Select '"+input+"'");
    	        }else{
         	       logTAFError(label+" '"+to.getText()+"' is not expected as '"+input+"'");
    	        }
    	    }else{
    	    	try{
    		        to.select(input);
    		        logTAFInfo("Select "+label+" '"+input+"'");
    		        selected = true;
    	    	}catch(Exception e){
    	    		try{
     		    		 to.select(oriInput) ;
  	    		    }catch(Exception e1){
    	    		 logTAFError("Select "+label+" '"+input+"' failed");
    	    		 selected = false;
  	    		    }
    	    	}
    	    }		
    	    if(returnSuccess)
    	    	return selected;
    		return modified;
    	} 
    	public boolean actionOnSelect(TextSelectGuiSubitemTestObject to, String label,String input, String action){
        	return actionOnSelect(true,to,label,input,action);
    	}
       	public boolean actionOnSelect(boolean localizable,TextSelectGuiSubitemTestObject to, String label,String input, String action){
    		boolean modified = false;
    		String oriInput = input;
    		
    		if(to==null){
    			logTAFError("Combo box not found: "+label);
    			return false;
    		}
    		
    		if(localizable)
    		   input = getLocValue(oriInput);
    		String tempT = "";
    	    if(!action.equalsIgnoreCase("New")){
    	    	//logTAFDebug("Saved: '"+to.getSelectedText()+"', Actual: "+input+"'");
    	    	tempT = to.getSelectedText();
    	    	if(tempT.equals(input)||tempT.equals(oriInput)){
    	    			logTAFInfo(label+" "+input+" loaded correctly");
    	    	}else if(action.equalsIgnoreCase("Modify")){
    	    		  try{
       		    		 to.select(input) ;
    	    		  }catch(Exception e){
    	    			  to.select(oriInput);
    	    		  }
       		    		modified = true;
       		    		logTAFInfo("Modify: Select '"+input+"'");
    	        }else{
         	       logTAFError(label+" '"+to.getText()+"' is not expected as '"+input+"'");
    	        }
    	    }else{
    	    	try{
      		      to.select(input);
      		      logTAFInfo("Select "+label+" '"+input+"'");
      	    	}catch(Exception e){
      	    		try{
      		    		 to.select(oriInput) ;
   	    		    }catch(Exception e1){
   	    		    	logTAFError("Select "+label+" '"+input+"' failed");
   	    		    }
      	    		
      	    	}
    	    }			  
    		return modified;
    	}       
    	public boolean actionOnText(TextGuiTestObject to, String label,String input, String action){
    		boolean modified = false;
    		
    		if(input.equals(""))
    			return false;
    		if(to==null){
    			logTAFError("Text box not found: "+label);
    			return false;
    		}
    	    if(!action.equalsIgnoreCase("New")){
 //   	    	logTAFDebug("Saved: \n'"+to.getText()+"', Actual: \n'"+input+"'");
//    	    	if(equalString(to.getText(),input)){
    	    	if(to.getText().equals(input.replace(""+(char)13, ""))){
    	    			logTAFInfo(label+" "+input+" loaded correctly");
    	    	}else if(action.equalsIgnoreCase("Modify")){
       		    		setText(to,input) ;
       		    		modified = true;
       		    		logTAFInfo("Modify: Set text '"+input+"'");
    	        }else{
         	       logTAFError(label+" '"+to.getText()+"' is not expected as '"+input+"'");
    	        }
    	    }else{
    	    	logTAFInfo("Set "+label+" '"+input+"'");
    		   setText(to,input);
    		   
    	    }			  
    		return modified;
    	}    
 

       	public boolean actionOnCheckbox(ToggleGUITestObject to, String label,boolean input, String action){
    		boolean modified = false;

    		if(to==null){
    			logTAFError("Check box not found: "+label);
    			return false;
    		}
    		if(to.getState().isSelected()== input){
    			if(!action.equalsIgnoreCase("New")){
    			   logTAFInfo(label+" check box '"+(input?"Selected":"NotSecleted")+"' loaded correctly");
    			}else{
    				logTAFInfo(label+" check box '"+(input?"Selected":"NotSecleted")+"'");
    			}
    	    }else{
    	    	
    	    	try{

    	    		if(action.equalsIgnoreCase("Modify")||action.equalsIgnoreCase("New")){

    	    			if(input){
    	    				//to.setState(State.selected()) ;
    	    				to.clickToState(State.selected());
    	    			}else{
    	    				//to.setState(State.notSelected()) ;
    	    				to.clickToState(State.notSelected());
    	    			}
    	    			if(to.getState().isSelected()!= input){
    	    				sleep(5);
    	    			}
    	    			if(to.getState().isSelected()== input)
    	    			   modified = true;
    	    			else
    	    				modified = false;
    	    		}
    	    	}catch(Exception e){
    	    		modified = false;
    	    	}

    	    	if(action.equalsIgnoreCase("Modify")&&modified)
    	    		logTAFInfo("Modify: "+label+" check box '"+(input?"Selected":"NotSecleted")+"'");
    	    	else if(action.equalsIgnoreCase("New")&&modified)
    	    		logTAFInfo(label+" check box '"+(input?"Selected":"NotSecleted")+"'");
    	    	else
    	    		logTAFError(autoIssue+label+" check box '"+to.getState().isSelected()+"' is not expected as '"+input+"'");

    	    }
//    	    if(!action.equalsIgnoreCase("New")){
//    	    	if(to.getState().isSelected()== input){
//    	    			logTAFInfo(label+" check box '"+input+"' loaded correctly");
//    	    	}else if(action.equalsIgnoreCase("Modify")){
//    	    		modified = true;
//    	    		if(input)
//    	    	       to.setState(State.selected()) ;
//    	    		else
//    	    			 to.setState(State.notSelected()) ;
//       		    	logTAFStep("Modify: Click check box to '"+input+"'");
//    	        }else{
//         	       logTAFError(label+" check box '"+to.getState().isSelected()+"' is not expected as '"+input+"'");
//    	        }
//    	    }else{
//    	    	if(to.getState().isSelected()!= input){
//    	    		modified = true;
//    	    		if(input)
//    	    	       to.setState(State.selected()) ;
//    	    		else
//    	    			 to.setState(State.notSelected()) ;
//    		        logTAFStep("Click check box to '"+input+"'");
//    	    	}
//    	    }			  
    		return modified;
    	}   
       	
        public Point getPointOfSelectItem(TextSelectGuiSubitemTestObject comboBox, int index){
        	
        	int boxWidth = Integer.parseInt(comboBox.getProperty("Width").toString());
        	int boxHeight = Integer.parseInt(comboBox.getProperty("Height").toString());
        	int itemHeight = Integer.parseInt(comboBox.getProperty("ItemHeight").toString());
        	Point itemPoint = atPoint(boxWidth/2,boxHeight+itemHeight/2+index*itemHeight);
        	return itemPoint;
        }
    	public boolean verifyPasswordEncryption(TextGuiTestObject gto, String inputPass){
    		boolean encrypted = false;
    		String encryptedPass="";
    		sleep(2);
    		try{
    			encryptedPass = gto.getText();
    			logTAFDebug("Password '"+inputPass+"' captured from the text box as: '"+encryptedPass+"'");
    			if(inputPass.equals("")){
    				if(encryptedPass.equals(inputPass)){
    					logTAFInfo("Password box is empty");
    					encrypted = true;
    				}else{
    					logTAFError("Password box is not empty, it showed as: '"+encryptedPass);
    					encrypted = false;
    				}
    				
    				return encrypted;
    			}
    			
    			// Check password encryption in the password GUI box
    			
    			if(encryptedPass.equals(inputPass)){
    				 //AXCore will never send plain text to Gateway
    			     logTAFError("Password displayed in plain text:"+encryptedPass);
//    			}else if(encryptedPass.length()!=6){ 
//    			     logTAFWarning("Encrypted password is "+encryptedPass.length()+" chars long");
//    			}else if(!encryptedPass.matches("\\**")){ 
//    			     logTAFError("Encrypted password should be *s, not as'"+encryptedPass+"'");
//    			}else if(encryptedPass.length()!=inputPass.length()){ 
//    			     logTAFError("Encrypted password should be "+inputPass.length()+ 
//    			    		     " chars long, not "+encryptedPass.length()+" chars long");
    			}else{
    				logTAFInfo("Password - '"+inputPass+"' was encrypted in the text box as:"+encryptedPass);
    				encrypted = true;
    			}
    		}catch(Exception e){
    			logTAFWarning(e.toString());
    		}
    				
    		return encrypted;		
    	}
    	
    	public void checkDBCipherText(String plainText, String cipherText){
    		// Check password encryption in database
    		
    		if(cipherText.equals("NotFound")&&!plainText.equals("")){
    		     logTAFError("Password '"+cipherText+" - "+plainText+"' not found in database");
    		}else if(cipherText.equals("NotFound")&&plainText.equals("")){
    		     logTAFInfo("Password has not been set yet.");
    		}else if(cipherText.equals(plainText)){
    			 //AXCore will never send plain text to Gateway
    		     logTAFError("Password '"+plainText+"' was not encrypted in  database, it apeared as:"+cipherText);
    		}else{
    			logTAFInfo("Password '"+plainText+"' was encrypted in database as:\n\t\t"+cipherText);
    		}
    		
    	}
    	
    	public boolean equalString(String s1, String s2){
    		boolean equals = true;
    		String LF = ""+(char)10;
    		String CR = ""+(char)13;
    		
    		//s2 = s2.replaceAll(CR,"");
    		int length1= s1.length();
    		int length2 = s2.length();   		
    		int c1, c2;
    		logTAFDebug("Comparing String s1: \n'"+s1+"'\n with String s2: \n'"+s2);
    		
    		for(int i=0;i<length1&i<length2;i++){
    			c1 = s1.charAt(i);
    			c2 = s2.charAt(i);
    			if(c1!=c2){
    				equals = false;
    				logTAFDebug("Char["+i+"] of s1 - "+(char)c1+"("+c1+") != Char["+i+"] of s2 - "+(char)c2+"("+c2+")");
    			}    				
    		}
    		
    		if(length1<length2){
    			for(int i=length1;i<length2;i++){
    				c2=s2.charAt(i);
    				logTAFDebug("Char["+i+"] of s2 - "+(char)c2+"("+c2+")");
    			}
    		}else if(length1>length2){
    			for(int i=length2;i<length1;i++){
    				c1=s1.charAt(i);
    				logTAFDebug("Char["+i+"] of s1 - "+(char)c1+"("+c1+")");
    			}
    		}
    		return equals;
    	}
    	
       public void activate(TestObject to){
    	   try{
    		   ((ITopWindow) to).activate();
    	   }catch(Exception e){
    		   logTAFWarning("Exception thrown: '"+e.toString()+"'");
    	   }
       }
        // End of Set, verify, modify Gui values
       
   	protected String getContentsBySelection(GuiTestObject gto,Point pt){
		String text = "";
		
		ITopWindow tw = (ITopWindow) gto.getTopParent();
		getSystemClipboard().setText("");
		if(gto.exists()){
			click(gto,pt);
			
			tw.inputKeys("^a");
			sleep(1);
			tw.inputKeys("^c");
			sleep(1);
			text = getSystemClipboard().getText().trim();
		}
		//text = getSystemClipboard().getText().trim();
		
	    return  text;	
	}    
//   	public static void compareDataInExcel(String masterFile,String actualFile,String types) {
//   		int maxRows = 65536;
//   		int maxColumns = 256;
//   		
//		  logTAFInfo("Master data from file: "+masterFile);
//		  logTAFInfo("Actual data form file: "+actualFile);
//		try
//	    {
//	      File master = new File(masterFile);
//	      File actual = new File(actualFile);
//
//		  InputStream isMaster = (InputStream)new FileInputStream(master);	
//		  InputStream isActual = (InputStream)new FileInputStream(actual);
//		  
//	      try{
//				HSSFWorkbook hwbMaster = new HSSFWorkbook(isMaster,true);
//				int numSheetsMaster = hwbMaster.getNumberOfSheets();
//				
//				HSSFWorkbook hwbActual = new HSSFWorkbook(isActual,true);
//				int numSheetsActual = hwbActual.getNumberOfSheets();
//				
//				if(numSheetsMaster==numSheetsActual){
//				   logTAFInfo(numSheetsMaster+" sheet(s) found in Both master and actual files");
//				}else{
//				   logTAFError(numSheetsActual+" sheet(s) in actual file, "+
//						       numSheetsMaster+" sheet(s) in master file");
//				}
//				
//                int numComparableSheets = numSheetsMaster<=numSheetsActual?numSheetsMaster:numSheetsActual;
//    
//                logTAFInfo("Comparing "+numComparableSheets+" sheet(s) from both files");
//                
//			      for (int sheet = 0; sheet < numComparableSheets; sheet++)
//			      {
//			    	int numMismatchRow = 0;
//			        HSSFSheet sMaster = hwbMaster.getSheetAt(sheet);
//			        int numRowsMaster = sMaster.getPhysicalNumberOfRows();
//			        HSSFSheet sActual = hwbActual.getSheetAt(sheet);
//			        int numRowsActual = sActual.getPhysicalNumberOfRows();
//			        logTAFInfo("Sheet: "+(sheet+1));
//			        if(numRowsMaster==numRowsActual){
//						   logTAFInfo(numRowsMaster+" Row(s) found in Both master and actual sheet "+(sheet+1));
//						}else{
//						   logTAFError(numRowsActual+" Rows(s) in actual file, "+
//								       numRowsMaster+" Rows(s) in master file ");
//						}
//			        	
//		            int numComparableRows = numRowsMaster<=numRowsActual?numRowsMaster:numRowsActual;
//		            numComparableRows = numComparableRows<=maxRows?numComparableRows:maxRows;
//		            logTAFInfo("Comparing "+numComparableRows+" Row(s) from both sheets one by one");
//		            	        	        
//			        for (int i = 0 ; i < numComparableRows; i++)
//			        {
//			          HSSFRow rowMaster = sMaster.getRow(i);
//			          int numCellsMaster = rowMaster.getLastCellNum();
//			          HSSFRow rowActual = sActual.getRow(i);
//			          int numCellsActual = rowActual.getLastCellNum();
//			          
//			          boolean mismatchRow = true;
//			         // logTAFInfo("Row: "+(i+1));
//				        if(numCellsMaster==numCellsActual){
//				        	   if(i==0)
//							      logTAFInfo(numCellsMaster+" Cell(s) found in Both master and actual - Row "+(i+1));
//							}else{
//							   if(i==0) // Only check the first for num cells - Steven
//								   logTAFError(numCellsActual+" Cell(s) in actual file - Row "+(i+1)+", "+
//									       numCellsMaster+" Cell(s) in master file - Row "+(i+1));
//							}
//				        	
//			            int numComparableCells = numCellsMaster<=numCellsActual?numCellsMaster:numCellsActual;
//			            numComparableCells = numComparableCells<=maxColumns?numComparableCells:maxColumns;
//                      
//			          if (numComparableCells > 0)
//			          {
//			        	if(i==0)
//				            logTAFInfo("Comparing "+numComparableCells+" Cell(s) from both rows one by one");
//			        	String cellValueMaster, cellValueActual;
//			        	HSSFCell cell = null;
//			        	
//			            for (int j = 0; j < numComparableCells; j++)
//			            {
//			              cell = rowMaster.getCell(j);
//			              cellValueMaster = UnicodeUtil.getStringCellValueWithType(cell,types);
//			              cell = rowActual.getCell(j);
//			              cellValueActual = UnicodeUtil.getStringCellValueWithType(cell,types);
//			              
//			              if(!cellValueMaster.equals(cellValueActual) ){
//			            	  if(i==0){
//			            		  if(cellValueMaster.toUpperCase().replaceAll("__", "_").contains
//			            			  (cellValueActual.toUpperCase().replaceAll(".| ", "_").replaceAll("__|___", "_"))){
//			            			  logTAFDebug("Warning - ACL DesktopHeader format issue, Sheet "+(sheet+1)+" Row "+(i+1)+" Cell "+(j+1) +
//					            			   " mismatch - expected value = '"+cellValueMaster+"' actual value = '"+cellValueActual +"'");
//			            		       continue;
//			                	  }else{
//			                		  logTAFWarning("ACL DesktopHeader format issue? , Sheet "+(sheet+1)+" Row "+(i+1)+" Cell "+(j+1) +
//					            			   " mismatch - expected value = '"+cellValueMaster+"' actual value = '"+cellValueActual +"'");
//			                		  continue;
//			                	  }
//			            	  }
//			            	  // Desktop has issue to handle headers
//			            	  //    -- sometimes adding '_' instead of ' ', adding a digit as suffix,and to UPPER CASE also, don't know why -- Steven
//			        
//			            	  if(mismatchRow)
//			            		 numMismatchRow++;
//			            	  if(numMismatchRow<25){
//			            		 if(cellValueMaster.startsWith("[Date]")){ 
//			            			 //Currently, there is an issue on office 2003 with format'English(Canada) or (United Kingdom) or (South Africa)' 
//			            			 logTAFError("Sheet "+(sheet+1)+" Row "+(i+1)+" Cell "+(j+1) +
//					            			 " mismatch - expected value = '"+cellValueMaster+"' actual value = '"+cellValueActual+"'"); 
//			            		 }else{
//			            		   logTAFError("Sheet "+(sheet+1)+" Row "+(i+1)+" Cell "+(j+1) +
//			            			 " mismatch - expected value = '"+cellValueMaster+"' actual value = '"+cellValueActual+"'"); 
//			            		 }
//			            	 }else if(numMismatchRow==25){
//			            		 if(cellValueMaster.startsWith("[Date]")){
//			            			 logTAFWarning("More mismatch record found - [Date].... ");
//			            		 }else{
//			            		  logTAFError("More mismatch record found .... ");
//			            		 }
//			            	 }
//			            	 mismatchRow = false;
//			              }
//			            }
//			          }			                    
//			        }
//			        logTAFInfo("Total "+numMismatchRow+" mismached records found in sheet "+(sheet+1)	);	
//			      }			      	
//			    }
//			    catch (UnsupportedEncodingException e)
//			    {
//			      logTAFWarning(e.toString());
//			    }
//			    catch (IOException e)
//			    {
//			      logTAFWarning(e.toString());
//			    }
//			    catch (Exception e)
//			    {
//			      logTAFWarning(e.toString());
//			    }
//	    }catch(Exception e){
//	    	logTAFWarning(e.toString());
//	    }
//        
//	  }
	
   	
   	public static boolean isValidExcel(String file){
   		
   		InputStream fls=null;  
   		boolean isValidExcel = true;
	      File fl = new File(file);
	      try{
		    fls = (InputStream)new FileInputStream(fl);
		    WorkbookFactory.create(fls);	
		    fls.close();
	      }catch(Exception e){
	    	  isValidExcel = false;
	      }  
	      return isValidExcel;
   	}
   	
   	public static boolean compareDataInExcel(String masterFile,String actualFile,String types) {
   		int maxRows = 65536;
   		int maxColumns = 256;
   		boolean success = true;
		  logTAFInfo("Master data from file: "+masterFile);
		  logTAFInfo("Actual data form file: "+actualFile);
		try
	    {
	      File master = new File(masterFile);
	      File actual = new File(actualFile);

		  InputStream isMaster = (InputStream)new FileInputStream(master);	
		  InputStream isActual = (InputStream)new FileInputStream(actual);
		  
	      try{//WorkbookFactory.create(in)
				Workbook hwbMaster = WorkbookFactory.create(isMaster);
				
				int numSheetsMaster = hwbMaster.getNumberOfSheets();
				
				Workbook hwbActual = WorkbookFactory.create(isActual);
				int numSheetsActual = hwbActual.getNumberOfSheets();
				
				if(numSheetsMaster==numSheetsActual){
				   logTAFInfo(numSheetsMaster+" sheet(s) found in Both master and actual files");
				}else{
				   logTAFError(numSheetsActual+" sheet(s) in actual file, "+
						       numSheetsMaster+" sheet(s) in master file");
				   success = false;
				}
				
                int numComparableSheets = numSheetsMaster<=numSheetsActual?numSheetsMaster:numSheetsActual;
    
                logTAFInfo("Comparing "+numComparableSheets+" sheet(s) from both files");
                
			      for (int sheet = 0; sheet < numComparableSheets; sheet++)
			      {
			    	int numMismatchRow = 0,numWarning=0;
			        Sheet sMaster = hwbMaster.getSheetAt(sheet);
			        int numRowsMaster = sMaster.getPhysicalNumberOfRows();
			        Sheet sActual = hwbActual.getSheetAt(sheet);
			        int numRowsActual = sActual.getPhysicalNumberOfRows();
			        logTAFInfo("Sheet: "+(sheet+1));
			        if(numRowsMaster==numRowsActual){
						   logTAFInfo(numRowsMaster+" Row(s) found in Both master and actual sheet "+(sheet+1));
						}else{
						   logTAFError(numRowsActual+" Rows(s) in actual file, "+
								       numRowsMaster+" Rows(s) in master file ");
						   success = false;
						}
			        	
		            int numComparableRows = numRowsMaster<=numRowsActual?numRowsMaster:numRowsActual;
		            numComparableRows = numComparableRows<=maxRows?numComparableRows:maxRows;
		            logTAFInfo("Comparing "+numComparableRows+" Row(s) from both sheets one by one");
		            
		            int[] actualIndex = null ;	
		            String[] header = null;
			        for (int i = 0 ; i < numComparableRows; i++)
			        {
			          Row rowMaster = sMaster.getRow(i);
			          int numCellsMaster = rowMaster.getLastCellNum();
			          org.apache.poi.ss.usermodel.Row rowActual = sActual.getRow(i);
			          int numCellsActual = rowActual.getLastCellNum();
			          
			          boolean mismatchRow = true;
			         // logTAFInfo("Row: "+(i+1));
				        if(numCellsMaster==numCellsActual){
				        	   if(i==0)
							      logTAFInfo(numCellsMaster+" Cell(s) found in Both master and actual - Row "+(i+1));
							}else{
							   if(i==0) // Only check the first for num cells - Steven
								   logTAFError(numCellsActual+" Cell(s) in actual file - Row "+(i+1)+", "+
									       numCellsMaster+" Cell(s) in master file - Row "+(i+1));
							   success = false;
							}
				        	
			            int numComparableCells = numCellsMaster<=numCellsActual?numCellsMaster:numCellsActual;
			            numComparableCells = numComparableCells<=maxColumns?numComparableCells:maxColumns;
                      
			          
			          if (numComparableCells > 0)
			          {
				        	String cellValueMaster=null, cellValueActual=null;
				        	Cell cell = null;
				        	String ignoreColumns = ".*EBCDIC.*";
			          
			        	if(i==0){
			        		actualIndex = new int[numCellsMaster];
			        		header = new String[numCellsMaster];
			        		
			        		boolean diffOrder = false;
			        	
			        		logTAFInfo("Comparing "+numComparableCells+" Cell(s) from both rows one by one");
			        		for (int aIndex = 0; aIndex < numCellsMaster; aIndex++)
			        		{
			        			boolean found = false, foundSimilar = false;
			        			int itemIndex = -1;
			        			cell = rowMaster.getCell(aIndex);
			        			cellValueMaster = UnicodeUtil.getStringCellValueWithType(cell,types);
                                header[aIndex] = cellValueMaster;
			        			for(int bIndex=0;bIndex < numCellsActual;bIndex++ ){
			        				cell = rowActual.getCell(bIndex);
			        				cellValueActual = UnicodeUtil.getStringCellValueWithType(cell,types);

			        				if(!cellValueMaster.equals(cellValueActual) ){
			        						if(cellValueMaster.toUpperCase().replaceAll("__", "_").contains
			        								(cellValueActual.toUpperCase().replaceAll(".| ", "_").replaceAll("__|___", "_"))){
			        							foundSimilar = true;
			        							itemIndex = bIndex;
			        						}		        					
			        				}else{
			        					found = true;
			        					itemIndex = bIndex;
			        					break;
			        				}
			        			}	
			        			
			        			actualIndex[aIndex]= itemIndex;
			        			
			        			if(itemIndex!=aIndex&&itemIndex!=-1){
			        				diffOrder = true;
			        				
			        			}
			        			if(!found){
			        				if(foundSimilar){
			        					logTAFWarning("Warning - ACL DesktopHeader format issue?, Sheet "+(sheet+1)+" Row "+(i+1)+" Cell "+(aIndex+1) +
						            			   " mismatch - expected value = '"+cellValueMaster+"' actual value = '"+cellValueActual +"'");
			        				}else{
			        					logTAFError("Column not found in actural file,  '"+cellValueMaster);
			        				}
			        			}else{
			        				logTAFDebug("Column '"+cellValueMaster+"' found in Actual file with index '"+itemIndex+"'");
			        			}
			        			
			        		} 
			        		if(diffOrder){
			        		logTAFWarning("Excel columns in different order !!!");
			        		}
			        		
			        		continue;
			        	} // End of header 

			            for (int j = 0; j < numComparableCells
			                                 &&actualIndex[j]!=-1; 
			                                 j++)
			            {
			              cell = rowMaster.getCell(j);
			              cellValueMaster = UnicodeUtil.getStringCellValueWithType(cell,types);
			              cell = rowActual.getCell(actualIndex[j]);
			              cellValueActual = UnicodeUtil.getStringCellValueWithType(cell,types);
			              
			              if(!cellValueMaster.equals(cellValueActual) ){

			            	  if(mismatchRow)
			            		  numMismatchRow++;
			            	  if(numMismatchRow<25){
			            		  if(cellValueMaster.startsWith("[Date]")||
			            				  header[j].matches(ignoreColumns)){ 
			            			  if(mismatchRow)
			            			      numMismatchRow--;
			            			  
			            			  //Currently, there is an issue on office 2003 with format'English(Canada) or (United Kingdom) or (South Africa)' 
			            			 if(numWarning++<25)
			            			  logTAFWarning("Sheet "+(sheet+1)+" Row "+(i+1)+" Cell "+(j+1) +
			            					  " mismatch - expected value = '"+cellValueMaster+"' actual value = '"+cellValueActual+"'"); 
			            		  }else{
			            			  mismatchRow = false;
			            			  logTAFError("Sheet "+(sheet+1)+" Row "+(i+1)+" Cell "+(j+1) +
			            					  " mismatch - expected value = '"+cellValueMaster+"' actual value = '"+cellValueActual+"'"); 
			            		  }
			            		  success = false;
			            	  }else if(numMismatchRow==25){
			            		  if(cellValueMaster.startsWith("[Date]")||
			            				  header[j].matches(ignoreColumns)){ 
			            			  if(mismatchRow)
			            			      numMismatchRow--;
			            			  if(numWarning++<25)
			            			  logTAFWarning("More mismatch record found - [Date] or ["+ignoreColumns+"].... ");
			            		  }else{
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
			        logTAFInfo("Total "+numMismatchRow+" mismached records found in sheet "+(sheet+1)	);	
			      }

			    }
			    catch (UnsupportedEncodingException e)
			    {
			      logTAFWarning(e.toString());
			    }
			    catch (IOException e)
			    {
			      logTAFWarning(e.toString());
			    }
			    catch (Exception e)
			    {
			      logTAFWarning(e.toString());
			    }
			    
			    isMaster.close();
			    isActual.close();
			   
	    }catch(Exception e){
	    	logTAFWarning(e.toString());
	    }
        return success;
	  }
    //************************************************************
    //********** Top Level Window - Message Box ******************
    //************************************************************
    
	// Windows 


	public boolean dismissWindow(String winTitle){
		  return dismissWindow(winTitle,false);
		}
	public boolean dismissWindow(String winTitle,boolean isInfo){
	    return dismissWindow(winTitle,"",isInfo,false);
	}
	public boolean dismissWindow(String winTitle,boolean isInfo, boolean loop){
	    return dismissWindow("",winTitle,isInfo,loop);
	}
	public boolean dismissWindow(String winClass,String winTitle,boolean isInfo, boolean loop){
	    return dismissWindow(winClass,winTitle,"X",isInfo,loop);
	}
	public boolean dismissWindow(String winClass,String winTitle,String userAction,boolean isInfo, boolean loop){
	    return dismissPopup(winClass,winTitle,userAction,isInfo,loop);
	}
	
	//************************************************************************
	//***************** New Error/Popup general handler **********************
    //************************************************************************
	public boolean endWithAction(TestObject to, String action){
		return endWithAction(to,action,false);
	}
	public boolean endWithAction(TestObject to, String action,boolean isInfo){
		boolean filecreated = false;
		GuiTestObject okButton,cancelButton;
		okButton = findPushbutton(to,"OK");
		cancelButton = findPushbutton(to,"Cancel");
		
		if(action.matches("OK|Finish")){
			click(okButton,"OK");		
			if(!dismissPopup(isInfo)){
				   filecreated = true;
			}
		}else if(action.equals("Cancel")){
			click(cancelButton,"Cancel");
			filecreated = false;
		}
		
		sleep(2);
		dismissPopup(true);

		if(to.exists()
		     &&(cancelButton = findPushbutton(to,"Cancel"))!=null){
	         click(cancelButton,"Cancel",true);
	         filecreated = false;
		}
		return filecreated;
	}
	//************************************************************************
	//***************** New Error/Popup general handler **********************
    //************************************************************************
	
	// begin of action oriented
	public boolean dismissPopup(){
		  String action = "";
		  boolean isInfo = false;
		  boolean loop = true;
		  return dismissPopup("",isInfo,loop);
		}
	public boolean dismissPopup(int maxCheck){
		return dismissPopup(maxCheck,"");
		
	}
	public boolean dismissPopup(int maxCheck,String expInfo){
		  String userAction = "";
		  boolean isInfo = false;
		  boolean loop = true;
		  String winClass = "";
		  String winTitle = "";
		  return dismissPopup(winClass,winTitle,userAction,isInfo,loop,maxCheck,expInfo);
		}
	public boolean dismissPopup(String action){
		  boolean isInfo = false;
		  return dismissPopup(action,isInfo);
		}
	public boolean dismissPopup(boolean isInfo){
	  String action = "";
	  return dismissPopup(action,isInfo);
	}
	public boolean dismissPopup(String userAction, boolean isInfo){
          boolean loop = false;
		  return dismissPopup(userAction,isInfo,loop);
		}
	
	public boolean dismissPopup(String userAction,boolean isInfo, boolean loop){
		 String winTitle = "";
	    return dismissPopup(winTitle,userAction,isInfo,loop);
	}
	// end of action oriented
	public boolean dismissPopup(String userAction,boolean isInfo, boolean loop,int maxCheck){
		 String winTitle = "";
	    return dismissPopup("",winTitle,userAction,isInfo,loop,maxCheck,"");
	}
    // begin of title oriented
	public boolean dismissPopup(String winTitle,String userAction){
		boolean isInfo = false;
	    return dismissPopup(winTitle,userAction,isInfo);
	}
	public boolean dismissPopup(String winClass,String winTitle,String userAction,String expInfo){
		boolean isInfo = false;
		boolean loop = false;
		int maxCheck = 3;
	    return dismissPopup(winClass,winTitle,userAction,isInfo,loop,maxCheck,expInfo);
	}
	public boolean dismissPopup(String winTitle,String userAction,boolean isInfo){
		 String winClass = "";
	    return dismissPopup(winClass,winTitle,userAction,isInfo,false);
	}
	public boolean dismissPopup(String winTitle,String userAction,boolean isInfo, boolean loop){
        String winClass = "";
		return dismissPopup(winClass,winTitle,userAction,isInfo,loop);
	}
	// end of title oriented
	
	public boolean dismissPopup(String winClass, String winTitle,String userAction,boolean isInfo, boolean loop){
		int maxCheck = 3;
		return dismissPopup(winClass,winTitle,userAction,isInfo,loop,maxCheck);
	}
	public boolean dismissPopup(String winClass, String winTitle,String userAction,boolean isInfo, boolean loop,int maxCheck){
		return dismissPopup(winClass,winTitle,userAction,isInfo,loop,maxCheck,"");
	}

	@SuppressWarnings("finally")
	public boolean aclVersionUpdate = false;
	public boolean dismissPopup(String winClass, String winTitle,String userAction,
			  boolean isInfoUser, boolean loop,int maxCheck,String expInfo){		
//		dismissPopup("#32770","ACL Wizard Error|"+LoggerHelper.autTitle,"OK",
//			          false,false,1, " continue");

        // ***************** Message related **************************
 		
		String platformVariants = "Confirm Save As";
		String fatalError = "Network Error";
		String vsError = "Visual Studio .* Debugger.?"+
		                  ""; 
		String progressBar = "Status of Task"+
                             "|.*Progress\\.\\.\\.";
		String incompletedMsg = ".*Valid choices are";
        // *************************************************************
		
		// ******** Default Pop up captions *******
		String winTitleDefault = "ACL|Aclwin|"+LoggerHelper.autTitle+".?|Security.?"+
		                 "|Project.?|Save Project As.?|Save New Project As.?"+
				         "|Confirm Save As.?|Network Error.?|Warning.?|Security Warning.?"+
				         "|ACL Error.?|Error.?"+
				         "|Automatic Updates.?"+
				         "|Save the file as.?"+
				         "|Visual Studio .* Debugger.?"+
				         "|Option files is missing.?"+
				         "|Status of Task.?"+
				         "|.*Progress\\.\\.\\."+
				         "|.*Error.*"+
				         "";					
		String winClassDefault = "#32770";
		//***********************************************
        
		// *********** actions *********************************************************
		String[] actions = {".*[Oo][kK].*","Yes","Restart Later","Close","Cancel","Don't Send","No","X"};
		String[] actions2 = {"Restart Later","Cancel","Don't Send","Close","X","No",".*[Oo][kK].*","Yes"};
		// *****************************************************************************
		
		// ********  Report *************************
		String msgPrefix = "[Popup Message] - \n\t\t";
		String userPre = "User Decision: ";
		String autoPre = "Automation Decision: ";
		String actualTitle = "";
	    // ******************************************
		

		
 // Step: 1 Preparing... 
	 // *********** Controls **************
		int numCheck = 0;
		boolean dismissed = false;
		boolean tryAgain = true;
		boolean notFound = true;
        boolean isInfo = isInfoUser;
        
		TopLevelTestObject popup = null;
		TestObject msgBox;
		TestObject popupObject;
		GuiTestObject userButton;
		GuiTestObject autoButton;
		
	 //*******************************************
        
	 // *** Pattern represented informations which are not considered as Error 
	    String nonError = "Automatic Updates|All previously saved.*"+
           "|.*Do you want to save the changes.*"+
           "|.*Namespace Tree Control.*"+
           "|.*will attempt to harmonize these fields.*"+
           "|.*Do you wish to continue.*"+
           "|.*Do you want to proceed.*"+
           "|.*is from a previous version.*"+
           "|.*Options file missing.*"+
           "|.*Are you sure you want to.*"+
           "|Edit";
	    
		if(!expInfo.equals("")){
			nonError += "|"+expInfo;
		}
	 // *** Max number of loops, negative number means infinite	
		if(maxCheck<=0){
			maxCheck = Integer.MAX_VALUE;
		}
    
    //  *** Title,Class and actions *************************
		if(winTitle.trim().equals("")){     //Title 0 - use specified caption
			winTitle = getLocalizedWinTitle(winTitleDefault);     //Title 1 - check default caption pattern
		}else if(!winTitle.matches(winTitleDefault)||
				!winTitleDefault.contains(winTitle)){
			actions = actions2;               // if it's a popup other than those in default,
			                                  // the default actions changes: cancel first
		}
		
		if(winClass.equals("")){          //Class 0 - user input
			winClass = winClassDefault;   //Class 1 - default to #32770
		}
		if(userAction.equalsIgnoreCase("Any")){ // Action 1 - user input
			                                    // Action 2 - default
			userAction = "";
		}
   //Step 2.  Begin with finding popup windows.....	
//		if(winTitle.contains("ACL Wizard Error")||expInfo.contains("continue")){
//			sleep(0);
//		}
		if(userAction.equals("Yes"))
			sleep(0);
		while(tryAgain
				&&numCheck<maxCheck
				&&(popupObject=findTopLevelWindow(winTitle,winClass))!=null
				){ 
                try{
			        popup = new TopLevelTestObject(popupObject);
                }catch(Exception e){
                	logTAFDebug("Window not found "+e.toString());
                	return false; // It's not a toplevelwindow;
                }
                
				notFound = false;
				

		  // 1. *** popup found, get the actual Title then convert to english
				IWindow iw = null;
				
				try{
					iw = getScreen().getActiveWindow();
					actualTitle = iw.getText();
				}catch(Exception e){
				}				
				try{
				    actualTitle = popup.getProperty(".name").toString();
				}catch(Exception e){
					actualTitle ="";
				}
				if(actualTitle.equals("")){
					try{
						actualTitle = getActiveWinTitle();
					}catch(Exception e){
						actualTitle="";
					}	
				}
				
				if(actualTitle.equals(LoggerHelper.autTitle)&&numCheck>3&&maxCheck==Integer.MAX_VALUE){  
					try{
						while(iw!=null&&actualTitle.equals(LoggerHelper.autTitle)&&iw.isShowing()){
							numCheck++;
							LoggerHelper.logTAFInfo("Too many "+iw.getText()+" - Errors, close the pop up");
							iw.close();
							sleep(1);
							iw = ObjectHelper.getDialog(LoggerHelper.autTitle,winClass,true);
							actualTitle = iw.getText();
						}
					}catch(Exception e){
						return true;
					}
            		if(!actualTitle.equals(LoggerHelper.autTitle)){
            			continue;
            		}else{
            			numCheck++;
            		}
				}else{
					numCheck++;
				}
				
				
				String locTitle = actualTitle;			
				actualTitle = getEngValue(actualTitle);
			// ********************************************************	
		    // 2. *** Wait if in progress....
				if(actualTitle.matches(platformVariants)){
					logTAFWarning("Label '"+actualTitle+"' is not an ACL message?");
				}
				// in the case of  not being handled in msg, currently trans props not found - Steven
				else if(actualTitle.matches("Delete")){  
					logTAFWarning("Delete warning ?");
					isInfo = false;
				}else if(actualTitle.matches(vsError)||actualTitle.contains("Studio")){  
					logTAFWarning(actualTitle);
					isInfo = false;
					userAction = "No";
				}
				
				
				if(actualTitle.matches(getLocValues(progressBar))){
					int maxProcessTime=60,atime=0;
					while(getActiveWinTitle().equals(locTitle)&&atime++<maxProcessTime){
					  logTAFInfo("Status of task: In progress..., wait for 10 seconds");
					  sleep(10);
					}
					if(getActiveWinTitle().equals(locTitle)){
						continue;
					}else{
					   return false;
					}
				}
			// *********************************************************
				
			// 3. *** Get message from the popup
				String msg = "";
				if((msgBox = findStatictext(popup))!=null){
					Object msgobj = msgBox.getProperty(".text");
				
					if(msgobj==null)						
						msgobj = msgBox.getProperty(".name");
					if(msgobj==null)
						msgobj = msgBox.getDescriptiveName();
					if(msgobj!=null){
						msg = msgobj.toString();
						//logTAFInfo("Message of "+ actualTitle +" is: '"+msg+"'");
						logTAFDebug("Message of "+ actualTitle +" is: '"+msg+"'");
					}else
						logTAFDebug("Message of "+ actualTitle +" Popup not found?" );
				}else{
					logTAFDebug("Message of "+ actualTitle +" Popup not found" );
				}
			// 4. *** Analyse message, determine if it's information or error message
				//****a. special cases
				if(msg.toUpperCase().startsWith(getLocValue("CLICK HERE"))){
					msg = "ACL Crashed?"; // For possible acl scrashing...
					isInfo = false;
				}else if(msg.matches(getLocValues(incompletedMsg)+".{5,}")){ // message completed
					msg = "Incompleted message ?"+msg;
					isInfo = true;
				}else if(msg.matches(getLocValues(incompletedMsg))){       // message incompleted.
					msg = "Incompleted message ?"+msg;
					isInfo = false;
				}else if(msg.matches(getLocValues(".*is from a previous version.*"))){					
					aclVersionUpdate = true;
				}
				//****b. filtering message and compare with non error pattern
				nonError = removeLineFeed(getLocValues(nonError));
				msg = removeLineFeed(msg);
				String san_msg = sanitizeText(msg);				
				String san_nonError = sanitizeText(nonError);				
//		logTAFInfo("***********'"+msg +"' matches '"+nonError +" = " + san_msg.matches(nonError));
				
				if(msg.matches(nonError)||
						//msg.contains(nonError)||
						san_msg.matches(san_nonError)||
						nonError.contains(msg)){
					isInfo = true;
				}else if(!expInfo.equals("")){ // if user specific info, user user specific true|false
					isInfo = isInfoUser;
				}else if(numCheck>1){ // All following popups are treated as infomation, 
					                  //we only report possible error from the first
					isInfo = true;
				}
//		logTAFInfo(nonError+" = '"+msg+"' "+(msg.matches(nonError)||numCheck>1));
				
			// 5. *** Apply action(s) to the popup message
				//****a. apply user specified actions
				if(!userAction.equals("")){
					//if(userAction.matches(".*Working|.*Last-saved|.*Cancel"));
					
					if(userAction.equalsIgnoreCase("X")){
						reportDismissedWin(actualTitle,msg,isInfo);
						popup.close();
					}else if(userAction.equalsIgnoreCase("ENTER")){
						reportDismissedWin(actualTitle,msg,isInfo);
						getScreen().inputKeys("{"+userAction+"}");
					}else if((userButton = findPushbutton(popup,userAction))!= null){
						if(isEnabled(userButton)){
						   reportDismissedWin(actualTitle,msg,isInfo);
						   click(userButton,userPre+userAction);	
					       dismissed = true;
						}else{
							dismissed = false;
						}
					}
//	            	if(userAction.matches(".*Working|.*Last-saved|.*Cancel")){
//            		     printObjectTree(popup);
//            		     sleep(0);
//            	      }
				}else{	
				//****b. Apply default actions - trying one by one until button found
					for(int act=0;act<actions.length;act++){
						if(actions[act].equalsIgnoreCase("X")){  // Close the popup
							reportDismissedWin(actualTitle,msg,isInfo);
						    getScreen().getActiveWindow().close();
							try{
						       getScreen().getActiveWindow().close();
							//   popup.close();
							   dismissed = true;
							}finally{
								break;
							}
						}else{
							if((autoButton = findPushbutton(popup,actions[act]))!=null){
//								if(msg.matches(getLocValues(".*harmonize.*")))
//								    logTAFDebug("Taking Action["+act+"]: "+autoPre+actions[act]);
								reportDismissedWin(actualTitle,msg,isInfo);
								click(autoButton,autoPre+actions[act]);	
							    dismissed = true;
							    break;
						      }
						}
					}
				}
		 // 6. *** Check if dismissed sucessfully
				if(!dismissed){
					//****a. close active window and report
					notFound = true;
					if(userAction.equals("")){
					logTAFWarning("Failed to dismiss this "+actualTitle+
					" by submit any button actions, we will try to use 'X' to close it now");					
					try{
					 reportDismissedWin(actualTitle,msg,isInfo);
					 getScreen().getActiveWindow().close();
					 //popup.close();
					 dismissed = true;
					}finally{
						
					}
					}else{
						logTAFDebug("Failed to dismiss Popup Message by action on '"+userAction+"' - '"+actualTitle+"': "+msg);
					}
				}else{
				   // **** b. just report
//					if(isInfo||numCheck>1){				
//						logTAFInfo("Popup Message - '"+actualTitle+"': "+msg);
//					}else{
//						logError(msg);
//					}
				}

		// 7. cleanup this loop

				if(!loop){ // use decition 
					tryAgain = false;
				}else{ // for following loop, use defaults only!
					sleep(1);	
					userAction = ""; 
					winTitle = getLocalizedWinTitle(winTitleDefault); 
					winClass = winClassDefault;
				}
		}//End while
		
		return dismissed;
	}
	
	   private static String locWinTitleDefault = "";
	   private String getLocalizedWinTitle(String winTitleDefault){
		   if(locWinTitleDefault == "")
		     locWinTitleDefault = getLocValues(winTitleDefault);
		   return "Localized:"+locWinTitleDefault;
	   }
	   private void reportDismissedWin(String winTitle,String msg,boolean isInfo){
		    String fatalError=".*cannot [\\s]*access.*"+
		                      ".*You cannot use the Command Line when a script is running.*"+
		                      "";
		    String progressBar = "Status of Task"+
                                 "|.*Progress\\.\\.\\.";
		    
		    String msg_en = getEngValue(msg+".*");
		    if(!msg_en.equals(msg+".*")){
		    	msg += "\n\t\t("+msg_en+")";
		    }else{
		    	//logTAFInfo("English: "+msg_en);
		    }
			if(isInfo&&(!msg.matches(fatalError)||msg_en.matches(fatalError))){				
				logTAFInfo("Popup Message - '"+winTitle+"': "+msg);
			}else if(winTitle.matches(progressBar)){
				logTAFInfo("Still In progress...?, wait for one more minute");
				sleep(60);
			}else{
				logError(winTitle+"-"+msg);
			}
	   }
	   
	   
	   public String getStaticValueByLabel(TestObject anchor,String label){
	    return getStaticValueByLabel(anchor,label,false);
	   }
	   public String getStaticValueByLabel(TestObject anchor,String label,boolean isChild){
		   String svalue=""	;
		   TestObject lab,item;
		   int labint=-1,svalint=-1;
		   
		   lab = findTestObject(anchor,true,".class","Static",".name",label);
		   if(lab!=null){
			   try{
			     labint = Integer.parseInt(lab.getProperty(".classIndex").toString());
			     svalint = labint+1;
			   }catch(Exception e){
				   
			   }
		   }
		   
		   item = findTestObject(anchor,true,".class","Static",".classIndex",svalint+"");
		   if(item!=null){
			   svalue = item.getProperty(".name").toString();
		   }
		   return svalue;
		   
	   }
       public void testmain(Object[] args){
    	   htmlhandler = new htmlRFTHandler();
    	   
       }
       public ObjectHelper(){
    	   foundObjs = new ArrayList<TestObject>();
    	   
       }
                 	
       public static void main(String[] args){
    	   String dpMasterFile="D:\\ACL\\TFSView\\RFT_Automation\\QA_Automation_2012_V2.0\\ACLQA_Automation\\ACL_Desktop\\DATA\\ExpectedData\\NonUnicode\\en_US\\Analyze\\Benford\\BenfordTest_10.FIL";
    	   String dpActualFile="P:\\RFT_DATA\\TempData\\Steven_Xiang\\Windows_XP_IP4_124\\Analyze\\Benford\\BenfordTest_10.FIL";
    	   new ObjectHelper().compareTextFile(dpMasterFile, dpActualFile,
					false,"File");	
       }

	}