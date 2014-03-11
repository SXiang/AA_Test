package ax.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;
import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestCaseQuickFilter extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestCaseQuickFilter test = new TestCaseQuickFilter();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
	@Override
	public void testMain(Object[] args) 
	{		
		startFromLine = 2; // 4-9
		endAtLine =60;     //			
		String poolFile = "testdata/ax/testdriver/frontend/QuickFilter.xls";
		//String poolFile = "testdata/ax/testdriver/frontend/EditCriteriaFilter.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
	}

}
