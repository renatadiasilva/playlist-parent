package dei.uc.pt.aor.wserver.core;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.DELETE;
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

import dei.uc.pt.aor.Playlist;
import dei.uc.pt.aor.PlaylistFacade;
import dei.uc.pt.aor.Song;
import dei.uc.pt.aor.SongFacade;
import dei.uc.pt.aor.User;
import dei.uc.pt.aor.UserFacade;

@Stateless
@Path("/songs")
public class SongService {

	private static final Logger log = LoggerFactory.getLogger(PlaylistService.class);	

	@EJB
	private SongFacade songmng;
	
	@EJB
	private UserFacade usermng;

	@EJB
	private PlaylistFacade playmng;

	//10
	@GET
	@Path("/totalsongs")
	@Produces({MediaType.TEXT_PLAIN})
	public int getTotalSongs() {
		log.info("Getting total of songs.");
		return songmng.findAll().size();
	}

	//11
	@GET
	@Path("/allsongs")
	@Produces({MediaType.APPLICATION_XML})
	public List<Song> getAllSongs() {
		log.info("Getting all songs.");
		return (List<Song>) songmng.findAllByOrder();
	}

	//12
	@GET
	@Path("/{sid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public Song getSongById(@PathParam("sid") Long id) {
		log.info("Getting song with id "+id);
		return  songmng.findSongById(id);
	}	

	//16
	@DELETE
	@Path("/deletesong/{sid}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response deleteSong(@PathParam("sid") Long id) {

		log.info("Deleting song with id "+id);
		Song song = songmng.findSongById(id);

		if (song != null) {
			try {
				User admin = usermng.findUserByEmail("admin@admin.com");
				song.setOwner(admin);
				songmng.update(song);
			} catch (EJBException e) {
				System.out.println("Error deleting song: "+e.getMessage());
			}
			return Response.ok().build();		
		} else return Response.notModified().build();
	}
	
	@GET
	@Path("/totalsongsofuser/id/{uid: \\d+}")
	@Produces({MediaType.TEXT_PLAIN})
	public int totalSongsOfUserById(@PathParam("uid") Long id) {
		log.info("Getting total songs of user with id "+id);
		User u = usermng.findUserById(id);
		return songmng.songsOfUser(u).size();
	}

	@GET
	@Path("/totalsongsofuser/email/{uemail: .+@.+\\.[a-z]+}")
	@Produces({MediaType.TEXT_PLAIN})
	public int totalSongsOfUserByEmail(@PathParam("uemail") String email) {
		log.info("Getting total songs of user with email "+email);
		User u = usermng.findUserByEmail(email);
		return songmng.songsOfUser(u).size();
	}

	//13
	@GET
	@Path("/songsofuser/id/{uid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public List<Song> songsOfUserById(@PathParam("uid") Long id) {
		log.info("Getting songs of user with id "+id);
		User u = usermng.findUserById(id);
		return (List<Song>) songmng.songsOfUserOrderId(u);
	}

	//13
	@GET
	@Path("/songsofuser/email/{uemail: .+@.+\\.[a-z]+}")
	@Produces({MediaType.APPLICATION_XML})
	public List<Song> getSongsOfUserByEmail(@PathParam("uemail") String email) {
		log.info("Getting songs of user with email "+email);
		User u = usermng.findUserByEmail(email);
		return (List<Song>) songmng.songsOfUser(u);
	}

	@GET
	@Path("/totalsongsofplaylist/{pid: \\d+}")
	@Produces({MediaType.TEXT_PLAIN})
	public int totalSongsOfPlaylist(@PathParam("pid") Long id) {
		log.info("Getting total songs of playlist with id "+id);
		Playlist p = playmng.findPlaylistById(id);
		return playmng.getSongsByOrder(p).size();
	}

	//8
	@GET
	@Path("/songsofplaylist/{pid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public List<Song> getSongsOfPlaylist(@PathParam("pid") Long id) {
		log.info("Getting songs of playlist with id "+id);
		Playlist p = playmng.findPlaylistById(id);
		return (List<Song>) playmng.getSongsByOrder(p);
	}

	//extra functionalities
	@PUT
	@Path("/updatesong/{sid: \\d+}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response updateSong(@PathParam("sid") Long id, 
			@DefaultValue("") @QueryParam("title") String title, 
			@DefaultValue("") @QueryParam("artist") String artist,
			@DefaultValue("") @QueryParam("album") String album,
			@DefaultValue("0") @QueryParam("releaseYear") int year,
			@DefaultValue("") @QueryParam("path") String path) {

		log.info("Updating song with id "+id);
		Song song = songmng.findSongById(id);		

		if (!title.equals("")) song.setTitle(title);
		if (!artist.equals("")) song.setArtist(artist);
		if (!album.equals("")) song.setAlbum(album);
		if (year != 0) song.setReleaseYear(year);
		if (!path.equals("")) song.setPathFile(path);
		
		songmng.update(song);
		
		return Response.ok(song).build();

	}
	
}