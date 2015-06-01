package dei.uc.pt.aor;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "songs")
public class Song implements Serializable {
	
	private static final long serialVersionUID = -846738109409670761L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	
	@NotNull
	@Column(name = "title", nullable = false)
	private String title;
	
	@NotNull
	@Column(name = "artist", nullable = false)
	private String artist;
	
	@NotNull
	@Column(name = "album", nullable = false)
	private String album;
	
	@NotNull
	@Column(name = "release_year", nullable = false)
	private int releaseYear;
	
	@NotNull
	@Column(name = "path_upload", nullable = false)
	private String pathUpload;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;
	
	@ManyToMany (mappedBy="songs")
	private List<Playlist> playlists;

	public Song() {
	}

	public Song(String title, String artist, String album, int releaseYear,
			String pathUpload, User owner) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.releaseYear = releaseYear;
		this.pathUpload = pathUpload;
		this.owner = owner;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getPathUpload() {
		return pathUpload;
	}

	public void setPathUpload(String pathUpload) {
		this.pathUpload = pathUpload;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Song other = (Song) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
