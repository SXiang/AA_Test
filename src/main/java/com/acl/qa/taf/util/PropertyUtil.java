package com.acl.qa.taf.util;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.Properties;

import com.acl.qa.taf.helper.superhelper.InitializeTerminateHelper;
import com.acl.qa.taf.helper.superhelper.LoggerHelper;


public class PropertyUtil {
		
	public static void setProperties (Object target, Properties prop) {
		Enumeration e = prop.propertyNames();
		String name,fi="",fiv="",fiexp = "imageName" ;
		String value;
		String f1 = "unicodeTest";
		value = prop.getProperty(f1);
		if(value!=null)
		   setProperty(target, f1, value.trim());
		while (e.hasMoreElements()) {
			name = ((String) e.nextElement()).trim();
			value = prop.getProperty(name).trim();
			//System.out.println("PropertySupport> set " + name + "=" + value);
			if(!name.equalsIgnoreCase(fiexp))
			   setProperty(target, name, value);
			else{
				fi = name;
				fiv = value;
			}
		}

		if(!fi.equals("")){
			setProperty(target, fi, fiv); //Finalisation
			//System.out.println("Server environment has been set");
		}
	}
	
    public static boolean setProperty(Object target, String name, Object value) {
    	
    	//**** Get value from System Property
    	String sysPropValue = "";
    	if(value==null){
    		value = "";
    	}
    	try{
    		sysPropValue = LoggerHelper.getSystemProperty(InitializeTerminateHelper.sysPropPrefix+name,value.toString().trim());
    	    // sysPropValue = System.getProperty(InitializeTerminateHelper.sysPropPrefix+name);
    		//System.out.println(InitializeTerminateHelper.sysPropPrefix+name+" = '"+sysPropValue+"'");
    	}catch(Exception e){
    		
    	}   	
//    	if(sysPropValue!=null&&!sysPropValue.equals("")){
//    		value = sysPropValue;
//    	}
    	
    	//**** Start to set value 
        try {
        	Class clazz = target.getClass();
            Method setter = findSetterMethod(clazz, name);
            
            if( setter == null ){
            	System.out.println("\t No setter found for '"+name+"'");
                return false;
            }
            
            // If the type is null or it matches the needed type, just use the value directly
            if( value == null || value.getClass() == setter.getParameterTypes()[0] ) {
                try{
            	     setter.invoke(target, new Object[]{value});
                }catch(Exception e){
                	//e.printStackTrace();
                }
            } else {
                // We need to convert it
            	try{
                  setter.invoke(target, new Object[]{ convert(value, setter.getParameterTypes()[0]) });
            	}catch(Exception e){
            		e.printStackTrace();
            	}
            }
            
            return true;
        } catch (Throwable ignore) {
            return false;
        }
    }

    private static Object convert(Object value, Class type) throws URISyntaxException {
        PropertyEditor editor = PropertyEditorManager.findEditor(type);
        
        if( editor != null ) { 
            editor.setAsText(value.toString());
            return editor.getValue();
        }
        
        if( type == URI.class ) {
            return new URI(value.toString());
        }
        
        return null;
    }

    private static Method findSetterMethod(Class clazz, String name) {
        // Build the method name.
        name = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        Method[] methods = clazz.getMethods();
        
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            Class params[] = method.getParameterTypes();
            if( method.getName().equals(name) 
                    && params.length==1
                    && isSettableType(params[0])) {
                return method;
            }
        }
        return null;
    }

    private static boolean isSettableType(Class clazz) {
        if( PropertyEditorManager.findEditor(clazz)!=null )
            return true;
        
        if( clazz == URI.class )
            return true;
        
        if( clazz == Boolean.class )
            return true;
        
        return false;
    }

}
