package dei.uc.pt.aor;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class NewPlaylistMB implements Serializable {

	private static final long serialVersionUID = -2624145242993606181L;

	private static final Logger log = LoggerFactory.getLogger(NewPlaylistMB.class);

	private String name;
	private Song song;
	private List<Song> tracks;
	
	@Inject
	private PlaylistsManagerMB manager;
	
	@Inject
	private PlaylistFacade playlistFacade;
	
	@Inject
	ActiveUserMB aUser;

	public NewPlaylistMB() {
		tracks = new ArrayList<Song>();
	}
	
	public String addPlaylist(ActiveUserMB auser) {
		log.info("Creating Playlist");
		log.debug("Creating Playlist "+name);
		if (playlistFacade.addPlaylist(auser.getCurrentUser(), name, tracks)) {
			return "listMyPlaylists?faces-redirect=true";
		} else {
        	String errorMsg = "There is already a playlist with that name!";
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			return null;
		}

	}
	
	public String addSongs() {
		return "listAllSongs?faces-redirect=true";		
	}
	
	public String addSongToPlaylist() {
		if (!tracks.contains(song)) tracks.add(song);
		return null;
	}

	public String removeSongFromPlaylist() {
		log.info("Removing song from new playlist");
		log.debug("Removing song "+song.getTitle()+" from new playlist");
		tracks.remove(song);
		return null;
	}
	
	public String newPlaylist() {
		name = "";
		manager.setNewP(true);
		tracks = new ArrayList<Song>();
		return "playlist?faces-redirect=true";
	}
	
	public List<Song> listSongs() {
		if (manager.isNewP()) return tracks;
		else return manager.getSongs();
	}

	/********* Getters e Setters ************/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public List<Song> getTracks() {
		return tracks;
	}

	public void setTracks(List<Song> tracks) {
		this.tracks = tracks;
	}
	
}