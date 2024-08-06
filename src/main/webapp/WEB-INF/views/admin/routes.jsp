

<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 30-05-2024
  Time: 10:01
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
    <title>Routes</title>


    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
            crossorigin="anonymous">
    <link rel="stylesheet" href="css/style1.css">




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


<body class="bg-dark mt-0">

<div class="container-fluid bg-white mt-0 p-0">
    <jsp:include page="navbar.jsp"/>
</div>
<div id="alertContainerDelete" class="d-flex ms-auto mt-auto"></div>

<div class="container bg-light mt-5  ">

    <div class="d-flex  ">
        <h3 class="text-center mx-auto mt-4 mb-4 ">Routes</h3>

    </div>

    <div class="d-flex mx-3 mt-3">
        <button type="button" class="btn btn-dark mx-2"
                data-bs-toggle="modal" data-bs-target="#add">Add Route
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

                <input type="text" id="searchRegion" name="region"
                       class="form-control border-0  shadow-none" placeholder="Search Routes">
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

<%--            <th scope="col" class="col-md-1">Route Id</th>--%>
            <th scope="col" class="col-md-2">Source</th>
            <th scope="col" class="col-md-2">Destination</th>
            <th scope="col" class="col-md-2">Distance</th>
            <th scope="col" class="col-md-2">Sub Routes</th>

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
                    <h3 class="mx-1 mt-2 fw-5">Add Routes</h3>
                    <button type="button" class="btn-close ms-auto" data-bs-dismiss="modal" id="close"
                            aria-label="Close"></button>
                </div>
            </div>
            <form method="post" id="myForm">
                <div class="modal-body">
                    <div class="row g-2">
                        <input type="text" value="" id="routeId" name="id" hidden>
                        <div class="col-md-4">
                            <div class=" mb-3">
                                <label for="source">Source <span style="color: red;">*</span></label>
                                <input type="text" class="form-control" id="source" placeholder="Source" name="source">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class=" mb-3">
                                <label for="destination" class="dm">Destination <span style="color: red;">*</span></label>
                                <input type="text" class="form-control" id="destination" placeholder="destination" name="destination">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class=" mb-3">
                                <label for="distance" class="dm">Distance (km) <span style="color: red;">*</span></label>
                                <input type="number" class="form-control" id="distance" placeholder="distance" name="distance">
                            </div>
                        </div>
                    </div>
                    <div class="row g-2 mb-3">
                        <div class="col-md-12">
                            <input id="subrouteCount" hidden>
                            <button type="button" id="addSubrouteButton" class="btn btn-outline-success ms-auto">Add Subroute</button>
                        </div>
                    </div>
                    <div id="subroute-container"></div>
                </div>
                <div class="modal-footer border-top-0 d-flex p-0 justify-content-center">
                    <button type="submit" class="btn btn-secondary text-white btn-lg rounded-end" style="width: 20vh;">Save</button>
                    <button type="button" class="btn btn-dark text-white btn-lg rounded-end" style="width: 20vh;" data-bs-dismiss="modal">Cancel</button>
                </div>
            </form>

        </div>


    </div>
