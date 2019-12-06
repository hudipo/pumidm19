package com.hudipo.pum_indomaret.features.approval.presenter;

import android.content.Context;
import android.util.Log;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.view.ApprovalHistoryContract;
import com.hudipo.pum_indomaret.model.approval.ApprovalListResponse;
import com.hudipo.pum_indomaret.model.approval.history.ApprovalHistoryListResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.networking.RetrofitClient;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class ApprovalHistoryPresenter implements ApprovalHistoryContract.ApprovalHistoryPresenterView<ApprovalHistoryContract.ApprovalHistoryView> {
    private Context context;
    private ApprovalHistoryContract.ApprovalHistoryView mView;
    private CompositeDisposable composite = new CompositeDisposable();
    private HawkStorage hawkStorage;

    public ApprovalHistoryPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(ApprovalHistoryContract.ApprovalHistoryView view) {
        this.mView = view;
        hawkStorage = new HawkStorage(context);
    }

    @Override
    public void onDetach() {
        mView = null;
        composite.clear();
    }

    @Override
    public void getData(HashMap<String, RequestBody> params) {
        RequestBody empId = RequestBody.create(String.valueOf(hawkStorage.getUserData().getEmpId()), MediaType.parse("text/plain"));
        params.put("emp_id", empId);
        mView.showLoading();
        composite.add(new ApiServices().getApiPumServices().getListHistoryApproval(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mView.dismissLoading();
                    if(response.isSuccessful()){
                        ApprovalHistoryListResponse approvalHistoryListResponse = response.body();
                        if (approvalHistoryListResponse != null){
                            if (!approvalHistoryListResponse.getError() && approvalHistoryListResponse.getData()!=null){
                                mView.showData(approvalHistoryListResponse.getData());
                            }else {
                                mView.error(approvalHistoryListResponse.getMessage());
                            }
                        }else {
                            mView.error(context.getString(R.string.err_server));
                        }
                    }else {
                        Converter<ResponseBody, ApprovalHistoryListResponse> errorConverter =
                                RetrofitClient.client().responseBodyConverter(ApprovalHistoryListResponse.class, new Annotation[0]);
                        ApprovalHistoryListResponse errorResponse;
                        try {
                            if (response.errorBody() != null){
                                errorResponse = errorConverter.convert(response.errorBody());
                                if (errorResponse != null){
                                    mView.error(errorResponse.getMessage());
                                }
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                            mView.error(context.getString(R.string.err_server));
                        }
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
    public void reject(List list) {

    }

    @Override
    public void approve(List list) {

    }
}
