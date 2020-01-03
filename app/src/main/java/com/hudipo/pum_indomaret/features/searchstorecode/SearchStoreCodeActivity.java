package com.hudipo.pum_indomaret.features.searchstorecode;

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
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.adapter.SearchStoreCodeAdapter;
import com.hudipo.pum_indomaret.model.storecode.StoreCodeItem;
import com.hudipo.pum_indomaret.repository.RepositoryResponse;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchStoreCodeActivity extends AppCompatActivity implements SearchStoreCodeContract.SearchStoreCodeView {

    @BindView(R.id.etSearchStoreCode)
    EditText etSearchStoreCode;
    @BindView(R.id.rvSearchStoreCode)
    RecyclerView rvSearchStoreCode;
    @BindView(R.id.pbSearchTrx)
    LottieAnimationView pbSearchTrx;

    private SearchStoreCodePresenter presenter;
    private SearchStoreCodeAdapter adapter;
    static public String EXTRA_SELECTED = "extra_selected_store_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_store_code);
        ButterKnife.bind(this);

        onAttachView();
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

    @Override
    public void showProgress() {
        pbSearchTrx.setVisibility(View.VISIBLE);
        rvSearchStoreCode.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        pbSearchTrx.setVisibility(View.GONE);
        rvSearchStoreCode.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDataStoreCode(List<StoreCodeItem> storeCodeItems) {
        if (storeCodeItems != null && storeCodeItems.size() > 0){
            setAdapterStoreCode(storeCodeItems);
        }
    }

    private void setAdapterStoreCode(List<StoreCodeItem> storeCodeItems) {
        adapter = new SearchStoreCodeAdapter(storeCodeItem -> {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_SELECTED, storeCodeItem);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        adapter.setStoreCodeItems(storeCodeItems);

        rvSearchStoreCode.setLayoutManager(new LinearLayoutManager(this));
        rvSearchStoreCode.setHasFixedSize(true);
        rvSearchStoreCode.setAdapter(adapter);

        searchDataStoreCode();
    }

    private void searchDataStoreCode() {
        etSearchStoreCode.addTextChangedListener(new TextWatcher() {
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
    public void setDataEmpty() {
        Toast.makeText(this, "Data is empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailed(String message) {
        Toast.makeText(this, "error : "+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttachView() {
        RepositoryResponse repositoryResponse = new RepositoryResponse();
        HawkStorage hawkStorage = new HawkStorage(this);
        presenter = new SearchStoreCodePresenter(repositoryResponse, hawkStorage);
        presenter.onAttach(this);
        presenter.loadDataStoreCode();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        finish();
    }
}
