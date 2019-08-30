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
import com.hudipo.pum_indomaret.features.approval.adapter.ApprovalAdapter;
import com.hudipo.pum_indomaret.features.approval.presenter.ApprovalPresenter;
import com.hudipo.pum_indomaret.features.approval.view.ApprovalContract;
import com.hudipo.pum_indomaret.model.approval.ApprovalModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApprovalFragment extends Fragment implements ApprovalContract.ApprovalView {
    @BindView(R.id.rvApproval)
    RecyclerView rvApproval;
    @BindView(R.id.cbApproval)
    CheckBox cbApproval;
    @BindView(R.id.btnApprove)
    LinearLayout btnApprove;
    @BindView(R.id.btnReject)
    LinearLayout btnReject;

    private View view;
    private ApprovalAdapter approvalAdapter;
    private ApprovalPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_approval, container, false);
        ButterKnife.bind(this, view);

        setView();
        presenter.getData();

        return view;
    }

    private void setView() {
        rvApproval.setLayoutManager(new LinearLayoutManager(getActivity()));
        cbApproval.setOnCheckedChangeListener((compoundButton, b) -> approvalAdapter.setAllChecked(b));
    }

    @Override
    public void showData(List<ApprovalModel> approvalModels) {
        approvalAdapter = new ApprovalAdapter(approvalModels);
        rvApproval.setAdapter(approvalAdapter);
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

        presenter = new ApprovalPresenter(getActivity());
        onAttachView();
    }
}
