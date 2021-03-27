/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.ProductDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.DBUtil;

/**
 *
 * @author tuanv
 */
public class ProductDAO {

    public ArrayList<ProductDTO> getProductforAdmin(int index, String category, String search, float pricemin, float pricemax) throws SQLException, ClassNotFoundException {
        ArrayList<ProductDTO> list = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "with x as(SELECT ROW_NUMBER() over (order by a.createDate desc) as row,productID,productName,quantity,image,description,price,createDate,status,b.categoryName\n"
                    + "FROM tblProduct a JOIN tblCategory b ON a.categoryID=b.categoryID\n"
                    + "WHERE b.categoryName like ? AND productName like ?  AND a.price>=? AND price <=?)\n"
                    + "SELECT row,productID,productName,quantity,image,description,price,createDate,status,categoryName FROM x WHERE row BETWEEN ? AND ?";
            pst = conn.prepareStatement(url);
            pst.setString(1, "%" + category + "%");
            pst.setString(2, "%" + search + "%");
            pst.setFloat(3, pricemin);
            pst.setFloat(4, pricemax);
            pst.setString(5, (index - 1) * 20 + 1 + "");
            pst.setString(6, index * 20 + "");
            rs = pst.executeQuery();
            while (rs.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                String productID = rs.getString("productID");
                String producName = rs.getString("productName");
                int quantity = rs.getInt("quantity");
                String image = rs.getString("image");
                String description = rs.getString("description");
                float price = rs.getFloat("price");
                String createDate = rs.getString("createDate");
                boolean status = rs.getBoolean("status");
                String categoryName = rs.getString("categoryName");
                ProductDTO prodto = new ProductDTO(productID, producName, quantity, image, description, price, createDate, status, categoryName);
                list.add(prodto);
            }
        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public int coutnProduct(String role, String category, String search, float pricemin, float pricemax) throws SQLException, ClassNotFoundException {
        int result = -1;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "";
            if (role.equals("ADMIN")) {
                url = "SELECT count(a.productID) as countpro\n"
                        + "FROM tblProduct a JOIN tblCategory b ON a.categoryID=b.categoryID\n"
                        + "WHERE b.categoryName like ? AND productName like ?  AND a.price>=? AND price <=?";
            } else {
                url = "SELECT count(a.productID) as countpro\n"
                        + "FROM tblProduct a JOIN tblCategory b ON a.categoryID=b.categoryID\n"
                        + "WHERE a.status=1 AND a.quantity>0 AND b.categoryName LIKE ? AND productName LIKE ? AND a.price>=? AND price <=?";
            }
            pst = conn.prepareStatement(url);
            pst.setString(1, "%" + category + "%");
            pst.setString(2, "%" + search + "%");
            pst.setFloat(3, pricemin);
            pst.setFloat(4, pricemax);
            rs = pst.executeQuery();
            if (rs.next()) {
                result = (int) Math.ceil((double) rs.getInt("countpro") / 20);
            }
        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public ArrayList<ProductDTO> getProductforUser(int index, String category, String search, float pricemin, float pricemax) throws SQLException, ClassNotFoundException {
        ArrayList<ProductDTO> list = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "with x as(SELECT ROW_NUMBER() over (order by a.createDate desc) as row,productID,productName,quantity,image,description,price,createDate,status,b.categoryName\n"
                    + "FROM tblProduct a JOIN tblCategory b ON a.categoryID=b.categoryID\n"
                    + "WHERE a.status=1 AND a.quantity>0 AND b.categoryName LIKE ? AND productName LIKE ? AND a.price>=? AND price <=?)\n"
                    + "SELECT row,productID,productName,quantity,image,description,price,createDate,status,categoryName FROM x WHERE row BETWEEN ? AND ?";
            pst = conn.prepareStatement(url);
            pst.setString(1, "%" + category + "%");
            pst.setString(2, "%" + search + "%");
            pst.setFloat(3, pricemin);
            pst.setFloat(4, pricemax);
            pst.setString(5, (index - 1) * 20 + 1 + "");
            pst.setString(6, index * 20 + "");
            rs = pst.executeQuery();
            while (rs.next()) {
                if (list == null) {
                    list = new ArrayList<>();
                }
                String productID = rs.getString("productID");
                String producName = rs.getString("productName");
                int quantity = rs.getInt("quantity");
                String image = rs.getString("image");
                String description = rs.getString("description");
                float price = rs.getFloat("price");
                String createDate = rs.getString("createDate");
                boolean status = rs.getBoolean("status");
                String categoryName = rs.getString("categoryName");
                ProductDTO prodto = new ProductDTO(productID, producName, quantity, image, description, price, createDate, status, categoryName);
                if (quantity > 0 && status) {
                    list.add(prodto);
                }
            }
        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public ProductDTO getProductbyID(String productID) throws SQLException, ClassNotFoundException {
        ProductDTO result = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT productID,productName,quantity,image,description,price,createDate,status,b.categoryName\n"
                    + "FROM tblProduct a JOIN tblCategory b ON a.categoryID=b.categoryID\n"
                    + "WHERE productID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, productID);
            rs = pst.executeQuery();
            if (rs.next()) {
                if (result == null) {
                    result = new ProductDTO();
                }
                String producName = rs.getString("productName");
                int quantity = rs.getInt("quantity");
                String image = rs.getString("image");
                String description = rs.getString("description");
                float price = rs.getFloat("price");
                String createDate = rs.getString("createDate");
                boolean status = rs.getBoolean("status");
                String categoryName = rs.getString("categoryName");
                result = new ProductDTO(productID, producName, quantity, image, description, price, createDate, status, categoryName);
            }
        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public float getPriceMax() throws SQLException, ClassNotFoundException {
        float result = 0;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT MAX(price) as price\n"
                    + "FROM tblProduct";
            pst = conn.prepareStatement(url);
            rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getFloat("price");
            }
        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public String getCategoryID(String categoryName) throws SQLException, ClassNotFoundException {
        String result = "";
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT categoryID\n"
                    + "FROM tblCategory\n"
                    + "WHERE categoryName=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, categoryName);
            rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getString("categoryID");
            }
        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return result;
    }

    public void createProduct(ProductDTO prodto) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "INSERT [dbo].[tblProduct] ([productName], [quantity], [image], [description], [price], [createDate], [status], [categoryID]) VALUES (?, ?, ?, ?, ?, CAST(? AS Date), 1, ?)";
            pst = conn.prepareStatement(url);
            pst.setString(1, prodto.getProductName());
            pst.setString(2, prodto.getQuantity() + "");
            pst.setString(3, prodto.getImage());
            pst.setString(4, prodto.getDescription());
            pst.setString(5, prodto.getPrice() + "");
            pst.setString(6, prodto.getCreateDate());
            pst.setString(7, getCategoryID(prodto.getCategory()));
            pst.executeUpdate();
        }finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void updateProduct(ProductDTO prodto) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "UPDATE tblProduct\n"
                    + " SET productName=?,description=?,image=?,price=?,quantity=?,status=?,categoryID=?\n"
                    + " WHERE productID=?;";
            pst = conn.prepareStatement(url);
            pst.setString(1, prodto.getProductName());
            pst.setString(2, prodto.getDescription());
            pst.setString(3, "picture/" + prodto.getImage());
            pst.setString(4, prodto.getPrice() + "");
            pst.setString(5, prodto.getQuantity() + "");
            if (prodto.getStatus()) {
                pst.setString(6, 1 + "");
            } else {
                pst.setString(6, 0 + "");
            }

            pst.setString(7, getCategoryID(prodto.getCategory()));
            pst.setString(8, prodto.getProductID());
            pst.executeUpdate();
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public ArrayList<String> getPayment() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<String> list = null;
        try {
            conn = DBUtil.getConnection();
            String url = " SELECT payName\n"
                    + " FROM tblPayment ";
            pst = conn.prepareStatement(url);
            rs = pst.executeQuery();
            while (rs.next()) {
                String payname = rs.getString("payName");
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(payname);
            }

        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public boolean CheckproductToCheckout(int quantity, String productID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = " SELECT productID\n"
                    + " FROM tblProduct\n"
                    + " WHERE quantity>? AND status=1 AND productID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, quantity + "");
            pst.setString(2, productID);
            rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    public int getQuantityProduct(String productID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int quantity = 0;
        try {
            conn = DBUtil.getConnection();
            String url = " SELECT quantity\n"
                    + " FROM tblProduct\n"
                    + " WHERE productID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, productID);
            rs = pst.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt("quantity");
            }
        }finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return quantity;
    }

    public void updateQuantity(int quantity, String productID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "UPDATE tblProduct\n"
                    + "SET quantity=?\n"
                    + "WHERE productID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, getQuantityProduct(productID) - quantity + "");
            pst.setString(2, productID);
            pst.executeUpdate();
        }finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public void deleteProduct(String productID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "UPDATE tblProduct\n"
                    + "SET status=0\n"
                    + "WHERE productID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, productID);
            pst.executeUpdate();
        }finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
