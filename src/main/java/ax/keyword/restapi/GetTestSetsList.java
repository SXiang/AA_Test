package ax.keyword.restapi;

import ax.lib.RestAPITaskHelper;

public class GetTestSetsList extends RestAPITaskHelper {
	/**
	 * Script Name   : <b>GetProjectList</b>
	 * Generated     : <b>Aug. 19, 2013 4:20:42 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2013/08/19
	 * @author Karen_Zou
	 */

	// Configurations defined in RestAPI Property file
	String serverName = "win2012-3.aclqa.local";
	String browserType = "IE";
	
	// BEGIN of datapool variables declaration
	private String dpScope;          //@arg value for Scope
                                    	//@value = working/library/""
	private String dpProjectName;    //@arg value for Project Name

	// END of datapool variables declaration
    
	private String uuid="";
	
	@Override
	public boolean dataInitialization() {
		boolean done= true;
     
		//*** read in data from datapool     
		//dpScope = getDpString("Scope");
		//dpProjectname = getDpString("ProjectName");
		//dpFileMaster = getDpString("MasterFile");
		
		dpScope = "working";
		dpProjectName = "12345";
		uuid = querySQL(dpScope+dpProjectName);
	    dpFileMaster = "C:\\GitHub\\ACLQAAutomation\\automation\\src\\main\\resources\\testdata\\ax\\restapi\\master\\GetProjectList\\GetProjectList.txt";
	    dpFileActual = "C:\\GitHub\\ACLQAAutomation\\automation\\output\\ax\\restapi\\actualdata\\bb.txt";

		if ((uuid != null) && (uuid != ""))
			url = "https://"+serverName+":8443/aclax/api/projects/"+uuid+"/testsets";
		else System.out.println("Error:" + "Can not find the uuid for the specific project");

		return done;
	}
	
	public void testRestAPIProjectList()
	{
		dataInitialization();

		RestAPITaskHelper.launchBrowser(browserType);
		driver.get(url);

		getPageWithCAS(driver);
		outputData = getPageSource(driver);
        
        System.out.println("\t"+outputData);

	    doVerification(outputData);
	    driver.close();
	}
 }