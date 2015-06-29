package dei.uc.pt.aor;

import java.util.ArrayList;

import javax.ejb.Stateful;
import javax.faces.bean.ApplicationScoped;

@Stateful
@ApplicationScoped
public class LoggedUsers implements LoggedUsersInterface {

	private ArrayList<User> loggedUsersList = new ArrayList<User>();

	public LoggedUsers() {
		super();
	}

	public void addUserToLoggedUsersList(User loggedUser) {
		loggedUsersList.add(loggedUser);
	}
	
	public void removeUserFromLoggedUsersList(User loggedUser) {
		loggedUsersList.remove(loggedUser);
	}


	public ArrayList<User> getLoggedUsersList() {
		return loggedUsersList;
	}

}
