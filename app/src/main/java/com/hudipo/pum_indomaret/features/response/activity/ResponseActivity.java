package com.hudipo.pum_indomaret.features.response.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.adapter.ResponseAdapter;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.features.response.contract.ResponseContract;
import com.hudipo.pum_indomaret.features.response.presenter.ResponsePresenter;
import com.hudipo.pum_indomaret.model.response.DataResponse;
import com.hudipo.pum_indomaret.model.response.DataResponseItem;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResponseActivity extends AppCompatActivity implements ResponseContract.ResponseView {

    @BindView(R.id.rvResponse)
    RecyclerView rvResponse;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.swipeRefreshResponse)
    SwipeRefreshLayout swipeRefreshResponse;
    @BindView(R.id.tvIsEmpty)
    TextView tvIsEmpty;

    private ResponsePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        ButterKnife.bind(this);

        onAttachView();
        initSwipeRefresh();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        onBackPressed();
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

    private void initSwipeRefresh() {
        swipeRefreshResponse.setOnRefreshListener(() -> {
            swipeRefreshResponse.setRefreshing(false);
            presenter.loadDataResponse();
        });
    }



    private void setAdapter(List<DataResponseItem> data) {
        ResponseAdapter responseAdapter = new ResponseAdapter(this);
        responseAdapter.setListResponse(data);

        rvResponse.setLayoutManager(new LinearLayoutManager(this));
        rvResponse.setAdapter(responseAdapter);
    }

    @Override
    public void showProgress() {
        tvIsEmpty.setVisibility(View.GONE);
        rvResponse.setVisibility(View.GONE);
        swipeRefreshResponse.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        tvIsEmpty.setVisibility(View.GONE);
        rvResponse.setVisibility(View.VISIBLE);
        swipeRefreshResponse.setRefreshing(false);
    }

    @Override
    public void showDataEmpty() {
        tvIsEmpty.setVisibility(View.VISIBLE);
        rvResponse.setVisibility(View.GONE);
        swipeRefreshResponse.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "error : " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDataResponse(DataResponse getDataResponse) {
        if (getDataResponse.getData() != null){
            setAdapter(getDataResponse.getData());
        }
    }

    @Override
    public void onAttachView() {
        HawkStorage hawkStorage = new HawkStorage(this);
        presenter = new ResponsePresenter();
        presenter.setHawkStorage(hawkStorage);
        presenter.onAttach(this);
        presenter.loadDataResponse();
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }


}
