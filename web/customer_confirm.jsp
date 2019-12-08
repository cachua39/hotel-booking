<%-- 
    Document   : customer_confirm
    Created on : Dec 2, 2019, 12:29:43 AM
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

            <div class="row mt-3 mb-4">
                <div class="col text-center">
                    <h2 class="text-primary font-weight-bold">Your Reservation</h2>
                </div>
            </div>

            <!-- Confirm -->
            <div class="row mt-5">
                <div class="col-7 mx-auto">
                    <div class="row text-center">
                        <div class="col">
                            <h5>Thanks for your reservation</h5>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col">
                            <s:if test="%{orderDTO != null}">
                                <table class="table">
                                    <thead>
                                    <th>Your Orders</th>
                                    <th class="text-right">Total</th>
                                    </thead>
                                    <tbody>
                                        <s:iterator value="%{orderDTO.listHotels}" var="item">
                                            <tr>
                                                <td>
                                                    <p class="font-weight-bold my-0"><s:property value="name"/></p>
                                                    <p class="ml-0 my-0"><small>+ <s:property value="checkIn"/> - <s:property value="checkOut"/></small></p>
                                                    <p class="ml-0 my-0"><small class="font-italic">+ <s:property value="type"/> Room - x<s:property value="quantityCart"/> Room(s)</small>
                                                    </p>
                                                </td>
                                                <td class="text-right text-primary font-weight-bold">
                                                    $ <s:property value="subTotalCart"/>
                                                </td>
                                            </tr>
                                        </s:iterator>
                                    </tbody>
                                </table>
                            </s:if>

                        </div>
                    </div>
                    <div class="horizontal-divider"></div>
                    <div class="row mt-2">
                        <div class="col-3 ml-2">
                            <h4 class="text-green">Discount</h4>
                        </div>
                        <div class="col-3 ml-auto text-right">
                            <h4 class="text-green font-weight-bold font-italic">- $<s:if test="%{orderDTO.couponValue > 0}"><s:property value="%{orderDTO.couponValue}"/></s:if><s:else>0</s:else></h4>
                        </div>
                    </div>
                    <div class="horizontal-divider"></div>
                    <div class="row mt-2">
                        <div class="col-3 ml-2">
                            <h4 class="text-primary font-weight-bold">Total</h4>
                        </div>
                        <div class="col-8 ml-auto text-right">
                                <h4 class="text-primary font-weight-bold">$ <s:property value="orderDTO.total"/></h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>

</html>
