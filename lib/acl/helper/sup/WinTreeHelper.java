package lib.acl.helper.sup;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import lib.acl.util.FileUtil;
import lib.acl.util.ImageCompare;
import lib.acl.util.NLSUtil;

import ACL_Desktop.AppObject.DesktopSuperHelper;
import ACL_Desktop.conf.beans.ProjectConf;

import com.rational.test.ft.object.interfaces.ClientTestObject;
import com.rational.test.ft.object.interfaces.GuiSubitemTestObject;
import com.rational.test.ft.object.interfaces.GuiTestObject;
import com.rational.test.ft.object.interfaces.ITopWindow;
import com.rational.test.ft.object.interfaces.IWindow;
import com.rational.test.ft.object.interfaces.ShellTestObject;
import com.rational.test.ft.object.interfaces.TestObject;
import com.rational.test.ft.object.interfaces.ToggleGUITestObject;
import com.rational.test.ft.object.interfaces.TopLevelSubitemTestObject;
import com.rational.test.ft.script.Action;
import com.rational.test.ft.script.IClipboard;
import com.rational.test.ft.script.MouseModifiers;
import com.rational.test.ft.script.RationalTestScript;
import com.rational.test.ft.script.State;
import com.rational.test.ft.script.Subitem;
import com.rational.test.ft.value.MethodInfo;
import com.rational.test.ft.vp.ITestData;
import com.rational.test.ft.vp.ITestDataElement;
import com.rational.test.ft.vp.ITestDataElementList;
import com.rational.test.ft.vp.ITestDataList;
import com.rational.test.ft.vp.ITestDataTable;
import com.rational.test.ft.vp.ITestDataText;
import com.rational.test.ft.vp.ITestDataTree;
import com.rational.test.ft.vp.ITestDataTreeNode;
import com.rational.test.ft.vp.ITestDataTreeNodes;

import conf.beans.FrameworkConf;

/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  February 03, 2012
 */
public abstract class WinTreeHelper extends PrintObjectInfoHelper
{
   	// ********  Deal with RFT Tree **************************************************
	// expand the curNode (i.e. GuiSubitemTestObject )

    public String SEP_REP_PATH = "->";
    public Point actionPoint = new Point(-30,10),
                  textPoint = new Point(5,10),
                  iconPoint = new Point (-15,10);
   
    public boolean  collapsible = true; 
    protected static void locateTreeRoot(GuiTestObject anchor){
    	locateTreeRoot(anchor,true);
    }
	protected static void locateTreeRoot(GuiTestObject anchor,boolean fromParent){
		GuiTestObject indicator,scrollbar;
		TestObject treeWin,to1[],to[];
		if(fromParent){
		 try{
		   treeWin = anchor.getParent();
		 }catch(Exception e){
			treeWin = null;
		 }
		}else{
			treeWin = anchor;
		}
		if(treeWin==null){
			return;
		}
		try{
			to1 = treeWin.find(atDescendant(".class",".Scrollbar"),false);		
			if(to1==null||to1[0]==null){ 
				   return;
			
			}		
			to = to1[0].find(atDescendant(".class",".Indicator"),false);

			if(to==null||to[0]==null){
				return;
			}
		}catch(Exception e){
			return;
		}

		scrollbar = new GuiTestObject(to1[0]);		
		indicator = new GuiTestObject(to[0]);
		Point scrollbarPt = scrollbar.getScreenPoint(atPoint(0,0));
		Point indicatorPt = indicator.getScreenPoint(atPoint(0,0));
		logTAFDebug("Drag from '"+indicatorPt+"' to '"+scrollbarPt+"'");
		if(scrollbarPt.x>50&&indicatorPt.y>50){
		    logTAFDebug("You have a long list of items, we will try to scroll up");
		    indicator.dragToScreenPoint(scrollbarPt);
		}
//		scrollbar = new GuiTestObject(to1[0]);
//		logTAFDebug("You have a long list of items, we will try to scroll up");
//		indicator = new GuiTestObject(to[0]);
//		indicator.dragToScreenPoint(scrollbar.getScreenPoint(atPoint(0,0)));
//		sleep(0);
	}
	
 
    protected int searchSubitem(GuiSubitemTestObject tree, String itemPath){

    	String[] nodes = itemPath.split(SEP_REP_PATH);
    	int itemIndex = -1;
    	
    	for(int i=0;i<nodes.length;i++){
    		if(i==0)
    			itemIndex = 0; // The root
    		else
    		   itemIndex = searchTreeNode(tree, nodes[i],itemIndex+1);
    		if(itemIndex==-1){
    			logTAFWarning("Node '"+nodes[i]+"' in path '"+itemPath+"' not found");
    			break;
    		}else{
    			expandNode(tree,atIndex(itemIndex));
    		}
    	}
    	
    	return itemIndex;
    }
    
    protected int searchTreeNode(GuiSubitemTestObject tree, String label, int startAt){
    	int itemIndex = -1;
    	int numItems = -1;
    	String itemName = "";
    	try{
    		numItems = Integer.parseInt(tree.getProperty(".numChildren").toString());
    	}catch(Exception e){
    		//logTAFWarning("Nothing found inside the navigation pane");
    		numItems = -1;
    		return itemIndex;
    	}
    	for(int i=startAt;i<numItems;i++){
    		if(propertyMatch((TestObject) tree.getSubitem(atIndex(i)),".visible","false"))
        		continue;        	
    		itemName = getNameFromClipboard(tree,atIndex(i));
    		if((i==0&&itemName.equals("")) // For root which is not modifiable
    				||itemName.equalsIgnoreCase(label)){
    			logTAFDebug("Node found: '"+label+"'");
    			itemIndex = i;
    			break;
    		}else if(collapsible){
    			expandNode(tree,atIndex(i),false);
    		}
    	}
    	return itemIndex;
    }
    
