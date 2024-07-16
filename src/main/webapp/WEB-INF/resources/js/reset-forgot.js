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
            maxlength: 25
        },

        confirmPassword: {
            required: true,
            minlength:8,
            maxlength: 25
        },



    },

    messages: {

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

$('#loader').hide();
$('#forgotForm').submit(function(event) {
    event.preventDefault();
    $('#loader').show();
    var email = $('#email').val();
    if($('#forgotForm').valid())
    {
        $.ajax({
            url: "${pageContext.request.contextPath}/successforget",
            type: "POST",
            data: {
                email:email
            },
            success: function(data) {
                console.log(data);
                if (data) {
                    $('#alertContainer').empty();
                    console.log("Reset link is sent to email.");
                    var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`
                    alert += `<strong>Success! </strong>`;
                    alert+=`Reset Link has been sent to this email successfully.`;
                    alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                    alert+=`</div>`;
                    console.log(alert)
                    $('#loader').hide();
                    $('#alertContainer').append(alert);

                } else {
                    console.log("This email id doesn't exist.");
                    $('#alertContainer').empty();
                    var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`
                    alert += `<strong>oops! </strong>`;
                    alert+=`This email does not exists, please sign-up.`;
                    alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                    alert+=`</div>`;
                    console.log(alert)
                    $('#loader').hide();
                    $('#alertContainer').append(alert);

                }
            },
            error: function() {
                console.log("Error submitting form.");
                $('#loader').hide();
            }
        });
    }
});


$('#confirmPasswordError').hide();
$('#loader').hide();
$('#resetForm').submit(function(event) {
    if ($('#resetForm').valid())
    {
        event.preventDefault();
        debugger
        $('#loader').show();


        // setTimeout(function() {
        //     $('#loader').hide();
        // }, 4000);
        var password = $('#password').val();
        var confirmPassword = $('#confirmPassword').val();
        var token = "${token}";

        if (password !== confirmPassword) {
            // $(this).addClass("is-invalid");
            console.log("passwords do not match")
            $("#confirmPasswordError").show();
            $('#confirmPassword').addClass("is-invalid");
        } else {
            // $(this).removeClass("is-invalid");
            $("#confirmPasswordError").hide()

            $.ajax({
                url: "${pageContext.request.contextPath}/resetForm",
                type: "POST",
                data: {
                    password:password,
                    token: token,

                },
                success: function(data) {
                    console.log(data);

                    console.log("Password changed!");
                    var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`
                    alert += `<strong>Success! </strong>`;
                    alert+=`Your Password has been changed successfully.`;
                    alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                    alert+=`</div>`;
                    console.log(alert)
                    $('#alertContainer').append(alert);
                    $('#loader').hide();

                },
                error: function() {
                    console.log("Error submitting form.");
                    $('#loader').hide();
                }
            });
        }

    }
});

