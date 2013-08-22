package com.acl.qa.taf.util;

import ibm.loggers.control.IPackageLoggerConstants;
import ibm.loggers.control.PackageLoggingController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;



public class FileUtil extends ibm.util.FileOps {
	
	public static String encoding = "UTF-8";	
	public static Locale javalocale = Locale.getDefault();	
	public static Locale locale = javalocale;
	public static String userWorkingDir = System.getProperty("user.dir").replaceAll("\\\\","/");
	//getSystemLocale();
	//**************  CMD operations ********************************
	
	public static String makeWriteable(String dir){
		String comm = "ATTRIB,-r,-s,-a,-h,\"" + getAbsDir(dir) + "\\*.*\",/D,/S";
		//String comm = "ATTRIB -r -s -a -h \"" + dir + "\\*.*\" /D /S";
    	return exeComm(comm);
	}
	public static String updateDir(String source, String dest){
		String comm = "XCOPY,\"" + getAbsDir(source).replaceAll("[\\\\/]+$","") + "\\*\",\"" + getAbsDir(dest) + "\",/Y,/S,/D/R,/I";
		//String comm = "XCOPY \"" + source + "\\*\" \"" + dest + "\" /Y /S /D /R /I";
    	return exeComm(comm);
	}
    public static String copyDir(String source, String dest){	
    	if(!new File(source).exists())
    			return "No source found - '"+source+"'";
    	//mkDirs(dest,true);
    	String comm = "XCOPY,\"" + getAbsDir(source).replaceAll("[\\\\/]+$","") + "\",\"" + getAbsDir(dest) + "\",/S,/R,/Y,/I,/G,/H,/Z,/C";
    	//String comm = "XCOPY \"" + source + "\" \"" + dest + "\" /S /R /Y /I /G /H /Z /C";
    	return exeComm(comm);
    }
    public static String copyFiles(String source, String dest){	
    	String comm = "XCOPY,\"" + getAbsDir(source) + "\",\"" + getAbsDir(dest) + "\",/R,/Y,/I,/G,/Z,/C";
    	//String comm = "XCOPY \"" + source + "\" \"" + dest + "\" /R /Y /I /G /Z /C";
    	return exeComm(comm);
    }
    public static String copyFile(String source, String dest){	
    	return copyFile(source,dest,true);
    }
    public static String copyFile(String source, String dest, boolean confirm){	
    	String result="done";
    	String appFile = ".+\\.XLS[X]?[\\s]*$";
    	//if(!new File(source).renameTo(new File(dest)))   	
    	String comm = "COPY,/B,/Y,\"" + getAbsDir(source) + "\",\"" + getAbsDir(dest) + "\"";
    	result = exeComm(comm);
    	if(confirm&&!new File(getAbsDir(dest)).exists()&&!dest.toUpperCase().matches(appFile)) // it only works for character files
    	    writeFileContents(getAbsDir(dest), getFileContents(source));
    	return result;    	
    }
    public static String renameFiles(String source, String dest,boolean confirm){	
    	String comm = "REN,\"" + getAbsDir(source) + "\",\"" + dest + "\"";
    	//String comm = "REN \"" + source + "\" \"" + dest + "\"";
    	return exeComm(comm,confirm);
    }
    public static String renameFiles(String source, String dest){	
    	return renameFiles(source,dest,true);
    }
    
