package ax.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestCaseTestSetDetails extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestCaseTestSetDetails test = new TestCaseTestSetDetails();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		//endAtLine = 9;     //
			
		String poolFile = "testdata/ax/testdriver/frontend/TestSetDetails.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
