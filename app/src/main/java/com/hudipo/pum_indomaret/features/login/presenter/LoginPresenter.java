package com.hudipo.pum_indomaret.features.login.presenter;

import android.content.Context;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.login.view.LoginContract;

public class LoginPresenter implements LoginContract.LoginPresenterView<LoginContract.LoginView>  {
    private Context context;
    private LoginContract.LoginView mView;

    public LoginPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(LoginContract.LoginView view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
    }

    @Override
    public void setLogin(String NIK, String password) {
        if(validate(NIK, password)){
            mView.showLoading();
            // TODO: 16/08/19 connect ke server
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
}
