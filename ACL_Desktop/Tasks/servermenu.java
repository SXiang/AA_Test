package ACL_Desktop.Tasks;

import java.awt.Point;
import java.util.ArrayList;

import lib.acl.util.FileUtil;
import resources.ACL_Desktop.Tasks.servermenuHelper;
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
import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;

public class servermenu extends servermenuHelper
{
	/**
	 * Script Name   : <b>servermenu</b>
	 * Generated     : <b>2012-01-12 11:09:40 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/01/12
	 * @author Steven_Xiang
	 */
	
    private Object[] options = {null,null,null,State.selected(),null,null};
    private ArrayList<String> profiles = new ArrayList<String>();
    
	// BEGIN of datapool variables declaration
   
    private String dpCommand;   //@arg test category 
    //@value = 'New'|Mofify'|'Verify|Delete|Connect|Disconnect' - For ServerProfile
    private String dpEndWith; //@arg the last action for current dialog
     //@value = 'Connect'|'Save'|'Delete|Cancel|Disconnect' - For ServerProfile
	private String dpProfileName; //@arg name of Profile to be tested/created/modified/verified
	private String dpHostName; //@arg name of Profile to be tested/created/modified/verified
	                           //@value = 'Automation_[Server]' is used for positive tests, actual value would be the IP address from the conf properties.
	private String dpCopyFrom; //@arg the name of profile to be copied to this new profile
	private String dpServerType; //@arg Server Type
	                             //@value = 'Windows'|'z/OS', default to Window
	private String dpUserID; //@arg User ID for the server connection
	private String dpPassword; //@arg Password for the server connection
	private String dpPrefix; //@arg Prefix for the server connection
	private String dpPort; //@arg Port number for the server connection
	private String dpEncryption; //@arg Encryption 1|0 for the server connection
	private String dpCompression; //@arg Compression 1|0 for the server connection
	private String dpEnableIMS; //@arg Enable IMS 1|0 for the server connection
	private String dpEnableSSO; //@arg Enable SSO 1|0 for the server connection
    // END of datapool variables declaration

	
	@Override
	public boolean dataInitialization() {
		boolean done= true;
		
		defaultMenu = "Server";
		dpCommand = getDpString("Command");
	    dpEndWith = getDpString("EndWith");	  
	    dpProfileName = getDpString("ProfileName");
		dpHostName = getDpString("HostName");  
		   if(dpHostName.startsWith("Automation_")){
			   if(ProjectConf.unicodeTest){
				   dpHostName = ProjectConf.unicodeServerIP;
			   }else{
				   dpHostName = ProjectConf.nonUnicodeServerIP;
			   }
	       }
		dpCopyFrom = getDpString("CopyFrom");
		dpServerType = getDpString("ServerType");    
		dpUserID = getDpString("UserID");    
		dpPassword = getDpString("Password");    
		dpPrefix = getDpString("Prefix");    
		dpPort = getDpString("Port");    
		dpEncryption = getDpString("Encryption");    
		dpCompression = getDpString("Compression");    
		dpEnableIMS = getDpString("EnableIMS");    
		dpEnableSSO = getDpString("EnableSSO");     
		return done;
	}

	public void testMain(Object[] args) 
	{
		super.testMain(args);
		
		//Steps:
		//@Step Activate ACLWin
		//@Step Configure Options - Use Factory setting
        //@Step Click Server-'sub Command' [Such as Server Profile|...]
		//@Step Click 'New' if dpCommand = New
		//@@Step Specify Profile Name and possible Copy From, Then 'OK'
		//@@Step If any Error, Click 'Cancel' and log an error
		//@@Step Fill in the the profiles
		//@Step if dpCommand = Modify or Verify, Select the profile
		//@@Step Modify|Verify options, and then Save
		//@Step if dpCommand = Delete, Click 'Delete' and confirm the deletion
		//@Step if dpCommand = Connect, Click 'Connect' and verify the status of the connection.
		//@Step Verify Gui status of ‘Save','Delete' and 'Connect'
        //@Step Close Server Profiles.		
          
         dLog.aclOptions("Interface","OK",options );
		
		if(menuItem.equals("ServerProfiles")){
			kUtil.invokeMenuCommand(dpMenuItem);
			kUtil.getItemList(sp_profilename().getText(), profiles);
			logTAFInfo("Current Server Profiles: {"+profiles+"}");
	        exeServerProfile();
//		}else if(subCommand.equalsIgnoreCase("DatabaseProfile")){
//		     exeDatabaseProfile();
//		}else if(subCommand.equalsIgnoreCase("Disconnect")){
//		     exeDisconnect();
		}else if(menuItem.equalsIgnoreCase("Connect")){
			 kUtil.invokeMenuCommand(dpMenuItem);
		     exeServerProfile();
//		}else if(subCommand.equalsIgnoreCase("ActivityLog")){
//		     exeActivityLog();
//		}else if(subCommand.equalsIgnoreCase("AXCoreClient")){
//		     exeAXCoreClient();
		}else{
			logTAFError("Command '"+menuItem+"' is invalid for this test");
		}
		
		kUtil.getItemList(sp_profilename().getText(), profiles);
		logTAFInfo("Current Server Profiles: {"+profiles+"}");
		if(!profiles.isEmpty()){
			//verifyGuiState(false,true,true);
		}		
		click(sp_close(),"Close");
		dLog.confirmAction("No");
	}


