package com.hudipo.pum_indomaret.features.searchtrx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.trxtype.TrxTypeResponse;

public class SearchTrxActivity extends AppCompatActivity implements SearchTrxContract.SearchTrxView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trx);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setDataTrxType(TrxTypeResponse trxTypeResponse) {

    }

    @Override
    public void setDataEmpty() {

    }

    @Override
    public void showFailed(String message) {

    }

    @Override
    public void onAttachView() {

    }

    @Override
    public void onDetachView() {

    }
}
