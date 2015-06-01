package dei.uc.pt.aor;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dei.uc.pt.aor.dao.SongDAO;

@Stateless
public class SongFacadeImp implements SongFacade {

	// meter cenas info, debug e error
	private static final Logger log = LoggerFactory.getLogger(SongFacadeImp.class);

	@EJB
	private SongDAO songDAO;
	
	public void save(Song song) {
		isSongWithAllData(song);
		songDAO.save(song);
	}
	
	public Song update(Song song) {
		isSongWithAllData(song);
		return songDAO.update(song);
	}
	
	public void delete(Song song) {
		songDAO.delete(song);
	}
	
	public Song find(int entityID) {
		return songDAO.find(entityID);
	}
	
	public List<Song> findAll() {
		return songDAO.findAll();
	}

	public List<Song> songsOfUser(User u) {
		return songDAO.songsOfUser(u);
	}
	
	private void isSongWithAllData(Song song) {
		boolean hasError = false;
		
		if (song == null) {
			hasError = true;
		}
		
		if (song.getTitle() == null || "".equals(song.getTitle().trim())){
			hasError = true;
		}
		
		if (song.getArtist() == null || "".equals(song.getArtist().trim())){
			hasError = true;
		}

		if (song.getAlbum() == null || "".equals(song.getAlbum().trim())){
			hasError = true;
		}

		if (song.getPathUpload() == null || "".equals(song.getPathUpload().trim())){
			hasError = true;
		}

		// > 1900?
		if (song.getReleaseYear() <= 0) {
			hasError = true;
		}

		if (hasError) {
			throw new IllegalArgumentException("The song is missing data. Check the title, artist, album"
					+ "path upload, and year of release, they should have value.");
		}
	}

}