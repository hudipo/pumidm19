package com.hudipo.pum_indomaret.features.requestpum.model;

public class DocumentDetailRequestModel {

    private String stringDocNumber;
    private String stringDocDate;
    private long longAmount;

    public DocumentDetailRequestModel() {
    }

    public DocumentDetailRequestModel(String stringDocNumber, String stringDocDate, int longAmount) {
        this.stringDocNumber = stringDocNumber;
        this.stringDocDate = stringDocDate;
        this.longAmount = longAmount;
    }

    public String getStringDocNumber() {
        return stringDocNumber;
    }

    public void setStringDocNumber(String stringDocNumber) {
        this.stringDocNumber = stringDocNumber;
    }

    public String getStringDocDate() {
        return stringDocDate;
    }

    public void setStringDocDate(String stringDocDate) {
        this.stringDocDate = stringDocDate;
    }

    public long getLongAmount() {
        return longAmount;
    }

    public void setLongAmount(long longAmount) {
        this.longAmount = longAmount;
    }
}
