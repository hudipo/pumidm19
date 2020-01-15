package com.hudipo.pum_indomaret.features.register;

import android.content.Intent;
import android.util.Log;

import com.hudipo.pum_indomaret.features.login.LoginActivity;
import com.hudipo.pum_indomaret.model.register.RegisterResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.networking.RetrofitClient;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RegisterPresenter implements RegisterContract.RegisterPresenterView<RegisterContract.RegisterView>{

    private RegisterContract.RegisterView mView;
    private CompositeDisposable composite = new CompositeDisposable();

    @Override
    public void onAttach(RegisterContract.RegisterView view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
        composite.clear();
    }

    @Override
    public void registerToServer(String empNum, String password, String pin) {
        HashMap<String, RequestBody> params = new HashMap<>();
        RequestBody requestEmpNum = RequestBody.create(empNum, MediaType.parse("text/plain"));
        RequestBody requestPass = RequestBody.create(password, MediaType.parse("text/plain"));
        RequestBody requestPin = RequestBody.create(pin, MediaType.parse("text/plain"));
        params.put("emp_num", requestEmpNum);
        params.put("password", requestPass);
        params.put("pin", requestPin);

        mView.showLoading();

        composite.add(new ApiServices().getApiPumServices()
                .register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mView.hideLoading();
                    if (response.code() == 200){
                        RegisterResponse registerResponse = response.body();
                        if (registerResponse != null){
                            if (!registerResponse.isError()){
                                mView.registerSuccess(registerResponse.getMessage());
                            }else {
                                mView.failedRegister(registerResponse.getMessage());
                            }
                        }
                    }else {
                        Converter<ResponseBody, RegisterResponse> errorConverter =
                                RetrofitClient.client().responseBodyConverter(RegisterResponse.class, new Annotation[0]);
                        RegisterResponse errorResponse;
                        try {
                            if (response.errorBody() != null){
                                errorResponse = errorConverter.convert(response.errorBody());

                                if (errorResponse != null){
                                    mView.failedRegister(errorResponse.getMessage());
                                }
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }, throwable -> {
                    mView.hideLoading();
                    mView.errorServer();
                }));
    }
}
