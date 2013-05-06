package ACL_Desktop.Tasks;

import java.awt.Point;
import java.io.File;

import lib.acl.helper.sup.LoggerHelper;
import lib.acl.util.FileUtil;
import resources.ACL_Desktop.Tasks.moreddwmenuHelper;
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

public class moreddwmenu extends moreddwmenuHelper
{
	/**
	 * Script Name   : <b>prtddwmenu</b>
	 * Generated     : <b>2012-02-23 10:15:39 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/02/23
	 * @author Karen_zou
	 */
    private String[] fieldLens, otherSeps,otherQuas;
    private String ddwtext = "Data Definition Wizard"; //dpMasterFile,dpActualFile;
    //private String fileExt = ".fil";
    private String actualName;
    
    private boolean cancelTest = false;
    private boolean dataFileCreated = false,
                    itemCreated = false;

    // BEGIN of datapool variables declaration
	private String dpOpenProject ;   //@arg command for open acl project
	     //@value = 'Open|OpenCancel|OpenNew|OpenWorking|OpenLastSaved|OpenRecent|CreateNew', default to OpenNew
	private String dpEndWith ;       //@arg the end options for current test 
	                                 //@value = 'Cancel|Finish' - Default to 'Finish'
	private String dpSelectPlatform ;//@arg Platform of this test
	                                 //@value = 'Local|[ProfileName]', the profileName can be either 'AuditExhang_'which pointed to our ACLSEs
	                                 //@value   ' or the profile you want to use.
	private String dpDataSource;     //@arg Data Source of this test
	                          	     //@value = 'Disk|ODBC|External|FlatFiles|Database', default to Disk (Currently only 'Disk' available) 
	private String dpFileFormat ;    //@arg File Format
	                                 //@value = 'PDF|Print Image'
	private String dpSelectFile;     //@arg relative/absolute path to the file
	private String dpCharacterSet ;  //@arg Character set
	                     			 //@value = 'ASCII|EBDIC|Unicode|Encoded'
	private String dpSelectPages ;   //@arg pages selected to import	
	private String dpNameConvention ;//@arg option of name convention for SAP 
	                                 //@value = 'local|standard'
	private String dpContextType ;   //@arg option of context type for XBRL 
    								 //@value = 'Instant|Period|Forever'
	private String dpSaveFileAs;     //@arg relative/absolute path to save the resulted file
	private String dpSaveLocalOrNet;//@arg save on Local or Net if this option is available 
	private String dpSaveTableAs;    //@arg name of the resulted table
	
	private String dpBytesToSkip;   //@arg num of bytes to be skipped
	private String dpFileType;      //@arg file type
	                                  //@value = 'DataFile|Skip'
	// END of datapool variables declaration


	@Override
	public boolean dataInitialization() {
		boolean done= true;

		dpOpenProject = getDpString("OpenProject");
		dpEndWith = getDpString("EndWith");	 
		dpSelectPlatform = getDpString("SelectPlatform");
		   if(dpSelectPlatform.equals("")){
			   dpSelectPlatform = "Local";
		   }else if(!dpSelectPlatform.equals("Local")){
			   if(dpSelectPlatform.endsWith("_")){
				   if(ProjectConf.unicodeTest)
				       dpSelectPlatform += "UniSE (Windows)";
				   else
					   dpSelectPlatform += "NonUniSE (Windows)";
			   }else if(!dpSelectPlatform.equals(" (Windows)")){
			       dpSelectPlatform += " (Windows)";		
			   }
		   }
		dpDataSource = getDpString("DataSource");
		   if(dpDataSource.equals("")){
			   dpSelectPlatform = "Disk";
		   }
		dpFileFormat = getDpString("FileFormat");
		dpSelectFile = getDpString("SelectFile");
		   if(dpSelectPlatform.equals("Local"))
		       dpSelectFile = FileUtil.getAbsDir(dpSelectFile,ProjectConf.localInputDataDir);
		   else
			   dpSelectFile = FileUtil.getAbsDir(dpSelectFile,ProjectConf.serverInputDataDir);
	    dpCharacterSet = getDpString("CharacterSet");
	    dpSelectPages = getDpString("SelectPages");
	    dpNameConvention = getDpString("NameConvention");
	    dpContextType = getDpString("ContextType");	    

		dpSaveLocalOrServer = getDpString("SaveLocalOrServer");
		    if(dpSaveLocalOrServer.equals("")){
		    	dpSaveLocalOrServer = "Local";
		    }
		
	    dpSaveFileAs = getDpString("SaveFileAs");
	    	dpSaveFileAs = setupTestFiles(dpSaveFileAs,dpSaveLocalOrServer,fileExt=".fil");
	    /*dpMasterFile = FileUtil.getAbsDir(dpSaveFileAs,ProjectConf.expectedDataDir);
		    FileUtil.mkDirs(dpMasterFile);
		    if(dpSaveLocalOrNet.equalsIgnoreCase("Local")){			    	
		        dpSaveFileAs = FileUtil.getAbsDir(dpSaveFileAs,ProjectConf.tempLocalDir);			        
		        FileUtil.mkDirs(dpSaveFileAs);
		    } else{
		    	dpSaveFileAs = FileUtil.getAbsDir(dpSaveFileAs,ProjectConf.tempServerDir);
		    	FileUtil.mkDirs(FileUtil.getAbsDir(dpSaveFileAs,ProjectConf.tempServerNetDir));
		    }
		 */   
		    dpActualFile = FileUtil.getFullNameWithExt(dpSaveFileAs,fileExt);
		    dpMasterFile = FileUtil.getFullNameWithExt(dpMasterFile,fileExt);
		dpSaveTableAs = getDpString("SaveTableAs");
                    
		//dpBytesToSkip = getDpString("BytesToSkip");
		dpFileType = getDpString("FileType");
 
		return done;
	}

