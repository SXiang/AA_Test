package lib.acl.tool;
import ibm.util.NLSHlpr;

import java.awt.Point;
import java.io.IOException;

import lib.acl.util.UnicodeUtil;
import lib.acl.helper.sup.UnicodeHelper;
import lib.acl.tool.*;
import resources.lib.acl.tool.htmlRFTHandlerHelper;
import conf.beans.FrameworkConf;
import conf.beans.TimerConf;

import com.rational.test.ft.*;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.*;
import com.rational.test.ft.vp.*;

/**
 * Description   : Functional Test Script
 * @author Steven_Xiang
 */
public class htmlRFTHandler extends htmlRFTHandlerHelper
{
	/**
	 * Script Name   : <b>htmlRFTHandler</b>
	 * Generated     : <b>2010-05-14 3:41:29 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.1  Build 2600 (S)
	 * 
	 * @since  2010/05/14
	 * @author Steven_Xiang
	 */
	
	public boolean proceedOnSecurityWarning(){
		boolean done = true;
		Point leftTopPoint = atPoint(200, 10);
		
		waitForExistence(browser_htmlBrowser());
		
		while(link_overridelink().exists()){
			link_overridelink().hover(1,leftTopPoint);
			link_overridelink().click(leftTopPoint);
			logTAFStep("Click link to proceed on Certificate Error");			
			sleep(TimerConf.pageRefreshTime);
		}
		
		return handleHtmlDialog();
	}
	
	public boolean handleHtmlDialog(){
		boolean done = true;
		if(html_htmlDialo().exists()){
			if(text_usernameWIN().exists()){
				logTAFStep("Windows user authentication:");
				setText(text_usernameWIN(),userName);
			}
			if(text_passwordWIN().exists()){
				setText(text_passwordWIN(),password);
				if(dialog_htmlDialogButtonOK().exists()){
					dialog_htmlDialogButtonOK().click();
					logTAFStep("Click OK to submit");
					sleep(1);
			}

			}else{
				logTAFDebug("Html dialog found ... ");
			//	html_htmlDialo().close();
			//	logTAFStep("Unknown dialog, click X to close it");
			//	sleep(1);
			}
				
		}		
		return done;
	}
	public boolean casSSO(String username, String password){
		boolean done = true;
		TestObject msg;
		String error="";
		
		waitForExistence(browser_htmlBrowser());
		
		if(button_loginsubmit().exists()){
			logTAFStep("CAS user authentication:");

			inputUnicodeChars(text_passwordSSO(),password);

			setText(text_usernameSSO(),username);		        
//			htmlMenuPaste(text_passwordSSO(),password);
//
//			htmlMenuPaste(text_usernameSSO(),username);					
			sleep(1);
			button_loginsubmit().click();
			logTAFStep("Click 'Login' to submit ");
			sleep(TimerConf.pageRefreshTime);
			
		    if(html_status().exists()){
		    	msg = html_status().find(atDescendant(".class","Html.TextNode"),false)[0];
		    	error = msg.getProperty(".text").toString();
		    	logTAFError("Login failed: '"+error+"'");
		    	done = false;
			}else{
				logTAFInfo("CAS - SSO succeeds without any error messages");	
			}
		}

		return done;
	}
	
	public void appLogin(TextGuiTestObject userBox,TextGuiTestObject passBox,GuiTestObject submit,String user,String pass){
		
		browser_htmlBrowser().waitForExistence(TimerConf.maxWaitTime,TimerConf.waitBetweenRetry);			

		if(browser_htmlBrowser().exists()){
				if(userBox.exists()){
//					if(userName.equals("Unknown")||userName.equals("")){
//						logTAFWarning("The username is '"+userName+"' which is not a valid username, a default user '"
//								+ProjectConf.adminUser+"' will be used instead");
//						userName = ProjectConf.adminUser;
//					}
                    inputChars(browser_htmlBrowser(),userBox, userName);
                    sleep(1);
                    logTAFDebug("UserName in text box: "+userBox.getText());

					if(passBox.exists()){
                        inputChars(browser_htmlBrowser(),passBox, password);
						sleep(1); //add 1 second wait time before clicking login button to make it more realistic
						logTAFDebug("Password in text box: "+passBox.getText());

						if(submit.exists()){
							click(submit);
							sleep(TimerConf.pageRefreshTime);
						}else{
							logTAFError("Login Button does not exist");
						}
						
					}
				}
		}
		
	}
	
