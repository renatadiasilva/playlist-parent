package dei.uc.pt.aor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "playlists")
@NamedQueries({
	@NamedQuery(name="Playlist.playlistOfUserByNameAsc",
			query="SELECT p FROM Playlist p WHERE p.owner = :ownerId ORDER BY p.name"),
	@NamedQuery(name="Playlist.playlistOfUserByDateAsc",
			query="SELECT p FROM Playlist p WHERE p.owner = :ownerId ORDER BY p.dateOfCriation, p.name"),
	@NamedQuery(name="Playlist.playlistOfUserByNameDesc",
			query="SELECT p FROM Playlist p WHERE p.owner = :ownerId ORDER BY p.name DESC"),
	@NamedQuery(name="Playlist.playlistOfUserByDateDesc",
			query="SELECT p FROM Playlist p WHERE p.owner = :ownerId ORDER BY p.dateOfCriation DESC, p.name"),
	@NamedQuery(name="Playlist.playlistOfUserBySizeAsc", 
			query="SELECT p FROM Playlist p WHERE p.owner = :ownerId ORDER BY p.size, p.name"),
	@NamedQuery(name="Playlist.playlistOfUserBySizeDesc", 
			query="SELECT p FROM Playlist p WHERE p.owner = :ownerId ORDER BY p.size DESC, p.name"),
	@NamedQuery(name="Playlist.playlistSameName",
			query="SELECT p FROM Playlist p WHERE p.owner = :ownerId AND p.name = :name"),
	@NamedQuery(name="Playlist.playlistsOfUserContainingSong",
			query="SELECT p FROM Playlist p JOIN p.songs s WHERE p.owner = :ownerId AND s.id = :idS"),
	@NamedQuery(name="Playlist.findPlaylistById",
		query="SELECT p FROM Playlist p WHERE p.id = :id"),
})
public class Playlist implements Serializable {

	private static final long serialVersionUID = -341288742583267978L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_creation", nullable = false)
	private Date dateOfCriation;
	
	@NotNull
	@Column(name = "size", nullable = false)
	private int size;
	
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;
	
	@ManyToMany
	@JoinTable(name = "playlist_contains_songs",
		joinColumns = @JoinColumn(name = "playlist_id"),
		inverseJoinColumns = @JoinColumn(name = "song_id"))
	private List<Song> songs;
	
	public Playlist() {
	}
	
	public Playlist(String name, Date dateOfCriation, User owner) {
		this.name = name;
		this.dateOfCriation = dateOfCriation;
		this.owner = owner;
		this.size = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfCriation() {
		return dateOfCriation;
	}

	public void setDateOfCriation(Date dateOfCriation) {
		this.dateOfCriation = dateOfCriation;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	public User getOwner() {
		return owner;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}
	
	public void addSong(Song s) {

		if (this.songs == null) this.songs = new ArrayList<Song>();
		this.songs.add(s);
		size++;
	}
	
	public void removeSong(Song s) {
		songs.remove(s);
		size--;
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
		Playlist other = (Playlist) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
