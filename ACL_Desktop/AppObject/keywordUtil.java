package ACL_Desktop.AppObject;

import ibm.loggers.control.PackageLoggingController;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import lib.acl.helper.sup.LoggerHelper;
import lib.acl.helper.sup.TAFLogger;
import lib.acl.util.FileUtil;
import lib.acl.util.NLSUtil;
import resources.ACL_Desktop.AppObject.keywordUtilHelper;
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

public class keywordUtil extends keywordUtilHelper
{
	private static final String GuiSubitmeTestObject = null;
	/**
	 * Script Name   : <b>keywordUtil</b>
	 * Generated     : <b>2012-01-10 2:42:20 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/01/10
	 * @author Steven_Xiang
	 */
	//===================================================//
	
	public static String workingProject = "";
	public static String projPath="",workingDir="";
	public static getObjects gObj = new getObjects();
	public static dialogUtil dLog = new dialogUtil();
	public static aclRoutines aRou = new aclRoutines();
	public static aclTableTabs aTabs = new aclTableTabs();
	public String[] propArray,valueArray,methodArray;
	public static String appClass = "ACLMainFrame";
	public static String appTitle = LoggerHelper.autTitle;
	public void setupMemusage(){

		waitForExistence(ACL9window());		
		if(app==null||!app.isAlive()){
			app = ACL9window().getProcess();
		}
		if(mt!=null){
			mt.setup(currentkeyword,imageName, ""+app.getProcessId(), TAFLogger.memusageCSV, FrameworkConf.timeIntervalForMemusage);
		}
	}
	public boolean isActivated(){
	   return isActivated(appTitle,appClass,false);
	}
	public boolean isActivated(boolean isInfo){
		   return isActivated(appTitle,appClass,isInfo);
		}
	public boolean isActivated(String winTitle,String winClass,boolean isInfo){
		boolean activated = true;
		Point treeTop ;
	      
        IWindow win = null,activeWin;
        String curWinTitle,expectedWinTitle="";
        int maxCheck = 3,numChecks = 0;

        if((win = getDialog(winTitle,winClass))!=null){
        
			try{
				expectedWinTitle = win.getText().trim();
				win.maximize();
			    win.click(atPoint(200,5));
			    win.activate();			
			    if(FrameworkConf.inBriefModel){
			    	return true;
			    }
			}catch(Exception e){
				logTAFDebug("Exception caught when trying to activate window '"+winClass+"'");
				checkACLCrash();
				curWinTitle = getActiveWinTitle();
				while(numChecks++<maxCheck
						&&!curWinTitle.equals("")
						&&!expectedWinTitle.equals("")
						){
				       if(dismissPopup("#32770",curWinTitle,"Any",isInfo,false)){	
				    	  // "",winTitle,userAction,isInfo,loop
				              curWinTitle = getActiveWinTitle();			             
				       }else{
				    	   break;
				       }
				}   
			}
			
	
			
			if(waitForExistence(ACL9window())){		
				
				try{
					app = ACL9window().getProcess();
					ACL9window().activate();
					locateTreeRoot(acl_sysTree());
					acl_sysTree().click(atIndex(0),iconPoint);
				}catch(Exception e){
					logTAFException(e);
					activated = false;
				}
			}else{				
				activated = false;		
			}

		}else{
			activated = false;
		}
		return activated;
	}
    public void activateAUT(){
       activateAUT(true);
    }
    	public void activateAUT(boolean reopenProject){	
    	if(((batchRun&&projPath.equals(""))   // Start of new batch test
    			||sysExceptionCaught
    			||app==null
    			||individualTest
    			||!isActivated(null,appClass,true))// AUT not activated
             ){ 
//    		if(app!=null)
//    		   logTAFWarning("!RFT failed to detect the AUT "+appClass+" or caught a sys exception/fatal error, " +
//		       		"\t\twe will try to restart the application");
    		
    		if(ProjectConf.singleInstance&&ACL9window().exists()){ // Stress test - memory and performance
    			return;
    		}
    		
    		 startApp(); 
    		
    		 if(!projPath.equals("")&&sysExceptionCaught&&reopenProject){
    			 sysExceptionCaught = false;
    		       logTAFStep("Reopen the project:"+
    		       		"\t\t"+projPath);   		   
    		       reopenProject(); 
    		 }else{
    			 sysExceptionCaught = false;
    		 }
    		 
    	}else{
    		ACL9window().maximize();
    		setupMemusage(); 
    	}
    }
    
    
    public void startApp(){
    	String source = ProjectConf.workbookBackupDir,
    	       dest = ProjectConf.AUT;
    	int numStart = 0,maxStartTime = TimerConf.launchWaitTime;
    	boolean started = false;
		dLog.currentSetting = "Unknown";
		dLog.safety = true; // In order to make sure to set safety off if needed
		dLog.overflow = true;

		try{
			// Copy prf file - moved to ProjectConf - Steven
//			FileUtil.copyDir(source+"acl9.prf", dest);
//			FileUtil.copyDir(source+"acl9.prf", ProjectConf.workbookDir);
//			FileUtil.regsvr32("ACLServer.dll",dest);
			
			//app = startApp(ProjectConf.appName);
			logTAFStep("Starting ACL ...'"+ProjectConf.appName+"'");
			//logTAFInfo(""+ProjectConf.startComm+"");
			while(!started&&numStart++<2){
				app = startApp(ProjectConf.appName,ProjectConf.startComm);	
				while(!started&&maxStartTime>0){
				    sleep(3);
				    maxStartTime -= 3;
				    started = ACL9window().exists();
				}
			}
			//logTAFInfo(""+ProjectConf.startComm+"");
			if(ACL9window().exists()){
				dismissPopup("OK");
				setupMemusage(); 				
				logTAFStep("Maximize ACL Window");
				ACL9window().maximize();
				
				app = ACL9window().getProcess();

				dLog.aclDefaultSetting();
				Point rside = getGuiRelativePoint(acl_sysTree(),"right",new Point(2,0));
				acl_sysTree().drag(rside,
						new Point(300,rside.y));
				
				locateTreeRoot(acl_sysTree());
				acl_sysTree().click(atIndex(0),iconPoint);
			}
			}catch(Exception e){
				logTAFException(e);
			}

    }
    
