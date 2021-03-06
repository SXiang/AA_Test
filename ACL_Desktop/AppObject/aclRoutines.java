package ACL_Desktop.AppObject;

import java.awt.Point;

import resources.ACL_Desktop.AppObject.aclRoutinesHelper;
import com.rational.test.ft.*;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.WPF.*;
import com.rational.test.ft.object.interfaces.dojo.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.object.interfaces.generichtmlsubdomain.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.*;
import com.rational.test.ft.vp.*;
import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;

public class aclRoutines extends aclRoutinesHelper
{
	/**
	 * Script Name   : <b>aclRoutine</b>
	 * Generated     : <b>2012-02-03 10:04:52 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/02/03
	 * @author Steven_Xiang
	 */
	public static getObjects gObj = new getObjects();
	public static dialogUtil dLog = new dialogUtil();
	public static keywordUtil kUtil = new keywordUtil();
	public static aclTableTabs aTabs = new aclTableTabs();

	public boolean exeACLCommands(String comms){
		return exeACLCommands(comms,"");
	}
	public boolean exeACLCommands(String comms,String actionOnTab){
		boolean done = true;
		
//MutilLine commands
//		String mutilLine = comms.replaceAll("[\\|;]","\r\n");		
//		exeACLCommand(mutilLine);
		//click(acl_contentPane(),atPoint(100,30));
		String[] commArr = comms.split("[\\|;]");		
		for(int i=0;i<commArr.length&&!commArr[i].equals("");i++){
			if(!exeACLCommand(commArr[i],i==0?true:false,actionOnTab)){
				done = false;
			}
		}
		if(comms.toUpperCase().contains("OPEN")){
			sleep(2);	
			dismissPopup("Any",true);
            kUtil.closeServerActivity(false);
            kUtil.isActivated(true);            
            //kUtil.checkACLCrash();
		}else{
           dismissPopup("Any",true);
		}

		return done;
	}
	public boolean exeACLCommand(String comm){
		return exeACLCommand(comm,"");
	}
	public boolean exeACLCommand(String comm,String actionOnTab){
		boolean clear = true;
		return exeACLCommand(comm,clear,actionOnTab);
	}
	public boolean exeACLCommand(String comm, boolean clear){
		return exeACLCommand(comm,clear,"");
	}
	public static TestObject cmd_to=null;
	public boolean exeACLCommand(String comm, boolean clear,String actionOnTab){
		if(comm==null||comm.equals(""))
			return true;
		GuiSubitemTestObject cmdLine;// = findTestObject(, String... pairs ) {
		boolean done = false;
		String tableStatus = "";
		String item = "";
		
		if(comm.matches("(?i)OPEN .*")){
			item = comm.replaceAll("(?i)OPEN (.*)", "$1").trim();
			//logTAFInfo(tableName + " is opened and active ? "+aTabs.isActive(tableName));
//			if(aTabs.isActive(item)){
			if(aTabs.isActive(item)||!aTabs.isTraceable(item)){
				tableStatus = aTabs.getTableStatus(item);
				//logTAFInfo("Acl status: '"+tableStatus+"'");
				if(applyWR&&tableStatus.matches("(?i)"+item+"\\|.*")){
				  logTAFInfo("Warning: "+item+" had been opened and is active!");
//				  return true;
				}
			}
			aTabs.handleWpage();
		}
		TestObject to = findTestObject(acl_SplitterWin(),".class","ACL_CommandLine_WND");
		//TestObject to = findTestObject(acl_SplitterWin(),".class","ACL_CmdLine_Edit",".classIndex","0");
		if((to==null||!propertyMatch(to,".visible","true",false))&&
				!(to=acL_CommandLine()).exists()){
			kUtil.invokeMenuCommand("Window->ShowCommandLine");
			//sleep(2);
			to = findTestObject(acl_SplitterWin(),".class","ACL_CommandLine_WND");
		}
		if(to==null){
			if(!(to=acL_CommandLine()).exists()){
			logTAFWarning("Failed to find acl command line?");
			return false;
			}
		}else{
		   cmd_to = to;
		}
        
//        if(clear){
//          click(findPushbutton(to,"ACL_CmdLine_ClearBtn"));
//          //click(acl_CmdLine_Clear(),"Clear Cmd");
//          sleep(1);
//        }
        
          try{
        	  cmdLine = new GuiSubitemTestObject(
        			  findTestObject(to,".class","ACL_CmdLine_Edit"));
        	  click(cmdLine,true);
              //click(acL_CommandLine());
          }catch(Exception e){
        	  logTAFDebug(e.toString());
          }
          
        inputUnicodeChars(comm);
        click(findPushbutton(to,"ACL_CmdLine_RunBtn"),"Run Cmd");
        //click(acl_CmdLine_Run(),"Run Cmd");
//        dismissPopup("Any",true,true);
//        kUtil.closeServerActivity();
//        if(comm.matches("[Oo][Pp]?[Ee]?[Nn]?.+")){
//        	dpSaveLocalOrNet = 
//        }
        if(comm.toUpperCase().matches("SET\\sSAFETY\\sOFF"))
           dLog.safety = false;
        else if(comm.toUpperCase().matches("SET\\sSAFETY\\sON"))
            dLog.safety = true;
        if(comm.toUpperCase().matches("SET\\sOVERFLOW\\sOFF"))
            dLog.overflow = false;
         else if(comm.toUpperCase().matches("SET\\sOVERFLOW\\sON"))
             dLog.overflow = true;
        
        //comm = comm.toUpperCase();
		if(comm.matches("(?i)OPEN .*")){
			//tableName = comm.replaceAll("(?i)OPEN (.*)", "$1").trim();
			sleep(2);	
			dismissPopup("Any",true);
            kUtil.closeServerActivity(false);
            kUtil.isActivated(true);  
            
            if(!comm.matches("(?i).* SECONDARY")){
              aTabs.add(item,actionOnTab);
              //aTabs.actOnTab(actionOnTab);
            }
            //kUtil.checkACLCrash();
		}else {
           dismissPopup("Any",true);
           if(comm.matches("(?i)CLOSE")){ // No effect on scripts, as design?
             aTabs.remove();
           }
		}

		return done;
	}
	
