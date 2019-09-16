package com.hudipo.pum_indomaret.features.requestpum.presenter;

import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;

import java.util.ArrayList;

public class DocumentPresenterImpl implements RequestContract.DocumentPresenter,RequestContract.RequestInteractor.OnFinishedListenerDocument {

    private RequestContract.DocumentView documentView;
    private RequestContract.RequestInteractor interactor;

    public DocumentPresenterImpl(RequestContract.DocumentView documentView,RequestContract.RequestInteractor interactor){
        this.documentView = documentView;
        this.interactor = interactor;
    }


    @Override
    public void getDocumentType() {
        interactor.getDocumentTypeList(this);
    }

    @Override
    public void onDetach() {
        this.documentView = null;
        this.interactor = null;
    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onDocumentTypeFetched(ArrayList<String> documentTypeList) {
        documentView.setDocumentType(documentTypeList);
    }
}
