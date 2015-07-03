package wsconsumer;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class RestConsumer {

	//Method to get the lyric by artist & title using REST WS - chartlyrics.com
	public String getChartLyric(String artist, String title) throws Exception  {

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect");
		target = target.queryParam("artist", artist);
		target = target.queryParam("song", title);

		// MediaType in XML 
		Response response = target.request(MediaType.APPLICATION_XML).get();
		ChartLyric cl = response.readEntity(ChartLyric.class);
		
		if (cl != null) return cl.getSongLyric();
		return null;
	}

	//Method to get the lyric by artist & title using REST WS - api.wikia.com
	public String getWikiaLyric(String artist, String title) throws JAXBException {
	
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://lyrics.wikia.com/api.php");
		target = target.queryParam("artist", artist);
		target = target.queryParam("song", title);
		target = target.queryParam("fmt", "xml");
		
		// MediaType in XML 
		Response response = target.request(MediaType.APPLICATION_XML).get();
		WikiaLyric wl = response.readEntity(WikiaLyric.class);
		
		if (wl != null) return wl.getSongLyric();
		return null;
	
	}	
		

}
