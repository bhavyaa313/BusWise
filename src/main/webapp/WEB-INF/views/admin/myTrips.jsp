<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 24-06-2024
  Time: 11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="<c:url value="/resources/image/logo.png"/>"/>
    <title>My Bookings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700;1,900&display=swap" rel="stylesheet">
    <style>
        .card-header {
            background-color: red;
            color: white;
            cursor: pointer;
        }
        .card-footer {
            background-color: white;
        }
        .card-title {
            color: #000000;
        }
        .card-text {
            margin-bottom: 0.5rem;
        }
        .card-body{
            background-color: rgb(255, 255, 255);
        }
        .lato-regular {
            font-family: "Lato", sans-serif;
            font-weight: 400;
            font-style: normal;
            word-spacing: 15px;
            letter-spacing: 1px;
        }

        #pagination {
            display: inline-block;
            vertical-align: middle;
            border-radius: 4px;
            padding: 1px 2px 4px 2px;
            border: 1px solid #DDDDDD; /* Light gray border */
            /* Light gray background */
        }

        #pagination a, #pagination i {
            display: inline-block;
            vertical-align: middle;
            width: 22px;
            color: #333333; /* Black text */
            text-align: center;
            font-size: 10px;
            padding: 3px 0 2px 0;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            -o-user-select: none;
            user-select: none;
        }

        #pagination a {
            margin: 0 2px 0 2px;
            border-radius: 4px;
            border: 1px solid #DDDDDD; /* Light gray border */
            cursor: pointer;
            /* Remove box-shadow for a cleaner look */
            text-shadow: none;
            background-color: #EEEEEE; /* Lighter gray background */
        }

        #pagination i {
            margin: 0 3px 0 3px;
        }

        #pagination a.current {
            border: 1px solid #AAAAAA; /* Slightly darker gray border */
            background-color: #DDDDDD; /* Light gray background */
        }
        .loader-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(255, 255, 255, 0.8); /* White background with opacity */
            z-index: 9999; /* Make sure it's on top of everything */
            display: none; /* Initially hidden */
            display: flex;
            justify-content: center;
            align-items: center;
            backdrop-filter: blur(5px); /* Blur the background */
        }

        /*.loader {*/
        /*    border: 16px solid #f3f3f3; !* Light grey *!*/
        /*    border-top: 16px solid #3498db; !* Blue *!*/
        /*    border-radius: 50%;*/
        /*    width: 120px;*/
        /*    height: 120px;*/
        /*    animation: spin 2s linear infinite;*/
        /*}*/

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }

    </style>
</head>
<body class="bg-light">
<jsp:include page="navbar.jsp" />


<div id="loader-overlay" class="loader-overlay">

    <div id="loader" class="text-center mt-3" >
        <div class="spinner-border text-white " role="status">

            <dotlottie-player src="https://lottie.host/99a98a1a-9a81-4e0b-a41f-e5180c93b2cb/Bkho40RfEt.json" background="transparent" speed="1" style="width: 50px; height: 50px;" loop autoplay></dotlottie-player>
        </div>
    </div>
</div>

<div class="container mt-5 lato-regular">
    <div class="d-flex">
        <h2 class="text-center mb-4 me-auto">My Bookings</h2>
        <div class="ms-auto">
            <select class="form-select form-select-lg mb-3" id="bookingFilter" aria-label="Large select example">

                <option value="1">All Bookings</option>
                <option value="2">Upcoming Trips</option>
                <option value="3">Past Trips</option>

            </select>
        </div>

    </div>

    <div class="row" id="bookingsContainer">
        <!-- Bookings will be populated dynamically -->
    </div>
</div>
<div class="d-flex justify-content-center">
    <div id="pagination" class="mb-5"></div>
