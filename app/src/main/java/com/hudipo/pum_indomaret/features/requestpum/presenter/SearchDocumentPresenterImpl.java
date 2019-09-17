package com.hudipo.pum_indomaret.features.requestpum.presenter;

import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;
import com.hudipo.pum_indomaret.features.requestpum.model.DocumentDetailRequestModel;

import java.util.ArrayList;

public class SearchDocumentPresenterImpl implements RequestContract.SearchDocumentPresenter,RequestContract.RequestInteractor.OnFinishedListenerSearchDocument {

    private RequestContract.SearchDocumentView searchDocumentView;
    private RequestContract.RequestInteractor interactor;

    public SearchDocumentPresenterImpl(RequestContract.SearchDocumentView searchDocumentView, RequestContract.RequestInteractor interactor){
        this.searchDocumentView = searchDocumentView;
        this.interactor = interactor;
    }


    @Override
    public void onDetach() {
        searchDocumentView = null;
        interactor = null;
    }

    @Override
    public void getDocumentList() {
        interactor.getDocumentDetailList(this);
    }

    @Override
    public void onDocumentDetailFetched(ArrayList<DocumentDetailRequestModel> documentDetailRequestModels) {
        searchDocumentView.setDocumentList(documentDetailRequestModels);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
