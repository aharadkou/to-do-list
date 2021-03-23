package by.gsu.epamlab.impl;

import java.util.HashMap;
import java.util.Map;

import by.gsu.epamlab.beans.Role;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.interfaces.IUserDAO;

public class UserImplMemory implements IUserDAO {

	static Map<String, String> mapUsers = new HashMap<>();
	
	static {
		mapUsers.put("user", "pass");
	}
	
	@Override
	public User getUser(String login, String password) {
		User user = new User(login, Role.GUEST);
		if(password.equals(mapUsers.get(login))) {
			user.setRole(Role.USER);
		}
		return user;
	}

	@Override
	public User addAndGetUser(String login, String password) {
		User user = new User(login, Role.GUEST);
		if(!mapUsers.containsKey(login)) {
			mapUsers.put(login, password);
			user.setRole(Role.USER);
		}
		return user;
	}
}
