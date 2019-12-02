package com.hudipo.pum_indomaret.model;

import android.net.Uri;
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
    private Uri fileDataUri;
    private String pin;
    private int orgId;
    private int userId;
    private String nameEmpDept;
    private String nameDocType;
    private String nameTrxType;
    private String nameFile;
    private String pathDocument;
    private boolean isImage;


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
        fileDataUri = in.readParcelable(Uri.class.getClassLoader());
        pin = in.readString();
        orgId = in.readInt();
        userId = in.readInt();
        nameEmpDept = in.readString();
        nameDocType = in.readString();
        nameTrxType = in.readString();
        nameFile = in.readString();
        isImage = in.readByte() != 0;
        pathDocument = in.readString();
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

    public String getPathDocument() {
        return pathDocument;
    }

    public void setPathDocument(String pathDocument) {
        this.pathDocument = pathDocument;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setImage(boolean image) {
        isImage = image;
    }

    public String getNameEmpDept() {
        return nameEmpDept;
    }

    public void setNameEmpDept(String nameEmpDept) {
        this.nameEmpDept = nameEmpDept;
    }

    public String getNameDocType() {
        return nameDocType;
    }

    public void setNameDocType(String nameDocType) {
        this.nameDocType = nameDocType;
    }

    public String getNameTrxType() {
        return nameTrxType;
    }

    public void setNameTrxType(String nameTrxType) {
        this.nameTrxType = nameTrxType;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public RequestModel() {
    }

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

    public Uri getFileDataUri() {
        return fileDataUri;
    }

    public void setFileDataUri(Uri fileDataUri) {
        this.fileDataUri = fileDataUri;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
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

    @Override
    public int describeContents() {
        return 0;
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
        dest.writeParcelable(fileDataUri, flags);
        dest.writeString(pin);
        dest.writeInt(orgId);
        dest.writeInt(userId);
        dest.writeString(nameEmpDept);
        dest.writeString(nameDocType);
        dest.writeString(nameTrxType);
        dest.writeString(nameFile);
        dest.writeByte((byte) (isImage ? 1 : 0));
        dest.writeString(pathDocument);
    }
}
