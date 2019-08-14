package com.hudipo.pum_indomaret.features.login.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.helper.CustomLoadingProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.etNIK)
    EditText etNIK;
    @BindView(R.id.etPassword)
    EditText etPassword;

    private CustomLoadingProgress loadingProgress = new CustomLoadingProgress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    void login(){
        if(validate()){
            loadingProgress.showCustomDialog(this);
        }
    }

    @OnClick(R.id.tvSignUp)
    void signUp(){
        // TODO: 14/08/19 show toast
    }

    private boolean validate() {
        boolean isValid = true;
        if(etNIK.getText().toString().trim().length()==0){
            etNIK.setError(getString(R.string.err_nik));
            isValid = false;
        }

        if(etPassword.getText().toString().trim().length()==0){
            etPassword.setError(getString(R.string.err_password));
            isValid = false;
        }
        return isValid;
    }
}
