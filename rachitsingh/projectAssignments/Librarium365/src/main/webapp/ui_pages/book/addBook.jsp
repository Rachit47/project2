<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*, library.model.Book"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Librarium360 - Add New Books</title>
</head>
<body>
	<%
    Boolean success = (Boolean) request.getAttribute("submissionSuccess");
    if (success != null && success) {
%>
	<script>
        alert("Books submitted successfully!");
    </script>
	<%
    }
%>
	<h2>Add a New Book</h2>

	<form method="post"
		action="${pageContext.request.contextPath}/InsertBookServlet">
		<input type="hidden" name="action" value="add" /> <label>Title:</label><br />
		<input type="text" name="title" required /><br />
		<br /> <label>Author:</label><br /> <input type="text" name="author"
			required /><br />
		<br /> <label>Category:</label><br /> <input type="text"
			name="category" required /><br />
		<br /> <label>Condition:</label><br /> <select name="condition">
			<option value="A">Active</option>
			<option value="I">Inactive</option>
		</select><br />
		<br /> <label>Availability:</label><br /> <select name="status">
			<option value="A">Available</option>
			<option value="I">Issued</option>
		</select><br />
		<br />

		<button type="submit">Add Book to Batch</button>
	</form>

	<br />

	<form method="post"
		action="${pageContext.request.contextPath}/InsertBookServlet">
		<input type="hidden" name="action" value="submit" />
		<button type="submit">Submit All Books</button>
	</form>

	<hr />

	<h3>Batch of books to be added:</h3>
	<%
    List<Book> batchedBooks = (List<Book>) session.getAttribute("batchedBooks");
    if (batchedBooks != null && !batchedBooks.isEmpty()) {
%>
	<ul>
		<% for (Book book : batchedBooks) { %>
		<li><strong><%= book.getTitle() %></strong> by <%= book.getAuthor() %>
			(Category: <%= book.getCategory() %>, Condition: <%= book.getCondition() %>,
			Status: <%= book.getAvailability() %>)</li>
		<% } %>
	</ul>
	<%
    } else {
%>
	<p>No books added yet.</p>
	<%
    }
%>

</body>
</html>
