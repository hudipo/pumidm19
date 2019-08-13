package com.hudipo.pum_indomaret.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hudipo.pum_indomaret.R;

import java.util.Objects;

public class CustomLoadingProgress {

    private AlertDialog.Builder dialogBuilder;
    private LinearLayout mainLayout;
    private TextView textView;
    private AlertDialog b;

    public void showCustomDialog(Context context) {
        dialogBuilder = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.costum_progress_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        mainLayout = dialogView.findViewById(R.id.mainLayout);
        textView = dialogView.findViewById(R.id.textView);
        textView.setVisibility(View.GONE);
        b = dialogBuilder.create();
        Objects.requireNonNull(b.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        b.setCancelable(false);
        b.show();
    }
    public void showCustomDialog(Context context, String text) {
        dialogBuilder = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.costum_progress_dialog, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        mainLayout = dialogView.findViewById(R.id.mainLayout);
        textView = dialogView.findViewById(R.id.textView);
        textView.setText(text);
        b = dialogBuilder.create();
        Objects.requireNonNull(b.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        b.setCancelable(false);
        b.show();
    }

    public void closeCustomDialog(){
        b.dismiss();
    }
}
