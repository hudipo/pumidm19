package com.hudipo.pum_indomaret.model.approval;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApprovalListResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setData(List<DataItem> data){
		this.data = data;
	}

	public List<DataItem> getData(){
		return data;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}