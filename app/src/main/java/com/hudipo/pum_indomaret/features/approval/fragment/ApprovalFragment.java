package com.hudipo.pum_indomaret.features.approval.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import java.util.ArrayList;
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
    @BindView(R.id.llAction)
    LinearLayout llAction;

    private View view;
    private ApprovalAdapter approvalAdapter;
    private ApprovalPresenter presenter;
    private List<ApprovalModel> approvalSelectedList = new ArrayList<>();
    private List<ApprovalModel> approvalModelList = new ArrayList<>();

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
        cbApproval.setOnCheckedChangeListener((compoundButton, b) ->{
            approvalAdapter.setAllChecked(b);
            if(b){
                approvalSelectedList.clear();
                approvalSelectedList.addAll(approvalModelList);
            }else approvalSelectedList.clear();
            initAction();
        });
    }

    @Override
    public void showData(List<ApprovalModel> approvalModels) {
        approvalModelList.clear();
        approvalModelList.addAll(approvalModels);

        approvalAdapter = new ApprovalAdapter(approvalModels, (approvalModel, checked) -> {
            if(checked){
                approvalSelectedList.add(approvalModel);
            }else {
                approvalSelectedList.remove(approvalModel);
            }
            initAction();
        });
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
    public void showAction() {
        if(llAction.getVisibility() == View.GONE){
            Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_up);
            llAction.setVisibility(View.VISIBLE);
            llAction.startAnimation(slideUp);
        }
    }

    @Override
    public void closeAction() {
        if(llAction.getVisibility() == View.VISIBLE){
            Animation slideDown = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down_out);
            llAction.setVisibility(View.GONE);
            llAction.startAnimation(slideDown);
        }
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

    private void initAction(){
        if(approvalSelectedList.size()>0){
            showAction();
        }else closeAction();
    }
}
