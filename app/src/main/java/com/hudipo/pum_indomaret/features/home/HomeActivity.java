package com.hudipo.pum_indomaret.features.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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
import com.hudipo.pum_indomaret.utils.Global;
import com.hudipo.pum_indomaret.utils.HawkStorage;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
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

    @BindView(R.id.tvRespName)
    TextView tvRespName;

    @BindView(R.id.tvEmpNumHome)
    TextView tvEmpNumHome;
    @BindView(R.id.rvHome)
    RecyclerView rvHome;
    @BindView(R.id.swipeRefreshHome)
    SwipeRefreshLayout swipeRefreshHome;
    @BindView(R.id.ciPhotoProfile)
    CircleImageView ciPhotoProfile;

    private CompositeDisposable composite = new CompositeDisposable();
    private HawkStorage hawkStorage;
    private HomeAdapter adapter;
    private LocalBroadcastManager localBroadcastManager;
    boolean doubleBackToExitPressedOnce = false;
    private int menuId;

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
        ciPhotoProfile.setOnClickListener(view ->
                startActivity(new Intent(this, SettingActivity.class))

        );
        Animatoo.animateZoom(this);

        initFirebase();
        Log.d(TAG, "onCreate: "+getIntent().getStringExtra("kodeNotif"));
        //jika dari notificaton
        if(getIntent().getStringExtra("kodeNotif") != null){
            Log.d("fakhri", "onCreate: masuk");
            String notificationId = getIntent().getStringExtra("kodeNotif");
            assert notificationId != null;
            if(notificationId.equals("1")){
                startActivity(new Intent(this, ApprovalActivity.class));
            }else if(notificationId.equals("2")){
                startActivity(new Intent(this, StatusActivity.class));
            }
        }
    }

    private void initSwipeRefresh() {
        swipeRefreshHome.setOnRefreshListener(() -> {
            swipeRefreshHome.setRefreshing(false);
            initAdapter();
        });
    }

    private void checkAvaPum() {
        composite.add(new ApiServices().getApiPumServices()
                .cekAvaPum(hawkStorage.getUserData().getEmpId(), hawkStorage.getUserData().getMaxCreatePum())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.code() != 200) {
                        new AlertDialog.Builder(this, R.style.CustomDialogTheme)
                                .setTitle("Sorry")
                                .setMessage("Your Request PUM has reached the maximum number")
                                .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                                .show();

                    } else {
                        startActivity(new Intent(this, ReqEmployeeActivity.class));
                        Animatoo.animateZoom(this);
                    }
                }));
    }

    private void checkMenu() {
        menuId = hawkStorage.getUserData().getMenuId();
        Log.d(TAG, "checkMenu: " + menuId);
        switch (menuId) {

            case 30:
            case 64:
                adapter.removeItem(HomeAdapter.request);
                adapter.removeItem(HomeAdapter.status);
                adapter.removeItem(HomeAdapter.response);
                break;
            case 28:
            case 71:
            case 72:
                adapter.removeItem(HomeAdapter.reports);
                adapter.removeItem(HomeAdapter.response);
                adapter.removeItem(HomeAdapter.approval);
                adapter.removeItem(HomeAdapter.request);
                break;
            case 29:
            case 65:
            case 69:
                adapter.removeItem((HomeAdapter.status));
                break;

//            case 28:
//                menuId = 28;
//                adapter.removeItem(HomeAdapter.request);
//                adapter.removeItem(HomeAdapter.approval);
//                adapter.removeItem(HomeAdapter.response);
//                adapter.removeItem(HomeAdapter.response);
//                adapter.removeItem(HomeAdapter.status);
//                break;
//            case 29:
//                menuId = 29;
//                adapter.removeItem(HomeAdapter.approval);
//                break;
//            case 30:
//                menuId = 30;
//                adapter.removeItem(HomeAdapter.request);
//                adapter.removeItem(HomeAdapter.reports);
//                adapter.removeItem(HomeAdapter.response);
//                break;
//            case 37:
//                menuId = 37;
//                adapter.removeItem(HomeAdapter.request);
//                adapter.removeItem(HomeAdapter.status);
//                adapter.removeItem(HomeAdapter.approval);
//                adapter.removeItem(HomeAdapter.reports);
//                adapter.removeItem(HomeAdapter.response);
//                break;
//            case 64:
//                menuId = 64;
//                adapter.removeItem(HomeAdapter.request);
//                adapter.removeItem(HomeAdapter.reports);
//                adapter.removeItem(HomeAdapter.response);
//                break;
//            case 65:
//                menuId = 65;
//                adapter.removeItem(HomeAdapter.approval);
//                break;
//            case 69:
//                menuId = 69;
//                adapter.removeItem(HomeAdapter.approval);
//                break;
//
//            case 71:
//                menuId = 71;
//                adapter.removeItem(HomeAdapter.request);
//                adapter.removeItem(HomeAdapter.status);
//                adapter.removeItem(HomeAdapter.approval);
//                adapter.removeItem(HomeAdapter.response);
//                break;
//            case 72:
//                menuId = 72;
//                adapter.removeItem(HomeAdapter.request);
//                adapter.removeItem(HomeAdapter.status);
//                adapter.removeItem(HomeAdapter.approval);
//                adapter.removeItem(HomeAdapter.response);
//                break;
        }
    }

    private void initAdapter() {
        adapter = new HomeAdapter(homeItem -> {
            switch (homeItem.getId()) {
                case 0:
                    checkAvaPum();
                    break;
                case 1:
                    startActivity(new Intent(this, ApprovalActivity.class));
                    Animatoo.animateZoom(this);
                    break;
                case 2:
                    startActivity(new Intent(this, StatusActivity.class));
                    Animatoo.animateZoom(this);
                    break;
                case 3:
                    startActivity(new Intent(this, ResponseActivity.class));
                    Animatoo.animateZoom(this);
                    break;
                case 4:
                    startActivity(new Intent(this, SettingActivity.class));
                    Animatoo.animateZoom(this);
                    break;
                case 5:
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
                    if (trxTypeResponse != null) {
                        hawkStorage.setTrxTypeData(trxTypeResponse);
                    }
                }, throwable -> {
                    Toast.makeText(this, "error : " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }

    private void getDeptAndSaveToHawkStorage() {
        composite.add(new ApiServices().getApiPumServices()
                .getDepartment(hawkStorage.getUserData().getOrgId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(departmentResponse -> {
                    if (departmentResponse != null) {
                        hawkStorage.setDepartmentData(departmentResponse);
                    }
                }, throwable -> Toast.makeText(this, "error : " + throwable.getMessage(), Toast.LENGTH_SHORT).show()));
    }

    private void setView() {
        HawkStorage hawkStorage = new HawkStorage(this);
        if (hawkStorage.getUserData() != null) {
            tvNameHome.setText(hawkStorage.getUserData().getName());
            tvPositionHome.setText(hawkStorage.getUserData().getPosition());
            tvRespName.setText(hawkStorage.getUserData().getRespName());
            tvEmpNumHome.setText(hawkStorage.getUserData().getEmpNum());
            if (tvPositionHome.getText().toString().matches("")) {
                tvPositionHome.setVisibility(View.GONE);
            }
            if (tvRespName.getText().toString().matches("SUPERUSER_MENU")) {
                tvRespName.setText("USER");
            } else if (tvRespName.getText().toString().matches("APPROVAL_MENU")) {
                tvRespName.setText("APPROVAL");
            }

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
                    Log.d("firebase", "onComplete: " + token);
                    uploadToken(token);
                });
    }

    private void uploadToken(String token) {
        composite.add(new ApiServices().getApiPumServices()
                .uploadTokenFirebase(hawkStorage.getUserData().getEmpId(), token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d(TAG, "uploadToken: " + response);
                }, throwable -> {
                    Log.d(TAG, "uploadToken: " + throwable.getMessage());
                }));
    }

    //terima brodcast
    private BroadcastReceiver listener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra("token");
            uploadToken(token);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        localBroadcastManager.registerReceiver(listener, new IntentFilter("FIREBASE_TOKEN"));
        String url = hawkStorage.getUserData().getPhotoProfile() + "?" + Global.getRandomString();
        Glide.with(this)
                .load(url)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .placeholder(R.drawable.person)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(ciPhotoProfile);
    }

    @Override
    protected void onPause() {
        super.onPause();
        localBroadcastManager.unregisterReceiver(listener);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 1800);
    }
}
