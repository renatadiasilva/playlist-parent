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
	private User currentUser;
	private boolean newUser;
	private HttpSession uSession;

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
	
	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User u) {
		this.currentUser = u;
	}

	public void startSession(){
		uSession = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		uSession.setAttribute("email", email);
	}
	
	public void endSession(){
		if(uSession!=null)
			uSession.invalidate();
		email = null;
	}
	
	public String greetingName() {
		String[] a = currentUser.getName().split(" ");
		return a[0];
	}

}
