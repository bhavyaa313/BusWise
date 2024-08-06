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
    <title>Sales Report</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">




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
    <div class="row mt-3 align-items-center">
        <div class="col-12 col-md-auto mb-3 mb-md-0">
            <div class="h5">Select a Date</div>
            <input type="date" class="form-control form-control-lg" placeholder="date" id="reportDate"  name="date">
        </div>
        <div class="col-12 col-md-auto mt-4 mb-md-0 text-center text-md-left">
            <button type="button" class="btn btn-lg btn-outline-dark mx-md-5" onclick="fetchSalesData()" id="fetch">Fetch Data</button>
        </div>
        <div class="col-12 col-md text-center text-md-end mt-4 mx-3">
            <div class="h3 mt-4 mt-md-0">Daily Sales Reports</div>
        </div>
    </div>




    <div class="row mt-5 mb-5 d-flex chartContainer">
        <div class="col-md-6 align-items-center justify-content-center ">
            <canvas id="totalSalesChart"></canvas>
        </div>


        <div class="col-md-6">
            <canvas id="numberOfBookingsChart" ></canvas>
        </div>

    </div>


    <div class="mt-5 d-flex flex-column flex-md-row justify-content-center justify-content-md-between mx-2 mx-md-5" id="margin11">
        <div class="h4 mb-3 mb-md-0 ms-auto">
            <span class="mr-3 mr-md-5">Detailed Daily Report</span>
            <button class="btn-lg btn btn-danger mx-5" id="downloadDailySales">Download Data</button>
            <a href="" download="" id="dailySalesPdf"></a>
        </div>
        <div id="alertContainerDaily" class="mt-3 mt-md-0"></div>
    </div>

</div>



<div class="container mt-5 d-none" id="monthlyReport">
    <div class="row mt-3 align-items-center">
        <div class="col-12 col-md-auto mb-3 mb-md-0">
            <div class="h5">Select a Month</div>
            <input type="month" class="form-control form-control-lg" placeholder="month" id="reportMonth" name="month">
        </div>
        <div class="col-12 col-md-auto mb-3 mt-4 mb-md-0 text-center text-md-left">
            <button type="button" class="btn btn-lg btn-outline-dark mx-md-5" onclick="fetchSalesDataMonthly()" id="fetch1">Fetch Data</button>
        </div>
        <div class="col-12 col-md text-center text-md-end mx-3 mt-4">
            <div class="h3 mt-4 mt-md-0">Monthly Sales Reports</div>
        </div>
    </div>



    <div class="row mt-5 mb-5 d-flex chartContainer">
        <div class="col-md-6 align-items-center justify-content-center ">
            <canvas id="totalSalesChart1"></canvas>
        </div>


        <div class="col-md-6">
            <canvas id="numberOfBookingsChart1" ></canvas>
        </div>

    </div>


    <div class="mt-5 d-flex flex-column flex-md-row justify-content-center justify-content-md-between mx-2 mx-md-5" id="margin11">
        <div class="h4 mb-3 mb-md-0 ms-auto">
            <span class="mr-3 mr-md-5">Detailed Monthly Report</span>
        <button class="btn-lg btn btn-danger mx-5" id="downloadMonthlySales">Download Data</button>
            <a href="" download="" id="monthlySalesPdf"></a>
        </div>
        <div id="alertContainerMonthly" class="mt-3 mt-md-0" ></div>
    </div>
</div>




<div class="container mt-5 d-none" id="yearlyReport">
    <div class="row mt-3 align-items-center">
        <div class="col-12 col-md-auto mb-3 mb-md-0">
            <div class="h5">Select a Year</div>
            <select class="form-control form-control-lg" name="year" id="reportYear">
                <option value="2022">2022</option>
                <option value="2023">2023</option>
                <option value="2024" selected>2024</option>
            </select>
        </div>
        <div class="col-12 col-md-auto mb-3 mt-4 mb-md-0 text-center text-md-left">
            <button type="button" class="btn btn-lg btn-outline-dark mx-md-5" onclick="fetchSalesDataYearly()" id="fetch2">Fetch Data</button>
        </div>
        <div class="col-12 col-md text-center text-md-end mx-2 mt-4">
            <div class="h3 mt-4 mt-md-0">Yearly Sales Reports</div>
        </div>
    </div>




    <div class="row mt-5 mb-5 d-flex chartContainer">
        <div class="col-md-6 align-items-center justify-content-center ">
            <canvas id="totalSalesChart2"></canvas>
        </div>


        <div class="col-md-6">
            <canvas id="numberOfBookingsChart2" ></canvas>
        </div>

    </div>

    <div class="row mt-5 mb-5 d-flex chartContainer">

    <canvas id="monthlySalesChart" width="400" height="200"></canvas>

