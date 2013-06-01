package lib.acl.helper;

import java.awt.Point;

import com.rational.test.ft.object.interfaces.FrameSubitemTestObject;
import com.rational.test.ft.object.interfaces.GuiSubitemTestObject;
import com.rational.test.ft.object.interfaces.TopLevelSubitemTestObject;
import com.rational.test.ft.script.RationalTestScript;

import lib.acl.helper.sup.*;
import lib.acl.helper.Interface.*;
/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  January 05, 2012
 */
public abstract class KeywordSuperHelper extends lib.acl.helper.sup.ObjectHelper implements KeywordInterface
{

	// BEGIN of datapool variables declaration
   

    protected String dpExpectedErr;
    protected String dpKnownBugs;
    protected boolean delFile = true;

    
//	protected String dpCommand;
//	protected String dpEndWith; //@arg Finish or cancel
                                //@VALUE = 'Finish' or 'Cancel', default to Finish
   // END of datapool variables declaration

	public void testMain(Object[] args) 
	{

		// Data-Driven Stub
		dataInit(args); 
		//exeCommands();
	}
	private boolean dataInit(Object[] args){
		boolean done= true;
		dpInitialization(args[0], args[1]);
		dataInitialization(); 
		
		dpExpectedErr = getDpString("ExpectedErr");
		  if(dpExpectedErr.matches("[\\s]*[Rr][Ee][Pp][Ll][Aa][Cc][Ee].*"))
			    delFile = false;
		  else
			  delFile = true;
		dpKnownBugs = getDpString("KnownBugs");
		 if(getDpString("IndividualTest").equalsIgnoreCase("TRUE"))
             individualTest = true;
		 else{
			 individualTest = false;
		 }
		applyWR = false;
		if(dpKnownBugs.equals("")){
			//if(dpKnownBugs.equals("")&&dpExpectedErr.equals("")){
			applyWR = true;
		}
////		dpCommand = getDpString("Command");
////		dpEndWith = getDpString("EndWith");

		return done;
	}
	
	protected String getNameFromPath(String path){
		String itemName = path;
		if(!path.equals(null)&&!path.equals("")){
			String temp[] = path.split("->");
			itemName = temp[temp.length-1];
		}	
	return itemName;
	}
}
