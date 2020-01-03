package com.hudipo.pum_indomaret.features.requestpum.presenter;

import android.content.Context;
import android.net.Uri;
import android.os.FileUtils;
import android.util.Log;

import com.hudipo.pum_indomaret.features.requestpum.contract.ReqValidationContract;
import com.hudipo.pum_indomaret.model.RequestModel;
import com.hudipo.pum_indomaret.model.createpum.CreatePumResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.utils.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReqValidationPresenter implements ReqValidationContract.ReqValidationPresenterView<ReqValidationContract.ReqValidationView> {

    private ReqValidationContract.ReqValidationView mView;
    private CompositeDisposable composite = new CompositeDisposable();
    private Context context;

    public ReqValidationPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(ReqValidationContract.ReqValidationView view) {
        mView  = view;
    }

    @Override
    public void onDetach() {
        if (mView != null){
            mView = null;
            composite.dispose();
        }
    }

    @Override
    public void clearComposite() {
        composite.clear();
    }

    @Override
    public void createPumToServer(RequestModel requestModel) {
        mView.showProgress();
        HashMap<String, RequestBody> params = new HashMap<>();
        if (requestModel != null){
            RequestBody requestEmpId = RequestBody.create(String.valueOf(requestModel.getEmpId()), MediaType.parse("text/plain"));
            RequestBody requestEmpDept = RequestBody.create(String.valueOf(requestModel.getEmpDeptId()), MediaType.parse("text/plain"));
            RequestBody requestUseDate = RequestBody.create(requestModel.getUseDate(), MediaType.parse("text/plain"));
            RequestBody requestRespDate = RequestBody.create(requestModel.getRespDate(), MediaType.parse("text/plain"));
            RequestBody requestDocNum = RequestBody.create(requestModel.getDocNum(), MediaType.parse("text/plain"));
            RequestBody requestTrxType = RequestBody.create(String.valueOf(requestModel.getTrxTypeId()), MediaType.parse("text/plain"));
            RequestBody requestDesc = RequestBody.create(requestModel.getDescription(), MediaType.parse("text/plain"));
            RequestBody requestAmount = RequestBody.create(String.valueOf(requestModel.getAmount()), MediaType.parse("text/plain"));
            RequestBody requestPin = RequestBody.create(requestModel.getPin(), MediaType.parse("text/plain"));
            RequestBody requestOrgId = RequestBody.create(String.valueOf(requestModel.getOrgId()), MediaType.parse("text/plain"));
            RequestBody requestUserId = RequestBody.create(String.valueOf(requestModel.getUserId()), MediaType.parse("text/plain"));

            params.put("emp_id", requestEmpId);
            params.put("emp_dept", requestEmpDept);
            params.put("use_date", requestUseDate);
            params.put("resp_date", requestRespDate);
            params.put("doc_num", requestDocNum);
            params.put("trx_type", requestTrxType);
            params.put("description", requestDesc);
            params.put("amount", requestAmount);
            params.put("pin", requestPin);
            params.put("org_id", requestOrgId);
            params.put("user_id", requestUserId);

            //Configuration for file
            String realPath;
            File file;
            MultipartBody.Part bodyFile = null;
            if (requestModel.getFileDataUri() != null && !requestModel.getFileDataUri().isEmpty()){
                if (requestModel.isImage()){
                    realPath = Utils.getRealPathImageFromURI(context, Uri.parse(requestModel.getFileDataUri()));
                }else {
                    realPath = requestModel.getPathDocument();
                }
                file = new File(realPath);
                String typeFile = context.getContentResolver().getType(Uri.parse(requestModel.getFileDataUri()));

                RequestBody requestFile = RequestBody.create(file, MediaType.parse(Objects.requireNonNull(typeFile)));
                bodyFile = MultipartBody.Part.createFormData("file_data", file.getName(), requestFile);
            }

            composite.add(new ApiServices().getApiPumServices()
                    .createPum(params, bodyFile)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        mView.hideProgress();
                        if (response.isSuccessful()){
                            CreatePumResponse createPumResponse = response.body();
                            if (createPumResponse != null && !createPumResponse.isError()) {
                                mView.successCreatePum();
                            }else {
                                mView.failedCreatePum("Something error");
                            }
                        }else {
                            mView.failedCreatePum("Error from server");
                        }
                    }, throwable -> {
                        mView.hideProgress();
                        mView.failedCreatePum(throwable.getMessage());
                    }));
        }
    }
}
