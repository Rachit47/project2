<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.* , library.model.Member"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Librarium - Members</title>
</head>
<body>
	<h1>All Registered Members of Librarium360:</h1>
	<table border="1" cellpadding="10">
		<thead>
			<tr>
				<th>Member ID</th>
				<th>Name</th>
				<th>E-mail</th>
				<th>Contact No.</th>
				<th>Gender</th>
				<th>Address</th>
			</tr>
		</thead>
		<tbody>
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
			</tr>
			<%
        }
    } else {
%>
			<tr>
				<td colspan="6">No members available.</td>
			</tr>
			<%
    }
%>
		</tbody>
	</table>
</body>
</html>