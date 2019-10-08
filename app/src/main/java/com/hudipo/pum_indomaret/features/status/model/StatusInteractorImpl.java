package com.hudipo.pum_indomaret.features.status.model;


import android.content.Context;
import android.util.Log;

import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class StatusInteractorImpl implements StatusContract.StatusInteractor {

    private Context context;
    private HawkStorage hawkStorage;
    private CompositeDisposable composite = new CompositeDisposable();
    StatusFilterRequestBody filterRequestBody = new StatusFilterRequestBody();

    public StatusInteractorImpl(Context context){
        this.context = context;
        hawkStorage = new HawkStorage(context);
    }

    @Override
    public void getStatusList(OnFinishedListenerStatus onFinishedListenerStatus) {

        Log.d("interactor",""+hawkStorage.getUserData().getEmpId());
        composite.add(new ApiServices().getApiPumServices().getStatusListFromNetwork(hawkStorage.getUserData().getEmpId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(statusResponse -> {
                    if (!statusResponse.getError()){
                        onFinishedListenerStatus.onStatusListFetched(statusResponse.getData());
                    }else {
                        onFinishedListenerStatus.onFailure("SERVER ERROR!");
                    }
                },throwable -> onFinishedListenerStatus.onFailure(throwable.getMessage()))
        );
    }

    @Override
    public void getFilteredStatusList(OnFinishedListenerStatus onFinishedListenerStatus, String startDate, String untilDate, String status) {
        Log.d("interactor",""+hawkStorage.getUserData().getEmpId());
        filterRequestBody.setEmp_id(hawkStorage.getUserData().getEmpId());
        filterRequestBody.setStart_date(startDate);
        filterRequestBody.setFinish_date(untilDate);
        filterRequestBody.setStatus(status);
        composite.add(new ApiServices().getApiPumServices().getFilteredStatusListFromNetwork(hawkStorage.getUserData().getEmpId(),startDate,untilDate,status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(statusResponse -> {
                    onFinishedListenerStatus.onFailure(statusResponse.getMessage());
                    if (!statusResponse.getError()){
                        onFinishedListenerStatus.onStatusListFetched(statusResponse.getData());
                    }else {
                        onFinishedListenerStatus.onFailure(statusResponse.getMessage());
                    }
                },throwable -> onFinishedListenerStatus.onFailure(throwable.getMessage()))
        );
    }

}
