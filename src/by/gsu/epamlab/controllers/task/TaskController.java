package by.gsu.epamlab.controllers.task;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.exceptions.ValidationException;
import by.gsu.epamlab.model.beans.Section;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.interfaces.ITaskDAO;


public class TaskController extends BaseTaskController {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strSection = request.getParameter(Constants.ATTR_SECTION);
		try {
			validateInput(Constants.ERR_EMPTY_ATTR, strSection);
			Section section = validateEnum(strSection, Section.class);
			ITaskDAO taskDao = getTaskDAO();
			User user = getSessionUser(request);
			List<Task> tasks = taskDao.getTasks(section, user);
			request.setAttribute(Constants.ATTR_LIST_TASKS, tasks);
			request.setAttribute(Constants.ATTR_SECTION, section);
			forward(Constants.JSP_TASKS, request, response);
		} catch (ValidationException | DAOException ex) {
			forwardError(ex.getMessage(), Constants.JSP_TASKS, request, response);
		}

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
