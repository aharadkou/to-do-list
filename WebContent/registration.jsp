<!DOCTYPE html>
<html>
<head>
<title>Registration</title>
</head>
<jsp:include page="_header.jsp"/>
<body>
	<form method="POST" >
		<div>
			<div>
				<label>Enter login:</label>
			</div>
			<div>
				<input type="text" required />
			</div>
			<div>
				<label>Enter password:</label>
			</div>	
			<div>					
				<input type="password" required />			
			</div>	
			<div>
				<label>Repeat password:</label>
			</div>	
			<div>					
				<input type="password" required />			
			</div>					
		</div>	
		<input type="submit" />
	</form>
</body>
</html>