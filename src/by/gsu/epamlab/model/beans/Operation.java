package by.gsu.epamlab.model.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;


import by.gsu.epamlab.constants.ConstantsSQL;

public enum Operation {
	FIX("Mark as fixed") {
		
		@Override
		public PreparedStatement getPreparedStatement(Connection conn) throws SQLException  {
			PreparedStatement ps = conn.prepareStatement(ConstantsSQL.UPDATE_TASK_WITH_TYPE);
			return ps;
		}

		@Override
		public void setPreparedStatementValues(PreparedStatement ps, Task task) throws SQLException {
			setUpdatePreparedStatementValues(ps, task, Section.FIXED.toString());		
		}
		
	}, 
	TO_BIN("Move to recycle bin") {
		
		@Override
		public PreparedStatement getPreparedStatement(Connection conn) throws SQLException  {
			PreparedStatement ps = conn.prepareStatement(ConstantsSQL.UPDATE_TASK_WITH_TYPE);
			return ps;
		}

		@Override
		public void setPreparedStatementValues(PreparedStatement ps, Task task) throws SQLException {
			setUpdatePreparedStatementValues(ps, task, Section.RECYCLE_BIN.toString());	
			
		}
		
	}, 
	RESTORE("Restore from recycle bin") {
		
		@Override
		public PreparedStatement getPreparedStatement(Connection conn) throws SQLException   {
			PreparedStatement ps = conn.prepareStatement(ConstantsSQL.RESTORE_FROM_BIN);
			return ps;
		}

		@Override
		public void setPreparedStatementValues(PreparedStatement ps, Task task) throws SQLException {
			ps.setInt(ConstantsSQL.PS_DELETE_TASK_IND_ID, task.getId());				
		}
		
	},
	DELETE("Delete from recycle bin") {
		
		@Override
		public PreparedStatement getPreparedStatement(Connection conn) throws SQLException   {
			PreparedStatement ps = conn.prepareStatement(ConstantsSQL.DELETE_FROM_BIN);
			return ps;
		}

		@Override
		public void setPreparedStatementValues(PreparedStatement ps, Task task) throws SQLException {
			ps.setInt(ConstantsSQL.PS_DELETE_TASK_IND_ID, task.getId());			
		}

	};
	
	Operation(String displayLabel) {
		this.displayLabel = displayLabel;
	}
	
	private String displayLabel;
	
	public String getDisplayLabel() {
		return displayLabel;
	}
	
	public abstract PreparedStatement getPreparedStatement(Connection conn) throws SQLException;
	
	public abstract void setPreparedStatementValues(PreparedStatement ps, Task task) throws SQLException;
	
	private static void setUpdatePreparedStatementValues(PreparedStatement ps, Task task, String typeName) 
			throws SQLException {
		ps.setString(ConstantsSQL.PS_UPDATE_TASK_IND_TYPE, typeName);
		ps.setInt(ConstantsSQL.PS_UPDATE_TASK_IND_ID, task.getId());	
	}
	
}
