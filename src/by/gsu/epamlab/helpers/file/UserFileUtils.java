package by.gsu.epamlab.helpers.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.exceptions.ValidationException;
import by.gsu.epamlab.model.beans.User;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;

public class UserFileUtils {
	
	private static final Logger LOGGER = Logger.getLogger(UserFileUtils.class.getName());
	
	public static File saveUserFile(FileItem fileItem, User user) throws ValidationException {
		try {
			String uploadDirPath = Constants.FILE_UPLOAD_DIR + File.separator +  
	        		user.getLogin();
	        File uploadDir = new File(uploadDirPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdir();
	        }	
	        String fileName = FilenameUtils.getName(fileItem.getName());
	        String filePath = uploadDirPath + File.separator + fileName;
	        File uploadedFile = checkFilePathRepeat(filePath);
	        fileItem.write(uploadedFile);
	        return uploadedFile;			
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
			throw new ValidationException(Constants.ERR_FILE_UPLOAD);			
		}

	}
	
	public static void deleteFile(File file) throws ValidationException {
		try {
			file.delete();			
		} catch (SecurityException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
			throw new ValidationException(Constants.ERR_FILE_DELETE);
		}
 
	}
	
	public static void sendFileAsResponse(File file, HttpServletResponse response) throws ValidationException {
 		final String BINARY_CONTENT_TYPE = "application/octet-stream";
 		final String RESPONSE_HEADER_KEY = "Content-Disposition";
 		final int BUFFER_SIZE = 4096;
 		final int END_OF_FILE_FLAG = -1; 
 		try {
 			OutputStream outStream = null;
 			FileInputStream fileInputStream = null;
 			try {
 				if(!file.exists()) {
 					throw new ValidationException(Constants.ERR_FILE_NOT_EXISTS);
 				}			
 				fileInputStream = new FileInputStream(file);
 				String mimeType = Files.probeContentType(file.toPath());
 		        if (mimeType == null) {        
 		            mimeType = BINARY_CONTENT_TYPE;
 		        }	
 		        response.setContentType(mimeType);
 		        String headerValue = "attachment; filename=\"" + file.getName() + "\"";
 		        response.setHeader(RESPONSE_HEADER_KEY, headerValue);	
 		        outStream = response.getOutputStream();
 		        byte[] buffer = new byte[BUFFER_SIZE];
 		        int bytesRead = END_OF_FILE_FLAG;		         
 		        while ((bytesRead = fileInputStream.read(buffer)) != END_OF_FILE_FLAG) {
 		            outStream.write(buffer, 0, bytesRead);
 		        } 				
 			} finally {
 				if(outStream != null) {
 					outStream.close();
 				}
 				if(fileInputStream != null) {
 					fileInputStream.close();
 				}
 			} 			
 		} catch(IOException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
			throw new ValidationException(Constants.ERR_FILE_DOWNLOAD);
 		} 		
	}
	
	private static File checkFilePathRepeat(String filePath) {
		File file = new File(filePath);
		String fullPath = FilenameUtils.getFullPath(filePath);
		String baseName = FilenameUtils.getBaseName(filePath);
		String extension = FilenameUtils.getExtension(filePath);
		while(file.exists()) {
			baseName += "_";
			file = new File(fullPath + baseName + "." + extension);
		}
		return file;
	}
}
