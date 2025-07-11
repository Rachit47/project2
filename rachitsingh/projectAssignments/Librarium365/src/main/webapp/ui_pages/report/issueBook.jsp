<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, library.model.IssueBook" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Librarium360 - Issue Book</title>
</head>
<body>

<%
    if (request.getAttribute("issueSuccess") != null) {
%>
    <script>alert("<%= request.getAttribute("issueSuccess") %>");</script>
<%
    } else if (request.getAttribute("issueError") != null) {
%>
    <script>alert("<%= request.getAttribute("issueError") %>");</script>
<%
    }

    if (request.getAttribute("returnSuccess") != null) {
%>
    <script>alert("<%= request.getAttribute("returnSuccess") %>");</script>
<%
    } else if (request.getAttribute("returnError") != null) {
%>
    <script>alert("<%= request.getAttribute("returnError") %>");</script>
<%
    }
%>

<h2>📓 Issue a Book to a Member of Librarium360</h2>
<form method="post" action="${pageContext.request.contextPath}/IssueBookServlet">
    <input type="hidden" name="action" value="issue" />
    
    <label>Book ID:</label><br/>
    <input type="text" name="bookId" required /><br/><br/>
    
    <label>Member ID:</label><br/>
    <input type="text" name="memberId" required /><br/><br/>
    
    <button type="submit">Issue Book</button>
</form>

<br/>
<hr/>

<h2>↩️ Return a Book to Librarium360</h2>
<form method="post" action="${pageContext.request.contextPath}/IssueBookServlet">
    <input type="hidden" name="action" value="return" />
    
    <label>Issue ID:</label><br/>
    <input type="number" name="issueId" required /><br/><br/>
    
    <button type="submit">Return Book</button>
</form>

</body>
</html>
