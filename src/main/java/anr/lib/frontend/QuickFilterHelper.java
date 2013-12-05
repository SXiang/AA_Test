package anr.lib.frontend;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ax.lib.frontend.FrontendCommonHelper;

public class QuickFilterHelper extends FrontendCommonHelper{
	/**
	 * Script Name   : <b>QuickFilterHelper</b>
	 * Generated     : <b>Dec 5, 2013</b>
	 * Description   : QuickFilterHelper
	 * 
	 * @since  Dec 2, 2013
	 * @author Ramneet Kaur
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	
	// END of datapool variables declaration

	// BEGIN locators of the web elements of DataVisualization page
	By colHeaderLocator = By.cssSelector("div[id^='col']");
	By quickFilterHeaderLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-header']");
	By closeQuickFilterMenuLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-header'] > i.icon-remove");
	By sortSectionLabelLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div.sort-header");
	By ascendingLinkLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div > div[id='sort-ascending']");
	By descendingLinkLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div > div[id='sort-descending']");
	//END
    
    // BEGIN of other local variables declaration 
	protected List<WebElement> allColumnHeaders;
	//END
	
	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		getSharedObj();
		super.dataInitialization();
		
		return true;
	}
	
	@Override
	public void testMain(Object[] args) {
		dataInitialization();
		super.testMain(onInitialize(args, getClass().getName()));
//		isElementEnabled(projectOpenButtonLocator, "Open Local Project");
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	
	public void clickColumnHeader(String columnName) {
		allColumnHeaders = driver.findElements(colHeaderLocator);
        for(int i = 0; i < allColumnHeaders.size(); i++) {
        	if(allColumnHeaders.get(i).getText().equalsIgnoreCase(columnName)){
        		allColumnHeaders.get(i).click();
        		logTAFStep("Column: '"+columnName+"' found and clicked on to get Quick filter menu.");
        	}
        }
	}	

	public void clickDescendingLink() {
		driver.findElement(descendingLinkLocator).click();
	}
	
	public void clickAscendingLink() {
		driver.findElement(ascendingLinkLocator).click();
	}
}
