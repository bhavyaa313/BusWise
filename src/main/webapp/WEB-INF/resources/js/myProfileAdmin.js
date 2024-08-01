function show5() {

    console.log("clicked")
    $('.hide1').removeClass("hide1");
    $('.random1').addClass("hide1");
    $('.enable1').removeAttr("disabled");
    $('.come1').removeAttr("hidden");
    $('.xx1').attr("hidden", "hidden");

}

function show6() {
    $('.hide1').removeClass("hide1");
    $('.see1').addClass("hide1");
    $('.enable1').attr("disabled", "disabled");
    $('.come1').attr("hidden", "hidden");
    $('.xx1').removeAttr("hidden");
    $('.error-message1').remove();
    $('#myProfileForm')[0].reset();
}

function show7() {

    if ($('#myProfileForm').valid()) {
        $('.hide1').removeClass("hide1");
        $('.see1').addClass("hide1");
        $('.enable1').attr("disabled", "disabled");
        $('.come1').attr("hidden", "hidden");
        $('.xx1').removeAttr("hidden");
    }

}

$(document).ready(function() {
    userId = $('#userId').val()
    console.log(userId)
    debugger
    $("#myProfileForm").submit(function(event) {
        /*  $('#routeSelect2').attr('disabled', false);
            $('#date2').attr('disabled', false);
            $('#fare2').attr('disabled', false);
            $('#duration2').attr('disabled', false);*/
        $(':disabled').each(function (e) {
            $(this).removeAttr('disabled');
        });
        debugger;
        event.preventDefault(); // Prevent default form submission
        if ($("#myProfileForm").valid()) {
            // Get form data using Spring form tag IDs
            var firstName = $("#fname").val();
            var lastName = $("#lname").val();
            var email = $("#email").val();
            var phone = $("#phone").val();
            var age = $("#age").val();
            var city = $("#city").val();
            var state = $("#state").val();

            $.ajax({
                url: "editProfile/" + userId,
                type: "POST",
                data: {
                    firstName: firstName,
                    lastName: lastName,
                    email: email,
                    phone: phone,
                    age: age,
                    city: city,
                    state: state
                },
                success: function(response) {
                    console.log("Form submitted successfully:", response);

                    $('#phone').prop('disabled', true);
                    $('#email').prop('disabled', true);
                    $('#city').prop('disabled', true);
                    $('#state').prop('disabled', true);
                    $('#fname').prop('disabled', true);
                    $('#lname').prop('disabled', true);
                    $('#age').prop('disabled', true);


                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error("Error submitting form:", textStatus, errorThrown);
                }
            });
        }
    });
});

