<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, library.model.Book"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Librarium - Books</title>
</head>
<body>
	<h1>All Registered Books of Librarium360:</h1>
	<table border="1" cellpadding="10">
		<thead>
			<tr>
				<th>Book ID</th>
				<th>Title</th>
				<th>Author</th>
				<th>Category</th>
				<th>Status</th>
				<th>Condition</th>
			</tr>
		</thead>
		<tbody>
			<%
			List<Book> books = (List<Book>) request.getAttribute("books");
			if (books != null) {
				for (Book book : books) {
			%>
			<tr>
				<td><%=book.getBookID()%></td>
				<td><%=book.getTitle()%></td>
				<td><%=book.getAuthor()%></td>
				<td><%=book.getCategory()%></td>
				<td><%=book.getAvailability()%></td>
				<td><%=book.getCondition()%></td>
			</tr>
			<%
			}
			} else {
			%>
			<tr>
				<td colspan="6">No books to display.</td>
			</tr>
			<%
}
%>
		</tbody>
	</table>
</body>
</html>