package com.acl.qa.taf.tool;
import java.io.File;
import java.io.IOException;

import com.acl.qa.taf.util.FileUtil;
import com.acl.qa.taf.util.UnicodeUtil;

/**
 * Description   : Functional Test Script
 * @author steven_xiang
 */
public class convertCsvToXls
{
	/**
	 * Script Name   : <b>covertCsvToXls</b>
	 * Generated     : <b>2010-06-29 12:44:49 PM</b>
	 * Description   : Functional Test Script
	 * Original Host : WinNT Version 5.1  Build 2600 (S)
	 * 
	 * @since  2010/06/29
	 * @author steven_xiang
	 */
	
	// Change csvFileOrDir for your conversion 
	String subFolder[]= {""};
//	String subFolder[]= {"/Analytics","/Copy_Paste","/Create_Rename","/Data_Analytic_Link",
//	        "/Delete","/Import_Export","/Login","/Permissions","/Search"};
//	String csvFileOrDir = "AX_GatewayPro/KeywordTable/Dubug_Datapools";
//	String csvFileOrDir = "AX_GatewayPro/KeywordTable/Dubug_Datapools/Data_Analytic_Link";
	String csvFileOrDir = "AX_GatewayPro/KeywordTable/SmokeTest/S_Admin_Add_Delete_AUTOQA_MODY.csv";
	//String csvFileOrDir = "AX_Gateway/KeywordTable/SmokeTest/Book3c.csv";
//	String csvFileOrDir = "AX_Exception/KeywordTable/SmokeTest";
//	String csvFileOrDir = "AX_Addins/KeywordTable/SmokeTest";
	public void testMain(Object[] args) 
	{
		for(int i=0;i<subFolder.length;i++){
			convertCsv(csvFileOrDir + subFolder[i]);
		}				
	}
	
	private void convertCsv(String csvFileOrDir){
		String xlsName="";
		String csvName=FileUtil.getAbsDir(csvFileOrDir);

		File csvFile = new File(FileUtil.getAbsDir(csvFileOrDir)); 
		if(csvFile.isFile()){ //Single file conversion		
			System.out.println("Processing file: '"+csvFileOrDir+"'");
			xlsName = csvName.replace(".csv",".xls");
			try {
				UnicodeUtil.CsvToXls(csvName, xlsName);
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}
		}else{ // All files in the folder
			System.out.println("Processing files in Folder: '"+csvFileOrDir+"'");
			for (String csvFileName: csvFile.list(new com.acl.qa.taf.tool.wikiConvert.FileFilter("csv"))) {				
			    csvName = csvFileOrDir + "/" + csvFileName;
				xlsName = csvName.replace(".csv", ".xls");
				try {
					UnicodeUtil.CsvToXls(csvName, xlsName);
				} catch (IOException e) {
					// 
					e.printStackTrace();
				}
			}
		}
	}
}