	public void testMain(Object[] args) 
	{
		super.testMain(args);

		//Steps:
		//@Step Activate ACLWin
		//@Step Click File->New->Table
		//@Step Do table creation with DDW and inputs
		//@Step End table creation with the option specified in 'EndWith'
		//@@Step should be 'Cancel' or 'Finish'
		//@Step Optionally run a test on file menu test to verify the creation of tables.
		
		actualName = kUtil.replaceSpecialChars(dpSaveTableAs);
		
		if(!dpOpenProject.equals("CreateNew")||!DDW().exists()){
//			aRou.exeACLCommands(dpPreCmd);
//			aRou.setACLFilters(dpPreFilter);
			kUtil.invokeMenuCommand("File->New->NewTable");
		}
        selectPlatform(ddwtext + " - Select Platform for Data Source");
        selectDataSource(ddwtext + " - Select .+"); 
        
        if(dpDataSource.matches("FlatFiles|Disk")){
    		selectFileToDefine("Select File to Define"); 
    		//if format not identified 
 		    selectCharacterSet(ddwtext+" - Character Set");
    		selectFileFormat(ddwtext+" - File Format");

        	if(dpFileFormat.matches("PDF")){
        		selectPDFPages(ddwtext+" - PDF File Definition");
        		if (ProjectConf.unicodeTest) {
        			pdfFileDefinitionUnicode(ddwtext+" - PDF File Definition");       			
        		}else {
        			pdfFileDefinition(ddwtext+" - PDF File Definition");
        		}
        		saveDataFileAs("Save Data File As");
        	}else if(dpFileFormat.matches("Print Image")){
        	    printimageFileDefinition(ddwtext+" - Print Image File Definition");
        		saveDataFileAs("Save Data File As");	
        	}else if (dpFileFormat.matches("SAP")) {
        		sapPrivateFileFormat(ddwtext+" - SAP private file format");
        		saveDataFileAs("Save Converted SAP File As");       		
        	}else if (dpFileFormat.matches("XBRL")) {
        		xbrlImport(ddwtext+" - XBRL Import");
        		saveDataFileAs("Save Data File As"); 
        	}else if (dpFileFormat.matches("dBASE")) {
        		;//No specific dialog needed for dBASE data type
        	}        	
        }
//        selectFileType(ddwtext+" - File Type");
//        identifyFields(ddwtext+" - Identify Fields");
//                   
        
        exeEndWith(ddwtext+" - Final",dpEndWith);
	}
    
	public void selectPlatform(String ddwt){
		
		waitForExistence(DDW());
		if(!checkWinAvailablity(ddwt)) return;
		
		if(dpSelectPlatform.equalsIgnoreCase("Local")){
            actionOnCheckbox(sp_local(),"Local",true,"New");
		}else{
			actionOnCheckbox(sp_server(),"ACL Server",true,"New");				
			actionOnSelect(sp_profile(), "ACL Server Profile",dpSelectPlatform,"New");
		}
		click(ddw_next(),"Next >");	
		sleep(1);

	}
	
    public void	selectDataSource(String ddwt){
        kUtil.closeServerActivity();
        if(!checkWinAvailablity(ddwt)) return;
        
		if(dpSelectPlatform.equalsIgnoreCase("Local")){
			if(dpDataSource.equalsIgnoreCase("Disk")){
				actionOnCheckbox(slds_disk(),"Disk",true,"New");	
				}else if(dpDataSource.equalsIgnoreCase("ODBC")){
					actionOnCheckbox(slds_odbc(),"ODBC",true,"New");		
				}else{
					actionOnCheckbox(slds_external(),"External Definition",true,"New");		
				}
		}else{
			if(dpDataSource.equalsIgnoreCase("FlatFiles")){
				actionOnCheckbox(ssds_flatFiles(),"Flat Files",true,"New");	
			}else{
				actionOnCheckbox(ssds_dbProfile(),"Database profile",true,"New");		
			  actionOnSelect(ssds_dbpros(), "Database profile",dpDataSource,"New");
			}
		}
		
		click(ddw_next(),"Next >");	
		sleep(1);

    }
    
