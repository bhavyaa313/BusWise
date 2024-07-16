<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 10-07-2024
  Time: 17:43
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wallet</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>

        .error-message1{
            display: block;
            margin-top: 10px;
            font-size: 0.6em;
            color: red;
            word-spacing: normal;
        }


        .wallet-card {
            margin-top: 20px;
        }

        .wallet-balance {
            font-size: 2em;
            font-weight: bold;
            margin: 20px 0;
        }

        .transaction-table th,
        .transaction-table td {
            text-align: center;
        }

        .transaction-table {
            margin-bottom: 0;
        }

        .modal-header {
            background-color: #007bff;
            color: white;
        }

        .modal-footer {
            justify-content: center;
        }

        .loader-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.7); /* White background with opacity */
            z-index: 9999; /* Make sure it's on top of everything */
            display: none; /* Initially hidden */
            display: flex;
            justify-content: center;
            align-items: center;
            backdrop-filter: blur(5px); /* Blur the background */
        }

        #pagination {
            display: inline-block;
            vertical-align: middle;
            border-radius: 4px;
            padding: 1px 2px 4px 2px;
            border: 1px solid #DDDDDD; /* Light gray border */
            /* Light gray background */
        }

        #pagination a, #pagination i {
            display: inline-block;
            vertical-align: middle;
            width: 22px;
            color: #333333; /* Black text */
            text-align: center;
            font-size: 10px;
            padding: 3px 0 2px 0;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            -o-user-select: none;
            user-select: none;
        }

        #pagination a {
            margin: 0 2px 0 2px;
            border-radius: 4px;
            border: 1px solid #DDDDDD; /* Light gray border */
            cursor: pointer;
            /* Remove box-shadow for a cleaner look */
            text-shadow: none;
            background-color: #EEEEEE; /* Lighter gray background */
        }

        #pagination i {
            margin: 0 3px 0 3px;
        }

        #pagination a.current {
            border: 1px solid #AAAAAA; /* Slightly darker gray border */
            background-color: #DDDDDD; /* Light gray background */
        }



    </style>
</head>

<body class="bg-dark">
<jsp:include page="userNavbar.jsp"></jsp:include>
<div id="loader-overlay" class="loader-overlay">
    <div >    <dotlottie-player src="https://lottie.host/a6d838af-200b-4f9e-bcbb-7bbe0747abf6/MOVS0NZcAT.json"
                                background="transparent" speed="1" style="width: 300px; height: 300px;" loop
                                autoplay></dotlottie-player> </div>
</div>
<div class="container mt-5">
    <div class="row justify-content-center wallet-card">
        <div class="col-md-10">
            <div class="card">
                <div class="card-header bg-secondary text-white text-center h4">
                    My Wallet
                </div>
                <div class="card-body text-center">
                    <div id="balance-container">


                    <p class="wallet-balance">Balance: $<span id="wallet-balance"></span></p>
                    </div>
                    <button class="btn btn-outline-success btn-lg " data-bs-toggle="modal" data-bs-target="#addFundsModal">Add
                        Funds</button>
                    <button class="btn btn-outline-danger btn-lg " data-bs-toggle="modal"
                            data-bs-target="#withdrawFundsModal">Withdraw Funds</button>
                </div>
            </div>
        </div>
    </div>

    <div class="row justify-content-center mt-5">
        <div class="col-md-10">
            <div class="card">
                <div class="card-header bg-secondary text-white text-center h3">
                    Transaction History
                </div>
                <div class="card-body p-0">
                    <div id="container-data-0"> </div>
                    <table class="table table-bordered table-striped transaction-table" id="myTable">
                        <thead class="thead-dark">
                        <tr>
                            <th>Date</th>
                            <th>Description</th>
                            <th>Amount</th>
                            <th>Type</th>

<%--                            <th>Balance After</th>--%>
                        </tr>
                        </thead>
                        <tbody id="transaction-history" class="h7">

                        </tbody>
                    </table>
                </div>
            </div>
            <div class="d-flex justify-content-center">
                <div id="pagination" class="mb-5 mt-3"></div>
            </div>
            <input type="text" id="currentPage" value="1" hidden>
        </div>
    </div>
</div>




<div class="modal fade" id="addFundsModal" tabindex="-1" aria-labelledby="addFundsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-success">
                <h5 class="modal-title ">Add Funds</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="addFundsForm">
                <div class="modal-body">

                    <div class="form-group">
                        <label for="amount">Amount</label>
                        <input type="number" class="form-control" id="amount" name="amount" required>
                    </div>

                </div>
                <div class="modal-footer">

                    <button type="submit" class="btn btn-success btn-lg"> Add</button>
                </div>

            </form>
        </div>
    </div>
</div>

<div class="modal fade" id="withdrawFundsModal" tabindex="-1" aria-labelledby="addFundsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-danger">
                <h5 class="modal-title ">Withdraw Funds</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form id="withdrawFundsForm">
                <div class="modal-body">

                    <div class="form-group">
                        <label for="amount">Amount</label>
                        <input type="number" class="form-control" id="amount2" name="amount2" required>
                    </div>

                </div>
                <div class="modal-footer">

                    <button type="submit" class="btn btn-danger btn-lg">Withdraw Funds</button>
                </div>

                <div id="alertContainer"></div>
            </form>
        </div>
    </div>
