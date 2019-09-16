package com.hudipo.pum_indomaret.features.setting.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.setting.fragment.CurrentPinFragment;
import com.hudipo.pum_indomaret.features.setting.fragment.FragmentCallback;
import com.hudipo.pum_indomaret.features.setting.fragment.NewPinConfirmFragment;
import com.hudipo.pum_indomaret.features.setting.fragment.NewPinFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_PIN;

public class ChangePinActivity extends AppCompatActivity implements FragmentCallback {
    public static final int CURRENT = 0;
    public static final int NEW = 1;
    public static final int NEW_CONFIRM = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);

        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,new CurrentPinFragment());
        fragmentTransaction.commit();
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
    public void nextFragment(int i) {
        switch (i){
            case CURRENT :
                loadFragment(new NewPinFragment());
                break;
            case NEW_CONFIRM:
                // TODO: 16/09/19 show success
                break;
        }
    }

    @Override
    public void nextFragment(int i, String pin) {
        if(i == NEW){
            Fragment newPinConfirmFragment = new NewPinConfirmFragment();
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_PIN, pin);
            newPinConfirmFragment.setArguments(bundle);
            loadFragment(newPinConfirmFragment);
        }
    }

    @OnClick(R.id.ivBack)
    void back(){
        finish();
    }
}
