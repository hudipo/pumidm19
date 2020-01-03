package com.hudipo.pum_indomaret.features.response.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.response.DataResponseItem;
import com.hudipo.pum_indomaret.model.submitresponse.SubmitResponseModel;
import com.hudipo.pum_indomaret.utils.Global;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateResponseActivity extends AppCompatActivity {

    static public String EXTRA_DATA_RESPONSE = "extra_data_response";

    @BindView(R.id.tvPumNumber)
    TextView tvPumNumber;
    @BindView(R.id.tvTransactionType)
    TextView tvTransactionType;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvAmountRemaining)
    TextView tvAmountRemaining;

    private DataResponseItem dataResponseItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_response);
        ButterKnife.bind(this);

        getDataResponse();
    }

    private void getDataResponse() {
        if (getIntent() != null){
            dataResponseItem = getIntent().getParcelableExtra(EXTRA_DATA_RESPONSE);
            if (dataResponseItem != null){
                setView(dataResponseItem);
            }
        }
    }

    private void setView(DataResponseItem dataResponseItem) {
        tvPumNumber.setText(String.valueOf(dataResponseItem.getPUMTRXID()));
        tvTransactionType.setText(String.valueOf(dataResponseItem.getTRXTYPEDESCRIPTION()));
        tvDescription.setText(dataResponseItem.getDESCRIPTION());
        tvAmount.setText(String.format("Rp. %s", Global.priceFormatter(String.valueOf(dataResponseItem.getAMOUNT()))));
        tvAmountRemaining.setText(String.format("Rp. %s", Global.priceFormatter(String.valueOf(dataResponseItem.getAMOUNTREMAINING()))));
    }

    @OnClick(R.id.btnBack)
    void back(){
        finish();
    }

    @OnClick(R.id.btnFull)
    void btnFull(){
        Intent intent = new Intent(this, ResponseFullActivity.class);
        intent.putParcelableArrayListExtra(ResponseFullActivity.EXTRA_TRX_TYPE, (ArrayList<? extends Parcelable>) dataResponseItem.getTRXTYPE());
        intent.putExtra(ResponseFullActivity.EXTRA_TRX_NUMBER, dataResponseItem.getTRXNUM());
        intent.putExtra(ResponseFullActivity.EXTRA_PUM_TRX_ID, dataResponseItem.getPUMTRXID());
        startActivity(intent);
    }

    @OnClick(R.id.btnPartial)
    void btnPartial(){
        Intent intent = new Intent(this, ResponsePartialActivity.class);
        intent.putExtra(ResponsePartialActivity.EXTRA_TRX_NUMBER, dataResponseItem.getTRXNUM());
        intent.putExtra(ResponsePartialActivity.EXTRA_PUM_TRX_ID, dataResponseItem.getPUMTRXID());
        intent.putParcelableArrayListExtra(ResponsePartialActivity.EXTRA_TRX_TYPE, (ArrayList<? extends Parcelable>) dataResponseItem.getTRXTYPE());
        startActivity(intent);
    }
}
