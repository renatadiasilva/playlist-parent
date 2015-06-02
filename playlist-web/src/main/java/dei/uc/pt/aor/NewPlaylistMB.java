package dei.uc.pt.aor;

import javax.enterprise.context.SessionScoped;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named
@SessionScoped
public class NewPlaylistMB implements Serializable {

	private static final long serialVersionUID = -2624145242993606181L;
	private String name;
	private Song song;
	private List<Song> tracks;
	
	@Inject
	private PlaylistsManagerMB manager;
	
	@Inject
	ActiveUserMB aUser;

	public NewPlaylistMB() {
		tracks = new ArrayList<Song>();
	}
	
	public String addPlaylist(ActiveUserMB auser) {

//		ver se ja existe playlist c esse nome
//		validaçao mais para o ano.
//		criar o path
		Playlist playlist = new Playlist(name, new Date(), auser.getCurrentUser());
		for (Song s : tracks) {
			playlist.addSong(s);
		}
		playlist.setSize(tracks.size());
		manager.addPlaylist(playlist);
		return "listMyPlaylists?faces-redirect=true";
//		ver para onde voltar dp do operation OK
//		} else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("erros!!!!"));
//		return "newSong";

	}
	
	public String addSongs() {
		return "listAllSongs?faces-redirect=true";		
	}
	
//	n deixar adicionar se ja existe na playlist
	public String addSongToPlaylist() {
		if (!tracks.contains(song)) tracks.add(song);
		return null;
	}

	public String removeSongFromPlaylist() {
		tracks.remove(song);
		return "newPlaylist";
	}
	
	public String myPlaylists() {
		return "listMyPlaylists?faces-redirect=true";
	}
	
	public String newPlaylist() {
		return "newPlaylist?faces-redirect=true";
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