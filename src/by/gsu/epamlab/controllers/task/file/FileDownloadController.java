package by.gsu.epamlab.controllers.task.file;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.controllers.task.BaseTaskController;
import by.gsu.epamlab.exceptions.ValidationException;
import by.gsu.epamlab.helpers.file.UserFileUtils;
import by.gsu.epamlab.model.beans.Section;

public class FileDownloadController extends BaseTaskController{
	private static final long serialVersionUID = 1L;
       
 	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = request.getParameter(Constants.ATTR_FILE);
		String strSection = request.getParameter(Constants.ATTR_SECTION);
		Section section = null;
		try {
			validateInput(Constants.ERR_EMPTY_ATTR, filePath, strSection);
			section = validateEnum(strSection, Section.class);
			File file = new File(filePath);
			UserFileUtils.sendFileAsResponse(file, response);
		} catch(ValidationException ex) {
			forwardErrorToTasks(ex.getMessage(), request, response, section);				
		}

	}

}
