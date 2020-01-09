package com.hudipo.pum_indomaret.model.login;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("DEPT_ID")
	private String deptId;

	@SerializedName("ORG_ID")
	private int orgId;

	@SerializedName("MAX_CREATE_PUM")
	private int maxCreatePum;

	@SerializedName("MAX_AMOUNT")
	private Double maxAmount;

	@SerializedName("MIN_AMOUNT")
	private Double minAmount;

	@SerializedName("EMP_NUM")
	private String empNum;

	@SerializedName("USER_ID")
	private int userId;

	@SerializedName("MENU_ID")
	private int menuId;

	@SerializedName("NAME")
	private String name;

	@SerializedName("POSITION")
	private String position;

	@SerializedName("EMP_ID")
	private int empId;

	@SerializedName("ROLE_ID")
	private int roleId;

	@SerializedName("RESP_ID")
	private int respId;

	@SerializedName("RESP_NAME")
	private String respName;

	@SerializedName("PHOTO_PROFILE")
	private String photoProfile;

	public String getPhotoProfile() {
		return photoProfile;
	}

	public void setPhotoProfile(String photoProfile) {
		this.photoProfile = photoProfile;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
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

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}

	public String getEmpNum() {
		return empNum;
	}

	public void setEmpNum(String empNum) {
		this.empNum = empNum;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRespId() {
		return respId;
	}

	public void setRespId(int respId) {
		this.respId = respId;
	}

	public String getRespName() {
		return respName;
	}

	public void setRespName(String respName) {
		this.respName = respName;
	}
}