package dei.uc.pt.aor;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "songs")
@NamedQueries({
	@NamedQuery(name="Song.allSongs",
			query="SELECT s FROM Song s ORDER BY s.id"),
	@NamedQuery(name="Song.songsOfUser",
			query="SELECT s FROM Song s WHERE s.owner = :ownerId ORDER BY s.title"),
	@NamedQuery(name="Song.songsOfUserOrderId",
			query="SELECT s FROM Song s WHERE s.owner = :ownerId ORDER BY s.id"),
	@NamedQuery(name="Song.songsByArtistTitle",
			query="SELECT s FROM Song s WHERE UPPER(s.title) LIKE :t AND UPPER(s.artist) LIKE :a ORDER BY s.title"),
	@NamedQuery(name="Song.findSongById",
			query="SELECT s FROM Song s WHERE s.id = :id"),
	@NamedQuery(name="Song.songsOfUserId",
			query="SELECT s FROM Song s WHERE s.id = :id AND s.owner = :ownerId"),
})
@XmlRootElement
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
	@Column(name = "path_file", nullable = false)
	private String pathFile;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;
	
	@ManyToMany(mappedBy="songs")
	@XmlTransient
	private List<Playlist> playlists;

	////Boolean para saber se a Song tem lyric
	@Column(name = "hasLyric")
	private boolean hasLyric;
	
	@OneToMany(mappedBy="music")
	@XmlTransient
	private List<Lyric> lyrics;
	
	public Song() {
	}

	public Song(String title, String artist, String album, int releaseYear,
			String path, User owner) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.releaseYear = releaseYear;
		this.pathFile = path;
		this.owner = owner;
		this.hasLyric = false;
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

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String path) {
		this.pathFile = path;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public boolean isHasLyric() {
		return hasLyric;
	}

	public void setHasLyric(boolean hasLyric) {
		this.hasLyric = hasLyric;
	}
	
	@XmlTransient
	public List<Lyric> getLyrics() {
		return lyrics;
	}

	public void setLyrics(List<Lyric> lyrics) {
		this.lyrics = lyrics;
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
	
	public String toString() {
		return title + " -> " + owner.getEmail();
	}

}
