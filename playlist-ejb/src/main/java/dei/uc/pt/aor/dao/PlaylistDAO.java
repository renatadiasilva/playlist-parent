package dei.uc.pt.aor.dao;

import javax.ejb.Stateless;

import dei.uc.pt.aor.Playlist;

@Stateless
public class PlaylistDAO extends GenericDAO<Playlist> {
	
	public PlaylistDAO() {
		super(Playlist.class);
	}

	public void delete(Playlist playlist) {
			super.delete(playlist.getId(), Playlist.class);
	}
	
}