    public void restoreFactorySettings(){
    	
    }
    
    // Overloaded openProject
    public String openProject(String projName){
      return openProject(projName,"File->OpenProject","OpenNew");
    }
    
    public String openProject(String projName,String option){
    	String dpMenuItem = "File->OpenProject";

        return openProject(projName,dpMenuItem,option);
      }
    public String closeProject(){
        return closeProject(""); 
    }
    public String closeProject(String projName){
    	String option = "Override";
        return closeProject(projName,option); 
    }
    public String closeProject(String projName, String option){
    	String dpMenuItem = "File->CloseProject";

        return closeProject(projName,dpMenuItem,option); 
    }
    public String closeProject(String projName, String dpMenuItem,String option){
        String pname = "";
        
		aRou.showNavigator("");		
		// Check if project exists - index 0
		pname = aRou.getProperties(0,"Name");
		if(!pname.equalsIgnoreCase("Unknown")){	
			if(pname.equalsIgnoreCase(pname)||projName==""){
			logTAFInfo("Close Project '"+pname+"'");
			invokeMenuCommand(dpMenuItem);
			dLog.dismissPopup("Any",true,true);   
	        // handle option.. TBI
			}else{
				logWarning("Project '"+projName+"' is not running, current project is '"+pname+"'");
			}
		}else{
			logTAFInfo("There is no project opened currently");
		}
		aTabs.clear();		
		return pname;
    }
    
