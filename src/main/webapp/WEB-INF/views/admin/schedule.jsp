<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 12-06-2024
  Time: 10:07
  To change this template use File | Settings | File Templates.
--%>
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
    <title>Schedule</title>





    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>

    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>

    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
            crossorigin="anonymous"></script>






    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>




    <style>


        .error-message1{
            display: block;
            margin-top: 10px;
            font-size: 0.8em;
            color: red;
        }

        #busAvailabilityError{
            color: red;
        }

        #busAvailabilityError1{
            color: red;
        }




        @media only screen and (max-width: 760px) {

            table,
            tbody,
            thead,
            tbody,
            th,
            td,
            tr {
                display: block;
                width: 100%;
                clear: both;
                text-align: start;
            }

            thead tr {
                position: absolute;
                left: -9999px;
            }

            tr {
                border-top: 5px solid #ccc;
            }

            td,
            th {
                border: none !important;
            }

            td:before {
                float: left;
                width: 95%;
                margin-left: -100%;
            }

            td {
                padding-left: 5%;
                -moz-box-sizing: border-box;
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
            }

            .table td {
                padding: 5px 20px 10px;
            }
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

    </style>
</head>


<body class="bg-dark" >


<div class="container-fluid bg-white">
    <jsp:include page="navbar.jsp" />
</div>

<div id="alertContainerDelete" class="d-flex ms-auto mt-auto"></div>

<div class="container bg-light mt-5  ">

    <div class="d-flex  ">
        <h3 class="text-center mx-auto mt-4 mb-4 ">Schedule</h3>
    </div>

    <div class="d-flex mx-3 mt-3">
        <button type="button" class="btn btn-dark mx-2"
                data-bs-toggle="modal" data-bs-target="#add">Add Schedule
        </button>
        <div class="col-lg-4 d-md-flex d-block ms-auto">

            <div class="input-group border me-2 rounded-0 mb-2 form-control">
                <div class="input-group-append">
                    <button class="btn text-secondary border bord
                    er-0 bg-white"
                            type="button">
                        <i class="bi bi-search"></i>
                    </button>
                </div>

                <input type="text" id="searchSource"  name="region"
                       class="form-control border-0  shadow-none" placeholder="Search Source or destination">
            </div>

            <div class="btn-group">
                <button type="button" class="btn btn-dark  dropdown-toggle mx-3 pl-2 pr-2" data-bs-toggle="dropdown" aria-expanded="false" id="sortDropdown">
                    <i class="bi bi-funnel"></i>   Sort
                </button>
                <ul class="dropdown-menu">
                    <li><button class="dropdown-item dropdown-item11"  >asc</button></li>
                    <li><button class="dropdown-item dropdown-item11" >desc</button></li>

                </ul>
            </div>

        </div>
        <!-- <div class="col-lg-4 d-md-flex d-block ms-auto">

        <div class="input-group border me-2 rounded-0 mb-2 form-control">
            <div class="input-group-append">
                <button class="btn text-secondary border border-0 bg-white"
                    type="button">
                    <i class="bi bi-search"></i>
                </button>
            </div>
            <input type="text" id="myInput1" onkeyup="search1()"
                class="form-control border-0  shadow-none" placeholder="Search by priority ">
        </div>

    </div> -->

    </div>
    <div id="container-data-0"> </div>
    <table class="table mt-3" id="myTable">
        <thead>
        <tr>

            <th scope="col" class="col-md-3">Schedule Id</th>
            <th scope="col" class="col-md-2">Date</th>
            <th scope="col" class="col-md-1">Time</th>
            <th scope="col" class="col-md-1">Source</th>
            <th scope="col" class="col-md-1">Destination</th>
            <th scope="col" class="col-md-2">Duration</th>
            <th scope="col" class="col-md-2">BusId</th>
            <th scope="col" class="col-md-2">Fare</th>


            <th scope="col"></th>


        </tr>
        </thead>
        <tbody id="tableData">


        </tbody>
    </table>

</div>
</div>

<div class="modal fade" id="add" tabindex="-1"
     aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog  modal-dialog-centered w-100">

        <div class="modal-content rounded-4 p-3 ">
            <div class="container-fluid  ">

                <div class="d-flex  mt-1  ">
                    <h3 class="mx-1 mt-2 fw-5">Create Schedule</h3>
                    <button type="button" class="btn-close ms-auto" data-bs-dismiss="modal" id="close" aria-label="Close"></button>
                </div>
            </div>
            <form
                    id="myFormAdd" method="post" >

                <div class="modal-body">


                    <div class="row mb-3">
                        <div class="col">
                            <label for="" >Date <span
                                    class="text-danger">*</span></label>
                            <input type="date" class="form-control" placeholder="date" aria-label="datetime" name="date1" id="date">
                        </div>
                        <div class="col">
                            <label for="" >Time <span
                                    class="text-danger">*</span></label>
                            <input type="time" class="form-control" placeholder="Time" aria-label="time" name="time1" id="time">
                        </div>
                    </div>

                    <div class="row mb-3">
                        <input id="routeId" type="text" hidden>
                        <div class="col">
                            <label for="" >Select Route <span
                                    class="text-danger">*</span></label>
                            <select class="form-select" aria-label="Default select example" id="routeSelect" name="route">

                            </select>
                        </div>

                    </div>

                    <div class="row mb-3">
                        <input id="busId" type="text" hidden>

                        <div class="col">
                            <label for="" >Select Bus <span
                                    class="text-danger">*</span></label>
                            <select class="form-select" aria-label="Default select example" id="busSelect" name="bus">

                            </select>
                            <span id="busAvailabilityError"></span>
                        </div>

                    </div>

                    <div class="row">
                        <div class="col">
                            <label for="" >Duration <span
                                    class="text-danger">*</span></label>
                            <input type="text" class="form-control" placeholder="Duration" aria-label="Duration" id="duration" name="duration">
                        </div>
                        <div class="col">
                            <label for="" >Fare <span
                                    class="text-danger">*</span></label>
                            <input type="number" class="form-control" placeholder="Fare" aria-label="Fare" id="fare" name="fare">
                        </div>
                    </div>






                </div>


                    <div
                            class="modal-footer border-top-0 d-flex p-0 justify-content-center">

                        <button type="submit" class="btn btn-dark  text-white btn-lg rounded-end" id="saveButton" style="width: 22vh;"
                        >Save</button>
                        <button type="reset" class="btn btn-secondary text-white btn-lg rounded-end " style="width: 22vh;"
                                data-bs-dismiss="modal" onclick="clearFormData()">Cancel</button>
                    </div>

            </form>
        </div>


    </div>
</div>
<div class="modal fade" id="edit" tabindex="-1"
     aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog  modal-dialog-centered w-100">

        <div class="modal-content rounded-4 p-3 ">
            <div class="container-fluid  ">

                <div class="d-flex  mt-1  ">
                    <h3 class="mx-1 mt-2 fw-5">Edit Schedule</h3>
                    <button type="button" class="btn-close ms-auto" data-bs-dismiss="modal"  aria-label="Close"></button>
                </div>
            </div>
            <form
                    id="myFormEdit" method="post" >

                <div class="modal-body">


                    <div class="row mb-3">
                        <div class="col">
                            <input type="text" id="sid" hidden>
                            <label for="" >Date <span
                                    class="text-danger">*</span></label>
                            <input type="date" class="form-control" placeholder="date" aria-label="datetime" name="date1" id="date2" disabled>
                        </div>
                        <div class="col">
                            <label for="" >Time <span
                                    class="text-danger">*</span></label>
                            <input type="time" class="form-control" placeholder="Time" aria-label="time" name="time1" id="time2">
                        </div>
                    </div>

                    <div class="row mb-3">

                        <div class="col">
                            <label for="" >Select Route <span
                                    class="text-danger">*</span></label>
                            <select class="form-select" aria-label="Default select example" id="routeSelect2" name="route" disabled>

                            </select>
                        </div>

                    </div>

                    <div class="row mb-3">


                        <div class="col">
                            <label for="" >Select Bus <span
                                    class="text-danger">*</span></label>
                            <select class="form-select" aria-label="Default select example" id="busSelect2" name="bus">

                            </select>
                            <span id="busAvailabilityError1"></span>
                        </div>

                    </div>

                    <div class="row">
                        <div class="col">
                            <label for="" >Duration <span
                                    class="text-danger">*</span></label>
                            <input type="text" class="form-control" placeholder="Duration" aria-label="Duration" id="duration2" name="duration" disabled>
                        </div>
                        <div class="col">
                            <label for="" >Fare <span
                                    class="text-danger">*</span></label>
                            <input type="number" class="form-control" placeholder="Fare" aria-label="Fare" id="fare2" name="fare" disabled>
                        </div>
                    </div>






                </div>


                <div
                        class="modal-footer border-top-0 d-flex p-0 justify-content-center">

                    <button type="submit" class="btn btn-dark  text-white btn-lg rounded-end" id="saveButton2" style="width: 22vh;"
                    >Save</button>
                    <button type="reset" class="btn btn-secondary text-white btn-lg rounded-end " style="width: 22vh;"
                            data-bs-dismiss="modal" onclick="clearFormData()">Cancel</button>
                </div>
                <div id="loader"  class="justify-content-center text-center h5 mt-3">Loading...</div>

            </form>
        </div>


    </div>
</div>
<div class="d-flex justify-content-center">
    <div id="pagination" class="mb-5"></div>
</div>
<input type="text" id="currentPage" value="1" hidden>

</div>







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
        Extend: function(data) {
            data = data || {};
            Pagination.size = data.size || 0;
            Pagination.page = data.page || 1;
            Pagination.step = data.step || 3;
        },

        // add pages by number (from [s] to [f])
        Add: function(s, f) {
            for (var i = s; i < f; i++) {
                Pagination.code += '<a>' + i + '</a>';
            }
        },

        // add last page with separator
        Last: function() {
            Pagination.code += '<i>...</i><a>' + Pagination.size + '</a>';
        },

        // add first page with separator
        First: function() {
            Pagination.code += '<a>1</a><i>...</i>';
        },

        // --------------------
        // Handlers
        // --------------------

        // change page
        Click: function() {
            Pagination.page = +this.innerHTML;
            Pagination.Start();
        },

        // previous page
        Prev: function() {
            Pagination.page--;
            if (Pagination.page < 1) {
                Pagination.page = 1;
            }
            Pagination.Start();
        },

        // next page
        Next: function() {
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
        Bind: function() {
            var a = Pagination.e.getElementsByTagName('a');
            for (var i = 0; i < a.length; i++) {
                if (+a[i].innerHTML === Pagination.page)
                    a[i].className = 'current';
                a[i].addEventListener('click', Pagination.Click, false);
            }
        },

        // write pagination
        Finish: function() {
            Pagination.e.innerHTML = Pagination.code;
            Pagination.code = '';
            Pagination.Bind();
        },

        // find pagination type
        Start: function() {
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
        Buttons: function(e) {
            var nav = e.getElementsByTagName('a');
            nav[0].addEventListener('click', Pagination.Prev, false);
            nav[1].addEventListener('click', Pagination.Next, false);
        },

        // create skeleton
        Create: function(e) {

            var html = ['<a>&#9668;</a>', // previous button
                '<span></span>', // pagination container
                '<a>&#9658;</a>' // next button
            ];

            e.innerHTML = html.join('');
            Pagination.e = e.getElementsByTagName('span')[0];
            Pagination.Buttons(e);
        },

        // init
        Init: function(e, data) {
            Pagination.Extend(data);
            Pagination.Create(e);
            Pagination.Start();
        }
    };

</script>




<script>


    $("#pagination").on("click", function() {
        console.log("page clicked")
        var current = $(".current").text();
        $("#currentPage").attr("value" , current);
        console.log(current)
        ajaxCallSearch();


    })

        $('#add').on('hidden.bs.modal', function() {
        $('#myFormAdd')[0].reset();
            $('.error-message1').remove();
    });
        $('#edit').on('hidden.bs.modal', function() {
        $('#myFormAdd')[0].reset();
            $('.error-message1').remove();
    });

$(document).ready(function() {
$("#loader").hide()
    console.log("hello")
debugger
console.log("ajax to get routes")
const routeSelect = $('#routeSelect');
const routeSelect2 = $('#routeSelect2');
const busSelect = $('#busSelect');
const busSelect2 = $('#busSelect2');

const durationInput = $('#duration');
        const fareInput = $('#fare');

debugger




        $.ajax({
            url: "getRoutes/"+${userId},
            type: 'POST', //

            success: function(data) {
                console.log(data)
                debugger
                if (data.length > 0) {
                    console.log("rouetshs")
                    routeSelect.empty(); // Clear existing options
                    routeSelect2.empty(); // Clear existing options
                    const defaultOption = $('<option>');
                    defaultOption.val(""); // Set an empty value
                    defaultOption.text("Select Routes"); // Display text "Select Routes"
                    routeSelect.append(defaultOption);
                    data.forEach(function(route) {
                        const option = $('<option>');
                        option.val(route.routeId);

                        option.text(route.routeDetails);
                        if (route.duration && route.fare) {
                            option.data('duration', route.duration);

                            option.data('fare', route.fare);
                        }
                        const option2 = option.clone();

                        routeSelect.append(option);
                        routeSelect2.append(option2);

                    });
                } else {

                    console.log("No data found for routes");
                }
                routeSelect.prop('selectedIndex', 0);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Error fetching routes:", textStatus, errorThrown);

            }
        });


        $.ajax({
            url: "getBuses/" +${userId},
            type: 'POST', //

            success: function(data) {
                console.log(data)
                if (data.length > 0) {
                    busSelect.empty();
                    busSelect2.empty();
                    const defaultOption = $('<option>');
                    defaultOption.val("");
                    defaultOption.text("Select Bus");
                    busSelect.append(defaultOption);
                    data.forEach(function(bus) {
                        const option = $('<option>');
                        option.val(bus.busId);
                        option.text(bus.busDetails);

                        const option2 = option.clone();
                        busSelect.append(option);
                        busSelect2.append(option2)
                    });

                } else {
                    // Handle empty data scenario (optional)
                    console.log("No data found for buses");
                }
                busSelect.prop('selectedIndex', 0);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Error fetching routes:", textStatus, errorThrown);

            }
        });




        routeSelect.change(function() {
            const selectedOption = $(this).find('option:selected');

            // Check if 'duration' and 'fare' data attributes exist and handle potential null values
            if (selectedOption.data('duration') !== undefined && selectedOption.data('fare') !== undefined) {
                const duration = selectedOption.data('duration') || ""; // Set empty string if duration is null
                const fare = selectedOption.data('fare') || ""; // Set empty string if fare is null

                // Set input values
                durationInput.val(duration);
                fareInput.val(fare);
            } else {
                console.log("Selected option has missing data attributes.");
                // Handle the case where at least one attribute is missing (optional)
                // You could clear the input fields or display an error message
                durationInput.val(""); // Clear duration input (optional)
                fareInput.val(""); // Clear fare input (optional)
            }
        });




    });


        $(document).ready(function() {

            $("#myFormAdd").submit(function(event) {
                debugger
                event.preventDefault(); // Prevent default form submission
                if ($("#myFormAdd").valid()) {
                    // Get form data using Spring form tag IDs
                    var date = $("#date").val();
                    var time = $("#time").val();
                    var route = $("#routeSelect").val();
                    var bus = $("#busSelect").val();
                    var fare = $("#fare").val();
                    var duration = $("#duration").val();
                    var hasError ;
                    const errorSpan = $("#busAvailabilityError"); // Optional for displaying error messages
                    $.ajax({
                        url: "checkBusAvailability/"+${userId}, // Replace with your endpoint URL
                        type: "POST",
                        data: {
                            bus: bus,
                            Date: date,
                            Time: time
                        },
                        success: function(response) {
                            console.log(response)
                            if (response) {

                                console.log("Bus available! Submitting form...");

                                errorSpan.text("");
                                $.ajax({
                                    url: "addSchedule/"+${userId},
                                    type: "POST",
                                    data: {
                                        date: date,
                                        time: time,
                                        route: route,
                                        bus: bus,
                                        fare: fare,
                                        duration: duration

                                    },

                                    success: function(response) {
                                        console.log("Form submitted successfully:", response);

                                        var form = document.getElementById("myFormAdd");
                                        form.reset();
                                        $(".btn-close").click();
                                        ajaxCallSearch("desc")



                                    },
                                    error: function(jqXHR, textStatus, errorThrown) {
                                        console.error("Error submitting form:", textStatus, errorThrown);

                                    }
                                });

                            } else {
                               console.log("error in bus")
                                errorSpan.text("Error: Bus is already booked at that date and time.");
                                hasError = true;
                                console.log(hasError)
                            }
                        },
                        error: function(jqXHR, textStatus, errorThrown) {
                            console.error("Error checking bus availability:", textStatus, errorThrown);

                        }
                    });





                }



            });
        });
debugger


   $('.dropdown-item').on('click', function() {
        const value11 = $(this).text();
        console.log(value11);
        ajaxCallSearch(value11)
    });


      $(document).ready(function() {
            console.log("in")
            ajaxCallSearch("desc");
        });


        $("#searchSource").on('keyup', function ()
        {
            ajaxCallSearch("desc");
        });

        function  ajaxCallSearch(sortDirection){


debugger



            var region = $("#searchSource").val().trim();


            console.log(sortDirection + "sort")



            if(region=="")
            {
                region= "empty";
            }


            var curPage = $("#currentPage").attr("value");


            if (!sortDirection || sortDirection === "") { // if no value is selected
                sortDirection = "desc"; // set a default value (e.g., "asc" or "desc")
            }


            $.ajax({

                url:"ajaxforSchedule/"+ ${userId},
                type: "post",
                data: {
                   region: region,
                    curPage: curPage,
                    sort: sortDirection
                },
                success: function (data) {
                    console.log(data)
                    console.log("success")



                    if (data.length === 0) {
                        if (curPage > 1) {
                            $("#currentPage").attr("value", 1);
                            ajaxCallSearch();
                            return; // Ensure the function exits here
                        } else {
                            $("#tableData").empty();
                            $("#pagination").hide();
                            $("#myTable").hide();
                            $('#container-data-0').empty();

                            var nodata = `
        <div class="container nodataC mt-5">
            <div id="nodata" class="d-flex align-items-center justify-content-center flex-column">
                <dotlottie-player src="https://lottie.host/c5d27b13-2786-4e34-8aca-ddf227ca5161/oNJTuK3K7k.json" background="transparent" speed="1" style="width: 300px; height: 300px;" loop autoplay></dotlottie-player>
                <div><h3 class="text-dark">No routes available!</h3></div>
            </div>
        </div>`;
                            $('#container-data-0').append(nodata);
                            return; // Ensure the function exits here
                        }
                    } else {
                        $("#tableData").empty();
                        $("#pagination").show();
                        $("#myTable").show();
                        $('#container-data-0').empty();
                    }




                    for (let i = 0; i < data.length; i++) {
                        var trips = data[i];
                        var scheduleDateTime = new Date(trips.date + ' ' + trips.time);
                        var currentDateTime = new Date();
                        var timeDifference = scheduleDateTime - currentDateTime;
                        var hoursDifference = timeDifference / (1000 * 60 * 60);

                        var disableButtons = hoursDifference < 24;




                        var row = `<tr class="w-100">

							<td>` + trips.scheduleId + `
                                <br>


                               ` + (trips.subroutes !== '( via )' ? trips.subroutes : '') + `

                            </td>
                            <td>` + trips.date + `</td>
                            <td>` + trips.time + `</td>
							<td>` + trips.source + `</td>
							<td>` + trips.destination + `</td>
							<td>` + trips.duration + `</td>
							<td>` + trips.busDetails + `</td>
							<td>` + trips.fare + `</td>


                           <td class="justify-content-end d-flex">
            <button type="button" class="btn btn-dark" style="width: 5vh"
    onclick="checkAndUpdateSchedule('` + trips.scheduleId + `', '` + trips.date + `', '` + trips.time + `', '` + trips.duration + `', '` + trips.fare + `', '` + trips.busId + `', '` + trips.routeId + `')"
    ` + (disableButtons ? 'disabled' : '') + `>
    <i class="bi bi-pen-fill text-white"></i>
</button>

                <button type="button" class="btn btn-secondary mx-3" data-bs-toggle="modal" data-bs-target="#delete-modal" style="width: 5vh"

                        ` + (disableButtons ? 'disabled' : '') + `>
                    <i class="bi bi-archive-fill text-white"></i>
                </button>

                   <div class="modal fade" id="delete-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h1 class="modal-title fs-5" id="exampleModalLabel">Confirm Delete</h1>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
            <i class="bi bi-exclamation-circle h1 text-danger mx-2 mt-2" ></i>
            <span class="h5 mx-4 mb-3">Are you sure you want to delete?</span>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-danger"   onclick="deletebutton('` + trips.scheduleId + `')">Confirm</button>
        </div>
      </div>
    </div>
  </div>
            </td>








						</tr>`

                        $("#tableData").append(row);
                        var temp =trips.count;
                        var totalFinal = Math.ceil(temp / 6);
                        init(totalFinal, curPage);

                    }

                }

            })

        }


    function checkAndUpdateSchedule(scheduleId, date, time, duration, fare, bus, route) {
        $('#alertContainerDelete').empty();
        $.ajax({
            url: "checkScheduleUsage/" + scheduleId + "/" + ${userId},
            type: "GET",
            success: function (response) {
                if (response.isUsed) {
                    var alert = `<div class="alert alert-warning alert-dismissible fade show" role="alert">`;
                    alert += `<strong>Error! </strong>`;
                    alert += `Schedule is used in current bookings and cannot be edited.`;
                    alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                    alert += `</div>`;
                    $('#alertContainerDelete').append(alert);
                } else {
                    updateSchedule(scheduleId, date, time, duration, fare, bus, route);

                    // Trigger the modal
                    $('#edit').modal('show');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error checking schedule usage:", textStatus, errorThrown);
                var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                alert += `<strong>Error! </strong>`;
                alert += `An error occurred while checking schedule usage.`;
                alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert += `</div>`;
                $('#alertContainerDelete').append(alert);
            }
        });
    }

    function updateSchedule(scheduleId, date, time, duration, fare, bus, route) {
        $('#sid').attr('value', scheduleId);
        $('#date2').attr('value', date);
        $('#time2').attr('value', time);
        $('#duration2').attr('value', duration);
        $('#fare2').attr('value', fare);
        $('#busSelect2').val(bus);
        console.log(bus + " bus");
        $('#busSelect2 option[value="' + route + '"]').attr("selected", "selected");
        $('#routeSelect2').val(route);
        $('#routeSelect2 option[value="' + route + '"]').attr("selected", "selected");
    }







    $(document).ready(function() {
        $("#myFormEdit").submit(function(event) {
            $('#routeSelect2').attr('disabled', false);
            $('#date2').attr('disabled', false);
            $('#fare2').attr('disabled', false);
            $('#duration2').attr('disabled', false);
            debugger
            event.preventDefault(); // Prevent default form submission
            if ($("#myFormEdit").valid()) {
                // Get form data using Spring form tag IDs
                var date = $("#date2").val();
                var time = $("#time2").val();
                var id = $("#sid").val();
                var bus = $("#busSelect2").val();
                var route = $("#routeSelect2").val();

                var fare = $("#fare2").val();
                var duration = $("#duration2").val();

                var hasError ;
                const errorSpan = $("#busAvailabilityError1"); // Optional for displaying error messages
                $.ajax({
                    url: "checkBusAvailability/"+ ${userId}, // Replace with your endpoint URL
                    type: "POST",
                    data: {
                        bus: bus,
                        Date: date,
                        Time: time

                    },
                    success: function(response) {
                        console.log(response)
                        if (response) {

                            console.log("Bus available! Submitting form...");

                            errorSpan.text("");
                            $.ajax({
                                url: "editSchedule/"+ ${userId},
                                type: "POST",
                                data: {
                                    date: date,
                                    time: time,
                                    route:route,
                                    duration:duration,
                                    fare:fare,
                                    bus: bus,
                                    id:id


                                },

                                success: function(response) {
                                    $('#loader').show()
                                    console.log("Form submitted successfully:", response);
                                    $('#routeSelect2').attr('disabled', true);
                                    $('#date2').attr('disabled', true);
                                    $('#fare2').attr('disabled', true);
                                    $('#duration2').attr('disabled', true);

                                    $(".btn-close").click();
                                    ajaxCallSearch("desc")
                                    $('#loader').hide()




                                },
                                error: function(jqXHR, textStatus, errorThrown) {
                                    console.error("Error submitting form:", textStatus, errorThrown);

                                }
                            });

                        } else {
                            console.log("error in bus")
                            errorSpan.text("Error: Bus is already booked at that date and time.");
                            hasError = true;
                            console.log(hasError)
                        }
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.error("Error checking bus availability:", textStatus, errorThrown);

                    }
                });




            }



        });
    });

    function deletebutton(scheduleId) {
        $('#alertContainerDelete').empty();
        $.ajax({
            url: "checkScheduleUsage/" + scheduleId + "/" + ${userId},
            type: "GET",
            success: function (response) {
                if (response.isUsed) {
                    var alert = `<div class="alert alert-warning alert-dismissible fade show" role="alert">`;
                    alert += `<strong>Error! </strong>`;
                    alert += `Schedule is used in current bookings and cannot be deleted.`;
                    alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                    alert += `</div>`;
                    $('.btn-close').click();
                    $('#alertContainerDelete').append(alert);
                } else {
                    $.ajax({
                        url: "deleteSchedule/" + scheduleId + "/" + ${userId},
                        type: "POST", //
                        success: function (response) {
                            ajaxCallSearch("desc");

                            console.log("Item deleted successfully:", response);
                            var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`
                            alert += `<strong>Success! </strong>`;
                            alert += `Schedule has been deleted successfully.`;
                            alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                            alert += `</div>`;
                            console.log(alert)
                            $('.btn-close').click();
                            $('#alertContainerDelete').append(alert);
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.error("Error deleting booking:", textStatus, errorThrown);
                            var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                            alert += `<strong>Error! </strong>`;
                            alert += `An error occurred while deleting schedule.`;
                            alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                            alert += `</div>`;
                            $('.btn-close').click();
                            $('#alertContainerDelete').append(alert);
                        }
                      });
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error checking schedule usage:", textStatus, errorThrown);
                var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                alert += `<strong>Error! </strong>`;
                alert += `An error occurred while checking schedule usage.`;
                alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert += `</div>`;
                $('.btn-close').click();
                $('#alertContainerDelete').append(alert);
            }
        });
    }

</script>
<script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>
<script src="<c:url value="/resources/js/schedule.js"/>"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
</body>
</html>