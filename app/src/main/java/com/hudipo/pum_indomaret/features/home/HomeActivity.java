package com.hudipo.pum_indomaret.features.home;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.adapter.HomeAdapter;
import com.hudipo.pum_indomaret.data.Data;
import com.hudipo.pum_indomaret.features.approval.activity.ApprovalActivity;
import com.hudipo.pum_indomaret.features.report.view.ReportActivity;
import com.hudipo.pum_indomaret.features.requestpum.activity.ReqEmployeeActivity;
import com.hudipo.pum_indomaret.features.response.activity.ResponseActivity;
import com.hudipo.pum_indomaret.features.setting.activity.SettingActivity;
import com.hudipo.pum_indomaret.features.status.StatusActivity;
import com.hudipo.pum_indomaret.model.firebase.UploadTokenResponse;
import com.hudipo.pum_indomaret.model.login.User;
import com.hudipo.pum_indomaret.model.setting.UploadProfilePicResponse;
import com.hudipo.pum_indomaret.networking.ApiServices;
import com.hudipo.pum_indomaret.networking.RetrofitClient;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static com.yalantis.ucrop.UCropFragment.TAG;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.tvNameHome)
    TextView tvNameHome;

    @BindView(R.id.tvPositionHome)
    TextView tvPositionHome;

    @BindView(R.id.tvEmpNumHome)
    TextView tvEmpNumHome;
    @BindView(R.id.rvHome)
    RecyclerView rvHome;
    @BindView(R.id.swipeRefreshHome)
    SwipeRefreshLayout swipeRefreshHome;

    private CompositeDisposable composite = new CompositeDisposable();
    private HawkStorage hawkStorage;
    private HomeAdapter adapter;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initHawkStorage();
        initAdapter();
        initSwipeRefresh();
        getDeptAndSaveToHawkStorage();
        getTrxTypeAndSaveToHawkStorage();

        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        initFirebase();
    }

    private void initSwipeRefresh() {
        swipeRefreshHome.setOnRefreshListener(() -> {
            swipeRefreshHome.setRefreshing(false);
            initAdapter();
        });
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
                    startActivity(new Intent(this, ReqEmployeeActivity.class));
                    Animatoo.animateZoom(this);
                    break;
                case 1 :
                    startActivity(new Intent(this, ApprovalActivity.class));
                    Animatoo.animateZoom(this);
                    break;
                case 2 :
                    startActivity(new Intent(this, StatusActivity.class));
                    Animatoo.animateZoom(this);
                    break;
                case 3 :
                    startActivity(new Intent(this, ResponseActivity.class));
                    Animatoo.animateZoom(this);
                    break;
                case 4 :
                    startActivity(new Intent(this, SettingActivity.class));
                    Animatoo.animateZoom(this);
                    break;
                case 5 :
                    startActivity(new Intent(this, ReportActivity.class));
                    Animatoo.animateZoom(this);
                    break;
            }
        });
        adapter.setListDataHome(Data.dataHome(this));
        rvHome.setLayoutManager(new GridLayoutManager(this, 2));
        rvHome.setAdapter(adapter);

        checkMenu();
        setView();
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
                    Toast.makeText(this, "error : "+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }

    private void getDeptAndSaveToHawkStorage() {
        composite.add(new ApiServices().getApiPumServices()
                .getDepartment(hawkStorage.getUserData().getOrgId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(departmentResponse -> {
                    if (departmentResponse != null){
                        hawkStorage.setDepartmentData(departmentResponse);
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

    private void initFirebase() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("firebase", "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = Objects.requireNonNull(task.getResult()).getToken();
                    Log.d("firebase", "onComplete: "+token);
                    uploadToken(token);
                });
    }

    private void uploadToken(String token) {
        composite.add(new ApiServices().getApiPumServices()
                .uploadTokenFirebase(hawkStorage.getUserData().getEmpId(), token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d(TAG, "uploadToken: "+response);
                }, throwable -> {
                    Log.d(TAG, "uploadToken: "+throwable.getMessage());
                }));
    }

    //terima brodcast
    private BroadcastReceiver listener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent ) {
            String token = intent.getStringExtra("token");
            uploadToken(token);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        localBroadcastManager.registerReceiver(listener, new IntentFilter("FIREBASE_TOKEN"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        localBroadcastManager.unregisterReceiver(listener);
    }
}
