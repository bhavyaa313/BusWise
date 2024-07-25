
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resources/css/reset-forgot.css" />">
    <title>Forgot Password</title>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>


    <script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>


    <%--    --%>
    <script src="script.js"></script>


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

        <form   id="forgotForm">
            <div class="form-group">
                <label for="email" class="form-label">Email <span
                        class="text-danger">*</span></label>
                <input type="email" class="form-control custom-input" id="email" name="email" placeholder=""/>


            </div>




            <button type="submit" class="btn btn-dark mt-3">Reset Password</button>
            <h6 class="text-danger text-center"></h6>

        </form>
        <p class="mt-4">Don't have an account? <a href="${pageContext.request.contextPath}/register">Sign up here.</a></p>


<div id="alertContainer"></div>

        <div id="loader" class="text-center mt-3" >
            <div class="spinner-border text-white " role="status">

                <dotlottie-player src="https://lottie.host/99a98a1a-9a81-4e0b-a41f-e5180c93b2cb/Bkho40RfEt.json" background="transparent" speed="1" style="width: 40px; height: 40px;" loop autoplay></dotlottie-player>
            </div>
        </div>
    </div>




</div>





<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>

<script src="<c:url value="/resources/js/reset-forgot.js" />"></script>
</body>

</html>