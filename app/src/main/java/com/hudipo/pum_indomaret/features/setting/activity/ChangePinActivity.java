package com.hudipo.pum_indomaret.features.setting.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.setting.fragment.CurrentPinFragment;
import com.hudipo.pum_indomaret.features.setting.fragment.FragmentCallback;
import com.hudipo.pum_indomaret.features.setting.fragment.NewPinConfirmFragment;
import com.hudipo.pum_indomaret.features.setting.fragment.NewPinFragment;
import com.hudipo.pum_indomaret.features.setting.presenter.ChangePinPresenter;
import com.hudipo.pum_indomaret.features.setting.view.ChangePinContract;
import com.hudipo.pum_indomaret.features.status.StatusActivity;
import com.hudipo.pum_indomaret.utils.Global;
import com.hudipo.pum_indomaret.utils.StartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_PIN;

public class ChangePinActivity extends AppCompatActivity implements FragmentCallback, ChangePinContract.ChangePinView {
    @BindView(R.id.loading)
    ProgressBar loading;

    public static final int CURRENT = 0;
    public static final int NEW = 1;
    public static final int NEW_CONFIRM = 2;
    private String currentPin = "";
    private String newPin = "";

    private ChangePinPresenter presenter;

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
        finish();
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        loading.setVisibility(View.GONE);
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
