package com.hudipo.pum_indomaret.features.register;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.textfield.TextInputLayout;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.login.LoginActivity;
import com.hudipo.pum_indomaret.helper.CustomLoadingProgress;
import com.hudipo.pum_indomaret.utils.CustomKeyboard;
import com.hudipo.pum_indomaret.utils.StartActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.RegisterView{

    @BindView(R.id.etNIKReg)
    EditText etNIKReg;
    @BindView(R.id.etPasswordReg)
    EditText etPasswordReg;
    @BindView(R.id.etPinReg)
    EditText etPinReg;
    @BindView(R.id.tilNIKReg)
    TextInputLayout tilNIKReg;
    @BindView(R.id.tilPasswordReg)
    TextInputLayout tilPasswordReg;
    @BindView(R.id.tilPinReg)
    TextInputLayout tilPinReg;

    private CustomLoadingProgress loadingProgress = new CustomLoadingProgress();
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        onAttachView();
        keyboardPinReg();
    }

    private void keyboardPinReg() {
        etPinReg.setCursorVisible(false);
        etPinReg.setOnFocusChangeListener((view, b) -> {
            if (view.equals(etPinReg) && b) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(
                        android.content.Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(inputManager).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                showKeyboard();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetachView();
    }

    @OnClick(R.id.btnRegister)
    void register() {
        if (validate()) {
            registerPresenter.registerToServer(
                    etNIKReg.getText().toString().trim(),
                    etPasswordReg.getText().toString().trim(),
                    etPinReg.getText().toString().trim());
        }
    }

    @OnClick(R.id.tvLogin)
    void login(){
        startActivity(new Intent(this, LoginActivity.class));
        Animatoo.animateSlideLeft(this);
    }

    @SuppressLint("InflateParams")
    private void showKeyboard() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(this));
        LayoutInflater inflater = this.getLayoutInflater();
        View custom_dialog = inflater.inflate(R.layout.show_keyboard, null);
        CustomKeyboard customKeyboard = custom_dialog.findViewById(R.id.customKeyboard);
        alertDialog.setView(custom_dialog);
        alertDialog.setOnDismissListener(dialogInterface -> etPinReg.clearFocus());

        alertDialog.setPositiveButton("OK", (dialogInterface, i) -> {
            etPinReg.setText(customKeyboard.getInputText());
            dialogInterface.dismiss();
        });

        alertDialog.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        alertDialog.show();
    }

    private boolean validate() {
        boolean isValid = true;
        if (etNIKReg.getText().toString().isEmpty()) {
            tilNIKReg.setError(getString(R.string.err_nik));
            isValid = false;
        } else {
            tilNIKReg.setError(null);
        }

        if (etPasswordReg.getText().toString().isEmpty()) {
            tilPasswordReg.setError(getString(R.string.err_password));
            isValid = false;
        } else {
            tilPasswordReg.setError(null);
        }

        if (etPinReg.getText().toString().isEmpty()) {
            tilPinReg.setError(getString(R.string.err_null_pin));
            isValid = false;
        } else if (etPinReg.getText().toString().length()<6) {
            tilPinReg.setError(getString(R.string.err_pin));
            isValid = false;
        } else {
            tilPinReg.setError(null);
        }
        return isValid;
    }

    @Override
    public void showLoading() {
        loadingProgress.showCustomDialog(this);
    }

    @Override
    public void hideLoading() {
        loadingProgress.closeCustomDialog();
    }

    @Override
    public void failedRegister(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void registerSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        StartActivity.goTo(this, LoginActivity.class);
        Animatoo.animateSlideLeft(this);
        finish();
    }

    @Override
    public void errorServer() {
        Toast.makeText(this, getString(R.string.err_server), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttachView() {
        registerPresenter = new RegisterPresenter();
        registerPresenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        registerPresenter.onDetach();
    }
}

