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
import com.hudipo.pum_indomaret.features.requestpum.model.DocumentDetailRequestModel;
import com.hudipo.pum_indomaret.features.requestpum.model.RequestInteractorImpl;
import com.hudipo.pum_indomaret.features.requestpum.presenter.SearchDocumentPresenterImpl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchDocumentReqActivity extends AppCompatActivity implements RequestContract.SearchDocumentView {

    @BindView(R.id.rcvSearchDoc)
    RecyclerView rcvSearchDoc;
    @BindView(R.id.searchBox)
    EditText searchBox;

    RequestContract.SearchDocumentPresenter presenter;
    private SearchDocumentAdapter.ItemClickListener itemClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_search_doc);
        ButterKnife.bind(this);
        presenter = new SearchDocumentPresenterImpl(this,new RequestInteractorImpl(this));
        initView();
    }

    private void initView() {
        presenter.getDocumentList();
    }

    @Override
    public void setDocumentList(ArrayList<DocumentDetailRequestModel> documentDetailRequestModels) {
        rcvSearchDoc.setHasFixedSize(true);
        rcvSearchDoc.setLayoutManager(new LinearLayoutManager(this));
        rcvSearchDoc.setAdapter(new SearchDocumentAdapter(documentDetailRequestModels, docNum -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",docNum);
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
