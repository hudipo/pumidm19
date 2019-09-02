package com.hudipo.pum_indomaret.features.requestpum.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.features.requestpum.adapter.SearchDocumentAdapter;

import butterknife.BindView;

public class SearchDocumentReqActivity extends AppCompatActivity {

    @BindView(R.id.rcvSearchDoc)
    RecyclerView rcvSearchDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_search_doc_req);
        rcvSearchDoc.setHasFixedSize(true);
        rcvSearchDoc.setLayoutManager(new LinearLayoutManager(this));
        rcvSearchDoc.setAdapter(new SearchDocumentAdapter());
    }
}
