package com.hudipo.pum_indomaret.features.login.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.features.login.presenter.LoginPresenter;
import com.hudipo.pum_indomaret.features.login.view.LoginContract;
import com.hudipo.pum_indomaret.features.register.view.RegisterActivity;
import com.hudipo.pum_indomaret.helper.CustomLoadingProgress;
import com.hudipo.pum_indomaret.utils.StartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView {
    @BindView(R.id.etNIK)
    EditText etNIK;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.tilNIK)
    TextInputLayout tilNIK;
    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;

    private CustomLoadingProgress loadingProgress = new CustomLoadingProgress();
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        onAttachView();
    }

    @OnClick(R.id.btnLogin)
    void login(){
        presenter.setLogin(etNIK.getText().toString().trim(), etPassword.getText().toString().trim());
    }

    @OnClick(R.id.tvSignUp)
    void signUp(){
        StartActivity.goTo(this, RegisterActivity.class);
    }

    @Override
    public void errorNIK(String message) {
        tilNIK.setError(message);
    }

    @Override
    public void errorPassword(String message) {
        tilPassword.setError(message);
    }

    @Override
    public void failedLogin(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        loadingProgress.showCustomDialog(this);
    }

    @Override
    public void dismissLoading() {
        loadingProgress.closeCustomDialog();
    }

    @Override
    public void loginSuccess() {
        StartActivity.goTo(this, HomeActivity.class);
        finishAffinity();
    }

    @Override
    public void errorServer() {
        Toast.makeText(this, getString(R.string.err_server), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttachView() {
        presenter = new LoginPresenter(this);
        presenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}
