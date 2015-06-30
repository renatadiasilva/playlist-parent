package dei.uc.pt.aor;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@SessionScoped
public class ActiveUserMB implements Serializable {

	private static final long serialVersionUID = 1429959255702576110L;
	private String email;
	private String name;
	private String password;
	private User currentUser;
	private String repeatedPassword;
	private boolean newUser;
	private boolean admin;
	private HttpSession uSession;

	public ActiveUserMB() {
		email = null;
		newUser = false;
		setAdmin(false);
	}
	
	public void changeToLogin() {
		this.newUser = false;
	}
	public void changeToNewUser() {
		this.newUser = true;
	}
	/********* Getters e Setters ************/
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isNewUser() {
		return newUser;
	}
	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}
	
	public void startSession() {
		if (email.equals("admin@admin.com")) setAdmin(true);
		else setAdmin(false);
		//uSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		//uSession.setAttribute("email", email);
	}
	
//	public void endSession(){
//		if (uSession != null) uSession.invalidate();
//		email = null;
//	}
	
	public String greetingName() {
		String[] a = name.split(" ");
		return a[0];
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

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

}