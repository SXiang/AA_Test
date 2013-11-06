package ax.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestCaseTestDetails extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestCaseTestDetails test = new TestCaseTestDetails();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		//startFromLine = 2; // 4-9
		//endAtLine = 28;     //
			
		String poolFile = "testdata/ax/testdriver/frontend/TestDetails.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
