package com.acl.qa.taf.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.GrayFilter;

import com.acl.qa.taf.helper.superhelper.InitializeTerminateHelper;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageDecoder;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageCompare extends InitializeTerminateHelper{
	// borrow original code from http://mindmeat.blogspot.com/2008/07/java-image-comparison.html, and update for our use
	
	// parameters might need tune-up begin
	private static  int comparex = 5;	// The number of vertical columns in the comparison grid.
	private static  int comparey = 5;	// The number of horizontal rows in the comparison grid.
	private static  int factorA = 2;	// The threshold value: If the difference in brightness exceeds this then the region is considered different.
	                                    
	private static  int factorD = 10;	// The stabilization factor.

	// parameters might need tune-up end

	private static final int debugMode = 0; // 0: show only exception message; 1: textual indication of change; 2: difference of factors

	private static boolean match = false;
	private static boolean average = true;

	private static BufferedImage img1;
	private static BufferedImage img2;
	private static BufferedImage imgc;

//	public static boolean isSimilarImage(String file1, String file2,boolean small){
//		comparey =3;
//		comparex =3;
//		factorA = 1;
//		factorD = 5;
//		return isSimilarImage(file1, file2);
//	}
	
	public static boolean isSimilarImage(String file1, String file2) {
		String imgDiffFileName = file2+"_diff.jpeg";
		return isSimilarImage(file1,file2,imgDiffFileName);
	}
	public static boolean isSimilarImage(String file1, String file2,String imgDiffFileName) {
		ImageCompare ic = new ImageCompare(file1, file2);
        
		try{
		    ic.compare();
		}catch(Exception e){
			System.out.println("Exception thrown when compare images '"+e.toString()+"'");
		}

		//if (debugMode >= 1) {
			//System.out.println("Match == " + match);
			if(! match) {
				
				logTAFError("Not Match: check image difference in file [" + imgDiffFileName + "]");
				saveJPG(imgc, imgDiffFileName);
			}
	//	}

		return match;
	}

	// compare the two images in this object.
	private boolean isSameSize(){
		int height = img1.getWidth();
		int width = img1.getWidth();
		int px = 20,py = 20;
		
		comparex = height/px;
		comparey = width/py;
		factorA = 25;
		factorD = 10;
		average = false;
		
		if(img1.getWidth()!=img2.getWidth()
				||img1.getHeight()!=img2.getHeight()){
			logTAFError(autoIssue+"Images are in different size?");
		    return false;
		}
		return true;
	}
	private void compare() {
		// setup change display image
		
		if(!isSameSize()){
			match = false;
			return;
		}
	

		imgc = imageToBufferedImage(img2);
		Graphics2D gc = imgc.createGraphics();
		gc.setColor(Color.RED);

		// convert to gray images.
		img1 = imageToBufferedImage(GrayFilter.createDisabledImage(img1));
		img2 = imageToBufferedImage(GrayFilter.createDisabledImage(img2));

		// how big are each section
		int blocksx = img1.getWidth() / comparex;
		int blocksy = img1.getHeight() / comparey;

		// set to a match by default, if a change is found then flag non-match
		match = true;

		// loop through whole image and compare individual blocks of images
		for (int y = 1; y < comparey-1; y++) {
			if (debugMode > 0) System.out.print("|");

			for (int x = 1; x < comparex-1; x++) {
				int b1 = getAverageBrightness(img1.getSubimage(x*blocksx, y*blocksy, blocksx - 1, blocksy - 1));
				int b2 = getAverageBrightness(img2.getSubimage(x*blocksx, y*blocksy, blocksx - 1, blocksy - 1));

				int diff = Math.abs(b1 - b2);
				if (diff > factorA) { 
					// the difference in a certain region has passed the threshold value of factorA
					// draw an indicator on the change image to show where change was detected.
					gc.drawRect(x*blocksx, y*blocksy, blocksx - 1, blocksy - 1);

					match = false;
				}
			}
		}
	}

	// returns a value specifying some kind of average brightness in the image.
	private int getAverageBrightness(BufferedImage img) {
		Raster r = img.getData();
       
		int total = 0;
		for (int y = 0; y < r.getHeight(); y++) {
			for (int x = 0; x < r.getWidth(); x++) {
				try{
				    total += r.getSample(r.getMinX() + x, r.getMinY() + y, 0);
				}catch(Exception e){
					logTAFInfo("Warning: "+e.toString());
				}
			}
		}
		if(average)
           return total / ((r.getWidth()/factorD)*(r.getHeight()/factorD));
		return total;
	}

	// constructor 1. use filenames
	private ImageCompare(String file1, String file2) {
		this(loadJPG(file1), loadJPG(file2));
	}

	// constructor 2. use awt images.
	private ImageCompare(Image _img1, Image _img2) {
		img1 = imageToBufferedImage(_img1);
		img2 = imageToBufferedImage(_img2);
	}

	// buffered images are just better. 
	private static BufferedImage imageToBufferedImage(Image img) {
		
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(img, null, null);
		return bi;
	}

	// write a buffered image to a jpeg file.
	@SuppressWarnings("restriction")
	private static void saveJPG(Image img, String filename) {
		BufferedImage bi = imageToBufferedImage(img);
		FileOutputStream out = null;
		File file = new File(filename);
		if(file.exists()){
			file.delete();
		}
		try { 
			out = new FileOutputStream(filename);
		} catch (java.io.FileNotFoundException io) { 
			System.out.println("File Not Found"); 
		}
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
		param.setQuality(0.8f, false);
		encoder.setJPEGEncodeParam(param);
		try { 
			encoder.encode(bi); 
			out.close(); 
		} catch (java.io.IOException io) {
			System.out.println("IOException"); 
		}
	}

	// read a jpeg file into a buffered image
	@SuppressWarnings("restriction")
	private static Image loadJPG(String filename) {
		FileInputStream in = null;
		try { 
			in = new FileInputStream(FileUtil.getAbsDir(filename));
		} catch (java.io.FileNotFoundException io) { 
			System.out.println("File Not Found"); 
		}

		JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(in);

		BufferedImage bi = null;
		try { 
			bi = decoder.decodeAsBufferedImage(); 
			in.close(); 
		} catch (java.io.IOException io) {
			System.out.println("IOException");
		}

		return bi;
	}	
}
