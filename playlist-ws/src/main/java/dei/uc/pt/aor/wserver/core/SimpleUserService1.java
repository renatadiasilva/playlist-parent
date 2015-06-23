package dei.uc.pt.aor.wserver.core;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dei.uc.pt.aor.User;
import dei.uc.pt.aor.UserFacade;

@Stateless
@Path("/simpleusers")
public class SimpleUserService1 {

	@Inject
	private UserFacade usermng;
	
	@GET
	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public String getAllInString(){
		// not the way ! just for test.. 
		System.out.println("entrou");

		ArrayList<User> usr_list = new ArrayList<User>();
		usr_list.addAll(usermng.getUsers());

		StringBuilder sb = new StringBuilder();
		
		System.out.println("passou");

		for (User usr : usr_list) {
			System.out.println(usr);
			sb.append(usr.toString()).append(" ; ");
		}
		return sb.toString();
	}
	
	
//	@GET
//	@Path("{suid: \\d+}")
//	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
//	public SimpleUser getSimpleUserById(@PathParam("suid") Long id){
//		// use logs!!! (im lazy)
//		System.out.println("get me : "+id);
//		
//		return usermng.find(id);
//	}
//	
	
	
//	@POST
//	@Path("/simpleuser")
//	@Consumes({MediaType.APPLICATION_XML})
//	@Produces({MediaType.APPLICATION_XML})
//	public Response createSimpleUser(SimpleUser user){
//		System.out.println(user.getId());
//		System.out.println(user.getUsername());
//		SimpleUser another = new SimpleUser();
//		another.setUsername(user.getUsername());// Why ? :(
//		
//		
//		SimpleUser newuser = usermng.create(another);
//		//Response.notModified();
//		
//		return Response.ok(newuser).build();
//		
//	}
	
}