	public boolean setACLFilters(String filters){
		boolean done = true;
		String[] filterArr = filters.split("[\\|]");
		
		for(int i=0;i<filterArr.length&&!filterArr[i].equals("");i++){
			if(!setACLFilter(filterArr[i])){
				done = false;
			}
		}
        dLog.dismissPopup("Any",true);
        //kUtil.closeServerActivity();
		return done;
	}
	private boolean setACLFilter(String filter){
		if(filter==null||filter.equals(""))
			return true;
		TestObject to;
		boolean done = false;
        //showCmdLine();
		to = getFilterBox();
		if(to==null){
			logTAFWarning("ACL filter edit box not found?");
			return false;
		}
		inputUnicodeChars(new TextGuiSubitemTestObject(to),filter);
        // inputUnicodeChars(acl_Filter_Text(),filter);
		//lick(findTestObject(acl_docManager(),,"Set Filter");
        click(acl_Filter_Run(),"Set Filter",true);
        //dLog.dismissPopup("Any",true,true);
//        kUtil.closeServerActivity();
//        if(comm.matches("[Oo][Pp]?[Ee]?[Nn]?.+")){
//        	dpSaveLocalOrNet = 
//        }
		return done;
	}
	
	public TestObject getFilterBox(){
		
		TestObject to = acl_docManager();

		if(DesktopSuperHelper.activeTab>DesktopSuperHelper.startTab){
			to = findTestObject(to,true,".class","ATL:.*",".classIndex",(DesktopSuperHelper.activeTab-DesktopSuperHelper.startTab)+"");	
		}
		return to = findTestObject(to,false,".class","Edit",".text","Filter:");	
	}
	
	public TestObject getFilterList(){
		TestObject to;
		//printObjectTree(acl_docManager());
		//to = findTestObject(acl_docManager(),false,".class",".Combobox",".text","Filter:");	
		to = findTestObject(acl_docManager(),false,".class",".List",".text","Filter:");	
		//printObjectTree(to);
		return to;
		
	}
	
