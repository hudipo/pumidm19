package com.hudipo.pum_indomaret.features.approval.presenter;

import android.content.Context;

import com.hudipo.pum_indomaret.features.approval.view.ApprovalContract;
import com.hudipo.pum_indomaret.model.approval.ApprovalModel;

import java.util.ArrayList;
import java.util.List;

public class ApprovalPresenter implements ApprovalContract.ApprovalPresenterView<ApprovalContract.ApprovalView> {
    private Context context;
    private ApprovalContract.ApprovalView mView;

    public ApprovalPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(ApprovalContract.ApprovalView view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
    }

    @Override
    public void getData() {
        List<ApprovalModel> list = new ArrayList<>();

        ApprovalModel model = new ApprovalModel();
        model.setPumNumber("123123123");
        model.setPumRequester("Fakhri Abdullah");
        model.setAmount(20000000);

        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);
        list.add(model);

        mView.showData(list);
    }

    @Override
    public void searchData(String query) {

    }

    @Override
    public void reject(List list) {

    }

    @Override
    public void approve(List list) {

    }
}
