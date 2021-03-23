package by.gsu.epamlab.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.gsu.epamlab.constants.Constants;
import by.gsu.epamlab.model.beans.User;


public class LoginFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession session = httpRequest.getSession();
		User user = (User) session.getAttribute(Constants.ATTR_USER);
		if (user == null) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendRedirect(request.getServletContext().getContextPath() + "/" + Constants.JSP_LOGIN); 
			return;
		}
		chain.doFilter(request, response);
	}

}	

