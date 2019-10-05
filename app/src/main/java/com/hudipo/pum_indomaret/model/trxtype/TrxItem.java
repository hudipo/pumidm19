package com.hudipo.pum_indomaret.model.trxtype;

import com.google.gson.annotations.SerializedName;

public class TrxItem{

	@SerializedName("PUM_TRX_TYPE_ID")
	private int pUMTRXTYPEID;

	@SerializedName("DESCRIPTION")
	private String dESCRIPTION;

	public void setPUMTRXTYPEID(int pUMTRXTYPEID){
		this.pUMTRXTYPEID = pUMTRXTYPEID;
	}

	public int getPUMTRXTYPEID(){
		return pUMTRXTYPEID;
	}

	public void setDESCRIPTION(String dESCRIPTION){
		this.dESCRIPTION = dESCRIPTION;
	}

	public String getDESCRIPTION(){
		return dESCRIPTION;
	}

}