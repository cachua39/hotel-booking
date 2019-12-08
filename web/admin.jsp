<%-- 
    Document   : admin
    Created on : Nov 30, 2019, 1:33:35 PM
    Author     : LeVaLu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">

        <link href="https://fonts.googleapis.com/css?family=Raleway:300,400,700,800&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/styles.css">

        <title>Hotel Finder</title>
    </head>

    <body>

        <div class="container-fluid">
            <div class="bg row">
                <div class="col">
                    <!-- User Option -->
                    <div class="row mt-3 form-inline px-4">
                        <div class="col">
                            <a href="index.jsp" class="h2 text-primary font-weight-bold">Letoh</a>
                        </div>
                        <s:if test="%{#session.ACCOUNT != null}">
                            <div class="mx-2">
                                <a href="admin_register.jsp" class="mx-3">
                                    <i class="h3 font-weight-bold fa fa-lg fa-user-plus"></i>
                                </a>
                            </div>
                            <div class="ml-auto mx-2">
                                <a href="index.jsp">
                                    <h4 class="font-italic font-weight-bold"><s:property value="%{#session.ACCOUNT.email}" /></h4>
                                </a>
                            </div>
                            <div class="mx-2">
                                <a href="logout">
                                    <i class="h3 fa fa-sign-out"></i>
                                </a>
                            </div>
                        </s:if>
                        <s:else>
                            <a href="customer_register.jsp" class="mx-3">
                                <i class="h3 font-weight-bold fa fa-lg fa-user-plus"></i>
                            </a>

                            <a href="login.jsp">
                                <h4 class="text-primary font-weight-bold font-italic"><i class="fa fa-lg fa-sign-in"></i> Login</h4>
                            </a>
                        </s:else>
                    </div>
                    <div class="row mt-5">
                        <div class="col">
                            <div class="row mb-3">
                                <h1 class="display-3 text-primary font-weight-bold mx-auto">letoH</h1>
                            </div>
                            <form id="formSearch">
                                <div class="row">
                                    <div class="form-group mx-auto">
                                        <input type="text" name="area"
                                               class="form-control input-material rounded search-box input-bg-transparent"
                                               placeholder="Enter hotel area"
                                               style="height: 90px !important; font-size: 1.9rem !important;">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group mx-auto">
                                        <input type="number" name="quantity" min="1"  
                                               class="form-control input-material rounded search-box input-bg-transparent"
                                               placeholder="Number of rooms" style="height: 60px !important;" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="row mx-auto">
                                        <div class="pl-4 pr-1">
                                            <input type="date" name="checkIn" id="dateFrom"
                                                   class="form-control input-material rounded search-date input-bg-transparent" required>
                                        </div>
                                        <div class="pr-4 pl-1">
                                            <input type="date" name="checkOut"  id="dateTo"
                                                   class="form-control input-material rounded search-date input-bg-transparent" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mt-3">
                                    <button type="submit" class="mx-auto btn-search" id="btnSearch"><i
                                            class="display-4 fa fa-search text-primary font-weight-bold"></i></button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container mb-4">
            <div  id="listHotelContainer">

            </div>
            <div class="modal fade" id="addToCartModal" tabindex="-1">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title text-success" id="addToCartTitle"></h5>
                        </div>
                        <div class="modal-body">
                            <p id="addToCartBody"></p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary btn-material" data-dismiss="modal">OK</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="dateModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title text-danger">Error</h5>
                        </div>
                        <div class="modal-body">
                            <p id="dateMsg"></p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary btn-material" data-dismiss="modal">OK</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/admin.js"></script>
    </body>

</html>