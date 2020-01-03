package com.hudipo.pum_indomaret.model.submitresponse;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SubmitResponseModel implements Parcelable {
    private int empId;
    private int pumTrxId;
    private int code;
    private int orgId;
    private List<SubmitResponseItem> responseItems;

    public SubmitResponseModel() {
    }

    private SubmitResponseModel(Parcel in) {
        empId = in.readInt();
        pumTrxId = in.readInt();
        code = in.readInt();
        orgId = in.readInt();
        responseItems = in.createTypedArrayList(SubmitResponseItem.CREATOR);
    }

    public static final Creator<SubmitResponseModel> CREATOR = new Creator<SubmitResponseModel>() {
        @Override
        public SubmitResponseModel createFromParcel(Parcel in) {
            return new SubmitResponseModel(in);
        }

        @Override
        public SubmitResponseModel[] newArray(int size) {
            return new SubmitResponseModel[size];
        }
    };

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getPumTrxId() {
        return pumTrxId;
    }

    public void setPumTrxId(int pumTrxId) {
        this.pumTrxId = pumTrxId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public List<SubmitResponseItem> getResponseItems() {
        return responseItems;
    }

    public void setResponseItems(List<SubmitResponseItem> responseItems) {
        this.responseItems = responseItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(empId);
        dest.writeInt(pumTrxId);
        dest.writeInt(code);
        dest.writeInt(orgId);
        dest.writeTypedList(responseItems);
    }
}
