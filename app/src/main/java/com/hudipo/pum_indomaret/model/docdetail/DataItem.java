package com.hudipo.pum_indomaret.model.docdetail;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("doc_amount")
	private double docAmount;

	@SerializedName("doc_num")
	private String docNum;

	@SerializedName("doc_date")
	private String docDate;

	public void setDocAmount(double docAmount){
		this.docAmount = docAmount;
	}

	public double getDocAmount(){
		return docAmount;
	}

	public void setDocNum(String docNum){
		this.docNum = docNum;
	}

	public String getDocNum(){
		return docNum;
	}

	public void setDocDate(String docDate){
		this.docDate = docDate;
	}

	public String getDocDate(){
		return docDate;
	}

}