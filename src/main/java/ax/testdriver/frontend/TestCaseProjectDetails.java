package ax.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestCaseProjectDetails extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestCaseProjectDetails test = new TestCaseProjectDetails();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		//endAtLine = 28;     //
			
		String poolFile = "testdata/ax/testdriver/frontend/ProjectDetails.xls";
<<<<<<< HEAD
=======
		//String poolFile = "src/main/resources/testdata/ax/testdriver/frontend/ProjectDetails.xls";
>>>>>>> d4a1162293cd399ad1b1f505feb1f127c4234c40
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
