$.validator.addMethod("regex", function (value, element, regexp) {
    var re = new RegExp(regexp);
    return this.optional(element) || re.test(value);
}, "Please check your input.");

console.log("booking details")
$("#bookingDetails").validate({

    errorClass: "error",
    errorElement: "label",

    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message1');
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




    },

    messages: {

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





    }


});

$.validator.addClassRules("name", {
    required: true
});
$.validator.addClassRules("age", {
    required: true,

});

$("#bookingDetails").validate({
    errorClass: "error",
    errorElement: "div",
    messages: {
        firstName: "Please enter your first name",
        lastName: "Please enter your last name",
        email: "Please enter a valid email address",
        age: "Please enter your age in digits"
    },
    submitHandler: function(form) {
        alert('Form submitted successfully!');
        form.submit();
    }
});



