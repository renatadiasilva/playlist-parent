package dei.uc.pt.aor;

import java.util.List;

public interface PlaylistFacade {

	public abstract void save(Playlist playlist);
	public abstract Playlist update(Playlist playlist);
	public abstract void delete(Playlist playlist);
	public abstract Playlist find(int entityID);
	public abstract List<Playlist> findAll();
	public List<Playlist> playlistsOfUser(User u);

}
