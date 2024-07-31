$.validator.addMethod("regex", function (value, element, regexp) {
    var re = new RegExp(regexp);
    return this.optional(element) || re.test(value);
}, "Please check your input.");

$.validator.addMethod("minDate", function(value, element) {
    var today = new Date();
    today.setHours(0, 0, 0, 0); // Reset time to midnight
    var inputDate = new Date(value);
    inputDate.setHours(0, 0, 0, 0); // Reset time to midnight for comparison
    return inputDate >= today;
}, "Please enter a date on or after today");


$.validator.addMethod("minTime", function(value, element) {
    // Get the date field value
    var dateFieldName = "date1"; // Replace with your actual date field name
    var inputDateValue = $(element).closest('form').find(`[name="${dateFieldName}"]`).val();

    if (!inputDateValue) {
        return true; // If there's no date value, skip time validation
    }

    var inputDate = new Date(inputDateValue);
    var today = new Date();
    today.setHours(0, 0, 0, 0); // Reset time to midnight
    inputDate.setHours(0, 0, 0, 0); // Reset time to midnight for comparison

    // Only apply time validation if the input date is today
    if (inputDate.getTime() === today.getTime()) {
        var now = new Date();
        var currentHours = now.getHours();
        var currentMinutes = now.getMinutes();

        // Parse the input time value (assuming format is HH:MM)
        var timeParts = value.split(':');
        if (timeParts.length !== 2) return false; // Invalid format

        var inputHours = parseInt(timeParts[0], 10);
        var inputMinutes = parseInt(timeParts[1], 10);

        // Check if the input time is greater than or equal to the current time
        return (inputHours > currentHours) || (inputHours === currentHours && inputMinutes >= currentMinutes);
    }
    return true; // If the date is not today, skip time validation
}, "Please enter a time on or after the current time");

$("#myFormAdd").validate({
    errorClass: "error",
    errorElement: "label",
    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message1');
    },
    rules: {
        date1: {
            required: true,
            dateISO: true,
            minDate: true
        },
        time1: {
            required: true,
            minTime:true
        },
        route: {
            required: true,
        },
        bus: {
            required: true,
        },
        fair: {
            required: true,
        },
        duration: {
            required: true,
        }
    },
    messages: {
        date1: {
            required: "Please select date",
            dateISO: "Use the built-in dateISO rule", // Use the built-in dateISO rule
            minDate: "Please select a valid date"
        },
        time1: {
            required: "Please enter time",
        },
        route: {
            required: "Please select route",
        },
        bus: {
            required: "Please select bus",
        },
        fair: {
            required: "Please enter fare",
        },
        duration: {
            required: "Please enter duration",
        }
    }
});

$("#myFormEdit").validate({
    errorClass: "error",
    errorElement: "label",
    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message1');
    },
    rules: {
        date1: {
            required: true,
            dateISO: true,
            minDate: true
        },
        time1: {
            required: true,
        },
        route: {
            required: true,
        },
        bus: {
            required: true,
        },
        fair: {
            required: true,
        },
        duration: {
            required: true,
        }
    },
    messages: {
        date1: {
            required: "Please select date",
            dateISO: "Use the built-in dateISO rule", // Use the built-in dateISO rule
            minDate: "Please select a valid date"
        },
        time1: {
            required: "Please enter time",
        },
        route: {
            required: "Please select route",
        },
        bus: {
            required: "Please select bus",
        },
        fair: {
            required: "Please enter fare",
        },
        duration: {
            required: "Please enter duration",
        }
    },


});

function getTodayDate() {
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // Months are 0-based, so add 1
    const day = String(today.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}


const dp = document.getElementById('date');
dp.setAttribute('min', getTodayDate());


function getCurrentTime() {
    const now = new Date();
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    return `${hours}:${minutes}`;
}




