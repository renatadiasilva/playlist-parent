package wsclient;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class RestConsumer {

	public static void main(String[] args) throws JAXBException {
		//		  createUser();
		//		  consumeSimpleService();
		//		  consumeSimpleUsersService();
		//		  getUserById(1L);
		getChartLyric("michael jackson", "thriller");
		getWikiaLyric("abba", "fernando");
	}

	//Method to get the lyric by artist & title using REST WS - chartlyrics.com
	private static void getChartLyric(String artist, String title) throws JAXBException {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect");

		target = target.queryParam("artist", artist);
		target = target.queryParam("song", title);

		//System.out.println(target.getUri());

		// MediaType in XML 
		Response response = target.request(MediaType.APPLICATION_XML).get();
		
		//System.out.println(response.getStatus());
		//System.out.println(response.readEntity(String.class));     //Attention: the Entity can only be read once!
		
		GetLyricResult glr = response.readEntity(GetLyricResult.class);

		System.out.println(glr.getSongLyric());
	}


	
	
	//Method to get the lyric by artist & title using REST WS - api.wikia.com
	private static void getWikiaLyric(String artist, String title) throws JAXBException {
	
		ResteasyClient client = new ResteasyClientBuilder().build();

//		http://lyrics.wikia.com/api.php?artist=Cake&song=Dime
//		http://lyrics.wikia.com/api.php?artist=Cake&song=Dime&fmt=xml
		ResteasyWebTarget target = client.target("http://lyrics.wikia.com/api.php");

		target = target.queryParam("artist", artist);
		target = target.queryParam("song", title);

		//System.out.println(target.getUri());

		// MediaType in XML 
		Response response = target.request(MediaType.APPLICATION_XML).get();
		
		//System.out.println(response.getStatus());
		//System.out.println(response.readEntity(String.class));     //Attention: the Entity can only be read once!
		
		GetLyricResult glr = response.readEntity(GetLyricResult.class);

		System.out.println(glr.getSongLyric());
	
	}	
		
		
	
	//	private static void createUser(){
	//		
	//		// Pode ser reutilizado !
	//		ResteasyClient client = new ResteasyClientBuilder().build();
	//
	//		// Target também!
	//		ResteasyWebTarget target = client.target("http://localhost:8080/thews-ws/rest/simpleusers/simpleuser");
	//
	//		
	//		SimpleUser theuser = new SimpleUser();
	//		theuser.setUsername("outro");
	//		
	//		
	//		// request e response já terão de ser criados por request e response
	//		Response response = target.request(MediaType.APPLICATION_XML).post(
	//				Entity.entity(theuser, "application/xml"));
	//
	//		if (response.getStatus() != 200) { // se nao correu tudo bem !
	//			throw new RuntimeException("Failed : HTTP error code : "
	//					+ response.getStatus());
	//		}
	//		
	//		// 1# em objecto ! 
	//		//SimpleUser usr_do_server =  response.readEntity(SimpleUser.class);
	//		//System.out.println(usr_do_server.getUsername());
	//		// ou um ou outro.. maneira 2# em string .. xml (de notar que a resposta tem o atributo id.. mas como o nosso objecto não o usa.. não o carrega)
	//		System.out.println(response.readEntity(String.class));
	//
	//		
	//	}
	//	
	//	private static void consumeSimpleService(){
	//		ResteasyClient client = new ResteasyClientBuilder().build();
	//
	//		ResteasyWebTarget target = client.target("http://localhost:8080/thews-ws/rest/simplews");
	//		
	//		Response response = target.request().get();
	//		
	//		System.out.println(response.readEntity(String.class));
	//		
	//	}
	//	
	//	private static void consumeSimpleUsersService(){
	//		ResteasyClient client = new ResteasyClientBuilder().build();
	//
	//		ResteasyWebTarget target = client.target("http://localhost:8080/thews-ws/rest/simpleusers");
	//		
	//		Response response = target.request().get();
	//		
	//		System.out.println(response.readEntity(String.class));
	//		
	//	}
	//	
	//	private static void getUserById(Long id){





}
