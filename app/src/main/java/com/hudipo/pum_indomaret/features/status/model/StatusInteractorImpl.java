package com.hudipo.pum_indomaret.features.status.model;


import android.content.Context;

import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class StatusInteractorImpl implements StatusContract.StatusInteractor {

    private Context context;
    private HawkStorage hawkStorage;
    private CompositeDisposable composite = new CompositeDisposable();

    public StatusInteractorImpl(Context context){
        this.context = context;
        hawkStorage = new HawkStorage(context);
    }

    @Override
    public void getStatusList(OnFinishedListenerStatus onFinishedListenerStatus) {

        composite.add(new ApiServices().getApiPumServices().getStatusListFromNetwork(hawkStorage.getUserData().getEmpId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(statusResponse -> {
                    if (!statusResponse.getError()){
                        onFinishedListenerStatus.onStatusListFetched(statusResponse.getMessage());
                    }
                }, onFinishedListenerStatus::onFailure)
        );
    }

    @Override
    public void getFilteredStatusList(OnFinishedListenerStatus onFinishedListenerStatus, FilterStatusModel filterStatusModel) {

    }

}
