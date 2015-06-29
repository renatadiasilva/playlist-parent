package dei.uc.pt.aor.wserver.core;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

	@Inject
	private UserFacade usermng;

	@Inject
	private SongFacade songmng;

	//6
	@GET
	@Path("/totalplaylists")
	@Produces({MediaType.TEXT_PLAIN})
	public int getTotalPlaylists() {
		return playmng.findAll().size();
	}

	//7
	@GET
	@Path("/allplaylists")
	@Produces({MediaType.APPLICATION_XML})
	public List<Playlist> getAllPlaylists() {
		return (List<Playlist>) playmng.findAllByOrder();
	}

	@GET
	@Path("/{pid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public Playlist getPlaylistById(@PathParam("pid") Long id) {
		return playmng.findPlaylistById(id);
	}	
	
	//9
	@GET
	@Path("/playlistsofuser/id/{uid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public List<Playlist> playlistsOfUserById(@PathParam("uid") Long id) {
		User u = usermng.findUserById(id);
		return (List<Playlist>) playmng.playlistsOfUser(u, 1);
	}

	//9
	@GET
	@Path("/playlistsofuser/email/{uemail: .+@.+\\.[a-z]+}")
	@Produces({MediaType.APPLICATION_XML})
	public List<Playlist> playlistsOfUserByEmail(@PathParam("uemail") String email) {
		User u = usermng.findUserByEmail(email);
		return (List<Playlist>) playmng.playlistsOfUser(u, 1);
	}

	//17
	@PUT
	@Path("/addsongtoplaylist")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response addSongToPlaylist(@QueryParam("song") Long sid, 
			@QueryParam("playlist") Long pid) {

		Playlist p = playmng.findPlaylistById(pid);		
		Song s = songmng.findSongById(sid);

		playmng.addSongToPlaylist(p, s);
		
		return Response.ok(p).build();

	}
	
	//17
	@PUT
	@Path("/removesongfromplaylist")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response removeSongFromPlaylist(@QueryParam("song") Long sid, 
			@QueryParam("playlist") Long pid) {

		Playlist p = playmng.findPlaylistById(pid);		
		Song s = songmng.findSongById(sid);

		playmng.removeSongFromPlaylist(p, s);;
		
		return Response.ok(p).build();

	}
		
}