    public static String moveFile(String source, String dest){	
    	String comm = "MOVE,\"" + getAbsDir(source) + "\",\"" + getAbsDir(dest) + "\",/Y";
    	//String comm = "MOVE \"" + source + "\" \"" + dest + "\" /Y";
    	return exeComm(comm);
    }
    public static String copyDirVbs(String script, int stime, String from, String dest){	
    	String comm = "Wscript," + script + "," + stime + "," + from + "," + dest;
    	return exeComm(comm);
    }
    public static String regsvr32(String dll, String path){
    	String comm = "regsvr32 /s "+getAbsDir(dll,path);
    	//System.out.println("Register 32: '"+comm+"'");
    	return exeComm(comm,false);
    }
    public static String delFile(String source,String path){	
    	//String comm = "DEL \"" + getAbsDir(source,path) + "\" /S /Q";
    	String comm = "DEL,\"" + getAbsDir(source,path) + "\",/S,/Q";
    	return exeComm(comm,false);
    }
    public static String delFile(String source){	
    	//String comm = "DEL \"" + getAbsDir(source) + "\" /S /Q";
    	String comm = "DEL,\"" + getAbsDir(source) + "\",/S,/Q";
    	return exeComm(comm,false);
    }
    
    public static String removeDir(String path){	
    	String comm = "RMDIR,\"" + getAbsDir(path) + "\",/S,/Q";
    	return exeComm(comm,false);
    }
    public static String[] runVBScript(String script, String args){
    	String[] lines;
    	
    	String comm = "Cscript," + getAbsDir(script,"") + ","+args;
    	
    	lines = exeComm(comm).split("\\n");
    	return lines;
    }
    public static String[] runExecutable(String script, String args){
    	String[] lines;
    	
    	String comm = getAbsDir(script,"") + " "+args;
    	
    	lines = exeComm(false,comm).split("\\n");
    	return lines;
    }
    