    public void selectFileToDefine(String title){
     	if(ddwWizardError("OK",false)){
            cancelDDW();
	    }
     	  
    	if(!sf_open().exists()){
    		//
    		return;
    	}
    	File f = new File(dpSelectFile);	
        String path = f.getParent();
        String name = f.getName();
        
    	if(dpDataSource.equalsIgnoreCase("Disk")){
    		if(sf_localfilename_old().exists())
    			click(sf_localfilename_old(),true);
    		else if(sf_localfilename().exists())
    			click(sf_localfilename(),true);

	    	inputUnicodeChars(dpSelectFile);
      	}else{
      		click(sf_lookin(),true);
	    	inputUnicodeChars(path);
      		click(sf_serverfilename());
      		inputUnicodeChars(name);
    	}
    	
    	//inputUnicodeChars(dpSelectFile);
    	click(sf_open(),"Open");
    	sleep(1);
    	if(kUtil.checkACLCrash()){
    		cancelTest = true;
    		return;
		}
    	if(ddw_selectfileok().exists()){
    		String msg = ddw_selectfilemsg().getProperty(".text").toString();
    		logTAFError(msg);
    		click(ddw_selectfileok(),"OK");
    		click(sf_cancel(),"Cancel");
    		cancelTest = true;
    		cancelDDW();
    	}
   }
    
    public void saveDataFileAs(String title){
   	    if(dismissPopup("OK|Yes",false)){
 		   sleep(2);
 		   if(!sf_save().exists()&&!sf_save_old().exists()){
 			   cancelDDW();
 		   }
   	    }
     	  
    	if(!sf_save().exists()&&!sf_save_old().exists()){
    		//
    		return;
    	}
    	File f = new File(dpSelectFile);	
        String path = f.getParent();
        String name = f.getName();
        
    	if(dpSaveLocalOrServer.equalsIgnoreCase("Local")){
    		if(sf_localfilename_old().exists())
    		    click(sf_localfilename_old(),true);
    		else if(sf_localfilename().exists())
    			click(sf_localfilename(),true);
    		//sf_localfilename().doubleClick();
	    	inputUnicodeChars(dpSaveFileAs);

      	}else{
      		click(sf_lookin(),true);
	    	inputUnicodeChars(path);
      		click(sf_serverfilename());
      		inputUnicodeChars(name);
    	}
    	if(sf_save_old().exists()){
    		click(sf_save_old(),"Save");
    	}else{
    	  click(sf_save(),"Save");
    	}
    	sleep(1);
    	saveDataFileMsg(title,"Yes",false);
    	
    	// *** For monaco progress bar testing ... Steven
    	if(!handleProgressBar(new ddwmenu().getProgressInfo(),"DDW",dpTestProgressBar.equalsIgnoreCase("Yes")?true:false)){
    		cancelDDW();
    	}
  }
 
  public void selectCharacterSet(String ddwt){
	  if(!checkWinAvailablity(ddwt)) return;
	 if(dpCharacterSet.equalsIgnoreCase("EBCDIC")) {
		// click(cs_ebcdic(),"EBCDIC");
	 }else if(dpCharacterSet.equalsIgnoreCase("ASCII")) {
		 actionOnCheckbox(cs_ascii(),"ASCII",true,"New");
	 }else if(dpCharacterSet.equalsIgnoreCase("Unicode")) {
		 actionOnCheckbox(cs_unicode(),"Unicode Text",true,"New");
	 }else if(dpCharacterSet.equalsIgnoreCase("Encoded")) {
		 actionOnCheckbox(cs_encoded(),"Encoded Text",true,"New");
	 }
	 click(ddw_next(),"Next >");
	 sleep(1);
  }

  public void selectFileFormat(String ddwt){
	  if(!checkWinAvailablity(ddwt)) return;

	  if(dpFileFormat.equalsIgnoreCase("PDF")) {
		 click(ff_pdf(),"");
	  }else if(dpFileFormat.equalsIgnoreCase("Report")) {
		 click(ff_printimage(),"");
	 }else if (dpFileFormat.equalsIgnoreCase("SAP")) {
		 click(ff_sap(),"");		 
	 }else if (dpFileFormat.equalsIgnoreCase("dBASE")) {
		 click(ff_dbase(),"");		 
	 }else if (dpFileFormat.equalsIgnoreCase("XBRL")) {
		 click(ff_xbrl(),"");		 
	 }
	 click(ddw_next(),"Next >");
  }

  public void selectPDFPages(String ddwt){
	  if(!checkWinAvailablity(ddwt)) return;
	  logTAFInfo("Select pages to import: '"+dpSelectPages+"'"	);
	  
	  if (dpSelectPages.equalsIgnoreCase("All")) {
		  actionOnCheckbox(pdffd_all(),"All",true,"New");
	  }else {
		  actionOnCheckbox(pdffd_pages(),"Pages",true,"New");	
		  actionOnText(pdffd_pages1(),"Set pages",dpSelectPages,"New");
	  }

	  click(ddw_next(),"Next >");	
  }

  public void specifyRecordType(String ddwt){
	  sleep(1);
	  
	  // Window: ACLWin.exe: Specify Record Type
	  if (ddw_specifyrecordtype().exists()){
		  //Select "New Record" type
		  actionOnCheckbox(specifyrecordtype_newrecord(),"New Record",true,"New");
		
		  //CLick "OK" to confirm record type specification
		  click(specifyrecordtype_ok(),"OK");
	  }else logTAFError("Specify Record Type");
  }
  
