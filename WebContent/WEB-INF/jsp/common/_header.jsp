<%@page import="by.gsu.epamlab.constants.Constants"%>
<%@page import="by.gsu.epamlab.model.beans.Section"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/lib/bootstrap.css"/>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/lib/jquery.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/main.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/lib/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/lib/bootstrap-confirmation.js"></script>
<nav class="navbar-expand-lg bg-light navbar-light">
	<div class="navbar-collapse" >
		<c:if test="${not empty sessionScope.user}">
		    <ul class="nav nav-tabs w-100 justify-content-start">
				<c:forEach items="<%=Section.values()%>" var="section">
					<li class="nav-item">
						<a class='nav-link
									<c:if test="${requestScope.section eq section}">
										active
									</c:if>						
						   		 '
						href="${pageContext.request.contextPath}${Constants.PATH_TASKS}?${Constants.ATTR_SECTION}=${section}">
							${section.displayLabel}</a>
					</li>		
				</c:forEach>
		    </ul>			
		</c:if>
	    <ul class="navbar-nav w-100 justify-content-end">
		  	<c:choose>
				 <c:when test="${not empty sessionScope.user}">
					<li class="nav-item">
						<a class="nav-link">User: ${user.login}</a>
					</li>
					<li class="nav-item">
						<form class="header-form" name="logout" method="POST" 
							action="${pageContext.request.contextPath}${Constants.PATH_LOGOUT}"></form>
		 			 	<a class="nav-link" href="#" onclick="Javascript:document.logout.submit()">Logout</a>
					</li>
				 </c:when>
		    	 <c:otherwise>
	  				<li class="nav-item">
	  					<a class="nav-link" href="${Constants.JSP_LOGIN }">Login</a>
	  				</li> 
	  				<li class="nav-item">
						<a class="nav-link" href="${Constants.JSP_REG}">Registration</a> 
	  				</li>     	  				    	 				  		     		 
				 </c:otherwise>       
		 	</c:choose>
    	</ul>	
	</div>
</nav>
