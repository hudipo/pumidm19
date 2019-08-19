package com.hudipo.pum_indomaret.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("EMP_ID")
    @Expose
    private Integer empId;
    @SerializedName("EMP_NUM")
    @Expose
    private String empNum;
    @SerializedName("NAME")
    @Expose
    private String name;
    @SerializedName("EMAIL")
    @Expose
    private String email;
    @SerializedName("DEPT_ID")
    @Expose
    private Integer deptId;
    @SerializedName("ACTIVE_FLAG")
    @Expose
    private Integer activeFlag;
    @SerializedName("MAX_CREATE_PUM")
    @Expose
    private Integer maxCreatePum;
    @SerializedName("CREATION_DATE")
    @Expose
    private String creationDate;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpNum() {
        return empNum;
    }

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Integer getMaxCreatePum() {
        return maxCreatePum;
    }

    public void setMaxCreatePum(Integer maxCreatePum) {
        this.maxCreatePum = maxCreatePum;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
