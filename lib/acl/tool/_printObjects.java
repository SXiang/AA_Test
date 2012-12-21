package lib.acl.tool;
import resources.lib.acl.tool._printObjectsHelper;
import java.util.Enumeration;
import java.util.Hashtable;

import lib.acl.helper.sup.ObjectHelper;
import lib.acl.helper.sup.UnicodeHelper;

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
 * @author Steven_Xiang
 */
public class _printObjects extends _printObjectsHelper
{
	/**
	 * Script Name   : <b>_printObjects</b>
	 * Generated     : <b>2010-05-06 2:25:32 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.1  Build 2600 (S)
	 * 
	 * @since  2010/05/06
	 * @author Steven_Xiang
	 */
	
	static boolean printChildren = true;
	static boolean printOwned = true, descriptionOnly = false;
	

	public void testMain(Object[] args) 
	{
		//getDialog(String caption,String className ) 
		//getScreen().getActiveWindow()
        //testMemusage();
		//testRegexp();
		
		TestObject tob;
		//UnicodeHelper.convertDatePattern("11/14/2000");
		//tob = workingwindow();
		//tob = XLBook();
		//excelWin().activate(); 
//    	tob = findSubWindow(summarizewindow(),true,"Output..."); 
//    	printObjectTree(tob);
//		printTestObjectTree(findCheckbutton(tob,"Local"),true,false,false);
//		sleep(1);
//		printTestObjectTree(findCheckbutton(tob,"Local"),true,false,false);
//		lbxNumericColumnslist().select("TRANS_AMOUNT");
//		lbxNumericColumnslist().select("TRANS_AMOUNT");
//		lbxNumericColumnslist().click(atText("TRANS_AMOUNT"));
//		lbxNumericColumnslist().click(atValue("TRANS_AMOUNT"));
//		lbxNumericColumnslist().click(atButton("TRANS_AMOUNT"));
//		lbxNumericColumnslist().click(atName("TRANS_AMOUNT"));
//		printObjectTree(lbxNumericColumnslist());
		//tob = new ACL_Soundwave.AppObject.TAFGetExcelObjects().getGuiByName("UndefineData");
//		tob = new ACL_Soundwave.AppObject.TAFGetExcelObjects().cellValueBox();
//		logTAFDebug(
//			    ExcelUtil.getCellValue(new ACL_Soundwave.AppObject.TAFGetExcelObjects().getExcelWindow(),
//			    "E3"));
		tob = documentManagerwindow();
		//tob = findTestObject(tob,false,".class",".List",".text","Filter:");	
		//tob = tabletable();
		//tob = grid();
		//tob = logInwindow();table_dashBoardStore().text__Overview().
		//((GuiTestObject) tob).hover(2);
		//System.out.println("GetText = '"+((TextSelectGuiSubitemTestObject) tob).getText());
		printObjectTree(tob);
		//printObjectTree(defineRangebutton().getParent());
		//printObjectTree(undefineRangebutton().getParent());
		//printTestDataTable(tob);
		//printTestDataList(tob);
		//logTAFInfo(lstRSCustomItemslist().getText().replaceAll("\n"," "	));
		//printObjectTree(getDialog(LoggerHelper.autTitle,"#32770"));
		//printActiveWindow();
		//testRegexp();
		//html_filterMainDiv().
//		printTableTestData(pagetablistpageTabList());
//		printTableTestData(sysListView32table());
//		printTableTestData(tree());
//		comboBoxcomboBox().click(ARROW);
//		sleep(1);
//		comboBoxcomboBox().click(atText("Weekly"));
//		sleep(1);
		
		//testing ....

//	 	   String dpMasterFile="W:\\QA_Automation_2012_V2.0\\ACL_User\\Steven\\ACLQA_Automation\\ACL_Desktop\\DATA\\" +
//	 	   		                  "ExpectedData\\NonUnicode\\en_CA\\Tools\\RunScript\\OLDBATSClient\\_AllTest\\_AllTest.TXT[Log]";
//	 	   String dpActualFile="W:\\QA_Automation_2012_V2.0\\ACL_User\\Steven\\ACLQA_Automation\\ACL_Desktop\\DATA\\" +
//	 	   		                  "ACLProject\\NonUnicode\\DesktopScript\\OldbatsDesktopEnglish\\OLDBATSClient\\_AllTest.TXT[Log]";
//	 	   new ObjectHelper().compareTextFile(dpMasterFile, dpActualFile,
//						false,"Log");	

	}
    

