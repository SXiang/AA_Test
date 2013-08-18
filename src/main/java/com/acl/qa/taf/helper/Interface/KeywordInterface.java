package com.acl.qa.taf.helper.Interface;

public interface KeywordInterface {

	// Methods to be implemented by all tasks 
	// and invoked from testMain in keywordSuperHelper
	
	boolean dataInitialization(); 
	public void onInitialize(Object[] args);
	public void testMain(Object[] args);
	//void exeCommands();
	
}