  private void selectFieldScope(Point startPoint,Point endPoint){
	  // Define the scope of header field
      logTAFInfo("Select field from ("+startPoint.getX()+","+startPoint.getY()+") to ("+endPoint.getX()+","+endPoint.getY()+")");	  
	  ddw_hint().click(LEFT, startPoint);		
	  // Window: ACLWin.exe: RegularExpression(Data Definition Wizard)
      ddw_hint().drag(startPoint, endPoint);
  }
  
  private void defineField(String fieldName, String fieldType){
	  ddw_fd().waitForExistence();
	  
	  // Field Definition
	  if (ddw_fd().exists()) {
		  logTAFInfo("Define field: Name->"+fieldName+"  Type->"+fieldType);	  
		  //Set field name
		  actionOnText(fd_name(), "Name", fieldName, "New");
		  // Set field type
		  actionOnSelect(fd_type(),"Type",fieldType,"New");
		  //CLick "OK" to confirm field definition
		  click(fd_ok(),"OK");
		  if(fd_ok().exists()){
			  logTAFError("Failed to Define field '"+fieldName+"'?");
			  click(fd_ok(),"OK");
		  }
	  }else logTAFError("Define field");
  }
  
  private void defineHeaderRecord(Point startPoint,Point endPoint){
		ddw_hint().click(LEFT, startPoint);
		// Window: ACLWin.exe: RegularExpression(Data Definition Wizard)
		ddw_hint().drag(startPoint, endPoint);
		
		if (ddw_rd().exists()) {
			//CLick "OK" to confirm Header Record definition
			click(rd_ok(),"OK");
		}else logTAFError("Define Header Record");
  }
 
  private void defineDetailRecord(Point detailLoc,Point btnLoc,Point tagLoc1,Point tagLoc2){
	  logTAFInfo("Click detail button");
	 
	  if (!ProjectConf.unicodeTest) {
		  ddw_hint().doubleClick(detailLoc);  //Click "detail" button 

		  if (ddw_rd().exists()) {
			  logTAFInfo("Click End button");	
			  rd_end_and().click(btnLoc);     //Click "End" button 

			  logTAFInfo("Click End->And ");     
			  contextpopupMenu().click(atPath("And"));      // Click End->And

			  logTAFInfo("Define detail criteria");
			  //Define detail criteria
			  ddw_hint().click(LEFT,tagLoc1);
			  ddw_hint().drag(tagLoc1, tagLoc2); 
			  //CLick "OK" to confirm Detail Record definition
			  click(rd_ok(),"OK");	          
		  }else logTAFError("Define Detail Record");
	  }else{
		  sleep(1);
		  ddw_hint().drag(tagLoc1, tagLoc2); 
		  sleep(2);
		  if (ddw_rd().exists()) {
			  click(rd_ok(),"OK");	
		  }else logTAFError("Define Detail Record");
	  }
  }
  
  private void deleteField(Point fieldLoc){
	  logTAFInfo("Click field to be deleted");
	  ddw_hint().click(RIGHT, fieldLoc);
	  
	  sleep(2);
	  // Float window pops up
	  contextpopupMenu().click(atPath("Delete Field"));
	  click(ddw_ok(),"OK");          //CLick "OK" to confirm field deletion
  }
  
  private void deleteRecord(Point recordLoc){
	  logTAFInfo("Click record to be deleted");
	  ddw_hint().click(RIGHT, recordLoc);
	  
	  sleep(2);
	  // Float window pops up
	  contextpopupMenu().click(atPath("Delete Record"));
	  click(ddw_ok(),"OK");          //CLick "OK" to confirm field deletion
  }
  
  public void pdfFileDefinition(String ddwt){
	  if(!checkWinAvailablity(ddwt)) return;

	  //Step 1: Define a new field from Header
	  // Select the scope for the new field
	  selectFieldScope(atPoint(500,55),atPoint(548,55));
	  // Specify Record Type
	  specifyRecordType("Specify Record Type");
	  // Define field
	  defineField("ReportDate","Datetime");
      
	  //Step 2: Define Header record
	  defineHeaderRecord(atPoint(131,50),atPoint(180,50));
	  
	  //Step 3: Define Detail record
      defineDetailRecord(atPoint(70,172),atPoint(108,6),atPoint(206,175), atPoint(211,175));
      //rd_end_and().click(atPoint(105,9));

      //Step 4: Define fields from Detail Record one by one 
	  // Field: custno
      deleteField(atPoint(140,157));
      deleteField(atPoint(162,157));
      // Select field scope		
	  selectFieldScope(atPoint(132,157),atPoint(164,157));
	  // Define field
	  defineField("custno","Character");

	  // Field: invdate
      deleteField(atPoint(183,157));
      deleteField(atPoint(226,157));
      // Select field scope	
	  selectFieldScope(atPoint(193,157), atPoint(241,157));
	  // Define field
	  defineField("invdate","Datetime");	  
	  
	  // Field: paydate
      deleteField(atPoint(259,157));
      deleteField(atPoint(297,157));
      // Select field scope	
	  selectFieldScope(atPoint(270,157), atPoint(321,157));
	  // Define field
	  defineField("paydate","Datetime");
	  
	  // Field: transno
      deleteField(atPoint(343,156));
      deleteField(atPoint(379,157));
      // Select field scope	
	  selectFieldScope(atPoint(354,157), atPoint(388,157));
	  // Define field
	  defineField("transno","Character");	

	  // Field: code
      deleteField(atPoint(423,157));
      // Select field scope		
	  selectFieldScope(atPoint(417,157), atPoint(426,157));
	  // Define field
	  defineField("code","Character");	

      // Field: amount
      deleteField(atPoint(527,157));
      // Select field scope		
	  selectFieldScope(atPoint(432,157), atPoint(575,157));
	  // Define field
	  defineField("amount","Numeric");	
		
	  click(ddw_next(),"Next >");	
  }

