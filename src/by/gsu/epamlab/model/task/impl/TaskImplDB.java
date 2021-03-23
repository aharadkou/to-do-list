package by.gsu.epamlab.model.task.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.constants.ConstantsSQL;
import by.gsu.epamlab.exceptions.ConnectionException;
import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.helpers.db.ConnectionManager;
import by.gsu.epamlab.helpers.db.DBUtils;
import by.gsu.epamlab.model.beans.Operation;
import by.gsu.epamlab.model.beans.Section;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;
import by.gsu.epamlab.model.interfaces.ITaskDAO;

public class TaskImplDB implements ITaskDAO {

	private static Logger LOGGER = Logger.getLogger(TaskImplDB.class.getName());
	
	@Override
	public List<Task> getTasks(Section section, User user) throws DAOException {
		PreparedStatement psTasks = null; 
		ResultSet rsTasks = null;
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			psTasks = section.getPreparedStatement(conn, user);
			rsTasks = psTasks.executeQuery();
			List<Task> tasks = new ArrayList<>();
			while(rsTasks.next()) {
				Task task = new Task();
				task.setId(rsTasks.getInt(ConstantsSQL.PS_TASK_IND_ID));
				task.setDescription(rsTasks.getString(ConstantsSQL.PS_TASK_IND_DESCR));
				task.setDate(rsTasks.getDate(ConstantsSQL.PS_TASK_IND_DATE));
				String fileName = rsTasks.getString(ConstantsSQL.PS_TASK_IND_FILE);
				if(fileName != null && !fileName.isEmpty()) {
					task.setFile(new File(fileName));				
				} else {
					task.setFile(null);
				}
				tasks.add(task);
			}
			return tasks;
		} catch(SQLException | ConnectionException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
			throw new DAOException(Constants.ERR_DB_PROBLEM);
		} finally {
			DBUtils.closeRecources(rsTasks, psTasks, conn);
		}
	}

	@Override
	public void performOperation(Operation operation, List<Task> tasks) throws DAOException {
		PreparedStatement psOperation = null; 
		PreparedStatement psSelectFile = null;		
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			psOperation = operation.getPreparedStatement(conn);
			if(operation == Operation.DELETE) {
				psSelectFile = conn.prepareStatement(ConstantsSQL.SELECT_FILE_NAME);
			}
			for(Task task : tasks) {
				if(operation == Operation.DELETE) {
					deleteFile(psSelectFile, task, conn);
				}
				operation.setPreparedStatementValues(psOperation, task);
				psOperation.addBatch();
			}
			psOperation.executeBatch();
		} catch(SQLException | ConnectionException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);			
			throw new DAOException(Constants.ERR_DB_PROBLEM);			
		} finally {
			DBUtils.closeRecources(psOperation, psSelectFile, conn);
		}
	}

	private void deleteFile(PreparedStatement ps, Task task, Connection conn) throws SQLException {
		ResultSet resSet = null;
		try {
			ps.setInt(ConstantsSQL.PS_SELECT_FILE_NAME_IND_ID, task.getId());
			resSet = ps.executeQuery();
			if(resSet.next()) {
				String fileName = resSet.getString(ConstantsSQL.PS_SELECT_FILE_NAME_IND_ID);
				if(fileName != null && !fileName.isEmpty()) {
					File deletedFile = new File(fileName);
					deletedFile.delete();							
				}
			}
		} finally {
			DBUtils.closeRecources(resSet);
		}			
	}	
	
	
	@Override
	public void addTask(Task task, User user) throws DAOException {
		PreparedStatement ps = null; 
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			ps = conn.prepareStatement(ConstantsSQL.INSERT_TASK);
			ps.setString(ConstantsSQL.PS_INSERT_TASK_IND_DESCR, task.getDescription());
			ps.setDate(ConstantsSQL.PS_INSERT_TASK_IND_DATE, task.getDate());	
			ps.setString(ConstantsSQL.PS_INSERT_TASK_IND_LOGIN, user.getLogin());
			if (task.getFile() != null) {
				ps.setString(ConstantsSQL.PS_INSERT_TASK_IND_FILE, task.getFile().getAbsolutePath());				
			} else {
				ps.setNull(ConstantsSQL.PS_INSERT_TASK_IND_FILE, Types.VARCHAR);
			}
			ps.executeUpdate();
		} catch(SQLException | ConnectionException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
			throw new DAOException(Constants.ERR_DB_PROBLEM);			
		} 		
		finally {
			DBUtils.closeRecources(ps, conn);
		}		
	}

	@Override
	public void updateTaskFile(Task task) throws DAOException {
		PreparedStatement psUpdateFile = null; 
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			psUpdateFile = conn.prepareStatement(ConstantsSQL.UPDATE_TASK_FILE);
			if (task.getFile() != null) {
				psUpdateFile.setString(ConstantsSQL.PS_UPDATE_TASK_FILE_IND_NAME, 
						task.getFile().getAbsolutePath());				
			} else {
				psUpdateFile.setNull(ConstantsSQL.PS_UPDATE_TASK_FILE_IND_NAME, Types.VARCHAR);
			}
			psUpdateFile.setInt(ConstantsSQL.PS_UPDATE_TASK_FILE_IND_ID, task.getId());
			psUpdateFile.executeUpdate();
		} catch(SQLException | ConnectionException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
			throw new DAOException(Constants.ERR_DB_PROBLEM);			
		} finally {
			DBUtils.closeRecources(psUpdateFile, conn);
		}					
	}

}
