package dei.uc.pt.aor;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.Calendar;

@Named
@RequestScoped
public class NewSongMB implements Serializable {

	private static final long serialVersionUID = -2624145242993606181L;
	private String title;
	private String artist;
	private String album;
	private String releaseYear;
	
	@Inject
	private PlaylistsManagerMB manager;
	
	public NewSongMB() {

	}
	
	public String addSong(ActiveUserMB auser, UploadFile uf) {

		if (releaseYear.matches("^\\d+$")) {
			
			int y = Integer.parseInt(releaseYear);
			
			if ( (y >= 1900) && (y <= Calendar.getInstance().get(Calendar.YEAR))) {
			
				Song song = new Song(title, artist, album, Integer.parseInt(releaseYear), "C", auser.getCurrentUser());
				manager.addSong(song);

				if (uf.upload(song.getId())) {
					song.setPathFile(uf.getPath());
					return manager.updateFilePath(song);
				} else return manager.deleteNewSong(song);
				
			} else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Release Year non valid!!"));

		} else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Release Year is not an integer number!!"));
		
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