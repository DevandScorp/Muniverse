<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 20.07.2018
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <meta charset="UTF-8">
    <title>Login</title>
    <base href="http://localhost:8080/">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="View/css/bootstrap.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="View/css/style.css">
    <script src="View/js/test.js"></script>
</head>
<body>
<script>
    var dropbox;
    dropbox = document.getElementById("dropbox");
    dropbox.addEventListener("dragenter", dragenter, false);
    dropbox.addEventListener("dragover", dragover, false);
    dropbox.addEventListener("drop", drop, false);
</script>
<script type = "text/javascript">
    function validate(form_id,email) {
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
        var address = document.forms[form_id].elements[email].value;
        if(reg.test(address) == false) {
            alert('Введите корректный e-mail');
            return false;
        }
    }
</script>
<div class="container">
    <div class="text-center" id = "image"><img src="View/images/logo_dark_1.png" class = "img-responsive" alt=""></div>
</div>

<div class="wrapper">
    <form method = "POST" action = "controller" enctype="multipart/form-data" id="form_id" onsubmit="javascript:return validate('form_id','email');">
       <div class="text-center"><h2><p class="text-danger"><%=request.getAttribute("warning")==null?"":request.getAttribute("warning")%></p></h2></div>
        <hr class="sep"/>
        <div class="text-center"><p class="text-primary">Please,sign up(you'll have to restart server after creating new user)</p></div>
        <div class="group">
            <input type="text" required="required" name = "nickname"/><span class="highlight"></span><span class="bar"></span>
            <label>Nickname</label>
        </div>
        <div class="group">
            <input type="text" required="required" name = "email" id = "email"/><span class="highlight"></span><span class="bar"></span>
            <label>Email</label>
        </div>
        <div class="group">
            <input type="password" required="required" name = "password"/><span class="highlight"></span><span class="bar"></span>
            <label>Password</label>
        </div>
        <div class="group">
            <span class="highlight"></span><span class="bar"></span>
            <input type="file" name="file" required = "required" id="dropbox" class="inputfile" accept=".jpg"/>
            <label>Choose your icon image</label>
        </div>
        <div class="btn-box">
            <button class="btn btn-submit" type="submit">Submit</button>
        </div>
    </form>

</div>
<script src="View/js/bootstrap.js"></script>
</body>
</html>
