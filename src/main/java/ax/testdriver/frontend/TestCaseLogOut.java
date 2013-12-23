package ax.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestCaseLogOut extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestCaseLogOut test = new TestCaseLogOut();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		//endAtLine =2;     //
			
		String poolFile = "testdata/ax/testdriver/frontend/LogOut.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}