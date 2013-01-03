package ACL_Desktop.AppObject;

import java.awt.Point;

import resources.ACL_Desktop.AppObject.aclDataDialogsHelper;
import ACL_Desktop.conf.beans.ProjectConf;

import com.rational.test.ft.object.interfaces.GuiTestObject;
import com.rational.test.ft.object.interfaces.IWindow;
import com.rational.test.ft.object.interfaces.SelectGuiSubitemTestObject;
import com.rational.test.ft.object.interfaces.TestObject;
import com.rational.test.ft.object.interfaces.ToggleGUITestObject;
import com.rational.test.ft.script.Action;
import com.rational.test.ft.script.Subitem;

public class aclDataDialogs extends aclDataDialogsHelper
{
	/**
	 * Script Name   : <b>aclDataDialogs</b>
	 * Generated     : <b>Mar 5, 2012 11:57:05 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/05
	 * @author Steven_Xiang
	 */
	


	//**********************************************************************************************//
	//Relations - ************************************************************************ Relations//
	//*********************************************************************************************//

	//*****************************************************************************************************//	
	public boolean setRelations(
			String parentTable,
			String parentKey,
			String childTable,
			String childTableIndex,
			String childKey){
		return setRelations(parentTable,
				parentKey,
				childTable,
				childTableIndex,
				childKey,
				false);
		}
		public boolean setRelations(
				String parentTable,
				String parentKey,
				String childTable,
				String childTableIndex,
				String childKey,
				boolean isInfo){
			boolean itemCreated = false;
			int numChildren = -1;
			String prname = getLocValue("%s (Parent)").replaceAll("%s", parentTable);
			String crname = childTable,cname1;
			String headerName = "Name"; // this should be localized... Steven
			String[] headerKey = {"",headerName};
			TestObject[] tables;
			SelectGuiSubitemTestObject parent, child;
			Point firstRow = atPoint(50,30),
			      cScreenPoint;
			int parentIndex, childIndex;
			
			try{
			   numChildren = Integer.parseInt(
					   relations_innerwin().getProperty(".numChildren").toString());
			}catch(Exception e){
				
			}
			if(childTable.length()>31){
				crname = childTable.substring(0,31);
			}
			
			if(numChildren>6||numChildren==-1)
				relationswin().maximize();
			if(!childTableIndex.equals("")){
				cname1=childTable+childTableIndex;
				if(cname1.length()>34){
					cname1 = cname1.substring(0,34);
				}
				crname =cname1+" ("+crname+")";
			}
			tables = relations_innerwin().find(atList(
					atChild(".class","#32770",".text",crname)
			        )
			        );
			if(tables!=null&&tables.length>0){
				child = new SelectGuiSubitemTestObject(tables[0].find(
						atChild(".class","SysListView32"))[0]);
			}else{
			    itemCreated = addTable(childTable,childTableIndex);
				
				tables = relations_innerwin().find(atList(
						atChild(".class","#32770",".text",crname)
				        )
				        );
				if(tables!=null&&tables.length>0){
					child = new SelectGuiSubitemTestObject(tables[0].find(
							atChild(".class","SysListView32"))[0]);
				}else{
					logTAFError("Child table '"+crname +"' not found");
					         dismissPopup("Name Relation","Cancel",false);
					click(relation_finish(),"Finish");
					return false;
				}
				
			}
			
			tables = relations_innerwin().find(atList(
					atChild(".class","#32770",".text",prname)
			        )
			        );
			if(tables!=null&&tables.length>0){
				
				parent = new SelectGuiSubitemTestObject(tables[0].find(
						atChild(".class","SysListView32"))[0]);
			}else{
				logTAFError("Parent table '"+prname +"' not found");
				return false;
			}
		    //parent.setState(atText(parentKey),Action.select());
			//printObjectTree(parent);

			headerKey[0] = parentKey;
			parentIndex = DesktopSuperHelper.selectSomeFields(parent,headerKey)[0];
			headerKey[0] = childKey;
			childIndex = DesktopSuperHelper.selectSomeFields(child,headerKey)[0];
			headerName = headerKey[1];
			
			Subitem pcell = atCell(atRow(atIndex(parentIndex)),atColumn(headerName));
			Subitem ccell = atCell(atRow(atIndex(childIndex)),atColumn(headerName));
			
			
			if(childIndex ==0){
				cScreenPoint = child.getScreenPoint(firstRow);
			}else{
				cScreenPoint = child.getScreenPoint(ccell);
				
			}
			
			
			if(parentIndex == 0){
				parent.dragToScreenPoint(firstRow,cScreenPoint);
				
			}else{
				parent.dragToScreenPoint(pcell,cScreenPoint);
				
			}

			   //dLog.confirmWarning("Yes",isInfo);
			dismissPopup("OK",isInfo);
			   //dLog.confirmWarning("Yes",true);
			//sleep(2);
			click(relation_finish(),"Finish");
			return itemCreated;
			
		}
		public boolean addTable(String table,String tableIndex){
			boolean itemCreated = false,done=true;
			IWindow iw;
			click(relation_addTable(),"Add Table");
			sleep(1);
			if(!table.equals("")){
			actionOnSelect(addTable_list(),"Select Table",table,"New");
			addTable_add().waitForExistence();
			click(addTable_add(),"Add");
			sleep(1);
			}
			//if(nameRelationwin().exists()){
			if((iw=getDialog("Name Relation","#32770",true))!=null){
				iw.activate();
				if(tableIndex.equals("")){
					logTAFError("You need to specify the index of '"+table+"' in your data pool for this test");
					click(nameRelation_cancel(),"Cancel");
					done = false;
				}else{
					nameRelation_text().waitForExistence();
					actionOnText(nameRelation_text(),"Name of Relation",table+tableIndex,"New");	
					nameRelation_ok().waitForExistence();
					click(nameRelation_ok(),"OK");
					
					sleep(1);
					dismissPopup("Name Relation","OK",true);
					//if(nameRelationwin().exists()){
					if((iw=getDialog("Name Relation","#32770"))!=null){
						iw.activate();
							logTAFError("You specified wrong index for '"+table+"' in your data pool for this test?");
							click(nameRelation_cancel(),"Cancel");
							done = false;
						}else{
							itemCreated = true;
						}
				}
			}
			
			sleep(1);
			click(addTable_close());
			return itemCreated;
			
		}
		