	public boolean htmlMenuPaste(TextGuiTestObject to, String text){
	    return htmlMenuPaste(to,to, text);
	}
	
	public boolean htmlMenuPaste(TextGuiTestObject to, TextGuiTestObject toAfter, String text){
		
			// Default html right click popup menu - 8 ITEMS: UNDO,--,CUT,COPY,PASTE,DELETE,--,SELECT ALL	
		
		String classes = "#32768|Html.NoOpTopWindow|Java.PopupMenu";
		int pasteIndex = 5,numItems=8;
		String texts = null;
		
		return htmlMenuPaste(to,toAfter,text,texts,classes,pasteIndex,numItems);
//		return htmlMenuAction(to,text,"Paste");
	}
	public boolean htmlMenuAction(TextGuiTestObject to, String text, String action){
		

		TopLevelSubitemTestObject copyPasteWin = copyPastewindow();
		boolean done = true;
		new NLSHlpr().copyToClipboard(text);
		
		to.setText("");
		//sleep(1);
		to.click(RIGHT, getRelativeCenter(to));
		sleep(2);	
		if(copyPasteWin.exists()){
			try{
				copyPasteWin.click(atText(action));	
				sleep(1);
			}catch(Exception e){
				logTAFWarning("Failed to paste text '"+text+" through windows paste menu");
				done = false;
			}
		}else{
			logTAFDebug("Popup Menu not found");
			done = false;
		}
		
		return done;
	}
	public boolean htmlMenuPaste(ITopWindow top, Point pt,String text){
		String classes = "#32768|Html.NoOpTopWindow|Java.PopupMenu";
		int pasteIndex = 5,numItems=8;
		String texts = null;
		GuiTestObject gui=null;
		ITopWindow popupMenu=null;
		
		boolean done = true;
		int waitForPopup = 0;
        Point tp = top.getScreenPoint();
          
		new NLSHlpr().copyToClipboard(text);
		try{
				
//			gui = getRootTestObject().objectAtPoint(tp);
//			logTAFDebug("Right click on GUI Name='"+gui.getDescriptiveName()+"'"+
//					                    ": Class='"+gui.getObjectClassName()+"'"+
//					                    ": Rec='"+gui.getScreenRectangle()+"'"+
//					                    ": AtPoint='"+pt+"'");
//			
//			gui.click(RIGHT, pt);
			getScreen().click(RIGHT,top.getScreenPoint(pt));
			
		   //  top.click(RIGHT, pt);
		}catch(Exception e){
			logTAFDebug("Exception thrown when right click '"+top+"' at "+pt+":"+e.toString());
		}
		if(texts!=null||classes!=null){		
			sleep(1);
			popupMenu = getDialog(texts,classes);
			while(popupMenu==null&&waitForPopup++<3){
				sleep(1);
				popupMenu = getDialog(texts,classes);
			}
		}else{
		       //	popupMenu =  htmlPopupMenu(); //need to be modified if it's useful
		}
				
		if(popupMenu!=null){
			try{
				Point pastePoint = getItemCenter(popupMenu,pasteIndex,numItems);
				popupMenu.click(pastePoint);	
				sleep(1);
			}catch(Exception e){
				logTAFWarning("Failed to paste text '"+text+" through windows paste menu");
				done = false;
			}
		}else{
			logTAFDebug("Popup Menu not found");
			done = false;
		}
		
		return done;
	}
			