	public void pdfFileDefinitionUnicode(String ddwt)
	{
		if(!checkWinAvailablity(ddwt)) return;
		  
		//Step1: Define Detail Record
		//Field: custno
		deleteRecord(atPoint(670,160));
		
	    // Select field scope		
		selectFieldScope(atPoint(130,157), atPoint(199,157));
		// Define field
		defineField("custno","Character");
		
		defineDetailRecord(null,null,atPoint(284,160),atPoint(284,160));

		//Step2: Define fields
        // Field: invdate	
	    // Select field scope		
		selectFieldScope(atPoint(255,157), atPoint(352,157));
		// Define field
		defineField("invdate","Datetime");
		
        // Field: paydate	
	    // Select field scope		
		selectFieldScope(atPoint(410,157), atPoint(506,157));
		// Define field
		defineField("paydate","Datetime");

        // Field: transno
	    // Select field scope		
		selectFieldScope(atPoint(576,157), atPoint(649,157));
		// Define field
		defineField("transno","Character");
		
        // Field: code
		hint_scrollbar().drag(atPoint(107,6), atPoint(185,5));
		// Select field scope		
		selectFieldScope(atPoint(284,157), atPoint(308,157));
		// Define field
		defineField("code","Character");
		
	    // Field: amount
	    // Select field scope		
		selectFieldScope(atPoint(316,157), atPoint(591,157));
		// Define field
		defineField("amount","Numeric");	
		
		//Step 3: Define a new field from Header & header Record
		// Select the scope for the new field
		selectFieldScope(atPoint(450,52), atPoint(549,49));
		// Specify Record Type
		specifyRecordType("Specify Record Type");
		// Define field
		defineField("ReportDate","Datetime");
	      
		// Define Header record
		hint_scrollbar().drag(atPoint(187,11), atPoint(102,11));
		defineHeaderRecord(atPoint(132,51), atPoint(227,47));
		  
		click(ddw_next(),"Next >");		
  }
	
  public void printimageFileDefinition(String ddwt){
	  if(!checkWinAvailablity(ddwt)) return;

	  //Step 1: Define ReportTime field from Header
	  // Select the scope for the new field
	  selectFieldScope(atPoint(570,56), atPoint(620,56));
	  // Specify Record Type
	  specifyRecordType("Specify Record Type");
	  // Define field
	  defineField("ReportTime","Character");
   
	  // Step 2: Define Header record
	  defineHeaderRecord(atPoint(129,55), atPoint(182,55));
		
	  //Step 3: Define Detail record
      defineDetailRecord(atPoint(80,215),atPoint(107,8),atPoint(191,218), atPoint(197,220));
      
      //Step 4: Define fields from Detail one by one 
	  // Field: custno
      deleteField(atPoint(149,157));
      deleteField(atPoint(166,157));
      // Select field scope
	  selectFieldScope(atPoint(132,157), atPoint(165,157));
	  // Define field
	  defineField("custno","Character");
	  
	  // Field: invdate
      deleteField(atPoint(217,156));
      // Select field scope		
	  selectFieldScope(atPoint(178,157), atPoint(226,157));
	  // Define field
	  defineField("invdate","Datetime");	  
	  
	  // Field: paydate
      deleteField(atPoint(257,154));
      deleteField(atPoint(300,157));
      // Select field scope			
	  selectFieldScope(atPoint(270,157), atPoint(320,157));
	  // Define field
	  defineField("paydate","Datetime");
	  
	  // Field: transno
      deleteField(atPoint(348,159));
      deleteField(atPoint(382,159));
      // Select field scope			
	  selectFieldScope(atPoint(360,157), atPoint(397,157));
	  // Define field
	  defineField("transno","Character");	

	  // Field: code
      deleteField(atPoint(436,158));
      // Select field scope			
	  selectFieldScope(atPoint(411,157), atPoint(417,157));
	  // Define field
	  defineField("code","Character");	

      // Field: amount
      deleteField(atPoint(539,159));
      deleteField(atPoint(560,156));
      // Select field scope		
	  selectFieldScope(atPoint(425,157), atPoint(567,157));
	  // Define field
	  defineField("amount","Character");
		
	  click(ddw_next(),"Next >");
  }
  
