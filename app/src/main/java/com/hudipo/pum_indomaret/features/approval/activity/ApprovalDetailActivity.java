package com.hudipo.pum_indomaret.features.approval.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.utils.StartActivity;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApprovalDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_detail);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.ivBack)
    void back(){
        finish();
    }

    @OnClick(R.id.btnDetail)
    void showImageFile(){
        StartActivity.goTo(this, FileViewerActivity.class);
    }

    @OnClick(R.id.btnApprove)
    void approve(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.approve));
        alert.setMessage(getString(R.string.message_dialog_approve));
        alert.setPositiveButton(getString(R.string.yes), ((dialogInterface, i) -> {
            // TODO: 15/09/19 to pin activity
            Toast.makeText(this, "To PIN Activity", Toast.LENGTH_LONG).show();
        }));
        alert.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> dialogInterface.dismiss());
        alert.show();
    }


    @OnClick(R.id.btnReject)
    void reject(){
        FrameLayout container = new FrameLayout(this);

        container.addView(generateEditText());

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.reject));
        alert.setMessage(getString(R.string.message_dialog_reject));
        alert.setPositiveButton(getString(R.string.yes), ((dialogInterface, i) -> {
            // TODO: 15/09/19 to pin activity
            Toast.makeText(ApprovalDetailActivity.this, "To PIN Activity", Toast.LENGTH_LONG).show();
        }));
        alert.setNegativeButton(getString(R.string.no), (dialogInterface, i) -> dialogInterface.dismiss());
        alert.setView(container);
        alert.show();
    }

    private EditText generateEditText()
    {
        int padding = getResources().getDimensionPixelSize(R.dimen.min_margin);
        int margin = getResources().getDimensionPixelSize(R.dimen.main_margin);
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = margin;
        params.rightMargin = margin;

        final EditText input = new EditText(this);
        input.setLayoutParams(params);
        input.setBackground(getDrawable(R.drawable.bg_rounded_border_black_solid_black));
        input.setHint(getString(R.string.hint_dialog_reject));
        input.setMinLines(3);
        input.setGravity(Gravity.TOP);
        input.setPadding(padding, padding, padding, padding);
        return input;
    }
}
