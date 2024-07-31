<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 08-07-2024
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/x-icon" href="<c:url value="/resources/image/logo.png"/>"/>
    <title>Home-Page</title>
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

    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
    <style>
        .btn-big {

            padding: 5rem 6rem;
            height: 7vh;
            width: 27vh;
        }

        @media (max-width: 576px) {
            .capsule {
                min-height: 82vh;
                padding: 10px;
            }

        }
    </style>
</head>

<body>


<jsp:include page="defaultNavbar.jsp" />



<div class="bg-light">
    <div class="container-fluid d-flex justify-content-center align-items-center h-100 mb-5 ">
        <div class="capsule bg-dark text-white d-flex flex-row">

            <div class="container text-center">
                <div>
                    <form method="post" action="${pageContext.request.contextPath}/search"  id="searchForm">
                        <div class="row mb-5">
                            <div class="col-md-4 col-12">
                                <select class="form-select form-select-lg mb-3  sourceSelect " aria-label="Large select example" id="source" name="source"
                                        style="height: 80px; width: 100%;">
                                    <option selected>From</option>
                                    <option value="1">One</option>
                                    <option value="2">Two</option>
                                    <option value="3">Three</option>
                                </select>
                            </div>
                            <div class="col-md-1 mt-3 mr-2">
                                <h1 class="fs-5 "> <i
                                        class="bi bi-arrow-left-right arrow color-white h1 justify-content-center align-items-center"></i>
                                </h1>
                            </div>
                            <div class="col-md-4 col-12">
                                <select class="form-select form-select-lg mb-3 destinationSelect" aria-label="Large select example" id="destination" name="destination"
                                        style="height: 80px; width: 100%;">
                                    <option selected>To</option>
                                    <option value="1">One</option>
                                    <option value="2">Two</option>
                                    <option value="3">Three</option>
                                </select>
                            </div>
                            <div class="col-md-3 ">

                                <input type="date" class="form-control form-control-lg" placeholder="date" name="date" id="date"
                                       style="height: 80px; width: 100%;">
                            </div>
                        </div>

                        <div class="row">
                            <div>
                                <%--                        <button type="button" class="btn btn-danger btn-lg btn-big "><span><i class="bi bi-search"></i><h3> Search</h3></span></button>--%>

                                <button type="submit" class="btn btn-danger btn-lg btn-big ">
                                    <i class="bi bi-search me-2"></i>
                                    <span class="h4">Search</span></button>

                            </div>
                        </div>

                    </form>
                </div>




                <div class="row">
                    <div>
                        <h1 class="font mt-5"> Start Your Journey With Us!</h1>
                    </div>
                </div>
            </div>



        </div>
    </div>




    <div class="container-fluid">
        <div class="row mx-5 d-flex justify-content-between">
            <div class="card mb-3 mx-4 col-12 p-0" style="max-width: 490px;">
                <div class="row g-0">
                    <div class="col-md-4">
                        <img src="<c:url value="/resources/image/homepage.jpg" />" class="img-fluid rounded-start " style="height: 24vh;"
                             alt="">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">Customer Satisfaction</h5>
                            <p class="card-text">BusWise prioritizes your comfort and satisfaction. From spacious
                                seats and reliable schedules to friendly staff dedicated to your well-being, choose
                                BusWise for a relaxing and enjoyable travel experience.</p>

                        </div>
                    </div>
                </div>
            </div>
            <div class="card mb-3 mx-4 p-0" style="max-width: 490px;">
                <div class="row g-0">
                    <div class="col-md-4 p-0">
                        <img src="<c:url value="/resources/image/staff.jpg" />" class="img-fluid rounded-start" style="height: 24vh;"
                             alt="...">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">Friendly Staff</h5>
                            <p class="card-text">BusWise journeys are made even better by our friendly staff.
                                Whether you need help with luggage or simply a smile and helpful advice, our team is
                                dedicated to ensuring your comfort and a stress-free travel experience.</p>

                        </div>
                    </div>
                </div>
            </div>
            <div class="card mb-3 mx-4 p-0" style="max-width: 490px;">
                <div class="row g-0">
                    <div class="col-md-4">
                        <img src="<c:url value="/resources/image/images.jfif" />" class="img-fluid img-card rounded-start" alt="..."
                             style="height: 24vh; ">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">On-Time Service</h5>
                            <p class="card-text">BusWise takes punctuality seriously. You can say goodbye to
                                last-minute delays and hello to reliable schedules. Arrive at your destination on
                                time, relaxed, and ready to explore with the peace of mind that BusWise provides.
                            </p>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="d-flex mb-5 mt-5 container  justify-content-center align-items-center">
        <h1 class="font-bus"> OUR BUSES </h1>
    </div>
    <div class="container align-items-center justify-content-center d-flex mt-5">

        <div id="carouselExampleAutoplaying" class="carousel slide align-items-center justify-content-center"
             data-bs-ride="carousel">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="<c:url value="/resources/image/bus11.avif" />" class="d-block img-slider img-fluid" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="<c:url value="/resources/image/bus2.jfif" />" class="d-block img-slider img-fluid" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="<c:url value="/resources/image/bus5.jfif" />" class="d-block img-slider img-fluid" alt="...">
                </div>
                <div class="carousel-item">
                    <img src="<c:url value="/resources/image/bus4.jpg" />" class="d-block img-slider img-fluid" alt="...">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleAutoplaying"
                    data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleAutoplaying"
                    data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>
    <div class="container">
        <div class="d-flex mb-5 mt-5">
            <h1>FAQs related to Bus Tickets Booking </h1>
        </div>
        <div class="accordion accordion-flush " id="accordionFlushExample">
            <div class="accordion-item ">
                <h2 class="accordion-header p-3">
                    <button class="accordion-button collapsed font-accordian" type="button"
                            data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false"
                            aria-controls="flush-collapseOne">
                        Do I need to create an account on the redBus site to book my bus ticket?
                    </button>
                </h2>
                <div id="flush-collapseOne" class="accordion-collapse collapse"
                     data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body font-accordian-body">
                        <div>
                            Book tickets easily - no login required! However, logging in unlocks additional
                            benefits.
                        </div>
                    </div>
                </div>
            </div>
            <div class="accordion-item mt-4 p-3">
                <h2 class="accordion-header">
                    <button class="accordion-button collapsed font-accordian" type="button"
                            data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false"
                            aria-controls="flush-collapseTwo">
                        How can I book bus tickets on redBus?
                    </button>
                </h2>
                <div id="flush-collapseTwo" class="accordion-collapse collapse"
                     data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body">Bus ticket Booking is effortless on BusWIse. To book the bus
                        tickets,
                        go to the main page and enter your source city and destination city in the “From” and “To”
                        fields, respectively. Enter the travel date and hit the search button. Now, you will see the
                        bus list available on the given bus route. You can use the filter option, such as duration,
                        fare, bus type, etc., to rearrange the list accordingly. This makes it easier for customers
                        to book their bus tickets online with BusWise.
                    </div>
                </div>
            </div>
            <div class="accordion-item mt-4 p-4 mb-4">
                <h2 class="accordion-header ">
                    <button class="accordion-button collapsed font-accordian" type="button"
                            data-bs-toggle="collapse" data-bs-target="#flush-collapseThree" aria-expanded="false"
                            aria-controls="flush-collapseThree">
                        What is an mTicket?
                    </button>
                </h2>
                <div id="flush-collapseThree" class="accordion-collapse collapse"
                     data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body">An mTicket is an Email that is sent to your Email on booking bus
                        tickets.</div>
                </div>
            </div>
        </div>
    </div>

