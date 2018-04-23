package com.example.gerrys.charperone.Model;

import com.google.firebase.database.PropertyName;

/**
 * Created by Cj_2 on 2017-11-12.
 */

public class Promo {
    private String name, description, code, value, promoId, image, status, url;

    public Promo(){

    }

    public Promo(String Id,String Image, String Name, String Description, String Value, String Code,String Status, String Url) {
        promoId=Id;
        name = Name;
        description = Description;
        value = Value;
        code = Code;
        image = Image;
        status = Status;
        Url = url;
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
    @PropertyName("Value")
    public String getValue() {
        return value;
    }
    @PropertyName("Value")
    public void setValue(String Value) {
        value = Value;
    }
    @PropertyName("Code")
    public String getCode() {
        return code;
    }
    @PropertyName("Code")
    public void setCode(String Code) {
        code = Code;
    }
    @PropertyName("PromoId")
    public String getPromoId() {
        return promoId;
    }
    @PropertyName("PromoId")
    public void setPromoId(String PromoId) {
        promoId = PromoId;
    }
    @PropertyName("Status")
    public String getStatus() {
        return status;
    }
    @PropertyName("Status")
    public void setStatus(String Status) {
        status = Status;
    }
    @PropertyName("Url")
    public String getUrl() {
        return url;
    }
    @PropertyName("Url")
    public void setUrl(String Url) {
        url = Url;
    }
}
