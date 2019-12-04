package com.hudipo.pum_indomaret.features.approval.detail.view;

import com.hudipo.pum_indomaret.model.approval.detail.DataApproval;
import com.hudipo.pum_indomaret.view.MainView;

public class ApprovalDetailContract {

    public interface ApprovalDetailPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void getData(Integer pumTrxId);
        void approve(Integer pumTrxId, String pin);
        void reject(Integer pumTrxId, String pin, String reasonValidation);
    }

    public interface ApprovalDetailView extends MainView{
        void showData(DataApproval dataApproval);
        void error(String message);
        void showLoading();
        void dismissLoading();
        void success(int message);
    }
}
