/**
 * 
 */
package ax.keyword.restapi.request;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Script Name   : <b>RestAPIRequestTest.java</b>
 * Generated     : <b>1:19:31 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Nov 8, 2013
 * @author steven_xiang
 * 
 */

@RunWith(Parameterized.class)

public class RestAPIRequestTest {

	/**
	 * @throws java.lang.Exception
	 */
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	private String result;
	private String master;
	
	public RestAPIRequestTest (String textActual,String textMaster){
		result = textActual;
		master = textMaster;
		
	}
    @Rule
	public RestAPIRequest apiReq = new RestAPIRequest();
    @Parameters
    public static Collection<Object[]> data(){
    	 String result = "- Response Code:400{ \" error\":\" paramset.notfound\"}";
         String master = "{\"error\":\"paramset.notfound\"} - Response Code:400";
         String result2 = "- Response Code:400{ \" error\":\" paramset.notfound\"}";
         String master2 = "{\"error\":\"paramset.notfound\"} - Response Code:400";
    	Object[][] data = new Object[][]{
    			{result,master},
    			{result2,master2}
    	} ;
    	
    	return Arrays.asList(data);
    }
	@Test
	public void testCompareJsonResultUnequale()	{
        
        boolean done = false;
        try{
            done = apiReq.compareJsonResult(result,master);
        }catch(Exception e){
        	
        }
        assertFalse("Only [|]|},{ can be used as line seperater",done);
	}
	
	@Test
	public void testCompareJsonResultEqual()	{
		
        //String result = "{ \" error\":\" paramset.notfound\"},{ \" testing\":\" paramset.notfound\"} - Response Code :  400";
       // String master = "{ \" error\":\" paramset.notfound\"},{\"error\":\"paramset.notfound\"} - Response Code:400";
        
        boolean done = false;
        try{
            done = apiReq.compareJsonResult(result,master);
        }catch(Exception e){
        	
        }
        assertTrue("Spaceses and order should be ignored for JSON comparison",done);
 
	}
	@Ignore
	public void test() {
		fail("Not yet implemented");
	}

}
