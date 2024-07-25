

console.log("yeesss routess")

// function validateDistanceSubroute(distance, distanceSubroute) {
//     if (distanceSubroute >= distance) {
//         return "Subroute distance cannot be greater than or equal to total distance.";
//     }
//     return null; // No error
// }

$("#myForm").validate({

    errorClass: "error",  // Customize error class for styling
    errorElement: "label",  // Use labels to display error messages

    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message1');  // Append error message to parent and add a class for styling
    },

    rules: {

        source: {
            required: true,

        },

        destination: {
            required: true,
        },

        distance: {
            required: true,
            min: 50,
        },

        subRoute: {
            required: true,
        },

        distanceSubroute: {
            required: true,

        }
    },

    messages: {
        source: {
            required: "Please enter Source",

        },

        destination: {
            required: "Please enter Destination"
        },

        distance: {
            required: "Please enter Distance",
            min: "Distance should be at least 50Km"
        },

        subrouteName: {
            required: "Please enter subroute"
        },

        subrouteDistance: {
            required: "Please enter distance"
        }
    }
});


$.validator.addClassRules("subrouteName", {
    required: true
});
$.validator.addClassRules("subrouteDistance", {
    required: true,

});





