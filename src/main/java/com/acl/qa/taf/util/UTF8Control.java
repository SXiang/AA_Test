package com.acl.qa.taf.util;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.ResourceBundle.Control;

public class UTF8Control extends Control {
	
	// Usage: ResourceBundle bundle = 
	//   ResourceBundle.getBundle("com.example.i18n.text", new UTF8Control());

	String encoding = "UTF-8";
    public ResourceBundle newBundle
        (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException
    {
        // The below is a copy of the default implementation.
        String bundleName = toBundleName(baseName, locale);
        String resourceName = FileUtil.getAbsDir(toResourceName(bundleName, "properties"));
        ResourceBundle bundle = null;
        FileInputStream stream = null;
        stream = new FileInputStream(resourceName);
//        InputStream stream = null;
//        if (reload) {
//            URL url = loader.getResource(resourceName);
//            if (url != null) {
//                URLConnection connection = url.openConnection();
//                if (connection != null) {
//                    connection.setUseCaches(false);
//                    stream = connection.getInputStream();
//                }
//            }
//        } else {
//            stream = loader.getResourceAsStream(resourceName);
//        }
        if (stream != null) {
            try {
                // Only this line is changed to make it to read properties files as UTF-8.
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, encoding));
            } finally {
                stream.close();
            }
        }
        return bundle;
    }
    
    public static String decode(String src,String from,String to){
    	String response = src;
 	   try {
 			response  = new String(src.getBytes(from), to);
 		} catch (UnsupportedEncodingException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 	   
 	   return response;
    }
    public static String utf8decode(String src,String from){
    	String to = "UTF-8";
    	return decode(src,from,to);
    }
	   
    public static String utf8decode(String src){
    	String from = "ISO8859_1";
    	String to = "UTF-8";
    	return decode(src,from,to);
    }
}