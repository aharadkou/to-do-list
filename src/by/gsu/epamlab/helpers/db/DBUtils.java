package by.gsu.epamlab.helpers.db;

import java.util.logging.Level;
import java.util.logging.Logger;

import by.gsu.epamlab.constants.Constants;

public class DBUtils {
	
	private static final Logger LOGGER = Logger.getLogger(DBUtils.class.getName());
		
	public static void closeRecources(AutoCloseable ... resources) {
		for (AutoCloseable resource : resources) {
			if(resource != null) {
				try {
					resource.close();					
				} catch (Exception ex) {
					LOGGER.log(Level.SEVERE, Constants.ERR_CLOSE_RESOURCE, ex);
				}				
			}
		}
	}
}
