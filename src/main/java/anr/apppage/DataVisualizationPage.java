/**
 * 
 */
package anr.apppage;

import java.awt.Rectangle;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Script Name   : <b>DataVisualizationPage.java</b>
 * Generated     : <b>5:38:19 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Dec 12, 2013
 * @author steven_xiang
 * 
 */
public class DataVisualizationPage extends WebPage{

	//*** Final varialbes	
	 private  final WebDriver pageDriver;
	 private final int chartLoadTime = 3;
	 private final int windowWidth = 1000;
	 private final int windowHeight = 700;
    //*** Common elements
	  @FindBy(xpath = "//div[@class='tabbable']/ul[@class='nav nav-tabs']/li[@active='t.active']")
	  private List<WebElement> navTabs;
	 @FindBy(xpath = "//div[@ng-click='openChartSelectorModal()' and @tooltip='Add a new chart']/i[@type='button']")
	  //@FindBy(xpath = "//div[@tooltip='Add a new chart']/i[@type='button']")
	  private WebElement addNewChart;  
	  
    //*** Add chart
	  @FindBy(xpath = "//div[@ng-click='pickChart(chartType)' and @tooltip='Pie Chart']")
	  public WebElement pieChart;
	  @FindBy(xpath = "//div[@ng-click='pickChart(chartType)' and @tooltip='Stacked Area Chart']")
	  public WebElement areaChart;
	  @FindBy(xpath = "//div[@ng-click='pickChart(chartType)' and @tooltip='Bar Chart']")
	  public WebElement barChart;
	  
