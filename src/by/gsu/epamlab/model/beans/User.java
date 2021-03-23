package by.gsu.epamlab.model.beans;

public class User {
	
	private String login;
	
	public User() {	}
	
	public User(String login) {
		super();
		this.login = login;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

}
