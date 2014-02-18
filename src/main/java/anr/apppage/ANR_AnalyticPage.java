/**
 * 
 */
package anr.apppage;

import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import ax.lib.restapi.db.SQLQuery;

/**
 * Script Name   : <b>AnalyticPage.java</b>
 * Generated     : <b>4:31:17 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Jan 8, 2014
 * @author steven_xiang
 * 
 */
public class ANR_AnalyticPage  extends WebPage{

	//*** Final varialbes ***	
	 private final int pageLoadTime = 3;
	 private String anName = "";
//Karen0208	 private String lastJob = "";
	 private final QuickFilterPage qfPage;

	 
	 //*** Search context ***
	 //private int analyticIndex = 0;

	    //*** Common elements ***
	    @FindBy(css = "div.analytic-row > div.row-fluid > div.ng-binding")
		private List<WebElement> analytics;
		
		//*** Analytic ***
		@FindBy(css = "div.row-fluid > a.icon_play")
		private WebElement runIconBtn;
		@FindBy(css = "div.row-fluid >  div.top-description")
		private WebElement description;
		
		@FindBy(css = "div.row-fluid > div.promptBesideInput.errorTextColor")
		private List<WebElement> inputValueError;		
		
		@FindBy(css = "div > span.keyboardPrompt.errorTextColor > em > span")
		private List<WebElement> inputNameError;
		
		@FindBy(css = "a.action-btn > span[key='_Button.Run.Label_']")
		private WebElement runBtn;
		
		@FindBy(css = "div[style*='height: auto'] > div.drawer[ng-show*='runInfo'] > span > a.action-btn.button_c")
		private WebElement continueBtn;
		
		@FindBy(css = "div.analyticParamInputView form[name$='InputForm'] input")
		private WebElement pSetValueBox;
		@FindBy(css = "form[name$='ParamSetNameForm'] > div > input[name*='paramSetNameInput']")
		private WebElement pSetNameBox;
		
		@FindBy(css = "form[name$='ParamSetNameForm'] a.action-btn > span[key='_Button.Continue.Label_']")
		private WebElement formNameContinueBtn;	
			
		@FindBy(css = "div.analyticParamInputView form[name$='InputForm'] a.action-btn > span[key='_Button.Continue.Label_']")
		private WebElement formInputContinueBtn;	
		@FindBy(css = "a.action-btn > span[key='_Button.Run.Label_']")
		private WebElement formRunBtn;

		//Karen may remove for ANR
		@FindBy(css = "div[style*='height: auto'] > div.drawer[ng-show*='runInfo'] > span > div span[key='_AX_RunJob.NewParameterSet.Label_']")
		private WebElement newPSetBtn;
		
		@FindBy(css = "div.select2-container.parameter-select > a.select2-choice > span.select2-chosen")
		private WebElement pSetDropdown;
		
		@FindBy(css = "div.select2-with-searchbox.select2-drop-active > div.select2-search > input.select2-search")
		private WebElement pSetInputBox;
		
		@FindBy(css = "div.select2-with-searchbox.select2-drop-active > ul.select2-results")
		private WebElement pSetSelectResults;	
		
		@FindBy(css = "div.sub-layer1 > div.dropdown > ul.dropdown-menu > li > a")
		private List<WebElement> pSetItems;
		
		@FindBy(css = "div.action-buttons > a.icon_list")
		private WebElement jobIconBtn;
		//Karen
		
		@FindBy(css = "div.action-buttons > a.icon_list_loading")
		private WebElement loadingIconBtn;
		
		@FindBy(css = "div.action-buttons > a.icon_cancel_analytic")
		private WebElement cancelIconBtn;
		
		@FindBy(css = "div.row-fluid >  div.results > h3 > span[key='_Anr.Results.Title_'")
		private WebElement resultsLable;

		//Karen may remove for ANR
		@FindBy(css = "div.action-buttons > a.icon_comment")
		private WebElement scheduleIconBtn;
		
		@FindBy(css = "div[style*='height: auto'] > div.drawer[ng-show*='results'] span[key*='_AX_ResultHistoryList.Title_']")
		private List<WebElement> resultsHistory;
		
//		@FindBy(css = "div[style*='height: auto'] > div.drawer[ng-show*='results'] span[key*='results']")
//		private List<WebElement> currentJobs;
		
		        @FindBy(css = "div[style*='height: auto'] > div.drawer[ng-show*='results'] > div > h3 > span")
		        private WebElement jobDrawerHeader;
		        @FindBy(css = "div[style*='height: auto'] > div.drawer[ng-show*='results'] > div.headlines > div > span")
		        private WebElement jobsTableColHeader;
				@FindBy(css = "div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div:nth-child(1)")
				private List<WebElement> jobRunBy;
				@FindBy(css = "div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div:nth-child(2)")
				private List<WebElement> jobParameterSet;
				@FindBy(css = "div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div:nth-child(3) > span")
				private List<WebElement> jobStartTime;
				@FindBy(css = "div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div:nth-child(4) > span")
				private List<WebElement> jobEndTime;
				@FindBy(css = "div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div:nth-child(5)")
				private List<WebElement> jobStatus;
				@FindBy(css = "div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div:nth-child(6) > a > span")
				private List<WebElement> jobResultLink;
		//Karen
				
