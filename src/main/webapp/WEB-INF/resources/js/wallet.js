$("#addFundsForm").validate({


    errorClass: "error",
    errorElement: "label",

    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message1');
    },

    rules: {

        amount: {
            required: true,

        },

    },

    messages: {

        amount: {
            required: "Please enter a Bus Amount",

        },



    }
});

$("#withdrawFundsForm").validate({


    errorClass: "error",
    errorElement: "label",

    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message1');
    },

    rules: {

        amount2: {
            required: true,

        },

    },

    messages: {

        amount2: {
            required: "Please enter a Bus Amount",

        },



    }
});