package com.hudipo.pum_indomaret.features.requestpum.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.hudipo.pum_indomaret.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ValidationReqActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_validation);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSubmitReq)
    void onClick(){
        new AlertDialog.Builder(this)
                .setTitle("Nuke planet Jupiter?")
                .setMessage("Note that nuking planet Jupiter will destroy everything in there.")
                .setPositiveButton("Nuke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Log.d("MainActivity", "Sending atomic bombs to Jupiter");
                    }
                })
                .setNegativeButton("Abort", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Log.d("MainActivity", "Aborting mission...");
                    }
                })
                .show();
        //startActivity(new Intent(ValidationReqActivity.this,SentReqActivity.class));
    }
}
