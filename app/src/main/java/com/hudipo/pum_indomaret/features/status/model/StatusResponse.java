package com.hudipo.pum_indomaret.features.status.model;

import java.io.Serializable;
import java.util.List;

public class StatusResponse {
    private Boolean error;
    private List<StatusModel> message;

    public StatusResponse(Boolean error, List<StatusModel> message) {
        this.error = error;
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<StatusModel> getMessage() {
        return message;
    }

    public void setMessage(List<StatusModel> message) {
        this.message = message;
    }

    public class StatusModel implements Serializable {
        private int pum_trx_id;
        private String trx_num;
        private String trx_date;
        private String pum_status;

        public StatusModel (int pum_trx_id, String trx_num, String trx_date, String pum_status){
            this.pum_trx_id = pum_trx_id;
            this.trx_num = trx_num;
            this.trx_date = trx_date;
            this.pum_status = pum_status;
        }

        public int getPum_trx_id() {
            return pum_trx_id;
        }

        public void setPum_trx_id(int pum_trx_id) {
            this.pum_trx_id = pum_trx_id;
        }

        public String getTrx_num() {
            return trx_num;
        }

        public void setTrx_num(String trx_num) {
            this.trx_num = trx_num;
        }

        public String getTrx_date() {
            return trx_date;
        }

        public void setTrx_date(String trx_date) {
            this.trx_date = trx_date;
        }

        public String getPum_status() {
            return pum_status;
        }

        public void setPum_status(String pum_status) {
            this.pum_status = pum_status;
        }
    }
}
