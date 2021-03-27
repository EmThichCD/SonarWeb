/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.ProductDAO;
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
public class CreateController extends HttpServlet {

    public static final String ERORRUSER = "index.html";
    public static final String ERORR = "createpro.jsp";
    public static final String SUCCESS = "createpro.jsp";

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
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER_LOGIN");
            if (user.getRole().equals("ADMIN")) {
                ProductErrorDTO perror = new ProductErrorDTO("", "", "", "", "");
                String productName = request.getParameter("txtproductName");
                if (!productName.trim().isEmpty() && productName.length() <= 50) {
                    if (!request.getParameter("txtquantity").trim().isEmpty()) {
                        int quantity = Integer.parseInt(request.getParameter("txtquantity"));
                        Part imagepath = request.getPart("txtimage");
                        String imagenewName = imagepath.getSubmittedFileName();
                        //hinh
                        if (!imagenewName.trim().isEmpty()) {
                            String mypath = getServletContext().getRealPath("");
                            File file = new File(mypath.substring(0, mypath.lastIndexOf("\\build")) + "\\web\\picture");
                            if (!file.exists()) {
                                file.mkdir();
                            }
                            imagepath.write(mypath.substring(0, mypath.lastIndexOf("\\build")) + "\\web\\picture\\" + imagenewName);
                            String description = request.getParameter("txtdescription");
                            if (!description.trim().isEmpty() && description.length() <= 500) {
                                if (!request.getParameter("txtprice").trim().isEmpty()) {
                                    int price = Integer.parseInt(request.getParameter("txtprice"));
                                    String category = request.getParameter("categoty");
                                    long millis = System.currentTimeMillis();
                                    java.sql.Date date = new java.sql.Date(millis);
                                    ProductDTO prodto = new ProductDTO("", productName, quantity, "picture/" + imagenewName, description, price, date.toString(), true, category);
                                    ProductDAO pdao = new ProductDAO();
                                    pdao.createProduct(prodto);
                                    url = SUCCESS;
                                    request.setAttribute("PRODUCT_SUCCESS", "CREATE SUCCESS");
                                } else {
                                    perror.setPriceError("Enter price");
                                }
                            } else {
                                if (description.length() > 500) {
                                    perror.setDescriptionError("Description less than 500 character");
                                } else {
                                    perror.setDescriptionError("Enter description");
                                }
                            }
                        } else {
                            perror.setImageError("choise product PICTURE");
                        }
                    } else {
                        perror.setQuantityError("Enter quantity");
                    }
                } else {
                    if (productName.length() > 50) {
                        perror.setProductNameError("Product name less than 50 character");
                    } else {
                        perror.setProductNameError("Enter Product name");
                    }
                }
                request.setAttribute("PRODUCT_ERROR", perror);
            } else {
                url = ERORRUSER;
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
