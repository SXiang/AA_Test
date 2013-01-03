package ACL_Desktop.Tasks.Data;

import java.io.File;

import resources.ACL_Desktop.Tasks.Data.relatetablesHelper;
import ACL_Desktop.AppObject.keywordUtil;
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

public class relatetables extends relatetablesHelper
{
	/**
	 * Script Name   : <b>relatetables</b>
	 * Generated     : <b>Mar 2, 2012 4:53:10 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/02
	 * @author Steven_Xiang
	 */
	private String secondaryTable = "";

	// BEGIN of datapool variables declaration
	
	private String dpPrimaryKey;  //@arg name of primarykey


	//Secondary Table
	private String dpSecondaryTable; //@arg name of the secondary table
	private String dpSecondaryTableIndex; //@arg index of the secondary table
	                                      //@value = '2|3...'

	private String dpSecondaryKey;  //@arg name of secondary key
                                 
	
	// END of datapool variables declaration


	@Override
	public boolean dataInitialization() {
		boolean done= true;
		command = "RelateTables";
		defaultMenu = "Data";
		winTitle = "Merge";
		tabMainName = winTitle; //"_Main";
		
		readSharedTestData();
        readSharedMainTestData();
 
        // Section 2: for this keyword
        dpPrimaryKey = getDpString("PrimaryKey");        
    	//dpPresort = getDpString("Presort");
    	
    	dpSecondaryTable = getDpString("SecondaryTable");
    	    secondaryTable = getNameFromPath(dpSecondaryTable);
    	dpSecondaryTableIndex = getDpString("SecondaryTableIndex");
    	dpSecondaryKey = getDpString("SecondaryKey");

    	if(dpActOnItem.matches(".*_DB2|.*_SQLServer|.*_Oracle")){
    		dpPrimaryKey = dpPrimaryKey.toUpperCase();
    		dpSecondaryKey = dpSecondaryKey.toUpperCase();
        }
    	
        	actualItem = secondaryTable+dpSecondaryTableIndex;
        	actualName = keywordUtil.replaceSpecialChars(actualItem);
        	actualName = dpActOnItem.replace(itemName,actualItem);
        	
            // Since there is no value for dpSvaeLocalOrNet, we don't set here
            //dpFileName = setupTestFiles(dpFileName,dpSaveLocalOrNet,fileExt=".file");

		return done;
	}

	public void testMain(Object[] args) 
	{
		super.testMain(args);
		openTest();
		aclMainDialog();        
	    //aclEndWith("fileAction");  
	    //doVerification("Log");  
		aRou.exeACLCommands(dpPostCmd);	          
	    
	}
	
	public void aclMainDialog(){
		int mt = 3,st =0;
		while(gettingStarted_OK().exists()&&st++<mt){
			click(gettingStarted_OK(),"OK");
			sleep(1);
		}
		
		dataDlog.setRelations(
				         itemName,
		        		 dpPrimaryKey,
		        		 secondaryTable,
		        		 dpSecondaryTableIndex,
		        		 dpSecondaryKey);
		      		        
	    
	}
	
 }
