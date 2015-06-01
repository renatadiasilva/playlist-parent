package dei.uc.pt.aor;

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
	
	private String operation; 
	
	private Playlist playlist;
	
	private Song song;
	
	public PlaylistsManagerMB() {
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
	
	public Song getSonglist() {
		if(song == null){
			song = new Song();
		}
		return song;
	}

	public void setSonglist(Song song) {
		this.song = song;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

//	public void populate(){
//		entitiesEJB.populate();
//	}
	
	// tirar
	public List<Playlist> getAllPlaylists() {
		return playlistFacade.findAll();
	}

	public List<Playlist> playlistsOfUser(ActiveUserMB auser) {
		
		return playlistFacade.playlistsOfUser(auser.getCurrentUser());
	}

	public String updatePlaylistStart() {
		return "updatePlaylist";
	}
	
	public String goToProfile() {
		return "profile";
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: Check if the name is empty."));
			return "profile";
		}

	}
	
	public boolean removeUser(ActiveUserMB auser) {
		
		try {
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
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: Check if data is empty."));
					return "changePass";
				} 
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: Wrong old password."));
				return "changePass";			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: Passwords don't match."));
			return "changePass";
		}	

	}

	
	public String updatePlaylistEnd() {
		
		try {
			playlistFacade.update(playlist);
			operation = "Playlist update";
			return "operationOK?faces-redirect=true"; // voltar para trás???
		} catch (EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error. Check if the name is empty."));
			return null;
		}
		
	}
	
	public Song getSong() {
		return song;
	}

	public String deletePlaylistEnd(){
		
		try {
			playlistFacade.delete(playlist);
			return "listMyPlaylists";
		} catch (EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error deleting playlist."));
			return null;
		}
		
	}
	
	public String listMyPlaylists(){
		return "listMyPlaylists";
	}
	
	public List<Song> getAllSongs() {
		return songFacade.findAll();
	}
	
	public List<Song> songsOfUser(ActiveUserMB auser) {
		
		return songFacade.songsOfUser(auser.getCurrentUser());
	}

	public String updateSongStart() {
		return "updateSong";
	}
	
	public String updateSongEnd() {
		
		try {			
			songFacade.update(song);
			operation = "Song update";
			return "operationOK?faces-redirect=true"; // voltar para trás???
		} catch (EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error. Check if the name is empty."));
			return null;
		}
		
	}

	// não apagar da base de dados mas sim mudar o owner para admin
	public String deleteSongEnd(){
		
		try {
			User admin = findUserByEmail("admin@admin.com");
			
			song.setOwner(admin);
			songFacade.update(song);
			songFacade.delete(song);
			return "listMySongs";
		} catch (EJBException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error deleting song."));
			return null;
		}
		
	}
	
	public String changePass() {
		return "changePass";
	}
	
	public String remove() {
		return "confirmRemoval";
	}
	
	public String listMySongs(){
		return "listMySongs";
	}
	
	public void addUser(User user) {
		userFacade.addUser(user);
//		entitiesEJB.addUser(user);
	}
	
	public User findUserByEmail(String email) {
		return userFacade.findUserByEmail(email);
	}

	public List<User> getUsers() {
		return userFacade.getUsers();
//		return entitiesEJB.getUsers();
	}
	
	public List<User> getUsersStartingBy(String exp) {
		return userFacade.usersWithNameStartingBy(exp);
//		return entitiesEJB.usersWithNameStartingBy(exp);
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

}