package com.hudipo.pum_indomaret.features.approval.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.adapter.ApprovalHistoryAdapter;
import com.hudipo.pum_indomaret.features.approval.presenter.ApprovalHistoryPresenter;
import com.hudipo.pum_indomaret.features.approval.view.ApprovalHistoryContract;
import com.hudipo.pum_indomaret.model.approval.ApprovalModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApprovalHistoryFragment extends Fragment implements ApprovalHistoryContract.ApprovalHistoryView {
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @BindView(R.id.cbApproval)
    CheckBox cbApproval;
    @BindView(R.id.btnDelete)
    LinearLayout btnDelete;

    private View view;
    private ApprovalHistoryAdapter approvalAdapter;
    private ApprovalHistoryPresenter presenter;

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
        cbApproval.setOnCheckedChangeListener((compoundButton, b) -> approvalAdapter.setAllChecked(b));
    }

    @Override
    public void showData(List<ApprovalModel> approvalModels) {
        approvalAdapter = new ApprovalHistoryAdapter(approvalModels);
        rvHistory.setAdapter(approvalAdapter);
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
