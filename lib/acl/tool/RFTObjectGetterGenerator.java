package lib.acl.tool;

import resources.lib.acl.tool.RFTObjectGetterGeneratorHelper;

//import AX_GatewayPro.AppObject.*;

import java.util.Vector;

import com.rational.test.ft.*;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.*;
import com.rational.test.ft.vp.*;

/**
 * Description   : Functional Test Script
 * @author steven_xiang
 */
public class RFTObjectGetterGenerator extends RFTObjectGetterGeneratorHelper
{
	/**
	 * Script Name   : <b>RFTObjectGetterGenerator</b>
	 * Generated     : <b>2009-11-10 9:33:04 AM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.1  Build 2600 (S)
	 * 
	 * @since  2009/11/10
	 * @author steven_xiang
	 */
	public void testMain(Object[] args) 
	{
		//Vector v = new Vector();
		Vector<RationalTestScript> v = new Vector<RationalTestScript>();
		
		// first 10
//		v.addElement(new adminMenuUnlock());
//		v.addElement(new adminMenuModifyUser());
//		v.addElement(new adminMenuUsers());
//		v.addElement(new analyticsDeleteHistory());
//		v.addElement(new analyticsDeleteScheduled());
//		v.addElement(new analyticsEditScheduled());
//		v.addElement(new analyticsStopRunning());
//		v.addElement(new buttonClickBack());
//		v.addElement(new buttonClickNext());
//		v.addElement(new buttonClickRun());
//		// 20
//		v.addElement(new buttonClickSchedule());
//		v.addElement(new check());
//		v.addElement(new closeAX());
//		v.addElement(new editMenuCopyPaste());
//		v.addElement(new editMenuCutPaste());
//		 // Object Map does not contain the specified id: [BJ.VbbJROscutF:MbJXa:LzDdSxl:8WV].]
		 // del table
		//v.addElement(new AX_GatewayPro.AppObject.editMenuSearch());
//		v.addElement(new expandNodes());
//		v.addElement(new fileMenuActivity());
//		v.addElement(new fileMenuDelete());
//		v.addElement(new fileMenuEngagement());
//         //30
//		v.addElement(new fileMenuExport());
//		v.addElement(new fileMenuImport());
//		v.addElement(new fileMenuOpen());
//		v.addElement(new fileMenuRename());
//		v.addElement(new fileMenuResult());
//		v.addElement(new fileMenuResult());
//		v.addElement(new fileMenuRun());
//		v.addElement(new fileMenuSchedule());
//		v.addElement(new helpMenuAboutACL());
//		v.addElement(new helpMenuDynamicHelp());
//         //40
//		v.addElement(new helpMenuHelpContent());
//		v.addElement(new helpMenuHelpSearch());
//		v.addElement(new keyboardActivity());
//		// del tree
		//v.addElement(new keyboardCopyPasteCTRL());
//		v.addElement(new keyboardCutPasteALT());
//		v.addElement(new keyboardCopyPasteALT());
//		v.addElement(new keyboardCutPasteCTRL());
//		v.addElement(new keyboardDelete());
//		v.addElement(new keyboardDisconnect());
//		v.addElement(new keyboardDragNDrop());
//         //50
//		v.addElement(new keyboardEngagement());
//		v.addElement(new keyboardExport());
//		v.addElement(new keyboardImportALT());
//		v.addElement(new keyboardOpen());
//		v.addElement(new keyboardRename());
//		v.addElement(new keyboardResults());
//		v.addElement(new loginAX());
//		v.addElement(new logoutAX());
//		//Object Map does not contain the specified id: [B1.VbbJROscutF:1fgCA5:LyykijK:8WW].].
		// del menu, cancel, axCoreClient
		//v.addElement(new menuAddUser());
//		v.addElement(new propertiesPermission());
//         //60
//		v.addElement(new rightClickActivity());
//		v.addElement(new rightClickCopyPaste());
//		v.addElement(new rightClickCutPaste());
//		v.addElement(new rightClickDelete());
//		v.addElement(new rightClickDisconnect());
//		v.addElement(new rightClickEngagement());
//		v.addElement(new rightClickExport());
//		v.addElement(new rightClickImport());
//		v.addElement(new rightClickOpen());
//		v.addElement(new rightClickRename());
//           //70
//		v.addElement(new rightClickResults());
//		//Object Map does not contain the specified id: [AZ.1N6MaFVZmulF:15Gbbq:LxQaArG:8WV].].
		//del all objects
		//v.addElement(new selectComboBox());
//		
//		//Object Map does not contain the specified id: [BP.1N6MaFVZmulF:15Gbbq:LxQaArG:8WV].].
		// del all objects
		//v.addElement(new setTextField());
//		v.addElement(new setupIP());
//		v.addElement(new startClient());
//		v.addElement(new verifyAnalyticInputs());
//		
//		//Object Map does not contain the specified id: [Br.1N6MaFVZmulF:15Gbbq:LxQaArG:8WV].].
		// del all except applicationMenuBar
		//v.addElement(new verifyAnalyticsHistory());
//		v.addElement(new verifyExportItems());
//		v.addElement(new verifyReposItems());
//		//Object Map does not contain the specified id: [Br.1N6MaFVZmulF:15Gbbq:LxQaArG:8WV].].
		//v.addElement(new verifyRunningAnalytics());
//         //78
//		//Object Map does not contain the specified id: [Br.1N6MaFVZmulF:15Gbbq:LxQaArG:8WV].].
        //del all except applicationMenuBar
		//v.addElement(new verifyScheduledAnalytics());
//		v.addElement(new verifySearchResults());
//		//Object Map does not contain the specified id: [BG.VbbJROscutF:MbJXa:LzDdSxl:8WW].].
		// del grid
		//v.addElement(new verifySummary());
//		v.addElement(new verifyTableFields());
//		v.addElement(new viewMenuRefresh());
//		v.addElement(new viewMenuRepository());
//		v.addElement(new viewMenuRestoreDefaults());
		// del all except applicationMenuBar
		//v.addElement(new verifyRunningAnalytics());
//		v.addElement(new ());

//        v.addElement(new AX_Gateway.AppObject.TAFGetGoldbugObjects());
//        v.addElement(new AX_Exception.AppObject.TAFGetGoldbugObjects());
	try {
		new ClassGenerator().updateScripts(v);
	}catch(Exception e){
		System.out.println(e);
	}
	}
}

