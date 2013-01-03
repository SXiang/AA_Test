package ACL_Desktop.Tasks.Edit;

import resources.ACL_Desktop.Tasks.Edit.variableAndFilterHelper;
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

public class variableAndFilter extends variableAndFilterHelper
{
	/**
	 * Script Name   : <b>variableAndFilter</b>
	 * Generated     : <b>Apr 4, 2012 4:38:06 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/04/04
	 * @author Steven_Xiang
	 */
    //*********************** Shared Variables in SuperHelper *******************
	//***  dpOpenProject
    //***  dpEndWith
    //***  dpProjectName
    //***  dpUnicodeTest
    //***  dpPreCmd	         
    //***  dpPostCmd	 
    //***  dpPreFilter
    //***  dpMenuItem 
    //***  dpSaveLocalOrServer
    //
    
     
	// BEGIN of datapool variables declaration   	
	protected String dpCommand ;      //@arg Variables|Filters
	protected String dpNewItem ; //@arg name of the item (var or filter) with the expression
	                              //@value = tiemName[|expected name] -> expression
    protected String dpRename; //@arg rename item
                                //@value = oldName -> newName[|expected name]
    protected String dpEdit; //@arg edit item
                                //@value = itemName == newName[|expected name] -> [expression]
    protected String dpDuplicate; //@arg duplicate item
                                 //@value = itemName[|resulted name]
    protected String dpDelete;   //@arg delete item[s]
                                 //@value = item1|item2....
	// END of datapool variables declaration
    protected String expSep = "->",
                     expectedSep = "\\|",
                     mainSep = "==";
    protected String keyNew = "New",
                     keyRename = "Rename...",
                     keyDuplicate = "lv_WinName_DLG_SELECT_DIALOG_13_27_5_6_PUSHBUTTON_5",//Duplicate",
                     keyEdit = "OK",
                     keyDelete = "Delete",
                     keyCopy = "Copy From...";
                     
	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Edit";
	
		
		readSharedTestData();

        dpCommand = getDpString("Command");
        dpNewItem = getDpString("NewItem");
        dpRename = getDpString("Rename");
        dpDelete = getDpString("Delete");
        dpEdit = getDpString("Edit");
        dpDuplicate = getDpString("Duplicate");
        
        command = dpCommand;
		winTitle = dpCommand;
		tabMainName = winTitle; //"_Main";
		
		if(dpEndWith.matches("Finish|OK")||dpEndWith.equals("")){
			dpEndWith = "OK";
		}

