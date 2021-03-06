// DO NOT EDIT: This file is automatically generated.
//
// Only the associated template file should be edited directly.
// Helper class files are automatically regenerated from the template
// files at various times, including record actions and test object
// insertion actions.  Any changes made directly to a helper class
// file will be lost when automatically updated.

package resources.ACL_Desktop.Tasks.Analyze;
import ACL_Desktop.AppObject.DesktopSuperHelper;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.vp.IFtVerificationPoint;

/**
 * Script Name   : <b>crossTabulate</b><br>
 * Generated     : <b>2012/04/02 12:40:33 PM</b><br>
 * Description   : Helper class for script<br>
 * Original Host : Windows XP x86 5.1 build 2600 Service Pack 3 <br>
 * 
 * @since  April 02, 2012
 * @author Steven_Xiang
 */
public abstract class crossTabulateHelper extends ACL_Desktop.AppObject.DesktopSuperHelper
{
	/**
	 * CrossTabulate: with default state.
	 *		.text : Cross-tabulate
	 * 		.class : #32770
	 * 		.processName : ACLWin.exe
	 * 		.name : Cross-tabulate
	 */
	protected TopLevelSubitemTestObject mainDialog() 
	{
		return new TopLevelSubitemTestObject(
                        getMappedTestObject("mainDialog"));
	}
	/**
	 * CrossTabulate: with specific test context and state.
	 *		.text : Cross-tabulate
	 * 		.class : #32770
	 * 		.processName : ACLWin.exe
	 * 		.name : Cross-tabulate
	 */
	protected TopLevelSubitemTestObject mainDialog(TestObject anchor, long flags) 
	{
		return new TopLevelSubitemTestObject(
                        getMappedTestObject("mainDialog"), anchor, flags);
	}
	
	/**
	 * CrossTabulate: with default state.
	 *		.text : Cross Tabulate
	 * 		.class : #32770
	 * 		.name : Cross Tabulate
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject mainTab() 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("mainTab"));
	}
	/**
	 * CrossTabulate: with specific test context and state.
	 *		.text : Cross Tabulate
	 * 		.class : #32770
	 * 		.name : Cross Tabulate
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject mainTab(TestObject anchor, long flags) 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("mainTab"), anchor, flags);
	}
	
	

	protected crossTabulateHelper()
	{
		setScriptName("ACL_Desktop.Tasks.Analyze.crossTabulate");
	}
	
}

