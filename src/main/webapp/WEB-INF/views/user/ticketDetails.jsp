
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ticket Details</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="<c:url value="/resources/css/ticketDetails.css" />">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/resources/css/ticketDetails.css" />">


    <style>
        .error-message1{
            display: block;
            margin-top: 10px;
            font-size: 0.6em;
            color: red;
            word-spacing: normal;
        }

        .seat {
            display: inline-block;
            margin: 5px;
        }
        .seat input[type="checkbox"] {
            display: none;
        }

        .lato-regular {
            font-family: "Lato", sans-serif;
            font-weight: 400;
            font-style: normal;
            word-spacing: 20px;
            letter-spacing: 2px;
        }

        .lato-regular2 {
            font-family: "Lato", sans-serif;
            font-weight: 300;
            font-style: normal;
            word-spacing: 20px;
            letter-spacing: 2px;
        }
        .form-group {
            margin-bottom: 1rem; /* Add some spacing between input fields */
        }
        .form-control-line {
            border: none; /* Remove default border */
            border-bottom: 1px solid #ced4da; /* Add bottom border */
            border-radius: 0; /* Remove rounded corners */
            background-color: transparent; /* Transparent background */
            box-shadow: none; /* Remove box shadow */
            height: 30px; /* Adjust height as needed */
            padding: 0; /* Remove padding */
        }

        .hidden-content {
            display: none; /* Initially hidden */
            margin-top: 20px; /* Adjust margin as needed */
        }

        .form-select-line2 {
            appearance: none; /* Remove default appearance */
            -webkit-appearance: none; /* For older versions of Chrome */
            -moz-appearance: none; /* For older versions of Firefox */
            background-color: transparent; /* Transparent background */
            border: none; /* Remove default border */
            border-bottom: 1px solid #ced4da; /* Add bottom border */
            padding: 0; /* Remove default padding */
            height: 30px; /* Adjust height as needed */
            width: 100%; /* Full width */
        }
        .form-control-line:focus {
            outline: none;
        }

        .modal1.right .modal-dialog1 {
            position: fixed;
            margin: auto;
            width: 50%;
            height: 100%;
            right: 0;
            top: 0;
        }

        .modal1.right .modal-content1 {
            height: 100%;
            overflow-y: auto;
        }

        .modal1.right .modal-body1 {
            padding: 2rem;
        }

        .form-group {
            margin-bottom: 1rem;
            /* Add some spacing between input fields */
        }

        .form-control-line {
            border: none;
            /* Remove default border */
            border-bottom: 1px solid #ced4da;
            /* Add bottom border */
            border-radius: 0;
            /* Remove rounded corners */
            background-color: transparent;
            /* Transparent background */
            box-shadow: none;
            /* Remove box shadow */
            height: 25px;
            /* Adjust height as needed */
            padding: 0;
            /* Remove padding */
        }

        .form-control-line:focus {
            outline: none;
            /* Remove outline on focus */
            border-color: #80bdff;
            /* Change border color on focus if needed */
        }



        /* Custom styles for radio buttons */
        .form-check-input[type="radio"] {
            appearance: none;
            -webkit-appearance: none;
            -moz-appearance: none;
            width: 18px;
            height: 18px;
            border: 2px solid #ced4da;
            /* Default Bootstrap border color */
            border-radius: 50%;
            outline: none;
            cursor: pointer;
            vertical-align: middle;
            position: relative;
        }

        /* Checked state */
        .form-check-input[type="radio"]:checked {
            background-color: rgb(179, 11, 11);
            /* Half-filled color */
        }

        /* Inner circle */
        .form-check-input[type="radio"]:checked::after {
            content: "";
            display: block;
            width: 50%;
            height: 50%;
            background-color: white;
            /* Inner half-filled color */
            border-radius: 50%;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        /* Label styling */
        .form-check-label {
            margin-left: 8px;
            /* Space between radio button and label */
            cursor: pointer;
        }

        /* Hide the default checkbox */
        .form-check-input[type="checkbox"] {
            appearance: none;
            -webkit-appearance: none;
            -moz-appearance: none;
            width: 18px;
            /* Set the size of the checkbox */
            height: 18px;
            border: 2px solid #ced4da;
            /* Default Bootstrap border color */
            border-radius: 4px;
            outline: none;
            cursor: pointer;
            vertical-align: middle;
            position: relative;
            background-color: white;
            /* Background color of the checkbox */
        }

        /* Checked state - use a custom white checkmark image */
        .form-check-input[type="checkbox"]:checked {
            background-color: rgb(179, 11, 11);
        ;
            /* Change to your desired checked color */
            background-image: url('data:image/svg+xml;utf8,<svg fill="white" height="16" viewBox="0 0 24 24" width="16" xmlns="http://www.w3.org/2000/svg"><path d="M9,16.17L4.83,12l-1.42,1.41L9,19 21,7l-1.41,-1.41z"/></svg>');
            /* White checkmark SVG data */
            background-size: 100% 100%;
            background-repeat: no-repeat;
        }

        /* Hide the actual checkbox */
        .form-check-input[type="checkbox"]::after {
            content: "";
            /* No content for the pseudo-element */
            display: block;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 70%;
            /* Adjust size of the checkbox */
            height: 70%;
            opacity: 0;
            /* Initially hide the checkbox */
        }

        /* Optional: Hover effect */
        .form-check-input[type="checkbox"]:hover {
            background-color: lightgray;
            /* Adjust hover color if needed */
        }
        <!-- Add some CSS for booked seats -->

         .seat.booked label {
             background-color: red;
             color: white;
         }

        .custom-modal .modal-dialog {
            display: flex;
            align-items: center;
            min-height: calc(100% - 1rem);
        }

        .custom-modal .modal-content {
            margin: auto;
            /* Optional: Add custom styles for the modal content */
        }

    </style>

    <script type="text/javascript">
        function autoRefresh() {
            // Reload the current page
            window.location.reload();
        }


        setTimeout(autoRefresh, 250000);
    </script>
</head>

<body class="">

<c:choose>
    <c:when test="${sessionScope.roleId == 2}">
        <jsp:include page="userNavbar.jsp" />
    </c:when>
    <c:otherwise>
        <jsp:include page="defaultNavbar.jsp" />
    </c:otherwise>
</c:choose>
<div class="container mt-4 ">

    <div class="d-flex h5 mb-5  lato-regular flex-row">
        <div class="mt-4">
            ${source} To ${destination}
        </div>

<%--        <div class="d-flex align-items-center m-auto  justify-content-center noData" id="error"><iframe src="https://lottie.host/embed/7e16bb56-6eff-46f0-af44-4b5d9c739553/ZunxByaaub.json"></iframe>--%>
<%--            oops! No data found!--%>

<%--        </div>--%>

        <div class="container nodataC mt-5">
            <div id="nodata" class=" d-flex align-items-center justify-content-center flex-column">
                <dotlottie-player src="https://lottie.host/c5d27b13-2786-4e34-8aca-ddf227ca5161/oNJTuK3K7k.json" background="transparent" speed="1" style="width: 300px; height: 300px;" loop autoplay></dotlottie-player>

                <div> <h3 class="text-dark">No data found!</h3></div>
            </div>
        </div>
        <form class="row align-items-end mx-4 ms-auto mt-3 " id="searchForm"  action="${pageContext.request.contextPath}/search" method="post"  hidden>
            <!-- Source Input -->
            <div class="col">
                <div class="form-group">
                    <select class="form-select   sourceSelect " aria-label="Large select example" id="source" name="source"
                            style="width: 20vh"
                    >
                        <option selected>From</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                        <option value="3">Three</option>
                    </select>
                </div>
            </div>

            <!-- Destination Input -->
            <div class="col">
                <div class="form-group">
                    <select class="form-select  destinationSelect " aria-label="Large select example" id="destination" name="destination"
                            style="width: 20vh" >
                        <option selected>To</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                        <option value="3">Three</option>
                    </select>
                </div>
            </div>

            <!-- Date Input -->
            <div class="col">
                <div class="form-group">

                    <input type="date" class="form-control-line lato-regular2" id="date" name="date"  >
                </div>
            </div>

            <!-- Search Button -->
            <div class="col-auto mt-2 mx-2" >
                <button type="submit" class="btn btn-outline-danger  ">Search</button>
            </div>
        </form>
        <div class="ms-auto mx-2 ">
            <button type="button" class="btn btn-outline-danger btn-lg mt-4" onclick="showContent()" id="searchButton">Modify Search</button>
        </div>
    </div>


    <div class="accordion card bg-light lato-regular" id="accordionExample">
        <c:forEach items="${ticketDetails}" var="t" varStatus="loop">
            <div class="accordion-item">
                <h2 class="accordion-header bg-light">
                    <button class="accordion-button" type="button" data-bs-toggle="collapse"
                            data-bs-target="#collapse-${loop.index}" aria-expanded="true" aria-controls="collapse-${loop.index}">
                        <div class="row w-100">
                            <div class="col-12 col-md-3">
                                <div class="d-none" id=""> ${t.scheduleId}  </div>
                                <div class="d-flex h6">
                                        ${t.date} <span class="mx-4"> ${t.time} </span>
                                </div>
                                <hr>
                                <div class="d-flex h4 mb-0">
                                        ${t.busDetails}  <span class="mx-4">AC Luxury </span>
                                </div>
                                <br>
                                <c:if test="${t.subroutes != '( via )'}">
                                    <div class="d-flex h5 text-muted opacity-75">
                                            ${t.subroutes}
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-12 col-md-5">
                                <p class="mx-5 ms-5">Origin</p>
                                <div class="d-flex flex-column justify-content-center align-items-center mt-4 position-relative">
                                    <div class="d-flex align-items-center h5">
                                            ${t.source}
                                        <span class="arrow1 mx-5 position-relative">
                <p class="font-duration text-center mt-3">${t.duration}</p>
            </span>
                                            ${t.destination}
                                    </div>
                                    <div class="position-absolute" style="top: -40px; right: 50px;"> <!-- Adjust top value as needed -->
                                        <p class="text-center">Destination</p>
                                    </div>
                                </div>
                            </div>

                            <div class="col-12 col-md-2">
                                <div class="d-flex h3 fw-5 text-success align-items-center mt-4 ms-5">
                                    <i class="bi bi-currency-rupee"></i>
                                        ${t.fare}
                                </div>
                            </div>
                            <div class="col-12 col-md-2">
                                <div type="button" class="btn btn-outline-danger btn-lg mt-4">Select Seats</div>
                                <p class="font-duration text-center mt-2">  ${t.noOfSeats -fn:length(t.bookedSeats)}  seats left</p>
                            </div>
                        </div>
                    </button>



                </h2>

                <div id="collapse-${loop.index}" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
                    <div class="accordion-body d-flex  justify-content-center">
                        <div class="d-flex flex-column justify-content-center align-items-center">
                            <label class="h4">Select Seat</label>
                            <div id="scheduleID" class="d-none d-sm-none">${ticketDetails[loop.index].scheduleId}</div>



                            <div class="bus seat2-2 border-0 p-0">
                                <c:set var="seatsPerRow" value="4"/>
                                <c:set var="totalSeats" value="${ticketDetails[loop.index].noOfSeats}"/>
                                <c:set var="bookedSeats" value="${ticketDetails[loop.index].bookedSeats}"/>
                                <c:set var="rows" value="${totalSeats % seatsPerRow == 0 ? totalSeats / seatsPerRow : Math.floor(totalSeats / seatsPerRow) + 1}"/>

                                <c:forEach var="row" begin="0" end="${rows - 1}" step="1">
                                    <div class="seat-row-${row}">
                                        <ol class="seats">
                                            <c:forEach var="col" begin="0" end="${seatsPerRow - 1}" step="1">
                                                <c:set var="seatNumber" value="${row * seatsPerRow + col + 1}"/>
                                                <c:set var="isBooked" value="${fn:contains(bookedSeats, seatNumber)}"/>
                                                <c:set var="disableSeat" value="${row == rows - 1 && col >= totalSeats % seatsPerRow}"/>

                                                <li class="seat
                        ${isBooked ? 'booked' : ''}
                        ${disableSeat ? ' disabled' : ''}">
                                                    <input role="input-passenger-seat" name="passengers[1][seat]"
                                                           id="seat-checkbox-1-${seatNumber}-${ticketDetails[loop.index].scheduleId}" value="${seatNumber}" required="" type="checkbox"
                                                           onclick="updateSelectedSeats()" ${isBooked ? 'disabled' : ''} ${disableSeat ? 'disabled' : ''}>
                                                    <label for="seat-checkbox-1-${seatNumber}-${ticketDetails[loop.index].scheduleId}">${seatNumber}</label>
                                                </li>
                                            </c:forEach>
                                        </ol>
                                    </div>
                                </c:forEach>

                            </div>










                            <div class="text-left mt-2 ">
                                <button class="btn btn-primary btn-xs mb-2">Available</button>
                                <button class="btn btn-success btn-xs mb-2">Choosen</button>
                                <button class="btn btn-danger btn-xs mb-2">Booked</button>
                            </div>
                        </div>

                        <form action="/" method="POST" class="d-flex flex-column mt-5">
                            <div class="mx-5 h4">
                                Selected Seats:
                                <span id="selected-seats-display">None</span>
                                <input  name="selected_seats" id="selected-seats-input" class="d-none" >
                                <span id="number-of-seats-display"></span>
                                <input name="number_of_seats" id="number-of-seats-input"  value="0" hidden>
                            </div>
                            <div class="mx-5 h4">
                                Total Fare:
                                <span id="total-fare-display">0</span>
                                <input type="hidden" name="total_fare" id="total-fare-input">
                            </div>
                            <div class="mx-5 mt-5">
                                <button type="button" class="btn btn-primary opacity-75 btn-lg" data-bs-toggle="modal" data-bs-target=".bd-example-modal-lg" id="modalClick">
                                    <span class="h5 mb-3">Continue Booking <i class="bi bi-arrow-right h3 mx-2"></i></span>
                                </button>
                            </div>
                        </form>

                    </div>
                </div>
            </div>

        </c:forEach>
    </div>

</div>




<div class="modal fade bd-example-modal-lg modal1 right mt-3 h-100" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel" aria-hidden="true" id="modal-main">
    <div class="modal-dialog modal-dialog1 modal-lg h-100">
        <div class="modal-content modal-content1" id="modal-nav">
            <div class="modal-header d-flex ">

                <div class="modal-title h5  " id="exampleModalToggleLabel">
                    <button type="button" class="btn-close mx-2" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                    Complete Your Booking
                </div>
                <span class="ms-auto  me-0">${source} To ${destination} | ${ticketDetails[0].date} | <span id="depatureTime">${ticketDetails[0].time}</span>   </span>

            </div>
            <div class="modal-body modal-body1 mt-4">
                <div class="container-fluid">
                    <form action="" id="bookingDetails">
                        <div>Seat <span id="seatNumber"> </span></div>









                        <div id="passengerDetailsContainer">

                        </div>




                        <div class="h5">Contact Details</div>

                        <div class="row">
                            <div class="col-md">
                                <div class="form-group">
                                    <label for="email" class="d-flex mt-3">Email</label>
                                    <input type="text" class="form-control-line" id="email" name="email" style="width: 50vh;">
                                </div>
                            </div>

                        </div>
                        <div class="row w-100">

                            <div class="col-md">
                                <div class="form-group">
                                    <label for="phone" class="d-flex mt-3">Phone</label>
                                    <input type="tel" class="form-control-line" id="phone" name="phone" style="width: 50vh;">
                                </div>
                            </div>
                        </div>

                        <div class="form-check">
                            <input class="form-check-input " type="checkbox" value="" id="wpCheck">
                            <label class="form-check-label " for="flexCheckDefault">
                                <svg xmlns="http://www.w3.org/2000/svg" x="0px" y="0px" width="25" height="25"
                                     viewBox="0 0 48 48">
                                    <path fill="#fff"
                                          d="M4.868,43.303l2.694-9.835C5.9,30.59,5.026,27.324,5.027,23.979C5.032,13.514,13.548,5,24.014,5c5.079,0.002,9.845,1.979,13.43,5.566c3.584,3.588,5.558,8.356,5.556,13.428c-0.004,10.465-8.522,18.98-18.986,18.98c-0.001,0,0,0,0,0h-0.008c-3.177-0.001-6.3-0.798-9.073-2.311L4.868,43.303z">
                                    </path>
                                    <path fill="#fff"
                                          d="M4.868,43.803c-0.132,0-0.26-0.052-0.355-0.148c-0.125-0.127-0.174-0.312-0.127-0.483l2.639-9.636c-1.636-2.906-2.499-6.206-2.497-9.556C4.532,13.238,13.273,4.5,24.014,4.5c5.21,0.002,10.105,2.031,13.784,5.713c3.679,3.683,5.704,8.577,5.702,13.781c-0.004,10.741-8.746,19.48-19.486,19.48c-3.189-0.001-6.344-0.788-9.144-2.277l-9.875,2.589C4.953,43.798,4.911,43.803,4.868,43.803z">
                                    </path>
                                    <path fill="#cfd8dc"
                                          d="M24.014,5c5.079,0.002,9.845,1.979,13.43,5.566c3.584,3.588,5.558,8.356,5.556,13.428c-0.004,10.465-8.522,18.98-18.986,18.98h-0.008c-3.177-0.001-6.3-0.798-9.073-2.311L4.868,43.303l2.694-9.835C5.9,30.59,5.026,27.324,5.027,23.979C5.032,13.514,13.548,5,24.014,5 M24.014,42.974C24.014,42.974,24.014,42.974,24.014,42.974C24.014,42.974,24.014,42.974,24.014,42.974 M24.014,42.974C24.014,42.974,24.014,42.974,24.014,42.974C24.014,42.974,24.014,42.974,24.014,42.974 M24.014,4C24.014,4,24.014,4,24.014,4C12.998,4,4.032,12.962,4.027,23.979c-0.001,3.367,0.849,6.685,2.461,9.622l-2.585,9.439c-0.094,0.345,0.002,0.713,0.254,0.967c0.19,0.192,0.447,0.297,0.711,0.297c0.085,0,0.17-0.011,0.254-0.033l9.687-2.54c2.828,1.468,5.998,2.243,9.197,2.244c11.024,0,19.99-8.963,19.995-19.98c0.002-5.339-2.075-10.359-5.848-14.135C34.378,6.083,29.357,4.002,24.014,4L24.014,4z">
                                    </path>
                                    <path fill="#40c351"
                                          d="M35.176,12.832c-2.98-2.982-6.941-4.625-11.157-4.626c-8.704,0-15.783,7.076-15.787,15.774c-0.001,2.981,0.833,5.883,2.413,8.396l0.376,0.597l-1.595,5.821l5.973-1.566l0.577,0.342c2.422,1.438,5.2,2.198,8.032,2.199h0.006c8.698,0,15.777-7.077,15.78-15.776C39.795,19.778,38.156,15.814,35.176,12.832z">
                                    </path>
                                    <path fill="#fff" fill-rule="evenodd"
                                          d="M19.268,16.045c-0.355-0.79-0.729-0.806-1.068-0.82c-0.277-0.012-0.593-0.011-0.909-0.011c-0.316,0-0.83,0.119-1.265,0.594c-0.435,0.475-1.661,1.622-1.661,3.956c0,2.334,1.7,4.59,1.937,4.906c0.237,0.316,3.282,5.259,8.104,7.161c4.007,1.58,4.823,1.266,5.693,1.187c0.87-0.079,2.807-1.147,3.202-2.255c0.395-1.108,0.395-2.057,0.277-2.255c-0.119-0.198-0.435-0.316-0.909-0.554s-2.807-1.385-3.242-1.543c-0.435-0.158-0.751-0.237-1.068,0.238c-0.316,0.474-1.225,1.543-1.502,1.859c-0.277,0.317-0.554,0.357-1.028,0.119c-0.474-0.238-2.002-0.738-3.815-2.354c-1.41-1.257-2.362-2.81-2.639-3.285c-0.277-0.474-0.03-0.731,0.208-0.968c0.213-0.213,0.474-0.554,0.712-0.831c0.237-0.277,0.316-0.475,0.474-0.791c0.158-0.317,0.079-0.594-0.04-0.831C20.612,19.329,19.69,16.983,19.268,16.045z"
                                          clip-rule="evenodd"></path>
                                </svg> Send Booking Details on WhatsApp
                            </label>
                        </div>
                        <div class="mt-3 text-muted">Tickets will be send to you via email! Thankyou</div>


                        <div class="d-flex mt-5">
                            <div class="h5" >Total Amount: INR <span id="totalAmount"></span> </div>
                            <input id="scheduleID2" type="text" hidden>


<%--                            <button class="btn text-white  ms-auto"--%>
<%--                                    style="background-color:  rgb(179, 11, 11);">Confirm</button>--%>
                        </div>
                        <div class="d-flex mt-5">
                            <div class="h5" >This amount will be deducted from your wallet. Thank you!</span> </div>



<%--                            <button class="btn text-white  ms-auto" type="submit"--%>
<%--                                    style="background-color:  rgb(179, 11, 11);">Confirm</button>--%>
                        </div>
                        <div class="d-flex mt-5">



                            <button class="btn text-white  ms-auto" type="submit"
                                    style="background-color:  rgb(179, 11, 11);">Confirm</button>

<%--                            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">--%>
<%--                                Launch demo modal--%>
<%--                            </button>--%>



                            <div class=" modal fade  " id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" >
                                <div class="modal-dialog" style="display: flex; align-items: center; max-height: calc(30% - 1rem);">
                                    <div class="modal-content" style="margin: auto;">
                                        <div class="modal-header">
                                            <h1 class="modal-title fs-5" id="exampleModalLabel">Payment Confirmation</h1>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            To continue with the payment, please click on confirm!
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                            <button type="button" class="btn btn-success" id="modalConfirmButton">Confirm</button>
                                        </div>
                                    </div>
                                </div>
                            </div>



                        </div>
                        <div id="alertContainer"></div>


                    </form>

                </div>


            </div>






        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>



<script>





    $(document).ready(function() {
        // Function to check and toggle button disable state
        function toggleContinueButton() {
            var selectedSeats = $('#selected-seats-display').text().trim();
            if (selectedSeats === 'None') {
                $('#modalClick').prop('disabled', true);
            } else {
                $('#modalClick').prop('disabled', false);
            }
        }
        toggleContinueButton();
        $('.seat input[type="checkbox"]').on('change', function() {
            updateSelectedSeats(); // Example function that updates selected seats
            toggleContinueButton();
        });

    })


    // const seatFare = '`+ticketDetails.fare+`';
    var seatFare = ${ticketDetails[0].fare};


    function updateSelectedSeats() {
        const checkboxes = document.querySelectorAll('.seat input[type="checkbox"]');
        const selectedSeats = [];
        checkboxes.forEach(checkbox => {
            if (checkbox.checked) {
                selectedSeats.push(checkbox.value);
            }
        });

        const totalFare = selectedSeats.length * seatFare;
        document.getElementById('selected-seats-display').textContent = selectedSeats.join(', ') || 'None';
        document.getElementById('total-fare-display').textContent = totalFare;




        document.getElementById('selected-seats-input').value = selectedSeats.join(', ');
        document.getElementById('total-fare-input').value = totalFare;

        const numberOfSeats = selectedSeats.length;



        // Update the hidden input with the number of selected seats
        document.getElementById('number-of-seats-input').value = numberOfSeats.toString();

    }

    $('#modalClick').click(function () {
        debugger
        var isLoggedIn = <c:out value="${not empty sessionScope.email}"/>;
        if (isLoggedIn) {

            console.log("clickeddddddd")
            var x = $('#total-fare-input').val();
            $('#totalAmount').text(x);
            var w = $('#selected-seats-input').val();
            var selecte_seats = w.split(",");
            console.log(selecte_seats);
            $('#seatNumber').text(selecte_seats);
            var y = $('#selected-seats-input')
            var numSeats = $('#number-of-seats-input').val();
            var z = $('#scheduleID').text();
            $('#scheduleID2').val(z);


            $('#passengerDetailsContainer').empty();


            for (var i = 1; i <= numSeats; i++) {
                var passengerRow = `
              <div>Passenger `+i+` </div>
                <div class="row row2">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label for="name`+i+` " class="d-flex mt-3">Name</label>
                            <input type="text" class="form-control-line name" id="name`+i+` " name="passenger`+i+` _name">
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label for="age`+i+` " class="d-flex mt-3">Age</label>
                            <input type="number" class="form-control-line age" id="age`+i+` " name="passenger`+i+` _age">
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <label class="d-flex mt-3 mb-3">Gender</label>
                            <input class="form-check-input" type="radio" name="gender`+i+`"
                                   id="male`+i+` " value="Male" checked >
                            <label class="form-check-label" for="male`+i+` ">
                                Male
                            </label>
                            <input class="form-check-input" type="radio" name="gender`+i+`"
                                   id="female`+i+` " value="Female">
                            <label class="form-check-label" for="female`+i+` ">
                                Female
                            </label>
                        </div>
                    </div>
                </div>`;


                $('#passengerDetailsContainer').append(passengerRow);
            }

        } else {
            window.location.href = '${pageContext.request.contextPath}/login';
        }



    });


    function updateForm() {
        // Additional form validation can be added here if necessary
        return true;
    }


</script>
<script>


    $(document).ready(function() {
        debugger
        console.log("ajax to get source")
        const sourceSelect = $('.sourceSelect');
        const destinationSelect = $('.destinationSelect');


        debugger


        $(document).ready(function() {
            // Assuming you have a select element with class 'sourceSelect'
            var sourceSelect = $('.sourceSelect');

            // AJAX call to fetch data
            $.ajax({
                url: "${pageContext.request.contextPath}/getSources",
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
        });


        $.ajax({
            url: "${pageContext.request.contextPath}/getDestination",
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
<script>
    function showContent() {


        $('#searchForm').removeAttr("hidden");
        $('#searchButton').attr("hidden", "hidden");
    }



    $(document).ready(function() {


        if ($('.accordion-item').length > 0) {


console.log("jigjr")
            $('.nodataC').addClass("d-none");
            $('.nodataC').addClass("d-sm-none")


            console.log('Accordion items found.');

        } else {

            console.log('No accordion items found.');

        }
    });

</script>

<script>



    $(document).ready(function () {
        var bookingId; // Variable to store the booking ID
        var reservationTimer; // Variable to store the reservation timer
        const RESERVATION_TIMEOUT = 0.5 * 60 * 1000; // 10 minutes in milliseconds

        $("#bookingDetails").submit(function (event) {

            if ($("#bookingDetails").valid()) {
                console.log("vhgerdrugvffiegveiguf")

                event.preventDefault();
                debugger
                // Prevent default form submission

                $('#exampleModal').modal('show');

                var check = $('#wpCheck').is(':checked') ? "yes" : "no";

                var email = $("#email").val();
                var phone = $("#phone").val();
                var wpCheck = check;
                var scheduleId = $("#scheduleID2").val();
                var amount = $("#totalAmount").text();
                var name = [];
                var age = [];
                var gender = [];
                var seat1 = $("#seatNumber").text();
                var seat = seat1.split(",");
                var depatureTime = $("#depatureTime").text();

                $(".row2").each(function () {
                    var nameInput = $(this).find("input[id^='name']");
                    var ageInput = $(this).find("input[id^='age']");
                    var genderInput = $(this).find("input[name^='gender']:checked");

                    if (nameInput.length > 0 && ageInput.length > 0 && genderInput.length) {
                        var name1 = nameInput.val();
                        var age1 = parseInt(ageInput.val());
                        var gender1 = genderInput.val();

                        if (name1 && age1 && gender1) {
                            name.push(name1);
                            age.push(age1);
                            gender.push(gender1);
                        }
                    }
                });

                $.ajax({
                    url: "${pageContext.request.contextPath}/user/bookTicket/${source}/${destination}/${userId}",
                    type: "POST",
                    traditional: true,
                    data: {
                        email: email,
                        phone: phone,
                        wpCheck: wpCheck,
                        scheduleId: scheduleId,
                        amount: amount,
                        name: name,
                        age: age,
                        gender: gender,
                        seat: seat,
                        depatureTime: depatureTime
                    },
                    success: function (response) {
                        console.log("Form submitted successfully:", response);
                        bookingId = response; // Store the booking ID



                        reservationTimer = setTimeout(function () {
                            debugger
                            console.log("in")
                            $.ajax({
                                url: "${pageContext.request.contextPath}/resetReservation/" + bookingId , // Endpoint to reset reservation timestamp
                                type: 'POST',
                                success: function () {
                                    debugger
                                    console.log("Reservation timestamp reset due to inactivity.");
                                    $('#exampleModal').modal('hide');
                                    $('#modal-main').modal('hide');

                                },
                                error: function (error) {
                                    console.error("Error resetting reservation timestamp:", error);
                                }
                            });
                        }, RESERVATION_TIMEOUT);
                    },
                    error: function (error) {
                        console.error("Error submitting form:", error);
                    }
                });
            }
        });

        $("#modalConfirmButton").click(function () {
            if (bookingId) {

                clearTimeout(reservationTimer);


                $.ajax({
                    url: "${pageContext.request.contextPath}/confirmBooking/" + bookingId  +"/" +${userId},
                    type: 'POST',
                    success: function () {

                        $.ajax({
                            url: '${pageContext.request.contextPath}/getBalance/${userId}',
                            type: 'POST',
                            success: function (data) {
                                var amount = $("#totalAmount").text();

                                if (data > amount) {

                                    $.ajax({
                                        url: "${pageContext.request.contextPath}/sendEmail/" + $("#email").val() + "/" + bookingId + "/" + ${userId},
                                        type: "GET",
                                        success: function () {
                                            console.log("Email sent successfully");
                                        },
                                        error: function (error) {
                                            console.error("Error sending mail:", error);
                                        }
                                    });

                                    window.location.href = "${pageContext.request.contextPath}/user/success/${source}/${destination}/" + bookingId + "/" + ${userId};
                                } else {
                                    var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                                    alert += `<strong>Error! </strong>`;
                                    alert += `Your Wallet has insufficient balance!`;
                                    alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                                    alert += `</div>`;
                                    $('#alertContainer').append(alert);
                                }
                            },
                            error: function (error) {
                                console.error("Error getting balance:", error);
                            }
                        });
                    },
                    error: function (error) {
                        console.error("Error confirming booking:", error);
                    }
                });
            }
        });
    });


</script>


<script
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
        crossorigin="anonymous"></script>
<script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
<script src="<c:url value="/resources/js/searchForm.js" />"></script>
<script src="<c:url value="/resources/js/bookingDetails.js" />"></script>
<script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>
</body>

</html>