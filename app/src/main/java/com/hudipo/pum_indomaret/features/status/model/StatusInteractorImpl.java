package com.hudipo.pum_indomaret.features.status.model;


import android.content.Context;
import android.util.Log;

import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
        HashMap<String, RequestBody> params = new HashMap<>();
        RequestBody empId = RequestBody.create(String.valueOf(hawkStorage.getUserData().getEmpId()),MediaType.parse("text/plain"));
        params.put("emp_id", empId);

        Log.d("interactor",""+hawkStorage.getUserData().getEmpId());
        composite.add(new ApiServices().getApiPumServices().getStatusListFromNetwork(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(statusResponse -> {
                    if (!statusResponse.getError()){
                        if (statusResponse.getData()!=null){
                            onFinishedListenerStatus.onStatusListFetched(statusResponse.getData());
                        }else onFinishedListenerStatus.onFailure("No data available!");
                    }else {
                        onFinishedListenerStatus.onFailure("Please check your internet connection!");
                    }
                },throwable -> onFinishedListenerStatus.onFailure(throwable.getMessage()))
        );
    }

    @Override
    public void getFilteredStatusList(OnFinishedListenerStatus onFinishedListenerStatus, String status, String startDate, String endDate) {
        HashMap<String, RequestBody> params = new HashMap<>();
        RequestBody empId = RequestBody.create(String.valueOf(hawkStorage.getUserData().getEmpId()),MediaType.parse("text/plain"));
        RequestBody statusReqBody = RequestBody.create(status,MediaType.parse("text/plain"));
        RequestBody startDateReqBody = RequestBody.create(startDate,MediaType.parse("text/plain"));
        RequestBody endDateReqBody = RequestBody.create(endDate,MediaType.parse("text/plain"));
        params.put("emp_id",empId);
        params.put("status",statusReqBody);
        params.put("start_date",startDateReqBody);
        params.put("end_date",endDateReqBody);
        composite.add(new ApiServices().getApiPumServices().getStatusListFromNetwork(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(statusResponse -> {
                    if (!statusResponse.getError()){
                        if (statusResponse.getData()!=null){
                            onFinishedListenerStatus.onStatusListFetched(statusResponse.getData());
                        }else onFinishedListenerStatus.onFailure("No data available!");
                    }else {
                        onFinishedListenerStatus.onFailure("Please check your internet connection!");
                    }
                },throwable -> onFinishedListenerStatus.onFailure(throwable.getMessage()))
        );
    }

}
