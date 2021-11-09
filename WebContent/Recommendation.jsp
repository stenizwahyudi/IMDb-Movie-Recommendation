<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2021/4/20
  Time: 2:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NexTV</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">

</head>
<body>

<nav class="navbar navbar-expand-lg navbar-secondary bg-secondary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Welcome, ${user.getUserName()}</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="#">My Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Log Out</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container my-5 col-8">
    <h1>${recommendation.getTitle()}</h1>
    <div class="row">
        <div class="col-1">
            Genre:
        </div>
        <div class="col">
            ${recommendation.getGenre()}
        </div>
    </div>
    <div class="row">
        <div class="col-1">
            Year:
        </div>
        <div class="col">
            ${recommendation.getYear()}
        </div>
    </div>
    <div class="row">
        <div class="col-1">
            Language:
        </div>
        <div class="col">
            ${recommendation.getLanguage()}
        </div>
    </div>
    <div class="row">
        <div class="col-1">
            Country:
        </div>
        <div class="col">
            ${recommendation.getCountry()}
        </div>
    </div>
    <div class="row">
        <div class="col-1">
            Country:
        </div>
        <div class="col">
            ${recommendation.getCountry()}
        </div>
    </div>
</div>
</body>
</html>
