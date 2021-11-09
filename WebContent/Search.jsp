<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2021/3/30
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>NexTV</title>
       <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">

    <script type="text/javascript">
        show = async (id) => {
            resp = await fetch("addToWatchList?id=" + id, {
                method: 'POST'
            });
            inWL = await resp.json();
            // console.log(inWL);
            if(inWL === false) {
                alert("Added to watchlist!")
            }
            else{
                alert("Already in watchlist!")
            };
        }

        sub = async (platformId) => {
            resp = await fetch("addToSubcriptionList?subId=" + platformId, {
                method: 'POST'
            });
            inSL = await resp.json();

            if (inSL === false) {
                alert("Subcribed!")
            }
            else {
                alert("Alredy Subcribed!")
            }
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
<div class="container my-5 col-8">
    <h1 style="color:green;">All you need is Search!! Find a TV show or movie</h1>
    <form action="search" method="get">
        <div class="row my-2">
            <div class="col">
                Name:
            </div>
            <div class="col-4">
                <input name="title" class="form-control" value=""/>
            </div>
            <div class="col">
                Genre:
            </div>
            <div class="col-4">
                <input name="genres" class="form-control" value=""/>
            </div>
		</div>
		<div class="row my-2">
            <div class="col">
                Language:
            </div>
            <div class="col-4">
                <input name="language" class="form-control" value=""/>
            </div>
            <div class="col">
                Year:
            </div>
            <div class="col-4">
                <input name="year" class="form-control" value=""/>
            </div>
        </div>
        <div class="col">
            <button type="submit" class="btn btn-primary col-12">Search</button>
        </div>
    </form>
    <br>
    <div class="row">
        <ul class="list-group">
            <c:forEach items="${recommendations}" var="recommendation">
                <li class="list-group-item">
                    <div class="row">
                        <div class="col">
                            <h3>${recommendation.getKey().getTitle()}</h3>
                            <p>
                                Genre: ${recommendation.getKey().getGenre()}
                            </p>
                            <p>
                                Year: ${recommendation.getKey().getYear()}
                            </p>
                            <p>
                                Language: ${recommendation.getKey().getLanguage()}
                            </p>
                            <p>
                                <button class="btn btn-primary" onclick="show('${recommendation.getKey().getRecommendationId()}');">Add to Watchlist</button>
                            </p>
                        </div>
                        <div class="col">
                            <p>
                                Available on:
                            </p>
                            <c:forEach items="${recommendation.getValue()}" var="platform">
                                <p class="d-flex justify-content-between align-items-center">
                                        ${platform.getPlatformName()}
                                        <c:if test = "${platform.getPlatformName() == 'NETFLIX'}">
              								<img src="Netflix_Logo.png" alt="..." class="img-thumbnail rounded" width="80" height="80">
            							</c:if>
            							<c:if test = "${platform.getPlatformName() == 'HULU'}">
              								<img src="huluLogo.png" alt="..." class="img-thumbnail rounded" width="80" height="80">
            							</c:if>
							            <c:if test = "${platform.getPlatformName() == 'PRIMEVIDEO'}">
							              <img src="primeVideoLogo.png" alt="..." class="img-thumbnail rounded" width="80" height="80">
							            </c:if>
							            <c:if test = "${platform.getPlatformName() == 'DISNEY+'}">
              								<img src="disneyLogo.jpeg" alt="..." class="img-thumbnail rounded " width="80" height="80">
            							</c:if>
                                    <button class="btn btn-primary" onclick="sub('${platform.getPlatformId()}')">Subscribe</button>
                                </p>
                            </c:forEach>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>

</div>
</body>
</html>
