<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Librarium360 - Books Manager</title>
</head>
<body>

	<h1>📚 Librarium360 - Book Management Dashboard</h1>

	<hr />
	<jsp:include page="addBook.jsp" />

	<hr />
	<jsp:include page="listAllBooks.jsp" />
	<jsp:include page="updateBook.jsp" />
	<jsp:include page="confirmDeleteBook.jsp" />
</body>
</html>
