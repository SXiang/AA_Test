package ACL_Desktop.AppObject_Karen;

import java.awt.Point;

import resources.ACL_Desktop.AppObject_Karen.aclSamplingDialogsHelper;
import ACL_Desktop.AppObject_Karen.DesktopSuperHelper;
import ACL_Desktop.AppObject.dialogUtil;
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

public class aclSamplingDialogs extends aclSamplingDialogsHelper
{
	/**
	 * Script Name   : <b>aclDataDialogs</b>
	 * Generated     : <b>2012-03-17 10:49:33 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/17
	 * @author Karen_zou
	 */

	protected dialogUtil dLog = new dialogUtil();
	protected Point[] tabPoint = {atPoint(1,1),atPoint(20,-10),atPoint(60,-10),atPoint(100,-10)};

	public boolean mainTab_Size(
			String sampleType,
			String confidence,
			String population,
			String materiality,
			String expectedTotalErr,
			String upperErrLimit,
			String expectedErrRate,
			String calculateBtn
	){
		Point mainPoint = tabPoint[1];

		sleep(1);
		if(!mainWin_size().exists()){
			logTAFError("'Main' window of Size not found");
			return false;
		}
		sleep(1);
		click(mainWin_size(),mainPoint,"Main");	
		
		if (sampleType.equalsIgnoreCase("Monetary")){
			actionOnCheckbox(size_monetary(),"Monetary", true,"New");
        	actionOnText(size_confidence(),"Confidence",confidence,"New");
        	actionOnText(size_population(),"Population",population,"New");
        	actionOnText(size_materiality(),"Materiality",materiality,"New");
        	actionOnText(size_expectedtotalerrs(),"Expected Total Errors",expectedTotalErr,"New");
		}else if (sampleType.equalsIgnoreCase("Record")){
				actionOnCheckbox(size_record(),"Record", true,"New");
	        	actionOnText(size_confidence(),"Confidence",confidence,"New");
	        	actionOnText(size_population(),"Population",population,"New");
	        	actionOnText(size_uppererrlimit(),"Upper Error Limit(%)",upperErrLimit,"New");
	        	actionOnText(size_expectederrrate(),"Expected Error Rate(%)",expectedErrRate,"New");
		} else {
			logTAFError("The input of Sample type incorrect!");
			return false;
		}			
		
		if (calculateBtn.equalsIgnoreCase("Yes")){
			if (isEnabled(size_calculatebtn())){
				click(size_calculatebtn(),"Calculate");
				if(foundError()){
					if (size_cancel().exists()){
						click(size_cancel(),"Cancel");
					}
					return false;
				}
			}else {
				logTAFError("Calculate button disabled");		
				return false;
			}
		}
        
		return true;
	}

	public boolean foundError(){
		String title = "Error",
		       action = "OK";
		boolean isInfo = false;
		return foundError(title,action,isInfo);
	}
	
	public boolean foundError(String title,String action,boolean isInfo){
		return dLog.dismissPopup(action,isInfo);
	}	

	public boolean mainTab_Evaluate(
			String sampleType,
			String confidence,
			String interval,
			String errors,
			String samplesize,
			String numbererrs
	){
		Point mainPoint = tabPoint[1];

		sleep(1);
		if(!mainWin_evaluate().exists()){
			logTAFError("'Main' window of Evaluate not found");
			return false;
		}
		click(mainWin_evaluate(),mainPoint,"Main");	
		
		if (sampleType.equalsIgnoreCase("Monetary")){
			actionOnCheckbox(evaluate_monetary(),"Monetary", true,"New");
        	actionOnText(evaluate_confidence(),"Confidence",confidence,"New");
        	actionOnText(evaluate_interval(),"Interval",interval,"New");
        	actionOnText(evaluate_errors(),"Errors",errors,"New");
		}else if (sampleType.equalsIgnoreCase("Record")){
			actionOnCheckbox(evaluate_record(),"Record", true,"New");
        	actionOnText(evaluate_confidence(),"Confidence",confidence,"New");
        	actionOnText(evaluate_samplesize(),"Sample Size",samplesize,"New");
        	actionOnText(evaluate_numbererrs(),"Number of Errors",numbererrs,"New");
		}else {
			logTAFError("Sample Type input incorrect!");		
			return false;
		}
        
		return true;
	}
	
