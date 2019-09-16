package com.hudipo.pum_indomaret.features.status.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hudipo.pum_indomaret.R;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder>{

    public interface ItemClickListener{
        void onItemClick(View view);
    }

    private ItemClickListener itemClickListener;

    public StatusAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter_status, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(itemClickListener);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bindItem(ItemClickListener itemClickListener){
            itemView.setOnClickListener(view ->
                    itemClickListener.onItemClick(view)
            );
        }
    }
}
