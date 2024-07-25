<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 29-05-2024
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <title>Login</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/reset-forgot.css" />">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>



    <style>




        body {
            background-color: #ffffff;
            background-image: url("<c:url value="/resources/image/bg.jpeg"/>");
            /* Replace with your image path */
            background-size: cover;
            /* Ensures the image covers the whole background */
            background-position: center;
            /* Centers the background image */
            background-repeat: no-repeat;
            /* Prevents the image from repeating */

            height: 100vh !important;
        }


    </style>
</head>

<body>


<div class="container-fluid d-flex align-items-center justify-content-center h-100">

    <div class=" p-5 bg-white">
        <h2 class="mb-3">Good to see you again!</h2>

        <form  action="${pageContext.request.contextPath}/success" method="post" id="loginForm" >
            <div class="form-group">
                <label for="email" class="form-label">Email <span
                        class="text-danger">*</span></label>
                <input type="email" class="form-control custom-input" id="email" name="email" placeholder=""/>
<%--                <form:errors path="email" cssClass="error-message" />--%>

            </div>
            <div class="form-group mb-4">
                <label for="password" class="form-label">Password<span
                        class="text-danger">*</span></label>
                <input type="password" class="form-control custom-input" id="password" name="password1" />
<%--                <form:errors path="password" cssClass="error-message" />--%>
            </div>

            <h6 class="text-danger text-center">${message }</h6>

            <button type="submit" class="btn btn-dark btn-block">Login</button>
            <a href="${pageContext.request.contextPath}/forgot" class="d-block text-center mt-2">Forgot password?</a>
        </form>
        <p class="mt-4">Don't have an account? <a href="${pageContext.request.contextPath}/register">Sign up here.</a></p>


        <div id="loader" class="text-center mt-3" >
            <div class="spinner-border text-white " role="status">

                <dotlottie-player src="https://lottie.host/99a98a1a-9a81-4e0b-a41f-e5180c93b2cb/Bkho40RfEt.json" background="transparent" speed="1" style="width: 40px; height: 40px;" loop autoplay></dotlottie-player>
            </div>
        </div>

    </div>



</div>
<script>
    $(document).ready(function() {
        $('#loader').hide();
        $('#loginForm').submit(function(event) {

            $('#loader').show();


            setTimeout(function() {
                $('#loader').hide();
            }, 4000);


        });
    });

</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
<script src="<c:url value="/resources/js/validate.js" />"></script>
</body>

</html>