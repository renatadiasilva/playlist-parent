package dei.uc.pt.aor;

import java.io.Serializable;
import java.util.ArrayList;



import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;

@Stateless
@ApplicationScoped
public class LoggedUsers implements Serializable{

	private static final long serialVersionUID = 3670108325049183451L;

	private ArrayList<User>loggedUsersList = new ArrayList<User>();

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
