package dei.uc.pt.aor;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;

@Named
@RequestScoped
public class LoginMB implements Serializable {
	
	private static final long serialVersionUID = -5381236051617076780L;
	private String email;
	private String name;
	private String password;
	
	@EJB
	private EncryptPass epw;
	
	@Inject
	private PlaylistsManagerMB manager;
	
	@Inject
	private ActiveUserMB aUser;

	public LoginMB() {
	}
		
	public String doLogin() {
		User u = manager.findUserByEmail(email);
		if (u != null) {
			if (u.getPassword().equals(epw.encrypt(password))) {
				manager.setName(u.getName());
				aUser.setName(u.getName());
				aUser.setEmail(email);
				aUser.setCurrentUser(u);
				aUser.startSession();
				return "/pages/index?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login failure: Wrong password."));
				return "login";
			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login failure: Wrong email."));
			return "login";
		}

	}
	
	public String doLogout() {
		aUser.endSession();
		return "/login?faces-redirect=true";
	}
	
	// apagar referências todas primeiro
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