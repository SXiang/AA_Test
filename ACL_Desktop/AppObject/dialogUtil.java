package ACL_Desktop.AppObject;

import java.awt.Point;

import lib.acl.util.FileUtil;
import resources.ACL_Desktop.AppObject.dialogUtilHelper;
import ACL_Desktop.conf.beans.ProjectConf;

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
import com.rational.test.util.regex.Regex;
import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;

public class dialogUtil extends dialogUtilHelper
{
	/**
	 * Script Name   : <b>dialogUtil</b>
	 * Generated     : <b>2012-01-16 11:47:15 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/01/16
	 * @author Steven_Xiang
	 */
	private static keywordUtil kUtil = new keywordUtil();
	public static String currentSetting = "Unknown";
	public static boolean safety = true;
	public static boolean overflow = true;
	
	public boolean fileChooser(String name,String act,boolean positive){
		boolean done = true;
		waitForExistence(fc_filename());
    	if(fc_filename().exists()){	
        	if(act.equalsIgnoreCase("Open")){
        		fc_filename().click();
        		inputUnicodeChars(name);            	
        	   clickOnObjectSafely(fc_open(),"Open");
        	}else if(act.equalsIgnoreCase("Save")){
        		//fc_filename().doubleClick();
        		inputUnicodeChars(name);
        		clickOnObjectSafely(fc_save(),"Save");
        	}

    	}else{
    		done = false;
    		logTAFError("Project dialog does not appear");
    	}
    	
    	return done;
	}
	public boolean aclDefaultSetting(){
	    return aclOptions("Interface","Factory",null);
	}
	public boolean aclOptions(String tab,String doneWith,Object[]options ){
		if(doneWith.equalsIgnoreCase(currentSetting)){
			   return true;
			}
		boolean done = false;
		Point In = atPoint(28,8),
		      Ta = atPoint(76,8);
//		      Vi = atPoint(),
//		      Co = atPoint(),
//		      Da = atPoint(),
//		      Nu = atPoint(),
//		      Pr = atPoint(),
//		      Ap = atPoint(),

		kUtil.invokeMenuCommand("Tools->Options");
		waitForExistence(op_Factory());
		if(!op_Factory().exists()){
			logTAFError("Options dialog does not appear");
			return done;
		}
		//op_window().click(atList(atText(tab)));
		for(int opIndex=0;options!=null&&opIndex<options.length;opIndex++){
			if(tab.equals("Interface")&&options[opIndex]!=null){			   
//				   GuiTestObject gto = findPagetab(op_tab(),tab);
//				   click(gto,tab);
		       switch (opIndex){
		          case 0: op_0_showToolbar().clickToState((State) options[opIndex]);
		              logTAFStep("Set Interface option ShowToolBar to '"+options[opIndex]+"'");
		        	  break;
		          case 1: op_0_includeFiltersInField().clickToState((State) options[opIndex]);
		              logTAFStep("Set Interface option Include Filters in Field Lists '"+options[opIndex]+"'");
		        	  break;
		          case 2: op_0_warnBeforeOverwriting().clickToState((State) options[opIndex]);
		              logTAFStep("Set Interface option Warn Before Overwriting Files to '"+options[opIndex]+"'");
		        	  break;
		          case 3: 
		        	  if(op_0_enableACLServerIntegration().getState().equals((State) options[opIndex])){
		        	     logTAFInfo("Current ACL Server Integration is '"+(State)options[opIndex]+"' as default"+"'");
		              }else{
		        	   op_0_enableACLServerIntegration().clickToState((State) options[opIndex]);
		               logTAFStep("Set Interface option Enable ACL Server integration '"+options[opIndex]+"'");
		              }
		              break;
		          case 4: op_0_saveAllScriptsLocally().clickToState((State) options[opIndex]);
		              logTAFStep("Set Interface option Save All Scripts Locally to '"+options[opIndex]+"'");
		        	  break;
		          case 5: op_0_beeps().setText((String) options[opIndex]);
		              logTAFStep("Set Interface option Beeps to '"+options[opIndex]+"'");
		        	  break;     	  
		       }
			}else if(tab.equals("Table")&&options[opIndex]!=null){			   
				   GuiTestObject gto = findPagetab(op_tab(),tab);
				   click(gto,tab);
//				   click(op_tab(),Ta,tabName);
			       switch (opIndex){
			          case 0: op_1_automaticallyProfileOnOpen().clickToState((State) options[opIndex]);
			              logTAFStep("Set Table option Automatically Profile On Open to '"+options[opIndex]+"'");
			        	  break;
//			          case 1: op_1_deleteDataFileWithTable().clickToState((State) options[opIndex]);
//			              logTAFStep("Set Table option DeleteDataFileWithTable '"+options[opIndex]);
//			        	  break;
			          case 2:  op_1_deleteDataFileWithTable().clickToState((State) options[opIndex]);
		                  logTAFStep("Set Table option DeleteDataFileWithTable '"+options[opIndex]+"'");
		        	      break;
//			          case 3: 
//			        	  if(op_0_enableACLServerIntegration().getState().equals((State) options[opIndex])){
//			        	     logTAFInfo("Current ACL Server Integration is '"+(State)options[opIndex]+"' as default");
//			              }else{
//			        	   op_0_enableACLServerIntegration().clickToState((State) options[opIndex]);
//			               logTAFStep("Set Interface option Enable ACL Server integration '"+options[opIndex]+"'");
//			              }
//			              break;
//			          case 4: op_0_saveAllScriptsLocally().clickToState((State) options[opIndex]);
//			              logTAFStep("Set Interface option Save All Scripts Locally to '"+options[opIndex]+"'");
//			        	  break;
//			          case 5: op_0_beeps().setText((String) options[opIndex]);
//			              logTAFStep("Set Interface option Beeps to '"+options[opIndex]+"'");
//			        	  break;     	  
			       }
			}
		}
		
		if(doneWith.equalsIgnoreCase("Cancel")){
			clickOnObjectSafely(op_Cancel(),"Cancel");
		}else if(doneWith.equalsIgnoreCase("Factory")){
		 if(ProjectConf.appLocale.equalsIgnoreCase("En")){
			clickOnObjectSafely(op_Factory(),"Factory");
			confirmAction("Yes");	
    		 }
			clickOnObjectSafely(op_OK(),"OK");
    		
			currentSetting = doneWith;
			safety = true;
			overflow = true;
		}else{
			clickOnObjectSafely(op_OK(),"OK");
		}
		return done;
	}
	
	public boolean confirmWarning(String action){
		return confirmWarning(action,true);
	}
	public boolean confirmWarning(String action,boolean isInfo){
		return dismissPopup(action,isInfo);
	}	
	
	public boolean confirmAction(String action){
		return confirmAction(action,true);
	}
	public boolean confirmAction(String action,boolean isInfo){
		return dismissPopup(action,isInfo);
	}
	
	

	public GuiTestObject getGuiByName(String gui) 
	{
		GuiTestObject rgui = null;
		      rgui = new GuiTestObject(
                        getMappedTestObject(gui));
		return rgui;
	}
	public void testMain(Object[] args) 
	{
		// TODO Insert code here
	}
	public dialogUtil(){
		
	}
}

