package dei.uc.pt.aor;

import java.util.ArrayList;
import javax.ejb.Local;

@Local
public interface LoggedUsersInterface {

	public void addUserToLoggedUsersList(User loggedUser);	
	public void removeUserFromLoggedUsersList(User loggedUser);
	public ArrayList<User> getLoggedUsersList();

}
