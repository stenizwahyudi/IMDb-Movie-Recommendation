<%--
  Created by IntelliJ IDEA.
  User: dianapham
  Date: 4/14/21
  Time: 1:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>NexTv</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script type="text/javascript"
            src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.min.js"></script>
</head>
<body>
<div style="text-align: center">
    <h1 style="color:orange">User Login</h1>
    <form id="loginForm" action="login" method="post">
        <%--@declare id="email"--%><%--@declare id="password"--%><label for="email">Email:</label>
        <input name="email" size="30" />
        <br><br>
        <label for="password">Password:</label>
        <input type="password" name="password" size="30" />
        <br>${message}
        <br><br>
        <button type="submit">Login</button>
        <a href="usercreate">Sign Up</a>
    </form>
</div>
</body>

<script type="text/javascript">

    $(document).ready(function() {
        $("#loginForm").validate({
            rules: {
                email: {
                    required: true,
                    email: true
                },

                password: "required",
            },

            messages: {
                email: {
                    required: "Please enter email",
                    email: "Please enter a valid email address"
                },

                password: "Please enter password"
            }
        });

    });
</script>
</html>
