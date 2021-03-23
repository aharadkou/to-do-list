package by.gsu.epamlab.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.helpers.MultipartRequestWrapper;

public class MultipartFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletRequest parsedRequest = parseRequest(httpRequest);
        chain.doFilter(parsedRequest, response);
	}

	@SuppressWarnings("unchecked")
	private HttpServletRequest parseRequest(HttpServletRequest request) throws ServletException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            return request;
        }
        List<FileItem> multipartItems = null;
        try {
        	multipartItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            Map<String, String[]> parameterMap = new HashMap<String, String[]>();
            for (FileItem multipartItem : multipartItems) {
                if (multipartItem.isFormField()) {
                    processFormField(multipartItem, parameterMap);
                } else {
                    processFileField(multipartItem, request);
                }
            }
            return new MultipartRequestWrapper(request, parameterMap);       
        } catch(FileUploadException ex) {
        	throw new ServletException(ex);
        }
	}
	
    private void processFormField(FileItem formField, Map<String, String[]> parameterMap) {
        String name = formField.getFieldName();
        String value = formField.getString();
        String[] values = parameterMap.get(name);
        if (values == null) {
            parameterMap.put(name, new String[] { value });
        } else {
            int length = values.length;
            String[] newValues = new String[length + 1];
            newValues = Arrays.copyOf(values, length + 1);
            newValues[length] = value;
            parameterMap.put(name, newValues);
        }
    }	

    private void processFileField(FileItem fileField, HttpServletRequest request) {
        if (fileField.getName().length() == 0) {
            request.setAttribute(fileField.getFieldName(), null);
        } else if (fileField.getSize() > Constants.MAX_FILE_BYTE_SIZE) {
            request.setAttribute(fileField.getFieldName(), new FileUploadException(
                Constants.ERR_FILE_BIG));
            fileField.delete();
        } else {
            request.setAttribute(fileField.getFieldName(), fileField);
        }
    }
    
}
