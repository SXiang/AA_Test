package ax.testdriver.restapi;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

import ax.TestSuiteExample;
import ax.lib.TestDriverHelper;

public class TestDriverExample extends TestDriverHelper implements TestDriverInterface{
	
    public static void main(String[] args){
    	TestDriverExample test = new TestDriverExample();
    	test.setMainScript(true);
    	test.onInitialize(args);
    }
    
	public void testMain(Object[] args) 
	{

		//startFromLine = 37; // 4-9
		//endAtLine =2;     //
		exeTestCase(args);
	}
		
	public void onInitialize(Object[] args) {
		String poolFile = "testdata/ax/TestSuiteExample.xls";
		testMain(onInitialize(poolFile, getClass().getName()));
	}
	
}

