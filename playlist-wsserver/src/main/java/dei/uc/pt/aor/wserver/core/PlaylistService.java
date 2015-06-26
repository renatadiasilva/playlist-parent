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
@Path("/playlists")
public class PlaylistService {

	@Inject
	private PlaylistFacade playmng;

	@GET
	@Path("/totalplaylists")
	@Produces({MediaType.TEXT_PLAIN})
	public int getTotalPlaylists() {
		return playmng.findAll().size();
	}

	@GET
	@Path("/allplaylists")
	@Produces({MediaType.APPLICATION_XML})
	public List<Playlist> getAllPlaylists() {
		return (List<Playlist>) playmng.findAllByOrder();
	}

	//buscar musicas...
	@GET
	@Path("/id/{pid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public Playlist getPlaylistById(@PathParam("pid") Long id) {
		return playmng.findPlaylistById(id);
	}	
		
	@POST
	@Path("/createplaylist")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response createUser(@QueryParam("name") String name, 
			@QueryParam("email") String email, @DefaultValue("123") @QueryParam("pass") String pass)
			throws NoSuchAlgorithmException, UnsupportedEncodingException{

		User user = new User(name, epw.encrypt(pass), email);
		playmng.addUser(user);

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
		User user = playmng.findUserById(id);

		if (user != null) {
			
			// pôr isto no facade!!
			List<Song> uSongs = songmng.songsOfUser(user);
			for (Song s : uSongs) {
				try {
//					log.info("Changing ownership of all songs to admin");
//					log.debug("Changing ownership of all songs of "+uemail+" to admin");
					User admin = playmng.findUserByEmail("admin@admin.com");

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
				playmng.delete(user);
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

		User user = playmng.findUserById(id);		

		if (!name.equals("")) user.setName(name);
		// encrypt??
		if (!pass.equals("")) user.setPassword(epw.encrypt(pass));
		playmng.update(user);
		
		return Response.ok(user).build();

	}

}
