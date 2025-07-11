<%@ page contentType="text/html;charset=UTF-8" language="java"
	import="java.util.*, library.model.Member"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Librarium - Update Member</title>
<script>
        function populateForm(id, name, email, mobile, gender, address) {
            document.getElementById("updateForm").style.display = "block";
            document.getElementById("memberId").value = id;
            document.getElementById("name").value = name;
            document.getElementById("email").value = email;
            document.getElementById("mobile").value = mobile;
            document.getElementById("gender").value = gender;
            document.getElementById("address").value = address;
        }
    </script>
</head>
<body>

	<%
    Boolean success = (Boolean) request.getAttribute("updateSuccess");
    if (success != null && success) {
%>
	<script>alert("Member updated successfully!");</script>
	<%
    } else if (success != null) {
%>
	<script>alert("Failed to update member.");</script>
	<%
    }
%>

	<h2>Update Member Details</h2>

	<div id="updateForm"
		style="display: none; border: 1px solid #ccc; padding: 10px; margin-bottom: 20px;">
		<form method="post"
			action="${pageContext.request.contextPath}/UpdateMemberServlet">
			<label>Member ID:</label><br> <input type="text" id="memberId"
				name="memberId" readonly><br>
			<br> <label>Name:</label><br> <input type="text" id="name"
				name="name" required><br>
			<br> <label>Email:</label><br> <input type="email"
				id="email" name="email" required><br>
			<br> <label>Mobile:</label><br> <input type="text"
				id="mobile" name="mobile" required><br>
			<br> <label>Gender:</label><br> <select id="gender"
				name="gender">
				<option value="M">M</option>
				<option value="F">F</option>
			</select><br>
			<br> <label>Address:</label><br>
			<textarea id="address" name="address" required></textarea>
			<br>
			<br>

			<button type="submit">Update Member</button>
		</form>
	</div>

	<!-- Member list table -->
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
				<button
					onclick="populateForm('<%= m.getMemberId() %>', '<%= m.getName() %>', '<%= m.getEmail() %>', '<%= m.getMobile() %>', '<%= m.getGender() %>', '<%= m.getAddress().replaceAll("'", "\\\\'") %>')">Update</button>
			</td>
		</tr>
		<%
            }
        } else {
    %>
		<tr>
			<td colspan="7">No members found.</td>
		</tr>
		<%
        }
    %>
	</table>

</body>
</html>
