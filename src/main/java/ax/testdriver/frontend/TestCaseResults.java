package ax.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestCaseResults extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestCaseResults test = new TestCaseResults();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		//endAtLine = 8;     //
			
		String poolFile = "testdata/ax/testdriver/frontend/Results.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
