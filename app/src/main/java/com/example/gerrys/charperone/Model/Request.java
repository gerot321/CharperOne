package com.example.gerrys.charperone.Model;

import java.util.List;

/**
 * Created by Cj_2 on 2017-11-26.
 */

public class Request {
    private String phone;
    private String name;

    private String total;
    private String status;
    private List<Order> shoes; // list of shoe order


    public Request() {
    }

    public Request(String phone, String name, String total, List<Order> shoes) {
        this.phone = phone;
        this.name = name;

        this.total = total;
        this.shoes = shoes;
        this.status = "0"; // default is 0, 0 = Order Placed, 1 = Shipping, 2 = Shipped
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getShoes() {
        return shoes;
    }

    public void setShoes(List<Order> shoes) {
        this.shoes = shoes;
    }
}
