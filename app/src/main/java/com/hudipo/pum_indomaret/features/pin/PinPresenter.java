package com.hudipo.pum_indomaret.features.pin;

import com.hudipo.pum_indomaret.model.pin.PinResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PinPresenter implements PinContract.PinPresenterView<PinContract.PinView> {

    private PinContract.PinView mView;
    private CompositeDisposable composite = new CompositeDisposable();

    @Override
    public void onAttach(PinContract.PinView view) {
        mView = view;
    }

    @Override
    public void onDetach() {
        if (mView != null){
            mView = null;
            composite.dispose();
        }
    }

    @Override
    public void clearComposite() {
        composite.clear();
    }

    @Override
    public void checkPinToServer(int empId, String pin) {
        mView.showProgress();
        composite.add(new ApiServices().getApiPumServices()
            .checkPin(empId, pin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(response -> {
                mView.hideProgress();
                if (response.isSuccessful()){
                    PinResponse pinResponse = response.body();
                    if (pinResponse != null && pinResponse.getMessage().equals("Pin Match")){
                        mView.showSuccess(pin);
                    }else {
                        mView.showFailed();
                    }
                }else {
                    mView.showFailed();
                }
            }, throwable -> {
                mView.hideProgress();
                mView.showFailed();
            }));
    }
}
