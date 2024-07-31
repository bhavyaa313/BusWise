<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 26-06-2024
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 26-06-2024
  Time: 10:03
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
    <title>Occupancy Reports</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">




<%--    <script--%>
<%--            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"--%>
<%--            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"--%>
<%--            crossorigin="anonymous"></script>--%>

    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>

    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
            crossorigin="anonymous"></script>



    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>

    <style>

        body {
            background-color: #f8f9fa;
        }
        .nav-tabs1 {
            margin-top: 20px;
            border-right: 2px solid #ffffff;
        }
        .nav-tabs {

            border-right: 2px solid #ffffff;
        }
        .nav-item1 {
            background: #cfceceb7;
            border: #6c727a;
        }
        .nav-tabs1 .nav-link1 {
            color: #495057;

            padding: 15px 20px;
            transition: all 0.3s;
            font-weight: bold;
            border-right: 2px solid #ffffff;


        }
        .nav-tabs .nav-link.active1 {
            background-color: #6c727a;
            color: #fff;

        }
        .nav-tabs1 .nav-link1:hover {
            background-color: #e9ecef;
            color: #007bff;
        }
        .nav-tabs1 .nav-link1:focus, .nav-tabs1 .nav-link1:active {
            box-shadow: none;
        }
        .container {
            background: #ffffff;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            padding: 20px;
        }
        .form-control {
            height: 50px;
            border-radius: 5px;
        }
        .date-picker-wrapper {
            display: flex;
            align-items: center;
            justify-content: space-between;

        }
        .date-picker-wrapper .h3 {
            margin-right: 20px;
        }
        .report-title {
            margin-top: 20px;
            font-size: 29px;
            font-weight: bold;
            align-items: center;
            align-content: center;
        }
        .fetchh{
            margin-top: 3vh;
        }

        .margin{
            margin-top: 75px;
        }


        #margin11{
            margin-top: 42vh;
        }
        .chartContainer {
            height: 400px; /* Adjust height as needed */
            margin-bottom: 5vh; /* Margin between chart and download report */
        }
    </style>


</head>
<%--<body>--%>
<%--<jsp:include page="navbar.jsp" />--%>
<%--<div>--%>
<%--    <canvas id="myChart"></canvas>--%>
<%--</div>--%>



<%--<script>--%>
<%--    const ctx = document.getElementById('myChart');--%>

<%--    new Chart(ctx, {--%>
<%--        type: 'bar',--%>
<%--        data: {--%>
<%--            labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],--%>
<%--            datasets: [{--%>
<%--                label: '# of Votes',--%>
<%--                data: [12, 19, 3, 5, 2, 3],--%>
<%--                borderWidth: 1--%>
<%--            }]--%>
<%--        },--%>
<%--        options: {--%>
<%--            scales: {--%>
<%--                y: {--%>
<%--                    beginAtZero: true--%>
<%--                }--%>
<%--            }--%>
<%--        }--%>
<%--    });--%>
<%--</script>--%>
<%--</body>--%>
<body>
<jsp:include page="navbar.jsp"></jsp:include>
<ul class="nav nav-tabs nav-tabs1 justify-content-center mx-3 mt-5">
    <li class="nav-item nav-item1 w-25">
        <button class="nav-link active1 nav-link1 w-100" id="daily">Daily</button>
    </li>
    <li class="nav-item nav-item1 w-25">
        <button class="nav-link nav-link1 w-100" id="monthly">Monthly</button>
    </li>
    <li class="nav-item nav-item1 w-25">
        <button class="nav-link nav-link1 w-100" id="yearly" >Yearly</button>
    </li>
</ul>



