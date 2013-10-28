package ax.testdriver.restapi;

import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestGetAnalyticDetails extends TestDriverExampleHelper implements TestDriverInterface{
	
	
    public static void main(String[] args){
    	TestGetAnalyticDetails test = new TestGetAnalyticDetails();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		
		//startFromLine = 11; // 4-9
		//endAtLine = 12;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/GetAnalyticDetails.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
	}
	
}
