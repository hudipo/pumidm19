package com.hudipo.pum_indomaret.features.searchdocdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.snackbar.Snackbar;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.adapter.SearchDocumentAdapter;
import com.hudipo.pum_indomaret.model.docdetail.DocDetailResponse;
import com.hudipo.pum_indomaret.repository.Repository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchDocDetailActivity extends AppCompatActivity implements SearchDocDetailContract.SearchDocDetailView{

    @BindView(R.id.rvSearchDoc)
    RecyclerView rvSearchDoc;
    @BindView(R.id.etSearchDoc)
    EditText etSearchDoc;
    @BindView(R.id.pbSearchDocDetail)
    LottieAnimationView pbSearchDocDetail;
    @BindView(R.id.constraintSearchDoc)
    ConstraintLayout constraintSearchDoc;

    private SearchDocDetailPresenter presenter;
    private SearchDocumentAdapter adapter;

    public static final String KEY_DATA_DOC_TYPE = "key_data_doc_type";
    public static final String EXTRA_SELECTED_DOC = "extra_selected_doc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doc_detail);
        ButterKnife.bind(this);

        onAttachView();
        getDataIntentAndLoadDataDocDetail();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.clearComposite();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetachView();
    }

    private void getDataIntentAndLoadDataDocDetail() {
        if (getIntent() != null){
            String docType = getIntent().getStringExtra(KEY_DATA_DOC_TYPE);
            if (docType != null){
                presenter.loadDataDocDetail(docType);
            }
        }
    }

    private void showSnackBar(String message){
        Snackbar.make(constraintSearchDoc, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideDown(this); //fire the slide left animation
        finish();
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        onBackPressed();
    }



    @Override
    public void showProgress() {
        pbSearchDocDetail.setVisibility(View.VISIBLE);
        rvSearchDoc.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        pbSearchDocDetail.setVisibility(View.INVISIBLE);
        rvSearchDoc.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDataDocDetail(DocDetailResponse docDetailResponse) {
        adapter = new SearchDocumentAdapter(docDetailResponse, dataItem -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra(EXTRA_SELECTED_DOC, dataItem);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
            Animatoo.animateSlideDown(this);
        });
        rvSearchDoc.setLayoutManager(new LinearLayoutManager(this));
        rvSearchDoc.setAdapter(adapter);

        searchDocDetail();
    }

    private void searchDocDetail() {
        etSearchDoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });
    }

    @Override
    public void showDataEmpty() {
        showSnackBar("DocData Empty");
    }

    @Override
    public void showFailed(String message) {
        showSnackBar(message);
    }

    @Override
    public void onAttachView() {
        Repository repository = new Repository();
        presenter = new SearchDocDetailPresenter(repository);
        presenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }
}
