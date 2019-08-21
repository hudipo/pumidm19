package com.hudipo.pum_indomaret.features.approval.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ApprovalAdapter extends RecyclerView.Adapter<ApprovalAdapter.ApprovalViewHolder> {

    @NonNull
    @Override
    public ApprovalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovalViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ApprovalViewHolder extends RecyclerView.ViewHolder {
        public ApprovalViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
