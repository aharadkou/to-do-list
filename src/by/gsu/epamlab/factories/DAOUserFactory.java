package by.gsu.epamlab.factories;

import java.util.HashMap;
import java.util.Map;

import by.gsu.epamlab.impl.UserImplDB;
import by.gsu.epamlab.impl.UserImplMemory;
import by.gsu.epamlab.interfaces.IUserDAO;

public class DAOUserFactory {

	private static class HeterogeneousMap {
		
		private Map<Class<?>, Object> map = new HashMap<>();
		
		private <T> void put(Class<T> type, T instance) {
			map.put(type, instance);
		}
		
		private <T> T get(Class<T> type) {
			return type.cast(map.get(type));
		}
	}
	
	private static HeterogeneousMap map = new HeterogeneousMap();
	
	static {
		map.put(UserImplMemory.class, new UserImplMemory());
		map.put(UserImplDB.class, new UserImplDB());
	}

	public static IUserDAO getDAO(Class<? extends IUserDAO> type) {
		return map.get(type);
	}
	
	
}
