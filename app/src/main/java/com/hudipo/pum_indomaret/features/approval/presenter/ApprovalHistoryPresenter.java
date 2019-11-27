package com.hudipo.pum_indomaret.features.approval.presenter;

import android.content.Context;

import com.hudipo.pum_indomaret.features.approval.view.ApprovalHistoryContract;

import java.util.ArrayList;
import java.util.List;

public class ApprovalHistoryPresenter implements ApprovalHistoryContract.ApprovalHistoryPresenterView<ApprovalHistoryContract.ApprovalHistoryView> {
    private Context context;
    private ApprovalHistoryContract.ApprovalHistoryView mView;

    public ApprovalHistoryPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void onAttach(ApprovalHistoryContract.ApprovalHistoryView view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        mView = null;
    }

    @Override
    public void getData() {
//        List<ApprovalModel> list = new ArrayList<>();
//
//        ApprovalModel model = new ApprovalModel();
//        model.setPumNumber("123123123");
//        model.setPumRequester("Fakhri Abdullah");
//        model.setAmount(20000000);
//        model.setType(1);
//
//        list.add(model);
//        list.add(model);
//        list.add(model);
//
//
//        ApprovalModel model2 = new ApprovalModel();
//        model2.setPumNumber("123123123");
//        model2.setPumRequester("Fakhri Abdullah");
//        model2.setAmount(20000000);
//        model2.setType(2);
//
//        list.add(model2);
//        list.add(model2);
//        list.add(model2);
//
//        mView.showData(list);
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
