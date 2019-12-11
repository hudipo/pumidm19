package com.hudipo.pum_indomaret.features.approval.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.activity.ApprovalFilterActivity;
import com.hudipo.pum_indomaret.features.approval.adapter.ApprovalHistoryAdapter;
import com.hudipo.pum_indomaret.features.approval.presenter.ApprovalHistoryPresenter;
import com.hudipo.pum_indomaret.features.approval.view.ApprovalHistoryContract;
import com.hudipo.pum_indomaret.model.approval.history.ApprovalHistoryListModel;
import com.hudipo.pum_indomaret.utils.PumDateFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;
import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_START_DATE;
import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_STATUS;
import static com.hudipo.pum_indomaret.utils.Extra.EXTRA_UNTIL_DATE;
import static com.hudipo.pum_indomaret.utils.RequestCode.CODE_FILTER;

public class ApprovalHistoryFragment extends Fragment implements ApprovalHistoryContract.ApprovalHistoryView {
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @BindView(R.id.swipeRefreshApproval)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tvError)
    TextView tvError;
    @BindView(R.id.search)
    SearchView searchView;

    private View view;
    private ApprovalHistoryAdapter approvalAdapter;
    private ApprovalHistoryPresenter presenter;
    private List<ApprovalHistoryListModel> approvalModelList = new ArrayList<>();
    private HashMap<String, RequestBody> params = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_approval_history, container, false);
        ButterKnife.bind(this, view);
        presenter = new ApprovalHistoryPresenter(getActivity());
        setView();

        onAttachView();

        return view;
    }

    private void setView() {
        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.getData(params));

        approvalAdapter = new ApprovalHistoryAdapter(new ArrayList<>());
        rvHistory.setAdapter(approvalAdapter);
        setSearch();
    }

    private void setSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                approvalAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void showData(List<ApprovalHistoryListModel> approvalModels) {
        approvalModelList.clear();
        approvalModelList.addAll(approvalModels);
        approvalAdapter.updateListApproval(approvalModels);
        rvHistory.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnFilter)
    void filter() {
        Intent intent = new Intent(getActivity(), ApprovalFilterActivity.class);
        startActivityForResult(intent, CODE_FILTER);
        Animatoo.animateSlideUp(getContext());
    }

    @Override
    public void error(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
        rvHistory.setVisibility(View.GONE);
    }

    @Override
    public void dismissLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAttachView() {
        presenter.onAttach(this);
        presenter.getData(params);
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_FILTER) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String startDate = data.getStringExtra(EXTRA_START_DATE);
                    String untilDate = data.getStringExtra(EXTRA_UNTIL_DATE);
                    String status = data.getStringExtra(EXTRA_STATUS);

                    HashMap<String, RequestBody> params = new HashMap<>();

                    RequestBody reqStart = RequestBody.create(PumDateFormat.dateFormatServer(startDate), MediaType.parse("text/plain"));
                    RequestBody reqUntil = RequestBody.create(PumDateFormat.dateFormatServer(untilDate), MediaType.parse("text/plain"));

                    params.put("start_date", reqStart);
                    params.put("end_date", reqUntil);
                    if (status != null) {
                        RequestBody reqStatus = RequestBody.create(status, MediaType.parse("text/plain"));
                        params.put("status", reqStatus);
                    }

                    presenter.getData(params);
                }
            }
        }
    }




    @OnClick(R.id.ivBack)
    void back() {
        Objects.requireNonNull(getActivity()).onBackPressed();
        Animatoo.animateSlideRight(getContext());
    }


}
