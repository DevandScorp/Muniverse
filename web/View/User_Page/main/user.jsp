<%@ page import="java.io.File" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 23.07.2018
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User_Page</title>
    <!--<base href="http://localhost:8080/View/User_Page/" target="_blank">-->
    <!-- Foundation Framework CSS -->
    <link rel="stylesheet" href="http://localhost:8080/View/User_Page/main/css/foundation.min.css">

    <!-- Include font -->
    <link href="https://fonts.googleapis.com/css?family=Lato:400,400i" rel="stylesheet">

    <!-- Include Amplitude JS -->
    <script type="text/javascript" src="http://localhost:8080/View/User_Page/dist/amplitude.js"></script>

    <!-- Foundation jQuery and Functions -->
    <script type="text/javascript" src="http://localhost:8080/View/User_Page/main/js/jquery.js"></script>
    <script type="text/javascript" src="http://localhost:8080/View/User_Page/main/js/foundation.min.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Cutive+Mono" rel="stylesheet">

    <!-- Include Style Sheet -->
    <link rel="stylesheet" type="text/css" href="http://localhost:8080/View/User_Page/main/css/app.css"/>
    <link rel="stylesheet" href="http://localhost:8080/View/css/bootstrap.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
    <link rel="stylesheet" href="http://localhost:8080/View/css/style.css">
    <script type = "text/javascript">
        $(function() {
            $('.show-form').click(function(){
                $('.form').stop().fadeToggle();
            });
        });


    </script>
    <script>
        var dropbox;
        dropbox = document.getElementById("dropbox");
        dropbox.addEventListener("dragenter", dragenter, false);
        dropbox.addEventListener("dragover", dragover, false);
        dropbox.addEventListener("drop", drop, false);
    </script>
</head>
<body>
<div class="text-center">
    <img src="http://localhost:8080/${user.getFilepath()}/logo.jpg" class = "user_img" alt="">
</div>
<div class="text-center">
    <p class="main">${user.getNickname()}</p>
</div>
<div class="text-center"><button type = "button" class = "show-form btn btn-primary" >Add song(and update server resources)</button></div>
<div class="form" style="display:none;">
    <div class="wrapper">
        <form method = "POST" action = "add_song" enctype="multipart/form-data">
            <hr class="sep"/>
            <textarea name="filepath" style = "display: none">${user.getNickname()}</textarea>
            <textarea name="get_id" style = "display: none">${user.getId()}</textarea>
            <div class="group">
                <span class="highlight"></span><span class="bar"></span>
                <input type="file" name="file" id="dropbox" class="inputfile" accept=".mp3" />
                <label>Choose or drag your mp3 file</label>
            </div>
            <div class="btn-box">
                <button class="btn btn-submit" type="submit">Add song</button>
            </div>
        </form>
    </div>
</div>
<div class="text-center">
    <p class="main_2">Here is your playlist</p>
</div>
<div class="grid-x grid-padding-x">
    <div class="large-12 medium-12 small-12 cell">
            ${user.getPlayer()}
    </div>
</div>
<div id="preload">
    <img src="http://localhost:8080/View/User_Page/main/img/previous.svg"/>
    <img src="http://localhost:8080/View/User_Page/main/img/play.svg"/>
    <img src="http://localhost:8080/View/User_Page/main/img/pause.svg"/>
    <img src="http://localhost:8080/View/User_Page/main/img/next.svg"/>
</div>
<button></button>
<script type="text/javascript">
    Amplitude.init({
        "songs": [
            ${user.getJS()}
        ]
    });
</script>
<!--
  Include UX functions JS

  NOTE: These are for handling things outside of the scope of AmplitudeJS
-->
<script type="text/javascript" src="http://localhost:8080/View/User_Page/main/js/functions.js"></script>
</body>
</html>
