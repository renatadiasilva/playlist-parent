package dei.uc.pt.aor;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import wsconsumer.SearchLyric;

import java.io.Serializable;

@Named
@SessionScoped
public class PlaylistsManagerMB implements Serializable {

	// Attributes
	
	private static final long serialVersionUID = -6930815753145461767L;
	private static final Logger log = LoggerFactory.getLogger(PlaylistsManagerMB.class);

	@EJB
	private PlaylistFacade playlistFacade;
	@EJB
	private SongFacade songFacade;
	@EJB
	private UserFacade userFacade;
	@EJB
	private LyricFacade lyrFacade;

	private String name;
	private String password;
	private String repeatedPassword;
	private String playlistName;
	private String releaseY;
	private String searchTitle;
	private String searchArtist;
	private boolean play;
	private boolean newP;
	private boolean newS;
	private boolean delP;
	private boolean search;
	private int order;
	private boolean show;
	private List<Song> searchList;
	private String operation; 
	private Playlist playlist;
	private Song song;
	private Lyric lyrics;
	
	// Constructor

	public PlaylistsManagerMB() {
		play = false;
		order = 1;
		show = false;
	}
	
	// USER //
	
	public String goToProfile() {
		return "profile?faces-redirect=true";
	}

	public String updateProfile(ActiveUserMB auser) {
		log.info("Updating profile of user");
		User u = auser.getCurrentUser();
		log.debug("Updating profile of user "+u.getEmail());
		auser.setName(name);
		userFacade.updateUserName(u, name);
		operation = "User Name Update";
		return "operationOK?faces-redirect=true";

	}

	public boolean removeUser(ActiveUserMB auser) {
		User u = auser.getCurrentUser();
		String uemail = u.getEmail();
		log.info("Removing completely user account ");
		log.debug("Removing completely user account "+uemail);
		userFacade.removeUser(u);
		return true;
	}

	public String updatePass(ActiveUserMB auser) {
		User u = auser.getCurrentUser();
		log.info("Updating password");
		log.debug("Updating password of "+u.getEmail());

		if (password.equals(repeatedPassword)) {
			if (userFacade.updateUserPass(u, auser.getPassword(), password)) {
				operation = "User Password Update";
				return "operationOK?faces-redirect=true";
			} else {
				String errorMsg = "Wrong old password.";
				log.error(errorMsg);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
				return "changePass";		
			}
		} else {
			String errorMsg = "Passwords don't match.";
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			return "changePass";
		}	

	}

	public String changePass() {
		return "changePass?faces-redirect=true";
	}

	public String remove() {
		return "confirmRemoval?faces-redirect=true";
	}

	public User addUser(String n, String p, String e) {
		try {
			User u = userFacade.addUser(n, p, e);
			return u;
		} catch (EJBException ejbe) {
			String errorMsg = "Error adding user"
					+ejbe.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			return null;
		}
	}

