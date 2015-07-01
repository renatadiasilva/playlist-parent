package dei.uc.pt.aor;

import java.util.List;

public interface SongFacade {

	public abstract void save(Song song);
	public abstract Song addSong(String title, String artist, String album, 
			String releaseYear, User u);
	public abstract Song update(Song song);
	public abstract boolean updateSongData(Song song, String releaseY);
	public abstract void delete(Song song);
	public abstract boolean songToAdmin(Song song);
	public abstract Song findSongById(Long id);
	public abstract Song find(int entityID);
	public abstract List<Song> findAll();
	public abstract List<Song> findAllByOrder();
	public abstract List<Song> songsOfUser(User u);
	public abstract boolean deleteSongOfUser(Long sid, Long uid);
	public abstract boolean deleteSongOfUserEmail(Long sid, String uemail);
	public abstract List<Song> songsOfUserOrderId(User u);
	public abstract List<Song> songsByArtistTitle(String expt, String expa);

}