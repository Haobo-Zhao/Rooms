<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="hello.*"%>
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
  
  <c:if test="${post.username == user.username || user.username == \"admin\"}">
  <div class="container" align="center">
  		<!-- logged in -->
         	<sec:authorize access="isAuthenticated()">
         	<a href="/post/${post.id}/edit" class="btn btn-info">Edit this post</a>
         	<a href="/post/${post.id}/delete" class="btn btn-danger">Delete this post</a>
         	<hr>
  		</sec:authorize>
  	</div>
  </c:if>
  	
  
  
  
  <div class="container">
  		<% Post post = (Post)request.getAttribute("post");%>
  		<p>Post Time: <%= post.getDate() %></p>
  		
  		<div class="thumbnail">
  		      <img src="/image/<%= post.getId() %>.jpg" alt="image" style="width: 100%;">
  	    </div>
  	    <br>
  		<h2><%= post.getDescription() %></h2>
  		<hr>
  		<h3>$<%= post.getRent() %>/month</h3>
  		<hr>
  		<h3>
  			<span><%= post.getContact() %></span>
  		</h3>
  		<hr>
  		<h3>Location : <%= post.getLocation() %></h3>
  		<hr>
  		<h2>Posting Id: <%= post.getId() + 10000 %></h1>
  		<hr>
  		<h2>All the comments</h2>
  		<c:forEach items="${comments}" var="comment">
     			<div class="container" style="boarder">
  				<p>${comment.user} said on ${comment.date}</p>
  			  	<p><h4>${comment.description}</h4></p>
    
      			<sec:authentication var="user" property="principal" />
  	    		<c:if test="${comment.user == user.username || user.username == \"admin\"}">
  	    		<form action="/post/${post.id}/comment/delete/${comment.id}" method="POST" autofocus>
  				  	<div class="container">
  				  		<a href="/post/${post.id}/comment/edit/${comment.id}" class="btn btn-info" style="float: right;margin-right: 30px;">edit</a>
  				  		<button type="submit" class="btn btn-danger btn-sm" style="float: right;margin-right: 30px;">delete</button>
  				  	</div>
  				</form>
      			</c:if>
      			<hr>
  		  	</div>
  		</c:forEach>
  		
  		
  		<h1>Leave a comment here</h1>
  		<form action="/post/${post.id}/comment" method="POST">
  			<div class="form-group">
  			    	<input type="text" name="description" class="form-control">
  		    </div>
  			<input type="hidden" name="postId" value="${post.id}">
  			<input type="hidden" name="user" value="<%= request.getUserPrincipal().getName() %>">
    		  	<button type="submit" class="btn btn-default">Submit</button>
    		</form>
    </div>
    </body>
    </html>
	
</body>
</html>