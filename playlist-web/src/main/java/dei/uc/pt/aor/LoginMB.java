package dei.uc.pt.aor;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginMB {
	
	private String email;
	private String name;
	private String password;
	
	@Inject
	private PlaylistsManagerMB manager;
	
	@Inject
	private ActiveUserMB aUser;

	public LoginMB() {
	}
		
	public String doLogin() {
		User u = manager.userLogin(email, password);
		if (u != null) {
			aUser.setCurrentUser(u);
			aUser.setEmail(email);
			aUser.startSession();
			return "/pages/index?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Login failure: Wrong email or password."));
			return "login";
		}
	}
	
	public String doLogout(){
		aUser.endSession();
		return "/login?faces-redirect=true";
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