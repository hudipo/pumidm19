package com.hudipo.pum_indomaret.features.searchtrx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    ProgressBar pbSearchTrx;

    private SearchTrxPresenter trxPresenter;
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
        SearchTrxAdapter searchTrxAdapter = new SearchTrxAdapter(trxItem -> {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_SELECTED, trxItem);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        searchTrxAdapter.setTrxTypeResponse(trxTypeResponse);

        rvSearchTrx.setLayoutManager(new LinearLayoutManager(this));
        rvSearchTrx.setAdapter(searchTrxAdapter);
    }

    @Override
    public void setDataEmpty() {
        Toast.makeText(this, "Data Kosong", Toast.LENGTH_LONG).show();
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

    @OnClick(R.id.btnBack)
    void btnBack(){
        finish();
    }
}
