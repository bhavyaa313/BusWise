<%--
  Created by IntelliJ IDEA.
  User: PCA84
  Date: 30-05-2024
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="<c:url value="/resources/image/logo.png"/>"/>
    <title>Buses</title>

    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"
            integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
            crossorigin="anonymous"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.3/jquery.validate.min.js"></script>


    <link rel="stylesheet" href="<c:url value="/resources/css/bus.css" />">

    <script src="<c:url value="/resources/js/bus.js"/>"></script>




    <style>


        .error-message1{
            display: block;
            margin-top: 10px;
            font-size: 0.8em;
            color: red;
        }




    </style>
</head>


<body class="bg-dark" >
<div class="container-fluid bg-white">
    <jsp:include page="navbar.jsp" />
</div>

<div id="alertContainerDelete" class="d-flex ms-auto mt-auto"></div>
<input type="text"  id="userId"  value="${userId}" hidden>
<div class="container bg-light mt-5  ">

    <div class="d-flex  ">
        <h3 class="text-center mx-auto mt-4 mb-4 ">Buses</h3>
    </div>

    <div class="d-flex mx-3 mt-3">
        <button type="button" class="btn btn-dark mx-2"
                data-bs-toggle="modal" data-bs-target="#add">Add Bus
        </button>
        <div class="col-lg-4 d-md-flex d-block ms-auto">

            <div class="input-group border me-2 rounded-0 mb-2 form-control">
                <div class="input-group-append">
                    <button class="btn text-secondary border bord
                    er-0 bg-white"
                            type="button">
                        <i class="bi bi-search"></i>
                    </button>
                </div>

                <input type="text" id="searchBusNumber"  name="bus"
                       class="form-control border-0  shadow-none" placeholder="Search Bus Number">
            </div>

        </div>
        <!-- <div class="col-lg-4 d-md-flex d-block ms-auto">

        <div class="input-group border me-2 rounded-0 mb-2 form-control">
            <div class="input-group-append">
                <button class="btn text-secondary border border-0 bg-white"
                    type="button">
                    <i class="bi bi-search"></i>
                </button>
            </div>
            <input type="text" id="myInput1" onkeyup="search1()"
                class="form-control border-0  shadow-none" placeholder="Search by priority ">
        </div>

    </div> -->

    </div>
    <div id="container-data-0"> </div>
    <table class="table mt-3" id="myTable">
        <thead>
        <tr>

<%--            <th scope="col" class="col-md-1">Bus Id</th>--%>
            <th scope="col" class="col-md-2">Bus Number</th>
            <th scope="col" class="col-md-2">Bus Type</th>
            <th scope="col" class="col-md-2">Seating Capacity</th>
            <th scope="col" class="col-md-2">Modified Date</th>
            <th scope="col"></th>


        </tr>
        </thead>
        <tbody id="tableData">


        </tbody>
    </table>

</div>
</div>