	//FOR OUTPUT TAB
    public boolean outputTab(DesktopSuperHelper dsh,
    		String command,
			String to,
			String fileType,
			String filename,
			String localOrServer,
			String outputHeader,
			String outputFooter
	){
      int defaultTabIndex = 2;
      return outputTab(dsh,
    		command,
    		defaultTabIndex,
			to,
			fileType,
			filename,
			localOrServer,
			outputHeader,
			outputFooter);
    }

    public boolean outputTab(DesktopSuperHelper dsh,
    		String command,
			int tabIndex,
			String to,
			String fileType,
			String filename,
			String localOrServer,
			String outputHeader,
			String outputFooter
	){
		Point outputPoint = tabPoint[tabIndex];
		GuiTestObject mainWin = null;
		
		if (command.equals("CalculateSampleSize")) {
			mainWin = mainWin_size();
		}else if (command.equals("EvaluateError")) {
			mainWin = mainWin_evaluate();
		}

		sleep(1);
		if(!mainWin.exists()){
			logTAFError("'Main' window not found");
			return false;
		}
		click(mainWin,outputPoint,"Output");

		//To && As
		if(to.equals("File")){
			actionOnCheckbox(output_file(),"To File",true,"New");
			if(fileType.equals("")||fileType.matches("ASCII Text File|Unicode Text File")){
				if (ProjectConf.unicodeTest){
					actionOnSelect(output_filetype(),"File Type","Unicode Text File","New");
			    }else {
			    	actionOnSelect(output_filetype(),"File Type","ASCII Text File","New");
			    }
			}
		}else if(to.equals("Graph")){
			actionOnCheckbox(output_graph(),"To Graph",true,"New");
		}else if(to.equals("Screen")||to.equals("")){
			actionOnCheckbox(output_screen(),"To Screen",true,"New");
		}else if(to.equals("Print")){
			actionOnCheckbox(output_print(),"To Print",true,"New");
		}

		localOrServer = setToLocal(localOrServer,output_local());	
		filename = dsh.setupTestFiles(filename,	localOrServer,".TXT");
	    
		if(to.matches("File")){
			actionOnText(output_name(),"To file",filename, "New");
		}
		//Optional
        if(!outputHeader.equals("")){
		    actionOnText(outputHeader_moreNext_text(),"Header",outputHeader,"New");	
       }
       if(!outputFooter.equals("")){
		    actionOnText(outputFooter_moreWhile_text(),"Footer",outputFooter,"New");	
      }
		return true;
	}	

	public String setToLocal(String localOrServer, ToggleGUITestObject tgo){
		boolean enabled = false,selected=false;

		if(!tgo.exists()){
			return "Local";  
		}
		selected = isChecked(tgo);
		enabled = isEnabled(tgo);

		if(localOrServer.equals("")||localOrServer.equals("TBD")){
			if(selected){
				localOrServer = "Local";
			}else{
				localOrServer = "Server";
			}
		}else if(enabled){
			actionOnCheckbox(tgo,"Local", 
					localOrServer.equalsIgnoreCase("Local")?true:false,"New");
		}
		
		return localOrServer;
	}

