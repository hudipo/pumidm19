package com.hudipo.pum_indomaret.features.requestpum.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;
import com.hudipo.pum_indomaret.features.requestpum.model.RequestInteractorImpl;
import com.hudipo.pum_indomaret.features.requestpum.presenter.FundPresenterImpl;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FundReqActivity extends AppCompatActivity implements RequestContract.FundView {

    @BindView(R.id.spnFundTrxType)
    Spinner spnTrxType;
    @BindView(R.id.edtFundDesc)
    EditText edtFundDesc;
    @BindView(R.id.edtFundAmount)
    EditText edtFundAmount;

    private FundPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_fund);
        ButterKnife.bind(this);
        presenter = new FundPresenterImpl(this,new RequestInteractorImpl());
        presenter.getTransactionTypeList();
    }

    @OnClick(R.id.btnNextFund)
    void onClick(){
        startActivity(new Intent(FundReqActivity.this, ValidationReqActivity.class));
    }

    @Override
    public void setTransactionType(ArrayList<String> transactionTypeList) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, transactionTypeList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spnTrxType.setAdapter(dataAdapter);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}