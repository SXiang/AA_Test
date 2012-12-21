package lib.acl.helper.sup;

import java.util.Enumeration;
import java.util.Hashtable;

import com.rational.test.ft.object.interfaces.IWindow;
import com.rational.test.ft.object.interfaces.TestObject;
import com.rational.test.ft.script.RationalTestScript;
import com.rational.test.ft.value.MethodInfo;
import com.rational.test.ft.vp.ITestData;
import com.rational.test.ft.vp.ITestDataElement;
import com.rational.test.ft.vp.ITestDataElementList;
import com.rational.test.ft.vp.ITestDataList;
import com.rational.test.ft.vp.ITestDataTable;
import com.rational.test.ft.vp.ITestDataText;

/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  February 04, 2012
 */
public abstract class PrintObjectInfoHelper extends UnicodeHelper
{
	// *******************       Print Object Tree     ********************
	
	static boolean printChildren = true;
	static boolean printOwned = true, descriptionOnly = false;
	
    // First level methods
	public static void printActiveWindow(){
		printObjectTree(getScreen().getActiveWindow());
	}
    public static void printObjectTree(IWindow root){
    	printIWindowTree(root,printChildren,printOwned,descriptionOnly);
    }
    
    public static void printObjectTree(TestObject root){
    	printTestObjectTree(root,true,false,descriptionOnly);
    	for(TestObject child:root.getChildren()){
    		//printTestObjectTree(child,true,false,descriptionOnly);
    		
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
    
    
    
    //**************************************
    //**************************************
    //**************************************
    
    
}
