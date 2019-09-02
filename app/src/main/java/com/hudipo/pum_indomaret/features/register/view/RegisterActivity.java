package com.hudipo.pum_indomaret.features.register.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.helper.CustomLoadingProgress;
import com.hudipo.pum_indomaret.utils.CustomKeyboard;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.etNIKReg)
    EditText etNIKReg;
    @BindView(R.id.etPasswordReg)
    EditText etPasswordReg;
    EditText etPinReg;
    @BindView(R.id.tilNIKReg)
    TextInputLayout tilNIKReg;
    @BindView(R.id.tilPasswordReg)
    TextInputLayout tilPasswordReg;
    @BindView(R.id.tilPinReg)
    TextInputLayout tilPinReg;

    private CustomLoadingProgress loadingProgress = new CustomLoadingProgress();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        etPinReg = findViewById(R.id.etPinReg);
        etPinReg.setCursorVisible(false);

        etPinReg.setOnTouchListener((view, motionEvent) -> {
            etPinReg.requestFocus();
            return true;
        });

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

    private void showKeyboard() {
        Log.d("TAG", "keyboad");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Objects.requireNonNull(this));
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View custom_dialog = inflater.inflate(R.layout.show_keyboard, null);
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

    @OnClick(R.id.btnRegister)
    void register() {
        if (validate()) {
            loadingProgress.showCustomDialog(this);
            Handler handler = new Handler();
            handler.postDelayed(() -> loadingProgress.closeCustomDialog(), 1200);
        }
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
}

