package com.hudipo.pum_indomaret.features.approval.detail.presenter;

import android.content.Context;
import android.util.Log;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.detail.view.ApprovalDetailContract;
import com.hudipo.pum_indomaret.features.approval.view.ApprovalContract;
import com.hudipo.pum_indomaret.model.approval.ApprovalListModel;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ApprovalDetailPresenter implements ApprovalDetailContract.ApprovalDetailPresenterView<ApprovalDetailContract.ApprovalDetailView> {
    private Context context;
    private ApprovalDetailContract.ApprovalDetailView mView;
    private CompositeDisposable composite = new CompositeDisposable();
    private HawkStorage hawkStorage;

    public ApprovalDetailPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(ApprovalDetailContract.ApprovalDetailView view) {
        mView = view;
        hawkStorage = new HawkStorage(context);
    }

    @Override
    public void onDetach() {
        mView = null;
        composite.clear();
    }

    @Override
    public void getData(Integer pumTrxId) {
        mView.showLoading();
        composite.add(new ApiServices().getApiPumServices().getDetailApproval(pumTrxId)
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
                mView.dismissLoading();
                mView.error(context.getString(R.string.err_server));
            })
        );
    }

    @Override
    public void approve(ApprovalListModel approvalModels) {

    }

    @Override
    public void reject(ApprovalListModel approvalModels) {

    }
}
