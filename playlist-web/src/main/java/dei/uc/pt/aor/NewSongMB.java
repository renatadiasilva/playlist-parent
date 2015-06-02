package dei.uc.pt.aor;

import javax.enterprise.context.RequestScoped;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;

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
	
	@Inject
	ActiveUserMB aUser;

	public NewSongMB() {

	}
	
	public String addSong(ActiveUserMB auser) {

//		ver se ja existe song 
//		validaçao mais para o ano.
//		criar o path
		Song song = new Song(title, artist, album, Integer.parseInt(releaseYear), "C", auser.getCurrentUser());
		manager.addSong(song);
		return "listMySongs?faces-redirect=true";
// ver mais redirect				
//		ver para onde voltar dp do operation OK
//		} else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("erros!!!!"));
//		return "newSong";

	}
	
	public String mySongs() {
		return "listMySongs?faces-redirect=true";
	}
	
	public String newSong() {
		return "newSong?faces-redirect=true";
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