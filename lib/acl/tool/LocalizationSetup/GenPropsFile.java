package lib.acl.tool.LocalizationSetup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import lib.acl.util.FileUtil;
/**
 * Description   : GenPropsFile
 * 					Converted from Merge_TransFiles.pl; And add the functionalities to 
 * 					include shortcuts in the properties files.
 * @since  2009/10/26
 * @author Henry_Wu
 * Modified by Henry Wu to add combinedPropsFile functions
 * Modified by Henry Wu to generated messages properties files directly from release build zip file
 */
public class GenPropsFile
{
	/////////////////////////////////////////////////////////////////////
	// input build zip file location, change it as needed
	private static String targetBuildPath = "acldbld_BumbleBee_int/20100430062541/";
	private static String releaseBuildI18nFile = "\\\\nas-build.acl.com/DevRoot/Data/Acldbld/gabara/cruisecontrol/artifacts/" 
		+ targetBuildPath + "I18nReleaseBuildClient/ReleaseBuildI18n-win32.win32.x86.zip";

	// output directory where you find the generated combined messages properties files
	private static String workingDir = "C:/temp/RFT_Localization/";
	private static String targetDir = workingDir + "properties/";
	/////////////////////////////////////////////////////////////////////

	// messages properties files package name
	private static String[] propsFilesListArr = {"com.acl.ax.base", "com.acl.ax.commonui", 
		"com.acl.ax.registry", "com.acl.ax.repo.server", "com.acl.ax.search", 
		"com.acl.ax.views.properties", "com.acl.ax.views.repository"};

	private static String versionInfoFileName = "version.properties";

	// supported language
	private static String[] supportLangArr = {"en", "bg", "de", "es", "fr", "pt"};

	private static String combinedPropsFilePrefix = "AXEM_Automation_";

	// obsolete Master properties files which include properties used only by automation, 
	// more maintenance efforts to keep it up-to-date
	private static String masterFile = workingDir + "input/MASTER.PROPERTIES";
	private static String outFileNamePrefix = "Master_AXEM_Automation_";

	private static String paddingStr = " ==== ";
	private static int logLvl = 2;	// 1: info; 2: debug

