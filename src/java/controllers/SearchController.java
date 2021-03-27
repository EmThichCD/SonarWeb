/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.ProductDAO;
import dtos.ProductDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author tuanv
 */
public class SearchController extends HttpServlet {

    public static final String ERROR = "shop.jsp";
    public static final String SUCCESS = "shop.jsp";

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
            ArrayList<ProductDTO> list = new ArrayList<>();
            String txtpriceMin = request.getParameter("txtPriceMin");
            float priceMin = 0;
            if (txtpriceMin == null) {
                txtpriceMin = "";
                if (txtpriceMin.isEmpty()) {
                    priceMin = 0;
                } else {
                    priceMin = (float) session.getAttribute("priceMin");
                }
            } else {
                if (txtpriceMin.isEmpty()) {
                    priceMin = 0;
                } else {
                    priceMin = Float.parseFloat(txtpriceMin);
                }
            }
            session.setAttribute("priceMin", priceMin);
            priceMin = (float) session.getAttribute("priceMin");
            float priceMax = 0;
            ProductDAO prodao = new ProductDAO();
            String txtpriceMax = request.getParameter("txtPriceMax");
            if (txtpriceMax == null) {
                txtpriceMax = "";
                if (txtpriceMax.isEmpty()) {
                    priceMax = prodao.getPriceMax();
                } else {
                    priceMax = (float) session.getAttribute("priceMax");
                }
            } else {
                if (txtpriceMax.isEmpty()) {
                    priceMax = prodao.getPriceMax();
                } else {
                    priceMax = Float.parseFloat(txtpriceMax);
                    if (priceMax == 0) {
                        priceMax = prodao.getPriceMax();
                    }
                }
            }
            session.setAttribute("priceMax", priceMax);
            priceMax = (float) session.getAttribute("priceMax");
            String category = request.getParameter("categoty");
            if (category == null) {
                category = "";
                if (category.isEmpty()) {
                    category = "";
                } else {
                    category = (String) session.getAttribute("category");
                }
            } else {
                if (category.equals("product")) {
                    category = "";
                }
            }
            session.setAttribute("category", category);
            category = (String) session.getAttribute("category");
            String txtsearch = request.getParameter("txtSearch");
            if (txtsearch == null) {
                txtsearch = "";
                if (txtsearch.isEmpty()) {
                    txtsearch = "";
                } else {
                    txtsearch = (String) session.getAttribute("txtsearch");
                }
            } else {
                if (txtsearch.isEmpty()) {
                    txtsearch = "";
                }
            }
            session.setAttribute("txtsearch", txtsearch);
            txtsearch = (String) session.getAttribute("txtsearch");
            UserDTO user = (UserDTO) session.getAttribute("USER_LOGIN");
            String indexString = request.getParameter("index");
            int index = 0;
            if (indexString == null) {
                if (session.getAttribute("index") == null) {
                    index = 1;
                } else {
                    index = (int) session.getAttribute("index");
                }
            } else {
                if (indexString.isEmpty()) {
                    index = 1;
                } else {
                    index = Integer.parseInt(indexString);
                }
            }
            if(request.getAttribute("NHAN")!=null){
                index=1;
            }
            session.setAttribute("index", index);
            index = (int) session.getAttribute("index");
            int numpage = 0;
            if (user == null) {
                list = prodao.getProductforUser(index, category, txtsearch, priceMin, priceMax);
                numpage = prodao.coutnProduct("USER", category, txtsearch, priceMin, priceMax);
            } else {
                if (user.getRole().equals("ADMIN")) {
                    list = prodao.getProductforAdmin(index, category, txtsearch, priceMin, priceMax);
                    numpage = prodao.coutnProduct("ADMIN", category, txtsearch, priceMin, priceMax);
                } else {
                    list = prodao.getProductforUser(index, category, txtsearch, priceMin, priceMax);
                    numpage = prodao.coutnProduct("USER", category, txtsearch, priceMin, priceMax);
                }
            }
            ArrayList<String> listpayment = prodao.getPayment();
            session.setAttribute("LIST_PAY", listpayment);
            session.setAttribute("NUM_PAGE", numpage);
            session.setAttribute("LIST_PRODUCT", list);
            url=SUCCESS;
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
