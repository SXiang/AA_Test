package anr.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;

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

		startFromLine = 2; // 4-9
		endAtLine = 2;     //
			
		String poolFile = "src/main/resources/testdata/anr/testdriver/frontend/OpenProject.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));		
	}

}