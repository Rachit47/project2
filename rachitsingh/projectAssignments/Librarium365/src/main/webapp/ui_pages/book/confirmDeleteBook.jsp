<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, library.model.Book"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Librarium360 - Delete Books</title>
</head>
<body>

	<%
    Boolean deleted = (Boolean) request.getAttribute("deleteSuccess");
    if (deleted != null && deleted) {
%>
	<script>alert("Book deleted successfully!");</script>
	<%
    }
%>

	<h2>Remove a Book from the Librarium360</h2>

	<table border="1" cellpadding="10">
		<tr>
			<th>BookId</th>
			<th>Title</th>
			<th>Author</th>
			<th>Category</th>
			<th>Status</th>
			<th>Condition</th>
			<th>Action</th>
		</tr>
		<%
        List<Book> books = (List<Book>) request.getAttribute("books");
        if (books != null && !books.isEmpty()) {
            for (Book book : books) {
    %>
		<tr>
			<td><%= book.getBookID() %></td>
			<td><%= book.getTitle() %></td>
			<td><%= book.getAuthor() %></td>
			<td><%= book.getCategory() %></td>
			<td><%= book.getAvailability() %></td>
			<td><%= book.getCondition() %></td>
			<td>
				<form method="post"
					action="${pageContext.request.contextPath}/deleteBook"
					style="display: inline;">
					<input type="hidden" name="bookId" value="<%= book.getBookID() %>" />
					<button type="submit"
						onclick="return confirm('Are you sure you want to delete this book?');">Delete</button>
				</form>
			</td>
		</tr>
		<%
            }
        } else {
    %>
		<tr>
			<td colspan="7">No books are available.</td>
		</tr>
		<%
        }
    %>
	</table>

</body>
</html>
