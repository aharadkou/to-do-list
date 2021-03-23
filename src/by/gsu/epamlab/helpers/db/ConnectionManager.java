package by.gsu.epamlab.helpers.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.exceptions.ConnectionException;

public class ConnectionManager {
	
	private static final Logger LOGGER = Logger.getLogger(ConnectionManager.class.getName());
	
	private static final DataSource DATA_SOURCE;
	
	private ConnectionManager() { }

	static {
		try {
			Context envContext = (Context) (new InitialContext().lookup(Constants.ROOT_CONTEXT));
			DATA_SOURCE = (DataSource) envContext.lookup(Constants.DATA_SOURCE_NAME);
		} catch (NamingException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
			throw new RuntimeException(ex);
		}
	}
	
	public static Connection getConnection() throws ConnectionException {
		try {
			return DATA_SOURCE.getConnection();		
		} catch (SQLException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
			throw new ConnectionException(Constants.ERR_CONNECTION);
		}
	}
}
