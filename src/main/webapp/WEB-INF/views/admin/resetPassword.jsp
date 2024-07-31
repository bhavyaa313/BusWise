
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
    <link rel="icon" type="image/x-icon" href="<c:url value="/resources/image/logo.png"/>"/>
    <title>Reset Password</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/reset-forgot.css" />">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
    <script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>
    <script src="<c:url value="/resources/js/reset-forgot.js" />"></script>




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
        <h2 class="mb-3">Reset Your Password !</h2>

        <form   id="resetForm">
            <div class="form-group">
                <label for="password" class="form-label">Password <span
                        class="text-danger">*</span></label>
                <input type="password" class="form-control custom-input" id="password" name="password" placeholder=""/>


            </div>
            <div class="form-group">
                <label for="confirmPassword" class="form-label">Confirm Password <span
                        class="text-danger">*</span></label>
                <input type="password" class="form-control custom-input" id="confirmPassword" name="confirmPassword" placeholder=""/>


            </div>
            <div class="invalid-feedback" id="confirmPasswordError">Passwords do not match!</div>




            <button type="submit" class="btn btn-dark mt-3">Reset Password</button>
            <h6 class="text-danger text-center"></h6>

        </form>



        <div id="alertContainer"></div>
        <div id="loader" class="text-center mt-3" >
            <div class="spinner-border text-white " role="status">

                <dotlottie-player src="https://lottie.host/99a98a1a-9a81-4e0b-a41f-e5180c93b2cb/Bkho40RfEt.json" background="transparent" speed="1" style="width: 40px; height: 40px;" loop autoplay></dotlottie-player>
            </div>
        </div>

    </div>



</div>
<script>

    $(document).ready(function() {
    $('#confirmPasswordError').hide();
    $('#loader').hide();
    $('#resetForm').submit(function(event) {
        if ($('#resetForm').valid())
        {
            event.preventDefault();
            debugger
            $('#loader').show();


            // setTimeout(function() {
            //     $('#loader').hide();
            // }, 4000);
            var password = $('#password').val();
            var confirmPassword = $('#confirmPassword').val();
            var token = "${token}";

            if (password !== confirmPassword) {
                // $(this).addClass("is-invalid");
                console.log("passwords do not match")
                $("#confirmPasswordError").show();
                $('#confirmPassword').addClass("is-invalid");
            } else {
                // $(this).removeClass("is-invalid");
                $("#confirmPasswordError").hide()

                $.ajax({
                    url: "${pageContext.request.contextPath}/resetForm",
                    type: "POST",
                    data: {
                        password:password,
                        token: token,

                    },
                    success: function(data) {
                        console.log(data);

                        console.log("Password changed!");
                        var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`
                        alert += `<strong>Success! </strong>`;
                        alert+=`Your Password has been changed successfully.`;
                        alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                        alert+=`</div>`;
                        console.log(alert)
                        $('#alertContainer').append(alert);
                        $('#loader').hide();

                    },
                    error: function() {
                        console.log("Error submitting form.");
                        $('#loader').hide();
                    }
                });
            }

        }
    });
    });

</script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>

<script src="<c:url value="/resources/js/reset-forgot.js" />"></script>
</body>

</html>