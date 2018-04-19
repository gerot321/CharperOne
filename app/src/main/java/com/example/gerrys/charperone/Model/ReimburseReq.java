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
    String amount;
    String image;

    public ReimburseReq(){

    }
    public ReimburseReq(String merchantId,String bankId,String an,String status,String image,String amount){
        this.merchantId=merchantId;
        this.bankId=bankId;
        this.an=an;
        this.amount=amount;
        this.image=image;
        this.status=status;
    }


    public String getStatus() {
        return status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    public void setStatus(String status) {
        this.status = status;
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
