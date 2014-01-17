package ax.keyword.frontend;

import ax.lib.frontend.ProjectsListHelper;

public class ProjectsList extends ProjectsListHelper{
	
	/**
	 * Script Name   : <b>ProjectsList</b>
	 * Generated     : <b>Sep 5, 2013</b>
	 * Description   : ProjectsList keyword
	 * 
	 * @author Ramneet Kaur
	 */

	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpProjectName; //@arg ProjectName whose link to be clicked for details
	protected String dpViewType; //@arg Type of view in which want to see the list of projects:
	                             //@value = card|list
	// END of datapool variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpProjectName = getDpString("ProjectName");
		dpViewType = getDpString("ViewType");
		//END
		return true;
	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		if(!dpMasterFiles[0].isEmpty()){
			verifyAllProjectsList();
			verifyHeaderFooter();
		}
		if(!dpViewType.isEmpty()){
			openView();
		}
		if(!dpProjectName.isEmpty()){
			String viewType = openView();
			openProjectDetails(viewType);
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	public String openView(){
		String viewType = findViewType();
		if(!dpViewType.isEmpty() && !viewType.equalsIgnoreCase(dpViewType)){
			if(dpViewType.equalsIgnoreCase("force:card")){
				viewCards();
				return "card";
			}else if(dpViewType.equalsIgnoreCase("force:list")){
				viewList();
				return "list";
			}else if(dpViewType.equalsIgnoreCase("card")){
				logTAFError("The expected view type does not match with current view type");
			}else if(dpViewType.equalsIgnoreCase("list")){
				logTAFError("The expected view type does not match with current view type");
			}
		}
		return viewType;
	}
	
	public void verifyAllProjectsList(){
		String viewType = openView();
		String allProjects = getAllProjects(viewType);
		if(allProjects.isEmpty()){
			logTAFError("No Project Found!!");
		}else{
			logTAFStep("Verify list of Projects - " + dpMasterFiles[0]);
			result[0] = allProjects; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[0]);
		}
	}
	
	public void verifyHeaderFooter(){
		String header = getProjectHeader();
		String footer = getFooter();
		logTAFStep("Verify Project Header - " + dpMasterFiles[1]);
		result[0] = header; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[1]);
		logTAFStep("Verify Page Footer - " + dpMasterFiles[2]);
		result[1] = footer; // You need to get actual result for
											// each comparison
		compareTxtResult(result[1], dpMasterFiles[2]);
	}
	
	public void openProjectDetails(String viewType){
		clickProjectName(viewType, dpProjectName);
	}
	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {
		//ProjectsList debug = new ProjectsList();
		//debug.verifyAllProjectsList();.
		//debug.verifyHeaderFooter();
		//debug.openProjectDetails();
	}
}
