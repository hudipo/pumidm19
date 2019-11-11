package com.hudipo.pum_indomaret.features.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.adapter.HomeAdapter;
import com.hudipo.pum_indomaret.data.Data;
import com.hudipo.pum_indomaret.features.approval.activity.ApprovalActivity;
import com.hudipo.pum_indomaret.features.report.view.ReportActivity;
import com.hudipo.pum_indomaret.features.requestpum.activity.EmployeeReqActivity;
import com.hudipo.pum_indomaret.features.response.activity.ResponseActivity;
import com.hudipo.pum_indomaret.features.setting.activity.SettingActivity;
import com.hudipo.pum_indomaret.features.status.StatusActivity;
import com.hudipo.pum_indomaret.model.home.HomeModel;
import com.hudipo.pum_indomaret.model.trxtype.TrxTypeResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.tvNameHome)
    TextView tvNameHome;

    @BindView(R.id.tvPositionHome)
    TextView tvPositionHome;

    @BindView(R.id.tvEmpNumHome)
    TextView tvEmpNumHome;
    @BindView(R.id.rvHome)
    RecyclerView rvHome;

    private CompositeDisposable composite = new CompositeDisposable();
    private HawkStorage hawkStorage;
    private HomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initHawkStorage();
        initAdapter();
        checkMenu();
        setView();
        getDeptAndSaveToHawkStorage();
        getTrxTypeAndSaveToHawkStorage();
    }

    private void checkMenu() {
        switch (hawkStorage.getUserData().getMenuId()){
            case 28 :
            case 71 :
            case 72 :
                adapter.removeItem(HomeAdapter.request);
                adapter.removeItem(HomeAdapter.approval);
                adapter.removeItem(HomeAdapter.response);
                adapter.removeItem(HomeAdapter.status);
                break;
            case 29 :
            case 65 :
            case 69 :
                adapter.removeItem(HomeAdapter.approval);
                break;
            case 30 :
            case 64 :
                adapter.removeItem(HomeAdapter.status);
                adapter.removeItem(HomeAdapter.request);
                adapter.removeItem(HomeAdapter.response);
                break;
            case 37 :
                adapter.removeItem(HomeAdapter.request);
                adapter.removeItem(HomeAdapter.approval);
                adapter.removeItem(HomeAdapter.response);
                adapter.removeItem(HomeAdapter.reports);
                adapter.removeItem(HomeAdapter.status);
                adapter.removeItem(HomeAdapter.settings);
                break;
        }
    }

    private void initAdapter() {
        adapter = new HomeAdapter(homeItem -> {
            switch (homeItem.getId()){
                case 0 :
                    startActivity(new Intent(this, EmployeeReqActivity.class));
                    break;
                case 1 :
                    startActivity(new Intent(this, ApprovalActivity.class));
                    break;
                case 2 :
                    startActivity(new Intent(this, StatusActivity.class));
                    break;
                case 3 :
                    startActivity(new Intent(this, ResponseActivity.class));
                    break;
                case 4 :
                    startActivity(new Intent(this, SettingActivity.class));
                    break;
                case 5 :
                    startActivity(new Intent(this, ReportActivity.class));
                    break;
            }
        });
        adapter.setListDataHome(Data.dataHome(this));
        rvHome.setLayoutManager(new GridLayoutManager(this, 2));
        rvHome.setAdapter(adapter);
    }

    private void initHawkStorage() {
        hawkStorage = new HawkStorage(this);
    }

    private void getTrxTypeAndSaveToHawkStorage() {
        composite.add(new ApiServices().getApiPumServices()
                .getTrxType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trxTypeResponse -> {
                    if (trxTypeResponse != null){
                        hawkStorage.setTrxTypeData(trxTypeResponse);
                    }
                }, throwable -> {

                }));
    }

    private void getDeptAndSaveToHawkStorage() {
        composite.add(new ApiServices().getApiPumServices()
        .getDepartement()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(departmentResponse -> {
            if (departmentResponse != null){
                hawkStorage.setDepartmentData(departmentResponse);
            }else {
            }
        }, throwable -> Toast.makeText(this, "error : "+throwable.getMessage(), Toast.LENGTH_SHORT).show()));
    }

    private void setView() {
        HawkStorage hawkStorage = new HawkStorage(this);
        if (hawkStorage.getUserData() != null){
            tvNameHome.setText(hawkStorage.getUserData().getName());
            tvPositionHome.setText(hawkStorage.getUserData().getPosition());
            tvEmpNumHome.setText(hawkStorage.getUserData().getEmpNum());
        }
    }
}
