package by.gsu.epamlab.model.user.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsSQL;
import by.gsu.epamlab.exceptions.ConnectionException;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.helpers.db.ConnectionManager;
import by.gsu.epamlab.helpers.db.DBUtils;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.interfaces.IUserDAO;

public class UserImplDB  implements IUserDAO {

	private static Logger LOGGER = Logger.getLogger(UserImplDB.class.getName());
	
	@Override
	public User getUser(String login, String password) throws DAOException {
		PreparedStatement psFindUser = null; 
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			psFindUser = conn.prepareStatement(ConstantsSQL.FIND_USER);
			psFindUser.setString(ConstantsSQL.PS_IND_LOGIN, login);
			psFindUser.setString(ConstantsSQL.PS_IND_PASSWORD, password);
			rs = psFindUser.executeQuery();
			if (rs.next()) {
				return new User(login);
			} else {
				throw new DAOException(Constants.ERR_INCORRECT_LOG_PASS);				
			}
		} catch(SQLException | ConnectionException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
			throw new DAOException(Constants.ERR_DB_PROBLEM);
		} finally {
			DBUtils.closeRecources(rs, conn, psFindUser);
		}
	}

	@Override
	public User addAndGetUser(String login, String password) throws DAOException {
		ResultSet rs = null;
		PreparedStatement psFindUser = null; 
		PreparedStatement psInsertUser = null; 		
		Connection conn = null;	
		try {
			conn = ConnectionManager.getConnection();
			psFindUser = conn.prepareStatement(ConstantsSQL.FIND_USER_BY_LOGIN);
			psFindUser.setString(ConstantsSQL.PS_IND_LOGIN, login);
			psInsertUser = conn.prepareStatement(ConstantsSQL.INSERT_USER);
			psInsertUser.setString(ConstantsSQL.PS_IND_LOGIN, login);
			psInsertUser.setString(ConstantsSQL.PS_IND_PASSWORD, password);
			synchronized(UserImplDB.class) {
				rs = psFindUser.executeQuery();
				if (rs.next()) {
					throw new DAOException(Constants.ERR_LOGIN_EXISTS);						
				}
				psInsertUser.executeUpdate();
			}
			return new User(login);	
		} catch(SQLException | ConnectionException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
			throw new DAOException(Constants.ERR_DB_PROBLEM);
		}  finally {
			DBUtils.closeRecources(rs, conn, psFindUser, psInsertUser);
		}
	} 
}
