package anr.keyword.frontend;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;

import com.acl.qa.taf.helper.Interface.KeywordInterface;

import anr.lib.frontend.OpenProjectHelper;

public class OpenProject extends OpenProjectHelper implements KeywordInterface{
	
	/**
	 * Script Name   : <b>Search</b>
	 * Generated     : <b>Oct 4, 2013</b>
	 * Description   : OpenProject
	 * @author Karen Zou
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	private String dpProjectFolder;
	private String dpProjectFile;
	private String dpTableName;
	// END of datapool variables declaration

    // BEGIN of other local variables declaration
	//END
	
	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		getSharedObj();
		super.dataInitialization();
		
		dpProjectFolder = getDpString("ProjectFolder");
		dpProjectFile = getDpString("ProjectFile");
		dpTableName = getDpString("TableName");
		
		return true;
	}
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		
		//Start App
		//startApp();
		
		//Launch browser
		launchBrowser();
		
		//Enter Project File with the full path
		clickProjectOpenButton(dpProjectFolder,dpProjectFile);
		
		if(((dpProjectFolder != null) && (dpProjectFolder!="")) && ((dpProjectFile != null)||(dpProjectFile!=""))) {
			verifyProjectName(dpProjectFolder,dpProjectFile);
		}
		if(!dpMasterFiles[0].isEmpty()){
			verifyAllAnalyticsList();
			verifyAllTablesList();
		}

		if ((dpTableName != null) && (dpTableName != "")) {
			OpenTable(dpTableName);
		}
		
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	public void verifyProjectName(String projectfolder, String projectfile){
		String projecttitle = getProjectTitle();
		if(projecttitle.isEmpty()){
			logTAFError("No Project title Found!!");
		}else{
			logTAFStep("Verify Project Name");
			if ((projectfile.replaceAll(".[a|A][c|C][l|L]", "")).equalsIgnoreCase(projecttitle))
				logTAFInfo("Project name is echo-displayed successfully");
			else
				logTAFError("Project name is echo-displayed unsuccessfully!!");
		}
	}
	
	public void verifyAllTablesList(){
		String allTables = getAllTables(dpProjectFolder,dpProjectFile);
		
		if(allTables.isEmpty()){
			logTAFError("No Table Found!!");
		}else{
			logTAFStep("Verify list of Tables - " + dpMasterFiles[0]);
			result[0] = allTables; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[0]);
		}
	}
	
	public void verifyAllAnalyticsList(){
		String allAnalytics = getAllAnalytics(dpProjectFolder,dpProjectFile);
		
		if(allAnalytics.isEmpty()){
			logTAFError("No Table Found!!");
		}else{
			logTAFStep("Verify list of Analytics - " + dpMasterFiles[1]);
			result[0] = allAnalytics; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[1]);
		}
	}
	
	
	public void OpenTable(String tablename){
		clickTableName(tablename);
	}

	public static void main(String args) {

	}
}