	public User findUserByEmail(String email) {
		try {
			return userFacade.findUserByEmail(email);
		} catch (EJBException e) {
			String errorMsg = "Error finding user "
					+ "by email: "+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		return null;
	}

	public List<User> getUsers() {
		try {
			return userFacade.getUsers();
		} catch (EJBException e) {
			String errorMsg = "Error getting users"
					+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		return null;
	}

	public List<User> getUsersStartingBy(String exp) {
		try {
			return userFacade.usersWithNameStartingBy(exp);
		} catch (EJBException e) {
			String errorMsg = "Error getting users"
					+ "with name starting by: "+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}

		return null;
	}
	
	// PLAYLIST //
	
	public List<Playlist> getAllPlaylists() {
		return playlistFacade.findAll();
	}

	public List<Playlist> playlistsOfUser(ActiveUserMB auser) {
		return playlistFacade.playlistsOfUser(auser.getCurrentUser(), order);
	}

	public List<Playlist> playlistsOfUserContainingSong(ActiveUserMB auser) {
		return playlistFacade.playlistsOfUserContainingSong(auser.getCurrentUser(), song);
	}
	
	public String updatePlaylistStart() {
		playlistName = playlist.getName();
		newP = false;
		return "playlist?faces-redirect=true";
	}

	public String viewPlaylistStart() {
		play = false;
		return "viewPlaylist?faces-redirect=true";
	}	

	public String updatePlaylistEnd(ActiveUserMB auser) {
		log.info("Updating playlist");
		log.debug("Updating playlist "+playlist.getName());
		if (playlistFacade.updatePlaylistName(playlist, playlistName, auser.getCurrentUser())) {
				return "listMyPlaylists?faces-redirect=true";
		} else {
			String errorMsg = "There is already a playlist with that name!";
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			return null;
		}

	}

	public String deletePlaylistStart() {
		delP = true;
		return "delete?faces-redirect=true";
	}

	public String deletePlaylistEnd() {

		try {
			playlistFacade.delete(playlist);
			return "listMyPlaylists?faces-redirect=true";
		} catch (EJBException e) {
			String errorMsg = "Error deleting playlist: "+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			return null;
		}

	}

	public String listMyPlaylists(){
		return "listMyPlaylists?faces-redirect=true";
	}
	
	public void start() {
		play = true;
	}	
	
	public String chooseOrder(int order) {
		this.order = order;
		return "listMyPlaylists";
	}

	// SONG //
	
	public String deleteSongStart() {
		delP = false;
		return "delete?faces-redirect=true";
	}

	public List<Song> getSongs() {
		try {
			searchList = playlistFacade.getSongs(playlist);
			return searchList;
		} catch (EJBException e) {
			String errorMsg = "Error getting songs"
					+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		return null;
	}

	public int index(Song s) {
		return searchList.indexOf(s)+1;
	}

	// change to loop
	public String path() {
		if (song != null) return song.getPathFile();
		return "";
	}

	public String removeSongFromPlaylist() {
		try {
			playlistFacade.removeSongFromPlaylist(playlist, song);
			newP = false;
			return null;
		} catch (EJBException e) {
			String errorMsg = "Error while removing"
					+ "song from new playlist: "+
					e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}

		return null;

	}

	public String addToPlaylist() {
		return "addToPlaylist?faces-redirect=true";
	}

	public String removeSongFromPlaylist2() {
		try {
			playlistFacade.removeSongFromPlaylist(playlist, song);
			return "addToPlaylist";
		} catch (EJBException e) {
			String errorMsg = "Error removing song"
					+ "from playlist: "+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		return null;
	}

	public String addSongToPlaylist() {
		try {
			playlistFacade.addSongToPlaylist(playlist, song);
			newP = false;
			return null;
		} catch (EJBException e) {
			String errorMsg = "Error adding song"
					+ "to playlist: "+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		return null;
	}

	public String addSongToPlaylist2() {
		try {
			playlistFacade.addSongToPlaylist(playlist, song);
			return "addToPlaylist";
		} catch (EJBException e) {
			String errorMsg = "Error adding song"
					+ "to new playlist: "+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		return null;
	}

	public List<Song> getAllSongs() {
		try {
			return songFacade.findAllByOrder();
		} catch (EJBException e) {
			String errorMsg = "Error adding getting"
					+ "all songs: "+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		return null;
	}

	public List<Song> songsOfUser(ActiveUserMB auser) {
		try {
			return songFacade.songsOfUser(auser.getCurrentUser());
		} catch (EJBException e) {
			String errorMsg = "Error listing songs"
					+ "of User: "+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		return null;
	}

	public String updateSongStart() {
		releaseY = ""+song.getReleaseYear();
		newS = false;
		return "song?faces-redirect=true";
	}

	public String updateFilePath(Song s) {
		log.info("Updating file path");
		log.debug("Updating file path of "+s.getTitle());
		try {			
			songFacade.update(s);
			return "listMySongs?faces-redirect=true";
		} catch (EJBException e) {
			String errorMsg = "Error updating song path file: "+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			return null;
		}

	}

	public String updateSongEnd() {
		log.info("Updating song");
		log.debug("Updating song "+song.getTitle());
		if (songFacade.updateSongData(song, releaseY)) {
			return "listMySongs?faces-redirect=true";
		} else {
			String errorMsg = "Release Year non valid!";
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		return null;
	}

	public String deleteNewSong(Song s) {
		log.info("Deleting new song");
		try {
			songFacade.delete(s); 
		} catch (EJBException e) {
			String errorMsg = "Error deleting new song: "+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		return null;
	}

	public String deleteSongEnd() {
		log.info("Changing ownership of song to admin");
		log.debug("Changing ownership of song "+song.getTitle()+"to admin");
		try {
			songFacade.songToAdmin(song);
			return "listMySongs";
		} catch (EJBException e) {
			String errorMsg = "Error deleting song: "+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			return null;
		}
	}

	public String search() {
		search = true;
		searchArtist = "";
		searchTitle = "";
		searchList = new ArrayList<>();
		return "search?faces-redirect=true";
	}

	public String goAllSongs() {
		search = false;
		return "listAllSongs?faces-redirect=true";
	}


	public String goBackSearch() {
		if (search) return "search?faces-redirect=true";
		else return "listAllSongs?faces-redirect=true";
	}

	public String searchSongs() {
		String expa = "%"+searchTitle.toUpperCase()+"%";
		String expt = "%"+searchArtist.toUpperCase()+"%";
		log.info("Searching songs");
		log.debug("Searching songs with title containing "
				+expa+" and with artist containing "+expt);
		try {
			searchList = songFacade.songsByArtistTitle(expa, expt);
			return "search";
		} catch (EJBException e) {
			String errorMsg = "Error searching songs"
					+e.getMessage();
			log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		return null;
	}

	public String listMySongs(){
		return "listMySongs?faces-redirect=true";
	}
	
	// LYRICS //
	
	public String showLyrics(ActiveUserMB auser, Song s) {
		log.info("Opening pop-up of lyrics");
		Lyric l = lyrFacade.getLyricSongUser(auser.getCurrentUser(), s);
		lyrics = l;
		show = true;
		return null;
	}
	
	public String updateLyrics(ActiveUserMB auser) {
		log.info("Updating lyrics");
		lyrFacade.editLyric(auser.getCurrentUser(), lyrics);
		return null;
	}
	
	public String searchLyrics() {
		log.info("Searching for lyrics");
		SearchLyric searchlyric = new SearchLyric();
		String text = searchlyric.getLyric(song.getArtist(), song.getTitle());
		
		if (!text.equals("") && text!=null){
			song.setHasLyric(true);
			lyrFacade.addLyric(text,song);
			songFacade.update(song);
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No search results for lyric!"));
		}
		
		return null;
	}
	
	public String closePopup(){
		log.info("Closing pop-up of lyrics");
		show = false;
		return null;
	}

	public List<Song> getSearchList() {
		if (searchList == null) searchList = new ArrayList<>();
		return searchList;
	}

	// GETTERS e SETTERS //

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

	public void setPlaylist(Playlist playlist) {
		this.playlist = playlist;
	}
	
	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public boolean isNewP() {
		return newP;
	}

	public void setNewP(boolean newP) {
		this.newP = newP;
	}

	public boolean isDelP() {
		return delP;
	}

	public void setDelP(boolean delP) {
		this.delP = delP;
	}

	public boolean isNewS() {
		return newS;
	}

	public void setNewS(boolean newS) {
		this.newS = newS;
	}

	public String getReleaseY() {
		return releaseY;
	}

	public void setReleaseY(String releaseY) {
		this.releaseY = releaseY;
	}

	public void setSearchList(List<Song> searchList) {
		this.searchList = searchList;
	}

	public String getSearchTitle() {
		return searchTitle;
	}

	public void setSearchTitle(String searchTitle) {
		this.searchTitle = searchTitle;
	}
	
	public Song getSong() {
		return song;
	}

	public String getSearchArtist() {
		return searchArtist;
	}

	public void setSearchArtist(String searchArtist) {
		this.searchArtist = searchArtist;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public String getLyrics() {
		return lyrics.getLyric();
	}

	public void setLyrics(String text) {
		lyrics = lyrFacade.getLyricById(lyrics.getId());
		lyrics.setLyric(text);
	}

	public Playlist getPlaylist() {
		if(playlist == null) playlist = new Playlist();
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

}