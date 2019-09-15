package com.hudipo.pum_indomaret.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Objects;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface DialogDataListener{
        void onDialogSet(String tag, int year, int month, int dayOfMonth);
    }

    private DialogDataListener dialogDataListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null){
            dialogDataListener = (DialogDataListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (dialogDataListener != null){
            dialogDataListener = null;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, date);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMonth) {
        dialogDataListener.onDialogSet(getTag(), year, month, dayOfMonth);
    }
}
