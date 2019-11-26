package com.hudipo.pum_indomaret.features.status.model;

import java.io.Serializable;
import java.util.List;

public class StatusResponse {
    private Boolean error;
    private String message;
    private List<StatusModel> data;

    public StatusResponse(Boolean error, String message, List<StatusModel> data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StatusModel> getData() {
        return data;
    }

    public void setData(List<StatusModel> data) {
        this.data = data;
    }

    public class StatusModel implements Serializable {
        private int PUM_TRX_ID;
        private String TRX_NUM;
        private String TRX_DATE;
        private String PUM_STATUS;

        public StatusModel() {
        }

        public StatusModel(int PUM_TRX_ID, String TRX_NUM, String TRX_DATE, String PUM_STATUS) {
            this.PUM_TRX_ID = PUM_TRX_ID;
            this.TRX_NUM = TRX_NUM;
            this.TRX_DATE = TRX_DATE;
            this.PUM_STATUS = PUM_STATUS;
        }

        public int getPUM_TRX_ID() {
            return PUM_TRX_ID;
        }

        public void setPUM_TRX_ID(int PUM_TRX_ID) {
            this.PUM_TRX_ID = PUM_TRX_ID;
        }

        public String getTRX_NUM() {
            return TRX_NUM;
        }

        public void setTRX_NUM(String TRX_NUM) {
            this.TRX_NUM = TRX_NUM;
        }

        public String getTRX_DATE() {
            return TRX_DATE;
        }

        public void setTRX_DATE(String TRX_DATE) {
            this.TRX_DATE = TRX_DATE;
        }

        public String getPUM_STATUS() {
            return PUM_STATUS;
        }

        public void setPUM_STATUS(String PUM_STATUS) {
            this.PUM_STATUS = PUM_STATUS;
        }
    }
}
