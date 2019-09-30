package com.hudipo.pum_indomaret.model.login;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("DEPT_ID")
	private String deptId;

	@SerializedName("INACTIVE_DATE")
	private String inActiveDate;

	@SerializedName("CREATED_BY")
	private int createdBy;

	@SerializedName("LAST_UPDATED_BY")
	private int lastUpdatedBy;

	@SerializedName("ORG_ID")
	private int orgId;

	@SerializedName("MAX_CREATE_PUM")
	private int maxCreatePum;

	@SerializedName("ACTIVE_FLAG")
	private String activeFlag;

	@SerializedName("EMAIL_OTP")
	private String emailOtp;

	@SerializedName("CREATION_DATE")
	private String creationDate;

	@SerializedName("MAX_AMOUNT")
	private int maxAmount;

	@SerializedName("EMP_NUM")
	private String empNum;

	@SerializedName("EMAIL")
	private String email;

	@SerializedName("LAST_UPDATE_DATE")
	private String lastUpdateDate;

	@SerializedName("respname")
	private String respname;

	@SerializedName("NAME")
	private String name;

	@SerializedName("POSITION")
	private String position;

	@SerializedName("EMP_ID")
	private int empId;

	@SerializedName("PROXY_AMOUNT_TO")
	private int proxyAmountTo;

	@SerializedName("role_id")
	private int roleId;

	@SerializedName("FLAG_APR")
	private String flagApr;

	@SerializedName("menu_id")
	private int menuId;

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getInActiveDate() {
		return inActiveDate;
	}

	public void setInActiveDate(String inActiveDate) {
		this.inActiveDate = inActiveDate;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(int lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public int getMaxCreatePum() {
		return maxCreatePum;
	}

	public void setMaxCreatePum(int maxCreatePum) {
		this.maxCreatePum = maxCreatePum;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getEmailOtp() {
		return emailOtp;
	}

	public void setEmailOtp(String emailOtp) {
		this.emailOtp = emailOtp;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public int getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(int maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getEmpNum() {
		return empNum;
	}

	public void setEmpNum(String empNum) {
		this.empNum = empNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getRespname() {
		return respname;
	}

	public void setRespname(String respname) {
		this.respname = respname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public int getProxyAmountTo() {
		return proxyAmountTo;
	}

	public void setProxyAmountTo(int proxyAmountTo) {
		this.proxyAmountTo = proxyAmountTo;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getFlagApr() {
		return flagApr;
	}

	public void setFlagApr(String flagApr) {
		this.flagApr = flagApr;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
}