<%@page import="by.gsu.epamlab.Constants"%>
<!DOCTYPE html>
<html>
<head>
<title>Login page</title>
</head>
<jsp:include page="_header.jsp"/>
<body>
	<form method="POST" action="${pageContext.request.contextPath}${Constants.PATH_LOGIN}" >
		<div>
			<div>
				<label>Enter login:</label>
			</div>
			<div>
				<input type="text" required name="${Constants.ATTR_LOGIN }"/>
			</div>
			<div>
				<label>Enter password:</label>
			</div>	
			<div>					
				<input type="password" required name="${Constants.ATTR_PASSWORD }" />			
			</div>		
		</div>	
		<input type="submit" />
	</form>
</body>
</html>