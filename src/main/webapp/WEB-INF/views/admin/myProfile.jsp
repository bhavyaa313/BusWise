<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 14-06-2024
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="<c:url value="/resources/image/logo.png"/>"/>
    <title>My Profile</title>


<%--    <script--%>
<%--            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"--%>
<%--            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"--%>
<%--            crossorigin="anonymous"></script>--%>

<%--    <script--%>
<%--            src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>--%>

    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
            crossorigin="anonymous"></script>


    <style>


        .error-message1 {
            display: block;
            margin-top: 10px;
            font-size: 0.8em;
            color: red;
        }

        .hide1 {
            display: none;
        }

        .custom-input {
            border: none;
            border-bottom: 1px solid #ccc;
            border-radius: 0;
            box-shadow: none;
            background-color: #FBFBFB;
        }

        .custom-input:focus {
            border-bottom: 1px solid #000;
            box-shadow: none;
            outline: none;
            background: #FBFBFB;
        }


    </style>
</head>


<body class="bg-dark">
<div class="container-fluid bg-white">
    <jsp:include page="navbar.jsp"/>
</div>

<input type="text"  id="userId"  value="${userId}" hidden>
<div class="container mt-5 bg-light">
    <form id="myProfileForm" method="post">
        <h2 class="mb-4 mt-5 p-4 mx-5">My Profile</h2>

        <input type="text" value="${userProfiles[0].id}" name="userProfileId" hidden>
        <div class="row mb-3 mx-5">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="fname" class="form-label">First Name <span
                            class="text-danger">*</span></label>
                    <input type="text" class="form-control custom-input enable1" name="firstName" id="fname"
                           placeholder="" value="${userProfiles[0].firstName}" disabled>
                </div>
            </div>
            <div class="col-md-6 ">
                <div class="form-group">
                    <label for="lname" class="form-label">Last Name <span
                            class="text-danger">*</span></label>
                    <input type="text" class="form-control custom-input enable1" name="lastName" id="lname"
                           placeholder="" value="${userProfiles[0].lastName}" disabled>
                </div>
            </div>

        </div>


        <div class="row mb-3 mx-5">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="email" class="form-label">Email <span
                            class="text-danger">*</span></label>
                    <input type="email" class="form-control custom-input " name="email" id="email" placeholder=""
                           value="${userProfiles[0].userId.email}" disabled>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="Phone" class="form-label">Phone <span
                            class="text-danger">*</span></label>
                    <input type="text" class="form-control custom-input enable1" name="phone" id="phone" placeholder=""
                           value="${userProfiles[0].userId.phone}" disabled>
                </div>
            </div>

        </div>


        <div class="row mx-5 mb-4">
            <div class="col-md-4">
                <div class="form-group">
                    <label for="age" class="form-label">Age</label>
                    <input type="text" class="form-control custom-input enable1" name="age" id="age" placeholder=""
                           value="${userProfiles[0].age}" disabled>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label for="city" class="form-label">City</label>
                    <input type="text" class="form-control custom-input enable1" name="city" id="city" placeholder=""
                           value="${userProfiles[0].city}" disabled>
                </div>
            </div>
            <div class="col-md-4">
                <div class="form-group">
                    <label for="state" class="form-label">State</label>
                    <input type="text" class="form-control custom-input enable1" placeholder="" name="state" id="state"
                           value="${userProfiles[0].state}" disabled>
                </div>
            </div>


        </div>


        <div class="mt-5 d-flex flex-row-reverse mb-3 mx-5 ">
            <button type="button"
                    class="btn btn-dark text-white btn-lg mx-3 random1 xx1 mb-2  "
                    onclick="show5()">Edit
            </button>
            <button type="button"
                    class="btn btn-danger btn-lg mx-2 hide1 see1 come1 mb-2"
                    onclick="show6()" hidden>Cancel
            </button>
            <button type="submit"
                    class="btn btn-success btn-lg mx-2 hide1 see1 come1 mb-2" onclick="show7()"
                    hidden>Save
            </button>


        </div>
    </form>

</div>
<br class="bg-white text-white">
<div class=" mt-5 mb-4 container-fluid">
    <br class="bg-white text-white">
    <div class="footer d-flex  align-items-center container  mt-5 bg-dark  ">
        <br class="bg-white text-white">
        <p class="mx-3 text-center mb-2 mt-2 text-white  "><span class="c bg-dark text-white"> c</span> 2024 BusWise India Pvt Ltd. All
            rights reserved
        </p>
    </div>
</div>



<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
<script src="<c:url value="/resources/js/myProfile.js"/>"></script>
<script src="<c:url value="/resources/js/myProfileAdmin.js"/>"></script>
</body>

</html>


