package dei.uc.pt.aor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import dei.uc.pt.aor.Playlist;
import dei.uc.pt.aor.User;

@Stateless
public class UserDAO extends GenericDAO<User> {

	public UserDAO() {
		super(User.class);
	}
	
	public void delete(User user) {
		super.delete(user.getId(), User.class);
	}

	public void save(User user) {
		super.save(user);
	}
	
	public User findUserByEmail(String email) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("email", email);
		return super.findOneResult("User.findUserByEmail", parameters);
	}
	
	public List<User> usersWithNameStartingBy(String exp) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("exp", exp);
		return super.findSomeResults("User.findUserStartingBy", parameters);
	}
	
}