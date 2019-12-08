<%-- 
    Document   : user_cart
    Created on : Dec 1, 2019, 2:36:50 PM
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
                    <h2 class="text-primary font-weight-bold">Your Cart</h2>
                </div>
            </div>

            <!-- List Products -->
            <div class="row mt-4">
                <div class="col">
                    <s:if test="%{!#session.CART.cart.isEmpty()}">
                        <form action="updateInCart" method="POST">
                            <table class="table">
                                <thead>
                                <th class="width-5">No.</th>
                                <th></th>
                                <th>Details</th>
                                <th class="text-center">Number of rooms</th>
                                <th class="text-center">Sub Total</th>
                                <th class="width-5"></th>
                                </thead>
                                <s:iterator value="%{#session.CART.cart.values}" var="hotel" status="counter">
                                    <tbody>
                                        <tr>
                                            <td><s:property value="%{#counter.count}"/></td>
                                            <td>
                                                <img src="<s:property value="%{#hotel.photo}"/>" class="rounded img-cart">
                                            </td>
                                            <td>
                                                <h4 class="font-weight-bold text-secondary"><s:property value="%{#hotel.name}"/></h4>
                                                <p class="my-0"><s:property value="%{#hotel.address}"/></p>
                                                <p class="mt-0 mb-1 font-italic font-weight-bold text-blue"><s:property value="%{#hotel.type}"/> Room</p>
                                                <p class="my-0 text-primary font-weight-bold font-italic">$<s:property value="%{#hotel.price}"/></p>
                                                <div class="row mt-2">
                                                    <div class="col-4">
                                                        <p class="my-0 font-italic text-green">Check In:</p>
                                                        <p class="my-0 mb-0 font-italic text-green">Check Out:</p>
                                                    </div>
                                                    <div class="col">
                                                        <p class="my-0 text-primary font-weight-bold font-italic"><s:property value="%{#hotel.checkIn}"/></p>
                                                        <p class="my-0 text-primary font-weight-bold font-italic"><s:property value="%{#hotel.checkOut}"/></p>
                                                    </div>
                                                </div>

                                            </td>
                                            <td style="width: 20% !important;">
                                                <div class="input-group mx-auto">
                                                    <div class="input-group-prepend">
                                                        <button type="button" class="btn btn-primary btn-material px-3" onclick="minus('<s:property value="%{#counter.count}"/>')">
                                                            <i class="fa fa-minus"></i>
                                                        </button>
                                                    </div>
                                                    <input type="text" name="quantities" class="form-control text-center" id="<s:property value="%{#counter.count}"/>" value="<s:property value="%{#hotel.quantityCart}"/>"
                                                           readonly>
                                                    <div class="input-group-append">
                                                        <button type="button" class="btn btn-primary btn-material px-3" onclick="plus('<s:property value="%{#counter.count}"/>')">
                                                            <i class="fa fa-plus"></i>
                                                        </button>
                                                    </div>
                                                </div>
                                            </td>
                                            <td style="width: 15% !important;">
                                                <h5 class="text-primary mt-2 font-weight-bold text-center" id="subTotal<s:property value="%{#counter.count}"/>">$<s:property value="%{#hotel.subTotalCart}"/></h5>
                                            </td>
                                            <td>
                                                <s:url value="deleteInCart" var="deleteInCartLink" escapeAmp="false">
                                                    <s:param value="%{#hotel.hotelId}" name="hotelId"/>
                                                    <s:param value="%{#hotel.typeId}" name="typeId"/>
                                                    <s:param value="%{#hotel.checkIn}" name="checkIn"/>
                                                    <s:param value="%{#hotel.checkOut}" name="checkOut"/>
                                                </s:url>
                                                <a href="<s:property value="#deleteInCartLink"/>"><i class="h3 fa fa-times text-primary"></i></a>
                                            </td>
                                        </tr>
                                    </tbody>
                                </s:iterator>

                            </table>
                            <div class="horizontal-divider"></div>
                            <div class="row mt-2">
                                <div class="col-2 ml-auto">
                                    <h5 class="font-weight-bold">Total: </h5>
                                </div>
                                <div class="col-2">
                                    <h5 class="text-primary font-weight-bold">$<s:property value="%{#session.CART.getTotal()}"/></h5>
                                </div>
                            </div>
                            <div class="horizontal-divider"></div>
                            <div class="row my-5">
                                <div class="col">
                                    <a href="loadInfo" class="btn btn-primary btn-material float-right mx-3 px-5">Checkout</a>
                                    <button type="submit" class="btn btn-primary btn-material float-right mx-3 px-5">Update Cart</a>
                                </div>
                            </div>
                        </form>
                    </s:if>
                    <s:else>
                        <div class="horizontal-divider"></div>
                        <div class="text-center h3 text-primary font-weight-bold mt-4">Your cart is empty!</div>
                    </s:else>
                </div>
            </div>
        </div>
        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/cart.js"></script>
    </body>

</html>