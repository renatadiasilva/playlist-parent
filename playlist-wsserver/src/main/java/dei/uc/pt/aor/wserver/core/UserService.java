package dei.uc.pt.aor.wserver.core;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dei.uc.pt.aor.EncryptPass;
import dei.uc.pt.aor.LoggedUsers;
import dei.uc.pt.aor.PlaylistFacade;
import dei.uc.pt.aor.SongFacade;
import dei.uc.pt.aor.User;
import dei.uc.pt.aor.UserFacade;

@Stateless
@Path("/users")
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);	

	@EJB
	private UserFacade usermng;
	@EJB
	private PlaylistFacade playmng;
	@EJB
	private SongFacade songmng;

	@Inject
	private LoggedUsers loggedlist;
	
	@EJB
	private EncryptPass epw;
	
	//1
	@GET
	@Path("/totalusers")
	@Produces({MediaType.TEXT_PLAIN})
	public int getTotalUsers() {
		log.info("Gettting total of users.");
		return usermng.getUsers().size();
	}

	//2
	@GET
	@Path("/allusers")
	@Produces({MediaType.APPLICATION_XML})
	public List<User> getAllUsers() {
		log.info("Gettting all users.");
		return (List<User>) usermng.findAllByOrder();
	}

	//3
	@GET
	@Path("/email/{uemail: .+@.+\\.[a-z]+}")
	@Produces({MediaType.APPLICATION_XML})
	public User getUserByEmail(@PathParam("uemail") String email) {
		log.info("Getting user with email "+email);
		return usermng.findUserByEmail(email);
	}	

	//3
	@GET
	@Path("/id/{uid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public User getUserById(@PathParam("uid") Long id) {
		log.info("Getting user with id "+ id);
		return usermng.findUserById(id);
	}	
	
	//4
	@GET
	@Path("/loggedusers")
	@Produces({MediaType.APPLICATION_XML})
	public List<User> getLoggedUsers() {
		log.info("Getting total logged users.");
		return (List<User>) loggedlist.getLoggedUsersList();
	}
	
	//5
	@GET
	@Path("/totalloggedusers")
	@Produces({MediaType.TEXT_PLAIN})
	public int getTotalLoggedUsers() {
		log.info("Getting logged users.");
		return loggedlist.getLoggedUsersList().size();
	}
	
	//14
	@POST
	@Path("/createuser")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response createUser(@QueryParam("name") String name, 
			@QueryParam("email") String email,
			@DefaultValue("123") @QueryParam("pass") String pass) {
		
		log.info("Creating user.");
		User user = usermng.addUser(name, pass, email);
		if (user != null) return Response.ok(user).build();
		 // email already exists
		else return Response.status(598).build();
	}

	//14
	@DELETE
	@Path("/deleteuser/id/{uid: \\d+}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response deleteUserById(@PathParam("uid") Long id) {

		log.info("Deleting user with "+id);
		if (usermng.removeUserById(id)) 
			return Response.ok().build();
		// user don't exist
		else return Response.notModified().build();		
	}
	
	//14
	@DELETE
	@Path("/deleteuser/email/{uemail: .+@.+\\.[a-z]+}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response deleteUserByEmail(@PathParam("uemail") String email) {

		log.info("Deleting user with email "+email);
		if (usermng.removeUserByEmail(email))
			return Response.ok().build();
		// user don't exist
		else return Response.notModified().build();
		
	}

	//15
	@PUT
	@Path("/changepass/id/{uid: \\d+}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response updateUserById(@PathParam("uid") Long id,
			@QueryParam("pass") String pass) {

		log.info("Changing pass of user with id "+id);
		User user = usermng.findUserById(id);		
		if (user != null) {
			usermng.updateUserPassAdmin(user, pass);		
			return Response.ok(user).build();
		// user don't exist
		} else return Response.status(598).build();

	}
	
	//15
	@PUT
	@Path("/changepass/email/{uemail: .+@.+\\.[a-z]+}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response updateUserByEmail(@PathParam("uemail") String email,
			@QueryParam("pass") String pass) {

		log.info("Changing pass of user with "+email);
		User user = usermng.findUserByEmail(email);		
		if (user != null) {
			usermng.updateUserPassAdmin(user, pass);
			return Response.ok(user).build();
		// user don't exist
		} else return Response.status(598).build();	

	}

}