	public boolean mainTab_Sample(
			DesktopSuperHelper dsh,
			String sampletype,
			String sampleon,
			String sampleparameters,
			String interval,
			String start,
			String cutoff,
			String seed,
			String size,
			String population,
			String ifexpr,
			String filename,
			String localOrServer,
			String useoutputtable,
			String sizebtn,
			String confidence,
			String materiality,
			String expectedtotalerrs,
			String uppererrlimit,
			String expectederrrate,
			String calculatebtn
			
	){
		Point mainPoint = tabPoint[1];

		sleep(1);
		if(!mainWin_sample().exists()){
			logTAFError("'Main' window not found");
			return false;
		}
		click(mainWin_sample(),mainPoint,"Main");	
		
		//Select sample type
		if (sampletype.equalsIgnoreCase("MUS")||sampletype.equals("")){
			actionOnCheckbox(sample_mus(),"MUS", true,"New");
			if (sampleparameters.equalsIgnoreCase("Fixed Interval")){
				actionOnCheckbox(sample_fixedinterval(),"Fixed Interval", true,"New");
	        	actionOnText(sample_interval(),"Interval",interval,"New");
	        	actionOnText(sample_start(),"Start",start,"New");
	        	actionOnText(sample_cutoff(),"Cutoff",cutoff,"New");
			}else if (sampleparameters.equalsIgnoreCase("Cell")){
				actionOnCheckbox(sample_cell(),"Cell", true,"New");
	        	actionOnText(sample_interval(),"Interval",interval,"New");
	        	actionOnText(sample_seed(),"Seed",seed,"New");
	        	actionOnText(sample_cutoff(),"Cutoff",cutoff,"New");				
			}else if (sampleparameters.equalsIgnoreCase("Random")){
				actionOnCheckbox(sample_random(),"Random", true,"New");
	        	actionOnText(sample_size(),"Size",size,"New");
	        	actionOnText(sample_seed(),"Seed",seed,"New");
	        	actionOnText(sample_population(),"Population",population,"New");					
			}else {
				logTAFError("Sample Parameters are incorrect");	
				return false;
			}
		} else if (sampletype.equalsIgnoreCase("Record")){
			actionOnCheckbox(sample_record(),"Record", true,"New");
			if (sampleparameters.equalsIgnoreCase("Fixed Interval")){
				actionOnCheckbox(sample_fixedinterval(),"Fixed Interval", true,"New");
	        	actionOnText(sample_interval(),"Interval",interval,"New");
	        	actionOnText(sample_start(),"Start",start,"New");
			}else if (sampleparameters.equalsIgnoreCase("Cell")){
				actionOnCheckbox(sample_cell(),"Cell", true,"New");
	        	actionOnText(sample_interval(),"Interval",interval,"New");
	        	actionOnText(sample_seed(),"Seed",seed,"New");
			}else if (sampleparameters.equalsIgnoreCase("Random")){
				actionOnCheckbox(sample_random(),"Random", true,"New");
	        	actionOnText(sample_size(),"Size",size,"New");
	        	actionOnText(sample_seed(),"Seed",seed,"New");
	        	actionOnText(sample_population(),"Population",population,"New");					
			}else {
				logTAFError("Sample Parameters are incorrect");		
				return false;
			}
		} else {
			logTAFError("Sample type is incorrect");	
			return false;
		}
		
		//Select SampleOn
		if (sampletype.equalsIgnoreCase("MUS")){
			actionOnSelect(sample_sampleon(),"Sample On...",sampleon,"New");
		}
		
		//localOrServer = "Local";
		localOrServer = setToLocal(localOrServer,sample_local());	
		
		//Check Size button enability
		if ((sampletype.equalsIgnoreCase("MUS")&&sampleparameters.equalsIgnoreCase("Fixed Interval"))||
			(sampletype.equalsIgnoreCase("Record"))){
			if (!sample_sizebtn().isEnabled()){
				logTAFError("Size button is disabled");					
			}else if (sizebtn.equalsIgnoreCase("Yes")){        //Click Size button();
					click(sample_sizebtn(),"Size...");
					mainTab_Sample_Size(
								sampletype,
								confidence,
								population,
								materiality,
								expectedtotalerrs,
								uppererrlimit,
								expectederrrate,
								calculatebtn
					);
				  }	
		}			

		//Set if & To
        if(!ifexpr.equalsIgnoreCase("")){
        	actionOnText(sample_if(),"If Condition",ifexpr,"New");
        }
        
        //actionOnText(sample_to(),"To file",dsh.setupTestFiles(filename,localOrServer, dsh.fileExt),"New");
        actionOnText(sample_to(),"To file",dsh.setupTestFiles(filename,localOrServer,".fil"),"New");
		
        if (useoutputtable.equalsIgnoreCase("Yes"))
        	if (!sample_useoutputtable().getProperty(".defaultAction").equals("Uncheck")) {
        		actionOnCheckbox(sample_useoutputtable(),"Use Output Table", true,"New");
        	}
        return true;
	}