</div>
<div class="modal fade" id="edit" tabindex="-1"
     aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog  modal-dialog-centered p-3 w-100 ">

        <div class="modal-content rounded-4 p-4">
            <div class="container-fluid  ">

                <div class="d-flex  mt-1  ">
                    <h3 class="fw-5 mx-2 mt-2 ">Edit
                        Routes</h3>
                    <button type="button" class="btn-close ms-auto" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>
                <form name="myForm1" id="myForm1" method="post">
                    <div class="modal-body">


                        <div class="row g-2">
                            <input type="text" value="" id="routeId1" name="id" hidden>
                            <div class="col-md-4">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="source1" placeholder="Source"
                                           name="source"  disabled > <label
                                        for="source">Source <span
                                        class="text-danger">*</span></label>
                                </div>
                            </div>

                            <div class="col-md-4">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="destination1"
                                           placeholder="name" name="destination" disabled > <label
                                        for="destination" class="dm">Destination <span
                                        class="text-danger">*</span></label>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-floating mb-3">
                                    <input type="number" class="form-control" id="distance1"
                                           placeholder="name" name="destination" disabled > <label
                                        for="distance" class="dm">Distance (km) <span
                                        class="text-danger">*</span></label>
                                </div>
                            </div>

                        </div>

                        <div class="row g-2 mb-3">
                            <div class="col-md-12">
                                <input id="subrouteCount1" hidden>
                                <button type="button" id="addSubrouteButton1" class="btn btn-outline-success ms-auto ">Add Subroute</button>
                            </div>
                        </div>
                        <div id="subroute-container1"></div>
                        <div id="subroute-container2"></div>


                    </div>
                    <div
                            class="modal-footer border-top-0 d-flex p-0 justify-content-center">

                        <button type="submit" class="btn btn-secondary  text-white btn-lg rounded-end"
                                style="width: 25vh;"
                        >Save
                        </button>
                        <button type="button" class="btn btn-dark text-white btn-lg rounded-end " style="width: 25vh;"
                                data-bs-dismiss="modal">Cancel
                        </button>
                    </div>
                </form>
            </div>

        </div>


    </div>
</div>
<div class="d-flex justify-content-center">
    <div id="pagination" class="mb-5"></div>
</div>
<input type="text" id="currentPage" value="1" hidden>

</div>


