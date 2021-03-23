package by.gsu.epamlab.controllers.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.exceptions.ValidationException;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.interfaces.IUserDAO;

public class RegistrationController extends BaseUserController {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter(Constants.ATTR_LOGIN);
		String password = request.getParameter(Constants.ATTR_PASSWORD);	
		String repeatPassword = request.getParameter(Constants.ATTR_REP_PASSWORD);
		try {
			validateInput(Constants.ERR_EMPTY_LOG_PASS, login, password, repeatPassword);			
			passwordConfirmed(password, repeatPassword);
			IUserDAO dao = getUserDAO();
			login = login.trim();
			password = password.trim();
			repeatPassword = repeatPassword.trim();
			User user = dao.addAndGetUser(login, repeatPassword);
			authorizeUser(user, request, response);
		} catch (DAOException | ValidationException ex) {
			request.setAttribute(Constants.ATTR_LOGIN, login);
			forwardError(ex.getMessage(), Constants.JSP_REG, request, response);
		}
	}
	
	private static void passwordConfirmed(String password, String repeatPassword) throws ValidationException {
		if (!password.equals(repeatPassword)) {
			throw new ValidationException(Constants.ERR_PASS_MATCH);
		}
	}

}
