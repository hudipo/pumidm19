package com.hudipo.pum_indomaret.model.approval.detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApprovalDetailResponse{

	@SerializedName("error")
	@Expose
	private Boolean error;
	@SerializedName("message")
	@Expose
	private String message;
	@SerializedName("data")
	@Expose
	private DataApproval data;

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

	public DataApproval getData() {
		return data;
	}

	public void setData(DataApproval dataApproval) {
		this.data = dataApproval;
	}
}