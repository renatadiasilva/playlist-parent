package dei.uc.pt.aor;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "users")
@NamedQueries({
	@NamedQuery(name="User.findUserByEmail", 
			query="SELECT u FROM User u WHERE u.email = :email"),
	@NamedQuery(name="User.findUserStartingBy",
			query="SELECT u FROM User u WHERE u.name like :exp"),
	@NamedQuery(name="User.findUserById", 
		query="SELECT u FROM User u WHERE u.id = :id"),
	@NamedQuery(name="User.findAllByIdOrder", 
		query="SELECT u FROM User u ORDER BY u.id")
}) 
@XmlType(propOrder = {
	    "id",
	    "email",
	    "name",
	    "password"
	})
@XmlRootElement
public class User implements Serializable {

	private static final long serialVersionUID = -3895128862200329846L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true)
	private Long id;
	
	@NotNull
	@Size(max=50)
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@NotNull
	@Column(name = "password", nullable = false)
	private String password;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "owner")
	@OrderBy("name")
//	@XmlTransient
	private List<Playlist> playlists;

	@OneToMany(mappedBy = "owner")
	@XmlTransient
	private List<Song> songs;
	
	@OneToMany(mappedBy = "owner")
	@XmlTransient
	private List<Lyric> lyrics;

	@ManyToMany(mappedBy = "owners")
	@XmlTransient
	private List<Role> roles;
	
	public User() {
	}
	
	public User(String name, String password, String email) {
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlTransient
	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	@XmlTransient
	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

	@XmlTransient
	public List<Lyric> getLyrics() {
		return lyrics;
	}
	
	public void setLyrics(List<Lyric> lyrics) {
		this.lyrics = lyrics;
	}
	
	@XmlTransient
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String toString() {
		return name + " -> " + email;
	}

}