	public static void main(String args[]) throws Exception
	{
		if (args.length > 0) {
			//Brawn: targetBuildPath = "Brawn_AX2.1.1/20100304084827/";
			//Goldbug: targetBuildPath = "acldbld_BumbleBee_int/20100430062541/";
			targetBuildPath = args[0].endsWith("/")? args[0] : args[0] + "/";
			
			releaseBuildI18nFile = "\\\\nas-build.acl.com/DevRoot/Data/Acldbld/gabara/cruisecontrol/artifacts/" 
				+ targetBuildPath + "I18nReleaseBuildClient/ReleaseBuildI18n-win32.win32.x86.zip";
		} 
		
		if (args.length > 1) {
			try {
				logLvl = Integer.parseInt(args[1]);
			} catch (Exception e) {
				logLvl = 2;
			}
		}
		
		Locale defaultLocale = FileUtil.locale;

		logDebug("Generate Messages Properties files from [" + releaseBuildI18nFile + "]"
				+ "\n\t output dir is [" + targetDir + "]"
				+ "\n\t optional masterFile [" + masterFile + "]");

		File targetDirFile = new File(targetDir);
		if (! targetDirFile.exists()) {
			targetDirFile.mkdirs();
		}		
		
		// populate masterHm for generateMasterPropertiesFiles()
		Map<String,String> masterHm = buildMasterHm(masterFile);

		// get messages properties files from ReleaseBuildI18n*.zip file
		getMsgsPropsFilesFromBuildZip();

		String buildVersionInfo = getBuildVersionInfo(workingDir + versionInfoFileName);

		String curLang;
		String curPropFileName;
		String outCombinedPropsFileName;
		// loop each languages
		for (int i=0; i<supportLangArr.length; i++) {
			curLang = supportLangArr[i];

			Map<String,String> localeHm = new HashMap<String, String>();

			logInfo("\n=================================================================");
			logInfo("\tSearching properties file for language " + curLang + " ... ");
			logInfo("=================================================================");

			/////////////////////////////////////////////////////////////////////////////////
			// build combined properties file of all messages file provided by developer
			outCombinedPropsFileName = targetDir + combinedPropsFilePrefix + curLang + ".properties";
			logInfo("\n" + paddingStr + "Generating combined properties file: " + outCombinedPropsFileName + paddingStr + "\n");

			FileWriter txtCombined = new FileWriter(new File(outCombinedPropsFileName));
			PrintWriter outCombined = new PrintWriter(txtCombined);

			// all are same name for same language but just under different directories
			curPropFileName = curLang.equals("en")? "messages.properties" : "messages_" + (curLang.equals("pt")? "pt_BR":curLang) + ".properties";
			
			outCombined.print("##############################################################\n");
			outCombined.print("## Generated Messages Property File [" + outCombinedPropsFileName.substring(outCombinedPropsFileName.lastIndexOf("/") + 1) + "]\n");
			outCombined.print("## \tbased on baseline: [" + buildVersionInfo + "]\n");
			outCombined.print("## \tfrom build file: [" + releaseBuildI18nFile + "]\n\n");
			
			String curPropDir;
			String curPropFilePath;
			File curPropFile;
			// loop each messages properties files
			for (String curPropsFile : propsFilesListArr) {
				curPropDir = curPropsFile.replaceAll("\\.", "/");
				curPropFilePath = curPropDir + "/" + curPropFileName;

				curPropFile = new File(workingDir + curPropFilePath);

				if (! curPropFile.exists()) {	// no property file found
					continue;
				}

				logDebug(paddingStr + "Searching " + curPropFilePath);

				outCombined.print("##############################################################\n");
				outCombined.print("## START of " + curPropFilePath + "\n");

				String line;
				String key;
				String value;
				FileReader freader = new FileReader(curPropFile);
				LineNumberReader lnreader = new LineNumberReader(freader);
				while ((line = lnreader.readLine()) != null){
					if(! isKeyValueLine(line)) {
						continue;
					}

					key = line.split("=")[0];
					value = line.split("=")[1].replaceAll("&", "");

					// duplicate key addUserTitle, change com/acl/ax/views/properties/messages.properties
					// to addUsersTitle based on value "Add Users"
					if (key.equalsIgnoreCase("addUserTitle") 
							&& curPropFilePath.contains("views/properties")) {
						key = "addUsersTitle";
					}

					outCombined.print(key + "=" + value + "\n");

					if (masterHm != null) {	// build locale hashmap
						logDebug("localeHm:[" + key + "=" + value + "]");
						localeHm.put(key, value);
					}
				}

				freader.close();
				lnreader.close();
			}

			logInfo("\tAdding shortcut to combined properties file ...");
			logInfo("\t\tcheck '## START of SHORTCUT Part ##' for list.");
			appendShortcuts(outCombined, curLang);

			txtCombined.close();
			outCombined.close();

			// generate MasterPropertiesFiles if needed
			if (masterHm != null) {
				generateMasterPropertiesFiles(curLang, masterHm, localeHm);
			}
		} // end loop of language

		// cleanup
		deleteDir(new File(workingDir + "com"));

		logInfo("\n!!! Well done, find generated messages properties files under [" + targetDir + "] !!!");

		Locale.setDefault(defaultLocale);
	}

