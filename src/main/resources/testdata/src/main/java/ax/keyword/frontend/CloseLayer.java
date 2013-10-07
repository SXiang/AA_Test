package ax.keyword.frontend;

import ax.lib.frontend.FrontendCommonHelper;

public class CloseLayer  extends FrontendCommonHelper{
	
	/**
	 * Script Name   : <b>CloseLayer</b>
	 * Generated     : <b>Sep 7, 2013</b>
	 * Description   : CloseLayer keyword
	 * 
	 * @author Ramneet Kaur
	 */

	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	// END of datapool variables declaration
	
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
		closeLayer();
		cleanUp();
		// *** cleanup by framework ***
		onTerminate();
	}
	

	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {
		//CloseLayer debug = new CloseLayer();
		//debug.closeLayer();
	}
}
