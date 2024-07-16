<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 20-06-2024
  Time: 15:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>E-Ticket</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-light">
<div class="container mt-5 bg-light" >
    <div class="text-center mb-4">
        <img src="<c:url value="/resources/image/logo.png" />" alt="Company Logo" class="img-fluid mb-3" style="height: 5vh;">

        <p><strong>Website:</strong> www.BusWise.com</p>
        <p><strong>Phone:</strong> 9429734667</p>
        <p><strong>Email:</strong> bhavyashah313@gmail.com</p>
        <h3 class="mt-4">Thank you for booking!</h3>
    </div>
    <h3 class="mx-2" >E-Ticket</h3>

    <div class="card">
        <div class="card-body">

            <div class="row">
                <!-- Passenger Information -->
                <div class="col-md-6 mb-3">
                    <h5 class="card-title text-center bg-light py-2">Passenger Information</h5>
                    <table class="table table-bordered">
                        <tbody>
                        <tr>
                            <td><strong>Name:</strong></td>
                            <td>${t.name}</td>
                        </tr>
                        <tr>
                            <td><strong>Age:</strong></td>
                            <td>${t.age}</td>
                        </tr>
                        <tr>
                            <td><strong>Gender:</strong></td>
                            <td>${t.gender}</td>
                        </tr>
                        <tr>
                            <td><strong>Phone:</strong></td>
                            <td>${t.phone}</td>
                        </tr>
                        <tr>
                            <td><strong>Email:</strong></td>
                            <td>${t.email}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <!-- Journey Details -->
                <div class="col-md-6 mb-3">
                    <h5 class="card-title text-center bg-light py-2">Journey Details</h5>
                    <table class="table table-bordered">
                        <tbody>
                        <tr>
                            <td><strong>Departure:</strong></td>
                            <td>${t.source}, ${t.dateTime}</td>
                        </tr>
                        <tr>
                            <td><strong>Arrival:</strong></td>
                            <td>${t.destination}</td>
                        </tr>
                        <tr>
                            <td><strong>Bus Number:</strong></td>
                            <td>${t.busNumber}</td>
                        </tr>
                        <tr>
                            <td><strong>Seat Number(s):</strong></td>
                            <td class="text-danger fw-bold">${t.seats}</td>
                        </tr>
                        <tr>
                            <td><strong>Route:</strong></td>
                            <td>${t.route}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="text-center mt-4">
        <p class="d-flex"><span class="ms-auto  "><button class="btn btn-danger btn-lg" id="ticket">Download</button></span></p>
        <div id="alertContainer" class="d-flex ms-auto align-items-end justify-content-end"></div>
        <p><small>Terms and Conditions apply. Please carry a valid ID proof during the journey.</small></p>
        <p><small>Your Ticket have been sent to your email!</small></p>
        <p class="d-flex"><span class="ms-auto  "><a class="btn btn-outline-danger btn-lg" href="${pageContext.request.contextPath}/admin/dashboard/${userId}" id="backToHome">Go to HomePage</a></span></p>
    </div>
</div>


<script
        src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

<script>
    $(document).ready(function() {
        console.log("cxome")
        $('#ticket').click(function() {

            $.ajax({
                url: '${pageContext.request.contextPath}/ticketDownlaod/${id}',
                type: 'GET',

                success: function(response) {
                    console.log("succes")
                    var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`
                    alert += `<strong>Success! </strong>`;
                    alert+=`Ticket Downloaded Successfully!`;
                    alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                    alert+=`</div>`;
                    console.log(alert)
                    $('#alertContainer').append(alert);

                },
                error: function() {
                    console.log("error")

                }
            });
        });
    });
</script>
</body>
</html>
