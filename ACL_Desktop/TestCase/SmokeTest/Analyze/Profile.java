package ACL_Desktop.TestCase.SmokeTest.Analyze;

import resources.ACL_Desktop.TestCase.SmokeTest.Analyze.ProfileHelper;
import com.rational.test.ft.*;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.WPF.*;
import com.rational.test.ft.object.interfaces.dojo.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.object.interfaces.generichtmlsubdomain.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.*;
import com.rational.test.ft.vp.*;
import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;

public class Profile extends ProfileHelper
{
	/**
	 * Script Name   : <b>Profile</b>
	 * Generated     : <b>Mar 24, 2012 3:49:33 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/24
	 * @author Steven_Xiang
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/SmokeTest/Analyze/Profile.xls";
	public void testMain(Object[] args) 
	{
		startFromLine = 9; // 4-9
		endAtLine = 9;     //
	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile,getClass().getName());
	}
}