  public void sapPrivateFileFormat(String ddwt){
	  if(!checkWinAvailablity(ddwt)) return;
	  logTAFInfo("Select field naming convention: '"+dpNameConvention+"'"	);
	  
	  if (dpNameConvention.equalsIgnoreCase("local")) {
		  actionOnCheckbox(sappff_local(),"Use local language field descriptions as ACL field names",true,"New");
	  }else {
		  actionOnCheckbox(sappff_standard(),"Use standard-delivered SAP German abbreviations as ACL field names",true,"New");
	  }

	  click(ddw_next(),"Next >");	
  }
 
  private void verifyButtonStatus(String desc,boolean moveRightStatus,boolean moveLeftStatus,boolean AddAllStatus,boolean RemoveAllStatus){
      //Verify status for selection buttons after choosing any context type and Available Contexts is not empty
	  logTAFInfo("Verify the status for selection buttons " + desc);
	  sleep(1);

	  if ((isEnabled(xbrl_moveright())==moveRightStatus)&&(isEnabled(xbrl_moveleft())==moveLeftStatus)&&
		  (isEnabled(xbrl_addall())==AddAllStatus)&&(isEnabled(xbrl_removeall())==RemoveAllStatus)){
	      logTAFInfo("The status for selection buttons " + desc + " is correct.");
	  }else logTAFError("The status for selection buttons " + desc + "is incorrect!");	
  }
  
  private void verifyListBoxNum(int LeftListBoxNum,int RightListBoxNum){
	  int curRightListBoxNum,curLeftListBoxNum;
	  
	  sleep(1);
      //Verify number of items in the left and right list boxes
	  curRightListBoxNum = Integer.parseInt(xbrl_rightlistbox().getProperty(".itemCount").toString());
	  if (curRightListBoxNum != RightListBoxNum){
		    logTAFError("The number of items in the right list box after Add All is incorrect!");
	 	 }else {   
			curLeftListBoxNum = Integer.parseInt(xbrl_leftlistbox().getProperty(".itemCount").toString());
			if (curLeftListBoxNum != LeftListBoxNum) {
	  		   logTAFError("The number of items in the left list box after Add All is incorrect!");		  				
		    }
		 }
	  return;
  }
  
  public void xbrlImport(String ddwt){
	  int numLeftListBox=0;
	  boolean hasData = false;
	  String action;
	  
	  if(!checkWinAvailablity(ddwt)) return;
	  logTAFInfo("XBRL Import-> Select XBRL Contexts to Import");

	  //Verify status for selection buttons without choosing any context type
	  action = "without choosing any context type";
	 //??? verifyButtonStatus(action,false,false,false,false);

	  //Select Context Type	-- Instant
	  if(dpContextType.equalsIgnoreCase("Instant")){
		  actionOnCheckbox(xbrl_instant(),"Instant",true,"New");
		  
		  //Get the number of items for the left list box
		  numLeftListBox = Integer.parseInt(xbrl_leftlistbox().getProperty(".itemCount").toString());
		  if (numLeftListBox > 0){ //The left list box is not empty
			  hasData = true;
  			  //Verify status for selection buttons after choosing Instant and Available Contexts is not empty
			  action = "after choosing Instant and Available Contexts is not empty";
			//verifyButtonStatus(action,false,false,true,false);
		  
			  if (xbrl_addall().isEnabled()){
	  			 click(xbrl_addall(),"Add All");

		  		 //Verify status for selection buttons after clicking Add All
				  action = "after clicking Add All";
				//verifyButtonStatus(action,false,false,false,true);
		  		 
		  		  //Verify number of items in the left and right list boxes
				  verifyListBoxNum(0,numLeftListBox);
		  	}
	  	  }  
	  }

	  //Select Context Type	-- Period
	  if (dpContextType.equalsIgnoreCase("Period")){
		  actionOnCheckbox(xbrl_period(),"Period",true,"New");
		  
		  //Get the number of items for the left list box
		  numLeftListBox = Integer.parseInt(xbrl_leftlistbox().getProperty(".itemCount").toString());
		  if (numLeftListBox > 0) {
			  hasData = true;
			  //Verify status for selection buttons after choosing Period and Available Contexts is not empty
			  action = "after choosing Period & Available Contexts is not empty";
			  //verifyButtonStatus(action,false,false,true,false);
			 
			  //Move the first item from Available Contexts to Selected Contexts
		  	  xbrl_leftlistbox().select(0);

		  	  //Verify status for selection buttons after selecting one item in Available Contexts
			  action = "after selecting one item in Available Contexts";
			//verifyButtonStatus(action,true,false,true,false);

			  click(xbrl_moveright(),"-->");	  

			  //Verify status for selection buttons after clicking --> button
			  action = "after clicking --> button";
		     /*	  if (numLeftListBox == 1){
				  verifyButtonStatus(action,false,false,false,true);
			  }else{
				  verifyButtonStatus(action,false,false,true,true);
			  }
		      */
		  	  if (numLeftListBox > 1){
		  		  //Select the last item from Available Contexts to Selected Contexts
		  		  xbrl_leftlistbox().select(numLeftListBox-2);

			  	  //Verify status for selection buttons after selecting one item in Available Contexts
				  action = "after selecting an item for 2nd. time in Available Contexts";
				  //verifyButtonStatus(action,true,false,true,true);

				  click(xbrl_moveright(),"-->");
		  		  
		  		  //Move an item from Selected Contexts to Available Contexts
		  		  xbrl_rightlistbox().select(0);

		  		  //Verify status for selection buttons after choosing an item in Selected Contexts 
				  action = "after selecting an item in Selected Contexts";
				  /* if (numLeftListBox == 2){
					  verifyButtonStatus(action,false,true,false,true);
		  		  }else{
					  verifyButtonStatus(action,false,true,true,true);
		  		  }
				   */
		  		  click(xbrl_moveleft(),"<--");
		  	  }
		  	  
			  //Verify the number of items in Available Contexts and Selected Contexts
		  	  verifyListBoxNum(numLeftListBox-1,1);
	      }
	  }
	  
	  //Select Context Type	-- Forever
	  if (dpContextType.equalsIgnoreCase("Forever")){
          actionOnCheckbox(xbrl_forever(),"Forever",true,"New");	
		  numLeftListBox = Integer.parseInt(xbrl_leftlistbox().getProperty(".itemCount").toString());
		  if (numLeftListBox > 0) {
			  hasData = true;

			  //Verify status for selection buttons after choosing any context type and Available Contexts is not empty
			  action = "after choosing Forever";

		  	  if (xbrl_addall().isEnabled()){
		  	      click(xbrl_addall(),"Add All");
		  		  click(xbrl_removeall(),"Remove All");

				  //Verify the number of items in Available Contexts and Selected Contexts
			  	  verifyListBoxNum(numLeftListBox,0);
		      }
		  	  //Select the first item from Available COntexts to Selected Contexts
		  	  xbrl_leftlistbox().select(0);
		  	  click(xbrl_moveright(),"-->");	  
	      }
	 }

	 if (hasData){
		 click(ddw_next(),"Next >");	
 		 xbrlImport1(ddwtext+" - XBRL Import");
		 xbrlImport2(ddwtext+" - XBRL Import");
	 }		 
	 else logTAFWarning("No testing data for "+ dpContextType + " Context Type!");
  }  
  