<script>



    function editRoute(subrouteList, subroutedistList, subrouteIdList, subrouteSequenceList) {
        const subrouteContainer1 = document.getElementById('subroute-container1');
        subrouteContainer1.innerHTML = ''; // Clear existing subroute fields
        const subrouteCount = subrouteList.length;
        document.getElementById('subrouteCount1').value = subrouteCount; // Set subroute count

        for (let i = 1; i <= subrouteCount; i++) {
            subrouteContainer1.innerHTML += `
            <div class="row g-2 row1" id="subroute-row-`+i+`">
                <div class="col-md-2">
                    <div class=" mb-3">
                        <label for="subroute-sequence1-`+i+`" ></label>
                        <input type="text" class="form-control subroute" id="subroute-sequence1-`+i+`" placeholder="Sequence" value="`+subrouteSequenceList[i-1]+`" readonly>

                    </div>
                </div>
                <div class="col-md-4">
                    <div class=" mb-3">
                           <label for="subroute-name1-`+i+`">Subroute</label>
                        <input type="text" class="form-control subroute subrouteName" id="subroute-name1-`+i+`" value="`+subrouteList[i-1]+`" placeholder="Subroute Name" required>

                    </div>
                </div>
                <div class="col-md-4">
                    <div class=" mb-3">
                        <label for="subroute-distance1-`+i+`" >Distance (km)</label>
                        <input type="text" class="form-control subroute subrouteDistance" id="subroute-distance1-`+i+`" value="`+subroutedistList[i-1]+`" placeholder="Distance" required>

                    </div>
                </div>
                <div class="col-md-2">
                    <button type="button" class="btn btn-outline-danger rounded-circle removeSubrouteButton mx-2 h4 mt-2" onclick="removeSubroute(this)">-</button>
                </div>
            </div>
        `;
        }

        // Initialize remove button event listeners
        document.querySelectorAll('.removeSubrouteButton').forEach(button => {
            button.addEventListener('click', () => removeSubroute(button));
        });

        updateSequences();
        updateSubrouteCount();
    }

    function removeSubroute(button) {
        // Find the closest '.row1' by traversing the DOM upwards from the button
        let row = button.closest('.row1');
        if (row) {
            row.remove();
            updateSequences();
            updateSubrouteCount();
        } else {
            console.error('Subroute row not found');
        }
    }

    function updateSequences() {
        const subrouteContainer = document.getElementById('subroute-container1');
        const subrouteRows = subrouteContainer.querySelectorAll('.row1');
        subrouteRows.forEach((row, index) => {
            const sequenceInput = row.querySelector('.subroute');
            if (sequenceInput) {
                sequenceInput.value = index + 1; // Update sequence number
            }
        });
    }

    function updateSubrouteCount() {
        const subrouteCountInput = document.getElementById('subrouteCount1');
        const count = document.querySelectorAll('#subroute-container1 .row1').length;
        subrouteCountInput.value = count;
    }

    // Add event listener for adding a subroute
    document.getElementById('addSubrouteButton1').addEventListener('click', () => {
        const subrouteContainer1 = document.getElementById('subroute-container1');
        const currentCount = document.querySelectorAll('#subroute-container1 .row1').length + 1; // Use correct count
        const newSubroute = `
        <div class="row g-2 row1" id="subroute-row-`+currentCount+`">
            <div class="col-md-2">
                <div class=" mb-3">

                    <label for="subroute-sequence1-`+currentCount+`" ></label>
                    <input type="text" class="form-control subroute" id="subroute-sequence1-`+currentCount+`" placeholder="Sequence" value="`+currentCount+`" readonly>
                </div>
            </div>
            <div class="col-md-4">
                <div class=" mb-3">
                     <label for="subroute-name1-`+currentCount+`" >Subroute</label>
                    <input type="text" class="form-control subroute subrouteName" id="subroute-name1-`+currentCount+`" placeholder="Subroute Name" required>

                </div>
            </div>
            <div class="col-md-4">
                <div class=" mb-3">
                    <label for="subroute-distance1-`+currentCount+`" >Distance (km)</label>
                    <input type="text" class="form-control subroute subrouteDistance" id="subroute-distance1-`+currentCount+`" placeholder="Distance" required>

                </div>
            </div>
            <div class="col-md-2">
                <button type="button" class="btn btn-outline-danger rounded-circle removeSubrouteButton mx-2 h4 mt-2" onclick="removeSubroute(this)">-</button>
            </div>
        </div>
    `;
        subrouteContainer1.insertAdjacentHTML('beforeend', newSubroute);
        updateSubrouteCount();
    });






    function checkAndUpdateRoute(routeId, source, destination, distance, subrouteCount, subrouteSequence, subrouteName, subrouteDistance, subrouteId) {
        $('#alertContainerDelete').empty();
        $.ajax({
            url: "checkRouteUsage/" + routeId + "/" + ${userId},
            type: "GET",
            success: function (response) {
                if (response.isUsed) {
                    var alert = `<div class="alert alert-warning alert-dismissible fade show" role="alert">`;
                    alert += `<strong>Error! </strong>`;
                    alert += `Route is used in schedules and cannot be edited.`;
                    alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                    alert += `</div>`;
                    $('#alertContainerDelete').append(alert);
                } else {
                    updateRoute(routeId, source, destination, distance, subrouteCount, subrouteSequence, subrouteName, subrouteDistance, subrouteId);

                    // Trigger the modal
                    $('#edit').modal('show');
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error checking route usage:", textStatus, errorThrown);
                var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                alert += `<strong>Error! </strong>`;
                alert += `An error occurred while checking route usage.`;
                alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert += `</div>`;
                $('#alertContainerDelete').append(alert);
            }
        });
    }

    function updateRoute(routeId, source, destination, distance, subrouteCount, subrouteSequence, subrouteName, subrouteDistance, subrouteId) {
        $('#routeId1').attr('value', routeId);
        $('#source1').attr('value', source);
        $('#destination1').attr('value', destination);
        $('#distance1').attr('value', distance);
        $('#subrouteCount1').val(subrouteCount);

        var subrouteList = subrouteName.split(",");
        var subroutedistList = subrouteDistance.split(",");
        var subrouteIdList = subrouteId.split(",");
        var subrouteSequenceList = subrouteSequence.split(",");

        $('#subrouteCount1 option[value="' + subrouteCount + '"]').attr("selected", "selected");

        editRoute(subrouteList, subroutedistList, subrouteIdList, subrouteSequenceList, subrouteCount);
    }




</script>


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
        var current = $(".current").text();
        $("#currentPage").attr("value", current);
         ajaxCallSearch()


    })
