package com.hudipo.pum_indomaret.features.requestpum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;
import com.hudipo.pum_indomaret.features.requestpum.model.RequestInteractionImpl;
import com.hudipo.pum_indomaret.features.requestpum.presenter.DocumentPresenterImpl;
import com.hudipo.pum_indomaret.model.RequestModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DocumentReqActivity extends AppCompatActivity implements RequestContract.DocumentView {

    public static final int DOC_DETAIL_REQ_CODE = 0;
    public static final String KEY_DATA_REQUEST_EMPLOYEE = "KEY_DATA_REQUEST_EMPLOYEE";

    @BindView(R.id.spnDocType)
    Spinner spnDocType;
    @BindView(R.id.tvDocNum)
    TextView tvDocNum;
    @BindView(R.id.imgSearcDoc)
    ImageButton imgSearchDoc;

    private RequestModel requestModel;

    private RequestContract.DocumentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_document);
        ButterKnife.bind(this);

        if (getIntent()!=null){
            requestModel = (RequestModel) getIntent().getSerializableExtra(KEY_DATA_REQUEST_EMPLOYEE);
        }

        initView();
    }

    private void initView() {
        presenter = new DocumentPresenterImpl(this, new RequestInteractionImpl(this));
        presenter.getDocumentType();

        spnDocType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnDocType.getSelectedItem().toString().equals("-")){
                    imgSearchDoc.setBackground(ContextCompat.getDrawable(DocumentReqActivity.this, R.drawable.bg_rounded_red));
                    imgSearchDoc.setEnabled(false);
                }else {
                    imgSearchDoc.setBackground(ContextCompat.getDrawable(DocumentReqActivity.this, R.drawable.bg_rounded_green));
                    imgSearchDoc.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                imgSearchDoc.setBackground(ContextCompat.getDrawable(DocumentReqActivity.this, R.drawable.bg_rounded_red));
                imgSearchDoc.setEnabled(false);
            }
        });

    }

    @OnClick(R.id.imgSearcDoc)
    void getDocDetail(){
        Intent intent = new Intent(DocumentReqActivity.this,SearchDocumentReqActivity.class);
        intent.putExtra(SearchDocumentReqActivity.KEY_DATA_DOC_TYPE, spnDocType.getSelectedItem().toString());
        startActivityForResult(intent,DOC_DETAIL_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DOC_DETAIL_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                String result = data.getStringExtra("result");
                tvDocNum.setText(result);
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

    @OnClick(R.id.btnNextDoc)
    void onClick(){
        goToFundReqActivity();
    }

    private void goToFundReqActivity() {
        Intent intent = new Intent(DocumentReqActivity.this,FundReqActivity.class);
        requestModel.setStringDocNumber(tvDocNum.getText().toString());
        requestModel.setStringDocType(spnDocType.getSelectedItem().toString());
        intent.putExtra(FundReqActivity.KEY_DATA_REQUEST_DOCUMENT, requestModel);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }
}
