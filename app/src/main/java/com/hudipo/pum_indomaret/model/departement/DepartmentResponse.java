package com.hudipo.pum_indomaret.model.departement;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DepartmentResponse {

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	@SerializedName("data")
	private ArrayList<DepartmentItem> department;

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setDepartment(ArrayList<DepartmentItem> department){
		this.department = department;
	}

	public ArrayList<DepartmentItem> getDepartment(){
		return department;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}