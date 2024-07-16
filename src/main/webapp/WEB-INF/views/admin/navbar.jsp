<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 29-05-2024
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>dashboard</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
            href="https://fonts.googleapis.com/css2?family=Edu+TAS+Beginner:wght@400..700&family=Montserrat:ital,wght@0,100..900;1,100..900&family=Roboto+Mono:ital,wght@0,100..700;1,100..700&display=swap"
            rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Edu+TAS+Beginner:wght@400..700&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
            href="https://fonts.googleapis.com/css2?family=Edu+TAS+Beginner:wght@400..700&family=Roboto+Mono:ital,wght@0,100..700;1,100..700&display=swap"
            rel="stylesheet">
</head>

<body class="bg-white">

<nav class="navbar navbar-expand-lg navbar-toggler bg-white">

    <div class="container-fluid flex-column">


<%--        <button class="btn btn-outline-info d-lg-none d-inline" type="button" data-bs-toggle="offcanvas"--%>
<%--                data-bs-target="#offcanvasExample" aria-controls="offcanvasExample">--%>
<%--            <i class="bi bi-list h1"></i>--%>
<%--        </button>--%>

        <div class="d-flex justify-content-between ">
            <div class="d-flex justify-content-start me-auto mx-3">
                <button class="btn  d-lg-none  me-auto d-flex" type="button" data-bs-toggle="offcanvas"
                        data-bs-target="#offcanvasExample" aria-controls="offcanvasExample">
                    <i class="bi bi-list h1"></i>
                </button>

            </div>
            <div class="d-flex me-auto d-sm-block d-lg-none">
                <img src="<c:url value="/resources/image/logo.png" />" class="img-fluid" alt="" style="width: 200px;">
            </div>

        </div>


        <hr class="bg-dark">

        <div class="collapse navbar-collapse w-100 d-none d-lg-inline align-items-center d-flex justify-content-center mb-3"
             id="navbarSupportedContent">
            <div class="d-flex me-auto">
                <img src="<c:url value="/resources/image/logo.png" />" class="img-fluid" alt="" style="width: 200px;">
            </div>
            <ul class="navbar-nav nav-underline">

                <li class="nav-item mx-2">
                    <a href="${pageContext.request.contextPath}/admin/dashboard/${userId}" class="nav-link ${active1}">Home</a>
                </li>
                <li class="nav-item mx-2">
                    <a href="${pageContext.request.contextPath}/admin/myProfile/${userId}" class="nav-link ${active2}">My Profile</a>
                </li>
                <li class="nav-item mx-2">
                    <a href="${pageContext.request.contextPath}/admin/buses/${userId}" class="nav-link ${active3}">Buses</a>
                </li>

                <li class="nav-item mx-2">
                    <a href="${pageContext.request.contextPath}/admin/routes/${userId}" class="nav-link ${active4}">Routes</a>
                </li>

                <li class="nav-item mx-2">
                    <a href="${pageContext.request.contextPath}/admin/schedule/${userId}" class="nav-link ${active5}">Schedule</a>
                </li>
                <li class="nav-item mx-2">
                    <a href="${pageContext.request.contextPath}/admin/myBookingsView/${userId}" class="nav-link ${active6}">My Bookings</a>
                </li>

                <li class="nav-item dropdown mx-2">
                    <a class="nav-link dropdown-toggle ${active8}" href="#" role="button" data-bs-toggle="dropdown"
                       aria-expanded="false">
                        Reports
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/salesReports/${userId}">Sales Reports</a></li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/occupancyReports/${userId}">Occupancy Reports</a></li>


                    </ul>
                </li>
                <li class="nav-item mx-2">
                    <a href="${pageContext.request.contextPath}/admin/bus-location/${userId}" class="nav-link ${active10}">Bus Location</a>
                </li>

            </ul>
            <div class="d-flex ms-auto">
                <a type="button" href="${pageContext.request.contextPath}/logout" class="btn btn-outline-dark mx-2 lo">Logout</a>
            </div>
        </div>


        <div class=" offcanvas offcanvas-start d-sm-block d-lg-none bg-white" tabindex="-1" id="offcanvasExample"
             aria-labelledby="offcanvasExampleLabel">
            <div class="offcanvas-body">
                <div class="d-flex me-auto">
                    <img src="/images/logo.png" class="img-fluid" alt="" style="width: 160px;">
                </div>
<%--                <span class="wlcm lo-btn">Welcome <span style="font-weight: 500;">Admin admin2</span></span>--%>
                <ul class="navbar-nav nav-underline">

                    <li class="nav-item mx-2">
                        <a href="${pageContext.request.contextPath}/admin/dashboard/${userId}" class="nav-link ${active1}">Home</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a href="${pageContext.request.contextPath}/admin/myProfile/${userId}" class="nav-link ${active2}">My Profile</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a href="${pageContext.request.contextPath}/admin/buses/${userId}" class="nav-link ${active3}">Buses</a>
                    </li>

                    <li class="nav-item mx-2">
                        <a href="${pageContext.request.contextPath}/admin/routes/${userId}" class="nav-link ${active4}">Routes</a>
                    </li>

                    <li class="nav-item mx-2">
                        <a href="${pageContext.request.contextPath}/admin/schedule/${userId}" class="nav-link ${active5}">Schedule</a>
                    </li>
                    <li class="nav-item mx-2">
                        <a href="${pageContext.request.contextPath}/admin/myBookingsView/${userId}" class="nav-link ${active6}">My Bookings</a>
                    </li>

                    <li class="nav-item dropdown mx-2">
                        <a class="nav-link dropdown-toggle ${active8}" href="#" role="button" data-bs-toggle="dropdown"
                           aria-expanded="false">
                            Reports
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/salesReports/${userId}">Sales Reports</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/occupancyReports/${userId}">Occupancy Reports</a></li>


                        </ul>
                    </li>
                    <li class="nav-item mx-2">
                        <a href="${pageContext.request.contextPath}/admin/bus-location/${userId}" class="nav-link ${active10}">Bus Location</a>
                    </li>

                </ul>
            </div>
        </div>



    </div>

</nav>
<script src="https://cdn.jsdelivr.net/npm/bootstra
p@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>