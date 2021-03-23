package by.gsu.epamlab.controllers.user;

import javax.servlet.ServletException;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.controllers.BaseController;
import by.gsu.epamlab.model.factories.DAOUserFactory;
import by.gsu.epamlab.model.interfaces.IUserDAO;

public class BaseUserController extends BaseController {
	
	private static final long serialVersionUID = 1L;
       
	private IUserDAO userDAO;
	
	public void init() throws ServletException {
		String userDAOImpl = getServletContext().
						getInitParameter(Constants.PARAM_USER_DAO_IMPL);
		userDAO = DAOUserFactory.getDAO(userDAOImpl); 
		if(userDAO == null) {
			throw new ServletException(Constants.ERR_DAO_IMPL_NOT_FOUND + Constants.PARAM_USER_DAO_IMPL);
		}
	}
	
	protected IUserDAO getUserDAO() {
		return userDAO;
	}
}
