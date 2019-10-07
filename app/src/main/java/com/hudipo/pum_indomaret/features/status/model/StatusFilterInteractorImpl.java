package com.hudipo.pum_indomaret.features.status.model;

import android.content.Context;

import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.util.ArrayList;

public class StatusFilterInteractorImpl implements StatusContract.StatusFilterInteractor {

    private HawkStorage hawkStorage;

    public StatusFilterInteractorImpl(Context context){
        hawkStorage = new HawkStorage(context);
        if (!hawkStorage.getStatusTypeData().isEmpty()){
            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add("Processing");
            arrayList.add("Waiting");
            arrayList.add("Approved");
            arrayList.add("Rejected");
            hawkStorage.setStatusTypeData(arrayList);
        }
    }

    @Override
    public void getStatusList(OnFinishedListenerStatusFilter onFinishedListenerStatusFilter) {
        onFinishedListenerStatusFilter.onStatusListFetched(hawkStorage.getStatusTypeData());
    }
}