<div class="container mt-5 d-none" id="dailyReport">
    <div class="d-flex mt-3">
        <div class="ms-0">
            <div class="h5">Select a Date</div>
            <input type="date" class="form-control form-control-lg" placeholder="date" id="reportDate" name="date">

        </div>
        <div class="fetchh">
            <button type="button" class="mx-5 btn btn-lg  btn-outline-dark " onclick="fetchDailyOccupancy()" id="fetch">Fetch Data</button>
        </div>
        <div class="report-title justify-content-center h3 mx-3 mt-4 ms-auto">Daily Occupancy Reports</div>
    </div>




    <div class="row mt-5 mb-5 d-flex chartContainer justify-content-center align-items-center m-auto">
        <canvas id="occupancyChart" width="500" height="250"></canvas>

    </div>


    <div class="mt-5  d-flex mx-5" id="margin11">

        <div class="h4  ms-auto  mx-5 ">  <span class="mx-5 mt-5"> Detailed Daily Report </span> <span class="ms-auto ">

        <button class="btn-lg btn btn-danger" id="downloadDailyOccupancy">Download Data</button>   </span></div>
        <div id="alertContainerDaily" ></div>
    </div>
</div>





<div class="container mt-5 d-none" id="monthlyReport">
    <div class="d-flex mt-3">
        <div class="ms-0">
            <div class="h5">Select a Date</div>
            <input type="month" class="form-control form-control-lg" placeholder="month" id="reportMonth" name="month">

        </div>
        <div class="fetchh">
            <button type="button" class="mx-5 btn btn-lg  btn-outline-dark " onclick="fetchMonthlyOccupancy()" id="fetch1">Fetch Data</button>
        </div>
        <div class="report-title justify-content-center h3 mx-3 mt-4 ms-auto">Monthly Occupancy Reports</div>
    </div>




    <div class="row mt-5 mb-5 d-flex chartContainer  justify-content-center align-items-center m-auto">

            <canvas id="monthlyOccupancyChart"  width="400" height="200"></canvas>


    </div>


    <div class="mt-5  d-flex mx-5" id="">

        <div class="h4  ms-auto  mx-5 ">  <span class="mx-5 mt-5"> Detailed Monthly Report </span> <span class="ms-auto ">

        <button class="btn-lg btn btn-danger" id="downloadMonthlySales">Download Data</button>   </span></div>
        <div id="alertContainerMonthly" ></div>
    </div>
</div>




<div class="container mt-5 d-none" id="yearlyReport">
    <div class="d-flex mt-3">
        <div class="ms-0">
            <div class="h5">Select a Year</div>

            <select class="form-control form-control-lg" name="year" id="reportYear">
                <option value="2022">2022</option>
                <option value="2023">2023</option>
                <option value="2024" selected>2024</option>
            </select>

        </div>
        <div class="fetchh">
            <button type="button" class="mx-5 btn btn-lg  btn-outline-dark " onclick="fetchYearlyOccupancy()" id="fetch2">Fetch Data</button>
        </div>
        <div class="report-title justify-content-center h3 mx-3 mt-4 ms-auto">Yearly Occupancy Reports</div>
    </div>






    <div class="row mt-5 mb-5 d-flex chartContainer justify-content-center m-auto">

        <canvas id="yearlyOccupancyChart" width="400" height="200"></canvas>

    </div>
    <div class="mt-5  d-flex mx-5" >

        <div class="h4  ms-auto  mx-5 ">  <span class="mx-5 mt-5"> Detailed yearly Report </span> <span class="ms-auto ">

        <button class="btn-lg btn btn-danger" id="downloadYearlyOccupancy">Download Data</button>   </span></div>
        <div id="alertContainer" ></div>
    </div>
</div>









