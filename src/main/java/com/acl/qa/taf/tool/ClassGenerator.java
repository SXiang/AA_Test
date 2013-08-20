package com.acl.qa.taf.tool;

import java.util.Vector;

import com.acl.qa.taf.helper.superhelper.ACLQATestScript;




/**
 * Use to produce all the getter methods for the object map in a script. <p>
 * 
 * This class creates methods that return widget classes. 
 * If you want to return something else, extend this class and override getWClassName.<p>
 * 
 * To use:<p>
 * 1) create new scripts in appobjects, populate their object maps and add every object in the map to the script.
 * 2) create a test script and instantiate a new java.util.Vector v.<p>
 * 3) for every appobject script (e.g. SearchPage) add a new object of that type to the vector (e.g. v.add(new SearchPage()))<p>
 * 4) call new ClassGenerator().updateScripts(v); <p>
 * 
 * 
 * @author tmgrigsb
 * @version 2.0
 * LastModified 04/06/04 by tsnow based on 03/01/04 by Kathy Endres based on 01/22/04 by tsnow based on 12/05/03 by agroves, based on 11/12/03 version by tmgrigsb
 */
public class ClassGenerator 
{
	/** This sets the ClassGenerator to create methods that will search for an object dynamically if it can't be found through the map.<p> 
	 * By default, this is set to false.<p>
	 */
	public static boolean findMisplacedObjects = false;
	
	/**
	 * Use to produce all the getter methods for the object map in a script. <p><p>
	 * 
	 * To use:<p>
	 *
	 * 1) create a test script and instantiate a new java.util.Vector v.<p>
	 * 2) for every appObject script (i.e. SearchPage) add a new object of that type to the vector (i.e. v.add(new SearchPage()))<p>
	 * 3) call new ClassGenerator.updateScripts(v); <p>
	 * 
	 * @param testScripts - a vector including the test scripts for which you wish to create methods
	 */
	public void updateScripts(Vector testScripts)
	{
		for(int i = 0; i < testScripts.size(); i++)
		{
			if(testScripts.elementAt(i) instanceof ACLQATestScript)
			{
				ACLQATestScript ts = (ACLQATestScript)testScripts.elementAt(i);
				String newContents = generateClass(ts);
				//updateFile(ts, newContents);
			}
				
		}	
	}

	public String generateClass(ACLQATestScript ts)
	{
//		IScriptDefinition sd = ts.getScriptDefinition();
//		Enumeration<?> e = sd.getTestObjectNames();
//		Vector v = new Vector();
//		String className = getClassName(ts.getClass().getName());
//		String packageName = getPackageName(ts.getClass().getName());
//		
//		// Diagnostic: if a fix is required, this tells us which object map to look in:
//		System.out.println("Processing " + className + ". . . .");
//		
//		String header = "package " + packageName + ";\n\n"
//			+ "import resources." + packageName + "." + className + "Helper;\n"
//			+ getImports();
//	
//		String footer = "\t// --------------------------------------------------------------------------\n\n"
//			+ "\tpublic void testMain (Object[] args) \n"
//			+	"\t{\n"
//			+	"\t\t // Unit testing can go here\n"
//			+	"\t\t // (will be deleted next time ClasGenerator is run)\n"
//			+	"\t}\n"
//			+	"}\n";		
//				
//		String classDec = "public class " + className + " extends " + className + "Helper {\n\n";
//			
//		while(e.hasMoreElements())
//		{
//			String currentObjName = (String)e.nextElement();
//			String role = sd.getRole(currentObjName);
//			String mapID = sd.getMapId(currentObjName);
//			String curClassName = (String)ts.getMap().find(mapID).getClassName();
//			String objClassName = (String)ts.getMap().find(mapID).getTestObjectClassName();
//			String methodText = generateMethod(currentObjName, objClassName, curClassName, role,  1);
//			classDec += methodText + "\n";
//		}
//				
		return "";//header+classDec+footer;
		
	}
	

//	public void updateFile(ACLQATestScript ts, String newFileContents)
//	{
//		File scriptFile = ts.getScriptDefinition().getScriptFile();
//		try
//		{
//			FileWriter writer = new FileWriter(scriptFile);
//			writer.write(newFileContents);
//			writer.flush();
//			writer.close();
//		}
//		catch(Exception e)
//		{
//			System.out.println(e.toString());
//			System.out.println("Unable to update " + ts.getClass());
//			return;
//		}
//	}

