package ax.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestCaseProjectsList extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestCaseProjectsList test = new TestCaseProjectsList();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		//endAtLine = 16;     //
			
		String poolFile = "testdata/ax/testdriver/frontend/ProjectsList.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
