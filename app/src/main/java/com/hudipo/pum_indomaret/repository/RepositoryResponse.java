package com.hudipo.pum_indomaret.repository;

import com.hudipo.pum_indomaret.model.historyresponse.HistoryResponse;
import com.hudipo.pum_indomaret.model.response.DataResponse;
import com.hudipo.pum_indomaret.model.storecode.StoreCodeResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RepositoryResponse {

    private HawkStorage hawkStorage;

    public void setHawkStorage(HawkStorage hawkStorage) {
        this.hawkStorage = hawkStorage;
    }

    public interface RepositoryResponseCallback{
        interface LoadDataResponse{
            void onDataLoad(DataResponse getDataResponse);
            void onDataError(String message);
        }

        interface LoadDataStoreCode{
            void onDataLoad(StoreCodeResponse storeCodeResponse);
            void onDataError(String message);
        }

        interface LoadDataHistory{
            void onDataLoad(HistoryResponse historyResponse);
            void onDataError(String message);
        }
    }

    public void getDataResponse(CompositeDisposable composite, RepositoryResponseCallback.LoadDataResponse callback){
        composite.add(new ApiServices().getApiPumServices()
            .getDataResponse(hawkStorage.getUserData().getEmpId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                if (response.isSuccessful()){
                    callback.onDataLoad(response.body());
                }else {
                    callback.onDataError("Server error");
                }
            }, throwable -> callback.onDataError(throwable.getMessage())));
    }

    public void getDataStoreCode(CompositeDisposable composite, RepositoryResponseCallback.LoadDataStoreCode callback){
        composite.add(new ApiServices().getApiPumServices()
                .getDataStoreCode(hawkStorage.getUserData()
                .getOrgId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccessful()){
                        callback.onDataLoad(response.body());
                    }else {
                        callback.onDataError("Server error");
                    }
                }, throwable -> callback.onDataError(throwable.getMessage())));
    }

    public void getDataHistory(CompositeDisposable composite, int pumTrxId, RepositoryResponseCallback.LoadDataHistory callback){
        composite.add(new ApiServices().getApiPumServices()
            .getDataHistoryResponse(pumTrxId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                if (response.isSuccessful()){
                    callback.onDataLoad(response.body());
                }else {
                    callback.onDataError("Server error");
                }
            }, throwable -> callback.onDataError(throwable.getMessage())));
    }
}
