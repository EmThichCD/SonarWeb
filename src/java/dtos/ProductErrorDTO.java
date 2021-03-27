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
public class ProductErrorDTO {
    private String productNameError;
    private String quantityError;
    private String imageError;
    private String descriptionError;
    private String priceError;

    public ProductErrorDTO() {
    }

    public ProductErrorDTO(String productNameError, String quantityError, String imageError, String descriptionError, String priceError) {
        this.productNameError = productNameError;
        this.quantityError = quantityError;
        this.imageError = imageError;
        this.descriptionError = descriptionError;
        this.priceError = priceError;
    }

    public String getProductNameError() {
        return productNameError;
    }

    public void setProductNameError(String productNameError) {
        this.productNameError = productNameError;
    }

    public String getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(String quantityError) {
        this.quantityError = quantityError;
    }

    public String getImageError() {
        return imageError;
    }

    public void setImageError(String imageError) {
        this.imageError = imageError;
    }

    public String getDescriptionError() {
        return descriptionError;
    }

    public void setDescriptionError(String descriptionError) {
        this.descriptionError = descriptionError;
    }

    public String getPriceError() {
        return priceError;
    }

    public void setPriceError(String priceError) {
        this.priceError = priceError;
    }
    
    
}
