console.log("csss")
$.validator.addMethod("regex", function (value, element, regexp) {
    var re = new RegExp(regexp);
    return this.optional(element) || re.test(value);
}, "Please check your input.");

$(".Form").validate({

    errorClass: "error",
    errorElement: "label",

    errorPlacement: function (error, element) {
        var errorMessage = error.text();
        error.appendTo(element.parent()).addClass('error-message').attr('data-full-message', errorMessage);

    },

    rules: {
        firstName: "required",
        lastName: "required",
        email: {
            required: true,
            email: true,
            regex: /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
        },
        phone: {
            required: true,
            regex: "^\\d{10}$",
            minlength: 10,
            maxlength: 10
        },
        password: {
            required: true,
            minlength:8,
            maxlength: 25,
            regex: /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>]).{8,}$/
        },
        password1: {
            required: true,
            minlength:8,
            maxlength: 25,

        },

        confirmPassword: {
            required: true,
            minlength:8,
            maxlength: 25,
            regex:/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*()\-_=+{};:,<.>]).{8,}$/
        },



        city:{
            regex: /^[A-Za-z]+$/
        },

        state:{
            regex: /^[A-Za-z]+$/

        }




    },

    messages: {
        firstName: {
            required: " Please enter a firstName"
        },
        lastName: {
            required: " Please enter a lastName"
        },
        email: {
            required: " Please enter an email address",
            email: "Please enter a valid email address.",
            regex: "Please enter a valid email address."
        },
        phone: {
            required: " Please enter a phone number",
            regex: "Please enter a valid phone number",
            minlength:
                " Your phone number must be consist of 10 numbers",
            maxlength:
                " Your phone number must be consist of 10 numbers",

        },

        password: {
            required: " Please enter a password",
            minlength:
                " Password should contain atleast 8 characters",
            maxlength: "password should not contain more than 25 characters ",
            regex: "Please enter password with atleast one number and one uppercase character and exactly one special character",

        },
        password1: {
            required: " Please enter a password",
            minlength:
                " Password should contain atleast 8 characters",
            maxlength: "password should not contain more than 25 characters ",

        },

        confirmPassword: {
            required: " Please enter a password",
            minlength:
                " Password should contain atleast 8 characters",
            maxlength: "password should not contain more than 25 characters ",
            regex: "Please enter password with atleast one number and one uppercase character and exactly one special character"
        },



        city:{
            regex:"Please enter valid city",

        },

        state:{
            regex: "Please enter valid state",
        }


    }
});