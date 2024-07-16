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

    <title>Register</title>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>

    <script src="<c:url value="/resources/js/validate.js" />"></script>
    <script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f1f1f1;
        }

        .error-message
        {
            color: red;
        }




        .custom-input {
            border: none;
            border-bottom: 1px solid #ccc;
            border-radius: 0;
            box-shadow: none;
        }

        .custom-input:focus {
            border-bottom: 1px solid #000;
            box-shadow: none;
            outline: none;
        }




        body {
            background-color: #ffffff;
            background-image: url("<c:url value="/resources/image/bg1.jpeg"/>");
            /* Replace with your image path */
            background-size: cover;
            /* Ensures the image covers the whole background */
            background-position: center;
            /* Centers the background image */
            background-repeat: no-repeat;
            /* Prevents the image from repeating */

            height: 100vh !important;
        }

        .card {
            border-radius: 10px;
        }

        .form-floating label {
            padding: 0 0.75rem;
            background-color: white;
            color: #007bff;
            /* Match the color of the welcome text */
            font-weight: bold;
        }
    </style>
</head>

<body>


<div class="container-fluid d-flex align-items-center justify-content-center h-100">

    <div class=" p-5 bg-white">
        <h2 class="mb-5">Welcome ! Register Here...</h2>

        <form:form modelAttribute="RegisterDto"  action="${pageContext.request.contextPath}/registerUser" method="post" id="registerForm"  cssClass="Form">

            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="firstname" class="form-label">First Name  <span
                                class="text-danger">*</span></label>
                        <form:input type="text" class="form-control custom-input" id="firstname" placeholder="" path="firstName"/>
                                <form:errors path="firstName" cssClass="error-message" class="text-danger" />
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="lastname" class="form-label">Last Name  <span
                                class="text-danger">*</span></label>
                        <form:input type="text" class="form-control custom-input" id="lastname" placeholder="" path="lastName"/>
                        <form:errors path="firstName" cssClass="error-message" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="email" class="form-label">Email <span
                                class="text-danger">*</span></label>
                        <form:input type="email" class="form-control custom-input" id="email" placeholder="" path="email"/>
                        <form:errors path="email" cssClass="error-message" />
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="Phone" class="form-label" >Phone <span
                                class="text-danger">*</span></label>
                        <form:input type="text" class="form-control custom-input" id="phone" placeholder="" path="phone"/>
                        <form:errors path="phone" cssClass="error-message" />
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group mb-4">
                        <label for="password" class="form-label">Password <span
                                class="text-danger">*</span></label>
                        <form:input type="password" class="form-control custom-input" id="password" placeholder="" path="password"/>
                        <form:errors path="password" cssClass="error-message" />
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group mb-4">
                        <label for="confirmPassword" class="form-label"> Confirm Password <span
                                class="text-danger">*</span></label>
                        <form:input type="password" class="form-control custom-input" id="confirmPassword" placeholder="" path="confirmPassword"/>
                        <form:errors path="confirmPassword" cssClass="error-message" />
                        <div class="invalid-feedback" id="confirmPasswordError"></div>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn btn-dark btn-block" id="saveButton">Sign up</button>
            <div id="alertContainer mt-3"></div>
        </form:form>
        <p class="mt-4">Already have an account? <a href="#">Login Here</a></p>
        <div id="loader" class="text-center mt-3" >
            <div class="spinner-border text-white " role="status">

                <dotlottie-player src="https://lottie.host/99a98a1a-9a81-4e0b-a41f-e5180c93b2cb/Bkho40RfEt.json" background="transparent" speed="1" style="width: 40px; height: 40px;" loop autoplay></dotlottie-player>
            </div>
        </div>
    </div>



</div>
<script>
    $("#confirmPassword").keyup(function() {
        var password = $("#password").val();
        var confirmPassword = $(this).val();

        // Check if passwords match
        if (password !== confirmPassword) {
            $(this).addClass("is-invalid");
            $("#confirmPasswordError").text("Passwords do not match!");
        } else {
            $(this).removeClass("is-invalid");
            $("#confirmPasswordError").text("");
        }
    });

</script>

<script>
    function disableSaveButton() {

        var invalidElements = document.querySelectorAll(".is-invalid");
        var hasInvalid = invalidElements.length > 0;
        var saveButton = document.getElementById("saveButton");
        if (saveButton) {
            saveButton.disabled = hasInvalid;
        }
    }

    // Call the function initially to check for existing invalid elements
    disableSaveButton();

    // Attach the function to be called whenever the class "is-invalid" might be added or removed
    document.addEventListener("DOMSubtreeModified", disableSaveButton);

</script>
<script>
    $(document).ready(function() {
        $('#loader').hide();
        $('#registerForm').submit(function(event) {
            debugger
            event.preventDefault();
            $('#alertContainer').empty()

            var email = $("#email").val();
            $.ajax({
                url:'${pageContext.request.contextPath}/checkEMail',
                type:'POST',
                data: {
                    email:email
                },
                success: function (data){


                    if (data == false) {
                        var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                        alert += `<strong>Error! </strong>`;
                        alert += `This email already exists!.`;
                        alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                        alert += `</div>`;
                        $('#alertContainer').append(alert);
                    }

                    else {

                        // Show the loader
                        $('#loader').show();

                        // Set a timeout to hide the loader after 3 seconds (3000 milliseconds)
                        setTimeout(function() {
                            $('#loader').hide();
                            $('#registerForm')[0].submit();
                        }, 3000); // Adjust the time as needed
                    }
                }
            })




        });
    });

</script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>

</html>