<%@ page contentType="text/html;charset=UTF-8" language="java"
	import="java.util.*,library.model.Book"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update Books</title>
</head>
<body>

	<% Boolean updated = (Boolean) request.getAttribute("updateSuccess"); %>
	<% if (updated != null && updated) { %>
	<script>alert("Book updated successfully!");</script>
	<% } %>

	<% Book selectedBook = (Book) request.getAttribute("selectedBook"); %>
	<% if (selectedBook != null) { %>
	<h2>Edit Book Details</h2>
	<form method="post"
		action="${pageContext.request.contextPath}/updateBook">
		<input type="hidden" name="action" value="update" /> <label>Book
			ID (non-editable):</label><br /> <input type="text" name="bookId"
			value="<%= selectedBook.getBookID() %>" readonly /><br />
		<br /> <label>Title:</label><br /> <input type="text" name="title"
			value="<%= selectedBook.getTitle() %>" required /><br />
		<br /> <label>Author:</label><br /> <input type="text" name="author"
			value="<%= selectedBook.getAuthor() %>" required /><br />
		<br /> <label>Category:</label><br /> <input type="text"
			name="category" value="<%= selectedBook.getCategory() %>" required /><br />
		<br /> <label>Condition:</label><br /> <select name="condition">
			<option value="A"
				<%= selectedBook.getCondition().name().equals("ACTIVE") ? "selected" : "" %>>Active</option>
			<option value="I"
				<%= selectedBook.getCondition().name().equals("INACTIVE") ? "selected" : "" %>>Inactive</option>
		</select><br />
		<br /> <label>Status:</label><br /> <select name="status">
			<option value="A"
				<%= selectedBook.getAvailability().name().equals("AVAILABLE") ? "selected" : "" %>>Available</option>
			<option value="I"
				<%= selectedBook.getAvailability().name().equals("ISSUED") ? "selected" : "" %>>Issued</option>
		</select><br />
		<br />

		<button type="submit">Update Book</button>
	</form>
	<hr />
	<% } %>

	<h2>All Registered Books</h2>
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
    if (books != null) {
        for (Book b : books) {
%>
		<tr>
			<td><%= b.getBookID() %></td>
			<td><%= b.getTitle() %></td>
			<td><%= b.getAuthor() %></td>
			<td><%= b.getCategory() %></td>
			<td><%= b.getAvailability() %></td>
			<td><%= b.getCondition() %></td>
			<td>
				<form method="post"
					action="${pageContext.request.contextPath}/updateBook"
					style="display: inline;">
					<input type="hidden" name="action" value="edit" /> <input
						type="hidden" name="bookId" value="<%= b.getBookID() %>" />
					<button type="submit">Edit</button>
				</form>
			</td>
		</tr>
		<%
        }
    }
%>
	</table>

</body>
</html>
