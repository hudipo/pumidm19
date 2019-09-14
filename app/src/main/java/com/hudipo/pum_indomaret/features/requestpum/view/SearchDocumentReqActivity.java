package com.hudipo.pum_indomaret.features.requestpum.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.requestpum.adapter.SearchDocumentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchDocumentReqActivity extends AppCompatActivity {

    @BindView(R.id.rcvSearchDoc)
    RecyclerView rcvSearchDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_search_doc);
        ButterKnife.bind(this);
        rcvSearchDoc.setHasFixedSize(true);
        rcvSearchDoc.setLayoutManager(new LinearLayoutManager(this));
        rcvSearchDoc.setAdapter(new SearchDocumentAdapter(docNum -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",docNum);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }));
    }
}
