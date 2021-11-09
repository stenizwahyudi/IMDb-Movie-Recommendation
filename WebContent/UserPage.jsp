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
          
          
          <script type="text/javascript">
        show = async (id) => {
            resp = await fetch("updateWatchList?id=" + id, {
                method: 'POST'
            });
            console.log("resp" + resp);
            inWL = await resp.json();
            console.log(inWL);
            if(inWL === false) {
                alert("Updated  watchlist!")
            }
            else{
                alert("Not updated watchlist!")
            };
        }
        
        deleteSub = async (id) => {
            resp = await fetch("deleteSubscription?id=" + id, {
                method: 'POST'
            });
            inWL = await resp.json();
            // console.log(inWL);
            if(inWL === false) {
                alert("Updated  watchlist!")
            }
            else{
                alert("Not updated watchlist!")
            };
        }
        </script>
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
<%-- <h1>${messages.title}</h1> --%>
<br>
	<h3 class="text-success">My WatchList</h3>
        <table border="1" class="table table-bordered table-striped w-auto">
            <tr>
                <th>Recommendation</th>
                <th>Watched</th>
            </tr>
            <c:forEach items="${watchLists}" var="watchList" >
                <tr>
                    <td><c:out value="${watchList.getRecommendation().getTitle()}" /></td>
                    <td>
                    <c:if test = "${watchList.getHasWatched() == 0}">
                    Not Watched&nbsp;&nbsp;
                    <button class="btn btn-primary" style="float: right;" onclick="show('${watchList.getWatchListId()}');javascript:history.go(0)">Watched</button>
                    </c:if>
                    <c:if test = "${watchList.getHasWatched() == 1}">
                    Watched&nbsp;&nbsp;
                    <button class="btn btn-primary" style="float: right;" onclick="show('${watchList.getWatchListId()}');javascript:history.go(0)">UnWatch</button>
                    </c:if>
                    </td>
                    
                </tr>
            </c:forEach>
       </table>
       <br>
       <h3 class="text-primary">My Subscriptions</h3>
       <table border="1" class="table table-bordered table-striped w-auto">
         <thead class="thead-dark">
            <tr>
                <th>Platform</th>
            </tr>
         </thead>
            <c:forEach items="${userSubscriptions}" var="userSubscription" >
                <tr>
                    <td><c:out value="${userSubscription.getPlatform().getPlatformName()}" />
                    <button class="btn btn-primary" style="float: right;" onclick="deleteSub('${userSubscription.getSubscriptionId()}');javascript:history.go(0)">UnSubscribe</button>
                    </td>
                </tr>
            </c:forEach>
       </table>
       <a href="Search.jsp">Back to search</a>
</body>
</html>
