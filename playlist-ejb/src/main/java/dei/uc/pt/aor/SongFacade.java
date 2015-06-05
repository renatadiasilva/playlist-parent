package dei.uc.pt.aor;

import java.util.List;

public interface SongFacade {

	public abstract void save(Song song);
	public abstract Song update(Song song);
	public abstract void delete(Song song);
	public abstract Song find(int entityID);
	public abstract List<Song> findAll();
	public abstract List<Song> findAllByOrder();
	public abstract List<Song> songsOfUser(User u);
	public abstract List<Song> songsByArtistTitle(String expt, String expa);

}
