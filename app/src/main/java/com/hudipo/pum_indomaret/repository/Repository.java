package com.hudipo.pum_indomaret.repository;

import android.content.Context;

import com.hudipo.pum_indomaret.model.departement.DepartmentResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class Repository {

    private HawkStorage hawkStorage;

    public Repository(HawkStorage hawkStorage) {
        this.hawkStorage = hawkStorage;
    }

    public interface RepositoryCallback{
        interface LoadDataDept{
            void onDataLoad(DepartmentResponse departmentResponse);
            void onDataError(Throwable throwable);
        }
    }

    public void getDataDepartment(CompositeDisposable composite, RepositoryCallback.LoadDataDept callback){

        composite.add(new ApiServices().getApiPumServices()
                .getDepartment(hawkStorage.getUserData().getOrgId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(departmentResponse -> {
                    if (departmentResponse != null){
                        callback.onDataLoad(departmentResponse);
                    }
                }, callback::onDataError));
    }


}