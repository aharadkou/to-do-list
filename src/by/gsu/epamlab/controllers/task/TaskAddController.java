package by.gsu.epamlab.controllers.task;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.exceptions.ValidationException;
import by.gsu.epamlab.helpers.file.UserFileUtils;
import by.gsu.epamlab.model.beans.Section;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.interfaces.ITaskDAO;

public class TaskAddController extends BaseTaskController {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strSection = request.getParameter(Constants.ATTR_SECTION);
		String description = request.getParameter(Constants.ATTR_DESCR);
		String date = request.getParameter(Constants.ATTR_DATE);
		Object fileObject = request.getAttribute(Constants.ATTR_FILE);
		Section section = null;
		try {
			validateInput(Constants.ERR_EMPTY_ATTR, strSection);
			validateInput(Constants.ERR_EMPTY_DESCRIPTION, description);			
			validateInput(Constants.ERR_EMPTY_DATE, date);
			validateFile(fileObject);
			section = validateEnum(strSection, Section.class);
			User user = getSessionUser(request);
			Task inputTask = getInputTask(description, date, fileObject, user);
			ITaskDAO taskDAO = getTaskDAO();
			taskDAO.addTask(inputTask, user);
			redirectToTasks(response, section);
		} catch (ValidationException | DAOException ex) {
			forwardErrorToTasks(ex.getMessage(), request, response, section);
		}
	}
	
	private static Task getInputTask(String description, String date, Object fileObject, User user) 
			throws ValidationException {
		try {
			File uploadedFile = null;
			if (fileObject != null) {
				FileItem fileItem = (FileItem) fileObject;
				uploadedFile = UserFileUtils.saveUserFile(fileItem, user);
			}
			Task inputTask = new Task();
			inputTask.setDescription(description);
			inputTask.setStringDate(date);
			inputTask.setFile(uploadedFile);
			return inputTask;
		} catch(ParseException ex) {
			throw new ValidationException(Constants.ERR_INVALID_DATE);
		}
	}

}
