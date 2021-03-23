package by.gsu.epamlab.model.factories;
import java.util.HashMap;
import java.util.Map;

import by.gsu.epamlab.model.interfaces.IUserDAO;
import by.gsu.epamlab.model.user.impl.UserImplDB;
import by.gsu.epamlab.model.user.impl.UserImplMemory;

public class DAOUserFactory {

	private static Map<String, IUserDAO> userImplMap = new HashMap<>();
	
	static {
		userImplMap.put(UserImplMemory.class.getName(), new UserImplMemory());
		userImplMap.put(UserImplDB.class.getName(), new UserImplDB());
	}

	public static IUserDAO getDAO(String className) {
		return userImplMap.get(className);
	}
	
}
