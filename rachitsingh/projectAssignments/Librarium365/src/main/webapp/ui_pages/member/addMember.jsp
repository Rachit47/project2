<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Librarium360 - Add Member</title>
</head>
<body>

	<%
    Boolean success = (Boolean) request.getAttribute("submissionSuccess");
    if (success != null) {
%>
	<script>
        alert("<%= success ? "Member added successfully!" : "Failed to add member." %>");
    </script>
	<%
    }
%>

	<h2>Register a New Member to Librarium360</h2>

	<form method="post"
		action="${pageContext.request.contextPath}/InsertMemberServlet">

		<label>Name:</label><br /> <input type="text" name="name" required /><br />
		<br /> <label>Email:</label><br /> <input type="email" name="email"
			required /><br />
		<br /> <label>Mobile:</label><br /> <input type="number" name="mobile"
			required /><br />
		<br /> <label>Gender:</label><br /> <select name="gender" required>
			<option value="M">M</option>
			<option value="F">F</option>
		</select><br />
		<br /> <label>Address:</label><br />
		<textarea name="address" rows="3" cols="30" required></textarea>
		<br />
		<br />

		<button type="submit">Submit</button>
	</form>

</body>
</html>
