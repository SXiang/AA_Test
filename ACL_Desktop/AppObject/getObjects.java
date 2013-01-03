package ACL_Desktop.AppObject;

import java.awt.Point;

import lib.acl.helper.sup.TAFLogger;

import resources.ACL_Desktop.AppObject.getObjectsHelper;
import ACL_Desktop.conf.beans.ProjectConf;
import ACL_Desktop.conf.beans.TimerConf;

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

import conf.beans.FrameworkConf;

public class getObjects extends getObjectsHelper
{
	/**
	 * Script Name   : <b>getObjects</b>
	 * Generated     : <b>2012-01-10 2:44:03 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/01/10
	 * @author Steven_Xiang
	 */
	public void testMain(Object[] args) 
	{
		// TODO Insert code here
	}


	//Ribbon gui
	//Soundwave|DefineRange|UndefineRange|Group|CheckForUpdates|Preferences|TestActions

	public GuiTestObject getGuiByName(String gui) 
	{
		GuiTestObject rgui = null;
		// ACL|DefineData||Summarize_Main|ComputedColumn|
		// Notes|RowStatus|Sample|WorkPaperInsert|SidePanel|Help
		      rgui = new GuiTestObject(
                        getMappedTestObject("acl_"+gui));
		return rgui;
	}
	
	
	
	
	//buttons
	
	public boolean verifyGUI(String gui, String state){
		GuiTestObject tgui = getGuiByName(gui);		
		boolean enabled = state.equals("0")?false:true;	
		return verifyGUI(tgui,enabled);
	}
	public boolean verifyGUI(GuiTestObject tgui, boolean enabled){
		return verifyGUI(tgui,enabled,false);
	}
	public boolean verifyGUI(GuiTestObject tgui, boolean enabled,boolean isInfo){		
		String en_state = "1048576|3145730|1074790400|1048704";
		String di_state = "1048577";
		boolean guistate = false,valid = false;		
		if(tgui.exists()){
		   guistate = propertyMatch(tgui, ".state",en_state);
     	}else{
     		valid = enabled==false;
     		if(!valid&&!isInfo){
     			logTAFError("Gui '"+tgui+"' is not "+(enabled?"enabled":"disabled")+ " as expected");
     		}else{
     			logTAFInfo("Gui '"+tgui+"' is "+(enabled?"enabled":"disabled")+ " as expected");
     		}
     		return valid;
     	}
		if(guistate!=enabled&&!isInfo){
			 logTAFError("Gui '"+tgui+"' is not "+(enabled?"enabled":"disabled")+ " as expected"+"( isEnabled = "+tgui.isEnabled()+")");
		}else{
			logTAFInfo("Gui '"+tgui+"' is "+(enabled?"enabled":"disabled")+ " as expected");
		     valid = true;
		    //logTAFWarning("Gui '"+gui+"' is not "+(enabled?"enabled":"disabled")+ " as expected"+", might be an automation issue at this moment");
		    
    	}
        return valid;
	}
	

    public String getTestDataFromResultPage(String type){
    	String text = null;
    	Point actPoint;
    	if(type.matches("View")){
           //TBD - for table view verification
//    		if(acl_viewGrid().exists()){
//    			
//    			//printObjectTree(acl_viewGrid());
//    			//logTAFInfo("TestType: "+acl_viewGrid().getTestDataTypes())	;
//    			//text = getTestDataFromGrid(acl_viewGrid());
//    		}
    	}else if(type.matches("File")){ // 
           //Do nothing ... 
    	}else if(type.matches("Print")){
           //Do nothing ... 
    	}else if(!type.equals("")){ // For type 'Log' and possible 'Screen'
    		if(type.matches("Graph")){
    			actPoint = getGuiRelativePoint(acl_DocManager(), "bottomleft", atPoint(10,-8));
    			click(acl_DocManager(),actPoint);
    			sleep(2);
    		}
    		actPoint = getGuiRelativePoint(acl_DocManager(), "top", atPoint(0,50));
    		text = getContentsBySelection(acl_DocManager(),actPoint);	  
    	}
    	
    	return text;
    }
	public getObjects(){

	}

}