</script>
<script>
    function disableSaveButton() {
        // Get all elements with class "is-invalid"
        var invalidElements = document.querySelectorAll(".is-invalid");

        // Check if any element has the class "is-invalid"
        var hasInvalid = invalidElements.length > 0;

        // Get the save button element (assuming it has an ID)
        var saveButton = document.getElementById("saveButton");

        // Disable or enable the save button based on the presence of invalid elements
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

    <%--function deletebutton(routeId) {--%>



    <%--    $.ajax({--%>
    <%--        url:  ${userId} +"/deleteRoute/" + routeId,--%>
    <%--        type: "POST", //--%>
    <%--        success: function (response) {--%>

    <%--            ajaxCallSearch();--%>

    <%--            console.log("Item deleted successfully:", response);--%>
    <%--            var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`--%>
    <%--            alert += `<strong>Success! </strong>`;--%>
    <%--            alert+=`Route has been deleted successfully.`;--%>
    <%--            alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;--%>
    <%--            alert+=`</div>`;--%>
    <%--            console.log(alert)--%>
    <%--            $('#alertContainerDelete').append(alert);--%>

    <%--        },--%>
    <%--        error: function(jqXHR, textStatus, errorThrown) {--%>
    <%--            console.error("Error deleting booking:", textStatus, errorThrown);--%>
    <%--            var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;--%>
    <%--            alert+=`<strong>Error! </strong>`;--%>
    <%--            alert+=`An error occurred while deleting route.`;--%>
    <%--            alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;--%>
    <%--            alert+=`</div>`--%>
    <%--            $('#alertContainerDelete').append(alert);--%>
    <%--        }--%>
    <%--    });--%>

    <%--}--%>

    function deletebutton(routeId) {
        $('#alertContainerDelete').empty();
        $.ajax({
            url:   "checkRouteUsage/" + routeId + "/" + ${userId},
            type: "GET",
            success: function (response) {
                if (response.isUsed) {
                    var alert = `<div class="alert alert-warning alert-dismissible fade show" role="alert">`;
                    alert += `<strong>Error! </strong>`;
                    alert += `Route is used in schedules and cannot be deleted.`;
                    alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                    alert += `</div>`;
                    $('#alertContainerDelete').append(alert);
                } else {
                    // Proceed with delete if the route is not used in schedules
                    $.ajax({
                        url: "deleteRoute/" + routeId +"/" + ${userId},
                        type: "POST",
                        success: function (response) {

                            var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`;
                            alert += `<strong>Success! </strong>`;
                            alert += `Route has been deleted successfully.`;
                            alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                            alert += `</div>`;
                            $('#alertContainerDelete').append(alert);
                             ajaxCallSearch();
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                            alert += `<strong>Error! </strong>`;
                            alert += `An error occurred while deleting the route.`;
                            alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                            alert += `</div>`;
                            $('#alertContainerDelete').append(alert);
                        }
                    });
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error checking route usage:", textStatus, errorThrown);
                var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                alert += `<strong>Error! </strong>`;
                alert += `An error occurred while checking route usage.`;
                alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert += `</div>`;
                $('#alertContainerDelete').append(alert);
            }
        });
    }