        //setupTestFiles(dpFileName,dpSaveLocalOrServer,dpAppendToFile,fileExt);
		return done;
	}

	public void testMain(Object[] args) 
	{
		super.testMain(args);
		openTest();
		aclMainDialog();        
	    closeDialog();
		aRou.exeACLCommands(dpPostCmd);	    
	}
	
	public void aclMainDialog(){
		mainDialog = mainDialog();//new TopLevelSubitemTestObject(findTopLevelWindow(winTitle)) ;
		getItem(dpNewItem);		
        renameItem(dpRename);
        editItem(dpEdit);
        duplicateItem(dpDuplicate);
        deleteItem(dpDelete);
        
	}
	public void closeDialog(){
		String butt = "Done|Cancel";
		if(mainDialog.exists()){
			GuiTestObject gto = findPushbutton(mainDialog, butt);
			if(gto!=null){
				click(gto,butt);
			}else{
				logTAFDebug("Button "+butt+" not found?");
				mainDialog.close();
			}
		}
		
	}
	public void verifyItem(String items,boolean openDialog){
		String[] item;
		dismissPopup();
		if(!listBoxlist().exists()){
			if(openDialog){
				kUtil.invokeMenuCommand(defaultMenu+"->"+command);
			}else{
               logTAFWarning("The varialbes/filters window not found?");
               kUtil.invokeMenuCommand(defaultMenu+"->"+command);
			}
		}
		if(!items.equals("")){
			item = items.split("\\|");

			for(int i=0;i<item.length&&!item[i].trim().equals("");i++)
		       actionOnSelect(listBoxlist(),command,item[i].trim(),"New");
		}
	
	}
	public void getItem(String item){
		if(item==null|item.equals("")){
			return;
		}
		String[] exps = item.split(expSep);	
		String[] names = null;
		String endWith = dpEndWith;
		String expectedName = "",name = "";
		if(exps.length>0){
		  names = exps[0].split(expectedSep);
		  expectedName = names[names.length-1].trim();
		  name = names[0].trim();

		  if(exps.length>1){
  			click(findPushbutton(mainDialog,keyNew),keyNew);	
  			sleep(2);
			endWith = buildExpression(exps[1].trim(),name,endWith);			
		  }
		  if(!endWith.equals("Cancel"))
		      verifyItem(expectedName,true);
		}
	}
	
	public void renameItem(String item){
		if(item==null|item.equals("")){
			return;
		}
		String[] exps = item.split(expSep);	
		String[] names = null;
		String endWith = dpEndWith;
		String expectedName = "",name = "",curname = "";
		TestObject to = null;
		String winTitle = "Rename",winClass = "#32770";
		if(exps.length>0){
			curname = exps[0].trim();
		}

		if(!curname.equals("")){			
			if(!actionOnSelect(listBoxlist(),command,curname,"New",true))
				return;
  			click(findPushbutton(mainDialog,keyRename),keyRename);	
		}else{
			return;
		}
		
		names = exps[1].split(expectedSep);
		expectedName = names[names.length-1].trim();
		name = names[0].trim();
		
		if(!name.equals("")){	
			to = findTopLevelWindow(winTitle,winClass);
			if(to!=null){
			  actionOnText(findEditbox(to,true,0),winTitle,name,"New");	
			  click(findPushbutton(to,endWith),endWith);			  
			}
		}
		verifyItem(expectedName,false);

		
	}

	public void editItem(String item){ //
		if(item==null|item.equals("")){
			return;
		}
		String[] text = item.split(mainSep);
		String[] exps = null;
		String[] names = null;
		String endWith = dpEndWith;
		String expectedName = "",name = "",curname;
		String exp = "";
		
		if(text.length>0){
		  curname = text[0].trim();
		  if(!actionOnSelect(listBoxlist(),command,curname,"New",true)){
			  click(findPushbutton(mainDialog,"Cancel"),"Cancel", true);
			  return;
		  }
		  click(findPushbutton(mainDialog,"OK"),"OK", true);
		}
		if(text.length>1){
		  exps = text[1].split(expSep);
		}
		
		if(exps!=null&&exps.length>0){
			  names = exps[0].split(expectedSep);
			  expectedName = names[names.length-1].trim();
			  name = names[0].trim();
			  if(exps.length>1){
				  exp = exps[1].trim();
			  }
			  endWith = buildExpression(exp,name,endWith);	
			 // actionOnSelect(listBoxlist(),command,expectedName,"New");	
		}
		verifyItem(expectedName,true);
	}	
	
	public void duplicateItem(String item){
		if(item==null|item.equals("")){
			return;
		}
		String[] names =null;
		String endWith = dpEndWith;
		String expectedName = "",name = "";
		
		if(!item.equals("")){
		   names = item.split(expectedSep);
		   expectedName = names[names.length-1].trim();
		   name = names[0].trim();
		}else{
			return;
		}
		
		if(actionOnSelect(listBoxlist(),command,name,"New",true)){	
		   GuiTestObject gto = findPushbutton(mainDialog,keyDuplicate);
		   //System.out.println(gto.getProperties());
		   click(gto,keyDuplicate);
		   verifyItem(expectedName,false);
		}

		
	}
	public void deleteItem(String items){
		if(items==null|items.equals("")){
			return;
		}
		String endWith = "Delete";
		TestObject to = null;
		String winTitle = "Delete",winClass = "#32770";
		String item[] = items.split("\\|");
		for(int i=0;i<item.length&&!item[i].equals("");i++){
		  if(actionOnSelect(listBoxlist(),command,item[i],"New",true)){	
		     click(findPushbutton(mainDialog,keyDelete),keyDelete);
		     dismissPopup(winClass,winTitle,"Delete",false,true,3,".*Delete.*");
		     //dismissPopup(winTitle,endWith,"Any",false,true);
		  }
		}
	}
 }