	public boolean htmlMenuPaste(TextGuiTestObject to, TextGuiTestObject toAfter,String text,String texts,String classes,
				int actionIndex, int numItems){		
		ITopWindow popupMenu=null;
		
		boolean done = true;
		int waitForPopup = 0;
		Point leftTopPoint = atPoint(20, 8);
		Point resetPoint = atPoint(-2,-2);
		new NLSHlpr().copyToClipboard(text);
		
//		to.setText("");
//		to.click(resetPoint);
		if(toAfter!=null){
		   to.setProperty(".value", "");
		   sleep(1);
		}else{
			toAfter = to;
		}
		
		toAfter.click(RIGHT, getRelativeCenter(toAfter));
		if(texts!=null||classes!=null){		
			sleep(1);
			popupMenu = getDialog(texts,classes);
			while(popupMenu==null&&waitForPopup++<3){
				sleep(1);
				popupMenu = getDialog(texts,classes);
			}
		}else{
		//	popupMenu =  htmlPopupMenu(); //need to be modified if it's useful
		}
				
		if(popupMenu!=null){
			try{
				Point pastePoint = getItemCenter(popupMenu,actionIndex,numItems);
//				for(int i=1;i<=numItems;i++){
//					pastePoint = getItemCenter(popupMenu,i ,numItems);	
//					popupMenu.hover(2,pastePoint);
//				}
				//popupMenu.click(atText("Paste"));
				popupMenu.click(pastePoint);	
				sleep(1);
//				_printObjects.printObjectTree((IWindow) popupMenu);
			}catch(Exception e){
				logTAFWarning("Failed to paste text '"+text+" through windows paste menu");
				done = false;
			}
		}else{
			logTAFDebug("Popup Menu not found");
			done = false;
		}
		
		return done;
	}
	
	public boolean menuPaste(TextGuiTestObject to, String text){
		
		return menuPaste(pastemenuItem(),to,text);
	}
	
