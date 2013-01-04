package ACL_Desktop.Tasks;

import java.io.File;

import lib.acl.helper.sup.LoggerHelper;
import lib.acl.util.FileUtil;
import resources.ACL_Desktop.Tasks.ddwmenuHelper;
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

public class ddwmenu extends ddwmenuHelper
{
	/**
	 * Script Name   : <b>ddwmenu</b>
	 * Generated     : <b>2012-02-09 4:47:23 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/02/09
	 * @author Steven_Xiang
	 */
	
	    private String[] fieldLens, otherSeps,otherQuas;
        private String ddwtext = "Data Definition Wizard";//,dpMasterFile,dpActualFile;
       // private String fileExt = ".fil",
        private String actualName;
        
        private boolean cancelTest = false;
        private boolean dataFileCreated = false,
                        itemCreated = false;
		// BEGIN of datapool variables declaration

		private String dpOpenProject ;   //@arg command for open acl project
		     //@value = 'Open|OpenCancel|OpenNew|OpenWorking|OpenLastSaved|OpenRecent|CreateNew', default to OpenNew
		private String dpEndWith ;  //@arg the end options for current test 
		                          //@value = 'Cancel|Finish' - Default to 'Finish'
		private String dpSelectPlatform ;  //@arg Platform of this test
		                                 //@value = 'Local|[ProfileName]', the profileName can be either 'AuditExhang_'which pointed to our ACLSEs
		                                 //@value   ' or the profile you want to use.
		private String dpDataSource;  //@arg Data Source of this test
		                          //@value = 'Disk|ODBC|External|FlatFiles|Database', default to Disk (Currently only 'Disk' available) 
		private String dpFileFormat ;   //@arg File Format
		         //@value = 'Access|Delimited|Excel|XML|Others'
		private String dpSelectFile;    //@arg relative/absolute path to the file
		private String dpCharacterSet ; //@arg Character set
		                     //@value = 'ASCII|EBDIC|Unicode|Encoded'
		private String dpWorksheet;       //@arg  name of the sheet or table (from DDW selection list) 
		private String dpMaxFieldsLength ;//@arg  field length
		                                  //@value = 'field chars length(width) and [field memo length length], separate by '|'
		private String dpStartOnLine;     //@arg  start on line 
		private String dpDetectType;       //@arg 'First100|Entire Excel' for Excel
		private String dpUseFirst;        //@arg  'ON|OFF' 
		private String dpFieldSeparator; //@arg  Field Separator
		                                 //@value =  'Comma|TAB|Semicolon|Other [and the chars]
		private String dpConsecutive; //@arg 'ON|OFF'
		private String dpTextQualifier; //@arg  Text Qualifier
		                                //@value = 'Double|Single|None|Other [and the chars]
		private String dpSaveFileAs;    //@arg relative/absolute path to save the resulted file
		
		private String dpSaveTableAs;   //@arg name of the resulted table
		
		private String dpRecordName;    //@arg record name for XML file
		private String dpAutoPreview;   //@arg option for XML, Yes or No, default to 'Yes'
		private String dpLengthOption;  //@arg option for Other
		                                //@value = 'Fixed|Variable|Skip', default to Fixed
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
			dpWorksheet = getDpString("Worksheet");
			dpMaxFieldsLength = getDpString("MaxFieldsLength");
			     fieldLens = dpMaxFieldsLength.split("\\|");