</div>
<input type="text" id="currentPage" name="curPage" value="1" hidden>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">

    console.log("page")

    function init(totalFinal, curPage) {

        Pagination.Init(document.getElementById('pagination'), {
            size: parseInt(totalFinal, 10), // pages size
            page: parseInt(curPage, 10), // selected page
            step: 1
            // pages before and after current
        });
    };

    var Pagination = {

        code: '',

        // --------------------
        // Utility
        // --------------------

        // converting initialize data
        Extend: function (data) {
            data = data || {};
            Pagination.size = data.size || 0;
            Pagination.page = data.page || 1;
            Pagination.step = data.step || 3;
        },

        // add pages by number (from [s] to [f])
        Add: function (s, f) {
            for (var i = s; i < f; i++) {
                Pagination.code += '<a>' + i + '</a>';
            }
        },

        // add last page with separator
        Last: function () {
            Pagination.code += '<i>...</i><a>' + Pagination.size + '</a>';
        },

        // add first page with separator
        First: function () {
            Pagination.code += '<a>1</a><i>...</i>';
        },

        // --------------------
        // Handlers
        // --------------------

        // change page
        Click: function () {
            Pagination.page = +this.innerHTML;
            Pagination.Start();
        },

        // previous page
        Prev: function () {
            Pagination.page--;
            if (Pagination.page < 1) {
                Pagination.page = 1;
            }
            Pagination.Start();
        },

        // next page
        Next: function () {
            Pagination.page++;
            if (Pagination.page > Pagination.size) {
                Pagination.page = Pagination.size;
            }
            Pagination.Start();
        },

        // --------------------
        // Script
        // --------------------

        // binding pages
        Bind: function () {
            var a = Pagination.e.getElementsByTagName('a');
            for (var i = 0; i < a.length; i++) {
                if (+a[i].innerHTML === Pagination.page)
                    a[i].className = 'current';
                a[i].addEventListener('click', Pagination.Click, false);
            }
        },

        // write pagination
        Finish: function () {
            Pagination.e.innerHTML = Pagination.code;
            Pagination.code = '';
            Pagination.Bind();
        },

        // find pagination type
        Start: function () {
            if (Pagination.size < Pagination.step * 2 + 6) {
                Pagination.Add(1, Pagination.size + 1);
            } else if (Pagination.page < Pagination.step * 2 + 1) {
                Pagination.Add(1, Pagination.step * 2 + 4);
                Pagination.Last();
            } else if (Pagination.page > Pagination.size - Pagination.step
                * 2) {
                Pagination.First();
                Pagination.Add(Pagination.size - Pagination.step * 2 - 2,
                    Pagination.size + 1);
            } else {
                Pagination.First();
                Pagination.Add(Pagination.page - Pagination.step,
                    Pagination.page + Pagination.step + 1);
                Pagination.Last();
            }
            Pagination.Finish();
        },

        // --------------------
        // Initialization
        // --------------------

        // binding buttons
        Buttons: function (e) {
            var nav = e.getElementsByTagName('a');
            nav[0].addEventListener('click', Pagination.Prev, false);
            nav[1].addEventListener('click', Pagination.Next, false);
        },

        // create skeleton
        Create: function (e) {

            var html = ['<a>&#9668;</a>', // previous button
                '<span></span>', // pagination container
                '<a>&#9658;</a>' // next button
            ];

            e.innerHTML = html.join('');
            Pagination.e = e.getElementsByTagName('span')[0];
            Pagination.Buttons(e);
        },

        // init
        Init: function (e, data) {
            Pagination.Extend(data);
            Pagination.Create(e);
            Pagination.Start();
        }
    };

</script>


<script>
    $("#pagination").on("click", function () {
        console.log("page clicked")
        debugger
        var current = $(".current").text()
        console.log(current)
        $("#currentPage").attr("value", current);
        renderData();


    })