    //public static boolean onRecovery = true; // For the use of open project
    public String openProject(String projName, String option,boolean isRunScript){
    	String dpMenuItem = "File->OpenProject";
        return openProject(projName,dpMenuItem,option,isRunScript);
 }
    public String openProject(String projName, String dpMenuItem,String option){
           boolean isRunScript = false;
           return openProject(projName,dpMenuItem,option,isRunScript);
    }
    public String openProject(String projName, String dpMenuItem,String option,boolean isRunScript){
    	String dirForUserScript = "DesktopScript/UserTempScript/";
    	String source = ProjectConf.workbookBackupDir,
	       dest = ProjectConf.workbookDir;
    	boolean isUserProject = false;
    	
        
    	String defaultExt = ".ACL";
    	String warningOp = "Working";
    	String fullName;
    	String curProj;
    	
    	if(projName.equals("")){   
    		if(LoggerHelper.projName.equals("")
    				||LoggerHelper.projName.equals(ProjectConf.projectName)){
    			workingProject = "";
    			workingDir = "";
    			projPath = "";	
    			LoggerHelper.projName ="";
    			return "";
    		}
    		if(aRou.getProperties(0,"Name").equalsIgnoreCase("Unknown")&&
    				!projPath.equals("")){
    		   projName = projPath;
    		}else if(!option.equalsIgnoreCase("OpenRecent")){
    		   return workingProject;
    		}
    	}
    	if(!projName.equals("")){  
    		if(projName.matches(".+\\..+$")){//if(!projName.matches(".+\\.[Aa][Cc][Ll]$")){    		
    		   defaultExt = "";    		
    	   }
    		try{
    		  File fl = new File(projName);
    		  String subDest="";
    		  if(!fl.isAbsolute()){
    			  subDest = fl.getParent();
    			  if(!subDest.equals("")){
    			      dest += subDest+"\\";
    			      source += subDest+"\\";
    			  }
    			  projName = fl.getName();
    		  }
    		}catch(Exception e){
    			//
    		}
    	}

    	boolean openedNew = false;
    	String projFullName = projName+defaultExt;
    	
    	fullName = FileUtil.getAbsDir(projFullName,source);
    	
    	if(fullName.equalsIgnoreCase(projFullName)){
    		isUserProject = true;
    		dest += dirForUserScript;
    		source = new File(fullName).getParent();
    	}
    	
    	projName = new File(fullName).getName().replaceAll("\\.[Aa][cC].?$", "");
    	
    	
       	if(option.equals("")&&!projName.equals("")){
       		curProj = aRou.getProperties(0,"Name").replaceAll("\\.[Aa][cC].?$", "").replace("Unknown", "");
       		
    		if(!projName.equalsIgnoreCase(curProj)){
    		           logTAFWarning("Project '"+projName+"' has not been opened yet");
    		           logTAFWarning("    - Current Project is '"+curProj+"'");
    		           closeProject();
    		           option = "Open";
    		}
    	}else if(option.endsWith("New")){
    		// copy from backup
    		// Close any running project
    		
    		//logTAFStep("Close current running project '"+"'");
    		closeProject();
    		logTAFStep("Delete all possible '"+projName+"' files from working dir: "+dest);
    		//FileUtil.delFile(dest+projFullName);
    		FileUtil.delFile(dest+projName+".ACL");
    		FileUtil.delFile(dest+projName+".AC");
    		FileUtil.delFile(dest+projName+".LIX");
    		FileUtil.delFile(dest+projName+".LOG");
    		
    		//FileUtil.mkDirs(dest);
    		
    		if(option.equalsIgnoreCase("OpenNew")){  
                String locFile = fullName;
    			if(new File(source+projName+".AC").exists())
    			       FileUtil.delFile(source+projName+".AC");
    			
//**** For oldbats temp only (Update from tests's folder dynamically)---- Steven *****
    			String oldbats = "OLDBATSClient";
    			String i18nbats = "OldBats_i18n";
//				String updateFolder = "\\\\192.168.10.129\\aclqa\\Working\\ACL Projects Test Cases\\Desktop\\ACL Projects"+
//                "\\Scripts To test 9.3 features\\Oldbats_New_Modified_6_11_2012\\";
				String updateFolder = "\\\\Biollante02\\Batches\\";
				String pdfFiles = "pdfFiles";
				if(new File(updateFolder).exists()){
					if(projName.equalsIgnoreCase(oldbats)){
						String oriProject = "OldBats_U";                    
						//Orig To Source/Dest
						String toDir = dest;
						if(!ProjectConf.unicodeTest){ // update NonUnicode project
							oriProject = "OldBats_NU";
							logTAFInfo("Warning: Updating "+projName+" from: '"+updateFolder+oriProject+"'");
							FileUtil.copyDir(updateFolder+oriProject, toDir);
							FileUtil.copyFile(updateFolder+oriProject+"\\"+oriProject+".acl", toDir+projName+".ACL");
							FileUtil.updateDir(updateFolder+pdfFiles, toDir+"..\\"+pdfFiles);    				
						}else{                       // update Unicode project
							//    					logTAFInfo("Warning: Updating "+projName+" from: '"+updateFolder+oriProject+"'");
							//    					FileUtil.copyDir(updateFolder+oriProject, toDir);
							//    					FileUtil.copyFile(updateFolder+oriProject+"\\"+oriProject+".acl", toDir+projName+".ACL");
							//    					FileUtil.updateDir(updateFolder+pdfFiles, toDir+"..\\"+pdfFiles);    					
						}

						//Source To Dest
						if(!toDir.equals(dest)){
							logTAFInfo("Warning: Updating "+projName+" from backup: '"+source+"'");
							FileUtil.copyDir(source, dest);
							FileUtil.updateDir(source+"..\\"+pdfFiles, dest+"..\\"+pdfFiles);
						}
						FileUtil.makeWriteable(dest);

					}else if(projName.equalsIgnoreCase(i18nbats)){
						//String oriProject = "OldBats_i18n";
						String oriProject = "OldBats_U";    				
						//Orig To Source/Dest
						String toDir = dest;
						logTAFInfo("Warning: Updating "+projName+" from: '"+updateFolder+oriProject+"'");
						FileUtil.copyDir(updateFolder+oriProject, toDir);
						//FileUtil.updateDir(updateFolder+oriProject, toDir);
						FileUtil.updateDir(updateFolder+pdfFiles, toDir+"..\\"+pdfFiles);

						//Source To Dest
						if(!toDir.equals(dest)){
							logTAFInfo("Warning: Updating "+projName+" from backup: '"+source+"'");
							//FileUtil.copyFile(source+"..\\"+projName+"_PROFFMTUPDATE.acl", source+projName+".ACL");

							FileUtil.copyDir(source, dest);
							FileUtil.updateDir(source+"..\\"+pdfFiles, dest+"..\\"+pdfFiles);
						}
						FileUtil.makeWriteable(dest);
					}
				}
// ******************************************** Finish temp update ***********************
    			
    			
    	        if(isRunScript){
    	        	locFile = localizeACLScript(source+projName);
    	        	//FileUtil.copyFile(file+"_"+ProjectConf.appLocale+".ACL",file)
    	        	//return "";
    	        }
    	        
        	    if(isUserProject){//||isRunScript){        	    	
        	    	if(new File(source).exists()){
        	    		//Copy everything from that dir
            	      logTAFStep("Update files in '"+dest+"' with : '"+source+"'");   
            	      FileUtil.updateDir(source,dest);
        	    	  projFullName = projName+".ACL";
        	    	  FileUtil.makeWriteable(dest);
        	    	}
        	    }else if(isRunScript){ 
//        	    	 logTAFStep("Copy files in '"+source+"' to working dir: '"+dest+"'");   
//           	         FileUtil.updateDir(source,dest);
//       	    	     FileUtil.makeWriteable(dest);
        	    }else{
        	    	//Copy project only
        	    	logTAFStep("Copy '"+fullName+" 'to working dir: "+dest);       	    
        	    	//FileUtil.copyDir(fullName, dest);    
        	    	//FileUtil.copyFile(locFile, dest+projName+".ACL");  
        	    }
        	    FileUtil.copyFile(locFile, dest+projName+".ACL");  
    		}
    	}
    	
    	//workingProject = projName;
    	
    	projPath = FileUtil.getAbsDir(projFullName,dest);
    	
//    	String chmod = "ATTRIB -r -s -a -h "+dest+".\\*.* /D /S";
//        FileUtil.exeComm(chmod);
    	
    	

    	if(option.equals("")&&!projName.equals("")){
        	// Do nothing, opened already
    	}else if(option.equalsIgnoreCase("CreateNew")){
        	FileUtil.mkDirs(projPath);
        	invokeMenuCommand("File->New->NewProject");
      	    dLog.fileChooser(projPath,"Save",true);

//      	   if(option.matches("OpenLastSaved")){
//      		   warningOp = "Last-saved";
//      	    }else if(option.matches("OpenCancel")){
//      		   warningOp = "Cancel";
//      	    }else if(option.matches("OpenWorking")){
//      		   warningOp = "Working";
//      	    }
    	}else if(!option.equalsIgnoreCase("OpenRecent")){ // File->OpenProject
    		//option = "Open"; // 
    		invokeMenuCommand(dpMenuItem);
    		openedNew = dLog.fileChooser(projPath,"Open",true);
    		// Use dynamic search
    		if(option.matches("OpenLastSaved")){
    			warningOp = "Last-saved";
    		}else if(option.matches("OpenCancel")){
    			warningOp = "Cancel";
    		}else if(option.matches("OpenWorking")){
    			warningOp = "Working";
    		}
    	}else{ //Open Recent
    		String rcName = projPath;
    		int maxChars = 95;
    		if(projPath.length()>maxChars){
    			rcName = projPath.substring(0,maxChars);//to handle index 1-99
    		    logTAFWarning("Project name is too long,may not be correctly located from Recent Projects");
    		    
    		}
    		logTAFInfo("Open Recent Project '"+rcName+"'...");
    		invokeMenuCommand("File->RecentProjects");
    		propArray = ".class,.text".split(",");
    		//valueArray = (".Menuitem,[1-9][0-9]?."+rcName.replaceAll("\\", "\\\\")).split(",");
    		valueArray = (".Menuitem,"+rcName).split(",");
    		methodArray = "equals,contains".split(",");
    		//TestObject getTestObject(TestObject topObject,String[] propertyArray, String[] valueArray)
    		TestObject rcp = getTestObject(acl_RPCMenu(),propArray,valueArray,methodArray);
    		clickOnObjectSafely((GuiTestObject) rcp,rcName);
    		openedNew = true;
    		
    	} 
    	
         if(openedNew){
        	 // Clean tabs in container
        	 aTabs.clear();
        	 
        	 sleep(2);
        	 if(!LoggerHelper.onRecovery){
        	    dismissPopup("OK|"+warningOp,LoggerHelper.onRecovery);
        	    //dLog.confirmWarning(warningOp,onRecovery); //if process was killed, onRecovery should be false 
        	 }else{
        		 dismissPopup("Yes|"+warningOp,true);     
               //dismissPopup("Yes|"+warningOp,true,true,3);         	   
        	 }
         	dismissPopup("OK",false);
         	
         	if(aclVersionUpdate){
                // copy back to backup -- due to possible version change -- Steven.         		
                FileUtil.copyFile(dest+projName+".AC", source+projName+".ACL");
                FileUtil.copyFile(dest+projName+".ACL.OLD", source+projName+".ACL.OLD");
                aclVersionUpdate = false;
        	}
            closeServerActivity(false);
            isActivated(true);            
            //checkACLCrash();

        }
         if(projName.equalsIgnoreCase("")){
        	 projName = aRou.getProperties(0,"Name").replaceAll("\\.[Aa][cC].?$", "").replace("Unknown", "");
         }
         workingProject = projName; 
         LoggerHelper.projName = workingProject;
         //logTAFWarning("##### Current Project: '"+workingProject+"'");
         workingDir = new File(projPath).getParent()+"\\";
         LoggerHelper.workingDir = workingDir;
         
         onRecovery = true;
    	return projName;
    }

