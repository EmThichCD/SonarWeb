/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.UserDAO;
import dtos.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tuanv
 */
public class LoginController extends HttpServlet {

    public static final String SUCCESS = "SearchController?index=1&txtPriceMin=0&txtPriceMax=0&categoty=product";
    public static final String ADMIN = "admin.jsp";
    public static final String ERORR = "login.jsp";

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
        String url = ERORR;
        try {
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            if (userID.trim().isEmpty()) {
                request.setAttribute("ERRORUSER", "Enter UserID");
            } else {
                if (password.trim().isEmpty()) {
                    request.setAttribute("ERRORPASSWORD", "Enter Password");
                } else {
                    UserDAO udao = new UserDAO();
                    UserDTO user = udao.checkLogin(userID, password);
                    if (user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("USER_LOGIN", user);
                        if (user.getRole().equals("USER")) {
                            session.setAttribute("txtsearch", "");
                            url = SUCCESS;
                        } else {
                            url = ADMIN;
                        }
                    } else {
                        request.setAttribute("ERROR", "UserID or Password is wrong");
                    }
                }
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
