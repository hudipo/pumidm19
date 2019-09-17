package com.hudipo.pum_indomaret.model;

public class ResponseModel {
    private int id;
    private String trxNumber;
    private String status;

    public ResponseModel(int id, String trxNumber, String status) {
        this.id = id;
        this.trxNumber = trxNumber;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrxNumber() {
        return trxNumber;
    }

    public void setTrxNumber(String trxNumber) {
        this.trxNumber = trxNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
