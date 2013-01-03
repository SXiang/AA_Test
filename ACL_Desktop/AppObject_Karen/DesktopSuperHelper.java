package ACL_Desktop.AppObject_Karen;

import ACL_Desktop.AppObject.aclDataDialogs;
import ACL_Desktop.AppObject.aclRoutines;
import ACL_Desktop.AppObject.dialogUtil;
import ACL_Desktop.AppObject.getObjects;
import ACL_Desktop.AppObject.keywordUtil;
import ACL_Desktop.conf.beans.ProjectConf;

import com.rational.test.ft.object.interfaces.TestObject;
import com.rational.test.ft.script.ITestObjectMethodState;
import com.rational.test.ft.script.RationalTestScript;
import com.rational.test.ft.script.ScriptCommandFlags;

import conf.beans.FrameworkConf;
import conf.beans.TimerConf;
import lib.acl.helper.*;
import lib.acl.tool.htmlRFTHandler;
import lib.acl.util.FileUtil;
/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  January 12, 2012
 */


public abstract class DesktopSuperHelper extends ACL_Desktop.AppObject.DesktopSuperHelper
{
	protected aclSamplingDialogs sampleDlog;
	public void testMain(Object[] args) 
	{ 
		super.testMain(args);
		//sampleDlog = new aclSamplingDialogs();
	}

	public DesktopSuperHelper(){

	}
}