    protected String getNameFromClipboard(GuiSubitemTestObject tree,Subitem item){
    	String itemName = "";
    	ITopWindow tw = (ITopWindow) tree.getTopParent();
    	int numWait = 0;
    	try{
    	   tree.click(item,iconPoint);
    	   sleep(1);
    	   tree.click(item,textPoint);
    	   sleep(2);
    	}catch(Exception e){
    		logTAFDebug(e.toString());
    	}
    	getSystemClipboard().setText("");
    	tw.inputKeys("^c");
    	while((itemName= getSystemClipboard().getText().trim()).equals("")
    			&& numWait++<5){
    		tree.click(item,textPoint);
    		sleep(2);
    	    tw.inputKeys("^c");
    	}
	    try{
//	    	   tree.click(item,iconPoint);
	    	}catch(Exception e){
	    		logTAFDebug(e.toString());
	    	}
    	return itemName;
    }
    //  ****************  Expand ********************
    
	protected boolean expandNode(GuiSubitemTestObject tree, Subitem item){
	    return expandNode(tree,item,true);
	}
	protected boolean expandNode(GuiSubitemTestObject tree, Subitem item,boolean expand)
	{		
		boolean expanded = false;
    	String defaultAction = "";
    	
    	try{
		    defaultAction = ((TestObject) tree.getSubitem(item)).getProperty(".defaultAction").toString();
    	}catch(Exception e){
    		defaultAction = "";
    	}    	
    	if(defaultAction.equalsIgnoreCase("Expand")){
    		if(expand){
    		   tree.click(item,actionPoint);
    		   expanded = true;
    		}else{
    		   expanded = false;
    		}
    	}else if(defaultAction.equalsIgnoreCase("Collapse")){
    		if(!expand){
    		   tree.click(item,actionPoint);
    		   expanded = false;
    		}else{
    			expanded = true;
    		}
    	}
		return expanded;
	}

	//verify icon
	protected boolean verifyItemIcon(GuiSubitemTestObject tree, Subitem item, String name,int status,String[] mFile, String[] aFile)
	{		
        boolean done = true;
    	int iconHeight = 18,
    	    iconWidth = 18,
    	    offsetX = - 18,
    	    offsetY = 0;
		String[] statusArray = {"Closed","Primary","Secondary","Opened"};
    	Point topLeft = tree.getScreenPoint(item,new Point(offsetX,offsetY));
    	
    	Rectangle iconRectangle = new Rectangle(topLeft.x,topLeft.y,iconWidth,iconHeight);
    	logTAFStep("Check table icon status,  expected - "+statusArray[status]);
    	tree.getImage(iconRectangle,aFile[status]);
    	
        if(compareImage(mFile[status],aFile[status],ProjectConf.updateMasterFile)){
        	logTAFInfo("Item "+name+" is with the icon indicating - "+statusArray[status]+" as expected"	);
        }else{
        	logTAFError("Item "+name+" is not with the icon indicating - "+statusArray[status]+" as expected"	);
        }
    	
        return done;
	}
	
    public boolean compareImage(String fileMaster,String fileActual,boolean updateMasterFile){
 	   File temp = new File(fileActual);
 	   if(!temp.exists()){
 		   logTAFWarning("File not found - '"+fileActual+"'");
 		   return true;
 	   }else if(!temp.isFile()){
 		   logTAFWarning("Not a file - '"+fileActual+"'");
 		   return true;
 	   }
 	                    // For debugging, don't update
 	                    // updateMasterFile = false;
 	   if(updateMasterFile||!new File(fileMaster).exists()){
 		   logTAFInfo("Save/Update contents of master file '"+fileMaster+"'");
 			   FileUtil.copyFile(fileActual, fileMaster);
            return true;
 	   }
 	       	   
 	   logTAFStep("Comparing image"+": "+fileMaster +" and "+fileActual);
 	   return ImageCompare.isSimilarImage(fileActual,fileMaster);
 	   
    }
	//**************** Helps *********************
  	public static boolean propertyMatch(TestObject to, String name,String value){
  		return propertyMatch(to,name,value,true);
  	}
   	public static boolean propertyMatch(TestObject to, 
   			String name,String value,boolean ignoreNotFound){
   		boolean match = false;
   		value = getLocValues(value);
   		String actualValue ="";
   		if(to!=null&&to.exists()){
   			try{
   				actualValue = to.getProperty(name).toString();
   				if(actualValue==null&&value!=null){
   					match = false;
   				}else{
   				match = actualValue.matches(value)
   				          ||actualValue.equalsIgnoreCase(value);
   				          //||actualValue.startsWith(value);
   				logTAFDebug("Actual value: '"+actualValue+"', Expected value: '"+ value+"'");
   				}
   			}catch(Exception e){
   				logTAFDebug("Exception when check property - "+name+" of "+ to+": "+e.toString());
   			    if(!ignoreNotFound){
   			    	match = false;
   			    }else{
   				   match = true;
   			    }
   				//_printObjects.printObjectTree(to);
   			}
   		}else{
			    if(!ignoreNotFound){
   			    	match = false;
   			    }else{
   				   match = true;
   			    }
   			logTAFWarning("Object doesn't exists - "+to);
   		}
   	     return match;
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


}
