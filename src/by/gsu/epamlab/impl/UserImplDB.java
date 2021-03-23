package by.gsu.epamlab.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.gsu.epamlab.beans.Role;
import by.gsu.epamlab.beans.User;
import by.gsu.epamlab.db.helpers.DBManager;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.interfaces.IUserDAO;

public class UserImplDB  implements IUserDAO {

	@Override
	public User getUser(String login, String password) {
		final String FIND_USER = "SELECT * FROM users WHERE login = "
				+ login + " AND password = " + password; 
		DBManager.buildConnection();
		Statement st = null; 
		ResultSet rs = null;
		try {
			st = DBManager.getConnection().createStatement();
			rs = st.executeQuery(FIND_USER);
			User user = new User(login, Role.GUEST);
			if (rs != null) {
				user.setRole(Role.USER);
			} 
			return user;
		} catch(SQLException ex) {
			throw new DAOException(ex);
		} finally {
			DBManager.closeResSet(rs);
			DBManager.closeStatements(st);
		}
	}

	@Override
	public boolean addUser(String login, String password) {
		final String INSERT_USER = "INSERT INTO users(login, password) VALUES(" 
				+ login + "," + password + ")";
		
	}
	
}
