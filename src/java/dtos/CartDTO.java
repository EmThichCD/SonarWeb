/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.HashMap;

/**
 *
 * @author tuanv
 */
public class CartDTO {
    private String orderID;
    private String userID;
    private String orderDate;
    private float totalPrice;
    private String payID;
    HashMap<String,OrderDetailDTO> hash;

    public CartDTO() {
    }

    public CartDTO(String orderID, String userID, String orderDate, float totalPrice, String payID, HashMap<String, OrderDetailDTO> hash) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.payID=payID;
        this.hash = hash;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayID() {
        return payID;
    }

    public void setPayID(String payID) {
        this.payID = payID;
    }
    

    public HashMap<String, OrderDetailDTO> getHash() {
        return hash;
    }

    public void setHash(HashMap<String, OrderDetailDTO> hash) {
        this.hash = hash;
    }
    public void add(OrderDetailDTO orderDetail){
        if(hash==null){
            this.hash=new HashMap<>();
        }
        if(this.hash.containsKey(orderDetail.getOrderDetailID())){
            int quantity=this.hash.get(orderDetail.getOrderDetailID()).getQuantity();
            orderDetail.setQuantity(quantity+1);
            float price=this.hash.get(orderDetail.getOrderDetailID()).getPrice()+orderDetail.getPrice();
            orderDetail.setPrice(price);
        }
        hash.put(orderDetail.getOrderDetailID(), orderDetail);
    }
    public void remove(OrderDetailDTO orderDetail){
        if(hash==null){
            return;
        }
        if(this.hash.containsKey(orderDetail)){
            this.hash.remove(orderDetail.getOrderDetailID());
        }
    }
    public void update(OrderDetailDTO orderDetail){
        if(hash==null){
            return;
        }
        if(hash.containsKey(orderDetail.getOrderDetailID())){
            hash.replace(orderDetail.getOrderDetailID(), orderDetail);
        }
    }
}
