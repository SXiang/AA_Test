package ax.lib.frontend;

import org.openqa.selenium.WebDriver;

import com.acl.qa.taf.helper.TestDriverSuperHelper;

public class FrontendTestDriverHelper extends TestDriverSuperHelper{
	
	//Variables could be shared among keywords of same test suite.
	
		public WebDriver currentDriver = null;
		public String currentImageName = "";
		public String browserType = "";
		public boolean casAuthenticated = false;
		public String currentDriverName = "";		
		// ***********************************************
		public FrontendTestDriverHelper() {
			// TODO Auto-generated constructor stub
		}
		
		/**
		 * @param args
		 */
		public void testMain(Object[] args) {
			// TODO Auto-generated method stub
			
		}

}