</script>
<script>

    $(document).ready(function() {
        $('#loader-overlay').hide();

        renderData();
        $('#bookingFilter').change(function() {
            renderData();
        });
    });



    function renderData(){
        var filterOption = $('#bookingFilter').val();
        var curPage = $("#currentPage").attr("value");
        $.ajax({
            type: "POST",
            data: {
                curPage: curPage,
                filterOption: filterOption

            },
            url: "${pageContext.request.contextPath}/admin/myBookings/${userId}",
            success: function(data) {
                renderBookings(data);

                var temp = data.length > 0 ? data[0].count : 0;
                var totalFinal = Math.ceil(temp / 6);
                if (totalFinal > 0) {
                    init(totalFinal, curPage);
                    $("#pagination").show();
                } else {
                    $("#pagination").hide();
                }

            },
            error: function() {
                alert("Failed to load bookings.");
            }
        });
    }

    function renderBookings(bookings) {
        debugger
        var curPage = $("#currentPage").attr("value");
        var bookingsContainer = $('#bookingsContainer');
        bookingsContainer.empty();

        var today = new Date(); // Get today's date

        if (bookings.length === 0) {
            console.log("No bus schedules for today.");
            var nodata =`<div class="container nodataC mt-5">
    <div id="nodata" class=" d-flex align-items-center justify-content-center flex-column">
        <dotlottie-player src="https://lottie.host/c5d27b13-2786-4e34-8aca-ddf227ca5161/oNJTuK3K7k.json" background="transparent" speed="1" style="width: 300px; height: 300px;" loop autoplay></dotlottie-player>

        <div> <h3 class="text-dark">No bookings available!</h3></div>
    </div>
</div>`
            bookingsContainer.append(nodata);
        }
        else{


            $.each(bookings, function(index, booking) {
                var bookingDate = new Date(booking.date); // Convert booking date string to Date object
                var today = new Date(); // Get today's date

                // Normalize the time portion of the dates to avoid time comparison issues
                bookingDate.setHours(0, 0, 0, 0);
                today.setHours(0, 0, 0, 0);
                var bookingHtml = `
            <div class="col-md-4 mb-4">
                <div class="card" data-passenger-count="`+booking.passengers.length+`">
                    <div class="card-header bg-dark opacity-50 text-white d-flex" data-bs-toggle="collapse" href="#details`+booking.bookingId+`" role="button" aria-expanded="false" aria-controls="details${booking.bookingId}">
                        Booking ID: #`+booking.bookingId+` <span class="ms-auto"><i class="bi bi-caret-down-fill"></i></span>
                    </div>
                    <div class="card-body">
                        <p class="card-text"><strong>Route:</strong> `+booking.route+`</p>
                        <p class="card-text"><strong>Departure:</strong> `+booking.depature+`</p>
                        <p class="card-text"><strong>Arrival:</strong> `+booking.arrival+`</p>
                        <p class="card-text"><strong>Date:</strong> `+booking.date+`</p>
                        <p class="card-text"><strong>Bus Number:</strong> `+booking.busNumber+`</p>
                        <p class="card-text"><strong>Contact:</strong> `+booking.email+`,<br> `+booking.phone+`</p>
                    </div>
                    <div id="details`+booking.bookingId+`" class="collapse">
                        <div class="card-body">
                            <h5 class="card-title mt-4">Passenger Information</h5>`;
                $.each(booking.passengers, function(index, passenger) {
                    var bookingDate = new Date(booking.date); // Convert booking date string to Date object
                    var today = new Date(); // Get today's date

                    // Normalize the time portion of the dates to avoid time comparison issues
                    bookingDate.setHours(0, 0, 0, 0);
                    today.setHours(0, 0, 0, 0);

                    console.log("Booking Date:", bookingDate);
                    console.log("Today's Date:", today);

                    var arrivalTimeString = booking.arrival;


                    var arrivalTimeParts = arrivalTimeString.split(':');
                    var arrivalHours = parseInt(arrivalTimeParts[0], 10);
                    var arrivalMinutes = parseInt(arrivalTimeParts[1], 10);


                    var today = new Date();
                    var arrivalTime = new Date(today.getFullYear(), today.getMonth(), today.getDate(), arrivalHours, arrivalMinutes);
                    var currentTime = new Date();

                    bookingHtml += `
    <div class="card-text mb-3">
        <strong>Name:</strong> `+passenger.name+` <br>
        <strong>Age:</strong> `+passenger.age+` <br>
        <strong>Gender:</strong>`+passenger.gender+` <br>
        <strong>Seat Number:</strong> `+passenger.seatNumber+`
        <div class="mt-2">`;

                    // Only show the Cancel Seat button if the booking date is greater than or equal to today's date
                    if (bookingDate > today) {
                        console.log("Showing Cancel Seat button for:", passenger.name);
                        bookingHtml += `
            <button class="btn btn-outline-danger btn-sm" onclick="cancelSeat('`+passenger.bookingDetailId+`', '`+passenger.bookingId+`')">Cancel Seat</button>
                    <div id="alertContainerCanelSeat" ></div>`;
                    }   if (bookingDate = today && arrivalTime> currentTime) {
                        console.log("Showing Cancel Seat button for:", passenger.name);
                        bookingHtml += `
            <button class="btn btn-outline-danger btn-sm" onclick="cancelSeat('`+passenger.bookingDetailId+`', '`+passenger.bookingId+`')">Cancel Seat</button>
                    <div id="alertContainerCanelSeat" ></div>`;
                    }

                    if (bookingDate = today && arrivalTime< currentTime) {

                        bookingHtml += `<div class="card-footer text-center bg-white"> This Trip is Completed!
    </div>`;
                    }





                    bookingHtml += `
        </div>
    </div>`;
                });

                bookingHtml += `
        </div>
    </div>`;

// Only add the footer with buttons if the booking date is greater than or equal to today's date
                if (bookingDate >= today) {
                    console.log("Showing footer buttons for booking ID:", booking.bookingId);
                    bookingHtml += `
        <div class="card-footer text-center bg-white">
            <button class="btn btn-outline-primary" onclick="downloadTicket('`+booking.bookingId+`')">Download Ticket</button>
            <button class="btn btn-outline-danger" onclick="deleteBooking('`+booking.bookingId+`')">Cancel Booking</button>

     <div id="alertContainerDownload" ></div>
        </div>`;

                }

                else if (bookingDate < today ) {
                    console.log("Marking trip as completed for booking ID:", booking.bookingId);
                    bookingHtml += `
    <div class="card-footer text-center bg-white"> This Trip is Completed!
    </div>`;
                }
                else {

                        console.log("Marking trip as completed for booking ID:", booking.bookingId);
                        bookingHtml += `
    <div class="card-footer text-center bg-white"> This Trip is Completed!
    </div>`;

                }

                bookingsContainer.append(bookingHtml);

                // var temp = booking.count;
                // console.log(temp + 4333);
                // var totalFinal = Math.ceil(temp / 6);
                // init(totalFinal, curPage);

            });
        }
    }



