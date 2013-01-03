package ACL_Desktop.TestCase.SmokeTest.Edit;

import resources.ACL_Desktop.TestCase.SmokeTest.Edit.FiltersHelper;
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

public class Filters extends FiltersHelper
{
	/**
	 * Script Name   : <b>Filters</b>
	 * Generated     : <b>Apr 4, 2012 4:42:06 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/04/04
	 * @author Steven_Xiang
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/SmokeTest/Edit/Filters.xls";
	public void testMain(Object[] args) 
	{
		startFromLine = 4; // 4-9
		endAtLine = 4;     //
	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile,getClass().getName());
	}
}



