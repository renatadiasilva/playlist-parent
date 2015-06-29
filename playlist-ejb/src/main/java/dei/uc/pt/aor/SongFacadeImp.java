package dei.uc.pt.aor;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dei.uc.pt.aor.dao.SongDAO;

@Stateless
public class SongFacadeImp implements SongFacade {

	private static final Logger log = LoggerFactory.getLogger(SongFacadeImp.class);

	@EJB
	private SongDAO songDAO;
	
	public void save(Song song) {
		log.info("Saving song in DB");
		isSongWithAllData(song);
		songDAO.save(song);
	}
	
	public Song update(Song song) {
		log.info("Updating song of DB");
		isSongWithAllData(song);
		return songDAO.update(song);
	}
	
	public void delete(Song song) {
		log.info("Deleting song from DB");
		songDAO.delete(song);
	}
	
	public Song find(int entityID) {
		log.info("Finding song with ID");
		return songDAO.find(entityID);
	}
	
	//duplicate??
	public Song findSongById(Long id) {
		log.info("Finding song by id");
		return songDAO.findSongById(id);
	}
	
	public List<Song> findAll() {
		log.info("Finding all songs");
		return songDAO.findAll();
	}
	
	public List<Song> findAllByOrder() {
		log.info("Finding all songs ordered by name");
		return songDAO.findAllByOrder();
	}

	public List<Song> songsOfUser(User u) {
		log.info("Finding all songs of user");
		return songDAO.songsOfUser(u);
	}
	
	public List<Song> songsOfUserOrderId(User u) {
		log.info("Finding all songs of user");
		return songDAO.songsOfUserOrderId(u);
	}

	public List<Song> songsByArtistTitle(String expt, String expa) {
		log.info("Finding songs by artist and/or title");
		return songDAO.songsByArtistTitle(expt, expa);
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

		if (song.getPathFile() == null || "".equals(song.getPathFile().trim())){
			hasError = true;
		}

		if (song.getReleaseYear() <= 0) {
			hasError = true;
		}

		if (hasError) {
			throw new IllegalArgumentException("The song is missing data. Check the title, artist, album"
					+ "path upload, and year of release, they should have value.");
		}
	}

}