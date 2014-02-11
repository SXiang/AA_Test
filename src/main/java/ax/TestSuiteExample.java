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
		// 2 : testDrivrExample
		// 3 - 10: API Get and Post
		// 11 - 20  : ANR
		// 21 -  : AX
		
		startFromLine = 11; // 4-9
		//endAtLine =8;     //
		
		//logTAFInfo(System.getProperty("user.dir"));
    	String poolFile = "testdata/ax/TestSuiteExample.xls";
		exeBatchRun(onInitialize(poolFile, getClass().getName()));
	}
		
	
}
