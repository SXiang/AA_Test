package com.acl.qa.taf.util;

import java.io.*;
import java.text.DateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.ss.usermodel.DataFormat;








import com.acl.qa.taf.helper.superhelper.ACLQATestScript;



/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  June 11, 2010
 */
public class UnicodeUtil extends ACLQATestScript
{
	static String encoding = "UTF-8";
	public static String csvComma = "<<;>>";
	static int numCsv =0;
	
	
	public static String setPoolLanguageIndex(String poolFile){
		String poolLanguageIndex = "1";
		String name;
		
		name = new File(FileUtil.getAbsDir(poolFile)).getName().replace(".xls", "").replace(".XLS","").replace(".csv","").replace(".CSV", "");
		if(name.endsWith("_en")||name.endsWith("_EN")){
			poolLanguageIndex = "0";
		}else if(name.endsWith("_ch")||name.endsWith("_CH")){
			poolLanguageIndex = "1";
		}else if(name.endsWith("_fr")||name.endsWith("_FR")){
			poolLanguageIndex = "2";
		}else if(name.endsWith("_es")||name.endsWith("_ES")){
			poolLanguageIndex = "3";
		}else if(name.endsWith("_de")||name.endsWith("_DE")){
			poolLanguageIndex = "4";
		}else if(name.endsWith("_pt")||name.endsWith("_PT")){
			poolLanguageIndex = "5";
		}else if(name.endsWith("_bg")||name.endsWith("_BG")){
			poolLanguageIndex = "6";
		}else {
			poolLanguageIndex = "1";
		}
		return poolLanguageIndex;
	}

	
	public static void CsvToXls(String csvName,String xlsName ) throws IOException
	{
		if(xlsName.equals("")||csvName.equals(xlsName)){
			return;
		}
		
		ArrayList<ArrayList<String>> arList=new ArrayList<ArrayList<String>>();
		ArrayList<String> al=null;

		String thisLine,sheetName;
		
		 //File to store data in form of XLS
	     File xf = new File(FileUtil.getAbsDir(xlsName));
	     OutputStream os = (OutputStream)new FileOutputStream(xf);	      
	     
	    //File to get data in form of CSV
	    File cf = new File(FileUtil.getAbsDir(csvName));
	    
	    sheetName = xf.getName().replace(".xls","").replace(".XLS","");
	    sheetName = sheetName.split("\\.")[sheetName.split("\\.").length-1];
	    InputStream is = (InputStream)new FileInputStream(cf);	      
	    InputStreamReader osr = new InputStreamReader(is, encoding);
	    BufferedReader br = new BufferedReader(osr);
		int columns = -1;
	    

		while ((thisLine = br.readLine()) != null)
		{
			al = new ArrayList<String>();
			String strar[] = thisLine.replaceAll(csvComma, ",").split(",");
			if(columns<0)
				columns = strar.length;
			if(strar.length>columns)
				continue;
			for(int j=0;j<columns;j++)
			{
				//Add to arraylist and handle null values
				al.add(strar[j]==null?"":strar[j]);
			}
			arList.add(al);
//			System.out.println();
		}

		try{
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet(sheetName);
			for(int k=0;k<arList.size();k++)
			{
				ArrayList<String> ardata = (ArrayList<String>)arList.get(k);
//				System.out.println("ardata " + ardata.size());
				HSSFRow row = sheet.createRow(0+k);
				
				for(int p=0;p<ardata.size();p++)
				{
//					System.out.print(ardata.get(p));
					HSSFCell cell = row.createCell( p);
					
					cell.setCellValue(ardata.get(p)==null?"":ardata.get(p).toString());
				}
//				System.out.println();
			}
		
			hwb.write(os);
			os.close();
//			System.out.println("Your excel file has been generated");
			}catch( Exception e ){
				System.err.println(e.toString());
			} 
		}

	
	public static String XlsToCsv(String xlsName, String csvName){
        
		if(xlsName.endsWith(".csv")||xlsName.endsWith(".CSV")){
			return xlsName;
		}
		
		try
	    {
	      //File to Get data in form of CSV
		  csvName = FileUtil.getAbsDir(csvName.replaceAll(".csv", ++numCsv+".csv"));
	      File cf = new File(csvName);
	      OutputStream os = (OutputStream)new FileOutputStream(cf);	      
	      OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
	      BufferedWriter bw = new BufferedWriter(osw);

		  //File to store data in form of XLS
		  File xf = new File(FileUtil.getAbsDir(xlsName));
		  InputStream is = (InputStream)new FileInputStream(xf);	   
		  
	      try{
				HSSFWorkbook hwb = new HSSFWorkbook(is,true);
				int numSheets = hwb.getNumberOfSheets();
				String cellValue ="";
				//System.out.println(xlsName);
			      numSheets = 1; // we just convert the first sheet for our automation, comment out this line if you need convert all sheets
			      for (int sheet = 0; sheet < numSheets; sheet++)
			      {
			        HSSFSheet s = hwb.getSheetAt(sheet);
			        HSSFRow row = null,header=null;		
			        HSSFCell cell = null;
			        // Gets the cells from sheet
			        //for (int i = 0 ; i <= s.getLastRowNum() ; i++)
			        for (int i = 0 ; i < s.getPhysicalNumberOfRows(); i++)
			        {
			          row = s.getRow(i);
                      if(i==0){
                    	  header = row;
                      }
			          if (row.getLastCellNum() > 0)
			          {
			        	cellValue = "";
			        	cell = row.getCell(0);
			        	cellValue = getStringCellValue(cell);
//			        	cellValue = cell.getStringCellValue();
//				          if(cellValue==null|cellValue.toUpperCase().matches(".*NULL.*")){
//				        	  cellValue = "";
//				          }
			            bw.write(cellValue);
				            
			            //System.out.println(cellValue);
			            for (int j = 1; j < s.getRow(0).getLastCellNum(); j++)
			            {
			              bw.write(',');
			              cell = row.getCell(j);
			              cellValue = getStringCellValue(cell);
//			              try{
//			            	  cellValue = "";
//			                  cellValue = row.getCell(j).getStringCellValue();
//			              }catch(IllegalStateException e){
//			            	    cellValue = ""+(row.getCell(j).getNumericCellValue());
//			            	    cellValue = cellValue.replaceAll("\\.0*$","");
//				            	System.out.println("**********Exception "+ e.toString());
//				          }catch(Exception e){
//			          
//			            	  System.out.println("**********Exception "+ e.toString());
//			              }		
//				          if(cellValue==null|cellValue.toUpperCase().matches(".*NULL.*")){
//				        	  cellValue = "";
//				          }
				         // System.out.print(" Cell["+j+"]="+cellValue+",");
			              bw.write(cellValue);
				            
			            }
			          }
			          bw.newLine();
//			          System.out.print("\n");
			        }
			      }
			      bw.flush();
			      bw.close();
			    }
			    catch (UnsupportedEncodingException e)
			    {
			      System.err.println("UnsupportedEncodingException in XlsToCsv of UnicodeUtil.java "+e.toString());
			    }
			    catch (IOException e)
			    {
			      System.err.println("IOException in XlsToCsv of UnicodeUtil.java "+e.toString());
			    }
			    catch (Exception e)
			    {
			      System.err.println("Other Exception in XlsToCsv of UnicodeUtil.java "+e.toString());
			    }
	    }catch(Exception e){
	    	System.err.println("Unknown Exception in XlsToCsv of UnicodeUtil.java "+e.toString());
	    }
	    
	     return csvName; 	      
	  }
	
