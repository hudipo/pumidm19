package com.hudipo.pum_indomaret.features.status.contract;

import com.hudipo.pum_indomaret.features.status.model.FilterStatusModel;
import com.hudipo.pum_indomaret.features.status.model.StatusResponse;
import java.util.List;

public interface StatusContract {
    interface StatusView {
        void setStatusList(List<StatusResponse.StatusModel> statusList);

        void showLoading();

        void hideLoading();

        void toast(String stringMessage);
    }

    interface StatusPresenter {
        void onDetach();

        void getStatusList();

        void getFilteredStatusList(FilterStatusModel filterStatusModel);
    }

    interface StatusInteractor {

        interface OnFinishedListenerStatus {
            void onStatusListFetched(List<StatusResponse.StatusModel> statusList);

            void onFailure(Throwable t);
        }

        void getStatusList(StatusInteractor.OnFinishedListenerStatus onFinishedListenerStatus);

        void getFilteredStatusList(StatusInteractor.OnFinishedListenerStatus onFinishedListenerStatus, FilterStatusModel filterStatusModel);
    }
}