    public boolean checkACLCrash(){
    	boolean done = false;
 	   String msg = "ACL encountered a problem and needs to close.";
       //sleep(2);
 	   if(acl_CrashWin().exists()
 			   &&acl_CrashCancel().exists()
 			   &&propertyMatch(acl_CrashWin(),".text","ACL")){
 		   logTAFError(msg);
 		   click(acl_CrashCancel(),"Cancel");
           done = true;

           startApp();
           reopenProject();
           
 	   }
 	   return done;
    }
    public void reopenProject(){
    	if(!projPath.equals("")){
        	logTAFStep("Reopen working project due to unexpected exceptions/crash");
            openProject(projPath,"OpenWorking"); 
         }else{
      	   //openProject(projPath,"OpenRecent"); 
         }
    }
    
    public boolean closeServerActivity(){
    	return closeServerActivity(true);
    }
   public boolean closeServerActivity(boolean isInfo){
	   boolean done = false;
	   int maxtime = 5,t1=0,t2=0;
	   String msg = "Server Activity window does not close automatically";
	   
	   TestObject serverActivity;
	   GuiTestObject closeBtn = null, cancelBtn =null;
	   IWindow iw;
	   String winTitle = "Server Activity";
	   String winClass = "#32770";
	   int maxWait = 60,wt =0;

	   while((serverActivity=findTopLevelWindow(winTitle))!=null
			   &&((closeBtn=findPushbutton(serverActivity,"Close"))==null)
			   && wt++<maxWait){
		   logTAFInfo("Server Activity..., wait...");
		   sleep(2);
		   
		   if(wt>=maxWait
				   &&((cancelBtn=findPushbutton(serverActivity,"Cancle"))!=null)){
			  logTAFError(autoIssue+"Connecting to Server got problem or took  too much time("+(2*maxWait)+" seconds), we will cancel the Server Activity now");
			   click(cancelBtn,"Cancel");
			   sleep(5);
			   if((serverActivity=findTopLevelWindow(winTitle))!=null
					   &&((closeBtn=findPushbutton(serverActivity,"Close"))!=null)){
				   if(isInfo)
			   		      logTAFWarning(msg);
					   else{
						   logTAFError(msg);
					   }
				   click(closeBtn,"Close",true);
				   done = true;
				   closeBtn = null;
				   sleep(2);
				   if((iw = getDialog(winTitle,winClass))!=null){
					   logTAFError("Failed to close Server Activity window by pressing 'Close'");
					   try{
					        iw.close();
					   }catch(Exception e){
						   
					   }
				   }
			   }  
		   }
	   }
	   
	   if(wt>=maxWait&&serverActivity!=null&&(closeBtn=findPushbutton(serverActivity,"Close"))!=null){
		   click(closeBtn,"Close",true);
		   if(isInfo)
	   		      logTAFWarning("Failed to connect to server");
			   else{
				   logTAFError("Failed to connect to server");
			   }
	   }
	   if(checkACLCrash())
		   return false;
	   	   
	   return done;
   }
   