	public boolean mainTab_Sample_Size(
			String sampleType,
			String confidence,
			String population,
			String materiality,
			String expectedTotalErr,
			String upperErrLimit,
			String expectedErrRate,
			String calculateBtn
	){
		Point mainPoint = tabPoint[1];

		sleep(1);
		
		if(!mainWin_Sample_Size().exists()){
			logTAFError("'Main' window not found");
			return false;
		}
		sleep(1);
		click(mainWin_Sample_Size(),mainPoint,"Main");	
		
		if (sampleType.equalsIgnoreCase("MUS")||sampleType.equalsIgnoreCase("")){
			if (sample_size_monetary().isEnabled()){
				actionOnCheckbox(sample_size_monetary(),"Monetary", true,"New");
				actionOnText(sample_size_confidence(),"Confidence",confidence,"New");
				actionOnText(sample_size_population(),"Population",population,"New");
				actionOnText(sample_size_materiality(),"Materiality",materiality,"New");
				actionOnText(sample_size_expectedtotalerrs(),"Expected Total Errors",expectedTotalErr,"New");
			}else {
				logTAFError("Sample Type enability is incorrect");
				return false;				
			}
		}else if (sampleType.equalsIgnoreCase("Record")){
				if (sample_size_record().isEnabled()){
					actionOnCheckbox(size_record(),"Record", true,"New");
					actionOnText(size_confidence(),"Confidence",confidence,"New");
					actionOnText(size_population(),"Population",population,"New");
					actionOnText(size_uppererrlimit(),"Upper Error Limit(%)",upperErrLimit,"New");
					actionOnText(size_expectederrrate(),"Expected Error Rate(%)",expectedErrRate,"New");
				}else {
					logTAFError("Sample Type enability is incorrect");
					return false;					
				}
		} else {
			logTAFError("Sample Type enability is incorrect");
			return false;	
		}			
		
		if (calculateBtn.equalsIgnoreCase("Yes")){
			click(size_calculatebtn(),"Calculate");
			if(foundError()){
				if (size_cancel().exists()){
					click(size_cancel(),"Cancel");
					return false;
				}
			}
		}
        
		click(sample_size_ok(),"OK");
		return true;
	}
	
	// FOR MORE TAB
	public boolean moreTab( String command,
			               String scope,
			               String wCondition,
			               String fields,
			               String subsample,
			               String reportorder,
			               String norepeats,
			               String append
			              ){
		sleep(1);
		if(!mainWin_sample().exists()){
			logTAFError("'More' window not found");
			return false;
		}
		Point morePoint = tabPoint[2];
		
		click(mainWin_sample(),morePoint,"More");

		//Select Scope
		String scopeArr[] = scope.split("\\|");
		if(scopeArr[0].equals("All")){
			actionOnCheckbox(more_all(),"All",true,"New");
		}else if(scopeArr[0].equals("First")){
			actionOnCheckbox(more_first(),"First",true,"New");
			if(scopeArr.length>1){
				actionOnText(more_firsttext(),"First",scopeArr[1],"New");
			}
		}else if(scopeArr[0].equals("Next")){
			actionOnCheckbox(more_next(),"Next",true,"New");
			if(scopeArr.length>1){
				actionOnText(outputHeader_moreNext_text(),"Next",scopeArr[1],"New");
			}
		}		
		actionOnText(outputFooter_moreWhile_text(),"While",wCondition,"New");

		selectFields("Extract Fields", fields);
		
		if (!fields.equalsIgnoreCase("Record")){
			actionOnCheckbox(more_subsample(),"Subsample",subsample.equalsIgnoreCase("Yes")?true:false,"New");
		}
		if(!norepeats.equalsIgnoreCase("")){
			actionOnCheckbox(more_norepeats(),"No Repeats",
					norepeats.equalsIgnoreCase("Yes")?true:false,"New");
		}
		if(!append.equalsIgnoreCase("")){
			actionOnCheckbox(more_append(),"Append To Existing File",
					append.equalsIgnoreCase("Yes")?true:false,"New");
		}
		return true;
	}

	public void selectFields(String caption, String fields){
		boolean done = true;
		
		if(fields.equalsIgnoreCase("Record")){
			actionOnCheckbox(moreSample_record(),"Record",true,"New");
		}else if (fields.equalsIgnoreCase("Add All")){
			actionOnCheckbox(moreSample_fields(),"Fields",true,"New");			
			click(more_extractfields(),caption);
			selectedFields("",fields,"OK");
		}else {
			actionOnCheckbox(moreSample_fields(),"Fields",true,"New");	
			selectSomeFields(more_fieldstable(),fields);
		}
	}
	