</div>
    <div class="mt-5 d-flex justify-content-center justify-content-md-between mx-2 mx-md-5">
        <div class="h4 ml-md-auto ms-md-auto mt-2">
            <span class="mr-3 ml-md-auto ms-md-auto ">Detailed Yearly Report</span>
        </div>
        <div class="ml-md-auto mx-5 ">
            <button class="btn-lg btn btn-danger" id="downloadYearlySales">Download Data</button>
            <a href="" download="" id="yearlySalesPdf"></a>
        </div>
        <div id="alertContainer" class="mt-3 mt-md-0"></div>
    </div>


</div>








<script type="text/javascript">
    var totalSalesChart;
    var numberOfBookingsChart;

    function fetchSalesData() {
        debugger
        var selectedDate = document.getElementById("reportDate").value;
        $.ajax({
            url: "${pageContext.request.contextPath}/dailySales",
            type: "POST",
            data: {
                date: selectedDate
            },
            success: function (data) {
                console.log("Sales Data:", data);
                debugger

                if (totalSalesChart) {
                    totalSalesChart.destroy();
                }
                if (numberOfBookingsChart) {
                    numberOfBookingsChart.destroy();
                }
                var totalSalesCtx = document.getElementById('totalSalesChart').getContext('2d');
                totalSalesChart = new Chart(totalSalesCtx, {
                    type: 'bar',
                    data: {
                        labels: ['Total Sales'],
                        datasets: [{
                            label: 'Total Sales',
                            data: [data.totalSales],
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            borderColor: 'rgba(54, 167, 260, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    padding: 30, // Adjust the padding between y-axis labels and axis
                                    callback: function(value) {
                                        return Number.isInteger(value) ? value : ''; // Display only integer values
                                    },
                                }
                            }
                        }
                    }
                });

                var numberOfBookingsCtx = document.getElementById('numberOfBookingsChart').getContext('2d');
                numberOfBookingsChart = new Chart(numberOfBookingsCtx, {
                    type: 'bar',
                    data: {
                        labels: ['Number of Bookings'],
                        datasets: [{
                            label: 'Number of Bookings',
                            data: [data.numberOfBookings],
                            backgroundColor: 'rgba(75, 192, 192, 0.2)',
                            borderColor: 'rgba(75, 192, 192, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    padding: 30, // Adjust the padding between y-axis labels and axis
                                    callback: function(value) {
                                        return Number.isInteger(value) ? value : ''; // Display only integer values
                                    },
                                }
                            }
                        }
                    }
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error fetching data:", textStatus, errorThrown);
            }
        });
    }


    var totalSalesChartMonthly;
    var numberOfBookingsChartMonthly;

    function fetchSalesDataMonthly() {
        debugger
        var selectedDate = document.getElementById("reportMonth").value;
        console.log(selectedDate)
        $.ajax({
            url: "${pageContext.request.contextPath}/monthlySales",
            type: "POST",
            data: {
                date: selectedDate
            },
            success: function (data) {
                console.log("Sales Data:", data);
                debugger

                if (totalSalesChartMonthly) {
                    totalSalesChartMonthly.destroy();
                }
                if (numberOfBookingsChartMonthly) {
                    numberOfBookingsChartMonthly.destroy();
                }
                var totalSalesCtx = document.getElementById('totalSalesChart1').getContext('2d');
                totalSalesChartMonthly = new Chart(totalSalesCtx, {
                    type: 'bar',
                    data: {
                        labels: ['Total Sales'],
                        datasets: [{
                            label: 'Total Sales',
                            data: [data.totalSales],
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            borderColor: 'rgba(54, 167, 260, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    padding: 30, // Adjust the padding between y-axis labels and axis
                                    callback: function (value) {
                                        return Number.isInteger(value) ? value : '';
                                    }
                                }
                            }
                        }
                    }
                });

                var numberOfBookingsCtx = document.getElementById('numberOfBookingsChart1').getContext('2d');
                numberOfBookingsChartMonthly = new Chart(numberOfBookingsCtx, {
                    type: 'bar',
                    data: {
                        labels: ['Number of Bookings'],
                        datasets: [{
                            label: 'Number of Bookings',
                            data: [data.numberOfBookings],
                            backgroundColor: 'rgba(75, 192, 192, 0.2)',
                            borderColor: 'rgba(75, 192, 192, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    padding: 30,
                                    callback: function (value) {
                                        return Number.isInteger(value) ? value : '';
                                    },
                                }
                            }
                        }
                    }
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error fetching data:", textStatus, errorThrown);
            }
        });
    }


    var totalSalesChartYearly;
    var numberOfBookingsChartYearly;

    function fetchSalesDataYearly() {
        debugger
        var year = document.getElementById("reportYear").value;

        $.ajax({
            url: "${pageContext.request.contextPath}/yearly-sales",
            type: "POST",
            data: {
                year: year
            },
            success: function (data) {
                console.log("Sales Data:", data);
                debugger

                if (totalSalesChartYearly) {
                    totalSalesChartYearly.destroy();
                }
                if (numberOfBookingsChartYearly) {
                    numberOfBookingsChartYearly.destroy();
                }
                var totalSalesCtx = document.getElementById('totalSalesChart2').getContext('2d');
                totalSalesChartYearly = new Chart(totalSalesCtx, {
                    type: 'bar',
                    data: {
                        labels: ['Total Sales'],
                        datasets: [{
                            label: 'Total Sales',
                            data: [data.totalSales],
                            backgroundColor: 'rgba(54, 162, 235, 0.2)',
                            borderColor: 'rgba(54, 167, 260, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    padding: 30, // Adjust the padding between y-axis labels and axis
                                    callback: function (value) {
                                        return Number.isInteger(value) ? value : ''; // Display only integer values
                                    },
                                }
                            }
                        }
                    }
                });

                var numberOfBookingsCtx = document.getElementById('numberOfBookingsChart2').getContext('2d');
                numberOfBookingsChartYearly = new Chart(numberOfBookingsCtx, {
                    type: 'bar',
                    data: {
                        labels: ['Number of Bookings'],
                        datasets: [{
                            label: 'Number of Bookings',
                            data: [data.numberOfBookings],
                            backgroundColor: 'rgba(75, 192, 192, 0.2)',
                            borderColor: 'rgba(75, 192, 192, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        scales: {
                            y: {
                                beginAtZero: true,
                                ticks: {
                                    callback: function(value) {
                                        return Number.isInteger(value) ? value : ''; // Display only integer values
                                    },
                                    padding: 30 // Adjust the padding between y-axis labels and axis
                                }
                            }
                        }
                    }
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Error fetching data:", textStatus, errorThrown);
            }
        });
    }


    $(document).ready(function () {

            var today = new Date().toISOString().split('T')[0];
            console.log("today" + today)
            $('#dailyReport').removeClass("d-none")
            $('#reportDate').val(today);
            $('#fetch').click();
           var dp = $('#reportDate').get(0)
        dp.setAttribute('max', today);


        }
    )


    $('#downloadDailySales').click(function () {
        var date = $('#reportDate').val()

        $.ajax({
            url: '${pageContext.request.contextPath}/dailySalesDownlaod/' + date,
            type: 'GET',

            success: function (response) {
                console.log("success");

                var fileUrl = response;
                var downloadLink = document.getElementById("dailySalesPdf");
                downloadLink.href = fileUrl;
                downloadLink.download = "Daily-Sales-Report-" + date + ".pdf";
                downloadLink.click();
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
            url: '${pageContext.request.contextPath}/monthly-sales-download/' + date,
            type: 'GET',

            success: function (response) {
                var fileUrl = response;
                var downloadLink = document.getElementById("monthlySalesPdf");
                downloadLink.href = fileUrl;
                downloadLink.download = "Monthly-Sales-Report-" + date + ".pdf";
                downloadLink.click();
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


    $('#downloadYearlySales').click(function () {
        var year = $('#reportYear').val();

        $.ajax({
            url: '${pageContext.request.contextPath}/yearly-sales-download/' + year,
            type: 'GET',

            success: function (response) {
                console.log("success");
                var fileUrl = response;
                var downloadLink = document.getElementById("yearlySalesPdf");
                downloadLink.href = fileUrl;
                downloadLink.download = "Yearly-Sales-Report-" + year + ".pdf";
                downloadLink.click();
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

        var dp = $('#reportMonth').get(0)
        dp.setAttribute('max', formattedDate);
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



        $(document).ready(function() {
            debugger
            var year = document.getElementById("reportYear").value;
        $.ajax({
        url: '${pageContext.request.contextPath}/year-monthly-sales',
        type: 'POST',
        data: {
            year: year
        },
        success: function(data) {
        var months = [];
        var totalSales = [];
        var numberOfBookings = [];

        for (var i = 0; i < data.length; i++) {
        months.push(getMonthName(data[i].month));
        totalSales.push(data[i].totalSales);
        numberOfBookings.push(data[i].numberOfBookings);

    }
        console.log(numberOfBookings +"fg")

        var ctx = document.getElementById('monthlySalesChart').getContext('2d');
        var chart = new Chart(ctx, {
        type: 'bar',
        data: {
        labels: months,
        datasets: [{
        label: 'Total Sales',
        data: totalSales,
        backgroundColor: 'rgba(54, 162, 235, 0.2)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1

    }, {
        label: 'Number of Bookings',
        data: numberOfBookings,
        backgroundColor: 'rgba(255, 99, 132, 0.2)',
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: 1,
        yAxisID: 'y-axis-1' // Assign this dataset to secondary y-axis
    }]
    },
        options: {
            scales: {
                y: {
                    type: 'linear',
                    display: true,
                    position: 'left',
                    beginAtZero: true,
                    grid: {
                        drawOnChartArea: false
                    }
                },
                y1: {
                    type: 'linear',
                    display: true,
                    position: 'right',
                    beginAtZero: true,
                    grid: {
                        drawOnChartArea: false
                    },

                    ticks: {
                        callback: function (value) {
                            return Number(value).toLocaleString();
                        }
                    }
                }
            }
        }
    });
    }
    });

        function getMonthName(monthIndex) {
        const monthNames = [
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
        ];
        return monthNames[monthIndex - 1];
    }
    });



</script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</body>
</html>
