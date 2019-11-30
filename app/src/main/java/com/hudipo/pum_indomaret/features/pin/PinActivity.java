package com.hudipo.pum_indomaret.features.pin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.utils.HawkStorage;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
public class PinActivity extends AppCompatActivity implements View.OnClickListener, PinContract.PinView {

    @BindView(R.id.key_0)
    TextView key0;
    @BindView(R.id.key_1)
    TextView key1;
    @BindView(R.id.key_2)
    TextView key2;
    @BindView(R.id.key_3)
    TextView key3;
    @BindView(R.id.key_4)
    TextView key4;
    @BindView(R.id.key_5)
    TextView key5;
    @BindView(R.id.key_6)
    TextView key6;
    @BindView(R.id.key_7)
    TextView key7;
    @BindView(R.id.key_8)
    TextView key8;
    @BindView(R.id.key_9)
    TextView key9;
    @BindView(R.id.key_fingerprint)
    ImageView keyFingerprint;
    @BindView(R.id.key_backspace)
    ImageView keyBackspace;
    @BindView(R.id.tvPin1)
    TextView tvPin1;
    @BindView(R.id.tvPin2)
    TextView tvPin2;
    @BindView(R.id.tvPin3)
    TextView tvPin3;
    @BindView(R.id.tvPin4)
    TextView tvPin4;
    @BindView(R.id.tvPin5)
    TextView tvPin5;
    @BindView(R.id.tvPin6)
    TextView tvPin6;
    @BindView(R.id.tvErrorPassword)
    TextView tvErrorPassword;
    @BindView(R.id.pbPin)
    ProgressBar pbPin;

    private String pin="";
    private boolean isReset = false;
    private PinPresenter pinPresenter;
    public static final String EXTRA_PIN = "extra_pin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        ButterKnife.bind(this);
        onAttachView();

        if (savedInstanceState!=null){
            restoreView(Objects.requireNonNull(savedInstanceState.getString("PIN")).length());
        }

        key0.setOnClickListener(this);
        key1.setOnClickListener(this);
        key2.setOnClickListener(this);
        key3.setOnClickListener(this);
        key4.setOnClickListener(this);
        key5.setOnClickListener(this);
        key6.setOnClickListener(this);
        key7.setOnClickListener(this);
        key8.setOnClickListener(this);
        key9.setOnClickListener(this);
        keyFingerprint.setOnClickListener(this);
        keyBackspace.setOnClickListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        pinPresenter.clearComposite();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetachView();
    }

    private void restoreView(int pinLength) {
        if (pinLength==1){
            tvPin1.setVisibility(View.VISIBLE);
        }else if(pinLength==2){
            tvPin1.setVisibility(View.VISIBLE);
            tvPin2.setVisibility(View.VISIBLE);
        }else if(pinLength==3){
            tvPin1.setVisibility(View.VISIBLE);
            tvPin2.setVisibility(View.VISIBLE);
            tvPin3.setVisibility(View.VISIBLE);
        }else if(pinLength==4){
            tvPin1.setVisibility(View.VISIBLE);
            tvPin2.setVisibility(View.VISIBLE);
            tvPin3.setVisibility(View.VISIBLE);
            tvPin4.setVisibility(View.VISIBLE);
        }else if(pinLength==5){
            tvPin1.setVisibility(View.VISIBLE);
            tvPin2.setVisibility(View.VISIBLE);
            tvPin3.setVisibility(View.VISIBLE);
            tvPin4.setVisibility(View.VISIBLE);
            tvPin5.setVisibility(View.VISIBLE);
        }else {
            tvPin1.setVisibility(View.VISIBLE);
            tvPin2.setVisibility(View.VISIBLE);
            tvPin3.setVisibility(View.VISIBLE);
            tvPin4.setVisibility(View.VISIBLE);
            tvPin5.setVisibility(View.VISIBLE);
            tvPin6.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (isReset){
            pin="";
            isReset=false;
            hideTv();
        }

        if (view.getTag()!=null && view.getTag().equals("number_button") && pin.length()<6){
            pin = pin+((TextView)view).getText().toString();
            if (pin.length()==1){
                tvPin1.setVisibility(View.VISIBLE);
            }else if (pin.length()==2){
                tvPin2.setVisibility(View.VISIBLE);
            }else if (pin.length()==3){
                tvPin3.setVisibility(View.VISIBLE);
            }else if (pin.length()==4){
                tvPin4.setVisibility(View.VISIBLE);
            }else if (pin.length()==5){
                tvPin5.setVisibility(View.VISIBLE);
            }else tvPin6.setVisibility(View.VISIBLE);
        }else{
            switch (view.getId()){
                case R.id.key_backspace :
                    if (pin.length()>0){
                        pin = pin.substring(0,pin.length()-1);
                        deletePin();
                    }
                    break;

                case R.id.key_fingerprint :
                    Toast.makeText(this, "fingerprint", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        if (pin.length()==6){
            HawkStorage hawkStorage = new HawkStorage(this);
            pinPresenter.checkPinToServer(hawkStorage.getUserData().getEmpId(), pin);
        }
    }


    private void deletePin() {
        if (pin.length()==1){
            tvPin2.setVisibility(View.INVISIBLE);
        }else if (pin.length()==2){
            tvPin3.setVisibility(View.INVISIBLE);
        }else if (pin.length()==3){
            tvPin4.setVisibility(View.INVISIBLE);
        }else if (pin.length()==4){
            tvPin5.setVisibility(View.INVISIBLE);
        }else if (pin.length()==5){
            tvPin6.setVisibility(View.INVISIBLE);
        }else tvPin1.setVisibility(View.INVISIBLE);
    }

    private void hideTv() {
        tvPin1.setVisibility(View.INVISIBLE);
        tvPin2.setVisibility(View.INVISIBLE);
        tvPin3.setVisibility(View.INVISIBLE);
        tvPin4.setVisibility(View.INVISIBLE);
        tvPin5.setVisibility(View.INVISIBLE);
        tvPin6.setVisibility(View.INVISIBLE);
        tvPin1.setTextColor(Color.BLACK);
        tvPin2.setTextColor(Color.BLACK);
        tvPin3.setTextColor(Color.BLACK);
        tvPin4.setTextColor(Color.BLACK);
        tvPin5.setTextColor(Color.BLACK);
        tvPin6.setTextColor(Color.BLACK);
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        if (pin.length()!=0){
            outState.putString("PIN",pin);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showProgress() {
        pbPin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbPin.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showFailed() {
        tvPin1.setTextColor(getResources().getColor(R.color.redError));
        tvPin2.setTextColor(getResources().getColor(R.color.redError));
        tvPin3.setTextColor(getResources().getColor(R.color.redError));
        tvPin4.setTextColor(getResources().getColor(R.color.redError));
        tvPin5.setTextColor(getResources().getColor(R.color.redError));
        tvPin6.setTextColor(getResources().getColor(R.color.redError));
        tvErrorPassword.setVisibility(View.VISIBLE);
        isReset=true;
    }

    @Override
    public void showSuccess(String pin) {
        tvErrorPassword.setVisibility(View.INVISIBLE);
        isReset=true;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PIN, pin);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onAttachView() {
        pinPresenter = new PinPresenter();
        pinPresenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        pinPresenter.onDetach();
    }
}