	public void testRegexp(){
		String value = "Linked (30)";
		String prefix = "Linked";
		System.out.println("Starting....");
		System.out.println(value.matches(prefix+" \\(\\d+\\)"));
		System.out.println("C:\\QA_test_myFolder".matches(".*:\\\\QA_.+Folder.*$"));
		
	}
	public void testMemusage(){
		lib.acl.util.MemusageTracer mt = new lib.acl.util.MemusageTracer();
        mt.start();
        try {
			mt.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean printTableTestData(GuiSubitemTestObject sto) {
        String nameInList="";
        ITestDataTable myTable =null;
		    try{
			   myTable = (ITestDataTable)sto.getTestData("contents");
		    }catch(Exception e){
		    	System.out.println("No test data for 'contents'?");
		    }
		    if (myTable==null)
		    	return false;
			for (int row =0; row<myTable.getRowCount(); row++) {
				try{
				   nameInList = myTable.getCell(row,0).toString();
				   System.out.println("Line "+(row+1)+": "+nameInList);
				}catch(Exception e){
					System.out.println("Line "+(row+1)+": "+e.toString());
				}				
			}		
			return true;
	}
	
	// *******************       Print Object Tree     ********************
	

    // First level methods
	public static void printActiveWindow(){
		printObjectTree(getScreen().getActiveWindow());
	}
    public static void printObjectTree(IWindow root){
    	printIWindowTree(root,printChildren,printOwned,descriptionOnly);
    }
    
    public static void printObjectTree(TestObject root){
    	printTestObjectTree(root,printChildren,printOwned,descriptionOnly);
    	for(TestObject child:root.getChildren()){
    		printTestObjectTree(child,printChildren,printOwned,descriptionOnly);
    		
    	}
    	//printTestObjectTree(root,printChildren,printOwned,descriptionOnly);
    }
    
    public static void printMethods(TestObject to){
    	MethodInfo[] m = to.getMethods();
    	for (int i=0; i<m.length; ++i){
    		logTAFInfo("Method "+ i +": name=" +
    				m[i].getName()+": signature=" + m[i].getSignature());
    	}
    }
    
    // Second Level methods
    public  static void printIWindowTree(IWindow root,boolean children,boolean owned,boolean descOnly){
    	logTAFInfo("## Parent of ["+root+"] is :"+root.getParent()); 
    	logTAFInfo("## Owner of ["+root+"] is :"+root.getOwner());
    	
    	logTAFInfo("Children Objects of ["+root+"]:");
        printTestObjectChildren(root.getChildren(),descOnly,"","");        
    }

   public static  void printTestObjectTree(TestObject root,boolean children,boolean owned,boolean descOnly){
    	
    	logTAFInfo("## Parent of ["+root+"] is :"+root.getParent()); 
    	logTAFInfo("## Owner of ["+root+"] is :"+root.getOwner());
    	//printMethods(root);
    	printHashTable(root.getProperties());
    	//printHashTable(root.getNonValueProperties());
    	if(children){
    		logTAFInfo("There are '"+root.getChildren().length+"' Children Objects of ["+root+"]:");
    		printTestObjectChildren(root.getChildren(),descOnly,""," ");
    	}
        
        if(owned){
        	logTAFInfo("Owned Objects of ["+root+"]:");
        	printTestObjectChildren(root.getOwnedObjects(),descOnly,""," ");
        }

    }
   
	// Third, print methods
 
   public static void printTestDataList(TestObject list){
	   String[] all = null;
	   ITestData iList = list.getTestData("list");
	   
	   if(iList == null){
		   return;
	   }
	   if (iList instanceof ITestDataText){
		   System.out.println("Element getText'"+"' = '"+((IWindow) iList).getText()+"'");
		   return;
	   }else if(!(iList instanceof ITestDataList)){
		   System.out.println("Element toString'"+"' = '"+iList+"'");
		   return;
	   }
	   
	
	   
	   int count = ((ITestDataList) iList).getElementCount();
	   all = new String[count];
	   ITestDataElementList iElementList = ((ITestDataList) iList).getElements();
	   for(int i=0;i<count;i++){
		   ITestDataElement iElement =iElementList.getElement(i);
		   String value = iElement.getElement().toString();
		   System.out.println("Element '"+i+"' = '"+value+"'");
		   all[i] = value;		   
	   }
	   
   }
    @SuppressWarnings("unchecked")
	public static void printTestDataTable(TestObject table){
    	
    		Enumeration<String> testDataTypes = table.getTestDataTypes().keys();
    		
    		while(testDataTypes.hasMoreElements()){
    			String testDataType = testDataTypes.nextElement();
    			System.out.println(testDataType);
    			ITestData iData = table.getTestData(testDataType);
    			if(iData instanceof ITestDataTable){
    				ITestDataTable iTableData = (ITestDataTable) table.getTestData(testDataType);
    				int rows = iTableData.getRowCount();
    				int cols = iTableData.getColumnCount();
    				for(int col=0;col<cols;col++){
    					System.out.print(iTableData.getColumnHeader(col));
    					System.out.print("\t\t");
    				}
    				System.out.print("\n");
    				for(int row=0;row<rows;row++){
    					for(int col=0;col<cols;col++){
    					System.out.print(iTableData.getCell(row,col));
    					System.out.print("\t\t");
    					}
    					System.out.print("\n\n");
    				}
    				System.out.print("\n");
    			}else if(iData instanceof ITestDataText){
    				ITestDataText iText =(ITestDataText)iData;
    				String text = iText.getText();
    				System.out.println(text+"\n\n");
    				
    			}
    		}
    	   

    }
    public static void printTestObjectChildren(TestObject[] children,boolean descOnly, String level,String spacer){
    	
    	for(int i=0;i<children.length;i++){
    		
    		if(descOnly){
//    			logTAFInfo(spacer+" "+level+(i+1)+". "+children[i].getDescriptiveName()+
//    			            " ||| "+children[i].getProperty(".class")+
//    			            " ||| "+children[i].getObjectClassName());
    		}else {
    			String name=null,clas=null;
    			try{
    			    name = children[i].getDescriptiveName();
    			    clas = children[i].getProperty(".class").toString();
        			logTAFInfo(spacer+" "+level+(i+1)+". "+name+
        					" ||| "+clas);
        			printHashTable(children[i].getProperties());
        			//printHashTable(children[i].getNonValueProperties());
        			
    			}catch(Exception e){
    				//name = name == null?"Unknown":name;
    				//clas = clas == null?"Unknown":clas;
    				name = name == null?""+children[i]:name;
    				clas = clas == null?""+children[i]:clas;
    				logTAFInfo(spacer+" "+level+(i+1)+". "+name+
        					" ||| "+clas);
    				//printHashTable(children[i].getProperties());
        			//printHashTable(children[i].getNonValueProperties());
    				//printMethods(children[i]);
    				
    			}

//    			logTAFInfo("<************Relative position Data***********>");
//    			printTestDataList(children[i]);
//    			printHashTable(children[i].getNonValueProperties());
//    			logTAFInfo("<*********************************>");
    		}
    		if(children[i]!=null)
    		   printTestObjectChildren(children[i].getChildren(),descOnly,level+(i+1),spacer+"\t");
    	}
    }
 
    public static void printTestObjectChildren(IWindow[] children,boolean descOnly,String level,String spacer){
    	
    	for(int i=0;i<children.length;i++){
    		if(descOnly)
    			logTAFInfo(spacer+" "+level+(i+1)+". "+children[i].getText());
    		else
    			logTAFInfo(spacer+" "+level+(i+1)+". "+children[i].getText()+
    					" ||| "+children[i]);
    		printTestObjectChildren(children[i].getChildren(),descOnly,level+(i+1),spacer+"\t");
    	}
    }
    

    
    public static void printHashTable(Hashtable ht){       
    	if(ht==null){
    		logTAFInfo("No value properties found");
    		return;
    	}else{
    		logTAFDebug(ht.toString());
    	}
    	Enumeration enu = ht.keys ();
    	
            Object valueStr="";
            while (enu.hasMoreElements ()) {
                String key = (String) enu.nextElement ();
                valueStr = ht.get(key);
                if(valueStr != null && !valueStr.toString().trim().equals("")){
                	
                    logTAFInfo ("{ " + key + " = " + valueStr + " }");
                }
            }
       
    }


	@Override
	public boolean dataInitialization() {
		// TODO Auto-generated method stub
		return false;
	}
}
