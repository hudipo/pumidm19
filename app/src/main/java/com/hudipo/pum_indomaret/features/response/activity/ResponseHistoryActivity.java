package com.hudipo.pum_indomaret.features.response.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.adapter.ResponseHistoryAdapter;
import com.hudipo.pum_indomaret.model.historyresponse.HistoryResponseItem;
import com.hudipo.pum_indomaret.model.historyresponse.HistoryResponse;
import com.hudipo.pum_indomaret.model.response.DataResponseItem;
import com.hudipo.pum_indomaret.repository.RepositoryResponse;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;

public class ResponseHistoryActivity extends AppCompatActivity {

    @BindView(R.id.rvResponseHistory)
    RecyclerView rvResponseHistory;
    @BindView(R.id.tvIsEmpty)
    TextView tvIsEmpty;

    private CompositeDisposable composite = new CompositeDisposable();
    static public String EXTRA_DATA_RESPONSE = "extra_data_response";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_history);
        ButterKnife.bind(this);

        getDataIntent();
    }

    private void getDataIntent() {
        if (getIntent() != null){
            DataResponseItem dataResponseItem = getIntent().getParcelableExtra(EXTRA_DATA_RESPONSE);
            if (dataResponseItem != null){
                loadDataHistory(dataResponseItem.getPUMTRXID());
            }
        }
    }

    private void loadDataHistory(int pumTrxId) {
        RepositoryResponse repositoryResponse = new RepositoryResponse();
        repositoryResponse.getDataHistory(composite, pumTrxId, new RepositoryResponse.RepositoryResponseCallback.LoadDataHistory() {
            @Override
            public void onDataLoad(HistoryResponse historyResponse) {
                if (!historyResponse.isError()){
                    if (historyResponse.getMessage().equals("Data Empty")){
                        showDataEmpty();
                    }else {
                        if (historyResponse.getData().size() > 0 && historyResponse.getData() != null){
                            showData();
                            setAdapterResponseHistory(historyResponse.getData());
                        }else {
                            showDataEmpty();
                        }
                    }
                }
            }

            @Override
            public void onDataError(String message) {

            }
        });
    }

    private void showData() {
        rvResponseHistory.setVisibility(View.VISIBLE);
        tvIsEmpty.setVisibility(View.GONE);
    }

    private void showDataEmpty() {
        rvResponseHistory.setVisibility(View.INVISIBLE);
        tvIsEmpty.setVisibility(View.VISIBLE);
    }

    private void setAdapterResponseHistory(List<HistoryResponseItem> data) {
        ResponseHistoryAdapter responseHistoryAdapter = new ResponseHistoryAdapter();
        responseHistoryAdapter.setItems(data);

        rvResponseHistory.setLayoutManager(new LinearLayoutManager(this));
        rvResponseHistory.setAdapter(responseHistoryAdapter);
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        finish();
    }
}
