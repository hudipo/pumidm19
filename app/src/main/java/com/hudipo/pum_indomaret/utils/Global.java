package com.hudipo.pum_indomaret.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.hudipo.pum_indomaret.model.OptionItem;
import com.hudipo.pum_indomaret.utils.spinner.CustomSpinnerFragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

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

    static public String priceFormatter(String price){
        return NumberFormat.getInstance(new Locale(("id"))).format(Double.parseDouble(price));
    }

    static public String getRandomString(){
        String SALTCHARS = "abcdefghijklmnopqrstu1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 5) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }
}
