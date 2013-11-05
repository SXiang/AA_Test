package anr.testdriver.frontend;

import anr.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestOpenProject extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestOpenProject test = new TestOpenProject();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		//startFromLine = 2; // 4-9
		//endAtLine = 8;     //
			
		String poolFile = "testdata/anr/testdriver/frontend/OpenProject.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}