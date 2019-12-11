package com.hudipo.pum_indomaret.features.requestpum.activity;

import android.app.Activity;
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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.searchdocdetail.SearchDocDetailActivity;
import com.hudipo.pum_indomaret.model.RequestModel;
import com.hudipo.pum_indomaret.model.docdetail.DocDataItem;
import com.hudipo.pum_indomaret.repository.Repository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReqDocumentActivity extends AppCompatActivity {

    @BindView(R.id.spnDocType)
    Spinner spnDocType;
    @BindView(R.id.tvDocNum)
    TextView tvDocNum;
    @BindView(R.id.btnSearchDoc)
    ImageButton btnSearchDoc;

    public static final String KEY_DATA_REQUEST_EMPLOYEE = "KEY_DATA_REQUEST_EMPLOYEE";
    private static final int REQ_CODE_DOC_DETAIL = 100;

    private RequestModel requestModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_document);
        ButterKnife.bind(this);

        getDataIntent();
        initSpinner();
        checkSpinnerType();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_DOC_DETAIL) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    DocDataItem docDataItem = data.getParcelableExtra(SearchDocDetailActivity.EXTRA_SELECTED_DOC);
                    if (docDataItem != null) {
                        tvDocNum.setText(docDataItem.getDocNum());
                        requestModel.setDocNum(docDataItem.getDocNum());
                    }
                }
            }
        }
    }

    private void disableButtonSearch() {
        btnSearchDoc.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_red));
        btnSearchDoc.setEnabled(false);
    }

    private void enableButtonSearch() {
        btnSearchDoc.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_green));
        btnSearchDoc.setEnabled(true);
    }

    private void checkSpinnerType() {
        spnDocType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String docSelected = spnDocType.getSelectedItem().toString().trim();
                if (docSelected.equals("-") || docSelected.equals(getString(R.string.doc_type))) {
                    requestModel.setNameDocType("-");
                    requestModel.setDocNum("-");
                    disableButtonSearch();
                } else {
                    requestModel.setNameDocType(docSelected);
                    enableButtonSearch();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String docSelected = spnDocType.getSelectedItem().toString().trim();
                if (docSelected.equals("-") || docSelected.equals(getString(R.string.doc_type))) {
                    requestModel.setNameDocType("-");
                    disableButtonSearch();
                } else {
                    requestModel.setNameDocType(docSelected);
                    enableButtonSearch();
                }
            }
        });
    }

    private void initSpinner() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_layout, Repository.getDataDocumentType(this));
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spnDocType.setAdapter(dataAdapter);
    }

    private void getDataIntent() {
        if (getIntent() != null) {
            requestModel = getIntent().getParcelableExtra(KEY_DATA_REQUEST_EMPLOYEE);
        }
    }

    @OnClick(R.id.btnSearchDoc)
    void setBtnSearchDoc() {
        Intent intent = new Intent(this, SearchDocDetailActivity.class);
        intent.putExtra(SearchDocDetailActivity.KEY_DATA_DOC_TYPE, spnDocType.getSelectedItem().toString());
        startActivityForResult(intent, REQ_CODE_DOC_DETAIL);
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideRight(this); //fire the slide left animation
        finish();
    }


    @OnClick(R.id.btnBackDoc)
    void btnBackDoc() {
       onBackPressed();
    }

    @OnClick(R.id.btnNextDoc)
    void btnNextDoc() {
        String docNum = tvDocNum.getText().toString();
//        if (checkValid(docNum)){
        Intent intent = new Intent(this, ReqFundActivity.class);
        intent.putExtra(ReqFundActivity.EXTRA_DOCUMENT_DETAIL, requestModel);
        startActivity(intent);
        Animatoo.animateSlideLeft(this);
//        }
    }

    private boolean checkValid(String docNum) {
        if (docNum.isEmpty()) {
            tvDocNum.setError("Your document is empty");
            Toast.makeText(this, "Your document is empty", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            tvDocNum.setError(null);
        }
        return true;
    }
}
