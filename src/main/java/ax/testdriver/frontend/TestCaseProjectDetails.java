package ax.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestCaseProjectDetails extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestCaseProjectDetails test = new TestCaseProjectDetails();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		//endAtLine = 4;     //
			
		String poolFile = "testdata/ax/testdriver/frontend/ProjectDetails.xls";

		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
