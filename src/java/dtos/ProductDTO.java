/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author tuanv
 */
public class ProductDTO implements Serializable{
    private String productID;
    private String productName;
    private int quantity;
    private String image;
    private String description;
    private float price;
    private String createDate;
    private boolean status;
    private String category;

    public ProductDTO() {
    }

    public ProductDTO(String productID, String productName, int quantity, String image, String description, float price, String createDate, boolean status, String category) {
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.image = image;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.status = status;
        this.category = category;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
