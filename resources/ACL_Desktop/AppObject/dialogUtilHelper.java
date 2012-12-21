// DO NOT EDIT: This file is automatically generated.
//
// Only the associated template file should be edited directly.
// Helper class files are automatically regenerated from the template
// files at various times, including record actions and test object
// insertion actions.  Any changes made directly to a helper class
// file will be lost when automatically updated.

package resources.ACL_Desktop.AppObject;
import lib.acl.helper.sup.ObjectHelper;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.vp.IFtVerificationPoint;

/**
 * Script Name   : <b>dialogUtil</b><br>
 * Generated     : <b>2012/03/14 4:53:51 PM</b><br>
 * Description   : Helper class for script<br>
 * Original Host : Windows XP x86 5.1 build 2600 Service Pack 3 <br>
 * 
 * @since  March 14, 2012
 * @author Steven_Xiang
 */
public abstract class dialogUtilHelper extends lib.acl.helper.sup.ObjectHelper
{
	/**
	 * Cancel: with default state.
	 *		.text : Cancel
	 * 		.class : .Pushbutton
	 * 		.name : Cancel
	 * 		.classIndex : 0
	 */
	protected GuiTestObject OpenCancel() 
	{
		return new GuiTestObject(
                        getMappedTestObject("OpenCancel"));
	}
	/**
	 * Cancel: with specific test context and state.
	 *		.text : Cancel
	 * 		.class : .Pushbutton
	 * 		.name : Cancel
	 * 		.classIndex : 0
	 */
	protected GuiTestObject OpenCancel(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("OpenCancel"), anchor, flags);
	}
	
	/**
	 * LastSaved: with default state.
	 *		.text : Last-saved
	 * 		.class : .Pushbutton
	 * 		.name : Last-saved
	 * 		.classIndex : 0
	 */
	protected GuiTestObject OpenLastSaved() 
	{
		return new GuiTestObject(
                        getMappedTestObject("OpenLastSaved"));
	}
	/**
	 * LastSaved: with specific test context and state.
	 *		.text : Last-saved
	 * 		.class : .Pushbutton
	 * 		.name : Last-saved
	 * 		.classIndex : 0
	 */
	protected GuiTestObject OpenLastSaved(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("OpenLastSaved"), anchor, flags);
	}
	
	/**
	 * Working: with default state.
	 *		.text : Working
	 * 		.class : .Pushbutton
	 * 		.name : Working
	 * 		.classIndex : 0
	 */
	protected GuiTestObject OpenWorking() 
	{
		return new GuiTestObject(
                        getMappedTestObject("OpenWorking"));
	}
	/**
	 * Working: with specific test context and state.
	 *		.text : Working
	 * 		.class : .Pushbutton
	 * 		.name : Working
	 * 		.classIndex : 0
	 */
	protected GuiTestObject OpenWorking(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("OpenWorking"), anchor, flags);
	}
	
	/**
	 * Cancel: with default state.
	 *		.text : Cancel
	 * 		.class : .Pushbutton
	 * 		.name : Cancel
	 * 		.classIndex : 0
	 */
	protected GuiTestObject confirm_cancel() 
	{
		return new GuiTestObject(
                        getMappedTestObject("confirm_cancel"));
	}
	/**
	 * Cancel: with specific test context and state.
	 *		.text : Cancel
	 * 		.class : .Pushbutton
	 * 		.name : Cancel
	 * 		.classIndex : 0
	 */
	protected GuiTestObject confirm_cancel(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("confirm_cancel"), anchor, flags);
	}
	
	/**
	 * SetAllOptionsToTheirDefaultSettings: with default state.
	 *		.class : .Statictext
	 */
	protected GuiSubitemTestObject confirm_info() 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("confirm_info"));
	}
	/**
	 * SetAllOptionsToTheirDefaultSettings: with specific test context and state.
	 *		.class : .Statictext
	 */
	protected GuiSubitemTestObject confirm_info(TestObject anchor, long flags) 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("confirm_info"), anchor, flags);
	}
	
	/**
	 * No: with default state.
	 *		.text : No
	 * 		.class : .Pushbutton
	 * 		.name : No
	 * 		.classIndex : 0
	 */
	protected GuiTestObject confirm_no() 
	{
		return new GuiTestObject(
                        getMappedTestObject("confirm_no"));
	}
	/**
	 * No: with specific test context and state.
	 *		.text : No
	 * 		.class : .Pushbutton
	 * 		.name : No
	 * 		.classIndex : 0
	 */
	protected GuiTestObject confirm_no(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("confirm_no"), anchor, flags);
	}
	
	/**
	 * OK: with default state.
	 *		.text : OK
	 * 		.class : .Pushbutton
	 * 		.name : OK
	 * 		.classIndex : 0
	 */
	protected GuiTestObject confirm_ok() 
	{
		return new GuiTestObject(
                        getMappedTestObject("confirm_ok"));
	}
	/**
	 * OK: with specific test context and state.
	 *		.text : OK
	 * 		.class : .Pushbutton
	 * 		.name : OK
	 * 		.classIndex : 0
	 */
	protected GuiTestObject confirm_ok(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("confirm_ok"), anchor, flags);
	}
	
	/**
	 * Yes: with default state.
	 *		.text : Yes
	 * 		.class : .Pushbutton
	 * 		.name : Yes
	 * 		.classIndex : 0
	 */
	protected GuiTestObject confirm_yes() 
	{
		return new GuiTestObject(
                        getMappedTestObject("confirm_yes"));
	}
	/**
	 * Yes: with specific test context and state.
	 *		.text : Yes
	 * 		.class : .Pushbutton
	 * 		.name : Yes
	 * 		.classIndex : 0
	 */
	protected GuiTestObject confirm_yes(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("confirm_yes"), anchor, flags);
	}
	
	/**
	 * Cancel: with default state.
	 *		.text : Cancel
	 * 		.class : .Pushbutton
	 * 		.name : Cancel
	 * 		.classIndex : 0
	 */
	protected GuiTestObject fc_cancel() 
	{
		return new GuiTestObject(
                        getMappedTestObject("fc_cancel"));
	}
	/**
	 * Cancel: with specific test context and state.
	 *		.text : Cancel
	 * 		.class : .Pushbutton
	 * 		.name : Cancel
	 * 		.classIndex : 0
	 */
	protected GuiTestObject fc_cancel(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("fc_cancel"), anchor, flags);
	}
	
	/**
	 * ComboBox: with default state.
	 *		.text : File name:
	 * 		.class : ComboBox
	 * 		.name : File name:
	 * 		.classIndex : 0
	 */
	protected TextSelectGuiSubitemTestObject fc_filename() 
	{
		return new TextSelectGuiSubitemTestObject(
                        getMappedTestObject("fc_filename"));
	}
	/**
	 * ComboBox: with specific test context and state.
	 *		.text : File name:
	 * 		.class : ComboBox
	 * 		.name : File name:
	 * 		.classIndex : 0
	 */
	protected TextSelectGuiSubitemTestObject fc_filename(TestObject anchor, long flags) 
	{
		return new TextSelectGuiSubitemTestObject(
                        getMappedTestObject("fc_filename"), anchor, flags);
	}
	
	/**
	 * Open: with default state.
	 *		.text : Open
	 * 		.class : .Pushbutton
	 * 		.name : Open
	 * 		.classIndex : 0
	 */
	protected GuiTestObject fc_open() 
	{
		return new GuiTestObject(
                        getMappedTestObject("fc_open"));
	}
	/**
	 * Open: with specific test context and state.
	 *		.text : Open
	 * 		.class : .Pushbutton
	 * 		.name : Open
	 * 		.classIndex : 0
	 */
	protected GuiTestObject fc_open(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("fc_open"), anchor, flags);
	}
	
	/**
	 * Save: with default state.
	 *		.text : Save
	 * 		.class : .Pushbutton
	 * 		.name : Save
	 * 		.classIndex : 0
	 */
	protected GuiTestObject fc_save() 
	{
		return new GuiTestObject(
                        getMappedTestObject("fc_save"));
	}
	/**
	 * Save: with specific test context and state.
	 *		.text : Save
	 * 		.class : .Pushbutton
	 * 		.name : Save
	 * 		.classIndex : 0
	 */
	protected GuiTestObject fc_save(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("fc_save"), anchor, flags);
	}
	
	/**
	 * ComboBox: with default state.
	 *		.text : Files of type:
	 * 		.class : ComboBox
	 * 		.name : Files of type:
	 * 		.classIndex : 1
	 */
	protected TextSelectGuiSubitemTestObject fc_type() 
	{
		return new TextSelectGuiSubitemTestObject(
                        getMappedTestObject("fc_type"));
	}
	/**
	 * ComboBox: with specific test context and state.
	 *		.text : Files of type:
	 * 		.class : ComboBox
	 * 		.name : Files of type:
	 * 		.classIndex : 1
	 */
	protected TextSelectGuiSubitemTestObject fc_type(TestObject anchor, long flags) 
	{
		return new TextSelectGuiSubitemTestObject(
                        getMappedTestObject("fc_type"), anchor, flags);
	}
	
	/**
	 * _0: with default state.
	 *		.text : Beep(s) Upon Task Completion
	 * 		.class : Edit
	 * 		.name : Beep(s) Upon Task Completion
	 * 		.classIndex : 0
	 */
	protected TextGuiTestObject op_0_beeps() 
	{
		return new TextGuiTestObject(
                        getMappedTestObject("op_0_beeps"));
	}
	/**
	 * _0: with specific test context and state.
	 *		.text : Beep(s) Upon Task Completion
	 * 		.class : Edit
	 * 		.name : Beep(s) Upon Task Completion
	 * 		.classIndex : 0
	 */
	protected TextGuiTestObject op_0_beeps(TestObject anchor, long flags) 
	{
		return new TextGuiTestObject(
                        getMappedTestObject("op_0_beeps"), anchor, flags);
	}
	
	/**
	 * EnableACLServerIntegration: with default state.
	 *		.text : Enable ACL Server integration
	 * 		.class : .Checkbutton
	 * 		.name : Enable ACL Server integration
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_0_enableACLServerIntegration() 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_0_enableACLServerIntegration"));
	}
	/**
	 * EnableACLServerIntegration: with specific test context and state.
	 *		.text : Enable ACL Server integration
	 * 		.class : .Checkbutton
	 * 		.name : Enable ACL Server integration
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_0_enableACLServerIntegration(TestObject anchor, long flags) 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_0_enableACLServerIntegration"), anchor, flags);
	}
	
	/**
	 * IncludeFiltersInFieldLists: with default state.
	 *		.text : Include Filters in Field Lists
	 * 		.class : .Checkbutton
	 * 		.name : Include Filters in Field Lists
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_0_includeFiltersInField() 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_0_includeFiltersInField"));
	}
	/**
	 * IncludeFiltersInFieldLists: with specific test context and state.
	 *		.text : Include Filters in Field Lists
	 * 		.class : .Checkbutton
	 * 		.name : Include Filters in Field Lists
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_0_includeFiltersInField(TestObject anchor, long flags) 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_0_includeFiltersInField"), anchor, flags);
	}
	
	/**
	 * SaveAllScriptsLocally: with default state.
	 *		.text : Save All Scripts Locally
	 * 		.class : .Checkbutton
	 * 		.name : Save All Scripts Locally
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_0_saveAllScriptsLocally() 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_0_saveAllScriptsLocally"));
	}
	/**
	 * SaveAllScriptsLocally: with specific test context and state.
	 *		.text : Save All Scripts Locally
	 * 		.class : .Checkbutton
	 * 		.name : Save All Scripts Locally
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_0_saveAllScriptsLocally(TestObject anchor, long flags) 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_0_saveAllScriptsLocally"), anchor, flags);
	}
	
	/**
	 * ShowToolbar: with default state.
	 *		.text : Show Toolbar
	 * 		.class : .Checkbutton
	 * 		.name : Show Toolbar
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_0_showToolbar() 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_0_showToolbar"));
	}
	/**
	 * ShowToolbar: with specific test context and state.
	 *		.text : Show Toolbar
	 * 		.class : .Checkbutton
	 * 		.name : Show Toolbar
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_0_showToolbar(TestObject anchor, long flags) 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_0_showToolbar"), anchor, flags);
	}
	
	/**
	 * WarnBeforeOverwritingFiles: with default state.
	 *		.text : Warn Before Overwriting Files
	 * 		.class : .Checkbutton
	 * 		.name : Warn Before Overwriting Files
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_0_warnBeforeOverwriting() 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_0_warnBeforeOverwriting"));
	}
	/**
	 * WarnBeforeOverwritingFiles: with specific test context and state.
	 *		.text : Warn Before Overwriting Files
	 * 		.class : .Checkbutton
	 * 		.name : Warn Before Overwriting Files
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_0_warnBeforeOverwriting(TestObject anchor, long flags) 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_0_warnBeforeOverwriting"), anchor, flags);
	}
	
	/**
	 * AutomaticallyProfileOnOpen: with default state.
	 *		.text : Automatically Profile on Open
	 * 		.class : .Checkbutton
	 * 		.name : Automatically Profile on Open
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_1_automaticallyProfileOnOpen() 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_1_automaticallyProfileOnOpen"));
	}
	/**
	 * AutomaticallyProfileOnOpen: with specific test context and state.
	 *		.text : Automatically Profile on Open
	 * 		.class : .Checkbutton
	 * 		.name : Automatically Profile on Open
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_1_automaticallyProfileOnOpen(TestObject anchor, long flags) 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_1_automaticallyProfileOnOpen"), anchor, flags);
	}
	
	/**
	 * DeleteDataFileWithTable: with default state.
	 *		.text : Delete Data File with Table
	 * 		.class : .Checkbutton
	 * 		.name : Delete Data File with Table
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_1_deleteDataFileWithTable() 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_1_deleteDataFileWithTable"));
	}
	/**
	 * DeleteDataFileWithTable: with specific test context and state.
	 *		.text : Delete Data File with Table
	 * 		.class : .Checkbutton
	 * 		.name : Delete Data File with Table
	 * 		.classIndex : 0
	 */
	protected ToggleGUITestObject op_1_deleteDataFileWithTable(TestObject anchor, long flags) 
	{
		return new ToggleGUITestObject(
                        getMappedTestObject("op_1_deleteDataFileWithTable"), anchor, flags);
	}
	
	/**
	 * Cancel: with default state.
	 *		.text : Cancel
	 * 		.class : .Pushbutton
	 * 		.name : Cancel
	 * 		.classIndex : 0
	 */
	protected GuiTestObject op_Cancel() 
	{
		return new GuiTestObject(
                        getMappedTestObject("op_Cancel"));
	}
	/**
	 * Cancel: with specific test context and state.
	 *		.text : Cancel
	 * 		.class : .Pushbutton
	 * 		.name : Cancel
	 * 		.classIndex : 0
	 */
	protected GuiTestObject op_Cancel(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("op_Cancel"), anchor, flags);
	}
	
	/**
	 * Factory: with default state.
	 *		.text : Factory...
	 * 		.class : .Pushbutton
	 * 		.name : Factory...
	 * 		.classIndex : 0
	 */
	protected GuiTestObject op_Factory() 
	{
		return new GuiTestObject(
                        getMappedTestObject("op_Factory"));
	}
	/**
	 * Factory: with specific test context and state.
	 *		.text : Factory...
	 * 		.class : .Pushbutton
	 * 		.name : Factory...
	 * 		.classIndex : 0
	 */
	protected GuiTestObject op_Factory(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("op_Factory"), anchor, flags);
	}
	
	/**
	 * OK: with default state.
	 *		.text : OK
	 * 		.class : .Pushbutton
	 * 		.name : OK
	 * 		.classIndex : 0
	 */
	protected GuiTestObject op_OK() 
	{
		return new GuiTestObject(
                        getMappedTestObject("op_OK"));
	}
	/**
	 * OK: with specific test context and state.
	 *		.text : OK
	 * 		.class : .Pushbutton
	 * 		.name : OK
	 * 		.classIndex : 0
	 */
	protected GuiTestObject op_OK(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("op_OK"), anchor, flags);
	}
	
	/**
	 * SysTabControl32: with default state.
	 *		.text : null
	 * 		.class : SysTabControl32
	 * 		.name : null
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject op_tab() 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("op_tab"));
	}
	/**
	 * SysTabControl32: with specific test context and state.
	 *		.text : null
	 * 		.class : SysTabControl32
	 * 		.name : null
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject op_tab(TestObject anchor, long flags) 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("op_tab"), anchor, flags);
	}
	
	/**
	 * Pagetablist: with default state.
	 *		.class : .Pagetablist
	 * 		.name : null
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject op_window() 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("op_window"));
	}
	/**
	 * Pagetablist: with specific test context and state.
	 *		.class : .Pagetablist
	 * 		.name : null
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject op_window(TestObject anchor, long flags) 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("op_window"), anchor, flags);
	}
	
	/**
	 * Options: with default state.
	 *		.text : RegularExpression(ACL 9|Options|Server Profiles|Security*|Select|Data Definition ...
	 * 		.class : #32770
	 * 		.processName : ACLWin.exe
	 * 		.name : RegularExpression(ACL 9|Options|Server Profile|Security*|Select*|Data Definition ...
	 */
	protected TopLevelSubitemTestObject optionswindow() 
	{
		return new TopLevelSubitemTestObject(
                        getMappedTestObject("optionswindow"));
	}
	/**
	 * Options: with specific test context and state.
	 *		.text : RegularExpression(ACL 9|Options|Server Profiles|Security*|Select|Data Definition ...
	 * 		.class : #32770
	 * 		.processName : ACLWin.exe
	 * 		.name : RegularExpression(ACL 9|Options|Server Profile|Security*|Select*|Data Definition ...
	 */
	protected TopLevelSubitemTestObject optionswindow(TestObject anchor, long flags) 
	{
		return new TopLevelSubitemTestObject(
                        getMappedTestObject("optionswindow"), anchor, flags);
	}
	
	/**
	 * Cancel: with default state.
	 *		.text : Cancel
	 * 		.class : .Pushbutton
	 * 		.name : Cancel
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_cancel1s() 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_cancel1s"));
	}
	/**
	 * Cancel: with specific test context and state.
	 *		.text : Cancel
	 * 		.class : .Pushbutton
	 * 		.name : Cancel
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_cancel1s(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_cancel1s"), anchor, flags);
	}
	
	/**
	 * cNoexsitngWorkbook_LOCALACLPathDoesNotExistPleaseVerifyTheCorrectPathWasGiven: with default state.
	 *		.class : .Statictext
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject project_msg() 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("project_msg"));
	}
	/**
	 * cNoexsitngWorkbook_LOCALACLPathDoesNotExistPleaseVerifyTheCorrectPathWasGiven: with specific test context and state.
	 *		.class : .Statictext
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject project_msg(TestObject anchor, long flags) 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("project_msg"), anchor, flags);
	}
	
	/**
	 * ConfirmSaveAs: with default state.
	 *		.class : .Pane
	 * 		.name : Confirm Save As
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject project_msg1() 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("project_msg1"));
	}
	/**
	 * ConfirmSaveAs: with specific test context and state.
	 *		.class : .Pane
	 * 		.name : Confirm Save As
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject project_msg1(TestObject anchor, long flags) 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("project_msg1"), anchor, flags);
	}
	
	/**
	 * WindowsCannotAccessACL重新命名ACL: with default state.
	 *		.class : .Statictext
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject project_msg1s() 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("project_msg1s"));
	}
	/**
	 * WindowsCannotAccessACL重新命名ACL: with specific test context and state.
	 *		.class : .Statictext
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject project_msg1s(TestObject anchor, long flags) 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("project_msg1s"), anchor, flags);
	}
	
	/**
	 * WQA_Automation_2012_V20ACL_UserDevelopmentACLQA_AutomationACL_DesktopDATAACLProjecUndeACL测试工程_SERVER: with default state.
	 *		.class : .Statictext
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject project_msg2() 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("project_msg2"));
	}
	/**
	 * WQA_Automation_2012_V20ACL_UserDevelopmentACLQA_AutomationACL_DesktopDATAACLProjecUndeACL测试工程_SERVER: with specific test context and state.
	 *		.class : .Statictext
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject project_msg2(TestObject anchor, long flags) 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("project_msg2"), anchor, flags);
	}
	
	/**
	 * No: with default state.
	 *		.text : No
	 * 		.class : .Pushbutton
	 * 		.name : No
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_no() 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_no"));
	}
	/**
	 * No: with specific test context and state.
	 *		.text : No
	 * 		.class : .Pushbutton
	 * 		.name : No
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_no(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_no"), anchor, flags);
	}
	
	/**
	 * No: with default state.
	 *		.text : No
	 * 		.class : .Pushbutton
	 * 		.name : No
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_no1() 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_no1"));
	}
	/**
	 * No: with specific test context and state.
	 *		.text : No
	 * 		.class : .Pushbutton
	 * 		.name : No
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_no1(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_no1"), anchor, flags);
	}
	
	/**
	 * OK: with default state.
	 *		.text : OK
	 * 		.class : .Pushbutton
	 * 		.name : OK
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_ok() 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_ok"));
	}
	/**
	 * OK: with specific test context and state.
	 *		.text : OK
	 * 		.class : .Pushbutton
	 * 		.name : OK
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_ok(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_ok"), anchor, flags);
	}
	
	/**
	 * OK: with default state.
	 *		.text : OK
	 * 		.class : .Pushbutton
	 * 		.name : OK
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_ok2() 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_ok2"));
	}
	/**
	 * OK: with specific test context and state.
	 *		.text : OK
	 * 		.class : .Pushbutton
	 * 		.name : OK
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_ok2(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_ok2"), anchor, flags);
	}
	
	/**
	 * Project: with default state.
	 *		.text : RegularExpression(Project|Save Project As|Save New Project As:|Confirm Save As)
	 * 		.class : #32770
	 * 		.processName : ACLWin.exe
	 */
	protected TopLevelSubitemTestObject project_window() 
	{
		return new TopLevelSubitemTestObject(
                        getMappedTestObject("project_window"));
	}
	/**
	 * Project: with specific test context and state.
	 *		.text : RegularExpression(Project|Save Project As|Save New Project As:|Confirm Save As)
	 * 		.class : #32770
	 * 		.processName : ACLWin.exe
	 */
	protected TopLevelSubitemTestObject project_window(TestObject anchor, long flags) 
	{
		return new TopLevelSubitemTestObject(
                        getMappedTestObject("project_window"), anchor, flags);
	}
	
	/**
	 * DirectUIHWND: with default state.
	 *		.text : Network Error
	 * 		.class : DirectUIHWND
	 * 		.name : Network Error
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject project_window1s() 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("project_window1s"));
	}
	/**
	 * DirectUIHWND: with specific test context and state.
	 *		.text : Network Error
	 * 		.class : DirectUIHWND
	 * 		.name : Network Error
	 * 		.classIndex : 0
	 */
	protected GuiSubitemTestObject project_window1s(TestObject anchor, long flags) 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("project_window1s"), anchor, flags);
	}
	
	/**
	 * Yes: with default state.
	 *		.text : Yes
	 * 		.class : .Pushbutton
	 * 		.name : Yes
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_yes() 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_yes"));
	}
	/**
	 * Yes: with specific test context and state.
	 *		.text : Yes
	 * 		.class : .Pushbutton
	 * 		.name : Yes
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_yes(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_yes"), anchor, flags);
	}
	
	/**
	 * Yes: with default state.
	 *		.text : Yes
	 * 		.class : .Pushbutton
	 * 		.name : Yes
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_yes1() 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_yes1"));
	}
	/**
	 * Yes: with specific test context and state.
	 *		.text : Yes
	 * 		.class : .Pushbutton
	 * 		.name : Yes
	 * 		.classIndex : 0
	 */
	protected GuiTestObject project_yes1(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("project_yes1"), anchor, flags);
	}
	
	/**
	 * Cancel: with default state.
	 *		.text : Cancel
	 * 		.class : .Pushbutton
	 * 		.name : Cancel
	 * 		.classIndex : 0
	 */
	protected GuiTestObject warning_cancel() 
	{
		return new GuiTestObject(
                        getMappedTestObject("warning_cancel"));
	}
	/**
	 * Cancel: with specific test context and state.
	 *		.text : Cancel
	 * 		.class : .Pushbutton
	 * 		.name : Cancel
	 * 		.classIndex : 0
	 */
	protected GuiTestObject warning_cancel(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("warning_cancel"), anchor, flags);
	}
	
	/**
	 * YouHaveEnteredAPasswordForThisProfileThisCanPresentASecurityRiskAsAnyoneWithAccessToACLWillHaveAcces: with default state.
	 *		.class : Static
	 * 		.classIndex : NR:Range[0 .. 1]
	 */
	protected GuiSubitemTestObject warning_msg() 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("warning_msg"));
	}
	/**
	 * YouHaveEnteredAPasswordForThisProfileThisCanPresentASecurityRiskAsAnyoneWithAccessToACLWillHaveAcces: with specific test context and state.
	 *		.class : Static
	 * 		.classIndex : NR:Range[0 .. 1]
	 */
	protected GuiSubitemTestObject warning_msg(TestObject anchor, long flags) 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("warning_msg"), anchor, flags);
	}
	
	/**
	 * No: with default state.
	 *		.text : No
	 * 		.class : .Pushbutton
	 * 		.name : No
	 * 		.classIndex : 0
	 */
	protected GuiTestObject warning_no() 
	{
		return new GuiTestObject(
                        getMappedTestObject("warning_no"));
	}
	/**
	 * No: with specific test context and state.
	 *		.text : No
	 * 		.class : .Pushbutton
	 * 		.name : No
	 * 		.classIndex : 0
	 */
	protected GuiTestObject warning_no(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("warning_no"), anchor, flags);
	}
	
	/**
	 * OK: with default state.
	 *		.text : OK
	 * 		.class : Button
	 * 		.name : OK
	 */
	protected GuiSubitemTestObject warning_ok() 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("warning_ok"));
	}
	/**
	 * OK: with specific test context and state.
	 *		.text : OK
	 * 		.class : Button
	 * 		.name : OK
	 */
	protected GuiSubitemTestObject warning_ok(TestObject anchor, long flags) 
	{
		return new GuiSubitemTestObject(
                        getMappedTestObject("warning_ok"), anchor, flags);
	}
	
	/**
	 * Yes: with default state.
	 *		.text : Yes
	 * 		.class : .Pushbutton
	 * 		.name : Yes
	 * 		.classIndex : 0
	 */
	protected GuiTestObject warning_yes() 
	{
		return new GuiTestObject(
                        getMappedTestObject("warning_yes"));
	}
	/**
	 * Yes: with specific test context and state.
	 *		.text : Yes
	 * 		.class : .Pushbutton
	 * 		.name : Yes
	 * 		.classIndex : 0
	 */
	protected GuiTestObject warning_yes(TestObject anchor, long flags) 
	{
		return new GuiTestObject(
                        getMappedTestObject("warning_yes"), anchor, flags);
	}
	
	

	protected dialogUtilHelper()
	{
		setScriptName("ACL_Desktop.AppObject.dialogUtil");
	}
	
}

