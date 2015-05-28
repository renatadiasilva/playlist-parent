package dei.uc.pt.aor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class EntitiesEJB implements EntitiesEJBRemote {
	
	// base de dados, tabela user foi ao ar... se meto novo user apaga anterior...

	@PersistenceContext(name = "Entities")
	private EntityManager em;

	private static final Logger log = LoggerFactory.getLogger(EntitiesEJB.class);

	public EntitiesEJB() {
	}

	public static Date getDate(int day, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);

		Date d = cal.getTime();
		return d;
	}

//	public void populate() {
//
//		User[] listOfUsers = {
//				new User("Renata Silva", "passR", "renatadiasilva@gmail.com"),
//				new User("Catarina Lapo", "passC", "ciclapo@hotmail.com") };
//
//		Playlist[] listOfPlaylists = {
//				new Playlist("rsPL1", getDate(20, 5, 2015), listOfUsers[0]),
//				new Playlist("rsPL2", getDate(21, 5, 2015), listOfUsers[0]),
//				new Playlist("clPL1", getDate(19, 5, 2015), listOfUsers[1]),
//				new Playlist("clPL2", getDate(21, 5, 2015), listOfUsers[1]), };
//
//		Song[] listOfSongs = {
//				new Song("Song1", "Artist1", "Album1", 2010, "C:/Desktop",
//						listOfUsers[0]),
//				new Song("Song2", "Artist1", "Album1", 2010, "C:/Desktop",
//						listOfUsers[0]),
//				new Song("Song3", "Artist2", "Album2", 2000, "C:/Desktop",
//						listOfUsers[0]),
//				new Song("Song4", "Artist2", "Album3", 2011, "C:/Desktop",
//						listOfUsers[0]),
//				new Song("Song5", "Artist3", "Album4", 2007, "C:/Desktop",
//						listOfUsers[0]),
//				new Song("Song6", "Artist3", "Album5", 2010, "C:/Desktop",
//						listOfUsers[1]),
//				new Song("Song7", "Artist3", "Album5", 1999, "C:/Desktop",
//						listOfUsers[1]),
//				new Song("Song8", "Artist4", "Album6", 2014, "C:/Desktop",
//						listOfUsers[1]),
//				new Song("Song9", "Artist4", "Album6", 2014, "C:/Desktop",
//						listOfUsers[1]),
//				new Song("Song10", "Artist5", "Album7", 2005, "C:/Desktop",
//						listOfUsers[1]), };
//
//		listOfPlaylists[0].addSong(listOfSongs[0]);
//		listOfPlaylists[0].addSong(listOfSongs[1]);
//		listOfPlaylists[0].addSong(listOfSongs[2]);
//		listOfPlaylists[0].addSong(listOfSongs[7]);
//
//		listOfPlaylists[1].addSong(listOfSongs[0]);
//		listOfPlaylists[1].addSong(listOfSongs[3]);
//		listOfPlaylists[1].addSong(listOfSongs[4]);
//		listOfPlaylists[1].addSong(listOfSongs[9]);
//
//		listOfPlaylists[2].addSong(listOfSongs[1]);
//		listOfPlaylists[2].addSong(listOfSongs[4]);
//		listOfPlaylists[2].addSong(listOfSongs[5]);
//		listOfPlaylists[2].addSong(listOfSongs[6]);
//		listOfPlaylists[2].addSong(listOfSongs[8]);
//		listOfPlaylists[2].addSong(listOfSongs[9]);
//
//		listOfPlaylists[3].addSong(listOfSongs[2]);
//		listOfPlaylists[3].addSong(listOfSongs[7]);
//		listOfPlaylists[3].addSong(listOfSongs[8]);
//		listOfPlaylists[3].addSong(listOfSongs[9]);
//
//		for (User u : listOfUsers)
//			em.persist(u);
//
//		for (Playlist p : listOfPlaylists)
//			em.persist(p);
//
//		for (Song s : listOfSongs)
//			em.persist(s);
//
//		int i = 0;
//		for (User u : listOfUsers) {
//			i++;
//			log.debug("User " + i + ": " + u.toString());
//		}
//
//	}

	@Override
	public List<User> getUsers() {
		log.info("Creating Query for all users");
		Query q = em.createQuery("from User u");
		@SuppressWarnings("unchecked")
		List<User> users = q.getResultList();

		return users;
	}

	public List<User> usersWithNameStartingBy(String exp) {
		log.info("Creating Query for all users with name starting by {}.",
				exp.substring(exp.length() - 2, exp.length() - 1));
		Query q = em.createQuery("from User u where u.name like :exp");
		q.setParameter("exp", exp);
		@SuppressWarnings("unchecked")
		List<User> result = q.getResultList();
		return result;
	}
	
	// basta dar o user como eu tinha, faz checks fora
	
	public User userLogin(String email, String password) {
		log.info("Checking login data for {}.", email);
		Query q = em.createQuery("from User u where u.email like :e");
		q.setParameter("e", email);
		@SuppressWarnings("unchecked")
		List<User> result = q.getResultList();
		
		if (result.size() > 0) {
			if (result.get(0).checkPassword(password)) {
				log.debug("Success: login done.");
				return result.get(0);
			} else {
				log.debug("Failure: login with wrong password.");
				return null;
			}
		} else {
			log.debug("Failure: login with wrong email.");
			return null;
		}
	}

	public boolean checkUserEmail(String email) {
		log.info("Checking for existing email: {}.", email);
		Query q = em.createQuery("from User u where u.email like :e");
		q.setParameter("e", email);
		@SuppressWarnings("unchecked")
		List<User> result = q.getResultList();
		
		if (result.size() == 0) {
			log.debug("Success: the new email can be added.");
			return true;
		} else {
			log.debug("Failure: the email already exists.");
			return false;			
		}
	}

	public void addUser(User u) {
		log.info("Adding user with email {} to DB.", u.getEmail());
		log.debug("Trying to add user: {0} ({1}).", u.getEmail(), u.getName());
		em.persist(u); //check if u has a valid email (not here): u.getPassword().contains("@")
	}
	
	// rolled back if DB not opened... mensagem de erro!!! exceptions no em.qq-coisa?
	
	
}
