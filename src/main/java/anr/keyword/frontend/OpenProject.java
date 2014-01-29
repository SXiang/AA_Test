package anr.keyword.frontend;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import com.acl.qa.taf.helper.Interface.KeywordInterface;
import anr.lib.frontend.OpenProjectHelper;

public class OpenProject extends OpenProjectHelper implements KeywordInterface{
	
	/**
	 * Script Name   : <b>OpenProject</b>
	 * Generated     : <b>Oct 4, 2013</b>
	 * Description   : OpenProject
	 * @author Karen Zou
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	private String dpProjectFolder;     //@arg the project folder to open
	private String dpProjectFile;       //@arg the project file name to open 
	private String dpOpenTablesOrFiles; //@arg select whether open Data Tables or Related Files
	//1225 private String dpTableName;
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
		dpOpenTablesOrFiles = getDpString("OpenTablesOrFiles");
		
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
		if(!(dpProjectFolder.isEmpty()) && !(dpProjectFile.isEmpty())) {
			clickProjectOpenButton(dpProjectFolder,dpProjectFile);
			
			verifyElementsEnabledOrDisplayed();
			verifyProjectName(dpProjectFolder,dpProjectFile);

			if(!dpMasterFiles[0].isEmpty()){
				verifyDescription();
				closeLayer();
			}

			if(!dpMasterFiles[1].isEmpty()){
				verifyAllAnalyticsList();
			}
			
			if (!dpMasterFiles[2].isEmpty()) {
				verifyHeaderFooter();
			}

			if (dpOpenTablesOrFiles.contains("table")){
				openDataTables();
			}else if (dpOpenTablesOrFiles.contains("file")){ 
				openRelatedFiles();
			}
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
			logTAFError("No Project title Found!!!");
		}else{
			logTAFStep("Verify Project Name");
			if ((projectfile.replaceAll(".[a|A][c|C][l|L][x|X|]*", "")).equalsIgnoreCase(projecttitle))
				logTAFInfo("Project name is echo-displayed successfully");
			else
				logTAFError("Project name is echo-displayed unsuccessfully!!");
		}
	}

	public void verifyDescription(){
		String projectDescrption = getDescription();
		
		logTAFStep("Verify Project Description");
		if(projectDescrption.isEmpty()){
			logTAFWarning("No Project Description Found. Please check your data.");
		}

		result[0] = projectDescrption;
		compareTxtResult(result[0],dpMasterFiles[0]);
	}

	public void verifyAllAnalyticsList(){
		String allAnalytics = getAllAnalytics(dpProjectFolder,dpProjectFile);
		
		logTAFStep("Verify list of Analytics - " + dpMasterFiles[1]);
		if(allAnalytics.isEmpty()){
			logTAFWarning("This project has no analytics. Please double check your data.");
		}
		
		result[0] = allAnalytics; // You need to get actual result for each comparison
		compareTxtResult(result[0], dpMasterFiles[1]);
	}
	
	public void verifyHeaderFooter(){
		String header = getProjectHeader();
		logTAFStep("verify Page Header - "+dpMasterFiles[2]);
		
		result[0] = header; // You need to get actual result for each comparison
		compareTxtResult(result[0], dpMasterFiles[2]);
	}
	
	public void openDataTables(){
		clickDataTablesButton();
	}
	
	public void openRelatedFiles(){
		clickRelatedFilesButton();
	}

	public static void main(String args) {

	}
}
