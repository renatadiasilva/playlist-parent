package dei.uc.pt.aor.wserver.core;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

//implementar o findUserById
//	@GET
//	@Path("{suid: \\d+}")
//	@Produces({MediaType.APPLICATION_XML})
//	public User getUserById(@PathParam("suid") int id){
//
//		return  usermng.findUserById(id);
//	}
		
	//testar
	@POST
	@Path("/createuser")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response createSimpleUser(User user) throws NoSuchAlgorithmException, UnsupportedEncodingException{

		//tirar user e meter nome, email, pass??
		usermng.addUser(user); //pass?

		User newuser = usermng.findUserByEmail(user.getEmail());
		//Response.notModified();

		return Response.ok(newuser).build();

	}
		
	//testar
	@DELETE
	@Path("/deleteuser")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response deleteUser(User user){

		User newuser = usermng.findUserByEmail(user.getEmail());
		usermng.delete(newuser);

		return Response.ok().build();

	}

	//testar
	@PUT
	@Path("/updateuser")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response updateUser(User user){

		//mudar email???
		User newuser = usermng.findUserByEmail(user.getEmail());		
//		User newuser = usermng.findUserById(user.getId());
//		newuser.setEmail(user.getEmail());
		newuser.setName(user.getName());
		//confirmar isto de mudar pass??
		newuser.setPassword(user.getPassword());

		return Response.ok(newuser).build();

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
