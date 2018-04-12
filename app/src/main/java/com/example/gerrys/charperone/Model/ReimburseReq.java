package com.example.gerrys.charperone.Model;

/**
 * Created by Gerrys on 11/04/2018.
 */

public class ReimburseReq {
    String reqId;
    String merchantId;
    String bankId;
    String an;
    String status;
    String image;

    public ReimburseReq(){

    }
    public ReimburseReq(String merchantId, String bankId, String an, String status, String image){
        this.merchantId=merchantId;
        this.bankId=bankId;
        this.an=an;
        this.image=image;
        this.status=status;
    }

    public String getImage() {
        return image;
    }

    public String getAn() {
        return an;
    }

    public String getBankId() {
        return bankId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setAn(String an) {
        this.an = an;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }
}
