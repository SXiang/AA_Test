package lib.acl.tool.wikiConvert;

import resources.lib.acl.tool.wikiConvert.TreeManagerHelper;
import com.rational.test.ft.*;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.WPF.*;
import com.rational.test.ft.object.interfaces.dojo.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.object.interfaces.generichtmlsubdomain.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.*;
import com.rational.test.ft.vp.*;
import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;

import com.rational.test.ft.object.interfaces.GuiSubitemTestObject;
import com.rational.test.ft.vp.ITestDataText;
import com.rational.test.ft.vp.ITestDataTree;
import com.rational.test.ft.vp.ITestDataTreeNode;
import com.rational.test.ft.vp.impl.ObjectReference;
import com.rational.test.util.regex.Regex;

public class TreeManager extends TreeManagerHelper
{
	public void testMain(Object[] args)
	{
		try
		{
			System.out.println(tree2().getParent().getTestDataTypes());
			//System.out.println(getPathForFirstNodeWithRegex(".Oracle", (GuiSubitemTestObject) tree2()));
			printTreeNavigationPaths(tree2());
			//clickNodeWithPath((GuiSubitemTestObject) tree2().getParent(), "Workbook_SERVER.ACL->Backup->Trans_DB2");
			//clickNodeMatchingRegex(tree2(), "Piano");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Returns the navigation string for the first node in the tree to
	 * match the regec
	 * @param sPattern –the mattern to match the node on
	 * @param targetTree –the tree to get the details from
	 * @return The first navigation string in the tree that matches the pattern
	 * @throws Exception
	 */
	public static String getPathForFirstNodeWithRegex(String sPattern, GuiSubitemTestObject targetTree) throws Exception
	{
		//create the regex object that will do the matching for us
		Regex rPattern = new Regex(sPattern);

		//create a string array to hold all the navigation strings
		String myPaths[] = arrayOfNavigationPaths(targetTree);

		//loop through each navigation string, starting from the top of the
		//tree, and working to the bottom of the tree
		for(int i = 0; i < myPaths.length; i++)
		{
			//if the regex matches the current node…
			if(rPattern.matches(myPaths[i]))
			{
				//return the navigation string for that node
				return myPaths[i];
			}
		}

		//if we got here, nothing in the tree matched the regex. Return null
		return null;
	}

	/**
	 * Clicks on a node in the tree
	 * @param targetTree –the tree to select the node from
	 * @param sNodePath –the path of the node
	 * @throws Exception
	 */
	public static void clickNodeWithPath(GuiSubitemTestObject targetTree, String sNodePath) throws Exception
	{
		targetTree.click(atPath(sNodePath));
	}

	/**
	 * Click on the first node that matches a regular expression
	 * @param targetTree –the tree to select the node from
	 * @param sRegex –the regex to identify the node with
	 * @throws Exception
	 */
	public static void clickNodeMatchingRegex(GuiSubitemTestObject targetTree, String sRegex) throws Exception
	{
		targetTree.click(atPath(getPathForFirstNodeWithRegex(sRegex, targetTree)));
	}

	/**
	 * Prints the tree navigation details of all nodes in a tree to the console
	 * @param targetTree –the tree to get the details from
	 * @throws Exception
	 */
	public static void printTreeNavigationPaths(GuiSubitemTestObject targetTree) throws Exception
	{
		//get the nodes from the tree
		System.out.println("'"+targetTree.getTestData("text").getName()+"'");
		System.out.println("'"+((ITestDataText) targetTree.getTestData("text")).getText()+"'");
		System.out.println("'"+targetTree.getTestData("grid")+"'");
		System.out.println("'"+targetTree.getTestData("contents"+"'"));
		System.out.println("'"+targetTree.getTestData("list"+"'"));
		System.out.println("'"+targetTree.getTestData("tree"+"'"));
		//ITestDataTreeNode[] nodes = ((ITestDataTree)targetTree.getTestData("tree")).getTreeNodes().getRootNodes();

		//print the navigation strings for those nodes
		//System.out.println(TreeManager.getPathForAllNodes("", nodes, true));
	}

	/**
	 * Returns a string array where each element is a navigation string.
	 * The array is ordered in the same way that the tree is ordered
	 * @param targetTree –the tree to get the details from
	 * @return –String[]
	 * @throws Exception
	 */
	private static String[] arrayOfNavigationPaths(GuiSubitemTestObject targetTree) throws Exception
	{
		//set up array to hold details
		String sNavigationPaths[];

		//get the nodes from the tree
		ITestDataTreeNode[] nodes = ((ITestDataTree)targetTree.getTestData("tree")).getTreeNodes().getRootNodes();
        //ITestDataText texts = (ITestDataText) targetTree.getTestData("text");
        
		//return a string array, based on splitting the results
		//string on the newline character
		return TreeManager.getPathForAllNodes("", nodes, true).split("\n");
	}

	/**
	 * Iterates through all nodes and figures out the tree navigation path for
	 * each node.
	 * @param sPrefix –the current location in the tree
	 * @param nNodes –the nodes to iterate through (generally, this is just the
	 * root node of the tree, although beginning halfway up the tree is possible)
	 * @param isRootNode –pass in "true" if the nNodes being passed in is the
	 * root node.
	 * @return — A string containing the navigation path for each node, separated
	 * by return characters
	 */
	private static String getPathForAllNodes(String sPrefix, ITestDataTreeNode[] nNodes, boolean isRootNode)
	{
		//if there are no nodes, return
		if(nNodes == null)
		{
			return "";
		}

		//prepare a blank string to contain the result of interogating the node
		String sResult= "";

		//loop through each node
		for (int index = 0; index < nNodes.length; index++)
		{
			//get the text value displayed on the node
			ObjectReference or = (ObjectReference) nNodes[index].getNode();
			String sNodeText = or.getObject().toString();

			//decide whether to prefix the node with "->" or not
			String sNextLine;
			if(isRootNode)
			{
				//we’re at the root node, don’t prefix
				sNextLine = sNodeText;
			}
			else
			{
				/*
				 * we’re not at the root node, we’re at some child somewhere
				 * prefix the node name with the tree nav so far, and also
				 * the "->" string…
				 */
				sNextLine = sPrefix + "->" + sNodeText;
			}

			//add the navigation line to the results
			sResult = sResult + sNextLine + "\n";

			//get the child nodes of the current node
			ITestDataTreeNode[] nChildren = nNodes[index].getChildren();

			/*
			 * if the current node has children, recursively call this method,
			 * passing in the current tree nav path, the child nodes, and "false"
			 * so that we know that we’re not at the tree root any more
			 */
			if(nChildren != null)
			{
				sResult = sResult + getPathForAllNodes(sNextLine, nChildren, false);
			}
		}

		//we’ve finished iterating through the tree; bail!
		return sResult;
	}
}