// DO NOT EDIT: This file is automatically generated.
//
// Only the associated template file should be edited directly.
// Helper class files are automatically regenerated from the template
// files at various times, including record actions and test object
// insertion actions.  Any changes made directly to a helper class
// file will be lost when automatically updated.

package resources.ACL_Desktop.Tasks.Sampling;
import ACL_Desktop.AppObject_Karen.DesktopSuperHelper;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.vp.IFtVerificationPoint;

/**
 * Script Name   : <b>evaluateerror</b><br>
 * Generated     : <b>2012/04/18 11:03:15 PM</b><br>
 * Description   : Helper class for script<br>
 * Original Host : Windows 7 x86 6.1 build 7601 Service Pack 1 <br>
 * 
 * @since  April 18, 2012
 * @author Karen_zou
 */
public abstract class evaluateerrorHelper extends ACL_Desktop.AppObject_Karen.DesktopSuperHelper
{
	/**
	 * Evaluate: with default state.
	 *		.text : Evaluate
	 * 		.class : #32770
	 * 		.processName : ACLWin.exe
	 * 		.name : Evaluate
	 */
	protected TopLevelSubitemTestObject mainDialog() 
	{
		return new TopLevelSubitemTestObject(
                        getMappedTestObject("mainDialog"));
	}
	/**
	 * Evaluate: with specific test context and state.
	 *		.text : Evaluate
	 * 		.class : #32770
	 * 		.processName : ACLWin.exe
	 * 		.name : Evaluate
	 */
	protected TopLevelSubitemTestObject mainDialog(TestObject anchor, long flags) 
	{
		return new TopLevelSubitemTestObject(
                        getMappedTestObject("mainDialog"), anchor, flags);
	}
	
	

	protected evaluateerrorHelper()
	{
		setScriptName("ACL_Desktop.Tasks.Sampling.evaluateerror");
	}
	
}

