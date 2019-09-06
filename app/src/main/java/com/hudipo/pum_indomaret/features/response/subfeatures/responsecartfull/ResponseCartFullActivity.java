package com.hudipo.pum_indomaret.features.response.subfeatures.responsecartfull;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.response.subfeatures.fullresponsesent.FullResponseSentActivity;
import com.hudipo.pum_indomaret.features.response.subfeatures.responsecartfull.adapter.ResponseCartFullAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResponseCartFullActivity extends AppCompatActivity {

    @BindView(R.id.rvResponseCart)
    RecyclerView rvResponseCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_cart_full);
        ButterKnife.bind(this);

        setAdapterResponseCartFull();
    }

    private void setAdapterResponseCartFull() {
        ResponseCartFullAdapter responseCartFullAdapter = new ResponseCartFullAdapter(this);
        responseCartFullAdapter.notifyDataSetChanged();

        rvResponseCart.setLayoutManager(new LinearLayoutManager(this));
        rvResponseCart.setAdapter(responseCartFullAdapter);
    }

    @OnClick(R.id.btnBack)
    void btnBack(){
        finish();
    }

    @OnClick(R.id.btnAddResponse)
    void btnAddResponse(){
        finish();
    }

    @OnClick(R.id.btnSubmit)
    void btnSubmit(){
        startActivity(new Intent(this, FullResponseSentActivity.class));
        finish();
    }

    @OnClick(R.id.btnCancel)
    void btnCancel(){
        Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
    }
}
