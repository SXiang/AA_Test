package com.acl.qa.taf.helper.superhelper;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import com.acl.qa.taf.util.FileUtil;
import com.acl.qa.taf.util.ImageCompare;
import com.acl.qa.taf.util.NLSUtil;






























/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  February 03, 2012
 */
public abstract class WinTreeHelper extends PrintObjectInfoHelper
{
   	// ********  Deal with RFT Tree **************************************************
	// expand the curNode (i.e. GuiSubitemTestObject )

    public String SEP_REP_PATH = "->";
    public Point actionPoint = new Point(-30,10),
                  textPoint = new Point(5,10),
                  iconPoint = new Point (-15,10);
   
    public boolean  collapsible = true; 
 
	//maneError method is used to report invalid input
	public boolean nameError(String name){
		boolean invalid = false;
		char[] invalidChar = {'/','\\',':','*','?','<','>','|'};

		for (char ichar:invalidChar){
			if(name.indexOf(ichar)!=-1){
				invalid = true;
				break;
			}
		}
		return invalid;
	}


	protected static String getDateStrByDate(java.util.Date myDate, String dateFormat){
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);

		return format.format(myDate);
	}

	// return month number (start from 1, i.e. January==1, December==12) by full month name
	protected int getMonthNum(String fullMonthName) {	
		Calendar cal = Calendar.getInstance();

		int curMonth = cal.get(Calendar.MONTH);

		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
		int i=0;
		for (; i<12; i++) {
			cal.set(Calendar.MONTH, i);

			if (fullMonthName.equalsIgnoreCase(sdf.format(cal.getTime()))) { 
				break;
			}
		}

		cal.set(Calendar.MONTH, curMonth);

		return (i < 12)? (i+1) : null;
	}

	// return full month name by month number which is start from 1, i.e. January==1, December==12
	protected String getFullMonthName(int monthNum) {		
		Calendar cal = Calendar.getInstance();

		int curMonth = cal.get(Calendar.MONTH);

		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
		cal.set(Calendar.MONTH, monthNum - 1);
		String monthStr = sdf.format(cal.getTime());

		cal.set(Calendar.MONTH, curMonth);

		return monthStr;
	}


}
