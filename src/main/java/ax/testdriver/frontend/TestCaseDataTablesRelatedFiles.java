package ax.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestCaseDataTablesRelatedFiles extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestCaseDataTablesRelatedFiles test = new TestCaseDataTablesRelatedFiles();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		//endAtLine = 9;     //
			
		String poolFile = "testdata/ax/testdriver/frontend/DataTablesRelatedFiles.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
