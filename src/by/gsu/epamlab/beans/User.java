package by.gsu.epamlab.beans;

public class User {
	
	private String login;
	
	private Role role;
	
	public User() {	}
	
	public User(String login, Role role) {
		super();
		this.login = login;
		this.role = role;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
}
