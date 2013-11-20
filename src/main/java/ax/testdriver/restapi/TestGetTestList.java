package ax.testdriver.restapi;

import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestGetTestList  extends TestDriverExampleHelper implements TestDriverInterface {
	
    public static void main(String[] args){
    	TestGetTestList test = new TestGetTestList();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		
		//startFromLine = 16; // 4-9
		//endAtLine = 16;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/GetTestList.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
	}
	
}