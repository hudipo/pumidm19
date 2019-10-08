package com.hudipo.pum_indomaret.features.status.model;

import java.io.Serializable;

public class StatusFilterRequestBody implements Serializable {

    private int emp_id;
    private String status;
    private String start_date;
    private String finish_date;

    public StatusFilterRequestBody(int emp_id, String status, String start_date, String finish_date) {
        this.emp_id = emp_id;
        this.status = status;
        this.start_date = start_date;
        this.finish_date = finish_date;
    }

    public StatusFilterRequestBody() {
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(String finish_date) {
        this.finish_date = finish_date;
    }
}
