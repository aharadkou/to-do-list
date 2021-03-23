<%@page import="by.gsu.epamlab.Constants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<div>
	<span>
		User:
	</span>
       <c:choose>
	 		 <c:when test="${not empty sessionScope.user}">
	 		 <span>${sessionScope.user.login } </span>
	 		 <form name="logout" method="POST" action="${pageContext.request.contextPath}${Constants.PATH_LOGOUT}"></form>
	 		 <a href="#" onclick="Javascript:document.logout.submit()">Logout</a>
	 		 </c:when>
     		 <c:otherwise>
     		 	<span>
  					guest	
  				</span>
  				<a href="${Constants.JSP_LOGIN }">Login</a>     	
  				<a href="${Constants.JSP_REG}">Registrate</a>    	 
			 </c:otherwise>       
	  </c:choose>
</div>