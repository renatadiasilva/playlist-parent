package dei.uc.pt.aor;

import java.util.List;

public interface UserFacade {

	public abstract User findUserById(Long id);
	public abstract User findUserByEmail(String email);
	public abstract User addUser(String name, String pass, String email);
	public abstract List<User> getUsers();
	public abstract List<User> findAllByOrder();
	public abstract List<User> usersWithNameStartingBy(String exp);
	public abstract boolean updateUserPass(User u, String oldpass, String newpass);
	public abstract boolean updateUserPassAdmin(User u, String pass);
	public abstract boolean updateUserName(User u, String name);
	public abstract void delete(User u);

}