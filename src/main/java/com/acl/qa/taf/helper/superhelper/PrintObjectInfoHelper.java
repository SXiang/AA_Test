package com.acl.qa.taf.helper.superhelper;

import java.util.Enumeration;
import java.util.Hashtable;












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
