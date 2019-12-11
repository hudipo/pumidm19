package com.hudipo.pum_indomaret.model.docdetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DocDataItem implements Parcelable {

	@SerializedName("doc_amount")
	private String docAmount;

	@SerializedName("doc_num")
	private String docNum;

	@SerializedName("doc_date")
	private String docDate;

	private DocDataItem(Parcel in) {
		docAmount = in.readString();
		docNum = in.readString();
		docDate = in.readString();
	}

	public static final Creator<DocDataItem> CREATOR = new Creator<DocDataItem>() {
		@Override
		public DocDataItem createFromParcel(Parcel in) {
			return new DocDataItem(in);
		}

		@Override
		public DocDataItem[] newArray(int size) {
			return new DocDataItem[size];
		}
	};

	public void setDocAmount(String docAmount){
		this.docAmount = docAmount;
	}

	public String getDocAmount(){
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(docAmount);
		dest.writeString(docNum);
		dest.writeString(docDate);
	}
}