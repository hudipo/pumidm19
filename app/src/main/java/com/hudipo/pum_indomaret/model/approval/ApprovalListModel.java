package com.hudipo.pum_indomaret.model.approval;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApprovalListModel {
	@SerializedName("PUM_TRX_ID")
	@Expose
	private Integer pUMTRXID;
	@SerializedName("TRX_NUM")
	@Expose
	private String tRXNUM;
	@SerializedName("NAME")
	@Expose
	private String nAME;
	@SerializedName("TRX_DATE")
	@Expose
	private String tRXDATE;
	@SerializedName("AMOUNT")
	@Expose
	private Integer aMOUNT;

	private boolean isChecked;

	public Integer getPUMTRXID() {
		return pUMTRXID;
	}

	public void setPUMTRXID(Integer pUMTRXID) {
		this.pUMTRXID = pUMTRXID;
	}

	public String getTRXNUM() {
		return tRXNUM;
	}

	public void setTRXNUM(String tRXNUM) {
		this.tRXNUM = tRXNUM;
	}

	public String getNAME() {
		return nAME;
	}

	public void setNAME(String nAME) {
		this.nAME = nAME;
	}

	public String getTRXDATE() {
		return tRXDATE;
	}

	public void setTRXDATE(String tRXDATE) {
		this.tRXDATE = tRXDATE;
	}

	public Integer getAMOUNT() {
		return aMOUNT;
	}

	public void setAMOUNT(Integer aMOUNT) {
		this.aMOUNT = aMOUNT;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}
}