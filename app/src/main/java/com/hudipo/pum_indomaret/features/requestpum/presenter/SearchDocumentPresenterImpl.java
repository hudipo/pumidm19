package com.hudipo.pum_indomaret.features.requestpum.presenter;

import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;
import com.hudipo.pum_indomaret.features.requestpum.model.DocumentDetailRequestModel;
import com.hudipo.pum_indomaret.model.docdetail.DocDetailResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchDocumentPresenterImpl implements RequestContract.SearchDocumentPresenter,RequestContract.RequestInteractor.OnFinishedListenerSearchDocument {

    private RequestContract.SearchDocumentView searchDocumentView;
    private RequestContract.RequestInteractor interactor;
    private CompositeDisposable composite = new CompositeDisposable();

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
    public void getDocumentList(String docType) {
        composite.add(new ApiServices().getApiPumServices()
                .getDocDetail(docType).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(docDetailResponse -> {
                    searchDocumentView.setDocumentList(docDetailResponse);
                }, throwable -> {

                }));
    }

    @Override
    public void onDocumentDetailFetched(ArrayList<DocumentDetailRequestModel> documentDetailRequestModels) {
//        searchDocumentView.setDocumentList(documentDetailRequestModels);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
