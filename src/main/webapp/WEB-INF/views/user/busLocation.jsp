<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 09-07-2024
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="<c:url value="/resources/image/logo.png"/>"/>
    <title>Bus locations</title>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDDCIb4xyEV8ok30VlxsidKGHw1NAlrfFM&libraries=places"></script>
    <script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>

    <style>
        #map {
            height: 800px;
            width: 100%;
        }
    </style>
</head>
<body onload="fetchBusLocations()" class="bg-dark">
<jsp:include page="userNavbar.jsp"></jsp:include>



<div class="container mt-5 " id="maps">

    <div id="map"></div>

</div>

<div  id="container-nodata">

    <script>
    let map;
    let directionsService;
    let directionsRenderers = [];
    let busMarkers = [];
    let intervals = [];
    let routes = [];
    const speed = 50 * 1000 / 3600;

    function fetchBusLocations() {


        console.log("Fetching bus locations...");
        fetch('${pageContext.request.contextPath}/locations/${userId}')
            .then(response => response.json())
            .then(data => {
                console.log('Bus location data:', data);
                const today = new Date();
                const todaysSchedules = data.filter(bus => new Date(bus.date).toDateString() === today.toDateString());
                if (todaysSchedules.length > 0) {
                    initMap(todaysSchedules);
                } else {
                    $('#maps').empty()
                    var nodata =`<div class="container nodataC mt-5">
    <div id="nodata" class=" d-flex align-items-center justify-content-center flex-column">
        <dotlottie-player src="https://lottie.host/c5d27b13-2786-4e34-8aca-ddf227ca5161/oNJTuK3K7k.json" background="transparent" speed="1" style="width: 300px; height: 300px;" loop autoplay></dotlottie-player>

        <div> <h3 class="text-white">No schedules now!</h3></div>
    </div>
</div>`
                    $('#container-nodata').append(nodata);

                    console.log("No bus schedules today")



                }
            })
            .catch(error => console.error('Error fetching bus locations:', error));
    }

    function initMap(busData) {
        debugger
        console.log("Initializing map...");
        map = new google.maps.Map(document.getElementById('map'), {
            zoom: 7,
            center: {lat: 21.7645, lng: 72.1519} // Center map to Bhavnagar
        });

        directionsService = new google.maps.DirectionsService();

        busData.forEach((bus, index) => {
            directionsRenderers[index] = new google.maps.DirectionsRenderer({
                map: map,
                polylineOptions: { strokeColor: index === 0 ? "blue" : "red" }
            });
            debugger
            const startTimeStr = bus.time;
            const [hours, minutes] = startTimeStr.split(':');
            const startTime = new Date();
            startTime.setHours(hours, minutes, 0);
            console.log(startTime + "starttmee");
            calculateAndDisplayRoute(bus, index, startTime);
        });
    }

    function calculateAndDisplayRoute(routeData, index, startTime) {
        console.log("Calculating and displaying route...", index, routeData.source, routeData.destination);
        var request = {
            origin: routeData.source,
            destination: routeData.destination,

            travelMode: 'DRIVING'
        };

        directionsService.route(request, function(result, status) {
            if (status == 'OK') {
                directionsRenderers[index].setDirections(result);
                routes[index] = result.routes[0].overview_path;
                console.log("Route:", routes[index]); // Log the calculated route
                //
                // markSubroutes(result.routes[0].legs[0].steps);
                calculateTravelTime(result.routes[0].legs[0].distance.value, index, startTime, routeData); // Distance in meters
            } else {
                console.error("Directions request failed due to " + status);
            }
        });




    }

    function markSubroutes(steps) {
        steps.forEach(step => {
            const marker = new google.maps.Marker({
                position: step.start_location,
                map: map,
                icon: {
                    url: "http://maps.google.com/mapfiles/ms/micons/cabs.png", // Adjust icon as needed
                    scaledSize: new google.maps.Size(25, 25)
                }
            });
        });
    }

    function calculateTravelTime(distance, index, startTime, busData) {
        console.log("Calculating travel time...", index);
        const totalTime = distance / speed; // Total travel time in seconds
        const numSteps = routes[index].length;
        const stepTime = totalTime / numSteps; // Time per step in seconds

        console.log("Total distance:", distance, "meters");
        console.log("Total travel time:", totalTime, "seconds");
        console.log("Number of steps:", numSteps);
        console.log("Time per step:", stepTime, "seconds");

        placeBusMarker(stepTime * 1000, index, startTime, busData); // Convert to milliseconds
    }

    function placeBusMarker(stepTime, index, startTime, busData) {
        console.log("Placing bus marker...", index);
        busMarkers[index] = new google.maps.Marker({
            position: routes[index][0],
            map: map,
            icon: {
                url: "http://maps.google.com/mapfiles/ms/icons/bus.png",
                scaledSize: new google.maps.Size(50, 50)
            }
        });


        moveBus(stepTime, index, startTime);
    }

    function moveBus(stepTime, index, startTime) {
        debugger
        console.log("Moving bus...", index);
        const currentTime = new Date();
        console.log(startTime , +"starttime")
        console.log(currentTime, +"currenttime");
        let initialStep = 0;

        // Calculate the initial step based on elapsed time if startTime is in the past
        if (currentTime > startTime) {

            const elapsedTimeInSeconds = (currentTime.getTime() - startTime.getTime()) / 1000;
            initialStep = Math.floor(elapsedTimeInSeconds / (stepTime / 1000));
            console.log("Bus started at elapsed time:", elapsedTimeInSeconds, "seconds");
        }

        // Move the bus from the initial step onwards
        let step = initialStep;

        if (step < routes[index].length) {
            busMarkers[index].setPosition(routes[index][step]);
            map.panTo(routes[index][step]); // Optionally, pan the map to follow the bus
            console.log("Bus position:", routes[index][step]);
        }

        intervals[index] = setInterval(function() {
            if (step >= routes[index].length - 1) {
                clearInterval(intervals[index]); // Stop the movement when the bus reaches the destination
                console.log("Bus has reached the destination.");
                $('#map').hide()
                $('#container-nodata').empty();
                var nodata =`<div class="container nodataC mt-5">
    <div id="nodata" class=" d-flex align-items-center justify-content-center flex-column">
        <dotlottie-player src="https://lottie.host/c5d27b13-2786-4e34-8aca-ddf227ca5161/oNJTuK3K7k.json" background="transparent" speed="1" style="width: 300px; height: 300px;" loop autoplay></dotlottie-player>

        <div> <h3 class="text-white">No schedules now!</h3></div>
    </div>
</div>`
                $('#container-nodata').append(nodata);
                directionsRenderers[index].setMap(null);


                // busMarkers[index].setIcon({
                //     url: "http://maps.google.com/mapfiles/ms/icons/busstop.png", // Example icon for destination
                //     scaledSize: new google.maps.Size(50, 50)
                // });
                // return;
            }

            step += 1;
            busMarkers[index].setPosition(routes[index][step]);
            map.panTo(routes[index][step]); // Optionally, pan the map to follow the bus
            console.log("Bus position:", routes[index][step]);
        }, stepTime);
    }

</script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</body>
</html>