		public void testMain(Object[] args) 
		{
			// TODO Insert code here
		}
	}


	
	
	
	
	//protected dialogUtil dLog = new dialogUtil();

//
//	public boolean mainTab(DesktopSuperHelper dsh,
//			            String command,
//			            String fields,
//			            String ifexp,
//			            String filename,
//			            //----- Create Index
//			            String localOrServer,
//			            String useOutputTable,
//			            //----- Export to App
//			            String exportAs,
//			            String withFieldNames,
//			            String toUnicode,
//			            String columnSeparator,
//			            String textQualifier
//			            ){
//		Point mainPoint = tabPoint[1];
//		
//		sleep(1);
//		if(!mainWin().exists()){
//			logTAFError("'Main' window not found");
//			return false;
//		}
//		click(mainWin(),mainPoint,"Main");		
//		
//		//Step Select fields
//		selectFields(fields);
//        //Step Set test files
//		if(command.equalsIgnoreCase("ExtractData")){
//			localOrServer = setToLocal(localOrServer,mainExtract_local());
//			if(!useOutputTable.equals("")){
//				actionOnCheckbox(mainExtract_useOutputTable(),"Use Output Table",
//						   useOutputTable.equalsIgnoreCase("Yes")?true:false,"New");
//			}
//		}else{
//			
//		  localOrServer = setToLocal(localOrServer,main_local());	
//		  if(!useOutputTable.equals("")){
//				actionOnCheckbox(main_useOutputTable(),"Use Output Table",
//						   useOutputTable.equalsIgnoreCase("Yes")?true:false,"New");
//	    	}
//		}
//		//Step If and To...
//		if(command.equalsIgnoreCase("CreateIndex")){
//			dsh.fileExt = ".INX";
//		}else if(command.equalsIgnoreCase("ExportToApp")){
//			localOrServer = "Local";
//		    dsh.fileExt = selectExportAs(exportAs,withFieldNames,columnSeparator,textQualifier,
//		    		                     toUnicode);
//		    actionOnText(mainExport_if(),"If Condition",ifexp,"New");		
//	        if(!dsh.fileExt.equals(""))
//				actionOnText(mainExport_totext(),		
//					"To file", 
//					dsh.setupTestFiles(filename,
//							localOrServer,
//							dsh.fileExt),
//					"New");
//		}else if(command.equalsIgnoreCase("ExtractData")){
//			actionOnText(mainExtract_iftext(),"If Condition",ifexp,"New");		
//	        if(!dsh.fileExt.equals(""))
//				actionOnText(mainExtract_totext(),		
//					"To file", 
//					dsh.setupTestFiles(filename,
//							localOrServer,
//							dsh.fileExt),
//					"New");
//		}else{
//			actionOnText(main_iftext(),"If Condition",ifexp,"New");		
//	        if(!dsh.fileExt.equals("")&&!filename.equals(""))
//				actionOnText(main_totext(),		
//					"To file", 
//					dsh.setupTestFiles(filename,
//							localOrServer,
//							dsh.fileExt),
//					"New");
//		}
//		
//		return true;
//	}
//    
//	public String selectExportAs(String exportAs,String withFieldNames,
//			                     String columnSeparator,String textQualifier,String toUnicode){
//		String[] fileExts = {".mdb","","dbf",".del",
//				             ".xlsx",".xls","xls",
//				             ".txt",".xml"};
//		String[] appName = {"Access","Clipboard","dBase III Plus","Delimited",
//	             "Excel 07-2010","Excel 97-2003","Excel 2.1",
//	             "Text","XML"};
//		int opIndex = 7; // Default to Text
//		boolean found = false;
//		if(exportAs.equals("")){
//			exportAs = appName[opIndex];
//		}
//		for(int i=0;i<appName.length;i++){
//			if(appName[i].equalsIgnoreCase(exportAs)){
//				opIndex = i;
//				if(!fileExts.equals("")){
//					exportAs += " (*"+fileExts[opIndex]+")";
//				    found = true;
//				}
//			}
//		}
//		if(!found)
//	        logTAFError("ACL does support - Export as '"+exportAs+"'");
//		
//		actionOnSelect(main_exportas(),"Export As",exportAs,"New");
//		if(!withFieldNames.equals(""))
//		     actionOnCheckbox(main_withFieldNames(),"Export with field names",
//				   withFieldNames.equalsIgnoreCase("Yes")?true:false,"New");
//		if(!columnSeparator.equals("")){
//			actionOnSelect(main_columnseparator(),"Column Separator",columnSeparator,"New");
//		}
//		if(!textQualifier.equals("")){
//			actionOnSelect(main_textqualifier(),"Text Qualifier",textQualifier,"New");
//		}
//		selectUnicode(toUnicode);
//		return fileExts[opIndex];
//	}
//	
//    public void selectUnicode(String toUnicode){
//        if(ProjectConf.unicodeTest){
//        	if(main_unicode().exists()){
//        		 actionOnCheckbox(main_unicode(),"Unicode",
//      				   toUnicode.equalsIgnoreCase("TRUE")?true:false,"New");
//        	}else{
//        		logTAFError("Unicode ACL should support this Unicode option");
//        	}
//        }else{
//        	if(main_unicode().exists()){
//        		logTAFError("NonUnicode ACL should not support this Unicode option");
//        	}
//		
//			
//		}
//	}
////    public String setToLocal(String localOrServer){
////    	return setToLocal(localOrServer,main_local());
////    }
//	public String setToLocal(String localOrServer, ToggleGUITestObject tgo){
//		boolean enabled = false,selected=false;
////        ToggleGUITestObject tgo;
////        if(tab.equalsIgnoreCase("Main")){
////        	tgo = main_local();
////        }else if(tab.equalsIgnoreCase("Output")){
////        	tgo = output_local();
////        }else{
////        	// For other possible situation
////        	  tgo = main_local();
////        }
//		if(!tgo.exists()){
//			return "Local";  
//		}
//		
//		selected = isChecked(tgo);
//		enabled = isEnabled(tgo);
//
//		if(localOrServer.equals("")||localOrServer.equals("TBD")){
//			if(selected){
//				localOrServer = "Local";
//			}else{
//				localOrServer = "Server";
//			}
//		}else if(enabled){
//			actionOnCheckbox(tgo,"Local", 
//					localOrServer.equalsIgnoreCase("Local")?true:false,"New");
//		}
//		
//		return localOrServer;
//
//	}
//	public void selectFields(String fields){
//		boolean done = true;
//		if(fields.equalsIgnoreCase("View")){
//			actionOnCheckbox(main_view(),"View",true,"New");
//		}else if(fields.equalsIgnoreCase("Record")){
//			actionOnCheckbox(main_record(),"Record",true,"New");
//		}else if(//fields.equalsIgnoreCase("Fields")&&
//		                main_fields().exists()){
//			actionOnCheckbox(main_fields(),"Fields",true,"New");
//			done = false;
//		}else{
//			done = false;
//		}
//		
//		if(fields.equalsIgnoreCase("Add All")){
//			click(main_onfields(),"Select Fields On...");
//			selectedFields("",fields,"OK");
//		}else if(!done){
//			DesktopSuperHelper.selectSomeFields(main_fieldstable(),fields);
//		}
//	}
//	
//	public void selectedFields(String fromTable,String fields,String endWith){
//
//		if(!fromTable.equals("")){
//			//Select from table
//		}
//		if(fields.equalsIgnoreCase("Add All")){
//				click(seFields_addall(),fields);
//		}else if(fields.equalsIgnoreCase("Clear")){
//				click(seFields_clearall(),fields);
//		}else{
//			    // TBD
//		}
//		
//		if(endWith.equalsIgnoreCase("OK")){
//			click(exp_ok(),"OK");
//		}else{
//			click(exp_cancel(),"Cancel");
//		}
//	}
//	
//
//
//	// FOR MORE TAB
//	public boolean moreTab( String command,
//			               String scope,
//			               String wCondition,
//			               String eof, // or error limit for virify
//			               String append
//			              ){
//		sleep(1);
//		
//		
//		if(!mainWin().exists()){
//			logTAFError("'More' window not found");
//			return false;
//		}
//		Point morePoint = tabPoint[2];
//		
//		click(mainWin(),morePoint,"More");
//
//		String scopeArr[] = scope.split("\\|");
//		if(scopeArr[0].equals("All")){
//			actionOnCheckbox(more_all(),"All",true,"New");
//		}else if(scopeArr[0].equals("First")){
//			actionOnCheckbox(more_first(),"First",true,"New");
//			if(scopeArr.length>1){
//				//actionOnText(mainIf_moreFirst_text(),"First",scopeArr[1],"New");
//				actionOnText(more_firsttext(),"First",scopeArr[1],"New");
//			}
//		}else if(scopeArr[0].equals("Next")){
//			actionOnCheckbox(more_next(),"Next",true,"New");
//			if(scopeArr.length>1){
//				actionOnText(more_nexttext(),"Next",scopeArr[1],"New");
//			}
//		}		
//		//actionOnText(mainTo_moreWhile_text(),"While",wCondition,"New");
//		actionOnText(more_whiletext(),"While",wCondition,"New");
//		if(!eof.equalsIgnoreCase("")){
//			if(command.equalsIgnoreCase("Verify")){
//				actionOnText(more_errorLimitText(),"Error Limit",eof,"New");
//			}else{
//			actionOnCheckbox(more_eof(),"EOF(End of file processing)",
//					eof.equalsIgnoreCase("Yes")?true:false,"New");
//			}
//		}
//		if(!append.equalsIgnoreCase("")){
//			actionOnCheckbox(more_append(),"Append To Existing File",
//					append.equalsIgnoreCase("Yes")?true:false,"New");
//		}
//		return true;
//	}
//	
//	//FOR OUTPUT TAB
//	
//    public boolean outputTab(DesktopSuperHelper dsh,
//    		String command,
//			String to,
//			String fileType,
//			String filename,
//			String localOrServer,
//			String outputHeader,
//			String outputFooter
//	){
//      int defaultTabIndex = 3;
//      return outputTab(dsh,
//    		  command,
//    		defaultTabIndex,
//			to,
//			fileType,
//			filename,
//			localOrServer,
//			outputHeader,
//			outputFooter);
//    }
//    
//	public boolean outputTab(DesktopSuperHelper dsh,
//			String command,
//			int tabIndex,
//			String to,
//			String fileType,
//			String filename,
//			String localOrServer,
//			String outputHeader,
//			String outputFooter
//	){
//		Point outputPoint = tabPoint[tabIndex];
//
//		sleep(1);
//		if(!mainWin().exists()){
//			logTAFError("'Main' window not found");
//			return false;
//		}
//		click(mainWin(),outputPoint,"Output");		
//
//			
//
//		//To && As
//		if(to.equals("File")){
//			actionOnCheckbox(output_file(),"To File", 
//					true,"New");
//			//localOrServer = setToLocal(localOrServer);	
//
//			if(fileType.matches("Unicode Text File|ASCII Text File")){
//				dsh.fileExt = ".TXT";
//			}else if(fileType.equals("HTML Text File")){
//				dsh.fileExt = ".HTM";
//			}else{
//				
//			}
//			if(!fileType.equals("")){
//				actionOnSelect(output_filetype(),"File Type",
//						fileType,"New");
//			}
//			
//		}else if(to.equals("Graph")){
//			actionOnCheckbox(output_graph(),"To Graph", 
//					true,"New");
//		}else if(to.equals("Screen")){
//			actionOnCheckbox(output_screen(),"To Screen", 
//					true,"New");
//		}else if(to.equals("Print")){
//			actionOnCheckbox(output_print(),"To Print", 
//					true,"New");
//		}		
//
//		localOrServer = setToLocal(localOrServer,output_local());	
//	    filename = dsh.setupTestFiles(filename,
//				localOrServer,
//				dsh.fileExt);
//	    
//		if(to.matches("File")){
//			actionOnText(output_name(),		
//					"To file", 
//					filename,
//			"New");
//		}
//		//Optional
//        if(!outputHeader.equals("")){
//        	if(output_header().exists()&&isEnabled(output_header()))
//		       actionOnText(output_header(),"Header",outputHeader,"New");	
//        	else{
//        		logTAFError("Header option is not enabled");
//        	}
//       }
//       if(!outputFooter.equals("")){
//    	   if(output_footer().exists()&&isEnabled(output_footer()))
//    		   actionOnText(output_footer(),"Footer",outputFooter,"New");	
//        	else{
//        		logTAFError("Footer option is not enabled");
//        	}
//		     
//      }
//		return true;
//	}	
//	
//	public boolean fileAction(String action){
//		boolean isInfo = false;
//		return fileAction(action, isInfo);
//	}
//
//	public boolean fileAction(String action,boolean isInfo){
//		return endWithAction(mainWin(),action,isInfo);
////		boolean filecreated = false;
////		if(action.matches("OK|Finish")){
////			click(main_ok(),"OK");
////			if(foundError()
////					&&main_cancel().exists()){
////				click(main_cancel(),"Cancel");
////				filecreated = false;
////			}else{
////			   filecreated = true;
////			   dLog.confirmWarning("Yes",isInfo);
////			   dLog.confirmWarning("OK",true);
////			   dLog.confirmWarning("Yes",true);
////			}
////			
////			
////		}else if(action.equals("Cancel")){
////			click(main_cancel(),"Cancel");
////			filecreated = false;
////		}
////		return filecreated;
//	}
//	
//	public boolean foundError(){
//		String title = "Error",
//		       action = "OK";
//		boolean isInfo = false;
//		return foundError(title,action,isInfo);
//	}
//	public boolean foundError(String title,String action,boolean isInfo){
//		return dismissPopup(action,isInfo);
//		
//		
//		
////		String msg = "ACL "+title;
////		String winTitle = title;
////		String className = "#32770";	
////		boolean dismissed = false;
////		sleep(1);
////
////		if(getDialog(winTitle,className)!=null){
////			if(error_msg().exists()){
////			   msg = error_msg().getProperty(".name").toString();
////			}else if(error_msg().exists()){
////				//leave it for win7
////			   msg = error_msg().getProperty(".name").toString();
////			}
////			
////			if(action.equalsIgnoreCase("OK")){
////				if(error_ok().exists())
////                    clickOnObjectSafely(error_ok(),action);
////				else if(error_ok().exists())
////					//for win7
////					clickOnObjectSafely(error_ok(),action);
////			}else {
////				
////			}
////			sleep(1);
////			if(getDialog(winTitle,className)==null){
////				dismissed = true;
////				if(!isInfo){
////					logTAFError(msg);
////				}else{
////					logTAFInfo("Confirm - '"+msg+"' - '"+action+"'");
////				}
////			}else{
////				logTAFDebug(msg+" dialog need to be handled!");
////			}
////		}else {
////			logTAFDebug("There is no error found");
////			return dismissed;
////		}
////		
////		return dismissed;
//	}	
//
//	// Alternation 
//	public boolean mainTab_Merge(DesktopSuperHelper dsh,
//			String command,
//			String pkeys,
//			String stable,
//			String skeys,	
//			String localOrServer,
//			String useoutputtable,
//			String ifexp,
//			String filename,
//			String presort
//	){
//
//	 boolean done =  mainTab_Join(dsh,
//			command,
//			pkeys,
//			"",
//			"",
//			stable,
//			skeys,
//			"",
//			"",
//			localOrServer,
//			useoutputtable,
//			ifexp,
//			filename);
//		if(!presort.equals("")){
//			actionOnCheckbox(mainMerge_presort(),"Presort", 
//					presort.equalsIgnoreCase("Yes")?true:false,"New");
//		}	
//		return done;
//	}
//	public boolean mainTab_Join(DesktopSuperHelper dsh,
//			String command,
//			String pkeys,
//			String pfields,
//			String ppresort,
//			String stable,
//			String skeys,
//			String sfields,
//			String spresort,
//			String localOrServer,
//			String useoutputtable,
//			String ifexp,
//			String filename
//	){
//		Point mainPoint = tabPoint[1];
//
//		sleep(1);
//		if(!mainWin().exists()){
//			logTAFError("'Main' window not found");
//			return false;
//		}
//		click(mainWin(),mainPoint,"Main");		
//		
//		//Step Select Primary keys/fields/presort
//		if(!pkeys.equals("")){
//			logTAFStep("Select Primary Keys");
//			if(pkeys.equalsIgnoreCase("Add All")){
//				click(mainJoin_pkeys(),"Primary Keys...");
//				selectedFields("",pkeys,"OK");
//			}else{
//				DesktopSuperHelper.selectSomeFields(mainJoin_pkeysTable(),pkeys);
//			}
//		}
//		if(!pfields.equals("")){
//			logTAFStep("Select Primary Fields");
//			if(pfields.equalsIgnoreCase("Add All")){
//				click(mainJoin_pfieldsTable(),"Primary Fields...");
//				selectedFields("",pfields,"OK");
//			}else {
//				DesktopSuperHelper.selectSomeFields(mainJoin_pfieldsTable(),pfields);
//			}
//		}
//		
//		if(!ppresort.equals("")){
//			actionOnCheckbox(mainJoin_ppresort(),"Presort Primary Table", 
//					ppresort.equalsIgnoreCase("Yes")?true:false,"New");
//		}	
//		//Step Select Secondary keys/fields
//		logTAFStep("Select Secondary Table");
//		actionOnSelect(mainJoin_stable(),"Secondary Table",stable,"New");
//		if(!skeys.equals("")){
//			logTAFStep("Select Secondary Keys");
//			if(skeys.equalsIgnoreCase("Add All")){
//				click(mainJoin_skeys(),"Secondary Keys...");
//				selectedFields("",skeys,"OK");
//			}else{
//				if(pfields.equals("")){
//				  DesktopSuperHelper.selectSomeFields(mainJoin_pfieldsTable(),skeys);
//				}else{
//				  DesktopSuperHelper.selectSomeFields(mainJoin_skeysTable(),skeys);
//				}
//			}
//		}
//
//		if(!sfields.equals("")){  
//			logTAFStep("Select Secondary Fields");
//			if(sfields.equalsIgnoreCase("Add All")){
//				click(mainJoin_sfieldsTable(),"Secondary Fields...");
//				selectedFields("",sfields,"OK");
//			}else {
//				DesktopSuperHelper.selectSomeFields(mainJoin_sfieldsTable(),sfields);
//			}
//		}
//		if(!spresort.equals("")){
//			actionOnCheckbox(mainJoin_spresort(),"Presort Secondary Table", 
//					spresort.equalsIgnoreCase("Yes")?true:false,"New");
//		}	
//
//
//		//Step Set test files
//		localOrServer = setToLocal(localOrServer,mainJoin_local());		
//
//		//Step If and To...
//
//		actionOnText(mainJoin_if(),"If Condition",ifexp,"New");		
//		if(!dsh.fileExt.equals(""))
//			actionOnText(mainJoin_to(),		
//					"To file", 
//					dsh.setupTestFiles(filename,
//							localOrServer,
//							dsh.fileExt),
//			"New");
//
//		return true;
//
//	}
//	public boolean moreTab_Simple( String command,
//			String scope,
//			String wCondition,
//			String append
//
//	){
//		sleep(1);
//		if(!mainWin().exists()){
//			logTAFError("'More' window not found");
//			return false;
//		}
//		Point morePoint = tabPoint[2];
//
//		click(mainWin(),morePoint,"More");
//
//		String scopeArr[] = scope.split("\\|");
//		if(scopeArr[0].equals("All")){
//			actionOnCheckbox(moreJoin_all(),"All",true,"New");
//		}else if(scopeArr[0].equals("First")){
//			actionOnCheckbox(moreJoin_first(),"First",true,"New");
//			if(scopeArr.length>1){
//				actionOnText(moreJoin_firsttext(),"First",scopeArr[1],"New");
//			}
//		}else if(scopeArr[0].equals("Next")){
//			actionOnCheckbox(moreJoin_next(),"Next",true,"New");
//			if(scopeArr.length>1){
//				actionOnText(moreJoin_nexttext(),"Next",scopeArr[1],"New");
//			}
//		}		
//		actionOnText(moreJoin_whiletext(),"While",wCondition,"New");
//		return true;
////		return moreTab_Join(dsh,command,
////				scope,wCondition,
////				"","","",
////				append);
//	}
//	public boolean moreTab_Join( DesktopSuperHelper dsh,String command,
//			String scope,
//			String wCondition,
//			String joinCategory,
//			String includeAPR,
//			String includeASR,
//			String append
//
//	){
//		boolean done = moreTab_Simple(command,
//				scope,
//				wCondition,
//				append);
//
//		//Join Categories
//		
//		if(joinCategory.equalsIgnoreCase("Matched")){
//			actionOnCheckbox(moreJoin_matchedPrimary(),"Matched Primary Records",
//					true,"New");
//		}else if(joinCategory.equalsIgnoreCase("Unmatched")){
//			actionOnCheckbox(moreJoin_unmatchedPrimary(),"Unmatched Primary Records",
//					true,"New");
//		}else if(joinCategory.equalsIgnoreCase("Many-to-Many")){
//			actionOnCheckbox(moreJoin_manyToMany(),"Many-to-Many Matched Records",
//					true,"New");
//		}
//		if(!includeAPR.equals("")){
//			actionOnCheckbox(moreJoin_includeAllPrimary(),"Include All Primary Records",
//					true,"New");
//		}
//		if(!includeASR.equals("")){
//			actionOnCheckbox(moreJoin_includeAllSecondary(),"Include All Secondary Records",
//					true,"New");
//		}
//		
//		//Append to file
////		if(!append.equalsIgnoreCase("")){
////			actionOnCheckbox(moreJoin_append(),"Append To Existing File",
////					append.equalsIgnoreCase("Yes")?true:false,"New");
////		}
//		return done;
//	}
//


	//**********This is a duplication implementation will be removed later-- Steven
