package by.gsu.epamlab.db.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.gsu.epamlab.Constants;
import by.gsu.epamlab.exceptions.ConnectionException;

public class DBManager {
	
	private static Connection connection;
	
	private DBManager() { }

	public static Connection getConnection() {
		return connection;
	}
	
	public static Connection buildConnection() {
		final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
		final String DB_URL = "jdbc:mysql://localhost/results?serverTimezone=Europe/Minsk"; 
		final String USER = "jse";
		final String PASSWORD = "jse";
		try {
			Class.forName(CLASS_NAME);
			return DriverManager.getConnection(DB_URL, USER, PASSWORD);			
		} catch (ClassNotFoundException | SQLException e) {
			throw new ConnectionException(Constants.ERR_CONNECTION);
		}
	}
	
	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.err.println(Constants.ERR_CLOSE_RESOURCE + e);
			}
		}
	}
	
	public static void closeStatements(Statement ... statements) { 
		for (Statement statement : statements) {
			if(statement != null) {
				try {
					statement.close();					
				} catch (SQLException e) {
					System.err.println(Constants.ERR_CLOSE_RESOURCE + e);
				}				
			}
		}
	}
	
	public static void closeResSet(ResultSet resSet) { 	
		if(resSet != null) {
			try {
				resSet.close();					
			} catch (SQLException e) {
				System.err.println(Constants.ERR_CLOSE_RESOURCE + e);
			}			
		}
	}		
}
