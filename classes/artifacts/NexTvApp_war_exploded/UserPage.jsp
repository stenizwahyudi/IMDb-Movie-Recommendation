<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UserPage</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-secondary">
        <div class="container-fluid">
            <span class="navbar-brand">Welcome, ${user.getUserName()}</span>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="userpage">My Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="logout">Log Out</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
	<h1>${messages.title}</h1>
        <table border="1">
            <tr>
                <th>WatchListId</th>
                <th>Recommendation</th>
                <th>Time Watched</th>
            </tr>
            <c:forEach items="${watchLists}" var="watchList" >
                <tr>
                    <td><c:out value="${watchList.getWatchListId()}" /></td>
                    <td><c:out value="${watchList.getRecommendation().getTitle()}" /></td>
                    <td><c:out value="${watchList.getHasWatched()}" /></td>
                </tr>
            </c:forEach>
       </table>
       <table border="1">
            <tr>
                <th>SubscriptionId</th>
                <th>Platform</th>
            </tr>
            <c:forEach items="${userSubscriptions}" var="userSubscription" >
                <tr>
                    <td><c:out value="${userSubscription.getSubscriptionId()}" /></td>
                    <td><c:out value="${userSubscription.getPlatform().getPlatformName()}" /></td>
                </tr>
            </c:forEach>
       </table>
       <a href="Search.jsp">Back</a>
</body>
</html>
