package com.hudipo.pum_indomaret.features.pin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PinActivity extends AppCompatActivity implements View.OnClickListener {

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

    private String pin="";
    private boolean isReset = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        ButterKnife.bind(this);
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
        }

        if (pin.length()==6){
            Toast.makeText(this, pin, Toast.LENGTH_SHORT).show();
            Handler handler = new Handler();
            handler.postDelayed(this::wrongPin,300);
        }
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

    private void wrongPin(){
        isReset=true;
        tvPin1.setTextColor(getResources().getColor(R.color.redError));
        tvPin2.setTextColor(getResources().getColor(R.color.redError));
        tvPin3.setTextColor(getResources().getColor(R.color.redError));
        tvPin4.setTextColor(getResources().getColor(R.color.redError));
        tvPin5.setTextColor(getResources().getColor(R.color.redError));
        tvPin6.setTextColor(getResources().getColor(R.color.redError));
    }
}
