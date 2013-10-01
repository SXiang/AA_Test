package ax.testdriver.restapi;

import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestGetProjectDetail  extends TestDriverExampleHelper implements TestDriverInterface {
	
    public static void main(String[] args){
    	TestGetProjectDetail test = new TestGetProjectDetail();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		
		startFromLine = 2; // 4-9
		endAtLine = 2;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/GetProjectDetail.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
	}
	
}
