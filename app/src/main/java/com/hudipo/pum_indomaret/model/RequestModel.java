package com.hudipo.pum_indomaret.model;

import java.io.Serializable;

public class RequestModel implements Serializable {

    private String stringEmployeeName;
    private int idEmployee;
    private String stringEmployeeDepartment;
    private String stringUseDate;
    private String stringRespDate;
    private String stringDocType;
    private String stringDocNumber;
    private String stringTrxType;
    private int idTrxType;
    private int intAmount;
    private String stringDescription;
    private String stringFileUri;

    public RequestModel() {
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public int getIdTrxType() {
        return idTrxType;
    }

    public void setIdTrxType(int idTrxType) {
        this.idTrxType = idTrxType;
    }

    public String getStringEmployeeName() {
        return stringEmployeeName;
    }

    public void setStringEmployeeName(String stringEmployeeName) {
        this.stringEmployeeName = stringEmployeeName;
    }

    public String getStringEmployeeDepartment() {
        return stringEmployeeDepartment;
    }

    public void setStringEmployeeDepartment(String stringEmployeeDepartment) {
        this.stringEmployeeDepartment = stringEmployeeDepartment;
    }

    public String getStringUseDate() {
        return stringUseDate;
    }

    public void setStringUseDate(String stringUseDate) {
        this.stringUseDate = stringUseDate;
    }

    public String getStringRespDate() {
        return stringRespDate;
    }

    public void setStringRespDate(String stringRespDate) {
        this.stringRespDate = stringRespDate;
    }

    public String getStringDocType() {
        return stringDocType;
    }

    public void setStringDocType(String stringDocType) {
        this.stringDocType = stringDocType;
    }

    public String getStringDocNumber() {
        return stringDocNumber;
    }

    public void setStringDocNumber(String stringDocNumber) {
        this.stringDocNumber = stringDocNumber;
    }

    public String getStringTrxType() {
        return stringTrxType;
    }

    public void setStringTrxType(String stringTrxType) {
        this.stringTrxType = stringTrxType;
    }

    public int getIntAmount() {
        return intAmount;
    }

    public void setIntAmount(int intAmount) {
        this.intAmount = intAmount;
    }

    public String getStringDescription() {
        return stringDescription;
    }

    public void setStringDescription(String stringDescription) {
        this.stringDescription = stringDescription;
    }

    public String getStringFileUri() {
        return stringFileUri;
    }

    public void setStringFileUri(String stringFileUri) {
        this.stringFileUri = stringFileUri;
    }
}
