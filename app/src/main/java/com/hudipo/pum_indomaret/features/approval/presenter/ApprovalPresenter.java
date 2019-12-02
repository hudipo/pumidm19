package com.hudipo.pum_indomaret.features.approval.presenter;

import android.content.Context;
import android.util.Log;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.view.ApprovalContract;
import com.hudipo.pum_indomaret.model.approval.ApprovalListModel;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ApprovalPresenter implements ApprovalContract.ApprovalPresenterView<ApprovalContract.ApprovalView> {
    private Context context;
    private ApprovalContract.ApprovalView mView;
    private CompositeDisposable composite = new CompositeDisposable();
    private HawkStorage hawkStorage;

    public ApprovalPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(ApprovalContract.ApprovalView view) {
        mView = view;
        hawkStorage = new HawkStorage(context);
    }

    @Override
    public void onDetach() {
        mView = null;
        composite.clear();
    }

    @Override
    public void getData() {
        mView.showLoading();
        composite.add(new ApiServices().getApiPumServices().getListApproval(hawkStorage.getUserData().getEmpId())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                mView.dismissLoading();
                if (response != null){
                    if (!response.getError() && response.getData()!=null){
                        mView.showData(response.getData());
                    }else {
                        mView.error(response.getMessage());
                    }
                }else {
                    mView.error(context.getString(R.string.err_server));
                }

            }, throwable -> {
                Log.d("fakhri", "getData: "+throwable.getMessage());
                mView.dismissLoading();
                mView.error(context.getString(R.string.err_server));
            })
        );
    }

    @Override
    public void searchData(String query) {

    }

    @Override
    public void reject(List<ApprovalListModel> list, String pin, String reasonValidation) {
        mView.showLoading();
        //dapatkan pum trx id
        List<Integer> pumTrxIds = new ArrayList<>();
        for(ApprovalListModel a : list){
            pumTrxIds.add(a.getPUMTRXID());
        }
        Map<String, String> params = new HashMap<>();
        params.put("kode","0");
        params.put("emp_id", String.valueOf(hawkStorage.getUserData().getEmpId()));
        params.put("pin",pin);
        if(!reasonValidation.isEmpty()){
            params.put("reason_validate", reasonValidation);
        }
        composite.add(new ApiServices().getApiPumServices().approve(pumTrxIds, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mView.dismissLoading();
                    if (response != null){
                        if (!response.isError()){
                            mView.success(0);
                            mView.closeAction();
                            getData();
                        }else {
                            mView.error(response.getMessage());
                        }
                    }else {
                        mView.error(context.getString(R.string.err_server));
                    }

                }, throwable -> {
                    Log.d("fakhri", "getData: "+throwable.getMessage());
                    mView.dismissLoading();
                    mView.error(context.getString(R.string.err_server));
                })
        );
    }

    @Override
    public void approve(List<ApprovalListModel> list, String pin) {
        mView.showLoading();
        //dapatkan pum trx id
        List<Integer> pumTrxIds = new ArrayList<>();
        for(ApprovalListModel a : list){
            pumTrxIds.add(a.getPUMTRXID());
        }
        Map<String, String> params = new HashMap<>();
        params.put("kode","1");
        params.put("emp_id", String.valueOf(hawkStorage.getUserData().getEmpId()));
        params.put("pin",pin);
        composite.add(new ApiServices().getApiPumServices().approve(pumTrxIds, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mView.dismissLoading();
                    if (response != null){
                        if (!response.isError()){
                            mView.success(1);
                            mView.closeAction();
                            getData();
                        }else {
                            mView.error(response.getMessage());
                        }
                    }else {
                        mView.error(context.getString(R.string.err_server));
                    }

                }, throwable -> {
                    Log.d("fakhri", "getData: "+throwable.getMessage());
                    mView.dismissLoading();
                    mView.error(context.getString(R.string.err_server));
                })
        );
    }
}
