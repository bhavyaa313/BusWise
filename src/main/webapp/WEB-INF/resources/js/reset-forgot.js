$.validator.addMethod("regex", function (value, element, regexp) {
    var re = new RegExp(regexp);
    return this.optional(element) || re.test(value);
}, "Please check your input.");

$("#resetForm").validate({

    errorClass: "error",
    errorElement: "label",

    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message');
    },

    rules: {

        password: {
            required: true,
            minlength:8,
            maxlength: 25,
            regex: /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>]).{8,}$/
        },

        confirmPassword: {
            required: true,
            minlength:8,
            maxlength: 25,
            regex: /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>]).{8,}$/
        },



    },

    messages: {

        password: {
            required: " Please enter a password",
            minlength:
                " Password should contain atleast 8 characters",
            maxlength: "password should not contain more than 25 characters ",
            regex: "Please enter password with atleast one number and one uppercase character and exactly one special character",
        },

        confirmPassword: {
            required: " Please enter a password",
            minlength:
                " Password should contain atleast 8 characters",
            maxlength: "password should not contain more than 25 characters ",
            regex: "Please enter password with atleast one number and one uppercase character and exactly one special character",
        },




    }
});


$("#forgotForm").validate({

    errorClass: "error",
    errorElement: "label",

    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message');
    },

    rules: {

        email: {
            required: true,
            email: true,
            regex: /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
        },



    },

    messages: {


        email: {
            required: " Please enter an email address",
            email: "Please enter a valid email address.",
            regex: "Please enter a valid email address."
        },



    }
});





