package dei.uc.pt.aor;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dei.uc.pt.aor.dao.LyricDAO;
import dei.uc.pt.aor.dao.UserDAO;

@Stateless
public class LyricFacadeImp implements LyricFacade {

	private static final Logger log = LoggerFactory.getLogger(LyricFacadeImp.class);
	
	@EJB
	private LyricDAO lyrDAO;

	@EJB
	private UserDAO userDAO;

	// first time that someone click on Get Lyrics
	public Lyric addLyric(String text, Song s) {
		log.info("Adding lyric to DB (owner = ADMIN)");
		User admin = userDAO.findUserByEmail("admin@admin.com");
		Lyric l = new Lyric(text, admin, s);
		lyrDAO.save(l);
		return l;
	}

	// if hasLyrics!!! (to pop-up)
	public Lyric getLyricSongUser(User u, Song s) {
		log.info("Getting lyric of DB (for a particular user)");
		Lyric l = lyrDAO.findLyricByUserAndSong(u, s);
		if (l == null) {
			User admin = userDAO.findUserByEmail("admin@admin.com");
			l = lyrDAO.findLyricByUserAndSong(admin, s);
		}
		
		return l;
	}
	
	// if hasLyrics and already in the pop-up (we know the Lyrics)
	public void editLyric(User u, Lyric lyric) {
		log.info("Editing lyric of DB (for a particular user)");
		User admin = userDAO.findUserByEmail("admin@admin.com");
		if (lyric.getOwner().equals(admin)) { //create new lyric
			Lyric l = new Lyric(lyric.getLyric(), u, lyric.getMusic());
			lyrDAO.save(l);
		} else lyrDAO.update(lyric);
	}
	
	public Lyric getLyricById(Long id) {
		log.info("Getting lyric of DB by id");
		Lyric l = lyrDAO.findLyricById(id);
		return l;
	}
	
}