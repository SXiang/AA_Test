package ax.keyword.frontend;

import ax.lib.frontend.LoginHelper;

public class Login extends LoginHelper{
	/**
	 * Script Name   : <b>Login</b>
	 * Generated     : <b>Sep 4, 2013</b>
	 * Description   : Login keyword
	 * 
	 * @author Ramneet Kaur
	 */
	
	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpCasType; //@arg SSO or nonSSO
	                            // @value = SSO|nonSSO
	protected String dpUserName; //@arg username for login
	protected String dpPassword; //@arg password for login
	// END of datapool variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpCasType = projectConf.getCasType();
		dpUserName = getDpString("UserName");
		dpPassword = getDpString("Password");
		//END
		return true;
	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		login();
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	public void login(){
		login(dpUserName,dpPassword,dpCasType);
	}
	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {
		//Login debug = new Login();
		//debug.login();
	}
}