//	public int[] selectSomeFields(SelectGuiSubitemTestObject sgto, String fields){
//	Point firstRow = atPoint(50,30),
//	      upbarbutton = getGuiRelativePoint(sgto, "topright", atPoint(-12,12));
//    String fieldsArr[] = fields.split("\\|");
//    int[] rowIndex = new int[fieldsArr.length];
//    
//    String headerName = "Name";
//    boolean swaped = false;
//    //printObjectTree(main_fieldstable());
//    
//	for(int i=0;i<fieldsArr.length;i++){
//		rowIndex[i] = searchTableRowByText(sgto,
//				headerName,fieldsArr[i]);
//		
//		if(rowIndex[i]<0){
//			logTAFError("Field not found - '"+fieldsArr[i]+"'");
//			continue;
//		}
//        if(i!=0&&(rowIndex[i]==0)){ // Force the first row to be selected as the first 
//        	rowIndex[i] = rowIndex[0];
//        	String temp = fieldsArr[i];
//        	fieldsArr[i] = fieldsArr[0];
//        	fieldsArr[0] = temp;
//        	rowIndex[0] = 0;
//        	swaped = true;
//        }
//	}
//	//printObjectTree(main_fieldstable());
//	for(int i=0,curIndex=0;i<rowIndex.length;i++){
//		if(rowIndex[i]<0){
//			continue;
//		}
//		logTAFInfo("Select field "+(i+1)+": '"+fieldsArr[i]+"' at Row: '"+(rowIndex[i]+1)+"'");		
//		Subitem cell = atCell(atRow(atIndex(rowIndex[i])),atColumn(headerName));
//
//		if(i==0){
//            if(rowIndex[i]==0){
//            	//It's a workaround for the selection of the first row 
//            	//To ensure works, the firstRow must to be selected as the first
//            	if(swaped){
//            		logTAFDebug("This is a workaround for the issue on select first row");
//            	}
//            	//sgto.doubleClick(firstRow);
//            	sgto.click(firstRow);
//            	
//            }else{
//            	sgto.setState(Action.select(),cell);
//            }
//		}else{		
//			for(int j=0;j<curIndex-rowIndex[i];j++){
//				sgto.click(upbarbutton);
//			}
//			sgto.setState(Action.extendSelect(),cell);
//			curIndex = rowIndex[i];
//		}			
//	}
//	//printObjectTree(main_fieldstable());
//	//sleep(0);
//	return rowIndex;
//	}
	//CrystalReports - 
