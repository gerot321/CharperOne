package com.example.gerrys.charperone.Model;

import com.google.firebase.database.PropertyName;

/**
 * Created by Cj_2 on 2017-11-12.
 */

public class Product {
    private String name, image, description, price, stock, merchantId,productId;

    public Product(){

    }

    public Product(String Id,String Name, String Image, String Description, String Price, String Stock, String MerchantId) {
        productId=Id;
        name = Name;
        image = Image;
        description = Description;
        price = Price;
        stock = Stock;
        merchantId = MerchantId;
    }
    @PropertyName("Name")
    public String getName() {
        return name;
    }
    @PropertyName("Name")
    public void setName(String Name) {
        name = Name;
    }
    @PropertyName("Image")
    public String getImage() {
        return image;
    }
    @PropertyName("Image")
    public void setImage(String Image) {
        image = Image;
    }
    @PropertyName("Description")
    public String getDescription() {
        return description;
    }
    @PropertyName("Description")
    public void setDescription(String Description) {
        description = Description;
    }
    @PropertyName("Price")
    public String getPrice() {
        return price;
    }
    @PropertyName("Price")
    public void setPrice(String Price) {
        price = Price;
    }
    @PropertyName("Stock")
    public String getStock() {
        return stock;
    }
    @PropertyName("Stock")
    public void setStock(String Stock) {
        stock = Stock;
    }
    @PropertyName("MerchantId")
    public String getMerchantId() {
        return merchantId;
    }
    @PropertyName("MerchantId")
    public void setMerchantId(String MerchantId) {
        merchantId = MerchantId;
    }
}
