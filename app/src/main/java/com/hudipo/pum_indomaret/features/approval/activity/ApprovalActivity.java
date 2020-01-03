package com.hudipo.pum_indomaret.features.approval.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.fragment.ApprovalFragment;
import com.hudipo.pum_indomaret.features.approval.fragment.ApprovalHistoryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApprovalActivity extends AppCompatActivity {
    @BindView(R.id.bottom_menu_approval)
    BottomNavigationView bottomNavigationView;

    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        ButterKnife.bind(this);

        boolean isLoadHistory = getIntent().getBooleanExtra("isLoadHistory", false);
        if (isLoadHistory) {
            loadFragment(new ApprovalHistoryFragment());
            bottomNavigationView.setSelectedItemId(R.id.menu_history);
        } else {
            loadFragment(new ApprovalFragment());
        }

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(listener, new IntentFilter("FINISH_ACTIVITY"));

        setClick();
    }

    private void setClick() {
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menu_approval:
                    loadFragment(new ApprovalFragment());
                    break;
                case R.id.menu_history:
                    loadFragment(new ApprovalHistoryFragment());
                    break;
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_approval, fragment)
                .commit();
    }

    //terima brodcast
    private BroadcastReceiver listener = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    public void onBackPressed(){
        super.onBackPressed();
//        Animatoo.animateSlideRight(this); //fire the slide left animation
        finish();

    }


}
