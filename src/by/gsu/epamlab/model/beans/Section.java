package by.gsu.epamlab.model.beans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import by.gsu.epamlab.constants.ConstantsSQL;
import by.gsu.epamlab.helpers.DateUtils;

public enum Section {
	
	
	TODAY("Today", new Operation[] { Operation.FIX, Operation.TO_BIN }, true) {

		@Override
		public PreparedStatement getPreparedStatement(Connection conn, User user) throws SQLException {
			return getSelectPreparedStatement(conn, user, ConstantsSQL.SELECT_TODAY_TASKS);
		}
		
	}, 
	
	TOMORROW("Tomorrow", new Operation[] {Operation.FIX, Operation.TO_BIN }, true) {

		@Override
		public PreparedStatement getPreparedStatement(Connection conn, User user) throws SQLException {
			return getSelectPreparedStatement(conn, user, ConstantsSQL.SELECT_TOMORROW_TASKS);
			
		}
		
		@Override
		public Date getSectionDate() {
			return DateUtils.getTodayDateWithOffset(1);
		}	
		
	}, 
	
	SOMEDAY("Someday", new Operation[] { Operation.FIX, Operation.TO_BIN }, true) {

		@Override
		public PreparedStatement getPreparedStatement(Connection conn, User user) throws SQLException {
			return getSelectPreparedStatement(conn, user, ConstantsSQL.SELECT_SOMEDAY_TASKS);
		}
		
		@Override
		public Date getSectionDate() {
			return DateUtils.getTodayDateWithOffset(2);
		}	
		
	},
	
	FIXED("Fixed", new Operation[] { Operation.TO_BIN }, false) {

		@Override
		public PreparedStatement getPreparedStatement(Connection conn, User user) throws SQLException {
			return getSelectTypePreparedStatement(conn, user, toString());
		}
		
	}, 
	
	RECYCLE_BIN("Recycle bin", new Operation[] { Operation.RESTORE, Operation.DELETE }, false) {

		@Override
		public PreparedStatement getPreparedStatement(Connection conn, User user) throws SQLException {
			return getSelectTypePreparedStatement(conn, user, toString());
		}
		
	};
	
	private String displayLabel;
	
	private Operation[] operations;
	
	private boolean addingPermitted;
	
	Section(String displayLabel, Operation[] operations, boolean addingPermitted) {
		this.operations = operations;
		this.displayLabel = displayLabel;
		this.addingPermitted = addingPermitted;
	}
	
	public abstract PreparedStatement getPreparedStatement(Connection conn, User user) throws SQLException;
	
	public Date getSectionDate() {
		return DateUtils.getTodayDateWithOffset(0);
	}
	
	public String getDisplayLabel() {
		return displayLabel;
	}
	
	public Operation[] getOperations() {
		return operations;
	}
	
	public boolean isAddingPermitted() {
		return addingPermitted;
	}
	
	private static PreparedStatement getSelectPreparedStatement(Connection conn, User user, String sqlQuery) 
			throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sqlQuery);
		ps.setString(ConstantsSQL.PS_SELECT_TASKS_IND_LOGIN, user.getLogin());
		return ps;		
	}
	
	private static PreparedStatement getSelectTypePreparedStatement(Connection conn, User user, String typeName) 
			throws SQLException {
		PreparedStatement ps = conn.prepareStatement(ConstantsSQL.SELECT_TASKS_WITH_TYPE);
		ps.setString(ConstantsSQL.PS_SELECT_TYPE_TASKS_IND_TYPE, typeName);
		ps.setString(ConstantsSQL.PS_SELECT_TYPE_TASKS_IND_LOGIN, user.getLogin());	
		return ps;
	}	
	
}
