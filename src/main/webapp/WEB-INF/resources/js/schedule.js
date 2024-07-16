$.validator.addMethod("regex", function (value, element, regexp) {
    var re = new RegExp(regexp);
    return this.optional(element) || re.test(value);
}, "Please check your input.");

$.validator.addMethod("minDate", function(value, element) {
    var today = new Date().getTimezoneOffset() * 60000; // Convert offset to milliseconds
    var inputDate = new Date(value).getTime();
    return inputDate >= today;
}, "Please enter a date on or after today");

$("#myFormAdd").validate({



    errorClass: "error",
    errorElement: "label",


    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message1');
    },



    rules: {

        date1:{
            required: true,
            dateISO: true,
            minDate: true

        },

        time1:{
            required: true,

        },

        route:{
            required: true,
        },

        bus:{
            required:true,
        },

        fair:{
            required: true,
        },

        duration:{
            required: true,
        }





    },

    messages: {

        date1:{
            required: "Please select date",
            dateISO: "Use the built-in dateISO rule", // Use the built-in dateISO rule
            minDate: "invalid dATE"
        },

        time1:{
            required: "Please enter time oo",

        },
        route:{
            required: "Please select route",
        },

        bus:{
            required:"Please select  bus",
        },

        fair:{
            required: "Please enter fair",
        },

        duration:{
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

        date1:{
            required: true,
            dateISO: true,
            minDate: true

        },

        time1:{
            required: true,

        },

        route:{
            required: true,
        },

        bus:{
            required:true,
        },

        fair:{
            required: true,
        },

        duration:{
            required: true,
        }





    },

    messages: {

        date1:{
            required: "Please select date",
            dateISO: "Use the built-in dateISO rule", // Use the built-in dateISO rule
            minDate: "Please select valid datefdsgfdsfgfg"
        },

        time1:{
            required: "Please enter time",

        },
        route:{
            required: "Please select route",
        },

        bus:{
            required:"Please select  bus",
        },

        fair:{
            required: "Please enter fair",
        },

        duration:{
            required: "Please enter duration",
        }



    }
});



