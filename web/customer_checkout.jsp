<%-- 
    Document   : customer_checkout
    Created on : Dec 1, 2019, 8:24:25 PM
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
            <!-- End User Option-->


            <div class="row mt-4">
                <div class="col">
                    <!-- Customer information -->
                    <table class="table table-borderless">
                        <thead>
                        <th>
                            Customer Information
                        </th>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Email:</td>
                                <td><s:property value="%{userInfo.Email}"/></td>
                            </tr>
                            <tr>
                                <td>Fullname:</td>
                                <td><s:property value="%{userInfo.Fullname}"/></td>
                            </tr>
                            <tr>
                                <td>Phone: </td>
                                <td><s:property value="%{userInfo.Phone}"/></td>
                            </tr>
                            <tr>
                                <td>Address:</td>
                                <td><s:property value="%{userInfo.Address}"/></td>
                            </tr>
                        </tbody>
                    </table>
                    <!-- End Customer information -->
                </div>

                <div class="col-1 vertical-divider"></div>
                <!-- Order Details -->
                <div class="col">
                    <s:if test="%{!#session.CART.cart.isEmpty()}">
                        <table class="table mt-4">
                            <thead>
                            <th>Your reservation</th>
                            <th class="text-right">Total</th>
                            </thead>
                            <tbody>
                                <s:iterator value="%{#session.CART.cart.values()}" var="item">
                                    <tr>
                                        <td>
                                            <p class="font-weight-bold my-0"><s:property value="%{#item.name}"/></p>
                                            <p class="ml-2 my-0"><small>+ <s:property value="%{#item.checkIn}"/> to <s:property value="%{#item.checkOut}"/></small></p>
                                            <p class="ml-2 my-0"><small class="font-italic">+ <s:property value="%{#item.type}"/> Room - x<s:property value="%{#item.quantityCart}"/></small></p>
                                        </td>
                                        <td class="text-right text-primary font-weight-bold">$ <s:property value="%{#item.subTotalCart}"/></td>
                                    </tr>
                                </s:iterator>

                            </tbody>
                        </table>
                        <div class="horizontal-divider"></div>
                        <div class="row mt-3">
                            <div class="col">
                                <button type="button" class="btn btn-primary btn-material" id="btnCheckCode">Check Code</button>
                            </div>
                            <div class="col-6">
                                <input type="text" id="txtCode" class="form-control input-material" placeholder="Discount Code">
                            </div>
                        </div>
                        <div class="row mt-2">
                            <div class="col">
                                <p class="h4 text-green font-weight-bold float-right" id="couponValue"><i class="fa fa-dollar"></i> <i>-0.0</i></p>
                            </div>
                        </div>
                        <div class="horizontal-divider"></div>
                        <div class="row mt-3">
                            <div class="col">
                                <p class="h4 font-weight-bold">Total</p>
                            </div>
                            <div class="col" id="txtTotal">
                                <p class="h3 text-primary font-weight-bold float-right"><i class="fa fa-dollar h3 text-primary font-weight-bold"> </i> <s:property value="%{#session.CART.getTotal()}"/></p>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col">
                                <i class="text-danger font-italic float-right"><s:property value="%{orderError.orderError}"/></i>
                            </div>
                        </div>
                        <form method="POST" id="formCheckout">
                            <input type="hidden" name="couponCode" id="hiddenCode"/>
                            <div class="row mt-5">
                                <div class="col">
                                    <button type="button" id="btnCheckout"
                                            class="btn btn-primary btn-material float-right font-weight-bold py-2 px-5 ml-4">Checkout</button>

                                    <button type="button" id="btnPaypal" class="float-right btn p-0">
                                        <img src="https://www.paypalobjects.com/webstatic/en_US/i/buttons/checkout-logo-large.png"
                                             alt="Check out with PayPal" class="btn-material" style="width: 90% !important"/>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </s:if>
                    <s:else>
                        <div class="text-center h3 text-primary font-weight-bold mt-4">Your cart is empty!</div>
                    </s:else>
                </div>
                <!-- Order Details -->
            </div>
        </div>
        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/checkout.js"></script>
    </body>

</html>