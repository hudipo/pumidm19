package com.hudipo.pum_indomaret.features.response.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.adapter.ResponseAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResponseActivity extends AppCompatActivity {

    @BindView(R.id.rvResponse)
    RecyclerView rvResponse;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.swipeRefreshResponse)
    SwipeRefreshLayout swipeRefreshResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        ButterKnife.bind(this);

        setAdapter();
        initSwipeRefresh();
        onClick();
    }

    private void initSwipeRefresh() {
        swipeRefreshResponse.setOnRefreshListener(() -> {
            swipeRefreshResponse.setRefreshing(false);
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSlideRight(this); //fire the slide left animation
        finish();
    }

    private void onClick() {
        btnBack.setOnClickListener(view -> finish());
    }

    private void setAdapter() {
        ResponseAdapter responseAdapter = new ResponseAdapter(this);
        responseAdapter.notifyDataSetChanged();

        rvResponse.setLayoutManager(new LinearLayoutManager(this));
        rvResponse.setAdapter(responseAdapter);
    }
}
