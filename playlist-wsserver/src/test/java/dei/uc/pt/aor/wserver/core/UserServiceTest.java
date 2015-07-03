package dei.uc.pt.aor.wserver.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	final String WSurl = "http://localhost:8080/playlist-wsserver/rest/users";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFalseUrl() {
	    String responseBody = "";
	    String output;
	    Boolean result = true;
	    Boolean expResult = false;
	 
	    try {
	    	URL url = new URL(WSurl+"/totalloggedusers1");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.TEXT_PLAIN);
	 
	        if (conn.getResponseCode() != 200) {
	            // if not 200 response code then fail test
	            result = false;
	        }
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));
	        while ((output = br.readLine()) != null) {
	            responseBody = output;
	        }
	        if (responseBody.length() < 1) {
	            // if response body is empty then fail test
	            result = false;
	        }
	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }

	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully false URL gets code != 200 ");

	}

	@Test
	public void testTotalUsers() {
	    String responseBody = "";
	    String output;
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/totalusers");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.TEXT_PLAIN);
	 
	        if (conn.getResponseCode() != 200) {
	            // if not 200 response code then fail test
	            result = false;
	        }
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));
	        while ((output = br.readLine()) != null) {
	            responseBody = output;
	        }
	        if (responseBody.length() < 1) {
	            // if response body is empty then fail test
	            result = false;
	        }
	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }
	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully web service getTotalUsers");

	}

	@Test
	public void testAllUsers() {
	    String responseBody = "";
	    String output;
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/allusers");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);	  
	 
	        if (conn.getResponseCode() != 200) {
	            // if not 200 response code then fail test
	            result = false;
	        }
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));
	        while ((output = br.readLine()) != null) {
	            responseBody = output;
	        }
	        
	        if (responseBody.length() < 1) {
	            // if response body is empty then fail test
	            result = false;
	        }

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }
	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully web service getAllUsers");

	}

	@Test
	public void testUserByEmail() {
	    String responseBody = "";
	    String output;
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/email/admin@admin.com");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);	  
	 
	        if (conn.getResponseCode() != 200) {
	            // if not 200 response code then fail test
	            result = false;
	        }
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));
	        while ((output = br.readLine()) != null) {
	            responseBody = output;
	        }
	        
	        if (responseBody.length() < 1) {
	            // if response body is empty then fail test
	            result = false;
	        }

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }
	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully web service getUserByEmail");

	}
	
	@Test
	public void testUserById() {
	    String responseBody = "";
	    String output;
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/id/1");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);	  
	 
	        if (conn.getResponseCode() != 200) {
	            // if not 200 response code then fail test
	            result = false;
	        }
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));
	        while ((output = br.readLine()) != null) {
	            responseBody = output;
	        }
	        
	        if (responseBody.length() < 1) {
	            // if response body is empty then fail test
	            result = false;
	        }

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }
	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully web service getUserById");

	}

	@Test
	public void testUserByIdFalse1() {
	    String responseBody = "";
	    String output;
	    Boolean result = true;
	    Boolean expResult = false;
	 
	    try {
	    	URL url = new URL(WSurl+"/id/admin@admin.com");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);	  
	 
	        if (conn.getResponseCode() != 200) {
	            // if not 200 response code then fail test
	            result = false;
	        }
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(
	                (conn.getInputStream())));
	        while ((output = br.readLine()) != null) {
	            responseBody = output;
	        }
	        
	        if (responseBody.length() < 1) {
	            // if response body is empty then fail test
	            result = false;
	        }

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }
	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully error call to web service getUserById (using email)");

	}

	@Test
	public void testDeleteUserFalse() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/deleteuser/id/10000");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("DELETE");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);	  
	 
	        assertEquals(conn.getResponseCode(), 304);

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }
	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully error call to web service deleteUserById (using non existent id)");

	}

	@Test
	public void testUserByIdFalse2() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/id/10000");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);	  
	 
	        assertEquals(conn.getResponseCode(), 204);
	        
	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }
	    
	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully error call to web service getUserById (using non existent id)");

	}

	@Test
	public void testCreateUserFalse() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/createuser?name=teste&email=admin@admin.com");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);	  
	 
	        assertEquals(conn.getResponseCode(), 598);
	        
	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }
	    
	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully error call to web service createUser (with existent email)");

	}

	@Test
	public void testUpdateUserFalse() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/changepass/email/x@x.x");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("PUT");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);	  
	 
	        assertEquals(conn.getResponseCode(), 598);
	        
	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }
	    
	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully error call to web service changeUserPassById (using non existent email)");

	}
		
	@Test
	public void testCreateDeleteUser() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/createuser?name=teste&email=teste@teste.com");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);	  
	 
	        assertEquals(conn.getResponseCode(), 200);
	        
	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }
	    
	    assertEquals(expResult, result);
	    
	    try {
	    	URL url = new URL(WSurl+"/deleteuser/email/teste@teste.com");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("DELETE");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);	  
	 
	        assertEquals(conn.getResponseCode(), 200);
	        
	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }

	    System.out.println("Checked successfully createUser followed by deleteUser");

	}

}