package com.hudipo.pum_indomaret.model.trxtype;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TrxTypeResponse{

	@SerializedName("data")
	private List<TrxItem> trx;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public List<TrxItem> getTrx() {
		return trx;
	}

	public void setTrx(List<TrxItem> trx) {
		this.trx = trx;
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