	public boolean menuPaste(GuiTestObject gsto,TextGuiTestObject to, String text){
		boolean done = true;
		
		Point leftTopPoint = atPoint(20, 8);
		try{
			new NLSHlpr().copyToClipboard(text);
			sleep(1);
			to.setProperty(".value","");
			sleep(1);
			to.click(RIGHT, leftTopPoint);
			waitForExistence(gsto);
			gsto.click(leftTopPoint);
			sleep(1);
		}catch(Exception e){
			logTAFWarning("Failed to paste text '"+text+" through windows paste menu");
			done = false;
		}
		return done;
	}
	public boolean closeHtmlPopup(String title){
		boolean done = false;
		ITopWindow popup=null;
		String titleSuffix = "Windows Internet Explorer";
		String classes = "IEFrame";
		if(!title.equalsIgnoreCase("")){
			title += " - "+titleSuffix+"|Certificate Error";
		}
		popup = getDialog(title,classes);
		if(popup!=null){
			logTAFStep("Close a html Popup with title '"+title+"'");
			popup.close();
			done = true;			
		}
		return done;
	}
	public boolean handleErrorDialog(){
			 
		String title="Error",error = title;
		ITopWindow popup;
		boolean foundErrorMsg = false;
		
		 while(okbutton().exists()&&errmsg().exists()){
			   
			   error = errmsg().getProperty(".text").toString();
		       logTAFWarning("'"+error+"'");
			  // logTAFStep("Table exceeds the maximum allowable rows or columns, Click Ok to load the allowable data.");
			  	  
			   okbutton().click();
			   logTAFStep("Click OK for this error msg to proceed");
			   sleep(TimerConf.dialogLoadTime);
			   foundErrorMsg = true;		
		   }
		 popup = getDialog(title,"Windows Security");
			if(popup!=null){
				if(text_usernameWIN().exists()){
				   logTAFWarning("Enter ironport.acl.com '"+"PasswordUnknown"+"'");
				   inputChars(text_usernameWIN(),"PasswordUnknown");
				   if(okToIronport().exists()){
					   click(okToIronport(),"OK");
				   }
				}else{
				   popup.close();
				}
				sleep(1);
			}
			
		popup = getDialog(title,null);
		if(popup!=null){
			logTAFWarning("Close a Erro Popup with title '"+title+"'");
			popup.close();
			foundErrorMsg = true;		
			sleep(1);
		}
		return foundErrorMsg;
	}
	public boolean handleFileDownload(String action,String file){
		boolean done= true;
		String[] actions = action.split("\\|");
		String actionAtComplete = "";
        IWindow saveAsWindow,saveAsButton;
		if(actions.length>1){
			   actionAtComplete = actions[1];
		   }
		
		//waitForExistence(dialog_htmlFileDownload());
		if(dialog_htmlFileDownload().exists()&&actions.length>0){
			
			dialog_htmlFileDownload().click(atPoint(5,5));
			logTAFStep("File Download window appears for '"+file+"'");
			// will implement  action later if needed, there is a problem for RFT 
			// working with the Save as window, need a find method to handle it instead.
			try{
			  if(actions[0].equalsIgnoreCase("Cancel")){
				  while(dialog_htmlButtonCancel().exists()){
					  dialog_htmlButtonCancel().click();
					  logTAFStep("Click 'Cancel' button to cancel downloading ");
					  sleep(1);
					  handleErrorDialog();
				  }
			  }else if(actions[0].equalsIgnoreCase("Open")){
				  while(dialog_htmlButtonOpen().exists()){
				      dialog_htmlButtonOpen().doubleClick();
				      logTAFStep("Click 'Open' button to open "+file);
				      sleep(1);
				      handleErrorDialog();
				  }
			  }else if(actions[0].equalsIgnoreCase("Save")){
				  while(dialog_htmlButtonSave().exists()){
				      dialog_htmlButtonSave().click();
				      logTAFStep("Click 'Save' button");
				      sleep(1);
				      handleErrorDialog();
				  }
				   sleep(5);
				  
				   saveAsWindow = getDialog("Save As","#32770");
				   if(saveAsWindow!=null){
					   saveAsWindow.click(atPoint(5,5));
				   }else{
					   logTAFError("Sava As window not found");
					   return false;
				   }
				   UnicodeHelper.inputChars(saveAsWindow,file);
				   //savebuttonSaveAs().waitForExistence(TimerConf.maxWaitTime,TimerConf.waitBetweenRetry);
				   //saveAswindow().click(atPoint(5,5));
//					inputChars(rftObjSelectfilestoimportwindow, getFormatedFilesString(dpImportFiles));	// RFT limit?: only 256 characters supportted
//					rftObjSelectfilestoimportwindow.inputKeys("{ENTER}");
//				   comboBoxSaveAsFile().doubleClick();
				   //UnicodeHelper.inputChars(saveAswindow(),file);
				  
				   logTAFStep("Save as '"+file+"'");
				   sleep(1);
				   saveAsButton=findControl(saveAsWindow, "&Save") ;
				   if(saveAsButton!=null){
					   saveAsButton.click();
				   }else{
					   logTAFError("Sava As button not found");
					   return false;
				   }
				   //savebuttonSaveAs().click();
				   logTAFStep("Click Save button to save file to the dir specified");
				   sleep(TimerConf.dialogLoadTime);
				   
			
				   postFileDownload(actionAtComplete,1200);

			  }
			}catch(Exception e){
				done = false;
				logTAFWarning("Exception when handle html file download "+e);
			}
		}else{
			logTAFError("File Download window didn't appear for '"+file+"'");
			done = false;
		}		
		return done;		
	}
	public void postFileDownload(String actionAtComplete,int maxWaitTime){
		   //int maxWaitTime = 1200;
		GuiTestObject gto = null;
		IWindow saveAsWindow,yesButton;
		
		   while(ReplaceWindow().exists()){
			   try{
			       gto = (GuiTestObject) ReplaceWindow().find(atDescendant(".class",".Pushbutton",".name","Yes"),false)[0];
				   gto.click();
				   logTAFStep("file existed in the dir, click OK to replace the existed file");
				   sleep(TimerConf.dialogLoadTime);
			   }catch(Exception e){		
				   logTAFWarning("Failed to click OK for 'Confirm replace, try close the dialog directly");
				   ReplaceWindow().close();
			   }

		   }
		   saveAsWindow = getDialog("Save As","#32770");
		   if(saveAsWindow != null){
			   yesButton = findControl(saveAsWindow, "&Yes") ;
			   while(yesButton!=null){
				   yesButton.click();
				   logTAFStep("file existed in the dir, click OK to replace the existed file");
				   sleep(TimerConf.dialogLoadTime);
				   yesButton = findControl(saveAsWindow, "&Yes") ;
			   }
		   }
		   if(actionAtComplete.equals(""))
			   return;
		   

//		   waitForExistence(dialog_htmlDialogButtonClose(),true,
//				   maxWaitTime,TimerConf.waitBetweenRetry);
		   
		   if(dialog_htmlDialogButtonClose().exists()||IEDialog().exists()){ // do only once
			     if(actionAtComplete.equalsIgnoreCase("Close")){
			    	 gto = (GuiTestObject) (dialog_htmlDialogButtonClose().exists()?
			    			 dialog_htmlDialogButtonClose():
			    				 IEDialog().find(atDescendant(".class",".Pushbutton",".name","Close"),false)[0]);
			    	 gto.click();
			    	 logTAFStep("Click close to close the Download complete dialog");
			    	 sleep(1);
			    	 handleErrorDialog();
			     }else{// if(actionAtComplete.equalsIgnoreCase("Close")))
			    	 gto = (GuiTestObject) (dialog_htmlButtonOpen().exists()?
			    			 dialog_htmlButtonOpen():
			    				 IEDialog().find(atDescendant(".class",".Pushbutton",".name","Open"),false)[0]);
			    	 gto.click();
			    	 logTAFStep("Click open to open the Downloaded file");
			    	 sleep(1);
			    	 handleErrorDialog();
			    	 sleep(TimerConf.dialogLoadTime);
			     }
			   }
	}
    public void verifyNativeFiles(GuiTestObject fileLink){
    	String fileExtension ="";
    	String fileName = fileLink.getProperty(".text").toString();
    	IWindow app = null;
    	String appLabel ="";
    	
    	logTAFInfo("Uploaded file: "+fileName);
    	try{
    	    fileExtension = fileName.split("\\.")[fileName.split("\\.").length-1];
    	}catch(Exception e){
    		logTAFWarning(" file extension for file '"+fileName+"' not found");
    		fileExtension = "";
    	}
    	        	
    	logTAFStep("Click the file link to open it using windows native application");
    	
    	fileLink.click(atPoint(5,3));
    	dialog_htmlButtonOpen().waitForExistence(TimerConf.maxWaitTime,TimerConf.waitBetweenRetry);
    	sleep(1);
    	dialog_htmlButtonOpen().click();
    	sleep(2*TimerConf.dialogLoadTime);
    	if(fileExtension.equals("txt")){
    		appLabel = "WordPad|Notepad";        		
    	}else if(fileExtension.equals("ppt")){
    		appLabel = "Microsoft PowerPoint";
    	}else if(fileExtension.equals("xls")){
    		appLabel = "Microsoft Excel";
    	}else if(fileExtension.equals("doc")){
    		appLabel = "Microsoft Word";
    	}else if(fileExtension.equals("wma")){
    		appLabel = "Windows Media Player";
    	}else if(fileExtension.equals("pdf")){
    		appLabel = "Adobe Reader";
    	}else if(fileExtension.equals("")){
    		appLabel = "";
    	}else if(fileExtension.equals("jpg")){
    		appLabel = "Windows Picture and Fax Viewer|Paint|fileName";
    	}else if(fileExtension.equals("")){
    		appLabel = "";
    	}else {
    		logTAFWarning("Automation currently doesn't support '"+fileExtension+"' file verification");
    	}
    	
    	if(!appLabel.equals("")){
    		for(int i=0;i<30&&app==null;i++){
    		   app = getITopWinObject(appLabel,null);
    		   sleep(2);
    		}
    		if(app.equals(null)){
    			logTAFError("Failed to open this file with '"+appLabel+"'");
    		}else{
    		    app.close();
    		    logTAFStep("Closing opening file "+fileName);
    		    sleep(TimerConf.pageRefreshTime);
    		}
    	}else{
    		logTAFWarning("Type unknown - "+fileName);
    	}
    }
    
