package com.hudipo.pum_indomaret.features.requestpum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.pin.PinActivity;
import com.hudipo.pum_indomaret.model.RequestModel;
import com.hudipo.pum_indomaret.utils.RequestCode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReqValidationActivity extends AppCompatActivity {

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

    public static String EXTRA_DATA_REQUEST = "extra_data_request";
    private RequestModel requestModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_validation);
        ButterKnife.bind(this);

        getDataRequestModel();
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
                        Intent intent = new Intent(ReqValidationActivity.this, PinActivity.class);
                        intent.putExtra(PinActivity.KEY_REQUEST_DATA, requestModel);
                        intent.putExtra("requestCode", RequestCode.PIN_REQUESTPUM_CODE);

                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", (dialog, which) -> {
                    //Log.d("MainActivity", "Aborting mission...");
                })
                .show();
    }

    @OnClick(R.id.imgBack)
    void onBackClicked(){
        super.onBackPressed();
    }
}
