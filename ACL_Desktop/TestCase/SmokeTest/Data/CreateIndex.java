package ACL_Desktop.TestCase.SmokeTest.Data;

import resources.ACL_Desktop.TestCase.SmokeTest.Data.CreateIndexHelper;
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

public class CreateIndex extends CreateIndexHelper
{
	/**
	 * Script Name   : <b>CreateIndex</b>
	 * Generated     : <b>2012-03-02 2:08:37 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/02
	 * @author Steven_Xiang
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/SmokeTest/Data/CreateIndex.xls";

	public void testMain(Object[] args) 
	{
		startFromLine = 8; // 4-9
		endAtLine = 8;     //
	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile,getClass().getName());
	}
}

