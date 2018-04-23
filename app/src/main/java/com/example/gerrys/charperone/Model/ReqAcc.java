package com.example.gerrys.charperone.Model;

import com.google.firebase.database.PropertyName;

/**
 * Created by Cj_2 on 2017-11-12.
 */

/**
 * Created by Cj_2 on 2017-11-12.
 */

public class ReqAcc {
    private String phone, name, pass, image, status, adress, gender, email;

    public ReqAcc(){

    }

    public ReqAcc(String Phone, String Name, String Image, String Pass, String Status, String Adress, String Gender,String Email) {
        name = Name;
        image = Image;
        phone = Phone;
        pass = Pass;
        status = Status;
        adress = Adress;
        gender = Gender;
        email = Email;
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
    public void setStatus(String Status) { status = Status; }
    @PropertyName("Address")
    public String getAddress() {
        return adress;
    }
    @PropertyName("Address")
    public void setAddress(String Adress) { adress = Adress; }
    @PropertyName("Gender")
    public String getGender() {
        return gender;
    }
    @PropertyName("Gender")
    public void setGender(String Gender) { gender = Gender; }
    @PropertyName("Email")
    public String getEmail() {
        return email;
    }
    @PropertyName("Email")
    public void setEmail(String Email) { email = Email; }
}