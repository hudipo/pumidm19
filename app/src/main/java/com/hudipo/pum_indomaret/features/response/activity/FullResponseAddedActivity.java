package com.hudipo.pum_indomaret.features.response.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hudipo.pum_indomaret.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FullResponseAddedActivity extends AppCompatActivity {

    public static final String EXTRA_TRX_NUMBER = "extra_trx_number";
    public static final String EXTRA_PUM_TRX_ID= "extra_pum_trx_id";
    private String trxNumber;
    private int pumTrxId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_response_added);
        ButterKnife.bind(this);

        getDataIntent();
    }

    private void getDataIntent() {
        if (getIntent() != null){
            trxNumber = getIntent().getStringExtra(EXTRA_TRX_NUMBER);
            pumTrxId = getIntent().getIntExtra(EXTRA_PUM_TRX_ID, 0);
        }
    }

    @OnClick(R.id.btnViewResponseCart)
    void btnViewResponseCart(){
        Intent intent = new Intent(this, ResponseCartFullActivity.class);
        intent.putExtra(ResponseCartFullActivity.EXTRA_TRX_NUMBER, trxNumber);
        intent.putExtra(ResponseCartFullActivity.EXTRA_PUM_TRX_ID, pumTrxId);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnAddAnotherResponse)
    void btnAddAnotherResponse(){
        finish();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
