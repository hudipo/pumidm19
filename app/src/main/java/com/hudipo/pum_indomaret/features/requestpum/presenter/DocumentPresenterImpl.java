package com.hudipo.pum_indomaret.features.requestpum.presenter;

import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;

import java.util.ArrayList;

public class DocumentPresenterImpl implements RequestContract.DocumentPresenter,RequestContract.RequestIntractor.OnFinishedListener {

    private RequestContract.DocumentView documentView;
    private RequestContract.RequestIntractor intractor;

    public DocumentPresenterImpl(RequestContract.DocumentView documentView,RequestContract.RequestIntractor intractor){
        this.documentView = documentView;
        this.intractor = intractor;
    }


    @Override
    public void getDocumentType() {
        intractor.getDocumentTypeList(this);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onDepartmentFetched(ArrayList<String> departmentList) {

    }

    @Override
    public void onFailure(Throwable t) {

    }

    @Override
    public void onEmployeeNameFetched(String employeName) {

    }

    @Override
    public void onDocumentTypeFetched(ArrayList<String> documentTypeList) {
        documentView.setDocumentType(documentTypeList);
    }
}
