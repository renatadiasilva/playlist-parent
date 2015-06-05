package dei.uc.pt.aor;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dei.uc.pt.aor.dao.UserDAO;

@Stateless
public class UserFacadeImp implements UserFacade {
	
	// meter cenas info, debug e error
	private static final Logger log = LoggerFactory.getLogger(UserFacadeImp.class);

	@EJB
	private UserDAO userDAO;
	
	public User findUserByEmail(String email) {
		return userDAO.findUserByEmail(email);
	}

	public void addUser(User u) {
		log.info("Adding user with email "+u.getEmail()+"to DB.");
		log.debug("Trying to add user: "+u.getEmail()+" "+u.getName()+").");
		userDAO.save(u);
	}
	
	public List<User> getUsers() {
		log.info("Creating Query for all users.");
		return userDAO.findAll();
	}

	public List<User> usersWithNameStartingBy(String exp) {
		log.info("Creating Query for all users with name starting by {}.",
				exp.substring(exp.length() - 2, exp.length() - 1));
		return userDAO.usersWithNameStartingBy(exp);
	}
	
	public User update(User u) {
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
	
	public void delete(User u) {
		log.info("Removing account for user with email {}.",u.getEmail());
		userDAO.delete(u);
	}

}