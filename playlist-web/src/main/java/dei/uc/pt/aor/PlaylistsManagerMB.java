package dei.uc.pt.aor;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class PlaylistsManagerMB implements Serializable {

	private static final long serialVersionUID = -6930815753145461767L;

	@EJB
	private EncryptPass epw;
	
	@EJB
	private PlaylistFacade playlistFacade;
	
	@EJB
	private SongFacade songFacade;

	@EJB
	private UserFacade userFacade;

	private String name;
	private String password;
	private String repeatedPassword;
	private String playlistName;
	
	private String operation; 
	
	private Playlist playlist;
	
	private Song song;
	
	private boolean play;
	
	public PlaylistsManagerMB() {
		setPlay(false);
	}
	
	public Playlist getPlaylist() {
		if(playlist == null){
			playlist = new Playlist();
		}
		return playlist;
	}
	
	public void setSong(Song song) {
		this.song = song;
	}
	
	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	// tirar
	public List<Playlist> getAllPlaylists() {
		return playlistFacade.findAll();
	}

	public List<Playlist> playlistsOfUser(ActiveUserMB auser) {
		return playlistFacade.playlistsOfUser(auser.getCurrentUser());
	}

	public String updatePlaylistStart() {
		playlistName = playlist.getName();
		return "updatePlaylist?faces-redirect=true";
	}
	
	public String viewPlaylistStart() {
		play = false;
		return "viewPlaylist?faces-redirect=true";
	}	
	
	public String goToProfile() {
		return "profile?faces-redirect=true";
	}

	public String updateProfile(ActiveUserMB auser) {

		User u = auser.getCurrentUser();
		
		try {
			u.setName(name);
			auser.setName(name);
			userFacade.update(u);
			operation = "User Name Update";
			return "operationOK?faces-redirect=true";
		} catch (EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error updating the name."));
			return "profile";
		}

	}
	
	public boolean removeUser(ActiveUserMB auser) {
		
		try {
			User u = auser.getCurrentUser();
			List<Song> uSongs = songFacade.songsOfUser(u);
			for (Song s : uSongs) {
				try {
					User admin = findUserByEmail("admin@admin.com");

					s.setOwner(admin);
					songFacade.update(s);
				} catch (EJBException e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error deleting song."));
					return false;
				}
			}
			
			List<Playlist> uPlaylists = playlistFacade.playlistsOfUser(u);
			for (Playlist p: uPlaylists) playlistFacade.delete(p);
			
			userFacade.delete(auser.getCurrentUser());
			return true;
		} catch (EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error deleting user."));
			return false;
		}
		
	}
	
	public String updatePass(String email, String pass) {
	
		User u = findUserByEmail(email);
		
		if (password.equals(repeatedPassword)) {
			if (u.getPassword().equals(epw.encrypt(pass))) {
				try {
					u.setPassword(epw.encrypt(password));
					userFacade.update(u);
					operation = "User Password Update";
					return "operationOK?faces-redirect=true";
				} catch (EJBException e) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error updating password."));
					return "changePass";
				} 
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Wrong old password."));
				return "changePass";			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Passwords don't match."));
			return "changePass";
		}	

	}
	
	public String updatePlaylistEnd(ActiveUserMB auser) {
		
		if ( (playlistName.equals(playlist.getName())) || 
				(playlistSameName(auser, playlistName).size() == 0) ) {

			try {
				playlist.setName(playlistName);
				playlistFacade.update(playlist);
				return "listMyPlaylists";
			} catch (EJBException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error updating playlist."));
				return null;
			}
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There is already a playlist with that name!"));
			return null;
		}
		
	}
		
	public Song getSong() {
		return song;
	}

	public String deletePlaylistEnd(){
		
		try {
			playlistFacade.delete(playlist);
			return "listMyPlaylists?faces-redirect=true";
		} catch (EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error deleting playlist."));
			return null;
		}
		
	}
	
	public String listMyPlaylists(){
		return "listMyPlaylists?faces-redirect=true";
	}
	
	public List<Playlist> playlistSameName(ActiveUserMB auser, String name) {
		return playlistFacade.playlistSameName(auser.getCurrentUser(), name);
	}

	public void addPlaylist(Playlist p) {
		playlistFacade.save(p);
	}
	
	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}
	
	public String removeSongFromPlaylist() {
		playlistFacade.removeSongFromPlaylist(playlist, song);
		return "updatePlaylist";
	}
	
	public String addSongToPlaylist() {
		playlistFacade.addSongToPlaylist(playlist, song);
		return "updatePlaylist";
	}
	

	public void addSong(Song s) {
		songFacade.save(s);
	}
	
	public List<Song> getAllSongs() {
		return songFacade.findAll();
	}
	
	public List<Song> songsOfUser(ActiveUserMB auser) {
		
		return songFacade.songsOfUser(auser.getCurrentUser());
	}

	public String updateSongStart() {
		return "updateSong?faces-redirect=true";
	}
	
	public String updateFilePath(Song s) {
		
		try {			
			songFacade.update(s);
			return "listMySongs?faces-redirect=true";
		} catch (EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error updating song path file."));
			return null;
		}

	}

	public String updateSongEnd() {
		
		try {			
			songFacade.update(song);
			return "listMySongs?faces-redirect=true";
		} catch (EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error updating song."));
			return null;
		}
		
	}
	
	public String deleteNewSong(Song s) {
		try {
			songFacade.delete(s); 
		} catch (EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error deleting new song"));
		}
		return null;
	
	}

	public String deleteSongEnd(ActiveUserMB auser) {
		
		if (auser.isAdmin()) {
			songFacade.delete(song);
			return "listMySongs";
		} else { 
			try {
				User admin = findUserByEmail("admin@admin.com");

				song.setOwner(admin);
				songFacade.update(song);
				return "listMySongs";
			} catch (EJBException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error deleting song."));
				return null;
			}
		} 
	}
	
	public String changePass() {
		return "changePass?faces-redirect=true";
	}
	
	public String remove() {
		return "confirmRemoval?faces-redirect=true";
	}
	
	public String listMySongs(){
		return "listMySongs?faces-redirect=true";
	}
	
	public void addUser(User user) {
		userFacade.addUser(user);
	}
	
	public User findUserByEmail(String email) {
		return userFacade.findUserByEmail(email);
	}

	public List<User> getUsers() {
		return userFacade.getUsers();
	}
	
	public List<User> getUsersStartingBy(String exp) {
		return userFacade.usersWithNameStartingBy(exp);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}
	
	public void start() {
		play = true;
	}

	public List<Song> getSongs() {
		return playlistFacade.getSongs(playlist);
	}
	
	// change to loop
	public String path() {
		if (song != null) return song.getPathFile();
		return "";
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

}