package ACL_Desktop.TestCase.SmokeTest.Server;

import resources.ACL_Desktop.TestCase.SmokeTest.Server.ServerProfileHelper;
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

public class ServerProfile extends ServerProfileHelper
{
	/**
	 * Script Name   : <b>ServerProfile</b>
	 * Generated     : <b>2012-01-12 11:19:26 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/01/12
	 * @author Steven_Xiang
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/SmokeTest/Server/ServerProfile.xls";
    
	public void testMain(Object[] args) 
	{
		startFromLine = 2; 
		endAtLine = 2 ;  
	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile, getClass().getName());
	}
}

