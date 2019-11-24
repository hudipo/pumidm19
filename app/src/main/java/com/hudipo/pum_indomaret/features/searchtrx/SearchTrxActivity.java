package com.hudipo.pum_indomaret.features.searchtrx;

import android.content.Intent;
import android.os.Bundle;

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

    private SearchTrxPresenter trxPresenter;
    static public int RESULT_CODE_SELECTED = 101;
    static public String EXTRA_SELECTED = "extra_selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trx);
        ButterKnife.bind(this);

        onAttachView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDetachView();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

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
            setResult(RESULT_CODE_SELECTED, intent);
            finish();
        });
        searchTrxAdapter.setTrxTypeResponse(trxTypeResponse);

        rvSearchTrx.setLayoutManager(new LinearLayoutManager(this));
        rvSearchTrx.setAdapter(searchTrxAdapter);
    }

    @Override
    public void setDataEmpty() {

    }

    @Override
    public void showFailed(String message) {

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
