package wsclient;


import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class SimpleWSConsumer {

	public static void main(String[] args) {
		  createUser();
		  consumeSimpleService();
		  consumeSimpleUsersService();
		  getUserById(1L);
		}
	
	private static void createUser(){
		
		// Pode ser reutilizado !
		ResteasyClient client = new ResteasyClientBuilder().build();

		// Target também!
		ResteasyWebTarget target = client.target("http://localhost:8080/thews-ws/rest/simpleusers/simpleuser");

		
		SimpleUser theuser = new SimpleUser();
		theuser.setUsername("outro");
		
		
		// request e response já terão de ser criados por request e response
		Response response = target.request(MediaType.APPLICATION_XML).post(
				Entity.entity(theuser, "application/xml"));

		if (response.getStatus() != 200) { // se nao correu tudo bem !
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		
		// 1# em objecto ! 
		//SimpleUser usr_do_server =  response.readEntity(SimpleUser.class);
		//System.out.println(usr_do_server.getUsername());
		// ou um ou outro.. maneira 2# em string .. xml (de notar que a resposta tem o atributo id.. mas como o nosso objecto não o usa.. não o carrega)
		System.out.println(response.readEntity(String.class));

		
	}
	
	private static void consumeSimpleService(){
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/thews-ws/rest/simplews");
		
		Response response = target.request().get();
		
		System.out.println(response.readEntity(String.class));
		
	}
	
	private static void consumeSimpleUsersService(){
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/thews-ws/rest/simpleusers");
		
		Response response = target.request().get();
		
		System.out.println(response.readEntity(String.class));
		
	}
	
	private static void getUserById(Long id){
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/thews-ws/rest/simpleusers");
		
		// importante ! alterações na path nao tomam efeito, temos sempre de mudar a referencia
		// target.path(""+id);  // no effect.. 
		target = target.path(""+id);// correct !
		
		// só para ver se está :)
		System.out.println(target.getUri());
		
		// default será o primeiro MediaType do service 
		Response response = target.request().get();
		
		System.out.println(response.readEntity(String.class));
		
	}
	



}
