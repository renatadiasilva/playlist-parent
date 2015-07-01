package dei.uc.pt.aor;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Named
@RequestScoped
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
	
	@Inject
	private LoggedUsers loggedUsers;

	public LoginMB() {
	}
	
	////////////////Security Login////////////////
	
	public String login(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try{
			request.login(email, password);
		}catch (ServletException e){
			context.addMessage(null, new FacesMessage("Login failed."));
			return null;
		}
		return doLogin();
	}
	
	public void logout(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		try{
			request.logout();
			//redirect
		}catch (ServletException e){	
			context.addMessage(null, new FacesMessage("Logout failed."));
		}
	}
	
	//////////////////////////////////////////////
	
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
				return null;
			}

		} else {
        	String errorMsg = "Login failure: Wrong email.";
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			return null;
		}

	}
	
	public String doLogout() {
		log.info("Doing logout");
		log.debug("Doing logout for user "+ aUser.getEmail());
		//aUser.endSession();
		loggedUsers.removeUserFromLoggedUsersList(aUser.getCurrentUser());
//		System.out.println("logout"+ aUser.getEmail() + "\n" +loggedUsers.getLoggedUsersList());
		logout();
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