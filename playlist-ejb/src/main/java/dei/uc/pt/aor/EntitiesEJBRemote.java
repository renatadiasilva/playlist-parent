package dei.uc.pt.aor;

import java.util.List;

public interface EntitiesEJBRemote {
//	public void populate();
	 
	public List<User> getUsers();
	public List<User> usersWithNameStartingBy(String exp);
	public void addUser(User u);
	public User userLogin(String email, String password);
	public boolean checkUserEmail(String email);

}