package com.hudipo.pum_indomaret.features.status;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.features.status.adapter.StatusAdapter;
import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.features.status.model.StatusDetailResponse;
import com.hudipo.pum_indomaret.features.status.model.StatusInteractorImpl;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;
import com.hudipo.pum_indomaret.features.status.presenter.StatusPresenterImpl;
import com.hudipo.pum_indomaret.features.status.statusdetail.StatusDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatusActivity extends AppCompatActivity implements StatusContract.StatusView {

    public static final int STATUS_REQUEST_CODE = 0;
    public static final int STATUS_FILTER_REQUEST_CODE = 1;
    public static final String STATUS_DETAIL = "detail";

    @BindView(R.id.rvStatus)
    RecyclerView rvStatus;
    @BindView(R.id.swipeRefreshStatus)
    SwipeRefreshLayout swipeRefreshStatus;
    @BindView(R.id.etSearchByPumNumber)
    EditText etSearch;

    @OnClick(R.id.btnFilterStatus)
    void goToStatusFilterAct(){
        Intent intent = new Intent(StatusActivity.this, StatusFilterActivity.class);
        startActivityForResult(intent,STATUS_FILTER_REQUEST_CODE);
        Animatoo.animateSlideUp(this);
    }

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
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapterStatus.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == STATUS_FILTER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;

                String startDate = data.getStringExtra("startDate");
                String untilDate = data.getStringExtra("untilDate");
                String status = data.getStringExtra("status");
                presenter.getFilteredStatusList(startDate,untilDate,status);
            }
        }
    }

    private void initRecyclerView() {
        rvStatus.setHasFixedSize(true);
        rvStatus.setLayoutManager(new LinearLayoutManager(this));
        adapterStatus = new StatusAdapter(currentStatus -> presenter.getDetailPum(currentStatus.getPUM_TRX_ID()));
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
    public void goToDetailActivity(StatusDetailResponse.StatusDetailModel statusDetailModel) {
        Intent intent = new Intent(StatusActivity.this, StatusDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(STATUS_DETAIL, statusDetailModel);
        intent.putExtras(bundle);
        startActivity(intent);
        Animatoo.animateSlideLeft(this);
    }

    @Override
    public void toast(String stringMessage) {
        new AlertDialog.Builder(this,R.style.CustomDialogTheme)
                .setTitle("Attention!")
                .setMessage(stringMessage)
                .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    private void initSwipeRefreshStatus() {
        swipeRefreshStatus.setOnRefreshListener(() -> presenter.onRefresh()
        );
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        startActivity(new Intent(this, HomeActivity.class));
        Animatoo.animateSlideRight(this);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
        Animatoo.animateSlideRight(this);
        finish();
    }
}
