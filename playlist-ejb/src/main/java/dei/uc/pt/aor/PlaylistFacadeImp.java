package dei.uc.pt.aor;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dei.uc.pt.aor.dao.PlaylistDAO;

@Stateless
public class PlaylistFacadeImp implements PlaylistFacade {

	// meter cenas info, debug e error
	private static final Logger log = LoggerFactory.getLogger(PlaylistFacadeImp.class);
	
	@EJB
	private PlaylistDAO playlistDAO;
	
	public void save(Playlist playlist) {
		isPlaylistWithAllData(playlist);
		playlistDAO.save(playlist);
	}
	
	public Playlist update(Playlist playlist) {
		isPlaylistWithAllData(playlist);
		return playlistDAO.update(playlist);
	}
	
	public void delete(Playlist playlist) {
		playlistDAO.delete(playlist);
	}
	
	public Playlist find(int entityID) {
		return playlistDAO.find(entityID);
	}
	
	//Não é necessária
	public List<Playlist> findAll() {
		return playlistDAO.findAll();
	}
	
	public List<Playlist> playlistsOfUser(User u) {
		return playlistDAO.playlistsOfUser(u);
	}
	
	private void isPlaylistWithAllData(Playlist playlist) {
		boolean hasError = false;
		if(playlist == null){
			hasError = true;
		}
		if (playlist.getName() == null || "".equals(playlist.getName().trim())){
			hasError = true;
		}
		
		//date of criation - today
		if (hasError){
			throw new IllegalArgumentException("The playlist is missing data. Check the name, it should have value.");
		}
	}

}