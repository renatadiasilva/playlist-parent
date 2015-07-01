package dei.uc.pt.aor;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dei.uc.pt.aor.dao.UserDAO;

@Stateless
public class UserFacadeImp implements UserFacade {
	
	private static final Logger log = LoggerFactory.getLogger(UserFacadeImp.class);

	@EJB
	private UserDAO userDAO;
	
	@EJB
	private EncryptPass epw;
	
	public void delete(User u) {
		log.info("Removing user from DB");
		userDAO.delete(u);
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