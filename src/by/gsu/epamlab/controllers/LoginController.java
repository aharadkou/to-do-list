package by.gsu.epamlab.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.beans.Role;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.factories.DAOUserFactory;
import by.gsu.epamlab.impl.UserImplMemory;
import by.gsu.epamlab.interfaces.IUserDAO;


public class LoginController extends BaseController {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter(Constants.ATTR_LOGIN);
		String password = request.getParameter(Constants.ATTR_PASSWORD);
		IUserDAO dao = DAOUserFactory.getDAO(UserImplMemory.class);
		User user = dao.getUser(login, password);
		if (user.getRole() == Role.USER) {
			HttpSession session = request.getSession();
			session.setAttribute(Constants.ATTR_USER, user);
			response.sendRedirect(Constants.JSP_INDEX);
		} else {
			
		}
	}

}
