package com.example.gerrys.charperone.Model;

import com.google.firebase.database.PropertyName;

/**
 * Created by Cj_2 on 2017-11-12.
 */

public class ReqAccCour {
    private String phone, name, pass, image, status;

    public ReqAccCour(){

    }

    public ReqAccCour(String Phone, String Name, String Image, String Pass, String Status) {
        name = Name;
        image = Image;
        phone = Phone;
        pass = Pass;
        status = Status;
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
    @PropertyName("Phone")
    public String getPhone() {
        return phone;
    }
    @PropertyName("Phone")
    public void setPhone(String Phone) { phone = Phone; }
    @PropertyName("Pass")
    public String getPass() {
        return pass;
    }
    @PropertyName("Pass")
    public void setPass(String Pass) { pass = Pass; }
    @PropertyName("Status")
    public String getStatus() {
        return status;
    }
    @PropertyName("Status")
    public void setStatus(String Status) { pass = Status; }
}