package com.hudipo.pum_indomaret.model.trxtype;

import com.google.gson.annotations.SerializedName;

public class TrxItem {

	@SerializedName("PUM_TRX_TYPE_ID")
	private int pumTrxTypeId;

	@SerializedName("DESCRIPTION")
	private String description;

	public int getPumTrxTypeId() {
		return pumTrxTypeId;
	}

	public void setPumTrxTypeId(int pumTrxTypeId) {
		this.pumTrxTypeId = pumTrxTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}