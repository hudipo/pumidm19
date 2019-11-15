package com.hudipo.pum_indomaret.model.departement;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DepartmentItem implements Serializable {

	@SerializedName("NAME")
	private String name;

	@SerializedName("DESCRIPTION")
	private String description;

	@SerializedName("DEPT_ID")
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