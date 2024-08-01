$.validator.addMethod("regex", function (value, element, regexp) {
    var re = new RegExp(regexp);
    return this.optional(element) || re.test(value);
}, "Please check your input.");

console.log("my profile")
$("#myProfileForm").validate({

    errorClass: "error",
    errorElement: "label",

    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message1');
    },

    rules: {
        firstName:
            {
                required:true,
                regex:/^[A-Za-z]+$/
            },
        lastName: {
            required:true,
            regex:/^[A-Za-z]+$/
        },
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
        city:{
            regex: /^[A-Za-z]+$/
        },

        state:{
            regex: /^[A-Za-z]+$/
        }




    },

    messages: {
        firstName: {
            required: "Please enter a firstName",
            regex: "Please enter valid name"
        },
        lastName: {
            required: "Please enter a lastName",
            regex: "Please enter valid name"
        },
        email: {
            required: "Please enter an email address",
            email: "Please enter a valid email address.",
            regex: "Please enter a valid email address."
        },
        phone: {
            required: "Please enter a phone number",
            regex: "Please enter a valid phone number",
            minlength:
                " Your phone number must be consist of 10 numbers",
            maxlength:
                " Your phone number must be consist of 10 numbers"
        },
        city:{
            regex:"Please enter valid city",

        },

        state:{
            regex: "Please enter valid state",
        }





    }
});