</div>





<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<!-- <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script> -->
 <script>

     $(document).ready(function (){
         $('.loader-overlay').hide();
         console.log("hide")
         getbalance()
         getTransactions()

     })

     function getbalance(){
         $.ajax({
             url:"${pageContext.request.contextPath}/getBalance/${userId}",
             type: "POST",
             success:function (data){
                 $('#balance-container').empty()

                 var x = `   <p class="wallet-balance">Balance: $ <span id="wallet-balance">`+data+`</span></p>`
                 $('#balance-container').append(x);
             }

         })
     }

     $('#addFundsForm').on('submit', function(event) {
                event.preventDefault();
         if ($("#addFundsForm").valid()) {
                var amount = ($('#amount').val());
                $.ajax({
                    url: "${pageContext.request.contextPath}/addAmount/${userId}",
                    type: "POST",
                    data:{
                        amount:amount
                    },
                    success:function () {
                        $('.btn-close').click()
                        $('.loader-overlay').show();
                        setTimeout(function() {
                            $('.loader-overlay').hide();
                        }, 2000);
                        getbalance()
                        getTransactions()
                        $(".btn-close").click();





                    },
                    error:function (){
                        alert("errror")
                    }


                })
         }

            });


     $('#withdrawFundsForm').on('submit', function(event) {

debugger
         //add validtion it should not be less than balance
         event.preventDefault();
         if ($("#withdrawFundsForm").valid()) {
         var amount1 =   $('#amount2').val();
         var balance = $('#wallet-balance').text();
         console.log(balance)
         if(amount1>balance){
             $('#alertContainer').empty();
             var alert = `<div class="alert alert-danger alert-dismissible fade show" role="alert">`
             alert += `<strong>oops! </strong>`;
             alert+=`Amount is more than your balance.`;
             alert+=`<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>`;
             alert+=`</div>`;
             console.log(alert)
             $('#alertContainer').append(alert);
         }
         else {
         $.ajax({
             url: "${pageContext.request.contextPath}/withdrawAmount/${userId}",
             type: "POST",
             data:{
                 amount:amount1
             },
             success:function () {
                 $('.btn-close').click()
                 $('.loader-overlay').show();
                 setTimeout(function() {
                     $('.loader-overlay').hide();
                 }, 2000);
                 getbalance()
                 getTransactions()
                 $(".btn-close").click();






             },
             error:function (){
                 alert("errror")
             }


         })
         }
         }

     });


    </script>

<script>
console.log("page")
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



$("#pagination").on("click", function() {
    console.log("page clicked")
    var current = $(".current").text();
    $("#currentPage").attr("value" , current);
    console.log(current)
    getTransactions()


})

function getTransactions(){
    var curPage = $("#currentPage").attr("value");
    $.ajax({
        url:'${pageContext.request.contextPath}/getTransactions/${userId}',
        type:'POST',
        data:{
            curPage: curPage
        },
        success:function (data){
            $("#transaction-history").empty();
            if (data.length === 0) {
                $("#pagination").hide();
                $("#myTable").hide();
                $('#container-data-0').empty();
                var nodata = `<div class="container nodataC mt-5">
    <div id="nodata" class=" d-flex align-items-center justify-content-center flex-column">
        <dotlottie-player src="https://lottie.host/c5d27b13-2786-4e34-8aca-ddf227ca5161/oNJTuK3K7k.json" background="transparent" speed="1" style="width: 300px; height: 300px;" loop autoplay></dotlottie-player>

        <div> <h3 class="text-dark">No Transactions available!</h3></div>
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
                var trasaction = data[i];
                var amountClass = trasaction.debit === "debit" ? "text-danger" : "text-success";
                var amountSign = trasaction.debit === "debit" ? "-" : "+";

                var row =`<tr class="w-100">
                             <td>`+trasaction.date+`</td>
                            <td>`+trasaction.description+`</td>
                            <td class="text-success `+amountClass+`  "> `+amountSign+`  `+trasaction.amount+`</td>
                            <td>`+trasaction.debit+`</td>

                          <tr>`
                //dataaa


                $("#transaction-history").append(row);
                var temp =trasaction.count;
                var totalFinal = Math.ceil(temp / 6);
                init(totalFinal, curPage);
            }
        }
    })
}
</script>

<script>
    $('#addFundsModal').on('hidden.bs.modal', function() {
        $('#addFundsForm')[0].reset();
    });
    $('#withdrawFundsModal').on('hidden.bs.modal', function() {
        $('#withdrawFundsForm')[0].reset();
    });
</script>


<script src = "https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs"
type = "module" ></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>
<script src="<c:url value="/resources/js/wallet.js" />"></script>

<%--<script--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>--%>
<%--<script src="https://code.jquery.com/jquery-3.7.1.min.js"--%>
<%--        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="--%>
<%--        crossorigin="anonymous"></script>--%>

</body>

</html>