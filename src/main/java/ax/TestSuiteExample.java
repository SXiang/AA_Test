package ax;
import com.acl.qa.taf.helper.Interface.TestSuiteInterface;

import ax.lib.restapi.TestSuiteExampleHelper;

public class TestSuiteExample extends TestSuiteExampleHelper implements TestSuiteInterface {

	/**
	 * Script Name   : <b>TestSuiteExample</b>
	 * Generated     : <b>Aug 10, 2013 10:34:01 AM</b>
	 * Description   : ACL Test Automation
	 * 
	 * @since  2013/08/10
	 * @author Steven_Xiang
	 */
		
    public static void main(String[] args){
    	TestSuiteExample test = new TestSuiteExample();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
	@Override
	public void testMain(Object[] args) 
	{
		//testCategory = "Daily";
		startFromLine = 3; // 4-9
		endAtLine =5;     //
		//logTAFInfo(System.getProperty("user.dir"));
    	String poolFile = "testdata/ax/TestSuiteExample.xls";
		exeBatchRun(onInitialize(poolFile, getClass().getName()));
	}
		
	
}
