/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.ProductDAO;
import daos.RecordDAO;
import dtos.ProductDTO;
import dtos.ProductErrorDTO;
import dtos.UserDTO;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig

/**
 *
 * @author tuanv
 */
public class UpdateProductController extends HttpServlet {

    public static final String SUCCESS = "SearchController";
    public static final String ERROR = "shop.jsp";
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
            if (user.getRole().equals("ADMIN")) {
                boolean check = true;
                ProductErrorDTO proE = new ProductErrorDTO("", "", "", "", "");
                String productID = request.getParameter("txtproID");
                String productName = request.getParameter("txtproName");
                if (productName.trim().isEmpty()) {
                    proE.setProductNameError("Enter product name");
                    check = false;
                }
                Part imagepath = request.getPart("txtimagenew");
                String imagenew = "";
                String imagenewName = imagepath.getSubmittedFileName();
                if (imagenewName.isEmpty()) {
                    imagenew = request.getParameter("image").split("/")[1];
                } else {
                    String mypath = getServletContext().getRealPath("");
                    File file = new File(mypath.substring(0, mypath.lastIndexOf("\\build")) + "\\web\\picture");
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    imagepath.write(mypath.substring(0, mypath.lastIndexOf("\\build")) + "\\web\\picture\\" + imagenewName);
                    imagenew=imagenewName;
                }
                String description = request.getParameter("txtprodescription");
                if (description.trim().isEmpty()) {
                    proE.setProductNameError("Enter product description");
                    check = false;
                }
                String txtquantity = request.getParameter("txtquantity");
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(txtquantity);
                    if (quantity < 0) {
                        check = false;
                        proE.setQuantityError("quantity >0");
                    }
                } catch (Exception e) {
                    check = false;
                    proE.setQuantityError("quantity is number");
                }
                String txtprice = request.getParameter("txtprice");
                float price = 0;
                try {
                    price = Float.parseFloat(txtprice);
                    if (price < 0) {
                        check = false;
                        proE.setPriceError("price >0");
                    }
                } catch (Exception e) {
                    check = false;
                    proE.setPriceError("price is number");
                }
                String createDate = request.getParameter("txtcreateDate");
                String category = request.getParameter("cbcategory");
                String status = request.getParameter("cbstatus");
                if (check) {
                    ProductDTO prodto = new ProductDTO(productID, productName, quantity, imagenew, description, price, createDate, Boolean.parseBoolean(status), category);
                    ProductDAO prodao = new ProductDAO();
                    prodao.updateProduct(prodto);
                    RecordDAO record = new RecordDAO();
                    record.recordUpdate(user.getUserID(), productID);
                    url = SUCCESS;
                }
                request.setAttribute("Pro_UPDATE_E", proE);
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
