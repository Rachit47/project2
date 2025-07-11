<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, library.model.*, library.enums.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Librarium360 - Reports Dashboard</title>
</head>
<body>
    <h1>📊 Librarium360 - Reports Dashboard</h1>

    <hr />
    <h2>📕 Overdue Books (issued for more than 30 days)</h2>
    <%
        List<IssueBook> overdueBooks = (List<IssueBook>) request.getAttribute("overdueBooks");
        if (overdueBooks != null && !overdueBooks.isEmpty()) {
    %>
        <table border="1" cellpadding="5">
            <tr>
                <th>Issue ID</th>
                <th>Book ID</th>
                <th>Member ID</th>
                <th>Status</th>
                <th>Issue Date</th>
            </tr>
            <%
                for (IssueBook i : overdueBooks) {
            %>
            <tr>
                <td><%= i.getIssueID() %></td>
                <td><%= i.getBookID() %></td>
                <td><%= i.getMemberID() %></td>
                <td><%= i.getStatus().getLabel() %></td>
                <td><%= i.getIssueDate() %></td>
            </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>No overdue books found.</p>
    <% } %>

    <hr />
    <h2>📚 Books Count by Category</h2>
    <%
        Map<String, Long> categoryCounts = (Map<String, Long>) request.getAttribute("categoryCounts");
        if (categoryCounts != null && !categoryCounts.isEmpty()) {
    %>
        <table border="1" cellpadding="5">
            <tr>
                <th>Category</th>
                <th>Count</th>
            </tr>
            <%
                for (Map.Entry<String, Long> entry : categoryCounts.entrySet()) {
            %>
            <tr>
                <td><%= entry.getKey() %></td>
                <td><%= entry.getValue() %></td>
            </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>No book category data available.</p>
    <% } %>

    <hr />
    <h2>👥 Members with Active Book Issues</h2>
    <%
        List<Member> activeMembers = (List<Member>) request.getAttribute("activeMembers");
        if (activeMembers != null && !activeMembers.isEmpty()) {
    %>
        <table border="1" cellpadding="5">
            <tr>
                <th>Member ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Mobile</th>
                <th>Gender</th>
                <th>Address</th>
            </tr>
            <%
                for (Member m : activeMembers) {
            %>
            <tr>
                <td><%= m.getMemberId() %></td>
                <td><%= m.getName() %></td>
                <td><%= m.getEmail() %></td>
                <td><%= m.getMobile() %></td>
                <td><%= m.getGender() %></td>
                <td><%= m.getAddress() %></td>
            </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>No members with active issues.</p>
    <% } %>
</body>
</html>
