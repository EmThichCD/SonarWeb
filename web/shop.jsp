<%-- 
    Document   : shop
    Created on : Jan 8, 2021, 5:02:19 PM
    Author     : tuanv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hana Shopping</title>
    </head>
    <body>
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

         <form action="MainController" method="POST" style="text-align: center; font-size: 20px">
            PriceMin: <input type="number" min="0" name="txtPriceMin" value="${param.txtPriceMin}">
            PriceMax: <input type="number" min="0" name="txtPriceMax" value="${param.txtPriceMax}">
            <select name="categoty" style="font-size: 20px">
                <c:if test="${param.categoty eq 'Product'}">
                    <option value="product" selected="selected">All product</option>
                </c:if>
                <c:if test="${param.categoty ne 'Product'}">
                    <option value="product">All product</option>
                </c:if>
                <c:if test="${param.categoty eq 'Coffee'}">
                    <option value="Coffee" selected="selected">coffee</option>
                </c:if>
                <c:if test="${param.categoty ne 'Coffee'}">
                    <option value="Coffee">coffee</option>
                </c:if>
                <c:if test="${param.categoty eq 'Cakes'}">
                    <option value="Cakes" selected="selected">cakes</option>
                </c:if>
                <c:if test="${param.categoty ne 'Cakes'}">
                    <option value="Cakes">cakes</option>
                </c:if>
                <c:if test="${param.categoty eq 'Candies'}">
                    <option value="Candies" selected="selected">candies</option>
                </c:if>
                <c:if test="${param.categoty ne 'Candies'}">
                    <option value="Candies">candies</option>
                </c:if>
            </select>
            <input type="text" name="txtSearch" style="width: 500px" value="${param.txtSearch}">
            <input type="submit" name="btnAction" value="Search">
        </form>

            <c:if test="${sessionScope.LIST_PRODUCT!=null}">
            <c:if test="${not empty sessionScope.LIST_PRODUCT}">

                <c:if test="${sessionScope.USER_LOGIN.role eq'ADMIN'}">
                    <a href="MainController?btnAction=CreateProduct">Create Product</a>
                    
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>productName</br>
                                        ${requestScope.Pro_UPDATE_E.productNameError}</th>
                                    <th>quantity</br>
                                        ${requestScope.Pro_UPDATE_E.quantityError}</th>
                                    <th>image</th>
                                    <th>New image</th>
                                    <th>description</br>
                                        ${requestScope.Pro_UPDATE_E.descriptionError}</th>
                                    <th>price</br>
                                        ${requestScope.Pro_UPDATE_E.priceError}</th>
                                    <th>createDate</th>
                                    <th>status</th>
                                    <th>category</th>
                                    <th>Update</th>
                                    <th>Delete</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="list" varStatus="counter" items="${sessionScope.LIST_PRODUCT}">     
                                <form action="MainController" method="POST"  enctype="multipart/form-data">
                                    <tr style="height: 150px">
                                        <td>${counter.count}</td>
                                        <td>
                                            <input type="text" value="${list.productName}" name="txtproName">
                                        </td>
                                        <td>
                                            <input type="number" min="1" value="${list.quantity}" name="txtquantity">
                                        </td>
                                        <td>
                                            <img src="${list.image}" alt="tttt" width="150px" height="150px" /> 
                                            <input type="hidden" name="image" value="${list.image}">
                                        </td>
                                        <td>
                                            <input type="file" accept="image/png, image/jpeg" name="txtimagenew">
                                        </td>
                                        <td>
                                            <input type="text" value="${list.description}" name="txtprodescription" height="90px">
                                        </td>
                                        <td>
                                            <input type="number" min="1" value="${list.price}" name="txtprice">
                                        </td>
                                        <td>${list.createDate}</td>
                                        <td>
                                            <select name="cbstatus">
                                                <option value="true" <c:if test="${list.status}">selected="selected"</c:if> >active</option>
                                                <option value="false" <c:if test="${!list.status}">selected="selected"</c:if> >inactive</option>
                                                </select>
                                        </td>
                                        <td>
                                            <select name="cbcategory">
                                                 <option value="Coffee" <c:if test="${list.category eq'Coffee'}">selected="selected"</c:if> >coffee</option>
                                                <option value="Cakes" <c:if test="${list.category eq'Cakes'}">selected="selected"</c:if> >cakes</option>
                                                <option value="Candies" <c:if test="${list.category eq'Candies'}">selected="selected"</c:if> >candies</option>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="hidden" name="txtproID" value="${list.productID}">
                                            <input type="hidden" name="txtcreateDate" value="${list.createDate}">
                                            <input type="submit" name="btnAction" value="update Product">
                                        </td>
                                        <td>
                                            <a style="text-decoration: none" href="MainController?btnAction=DeleteProduct&productID=${list.productID}" onclick="return confirm('Do you want to delete ?')">DELETE</a>
                                        </td>
                                    </tr>
                                    </form>
                                </c:forEach>
                            </tbody>
                        </table>
                    <c:forEach var="i" begin="1" end="${sessionScope.NUM_PAGE}">
                        <a href="SearchController?index=${i}&txtSearch=${param.txtSearch}&txtPriceMin=${param.txtPriceMin}&txtPriceMax=${param.txtPriceMax}&categoty=${param.categoty}">${i}</a>
                    </c:forEach>
                </c:if>

                <c:if test="${sessionScope.USER_LOGIN.role eq'USER'}">
                    ${requestScope.ADD_SUSSCESS}
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>productName</th>
                                <th>image</th>
                                <th>description</th>
                                <th>price</th>
                                <th>createDate</th>
                                <th>category</th>
                                <th>Add to Cart</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="list" varStatus="counter" items="${sessionScope.LIST_PRODUCT}">
                                <tr style="height: 150px">
                                    <td>${counter.count}</td>
                                    <td>${list.productName}</td>
                                    <td>
                                        <img src="${list.image}" alt="tttt" width="150px" height="auto" /> 
                                    </td>
                                    <td>${list.description}</td>
                                    <td>${list.price}</td>
                                    <td>${list.createDate}</td>
                                    <td>${list.category}</td>
                                    <td>
                                        <c:url var="Add" value="MainController">
                                            <c:param name="btnAction" value="Add"></c:param>
                                            <c:param name="producID" value="${list.productID}"></c:param>
                                        </c:url>
                                        <a href="${Add}">Add</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <c:forEach var="i" begin="1" end="${sessionScope.NUM_PAGE}">
                        <a href="SearchController?index=${i}&txtSearch=${param.txtSearch}&txtPriceMin=${param.txtPriceMin}&txtPriceMax=${param.txtPriceMax}&categoty=${param.categoty}">${i}</a>
                    </c:forEach>
                </c:if>

                <c:if test="${sessionScope.USER_LOGIN.role ne'USER' and sessionScope.USER_LOGIN.role ne'ADMIN'}">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No</th>
                                <th>productName</th>
                                <th>image</th>
                                <th>description</th>
                                <th>price</th>
                                <th>createDate</th>
                                <th>category</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="list" varStatus="counter" items="${sessionScope.LIST_PRODUCT}">
                                <tr style="height: 150px">
                                    <td>${counter.count}</td>
                                    <td>${list.productName}</td>
                                    <td>
                                        <img src="${list.image}" alt="tttt" width="150px" height="auto" /> 
                                    </td>
                                    <td>${list.description}</td>
                                    <td>${list.price}</td>
                                    <td>${list.createDate}</td>
                                    <td>${list.category}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <c:forEach var="i" begin="1" end="${sessionScope.NUM_PAGE}">
                        <a href="SearchController?index=${i}&txtSearch=${param.txtSearch}&txtPriceMin=${param.txtPriceMin}&txtPriceMax=${param.txtPriceMax}&categoty=${param.categoty}">${i}</a>
                    </c:forEach>
                </c:if>
            </c:if>
        </c:if>

    </body>
</html>
