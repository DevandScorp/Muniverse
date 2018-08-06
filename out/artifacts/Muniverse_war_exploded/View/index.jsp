<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 19.07.2018
  Time: 23:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login/SignUp</title>
    <base href="http://localhost:8080/">
    <%--<meta name="viewport" content="width=device-width, initial-scale=1">--%>
    <link rel="stylesheet" href="View/css/bootstrap.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="View/css/style.css">

</head>
<body>

<div class="container">
    <div class="text-center" id = "image"><img src="View/images/logo_dark_1.png" class = "img-responsive" alt=""></div>
</div>

<div class="wrapper">
    <form method = "POST" action = "login">

        <hr class="sep"/>
        <div class="text-center"><p class="text-primary">Please,log in</p></div>
        <div class="text-center"><h2><p class="text-danger"><%=request.getAttribute("warning")==null?"":request.getAttribute("warning")%></p></h2></div>
        <div class="group">
            <input type="text" required="required" name = "email"/><span class="highlight"></span><span class="bar"></span>
            <label>Email</label>
        </div>
        <div class="group">
            <input type="password" required="required" name = "password"/><span class="highlight"></span><span class="bar"></span>
            <label>Password</label>
        </div>
        <div class="text-center"><p class="text-primary">Don't have an account?</p></div>
        <div class="text-center"><a href="View/signup.jsp"><p class="text-primary">Sign Up</p></a></div>
        <div class="btn-box">
            <button class="btn btn-submit" type="submit">Submit</button>
            <%--<button class="btn btn-signup" type="button">Sign Up</button>--%>
        </div>
    </form>

</div>
<%--<script src="http://localhost:8080/js/bootstrap.js"></script>--%>
</body>
</html>
