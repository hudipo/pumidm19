package com.hudipo.pum_indomaret.features.searchtrxtyperesponse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.adapter.SearchTrxResponseAdapter;
import com.hudipo.pum_indomaret.model.response.TrxTypeItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchTrxTypeResponseActivity extends AppCompatActivity {

    @BindView(R.id.etSearchTransaction)
    EditText etSearchTransaction;
    @BindView(R.id.rvSearchTrx)
    RecyclerView rvSearchTrx;

    public static final String EXTRA_TRX_TYPE = "extra_trx_type";
    public static final String EXTRA_SELECTED_TRX_TYPE = "extra_selected_trx_type";
    private SearchTrxResponseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trx_type_response);
        ButterKnife.bind(this);

        getDataTrx();
        searchTrx();
    }

    private void searchTrx() {
        etSearchTransaction.addTextChangedListener(new TextWatcher() {
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

    private void getDataTrx() {
        if (getIntent() != null){
            ArrayList<TrxTypeItem> trxTypeItems = getIntent().getParcelableArrayListExtra(EXTRA_TRX_TYPE);
            if (trxTypeItems != null && trxTypeItems.size() > 0){
                setAdapterTrx(trxTypeItems);
            }
        }
    }

    private void setAdapterTrx(ArrayList<TrxTypeItem> trxTypeItems) {
        adapter = new SearchTrxResponseAdapter(trxItem -> {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_SELECTED_TRX_TYPE, trxItem);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        adapter.setTrxTypeItems(trxTypeItems);

        rvSearchTrx.setLayoutManager(new LinearLayoutManager(this));
        rvSearchTrx.setAdapter(adapter);
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        finish();
    }
}
