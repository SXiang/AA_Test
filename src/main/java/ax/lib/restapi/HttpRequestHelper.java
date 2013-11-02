/**
 * 
 */
package ax.lib.restapi;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
 
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.acl.qa.taf.util.UTF8Control;
 
/**
 * Script Name   : <b>HttpRequest</b>
 * Generated     : <b>8:59:36 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Oct 29, 2013
 * @author steven_xiang
 * 
 */
public class HttpRequestHelper extends RestapiHelper{

	/**
	 * 
	 */
	   String cookieSid = "JSESSIONID=5577D99D5041A3184589691D5AA9C219";
	   String cookieCas = "CASTGC=TGT-9-cZpGCn4oDqaFacpOtUrmpTPQBMBE3bH5nTaPoLJie13taKVFtQ-cas";
       String encoding = "UTF-8";
       
	   public String sendApiRequest(WebDriver driver,String url,String jsonInput){
		   
			try{
				// Update cookie
				
				Cookie jsessionCookie = driver.manage().getCookieNamed("JSESSIONID");
				cookieSid = jsessionCookie.getName()+"="+jsessionCookie.getValue();
				
				
//				Cookie jc = driver.manage().getCookieNamed("CASTGC");
//				cookieCas = jc.getName()+"="+jc.getValue();
				
				for(Cookie ck:driver.manage().getCookies()){
					logTAFInfo(ck.getName()+"="+ck.getValue());
				}
				
				//url = url+";"+cookie;
			}catch(Exception e){
				logTAFInfo("Warning: Cookie not found "+e.toString());
				
			}
			
		   String result = "";
		   try{
			if(jsonInput.equals("")){
				result = doGet(url);
			}else{
//				result = doPost(url.replaceFirst("/data",""),"");
//				logTAFInfo("Debug: "+result);
				result = doPost(url,jsonInput);
//				logTAFInfo("Debug: "+result);
			}
		   }catch(Exception e){
			   logTAFException(e);
		   }
			return result;
	   }
		// HTTP GET request
		public String doGet(String url,String query) throws Exception {
			if(!query.isEmpty())
				url=url+"?"+query;
			return doGet(url);
		}
		public String doGet(String url) throws Exception {	 			 
			HttpURLConnection con = getHttpURLConnection(url);
		     con.setRequestMethod("GET");
			 setRequestProperties(con);		
				//Test ...
	          //logTAFStep("Sending 'Get' request to URL : " + url);

            return getHttpResponse(con);
		}
	 
		// HTTP POST request
		public String doPost(String url,String query) throws Exception {			
			HttpURLConnection con = getHttpURLConnection(url);
			
			
			con.setUseCaches(false);
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setAllowUserInteraction(true);
			con.setRequestMethod("POST");	
			setRequestProperties(con);	
//			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//			wr.writeBytes(query);
			con.connect();
			OutputStream wr = con.getOutputStream();
			wr.write(query.getBytes());
			
			wr.flush();
            wr.close();
            con.connect();
			//Test ...
			logTAFStep("Sending 'POST' request to URL : " + url +" with JSON body '"+query+"'");
			           //System.out.println("Post parameters : " + query);

	        return getHttpResponse(con);
	 
		}
		
