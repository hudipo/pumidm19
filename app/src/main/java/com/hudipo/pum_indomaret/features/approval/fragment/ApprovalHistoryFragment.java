package com.hudipo.pum_indomaret.features.approval.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.activity.ApprovalFilterActivity;
import com.hudipo.pum_indomaret.features.approval.adapter.ApprovalHistoryAdapter;
import com.hudipo.pum_indomaret.features.approval.presenter.ApprovalHistoryPresenter;
import com.hudipo.pum_indomaret.features.approval.view.ApprovalHistoryContract;
import com.hudipo.pum_indomaret.model.approval.ApprovalModel;
import com.hudipo.pum_indomaret.utils.RequestCode;
import com.hudipo.pum_indomaret.utils.StartActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApprovalHistoryFragment extends Fragment implements ApprovalHistoryContract.ApprovalHistoryView {
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;

    private View view;
    private ApprovalHistoryAdapter approvalAdapter;
    private ApprovalHistoryPresenter presenter;
    private List<ApprovalModel> approvalSelectedList = new ArrayList<>();
    private List<ApprovalModel> approvalModelList = new ArrayList<>();
    private boolean isCheckedAll = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_approval_history, container, false);
        ButterKnife.bind(this, view);

        setView();
        presenter.getData();

        return view;
    }

    private void setView() {
        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void showData(List<ApprovalModel> approvalModels) {
        approvalModelList.clear();
        approvalModelList.addAll(approvalModels);

        approvalAdapter = new ApprovalHistoryAdapter(approvalModels);
        rvHistory.setAdapter(approvalAdapter);
    }

    @OnClick(R.id.btnFilter)
    void filter(){
        Intent intent = new Intent(getActivity(), ApprovalFilterActivity.class);
        Objects.requireNonNull(getActivity()).startActivityForResult(intent, RequestCode.CODE_FILTER);
    }

    @Override
    public void errorNotSelected(String message) {

    }

    @Override
    public void error(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void success(String message) {

    }

    @Override
    public void onAttachView() {
        presenter.onAttach(this);
    }

    @Override
    public void onDetachView() {
        presenter.onDetach();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        presenter = new ApprovalHistoryPresenter(getActivity());
        onAttachView();
    }
}
