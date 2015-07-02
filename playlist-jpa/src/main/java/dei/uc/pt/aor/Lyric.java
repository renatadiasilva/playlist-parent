package dei.uc.pt.aor;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lyrics")
@NamedQueries({
	@NamedQuery(name="Lyric.lyricByUserAndSong",
			query="SELECT l FROM Lyric l WHERE l.owner = :owner AND l.music = :music ")
})
public class Lyric implements Serializable {
	
	private static final long serialVersionUID = -846738109409670761L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	
	@NotNull
	@Column(name = "lyric", nullable = false, length=5000)
	private String lyric;
		
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;
	
	@ManyToOne
	@JoinColumn(name = "music_id")
	private Song music;
	
	
	public Lyric() {
	}

	public Lyric(String lyric, User owner, Song music) {
		this.lyric = lyric;
		this.owner = owner;
		this.music = music;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Song getMusic() {
		return music;
	}

	public void setMusic(Song music) {
		this.music = music;
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
		Lyric other = (Lyric) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