   public String saveProjectAs(String projName,boolean isInfo){
      return saveProjectAs(projName,isInfo,true);
   }
   public String saveProjectAs(String projName,boolean isInfo,
		                        boolean delFile){
    	
    	String source = ProjectConf.workbookBackupDir,
	       dest = ProjectConf.workbookDir;
    	String defaultExt = ".ACL";
    	String warningOp = "OpenWorking";
    	String fullName;
    	if(projName.equals("")){   
    		return workingProject;
    	}else  if(projName.matches(".+\\..+$")){//if(!projName.matches(".+\\.[Aa][Cc][Ll]$")){    		
    		defaultExt = "";    		
    	}
    	
    	String projFullName = FileUtil.getAbsDir(projName+defaultExt,dest);
        
    	if(delFile==true){
    	      FileUtil.delFile(projFullName);
    	}
    	    	
    	   invokeMenuCommand("File->SaveProjectAs");
    	   dLog.fileChooser(projFullName,"Save",true);
    	   sleep(2);
    	   if(dismissPopup("Yes|OK",isInfo)){
    	      sleep(1);
    	   }

//    	   if(dismissPopup("OK",true)){ // for info: All previously saved changes will be copied 
//    	       sleep(1);
//    	   }
    	   if(dismissPopup("Network Error","Cancel",false)){ 
    		  sleep(1);//Win7 Network Error
    	   }
    	   if(dismissPopup("Cancel",true)){
    		   logTAFWarning("Save project as has been canceled due to errors detected");
    	   }
    	   //dLog.confirmWarning(isInfo?"No":"Yes",isInfo);
    	   //dLog.confirmWarning("OK",true);
    	
    	return projFullName;
    }
 
