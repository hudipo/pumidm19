package com.hudipo.pum_indomaret.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.hudipo.pum_indomaret.model.OptionItem;
import com.hudipo.pum_indomaret.utils.spinner.CustomSpinnerFragment;

import java.util.ArrayList;

public class Global {
    static public void openPicker(FragmentManager fragmentManager, ArrayList<OptionItem> options, int requestCode, String tittle){
        String TAG_DIALOG = "Dialog";

        CustomSpinnerFragment customSpinnerFragment = new CustomSpinnerFragment(tittle, requestCode);
        customSpinnerFragment.setData(options);
        customSpinnerFragment.show(fragmentManager.beginTransaction(), TAG_DIALOG);
    }

    static public void toast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
