package com.example.gerrys.charperone.Model;

public class Upload {
    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }
}
