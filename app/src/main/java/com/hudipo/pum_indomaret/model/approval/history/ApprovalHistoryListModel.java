package com.hudipo.pum_indomaret.model.approval.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApprovalHistoryListModel {

	@SerializedName("STATUS")
	@Expose
	private String status;

	@SerializedName("TRX_DATE")
	@Expose
	private String trxDate;

	@SerializedName("ACTIONDATE")
	@Expose
	private String actionDate;

	@SerializedName("USERNAME")
	@Expose
	private String username;

	@SerializedName("DEPARTMENT")
	@Expose
	private String department;

	@SerializedName("TRX_NUM")
	@Expose
	private String trxNum;

	@SerializedName("PUM_TRX_ID")
	@Expose
	private Integer pumTrxId;

	@SerializedName("AMOUNT")
	@Expose
	private Integer amount;

	public String getStatus() {
		return status;
	}

	public String getTrxDate() {
		return trxDate;
	}

	public String getActionDate() {
		return actionDate;
	}

	public String getUsername() {
		return username;
	}

	public String getDepartment() {
		return department;
	}

	public String getTrxNum() {
		return trxNum;
	}

	public Integer getPumTrxId() {
		return pumTrxId;
	}

	public Integer getAmount() {
		return amount;
	}
}