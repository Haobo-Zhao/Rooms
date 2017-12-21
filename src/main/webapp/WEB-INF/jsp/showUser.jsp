<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="hello.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
<title>Specific Information</title>
</head>
<body>

<div class="container">
    <h1>All user</h1>
    <hr>
    <%
        for (User user : (Iterable<User>)request.getAttribute("users")) {
    %>
            <div class="fluid">
                    <p>
                        <b><%= user.getFirstname() %></b> <b><%= user.getLastname() %></b>
                    <p>
            </div>
    <%
        }
    %>
</div>
	
</body>
</html>