//	public boolean  crystalReport_Create(DesktopSuperHelper dsh,
//			String filename,
//			String fields,
//			String lanuchCR,
//			String localOrServer,
//			String action
//	){
//		boolean filecreated = false;
//		String fieldsArr[] = fields.split("\\|");
//		filename = dsh.setupTestFiles(filename,
//				localOrServer,
//				dsh.fileExt);
//
//		if(!filename.equals("")){
//			actionOnText(findEditbox(mainDialog_CR(),true,0),		
//					"Template file", 
//					filename,
//			"New");
//		}
//
//		if(fields.matches("Add All|Clear All")){
//			click(findPushbutton(mainDialog_CR(),fields),fields);
//		}else{
//			SelectGuiSubitemTestObject availableFields = 
//				
//				findTable(mainDialog_CR(), true, "ListBox", 0);
//			GuiTestObject selectBtn = findPushbutton(mainDialog_CR(),"-->");
//			for(int i=0;i<fieldsArr.length;i++){
//				actionOnSelect(availableFields,"Available fields",fieldsArr[i],"New");
//				click(selectBtn,"-->");
//			}
//		}
//		//Currently 'No' for this op only 
//		lanuchCR = "No";
//		actionOnCheckbox(findCheckbutton(mainDialog_CR(),"Launch.*"),"Launch Crystal Reports application", 
//				lanuchCR.equalsIgnoreCase("Yes")?true:false,"New");
//
//		if(action.matches("[Oo][Kk]|[Ff][Ii][Nn][Ii][Ss][Hh]")){
//			click(findPushbutton(mainDialog_CR(),"OK"),"OK");
//			filecreated = true;
//			GuiTestObject cancelBtn ;
//			if(dismissPopup("Yes",false)
//					&&(cancelBtn = findPushbutton(mainDialog_CR(),"Cancel"))!=null){
//				click(cancelBtn,"Cancel");
//				filecreated = false;
//			}else{
//				filecreated = true;
//				dismissPopup("Yes",false);
//				sleep(1);
//				dismissPopup("OK",true);
//			}
//		}else{
//			click(findPushbutton(mainDialog_CR(),"Cancel"),"Cancel");
//		}
//
//		return filecreated;
//	}	
	
