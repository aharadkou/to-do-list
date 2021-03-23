package by.gsu.epamlab.controllers.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.exceptions.ValidationException;
import by.gsu.epamlab.model.beans.Operation;
import by.gsu.epamlab.model.beans.Section;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.interfaces.ITaskDAO;

public class OperationController extends BaseTaskController {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] ids = request.getParameterValues(Constants.ATTR_CHECKED_TASKS);
		String strOperation = request.getParameter(Constants.ATTR_OPERATION);
		String strSection = request.getParameter(Constants.ATTR_SECTION);
		Section section = null;
		try {
			validateInput(Constants.ERR_EMPTY_ATTR, strOperation, strSection);
			Operation operation = validateEnum(strOperation, Operation.class);
			section = validateEnum(strSection, Section.class);
			List<Task> inputTasks = getInputTasks(ids);
			ITaskDAO taskDao = getTaskDAO();
			taskDao.performOperation(operation, inputTasks);
			redirectToTasks(response, section);
		} catch (DAOException | ValidationException ex) {
			forwardErrorToTasks(ex.getMessage(), request, response, section);
		}
	}
	
	
	private static List<Task> getInputTasks(String[] ids) throws ValidationException {
		if(ids == null || ids.length == 0) {
			throw new ValidationException(Constants.ERR_EMPTY_CHECK);
		}
		List<Task> inputTasks = new ArrayList<>();
		try {
			for(int i = 0; i < ids.length; i++) {
				Task inputTask = new Task();
				inputTask.setId(Integer.parseInt(ids[i]));
				inputTasks.add(inputTask);
			}
			return inputTasks;
		} catch(NumberFormatException e) {
			throw new ValidationException(Constants.ERR_INVALID_CHECK);			
		}
	}

}
