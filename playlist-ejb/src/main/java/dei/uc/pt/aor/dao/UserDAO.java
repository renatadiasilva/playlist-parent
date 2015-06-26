package dei.uc.pt.aor.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

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
	
	public User findUserById(Long id) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		List<User> list = super.findSomeResults("User.findUserById", parameters);
		if (list.size() == 1) return list.get(0);
		else return null;
	}

	public User findUserByEmail(String email) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("email", email);
		List<User> list = super.findSomeResults("User.findUserByEmail", parameters);
		if (list.size() == 1) return list.get(0);
		else return null;
	}
	
	public List<User> usersWithNameStartingBy(String exp) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("exp", exp);
		return super.findSomeResults("User.findUserStartingBy", parameters);
	}
	
}