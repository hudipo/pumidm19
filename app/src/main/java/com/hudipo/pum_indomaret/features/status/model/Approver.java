package com.hudipo.pum_indomaret.features.status.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Approver implements Serializable {

    @SerializedName("EMP_ID")
    public int empId;

    @SerializedName("NAME")
    public String name;

    public Approver(int empId, String name){
        this.empId = empId;
        this.name=name;
    }
}
