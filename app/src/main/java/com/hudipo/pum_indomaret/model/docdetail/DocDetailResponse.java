package com.hudipo.pum_indomaret.model.docdetail;

import com.google.gson.annotations.SerializedName;

public class DocDetailResponse{

	@SerializedName("document")
	private Document document;

	@SerializedName("error")
	private boolean error;

	public void setDocument(Document document){
		this.document = document;
	}

	public Document getDocument(){
		return document;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

}