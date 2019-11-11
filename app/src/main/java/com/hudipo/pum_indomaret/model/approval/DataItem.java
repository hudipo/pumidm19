package com.hudipo.pum_indomaret.model.approval;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("trx_num")
	private String trxNum;

	@SerializedName("amount")
	private int amount;

	@SerializedName("name")
	private String name;

	@SerializedName("trx_date")
	private String trxDate;

	@SerializedName("pum_trx_id")
	private int pumTrxId;

	public void setTrxNum(String trxNum){
		this.trxNum = trxNum;
	}

	public String getTrxNum(){
		return trxNum;
	}

	public void setAmount(int amount){
		this.amount = amount;
	}

	public int getAmount(){
		return amount;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setTrxDate(String trxDate){
		this.trxDate = trxDate;
	}

	public String getTrxDate(){
		return trxDate;
	}

	public void setPumTrxId(int pumTrxId){
		this.pumTrxId = pumTrxId;
	}

	public int getPumTrxId(){
		return pumTrxId;
	}
}