</script>
<script>
    $(document).ready(function () {
        console.log("in")
        ajaxCallSearch();
    });

    $("#searchRegion").on('keyup', ajaxCallSearch);

    function ajaxCallSearch(event) {




        var region = $("#searchRegion").val().trim();
        console.log(region)

        if (region == "") {
            region = "empty";
        }


        var curPage = $("#currentPage").attr("value");

        $.ajax({

            url:  "ajaxforSearchRoute/" + ${userId},
            type: "post",
            data: {
                region: region,
                curPage: curPage
            },
            success: function (data) {

                console.log("success"+data)

                var curPage = $('#currentPage').attr("value");

                console.log("mee")

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

                    var routes = data[i];


                    var row = `<tr class="w-100">


							<td>` + routes.source + `</td>
							<td>` + routes.destination + `</td>
							<td>` + routes.distance + `</td>
							<td>` + routes.subRoutes + `</td>
                            <td class="justify-content-end d-flex">
<button type="button" class="btn btn-dark editbtn" style="width: 18vh"
        onclick="checkAndUpdateRoute('` + routes.routeId + `', '` + routes.source + `', '` + routes.destination + `', '` + routes.distance + `', '` + routes.subroutesCount + `', '` + routes.sequence + `', '` + routes.name + `', '` + routes.distancesub + `', '` + routes.subrouteId + `')">
    Edit
</button>

                                <button type="button"  class="btn btn-secondary mx-3 " style="width: 18vh"  onclick="deletebutton(`+routes.routeId+`)">
                                  Delete</button>
                            </td>








						</tr>`

                    $("#tableData").append(row);
                    var temp = routes.count;
                    console.log(temp)
                    var totalFinal = Math.ceil(temp / 6);
                    init(totalFinal, curPage);

                }

            }

        })

    }
</script>
<script>
    function clearFormData() {
        // Get the form element by its ID or using another selector
        var form = document.getElementById("FormId");
        $("#busNumber").removeClass("is-invalid");

        // Reset the form (clears most elements)
        form.reset();
        $('.error-message1').remove();
        $('.error-message').remove();



    }

</script>
<script>
    document.addEventListener('DOMContentLoaded', () => {
        const subrouteContainer = document.getElementById('subroute-container');
        const addSubrouteButton = document.getElementById('addSubrouteButton');
        const subrouteCountInput = document.getElementById('subrouteCount');
        let subrouteCount = 0; // Initialize subroute count

        // Event listener for the "Add Subroute" button
        addSubrouteButton.addEventListener('click', () => {
            subrouteCount++;
            addSubrouteRow(subrouteCount);
            updateSubrouteCount(); // Update the hidden input field
        });

        // Function to add a new subroute row
        function addSubrouteRow(i) {
            const row = document.createElement('div');
            row.classList.add('row', 'g-2');
            row.innerHTML = `
            <div class="col-md-2">
                <div class=" mb-3">

                     <label for="subroute-sequence-`+i+`"></label>
                    <input type="text" class="form-control subroute" id="subroute-sequence-`+i+`" placeholder="Sequence" value="`+i+`" readonly>

                </div>
            </div>
            <div class="col-md-4">
                <div class=" mb-3">
                     <label for="subroute-name-`+i+`">Subroute</label>
                    <input type="text" class="form-control subroute subrouteName" id="subroute-name-`+i+`" placeholder="Subroute Name" required>

                </div>
            </div>
            <div class="col-md-4">
                <div class=" mb-3">
                      <label for="subroute-distance-`+i+`">Distance (km)</label>
                    <input type="number" class="form-control subroute subrouteDistance" id="subroute-distance-`+i+`" placeholder="Distance" required>

                </div>
            </div>
            <div class="col-md-2 d-flex align-items-center">
             <button type="button" class="  btn btn-outline-danger rounded-circle removeSubrouteButton mx-2 h4 mt-2"> - </button>
            </div>
        `;

            subrouteContainer.appendChild(row);

            // Event listener for the remove button in the newly added row
            row.querySelector('.removeSubrouteButton').addEventListener('click', () => {
                subrouteContainer.removeChild(row);
                subrouteCount--; // Decrement subroute count
                updateSequences(); // Update sequence numbers
                updateSubrouteCount(); // Update the hidden input field
            });
        }

        // Function to update sequence numbers of all subroute rows
        function updateSequences() {
            const subroutes = subrouteContainer.querySelectorAll('.row');
            subroutes.forEach((subroute, index) => {
                const sequenceInput = subroute.querySelector('.form-control.subroute');
                sequenceInput.value = index + 1; // Update sequence number
            });
        }

        // Function to update the hidden subroute count input field
        function updateSubrouteCount() {
            subrouteCountInput.value = subrouteCount;
        }
    });


