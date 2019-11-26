package com.hudipo.pum_indomaret.features.pin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.requestpum.SentReqActivity;
import com.hudipo.pum_indomaret.model.RequestModel;
import com.hudipo.pum_indomaret.networking.ApiServices;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
    private int requestCode;

    public static final String KEY_REQUEST_DATA = "KEY_REQUEST_DATA";
    private RequestModel requestModel;
    private CompositeDisposable composite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        ButterKnife.bind(this);

        initComposite();

        if (savedInstanceState!=null){
            restoreView(Objects.requireNonNull(savedInstanceState.getString("PIN")).length());
        }

        if (getIntent()!=null){
            requestCode = getIntent().getIntExtra("requestCode",0);
            requestModel = (RequestModel) getIntent().getSerializableExtra(KEY_REQUEST_DATA);
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

    private void initComposite() {
        composite = new CompositeDisposable();
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
            if (requestModel != null){
                createPumToServer(requestModel, pin);
            }
        }
    }

    private void createPumToServer(RequestModel requestModel, String pinString) {
        HashMap<String, RequestBody> params = new HashMap<>();
//        RequestBody empId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(requestModel.getIdEmployee()));
//        RequestBody empDept = RequestBody.create(MediaType.parse("text/plain"), requestModel.getStringEmployeeDepartment());
//        RequestBody useDate = RequestBody.create(MediaType.parse("text/plain"), requestModel.getStringUseDate());
//        RequestBody respDate = RequestBody.create(MediaType.parse("text/plain"), requestModel.getStringRespDate());
//        RequestBody docNum = RequestBody.create(MediaType.parse("text/plain"), requestModel.getStringDocNumber());
//        RequestBody trxType = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(requestModel.getIdTrxType()));
//        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), requestModel.getStringDescription());
//        RequestBody amount = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(requestModel.getIntAmount()));
//        RequestBody uploadFile = RequestBody.create(MediaType.parse("text/plain"), requestModel.getStringFileUri());
        RequestBody pin = RequestBody.create(pinString, MediaType.parse("text/plain"));

//        params.put("emp_id", empId);
//        params.put("emp_dept", empDept);
//        params.put("use_date", useDate);
//        params.put("resp_date", respDate);
//        params.put("doc_num", docNum);
//        params.put("trx_type", trxType);
//        params.put("description", description);
//        params.put("amount", amount);
//        params.put("upload_file", uploadFile);
        params.put("pin", pin);

        composite.add(new ApiServices()
                .getApiPumServices()
                .createPum(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createPumResponse -> {
                    if (createPumResponse.isError()){
                        failedCreatePum();
                        Toast.makeText(this, "message: "+createPumResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        successCreatePum();
                    }
                }, throwable -> {
                    failedCreatePum();
                    Toast.makeText(this, "message: "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
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

    private void successCreatePum(){
        startActivity(new Intent(PinActivity.this, SentReqActivity.class));
        isReset=true;
    }

    private void failedCreatePum(){
        tvPin1.setTextColor(getResources().getColor(R.color.redError));
        tvPin2.setTextColor(getResources().getColor(R.color.redError));
        tvPin3.setTextColor(getResources().getColor(R.color.redError));
        tvPin4.setTextColor(getResources().getColor(R.color.redError));
        tvPin5.setTextColor(getResources().getColor(R.color.redError));
        tvPin6.setTextColor(getResources().getColor(R.color.redError));
        isReset=true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (pin.length()!=0){
            outState.putString("PIN",pin);
        }
        super.onSaveInstanceState(outState);
    }
}