</script>
<div id="alertContainerCancelBooking" class="d-flex justi-content-end align-items-end" ></div>
<script>
    function cancelSeat(bookingDetailId, bookingId) {
        debugger
        var bookingCard = $('#details' + bookingId).closest('.card');
        var passengerCount = parseInt(bookingCard.data('passenger-count'), 10);
        $('#loader-overlay').show();
        if (passengerCount === 1) {
            deleteBooking(bookingId);
            $('#loader-overlay').hide();
        }
        else {
            $('#alertContainerCancelSeat').empty();

            $.ajax({
                url:  "${pageContext.request.contextPath}/admin/cancelSeat/" + bookingDetailId +"/" + bookingId +"/"+  ${userId},
                type: "POST", //
                success: function (response) {
                    var message = response.message;
                    $('#message').text(response.message);
                    var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`
                    alert += `<strong>Success! </strong>`;
                    alert+=`Your seat has been cancelled successfully.`;
                    alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                    alert+=`</div>`;
                    console.log(alert)


                    console.log("Item deleted successfully:", response);
                    bookingCard.data('passenger-count', passengerCount - 1);
                    $('#loader-overlay').hide();

                    renderData()
                    $('#alertContainerCancelSeat').append(alert);



                },
                error: function (jqXHR, textStatus, errorThrown) {
                    var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                    alert+=`<strong>Error! </strong>`;
                    alert+=`An error occurred while cancellation of seat.`;
                    alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                    alert+=`</div>`

                    $('#loader-overlay').hide();
                    $('#alertContainerCancelSeat').append(alert);


                }
            });
        }

    }

    function deleteBooking(bookingId) {

        $('#alertContainerCancelBooking').empty();
        $('#loader-overlay').show();
        $.ajax({
            url: "${pageContext.request.contextPath}/admin/cancelBooking/" + bookingId +"/" +${userId},
            type: "POST",
            success: function(response) {
                var message = response.message;
                $('#message').text(response.message);

                renderData()
                console.log("Booking deleted successfully:", response);

                var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`
                alert += `<strong>Success! </strong>`;
                alert+=`Your booking has been cancelled successfully.`;
                alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert+=`</div>`;
                console.log(alert)

                $('#loader-overlay').hide();
                $('#alertContainerCancelBooking').append(alert);

            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Error deleting booking:", textStatus, errorThrown);
                var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                alert+=`<strong>Error! </strong>`;
                alert+=`An error occurred while cancellation of booking.`;
                alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert+=`</div>`

                $('#loader-overlay').hide();
                $('#alertContainerCancelBooking').append(alert);
            }
        });
    }


</script>



<script>


    function downloadTicket(bookingId) {
        $('#alertContainerDownload').empty();
        $.ajax({
            url: '${pageContext.request.contextPath}/ticketDownlaod/'+bookingId ,
            type: 'GET',

            success: function(response) {

                var fileUrl = response;
                window.location.href= fileUrl;
                console.log("succes")
                var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`
                alert += `<strong>Success! </strong>`;
                alert+=`Your ticket has been downloaded successfully.`;
                alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert+=`</div>`;
                console.log(alert)
                $('#alertContainerDownload').append(alert);

            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Error downlaoding :", textStatus, errorThrown);
                var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                alert+=`<strong>Error! </strong>`;
                alert+=`An error occurred while downlaoding.`;
                alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert+=`</div>`
                $('#alertContainerDownload').append(alert);
            }
        });
    }

</script>

<script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz4fnFO9gybBogGz4MhA0DTVU7jLrP3vv4MOO5Q4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-HoA7Kt98Md5qCZmb3l4b4U0fQW0nPEd6sMBoX5YlTAnCl5xzQ3G01TIHuHtPpTHc" crossorigin="anonymous"></script>
</body>
</html>