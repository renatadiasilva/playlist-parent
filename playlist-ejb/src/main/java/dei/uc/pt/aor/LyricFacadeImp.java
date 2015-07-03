package dei.uc.pt.aor;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import dei.uc.pt.aor.dao.LyricDAO;
import dei.uc.pt.aor.dao.UserDAO;

@Stateless
public class LyricFacadeImp implements LyricFacade {

	@EJB
	private LyricDAO lyrDAO;

	@EJB
	private UserDAO userDAO;

	// texto não vazio!!! quando é criada ou a primeira vez user
	public Lyric addLyric(String text, Song s) {
		User admin = userDAO.findUserByEmail("admin@admin.com");
		Lyric l = new Lyric(text, admin, s);
		lyrDAO.save(l);
		return l;
	}
	
	// if hasLyrics!!! (to pop-up)
	public Lyric getLyricSongUser(User u, Song s) {
		Lyric l = lyrDAO.findLyricByUserAndSong(u, s);
		if (l == null) {
			User admin = userDAO.findUserByEmail("admin@admin.com");
			l = lyrDAO.findLyricByUserAndSong(admin, s);
		}
		
		return l;
	}
	
	// if hasLyrics and already in the pop-up (we know the Lyrics)
	public void editLyric(User u, Lyric lyric) {
		User admin = userDAO.findUserByEmail("admin@admin.com");
		if (lyric.getOwner().equals(admin)) { //create new lyric
			Lyric l = new Lyric(lyric.getLyric(), u, lyric.getMusic());
			lyrDAO.save(l);
		} else lyrDAO.update(lyric);
	}
	
	public Lyric getLyricById(Long id) {
		Lyric l = lyrDAO.findLyricById(id);
		return l;
	}
	
}