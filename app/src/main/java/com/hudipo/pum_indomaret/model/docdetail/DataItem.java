package com.hudipo.pum_indomaret.model.docdetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataItem implements Serializable {

	@SerializedName("doc_amount")
	private int docAmount;

	@SerializedName("doc_num")
	private String docNum;

	@SerializedName("doc_date")
	private String docDate;

	public void setDocAmount(int docAmount){
		this.docAmount = docAmount;
	}

	public int getDocAmount(){
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