  public void xbrlImport1(String ddwt){
	  if(!checkWinAvailablity(ddwt)) return;
	  logTAFInfo("XBRL Import-> Select Elements to Import");

	  //Click Deselect All
	  logTAFInfo("Click Deselect All");
	  click(xbrl_deselectall(),"Deselect All");
	  
	  sleep(1);
	  //Click Select All
	  logTAFInfo("Click Select All");
	  click(xbrl_selectall(),"Select All");

	  sleep(1);
	  //Click Reverse Selection
	  logTAFInfo("Click Reverse Selection");
	  click(xbrl_reverseselection(),"Reverse Selection");

	  sleep(1);
	  //Click Select All
	  logTAFInfo("Click Select All");
	  click(xbrl_selectall(),"Select All");
	  
	  click(ddw_next(),"Next >");
  }

  public void xbrlImport2(String ddwt){
	  if(!checkWinAvailablity(ddwt)) return;
	  logTAFInfo("XBRL Import-> Preview Data");
	  
	  click(ddw_next(),"Next >");	
  } 
  
  public void recordDefinition(String ddwt){
	  if(!checkWinAvailablity(ddwt)) return;
	  //TBD ...
	  
	  click(ddw_next(),"Next >");	
	  
  }
  public void identifyRecord(String ddwt){
	  if(!checkWinAvailablity(ddwt)) return;
	  //TBD ...
	  
	  click(ddw_next(),"Next >");	
	  
  }
  
  public void identifyFields(String ddwt){
	  if(!checkWinAvailablity(ddwt)) return;
      //TBD ...
	  
	  click(ddw_next(),"Next >");	
	  
  }
  public void editFieldProperties(String ddwt){
	  if(!checkWinAvailablity(ddwt)) return;
      //TBD ...
	  
	  click(ddw_next(),"Next >");	
	  
  }

	public void exeEndWith(String ddwt,String endWith){
		if(cancelTest) return;
		int num = 0;
		while(!checkWinAvailablity(ddwt)&&
				num++<5){
			if(ddw_next().exists()){
			   click(ddw_next(),"Next >");	
			}else if (ddw_cancel().exists()){
			   click(ddw_cancel(),"Cancel");	
			   click(ddw_yescancel(),"Yes");	
			   return;
			}else	sleep(1);
		}
		if(num>=5) //Final window doesn't appear
			return;
		
		if(endWith.equalsIgnoreCase("Cancel")&&ddw_cancel().exists()){
			 cancelDDW();
		}else if(ddw_finish().exists()){
			click(ddw_finish(),"Finish");	 
			sleep(1);
			if(finish_tablename().exists()){
			   actionOnText(finish_tablename(), "TableName",dpSaveTableAs, "New");
			   click(finish_ok(),"OK");
		
			   dLog.confirmAction("Yes",false); // for overwrite				   
			   dLog.confirmWarning("OK",false);   
			   //sleep(1);
			   if(tableLayout_View().exists()){
				   //if(dp)
				   logTAFStep("Close table lable layout view");
				   tableLayout_View().close();
			   }
			   logTAFStep("Verify - Itme and Data file");
			   kUtil.invokeMenuCommand("File->SaveProject");
			   aRou.searchSubitems(actualName);
			   if(dataFileCreated)
				   System.out.println("dpMasterFile:"+dpMasterFile.toString());
			      compareTextFile(dpMasterFile, dpActualFile,ProjectConf.updateMasterFile);
			}
		}
	}
	
