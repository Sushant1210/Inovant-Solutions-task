package com.example.inovantsolutionstask.Modal;

public class MoreItemsModal {

    String brandName, dressName, currencyCode, finalAmt, regularAmt, itemImage;

    public MoreItemsModal(String brandName, String dressName, String currencyCode, String finalAmt, String regularAmt, String itemImage) {
        this.brandName = brandName;
        this.dressName = dressName;
        this.currencyCode = currencyCode;
        this.finalAmt = finalAmt;
        this.regularAmt = regularAmt;
        this.itemImage = itemImage;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public String getDressName() {
        return dressName;
    }

    public void setDressName(String dressName) {
        this.dressName = dressName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getFinalAmt() {
        return finalAmt;
    }

    public void setFinalAmt(String finalAmt) {
        this.finalAmt = finalAmt;
    }

    public String getRegularAmt() {
        return regularAmt;
    }

    public void setRegularAmt(String regularAmt) {
        this.regularAmt = regularAmt;
    }
}

