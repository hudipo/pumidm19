package com.hudipo.pum_indomaret.features.response.subfeatures.responsehistory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsehistory.adapter.ResponseHistoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResponseHistoryActivity extends AppCompatActivity {

    @BindView(R.id.rvResponseHistory)
    RecyclerView rvResponseHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_history);
        ButterKnife.bind(this);

        setAdapterResponseHistory();
    }

    private void setAdapterResponseHistory() {
        ResponseHistoryAdapter responseHistoryAdapter = new ResponseHistoryAdapter();
        responseHistoryAdapter.notifyDataSetChanged();

        rvResponseHistory.setLayoutManager(new LinearLayoutManager(this));
        rvResponseHistory.setAdapter(responseHistoryAdapter);
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        finish();
    }
}