<script>



    $(document).ready(function () {

            var today = new Date().toISOString().split('T')[0];
            console.log("today" + today)
            $('#dailyReport').removeClass("d-none")
            $('#reportDate').val(today);


            $('#fetch').click();


        }
    )


    $('#downloadDailyOccupancy').click(function () {
        console.log("helo")
        var date = $('#reportDate').val()

        $.ajax({
            url: '${pageContext.request.contextPath}/daily-occupancy-download/' + date,
            type: 'GET',

            success: function (response) {
                console.log("success");
                var fileUrl = response;
                window.location.href= fileUrl;
                var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`
                alert += `<strong>Success! </strong>`;
                alert+=`Report Downloaded Successfully!`;
                alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert+=`</div>`;
                console.log(alert)
                $('#alertContainerDaily').append(alert);
            },
            error: function () {
                console.log("error");
                var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                alert+=`<strong>Error! </strong>`;
                alert+=`An error occurred while downloading the report.`;
                alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert+=`</div>`
                $('#alertContainerDaily').append(alert);
            }
        });
    });


    $('#downloadMonthlySales').click(function () {
        var date = $('#reportMonth').val()

        $.ajax({
            url: '${pageContext.request.contextPath}/monthly-occupancy-download/' + date,
            type: 'GET',

            success: function (response) {
                console.log("success");
                var fileUrl = response;
                window.location.href= fileUrl;
                var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`
                alert += `<strong>Success! </strong>`;
                alert+=`Report Downloaded Successfully!`;
                alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert+=`</div>`;
                console.log(alert)
                $('#alertContainerMonthly').append(alert);
            },
            error: function () {
                console.log("error");
                var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                alert+=`<strong>Error! </strong>`;
                alert+=`An error occurred while downloading the report.`;
                alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert+=`</div>`
                $('#alertContainerMonthly').append(alert);
            }
        });
    });


    $('#downloadYearlyOccupancy').click(function () {
        var year = $('#reportYear').val()

        $.ajax({
            url: '${pageContext.request.contextPath}/yearly-occupancy-download/' + year,
            type: 'GET',

            success: function (response) {
                console.log("success");
                var fileUrl = response;
                window.location.href= fileUrl;
                var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`
                alert += `<strong>Success! </strong>`;
                alert+=`Report Downloaded Successfully!`;
                alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert+=`</div>`;
                console.log(alert)
                $('#alertContainer').append(alert);
            },
            error: function () {
                console.log("error");
                var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                alert+=`<strong>Error! </strong>`;
                alert+=`An error occurred while downloading the report.`;
                alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert+=`</div>`
                $('#alertContainer').append(alert);
            }
        });
    });


    $('#daily').click(function () {
        console.log("yeyye")

        $("#dailyReport").removeClass("d-none")
        $("#monthlyReport").addClass("d-none")
        $("#yearlyReport").addClass("d-none")
        $("#monthly").removeClass("active1")
        $("#yearly").removeClass("active1")
        $("#daily").addClass("active1")


    });


    $('#monthly').click(function () {
        console.log("yeyye")
        debugger

        $("#monthlyReport").removeClass("d-none")

        $("#yearlyReport").addClass("d-none")
        $("#dailyReport").addClass("d-none")
        $("#daily").removeClass("active1")
        $("#yearly").removeClass("active1")
        $("#monthly").addClass("active1")
        const today = new Date();
        const currentYear = today.getFullYear();
        const currentMonth = today.getMonth() + 1;

        const formattedMonth = currentMonth < 10 ? '0' + currentMonth : currentMonth.toString();

        const formattedDate = currentYear + '-' + formattedMonth;

        const reportMonthInput = document.getElementById('reportMonth');
        reportMonthInput.value = formattedDate;

        // var dp = $('#reportMonth').get(0)
        // dp.setAttribute('max', formattedDate);
        $('#fetch1').click();


    });


    $('#yearly').click(function () {
        console.log("yeyye")
        $("#dailyReport").addClass("d-none")
        $("#monthlyReport").addClass("d-none")
        $("#yearlyReport").removeClass("d-none")

        $("#monthly").removeClass("active1")
        $("#daily").removeClass("active1")
        $("#yearly").addClass("active1")
        $("#fetch2").click();


    });





    var occupancyChart;

    function fetchDailyOccupancy() {
        var date = $('#reportDate').val();
        $.ajax({
            url: '${pageContext.request.contextPath}/daily-occupancy',
            type: 'POST',
            data: { date: date },
            success: function(data) {
                console.log(data);

                if (occupancyChart) {
                    occupancyChart.destroy();
                }

                const labels = data.map(item => `Route `+item.routeId+`: `+item.route+``);
                const occupancyPercentages = data.map(item => item.occupancyPercentage);

                const ctx = document.getElementById('occupancyChart').getContext('2d');
                occupancyChart = new Chart(ctx, {
                    type: 'bar', // Change chart type to bar
                    data: {
                        labels: labels,
                        datasets: [{
                            label: 'Occupancy Percentage',
                            data: occupancyPercentages,
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            legend: {
                                display: false // Adjust legend display if needed
                            },
                            tooltip: {
                                callbacks: {
                                    label: function(tooltipItem) {
                                        return ``+tooltipItem.label+`: `+tooltipItem.raw.toFixed(2)+`%`;
                                    }
                                }
                            }
                        },
                        scales: {
                            x: {
                                title: {
                                    display: true,
                                    text: 'Routes and Schedule IDs' // X-axis title
                                }
                            },
                            y: {
                                title: {
                                    display: true,
                                    text: 'Occupancy Percentage (%)' // Y-axis title
                                },
                                ticks: {
                                    beginAtZero: true,
                                    callback: function(value) {
                                        return value + '%';
                                    }
                                }
                            }
                        }
                    }
                });
            },
            error: function(err) {
                console.error(err);
            }
        });
    }


    var monthlyOccupancyChart;

    function fetchMonthlyOccupancy() {
        debugger
        var date = $('#reportMonth').val();
        if (monthlyOccupancyChart) {
            monthlyOccupancyChart.destroy();
        }

        $.ajax({
            url: '${pageContext.request.contextPath}/monthly-occupancy',
            type: 'POST',
            data: { date: date },
            success: function(data) {
                console.log('Received data:', data);

                if (!data || data.length === 0) {
                    console.error('No data received or data is empty');
                    return;
                }

                const labels =  data.map(item => `Route`+item.routeId+`: `+item.route+``);
                const occupancyPercentages = data.map(item => item.occupancyPercentage);

                const ctx = document.getElementById('monthlyOccupancyChart').getContext('2d');
                monthlyOccupancyChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: labels,
                        datasets: [{
                            label: 'Occupancy Percentage',
                            data: occupancyPercentages,
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            legend: {
                                display: false // Adjust legend display if needed
                            },
                            tooltip: {
                                callbacks: {
                                    label: function(tooltipItem) {
                                        return ``+tooltipItem.label+`: `+tooltipItem.raw.toFixed(2)+`}%`;
                                    }
                                }
                            }
                        },
                        scales: {
                            x: {
                                title: {
                                    display: true,
                                    text: 'Routes'
                                }
                            },
                            y: {
                                title: {
                                    display: true,
                                    text: 'Occupancy Percentage (%)'
                                },
                                ticks: {
                                    beginAtZero: true,
                                    callback: function(value) {
                                        return value + '%';
                                    }
                                }
                            }
                        }
                    }
                });
            },
            error: function(err) {
                console.error('Error fetching data:', err);
            }
        });
    }


    var yearlyOccupancyChart;

    function fetchYearlyOccupancy() {
        debugger
        var year = $('#reportYear').val();
        if (yearlyOccupancyChart) {
            yearlyOccupancyChart.destroy();
        }

        $.ajax({
            url: '${pageContext.request.contextPath}/yearly-occupancy',
            type: 'POST',
            data: { year: year },
            success: function(data) {
                console.log('Received data:', data);

                if (!data || data.length === 0) {
                    console.error('No data received or data is empty');
                    return;
                }

                const labels =  data.map(item => `Route`+item.routeId+`: `+item.route+``);
                const occupancyPercentages = data.map(item => item.occupancyPercentage);

                const ctx = document.getElementById('yearlyOccupancyChart').getContext('2d');
                yearlyOccupancyChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: labels,
                        datasets: [{
                            label: 'Occupancy Percentage',
                            data: occupancyPercentages,
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            borderColor: 'rgba(54, 162, 235, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            legend: {
                                display: false // Adjust legend display if needed
                            },
                            tooltip: {
                                callbacks: {
                                    label: function(tooltipItem) {
                                        return ``+tooltipItem.label+`: `+tooltipItem.raw.toFixed(2)+`}%`;
                                    }
                                }
                            }
                        },
                        scales: {
                            x: {
                                title: {
                                    display: true,
                                    text: 'Routes'
                                }
                            },
                            y: {
                                title: {
                                    display: true,
                                    text: 'Occupancy Percentage (%)'
                                },
                                ticks: {
                                    beginAtZero: true,
                                    callback: function(value) {
                                        return value + '%';
                                    }
                                }
                            }
                        }
                    }
                });
            },
            error: function(err) {
                console.error('Error fetching data:', err);
            }
        });
    }


</script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</body>
</html>