	public String generateMethod(String objName, String objClassName, String className, String role, int numTabs)		
	{
		String tabs = getTabs(numTabs);		
		String wClassName = getWClassName(objClassName, className, role);
		boolean dynamicSearchIfNotFound = false;
		
		// some objects with certain roles can't be found dynamically (don't tend to have good search criteria)
		// so we need to exclude them here from dynamicSearchIfNotFound
	    if (findMisplacedObjects && (( ! role.equals("Table")) && ( ! role.equals("Document")) && ( ! role.equals("Browser")) && ( ! role.equals("Dialog")))) {
	    	dynamicSearchIfNotFound = true;
	    }

		if(wClassName.equals(""))
		{
			return "";
		}
		String capitalizedObjName = objName.substring(0,1).toUpperCase() + objName.substring(1);
		
		// If findMisplacedObjects is set to true:
		// We're enabling caller to pass in false to prohibit recursion. Useful in waitForExistence( ),
		// for instance, where we take an unnecessary performance hit.
		// If caller passes no argument, defaults to true (recursion).
		String methodText = tabs + "public " + wClassName + " get" + capitalizedObjName +"(";
		if (dynamicSearchIfNotFound) {
			methodText += "boolean dynamicSearchIfNotFound";
		}
		
		//We added ANY, NO_STATE to anchor the object when it is created because the Java folks were getting ObjectNotFoundErrors sometimes.
		//This anchor solved their problem and seemed to present no problems for the Html folks, so we put it in as the default. -tbs
		//ANY is a constant in ACLQATestScript, and NO_STATE is a constant in the ACLQATestScriptConstants interface that just mirrors ScriptCommandFlags.NO_FLAGS.
		methodText += ")" + " {\n"
		    + tabs + "\t" + "TestObject to = " + objName + "(ANY, NO_STATE);\n";
		    
	    if (dynamicSearchIfNotFound)
	    {
		     methodText += tabs + "\t" + "if (dynamicSearchIfNotFound && ( ! to.exists())) {\n"
		     + tabs + "\t" + "\t" + "to = ObjectFactory.findMisplacedTestObject(to, this);\n"
		     + tabs + "\t" + "\t" + "if (to == null)\n"
		     + tabs + "\t" + "\t" + "\t" + "return new " + wClassName + "(" + objName + "(ANY, NO_STATE));\n"
		     + tabs + "\t" + "}\n";	     
	    }
	    methodText += tabs + "\t" + "return new " + wClassName + "(to);\n"
	    	+ tabs + "}\n";
	    	
	   	if (dynamicSearchIfNotFound) {
	   		methodText += "\n" + tabs + "public " + wClassName + " get" + capitalizedObjName +"(ANY, NO_STATE)"
    		+ " {\n"
		    + tabs + "\t" + "return this.get" + capitalizedObjName + "(true);\n"
	    	+ tabs + "}\n";
	   	}
				
		return methodText;
	}
	
	/**
	 * This method finds the correct widget to return depending on the role or class of the object in the map.<p>
	 * If you extend this class, you probably will want to override this method.*/
	/* We are going to need to add a lot to this method as we come across new stuff */
	
	protected String getWClassName(String objClassName, String className, String role)
	{
		String name = getSWTWClassName(objClassName, className, role);
		if (name != null) // if swt, return the correct swt widget
		{
			return name;
		}
		
		//else, check the roles:
		if(role.equals("Button"))
		{
		  	return "WButton";
		}
		else if(role.equals("CheckBox"))
		{
		  	return "WCheckBox";
		}
		else if(role.equals("Image"))
		{
		 	return "WImage";
		}
		else if(role.equals("Link"))
		{
			return "WLink";
		}
		else if(role.equals("List"))
		{
		 	return "WListBox";
		}
		else if(role.equals("ComboBox"))
		{
		 	return "WListBox";
		}
		else if(role.equals("RadioButton"))
		{
		 	return "WRadioButton";
		}
		else if(role.equals("Table"))
		{
		 	return "WTable";
		}
		else if(role.equals("Text"))
		{
		 	return "WTextField";
		}  
		else if(role.equals("Document"))			
		{
		 	return "WTextField";
		}  
		else if(role.equals("Form"))				
		{
		 	return "GuiTestObject";
		}  
		else if(role.equals("Browser"))		
		{
		 	return "WBrowser";
		}  
		else if(role.equals("Frame"))
		{
			return "WFrame";
		}
		else if(role.equals("Dialog"))			
		{
			if(className.equals("Html.DialogButton"))	
			{
		 		return "WButton";
			}			
			else if(className.equals("Html.DialogStatic"))				
			{
				return "WStaticText";
			}
			else if(className.equals("Html.DialogEdit"))				
			{
				return "WTextField";
			}
			else			
			{
				System.out.println("Role/Class: " + role + "/" + className +" not handled in ClassGenerator... please submit an RFE");
				return "GuiTestObject";
			}
		}
		else if(role.equals("Html"))
		{
			if(className.equals("Html.SPAN"))
			{
		 		return "WStaticText";
			}
			else if(className.equals("Html.INPUT.password"))
			{
				return "WTextField";
			}
			else if(className.equals("Html.LABEL"))
			{
				return "WStaticText";
			}
			else if(className.equals("Html.DIV"))
			{
				return "WStaticText";
			}
			else if(className.equals("Html.LEGEND"))				
			{
				return "WStaticText";
			}
			else if(className.equals("Html.Dialog"))				
			{
				return "TopLevelTestObject";
			}
			else if(className.equals("Html.DialogButton"))				
			{
				return "WButton";
			}
			else if(className.equals("Html.INPUT.file"))				
			{
				return "GuiTestObject";
			}
			else			
			{
				System.out.println("Role/Class: " + role + "/" + className +" not handled in ClassGenerator... please submit an RFE");
				return "GuiTestObject";
			}		
		}
		else //no matches of the role, so return either an ancestor class or a Rational object class that provides the most specific methods that we can pass back for the object			
		{
			System.out.println("Warning: the role " + role +" is not handled in ClassGenerator... please submit an RFE if needed");				
			if (objClassName.equals("GuiTestObject"))
				return "Widget";
			else if (objClassName.equals("TopLevelTestObject"))
				return "TopLevelWidget";
			else if (objClassName.equals("ToggleGUITestObject"))
				return "ToggleWidget";
			else if (objClassName.equals("GuiSubitemTestObject"))
				return "SubitemWidget";
			else if (objClassName.equals("StatelessGuiSubitemTestObject"))
				return "StatelessWidget";				
			else 
				return objClassName;			
		}	
			
	}
	
