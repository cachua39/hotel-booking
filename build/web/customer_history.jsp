<%-- 
    Document   : customer_history
    Created on : Dec 2, 2019, 1:35:04 AM
    Author     : LeVaLu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
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
        <div class="container">
            <!-- User Option -->
            <div class="row mt-3 form-inline px-4">
                <div class="col">
                    <a href="index.jsp" class="h2 text-primary font-weight-bold">Letoh</a>
                </div>
                <s:if test="%{#session.ACCOUNT != null}">
                    <div class="ml-auto mx-2">
                        <a href="index.jsp">
                            <h4 class="font-italic font-weight-bold"><s:property value="%{#session.ACCOUNT.email}" /></h4>
                        </a>
                    </div>
                    <div class="mx-2">
                        <a href="customer_cart.jsp">
                            <i class="h3 fa fa-shopping-cart"></i>
                        </a>
                    </div>
                    <div class="mx-2">
                        <a href="customer_history.jsp">
                            <i class="h3 fa fa-book"></i>
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

            <!-- Search Bar -->
            <div class="row mt-4">
                <div class="col">
                    <form class="form-inline" id="formSearch">
                        <div class="form-group ml-5 ml-auto">
                            <input type="text" name="hotelName" class="form-control input-material" placeholder="Search by Hotel Name" size="25">
                            <button type="submit" class="btn btn-primary btn-material ml-4">Search</button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- List orders -->
            <div class="row mt-4">
                <div class="col">
                    <table class="table mt-3">
                        <thead>
                        <th class="width-5">No.</th>
                        <th>Order ID</th>
                        <th>Details</th>
                        <th>Discount</th>
                        <th>Total</th>
                        <th class="width-5"></th>
                        </thead>
                        <tbody id="tblHistory">
                        </tbody>
                    </table>
                </div>
            </div>
            
             <div class="modal fade" id="historyModal" tabindex="-1">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="modalTitle"></h5>
                        </div>
                        <div class="modal-body">
                            <p id="modalBody"></p>
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
        <script src="js/customer_history.js"></script>
    </body>

</html>