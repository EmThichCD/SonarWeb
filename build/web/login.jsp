<%-- 
    Document   : login
    Created on : Jan 6, 2021, 4:35:32 PM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1 style="text-align: center">Welcome Hana Shop__drinks/fast food</h1>
        <form action="MainController" method="POST" >
            <table border="0" style="margin: 0 auto">                
                <tbody>
                    <tr>
                        <td>UserID</td>
                        <td><input type="text" name="userID" value="${param.userID}"></td>
                        <td>${requestScope.ERRORUSER }</td>
                    </tr>
                    <tr>
                        <td>Password</td>
                        <td><input type="password" name="password" value="${param.password}"></td>
                        <td>${requestScope.ERRORPASSWORD }</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>${requestScope.ERROR }</br></td>
                    </tr>
                    <tr>
                        <td><input type="submit" name="btnAction" value="Login"></td>
                        <td><a href="MainController?btnAction=Search">Product</a></td>
                    </tr>
                </tbody>
            </table>
        </form>
    </body>
</html>