	public TestObject getFilterText(){
		TestObject to;
		
		return to = findTestObject(getFilterBox(),false,".class",".Text",".text","Filter:");	
		
	}
	public String getProperties(int itemIndex,String item){
		String text = "Unknown";
        TopLevelSubitemTestObject tablePro;
		GuiSubitemTestObject tableGen;
		TestObject to;
        
		locateTreeRoot(acl_Tree());
		
		//logTAFInfo("Click projectName first time in 5 seconds");
		acl_Tree().click(atIndex(itemIndex),iconPoint);
		
		
		if(!kUtil.invokeMenuCommand("File->Properties",false))
				return text;
		
		if(!waitForExistence(tablePro=Properties())||tablePro==null){
			return text;
		}
		
		tableGen = findSubWindow(tablePro,true,"General");
		if(tableGen==null)
			return text;
	
		 if(item.equalsIgnoreCase("Location")){
			 to = findTestObject(tableGen,".class","Edit",".name","Location:");
			 if(to!=null){
				 text = to.getDescriptiveName();
			 }
//			 text = new GuiTestObject(
//	                 getMappedTestObject("prop_"+item)).getDescriptiveName();
		 }else{
			 try{
			     text = getStaticValueByLabel(tableGen,item+":",true);
			 }catch(Exception e){
				 
			 }
			 
//		    text = new GuiTestObject(
//                 getMappedTestObject("prop_"+item)).getProperty(".name").toString();
//		    if(text!=null&&text.trim().equalsIgnoreCase(item+":")){
//		    	text = new GuiTestObject(
//		                 getMappedTestObject("prop_Modified")).getProperty(".name").toString();
//		    }
		 } 
		
		click(prop_OK(),"OK",true);
		sleep(1);
		return text.trim();
	}
//	
//	public void locateTreeRoot(){
//		GuiTestObject indicator;
//		TestObject to = findTestObject(acl_TreeBar(),".class",".Indicator");
//		if(to==null){
//			return;
//		}
//		logTAFDebug("You have a long list of items, we will try to scroll up");
//		indicator = new GuiTestObject(to);
//		indicator.dragToScreenPoint(acl_TreeBar().getScreenPoint(atPoint(0,0)));
//		sleep(0);
//	}
	public int[] searchSubitems(String pathToItem){	
		return searchSubitems(false,pathToItem);
	}
	public int[] searchSubitems(boolean checkStatus,String pathToItem){	
		return searchSubitems(checkStatus,pathToItem,"Navigator",false);
	}
	public int[] searchSubitems(boolean checkStatus,String pathToItem,String[] mFile, String[] aFile){
		return searchSubitems(checkStatus,pathToItem,"Navigator",false,mFile,aFile);
	}	
	public int[] searchSubitems(String pathToItem,boolean isInfo){	
		return searchSubitems(false,pathToItem,"Navigator",isInfo);
	}
	public int[] searchSubitems(String pathToItems,String way){
	    return searchSubitems(false,pathToItems,way,false);
	}
	public int[] searchSubitems(String pathToItems,String way,boolean isInfo){
		return searchSubitems(false,pathToItems,way,isInfo);
	}
	public int[] searchSubitems(boolean checkStatus,String pathToItems,String way,boolean isInfo){
		return searchSubitems(checkStatus,pathToItems,way,isInfo,null,null);
	}
	public int[] searchSubitems(boolean checkStatus,String pathToItems,String way,boolean isInfo,String[] mFile, String[] aFile){
		int itemIndex[]=null;
		
//		String path[];
		String items[];
		String targetItem;
		String sep = "->";
        //String treeRoot = keywordUtil.workingProject+".ACL";
        String treeRoot = projName+".ACL";
        //exeACLCommand("CLOSE");
		if(way.equalsIgnoreCase("Navigator")){
			actionPoint = new Point(-30,10);
			textPoint = new Point(5,10);
            iconPoint = new Point (-15,10);
            
            items = pathToItems.split("\\|");
            
            itemIndex = new int[items.length];
			showNavigator("");
			
			// Check if project exists - index 0
			String name = getProperties(0,"Name");
			
			if(name.equalsIgnoreCase(treeRoot)){				
				logTAFInfo("Current project name '"+name+"'");
			}else{
				logTAFWarning("Project name is '"+name+"', " +projName+".ACL"+" expected");
			}
			if(projName.matches("|.ACL")){
				projName = name.replace(".ACL","");
			    treeRoot = name;
			}
			// TBD ....................
			showNavigator("");
			for(int i=0; i<items.length; i++){
				String[] item = items[i].split(":");
				String path = item[0];
				int status = 0; //(:0) [CLOSED],(:3)[OPENED],(:1) [PRIMARY],(:2) [SECONDARY]
				if(item.length>1){
					try{
						status = Integer.parseInt(item[1]);
					}catch(Exception e){
						status = 0;
					}
				}
				
				if(i>0){
					collapsible = false;
				}
				
			        itemIndex[i] = searchSubitem(acl_Tree(),treeRoot+sep+path);			
			
				if(itemIndex[i]==-1){
					if(isInfo){
						logTAFWarning("Item '"+path+"' not found");
					}else{
					  logTAFError("Item '"+path+"' not found");
					}
				}else{
					logTAFInfo("Item '"+path+"' found");
					if(checkStatus){
						verifyItemIcon(acl_Tree(),atIndex(itemIndex[i]),path,status,mFile,aFile);
					}
				}  
				
				if(i>0&&i==items.length-1)
					collapsible = true;
			}
		}else{
//		exeACLCommand("OPEN "+pathToItems);
//		sleep(3);
//		exeACLCommand("CLOSE");
//		showNavigator("Overview");
	}
		
	return itemIndex;
}


	
	public boolean exeSelectSubitems(int[] itemIndex){
	   return exeSelectSubitems(itemIndex,null);
	}
    public boolean exeSelectSubitems(int[] itemIndex,String itemNames){
        boolean isfirst = true,selected = false;
        String[] itemName=null;
        if(itemNames!=null)
        	itemName = itemNames.split("\\|");
        
		for(int i=0; i<itemIndex.length; i++){
			
			if(itemIndex[i]!=-1){
				if(isfirst){
			       acl_Tree().click(LEFT,atIndex(itemIndex[i]),iconPoint);
			       //if(itemName!=null&&itemName.length>=i+1&&itemName[i]!="")
			    	   logTAFInfo("Select item '"+itemName[i]+"'");
			       isfirst = false;
				}else{						
					acl_Tree().click(SHIFT_LEFT,atIndex(itemIndex[i]),iconPoint);
					//if(itemName!=null&&itemName.length>=i+1&&itemName[i]!="")
				    	   logTAFInfo("Shift Select item '"+itemName[i]+"'");
				}
				
				
				selected = true;
			}
		}
		return selected;
    }
//	public void showCmdLine(){
//	  if(!acL_CommandLine().exists())
//		 // if(!acl_CmdLine_Clear().exists())
//		 kUtil.invokeMenuCommand("Window->ShowCommandLine");
//	}
	public void showNavigator(){
		showNavigator("Overview");
	}

