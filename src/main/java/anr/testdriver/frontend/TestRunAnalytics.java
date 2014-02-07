package anr.testdriver.frontend;

import anr.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;
/**
 * Script Name   : <b>TestRunAnalyticsWithNewPSet.java</b>
 * Generated     : <b>3:02:59 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Feb. 4, 2014
 * @author Karen Zou
 * 
 */
public class TestRunAnalytics extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestRunAnalytics test = new TestRunAnalytics();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		endAtLine = 7;     //
			
		String poolFile = "src/main/resources/testdata/anr/testdriver/frontend/RunAnalytics.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));		
	}

}