package dei.uc.pt.aor;

import java.util.List;

public interface PlaylistFacade {

	public abstract void save(Playlist playlist);
	public abstract Playlist update(Playlist playlist);
	public abstract void delete(Playlist playlist);
	public abstract Playlist find(int entityID);
	public abstract List<Playlist> findAll();
	public abstract List<Playlist> playlistsOfUser(User u);
	public abstract List<Song> getSongs(Playlist p);
	public abstract void removeSongFromPlaylist(Playlist p, Song s); 
	public abstract void addSongToPlaylist(Playlist p, Song s); 
}
