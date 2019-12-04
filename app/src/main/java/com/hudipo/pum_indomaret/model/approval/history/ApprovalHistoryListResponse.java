package com.hudipo.pum_indomaret.model.approval.history;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApprovalHistoryListResponse{

	@SerializedName("data")
	@Expose
	private List<ApprovalHistoryListModel> data;

	@SerializedName("error")
	@Expose
	private boolean error;

	@SerializedName("message")
	@Expose
	private String message;

	public List<ApprovalHistoryListModel> getData(){
		return data;
	}

	public boolean getError(){
		return error;
	}

	public String getMessage(){
		return message;
	}
}