		private String getHttpResponse(HttpURLConnection con){
			String line;
			StringBuffer response = new StringBuffer();
			String jsonErr = "500|401|400";
			responseCode = 0;
			try{
				// Test.... 
			    //  logTAFInfo("Request Method before:" + con.getRequestMethod());
				responseCode = con.getResponseCode();			
				// Test.... 
			    //  logTAFInfo("Response Code:" + responseCode);
			    //  logTAFInfo("Request Method after:" + con.getRequestMethod());
				InputStream is;          
				if (responseCode != 200) {
					is = con.getErrorStream();
				} else {
					is = con.getInputStream();
				}
				BufferedReader br = new BufferedReader(
						new InputStreamReader(is,encoding));

				while ((line = br.readLine()) != null) {
					response.append(line);
				}
				br.close();
				if (responseCode != 200) {
				   if(jsonErr.contains(""+responseCode)){
					    response.append(" - Response Code:"+responseCode);
				    }else if(responseCode < 500){
					    logTAFError(autoIssue+"Response Code:" + responseCode);
				    }else{
				    	 logTAFError("Response Code:" + responseCode);
				    }
				}
				//Test ...
				//System.out.println(response.toString());

				con.disconnect();
			}catch(Exception e){
				//
			}
			return response.toString();

		}
    public boolean isJsonText(String text){
    	boolean isJson = false;
    	    try {
    	        new JSONObject(text);
    	        isJson = true;
    	    }catch(JSONException ex) { 
    	        isJson = false;
    	    }catch(Exception e){ //incase...
    	        try{
    	             isJson = text.matches("^\\s*[\\[\\{.+");
    	        }catch(Exception other){
    		       //
    	        }
    	    }
    	return isJson;
    }
	public void setRequestProperties(HttpURLConnection con){
        //Optional header ...  - Steven 
		String http_agent = "Mozilla/5.0";
		String http_lang = "en-US,en;q=0.5";
		String http_content = "application/json;charset=utf-8";
		String http_accept = "application/json";
		int conTimeout = 10000;
		int readTimeout = 10000;
		
		//set timeout
		con.setConnectTimeout(conTimeout);
		con.setReadTimeout(readTimeout);

		//add request header
		//logTAFInfo("Current SID in use '"+cookieSid+"'");
		con.setRequestProperty("Cookie", cookieSid);
        con.setRequestProperty("User-Agent", http_agent);
		//con.setRequestProperty("Content-Language", http_lang);
		con.setRequestProperty("Content-Type", http_content);
		con.setRequestProperty("Accept", http_accept);
		
	}
	
    public HttpURLConnection getHttpURLConnection(String url){
		HttpURLConnection con = null;
		try{
			URL obj = new URL(url);
			if(url.toLowerCase().startsWith("https")){
		      con = (HttpsURLConnection) obj.openConnection();
			}else{
			  con = (HttpURLConnection) obj.openConnection();
			}
		}catch(Exception e){
			//System.out.println(e.toString());
			//return e.toString();
		}	
		return con;
    }
	//*************   Constructor *************
	public void disableCertificateValidation() {
		  // Create a trust manager that does not validate certificate chains
		  TrustManager[] trustAllCerts = new TrustManager[] { 
		    new X509TrustManager() {
		      public X509Certificate[] getAcceptedIssuers() { 
		        return new X509Certificate[0]; 
		      }
		      public void checkClientTrusted(X509Certificate[] certs, String authType) {}
		      public void checkServerTrusted(X509Certificate[] certs, String authType) {}
		  }};

		  // Ignore differences between given hostname and certificate hostname
		  HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				// TODO Auto-generated method stub
				return true;
			}
		  };

		  // Install the all-trusting trust manager
		  try {
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, trustAllCerts, new SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		    HttpsURLConnection.setDefaultHostnameVerifier(hv);
		  } catch (Exception e) {}
		}
	public HttpRequestHelper() {
		disableCertificateValidation();
	}

	 //**********  Debug main ***************************
		public static void main(String[] args) {

			HttpRequestHelper http = new HttpRequestHelper();
			String url = "https://win2012-64-ramn.aclqa.local:8443/aclax/api/tables/ba96cf2b-3c23-4280-b23a-a8f0f3dc6082";
			String query ="";	
			String result;
			query="";
			System.out.println("Testing 1 - Send Http GET request");
			try {
				result = http.doGet(url,query);
				System.out.println(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
			url = "https://win2012-64-ramn.aclqa.local:8443/aclax/api/tables/ba96cf2b-3c23-4280-b23a-a8f0f3dc6082/data";
			query = "{}";
			System.out.println("\nTesting 2 - Send Http POST request");
			try {
				result = http.doPost(url,query);
				System.out.println(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
		}
	 
}