	//*** Chart configuration
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//i[@ng-click='toggleChartConfigPanel()']") 
	  ///html/body/div/div/div/div/div[2]/div/section/div[2]/div/div[3]/div/div[2]/div/div[1]/i
	  public WebElement configurePanel;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//i[@ng-click='toggleChartConfigPanel()']/div[@class='chartconfig-btn ng-binding' and text()='Configure']") 
	  public List<WebElement> configureButtons;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/select[@tooltip='Category']") 
	  public WebElement categorySelect;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/select[@tooltip='Sub-Category']")
	  public WebElement subCategorySelect;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/select[@tooltip='Value']")
	  public WebElement valueSelect;
	  
	  
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/div/button[@btn-radio='average']")
	  public WebElement averageChart;	  
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/div/button[@btn-radio='sum']")
	  public WebElement sumChart;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/div/button[@btn-radio='min']")
	  public WebElement minChart;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/div/button[@btn-radio='max']")
	  public WebElement maxChart;
	  
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider-last']/input[@id='submit' and @value='Apply']")
	  public WebElement applyChartConf;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider-last']/input[@value='Delete chart']")
	  public WebElement deleteChartConf;
	  
	  
     //*** Chart Details
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//*[local-name()='g' and @class='nv-series']"+
              "/*[local-name()='text' and @class='nv-legend-text' and @text-anchor='start']")
      public List<WebElement> nvSeries;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//*[local-name()='g' and @class]"+
                         "/*[local-name()='text' and @class='nv-legend-text' and @text-anchor='start' and text()='Stacked']")
	  public WebElement stacked;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//*[local-name()='g' and @class]"+
              "/*[local-name()='text' and @class='nv-legend-text' and @text-anchor='start' and text()='Stream']")
      public WebElement stream;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//*[local-name()='g' and @class]"+
              "/*[local-name()='text' and @class='nv-legend-text' and @text-anchor='start' and text()='Expanded']")
      public WebElement expanded;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//*[local-name()='svg']")
		 
	  public WebElement chartWarp;
	  
	  public WebElement activateChart(int tabIndex){
		  WebElement currentTab = navTabs.get(tabIndex);
		  logTAFStep("Activate chart - tab "+tabIndex);
		  //currentTab.click();
		  click(currentTab,"Tab-"+tabIndex);
		  sleep(chartLoadTime);
		  return currentTab;
	  }
	  
	  public void expandConfPanel(){	
		  expandConfPanel(true);
	  }
	  
	  public void expandConfPanel(boolean expand){
		  try{
			  if(!applyChartConf.isDisplayed()){
				  if(expand){
					  logTAFStep("Expand configuration panel by clicking 'Configure' button");
					  toggleConfPanel();
				  }
			  }else{
					  if(!expand){
						  logTAFStep("Hide configuration panel by clicking 'Configure' button");
                          toggleConfPanel();
					  }
				  }
			  
				  }catch(Exception e){
					  logTAFWarning(e.toString());
				  }
	  }
	  
	  private void toggleConfPanel(){
		  for(WebElement conf:configureButtons){
			  if(conf.isDisplayed()){
				  click(conf);
				  break;
			  }
		  }
	  }
	  public void selectChartValue(String type, String option){
		  if(option==null||option.trim().equals(""))
			  return;
		  Select sel = getSelect(type);
		  selectItem(sel,option);

	  }
	  
	  public void verifyChartConf(String type, String option){
		  if(option==null||option.trim().equals(""))
			  return;
		  Select select = getSelect(type);		
		  selectItem(select,option,"Verify");
	  }
	  
	  public void deleteConf(){
		  logTAFStep("Delete chart configuration");
		  click(deleteChartConf,"Delete Chart");
		  //deleteChartConf.click();
	  }
	  
	  public void saveChartImage(WebDriver pageDriver, String fileName){
		  sleep(chartLoadTime);
		  expandConfPanel(false);
		  Window win = pageDriver.manage().window();
		  int removeEdge = 0;
		  Point winpt = win.getPosition();
		  Dimension windi = win.getSize();
		  Point pt = new Point(winpt.x+200,winpt.y+200);
		  Dimension di = new Dimension(windi.width-400,windi.height-200);
		  try{//debugging... 
		    pt = chartWarp.getLocation();
		    di = chartWarp.getSize();
		  }catch(Exception e){
			  logTAFWarning("ChartWarp not found?"+e.toString().substring(0,10)+"...");
		  }
		  
		  int width = di.width+pt.x > windi.width + winpt.x ? (windi.width - pt.x -removeEdge):di.width-removeEdge;
		  int height = di.height+pt.y > windi.height + winpt.y ? (windi.height - pt.y -removeEdge):(di.height-removeEdge);
		  
		  Rectangle rec = new Rectangle(pt.x, pt.y, width, height);
		  logTAFStep("Take screenshot on current chart rectangle '"+rec+"'");
		  captureScreen(fileName, rec);
		  logTAFInfo("Chart image is saved to  '"+fileName+"'");
		  
	  }
	  public void selectArea(WebDriver driver,String select){
		  if(select.equalsIgnoreCase("Stream")){
			  click(driver,stream,"Stream");			  
		  }else if(select.equalsIgnoreCase("Expanded")){
			  click(driver,expanded,"Expanded");			  
		  }else if(select.equalsIgnoreCase("Stacked")){
			  click(driver,stacked,"Stacked");			  
		  }
	  }
	  public void selectSeries(WebDriver driver, boolean exclusive){
		  if(nvSeries.size()==0){
			  logTAFWarning("nv-series not found?");
			  return;
		  }
		  WebElement nvs = nvSeries.get(nvSeries.size()/2);
		  
		  if(exclusive){
			  nvs = nvSeries.get(nvSeries.size()/2);
			  //logTAFStep("Double click series '"+nvs.getText());
			  doubleClick(driver,nvs,nvs.getText());
			  //new Actions(driver).doubleClick(nvs).perform();
		  }else{
			  nvs = nvSeries.get(0);
		     // logTAFStep("Click series '"+nvs.getText());
			 // nvs.click();
			  click(nvs,nvs.getText());
		  }
		  
	  }
	  //**** Private methods ****
	  private Select getSelect(String type){
		  Select select;
		  if(type.equalsIgnoreCase("Category")){
			  select = new Select(categorySelect);
		  }else if(type.equalsIgnoreCase("Sub-Category")){
			  select = new Select(subCategorySelect);
		  }else if(type.equalsIgnoreCase("Value")){
			  select = new Select(valueSelect);
		  }else{
			  logTAFWarning("Select type '"+type+"' is not valid? ");
			  select = new Select(categorySelect);
		  }
		  
		  return select;
	  }
	  
	  
	  public void addNewChart(String type){
		  //addNewChart.click();
		  //sleep(chartLoadTime);
		  By pieChartLocator = By.xpath("//div[@ng-click='pickChart(chartType)' and @tooltip='Pie Chart']");
		  click(pageDriver,addNewChart,"Add a chart",pieChart);
		  sleep(chartLoadTime);
		  if(type.equals("BarChart")){
			 // barChart.click();
			  click(barChart);
		  }else if(type.equals("PieChart")){
			  //pieChart.click();
			  click(pieChart);
		  }else if(type.equals("AreaChart")){
			  //areaChart.click();
			  click(areaChart);
		  }else{
			  //pieChart.click();
			  click(pieChart);
		  }
		  sleep(chartLoadTime);
	  }
	  
	  public DataVisualizationPage(WebDriver driver){
		  
		    this.pageDriver = driver; 
		    		    
		    driver.manage().window().setSize(new Dimension(windowWidth,windowHeight));
	  }
}
