package com.hudipo.pum_indomaret.features.response.subfeatures.responsecartpartial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.subfeatures.partialresponsesent.PartialResponseSentActivity;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsecartpartial.adapter.ResponseCartPartialAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResponseCartPartialActivity extends AppCompatActivity {

    @BindView(R.id.rvResponseCart)
    RecyclerView rvResponseCart;

    private ResponseCartPartialAdapter responseCartPartialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_cart_partial);
        ButterKnife.bind(this);

        setAdapterResponseCart();
    }

    private void setAdapterResponseCart() {
        responseCartPartialAdapter = new ResponseCartPartialAdapter(this);
        responseCartPartialAdapter.notifyDataSetChanged();

        rvResponseCart.setLayoutManager(new LinearLayoutManager(this));
        rvResponseCart.setAdapter(responseCartPartialAdapter);
    }

    @OnClick(R.id.btnAddResponse)
    void btnAddResponse(){
        finish();
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        finish();
    }

    @OnClick(R.id.btnSubmit)
    void btnSubmit(){
        startActivity(new Intent(this, PartialResponseSentActivity.class));
        finish();
    }

    @OnClick(R.id.btnCancel)
    void btnCancel(){
        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
    }
}
