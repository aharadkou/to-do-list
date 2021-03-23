package by.gsu.epamlab.constants;

public class Constants {

	private Constants() {}
	
	public static final String ROOT_CONTEXT = "java:comp/env";
	
	public static final String DATA_SOURCE_NAME = "jdbc/tododb";	

	
	public static final String PARAM_USER_DAO_IMPL = "userDAOImpl";
	
	public static final String PARAM_TASK_DAO_IMPL = "taskDAOImpl";
	
	
	public static final String ERR_DAO_IMPL_NOT_FOUND = "DAO object not found! => ";	
	
	public static final String ERR_CLOSE_RESOURCE = "problem with closing resource";
	
	public static final String ERR_CONNECTION = "problem with connection to DB";
	
	public static final String ERR_INCORRECT_LOG_PASS = "Incorrect login or password!";
	
	public static final String ERR_LOGIN_EXISTS = "User with such login already exists!";	
	
	public static final String ERR_EMPTY_ATTR = "Request attributes are empty!";
	
	public static final String ERR_EMPTY_LOG_PASS = "Login or password is empty!";			
	
	public static final String ERR_PASS_MATCH = "Passwords don't match!";		
	
	public static final String ERR_EMPTY_CHECK = "No task selected!";			
	
	public static final String ERR_EMPTY_DESCRIPTION = "Task description is empty!";
	
	public static final String ERR_EMPTY_DATE = "Task date is empty!";	
		
	public static final String ERR_INVALID_CHECK = "Selected tasks are invalid!";
	
	public static final String ERR_INVALID_DATE = "Input date is invalid!";
	
	public static final String ERR_INVALID_REQUEST = "Failed to process request!";
	
	public static final String ERR_FILE_BIG = "Uploaded file is too big!";
	
	public static final String ERR_FILE_UPLOAD = "Problem with file uploading!";
	
	public static final String ERR_FILE_NOT_EXISTS = "No such file!";
	
	public static final String ERR_FILE_DOWNLOAD = "Problem with file uploading!";
	
	public static final String ERR_FILE_DELETE = "Problem with file delete!";
	
	public static final String ERR_DB_PROBLEM = "Problem with database occured!"; 
	
			
	public static final String JSP_LOGIN = "login.jsp";
	
	public static final String PATH_LOGIN = "/login";	
	
	public static final String PATH_LOGOUT= "/logout";	
	
	public static final String PATH_TASKS = "/tasks";	

	public static final String PATH_OPERATION = "/tasks/operation";	
	
	public static final String PATH_TASK_ADD = "/tasks/operation/add";	
	
	public static final String PATH_FILE_DOWNLOAD = "/tasks/file/download";	
	
	public static final String PATH_FILE_ADD = "/tasks/file/add";	
	
	public static final String PATH_FILE_DELETE = "/tasks/file/delete";	
	
	public static final String JSP_REG = "registration.jsp";
	
	public static final String PATH_REG = "/registration";	
	
	public static final String JSP_INDEX = "index.jsp";
	
	public static final String JSP_TASKS = "/WEB-INF/jsp/tasks.jsp";
	
	public static final String JSP_HEADER = "/WEB-INF/jsp/common/_header.jsp";
	
	public static final String JSP_FOOTER = "/WEB-INF/jsp/common/_footer.jsp";
	
	public static final String JSP_ERROR = "/WEB-INF/jsp/common/_error.jsp";	
	
	public static final String ATTR_LOGIN = "login";

	public static final String ATTR_PASSWORD = "password";	
	
	public static final String ATTR_REP_PASSWORD = "repeatPassword";	
	
	public static final String ATTR_USER = "user";	
	
	public static final String ATTR_ERROR = "error";
	
	public static final String ATTR_SECTION = "section";	
	
	public static final String ATTR_OPERATION = "operation";	
	
	public static final String ATTR_LIST_TASKS = "listTasks";	
	
	public static final String ATTR_CHECKED_TASKS = "checkedTasks";
	
	public static final String ATTR_DESCR = "description";
	
	public static final String ATTR_DATE = "date";	
	
	public static final String ATTR_FILE = "file";		
	
	public static final String ATTR_TASK_ID = "taskId";			
	
	
	
	public static final String OUTPUT_DATE_PATTERN = "dd.MM.yyyy";	
	
	public static final String FILE_UPLOAD_DIR = "D:\\userFiles";
	
	public static final int MAX_FILE_MB_SIZE = 8;
    
	public static final int MAX_FILE_BYTE_SIZE = 1024 * 1024 * MAX_FILE_MB_SIZE;
	
}
