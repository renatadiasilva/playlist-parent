package dei.uc.pt.aor.wserver.core;

//import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
//import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
//import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

import dei.uc.pt.aor.User;
import dei.uc.pt.aor.UserFacade;

@Stateless
@Path("/users")
public class UserService {

	@Inject
	private UserFacade usermng;
	
	@GET
	@Path("/allusers")
	@Produces({MediaType.APPLICATION_XML})
//	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
	public List<User> getAllUsers() {
		return usermng.getUsers();
	}
	
	@GET
	@Path("{uemail}")
	@Produces({MediaType.APPLICATION_XML})
	public User getSimpleUserById(@PathParam("uemail") String email){
		
		return  usermng.findUserByEmail(email);
	}	
	
	
//	@GET
//	@Produces({MediaType.TEXT_HTML, MediaType.TEXT_PLAIN})
//	public String getAllInString(){
//		// not the way ! just for test.. 
//		
//		ArrayList<SimpleUser> usr_list = new ArrayList<SimpleUser>();
//		usr_list.addAll(usermng.findAll());
//
//		StringBuilder sb = new StringBuilder();
//
//		for (SimpleUser usr : usr_list)
//			sb.append(usr.toString()).append(" ; ");
//		
//		return sb.toString();
//	}
//
//
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