	public boolean verifyHtmlTitle(String pageTitle){
		//@Step verify the title of loaded page, report fail or pass
		boolean done= false;
		//handleErrorDialog();
		try{
		    document_html().waitForExistence();
		}catch(Exception e){
			logTAFWarning("The page took longer time to be loaded");
		}
		
		sleep(TimerConf.pageRefreshTime);
		
//		if(done=vpManual("PageTitleMatch",
//				pageTitle,
//				document_html().getProperty(".title")
//				).performTest()){
//			logTAFStep("Page loaded correctly:'"+ pageTitle+"'");
//			done = true;
		if(document_html().getProperty(".title").toString().equals(pageTitle)){
			logTAFStep("Page loaded correctly:'"+ pageTitle+"'");
			done = true;
		}else if(document_html().getProperty(".title").toString().startsWith(pageTitle)){
//			logTAFWarning("Actual page title '"+document_html().getProperty(".title").toString()+
//					"' started with expected title '"+pageTitle+"'");
			done = true;
		}else if(document_html().getProperty(".title").toString().contains(pageTitle)){
			logTAFWarning("Expected page title contained in actual page title, but not exactly match");
			logTAFInfo("\t-Expected title: "+pageTitle);
			logTAFInfo("\t-Actual title: "+document_html().getProperty(".title").toString());
			done = true;
		}else if(document_html().getProperty(".title").toString().matches(pageTitle)){
			logTAFWarning("Page title loaded in correct format?:'"+ pageTitle+"'");
			done = true;
		}else{
			logTAFError("Page title '"+document_html().getProperty(".title")+
					"' is not correct as expected: '"+pageTitle+"'");
			done = false;
		}
		return done;
	}
	
