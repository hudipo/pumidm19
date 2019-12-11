package com.hudipo.pum_indomaret.features.searchtrx;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.adapter.SearchTrxAdapter;
import com.hudipo.pum_indomaret.model.trxtype.TrxTypeResponse;
import com.hudipo.pum_indomaret.repository.Repository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchTrxActivity extends AppCompatActivity implements SearchTrxContract.SearchTrxView{

    @BindView(R.id.rvSearchTrx)
    RecyclerView rvSearchTrx;
    @BindView(R.id.pbSearchTrx)
    LottieAnimationView pbSearchTrx;
    @BindView(R.id.etSearchTransaction)
    EditText etSearchTransaction;

    private SearchTrxPresenter trxPresenter;
    private SearchTrxAdapter searchTrxAdapter;
    static public String EXTRA_SELECTED = "extra_selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trx);
        ButterKnife.bind(this);

        onAttachView();
    }

    @Override
    protected void onStop() {
        super.onStop();
        trxPresenter.clearComposite();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetachView();
    }

    @Override
    public void showProgress() {
        pbSearchTrx.setVisibility(View.VISIBLE);
        rvSearchTrx.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        pbSearchTrx.setVisibility(View.INVISIBLE);
        rvSearchTrx.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDataTrxType(TrxTypeResponse trxTypeResponse) {
        if (trxTypeResponse != null){
            setAdapter(trxTypeResponse);
        }
    }

    private void setAdapter(TrxTypeResponse trxTypeResponse) {
        searchTrxAdapter = new SearchTrxAdapter(trxItem -> {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_SELECTED, trxItem);
            setResult(Activity.RESULT_OK, intent);
            finish();
            Animatoo.animateSlideDown(this);
        });
        searchTrxAdapter.setTrxTypeResponse(trxTypeResponse);

        rvSearchTrx.setLayoutManager(new LinearLayoutManager(this));
        rvSearchTrx.setAdapter(searchTrxAdapter);

        searchTrx();
    }

    private void searchTrx() {
        etSearchTransaction.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchTrxAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchTrxAdapter.getFilter().filter(s.toString());
            }
        });
    }

    @Override
    public void setDataEmpty() {
        Toast.makeText(this, "DataApproval Kosong", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAttachView() {
        trxPresenter = new SearchTrxPresenter(new Repository());
        trxPresenter.onAttach(this);
        trxPresenter.loadDataTrxType();
    }

    @Override
    public void onDetachView() {
        trxPresenter.onDetach();
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
}
