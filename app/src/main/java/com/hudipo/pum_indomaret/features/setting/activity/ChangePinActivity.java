package com.hudipo.pum_indomaret.features.setting.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.setting.fragment.CurrentPinFragment;
import com.hudipo.pum_indomaret.features.setting.fragment.FragmentCallback;
import com.hudipo.pum_indomaret.features.setting.fragment.NewPinConfirmFragment;
import com.hudipo.pum_indomaret.features.setting.fragment.NewPinFragment;
import com.hudipo.pum_indomaret.features.setting.presenter.ChangePinPresenter;
import com.hudipo.pum_indomaret.features.setting.view.ChangePinContract;
import com.hudipo.pum_indomaret.features.status.StatusActivity;
import com.hudipo.pum_indomaret.helper.CustomLoadingProgress;
import com.hudipo.pum_indomaret.utils.Global;
import com.hudipo.pum_indomaret.utils.StartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_PIN;

public class ChangePinActivity extends AppCompatActivity implements FragmentCallback, ChangePinContract.ChangePinView {


    public static final int CURRENT = 0;
    public static final int NEW = 1;
    public static final int NEW_CONFIRM = 2;
    private String currentPin = "";
    private String newPin = "";

    private ChangePinPresenter presenter;
    private CustomLoadingProgress loadingProgress = new CustomLoadingProgress();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);

        ButterKnife.bind(this);
        presenter = new ChangePinPresenter(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,new CurrentPinFragment());
        fragmentTransaction.commit();

        onAttachView();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_right_out);
        fragmentTransaction.add(R.id.frameLayout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void nextFragment(int i, String pin) {
        switch (i){
            case CURRENT :
                currentPin = pin;
                loadFragment(new NewPinFragment());
                break;
            case NEW :
                newPin = pin;
                loadFragment(new NewPinConfirmFragment());
                break;
            case NEW_CONFIRM :
                //cek dulu confirmnya sama gak
                if(newPin.equals(pin)){
                    presenter.changePin(currentPin, newPin);
                }else {
                    Toast.makeText(this, "Confirm PIN doesn't match", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @OnClick(R.id.ivBack)
    void back(){
        onBackPressed();

    }

    @Override
    public void onBackPressed() {
        hideKeyboard();
        super.onBackPressed();
        finish();
    }

    @Override
    public void showLoading() {
        loadingProgress.showCustomDialog(this);
        hideKeyboard();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void dismissLoading() {
        loadingProgress.closeCustomDialog();
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {
        StartActivity.goTo(this, ChangePinSuccessActivity.class);
        finish();
    }

    @Override
    public void onAttachView() {
        presenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetachView();
    }
}
