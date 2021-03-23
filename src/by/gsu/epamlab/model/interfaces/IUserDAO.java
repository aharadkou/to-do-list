package by.gsu.epamlab.model.interfaces;

import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.model.beans.User;

public interface IUserDAO {
	
	User getUser(String login, String password) throws DAOException;
	
	User addAndGetUser(String login, String password) throws DAOException;

}
