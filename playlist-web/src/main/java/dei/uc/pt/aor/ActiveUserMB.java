package dei.uc.pt.aor;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

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

	public ActiveUserMB() {
		email = null;
		newUser = false;
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

}