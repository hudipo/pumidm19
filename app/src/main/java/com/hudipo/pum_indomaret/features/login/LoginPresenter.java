package com.hudipo.pum_indomaret.features.login;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.login.LoginResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.networking.RetrofitClient;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.LoginPresenterView<LoginContract.LoginView>  {
    private Context context;
    private LoginContract.LoginView mView;
    private CompositeDisposable composite = new CompositeDisposable();
    private HawkStorage hawkStorage;

    private static final String TAG = "LOGIN_PRESENTER";

    LoginPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(LoginContract.LoginView view) {
        mView = view;
        hawkStorage = new HawkStorage(context);
    }

    @Override
    public void onDetach() {
        mView = null;
        composite.clear();
    }

    @Override
    public void setLogin(String NIK, String password) {
        if(validate(NIK, password)){
            mView.showLoading();
            loginToServer(NIK, password);
        }
    }

    private boolean validate(String NIK, String password) {
        boolean isValid = true;
        if(NIK.isEmpty()){
            mView.errorNIK(context.getString(R.string.err_nik));
            isValid = false;
        }else {
            mView.errorNIK(null);
        }

        if(password.isEmpty()){
            mView.errorPassword(context.getString(R.string.err_password));
            isValid = false;
        }else {
            mView.errorPassword(null);
        }
        return isValid;
    }

    private void loginToServer(String nik, String password) {
        HashMap<String, RequestBody> params = new HashMap<>();
        RequestBody user = RequestBody.create(MediaType.parse("text/plain"), nik);
        RequestBody pass = RequestBody.create(MediaType.parse("text/plain"), password);
        params.put("emp_num", user);
        params.put("password", pass);

        composite.add(new ApiServices().getApiPumServices().login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    mView.dismissLoading();
                    if (response.code() == 200){
                        LoginResponse loginResponse = response.body();
                        if (loginResponse != null){
                            if (!loginResponse.isError()){
                                mView.failedLogin(loginResponse.getMessage());
                            }else {
                                hawkStorage.setUserData(loginResponse.getUser());
                                hawkStorage.setLogin(true);
                                mView.loginSuccess();
                            }
                        }
                    }else {
                        Converter<ResponseBody, LoginResponse> errorConverter =
                                RetrofitClient.client().responseBodyConverter(LoginResponse.class, new Annotation[0]);
                        LoginResponse errorResponse;
                        try {
                            if (response.errorBody() != null){
                                errorResponse = errorConverter.convert(response.errorBody());

                                if (errorResponse != null){
                                    mView.failedLogin(errorResponse.getMessage());
                                }
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }

                }, throwable -> {
                    mView.dismissLoading();
                    mView.errorServer();
                })
        );
    }
}