	public void exeServerProfile() {
         
		if(dpCommand.equalsIgnoreCase("New")){
			addNewServerProfile(dpProfileName,dpCopyFrom);
		}else if(dpCommand.equalsIgnoreCase("Verify")){
			logTAFStep("Select Profile Name: '"+dpProfileName+"'");
			sp_profilename().select(dpProfileName);
			doServerProfile("Verify");
			//verifyGuiState(false,true,true);
		}else if(dpCommand.equalsIgnoreCase("Modify")){
	    	logTAFStep("Select Profile Name: '"+dpProfileName+"'");
			sp_profilename().select(dpProfileName);
			doServerProfile("Modify");
			//modifyServerProfile();
		}else if(dpCommand.equalsIgnoreCase("Delete")){
			deleteServerProfile(dpProfileName);
		}else if(dpCommand.equalsIgnoreCase("Connect")){
			connectServerProfile(dpProfileName);
		}else {
			logTAFError("Command '"+dpCommand+"' is available in Server Profile tests");
		}
		
	}
	public void addNewServerProfile(String profileName){

       addNewServerProfile(profileName,"");
	}
	public void addNewServerProfile(String profileName,String copyFrom){		   
		   String command = "New";
	       if(profiles.contains(profileName)){
	    	   if(dpExpectedErr.equals("")){
	    		   //if(!dpEndWith.equals("Cancel")){
	    	     logWarning("Delete Server profile '"+profileName+"' before adding it as a new profile");
	    	     deleteServerProfile(profileName);
	    	   }
	       }
	   
		click(sp_new(),"New");
		TestObject addNew = findTopLevelWindow("Add New Profile");   

		//sp_add_profilename().waitForExistence();
		TextGuiTestObject sp_add_profilename= findEditbox(addNew,true,0);
		logTAFStep("Input profile name: '"+profileName+"'");
		setText(sp_add_profilename,profileName);
		if(!copyFrom.equals("")){
			logTAFStep("Select copy from '"+copyFrom+"'");
			sp_add_copyfrom().select(copyFrom);
			command = "Copy";
		}
		if(dpEndWith.equals("Cancel")){
			click(sp_add_cancel(),"Cancel");
			verifyGuiState(false,true,true);
			return;
		}else{
		 click(sp_add_ok(),"OK");
		 sleep(2);
		 if(sp_info_win().exists()){
			 //logTAFError("ACL info: '"+sp_info_win().getProperty(".text")+"'");
			 logTAFError(sp_info_win().getProperty(".text").toString());
			 click(sp_info_ok(),"OK");
			 click(sp_add_cancel(),"Cancel");
			 //verifyGuiState(false,true,true);
			 return;
		 }
		}
		doServerProfile(command);
		//modifyServerProfile();

	}
    public void doServerProfile(String action){
    	if(action.matches("New|Modify|Verify")){
    		//logTAFInfo("Set Server Type: '"+dpServerType+"'");
    		//sp_servertype().select(dpServerType);   	
    		actionOnSelect(sp_servertype(), "Set Server Type",dpServerType, action);
    		if(dpServerType.equals("Windows")){
    			//logTAFInfo("Enable SSO: '"+dpEnableSSO+"'");
    			//sp_sso().clickToState(dpEnableSSO.equals("1")?SELECTED:NOT_SELECTED);
    			actionOnCheckbox(sp_sso(), "Enable SSO",dpEnableSSO.equals("1")?true:false, action);
    		}
    		//   logTAFInfo("Set User ID: '"+dpUserID+"'");
    		//setText(sp_userid(),dpUserID);
    		actionOnText(sp_userid(), "User ID",dpUserID, action);
    		//logTAFInfo("Set Password: '"+dpPassword+"'");
    		//setText(sp_password(),dpPassword);
    		actionOnText(sp_password(), "Password",dpPassword, action);
    		//logTAFInfo("Set Prefix: '"+dpServerType+"'");
    		//setText(sp_prefix(),dpPrefix);
    		actionOnText(sp_prefix(), "Prefix",dpPrefix, action);
    		//logTAFInfo("Set Host Name: '"+dpHostName+"'");
    		//setText(sp_hostname(),dpHostName);
    		actionOnText(sp_hostname(), "Host Name",dpHostName, action);
    		//logTAFInfo("Set Port: '"+dpPort+"'");
    		//sp_port().setText(dpPort);
    		actionOnText(sp_port(), "Port",dpPort, action);
    		//logTAFInfo("Enable Encryption: '"+dpEncryption+"'");
    		//sp_encryption().clickToState(dpEncryption.equals("1")?SELECTED:NOT_SELECTED);
    		actionOnCheckbox(sp_encryption(), "Enable Encryption",dpEncryption.equals("1")?true:false, action);
    		//logTAFInfo("Enable Compression: '"+dpCompression+"'");
    		//sp_compression().clickToState(dpCompression.equals("1")?SELECTED:NOT_SELECTED);
    		actionOnCheckbox(sp_compression(), "Enable Compression",dpCompression.equals("1")?true:false, action);
    		if(dpServerType.equals("z/OS")){
    			//logTAFInfo("Enable IMS: '"+dpEnableIMS+"'");
    			//sp_ims().clickToState(dpEnableIMS.equals("1")?SELECTED:NOT_SELECTED);
    			actionOnCheckbox(sp_ims(), "Enable IMS",dpEnableIMS.equals("1")?true:false, action);
    		}
    	}
		if(gObj.verifyGUI(sp_save(),true,true)){
			click(sp_save(),"Save");

			if(!sp_password().getText().equals(""))
				dLog.confirmWarning("OK");
			if(confirmACLError(false))
				return;
			sleep(2);
		}
		
	    if(dpEndWith.equalsIgnoreCase("Delete")){
			deleteServerProfile(dpProfileName);
		}else if(dpEndWith.equalsIgnoreCase("Connect")){
			connectServerProfile(dpProfileName);
		}else if(dpEndWith.equalsIgnoreCase("Cancel")){
			//verifyGuiState(false,true,true);
		}

		
	}
    
