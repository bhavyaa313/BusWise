$.validator.addMethod("regex", function (value, element, regexp) {
    var re = new RegExp(regexp);
    return this.optional(element) || re.test(value);
}, "Please check your input.");

$(".Form").validate({

    errorClass: "error",
    errorElement: "label",

    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message');
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
            maxlength: 25
        },

        confirmPassword: {
            required: true,
            minlength:8,
            maxlength: 25
        },

        busNumber:{
            required: true,
            minlength: 5,
            maxlength:12
        },

        seatingCapacity:{
            required: true,
            min: 20,
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
                " Your phone number must be consist of 10 numbers"
        },
        password: {
            required: " Please enter a password",
            minlength:
                " Password should contain atleast 8 characters",
            maxlength: "password should not contain more than 25 characters "
        },

        confirmPassword: {
            required: " Please enter a password",
            minlength:
                " Password should contain atleast 8 characters",
            maxlength: "password should not contain more than 25 characters "
        },

        busNumber:{
            required: "Please enter a Bus Number",
            minlength: "Bus Number should contain atleast 5 characters ",
            maxlength: "Bus Number should not contain more than 12 characters "
        },

        seatingCapacity:{
            required: "Please enter a seating capacity",
            min: "Seating capacity should be more than 20",
        }


    }
});