<%@ page contentType="text/html;charset=UTF-8" language="java"
	import="java.util.*, library.model.Member"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Librarium360 - Delete Member Details</title>
</head>
<body>

	<%
    Boolean success = (Boolean) request.getAttribute("deleteSuccess");
    if (success != null && success) {
%>
	<script>alert("Member deleted successfully!");</script>
	<%
    } else if (success != null) {
%>
	<script>alert("Failed to delete member.");</script>
	<%
    }
%>

	<h2>Delete Member Details</h2>

	<table border="1" cellpadding="10">
		<tr>
			<th>Member ID</th>
			<th>Name</th>
			<th>Email</th>
			<th>Mobile</th>
			<th>Gender</th>
			<th>Address</th>
			<th>Action</th>
		</tr>

		<%
    List<Member> members = (List<Member>) request.getAttribute("members");
    if (members != null && !members.isEmpty()) {
        for (Member m : members) {
%>
		<tr>
			<td><%= m.getMemberId() %></td>
			<td><%= m.getName() %></td>
			<td><%= m.getEmail() %></td>
			<td><%= m.getMobile() %></td>
			<td><%= m.getGender() %></td>
			<td><%= m.getAddress() %></td>
			<td>
				<form method="post"
					action="${pageContext.request.contextPath}/DeleteMemberServlet"
					style="display: inline;">
					<input type="hidden" name="memberId" value="<%= m.getMemberId() %>" />
					<button type="submit"
						onclick="return confirm('Are you sure you want to delete this member?');">Delete</button>
				</form>
			</td>
		</tr>
		<%
        }
    } else {
%>
		<tr>
			<td colspan="7">No members to show.</td>
		</tr>
		<%
    }
%>
	</table>

</body>
</html>
