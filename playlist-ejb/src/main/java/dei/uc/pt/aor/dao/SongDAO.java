package dei.uc.pt.aor.dao;

import javax.ejb.Stateless;

import dei.uc.pt.aor.Song;

@Stateless
public class SongDAO extends GenericDAO<Song> {
	
	public SongDAO() {
		super(Song.class);
	}

	public void delete(Song song) {
			super.delete(song.getId(), Song.class);
	}
	
}
