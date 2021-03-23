package by.gsu.epamlab.controllers.task.file;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.controllers.task.BaseTaskController;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.exceptions.ValidationException;
import by.gsu.epamlab.helpers.file.UserFileUtils;
import by.gsu.epamlab.model.beans.Section;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.interfaces.ITaskDAO;


public class FileDeleteController extends BaseTaskController {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String taskId = request.getParameter(Constants.ATTR_TASK_ID);
		String filePath = request.getParameter(Constants.ATTR_FILE);
		String strSection = request.getParameter(Constants.ATTR_SECTION);
		Section section = null;
		try {
			validateInput(Constants.ERR_EMPTY_ATTR, taskId, filePath, strSection);
			section = validateEnum(strSection, Section.class);
			User user = getSessionUser(request);
			File file = new File(filePath);
			UserFileUtils.deleteFile(file);
			Task task = getInputTask(taskId, null, user);
			ITaskDAO taskDAO = getTaskDAO();
			taskDAO.updateTaskFile(task);
			redirectToTasks(response, section);
		} catch(DAOException | ValidationException ex) {
			forwardErrorToTasks(ex.getMessage(), request, response, section);		
		}
	}
}