</script>

<script>


</script>

<script>
    $('#add').on('hidden.bs.modal', function () {
        $('#myForm')[0].reset();
        $('.error-message1').remove();
        $('.error-message').remove();
        // $('.is-invalid1').remove();
    });
    // $('#edit').on('hidden.bs.modal', function() {
    //     $('#myFormAdd')[0].reset();
    // });
</script>
<script>
    $(document).ready(function () {


        $("#myForm").submit(function (event) {
            debugger
            event.preventDefault(event); // Prevent default form submission

            // Get form data

            var source = $("#source").val();
            var destination = $("#destination").val();
            var distance = $("#distance").val();
            var subroutesCount = $("#subrouteCount").val();
            var sequence = [];
            var name = [];
            var distancesub = [];
            var hasError = false;


            $(".row").each(function () {
                var sequenceInput = $(this).find("input[id^='subroute-sequence']");
                var nameInput = $(this).find("input[id^='subroute-name']");
                var distanceInput = $(this).find("input[id^='subroute-distance']");
                if (sequenceInput.length > 0 && nameInput.length > 0 && distanceInput.length > 0) {
                    var sequence1 = parseInt(sequenceInput.val());
                    var name1 = nameInput.val();
                    var distance1 = parseFloat(distanceInput.val());


                    if (sequence1 && name1 && distance1)

                        sequence.push(sequence1);
                    {
                        // if (sequenceInput.length > 0 && nameInput.length > 0 && distanceInput.length > 0) {
                        //     var sequence1 = parseInt(sequenceInput.val());
                        //     var name1 = nameInput.val().toLowerCase(); // Convert name to lowercase for case-insensitive comparison
                        //     var distance1 = parseFloat(distanceInput.val());
                        //
                        //     // Check if name already exists
                        //     var duplicateFound = false;
                        //     for (var i = 0; i < name.length; i++) {
                        //         if (name1 === existingSubrouteNames[i]) {
                        //             duplicateFound = true;
                        //             distanceInput.addClass('is-invalid1');
                        //             distanceInput.after('<span class="error-message text-danger">Subroute distance cannot exceed total route distance (' + distance + ' km).</span>');
                        //             hasError = true;
                        //
                        //         }
                        //     }
                        // }
                        // else {
                        //     distanceInput.remove('is-invalid1'); // Remove error class (if previously added)
                        //     distanceInput.next('.error-message').remove();
                        // }

                        name.push(name1);

                        if (distance1 > distance) {

                            const existingErrorMessage = distanceInput.next('.error-message');
                            hasError = true;

                            if (!existingErrorMessage.length) {
                                distanceInput.addClass('is-invalid1');
                                distanceInput.after('<span class="error-message text-danger">Subroute distance cannot exceed total route distance (' + distance + ' km).</span>');
                                hasError = true;
                            }
                        } else {
                            distanceInput.remove('is-invalid1'); // Remove error class (if previously added)
                            distanceInput.next('.error-message').remove();
                        }
                        distancesub.push(distance1);

                    }
                }

            });


            if (source === destination) {
                hasError = true;
                // Check if the error message already exists
                if (!$("#destination").next().hasClass("error-message")) {
                    $("#destination").addClass('is-invalid1');
                    console.log(source + 11);
                    console.log(destination + 22);
                    $("#destination").after('<span class="error-message text-danger">Source and Destination can not be same.</span>');
                    hasError = true;
                }
            }

            if (hasError) {
                console.log("haserror")

                $('.is-invalid1').focus();
            } else if ($("#myForm").valid()) {
                console.log($("#myForm").valid())

                console.log("Form data is valid:", source, destination, distance, subroutesCount, sequence, name, distancesub);

                $.ajax({
                    url:   "addRoute" +"/" + ${userId},
                    type: "POST",
                    traditional: true,
                    data: {

                        source: source,
                        destination: destination,
                        subroutesCount: subroutesCount,
                        sequence: sequence,
                        name: name,
                        distance: distance,
                        distancesub: distancesub

                    },

                    success: function (data) {


                        console.log("Form submitted successfully:", data);
                        var form = document.getElementById("myForm");
                        form.reset();
                        $(".btn-close").click();
                        ajaxCallSearch()
                    },
                    error: function (error) {
                        console.error("Error submitting form:", error);

                    }
                })
            } else {
                console.log("errors")
            }
        });
    });





    // Optional form validation function (replace with your validation logic)

