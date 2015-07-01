package dei.uc.pt.aor;

import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoggedUsers {

	private ArrayList<User> loggedUsersList = new ArrayList<User>();
	private ArrayList<Integer> countlist = new ArrayList<Integer>();
	
	public LoggedUsers() {
		super();
	}

	public void addUserToLoggedUsersList(User loggedUser) {
		if (!loggedUsersList.contains(loggedUser)) {
			loggedUsersList.add(loggedUser);
			countlist.add(1);
		} else {
			int index = loggedUsersList.indexOf(loggedUser);
			Integer countU = countlist.get(index)+1;
			countlist.set(index, countU);
		}
	}
	
	public void removeUserFromLoggedUsersList(User loggedUser) {
		int index = loggedUsersList.indexOf(loggedUser);
		Integer countU = countlist.get(index)-1;
		if (countU == 0) 
			loggedUsersList.remove(loggedUser);
		else countlist.set(index, countU);
	}

	public ArrayList<User> getLoggedUsersList() {		
		return loggedUsersList;
	}

}
