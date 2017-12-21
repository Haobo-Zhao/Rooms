<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="hello.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
<title>Home Page</title>
</head>
<body>
<nav class="navbar navbar-default">
      <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/">Home</a>
        </div>
    
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li><a href="/about">About Us <span class="sr-only">(current)</span></a></li>
            <li><a href="/contact">Contact Us</a></li>
          </ul>
          
			<sec:authorize access="isAnonymous()">
	          	<ul class="nav navbar-nav navbar-right">
		            <li><a href="/login">Log in</a></li>
		            <li><a href="/user">Sign up</a></li>
	          	</ul>
			</sec:authorize>
			
			<!-- logged in -->
          	<sec:authorize access="isAuthenticated()">
          	<sec:authentication var="user" property="principal" />
	          	<ul class="nav navbar-nav navbar-right">
		            <li><a href="/">Welcome, ${user.username}!</a></li>
		            <li><a href="/logout">Log out</a></li>
	          	</ul>
   			</sec:authorize>
   			
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>

    <div class="container">
    	<h2>
    		<p>Shoot me emails at haobo4608@gmail.com.</p>
    		<p>Generally, I respond super quick.</p> 
    	</h2>
    </div>
</body>
</html>