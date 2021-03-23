package by.gsu.epamlab.model.factories;

import java.util.HashMap;
import java.util.Map;

import by.gsu.epamlab.model.interfaces.ITaskDAO;
import by.gsu.epamlab.model.task.impl.TaskImplDB;

public class DAOTaskFactory {
	private static Map<String, ITaskDAO> taskImplMap = new HashMap<>();
	
	static {
		taskImplMap.put(TaskImplDB.class.getName(), new TaskImplDB());
	}

	public static ITaskDAO getDAO(String className) {
		return taskImplMap.get(className);
	}
}
