package com.hudipo.pum_indomaret.model.approval.detail;

import com.google.gson.annotations.SerializedName;

public class AppItem {

	@SerializedName("EMP_ID")
	private int eMPID;

	@SerializedName("NAME")
	private String nAME;

	public int getEMPID(){
		return eMPID;
	}

	public String getNAME(){
		return nAME;
	}
}