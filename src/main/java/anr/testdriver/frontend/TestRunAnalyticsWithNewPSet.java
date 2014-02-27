/**
 * 
 */
package anr.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

/**
 * Script Name   : <b>TestRunAnalyticsWithNewPSet.java</b>
 * Generated     : <b>3:02:59 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Jan 8, 2014
 * @author steven_xiang
 * 
 */
public class TestRunAnalyticsWithNewPSet  extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestRunAnalyticsWithNewPSet test = new TestRunAnalyticsWithNewPSet();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		//startFromLine = 12; // 4-9,12(1000 records
		//endAtLine = 6;     //
			
		String poolFile = "testdata/ax/testdriver/frontend/TestRunAnalyticWithNewPSet.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));		
	}
}
