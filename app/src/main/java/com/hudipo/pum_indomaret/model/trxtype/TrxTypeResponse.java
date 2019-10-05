package com.hudipo.pum_indomaret.model.trxtype;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TrxTypeResponse{

	@SerializedName("trx")
	private List<TrxItem> trx;

	@SerializedName("error")
	private boolean error;

	public void setTrx(List<TrxItem> trx){
		this.trx = trx;
	}

	public List<TrxItem> getTrx(){
		return trx;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

}