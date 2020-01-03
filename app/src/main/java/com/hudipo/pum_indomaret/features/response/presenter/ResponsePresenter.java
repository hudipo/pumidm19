package com.hudipo.pum_indomaret.features.response.presenter;

import android.util.Log;

import com.hudipo.pum_indomaret.features.response.contract.ResponseContract;
import com.hudipo.pum_indomaret.model.response.DataResponse;
import com.hudipo.pum_indomaret.repository.RepositoryResponse;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import io.reactivex.disposables.CompositeDisposable;

public class ResponsePresenter implements ResponseContract.ResponsePresenterView<ResponseContract.ResponseView>{

    private ResponseContract.ResponseView mView;
    private CompositeDisposable composite = new CompositeDisposable();
    private RepositoryResponse repositoryResponse;

    public void setHawkStorage(HawkStorage hawkStorage) {
        repositoryResponse = new RepositoryResponse();
        repositoryResponse.setHawkStorage(hawkStorage);
    }

    @Override
    public void onAttach(ResponseContract.ResponseView view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        if (mView != null){
            mView = null;
        }
        composite.dispose();
    }

    @Override
    public void clearComposite() {
        composite.clear();
    }

    @Override
    public void loadDataResponse() {
        mView.showProgress();
        repositoryResponse.getDataResponse(composite, new RepositoryResponse.RepositoryResponseCallback.LoadDataResponse() {
            @Override
            public void onDataLoad(DataResponse getDataResponse) {
                mView.hideProgress();
                if (getDataResponse.getData().size() > 0 && getDataResponse.getData() != null){
                    mView.setDataResponse(getDataResponse);
                }else {
                    mView.showDataEmpty();
                }
            }

            @Override
            public void onDataError(String message) {
                mView.hideProgress();
                if (message != null){
                    mView.showError(message);
                }
            }
        });
    }
}
