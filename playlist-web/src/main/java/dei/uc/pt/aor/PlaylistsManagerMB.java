package dei.uc.pt.aor;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped  //Session? só usar LoginMB???
public class PlaylistsManagerMB {

	@EJB
	private EntitiesEJBRemote entitiesEJB;

	public PlaylistsManagerMB() {
	}

//	public void populate(){
//		entitiesEJB.populate();
//	}
	
	public void addUser(User user) {
		entitiesEJB.addUser(user);
	}

	public User userLogin(String email, String password) {
		return entitiesEJB.userLogin(email, password);
	}

	public boolean checkUserEmail(String email) {
		return entitiesEJB.checkUserEmail(email);
	}
		
	public List<User> getUsers() {
		return entitiesEJB.getUsers();
	}
	
	public List<User> getUsersStartingBy(String name) {
		return entitiesEJB.usersWithNameStartingBy(name);
	}
	
}