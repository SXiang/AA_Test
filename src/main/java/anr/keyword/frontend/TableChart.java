/**
 * 
 */
package anr.keyword.frontend;

/**
 * Script Name   : <b>TableChart.java</b>
 * Generated     : <b>3:42:35 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Dec 5, 2013
 * @author steven_xiang
 * 
 */


import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import anr.apppage.DataVisualizationPage;
import anr.lib.frontend.*;
import ax.lib.frontend.ResultsHelper;

import com.acl.qa.taf.helper.Interface.KeywordInterface;
import com.acl.qa.taf.util.FileUtil;
import com.acl.qa.taf.util.FormatHtmlReport;
import com.acl.qa.taf.util.UTF8Control;



public class TableChart extends DataVisualizationHelper implements KeywordInterface {

	// AX Server: autoqawin2012.aclqa.local - 10.83
	// BEGIN of datapool variables declaration
	protected String dpCategory;   	//@arg value of Category
	protected String dpSubCategory;   	//@arg value of Sub-Category, for certain charts
	protected String dpValue;   	    //@arg value of chart
	protected String dpSummarizedType; //@arg summarized type 
	                                   //@value = Sum|Average|Min|Max
	protected String dpChartAction;   //@arg action for chart configuration
	                                  //@value = Delete|Add|Modify|Verify
	protected int dpChartIndex;    //@arg tab index
	protected String dpChartType;     //@arg chart type to work on
	                                  //value = PieChart|BarChart|AreaChart
	// END of datapool variables declaration

	protected DataVisualizationPage dvpage;
	
	@Override
	public boolean dataInitialization() {

		super.dataInitialization();
		
		//*** read in data from datapool     

		dpCategory = getDpString("Category");
		dpSubCategory = getDpString("SubCategory");
		dpValue = getDpString("Value");
		dpSummarizedType = getDpString("SummarizedType");
		dpChartAction = getDpString("ChartAction");
		dpChartIndex = getDpInteger("ChartIndex");
        dpChartType = getDpString("ChartType");
        delFile = true;
		return true;
	}
	
	//***************  Part 2  *******************
	// *********** Test logic ********************
	// *******************************************
	
	//Comments - 'Steps' in this part are used to generate keyword docs
	@Override
	public void testMain(Object[] args){			
		super.testMain(onInitialize(args, getClass().getName()));
		
		//Steps:
		//@Step Verify data visualization page
		//@Step Create/Delete charts
		//@Step ...
		
		sleep(2);
		dvpage = PageFactory.initElements(driver, DataVisualizationPage.class);
		if(dpChartAction.equalsIgnoreCase("Add")){
			logTAFStep("Click adding new chart");
			//dvpage.addNewChart.click();
			dvpage.addNewChart(dpChartType);
			//dvpage.expandConfPanel(true);
			configChart();
		}else{
			dvpage.activateChart(dpChartIndex);
			//dvpage.expandConfPanel(true);
			if(dpChartAction.equalsIgnoreCase("Delete")){
				dvpage.deleteConf();
		    }else if(dpChartAction.equalsIgnoreCase("Modify")){
		    	configChart();
		    }else if(dpChartAction.equalsIgnoreCase("Verify")){
			    verifyChart();
		    }
		}
		
		doVerification();
	
		cleanUp();		
	  //*** cleanup by framework ***		
		onTerminate();
	}
	
	//***************  Part 3  *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	private void configChart(){
		dvpage.selectChartValue("Category",dpCategory);
		dvpage.selectChartValue("Sub-Category",dpSubCategory);
		dvpage.selectChartValue("Value",dpValue);
		dvpage.applyChartConf.click();
		//dvpage.expandConfPanel(false);
	}
	
	private void verifyChart(){
		dvpage.verifyChartConf("Category",dpCategory);
		dvpage.verifyChartConf("Sub-Category",dpSubCategory);
		dvpage.verifyChartConf("Value",dpValue);
	}
	


	public void doVerification(){
		String _masterImage = setupMasterFile(dpMasterFiles[0]);
		String _actualImage = thisActualFile;
		String extendedImage = "";
		String masterImage = _masterImage;
		String actualImage = _actualImage;
		
		if(_masterImage.equals(""))
			return;
		//dvpage.expandConfPanel(false);
		logTAFStep("Verify original chart...");
		dvpage.saveChartImage(driver,actualImage);
		verifyImage(masterImage,actualImage);
		
		logTAFStep("Verify chart by click one series");
		extendedImage = "_toggle1.jpeg";
		masterImage = modifyMasterFile(extendedImage);
		actualImage = thisActualFile;
		//dvpage.selectSeries(driver,false);
		//dvpage.saveChartImage(driver,actualImage);
		//verifyImage(masterImage,actualImage);
		
		logTAFStep("Verify chart by double click one series");
		
		extendedImage = "_toggle2.jpeg";
		masterImage = modifyMasterFile(extendedImage);
		actualImage = thisActualFile;
		//dvpage.selectSeries(driver,true);
		//dvpage.saveChartImage(driver,actualImage);
		//verifyImage(masterImage,actualImage);
		
		}


 }