   public boolean invokeMenuCommand(String commands){
      return invokeMenuCommand(commands,true);
   }
    public boolean invokeMenuCommand(String commands,boolean showNavigator){
    	boolean done = true;
    	
    	if(commands.matches("%*|^*")){
   	       inputShortcut(commands);
   	       return done;
      	}
    	//click(ACL9window());
    	if(showNavigator)
    	   aRou.showNavigator();
    	String[] command = commands.split("->");
		GuiTestObject item = null,preItem = null;

		for(int i=0;i<command.length;i++){
		   logTAFDebug("ACL command : '"+(command[i])+"'");
		   item = gObj.getGuiByName(command[i]);   
		   
		   if(item!=null){			  
		      //clickOnObjectSafely(item,command[i]);
			   //logTAFDebug(command[i]+" State: "+item.getProperty(".state").toString());
			   if(propertyMatch(item,".state","1")){ // ??? should be 133, enable = 132				   
				   if(preItem!=null)
					   click(preItem,command[i-1]);
		           done = false;
		           break;
			   }else{
		           click(item,command[i]);
		           
		           preItem = item;
			   }
		   }else{
			   done = false;
			   logTAFError(command[i] + " not available? ");
			   break;
		   }
		   //click(item,command[i]);
		   
		}
    	return done;
    }

