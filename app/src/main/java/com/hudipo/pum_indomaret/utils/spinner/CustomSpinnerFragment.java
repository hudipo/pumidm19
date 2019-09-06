package com.hudipo.pum_indomaret.utils.spinner;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.OptionItem;
import com.hudipo.pum_indomaret.utils.spinner.adapter.AdapterSpinner;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomSpinnerFragment extends DialogFragment {
    
    @BindView(R.id.rvSpinner)
    RecyclerView rvSpinner;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private SpinnerListener spinnerListener;
    private ArrayList<OptionItem> listSpinner = new ArrayList<>();
    private String tittle;
    private int requestCode;

    public CustomSpinnerFragment(String tittle, int requestCode) {
        this.tittle = tittle;
        this.requestCode = requestCode;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context!=null){
            spinnerListener = (SpinnerListener) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        int margin = getResources().getDimensionPixelOffset(R.dimen.dialog_fragment_margin);
        Dialog dialog = getDialog();
        InsetDrawable insetDrawable = new InsetDrawable(new ColorDrawable(Color.TRANSPARENT), margin);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(insetDrawable);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_spinner, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapterSpinner();
        initView();
    }

    private void initView() {
        tvTitle.setText(tittle);
    }

    private void setAdapterSpinner() {
        AdapterSpinner adapterSpinner = new AdapterSpinner(listSpinner, optionItem -> {
            spinnerListener.onOptionItemSelected(optionItem, requestCode);
            getDialog().dismiss();
        });
        adapterSpinner.notifyDataSetChanged();

        rvSpinner.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSpinner.setAdapter(adapterSpinner);
    }

    public void setData(ArrayList<OptionItem> listSpinner){
        this.listSpinner.addAll(listSpinner);
    }

    public interface SpinnerListener{
        void onOptionItemSelected(OptionItem optionItem, int requestCode);
    }
}
