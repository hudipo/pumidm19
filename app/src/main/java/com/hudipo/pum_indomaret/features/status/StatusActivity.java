package com.hudipo.pum_indomaret.features.status;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.home.HomeActivity;
import com.hudipo.pum_indomaret.features.pin.PinActivity;
import com.hudipo.pum_indomaret.features.requestpum.activity.DocumentReqActivity;
import com.hudipo.pum_indomaret.features.requestpum.activity.SearchDocumentReqActivity;
import com.hudipo.pum_indomaret.features.requestpum.activity.ValidationReqActivity;
import com.hudipo.pum_indomaret.features.status.adapter.StatusAdapter;
import com.hudipo.pum_indomaret.features.status.contract.StatusContract;
import com.hudipo.pum_indomaret.features.status.model.StatusFilterRequestBody;
import com.hudipo.pum_indomaret.features.status.model.StatusInteractorImpl;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;
import com.hudipo.pum_indomaret.features.status.presenter.StatusPresenterImpl;
import com.hudipo.pum_indomaret.features.status.statusdetail.StatusDetailActivity;
import com.hudipo.pum_indomaret.utils.RequestCode;
import com.hudipo.pum_indomaret.utils.StartActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatusActivity extends AppCompatActivity implements StatusContract.StatusView {

    public static final int STATUS_REQUEST_CODE = 0;
    public static final int STATUS_FILTER_REQUEST_CODE = 1;
    private StatusFilterRequestBody statusFilterRequestBody;

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
//                Toast.makeText(this,data.getStringExtra("startDate"),Toast.LENGTH_SHORT).show();
//                Toast.makeText(this,data.getStringExtra("untilDate"),Toast.LENGTH_SHORT).show();
//                Toast.makeText(this,data.getStringExtra("status"),Toast.LENGTH_SHORT).show();

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
        super.onBackPressed();
    }
}
