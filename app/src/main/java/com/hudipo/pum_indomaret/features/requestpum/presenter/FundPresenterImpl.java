package com.hudipo.pum_indomaret.features.requestpum.presenter;

import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;

import java.util.ArrayList;

public class FundPresenterImpl implements RequestContract.FundPresenter,RequestContract.RequestInteractor.OnFinishedListenerFund {

    private RequestContract.FundView fundView;
    private RequestContract.RequestInteractor interactor;

    public FundPresenterImpl(RequestContract.FundView fundView, RequestContract.RequestInteractor interactor){
        this.fundView = fundView;
        this.interactor = interactor;
    }

    @Override
    public void onDetach() {
        fundView = null;
        interactor = null;
    }

    @Override
    public void getTransactionTypeList() {
        interactor.getTransactionTypeList(this);
    }

    @Override
    public void onTransactionTypeFetched(ArrayList<String> transactionTypeList) {
        fundView.setTransactionType(transactionTypeList);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