    public static String getStringCellValue(HSSFCell cell){
        return getStringCellValueWithType(cell, "");
    }
    
    public static String getStringCellValueWithType(Cell cell, String types){
    	String cellValue = "";
    	String type = "";
    	
    	DateFormat df = DateFormat.getDateInstance() ;

    	int cellType = Cell.CELL_TYPE_STRING;
    	int exCellType = Cell.CELL_TYPE_STRING;
    	try{
    		cellType = cell.getCellType() ;
    		cellValue = new DataFormatter().formatCellValue(cell);
    		if(cellValue.matches("\\d+/\\d+/\\d+|\\d+-\\d+-\\d+")){
    			exCellType = -1;
    			//System.err.println("Value displayed in cell '"+cellValue+"'");
    		}
    	//	System.err.println("Value displayed in cell '"+cellValue+"'");
    		if(exCellType==-1){
    			cellValue = df.format(cell.getDateCellValue());
    			   type = "[Date]";
    		}else if(cellType==Cell.CELL_TYPE_NUMERIC){
    			cellValue = ""+(cell.getNumericCellValue());
          	    type = "[Numeric]";
    		}else if(cellType==Cell.CELL_TYPE_BOOLEAN){
    			cellValue = ""+cell.getBooleanCellValue();
    			type = "[Boolean]";
    		}else {
    			cellValue = cell.getStringCellValue();
    			if(cellType==Cell.CELL_TYPE_FORMULA){
    				type = "[Formula]";
    			}else if(cellType==Cell.CELL_TYPE_STRING){
    				type = "[String]";
    			}else if(cellType==Cell.CELL_TYPE_ERROR){
    				type ="[Error]";
    			}else{
    				type ="[Unknown]";
    			}
    		}
        }catch(Exception e){     
        	  try{
        		  cellValue = cell.getStringCellValue();
        		  type ="[String]";
        	  }catch(Exception unknown){
        		  try{
        			  if(cell==null){
        				 // System.out.println("Warning: empty cell - null value");
        				  cellValue="";
        			  }else{
        		       cellValue = new DataFormatter().formatCellValue(cell); 
        			  }
        		  }catch(Exception any){
        			 any.printStackTrace(); 
        				  
                  }
       		  
        		  type = "[NULL]";
        	  }
          	// System.out.println("**********Exception when get cell value '"+cellValue+"' "+ e.toString());
        }	
        
        if(cellValue==null|cellValue.toUpperCase().matches(".*NUL.*|\\s*|.*### ERR ###.*")){
      	  cellValue = "";     	  
        }
        cellValue = cellValue.trim();
        cellValue = cellValue.replaceAll("\\.0*$","").replaceAll(",",csvComma);
        
        if(cellValue.equals("")){
        	type = "[NULL]";
        }
        //System.out.println("**********cellValue '"+ cellValue+"' type '"+type+"'");
        if(types.contains(type)||types.equalsIgnoreCase("All")){
        	cellValue = type+cellValue;
        }
        return cellValue;
    }
    
//	public static void writeUnicode(final DataOutputStream out, final String value)  {
	public static String convertUnicodeSpace(String uniString){
	
		String[] from = {"\u00A0"};
		String to = "\u0020";
		for(int i=0;i<from.length;i++){
			uniString = uniString.replaceAll(from[i],to);
		}
		
		return uniString.trim();
	}
	
	public static String removeUnicodeSpace(String uniString){
		
		String from = "\u0020\u0020";
		String to = "\u0020";
		while(uniString.contains(from)){
			uniString = uniString.replaceAll(from,to);
		}
		
		return uniString.trim();
	}
	
	public static void printHexString(String in){
		byte[] array = in.getBytes();
		for (int k = 0; k < array.length; k++) {
			System.out.println("[" + k + "] = " + "0x" +
					   byteToHex(array[k]));
		    }

	}
	
	   static public String byteToHex(byte b) {
		      // Returns hex String representation of byte b
		      char hexDigit[] = {
		         '0', '1', '2', '3', '4', '5', '6', '7',
		         '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		      };
		      char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		      return new String(array);
		   }

		   static public String charToHex(char c) {
		      // Returns hex String representation of char c
		      byte hi = (byte) (c >>> 8);
		      byte lo = (byte) (c & 0xff);
		      return byteToHex(hi) + byteToHex(lo);
		   }

	public static void main(String[] args){
		try {
			CsvToXls("AX_Gateway\\KeywordTable\\SmokeTest\\login.csv",
					"AX_Gateway\\KeywordTable\\SmokeTest\\login.xls");
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
	}

}
