package ax.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;
import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestCaseFilterPanel extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestCaseFilterPanel test = new TestCaseFilterPanel();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		//endAtLine =20;     //
			
		String poolFile = "testdata/ax/testdriver/frontend/FilterPanel.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
