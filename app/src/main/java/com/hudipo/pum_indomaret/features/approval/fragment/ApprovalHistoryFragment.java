package com.hudipo.pum_indomaret.features.approval.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.activity.ApprovalFilterActivity;
import com.hudipo.pum_indomaret.features.approval.adapter.ApprovalHistoryAdapter;
import com.hudipo.pum_indomaret.features.approval.presenter.ApprovalHistoryPresenter;
import com.hudipo.pum_indomaret.features.approval.view.ApprovalHistoryContract;
import com.hudipo.pum_indomaret.model.approval.ApprovalListModel;
import com.hudipo.pum_indomaret.model.approval.history.ApprovalHistoryListModel;
import com.hudipo.pum_indomaret.utils.RequestCode;
import com.hudipo.pum_indomaret.utils.StartActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;

public class ApprovalHistoryFragment extends Fragment implements ApprovalHistoryContract.ApprovalHistoryView {
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @BindView(R.id.swipeRefreshApproval)
    SwipeRefreshLayout swipeRefreshLayout;

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
    }

    @Override
    public void showData(List<ApprovalHistoryListModel> approvalModels) {
        approvalModelList.clear();
        approvalModelList.addAll(approvalModels);
        approvalAdapter.updateListApproval(approvalModels);
    }

    @OnClick(R.id.btnFilter)
    void filter(){
        Intent intent = new Intent(getActivity(), ApprovalFilterActivity.class);
        Objects.requireNonNull(getActivity()).startActivityForResult(intent, RequestCode.CODE_FILTER);
    }

    @Override
    public void error(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
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

    }
}
