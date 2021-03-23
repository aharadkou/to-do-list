package by.gsu.epamlab.controllers.task;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.controllers.BaseController;
import by.gsu.epamlab.exceptions.ValidationException;
import by.gsu.epamlab.helpers.file.UserFileUtils;
import by.gsu.epamlab.model.beans.Section;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.factories.DAOTaskFactory;
import by.gsu.epamlab.model.interfaces.ITaskDAO;


public class BaseTaskController extends BaseController {
	private static final long serialVersionUID = 1L;
    
	private ITaskDAO taskDAO;
	
	public void init() throws ServletException {
		String taskDAOImpl = getServletContext().
				getInitParameter(Constants.PARAM_TASK_DAO_IMPL);
		taskDAO = DAOTaskFactory.getDAO(taskDAOImpl);
		if(taskDAO == null) {
			throw new ServletException(Constants.ERR_DAO_IMPL_NOT_FOUND + Constants.PARAM_TASK_DAO_IMPL);
		}
	}
	
	protected ITaskDAO getTaskDAO() {
		return taskDAO;
	}
	
	protected void forwardErrorToTasks(String errMessage, HttpServletRequest request, 
			HttpServletResponse response, Section section) throws ServletException, IOException {
		if(section == null) {
			section = Section.TODAY;
		}
		request.setAttribute(Constants.ATTR_SECTION, section.toString());
		forwardError(errMessage, Constants.PATH_TASKS, request, response);
	}	
	

	
	protected static <T extends Enum<T>> T validateEnum(String strEnum, Class<T> enumType) 
			throws ValidationException {
		try {
			T enumValue = Enum.valueOf(enumType, strEnum);
			return enumValue;
		} catch(IllegalArgumentException ex) {
			throw new ValidationException(Constants.ERR_INVALID_REQUEST);
		}		
	}
	
	protected static void validateFile(Object fileObject) throws ValidationException {
		if(fileObject instanceof FileUploadException) {
			FileUploadException fileUploadException = (FileUploadException) fileObject;
			throw new ValidationException(fileUploadException.getMessage());
		}
	}
	
	protected static Task getInputTask(String taskId, Object fileObject, User user) throws ValidationException {
		try {
			FileItem fileItem = (FileItem) fileObject;
			int id = Integer.parseInt(taskId);
			Task task = new Task();
			task.setId(id);
			if (fileItem != null) {
				File file = UserFileUtils.saveUserFile(fileItem, user);
				task.setFile(file);				
			}
			return task;
		} catch (NumberFormatException ex) {
			throw new ValidationException(Constants.ERR_INVALID_REQUEST);			
		}	
	}	
	
}
