<%-- 
    Document   : history
    Created on : Jan 17, 2021, 5:39:48 PM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
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
        <form action="MainController">
            DATE: <input type="date" name="txtdate" value="${param.txtdate}" max=""></br>
            Product Name: <input type="text" name="txtproname" value="${param.txtproname}"></br>
            <input type="submit" name="btnAction" value="Search History">
        </form>
        <h1>VIEW HISTORY</h1>            
        <c:if test="${requestScope.LIST_HISTORY != null}">
            <c:if test="${not empty requestScope.LIST_HISTORY}">
                <c:forEach var="cart" items="${requestScope.LIST_HISTORY}">
                    <h1>${cart.orderDate}------${cart.totalPrice}VND-------<c:if test="${cart.payID eq '1'}">COD</c:if><c:if test="${cart.payID eq '2'}">via PayPal</c:if></h1>                  
                        <table border="1" style="width: 1200px">
                        <tbody>
                            <c:forEach var="orderDetail" items="${cart.hash}">
                            <tr>
                                <td>${orderDetail.value.product.productName}</td>
                                <td><img src="${orderDetail.value.product.image}" alt="tttt" width="50px" height="auto" /> </td>
                                <td>${orderDetail.value.quantity}</td>
                                <td>${orderDetail.value.price}</td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    
                </c:forEach>
            </c:if>
        </c:if>
        </c:if>            
        <c:if test="${sessionScope.USER_LOGIN.role ne 'USER'}">
            <div>You are not authorized, please login again</div>
            <a href="MainController?btnAction=Logout">Login</a>
        </c:if>
    </body>
</html>
