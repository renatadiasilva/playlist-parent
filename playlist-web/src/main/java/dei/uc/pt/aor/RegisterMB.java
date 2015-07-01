package dei.uc.pt.aor;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

@Named
@RequestScoped
public class RegisterMB implements Serializable {

	private static final long serialVersionUID = -2624145242993606181L;

	private static final Logger log = LoggerFactory.getLogger(RegisterMB.class);

	private String email;
	private String password;
	private String repeatedPassword;
	private String name;
	
	@Inject
	private PlaylistsManagerMB manager;
	
	@Inject
	LoginMB login;
	
	@Inject
	ActiveUserMB aUser;

	public RegisterMB() {

	}

	public void newUser() {
		log.info("Creating new user");
		log.debug("Creating new user "+email);
		name = login.getName();
		email = login.getEmail();
		password = login.getPassword();

		if (email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+"
				+ "(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			if (password.equals(repeatedPassword)) {

				if (manager.addUser(name, password, email)) {
					aUser.changeToLogin();
				} else {
		        	String errorMsg = "This email already exists!";
		        	log.error(errorMsg);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
				}

			} else {
	        	String errorMsg = "Passwords don't match!";
	        	log.error(errorMsg);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
			}
		} else {
        	String errorMsg = "The email is not valid!";
        	log.error(errorMsg);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
		}
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