<div class="modal fade" id="add" tabindex="-1"
     aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog  modal-dialog-centered w-100">

        <div class="modal-content rounded-4 p-3 ">
            <div class="container-fluid  ">

                <div class="d-flex  mt-1  ">
                    <h3 class="mx-1 mt-2 fw-5">Add Bus</h3>
                    <button type="button" class="btn-close ms-auto" data-bs-dismiss="modal" id="close" aria-label="Close"></button>
                </div>
            </div>
            <form
                  id="myFormAdd" method="post" >

                <div class="modal-body">


                    <div class="row g-2">

                        <div class="col-md">
                            <div class="form mb-3">
                                <label
                                        >Bus Number <span
                                        class="text-danger">*</span>   </label>
                                <input type="text" class="form-control " placeholder=" Bus Number" id="busNumber"
                                       name="busNumber"/>

                                <div class="invalid-feedback" id="busNumberError"></div>
                            </div>

                        </div>


                        <div class="col-md">
                            <div class="form mb-3">
                                <label >Select Bus Type <span
                                        class="text-danger">*</span> </label>
                                <select class="form-select"
                                        aria-label="Floating label select example" name="busType" id="busType">

                                    <option value="Seating">Seating</option>
                                    <option value="Sleeping">Sleeping</option>

                                </select>

                            </div>
                        </div>
                    </div>

                    <div class="row g-2">

                        <div class="col-md-6">
                            <div class=" form mb-3">
                                <label
                                         class="dm ">Seating Capacity <span
                                        class="text-danger">*</span></label>
                                <input type="number" class="form-control error" id="seatingCapacity"
                                       placeholder="Seating Capacity" name="seatingCapacity"/>


                            </div>
                        </div>


                    </div>


                </div>
                <div
                        class="modal-footer border-top-0 pb-5 d-flex justify-content-center">


                    <div
                            class="modal-footer border-top-0 d-flex p-0 justify-content-center">

                        <button type="submit" class="btn btn-dark  text-white btn-lg rounded-end" id="saveButton" style="width: 22vh;"
                               >Save</button>
                        <button type="reset" class="btn btn-secondary text-white btn-lg rounded-end " style="width: 22vh;"
                                data-bs-dismiss="modal" onclick="clearFormData()">Cancel</button>
                    </div>
                </div>
            </form>
        </div>


    </div>
</div>
<div class="modal fade" id="edit" tabindex="-1"
     aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog  modal-dialog-centered p-3 w-100 ">

        <div class="modal-content rounded-4 p-4">
            <div class="container-fluid  ">

                <div class="d-flex  mt-1  ">
                    <h3 class="fw-5 mx-2 mt-2 ">Edit
                        Bus</h3>
                    <button type="button" class="btn-close ms-auto" data-bs-dismiss="modal"  aria-label="Close"></button>
                </div>
            </div>
            <form  method="post" id="myFormEdit">
                <div class="modal-body">


                    <div class="row g-2">
                        <input type="text" value="" id="bid" name="id" hidden>
                        <div class="col-md">
                            <div class="form mb-3">
                                <label for="">Bus Number <span
                                        class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="bn"
                                       placeholder="bus number" name="busNumber" value="" disabled>

                            </div>
                        </div>


                        <div class="col-md">
                            <div class="form mb-3">
                                <label for="bt" class="">Select Bus Type <span
                                        class="text-danger">*</span></label>
                                <select class="form-select" id="bt"
                                        aria-label="Floating label select example" name="busType">

                                    <option value="Seating">Seating</option>
                                    <option value="Sleeping">Sleeping</option>>

                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="row g-2">

                        <div class="col-md-6">
                            <div class="form mb-3">
                                <label
                                        for="" class="dm">Seating Capacity <span
                                        class="text-danger">*</span></label>
                                <input type="text" class="form-control" id="bc"
                                       placeholder="seating capacity" name="seatingCapacity"
                                       value="">
                            </div>
                        </div>


                    </div>


                </div>
                <div
                        class="modal-footer border-top-0 pb-5 d-flex justify-content-center">

                    <div
                            class="modal-footer border-top-0 d-flex p-0 justify-content-center">

                        <button type="submit" class="btn btn-secondary  text-white btn-lg rounded-end" style="width: 20vh;"
                               >Save</button>
                        <button type="button" class="btn btn-dark text-white btn-lg rounded-end " style="width: 20vh;"
                                data-bs-dismiss="modal" >Cancel</button>
                    </div>
                </div>
            </form>
        </div>


    </div>
</div>
<div class="d-flex justify-content-center">
    <div id="pagination" class="mb-5"></div>
</div>
<input type="text" id="currentPage" value="1" hidden>

</div>



<script src="https://unpkg.com/@dotlottie/player-component@latest/dist/dotlottie-player.mjs" type="module"></script>
<script src="<c:url value="/resources/js/bus.js"/>"></script>

</body>
</html>