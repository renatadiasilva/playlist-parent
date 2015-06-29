package dei.uc.pt.aor;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Named
//verificar dp da segurança
@ApplicationScoped
public class LoginMB implements Serializable {
	
	private static final long serialVersionUID = -5381236051617076780L;
	
	private static final Logger log = LoggerFactory.getLogger(LoginMB.class);

	private String email;
	private String name;
	private String password;
	
	@EJB
	private EncryptPass epw;
	
	@Inject
	private PlaylistsManagerMB manager;
	
	@Inject
	private ActiveUserMB aUser;
	
	@EJB
	private LoggedUsersInterface loggedUsers;

	public LoginMB() {
	}
		
	public String doLogin() {
		log.info("Doing login");
		log.debug("Doing login for "+email);
		User u = manager.findUserByEmail(email);
		if (u != null) {
			if (u.getPassword().equals(epw.encrypt(password))) {
				manager.setName(u.getName());
				aUser.setName(u.getName());
				aUser.setEmail(email);
				aUser.setCurrentUser(u);
				aUser.startSession();
				loggedUsers.addUserToLoggedUsersList(u);
				email = "";
//				System.out.println("login"+ aUser.getEmail() + "\n" +loggedUsers.getLoggedUsersList());
				return "/pages/listMyPlaylists?faces-redirect=true";
			} else {
	        	String errorMsg = "Login failure: Wrong password.";
	        	log.error(errorMsg);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
				return "login";
			}

		} else {
        	String errorMsg = "Login failure: Wrong email.";
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			return "login";
		}

	}
	
	public String doLogout() {
		log.info("Doing logout");
		log.debug("Doing logout for user "+ aUser.getEmail());
		aUser.endSession();
		loggedUsers.removeUserFromLoggedUsersList(aUser.getCurrentUser());
//		System.out.println("logout"+ aUser.getEmail() + "\n" +loggedUsers.getLoggedUsersList());
		return "/login?faces-redirect=true";
	}
	
	public String removeUser(ActiveUserMB auser) {
		manager.removeUser(auser);
		return doLogout();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}