package dei.uc.pt.aor.wserver.core;

import static org.junit.Assert.*;

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
public class SongServiceTest {

	final String WSurl = "http://localhost:8080/playlist-wsserver/rest/songs";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testFalseUrl() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/totalsongs1");
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
	public void testTotalSongs() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/totalsongs");
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
	    
		System.out.println("Checked successfully web service getTotalSongs");

	}

	@Test
	public void testAllSongs() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/allsongs");
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
	    
		System.out.println("Checked successfully web service getAllSongs");

	}

	@Test
	public void testSongInfoFalse() {
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
	    
		System.out.println("Checked successfully web service getSongByUserId");

	}

	@Test
	public void testDeleteSongOfUserFalse() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/deletesongofuser/id?sid=10000&uidemail=10000");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("DELETE");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);
	 
		    assertTrue(conn.getResponseCode() == 304);

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }

	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully web service deleteSongByUserId");

	}

	@Test
	public void testTotalSongOfUser() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/totalsongsofuser/email/admin@admin.com");
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
	    
		System.out.println("Checked successfully web service totalSongsOfUserById");

	}

	@Test
	public void testSongsOfUserById() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/songsofuser/id/1");
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
	    
		System.out.println("Checked successfully web service getSongsOfUserByEmail");

	}

	@Test
	public void testSongsOfPlaylistFalse() {
	    Boolean result = true;
	    Boolean expResult = true;
	 
	    try {
	    	URL url = new URL(WSurl+"/songsofplaylist/10000");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", MediaType.APPLICATION_XML);
	 
		    assertFalse(conn.getResponseCode() == 200);

	        conn.disconnect();
	    } catch (IOException e) {
	        // if MalformedURLException, ConnectException, etc. then fail test
	        result = false;
	    }

	    assertEquals(expResult, result);
	    
		System.out.println("Checked successfully web service getSongsOfPlaylist");

	}

}
