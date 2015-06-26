package dei.uc.pt.aor.wserver.core;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
	
	//tirar?
	@Inject
	private SongFacade songmng;
	@Inject
	private PlaylistFacade playmng;

	@Inject
	private EncryptPass epw;
	
	@GET
	@Path("/totalusers")
	@Produces({MediaType.TEXT_PLAIN})
	public int getTotalUsers() {
		return usermng.getUsers().size();
	}

	@GET
	@Path("/allusers")
	@Produces({MediaType.APPLICATION_XML})
	public List<User> getAllUsers() {
		return (List<User>) usermng.findAllByOrder();
	}

	@GET
	@Path("/email/{uemail}")
	@Produces({MediaType.APPLICATION_XML})
	public User getUserByEmail(@PathParam("uemail") String email) {
		return  usermng.findUserByEmail(email);
	}	

	@GET
	@Path("/id/{uid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public User getUserById(@PathParam("uid") Long id) {
		return  usermng.findUserById(id);
	}	
		
	@POST
	@Path("/createuser")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response createUser(@QueryParam("name") String name, 
			@QueryParam("email") String email, @DefaultValue("123") @QueryParam("pass") String pass)
			throws NoSuchAlgorithmException, UnsupportedEncodingException{

		User user = new User(name, epw.encrypt(pass), email);
		usermng.addUser(user);

		// precisa?? (verifica se já existe email!!!!!
//		User newuser = usermng.findUserByEmail(user.getEmail());
		//Response.notModified();

		return Response.ok(user).build();

	}

	@DELETE
	@Path("/deleteuser/{uid}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response deleteUser(@PathParam("uid") Long id) {

		Boolean error = false;
		User user = usermng.findUserById(id);

		if (user != null) {
			
			// pôr isto no facade!!
			List<Song> uSongs = songmng.songsOfUser(user);
			for (Song s : uSongs) {
				try {
//					log.info("Changing ownership of all songs to admin");
//					log.debug("Changing ownership of all songs of "+uemail+" to admin");
					User admin = usermng.findUserByEmail("admin@admin.com");

					s.setOwner(admin);
					songmng.update(s);
				} catch (EJBException e) {
		        	String errorMsg = "Error changing the ownership of songs to admin: "+e.getMessage();
//		        	log.error(errorMsg);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
					error = true;
					break;
				}
			}
			
			if (!error) {
//				log.info("Deleting all playlists of user");
//				log.debug("Deleting all playlists of "+uemail);
				List<Playlist> uPlaylists = playmng.playlistsOfUser(user, 1);
				for (Playlist p: uPlaylists) playmng.delete(p);

//				log.info("Deleting account of user");
//				log.debug("Deleting account of "+uemail);
				usermng.delete(user);
			}
			return Response.ok().build();
		} else {
			return Response.ok().build();
			// ver melhor
//			return Response.notModified().build();
		}
		
	}

	@PUT
	@Path("/updateuser/{uid: \\d+}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response updateUser(@PathParam("uid") Long id, 
			@DefaultValue("") @QueryParam("name") String name, 
			@DefaultValue("") @QueryParam("pass") String pass) {

		User user = usermng.findUserById(id);		

		if (!name.equals("")) user.setName(name);
		// encrypt??
		if (!pass.equals("")) user.setPassword(epw.encrypt(pass));
		usermng.update(user);
		
		return Response.ok(user).build();

	}

}
