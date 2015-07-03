package dei.uc.pt.aor.wserver.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.core.MediaType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistServiceTest {

	final String WSurl = "http://localhost:8080/playlist-wsserver/rest/playlists";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFalseUrl() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/totalplaylists1");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.TEXT_PLAIN);
	 
		    assertFalse(conn.getResponseCode() == 200);

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }

	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully false URL gets code != 200 ");

	}

	@Test
	public void testTotalPlaylists() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/totalplaylists");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.TEXT_PLAIN);
	 
		    assertTrue(conn.getResponseCode() == 200);

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }

	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully web service getTotalPlaylists");

	}

	@Test
	public void testAllPlaylists() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/allplaylists");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);
	 
		    assertTrue(conn.getResponseCode() == 200);

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }

	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully web service getAllPlaylists");

	}

	@Test
	public void testPlaylistInfoFalse() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/100000");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);
	 
		    assertTrue(conn.getResponseCode() == 204);

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }

	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully error call to web service getPlaylistInfo");

	}

	@Test
	public void testPlaylistsOfUserById() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/playlistsofuser/id/1");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);
	 
		    assertTrue(conn.getResponseCode() == 200);

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }

	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully web service getPlaylistsOfUserById");

	}

	@Test
	public void addSongToPlaylists() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/addsongtoplaylist?song=100000&playlist=10000");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("PUT");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);
	 
		    assertTrue(conn.getResponseCode() == 500);

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }

	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully web service getSongsOfUserByEmail");

	}

}