package com.hudipo.pum_indomaret.features.approval.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.approval.fragment.ApprovalFragment;
import com.hudipo.pum_indomaret.features.approval.fragment.ApprovalHistoryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApprovalActivity extends AppCompatActivity {
    @BindView(R.id.bottom_menu_approval)
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        ButterKnife.bind(this);

        loadFragment(new ApprovalFragment());

        setClick();
    }

    private void setClick() {
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch(menuItem.getItemId()){
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
}
