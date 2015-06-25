package wsclient;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class SimpleUser {
// Nota.. dependendo da situação pode fazer sentido ter uma dependencia para onde o SimpleUser está criado (no caso do exemplo: adomain-domain)
	
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
