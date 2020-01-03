package com.hudipo.pum_indomaret.features.status.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class StatusDetailResponse implements Serializable {
    public Boolean error;
    public String message;
    public StatusDetailModel data;

    public class StatusDetailModel implements Serializable{
        @SerializedName("PUM_TRX_ID")
        public int pumTrxId;

        @SerializedName("TRX_NUM")
        public String trxNum;

        @SerializedName("TRX_DATE")
        public String trxDate;

        @SerializedName("USE_DATE")
        public String useDate;

        @SerializedName("EMP_ID")
        public int empId;

        @SerializedName("RESP_ESTIMATE_DATE")
        public String respEstimateDate;

        @SerializedName("UPLOAD_DATA")
        public String uploadData;

        @SerializedName("FILE_DATA")
        public String fileData;

        @SerializedName("PUM_TRX_TYPE_ID")
        public String pumTrxTypeId;

        @SerializedName("DESCRIPTION")
        public String description;

        @SerializedName("REASON_APPROVE")
        public String reason;

        @SerializedName("AMOUNT")
        public long amount;

        @SerializedName("EMP_NUM")
        public String empNum;

        @SerializedName("NAME")
        public String name;

        @SerializedName("DEPARTMENT")
        public String department;

        @SerializedName("DATA_APP")
        public DataApp dataApp;

        public class DataApp implements Serializable{
            @SerializedName("App_1")
            public List<Approver> app1;

            @SerializedName("App_2")
            public List<Approver> app2;

            @SerializedName("App_3")
            public List<Approver> app3;

            @SerializedName("App_4")
            public List<Approver> app4;

            @SerializedName("App_5")
            public List<Approver> app5;
        }
    }
}
