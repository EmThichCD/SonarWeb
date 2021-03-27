/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@MultipartConfig 
/**
 *
 * @author tuanv
 */
public class MainController extends HttpServlet {

    public static final String LOGIN = "LoginController";
    public static final String LOGOUT = "LogoutController";
    public static final String LOGIN_PAGE = "login.jsp";
    public static final String CREATE_PAGE = "createpro.jsp";
    public static final String CREATE = "CreateController";
    public static final String SEARCH = "SearchController";
    public static final String ADD = "AddController";
    public static final String VIEWCART_PAGE = "viewcart.jsp";
    public static final String UPDATE_PRODUCT = "UpdateProductController";
    public static final String UPDATE_CART = "UpdateCartController";
    public static final String DELETE_CART = "DeleteCartController";
    public static final String CHECKOUT = "CheckoutController";
    public static final String HISTORY_PAGE = "HistoryController";
    public static final String DELETEPRODUCT = "DeleteController";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        /* TODO output your page here. You may use following sample code. */
        String url = LOGIN_PAGE;
        try {
            String action = request.getParameter("btnAction");
            if ("Login".equals(action)) {
                url = LOGIN;
            }
            if ("LoginPage".equals(action)) {
                url = LOGIN_PAGE;
            }
            if ("Logout".equals(action)) {
                url = LOGOUT;
            }
            if ("CreateProduct".equals(action)) {
                url = CREATE_PAGE;
            }
            if ("Create".equals(action)) {
                url = CREATE;
            }
            if ("Search".equals(action)) {
                request.setAttribute("NHAN", "xacnhan");
                url = SEARCH;
            }
            if ("Add".equals(action)) {
                url = ADD;
            }
            if ("CartPage".equals(action)) {
                url = VIEWCART_PAGE;
            }
            if ("update Product".equals(action)) {
                url = UPDATE_PRODUCT;
            }
            if ("Update Cart".equals(action)) {
                url = UPDATE_CART;
            }
            if ("DeleteCart".equals(action)) {
                url = DELETE_CART;
            }
            if ("CheckOut".equals(action)) {
                url = CHECKOUT;
            }
            if ("HistoryPage".equals(action)) {
                url = HISTORY_PAGE;
            }
            if ("Search History".equals(action)) {
                url = HISTORY_PAGE;
            }
            if ("DeleteProduct".equals(action)) {
                url = DELETEPRODUCT;
            }
        } catch (Exception e) {
            log(e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
