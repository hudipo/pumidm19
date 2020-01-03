package com.hudipo.pum_indomaret.model.historyresponse;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class HistoryResponseItem {

	@SerializedName("AMOUNT")
	private int aMOUNT;

	@SerializedName("PUM_RESP_TRX_NUM")
	private String pUMRESPTRXNUM;

	@SerializedName("RESP_DATE")
	private String rESPDATE;

	public void setAMOUNT(int aMOUNT){
		this.aMOUNT = aMOUNT;
	}

	public int getAMOUNT(){
		return aMOUNT;
	}

	public void setPUMRESPTRXNUM(String pUMRESPTRXNUM){
		this.pUMRESPTRXNUM = pUMRESPTRXNUM;
	}

	public String getPUMRESPTRXNUM(){
		return pUMRESPTRXNUM;
	}

	public void setRESPDATE(String rESPDATE){
		this.rESPDATE = rESPDATE;
	}

	public String getRESPDATE(){
		return rESPDATE;
	}
}