	private static String getBuildVersionInfo(String versionFileName) {
		String line;
		String key;
		String value = "";
		
		try {
			File curPropFile = new File(versionFileName);
			FileReader freader = new FileReader(curPropFile);
			LineNumberReader lnreader = new LineNumberReader(freader);
			
			while ((line = lnreader.readLine()) != null){
				if(! isKeyValueLine(line)) {
					continue;
				}

				key = line.split("=")[0];
				
				if (key.equalsIgnoreCase("baseline")) {
					value = line.split("=")[1];
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return value;
	}

	private static ZipInputStream zipinputstream;
	private static FileOutputStream fileoutputstream;

	private static void getMsgsPropsFilesFromBuildZip() throws Exception {
		logInfo("\n ==============================================================================================");
		logInfo(paddingStr + "Unzip properties jar files from [" + releaseBuildI18nFile + "] ..." + paddingStr);

		zipinputstream = new ZipInputStream(new FileInputStream(releaseBuildI18nFile));
		ZipEntry zipentry = zipinputstream.getNextEntry();

		// expected hierarchy: AuditExchange\plugins\com.acl.ax.*.jar
		int depth = 1;
		String propJarsDirRelPath = "";
		while (zipentry != null) {
			String entryName = zipentry.getName();

			if ((depth == 1) ||	// AuditExchange\
					(depth == 2 && entryName.equals(propJarsDirRelPath + "plugins/"))) // AuditExchange\plugins\
			{
				depth++;

				logDebug("Creating [" + workingDir + entryName + "]");
				new File(workingDir + entryName).mkdirs();

				propJarsDirRelPath = entryName;
			} else if (depth == 3 && isPropsFile(entryName)) {
				saveUnzipedItem(entryName);
			}

			zipinputstream.closeEntry();
			zipentry = zipinputstream.getNextEntry();
		}

		zipinputstream.close();

		//////////////////////////////////////////////////
		// get properties files from properties jar files
		logInfo("\n ==============================================================");
		logInfo(paddingStr + "Unzip properties files from properties jar files ..." + paddingStr);

		String propsJarsFileDir = workingDir + propJarsDirRelPath;
		int count = 1;
		for (String curPropJarFile : new File(propsJarsFileDir).list()) {
			logDebug(paddingStr + count++ + ". Get properties files from [" + curPropJarFile + "] ..." + paddingStr);

			zipinputstream = new ZipInputStream(new FileInputStream(propsJarsFileDir + "/" + curPropJarFile));
			zipentry = zipinputstream.getNextEntry();

			while (zipentry != null) {
				String entryName = zipentry.getName();

				if (entryName.startsWith("com")
						&& entryName.endsWith("/")
						&& isPropDir(entryName)) {
					new File(workingDir + entryName).mkdirs();
				} else if (entryName.startsWith("com") && entryName.endsWith("properties")){
					saveUnzipedItem(entryName);
				}

				zipinputstream.closeEntry();
				zipentry = zipinputstream.getNextEntry();
			}

			zipinputstream.close();
		}

		// cleanup
		deleteDir(new File(workingDir + propJarsDirRelPath.substring(0, propJarsDirRelPath.indexOf("/"))));
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			for (String child : dir.list()) {
				boolean success = deleteDir(new File(dir, child));
				if (! success) {
					return false;
				}
			}
		}

		logDebug("Deleting [" + dir + "] ...");
		return dir.delete();
	}

	private static boolean isPropDir(String entryName) {
		boolean found = false;

		String curPropsDir;
		for (String curPropsFile : propsFilesListArr) {
			curPropsDir = curPropsFile.replaceAll("\\.", "/") + "/";
			if (curPropsDir.contains(entryName)) {
				found = true;
				break;
			}
		}

		return found;
	}

	private static boolean isPropsFile(String entryName) {
		boolean found = false;
		for (String curPropsFile : propsFilesListArr) {
			if (entryName.contains("plugins/" + curPropsFile)
					&& ! entryName.contains("help")) {
				found = true;
				break;
			}
		}

		return found;
	}

	private static void saveUnzipedItem(String entryName) {
		try {
			logDebug("Saving [" + entryName + "] ...");
			if (! entryName.contains(versionInfoFileName)) {
				fileoutputstream = new FileOutputStream(workingDir + entryName);
			} else {
				fileoutputstream = new FileOutputStream(workingDir + versionInfoFileName);
			}

			byte[] buf = new byte[1024];
			int n;

			while ((n = zipinputstream.read(buf, 0, 1024)) > -1){
				fileoutputstream.write(buf, 0, n);
			}

			fileoutputstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isKeyValueLine(String line) {
		//logDebug("Line:  " + ": " + line);
		return (line == null || "".equalsIgnoreCase(line.trim()) 
				|| line.startsWith("#") || line.indexOf("=") == -1)? false : true;
	}

	private static Map<String,String> buildMasterHm(String masterFile) {
		Map<String,String> masterHm = new TreeMap<String, String>();
		String[] tmpArr;

		String line;
		String key;
		String value;

		// Build master map
		try {
			FileReader freader = new FileReader(new File(masterFile));
			LineNumberReader lnreader = new LineNumberReader(freader);
			while ((line = lnreader.readLine()) != null){
				if(! isKeyValueLine(line)) {
					continue;
				}

				tmpArr = line.split("=");
				key = tmpArr[0];
				value = (tmpArr.length > 1)? tmpArr[1] : "NULL"; // keep default value

				logDebug(key + "=" + value);
				masterHm.put(key, value);
			}

			freader.close();
			lnreader.close();
		} catch (IOException e) {
			logInfo(paddingStr + "No baseline master properties file provided, so NO master properties file generated." + paddingStr);
			masterHm = null;
		}

		return masterHm;
	}

	private static void generateMasterPropertiesFiles(String curLang, Map<String,String> masterHm, Map<String,String> localeHm) {

		try {
			String outFileName = targetDir + outFileNamePrefix + curLang + ".properties";
			logInfo("\n" + paddingStr + "Generating master properties file: " + outFileName + paddingStr + "\n");
			FileWriter txt = new FileWriter(new File(outFileName));
			PrintWriter out = new PrintWriter(txt);

			String key;
			String value;

			for (Entry<String, String> masterEntry : masterHm.entrySet()) {
				key = masterEntry.getKey();
				value = localeHm.get(key);

				if (value != null && ! "".equalsIgnoreCase(value)) {
					value = value.replaceAll("&", "");
				} else {
					// use masterHm default value
					value = masterEntry.getValue();
					logWarning("No property found for [" + key 
							+ "] ==> [" + value + "] (set to default by masterFile)");
				}

				logDebug("write2File:[" + key + "=" + value + "]");
				out.print(key + "=" + value + "\n");
			}

			// 3. append more key/value for keyboard shortcuts
			logInfo("\tAdding shortcut to master properties file ...");
			logInfo("\t\tcheck '## START of SHORTCUT Part ##' for list.");
			appendShortcuts(out, curLang);

			txt.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final HashMap<String,String> enShortcuts = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("scCopy","%ec"); 				//Edit - Copy
			put("scCut","%et"); 				//Edit - Cut
			put("scPaste","%ep"); 				//Edit - Paste
			put("scNewEng","%f%w%e");			//File - New - Engagement
			put("scNewAct","%f%w%a");			//File - New - Activity
			put("scNewResult","%f%w%r"); 		//File - New - Result
			put("scRename","%fm");				//File - Rename
			put("scDiscon", "%f%s");			//Repository - Disconnect
			put("scDel", "%f%d{ENTER}");		//File - Delete
			put("scImport", "%fi");				//File - Import		
			put("scExport", "%fe");				//File - Export
			put("scAnaRun", "%nr");				//Anakytics - Run
			put("scAnaSch", "%ns");				//Anakytics - Schedule
		}
	};

	private static final HashMap<String,String> frShortcuts = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("scCopy","%mp"); 		
			put("scCut","%mc");		
			put("scPaste","%mo");
			put("scNewEng","%f%n%e");	
			put("scNewAct","%f%n%a");	
			put("scNewResult","%f%n%r");
			put("scRename","%fr");		
			put("scDiscon", "%f%d");	
			put("scDel", "%f%s");	
			put("scImport", "%fi");		
			put("scExport", "%fe");
			put("scAnaRun", "%ne");
			put("scAnaSch", "%np");
		}
	};

	private static final HashMap<String,String> esShortcuts = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("scCopy","%ec"); 		
			put("scCut","%et");		
			put("scPaste","%ep");
			put("scNewEng","%a%n%m");	
			put("scNewAct","%a%n%a");	
			put("scNewResult","%a%n%r");
			put("scRename","%ar");		
			put("scDiscon", "%a%d");	
			put("scDel", "%a%m");	
			put("scImport", "%ai");		
			put("scExport", "%ae");
			put("scAnaRun", "%ne");
			put("scAnaSch", "%np");
		}
	};

