package ax.keyword.frontend;

import ax.lib.frontend.LogOutHelper;

public class LogOut extends LogOutHelper{
	
	/**
	 * Script Name   : <b>ProjectsList</b>
	 * Generated     : <b>Dec 12, 2013</b>
	 * Description   : Logout keyword
	 * 
	 * @author Yousef Aichour
	 */
	//***************  Part 1  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		return true;
	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
	
		logout();
		cleanUp();
		
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {
		//LogOut debug = new LogOut();
		//debug.logout()
	}
}
