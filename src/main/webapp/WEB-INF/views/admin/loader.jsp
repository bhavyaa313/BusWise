<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 17-06-2024
  Time: 18:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        .loader {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(255, 255, 255, 0.8);
            display: none; /* Initially hidden */
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }
    </style>
</head>
<body>
<div class="loader" id="loader">
    <iframe src="https://lottie.host/embed/fbcdcd87-68b0-47ad-b53d-9bacf37574a8/36BXkNFmgV.json" width="300" height="300" style="border:none;overflow:hidden;"></iframe>
</div>



<script>
    // Function to show loader
    debugger
    function showLoader() {

        document.getElementById('loader').style.display = 'flex';
    }

    function hideLoader() {
        document.getElementById('loader').style.display = 'none';
    }


    document.getElementById('searchForm').addEventListener('submit', function(event) {
        event.preventDefault();

        if ($('#searchForm').valid()) {
            showLoader();

            // Set the loader to display for a specific time (e.g., 2 seconds) before submitting the form
            setTimeout(() => {
                history.pushState({ formSubmitted: true }, '', '');
                $('#searchForm')[0].submit(); // Ensure correct form is submitted
            }, 2000);
        }
    });

    // Hide loader after page load (useful for initial load)
    window.addEventListener('load', function() {
        hideLoader();
    });

    debugger
    window.onpopstate = function(event) {
        if (event.state && event.state.formSubmitted) {
            hideLoader(); // Hide the loader when navigating back
        }
    };
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
