<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Librarium360 - Members Manager</title>
</head>
<body>

	<h1>👤 Librarium360 - Member Management Dashboard</h1>

	<hr />
	<jsp:include page="addMember.jsp" />

	<hr />
	<jsp:include page="listAllMembers.jsp" />
	<jsp:include page="updateMember.jsp" />
	<jsp:include page="confirmDeleteMember.jsp" />

</body>
</html>

