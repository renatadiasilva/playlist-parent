package dei.uc.pt.aor.wserver.core;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
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

	//musicas de uma playlist	
	//playlist de um dado user
	//adicionar e remover músicas de playlists
	
	
	//será necessário o playlist by Id?
	@GET
	@Path("/id/{pid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public Playlist getPlaylistById(@PathParam("pid") Long id) {
		return playmng.findPlaylistById(id);
	}	
	
	//9
	@GET
	@Path("/playlistsofuser/{uid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public List<Playlist> getPlaylistsOfUser(@PathParam("uid") Long id) {
		User u = usermng.findUserById(id);
		return (List<Playlist>) playmng.playlistsOfUser(u, 1);
	}

	//17
	@PUT
	@Path("/addsongtoplaylist/{sid: \\d+}/{pid: \\d+}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response addSongToPlaylist(@PathParam("sid") Long sid, 
			@PathParam("pid") Long pid) {

		Playlist p = playmng.findPlaylistById(pid);		
		Song s = songmng.findSongById(sid);

		playmng.addSongToPlaylist(p, s);
		
		return Response.ok(p).build();

	}
	
	//17
	@PUT
	@Path("/removesongfromplaylist/{sid: \\d+}/{pid: \\d+}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response removeSongFromPlaylist(@PathParam("sid") Long sid, 
			@PathParam("pid") Long pid) {

		Playlist p = playmng.findPlaylistById(pid);		
		Song s = songmng.findSongById(sid);

		playmng.removeSongFromPlaylist(p, s);;
		
		return Response.ok(p).build();

	}
		
}