	public boolean messageOnPage(String msg){
		
		String htmlDocText = document_html().getProperty(".text").toString();
			//logTAFDebug(htmlDocText+" CONTAINS "+msg+ " ?");		
		return htmlDocText.contains(msg);

	}
	
	public boolean dismissBrowser(){ 
	   return dismissBrowser("All");
	}
	
	public boolean dismissBrowser(String expectedTitle){
		boolean dismissedAWindow = false;
		String cerifcateError = "CERTIFICATE ERROR";
		DomainTestObject domains[] = getDomains();
		
		
		if(waitForLoading(document_html())){
			//return true;
		}
		
		if(expectedTitle.toUpperCase().startsWith("AX ")){
			expectedTitle = "AX";
		}
	 try
	   {
		for (int i = 0; i < domains.length; ++i)
		{
			if (domains[i].getName().equals("Html"))
			{
				// HTML domain is found.
				TestObject[] topObjects = domains[i].getTopObjects();
				if (topObjects != null)
				{
					
						for (int j = 0; j<topObjects.length; ++j)
						{	
							boolean duplicate = false;
							if (topObjects[j].getProperty(".class").toString().equals("Html.Dialog"))
							{
								// A top-level HtmlDialog is found.
								//logWarning("HtmlScript.onObjectNotFound - dismissing dialog.");
									dismissedAWindow = true;
									((TopLevelTestObject)topObjects[j]).close();
									logTAFWarning("Dismissing a dialog.");
								
							}else if(topObjects[j].getProperty(".class").equals("Html.HtmlBrowser")){
								TestObject[] docs = topObjects[j].getChildren();
								boolean done = false;
								String actualTitle ="";
								for (int m=0; m<docs.length&&!done;m++){
									if(docs[m].getProperty(".class").toString().equals("Html.HtmlDocument")){
										done = true;
										actualTitle = docs[m].getProperty(".title").toString();
										if((!actualTitle.toUpperCase().startsWith(expectedTitle)&&
										   !actualTitle.toUpperCase().startsWith(cerifcateError)&&
										   !actualTitle.toUpperCase().startsWith("ACL "))||
												duplicate){
											// A unexpected HtmlBrowser is found.
												dismissedAWindow = true;
												((TopLevelTestObject)topObjects[j]).close();
												if(!expectedTitle.equalsIgnoreCase("All")){
												  logTAFWarning("Dismissing Browser - '"+actualTitle+"'");
										    	}else{
										    		logTAFDebug("Closing Browser - '"+actualTitle+"'");
										    	}
											duplicate = true;
										}
									} // end of if
									
								}
								
							}
						}
						unregister(topObjects);
				}

			}
			}
	    }catch(Exception e){
	    	logTAFDebug("Exception thrown when checking duplicate windows "+e.toString());
		}
     return dismissedAWindow;
	} 
	
    public boolean isInProgress(){
    	boolean showing = false;
    	if(html_progressBar().exists()){
    		showing = html_progressBar().isShowing();
    	}
    	if(showing){
    		sleep(2*TimerConf.dialogLoadTime);
    		logTAFDebug("It's a chllenge to get loading status...");
    		showing = false;
    	}
    	return showing;
    }
    
	public void testMain(Object[] args) 
	{
		try {
			UnicodeUtil.CsvToXls("AX_Gateway\\KeywordTable\\SmokeTest\\login.csv",
			"AX_Gateway\\KeywordTable\\SmokeTest\\login.xls");
			
			UnicodeUtil.XlsToCsv("AX_Gateway\\KeywordTable\\SmokeTest\\login.xls",
			"AX_Gateway\\KeywordTable\\SmokeTest\\_login.csv");
			
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
	}
}