    public void getItemList(String lines,ArrayList<String> list){
    	String[] items = lines.split("\n");
    	list.clear();
    	for(int i=0;i<items.length;i++){
          list.add(items[i]); 
    	}
    	
    }
    public void inputShortcut(String sc){
    	Point acp = new Point(2,5);
    	acl_MenuBar().doubleClick(acp);
    	sleep(2);
    	getScreen().getActiveWindow().inputKeys(sc);
    }
	public static String replaceSpecialChars(String name){
        
		String chars = "[/\\\\:*?,|\\-\\s]";
        String subs = "_";
                   //name = " "+name+" "+chars+name+"   ";
        String rname = name;
        
        if(name==null)
        	return rname;
        try{
            rname = name.replaceAll(chars, subs);
        }catch(Exception e){
        	logTAFDebug(e.toString())	;
        }
        if(!rname.equals(name)){
        	logTAFInfo("This name should be converted from '"+name+"' to '"+rname+"' by app");
        }else{
        	logTAFDebug("This name for ACL is valid? - '"+name+"' ");
        }
		return rname;
	}
	
	public String localizeACLScript(String file){
		// To make it work completely, we need to modify those fmt layouts 
		// for the purpose of verification - Steven.
		
		String script = "";
		String encoding = "windows-1252";//"ASCII";
		String locFile = file+"_"+ProjectConf.appLocale;
		
		if(ProjectConf.appLocale.equalsIgnoreCase("En")){
			return file+".ACL";
		}
		if(!ProjectConf.updateMasterFile&&new File(locFile).exists())
			return locFile;
		
		String lineSep = getSystemProperty("line.separator");
		String[] reps = {
				"(?i)\\s*SET\\s+FILTER.+","(?i)(RELCROSS[\\.]??)(Count)(_)",
				//"\"Missing number\"","\"|Missing|number|\"",
				//"(?i)\\s*[TF]\\s*IF\\s*TEST.+","(?i)(\\([\\d]+,\"\\s*)([*]*\\s*[a-zA-Z].+?)(\"\\))"
				"(?i)\\s*[TF]\\s*IF\\s*.+","(?i)(\\([\\d]+,\"\\s*)([*]*\\s*[a-zA-Z].+?)(\"\\))"
		};
		if(ProjectConf.appLocale.matches("(?i)pl")){
			encoding = "windows-1250";
		}
	    if(ProjectConf.isUnicodeTest()){
	    	encoding = "UTF-16LE";
	    }
	    FileUtil.copyFile(file,file+"_en.ACL");
		try {
			FileInputStream fstream = new FileInputStream(file);
			
			BufferedReader in =
				new BufferedReader(new InputStreamReader(fstream,encoding));
			String line = "";
     
			while (((line = in.readLine()) != null)) {
                for(int i=0;i<reps.length-1;i=i+2){
                	if(line.matches(reps[i])){
                		//System.out.println("Original line: "+line);
                		line = replaceLocAll(reps[i+1],line);
                		//System.out.println("Translat line: "+line);
                		
                	}
                }
				script += line + lineSep ;
			}
			in.close();
			//System.out.println("File: '"+numLines+" lines - "+file);
		} catch (IOException e) {
			PackageLoggingController.logPackageError(PackageLoggingController.PACKAGELOGLEVEL_ERRORS_ONLY, "Error in FileOps#readFile: " + e.getMessage());           
		}
		
		FileUtil.writeFileContents(locFile, script,encoding);
		//FileUtil.copyFile(file+"_"+ProjectConf.appLocale+".ACL",file);
		return locFile;
	}
    public keywordUtil(){
    }
    
    public static void main(String args[]){
    	//localizeACLScript(projPath);
    }
}

