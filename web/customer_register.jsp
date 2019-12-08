<%-- 
    Document   : customer_register
    Created on : Nov 30, 2019, 3:08:08 PM
    Author     : LeVaLu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
            <div class="row mt-4 form-inline">
                <div class="col">
                    <a href="index.jsp" class="h2 text-primary font-weight-bold">Letoh</a>
                </div>

            </div>

            <div class="row mt-3 mb-5">
                <div class="col text-center">
                    <h2 class="text-primary font-weight-bold">Create Account</h2>
                </div>
            </div>
            <div class="row">
                <div class="col-5 mx-auto">
                    <form action="register" method="POST">
                        <div class="form-group row">
                            <label for="emailInput" class="col-3 col-form-label">Email</label>
                            <div class="col-9">
                                <input type="email" name="email" placeholder="Email" id="emailInput" class="form-control input-material" required>
                                <s:if test="%{exception.message.contains('duplicate')}">
                                    <small class="font-italic text-danger"><s:property value="%{email}"/>  is existed!</small>
                                </s:if>
                                
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="passwordInput" class="col-3 col-form-label">Password</label>
                            <div class="col-9">
                                <input type="password" name="password" placeholder="Password" class="form-control input-material" required>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="fullNameInput" class="col-3 col-form-label">Fullname</label>
                            <div class="col-9">
                                <input type="text" name="fullname" placeholder="Fullname" id="fullNameInput"
                                       class="form-control input-material" required>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="phoneInput" class="col-3 col-form-label">Phone</label>
                            <div class="col-9">
                                <input type="text" name="phone" placeholder="Phone" id="phoneInput" class="form-control input-material" required>
                            </div>
                        </div>

                        <div class="form-group row">
                            <label for="addressInput" class="col-3 col-form-label">Address</label>
                            <div class="col-9">
                                <input type="text" name="address" placeholder="Address" id="addressInput" class="form-control input-material" required>
                            </div>
                        </div>
                        <button class="btn btn-primary btn-material float-right mt-4 px-4">Create</button>
                    </form>
                </div>
            </div>
        </div>
        <script src="js/jquery.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>

</html>
