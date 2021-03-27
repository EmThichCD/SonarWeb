<%-- 
    Document   : viewcart
    Created on : Jan 15, 2021, 7:17:38 PM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
    </head>
    <body>
        <c:if test="${sessionScope.USER_LOGIN.role eq 'USER'}">
        <table border="0">
            <tbody>
                <tr>
                    <td style="width: 200px; font-size: 20px">Hana Shop</td>
                    <td style="width: 200px; font-size: 20px"><a href="MainController?btnAction=Search">Product</a></td>
                    <c:set var="USER" value="USER"></c:set>
                    <c:if test="${sessionScope.USER_LOGIN.role.equals(USER)}">
                        <td style="width: 200px; font-size: 20px"><a href="MainController?btnAction=CartPage">Cart</a></td>  
                        <td style="width: 200px; font-size: 20px"><a href="MainController?btnAction=HistoryPage">History</a></td>   
                    </c:if>          
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
        <h1>Your shopping cart:</h1>
        ${requestScope.CHECKOUT_ERROR}
        <c:if test="${sessionScope.CART != null}">
            <c:if test="${not empty sessionScope.CART}">
                <c:set var="toltal" value="0"></c:set>
                    <table border="0" style="width: 1000px; text-align: center ">
                        <thead>
                            <tr>
                                <th>Product Name</th>
                                <th>Image</th>
                                <th>Quantity</th>
                                <th>price</th>
                                <th>update</th>
                                <th>delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="orderDetail"  items="${sessionScope.CART.hash}">
                            <form action="MainController">
                                <tr style="height: 50px">
                                    <td>${orderDetail.value.product.productName}
                                        <input type="hidden" name="txtproductID" value="${orderDetail.value.product.productID}">
                                    </td>
                                    <td>
                                        <img src="${orderDetail.value.product.image}" alt="tttt" width="50px" height="auto" /> 
                                    </td>
                                    <td>
                                        <input type="number" name="txtquantity" value="${orderDetail.value.quantity}" max="999999999">
                                    </td>
                                    <td>
                                        ${orderDetail.value.price}
                                        <c:set var="toltal" value="${orderDetail.value.price +toltal}"></c:set>
                                    </td>
                                    <td><input type="submit" name="btnAction" value="Update Cart"></td>
                                    <td>
                                    <c:url var="DeleteCart" value="MainController">
                                        <c:param name="txtproductID" value="${orderDetail.value.product.productID}"></c:param>
                                        <c:param name="btnAction" value="DeleteCart"></c:param>
                                    </c:url>
                                        <a style="text-decoration: none" href="${DeleteCart}" onclick="return confirm('Do you want to delete ?')">DELETE</a>
                                    </td>
                                </tr>
                            </form>
                        </c:forEach>
                    </tbody>
                </table>
                ${requestScope.QUANTITYERROR}
                <h1>TOTAL PRICE: ${toltal}</h1>
                <h1>PAYMENT</h1>
                <form action="MainController">
                    <select name="cbpayment">
                        <option value="COD" <c:if test="${param.cbpayment eq 'COD'}">selected="selected"</c:if>>COD</option>
                        <option value="viaPayPal" <c:if test="${param.cbpayment eq 'viaPayPal'}">selected="selected"</c:if>>via PayPal</option>
                    </select>
                        <input type="hidden" name="totalPrice"value="${toltal}">
                        <input type="submit" name="btnAction"value="CheckOut">
                </form>
            </c:if>
        </c:if>
        </c:if>            
        <c:if test="${sessionScope.USER_LOGIN.role ne 'USER'}">
            <div>You are not authorized, please login again</div>
            <a href="MainController?btnAction=Logout">Login</a>
        </c:if>
    </body>
</html>
