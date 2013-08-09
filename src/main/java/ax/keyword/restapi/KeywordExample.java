package ax.keyword.restapi;

import org.openqa.selenium.WebElement;

public class KeywordExample extends YourKeywordHelper{
		/**
		 * Script Name   : <b>KeywordExample</b>
		 * Generated     : <b>Mar 24, 2012 3:42:42 PM</b>
		 * Description   : Functional Test Script
		 * 
		 * @since  2012/03/24
		 * @author Steven_Xiang
		 */
	    //*********************** Shared Variables in KeywordHelper *******************
		//***  dpOpenProject
	    //***  dpEndWith
	    //***  dpProjectName
	    //***  dpUnicodeTest
	    //
	    
	  //*********************** Shared Main test Variables  in YourKeywordHelper ***********
	  //***  dpFields;  
	  //***  dpExpression; 
	  //***  dpIf;     
	  //***  dpFileName; 
	  //***  dpUseOutputTable; 
	  //***  dpAppendToFile;
	  //***  dpSaveLocalOrServer="TBD"; 
      //***  ...


		// BEGIN of datapool variables declaration   	
		protected String dpAgingPeriods; //@arg value for Aging Periods
		                                  //@value = [num1 num2 num3...]
		protected String dpOn;      //@arg field or expression for test
		protected String dpCutoffDate; //@arg cutoff date
		                                //@value dd-MMM-yyyy

		protected String dpSuppressOthers; //@arg 'Yes' or 'No', default to 'No'
		protected String dpBreak; //@arg field name to be broken

		// END of datapool variables declaration

		@Override
		public boolean dataInitialization() {
			boolean done= true;
         
		//*** Possible shared variables defined in YourKeywordHelper
//			defaultMenu = "Analyze";
//			command = "Age";
//			winTitle = command;
//			tabMainName = winTitle; //"_Main";
//			fileExt = ".TXT";
			
		//*** Possible methods in YourKeywordHelper
			//readSharedTestData();
	       // readSharedMainTestData();        
	       // readOutputTestData();
	        
	   //*** read in data     
//	        dpCutoffDate = getDpString("CutoffDate");
//	        dpAgingPeriods = getDpString("AgingPeriods");
//	        dpOn = getACLFields("On");
//	       
//	        dpSuppressOthers = getDpString("SuppressOthers");
//	        dpBreak = getDpString("Break");
			return done;
		}

		public void testMain(Object[] args) 
		{
//			super.testMain(args);
		//*** your test logic
//			openTest();
//			aclMainDialog();        
//		    aclEndWith("fileAction");  
//		    doVerification(dpTo); //"Log"
//			aRou.exeACLCommands(dpPostCmd);	
//			
		}
		
		public void aclMainDialog(){
//			mainDialog = mainDialog();//new TopLevelSubitemTestObject(findTopLevelWindow(winTitle)) ;
//			
//			click(findPagetab(mainDialog, mainTab), mainTab);	
//	    	tabMain = findSubWindow(mainDialog,true,tabMainName);
//			thisMainTab(tabMain);
//
//			click(findPagetab(mainDialog, outputTab), outputTab);	
//	    	tabOutput = findSubWindow(mainDialog,true,tabOutputName);    	
//			thisOutputTab(tabOutput);
//			
//			click(findPagetab(mainDialog, moreTab), moreTab);
//	    	tabMore = findSubWindow(mainDialog,true,tabMoreName);
//			thisMoreTab(tabMore);
//			
		}
		
		public void thisMainTab(WebElement tabDialog){
//			String keyToOnExp = winTitle+" On...";	
//			String keyToExp = "Subtotal Fields...";
//			
//			// Work on tabDialog
//			if(!dpOn.equals("")&&!selectedFromFields(tabDialog,keyToOnExp,dpOn)){
//				actionOnSelect(findComboBox(tabDialog,true,0),keyToOnExp,dpOn,"New");
//			}
//			
//			if(!dpFields.equals("")&&!selectedFromFields(tabDialog,keyToExp,dpFields)){
//				selectSomeFields(findTable(tabDialog,true,0),dpFields);
//			}
//			
//            .....
		}
		
	    public void thisMoreTab(WebElement tabDialog){   	
//	    	// Work on tabDialog
//	    	super.thisMoreTab(tabDialog);
//	    	
//	    	if(!dpSuppressOthers.equals("")){
//				actionOnCheckbox(findCheckbutton(tabDialog,"Suppress Others"),
//						"Suppress Others",
//						dpSuppressOthers.equalsIgnoreCase("Yes")?true:false,"New");
//				}
//	    	
//	    	if(!dpBreak.equals("")){
//	  		  actionOnText(findEditbox(tabDialog,true,3),"Break",dpBreak,"New");	
//	  		}
//	    	
	    }
	    

	 }

