package dei.uc.pt.aor;

import java.util.List;

public interface PlaylistFacade {

	public abstract void save(Playlist playlist);
	public abstract boolean addPlaylist(User u, String name, List<Song> tracks);
	public abstract Playlist update(Playlist playlist);
	public abstract boolean updatePlaylistName(Playlist playlist, String playlistName, User u);
	public abstract void delete(Playlist playlist);
	public abstract Playlist find(int entityID);
	public abstract Playlist findPlaylistById(Long id);
	public abstract List<Playlist> findAllByOrder();
	public abstract List<Playlist> findAll();
	public abstract List<Playlist> playlistsOfUser(User u, int order);
	public abstract List<Song> getSongs(Playlist p);
	public abstract List<Song> getSongsByOrder(Playlist p);
	public abstract void removeSongFromPlaylist(Playlist p, Song s); 
	public abstract void addSongToPlaylist(Playlist p, Song s);
	public abstract List<Playlist> playlistSameName(User u, String name);
	public abstract List<Playlist> playlistsOfUserContainingSong(User u, Song s);
}
