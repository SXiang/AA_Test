/**
 * 
 */
package com.acl.qa.taf.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Script Name   : <b>MutilOutput.java</b>
 * Generated     : <b>8:38:58 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Aug 30, 2013
 * @author steven_xiang
 * 
 */
public class MultiOutputStream extends OutputStream
{
	OutputStream[] outputStreams;
	
	public MultiOutputStream(OutputStream... outputStreams)
	{
		this.outputStreams= outputStreams; 
	}
	
	@Override
	public void write(int b) throws IOException
	{
		for (OutputStream out: outputStreams)
			out.write(b);			
	}
	
	@Override
	public void write(byte[] b) throws IOException
	{
		for (OutputStream out: outputStreams)
			out.write(b);
	}
 
	@Override
	public void write(byte[] b, int off, int len) throws IOException
	{
		for (OutputStream out: outputStreams){
			//flush();
			out.write(b, off, len);
		}
	}
 
	@Override
	public void flush() throws IOException
	{
		for (OutputStream out: outputStreams)
			out.flush();
	}
 
	@Override
	public void close() throws IOException
	{
		for (OutputStream out: outputStreams)
			out.close();
	}
}