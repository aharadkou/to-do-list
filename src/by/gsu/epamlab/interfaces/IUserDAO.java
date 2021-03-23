package by.gsu.epamlab.interfaces;

import by.gsu.epamlab.beans.User;

public interface IUserDAO {
	
	User getUser(String login, String password);
	
	User addAndGetUser(String login, String password);

}
