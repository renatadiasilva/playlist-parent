package dei.uc.pt.aor;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Named
@RequestScoped
public class NewSongMB implements Serializable {

	private static final long serialVersionUID = -2624145242993606181L;

	private static final Logger log = LoggerFactory.getLogger(NewSongMB.class);

	private String title;
	private String artist;
	private String album;
	private String releaseYear;
	
	@Inject
	private PlaylistsManagerMB manager;
	
	@Inject
	private SongFacade songFacade;

	public NewSongMB() {

	}
	
	public String addSong(ActiveUserMB auser, UploadFile uf) {
		log.info("Adding new song");
		log.debug("Adding new song with"+title);

		User u = auser.getCurrentUser();
		Song s = songFacade.addSong(title, artist, album, releaseYear, u);

		if (s != null) {
			log.info("Uploading song file");		
			if (uf.upload(s.getId())) {
				s.setPathFile(uf.getPath());
				return manager.updateFilePath(s);
			} else return manager.deleteNewSong(s);
		} else {
	        String errorMsg = "Release Year non valid!";
	        log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
		return null;

	}
	
	public String newSong() {
		title = "";
		artist = "";
		album = "";
		releaseYear = "";
		manager.setNewS(true);
		return "song?faces-redirect=true";
	}

	/********* Getters e Setters ************/
	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public void setUserName(String name) {
		this.releaseYear = name;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String password) {
		this.artist = password;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String repeatedPassword) {
		this.album = repeatedPassword;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String email) {
		this.title = email;
	}
	
}