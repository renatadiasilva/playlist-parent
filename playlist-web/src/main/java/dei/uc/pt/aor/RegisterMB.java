package dei.uc.pt.aor;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class RegisterMB implements Serializable {

	private static final long serialVersionUID = -2624145242993606181L;
	private String email;
	private String password;
	private String repeatedPassword;
	private String name;
	
	@EJB
	private EncryptPass epw;
	
	@Inject
	private PlaylistsManagerMB manager;
	
	@Inject
	LoginMB login;
	
	@Inject
	ActiveUserMB aUser;

	public RegisterMB() {

	}

	public void newUser() {

		name = login.getName();
		email = login.getEmail();
		password = login.getPassword();
		
		if (password.equals(repeatedPassword)) {

			if (manager.findUserByEmail(email) == null) {
				User u = new User(name, epw.encrypt(password), email);
				manager.addUser(u); 
				aUser.changeToLogin();
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("This email already exists!"));
			}
				
		} else FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Passwords don't match."));

	}
	
	/********* Getters e Setters ************/
	public String getName() {
		return name;
	}

	public void setUserName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
