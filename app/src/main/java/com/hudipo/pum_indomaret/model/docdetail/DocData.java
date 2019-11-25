package com.hudipo.pum_indomaret.model.docdetail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DocData implements Parcelable {

	@SerializedName("first_page_url")
	private String firstPageUrl;

	@SerializedName("path")
	private String path;

	@SerializedName("per_page")
	private int perPage;

	@SerializedName("total")
	private int total;

	@SerializedName("data")
	private List<DocDataItem> data;

	@SerializedName("last_page")
	private int lastPage;

	@SerializedName("last_page_url")
	private String lastPageUrl;

	@SerializedName("next_page_url")
	private String nextPageUrl;

	@SerializedName("from")
	private int from;

	@SerializedName("to")
	private int to;

	@SerializedName("prev_page_url")
	private String prevPageUrl;

	@SerializedName("current_page")
	private int currentPage;

	private DocData(Parcel in) {
		firstPageUrl = in.readString();
		path = in.readString();
		perPage = in.readInt();
		total = in.readInt();
		data = in.createTypedArrayList(DocDataItem.CREATOR);
		lastPage = in.readInt();
		lastPageUrl = in.readString();
		nextPageUrl = in.readString();
		from = in.readInt();
		to = in.readInt();
		prevPageUrl = in.readString();
		currentPage = in.readInt();
	}

	public static final Creator<DocData> CREATOR = new Creator<DocData>() {
		@Override
		public DocData createFromParcel(Parcel in) {
			return new DocData(in);
		}

		@Override
		public DocData[] newArray(int size) {
			return new DocData[size];
		}
	};

	public void setFirstPageUrl(String firstPageUrl){
		this.firstPageUrl = firstPageUrl;
	}

	public String getFirstPageUrl(){
		return firstPageUrl;
	}

	public void setPath(String path){
		this.path = path;
	}

	public String getPath(){
		return path;
	}

	public void setPerPage(int perPage){
		this.perPage = perPage;
	}

	public int getPerPage(){
		return perPage;
	}

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setData(List<DocDataItem> data){
		this.data = data;
	}

	public List<DocDataItem> getData(){
		return data;
	}

	public void setLastPage(int lastPage){
		this.lastPage = lastPage;
	}

	public int getLastPage(){
		return lastPage;
	}

	public void setLastPageUrl(String lastPageUrl){
		this.lastPageUrl = lastPageUrl;
	}

	public String getLastPageUrl(){
		return lastPageUrl;
	}

	public void setNextPageUrl(String nextPageUrl){
		this.nextPageUrl = nextPageUrl;
	}

	public String getNextPageUrl(){
		return nextPageUrl;
	}

	public void setFrom(int from){
		this.from = from;
	}

	public int getFrom(){
		return from;
	}

	public void setTo(int to){
		this.to = to;
	}

	public int getTo(){
		return to;
	}

	public void setPrevPageUrl(String prevPageUrl){
		this.prevPageUrl = prevPageUrl;
	}

	public String getPrevPageUrl(){
		return prevPageUrl;
	}

	public void setCurrentPage(int currentPage){
		this.currentPage = currentPage;
	}

	public int getCurrentPage(){
		return currentPage;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(firstPageUrl);
		dest.writeString(path);
		dest.writeInt(perPage);
		dest.writeInt(total);
		dest.writeTypedList(data);
		dest.writeInt(lastPage);
		dest.writeString(lastPageUrl);
		dest.writeString(nextPageUrl);
		dest.writeInt(from);
		dest.writeInt(to);
		dest.writeString(prevPageUrl);
		dest.writeInt(currentPage);
	}
}