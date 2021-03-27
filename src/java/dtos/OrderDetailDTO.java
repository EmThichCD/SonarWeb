/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author tuanv
 */
public class OrderDetailDTO {
    private String orderDetailID;
    private ProductDTO product;
    private int quantity;
    private float price;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String orderDetailID, ProductDTO product, int quantity, float price) {
        this.orderDetailID = orderDetailID;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public String getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(String orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
}