</script>
<script>
    $(document).ready(function () {
        debugger
        $("#myForm1").submit(function (event) {
            $('#source1').attr('disabled', false);
            $('#destination1').attr('disabled', false);
            $('#distance1').attr('disabled', false);
            event.preventDefault(event);
            // Prevent default form submission
            console.log("ajax on edit")
            // Get form data
            debugger

            var source1 = $("#source1").val();
            var destination1 = $("#destination1").val();
            var distance1 = $("#distance1").val();
            var subroutesCount1 = $("#subrouteCount1").val();
            var id1 = $("#routeId1").val();
            var sequence1 = [];
            var name1 = [];
            var distancesub1 = [];
            var hasError1 = false;


            $(".row1").each(function () {
                debugger
                var sequenceInput1 = $(this).find("input[id^='subroute-sequence1']");
                var nameInput1 = $(this).find("input[id^='subroute-name1']");
                var distanceInput1 = $(this).find("input[id^='subroute-distance1']");
                if (sequenceInput1.length > 0 && nameInput1.length > 0 && distanceInput1.length > 0) {
                    var sequence11 = parseInt(sequenceInput1.val());
                    var name11 = nameInput1.val();
                    var distance11 = parseFloat(distanceInput1.val());


                    if (sequence11 && name11 && distance11)

                        sequence1.push(sequence11);
                    {

                        name1.push(name11);

                        if (distance11 > distance1) {

                            const existingErrorMessage1 = distanceInput1.next('.error-message');
                            hasError1 = true;

                            if (!existingErrorMessage1.length) {
                                distanceInput1.addClass('is-invalid1');
                                distanceInput1.after('<span class="error-message text-danger">Subroute distance cannot exceed total route distance (' + distance + ' km).</span>');
                                hasError = true;
                            }
                        } else {
                            distanceInput1.remove('is-invalid1'); // Remove error class (if previously added)
                            distanceInput1.next('.error-message').remove();
                        }
                        distancesub1.push(distance11);

                    }
                }

            });

            console.log(sequence1)
            console.log(name1)
            console.log(distancesub1)




            if (hasError1) {
                console.log("haserror")

                $('.is-invalid1').focus();
            } else if ($("#myForm1").valid()) {
                console.log($("#myForm1").valid())



                $.ajax({
                    url:  "editRoute"+"/" + ${userId}, // Replace with your server-side script URL
                    type: "POST",
                    traditional: true,
                    data: {

                        source: source1,
                        destination: destination1,
                        subroutesCount: subroutesCount1,
                        sequence: sequence1,
                        name: name1,
                        distance: distance1,
                        distancesub: distancesub1,
                        id: id1

                    },

                    success: function (data) {


                        console.log("Form submitted successfully:", data);
                        $('#source1').attr('disabled', true);
                        $('#destination1').attr('disabled', true);
                        $('#distance1').attr('disabled', true);
                        $(".btn-close").click();
                         ajaxCallSearch()
                    },
                    error: function (error) {
                        console.error("Error submitting form:", error);

                    }
                })
            } else {
                console.log("errors")
            }
        });
    });





    // Optional form validation function (replace with your validation logic)

</script>
<script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>

<script src="<c:url value="/resources/js/route.js" />"></script>
</body>

</html>
</body>
</html>