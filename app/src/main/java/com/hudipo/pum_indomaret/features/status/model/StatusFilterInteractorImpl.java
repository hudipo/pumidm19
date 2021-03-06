package com.hudipo.pum_indomaret.features.status.model;

import android.content.Context;

import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.util.ArrayList;

public class StatusFilterInteractorImpl implements StatusContract.StatusFilterInteractor {

    private HawkStorage hawkStorage;

    public StatusFilterInteractorImpl(Context context){
    }

    @Override
    public void getStatusList(OnFinishedListenerStatusFilter onFinishedListenerStatusFilter) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("--");
        arrayList.add("New");
        arrayList.add("Process");
        arrayList.add("Invoice");
        arrayList.add("Reject");
        onFinishedListenerStatusFilter.onStatusListFetched(arrayList);
    }
}
