package dei.uc.pt.aor;

import java.util.List;

public interface UserFacade {

	public User findUserByEmail(String email);
	public void addUser(User u);
	//tirar?
	public List<User> getUsers();
	public List<User> usersWithNameStartingBy(String exp);
	public User update(User u);

}