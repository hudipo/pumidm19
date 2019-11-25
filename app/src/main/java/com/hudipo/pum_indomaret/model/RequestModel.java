package com.hudipo.pum_indomaret.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestModel implements Parcelable {

    private int empId;
    private int empDeptId;
    private String useDate;
    private String respDate;
    private String docNum;
    private int trxTypeId;
    private String description;
    private Double amount;
    private String fileDataUri;
    private int pin;
    private int orgId;
    private int userId;

    public RequestModel() {
    }

    private RequestModel(Parcel in) {
        empId = in.readInt();
        empDeptId = in.readInt();
        useDate = in.readString();
        respDate = in.readString();
        docNum = in.readString();
        trxTypeId = in.readInt();
        description = in.readString();
        if (in.readByte() == 0) {
            amount = null;
        } else {
            amount = in.readDouble();
        }
        fileDataUri = in.readString();
        pin = in.readInt();
        orgId = in.readInt();
        userId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(empId);
        dest.writeInt(empDeptId);
        dest.writeString(useDate);
        dest.writeString(respDate);
        dest.writeString(docNum);
        dest.writeInt(trxTypeId);
        dest.writeString(description);
        if (amount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(amount);
        }
        dest.writeString(fileDataUri);
        dest.writeInt(pin);
        dest.writeInt(orgId);
        dest.writeInt(userId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RequestModel> CREATOR = new Creator<RequestModel>() {
        @Override
        public RequestModel createFromParcel(Parcel in) {
            return new RequestModel(in);
        }

        @Override
        public RequestModel[] newArray(int size) {
            return new RequestModel[size];
        }
    };

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getEmpDeptId() {
        return empDeptId;
    }

    public void setEmpDeptId(int empDeptId) {
        this.empDeptId = empDeptId;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public String getRespDate() {
        return respDate;
    }

    public void setRespDate(String respDate) {
        this.respDate = respDate;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public int getTrxTypeId() {
        return trxTypeId;
    }

    public void setTrxTypeId(int trxTypeId) {
        this.trxTypeId = trxTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getFileDataUri() {
        return fileDataUri;
    }

    public void setFileDataUri(String fileDataUri) {
        this.fileDataUri = fileDataUri;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
