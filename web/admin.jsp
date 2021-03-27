<%-- 
    Document   : admin
    Created on : Jan 8, 2021, 4:26:42 PM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body>
        <c:if test="${sessionScope.USER_LOGIN.role eq 'ADMIN'}">
        <table border="1">
            <tbody>
                <tr>
                    <td><a href="MainController?btnAction=CreateProduct">Create Product</a></td>
                    <td><a href="SearchController?index=1&txtSearch=${param.txtSearch}&txtPriceMin=${param.txtPriceMin}&txtPriceMax=${param.txtPriceMax}&categoty=${param.categoty}">View Product</a></td>
                </tr>
            </tbody>
        </table>
        </c:if>
        <c:if test="${sessionScope.USER_LOGIN.role ne 'ADMIN'}">
            <div>You are not authorized, please login again</div>
            <a href="MainController?btnAction=Logout">Login</a>
        </c:if>
    </body>
</html>
