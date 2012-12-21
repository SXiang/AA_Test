package lib.acl.util;
import java.io.*;
import java.util.*;

public class UniPropertiesLoader {
       static String encoding = "UTF-8";
	   public static Properties loadProperties(InputStream is)
	   {
		   Properties pts = null;
	      try {
			pts = loadProperties(is, encoding);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pts;
	   }

	   public static Properties loadProperties(InputStream is, String encoding) throws IOException
	   {
	      StringBuilder sb = new StringBuilder();
	      InputStreamReader isr = new InputStreamReader(is, encoding);
	      while(true)
	      {
	         int temp = isr.read();
	         if(temp < 0)
	            break;

	         char c = (char) temp;
	         sb.append(c);
	      }

	      String inputString = escapifyStr(sb.toString());
	      byte[] bs = inputString.getBytes("ISO-8859-1");
	      ByteArrayInputStream bais = new ByteArrayInputStream(bs);

	      Properties ps = new Properties();
	      ps.load(bais);
	      return ps;
	   }
	      
	   private static char hexDigit(char ch, int offset)
	   {
	      int val = (ch >> offset) & 0xF;
	      if(val <= 9)
	         return (char) ('0' + val);
	      
	      return (char) ('A' + val - 10);
	   }

	   
	   private static String escapifyStr(String str)
	   {      
	      StringBuilder result = new StringBuilder();

	      int len = str.length();
	      for(int x = 0; x < len; x++)
	      {
	         char ch = str.charAt(x);
	         if(ch <= 0x007e)
	         {
	            result.append(ch);
	            continue;
	         }
	         
	         result.append('\\');
	         result.append('u');
	         result.append(hexDigit(ch, 12));
	         result.append(hexDigit(ch, 8));
	         result.append(hexDigit(ch, 4));
	         result.append(hexDigit(ch, 0));
	      }
	      return result.toString();
	   }
	}
