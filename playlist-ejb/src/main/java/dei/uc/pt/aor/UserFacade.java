package dei.uc.pt.aor;

import java.util.List;

public interface UserFacade {

	public abstract User findUserByEmail(String email);
	public abstract void addUser(User u);
	//tirar?
	public abstract List<User> getUsers();
	public abstract List<User> usersWithNameStartingBy(String exp);
	public abstract User update(User u);
	public abstract void delete(User u);

}