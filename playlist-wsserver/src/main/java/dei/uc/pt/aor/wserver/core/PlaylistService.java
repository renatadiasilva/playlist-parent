package dei.uc.pt.aor.wserver.core;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dei.uc.pt.aor.Playlist;
import dei.uc.pt.aor.PlaylistFacade;

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
		
}