	private static final HashMap<String,String> deShortcuts = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("scCopy","%bk"); 		
			//put("scCut","%b{ENTER}");	//Edit - Cut (walk around for bug) - should be fixed now
			put("scCut","%ba"); //Edit - Cut (should be this one)
			//for cut should use the et to ba conversion below, but current theres's a bug
			//in the app that the mnemonic is wrong, so select the first item from edit menu instead
			//myStringToConvert = myStringToConvert.replace("scCut","%ba");
			put("scPaste","%be");
			put("scNewEng","%d%n%e");	
			put("scNewAct","%d%n%a");	
			put("scNewResult","%d%n%r");	
			put("scRename","%du");		
			put("scDiscon", "%d%v");	
			put("scDel", "%d%l");	
			put("scImport", "%di");		
			put("scExport", "%de");
			put("scAnaRun", "%aa");
			put("scAnaSch", "%ab");
		}
	};

	private static final HashMap<String,String> ptShortcuts = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("scCopy","%eo"); 		
			put("scCut","%et");		
			put("scPaste","%el");
			put("scNewEng","%a%n%a");	
			put("scNewAct","%a%n%t");	
			put("scNewResult","%a%n%r");
			put("scRename","%ar");		
			put("scDiscon", "%a%d");	
			put("scDel", "%ax");	
			put("scImport", "%ai");		
			put("scExport", "%ae"); 
			put("scAnaRun", "%ne");
			put("scAnaSch", "%na");
		}
	};

	// no mapping for bg till now
	//private static final HashMap<String,String> bgShortcuts = enShortcuts;
	private static final HashMap<String,String> bgShortcuts = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("scCopy","%\\u0440\\u043a"); 		
			put("scCut","%\\u0440\\u0438");		
			put("scPaste","%\\u0440\\u043f");
			put("scNewEng","%\\u0424%\\u043d%\\u0430");	
			put("scNewAct","%\\u0424%\\u043d%\\u0434");	
			put("scNewResult","%\\u0424%\\u043d%\\u0440");
			put("scRename","%\\u0424\\u043f");
			put("scDiscon", "%\\u0424\\u043a");
			put("scDel", "%\\u0424\\u0437");
			put("scImport", "%\\u0424\\u0438");	
			put("scExport", "%\\u0424\\u0435");
			put("scAnaRun", "%\\u043d\\u0441");
			put("scAnaSch", "%\\u043d\\u043f");
		}
	};

	private static void appendShortcuts(PrintWriter out, String langStr) {	
		out.print("\n############################\n");
		out.print("## START of SHORTCUT Part ##\n");

		HashMap<String,String> shortcutsMap=null;

		if ("en".equalsIgnoreCase(langStr)) {
			shortcutsMap = enShortcuts;
		} else if ("fr".equalsIgnoreCase(langStr)) {
			shortcutsMap = frShortcuts;
		} else if ("es".equalsIgnoreCase(langStr)) {
			shortcutsMap = esShortcuts;
		} else if ("de".equalsIgnoreCase(langStr)) {
			shortcutsMap = deShortcuts;
		} else if ("pt".equalsIgnoreCase(langStr)) {
			//for pt_BR
			shortcutsMap = ptShortcuts;
		} else if ("bg".equalsIgnoreCase(langStr)) {
			shortcutsMap = bgShortcuts;
		}

		// write to target property file
		for (Entry<String, String> entry : shortcutsMap.entrySet()) {
			out.print(entry.getKey() + "=" + entry.getValue() + "\n");
		}
	}

	private static void logWarning(String message) {
		System.out.println("\t\t#### " + message + "####");
	}

	private static void logInfo(String message) {
		if (logLvl >= 1) {
			System.out.println(message);
		}
	}

	private static void logDebug(String message) {
		if (logLvl >= 2) {
			System.out.println(message);
		}
	}
}