package com.hudipo.pum_indomaret.features.approval.view;

import com.hudipo.pum_indomaret.model.approval.ApprovalListModel;
import com.hudipo.pum_indomaret.view.MainView;

import java.util.List;

public class ApprovalContract {

    public interface ApprovalPresenterView<T extends MainView>{
        void onAttach(T view);
        void onDetach();
        void getData();
        void searchData(String query);
        void approve(List<ApprovalListModel> approvalModels, String pin);
        void reject(List<ApprovalListModel> approvalModels, String pin, String reasonValidation);
    }

    public interface ApprovalView extends MainView{
        void showData(List<ApprovalListModel> approvalModels);
        void errorNotSelected(String message);
        void error(String message);
        void showLoading();
        void dismissLoading();
        void success(int requestType);
        void showAction();
        void closeAction();
    }
}
