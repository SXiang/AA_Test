package ax;

import com.acl.qa.taf.helper.Interface.TestSuiteInterface;
import ax.lib.restapi.TestSuiteExampleHelper;

public class TestSuiteFrontEnd extends TestSuiteExampleHelper implements TestSuiteInterface {

	/**
	 * Script Name   : <b>TestSuiteFrontEnd</b>
	 * Generated     : <b>Sep 18, 2013</b>
	 * Description   : ACL Test Automation TestSuiteFrontEnd
	 * 
	 * @author Ramneet_Kaur
	 */
		
    public static void main(String[] args){
    	TestSuiteFrontEnd test = new TestSuiteFrontEnd();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
	@Override
	public void testMain(Object[] args) 
	{
		//testCategory = "Daily";
		//startFromLine = 37; // 4-9
		//endAtLine =2;     //
		//logTAFInfo(System.getProperty("user.dir"));
    	String poolFile = "testdata/ax/TestSuiteFrontEnd.xls";
		exeBatchRun(onInitialize(poolFile, getClass().getName()));
	}
			
}
