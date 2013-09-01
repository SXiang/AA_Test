/**
 * 
 */
package com.acl.qa.taf.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.*;

import com.acl.qa.taf.helper.superhelper.ACLQATestScript;

/**
 * Script Name :DatapoolUtil.java
 * Generated   :10:02:31 AM
 * Description : ACL Test Automation
 * @since Aug 16, 2013
 * @author steven_xiang
 *
 */
public class DatapoolUtil {

	public Object[] setDefaultDataPool(ACLQATestScript ts, String poolFile){
		
		File xf;
		InputStream is;
		HSSFSheet datapool = null;
		Iterator dpi = null;
		ArrayList<String> dph = new ArrayList<String>();
		
		if(poolFile.matches("(?i).*\\.csv")){
			System.out.println("CSV is not supported as input file, convert to XLS and try again !");
			return null;
		}
		
		try{
			xf = new File(FileUtil.getAbsDir(poolFile));
			is = new FileInputStream(xf);	
			xf.delete();
		}catch(Exception e){
			System.out.println("Failed to load file "+poolFile+" !");
			return null;
		}
		
		try{
			HSSFWorkbook hwb = new HSSFWorkbook(is,true);
			int numSheets = hwb.getNumberOfSheets();
			    numSheets = 1; // ** Currently, only load the first sheet for testing - Steven
			    for (int sheet = 0; sheet < numSheets; sheet++)
			      {
			        datapool = hwb.getSheetAt(sheet);
			        dpi = datapool.rowIterator();
			        
			        if(dpi.hasNext()){
			        	HSSFRow header = (HSSFRow) dpi.next();
			        	Iterator cellIter = header.cellIterator();
			        	while(cellIter.hasNext()){
			        		HSSFCell dpc = (HSSFCell) cellIter.next();
			        		if(dpc.toString()==null)
			        			dph.add("");
			        		else
			        		   dph.add(dpc.toString());
			        	}
			        	
			        }
			        }
			      }catch(Exception e){
			        	System.out.println("Failed to read the hader from your datapool, check the format and try again");
			        	System.out.println("Is this correct: '"+poolFile+"'?");
			        	return null;
        }
		

		return new Object[]{datapool, dph,dpi};
	}

}