package com.hudipo.pum_indomaret.utils.spinner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hudipo.pum_indomaret.R;
import com.hudipo.pum_indomaret.model.OptionItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterSpinner extends RecyclerView.Adapter<AdapterSpinner.ViewHolder>{

    private ArrayList<OptionItem> listSpinner;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener{
        void onItemClick(OptionItem optionItem);
    }

    public AdapterSpinner(ArrayList<OptionItem> listSpinner, ItemClickListener itemClickListener) {
        this.listSpinner = listSpinner;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(listSpinner.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return listSpinner.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.tvLabel)
        TextView tvLabel;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindItem(OptionItem itemOption, ItemClickListener itemClickListener){
            if (itemOption.getIcon() == null){
                ivIcon.setVisibility(View.GONE);
            }else {
                Glide.with(itemView).load(itemOption.getIcon()).into(ivIcon);
            }
            tvLabel.setText(itemOption.getLabel());

            itemView.setOnClickListener(view -> itemClickListener.onItemClick(itemOption));
        }
    }
}
