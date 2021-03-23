package by.gsu.epamlab.model.interfaces;

import java.util.List;

import by.gsu.epamlab.exceptions.DAOException;
import by.gsu.epamlab.model.beans.Operation;
import by.gsu.epamlab.model.beans.Section;
import by.gsu.epamlab.model.beans.Task;
import by.gsu.epamlab.model.beans.User;

public interface ITaskDAO {
	
	List<Task> getTasks(Section section, User user) throws DAOException; 
	
	void performOperation(Operation operation, List<Task> tasks) throws DAOException;	
	
	void addTask(Task task, User user) throws DAOException;
	
	void updateTaskFile(Task task) throws DAOException;

}
