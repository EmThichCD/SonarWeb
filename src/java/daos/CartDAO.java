/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.CartDTO;
import dtos.OrderDetailDTO;
import dtos.ProductDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import utils.DBUtil;

/**
 *
 * @author tuanv
 */
public class CartDAO {

    public void createOrder(CartDTO cart) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = " INSERT tblOrder (orderDate,userID,totalPrice,payId)\n"
                    + " VALUES(?,?,?,?)";
            pst = conn.prepareStatement(url);
            pst.setString(1, cart.getOrderDate());
            pst.setString(2, cart.getUserID());
            pst.setString(3, cart.getTotalPrice() + "");
            pst.setString(4, cart.getPayID());
            pst.executeUpdate();
            HashMap<String, OrderDetailDTO> hash = cart.getHash();
            Set<String> key = hash.keySet();
            for (String kstring : key) {
                createOrderDetal(hash.get(kstring), getOrderID(cart.getUserID()));
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public String getOrderID(String userID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String result = "";
        try {
            conn = DBUtil.getConnection();
            String url = " SELECT max(orderID) as orderID\n"
                    + " FROM tblOrder\n"
                    + " WHERE userID=?";
            pst = conn.prepareStatement(url);
            pst.setString(1, userID);
            rs = pst.executeQuery();
            if (rs.next()) {
                result = rs.getString("orderID");
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

    public void createOrderDetal(OrderDetailDTO orderDetail, String orderID) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = DBUtil.getConnection();
            String url = "INSERT tblOrderDetail (orderID,productID,quantity,price)\n"
                    + " VALUES(?,?,?,?)";
            pst = conn.prepareStatement(url);
            pst.setString(1, orderID);
            pst.setString(2, orderDetail.getProduct().getProductID());
            pst.setString(3, orderDetail.getQuantity() + "");
            pst.setString(4, orderDetail.getPrice() + "");
            pst.executeUpdate();
            ProductDAO prodao=new ProductDAO();
            prodao.updateQuantity(orderDetail.getQuantity(), orderDetail.getProduct().getProductID());
        }finally {
            if (pst != null) {
                pst.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public ArrayList<CartDTO> getHistory(String date, String productname, String userID) throws SQLException, ClassNotFoundException {
        ArrayList<CartDTO> list = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "";
            if (date.isEmpty()) {
                url = "SELECT a.orderID,a.orderDate,a.payId,a.totalPrice\n"
                        + "FROM tblOrder a JOIN tblOrderDetail b ON a.orderID=b.orderID\n"
                        + "				JOIN tblProduct c ON b.productID=c.productID\n"
                        + "WHERE c.productName like ? AND a.userID=?";
            } else {
                url = "SELECT a.orderID,a.orderDate,a.payId,a.totalPrice\n"
                        + "FROM tblOrder a JOIN tblOrderDetail b ON a.orderID=b.orderID\n"
                        + "				JOIN tblProduct c ON b.productID=c.productID\n"
                        + "WHERE a.orderDate=? AND c.productName like ? AND a.userID=?";
            }
            pst = conn.prepareStatement(url);
            if (!date.isEmpty()) {
                pst.setString(1, date);
                pst.setString(2, "%" + productname + "%");
                pst.setString(3, userID);
            } else {
                pst.setString(1, "%" + productname + "%");
                pst.setString(2, userID);
            }
            rs = pst.executeQuery();
            while (rs.next()) {
                String orderID = rs.getString("orderID");
                String orderDate = rs.getString("orderDate");
                String payId = rs.getString("payId");
                float toatlprice = rs.getFloat("totalPrice");
                if (list == null) {
                    list = new ArrayList<>();
                }
                CartDTO cart = new CartDTO(orderID, userID, orderDate, toatlprice, payId, new HashMap<String, OrderDetailDTO>());
                if(list.size()==0){
                    list.add(cart);
                }else{
                    boolean check=true;
                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getOrderID().equals(cart.getOrderID())){
                            check=false;
                        }
                    }
                    if(check){
                        list.add(cart);
                    }
                }
            }
            for (int i=0; i < list.size(); i++) {
                list.get(i).setHash(getHistoryOrder(list.get(i).getOrderID()));
            }
        } finally {
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

    public HashMap<String, OrderDetailDTO> getHistoryOrder(String orderID) throws SQLException, ClassNotFoundException {
        HashMap<String, OrderDetailDTO> hash = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String url = "SELECT orderDetailID,productID,quantity,price\n"
                    + "FROM tblOrderDetail\n"
                    + "WHERE orderID=?";
            pst=conn.prepareStatement(url);
            pst.setString(1, orderID);
            rs=pst.executeQuery();
            while(rs.next()){
                String orderDetailID=rs.getString("orderDetailID");
                String productID=rs.getString("productID");
                float price=rs.getFloat("price");
                int quantity=rs.getInt("quantity");
                ProductDAO prodao=new ProductDAO();
                ProductDTO prodto=prodao.getProductbyID(productID);
                if(hash==null){
                    hash=new HashMap<>();
                }
                OrderDetailDTO orderDetail=new OrderDetailDTO(orderDetailID, prodto, quantity, price);
                hash.put(productID, orderDetail);
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
        return hash;
    }
}
