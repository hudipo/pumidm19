package com.hudipo.pum_indomaret.features.requestpum.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;
import com.hudipo.pum_indomaret.features.requestpum.model.RequestIntractorImpl;
import com.hudipo.pum_indomaret.features.requestpum.presenter.DocumentPresenterImpl;
import com.hudipo.pum_indomaret.model.RequestModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DocumentReqActivity extends AppCompatActivity implements RequestContract.DocumentView {

    public static final int DOC_DETAIL_REQ_CODE = 0;

    @BindView(R.id.spnDocType)
    Spinner spnDocType;
    @BindView(R.id.tvDocNum)
    TextView tvDocNum;

    private RequestModel requestModel;

    private RequestContract.DocumentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_document);
        ButterKnife.bind(this);
        if (getIntent()!=null){
            requestModel = (RequestModel) getIntent().getSerializableExtra("CURRENT_REQUEST");
        }

        initView();
    }

    private void initView() {
        presenter = new DocumentPresenterImpl(this, new RequestIntractorImpl());
        presenter.getDocumentType();
    }

    @OnClick(R.id.imgSearcDoc)
    void getDocDetail(){
        startActivityForResult(new Intent(DocumentReqActivity.this,SearchDocumentReqActivity.class),DOC_DETAIL_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==DOC_DETAIL_REQ_CODE){
            if (resultCode==RESULT_OK){
                tvDocNum.setText(data.getStringExtra("result"));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @OnClick(R.id.imgBack)
    void backPressed(){
        onBackPressed();
    }

    @Override
    public void setDocumentType(ArrayList<String> documentType) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item_layout, documentType);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spnDocType.setAdapter(dataAdapter);
    }
}