	public WebElement locateAnalytic(String analyticName){
		WebElement analytic = null;
		for(int i = 0; i < analytics.size(); i++) {
			if (analytics.get(i).getText().equalsIgnoreCase(analyticName)) {
				logTAFInfo("Working analytic: '"+analyticName+"'");
				analytic = analytics.get(i);
				return analytic;
			}
		}
		
		return analytic;
	}
		
	public void openAnalytic(){
		openAnalytic(anName,true);
	}
	
	public void closeAnalytic(){
		openAnalytic(anName,false);
	}
	public void openAnalytic(String analyticName){
		openAnalytic(analyticName,true);
	}	
	public void closeAnalytic(String analyticName){
		openAnalytic(analyticName,false);
	}	
	private void openAnalytic(String analyticName,boolean expand){
		
		if(analyticName.equalsIgnoreCase(anName)){
			logTAFInfo("Warning: you need to reinitial search context");
			return;
		}
		
		toggleElementByClick(description,runIconBtn,"Analytic",expand);
	}
	
	public String getAnalyticDescription(){
		return description.getText();
	}
	
	public void cancelAnalytic(){
		waitUntil(cancelIconBtn);
		click(cancelIconBtn,"Cancel(X)");
	}	
	
   public boolean deleteParameterSet(String analyticName, String pSetName){
	     return true;
   }

	public boolean setParameterSet(String[] setValues,int startIndex){
		click(continueBtn,"Continue",newPSetBtn);
		sleep(2);
		click(newPSetBtn,"New");
		
		for(int i=startIndex+1;i<setValues.length&&!setValues.equals("");i++){
			waitUntil(formInputContinueBtn);
			String value = setValues[i];
			if(!value.trim().equals(""))
			    inputChars(pSetValueBox, value);
			if(inputValueError.size()>0){
			  if(inputValueError.get(0).isDisplayed()){
				String error = inputValueError.get(0).getText()	;
				if(error.trim().length()>2){
				  logTAFError(error+" - '"+value+"'");
				  return false;
				}
			  }
			}
			if(formInputContinueBtn.isDisplayed()){
			   click(formInputContinueBtn,"Continue");
			}
		}
		
		String name = setValues[startIndex];
		if(!name.trim().equals("")){
		  waitUntil(pSetNameBox);		
		  inputChars(pSetNameBox,name);
     	}
		
		if(inputNameError.size()>0){
			if(inputNameError.get(0).isDisplayed()){
			  String error = inputNameError.get(0).getText()	;
			  if(error.trim().length()>2){
			    logTAFError(error+" - '"+setValues[startIndex]+"'");
			    return false;
			  }
			}
		}
		
		if(formNameContinueBtn.isDisplayed()){
		    click(formNameContinueBtn,"Continue");
		}
		return true;
	}
	
	public boolean selectParameterSet(String[] setValues,int startIndex){
		boolean done = true;

		click(continueBtn,"Continue");
		click(pSetDropdown,"Pick A Parameter Set", pSetInputBox);
		inputChars(pSetInputBox,setValues[startIndex]);
		pSetInputBox.sendKeys(Keys.ENTER);

		if(pSetSelectResults.isDisplayed()){
			try{
			   String error = pSetSelectResults.findElement(By.cssSelector("li.select2-no-results")).getText();
			   logTAFError(error+" - '"+setValues[startIndex]+"'");
			   done = false;
			}catch(Exception e){
			}
			
		}
		// Check input values here ... 
		return done;
	}
	
	public void endWith(String command){
		   String[] comms = command.split("\\|");
		   for(String comm:comms){
			   if(comm.equalsIgnoreCase("Run")){
				   runAnalytic();
			   }else if(comm.equalsIgnoreCase("Cancel")){
				   //
			   }else if(comm.equalsIgnoreCase("Verify")){
				   //
			   }
		   }
	   }
	   
	public void runAnalytic(){

		   final int maxWait = 300;
		   final int sleepPeriod = 5;
		   
		   // click(formRunBtn,"Run");
		   waitUntil(runBtn);
		   click(runBtn,"Run");
		   
		   WebDriverWait runWait = new WebDriverWait(pageDriver, maxWait,  sleepPeriod*1000);
		   waitUntil(runWait,resultsLable,true);
		   
   }
  
	public ANR_AnalyticPage(WebDriver driver) {
	    pageDriver = driver; 
	    pageDriverWait = new WebDriverWait(driver,30);
	    
	    qfPage = PageFactory.initElements(driver, QuickFilterPage.class);
	}

}
