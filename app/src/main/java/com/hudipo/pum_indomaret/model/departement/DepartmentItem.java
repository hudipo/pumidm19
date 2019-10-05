package com.hudipo.pum_indomaret.model.departement;

import com.google.gson.annotations.SerializedName;

public class DepartmentItem{

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("dept_id")
	private int deptId;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setDeptId(int deptId){
		this.deptId = deptId;
	}

	public int getDeptId(){
		return deptId;
	}

}