//**********************************************************************************************//
//Report - ************************************************************************ Report//
//*********************************************************************************************//

//*****************************************************************************************************//	
//protected Point[] tabPoint = {atPoint(1,1),atPoint(20,-10),atPoint(60,-10),atPoint(100,-10)};
//public boolean mainTab_Report(
//		String command,
//		String header,
//		String footer,
//		String ifexp,
//		String presort,
//		String summarize,
//		String suppressBlank,
//		String spacing,
//		String fitToPage
//){
//	Point mainPoint = tabPoint[1];
//
//	sleep(1);
//	if(!mainWin().exists()){
//		logTAFError("'Main' window not found");
//		return false;
//	}
//	click(mainWin(),mainPoint,"Main");		
//	if(!header.equals("")){
//		actionOnText(main_header(),"Header",header,"New");	
//	}
//	if(!footer.equals("")){
//		actionOnText(main_footer(),"Footer",footer,"New");	
//	}
//	if(!ifexp.equals("")){
//		actionOnText(main_iftext(),"If Condition",ifexp,"New");		
//	}
//
//	if(!presort.equals("")){
//		actionOnCheckbox(main_presort(),"Presort", 
//				presort.equalsIgnoreCase("Yes")?true:false,"New");
//	}	
//
//	if(!summarize.equals("")){
//		actionOnCheckbox(main_summarize(),"Summarize", 
//				summarize.equalsIgnoreCase("Yes")?true:false,"New");
//	}
//
//	if(!suppressBlank.equals("")){
//		actionOnCheckbox(main_suppressblank(),"Suppress blank detail lines", 
//				suppressBlank.equalsIgnoreCase("Yes")?true:false,"New");
//	}
//
//	if(!fitToPage.equals("")){
//		actionOnCheckbox(main_fittopage(),"Fit to page", 
//				fitToPage.equalsIgnoreCase("Yes")?true:false,"New");
//	}
//
//	if(!spacing.equals("")){
//		actionOnSelect(main_spacing(),"Spacing",spacing,"New");
//	}
//
//	return true;
//}	
	
	
	
	
	
	
	
	
	
	
	
	
	
