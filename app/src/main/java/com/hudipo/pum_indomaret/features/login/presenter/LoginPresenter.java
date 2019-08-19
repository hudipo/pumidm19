package com.hudipo.pum_indomaret.features.login.presenter;

import android.content.Context;
import android.util.Log;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.login.view.LoginContract;
import com.hudipo.pum_indomaret.model.login.LoginResponse;
import com.hudipo.pum_indomaret.networking.APIService;
import com.hudipo.pum_indomaret.networking.RetrofitClient;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginPresenter implements LoginContract.LoginPresenterView<LoginContract.LoginView>  {
    private Context context;
    private LoginContract.LoginView mView;
    private CompositeDisposable disposables = new CompositeDisposable();
    private APIService apiService;

    private static final String TAG = "LOGIN_PRESENTER";

    public LoginPresenter(Context context) {
        this.context = context;
        apiService = RetrofitClient.client().create(APIService.class);
    }

    @Override
    public void onAttach(LoginContract.LoginView view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
        disposables.clear();
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
        HashMap<String, RequestBody> params = new HashMap<String, RequestBody>();
        RequestBody user = RequestBody.create(MediaType.parse("text/plain"), nik);
        RequestBody pass = RequestBody.create(MediaType.parse("text/plain"), password);
        params.put("emp_num", user);
        params.put("password", pass);

        disposables.add(apiService.login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse loginResponse) {
                        mView.dismissLoading();
                        if(loginResponse.getError()){
                            mView.failedLogin(loginResponse.getMessage());
                        }else {
                            mView.loginSuccess();
                            // TODO: 19/08/19 save user data to preferences

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                        mView.dismissLoading();
                        mView.errorServer();
                    }
                })
        );
    }
}
