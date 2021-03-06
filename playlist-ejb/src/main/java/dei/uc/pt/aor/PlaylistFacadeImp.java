package dei.uc.pt.aor;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dei.uc.pt.aor.dao.PlaylistDAO;

@Stateless
public class PlaylistFacadeImp implements PlaylistFacade {

	private static final Logger log = LoggerFactory.getLogger(PlaylistFacadeImp.class);
	
	@EJB
	private PlaylistDAO playlistDAO;
	
	public void save(Playlist playlist) {
		log.info("Saving playlist in DB");
		isPlaylistWithAllData(playlist);
		playlistDAO.save(playlist);
	}
	
	public boolean addPlaylist(User u, String name, List<Song> tracks) {
		if (playlistSameName(u, name).size() == 0) {
			Playlist p = new Playlist(name, new Date(), u);
			for (Song s : tracks) p.addSong(s);
			playlistDAO.save(p);
			return true;
		}
		return false;
	}
	
	public Playlist update(Playlist playlist) {
		log.info("Updating playlist of DB");
		isPlaylistWithAllData(playlist);
		return playlistDAO.update(playlist);
	}
	
	public boolean updatePlaylistName(Playlist playlist, String playlistName, User u) {
		log.info("Updating playlist of DB (with validation)");
		if ( (playlistName.equals(playlist.getName())) || 
				(playlistDAO.playlistSameName(u, playlistName).size() == 0) ) {
			playlist.setName(playlistName);
			isPlaylistWithAllData(playlist);
			playlistDAO.update(playlist);
			return true;
		}
		return false;
	}
	
	public void delete(Playlist playlist) {
		log.info("Deleting playlist from DB");
		playlistDAO.delete(playlist);
	}
	
	public Playlist find(int entityID) {
		log.info("Finding playlist by ID");
		return playlistDAO.find(entityID);
	}
	
	//duplicate??
	public Playlist findPlaylistById(Long id) {
		log.info("Finding playlist by id");
		return playlistDAO.findPlaylistById(id);
	}

	public List<Playlist> findAll() {
		log.info("Creating Query for all playlists");
		return playlistDAO.findAll();
	}
	
	public List<Playlist> findAllByOrder() {
		log.info("Creating Query for all playlists (ordered by id)");
		return playlistDAO.findAllByOrder();
	}
	
	public List<Playlist> playlistsOfUser(User u, int order) {
		log.info("Creating Query for playlists of User");
		return playlistDAO.playlistsOfUser(u, order);
	}
	
	public List<Playlist> playlistsOfUserContainingSong(User u, Song s) {
		log.info("Creating Query for all playlists of user costaining song");
		return playlistDAO.playlistsOfUserContainingSong(u, s);
	}
	
	public List<Playlist> playlistSameName(User u, String name) {
		log.info("Creating Query for playlist of same name");
		return playlistDAO.playlistSameName(u, name);
	}
	
	public List<Song> getSongs(Playlist p) {
		log.info("Getting songs of playlist");
		return playlistDAO.getSongs(p);
	}
	
	public List<Song> getSongsByOrder(Playlist p) {
		log.info("Getting songs of playlist");
		return playlistDAO.getSongsByOrder(p);
	}

	public void removeSongFromPlaylist(Playlist p, Song s) {
		log.info("Removing song from playlist");
		playlistDAO.removeSong(p,s);
	}

	public void addSongToPlaylist(Playlist p, Song s) {
		log.info("Adding songs to playlist");
		playlistDAO.addSong(p,s);
	}

	private void isPlaylistWithAllData(Playlist playlist) {
		boolean hasError = false;
		
		if(playlist == null){
			hasError = true;
		}
		if (playlist.getName() == null || "".equals(playlist.getName().trim())){
			hasError = true;
		}
		
		if (hasError){
			throw new IllegalArgumentException("The playlist is missing data. Check the name, it should have value.");
		}
	}

}