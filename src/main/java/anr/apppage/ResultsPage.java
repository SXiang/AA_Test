/**
 * 
 */
package anr.apppage;

import java.awt.Point;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import anr.lib.frontend.ANR_FrontendCommonHelper;

public class ResultsPage extends WebPage{
	/**
	 * Script Name   : <b>OpenProjectsHelper</b>
	 * Generated     : <b>Feb.14, 2013</b>
	 * Description   : ResultsPage
	 * 
	 * @since  Feb 12, 2014
	 * @author Karen Zou
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// Common elements
	@FindBy(css = "div.analytic-row > div.row-fluid > div.ng-binding")
	private List<WebElement> allAnalytics;
	
	@FindBy(css = "div.row-fluid > div.action-button-group > div.action-buttons > a[title~='results']")
	private WebElement allResultsIcon;
	
	@FindBy(css="div.row-fluid > div.results > h3 > span[key='_Anr.Results.Title_']")
	private WebElement resultsLabel;
	
	@FindBy(css="div.row-fluid > div[class^='results'] > div[id='jobLastRunInfo'] > span:not([style='display: none;']) > span")
	private WebElement analyticStatus;

	@FindBy(css="div.row-fluid > div[class^='results'] > div.anr-table-title-row > div > span")
	private List<WebElement> resultsListTitle;

	@FindBy(css="div.row-fluid > div[class^='results'] > div[class='row-fluid'] >  div:nth-child(2) > div > div.span5")
	private List<WebElement> allResultsListName;
	
	@FindBy(css="div.row-fluid > div[class^='results'] > div[class='row-fluid'] >  div:nth-child(2) > div > div.span2")
	private List<WebElement> allResultsListType;
	
	@FindBy(css="div.row-fluid > div[class^='results'] > div[class='row-fluid'] >  div:nth-child(2) > div > div.span2:nth-child(3)")
	private List<WebElement> allResultsListRecords;

	@FindBy(css="div.row-fluid > div[class^='results'] > div[class='row-fluid'] >  div:nth-child(2) > div > div.span2:nth-child(4)")
	private List<WebElement> allResultsListSize;

	@FindBy(css="div.row-fluid > div[class^='results'] > div[class='row-fluid'] >  div:nth-child(2) > div > div.span1 > a")
	private List<WebElement> allResultsDataVisualizeIcon;
	
//END
	
	//*****************************************
	// ******* Methods           ****************
	// *******************************************
	public WebElement locateAnalytic(String analyticName){
		String analyticXpath = "//div[@class='ng-scope']"
				+ "/div[@ng-include and div[contains(@class,'analytic-row')]"
				+ "/div[@class='row-fluid']"
				+ "/div[contains(@class,'ng-binding') and contains(text(),'"
	            +analyticName+"')]]";
		WebElement analytic = pageDriver.findElement(By.xpath(analyticXpath));
		//anName = analyticName;
		scrollToElement(analytic,(new Point(0,300)));
		logTAFInfo("Working analytic: '"+analyticName+"'");
		return analytic;
	}
	
	public void openResultsView(String analyticName){
		openResultsView(analyticName,true);
	}	
	
	public void closeResultsView(String analyticName){
		openResultsView(analyticName,false);
	}
	
	//Open Results view if displayed is TRUE, otherwise hide it
	public void openResultsView(String analyticname,boolean displayed) {
		String resulticonttitle = "";
	
		resulticonttitle = allResultsIcon.getAttribute("title");
		if (displayed) {
			if (resulticonttitle.contains("Show results")) {
				toggleElementByClick(resultsLabel,allResultsIcon,"Show Results",true);
				return;
			}
		} else {
				if (resulticonttitle.contains("Hide results")) {
					toggleElementByClick(resultsLabel,allResultsIcon,"Hide Result",false);
					return;
				}
		}
		
		if (displayed) 
	       logTAFInfo("Results view is already open, no need to click Results icon......");
		else
		   logTAFInfo("Results view is already closed, no need to click Results icon......");	
		return;
	}	
	

	public  String getAllResultsList() {
		String allresults = "";
		
		((JavascriptExecutor) pageDriver).executeScript("scroll(250,0);");
		takeScreenshot();

		//Get Results Label-localization
		allresults = "@"+resultsLabel.getText()+"@\r\n";
		
		//Get Results Status-localization
		allresults += "@"+analyticStatus.getText()+"@";
		
		//Get Results Title Row-localization
		allresults += "\r\n" + "@";
		for (int i= 0; i < resultsListTitle.size(); i++ )
		    allresults += resultsListTitle.get(i).getText()+" ";
		allresults += "@";

		//Get all results list for the analytic
		for(int i = 0; i < allResultsListName.size(); i++) {
			allresults += "\r\n" + allResultsListName.get(i).getText()+" ";
			allresults += allResultsListType.get(i).getText()+" ";
			allresults += allResultsListRecords.get(i).getText()+" ";
			allresults += allResultsListSize.get(i).getText();
		}

        return allresults;
	}

	public boolean clickTableName(String tableName) {
	    logTAFStep("Click Data Visualization icon button for the result table " + tableName);
		
	    //Find the result table, then click the table name
		for(int i = 0; i < allResultsListName.size(); i++) {
		    String temp = allResultsListName.get(i).getText();
        	if (temp.equalsIgnoreCase(tableName)) {
        	    logTAFInfo("Result table name has been found successfully!");
        	    logTAFStep("Click the related data visualize icon...");
        	 
        	    allResultsDataVisualizeIcon.get(i).click();
        		
        		sleep(1);
        		return true;
        	}
		}

        return false;
	}
	
	public ResultsPage(WebDriver driver) {
	    pageDriver = driver; 
	    pageDriverWait = new WebDriverWait(driver,30);

	}
}
