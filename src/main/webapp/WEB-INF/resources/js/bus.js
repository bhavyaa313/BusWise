
$.validator.addMethod("regex", function (value, element, regexp) {
    var re = new RegExp(regexp);
    return this.optional(element) || re.test(value);
}, "Please check your input.");
$("#myFormAdd").validate({


    errorClass: "error",
    errorElement: "label",

    errorPlacement: function (error, element) {
        error.appendTo(element.parent()).addClass('error-message1');
    },

    rules: {

        busNumber: {
            required: true,
            minlength: 5,
            maxlength: 11,
            regex : /^[A-Z]{1,2}\d{2}[A-Z]{1,2}\d{1,4}$/,
        },

        seatingCapacity: {
            required: true,
            min: 20,
        }

    },

    messages: {

        busNumber: {
            required: "Please enter a Bus Number",
            minlength: "Bus Number should contain atleast 5 characters ",
            maxlength: "Bus Number should not contain more than 12 characters ",
            regex: "Please enter valid Bus Number"
        },

        seatingCapacity: {
            required: "Please enter a seating capacity",
            min: "Seating capacity should be more than 20",
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

        busNumber: {
            required: true,
            minlength: 5,
            maxlength: 12
        },

        seatingCapacity: {
            required: true,
            min: 20,
        }

    },

    messages: {

        busNumber: {
            required: "Please enter a Bus Number",
            minlength: "Bus Number should contain atleast 5 characters ",
            maxlength: "Bus Number should not contain more than 12 characters "
        },

        seatingCapacity: {
            required: "Please enter a seating capacity",
            min: "Seating capacity should be more than 20",
        }


    }
});



$(document).ready(function() {


     userId = $('#userId').val()
    console.log(userId)
})
function checkAndUpdateBus(busId, busNumber, busType, seatingCapacity) {
    $.ajax({
        url: 'checkBusUsage/' + busId + '/' + userId,
        type: "GET",
        success: function (response) {
            if (response.isUsed) {
                var alert = `<div class="alert alert-warning alert-dismissible fade show" role="alert">`;
                alert += `<strong>Error! </strong>`;
                alert += `Bus is used in schedules and cannot be edited.`;
                alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert += `</div>`;
                $('#alertContainerDelete').append(alert);
            } else {
                updateBus(busId, busNumber, busType, seatingCapacity);

                // Trigger the modal
                $('#edit').modal('show');
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
            alert += `<strong>Error! </strong>`;
            alert += `An error occurred while checking bus usage.`;
            alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
            alert += `</div>`;
            $('#alertContainerDelete').append(alert);
        }
    });
}

function updateBus(busId, busNumber, busType, seatingCapacity) {
    $('#bid').attr('value', busId);
    $('#bn').attr('value', busNumber);
    $('#bt').attr('value', busType);
    $('#bc').attr('value', seatingCapacity);
}



function init(totalFinal, curPage) {

    Pagination.Init(document.getElementById('pagination'), {
        size: parseInt(totalFinal, 10), // pages size
        page: parseInt(curPage, 10), // selected page
        step: 1
        // pages before and after current
    });
};

    var Pagination = {

    code: '',

    // --------------------
    // Utility
    // --------------------

    // converting initialize data
    Extend: function(data) {
    data = data || {};
    Pagination.size = data.size || 0;
    Pagination.page = data.page || 1;
    Pagination.step = data.step || 3;
},

    // add pages by number (from [s] to [f])
    Add: function(s, f) {
    for (var i = s; i < f; i++) {
    Pagination.code += '<a>' + i + '</a>';
}
},

    // add last page with separator
    Last: function() {
    Pagination.code += '<i>...</i><a>' + Pagination.size + '</a>';
},

    // add first page with separator
    First: function() {
    Pagination.code += '<a>1</a><i>...</i>';
},

    // --------------------
    // Handlers
    // --------------------

    // change page
    Click: function() {
    Pagination.page = +this.innerHTML;
    Pagination.Start();
},

    // previous page
    Prev: function() {
    Pagination.page--;
    if (Pagination.page < 1) {
    Pagination.page = 1;
}
    Pagination.Start();
},

    // next page
    Next: function() {
    Pagination.page++;
    if (Pagination.page > Pagination.size) {
    Pagination.page = Pagination.size;
}
    Pagination.Start();
},

    // --------------------
    // Script
    // --------------------

    // binding pages
    Bind: function() {
    var a = Pagination.e.getElementsByTagName('a');
    for (var i = 0; i < a.length; i++) {
    if (+a[i].innerHTML === Pagination.page)
    a[i].className = 'current';
    a[i].addEventListener('click', Pagination.Click, false);
}
},

    // write pagination
    Finish: function() {
    Pagination.e.innerHTML = Pagination.code;
    Pagination.code = '';
    Pagination.Bind();
},

    // find pagination type
    Start: function() {
    if (Pagination.size < Pagination.step * 2 + 6) {
    Pagination.Add(1, Pagination.size + 1);
} else if (Pagination.page < Pagination.step * 2 + 1) {
    Pagination.Add(1, Pagination.step * 2 + 4);
    Pagination.Last();
} else if (Pagination.page > Pagination.size - Pagination.step
    * 2) {
    Pagination.First();
    Pagination.Add(Pagination.size - Pagination.step * 2 - 2,
    Pagination.size + 1);
} else {
    Pagination.First();
    Pagination.Add(Pagination.page - Pagination.step,
    Pagination.page + Pagination.step + 1);
    Pagination.Last();
}
    Pagination.Finish();
},

    // --------------------
    // Initialization
    // --------------------

    // binding buttons
    Buttons: function(e) {
    var nav = e.getElementsByTagName('a');
    nav[0].addEventListener('click', Pagination.Prev, false);
    nav[1].addEventListener('click', Pagination.Next, false);
},

    // create skeleton
    Create: function(e) {

    var html = ['<a>&#9668;</a>', // previous button
    '<span></span>', // pagination container
    '<a>&#9658;</a>' // next button
    ];

    e.innerHTML = html.join('');
    Pagination.e = e.getElementsByTagName('span')[0];
    Pagination.Buttons(e);
},

    // init
    Init: function(e, data) {
    Pagination.Extend(data);
    Pagination.Create(e);
    Pagination.Start();
}
};




    function disableSaveButton() {
    // Get all elements with class "is-invalid"
    var invalidElements = document.querySelectorAll(".is-invalid");

    // Check if any element has the class "is-invalid"
    var hasInvalid = invalidElements.length > 0;

    // Get the save button element (assuming it has an ID)
    var saveButton = document.getElementById("saveButton");

    // Disable or enable the save button based on the presence of invalid elements
    if (saveButton) {
    saveButton.disabled = hasInvalid;
}
}


    // Call the function initially to check for existing invalid elements
    disableSaveButton();

    // Attach the function to be called whenever the class "is-invalid" might be added or removed
    document.addEventListener("DOMSubtreeModified", disableSaveButton);

    $(document).ready(function() {
    $("#busNumber").blur(function() {
        var busNumber = $(this).val();

        // Make AJAX request only if the bus number has a certain minimum length (optional)
        if (busNumber.length >= 2) {  // Adjust the minimum length as needed
            $("#busNumber").removeClass("is-invalid"); // Clear previous errors
            $("#busNumberError").text("");

            $.ajax({
                url:  'checkBusNumber/'+userId, // Replace with your actual URL endpoint
                type: "GET",
                data: { busNumber: busNumber },
                success: function(response) {
                    if (response.exists) {
                        $("#busNumber").addClass("is-invalid");
                        $("#busNumberError").text("Bus number already exists!");

                    }
                    else if (busNumber.length<4)
                    {
                        // Adjust the minimum length as needed
                        $("#busNumber").addClass("is-invalid"); // Clear previous errors
                        $("#busNumberError").text("Invalid Bus number");
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error("Error checking bus number:", textStatus, errorThrown);
                    // Handle AJAX errors gracefully (e.g., display a generic error message)
                }
            });
        }
    });
});


    function deletebutton(busId) {
    $.ajax({
        url: 'checkBusUsage/'+busId  +'/'+ userId,
        type: "GET",
        success: function (response) {
            if (response.isUsed) {
                var alert = `<div class="alert alert-warning alert-dismissible fade show" role="alert">`;
                alert += `<strong>Error! </strong>`;
                alert += `Bus is used in schedules and cannot be deleted.`;
                alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                alert += `</div>`;
                $('#alertContainerDelete').append(alert);
            } else {
                $.ajax({
                    url: 'deleteBus/' + busId + "/" + userId,
                    type: "POST",
                    success: function(response) {
                        ajaxCallSearch();
                        var alert = `<div class="alert alert-success alert-dismissible fade show" role="alert">`;
                        alert += `<strong>Success! </strong>`;
                        alert += `Bus has been deleted successfully.`;
                        alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                        alert += `</div>`;
                        $('#alertContainerDelete').append(alert);
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
                        alert += `<strong>Error! </strong>`;
                        alert += `An error occurred while deleting the bus.`;
                        alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
                        alert += `</div>`;
                        $('#alertContainerDelete').append(alert);
                    }
                });
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`;
            alert += `<strong>Error! </strong>`;
            alert += `An error occurred while checking bus usage.`;
            alert += `<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
            alert += `</div>`;
            $('#alertContainerDelete').append(alert);
        }
    });
}

    $(document).ready(function() {
    console.log("in")
    ajaxCallSearch();
});



    debugger
    function  ajaxCallSearch(){
    console.log("mee")


debugger
    var busNumber = $("#searchBusNumber").val();
    console.log(busNumber)

    if(busNumber=="")
{
    busNumber= "empty";
}


    var curPage = $("#currentPage").attr("value");

    $.ajax({


    url:   'ajaxforSearchBus/'+ userId,
    type: "post",
    data: {
    busNumber: busNumber,
    curPage: curPage
},
    success: function (data) {

    console.log(data)


    $("#tableData").empty();

    if (data.length === 0) {
    $("#pagination").hide();
    $("#myTable").hide();


    $('#container-data-0').empty();
    var nodata =`<div class="container nodataC mt-5">
    <div id="nodata" class=" d-flex align-items-center justify-content-center flex-column">
        <dotlottie-player src="https://lottie.host/c5d27b13-2786-4e34-8aca-ddf227ca5161/oNJTuK3K7k.json" background="transparent" speed="1" style="width: 300px; height: 300px;" loop autoplay></dotlottie-player>

        <div> <h3 class="text-dark">No buses available!</h3></div>
    </div>
</div>`
    $('#container-data-0').append(nodata);
}


    else {
    $("#pagination").show();
    $("#myTable").show();// Show pagination if data exists
    $('#container-data-0').empty();
}
    for (let i = 0; i < data.length; i++) {

    var buses = data[i];
    console.log(buses+"-------------------------------------")

    var row = `<tr class="w-100">

							<td>` + buses.busId + `</td>
							<td>` + buses.busNumber + `</td>
							<td>` + buses.busType + `</td>
							<td>` + buses.seatingCapacity + `</td>
							<td>` + buses.modifiedDate + `</td>
                            <td class="justify-content-end d-flex">

                                <button type="button" class="btn btn-dark" style="width: 18vh"
        onclick="checkAndUpdateBus('`+buses.busId+`', '`+buses.busNumber+`', '`+buses.busType+`', '`+buses.seatingCapacity+`')">
    Edit
</button>

                                <button type="button" onclick="deletebutton(`+buses.busId+`)" class="btn btn-secondary mx-3 " style="width: 18vh">
                                  Delete</button>
                            </td>

						</tr>`

    $("#tableData").append(row);
    var temp = buses.count;
    var totalFinal = Math.ceil(temp / 5);
    init(totalFinal, curPage);

}

}

})

}

    function clearFormData() {
    // Get the form element by its ID or using another selector
    var form = document.getElementById("myFormAdd");
    $("#busNumber").removeClass("is-invalid");
    form.classList.remove("error");
    // Reset the form (clears most elements)
    form.reset();


}


    $('#add').on('hidden.bs.modal', function() {
    $('#myFormAdd')[0].reset();
});
    $('#edit').on('hidden.bs.modal', function() {
    $('#myFormAdd')[0].reset();
});


    $("#myFormAdd").submit(function(event) {
        event.preventDefault(); // Prevent default form submission
        if ($("#myFormAdd").valid()) {
            // Get form data using Spring form tag IDs
            var busNumber = $("#busNumber").val();
            var busType = $("#busType").val();
            var seatingCapacity = $("#seatingCapacity").val();



            $.ajax({
                url:  'addBus/'+userId,
                type: "POST",
                data: {
                    busNumber: busNumber,
                    busType: busType,
                    seatingCapacity: seatingCapacity
                },

                success: function(response) {
                    console.log("Form submitted successfully:", response);

                    var form = document.getElementById("myFormAdd");
                    form.reset();
                    $(".btn-close").click();
                    ajaxCallSearch()



                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error("Error submitting form:", textStatus, errorThrown);

                }
            });
        }

    });


    $(document).ready(function() {
    $("#myFormEdit").submit(function(event) {
        $('#bn').attr('disabled', false);

        event.preventDefault(); // Prevent default form submission
        if ($("#myFormEdit").valid()) {
            // Get form data using Spring form tag IDs
            var busNumber = $("#bn").val();
            var busType = $("#bt").val();
            var seatingCapacity = $("#bc").val();
            var id = $("#bid").val();



            $.ajax({
                url:  'editBus/'+userId,
                type: "POST",
                data: {
                    busNumber: busNumber,
                    busType: busType,
                    seatingCapacity: seatingCapacity,
                    id:id
                },

                success: function(response) {
                    console.log("Form submitted successfully:", response);

                    var form = document.getElementById("myFormEdit");
                    form.reset();
                    $(".btn-close").click();
                    ajaxCallSearch()



                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error("Error submitting form:", textStatus, errorThrown);

                }
            });
        }

    });

        $("#searchBusNumber").on('keyup', ajaxCallSearch);
        $("#pagination").on("click", function() {
            console.log("page clicked")
            var current = $(".current").text();
            $("#currentPage").attr("value" , current);
            console.log(current)
            ajaxCallSearch();


        })
});