	// DDW Wizard Error.
	public void cancelDDW(){
		
		if(ddw_cancel().exists()){
			click(ddw_cancel(),"Cancel");    
			dLog.confirmAction("Yes",true);
			cancelTest = true;
		}
	}
	
	public boolean checkWinAvailablity(GuiTestObject gto,String title){
		boolean found = false;

    	if(ddwWizardError("OK",false)){
              cancelDDW();
	    	 }
		if(cancelTest)
				return false;
		waitForExistence(gto);
		
		if(!propertyMatch(gto,".text",title)){
			logTAFDebug("'"+title+"' not found ");
		}else{
			found = true;
			logTAFStep(title);
		}
		return found;
	}
	
	public boolean checkWinAvailablity(String title){
		sleep(1);
      return checkWinAvailablity(DDW(),title);
	}

/*	public boolean saveDataFileMsg(String action){
		return saveDataFileMsg(action,true);
	}
*/
	public boolean saveDataFileMsg(String title,String action,boolean isInfo){
		//String msg = "Save Data File As";
		String msg = title;
		String winTitle = title;
		String className = "#32770";			
		boolean dismissed = false;
		sleep(1);
		dataFileCreated = true;
		if(getDialog(winTitle,className)!=null){
			msg = replace_msg().getProperty(".text").toString();
			if(msg.contains("replace it")){
				isInfo = true;
			}
			if(action.equalsIgnoreCase("Yes")){
		       clickOnObjectSafely(replace_yes(),action);
			}else {
				if(msg.contains("replace it"))
				  dataFileCreated = false;
			}
			sleep(2);
			if(getDialog(winTitle,className)==null){
				dismissed = true;
				if(!isInfo){
					logTAFError(msg);
				}else{
					logTAFInfo("Confirm - '"+msg+"' - '"+action+"'");
				}
			}else{
				logTAFDebug(msg+" dialog need to be handled!");
			}
		}else {
			logTAFDebug("There is no dialog for confirmation");
			return dismissed;
		}
		
		return dismissed;
	}	
	public boolean ddwWizardError(String action,boolean isInfo){
		String msg = "ACL Wizard Error";
		String winTitle = msg;
		String winTitle1 = LoggerHelper.autTitle;
		String className = "#32770";			
		boolean dismissed = false;
		int numSecond =0;
		
    	if(kUtil.checkACLCrash()){
    		cancelTest = true;
    		return true;
		}
		if(getDialog(winTitle,className)!=null){
			if(ddw_msg().exists())
			    msg = ddw_msg().getProperty(".text").toString();
			else if(ddw_msg1().exists()){
				msg = ddw_msg1().getProperty(".text").toString();
			}	
			else if(ddw_msgcancel().exists()){
				msg = ddw_msgcancel().getProperty(".text").toString();
			}	
			if(getEngValue(msg).contains(" continue ")){
				isInfo = true;
				action = "Yes";
			}
			if(action.equalsIgnoreCase("OK")){
				if(ddw_ok().exists())
		           clickOnObjectSafely(ddw_ok(),action);
				else if(ddw_ok1().exists()){
					clickOnObjectSafely(ddw_ok1(),action);
				}	
			}else if(action.equalsIgnoreCase("Yes")){
				clickOnObjectSafely(ddw_yescancel(),action);
			}
			//sleep(1);
			if(!isInfo){
				logTAFError(msg);
			}else{
				logTAFInfo("Confirm - '"+msg+"' - '"+action+"'");
			}
			sleep(1);
			if(getDialog(winTitle,className)==null){
				if(!action.equalsIgnoreCase("Yes"))
				   dismissed = true;

			}else{
				logTAFDebug(msg+" dialog need to be handled!");
			}
		}else 	if(getDialog(winTitle1,className)!=null){
			if(ddw_ok1().exists()){
				msg = "Invalid data in file";
			    //msg = ddw_info().getProperty(".text").toString();
			}else if(ddw_xmlcancel().exists()){
				do{
					sleep(2);
				}while(ddw_xmlcancel().exists()&&numSecond++<10);
				
				if(ddw_xmlcancel().exists()){
					click(ddw_cancel(),"Cancel");
					msg = "ACL took too long (more than 20 seconds) to examing the contents of file !";
					isInfo = true;
				}
			}	
			
			if(action.equalsIgnoreCase("OK")){
				if(ddw_ok1().exists()){
					clickOnObjectSafely(ddw_ok1(),action);
					sleep(1);
				}	
			}
			
			if(!isInfo){
				logTAFError(msg);
			}else{
				logTAFInfo("Confirm - '"+msg+"' - '"+action+"'");
			}
			if(getDialog(winTitle1,className)==null){
				if(!action.equalsIgnoreCase("Yes"))
				   dismissed = true;

			}else{
				logTAFDebug(msg+" dialog need to be handled!");
			}
		}else{
			logTAFDebug("There is no dialog for confirmation");
			return dismissed;
		}
		return dismissed;
	}	
}

