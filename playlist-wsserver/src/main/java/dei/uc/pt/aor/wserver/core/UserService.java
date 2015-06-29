package dei.uc.pt.aor.wserver.core;

import java.util.List;

import javax.ejb.EJBException;
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

import dei.uc.pt.aor.EncryptPass;
import dei.uc.pt.aor.LoggedUsers;
import dei.uc.pt.aor.Playlist;
import dei.uc.pt.aor.PlaylistFacade;
import dei.uc.pt.aor.Song;
import dei.uc.pt.aor.SongFacade;
import dei.uc.pt.aor.User;
import dei.uc.pt.aor.UserFacade;

@Stateless
@Path("/users")
public class UserService {

	@Inject
	private UserFacade usermng;
	@Inject
	private PlaylistFacade playmng;
	@Inject
	private SongFacade songmng;

	@Inject
	private LoggedUsers loggedlist;
	
	@Inject
	private EncryptPass epw;
	
	//1
	@GET
	@Path("/totalusers")
	@Produces({MediaType.TEXT_PLAIN})
	public int getTotalUsers() {
		return usermng.getUsers().size();
	}

	//2
	@GET
	@Path("/allusers")
	@Produces({MediaType.APPLICATION_XML})
	public List<User> getAllUsers() {
		return (List<User>) usermng.findAllByOrder();
	}

	//3
	@GET
	@Path("/email/{uemail: .+@.+\\.[a-z]+}")
	@Produces({MediaType.APPLICATION_XML})
	public User getUserByEmail(@PathParam("uemail") String email) {
		return usermng.findUserByEmail(email);
	}	

	//3
	@GET
	@Path("/id/{uid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public User getUserById(@PathParam("uid") Long id) {
		return usermng.findUserById(id);
	}	
	
	//4
	@GET
	@Path("/loggedusers")
	@Produces({MediaType.APPLICATION_XML})
	public List<User> getLoggedUsers() {
		return (List<User>) loggedlist.getLoggedUsersList();
	}
	
	//5
	@GET
	@Path("/totalloggedusers")
	@Produces({MediaType.TEXT_PLAIN})
	public int getTotalLoggedUsers() {
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

		User user = new User(name, epw.encrypt(pass), email);
		usermng.addUser(user);

		return Response.ok(user).build();

	}

	//14
	@DELETE
	@Path("/deleteuser/id/{uid: \\d+}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response deleteUserById(@PathParam("uid") Long id) {

		Boolean error = false;
		User user = usermng.findUserById(id);

		if (user != null) {			
			List<Song> uSongs = songmng.songsOfUser(user);
			for (Song s : uSongs) {
				try {
					User admin = usermng.findUserByEmail("admin@admin.com");
					s.setOwner(admin);
					songmng.update(s);
				} catch (EJBException e) {
		        	System.out.println("Error changing the ownership of songs to admin: "+e.getMessage());
					error = true;
					break;
				}
			}
			
			if (!error) {
				List<Playlist> uPlaylists = playmng.playlistsOfUser(user, 1);
				for (Playlist p: uPlaylists) playmng.delete(p);
				usermng.delete(user);
			}
			return Response.ok().build();
		} else return Response.notModified().build();		
	}
	
	//14
	@DELETE
	@Path("/deleteuser/email/{uemail: .+@.+\\.[a-z]+}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response deleteUserByEmail(@PathParam("uemail") String email) {

		Boolean error = false;
		User user = usermng.findUserByEmail(email);

		if (user != null) {
			List<Song> uSongs = songmng.songsOfUser(user);
			for (Song s : uSongs) {
				try {
					User admin = usermng.findUserByEmail("admin@admin.com");
					s.setOwner(admin);
					songmng.update(s);
				} catch (EJBException e) {
		        	System.out.println("Error changing the ownership of songs to admin: "+e.getMessage());
					error = true;
					break;
				}
			}
			
			if (!error) {
				List<Playlist> uPlaylists = playmng.playlistsOfUser(user, 1);
				for (Playlist p: uPlaylists) playmng.delete(p);
				usermng.delete(user);
			}
			return Response.ok().build();
		} else return Response.notModified().build();
		
	}

	//15
	@PUT
	@Path("/changepass/id/{uid: \\d+}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response updateUserById(@PathParam("uid") Long id,
			@QueryParam("pass") String pass) {

		User user = usermng.findUserById(id);		

		user.setPassword(epw.encrypt(pass));
		usermng.update(user);
		
		return Response.ok(user).build();

	}
	
	//15
	@PUT
	@Path("/changepass/email/{uemail: .+@.+\\.[a-z]+}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response updateUserByEmail(@PathParam("uemail") String email,
			@QueryParam("pass") String pass) {

		User user = usermng.findUserByEmail(email);		

		user.setPassword(epw.encrypt(pass));
		usermng.update(user);
		
		return Response.ok(user).build();

	}

}