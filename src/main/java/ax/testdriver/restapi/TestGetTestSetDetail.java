package ax.testdriver.restapi;

import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestGetTestSetDetail  extends TestDriverExampleHelper implements TestDriverInterface {
	
    public static void main(String[] args){
    	TestGetTestSetDetail test = new TestGetTestSetDetail();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		
		//startFromLine = 18; // 4-9
		//endAtLine = 18;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/GetTestSetDetail.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
	}
	
}