			dpStartOnLine = getDpString("StartOnLine");
			dpDetectType = getDpString("DetectType");
			dpUseFirst = getDpString("UseFirst");
			dpConsecutive = getDpString("Consecutive");
			dpFieldSeparator = getDpString("FieldSeparator");
                 otherSeps = dpFieldSeparator.split("\\|");
                 if(otherSeps.length<2||otherSeps[1].equals("Comma")){
                	 otherSeps = new String[2];
                	 otherSeps[0]= "Other";
                	 otherSeps[1] = ",";
                 }
			dpTextQualifier = getDpString("TextQualifier");
			     otherQuas = dpTextQualifier.split("\\|");
			dpSaveLocalOrServer = getDpString("SaveLocalOrServer");
			    if(dpSaveLocalOrServer.equals("")){
			    	dpSaveLocalOrServer = "Local";
			    }
			dpSaveFileAs = getDpString("SaveFileAs");
			   dpSaveFileAs = setupTestFiles(dpSaveFileAs,dpSaveLocalOrServer,fileExt=".fil");
//			    dpMasterFile = FileUtil.getAbsDir(dpSaveFileAs,ProjectConf.expectedDataDir);
//			    FileUtil.mkDirs(dpMasterFile);
//			    if(dpSaveLocalOrServer.equalsIgnoreCase("Local")){			    	
//			        dpSaveFileAs = FileUtil.getAbsDir(dpSaveFileAs,ProjectConf.tempLocalDir);			        
//			        FileUtil.mkDirs(dpSaveFileAs,delFile);
//			    } else{
//			    	dpSaveFileAs = FileUtil.getAbsDir(dpSaveFileAs,ProjectConf.tempServerDir);
//			    	FileUtil.mkDirs(FileUtil.getAbsDir(dpSaveFileAs,ProjectConf.tempServerNetDir),delFile);
//			    }
			    
			    dpActualFile = FileUtil.getFullNameWithExt(dpSaveFileAs,fileExt);
			    dpMasterFile = FileUtil.getFullNameWithExt(dpMasterFile,fileExt);
			dpSaveTableAs = getDpString("SaveTableAs");
			dpRecordName = getDpString("RecordName");
			dpAutoPreview = getDpString("AutoPreview");
			dpLengthOption = getDpString("LengthOption");
			                               
			dpBytesToSkip = getDpString("BytesToSkip");
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
				aRou.exeACLCommands(dpPreCmd);
				aRou.setACLFilters(dpPreFilter);
				kUtil.invokeMenuCommand("File->New->NewTable");
			}
            selectPlatform(ddwtext + " - Select Platform for Data Source");
            selectDataSource(ddwtext + " - Select .+ Data Source"); 
            
            //selectDataSource(ddwtext + " - Select .+"); 
            if(dpDataSource.matches("FlatFiles|Disk")){
        		selectFileToDefine("Select File to Define"); 
        		   //if format not realized 
     		    selectCharacterSet(ddwtext+" - Character Set");
        		selectFileFormat(ddwtext+" - File Format");
        		
            	if(dpFileFormat.matches("Excel")){
            		selectExcelSheet(ddwtext+" - Data Source");
            		excelImport(ddwtext+" - Excel Import");
            		saveDataFileAs("Save Data Data File As");
            	}else if(dpFileFormat.matches("Access")){
            		selectAccessTable(ddwtext+" - Data Source");           		
            		saveDataFileAs("Save Data Data File As");
            	}else if(dpFileFormat.matches("Delimited")){
            		delimitedFileProperties(ddwtext+" - Delimited File Properties");
            		//identifyProperties(ddwtext+" - File Properties");
            		saveDataFileAs("Save Data File As");
            		editFieldProperties(ddwtext+" - Edit Field Properties");
            	}else if(dpFileFormat.matches("XML")){
            		xmlImport(ddwtext+" - XML Import");
            		saveDataFileAs("Save Data File As");
            	}else if(dpFileFormat.matches("Other")){
            		identifyProperties(ddwtext+" - File Properties");
            		selectFileType(ddwtext+" - File Type");
            		if(dpFileType.equalsIgnoreCase("DataFile")){
            		    identifyFields(ddwtext+" - Identify Fields");            		
            		    editFieldProperties(ddwtext+" - Edit Field Properties");
            		}else if(dpFileType.equalsIgnoreCase("Report")){
            			recordDefinition(ddwtext+" - Record Definition Introduction");
            			identifyFields(ddwtext+" - Identify Fields (Detail)");   
            			editFieldProperties(ddwtext+" - Edit Field Properties (Detail)");
            		}else if(dpFileType.equalsIgnoreCase("MultipleRecord")){
            			recordDefinition(ddwtext+" - Record Definition Introduction");
            			identifyRecord(ddwtext+" - Identify Record/Line Types");   
            			//editFieldProperties(ddwtext+" - Edit Field Properties (Detail)");
            		}else if(dpFileType.equalsIgnoreCase("Skip")){
            		}
            	}else if(dpFileFormat.matches("AccPac")){
            		identifyFields(ddwtext+" - Identify Fields");            		
        		    editFieldProperties(ddwtext+" - Edit Field Properties");
            	}else if (dpFileFormat.matches("dBASE")) {
            		//No specific dialog needed for dBASE data type
            	} 
            }
