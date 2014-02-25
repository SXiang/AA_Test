/**
 * 
 */
package anr.lib.frontend;

import org.openqa.selenium.WebDriver;

import com.acl.qa.taf.helper.TestDriverSuperHelper;
import com.acl.qa.taf.helper.TestSuiteSuperHelper;

/**
 * Script Name   : <b>FrontendTestSuiteHelper.java</b>
 * Generated     : <b>1:59:25 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Dec 31, 2013
 * @author steven_xiang
 * 
 */
public class FrontendTestSuiteHelper extends TestSuiteSuperHelper{

	/**
	 * 
	 */
	// Variables shared among tests
	//Karen 02/06
	//public WebDriver currentDriver = null;
	//public String currentImageName = "";
//Karen
	
	public WebDriver currentDriver = null;
	public String currentImageName = "";
	public String browserType = "";
	public boolean casAuthenticated = false;
	public String currentDriverName = "";	

	// ***********************************************
	public FrontendTestSuiteHelper() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param args
	 */
	public void testMain(Object[] args) {
		// TODO Auto-generated method stub
		
	}

}