package dei.uc.pt.aor;

import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoggedUsers {

	private ArrayList<User> loggedUsersList = new ArrayList<User>();

	public LoggedUsers() {
		super();
	}

	public void addUserToLoggedUsersList(User loggedUser) {
		if (!loggedUsersList.contains(loggedUser))
			loggedUsersList.add(loggedUser);
	}
	
	public void removeUserFromLoggedUsersList(User loggedUser) {
		loggedUsersList.remove(loggedUser);
	}

	public ArrayList<User> getLoggedUsersList() {		
		return loggedUsersList;
	}

}
