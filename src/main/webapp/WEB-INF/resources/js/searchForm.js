
$.validator.addMethod("minDate", function(value, element) {
    var today = new Date().getTimezoneOffset() * 60000; // Convert offset to milliseconds
    var inputDate = new Date(value).getTime();
    return inputDate >= today;
}, "Please enter a date on or after today");

$.validator.addMethod("differentFrom", function(value, element, param) {
    var sourceValue = $(param).val();
    return value !== sourceValue;
}, "Source and destination cannot be the same");


$("#searchForm").validate({



    errorClass: "error",
    errorElement: "label",

    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message1');
    },

    rules: {

        source:{
            required: true,
        },

        destination:{
            required:true,
            differentFrom: "#source"
        },

        date:{
            required:true,
            minDate: true
        },


    },

    messages: {
        source:{
            required: "Please Select Source!",
        },

        destination:{
            required:"Please Select Destination!",
        },

        date:{
            required:"Please Select Date!",
            minDate: "Please Select Valid Date!"
        },



    }
});

    $(document).ready(function() {
    debugger
        userId = $('#userId').val()
        console.log(userId)
    console.log("ajax to get source")
    const sourceSelect = $('.sourceSelect');
    const destinationSelect = $('.destinationSelect');


    debugger




    $.ajax({
    url: "getSources/" +userId,
    type: 'GET',

    success: function(data) {
    console.log(data)
    if (data.length > 0) {
    sourceSelect.empty();
    const defaultOption = $('<option>');
    defaultOption.val("");
    defaultOption.text("Select Source");
    sourceSelect.append(defaultOption);
    data.forEach(function(source) {
    const option = $('<option>');
    option.val(source);

    option.text(source);

    sourceSelect.append(option);

});
} else {

    console.log("No data found for routes");
}
    sourceSelect.prop('selectedIndex', 0);
},
    error: function(jqXHR, textStatus, errorThrown) {
    console.error("Error fetching routes:", textStatus, errorThrown);

}
});

    $.ajax({
    url: "getDestination/"+userId,
    type: 'GET',

    success: function(data) {
    console.log(data)
    if (data.length > 0) {
    destinationSelect.empty();
    const defaultOption = $('<option>');
    defaultOption.val("");
    defaultOption.text("Select destination");
    destinationSelect.append(defaultOption);
    data.forEach(function(des) {
    const option = $('<option>');
    option.val(des);

    option.text(des);

    destinationSelect.append(option);

});
} else {

    console.log("No data found for routes");
}
    destinationSelect.prop('selectedIndex', 0);
},
    error: function(jqXHR, textStatus, errorThrown) {
    console.error("Error fetching routes:", textStatus, errorThrown);

}
});
});







