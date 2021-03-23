package by.gsu.epamlab.helpers;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MultipartRequestWrapper extends HttpServletRequestWrapper {

	private final Map<String, String[]> parameterMap;	
	
	public MultipartRequestWrapper(HttpServletRequest request, Map<String, String[]> parameterMap) {
		super(request);
		this.parameterMap = parameterMap;
	}

    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }
    public String[] getParameterValues(String name) {
        return parameterMap.get(name);
    }
    public String getParameter(String name) {
        String[] params = getParameterValues(name);
        return (params != null && params.length > 0) ? params[0] : null;
    }
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(parameterMap.keySet());
    }	
	
}
