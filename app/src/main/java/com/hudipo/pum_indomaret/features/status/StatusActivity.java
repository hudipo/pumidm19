package com.hudipo.pum_indomaret.features.status;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.status.adapter.StatusAdapter;
import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.features.status.model.StatusInteractorImpl;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;
import com.hudipo.pum_indomaret.features.status.presenter.StatusPresenterImpl;
import com.hudipo.pum_indomaret.features.status.statusdetail.StatusDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatusActivity extends AppCompatActivity implements StatusContract.StatusView {

    @BindView(R.id.rvStatus)
    RecyclerView rvStatus;
    @BindView(R.id.swipeRefreshStatus)
    SwipeRefreshLayout swipeRefreshStatus;
    @BindView(R.id.etSearchByPumNumber)
    EditText etSearch;

    private StatusAdapter adapterStatus;
    private StatusContract.StatusPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        ButterKnife.bind(this);

        presenter = new StatusPresenterImpl(this, new StatusInteractorImpl(this));

        initSwipeRefreshStatus();
        initRecyclerView();
        startListener();
    }

    private void startListener() {

    }

    private void initRecyclerView() {
        rvStatus.setHasFixedSize(true);
        rvStatus.setLayoutManager(new LinearLayoutManager(this));
        adapterStatus = new StatusAdapter(currentStatus -> {
            Intent intent = new Intent(StatusActivity.this, StatusDetailActivity.class);
            intent.putExtra("CURRENT_STATUS", currentStatus);
            startActivity(intent);
        });
        rvStatus.setAdapter(adapterStatus);
        presenter.getStatusList();
    }


    @Override
    public void setStatusList(List<StatusResponse.StatusModel> statusList) {
        adapterStatus.setStatusModelList(statusList);
    }

    @Override
    public void showLoading() {
        swipeRefreshStatus.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshStatus.setRefreshing(false);
    }

    @Override
    public void toast(String stringMessage) {
        Toast.makeText(this, stringMessage, Toast.LENGTH_SHORT).show();
    }

    private void initSwipeRefreshStatus() {
        swipeRefreshStatus.setOnRefreshListener(() -> presenter.getStatusList()
        );
    }


    @OnClick(R.id.btnBack)
    void btnBack(){
        onBackPressed();
    }
}
