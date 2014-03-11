package ax.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;
import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestCaseSortOnFilterPanel extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestCaseSortOnFilterPanel test = new TestCaseSortOnFilterPanel();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		endAtLine =162;     //
			
		String poolFile = "testdata/ax/testdriver/frontend/SortOnFilterPanel.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
