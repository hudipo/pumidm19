package com.hudipo.pum_indomaret.features.status;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.features.status.adapter.StatusAdapter;
import com.hudipo.pum_indomaret.features.status.statusdetail.StatusDetailActivity;
import com.hudipo.pum_indomaret.utils.StartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatusActivity extends AppCompatActivity {

    @BindView(R.id.rvStatus)
    RecyclerView rvStatus;
    @BindView(R.id.swipeRefreshStatus)
    SwipeRefreshLayout swipeRefreshStatus;

    StatusAdapter adapterStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        ButterKnife.bind(this);

        showLoading();
        initSwipeRefreshStatus();
        setAdapterStatus();
    }

    private void showLoading() {
        swipeRefreshStatus.post(() -> swipeRefreshStatus.setRefreshing(true));
        new Handler().postDelayed(() ->
                swipeRefreshStatus.post(() -> swipeRefreshStatus.setRefreshing(false))
        , 2000);
    }

    private void initSwipeRefreshStatus() {
        swipeRefreshStatus.setOnRefreshListener(() -> swipeRefreshStatus.setRefreshing(false));
    }

    private void setAdapterStatus() {
        adapterStatus = new StatusAdapter(view -> {
            goToStatusDetail();
        });
        adapterStatus.notifyDataSetChanged();

        rvStatus.setLayoutManager(new LinearLayoutManager(this));
        rvStatus.setAdapter(adapterStatus);
    }

    private void goToStatusDetail() {
        startActivity(new Intent(this, StatusDetailActivity.class));
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        StartActivity.goTo(this, HomeActivity.class);
        finishAffinity();
    }
}
