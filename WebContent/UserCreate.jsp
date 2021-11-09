<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sign Up NexTV</title>
</head>
<body>
<div style="text-align: center">
	<h1 style="color:green;">Sign Up NexTV</h1>
	<form action="usercreate" method="post">
		<p>
			<label for="username">UserName</label>
			<input id="username" name="username" size="30" value="">
		</p>
		<p>
			<label for="email">Email</label>
			<input id="email" name="email" size="30" value="">
		</p>
		<p>
			<label for="password">Password</label>
			<input id="password" name="password" size="30" type="password" value="">
		</p>
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
	</div>
</body>
</html>