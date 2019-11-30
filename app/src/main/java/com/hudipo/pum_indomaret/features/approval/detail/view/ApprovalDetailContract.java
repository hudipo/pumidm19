package com.hudipo.pum_indomaret.features.approval.detail.view;

import com.hudipo.pum_indomaret.model.approval.ApprovalListModel;
import com.hudipo.pum_indomaret.model.approval.detail.DataApproval;
import com.hudipo.pum_indomaret.view.MainView;

import java.util.List;

public class ApprovalDetailContract {

    public interface ApprovalDetailPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void getData(Integer pumTrxId);
        void approve(ApprovalListModel approvalModels);
        void reject(ApprovalListModel approvalModels);
    }

    public interface ApprovalDetailView extends MainView{
        void showData(DataApproval dataApproval);
        void error(String message);
        void showLoading();
        void dismissLoading();
        void success(String message);
    }
}
