package by.gsu.epamlab.model.user.impl;

import java.util.HashMap;
import java.util.Map;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.interfaces.IUserDAO;

public class UserImplMemory implements IUserDAO {

	static Map<String, String> mapUsers = new HashMap<>();
	
	static {
		mapUsers.put("user", "pass");
	}
	
	@Override
	public User getUser(String login, String password) throws DAOException {
		if(password.equals(mapUsers.get(login))) {
			return new User(login);
		} else {
			throw new DAOException(Constants.ERR_INCORRECT_LOG_PASS);
		}
	}

	@Override
	public User addAndGetUser(String login, String password) throws DAOException {
		synchronized(mapUsers) {
			if(!mapUsers.containsKey(login)) {
				mapUsers.put(login, password);
				return new User(login);
			} else {
				throw new DAOException(Constants.ERR_LOGIN_EXISTS);			
			}			
		}
	}
}
