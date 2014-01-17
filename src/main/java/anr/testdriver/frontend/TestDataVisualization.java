package anr.testdriver.frontend;

import anr.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestDataVisualization extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestDataVisualization test = new TestDataVisualization();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		//startFromLine = 2; // 4-9
		//endAtLine = 8;     //
			
		String poolFile = "src/main/resources/testdata/anr/testdriver/frontend/DataVisualization.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));		
	}

}