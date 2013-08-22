package ax.lib;

import java.io.File;
import java.util.regex.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import com.gargoylesoftware.htmlunit.BrowserVersion;

import pageObjects.LoginPage;

import ax.lib.util.FileUtil;

public class RestAPITaskHelper extends ax.lib.KeywordTaskHelper {
	/**
	 * Script Name   : <b>GetProjectList</b>
	 * Generated     : <b>Aug. 19, 2013 4:20:42 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2013/08/19
	 * @author Karen_Zou
	 */
	
	public static WebDriver driver;
		
	// Configurations defined in RestAPI Property file
	static String serverName = "win2012-3.aclqa.local";
    static boolean unicodeTest = true;
	boolean casAuthenticated = false;
	static boolean updateMasterFile = false;
	static String browserType = "IE";
	static int versionNumber = 9; 

	// Shared variables defined in RestAPIKeywordTaskHelper (Have to be set values in child class)
	// BEGIN of datapool variables declaration    
	protected String dpFileMaster;
    protected String dpFileActual;
	// END of datapool variables declaration
    
	protected String outputData = "";
    protected String url = "";

    protected String lineDelimiter = "\\,";    
    // ********** Shared end
    
	public static void launchBrowser(String browserType) {
		if(browserType.equalsIgnoreCase("IE")){
			driver = new HtmlUnitDriver(initIEBrowser(versionNumber));
		}else{
			//other browser's code
		}
	}

	private static BrowserVersion initIEBrowser(int versionNumber) {
	      String applicationName = "Microsoft Internet Explorer";
	      String applicationVersion = "5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3)";
	      String userAgent = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3)";

	      return (new BrowserVersion(applicationName,applicationVersion,userAgent,versionNumber));
 	}

	protected void getPageWithCAS(WebDriver driver){
		if (!casAuthenticated) {
			LoginPage objLoginPage = new LoginPage(driver);
			
			objLoginPage.login();
		}
	}

    protected String getPageSource(WebDriver driver){
 	   String response = driver.getPageSource();
 	   return response;
    }

	public void doVerification(String textResult){
		if (compareTextFile(textResult,"Text")) { 
		   System.out.println("Comparison is successful!");
		} else {
			System.out.println("Comparison is failed!");
		}	
	}

	public boolean compareTextFile(String textResult,String verifyType){
		return compareTextFile(dpFileMaster, dpFileActual,textResult,updateMasterFile,verifyType);
	}

    public boolean compareTextFile(String fileMaster,String fileActual,String actualContents,boolean updateMasterFile,String label){
     	
  	   boolean success = true;
  	   int numLineLimits = 20000;
  	   int sizeLimits = 10000;
  	   String[] textMaster, textActual;

  	   // Write the actual file by actual contents
  	   if (label.equalsIgnoreCase("Text")) {
  		   if(actualContents!=null) {
  			   FileUtil.writeFileContents(fileActual, actualContents);
  		   }
   	   }
  			   
  	   File temp = new File(fileActual);
  	   if(!temp.exists()){
  		   System.out.println(("Warning:"+"File not found - '"+fileActual+"'"));
  		   return true;
  	   }else if(!temp.isFile()){
  		   System.out.println(("Warning:"+"Not a file - '"+fileActual+"'"));
  		   return true;
  	   }else if(temp.length()>sizeLimits*1000){
  		   System.out.println(("Warning:"+"File too big to be compared ("+temp.length()/(1000*1000)+"MBytes)- '"+fileActual+"'"));
  		   return true;
  	   }
  	   
  	   // Save/Update master file  
  	   if(updateMasterFile||!new File(fileMaster).exists()){
  		   System.out.println(("Warning:"+"Save/Update contents of master file '"+fileMaster+"'"));
  		   FileUtil.copyFile(fileActual, fileMaster);
           return true;
  	   }
  	   
  	   System.out.println(("Step:"+"Comparing "+fileMaster +" and "+fileActual));
  	   String tempMaster = FileUtil.readFile(fileMaster,numLineLimits,true);
  	   String tempActual = FileUtil.readFile(fileActual,numLineLimits,true);
 	     
  	   textMaster = tempMaster.split(lineDelimiter);
  	   textActual = tempActual.split(lineDelimiter);
  	   
  	   boolean compareNumLines = isCompareable(fileMaster)&&isCompareable(fileActual);
  	   success = compareString(compareNumLines,textMaster,textActual);
  	   
  	   return success;
     }
   
     public boolean isCompareable(String fname){
  	    String exts = ".+[Ll][oO][gG]\\]?\\s$|.+VIEW\\]\\s$?|.+GRAPH\\]?\\s$|.+[Tt][Xx][Tt]|[Ff][iI][Ll]\\s$";
  	    if(fname.matches(exts))
  		    return true;
  	    return false;
     }
  
     public static boolean compareString(boolean compareNumLines,String[] tm, String[] ta){
   	   boolean success = true;
   	   String sm,sa,msg;
   	   
   	   boolean done = false;
   	   int maxError =10,numError=0;
   	   
  	   String regexID = "\"id\" :\"[0-9a-fA-F]{8}(-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}\"";
   	
   	   for(int i=0;i<tm.length&&i<ta.length&&!done&&numError<maxError;i++){
       	   if(tm[i].trim().matches("[\\s]*")&&ta[i].trim().matches("[\\s]*")){
       		   continue;
       	   }
 
  		   if(!tm[i].trim().equalsIgnoreCase(ta[i].trim())){
  			   System.out.println("tm[i]:"+tm[i]);
  			   sm = getPrintableText(tm[i]);
   			   sa = getPrintableText(ta[i]);

   			   Pattern pat = Pattern.compile(regexID);

   			   if ((pat.matcher(sm).find())&&(pat.matcher(sm).find())) {
         		   String str1 = sm.replaceFirst(regexID, "uuid");
         		   String str2 = sa.replaceFirst(regexID, "uuid");
         		   if (!str1.equals(str2)) {
   					   msg = "Not match(ignore uuid value) - "+": " + "'Expected, '"+sm+""+"', Actual, '"+sa+"'";
   					   numError++;
   				  	   System.out.println("Error:" + msg);
   					   success = false;
         		   }
         	   } else {
					   msg = "Not match - "+": " + "'Expected, '"+sm+""+"', Actual, '"+sa+"'";
					   numError++;
				  	   System.out.println("Error:" + msg);
					   success = false;
        	   }
  		   }   		   
   	   }

   	   if(success){
   		   System.out.println("No difference found between both files");
   	   }

   	   return success;
      }

      public static String getPrintableText(String text){
   	   	   String[] pattern = {"[^\\p{Print}]+", //Not printable
   	   	 	   				   "[\\s]+|\\p{Cc}" //Cntrl    	"[\\x00-\\x1F\\x7F]"		 
   	   	   					  };

    	   String[] format = {"",
    			   			  ""//"[Cntrl]"
    	                     };
    	   for(int i=0;i<pattern.length;i++){
    		   try{
    		       text = text.replaceAll(pattern[i],format[i]);
    		   }catch(Exception e){
    			   text = "";
    		   }
    	   }
    	    return text;
       }
      
      public String querySQL(String sql){
    	    return "500090fb-eb82-4006-a821-65fbe7dd0f54";
       }
}