	protected String getSWTWClassName(String objClassName, String className, String role)
	{
		if(className.indexOf("swt") >= 0)
		{
			if(role.equals("ComboBox")  && className.equals("org.eclipse.swt.widgets.Combo"))
			{
			 	return "SWTComboBox";
			}
			else if(role.equals("Text")  && className.equals("org.eclipse.swt.widgets.Text"))
			{
			    return "SWTText";
			}  		
			else if(role.equals("Label")  && className.indexOf("org.eclipse.swt") >= 0)
			{
				return "SWTLabel";
			}
			else if(role.equals("PageTabList")  && 
				(className.equals("org.eclipse.swt.custom.CTabFolder") ||
				 className.equals("org.eclipse.swt.widgets.TabFolder")))
										
			{
				return "SWTCTabFolder";
			}
			else if(role.equals("Table")  && className.equals("org.eclipse.swt.widgets.Table"))
			{
				return "SWTTable";
			}
			else if(role.equals("TableTree")  && className.equals("org.eclipse.swt.custom.TableTree"))
			{
				return "SWTTableTree";
			}	
			else if(role.equals("Tree")  && className.equals("org.eclipse.swt.widgets.Tree"))
			{
				return "SWTTree";
			}					
			else if(role.equals("ToolBar") && className.equals("org.eclipse.swt.widgets.CoolBar"))
			{
				return "SWTCoolBar";
			}
			else if(role.equals("ToolBar") && className.equals("org.eclipse.swt.widgets.ToolBar"))
			{
				return "SWTToolBar";
			}
			else if(role.equals("Button") && className.equals("org.eclipse.swt.widgets.ToolItem"))
			{
				return "SWTToolItemButton";
			}
			else if(role.equals("CheckBox") && className.equals("org.eclipse.swt.widgets.ToolItem"))
			{
				return "SWTToolItemCheckBox";
			}
			else if(role.equals("MenuBar") && className.equals("org.eclipse.swt.widgets.Menu"))
			{
				return "SWTMenu";
			}
			else if(role.equals("MenuItem") && className.equals("org.eclipse.swt.widgets.MenuItem"))
			{
				return "SWTMenuItem";
			}
			else if(role.equals("PopupMenu") && className.equals("Java.PopupMenu"))
			{
				return "SubitemWidget";
			}
			else if(role.equals("Frame") && className.equals("org.eclipse.swt.widgets.Shell"))
			{
				//@tbs: Changed to return more specific widget
				//return "TopLevelWidget";
				return "WFrame";
			}
			else
				return null;
		}
		else
			return null;			
	}
		
	protected String getPackageName(String fullClassName)
	{
		int start = 0;
		int end = fullClassName.lastIndexOf('.');
		return fullClassName.substring(start, end);
	}
	protected String getClassName(String fullClassName)
	{
		int start = fullClassName.lastIndexOf('.') + 1;
		return fullClassName.substring(start);
	}
	protected String getTabs(int numTabs)
	{
		String ret = "";
		for(int i = 0; i < numTabs; i++)
		{
			ret += "\t";	
		}
		return ret;
	}
	
}