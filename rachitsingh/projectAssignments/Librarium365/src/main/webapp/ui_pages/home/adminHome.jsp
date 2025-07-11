<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Librarium360 - Admin Dashboard</title>
</head>
<body
	style="font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding-top: 60px;">

	<h1 style="color: #2c3e50;">📘 Welcome to Librarium360 Admin
		Dashboard</h1>
	<p style="font-size: 18px; color: #555;">Manage your library
		operations from here.</p>

	<div style="margin-top: 40px;">
		<form action="${pageContext.request.contextPath}/manageBooks"
			method="get" style="margin: 20px;">
			<button type="submit"
				style="padding: 15px 30px; font-size: 18px; background-color: #3498db; color: white; border: none; border-radius: 8px; cursor: pointer;">
				📚 Manage Books</button>
		</form>

		<form action="${pageContext.request.contextPath}/manageMembers"
			method="get" style="margin: 20px;">
			<button type="submit"
				style="padding: 15px 30px; font-size: 18px; background-color: #27ae60; color: white; border: none; border-radius: 8px; cursor: pointer;">
				👤 Manage Members</button>
		</form>

		<form action="${pageContext.request.contextPath}/reportMain"
			method="get" style="margin: 20px;">
			<button type="submit"
				style="padding: 15px 30px; font-size: 18px; background-color: #e67e22; color: white; border: none; border-radius: 8px; cursor: pointer;">
				📊 Generate Reports</button>
		</form>
	</div>

</body>
</html>