	public void selectedFields(String fromTable,String fields,String endWith){

		if(!fromTable.equals("")){
			//Select from table
		}
		if(fields.equalsIgnoreCase("Add All")){
				click(selectedfields_addall(),fields);
		}else if(fields.equalsIgnoreCase("Clear")){
				click(selectedfields_addall(),fields);
		}else{
			    // TBD
		}
		
		if(endWith.equalsIgnoreCase("OK")){
			click(expr_ok(),"OK");
		}else{
			click(expr_cancel(),"Cancel");
		}
	}
	
	public void selectSomeFields(SelectGuiSubitemTestObject sgto, String fields){
		Point firstRow = atPoint(50,30),
		      upbarbutton = getGuiRelativePoint(sgto, "topright", atPoint(-12,12));
	    String fieldsArr[] = fields.split("\\|");
	    int[] rowIndex = new int[fieldsArr.length];
	    String headerName = "Name";
	    boolean swaped = false;
	    //printObjectTree(main_fieldstable());
	    
		for(int i=0;i<fieldsArr.length;i++){
			rowIndex[i] = searchTableRowByText(sgto,
					headerName,fieldsArr[i]);
			
			if(rowIndex[i]<0){
				logTAFError("Field not found - '"+fieldsArr[i]+"'");
				continue;
			}
            if(i!=0&&(rowIndex[i]==0)){ // Force the first row to be selected as the first 
            	rowIndex[i] = rowIndex[0];
            	String temp = fieldsArr[i];
            	fieldsArr[i] = fieldsArr[0];
            	fieldsArr[0] = temp;
            	rowIndex[0] = 0;
            	swaped = true;
            }
		}
		//printObjectTree(main_fieldstable());
		for(int i=0;i<rowIndex.length;i++){
			if(rowIndex[i]<0){
				continue;
			}
			logTAFInfo("Select field "+(i+1)+": '"+fieldsArr[i]+"'");		
			Subitem cell = atCell(atRow(atIndex(rowIndex[i])),atColumn(headerName));

			if(i==0){
                if(rowIndex[i]==0){
                	//It's a workaround for the selection of the first row 
                	//To ensure works, the firstRow must to be selected as the first
                	if(swaped){
                		logTAFDebug("This is a workaround for the issue on select first row");
                	}
                	sgto.doubleClick(firstRow);
                	
                }else{
                	sgto.setState(Action.select(),cell);
                }
			}else{		
				for(int j=0;j<rowIndex[i];j++){
					sgto.click(upbarbutton);
				}
				sgto.setState(Action.extendSelect(),cell);
			}			
		}
	}
	
	public boolean fileAction(String command,String action){
		boolean isInfo = false;
		return fileAction(command,action, isInfo);
	}
	
	public boolean fileAction(String command,String action,boolean isInfo){
		boolean filecreated = false, specialerr = false;
		GuiTestObject main_cancel = null;
		
		if (command.equals("CalculateSampleSize")){
			main_cancel = size_cancel();
		}else if (command.equals("SampleRecords")){
			main_cancel = sample_cancel();
		}else if (command.equals("EvaluateError")){
			main_cancel = evaluate_cancel();
		}
		
		if(action.matches("OK|Finish")){
			click(main_ok(),"OK");
			if (command.equals("EvaluateError")){
				if (evaluate_error().exists()){
					specialerr = true;
					filecreated = false;
					String msg = (String)evaluate_error().getProperty(".name");
		    	    logTAFDebug(msg);
					logTAFError(msg);
					click(evaluate_error_ok(),"OK");
				}
			}
			if (specialerr||(!specialerr && foundError())){
				if (main_cancel.exists()){
					click(main_cancel,"Cancel");
				}
			}else{
			   filecreated = true;
			   dLog.confirmWarning("Yes",isInfo);
			   dLog.confirmWarning("OK",true);
			   dLog.confirmWarning("Yes",true);
			}
		}else if(action.equals("Cancel")){
				click(main_cancel,"Cancel");
				filecreated = false;
		}
		return filecreated;
	}


	public void testMain(Object[] args) 
	{
		// TODO Insert code here
	}
}

