package com.hudipo.pum_indomaret.model.approval;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApprovalListResponse{
	@SerializedName("error")
	@Expose
	private Boolean error;
	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("data")
	@Expose
	private List<ApprovalListModel> data = null;

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ApprovalListModel> getData() {
		return data;
	}

	public void setData(List<ApprovalListModel> data) {
		this.data = data;
	}
}