package com.hudipo.pum_indomaret.features.login.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
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
    @BindView(R.id.tilNIK)
    TextInputLayout tilNIK;
    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;

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
        Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show();
    }

    private boolean validate() {
        boolean isValid = true;
        if(etNIK.getText().toString().isEmpty()){
            tilNIK.setError(getString(R.string.err_nik));
            isValid = false;
        }

        if(etPassword.getText().toString().isEmpty()){
            tilPassword.setError(getString(R.string.err_password));
            isValid = false;
        }
        return isValid;
    }
}
