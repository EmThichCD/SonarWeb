<%-- 
    Document   : createpro
    Created on : Jan 11, 2021, 10:04:01 AM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Page</title>
    </head>
    <body>
        <c:if test="${sessionScope.USER_LOGIN.role eq 'ADMIN'}">
        <table border="0">
            <tbody>
                <tr>
                    <td style="width: 200px; font-size: 20px">Hana Shop</td>
                    <td style="width: 200px; font-size: 20px"><a href="MainController?btnAction=Search">Product</a></td>                
                    <td style="width: 1500px; text-align: right; font-size: 20px">
                        ${sessionScope.USER_LOGIN.fullName}
                        <c:if test="${sessionScope.USER_LOGIN!=null}">
                            <a href="MainController?btnAction=Logout">Logout</a>
                        </c:if>
                        <c:if test="${sessionScope.USER_LOGIN==null}">
                            <a href="MainController?btnAction=LoginPage">Login</a>
                        </c:if>
                    </td>
                </tr>
            </tbody>
        </table>  
        <h1>Create new product</h1>
        <form action="MainController" method="POST" enctype="multipart/form-data"> 
            <table border="0">
                <tbody>
                    <tr>
                        <td>ProductName:</td>
                        <td><input type="text" name="txtproductName" value="${param.txtproductName}"></td>
                        <td>${requestScope.PRODUCT_ERROR.productNameError}</td>
                    </tr>
                    <tr>
                        <td>Quantity</td>
                        <td><input type="number" name="txtquantity" value="${param.txtquantity}" min="1"></td>
                        <td>${requestScope.PRODUCT_ERROR.quantityError}</td>
                    </tr>
                    <tr>
                        <td>Image</td>
                        <td><input type="file" name="txtimage" value="${param.txtimage}" accept="image/png, image/jpeg"></td>
                        <td>${requestScope.PRODUCT_ERROR.imageError}</td>
                    </tr>       
                        <td>Description</td>
                        <td><input type="text" name="txtdescription" value="${param.txtdescription}"></td>
                        <td>${requestScope.PRODUCT_ERROR.descriptionError}</td>
                    </tr>
                    <tr>
                        <td>Price</td>
                        <td><input type="number" name="txtprice" value="${param.txtprice}" min="1"></td>
                        <td>${requestScope.PRODUCT_ERROR.priceError}</td>
                    </tr>
                    <tr>
                        <td>Category</td>
                        <td>
                            <select name="categoty" >
                                <option value="coffee">coffee</option>
                                <option value="cakes">cakes</option>
                                <option value="candies">candies</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="submit" name="btnAction" value="Create"></td>
                    </tr>

                </tbody>
            </table>
        </form>
        ${requestScope.PRODUCT_SUCCESS}
        </c:if>
        <c:if test="${sessionScope.USER_LOGIN.role ne 'ADMIN'}">
            <div>You are not authorized, please login again</div>
            <a href="MainController?btnAction=Logout">Login</a>
        </c:if>
    </body>
</html>