	public void activateACL(){
			ACL_Win().activate();
	}
	public void showNavigator(String tab){
		
		Point tabOverview = new Point(30, 3);
		Point tabLog = new Point(70, 10);
		GuiSubitemTestObject nav = acl_TabNavigator();
		GuiTestObject guio,guil;
		TestObject anchor;
		int wait=0,maxWait=30;
		try{
			
			while((nav==null||!nav.exists())
					&&wait++<maxWait){		
                sleep(2);
				if((nav==null||!nav.exists())&&acl_Treewin().exists()){
					anchor = acl_Treewin().getParent().getParent();
					TestObject to = findTestObject(anchor,true,
							".class","WTL_DontNetTabCtrl",".name","ACL_TabNabigator_WND");
					if(to!=null){
						nav = new GuiSubitemTestObject(to);
					}else{
						nav = null;
					}
				}
				sleep(2);
			}
		}catch(Exception e){
			logTAFDebug("Exception caught when navigating the tree");
			nav = acl_TabNavigator();
			if(!waitForExistence(nav))
				return;
		}

		//printObjectTree(nav);
		if(tab.equals("Log")){
			click(nav,tabOverview);
			click(nav,tabLog,tab);
		}else{
			click(nav,tabLog);
			click(nav,tabOverview,tab);
		}
		
	}
	public void testMain(Object[] args) 
	{
		// TODO Insert code here
	}
}

