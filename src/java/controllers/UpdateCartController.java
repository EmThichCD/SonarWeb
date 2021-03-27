/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.ProductDAO;
import dtos.CartDTO;
import dtos.OrderDetailDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tuanv
 */
public class UpdateCartController extends HttpServlet {

    public static final String SUCCESS = "viewcart.jsp";
    public static final String ERROR = "viewcart.jsp";
    public static final String ERRORUSER = "index.html";

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
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER_LOGIN");
            if (user.getRole().equals("USER")) {
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                if (cart != null) {
                    String productID = request.getParameter("txtproductID");
                    String txtquantity = request.getParameter("txtquantity");
                    int quantity = 0;
                    if (!txtquantity.trim().isEmpty()) {
                        try {
                            quantity = Integer.parseInt(txtquantity);
                            ProductDAO prodao = new ProductDAO();
                            int quantitytocheck = prodao.getQuantityProduct(productID);
                            if (quantity > quantitytocheck) {
                                request.setAttribute("QUANTITYERROR", "product not enough");
                            } else {
                                if (quantity > 0) {
                                    HashMap<String, OrderDetailDTO> hash = cart.getHash();
                                    if (hash.containsKey(productID)) {
                                        OrderDetailDTO orderDetail = hash.get(productID);
                                        orderDetail.setQuantity(quantity);
                                        orderDetail.setPrice(quantity * (orderDetail.getProduct().getPrice()));
                                        hash.replace(productID, orderDetail);
                                        cart.setHash(hash);
                                        session.setAttribute("CART", cart);
                                        url = SUCCESS;
                                    }
                                } else {
                                    request.setAttribute("QUANTITYERROR", "quantity is num >0");
                                }
                            }

                        } catch (Exception e) {
                            request.setAttribute("QUANTITYERROR", "quantity is num >0");
                        }
                    } else {
                        request.setAttribute("QUANTITYERROR", "quantity is num >0");
                    }
                }
            } else {
                url = ERRORUSER;
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