    public void deleteServerProfile(String profileName){
    	String act = "OK";
    	logTAFStep("Select Profile Name: '"+profileName+"'");
		sp_profilename().select(profileName);
		sleep(2);
		if(sp_disconnect().exists()){
		  click(sp_disconnect(),"Disconnect");
		}
		click(sp_delete(),"Delete");
		if(dpEndWith.equals("Cancel")){
			act = "Cancel";
		}
		if(!dLog.confirmAction(act)){
			logTAFError("Failed to comfirm the deletion");
		}
	}
    public void connectServerProfile(String profileName){
    	String cStatus = "";
    	String winTitle = "Server Activity",
    	       className = "#32770";
    	IWindow iw = null;
    	logTAFStep("Select Profile Name: '"+profileName+"'");
		sp_profilename().select(profileName);
		if(sp_disconnect().exists()){
			click(sp_disconnect(),"Disconnect");
			sleep(2);
		}
		click(sp_connect(),"Connect");
		sleep(2);
		while((iw=getDialog(winTitle,className))!=null){
			if(dLog.confirmAction("OK")){
				if((iw = findControl(iw,"Close"))!=null){
				   iw.click();
				} else{
				click(sp_activity_close(),"Close");
				}
				break;
			}
			sleep(2);
		}
		
		//cStatus = sp_connectstatus().getProperty(".name").toString();
		if(sp_disconnect().exists()){
		//if(cStatus.equalsIgnoreCase("Connected")){
			logTAFInfo("Profile '"+profileName+"' connected");
			if(dpEndWith.equalsIgnoreCase("Disconnect")){
				disConnectServerProfile(profileName);
			}
		}else{
			logTAFError("Failed to build server connection -'"+profileName+"' : "+cStatus);
		}
	}
    public void disConnectServerProfile(String profileName){
    	String cStatus = "";
    	
    	logTAFStep("Select Profile Name: '"+profileName+"'");
		sp_profilename().select(profileName);
		if(sp_connect().exists()){
			logTAFWarning("Profile '"+profileName+"' is not connected to server");
			
		}else{
		    click(sp_disconnect(),"Disconnect");
		    sleep(2);
		}
		
		//cStatus = sp_connectstatus().getProperty(".name").toString();

		if(sp_connect().exists()){
		//if(cStatus.equalsIgnoreCase("Disconnected")){
			logTAFInfo("Profile '"+profileName+"' Disconnected");
			if(dpEndWith.equalsIgnoreCase("Connect")){
				connectServerProfile(profileName);
			}
		}else{
			logTAFError("Failed to disconnect server -'"+profileName+"' : "+cStatus);
		} 
    }
    
   public void verifyGuiState(boolean save,boolean delete, boolean connect){
	   String[] gui,guiStates;
//	    logTAFErrorInfo("Button Save is "+(save==true?"enabled":"disnabled")
//	    		,gObj.verifyGUI(sp_save(),save));
	    gObj.verifyGUI(sp_save(),save);	
	    gObj.verifyGUI(sp_delete(),delete);
	    gObj.verifyGUI(sp_connect(),connect);
	    //gObj.verifyGUI(sp_disconnect(), true);
   }
}

