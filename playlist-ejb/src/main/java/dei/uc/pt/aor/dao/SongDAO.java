package dei.uc.pt.aor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import dei.uc.pt.aor.Song;
import dei.uc.pt.aor.User;

@Stateless
public class SongDAO extends GenericDAO<Song> {
	
	public SongDAO() {
		super(Song.class);
	}

	public void delete(Song song) {
		super.delete(song.getId(), Song.class);
	}

	public Song findSongById(Long id) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		List<Song> list = super.findSomeResults("Song.findSongById", parameters);
		if (list.size() == 1) return list.get(0);
		else return null;
	}
	
	public List<Song> songsOfUser(User u) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ownerId", u);
		return super.findSomeResults("Song.songsOfUser", parameters);
	}	
	
	public Song songsOfUserId(Long id, User u) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("ownerId", u);
		List<Song> list = super.findSomeResults("Song.songsOfUserId", parameters);
		if (list.size() == 1) return list.get(0);
		else return null;
 	}	

	public List<Song> songsOfUserOrderId(User u) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ownerId", u);
		return super.findSomeResults("Song.songsOfUserOrderId", parameters);
	}

	public List<Song> songsByArtistTitle(String expt, String expa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("t", expt);
		parameters.put("a", expa);
		return super.findSomeResults("Song.songsByArtistTitle", parameters);
	}

	public List<Song> findAllByOrder() {
		return super.findAllByOrder("Song.allSongs");
	}
	
}