</div>
<hr>
<div class="d-flex mt-5 mb-4 container">
    <div class="footer d-flex  align-items-center container  mt-5  ">
        <p class="mx-3 text-center mb-2 mt-2 text-dark  "> <span class="c"> c</span> 2024 BusWise India Pvt Ltd. All
            rights reserved
        </p>
    </div>
</div>
<jsp:include page="loader.jsp" />
<script>
    $(document).ready(function() {
        debugger
        console.log("ajax to get source")
        const sourceSelect = $('.sourceSelect');
        const destinationSelect = $('.destinationSelect');


        debugger




        $.ajax({
            url: "getSources",
            type: 'GET',

            success: function(data) {
                console.log(data)
                if (data.length > 0) {
                    sourceSelect.empty();
                    const defaultOption = $('<option>');
                    defaultOption.val("");
                    defaultOption.text("Select Source");
                    sourceSelect.append(defaultOption);
                    data.forEach(function(source) {
                        const option = $('<option>');
                        option.val(source);

                        option.text(source);

                        sourceSelect.append(option);

                    });
                } else {

                    console.log("No data found for routes");
                }
                sourceSelect.prop('selectedIndex', 0);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Error fetching routes:", textStatus, errorThrown);

            }
        });

        $.ajax({
            url: "getDestination",
            type: 'GET',

            success: function(data) {
                console.log(data)
                if (data.length > 0) {
                    destinationSelect.empty();
                    const defaultOption = $('<option>');
                    defaultOption.val("");
                    defaultOption.text("Select destination");
                    destinationSelect.append(defaultOption);
                    data.forEach(function(des) {
                        const option = $('<option>');
                        option.val(des);

                        option.text(des);

                        destinationSelect.append(option);

                    });
                } else {

                    console.log("No data found for routes");
                }
                destinationSelect.prop('selectedIndex', 0);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Error fetching routes:", textStatus, errorThrown);

            }
        });
    });
</script>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="<c:url value="/resources/js/searchForm.js" />"></script>

</body>



</html>
