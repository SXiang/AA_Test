package ax.testdriver.restapi;

import ax.lib.TestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;



public class TestDriverExample  extends TestDriverHelper implements TestDriverInterface{
	
	
    public static void main(String[] args){
    	TestDriverExample test = new TestDriverExample();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		
		startFromLine = 5; // 4-9
		endAtLine =5;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/TestDriverExample.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}
	
}

