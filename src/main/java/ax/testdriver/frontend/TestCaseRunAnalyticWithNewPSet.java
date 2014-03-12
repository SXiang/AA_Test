package ax.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;
import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestCaseRunAnalyticWithNewPSet extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestCaseRunAnalyticWithNewPSet test = new TestCaseRunAnalyticWithNewPSet();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		//endAtLine =20;     //
			
		String poolFile = "testdata/ax/testdriver/frontend/TestRunAnalyticWithNewPSet.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
