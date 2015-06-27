package dei.uc.pt.aor.wserver.core;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
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

import dei.uc.pt.aor.Playlist;
import dei.uc.pt.aor.PlaylistFacade;
import dei.uc.pt.aor.Song;
import dei.uc.pt.aor.SongFacade;
import dei.uc.pt.aor.User;
import dei.uc.pt.aor.UserFacade;

@Stateless
@Path("/songs")
public class SongService {

	@Inject
	private SongFacade songmng;
	
	@Inject
	private UserFacade usermng;

	@Inject
	private PlaylistFacade playmng;

	@GET
	@Path("/totalsongs")
	@Produces({MediaType.TEXT_PLAIN})
	public int getTotalSongs() {
		return songmng.findAll().size();
	}

	@GET
	@Path("/allsongs")
	@Produces({MediaType.APPLICATION_XML})
	public List<Song> getAllSongs() {
		return (List<Song>) songmng.findAllByOrder();
	}

	@GET
	@Path("/id/{sid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public Song getSongById(@PathParam("sid") Long id) {
		return  songmng.findSongById(id);
	}	

	@DELETE
	@Path("/deletesong/{sid}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response deleteSong(@PathParam("sid") Long id) {

		Song song = songmng.findSongById(id);

		if (song != null) {
			
			// pôr isto no facade!!
//			log.info("Changing ownership of song to admin");
//			log.debug("Changing ownership of song "+song.getTitle()+"to admin");
			try {
				User admin = usermng.findUserByEmail("admin@admin.com");

				song.setOwner(admin);
				songmng.update(song);
			} catch (EJBException e) {
//	        	String errorMsg = "Error deleting song: "+e.getMessage();
//	        	log.error(errorMsg);
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			}
		}
		return Response.ok().build();		
	}

	@DELETE
	@Path("/deletesongforgood/{sid}")
	@Consumes({MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_XML})
	public Response deleteSongDef(@PathParam("sid") Long id) {

		Song song = songmng.findSongById(id);

		if (song != null) {
			
			try {
				//remover das playlists (query playlistsOfSong; for p remove song)
				songmng.delete(song);
			} catch (EJBException e) {
//	        	String errorMsg = "Error deleting song: "+e.getMessage();
//	        	log.error(errorMsg);
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			}
		}
		return Response.ok().build();		
	}

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

		Song song = songmng.findSongById(id);		

		if (!title.equals("")) song.setTitle(title);
		if (!artist.equals("")) song.setArtist(artist);
		if (!album.equals("")) song.setAlbum(album);
		if (year != 0) song.setReleaseYear(year);
		if (!path.equals("")) song.setPathFile(path);
		
		songmng.update(song);
		
		return Response.ok(song).build();

	}
	
	@GET
	@Path("/songsofuser/{uid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public List<Song> getSongsOfUser(@PathParam("uid") Long id) {
		User u = usermng.findUserById(id);
		return (List<Song>) songmng.songsOfUser(u);
	}

	@GET
	@Path("/songsofplaylist/{uid: \\d+}")
	@Produces({MediaType.APPLICATION_XML})
	public List<Song> getSongsOfPlaylist(@PathParam("uid") Long id) {
		Playlist p = playmng.findPlaylistById(id);
		return (List<Song>) playmng.getSongs(p);
	}

}
