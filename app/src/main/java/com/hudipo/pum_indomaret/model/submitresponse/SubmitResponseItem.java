package com.hudipo.pum_indomaret.model.submitresponse;

import android.os.Parcel;
import android.os.Parcelable;

public class SubmitResponseItem implements Parcelable {
    private int id;
    private int trxTypeId;
    private String description;
    private String storeCode;
    private String fileUri;
    private String realPath;
    private String typeFile;
    private String amount;
    private String textTransactionType;
    private boolean isImage;

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    private SubmitResponseItem(Parcel in) {
        id = in.readInt();
        trxTypeId = in.readInt();
        description = in.readString();
        storeCode = in.readString();
        fileUri = in.readString();
        amount = in.readString();
        realPath = in.readString();
        typeFile = in.readString();
        textTransactionType = in.readString();
        isImage = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(trxTypeId);
        dest.writeString(description);
        dest.writeString(storeCode);
        dest.writeString(fileUri);
        dest.writeString(amount);
        dest.writeString(realPath);
        dest.writeString(typeFile);
        dest.writeString(textTransactionType);
        dest.writeByte((byte) (isImage ? 1 : 0));
    }

    public static final Creator<SubmitResponseItem> CREATOR = new Creator<SubmitResponseItem>() {
        @Override
        public SubmitResponseItem createFromParcel(Parcel in) {
            return new SubmitResponseItem(in);
        }

        @Override
        public SubmitResponseItem[] newArray(int size) {
            return new SubmitResponseItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextTransactionType() {
        return textTransactionType;
    }

    public void setTextTransactionType(String textTransactionType) {
        this.textTransactionType = textTransactionType;
    }

    public SubmitResponseItem() {
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

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }


    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isImage() {
        return isImage;
    }

    public void setIsImage(boolean image) {
        isImage = image;
    }
}
