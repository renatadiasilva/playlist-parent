package dei.uc.pt.aor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import dei.uc.pt.aor.Playlist;
import dei.uc.pt.aor.User;

@Stateless
public class PlaylistDAO extends GenericDAO<Playlist> {
	
	public PlaylistDAO() {
		super(Playlist.class);
	}

	public void delete(Playlist playlist) {
			super.delete(playlist.getId(), Playlist.class);
	}
	
	public List<Playlist> playlistsOfUser(User u) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ownerId", u);
		return super.findSomeResults("Playlist.playlistOfUserByNameAsc", parameters);
	}
	
}
