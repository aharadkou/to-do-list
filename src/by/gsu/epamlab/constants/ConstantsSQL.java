package by.gsu.epamlab.constants;

import by.gsu.epamlab.model.beans.Section;

public class ConstantsSQL {
	
	private ConstantsSQL() { }

	public final static String FIND_USER = "SELECT login FROM users WHERE login = ? AND password = ?";
	
	public final static String FIND_USER_BY_LOGIN = "SELECT login FROM users WHERE login = ?";
	
	public final static String INSERT_USER = "INSERT INTO users(login, password) VALUES(?, ?)";
	
	public final static int PS_IND_LOGIN = 1;
	
	public final static int PS_IND_PASSWORD = 2;
	
	
	public final static String SELECT_TODAY_TASKS = "SELECT id, description, date, fileName "
			                                      + "FROM tasks WHERE date <= CURDATE() AND idType IS NULL AND idUser = "
			                                      + "(SELECT id FROM users WHERE login = ?)";
	
	
	public final static String SELECT_TOMORROW_TASKS = "SELECT id, description, date, fileName "
            									+ "FROM tasks WHERE date = (CURDATE() + INTERVAL 1 DAY) AND idType IS NULL AND idUser = "
            									+ "(SELECT id FROM users WHERE login = ?)";	
	
	public final static String SELECT_SOMEDAY_TASKS = "SELECT id, description, date, fileName "
												+ "FROM tasks WHERE date > (CURDATE() + INTERVAL 1 DAY) AND idType IS NULL AND idUser = "
												+ "(SELECT id FROM users WHERE login = ?)";		
	
	
	public final static int PS_SELECT_TASKS_IND_LOGIN = 1;
	
	public final static int PS_TASK_IND_ID = 1;	
	
	public final static int PS_TASK_IND_DESCR = 2;	
	
	public final static int PS_TASK_IND_DATE = 3;		
		
	public final static int PS_TASK_IND_FILE = 4;		
	
	
	public final static String SELECT_TASKS_WITH_TYPE = "SELECT id, description, date, fileName "
												+ "FROM tasks WHERE idType = (SELECT id FROM taskTypes WHERE typeName = ?) AND idUser = "
												+ "(SELECT id FROM users WHERE login = ?)";	
	
	
	public final static int PS_SELECT_TYPE_TASKS_IND_TYPE = 1;	
	
	
	public final static int PS_SELECT_TYPE_TASKS_IND_LOGIN = 2;	
	
	
	
	
	public final static String INSERT_TASK = "INSERT INTO tasks(description, date, idUser, fileName) "
			+ "SELECT ?, ?, id, ? FROM users WHERE login = ?";	
	
	public final static int PS_INSERT_TASK_IND_DESCR = 1;
	
	public final static int PS_INSERT_TASK_IND_DATE = 2;	
	
	public final static int PS_INSERT_TASK_IND_FILE = 3;
	
	public final static int PS_INSERT_TASK_IND_LOGIN = 4;
	
	
	
	
	public final static String UPDATE_TASK_WITH_TYPE = "UPDATE tasks SET idType = (SELECT id FROM taskTypes WHERE typeName = ?) "
			+ "WHERE id = ?";
	
	public final static int PS_UPDATE_TASK_IND_TYPE = 1;
	
	public final static int PS_UPDATE_TASK_IND_ID = 2;	
	
	
	
	
	public final static String RESTORE_FROM_BIN = "UPDATE tasks SET idType = NULL "
			+ "WHERE id = ?";	
	
	public final static String DELETE_FROM_BIN = "DELETE FROM tasks WHERE id = ? AND idType = (SELECT id FROM taskTypes WHERE typeName = '"
			+ Section.RECYCLE_BIN + "')";
	
	public final static int PS_DELETE_TASK_IND_ID = 1;		
	
	
	public final static String CLEAR_BIN = "DELETE FROM tasks idType = (SELECT id FROM taskTypes WHERE typeName = '"
			+ Section.RECYCLE_BIN + "')";
	
	
	public final static String UPDATE_TASK_FILE = "UPDATE tasks SET fileName = ? WHERE id = ?";
	
	public final static int PS_UPDATE_TASK_FILE_IND_NAME = 1;
	
	public final static int PS_UPDATE_TASK_FILE_IND_ID = 2;	 
	
	
	public final static String SELECT_FILE_NAME = "SELECT fileName FROM tasks WHERE id = ?";
	
	public final static int PS_SELECT_FILE_NAME_IND_ID = 1;	
	
}