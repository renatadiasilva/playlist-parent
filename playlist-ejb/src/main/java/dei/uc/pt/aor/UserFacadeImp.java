package dei.uc.pt.aor;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dei.uc.pt.aor.dao.LyricDAO;
import dei.uc.pt.aor.dao.PlaylistDAO;
import dei.uc.pt.aor.dao.SongDAO;
import dei.uc.pt.aor.dao.UserDAO;

@Stateless
public class UserFacadeImp implements UserFacade {

	private static final Logger log = LoggerFactory.getLogger(UserFacadeImp.class);

	@EJB
	private UserDAO userDAO;

	@EJB
	private SongDAO songDAO;
	
	@EJB
	private PlaylistDAO playDAO;
	
	@EJB
	private LyricDAO lyricDAO;

	@EJB
	private EncryptPass epw;

	public void delete(User u) {
		log.info("Removing user from DB");
		userDAO.delete(u);
	}
	
	public boolean removeUser(User user) {
		if (user != null) { //user exists
			List<Song> uSongs = songDAO.songsOfUser(user);
			if (uSongs.size() != 0) {
				User admin = userDAO.findUserByEmail("admin@admin.com");
				//remove user songs (ownership to ADMIN)
				for (Song s : uSongs) {
					s.setOwner(admin);
					songDAO.update(s);
				}
			}
			
			//remove playlists of user
			List<Playlist> uPlaylists = playDAO.playlistsOfUser(user, 1);
			for (Playlist p: uPlaylists) playDAO.delete(p);
			
			//remove lyrics of user
			List<Lyric> uLyrics = lyricDAO.findLyricsByUser(user);
			for (Lyric l : uLyrics) lyricDAO.delete(l);
			
			
			//finally, remove user
			userDAO.delete(user);
			return true;
		}
		return false;
	}
	
	public boolean removeUserById(Long id) {
		User user = userDAO.findUserById(id);
		return removeUser(user);
	}

	public boolean removeUserByEmail(String email) {
		User user = userDAO.findUserByEmail(email);
		return removeUser(user);
	}
	
	public User findUserById(Long id) {
		log.info("Finding user by id");
		return userDAO.findUserById(id);
	}

	public User findUserByEmail(String email) {
		log.info("Finding user by email");
		return userDAO.findUserByEmail(email);
	}

	public List<User> findAllByOrder() {
		log.info("Creating Query for all users (ordered by id)");
		return userDAO.findAllByOrder();
	}

	public User addUser(String name, String pass, String email) {
		log.info("Saving user in DB");
		if (userDAO.findUserByEmail(email) == null) {
			User u = new User(name, epw.encrypt(pass),email);
			userDAO.save(u);
			return u;
		}
		return null;
	}

	public List<User> getUsers() {
		log.info("Creating Query for all users");
		return userDAO.findAll();
	}

	public List<User> usersWithNameStartingBy(String exp) {
		log.info("Creating Query for all users with name starting by");
		return userDAO.usersWithNameStartingBy(exp);
	}
	
	public User update(User u) {
		log.info("Updating user of DB");
		isUserWithAllData(u);
		return userDAO.update(u);
	}

	
	public boolean updateUserPass(User u, String oldpass, String newpass) {
		log.info("Updating user password of DB");
		if (u != null) {
			if (u.checkPassword(epw.encrypt(oldpass))) {
				u.setPassword(epw.encrypt(newpass));
				isUserWithAllData(u);
				userDAO.update(u);
				return true;
			}
		}
		return false;
	}

	public boolean updateUserPassAdmin(User u, String pass) {
		log.info("Updating user password of DB (admin mode)");
		if (u != null) {
			u.setPassword(epw.encrypt(pass));
			isUserWithAllData(u);
			userDAO.update(u);
			return true;
		}
		return false;
	}

	public boolean updateUserName(User u, String name) {
		log.info("Updating user name of DB");
		if (u != null) {
			u.setName(name);
			isUserWithAllData(u);
			userDAO.update(u);
			return true;
		}
		return false;
	}

	private void isUserWithAllData(User u) {
		boolean hasError = false;

		if (u == null) {
			hasError = true;
		}

		if (u.getName() == null || "".equals(u.getName().trim())){
			hasError = true;
		}

		if (hasError){
			throw new IllegalArgumentException("The user is missing data. Check the name and password, they should have value.");
		}
	}


}