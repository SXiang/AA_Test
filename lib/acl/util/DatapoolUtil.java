package lib.acl.util;

import java.io.File;

import lib.acl.helper.sup.TAFLogger;

import org.eclipse.hyades.edit.datapool.IDatapool;
import org.eclipse.hyades.execution.runtime.datapool.IDatapoolIterator;

import conf.beans.TimerConf;

import com.rational.test.ft.datapool.DatapoolFactory;
import com.rational.test.ft.datapool.DatapoolUtilities;
import com.rational.test.ft.script.RationalTestScript;

import conf.beans.FrameworkConf;

/**
 * Description   : Functional Test Script
 * @author steven_sxiang
 */
public class DatapoolUtil
{
	/**
	 * Script Name   : <b>DataPoolUtil</b>
	 * Generated     : <b>Oct 22, 2009 11:28:55 AM</b>
	 * Description   : Datapools will be loaded automatically depending on the pool info in an input file
	 * Expected State: Successful Run
	 * 
	 * @since  2009/10/22
	 * @author steven_sxiang
	 */

	public static void defaultLogProcess(RationalTestScript ts){

		//boolean rename=false;
//		boolean copyResult=false;
//		boolean copyFig=false;
		String copyResult="";
		String copyFig="";
		//"../" + projectName + "_logs/";
		String rftLogDir = FrameworkConf.logRoot + TAFLogger.name;
		String tafLogDir = TAFLogger.file;
		String vbsDir = FrameworkConf.copyFilesVbs;
		String adjustDir = "\\\\"+FrameworkConf.projectName+"\\\\\\.\\.";

		//newRftLogName = TAFLogger.name + TAFLogger.time;

		File rftLogDirF = new File(rftLogDir);
		File tafLogDirF = new File(tafLogDir);
		File vbsDirF = new File(vbsDir);
        try{
        	if (ts.isMainScript() && rftLogDirF.exists()){
        		//System.out.println(rftLogDirF+" Existed");			
        		copyResult = FileUtil.copyDir(rftLogDirF.getAbsolutePath().replaceAll(
        				adjustDir, ""), tafLogDirF.getAbsolutePath().replaceAll(
        						adjustDir, ""));			
        		copyFig = FileUtil.copyDir((rftLogDirF.getAbsolutePath() + "\\..\\Log_Assistive_Files").replaceAll(
        				adjustDir,""), (tafLogDirF.getAbsolutePath() + "\\..\\Log_Assistive_Files"));
        		copyResult = FileUtil.copyDirVbs(vbsDirF.getAbsolutePath(), conf.beans.TimerConf.onTerminateSleep, 
					rftLogDirF.getAbsolutePath().replaceAll(
							adjustDir, "") + "\\*",
					tafLogDirF.getAbsolutePath().replaceAll(
							adjustDir, ""));
        		
//        		copyResult = FileUtil.copyDir(rftLogDirF.getAbsolutePath().replaceAll(
//        				adjustDir, ""), tafLogDirF.getAbsolutePath().replaceAll(
//        						adjustDir, ""));			
//        		copyFig = FileUtil.copyDir((rftLogDirF.getAbsolutePath() + "\\..\\Log_Assistive_Files").replaceAll(
//        				adjustDir,""), (tafLogDirF.getAbsolutePath() + "\\..\\Log_Assistive_Files").replaceAll(
//        						adjustDir, ""));
//        		copyResult = FileUtil.copyDirVbs(vbsDirF.getAbsolutePath(), conf.beans.TimerConf.onTerminateSleep, 
//					rftLogDirF.getAbsolutePath().replaceAll(
//							adjustDir, ""),
//					tafLogDirF.getAbsolutePath().replaceAll(
//							adjustDir, ""));
        		//rename = FileUtil.renameDir(rftLogDirF.getAbsolutePath(), newRftLogName);
        	}
        	}catch(Exception e){
        		System.err.println("Log Process exception: "+ e.toString());
        	}
	}
	
	public static Object[] setDefaultDataPool(RationalTestScript ts, File poolCsvFile){
		IDatapoolIterator dpi;
		IDatapool dataPool;

		//solve issue : <Null> in csv file -- may not needed any more sotped export back to csv
		//If xml, we acturally don't need this 
		   replaceData(poolCsvFile.getAbsolutePath(), "<NULL>", "",true);

		
		dataPool = DatapoolUtilities.loadCSV(poolCsvFile, ",", true);	

		DatapoolFactory dpf = (DatapoolFactory) ts.dpFactory();	

		dpi = dpf.open(dataPool, DatapoolFactory.DEFAULT_ITERATOR_CLASS, true);
		dpi.dpInitialize(dataPool);
		ts.dpInitialization(dataPool, dpi);

		return new Object[]{dataPool, dpi};
	}

	// Trying to solve <NULL> problem when exporting to csv file...
	public static void replaceData(String filename, String orig, String curr,boolean csvFile){
		String contents = FileUtil.getFileContents(filename);
		contents = contents.replaceAll(orig, curr);
		FileUtil.writeFileContents(filename, contents); 
	}
}
