package com.hudipo.pum_indomaret.features.requestpum.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.requestpum.adapter.SearchDocumentAdapter;
import com.hudipo.pum_indomaret.features.requestpum.contract.RequestContract;
import com.hudipo.pum_indomaret.features.requestpum.model.RequestInteractionImpl;
import com.hudipo.pum_indomaret.features.requestpum.presenter.SearchDocumentPresenterImpl;
import com.hudipo.pum_indomaret.model.docdetail.DocDetailResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchDocumentReqActivity extends AppCompatActivity implements RequestContract.SearchDocumentView {

    @BindView(R.id.rcvSearchDoc)
    RecyclerView rcvSearchDoc;
    @BindView(R.id.searchBox)
    EditText searchBox;

    public static final String KEY_DATA_DOC_TYPE = "KEY_DATA_DOC_TYPE";

    RequestContract.SearchDocumentPresenter presenter;
    private SearchDocumentAdapter.ItemClickListener itemClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_search_doc);
        ButterKnife.bind(this);
        presenter = new SearchDocumentPresenterImpl(this,new RequestInteractionImpl(this));

        getDataIntent();
    }

    private void getDataIntent() {
        if (getIntent() != null){
            String docType = getIntent().getStringExtra(KEY_DATA_DOC_TYPE);
            presenter.getDocumentList(docType);
        }
    }

    @Override
    public void setDocumentList(DocDetailResponse docDetailResponse) {
        rcvSearchDoc.setHasFixedSize(true);
        rcvSearchDoc.setLayoutManager(new LinearLayoutManager(this));
        rcvSearchDoc.setAdapter(new SearchDocumentAdapter(docDetailResponse, dataItem -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", dataItem.getDocNum());
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }));
    }

    @OnClick(R.id.imgBack)
    void backPressed(){
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
