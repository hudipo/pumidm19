package com.hudipo.pum_indomaret.features.requestpum.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.pin.PinActivity;
import com.hudipo.pum_indomaret.features.requestpum.contract.ReqValidationContract;
import com.hudipo.pum_indomaret.features.requestpum.presenter.ReqValidationPresenter;
import com.hudipo.pum_indomaret.model.RequestModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReqValidationActivity extends AppCompatActivity implements ReqValidationContract.ReqValidationView {

    @BindView(R.id.tvEmpDept)
    TextView tvEmpDept;
    @BindView(R.id.tvUseDate)
    TextView tvUseDate;
    @BindView(R.id.tvRespDate)
    TextView tvRespDate;
    @BindView(R.id.tvDocType)
    TextView tvDocType;
    @BindView(R.id.tvDocNum)
    TextView tvDocNum;
    @BindView(R.id.tvTrxType)
    TextView tvTrxType;
    @BindView(R.id.tvFile)
    TextView tvFile;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvDesc)
    TextView tvDescription;
    @BindView(R.id.pbReqValidation)
    ProgressBar pbReqValidation;

    public static String EXTRA_DATA_REQUEST = "extra_data_request";
    private final int REQUEST_CODE_PIN = 100;
    private RequestModel requestModel;
    private ReqValidationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_validation);
        ButterKnife.bind(this);

        getDataRequestModel();
        onAttachView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.clearComposite();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PIN){
            if (resultCode == Activity.RESULT_OK){
                if (data != null) {
                    String pin = data.getStringExtra(PinActivity.EXTRA_PIN);
                    requestModel.setPin(pin);
                    presenter.createPumToServer(requestModel);
                }
            }
        }
    }

    private void getDataRequestModel() {
        if (getIntent() != null){
            requestModel = getIntent().getParcelableExtra(EXTRA_DATA_REQUEST);
            if (requestModel != null){
                initView(requestModel);
            }
        }
    }

    private void initView(RequestModel requestModel) {
        tvEmpDept.setText(requestModel.getNameEmpDept());

        tvUseDate.setText(requestModel.getUseDate());

        tvRespDate.setText(requestModel.getRespDate());

        tvDocType.setText(requestModel.getNameDocType());

        tvDocNum.setText(requestModel.getDocNum());

        tvTrxType.setText(requestModel.getNameTrxType());

        tvAmount.setText(String.valueOf(requestModel.getAmount()));

        tvFile.setText(requestModel.getNameFile());

        tvDescription.setText(requestModel.getDescription());
    }

    @OnClick(R.id.btnSubmitReq)
    void onClick(){
        new AlertDialog.Builder(this,R.style.CustomDialogTheme)
                .setTitle("Submit")
                .setMessage("Are you sure want to make this request?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    if (requestModel != null){
                        Intent intent = new Intent(this, PinActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_PIN);
                    }
                })
                .setNegativeButton("No", (dialog, which) -> {
                    Log.d("MainActivity", "Aborting mission...");
                })
                .show();
    }

    @OnClick(R.id.imgBack)
    void onBackClicked(){
        super.onBackPressed();
    }

    @Override
    public void showProgress() {
        pbReqValidation.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbReqValidation.setVisibility(View.INVISIBLE);
    }

    @Override
    public void failedCreatePum(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successCreatePum() {
        Intent intent = new Intent(this, SentReqActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void onAttachView() {
        presenter = new ReqValidationPresenter(this);
        presenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }
}
