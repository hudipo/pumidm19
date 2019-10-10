package com.hudipo.pum_indomaret.features.status.contract;

import com.hudipo.pum_indomaret.features.status.model.StatusFilterRequestBody;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;

import java.util.ArrayList;
import java.util.List;

public interface StatusContract {

    interface StatusFilterView {

        void setStartDate(String date);

        void setUntilDate(String date);

        void setStatusList(ArrayList<String> statusList);

        void setDatePickerView(int year, int month, int day);

        void goBackToStatusAct(String startDate, String untilDate, String status);

        void toast(String stringMessage);
    }

    interface StatusFilterPresenter {

        void validateDate(String startDate, String untilDate, String status);

        void onDateSet(int year, int month, int day);

        void getDatePickerData();

        void onStartDateClicked();

        void onUntilDateClicked();

        void onDetach();

        void getStatusList();

    }

    interface StatusFilterInteractor {

        interface OnFinishedListenerStatusFilter {
            void onStatusListFetched(ArrayList<String > statusList);

            void onFailure(String errorMessage);
        }

        void getStatusList(StatusFilterInteractor.OnFinishedListenerStatusFilter onFinishedListenerStatusFilter);

    }

    interface StatusView {
        void setStatusList(List<StatusResponse.StatusModel> statusList);

        void showLoading();

        void hideLoading();

        void toast(String stringMessage);
    }

    interface StatusPresenter {
        void onDetach();

        void getStatusList();

        void onRefresh();

        void getFilteredStatusList(String startDate, String untilDate, String status);
    }

    interface StatusInteractor {

        interface OnFinishedListenerStatus {
            void onStatusListFetched(List<StatusResponse.StatusModel> statusList);

            void onFailure(String errorMessage);
        }

        void getStatusList(StatusInteractor.OnFinishedListenerStatus onFinishedListenerStatus);

        void getFilteredStatusList(StatusInteractor.OnFinishedListenerStatus onFinishedListenerStatus, String startDate, String untilDate, String status);
    }
}
