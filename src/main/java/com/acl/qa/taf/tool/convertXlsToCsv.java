package com.acl.qa.taf.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.acl.qa.taf.util.FileUtil;

/**
 * Description : Functional Test Script
 * 
 * @author steven_xiang
 */
public class convertXlsToCsv {
	/**
	 * Script Name : <b>covertCsvToXls</b> Generated : <b>2010-06-29 12:44:49
	 * PM</b> Description : Functional Test Script Original Host : WinNT Version
	 * 5.1 Build 2600 (S)
	 * 
	 * @since 2010/06/29
	 * @author steven_xiang
	 */

	// Change csvFileOrDir for your conversion
	// String xlsFileOrDir =
	// "AX_GatewayPro/KeywordTable/SmokeTestNU/s_copy_light.xls";
	// String xlsFileOrDir =
	// "AX_Exception/KeywordTable/SmokeTest/Access_All_Entities_EM.xls";
	String xlsFileOrDir = "AX_GatewayPro/KeywordTable/SmokeTest/s_script_editor.xls";
	// String xlsFileOrDir = "AX_Exception/KeywordTable/SmokeTest";
	static String encoding = "UTF-8";

	public void testMain(Object[] args) {
		String csvName = "";
		String xlsName = FileUtil.getAbsDir(xlsFileOrDir);

		File xlsFile = new File(FileUtil.getAbsDir(xlsFileOrDir));
		if (xlsFile.isFile()) { // Single file conversion
			System.out.println("Processing file: '" + xlsFileOrDir + "'");
			csvName = xlsName.replace(".xls", ".csv");
			try {
				XlsToCsv(xlsName, csvName);
			} catch (Exception e) {
				//
				System.out.println("Exception from file processing");
				e.printStackTrace();
			}
		} else { // All files in the folder
			System.out.println("Processing files in Folder: '" + xlsFileOrDir
					+ "'");
			for (String xlsFileName : xlsFile
					.list(new com.acl.qa.taf.tool.FileFilter("xls"))) {
				xlsName = xlsFileOrDir + "/" + xlsFileName;
				csvName = xlsName.replace(".xls", ".csv");
				try {
					XlsToCsv(xlsName, csvName);
				} catch (Exception e) {
					//
					System.out.println("Exception from folder processing");
					e.printStackTrace();
				}
			}
		}
	}

	public static String XlsToCsv(String xlsName, String csvName) {
		// ************
		String updateColumn1 = "RequiredArgs[][STRING]";
		String columnBeforeColumn1 = "Keyword[][STRING]";
		int indexColumn1 = -1;
		// ************
		if (xlsName.endsWith(".csv") || xlsName.endsWith(".CSV")) {
			return xlsName;
		}

		try {
			// File to Get data in form of CSV
			// csvName = csvName.replaceAll(".csv", "_ConvertTest"+".csv");
			File cf = new File(FileUtil.getAbsDir(csvName));
			OutputStream os = new FileOutputStream(cf);
			OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
			BufferedWriter bw = new BufferedWriter(osw);

			// File to store data in form of XLS
			File xf = new File(FileUtil.getAbsDir(xlsName));
			InputStream is = new FileInputStream(xf);

			try {
				HSSFWorkbook hwb = new HSSFWorkbook(is, true);
				int numSheets = hwb.getNumberOfSheets();
				String cellValue = "";
				// System.out.println(xlsName);
				numSheets = 1; // we just convert the first sheet for our
								// automation, comment out this line if you need
								// convert all sheets
				for (int sheet = 0; sheet < numSheets; sheet++) {
					HSSFSheet s = hwb.getSheetAt(sheet);
					HSSFRow row = null, header = null;
					// Gets the cells from sheet
					// for (int i = 0 ; i <= s.getLastRowNum() ; i++)
					for (int i = 0; i < s.getPhysicalNumberOfRows(); i++) {
						row = s.getRow(i);
						if (i == 0) {
							header = row;
						}
						if (row.getLastCellNum() > 0) {
							cellValue = row.getCell(0).getStringCellValue();
							bw.write(cellValue);
							// System.out.print(cellValue);
							for (int j = 1; j < row.getLastCellNum(); j++) {
								bw.write(',');
								cellValue = row.getCell(j).getStringCellValue();
								// ******* Adding RequriedArgs column to
								// existing xls file
								if (indexColumn1 == j && i != 0) {
									cellValue = getRequriedArgs(header, row, j)
											+ "," + cellValue;
								}
								if (cellValue.equals(columnBeforeColumn1)
										&& (j + 1) < row.getLastCellNum()
										&& !row.getCell(j + 1)
												.getStringCellValue()
												.equals(updateColumn1)) {
									cellValue = cellValue + "," + updateColumn1;
									indexColumn1 = j + 1;
								}
								// ********* End of adding

								bw.write(cellValue);
								// System.out.println(cellValue);
							}
						}
						bw.newLine();
						// System.out.print("\n");
					}
				}
				bw.flush();
				bw.close();
			} catch (UnsupportedEncodingException e) {
				System.err.println(e.toString());
			} catch (IOException e) {
				System.err.println(e.toString());
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}

		return csvName;
	}

	public static String getRequriedArgs(HSSFRow header, HSSFRow row,
			int beginAt) {
		StringBuffer valueOfColumn = new StringBuffer();
		String currentValue = "";
		String currentHeader = "";
		boolean firstValue = true;
		String exceptStrings = "ExpectedErr[][STRING];Recent_Test[][STRING];Test_Message[][STRING];Test_Date[][STRING]";
		for (int i = beginAt; i < row.getLastCellNum(); i++) {
			currentValue = row.getCell(i).getStringCellValue();
			if (!currentValue.trim().equals("")) {
				currentHeader = header.getCell(i).getStringCellValue();
				if (!exceptStrings.contains(currentHeader)) {
					currentHeader = currentHeader.replace("[][STRING]", "");
					if (!firstValue)
						valueOfColumn.append(";");
					valueOfColumn.append(currentHeader);
					firstValue = false;
				}

			}
		}
		return valueOfColumn.toString();
	}

}
