package com.hudipo.pum_indomaret.features.response.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.adapter.ResponseAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResponseActivity extends AppCompatActivity {

    @BindView(R.id.rvResponse)
    RecyclerView rvResponse;
    @BindView(R.id.btnBack)
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        ButterKnife.bind(this);

        setAdapter();
        onClick();
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