//            selectFileType(ddwtext+" - File Type");
//            identifyFields(ddwtext+" - Identify Fields");
//                                   
            exeEndWith(ddwtext+" - Final",dpEndWith);
			
		}
        
		public void selectPlatform(String ddwt){
			
			waitForExistence(DDW());
			if(!checkWinAvailablity(ddwt)) 
				return;
			
			if(dpSelectPlatform.equalsIgnoreCase("Local")){
                actionOnCheckbox(sp_local(),"Local",true,"New");
			}else{
				actionOnCheckbox(sp_server(),"ACL Server",true,"New");				
				actionOnSelect(sp_profile(), "ACL Server Profile",dpSelectPlatform,"New");
			}
			
			click(ddw_next(),"Next >");	
			//click(findPushbutton(DDW(),"Next >"));	
			sleep(1);

		}
		
	    public void	selectDataSource(String ddwt){
	        kUtil.closeServerActivity();


	        if(!checkWinAvailablity(ddwt)) //ddwt is regexp which not support by localization here
	        	return;
//	        logTAFStep(ddwt);
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
			//click(findPushbutton(DDW(),"Next >"));	
			sleep(1);

	    }
	    
	    public void selectFileToDefine(String title){
	     	  if(ddwWizardError("OK",false)){
	              cancelDDW();
		    	 }
	     	  
	    	if(!sf_open().exists()){
	    		return;
	    	}
	    	File f = new File(dpSelectFile);	
	        String path = f.getParent();
	        String name = f.getName();
	        
	        
	    	if(dpDataSource.equalsIgnoreCase("Disk")){
	    		if(sf_localfilename().exists())
	    		    sf_localfilename().doubleClick();
	    		else if(sf_localfilename1().exists())
	    			sf_localfilename1().doubleClick();
		    	inputUnicodeChars(dpSelectFile);
	      	}else{
	      		sf_lookin().doubleClick();
		    	inputUnicodeChars(path);
	      		sf_serverfilename().click();
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
	    	if(ddw_selectfileok1().exists()){
	    		String msg = ddw_selectfilemsg1().getProperty(".name").toString();
	    		logTAFError(msg);
	    		click(ddw_selectfileok1(),"OK");
	    		click(sf_cancel(),"Cancel");
	    		cancelTest = true;
	    		cancelDDW();
	    	}
	    }
	    
	    public void saveDataFileAs(String title){
	     	  if(dismissPopup("OK|Yes",false)){
	     		  sleep(2);
	     		 if(!sf_save().exists()){
	              cancelDDW();
		    	 }
	     	  }
	     	  
	    	if(!sf_save().exists()){
	    		//
	    		return;
	    	}
	    	File f = new File(dpSelectFile);	
	        String path = f.getParent();
	        String name = f.getName();
	        
	    	if(dpSaveLocalOrServer.equalsIgnoreCase("Local")){
	    		if(sf_localfilename().exists())
	    		    sf_localfilename().doubleClick();
	    		else if(sf_localfilename1().exists())
	    			sf_localfilename1().doubleClick();
	    		//sf_localfilename().doubleClick();
		    	inputUnicodeChars(dpSaveFileAs);

	      	}else{
	      		sf_lookin().doubleClick();
		    	inputUnicodeChars(path);
	      		sf_serverfilename().click();
	      		inputUnicodeChars(name);
	    	}
	    	click(sf_save(),"Save");
	    	sleep(1);
	    	saveDataFileMsg("Yes",false);
	    	if(!handleProgressBar(progressInfoImport(),"DDW",dpTestProgressBar.equalsIgnoreCase("Yes")?true:false)){
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
    	 //click(findPushbutton(DDW(),"Next >"));	
    	 sleep(1);

      }
      public void selectFileFormat(String ddwt){
    	  if(!checkWinAvailablity(ddwt)) 
    		  return;
    	 if(dpFileFormat.equalsIgnoreCase("Access")) {
    		 actionOnCheckbox(ff_access(),"Access database",true,"New");
//    	 }else if(dpFileFormat.equalsIgnoreCase("AccPac")) {
//    		 click(ff_accpac(),"AccPac master file");
//    	 }else if(dpFileFormat.equalsIgnoreCase("dBASE")) {
//    		 click(ff_dbase(),"dBASE compatible file");
    	 }else if(dpFileFormat.equalsIgnoreCase("Delimited")) {
    		 actionOnCheckbox(ff_delimited(),"Delimited text file",true,"New");
    	 }else if(dpFileFormat.equalsIgnoreCase("Excel")) {
    		 actionOnCheckbox(ff_excel(),"Excel file",true,"New");
//    	 }else if(dpFileFormat.equalsIgnoreCase("PDF")) {
//    		 click(ff_pdf(),"");
//    	 }else if(dpFileFormat.equalsIgnoreCase("Report")) {
//    		 click(ff_printimage(),"");
//    	 }else if(dpFileFormat.equalsIgnoreCase("SAP")) {
//    		 click(ff_sap(),"");
//    	 }else if(dpFileFormat.equalsIgnoreCase("XBRL")) {
//    		 click(ff_xbrl(),"");
    	 }else if(dpFileFormat.equalsIgnoreCase("XML")) {
    		 actionOnCheckbox(ff_xml(),"XML file",true,"New");
    	 }else {
    		 actionOnCheckbox(ff_other(),"Other file format",true,"New");
    	 }
    	 
    	 click(ddw_next(),"Next >");
    	 //click(findPushbutton(DDW(),"Next >"));	
      }

      public void selectExcelSheet(String ddwt){
    	  if(!checkWinAvailablity(ddwt)) return;
    	  logTAFInfo("Select sheet '"+dpWorksheet+"'"	);
          sds_sheet().select(dpWorksheet);
          
    	  actionOnCheckbox(sds_firstrows(),"Use first row as Field Names",
    			  dpUseFirst.equalsIgnoreCase("ON")?true:false,"New");
    	  if(dpDetectType.equals("First 100")){
    		  actionOnCheckbox(sds_first100(),"First 100 records",
        			  true,"New");
    	  }else {
    		  actionOnCheckbox(sds_entire(),"Entire Excel Worksheet or Named Range",
    			  true,"New");
    	  }
    	  
    	  click(ddw_next(),"Next >");	
    	  //click(findPushbutton(DDW(),"Next >"));	
      }
      public void selectAccessTable(String ddwt){
    	  if(!checkWinAvailablity(ddwt)) return;
    	  logTAFInfo("Select table '"+dpWorksheet+"'"	);
          sds_sheet().select(dpWorksheet);
    	  if(fieldLens.length>0){
    		  actionOnText(sds_charlength(),"Maximum Character Field Length",
        			 fieldLens[0],"New");
    	  }
    	  if(fieldLens.length>1){
    		  actionOnText(sds_memolength(),"Maximum Memo Field Length",
         			 fieldLens[1],"New");
    	  }
    	  
    	  click(ddw_next(),"Next >");	
    	  //click(findPushbutton(DDW(),"Next >"));	
      }    
      
      public void delimitedFileProperties(String ddwt){
    	  if(!checkWinAvailablity(ddwt)) return;
    	  actionOnCheckbox(dfp_usefirst(),"Use first row as field names?",
    			 dpUseFirst.equalsIgnoreCase("ON")?true:false,"New");
    	  actionOnCheckbox(dfp_consecutive(),"Treat Consecutive qualifiers as one",
     			 dpConsecutive.equalsIgnoreCase("ON")?true:false,"New");
    	  actionOnText(dfp_start(),"Start on Line","dpStartOnLine","New");
    	  actionOnText(dfp_fieldwidth(),"Field Width",dpMaxFieldsLength,"New");
     	 if(dpFieldSeparator.equalsIgnoreCase("Comma")) {
    		 actionOnCheckbox(dfp_comma(),"Comma",true,"New");
    	 }else if(dpFieldSeparator.equalsIgnoreCase("TAB")) {
    		 actionOnCheckbox(dfp_tab(),"TAB",true,"New");
    	 }else if(dpFieldSeparator.equalsIgnoreCase("Semicolon")) {
    		 actionOnCheckbox(dfp_semicolon(),"Semicolon",true,"New");
    	 }else if(dpFieldSeparator.matches("Other.+")) {
    		 actionOnCheckbox(dfp_other(),"Other",true,"New");
    		 actionOnText(dfp_separator(),"Separator",otherSeps[1],"New");
    	 }else {
    		 //
    	 }
     if(dpTextQualifier.equalsIgnoreCase("Double Quote")) {
   		 actionOnCheckbox(dfp_doublequalifier(),"Double Quote",true,"New");
   	 }else if(dpTextQualifier.equalsIgnoreCase("Single Quote")) {
   		 actionOnCheckbox(dfp_singlequalifier(),"Single Quote",true,"New");
   	 }else if(dpTextQualifier.equalsIgnoreCase("None")) {
   		 actionOnCheckbox(dfp_nonequalifier(),"None",true,"New");
   	 }else if(dpTextQualifier.matches("Other.+")) {
   		 actionOnCheckbox(dfp_otherqualifier(),"Other",true,"New");
   		 actionOnText(dfp_qualifier(),"Qualifier",otherQuas[1],"New");
   	 }else {
   		 //
   	 }
    	 click(ddw_next(),"Next >");
    	 //click(findPushbutton(DDW(),"Next >"));	
      }

      public void xmlImport(String ddwt){
    	  if(!checkWinAvailablity(ddwt)) return;
    	  logTAFInfo("Select xml record '"+dpRecordName);
    	  import_xmltree().click(atPath(dpRecordName));
    	 // import_xmltree().click(atPath("RECORDS->RECORD"));
    	  click(import_xmladd(),"Add");
    	  actionOnCheckbox(import_xmlautoPreview(),"Auto Preview",
    			  dpAutoPreview.equalsIgnoreCase("Yes")?true:false,"New");
    	  click(ddw_next(),"Next >");	
    	  
    	  if(!checkWinAvailablity(ddwt)) return;
    	  //TBD ...
    	  click(ddw_next(),"Next >");	
    	  if(!checkWinAvailablity(ddwt)) return;
    	  //TBD ...
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
      
      public void identifyProperties(String ddwt){
    	  if(!checkWinAvailablity(ddwt)) return;
    	
    	  if(dpLengthOption.equalsIgnoreCase("Fixed")){
    	      actionOnCheckbox(fp_fixed(),"Fixed Length",
    			  true,"New");
          }else if(dpLengthOption.equalsIgnoreCase("Variable")){
    	      actionOnCheckbox(fp_variable(),"Variable Length",
        			  true,"New");
          }else if(dpLengthOption.equalsIgnoreCase("Skip")){
    	      actionOnCheckbox(fp_skip(),"Skip to Finish",
        			  true,"New");
          }else{
        	  
          }
    	  
    	  actionOnText(fp_bytes(),"Bytes to Skip",dpBytesToSkip,"New");
    	  actionOnText(fp_record(),"Record Length",dpMaxFieldsLength,"New");
    	  
    	  click(ddw_next(),"Next >");	
    	  
      }
      
      public void excelImport(String ddwt){
    	  if(!checkWinAvailablity(ddwt)) return;
    	  //TBD ...
    	  
    	  click(ddw_next(),"Next >");	
    	  
      }
      public void selectFileType(String ddwt){
       	  if(!checkWinAvailablity(ddwt)) return;
      	
    	  if(dpFileType.equalsIgnoreCase("DataFile")){
    	      actionOnCheckbox(ft_datafile(),"Data File",
    			  true,"New");
          }else  if(dpFileType.equalsIgnoreCase("Report")){
    	      actionOnCheckbox(ft_printimage(),"Print Image File",
        			  true,"New");
          }else if(dpFileType.equalsIgnoreCase("MultipleRecord")){
    	      actionOnCheckbox(ft_multiplerecord(),"Multiple Record Type File",
        			  true,"New");
          }else if(dpFileType.equalsIgnoreCase("Skip")){
    	      actionOnCheckbox(ft_skipfield(),"Skip Field Identification",
        			  true,"New");
          }else{
         
          }
    	  
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
				if(ddw_next().exists())
				   click(ddw_next(),"Next >");	
				else
					sleep(1);
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
			       itemCreated = true;
				   dLog.confirmAction("Yes",false); // for overwrite				   
				   dLog.confirmWarning("OK",false);   
 
				   //sleep(1);
				   if(tableLayout_View().exists()){
					   //if(dp)
					   logTAFStep("Close table layout view");
					   tableLayout_View().close();
				   }
				   logTAFStep("Verify - Itme and Data file");
				   kUtil.invokeMenuCommand("File->SaveProject");
				   if(itemCreated)
				      aRou.searchSubitems(actualName);
				   if(dataFileCreated)
				      compareTextFile(dpMasterFile, dpActualFile,ProjectConf.updateMasterFile);
				}
			}
			
			
			

		}

		
		// DDW Wizard Error.
		public void cancelDDW(){
			
			if(ddw_cancel().exists()){
				click(ddw_cancel(),"Cancel");    
				sleep(2);
				dismissPopup("Data Definition Wizard","Yes",true);
				cancelTest = true;
			}
		}
		public boolean checkWinAvailablity(GuiTestObject gto){
		  return checkWinAvailablity(gto,"");
		}
		public boolean checkWinAvailablity(GuiTestObject gto,String title){
			
			boolean found = false;
//        	if(title.contains(" - Select .+ Data Source")){
//			    sleep(0);
//		    }
	    	if(ddwWizardError("OK",false)){
	              cancelDDW();
		    	 }
			if(cancelTest)
					return false;
			waitForExistence(gto);
			
			if(title.equals("")){
				return true;
			}
			if(!propertyMatch(gto,".text",title)){
				logTAFDebug("'"+title+"' not found? ");
				sleep(0);
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

		public boolean saveDataFileMsg(String action){
			return saveDataFileMsg(action,true);
		}
		public boolean saveDataFileMsg(String action,boolean isInfo){
			String msg = "Save Data File As|Confirm Save As";
			String winTitle = msg;
			String className = "#32770";			
			boolean dismissed = false;
			sleep(1);
			dataFileCreated = true;
			if(getDialog(winTitle,className)!=null){
				if(replace_msg().exists())
				   msg = replace_msg().getProperty(".text").toString();
				else if(replace_msg1().exists())
				   msg = replace_msg1().getProperty(".text").toString();
				
				if(msg.contains("replace it")){
					isInfo = true;
				}
				if(action.equalsIgnoreCase("Yes")){
					if(replace_yes().exists())
                        clickOnObjectSafely(replace_yes(),action);
					else if(replace_yes1().exists())
						clickOnObjectSafely(replace_yes1(),action);
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
	    	
	    	//dismissPopup(String winClass, String winTitle,String userAction,boolean isInfoUser, boolean loop,int maxCheck,String expInfo)
	    	dismissed = dismissPopup(className,winTitle+"|"+winTitle1,action,
	    			isInfo, false, 1," continue");
//				dismissPopup("#32770","ACL Wizard Error","OK",
//						     false,false,1, " continue");
				if(getDialog(winTitle1,className)!=null&&ddw_xmlcancel().exists()){
					do{
						sleep(2);
					}while(ddw_xmlcancel().exists()&&numSecond++<10);
					
					if(ddw_xmlcancel().exists()){
						click(ddw_cancel(),"Cancel");
						dismissed = true;
						msg = "ACL took too long (more than 20 seconds) to examing the contents of file !";
						isInfo = true;
					}
				}	

			return dismissed;
		}	
		public TestObject getProgressInfo(){
			return progressInfoImport();
		}
	}