    public static String renameDir(String path, String name ){
    	String comm = "MOVE,/Y" + path + "," + name;
    	return exeComm(comm);
    }
    
    
    public static String combinefiles(String[] files,String tofile){
    	String comm = "COPY,/Y,"+getAbsDir(files[0],"");
    	for(int i=1;i<files.length;i++){
    		comm += " + "+getAbsDir(files[i],"");
    	}
    	return exeComm(comm+","+getAbsDir(tofile,""));
    }
    // Use Java Runtime to execute commands
    public static String exeComm(String command){
    	return exeComm(command,true);
    }
    public static String exeComm(boolean usPB,String command){
    	return exeComm(usPB,command,true);
    }
    public static String exeComm(String command,boolean getResult){    
    	return exeComm(true,command,getResult);
    }
    public static String exeComm(boolean usPB,String command,boolean getResult){
    	String cmd = "cmd.exe /c ";
    	//String cmd = "cmd.exe /U /K ";
    	Process ps = null;
    	String result = "Done";
    	ProcessBuilder processBuilder;
    	
//    	command = command.replaceAll("/,", "\\");
//    	command = command.replaceAll("\\/,", ",");
//    	command = command.replaceAll("/,", "\\,");
//    	command = command.replaceAll("/", "\\");
    	try {
    		if(usPB&&command.contains(",")){
    			ps = processComm(command.split(","));    			
        	}else{
    			ps = Runtime.getRuntime().exec(cmd + command);
    		}
    		if(getResult)
    		   result = readStream(ps.getInputStream());
    		else{
    			Thread.sleep(3000);
    		}
    		//Thread.sleep(1000);
    		//ps.destroy();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
//    	System.out.println("\t\tExecute Command: '"+cmd+command+"'\n"+
//    			           "\t\tReturned StdOut: '"+result+"---");
    	if(result.equals("Done")){
    		result="";
    	}
    	return result;
    }

    public static Process processComm(String[] cmd) throws IOException{
    	Map<String, String> newEnv = new HashMap<String, String>();
    	newEnv.putAll(System.getenv());
    	String[] i18n = new String[cmd.length + 2];
    	i18n[0] = "cmd";
    	i18n[1] = "/C";
    	i18n[2] = cmd[0];
    	for (int counter = 1; counter < cmd.length; counter++)
    	{
    		String envName = "JENV_" + counter;
    		i18n[counter + 2] = "%" + envName + "%";
    		newEnv.put(envName, cmd[counter]);
    	}
    	cmd = i18n;

    	ProcessBuilder pb = new ProcessBuilder(cmd);
    	Map<String, String> env = pb.environment();
    	env.putAll(newEnv);
    	return pb.start();
    }
	public static String readStream(InputStream fstream) {
		String file = "";
		int timeLimit = 0;
		try {
			BufferedReader in =
				new BufferedReader(new InputStreamReader(fstream,encoding));
			String line = "";
			
			while ((line=in.readLine())!= null) {
				if(!line.matches("Microsoft.*|Copyright.*|\\s")&&!line.equals(""))
				      file = file + line + "\n";
			}
			in.close();
		} catch (IOException e) {
			PackageLoggingController.logPackageError(IPackageLoggerConstants.PACKAGELOGLEVEL_ERRORS_ONLY, "Error in FileOps#readFile: " + e.getMessage());
		}
		return file;
	}
    //**************   Fils operations ***************************
	 public static String getAbsDir(String inputDir){
	    return getAbsDir(inputDir,"");
	 }
	 
    public static String getAbsDir(String inputDir,String root){
    	String absDir = inputDir;
    	String userDir = "%user.dir%";
    	URL absURL;
    	boolean isDir = false;
    	
    	
    	absDir = absDir.replaceAll(userDir, userWorkingDir);
    	File dirFile = new File(absDir);
    	
    	if(dirFile.isDirectory()){
    		isDir = true;
    	}else {
    		if(inputDir.endsWith("/")||inputDir.endsWith("\\")){
    			isDir = true;
    		}
    	}
    	if(dirFile.isAbsolute()){
    		absDir = absDir.replaceAll("/$|\\|$", "");
    	}else{
    		absDir = absDir.replaceAll("\\\\","/");
    		if(absDir.startsWith("/")){
    			absDir.replaceFirst("/", "");
    			//absURL =class.getClassLoader().getResource(absDir);  			
    		}
			   absURL = ClassLoader.getSystemResource(absDir);
    		
    		if(absURL==null){
    			System.out.println("Dir: '"+inputDir+"' is not a valid java resources?");
    			absDir = inputDir;
    		}else{
    			absDir = absURL.toString().replaceFirst("file:/", "");
    		}
    		    		
    	}  
    	absDir.replaceAll("//", "/");

			if(!absDir.endsWith("/")&&!absDir.endsWith("\\")
					&&isDir)
				absDir += "/";
			

    	return absDir;
    }

    public static long getFileSize(String filename) {

        File file = new File(getAbsDir(filename));
        
        if (!file.exists() || !file.isFile()) {
          System.out.println("File "+filename+" doesn\'t exist");
          return -1;
        }
        
        //Here we get the actual size
        return file.length();
      } 
    
    
    // Handle UTF-8 encoding
    public static String readFile(String source){
    	return readFile(source,"\n",Integer.MAX_VALUE, false);
    }
    
    public static String readFile(String source,String del){
    	return readFile(source,del,Integer.MAX_VALUE, false);
    }
    public static String readFile(String source,String del,int maxLines){
    	return readFile(source,del, maxLines,false);
    }
    public static String readFile(String source,int maxLines, boolean addLineFeed){
    	return readFile(source,maxLines,addLineFeed,encoding);
    }
    public static String readFile(String source,int maxLines, boolean addLineFeed,String encoding) {
    	return readFile(source,"\n",maxLines,addLineFeed,encoding);
    }
    public static String readFile(String source,String del,int maxLines, boolean addLineFeed) {
    	return readFile(source,del,maxLines,addLineFeed,encoding);
    }
	public static String readFile(String source,String del,int maxLines, boolean addLineFeed,String encoding) {
		String file = "";
		int maxLen=0,curLen=0 ;
		String controls = "[\\p{Cc}\\p{Cntrl}]";
		
        String defaultDel = System.getProperty("line.separator");
        if(del.equals(""))
        	del = defaultDel;
		if(!readable(source)){
			return "Error, Can't read file -"+source;
		}
		try {
			FileInputStream fstream = new FileInputStream(getAbsDir(source));
			
			BufferedReader in =
				new BufferedReader(new InputStreamReader(fstream,encoding));
			String line = "";
			int numLines = 0;
            
			while (((line = in.readLine()) != null)&&(numLines++<maxLines)) {
				if(addLineFeed){
					//line = ObjectHelper.getPrintableText(line);
					//line = line.replaceAll(controls, "");
					if(line.length()>100){
						//line = line.replaceAll("[\\x03\\x04]", "\n");
						//line = line.replaceAll("(.{500,600}"+controls+")", "$1"+del);
						
						line = line.replaceAll("(.{100,100})", "$1"+del);
						
					}
					file = file + line + del;
				}
//				else{
//					line = line.replaceAll("["+del+"]", defaultDel);
//					file = file + line + defaultDel;
//				}
				file = file + line + del;
				
			}
			in.close();
			//System.out.println("File: '"+numLines+" lines - "+file);
		} catch (IOException e) {
			PackageLoggingController.logPackageError(IPackageLoggerConstants.PACKAGELOGLEVEL_ERRORS_ONLY, "Error in FileOps#readFile: " + e.getMessage());           
		}
		return file;
	}
    public static boolean readable(String source){
    	boolean readable=false;
    	int maxWait=10;
    	int wait = 0;
        File fi = new File(getAbsDir(source));
        while(!fi.canRead()&&wait++<maxWait){
        	try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        }
        readable = fi.canRead();
        
        return readable;
    }
    public static String getFileContents(String filename) {
      return getFileContents(filename,encoding);
    }
	public static String getFileContents(String filename,String encoding) {
		filename = getAbsDir(filename);
		if(!readable(filename)){
			return "";
		}
		try {
			File file = new File(filename);
			//FileReader in = new FileReader(file);
			InputStreamReader in = new InputStreamReader(new FileInputStream(filename),encoding);
		
			char c[] = new char[(int) file.length()];
			in.read(c);
			String fileContentsString = new String(c);
			in.close();
			return fileContentsString;
		} catch (IOException e) {
			PackageLoggingController.logPackageError(IPackageLoggerConstants.PACKAGELOGLEVEL_ERRORS_ONLY, "Error in FileOps#getFileContents: " + e.getMessage());
			return "";
		}
	}
	

		public static void appendStringToFile(FileWriter out, String sContents) {			
			try {
				out.write(sContents+System.getProperty("line.separator"));
				out.flush();
				
			} catch (IOException e) {
				PackageLoggingController.logPackageError(IPackageLoggerConstants.PACKAGELOGLEVEL_ERRORS_ONLY, "Error in FileOps#appendStringToFile: " + e.getMessage());
			}
		}
	
	public static void writeFileContents(String filename, String sContents) {
	   writeFileContents(filename,sContents,encoding);
	}
	
	public static OutputStreamWriter getWriter(String filename){
		return getWriter(filename,encoding);
	}
	public static OutputStreamWriter getWriter(String filename, String eccoding){
		OutputStreamWriter out = null;
         try {
			out = new OutputStreamWriter(new FileOutputStream(filename),encoding);			
		} catch (Exception e) {
			System.err.println("\tWarning: exception when writing to file "+filename+": "+e.toString());
			//PackageLoggingController.logPackageError(PackageLoggingController.PACKAGELOGLEVEL_ERRORS_ONLY, "Error in FileOps#writeFileContents: " + e.getMessage());
		}
		return out;
	}
	public static void writeFileContents(String filename, String sContents,String encoding) {
		//write specified string content to file	
		try {
			
			//FileWriter out = new FileWriter(filename);
			OutputStreamWriter out = getWriter(filename,encoding); 			
			out.write(sContents);
			out.close();
		} catch (Exception e) {
			System.err.println("\tWarning: exception when writing to file "+filename+": "+e.toString());
			//PackageLoggingController.logPackageError(PackageLoggingController.PACKAGELOGLEVEL_ERRORS_ONLY, "Error in FileOps#writeFileContents: " + e.getMessage());
		}
	}

   public static boolean isFileLockReleased(String filename,int maxTime){
	   boolean done = false;
	   FileOutputStream fos = null;
	   int time = 0;
	   while(!done&&time<maxTime){
		   try{
			 Thread.sleep(5000);
		     File f = new File(getAbsDir(filename));
		     //System.out.println(f.canWrite()); // -> true
		     fos = new FileOutputStream(f); // -> throws a FileNotFoundException
		     done = true;
		   }catch(Exception e){
			   time += 5;			   
		   }
	   }
	   
	   try {
		      fos.close();
	   } catch (IOException e) {}
	   
	   return done;
   }
   public static String removeFileExtension(String filename){
	   String pattern = "[.][^.]+$";
	   String name = filename.replaceFirst(pattern, "");
       return name;
   }
    public static String getFullNameWithExt(String orifile,String fileExt){
    	if(orifile==null||orifile.equals("")){
    		return "";
    	}
    	String filename = orifile,fname;
    	fname = getFullName(orifile);
    
    	if(fname!=null&&fname.length()>0){
    		if(!fname.matches(".*\\..+")){
    			filename = orifile+fileExt;
    		}
    	}
    	return filename;
    }
    
    public static String getFullName(String orifile){
    	if(orifile==null||orifile.equals("")){
    		return "";
    	}
    	String filename = orifile;
    	
    	try{
    		File file = new File(getAbsDir(orifile));
    		filename = file.getName();
    	}catch(Exception e){
    		
    	}
    	return filename;
    }
    public static void mkDirs(String path){
        mkDirs(path,false);
    }
	public static void mkDirs(String path,boolean delFile) {
        
		File file = new File(getAbsDir(path));
        if(delFile){
            delFile(path);
        }
        if(!file.isDirectory()){
          if(!file.exists()){
        	  if(path.endsWith("\\")||path.endsWith("/")){
        		  
        	  }else{
        	   path = file.getParent();
        	   file = new File(path);
        	  }
          }else{
       	   path = file.getParent();
       	   file = new File(path);
          }
          
        }

        try{
        	if(!file.mkdirs()){
        		exeComm("mkdir "+path,true);
        	}
        }catch(Exception e){
        	exeComm("mkdir "+path,true);
        	//file.mkdirs();
        }
	}
	public static void mapDrive(String dl, String serverDir, String user, String pass){
		
		String delDir,mapDir;
	
		delDir =  "net use "+dl+": /delete";
		mapDir =  "net use "+serverDir+" \""+pass+"\""+
		          " /USER:\""+user+"\" /P:Yes";
		//System.out.println("Del net drive: "+delDir);
		if(dl!=null&&!dl.equals(""))
		      exeComm(delDir,true);
		//System.out.println("Map net drive: "+mapDir);
		exeComm(mapDir,true);
	}
   public static Locale getSystemLocale(){
	   String language = "",country = "CA";
	   Locale locale = null;
	   
    // Disable following if they are  too expensive - Steven
	   
	   String comm = "systeminfo | findstr /B /C:\"System Locale\"";
	   String line = exeComm(comm);
	   if(line!=null){
		   try{
	           line = line.substring(line.indexOf("System Locale")+14,line.indexOf(";")).replaceAll("\\s","");
	           String[] tp = line.split("[-]");
	           language = tp[0];
	           if(!language.equalsIgnoreCase("en")&&tp.length>1){
	              country = tp[1];
	           }
               locale = new Locale(language);
	       
		   }catch(Exception e){
			   
		   }
	   }
	   //locale =  language+"_"+country;
	   if(locale==null){
		   System.out.println("Warning: Failed to get the OS's system locale by '"+comm+"'\n Java system locale will be used instead");
		   locale = javalocale = Locale.getDefault();	
	   }

	   return locale;
   }
}
